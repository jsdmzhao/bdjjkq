package com.googlecode.jtiger.assess.task.stat.empduty.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.googlecode.jtiger.assess.task.statcfg.model.Task;
import com.googlecode.jtiger.core.model.BaseIdModel;
import com.googlecode.jtiger.modules.hr.employee.model.Employee;

/**
 * <pre>
 * 警员日常勤务记录
 * 由于任务常量内容都来源于公安网数据库,所以本地不用保存任务常量记录
 * 而日常勤务是否需要记录呢?估计需要.
 * @author DELPHI
 *</pre>
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "assess_employ_duty_record")
public class EmployeeDutyRecord extends BaseIdModel {
	/** 关联警员 */
	private Employee employee;
	/** 关联日常勤务项 */
	private Task task;
	/** 记录时间 */
	private Date recordTime;
	/** 考核年月 */
	private String yearAndMonth;

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumn(name = "employee_id")
	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumn(name = "task_id")
	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public Date getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}

	public String getYearAndMonth() {
		return yearAndMonth;
	}

	public void setYearAndMonth(String yearAndMonth) {
		this.yearAndMonth = yearAndMonth;
	}
}
