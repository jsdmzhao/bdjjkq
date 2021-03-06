package com.googlecode.jtiger.assess.evaluate.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.jtiger.assess.AssessConstants;
import com.googlecode.jtiger.assess.evaluate.EvaluateConstants;
import com.googlecode.jtiger.assess.evaluate.model.EvaluateRecord;
import com.googlecode.jtiger.assess.evaluate.model.EvaluateRecordDetail;
import com.googlecode.jtiger.assess.evaluate.model.EvaluateRecordTaskConst;
import com.googlecode.jtiger.assess.task.stat.empduty.model.EmployeeDutyRecord;
import com.googlecode.jtiger.assess.task.stat.empduty.service.EmployeeDutyRecordManager;
import com.googlecode.jtiger.assess.task.statcfg.model.Task;
import com.googlecode.jtiger.assess.task.statcfg.model.TaskType;
import com.googlecode.jtiger.assess.task.statcfg.service.TaskManager;
import com.googlecode.jtiger.assess.task.statcfg.service.TaskTypeManager;
import com.googlecode.jtiger.assess.transgress.stat.StatCondition;
import com.googlecode.jtiger.assess.transgress.stat.dao.TransgressSatDao;
import com.googlecode.jtiger.assess.transgress.statcfg.model.TransgressStatItem;
import com.googlecode.jtiger.core.service.BaseGenericsManager;
import com.googlecode.jtiger.modules.hr.dept.model.Dept;
import com.ibm.icu.util.Calendar;

/**
 * 评分记录业务层类
 * 
 * @author DELPHI
 * 
 */
@Service
public class EvaluateRecordManager extends BaseGenericsManager<EvaluateRecord> {
	/** 日常勤务Manager */
	@Autowired
	private EmployeeDutyRecordManager employeeDutyRecordManager;
	/** 统计Dao,以完成任务常量的评分 */
	@Autowired
	private TransgressSatDao statDao;
	/** 任务Manager */
	@Autowired
	private TaskManager taskManager;

	/** 考核标注类型Manager */
	@Autowired
	private TaskTypeManager taskTypeManager;
	/** 任务常量评分记录Manager */
	@Autowired
	private EvaluateRecordTaskConstManager evaluateRecordTaskConstManager;

	@Autowired
	private EvaluateRecordDetailManager evaluateRecordDetailManager;

	/**
	 * 根据部门评分
	 * 
	 * @param dept
	 *            要评分部门
	 * @param beginDate
	 *            起始时间
	 * @param endDate
	 *            截至时间
	 * @param year
	 *            评分年份
	 * @param month
	 *            评分月份
	 * @param evaluateRecord
	 *            关联评分记录
	 * @param evalType
	 *            评分类型,区分月考核,日考核
	 */
	@Transactional
	private void evalByDept(Dept dept, Date beginDate, Date endDate, int year,
			int month, EvaluateRecord evaluateRecord, String evalType) {
		// 日常勤务评分
		Float total = evelDuty(dept, beginDate, endDate, evaluateRecord,
				evalType);
		// 任务常量评分
		evelTaskConst(dept, beginDate, endDate, evaluateRecord);

		// 累加任务常量各个分值
		for (EvaluateRecordTaskConst c : evaluateRecord.getConsts()) {
			total += c.getPoint();
		}
		// 只在月考核时保存明细
		if (EvaluateConstants.EVAL_TYPE_MONTHLY.equals(evalType)) {
			// 评分明细中任务常量项
			EvaluateRecordDetail evaluateTaskConst = new EvaluateRecordDetail();
			// 任务常量是考核标准中特殊的一项,类型为"1"
			TaskType type = taskTypeManager.query(
					"from TaskType tt where tt.type = '1'").get(0);
			evaluateTaskConst.setTotal(total);
			// 保存评分明细记录
			saveDetail(evaluateRecord, evaluateTaskConst, type);
		}

		// 设定总分
		evaluateRecord.setTotal(total);
		// 保存记录
		save(evaluateRecord);
	}

