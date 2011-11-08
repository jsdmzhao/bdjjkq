package com.googlecode.jtiger.assess.evaluate.stat.webapp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;

import com.googlecode.jtiger.assess.AssessConstants;
import com.googlecode.jtiger.assess.core.webapp.AssessBaseAction;
import com.googlecode.jtiger.assess.evaluate.model.EvaluateRecord;
import com.googlecode.jtiger.assess.evaluate.model.EvaluateRecordDetail;
import com.googlecode.jtiger.assess.evaluate.model.EvaluateRecordTaskConst;
import com.googlecode.jtiger.assess.evaluate.service.EvaluateRecordDetailManager;
import com.googlecode.jtiger.assess.evaluate.service.EvaluateRecordTaskConstManager;
import com.googlecode.jtiger.assess.evaluate.stat.service.EvaluateRecordStatManager;
import com.googlecode.jtiger.assess.task.statcfg.model.Task;
import com.googlecode.jtiger.assess.task.statcfg.model.TaskType;
import com.googlecode.jtiger.assess.task.statcfg.service.TaskManager;
import com.googlecode.jtiger.assess.task.statcfg.service.TaskTypeManager;
import com.googlecode.jtiger.modules.hr.dept.model.Dept;
import com.googlecode.jtiger.modules.hr.dept.service.DeptManager;
import com.ibm.icu.util.Calendar;

/**
 * 考核统计，环比Action
 * 
 * @author DELPHI
 * 
 */
