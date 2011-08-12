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

/**
 * 日常勤务记录Action
 * 
 * @author DELPHI
 * 
 */
@SuppressWarnings("serial")
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class EmployeeDutyRecordAction extends
		AssessBaseAction<EmployeeDutyRecord, EmployeeDutyRecordManager> {
	/** 任务类型Manager */
	@Autowired
	private TaskTypeManager taskTypeManager;
	/** 任务Manager */
	@Autowired
	private TaskManager taskManager;
	/** 任务明细Manager */
	@Autowired
	private TaskDetailManager taskDetailManager;
	/** 部门Manager */
	@Autowired
	private DeptManager deptManager;
	/** 警员Manager */
	@Autowired
	private EmployeeManager employeeManager;
	/** 任务类型集合 */
	private List<TaskType> taskTypes;
	/** 任务集合 */
	private List<Task> tasks = new ArrayList<Task>();
	/** 部门集合 */
	private List<Dept> depts;
	// 查询起始时间
	private Date beginTime;
	// 查询截至时间
	private Date endTime;
	/** 部门代码 */
	private String deptCode;
	/**
	 * index方法
	 */
	public String index() {
		StringBuffer buf = new StringBuffer(
				"from EmployeeDutyRecord edr where 1=1 ");
		List<Object> args = new ArrayList<Object>();
		//根据警员姓名查询
		if (getModel().getEmployee() != null
				&& StringUtils.isNotBlank(getModel().getEmployee().getName())) {
			buf.append("and edr.employee.name like ? ");
			args.add(MatchMode.ANYWHERE.toMatchString(getModel().getEmployee()
					.getName()));
		}
		//根据任务查询
		if (getModel().getTask() != null
				&& StringUtils.isNotBlank(getModel().getTask().getId())) {
			buf.append("and edr.task.id = ? ");
			args.add(getModel().getTask().getId());
		}
		//根据部门查询
		if (StringUtils.isNotBlank(deptCode)) {
			Dept dept = deptManager.getDeptByCode(deptCode);
			buf.append("and edr.employee.dept.id = ? ");
			args.add(dept.getId());
		}

		// 根据记录时间区间查询
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
	 * 编辑,首先是要选类别,将类别集合传递到页面,供用户选择
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
		//得到用户选择的任务类型
		String taskTypeId = getRequest().getParameter("taskTypeId");
		//根据任务类型查询任务
		List<Task> tasks = taskManager.getTaskByType(taskTypeId);
		//构建返回给页面的json串
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
	 * 根据id得到任务的Ajax方法
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getTaskById() {
		//得到用户选择的任务id
		String taskId = getRequest().getParameter("taskId");
		Task task = taskManager.get(taskId);
		//根据任务构建返回json串
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
		//得到用户选择的任务
		String taskId = getRequest().getParameter("taskId");
		//根据任务得到任务明细集合
		String hql = "from TaskDetail td where td.task.id = ?";
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		List<TaskDetail> tds = getManager().getDao().query(hql, taskId);
		//根据任务明细集合构建返回给界面的json串
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
		//涉及警员的id集合
		String empIds = getRequest().getParameter("empIds");
		
		Assert.notNull(getModel().getTask());
		Assert.notNull(getModel().getTask().getId());
		Assert.notNull(getModel().getTaskDetail());
		Assert.notNull(getModel().getTaskDetail().getId());
		Assert.notNull(empIds);

		//得到关联任务
		Task task = taskManager.get(getModel().getTask().getId());
		//得到关联任务明细
		TaskDetail td = taskDetailManager.get(getModel().getTaskDetail()
				.getId());
		//遍历警员id,为每个警员生成日常勤务记录
		if (StringUtils.isNotBlank(empIds)) {
			String[] empIdArr = empIds.split(",");
			for (String id : empIdArr) {
				if (StringUtils.isNotBlank(id)) {
					Employee emp = employeeManager.get(id);
					//构建日常勤务记录实体实例
					EmployeeDutyRecord edr = new EmployeeDutyRecord();
					edr.setEmployee(emp);//警员
					edr.setTask(task);//日常勤务(任务)
					edr.setTaskDetail(td);//任务明细
					edr.setRecordTime(new java.util.Date());//事件

					getManager().save(edr);//保存记录
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