	/**
	 * <pre>
	 * 根据日常勤务评分
	 * 汇总大队加分/减分项 应该是将明细保存到数据库的,但是由于目前只是考核到大队,
	 * 对于警员的考核需求限于累加,并且任务分配等需求未定,在这里仅仅只是得到一个数字
	 * 随着需求的变更,此处会有修改任务...
	 * </pre>
	 */
	private Float evelDuty(Dept dept, Date beginDate, Date endDate,
			EvaluateRecord evaluateRecord, String evalType) {
		List<TaskType> typeList = taskTypeManager.get();
		Map<TaskType, EvaluateRecordDetail> erds = new HashMap<TaskType, EvaluateRecordDetail>();
		for (TaskType tt : typeList) {
			erds.put(tt, new EvaluateRecordDetail());
		}
		/*
		 * // 构建评分明细,以记载各个评分类别分别多少分(小计) EvaluateRecordDetail edTaskDuty = new
		 * EvaluateRecordDetail();// 日常勤务 EvaluateRecordDetail edGroupB = new
		 * EvaluateRecordDetail();// B组考核标准 EvaluateRecordDetail edAward = new
		 * EvaluateRecordDetail();// 重奖 EvaluateRecordDetail edReject = new
		 * EvaluateRecordDetail();// 一票否决 EvaluateRecordDetail edOther = new
		 * EvaluateRecordDetail();// 其他奖项
		 */
		Float total = 0f;// 总分变量
		// 查询日常勤务分数记录
		String hql = "from EmployeeDutyRecord edr where edr.recordTime >= ? and edr.recordTime <= ? and edr.employee.dept.id = ?";

		List<EmployeeDutyRecord> employeeDutyRecords = employeeDutyRecordManager
				.query(hql, new Object[] { beginDate, endDate, dept.getId() });
		// 根据加减分-分值累计
		for (EmployeeDutyRecord edr : employeeDutyRecords) {
			Float point = 0f;
			// 加减分,根据加/减分的标识,进行加分或者减分操作
			if ("0".equals(edr.getTaskDetail().getAddOrDecrease())) {
				// total -= edr.getTaskDetail().getPoint();
				point = -1 * edr.getTaskDetail().getPoint();// 减分项,负分
			} else {
				// total += edr.getTaskDetail().getPoint();
				point = edr.getTaskDetail().getPoint();// 加分项,正分
			}
			total += point;// 累加,(正分加,负分减)
			// 月考核记录明细
			if (EvaluateConstants.EVAL_TYPE_MONTHLY.equals(evalType)) {
				Set<TaskType> types = erds.keySet();
				for (TaskType type : types) {
					if (edr.getTask().getTaskType().getId()
							.equals(type.getId())) {
						erds.get(type).setTotal(
								edr.getTask().getTotal() + point);
						erds.get(type).setTaskType(type);
					}
				}
			}

			/*
			 * String typeName = edr.getTask().getTaskType().getName(); // 日常勤务
			 * if (AssessConstants.TASK_DUTY.equals(typeName)) {
			 * edTaskDuty.setTotal(edTaskDuty.getTotal() + point); // B组考核标准 }
			 * else if (AssessConstants.TASK_GROUP_B.equals(typeName)) {
			 * edGroupB.setTotal(edGroupB.getTotal() + point); // 重奖 } else if
			 * (AssessConstants.TASK_AWARD.equals(typeName)) {
			 * edAward.setTotal(edAward.getTotal() + point); // 一票否决 } else if
			 * (AssessConstants.TASK_REJECT.equals(typeName)) {
			 * edReject.setTotal(edReject.getTotal() + point); // 其他奖项 } else if
			 * (AssessConstants.TASK_OTHER.equals(typeName)) {
			 * edOther.setTotal(edOther.getTotal() + point); }
			 */
		}

		// 月考核时记录明细
		if (EvaluateConstants.EVAL_TYPE_MONTHLY.equals(evalType)) {
			/*
			 * saveDetail(evaluateRecord, edTaskDuty,
			 * AssessConstants.TASK_DUTY); saveDetail(evaluateRecord, edGroupB,
			 * AssessConstants.TASK_GROUP_B); saveDetail(evaluateRecord,
			 * edAward, AssessConstants.TASK_AWARD); saveDetail(evaluateRecord,
			 * edReject, AssessConstants.TASK_REJECT);
			 * saveDetail(evaluateRecord, edOther, AssessConstants.TASK_OTHER);
			 */
			Set<TaskType> types = erds.keySet();
			for (TaskType type : types) {
				saveDetail(evaluateRecord, erds.get(type), type);
			}
		}

		return total;

	}

