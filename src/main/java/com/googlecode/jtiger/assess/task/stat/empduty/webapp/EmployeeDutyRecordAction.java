package com.googlecode.jtiger.assess.task.stat.empduty.webapp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.xwork.StringUtils;
import org.hibernate.criterion.MatchMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;

import com.googlecode.jtiger.assess.core.webapp.AssessBaseAction;
import com.googlecode.jtiger.assess.task.stat.empduty.model.EmployeeDutyRecord;
import com.googlecode.jtiger.assess.task.stat.empduty.service.EmployeeDutyRecordManager;
import com.googlecode.jtiger.assess.task.statcfg.model.Task;
import com.googlecode.jtiger.assess.task.statcfg.model.TaskDetail;
import com.googlecode.jtiger.assess.task.statcfg.model.TaskType;
import com.googlecode.jtiger.assess.task.statcfg.service.TaskDetailManager;
import com.googlecode.jtiger.assess.task.statcfg.service.TaskManager;
import com.googlecode.jtiger.assess.task.statcfg.service.TaskTypeManager;
import com.googlecode.jtiger.core.util.ReflectUtil;
import com.googlecode.jtiger.modules.hr.HrConstants;
import com.googlecode.jtiger.modules.hr.dept.model.Dept;
import com.googlecode.jtiger.modules.hr.dept.service.DeptManager;
import com.googlecode.jtiger.modules.hr.employee.model.Employee;
import com.googlecode.jtiger.modules.hr.employee.service.EmployeeManager;

@SuppressWarnings("serial")
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class EmployeeDutyRecordAction extends
		AssessBaseAction<EmployeeDutyRecord, EmployeeDutyRecordManager> {
	@Autowired
	private TaskTypeManager taskTypeManager;
	@Autowired
	private TaskManager taskManager;
	@Autowired
	private TaskDetailManager taskDetailManager;
	@Autowired
	private DeptManager deptManager;
	@Autowired
	private EmployeeManager employeeManager;
	private List<TaskType> taskTypes;
	private List<Task> tasks = new ArrayList<Task>();
	private List<Dept> depts;
	// 查询起始时间
	private Date beginTime;
	// 查询截至时间
	private Date endTime;
	
	private String deptCode;

	public String index() {
		StringBuffer buf = new StringBuffer(
				"from EmployeeDutyRecord edr where 1=1 ");
		List<Object> args = new ArrayList<Object>();
		if (getModel().getEmployee() != null
				&& StringUtils.isNotBlank(getModel().getEmployee().getName())) {
			buf.append("and edr.employee.name like ? ");
			args.add(MatchMode.ANYWHERE.toMatchString(getModel().getEmployee()
					.getName()));
		}

		if (getModel().getTask() != null
				&& StringUtils.isNotBlank(getModel().getTask().getId())) {
			buf.append("and edr.task.id = ? ");
			args.add(getModel().getTask().getId());
		}
		if(StringUtils.isNotBlank(deptCode)){
			Dept dept = deptManager.getDeptByCode(deptCode);
			buf.append("and edr.employee.dept.id = ? ");
			args.add(dept.getId());
		}
		

		// 记录时间区间
		if (beginTime != null) {
			buf.append("and edr.recordTime >= ? ");
			args.add(beginTime);
		}
		if (endTime != null) {
			buf.append("and edr.recordTime <= ? ");
			args.add(endTime);
		}

		buf.append("order by edr.yearAndMonth,id desc ");
		page = getManager().pageQuery(pageOfBlock(), buf.toString(),
				args.toArray());
		

		restorePageData(page);

		return INDEX;
	}

	/**
	 * 编辑,首先是要选类别
	 */
	public String edit() {
		depts = deptManager.query("from Dept d where d.deptSort = ?",
				HrConstants.DEPT_TYPE_1);
		taskTypes = taskTypeManager.get();

		return INPUT;
	}

	/**
	 * 根据类型列出任务的Ajax方法
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getTasksByType() {
		String taskTypeId = getRequest().getParameter("taskTypeId");
		List<Task> tasks = taskManager.getTaskByType(taskTypeId);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (Task t : tasks) {
			Map<String, Object> map = ReflectUtil.toMap(t, new String[] { "id",
					"name" }, true);
			list.add(map);
		}
		if (CollectionUtils.isNotEmpty(list)) {
			String json = toJson(list);
			logger.debug(json);
			renderJson(json);
		}
		return null;
	}

	/**
	 * 根据id得到任务和任务明细的Ajax方法
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getTaskById() {
		String taskId = getRequest().getParameter("taskId");
		Task task = taskManager.get(taskId);

		// taskJson.setTaskDutyDetails(task.getTaskDutyDetails());
		Map<String, Object> map = ReflectUtil.toMap(task, new String[] { "id",
				"name", "total", "aimCount" }, true);

		if (task != null) {
			String json = toJson(map);
			logger.debug(json);
			renderJson(json);
		}

		return null;
	}

	/**
	 * 得到任务明细
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getTaskDetails() {
		String taskId = getRequest().getParameter("taskId");
		String hql = "from TaskDetail td where td.task.id = ?";
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		List<TaskDetail> tds = getManager().getDao().query(hql, taskId);
		for (TaskDetail td : tds) {
			Map<String, Object> map = ReflectUtil.toMap(td, new String[] {
					"id", "name", "addOrDecrease", "point", "decreaseLeader" },
					true);
			list.add(map);
		}
		if (CollectionUtils.isNotEmpty(list)) {
			String json = toJson(list);
			logger.debug(json);
			renderJson(json);
		}

		return null;
	}

	/**
	 * 保存日常勤务记录
	 */
	public String save() {
		// String taskId = getRequest().getParameter("taskId");
		// String pointId = getRequest().getParameter("pointId");
		String empIds = getRequest().getParameter("empIds");

		Assert.notNull(getModel().getTask());
		Assert.notNull(getModel().getTask().getId());
		Assert.notNull(getModel().getTaskDetail());
		Assert.notNull(getModel().getTaskDetail().getId());
		Assert.notNull(empIds);

		Task task = taskManager.get(getModel().getTask().getId());
		TaskDetail td = taskDetailManager.get(getModel().getTaskDetail()
				.getId());

		if (StringUtils.isNotBlank(empIds)) {
			String[] empIdArr = empIds.split(",");
			for (String id : empIdArr) {
				if (StringUtils.isNotBlank(id)) {
					Employee emp = employeeManager.get(id);

					EmployeeDutyRecord edr = new EmployeeDutyRecord();
					edr.setEmployee(emp);
					edr.setTask(task);
					edr.setTaskDetail(td);
					edr.setRecordTime(new java.util.Date());

					getManager().save(edr);
				}
			}
		}

		return SUCCESS;
	}

	public List<TaskType> getTaskTypes() {
		return taskTypes;
	}

	public void setTaskTypes(List<TaskType> taskTypes) {
		this.taskTypes = taskTypes;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	public List<Dept> getDepts() {
		return depts;
	}

	public void setDepts(List<Dept> depts) {
		this.depts = depts;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

}