@SuppressWarnings("serial")
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class EvaluateRecordStatAction extends
		AssessBaseAction<EvaluateRecord, EvaluateRecordStatManager> {
	/** 考核部门 */
	private Dept dept;
	/** 部门编号 */
	private String deptCode;
	/** 部门Manager */
	@Autowired
	private DeptManager deptManager;
	/** 任务类型Manager */
	@Autowired
	private TaskTypeManager taskTypeManager;
	/** 任务Manager */
	@Autowired
	private TaskManager taskManager;
	/** 评分明细Manager */
	@Autowired
	private EvaluateRecordDetailManager evaluateRecordDetailManager;
	/** 评分常量项Manager */
	@Autowired
	private EvaluateRecordTaskConstManager evaluateRecordTaskConstManager;

	/** 用户选择的考核标准类型 */
	private TaskType taskType;
	/** 用户选择的任务 */
	private Task task;

	/**
	 * 考核得分环比
	 * 
	 * @return
	 */
	public String linkRelatie() {
		Assert.notNull(deptCode);
		// 要环比的部门
		dept = deptManager.getDeptByCode(deptCode);
		StringBuffer buf = new StringBuffer();
		// 得到当前年份
		Calendar c = Calendar.getInstance();

		int year = c.get(Calendar.YEAR);
		// 取得指定部门当前年份各月考核数据
		List<EvaluateRecord> list = getEvaluateRecords(year, dept);
		for (EvaluateRecord er : list) {
			buf.append(er.getMonth() + "月;").append(er.getTotal() + "\\n");
		}

		getRequest().setAttribute("csvData", buf.toString());
		getRequest().setAttribute("year", year);
		getRequest().setAttribute("title",
				year + "年" + dept.getName() + "考核得分环比");

		return "linkRelatie";
	}

	/**
	 * 根据考核标准类型环比
	 * 
	 * @return
	 */
	public String linkRelatieByTaskType() {
		Assert.notNull(deptCode);
		// 得到用户选择的部门
		dept = deptManager.getDeptByCode(deptCode);
		StringBuffer buf = new StringBuffer();
		// 得到当前年份
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		// 得到用户选择的考核标准类型
		taskType = taskTypeManager.get(taskType.getId());
		// 取得指定部门指定考核标准类型的考核数据
		List<EvaluateRecordDetail> list = getEvaluateRecordDetails(year, dept,
				taskType);
		for (EvaluateRecordDetail er : list) {
			buf.append(er.getEvaluateRecord().getMonth() + "月;").append(
					er.getTotal() + "\\n");
		}

		getRequest().setAttribute("csvData", buf.toString());
		getRequest().setAttribute("year", year);
		getRequest().setAttribute("title",
				year + "年" + dept.getName() + taskType.getName() + "项环比");

		return "linkRelatieByTaskType";
	}

	/**
	 * 根据任务常量环比
	 * 
	 * @return
	 */
	public String linkRelatieByTaskConst() {
		Assert.notNull(deptCode);
		// 得到用户选择的部门
		dept = deptManager.getDeptByCode(deptCode);
		StringBuffer buf = new StringBuffer();
		// 得到当前年份
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		// 得到用户选择的任务常量
		task = taskManager.get(task.getId());
		// 取得指定部门某项指定任务常量的完成数量
		List<EvaluateRecordTaskConst> list = getEvaluateRecordTaskConst(year,
				dept, task);
		for (EvaluateRecordTaskConst ert : list) {
			buf.append(ert.getEvaluateRecord().getMonth() + "月;").append(
					ert.getFactCount() + "\\n");
		}

		getRequest().setAttribute("csvData", buf.toString());
		getRequest().setAttribute("year", year);
		getRequest().setAttribute("title",
				year + "年" + dept.getName() + task.getName() + "完成数量环比");

		return "linkRelatieByTaskConst";
	}

	public String index() {
		return "index";
	}

	public String indexByTaskType() {
		return "indexByTaskType";
	}

	public String indexByTaskConst() {
		return "indexByTaskConst";
	}

	/**
	 * 得到考核任务常量项数据
	 * 
	 * @param year
	 * @param dept
	 * @param task
	 * @return
	 */
	private List<EvaluateRecordTaskConst> getEvaluateRecordTaskConst(int year,
			Dept dept, Task task) {
		String hql = "from EvaluateRecordTaskConst ert where ert.evaluateRecord.year = ? "
				+ "and ert.evaluateRecord.dept.id = ? and ert.task.id = ? order by ert.evaluateRecord.month";

		return evaluateRecordTaskConstManager.query(hql, year, dept.getId(),
				task.getId());
	}

	/**
	 * 得到考核明细数据
	 * 
	 * @param year
	 * @param dept
	 * @param taskType
	 * @return
	 */
	private List<EvaluateRecordDetail> getEvaluateRecordDetails(int year,
			Dept dept, TaskType taskType) {
		String hql = "from EvaluateRecordDetail erd where erd.evaluateRecord.year = ? "
				+ "and erd.evaluateRecord.dept.id = ? and erd.taskType.id = ? order by erd.evaluateRecord.month";

		return evaluateRecordDetailManager.query(hql, year, dept.getId(),
				taskType.getId());

	}

	/**
	 * 得到某部门，某年所有数据
	 * 
	 * @param year
	 * @param dept
	 * @return
	 */
	private List<EvaluateRecord> getEvaluateRecords(int year, Dept dept) {
		String hql = "from EvaluateRecord er where er.year = ? and er.dept.id = ? order by er.month";

		return getManager().query(hql, new Object[] { year, dept.getId() });
	}

	public Dept getDept() {
		return dept;
	}

	public void setDept(Dept dept) {
		this.dept = dept;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public TaskType getTaskType() {
		return taskType;
	}

	public void setTaskType(TaskType taskType) {
		this.taskType = taskType;
	}

	public List<TaskType> getTaskTypes() {
		String hql = "from TaskType";

		return taskTypeManager.query(hql);
	}

	/**
	 * 查询得到考核标准中的任务常量
	 * 
	 * @return
	 */
	public List<Task> getTaskCosnts() {
		String hql = "from Task t where t.taskConstOrDuty = ?";
		return taskManager.query(hql, AssessConstants.TASK_CONST);
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

}