	/**
	 * 设定评分明细和评分记录之间的关联,并保存评分明细记录
	 * 
	 * @param er
	 * @param erd
	 */
	@Transactional
	private void saveDetail(EvaluateRecord er, EvaluateRecordDetail erd,
			TaskType type) {
		erd.setTaskType(type);// 设定类型
		er.getDetails().add(erd);// 设定和评分记录的关联
		erd.setEvaluateRecord(er);//   	
		evaluateRecordDetailManager.save(erd);// 保存
	}

	/**
	 * 根据任务常量,为大队评分
	 */
	@Transactional
	private void evelTaskConst(Dept dept, Date beginDate, Date endDate,
			EvaluateRecord evaluateRecord) {
		// Float total = 0f;
		// 查出任务常量项
		String hql = "from Task t where t.taskConstOrDuty = ?";
		List<Task> tasks = taskManager.query(hql, AssessConstants.TASK_CONST);
		// 迭代各个任务常量(评分项)
		for (Task t : tasks) {
			// 得到任务常量对应的统计项目(针对于违法的统计)
			TransgressStatItem itm = t.getTransgressStatItem();

			// 根据统计项,构建统计条件类实例(封装各种统计条件)
			// 用以传递给TransgressSatDao的stat方法
			StatCondition condition = new StatCondition();

			condition.setEachDayStat(false);// 是否是每天的日常统计

			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			condition.setBeginHourMinute(sf.format(beginDate));// 起始时间
			condition.setEndHourMinute(sf.format(endDate));// 截至时间

			condition.setTransgressActionCodesStr(itm
					.getTransgressActionCodes());// 违法行为代码
			condition.setVehicleUseCodes(itm.getVehicleUseCodes());// 车辆使用性质
			condition.setUnionForce(itm.getUnionForce());// 是否关联强制表
			condition.setTimeCondition(itm.getFindOrDealWith());// 发现时间/处理时间
			condition.setFlapperTyps(itm.getFlapperTypes());// 号牌种类
			condition.setVioSurveil(itm.getVioSurveil());// 是否统计VIO_SURVEIL表(非现场文本记录表)
			condition.setDeptCode(dept.getDeptCode());// 部门编号

			// 执行统计
			List<Map<String, Object>> list = statDao.stat(condition);
			// 得到分值
			BigDecimal current = (BigDecimal) list.get(0).get("TOTAL_COUNT");

			// 构建任务常量评分记录实体实例,并设定各个属性
			EvaluateRecordTaskConst ertc = new EvaluateRecordTaskConst();
			ertc.setTask(t);// 关联任务
			ertc.setRecordDate(new java.util.Date());// 记录时间
			ertc.setFactCount(current.intValue());// 实际完成数量
			Integer difCount = current.intValue()// 和考核指标的差值
					- Integer.valueOf(t.getAimCount());

			// 如果超额
			if (difCount > 0) {
				// 实际得分为该项满分加上超出分数
				ertc.setPoint(t.getTotal() + t.getAddPoint() * difCount);
			} else {// 未完成数额
				// 实际得分为该项满分加减分分数(负值)分数
				ertc.setPoint(t.getDecreasePoint() * difCount);
			}
			// 为评分记录增加关联的任务常量评分
			evaluateRecord.getConsts().add(ertc);
			ertc.setEvaluateRecord(evaluateRecord);
			// 保存任务常量评分记录
			evaluateRecordTaskConstManager.save(ertc);
		}
	}

