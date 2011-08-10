package com.googlecode.jtiger.assess.evaluate.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.jtiger.assess.AssessConstants;
import com.googlecode.jtiger.assess.evaluate.EvaluateConstants;
import com.googlecode.jtiger.assess.evaluate.model.EvaluateRecord;
import com.googlecode.jtiger.assess.evaluate.model.EvaluateRecordTaskConst;
import com.googlecode.jtiger.assess.task.stat.empduty.model.EmployeeDutyRecord;
import com.googlecode.jtiger.assess.task.stat.empduty.service.EmployeeDutyRecordManager;
import com.googlecode.jtiger.assess.task.statcfg.model.Task;
import com.googlecode.jtiger.assess.task.statcfg.service.TaskManager;
import com.googlecode.jtiger.assess.transgress.stat.StatCondition;
import com.googlecode.jtiger.assess.transgress.stat.dao.TransgressSatDao;
import com.googlecode.jtiger.assess.transgress.statcfg.model.TransgressStatItem;
import com.googlecode.jtiger.core.service.BaseGenericsManager;
import com.googlecode.jtiger.modules.hr.dept.model.Dept;
import com.ibm.icu.util.Calendar;

@Service
public class EvaluateRecordManager extends BaseGenericsManager<EvaluateRecord> {
	@Autowired
	private EmployeeDutyRecordManager employeeDutyRecordManager;
	@Autowired
	private TransgressSatDao statDao;
	@Autowired
	private TaskManager taskManager;
	@Autowired
	private EvaluateRecordTaskConstManager evaluateRecordTaskConstManager;

	@Transactional
	public void evalByDept(Dept dept, Date beginDate, Date endDate, int year,
			int month, EvaluateRecord evaluateRecord) {

		Float total = evelDuty(dept, beginDate, endDate, evaluateRecord);
		evelTaskConst(dept, beginDate, endDate, evaluateRecord);
		for (EvaluateRecordTaskConst c : evaluateRecord.getConsts()) {
			total += c.getPoint();
		}

		evaluateRecord.setTotal(total);

		save(evaluateRecord);
	}

	/**
	 * .根据日常勤务,汇总大队加分/减分项 应该是将明细保存到数据库的,但是由于目前只是考核到大队,
	 * 对于警员的考核需求限于累加,并且任务分配等需求未定,在这里仅仅只是得到一个数字
	 */
	public Float evelDuty(Dept dept, Date beginDate, Date endDate,
			EvaluateRecord evaluateRecord) {

		Float total = 0f;
		String hql = "from EmployeeDutyRecord edr where edr.recordTime >= ? and edr.recordTime <= ? and edr.employee.dept.id = ?";
		List<EmployeeDutyRecord> employeeDutyRecords = employeeDutyRecordManager
				.query(hql, new Object[] { beginDate, endDate, dept.getId() });
		for (EmployeeDutyRecord edr : employeeDutyRecords) {
			// 加减分
			if ("0".equals(edr.getTaskDetail().getAddOrDecrease())) {
				total -= edr.getTaskDetail().getPoint();
			} else {
				total += edr.getTaskDetail().getPoint();
			}
		}

		return total;

	}

	/**
	 * .根据任务常量,为大队评分
	 */
	@Transactional
	public void evelTaskConst(Dept dept, Date beginDate, Date endDate,
			EvaluateRecord evaluateRecord) {
		// Float total = 0f;
		// 查出任务常量项
		String hql = "from Task t where t.taskConstOrDuty = ?";
		List<Task> tasks = taskManager.query(hql, AssessConstants.TASK_CONST);
		for (Task t : tasks) {
			TransgressStatItem itm = t.getTransgressStatItem();

			StatCondition condition = new StatCondition();
			condition.setEachDayStat(false);

			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			condition.setBeginHourMinute(sf.format(beginDate));
			condition.setEndHourMinute(sf.format(endDate));

			condition.setTransgressActionCodesStr(itm
					.getTransgressActionCodes());
			condition.setVehicleUseCodes(itm.getVehicleUseCodes());
			condition.setUnionForce(itm.getUnionForce());
			condition.setTimeCondition(itm.getFindOrDealWith());
			condition.setDeptCode(dept.getDeptCode());

			List<Map<String, Object>> list = statDao.stat(condition);
			BigDecimal current = (BigDecimal) list.get(0).get("TOTAL_COUNT");

			EvaluateRecordTaskConst ertc = new EvaluateRecordTaskConst();
			ertc.setTask(t);
			ertc.setRecordDate(new java.util.Date());
			ertc.setFactCount(current.intValue());
			Integer difCount = current.intValue()
					- Integer.valueOf(t.getAimCount());

			// 如果超额
			if (difCount > 0) {
				// 实际得分为该项满分加上超出分数
				ertc.setPoint(t.getTotal() + t.getAddPoint() * difCount);
			} else {// 未完成数额
				// 实际得分为该项满分加减分分数(负值)分数
				ertc.setPoint(t.getDecreasePoint() * difCount);
			}
			evaluateRecord.getConsts().add(ertc);
			ertc.setEvaluateRecord(evaluateRecord);
			evaluateRecordTaskConstManager.save(ertc);
		}
	}

	@Transactional
	public void eval(Dept dept, int year, int month) {

		EvaluateRecord evaluateRecord = new EvaluateRecord();
		evaluateRecord.setDept(dept);
		evaluateRecord.setRecordTime(new java.util.Date());
		evaluateRecord.setYear(year);
		evaluateRecord.setMonth(month);
		evaluateRecord.setRecordType(EvaluateConstants.EVAL_TYPE_MONTHLY);

		save(evaluateRecord);

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

		evalByDept(dept, beginDate, endDate, year, month, evaluateRecord);

	}

	/**
	 * 考核日报
	 * 
	 * @param dept
	 * @param evalDate
	 */
	@Transactional
	public void evalDaily(Dept dept, Date evalDate, int year, int month,
			int day, int hour, int minute, int second) {
		EvaluateRecord evaluateRecord = new EvaluateRecord();
		evaluateRecord.setDept(dept);
		evaluateRecord.setRecordTime(new java.util.Date());

		evaluateRecord.setRecordType(EvaluateConstants.EVAL_TYPE_DAILY);

		save(evaluateRecord);
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

		evalByDept(dept, beginDate, endDate, year, month, evaluateRecord);

	}
}