	/**
	 * 月度考核
	 * 
	 * @param dept
	 * @param year
	 * @param month
	 */
	@Transactional
	public void eval(Dept dept, int year, int month) {
		cleanSameEvaluateRecord(dept, year, month);
		// 构建考核记录
		EvaluateRecord evaluateRecord = new EvaluateRecord();
		evaluateRecord.setDept(dept);// 部门
		evaluateRecord.setRecordTime(new java.util.Date());// 时间
		evaluateRecord.setYear(year);// 考核年份
		evaluateRecord.setMonth(month);// 考核月份
		// 考核类型为月度考核,以区分每日生成的考核日报
		evaluateRecord.setRecordType(EvaluateConstants.EVAL_TYPE_MONTHLY);
		// 保存考核记录
		save(evaluateRecord);

		// 构建考核时间条件
		Calendar c = Calendar.getInstance();
		c.set(Calendar.MONTH, month - 1);
		c.set(Calendar.YEAR, year);
		c.set(Calendar.DATE, 0);
		c.set(Calendar.HOUR, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);

		Date beginDate = c.getTime();

		c.set(Calendar.MONTH, month);

		Date endDate = c.getTime();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		logger.debug(sf.format(beginDate));
		logger.debug(sf.format(endDate));

		// 计算考核
		evalByDept(dept, beginDate, endDate, year, month, evaluateRecord,
				EvaluateConstants.EVAL_TYPE_MONTHLY);

	}

	/**
	 * <pre>
	 * 考核日报
	 * 为了能在OA中有个前台页面,展示"考核日报",需要每天生成一个考核记录
	 * @param dept
	 * @param evalDate
	 * </pre>
	 */
	@Transactional
	public void evalDaily(Dept dept, Date evalDate, int year, int month,
			int day, int hour, int minute, int second) {
		cleanSameEvaluateRecord(dept, year, month);
		// 构建考核记录实体实例
		EvaluateRecord evaluateRecord = new EvaluateRecord();
		evaluateRecord.setDept(dept);// 部门
		evaluateRecord.setRecordTime(new java.util.Date());// 时间
		// 类型为考核日报
		evaluateRecord.setRecordType(EvaluateConstants.EVAL_TYPE_DAILY);
		evaluateRecord.setYear(year);
		evaluateRecord.setMonth(month);

		// 保存记录
		save(evaluateRecord);
		// 设定时间条件
		Calendar c = Calendar.getInstance();
		c.set(Calendar.MONTH, month);
		c.set(Calendar.YEAR, year);
		c.set(Calendar.DATE, 0);
		c.set(Calendar.HOUR, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);

		Date beginDate = c.getTime();

		c.set(Calendar.DATE, day);
		c.set(Calendar.HOUR, hour);
		c.set(Calendar.MINUTE, minute);
		c.set(Calendar.SECOND, second);

		Date endDate = c.getTime();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		logger.debug(sf.format(beginDate));
		logger.debug(sf.format(endDate));

		// 计算考核
		evalByDept(dept, beginDate, endDate, year, month, evaluateRecord,
				EvaluateConstants.EVAL_TYPE_DAILY);

	}

	/**
	 * 为避免重复评分,例如修改系统日期后,就会导致重复评分,需要先将"重复"记录清空
	 * 
	 * @param dept
	 * @param year
	 * @param month
	 */
	private void cleanSameEvaluateRecord(Dept dept, int year, int month) {
		String hql = "from EvaluateRecord er where er.dept.id = ? and er.year = ? and er.month = ?";
		List<EvaluateRecord> list = query(hql, new Object[] { dept.getId(),
				year, month });
		if (CollectionUtils.isNotEmpty(list)) {
			for (EvaluateRecord er : list) {
				remove(er);
			}
		}
	}
}
