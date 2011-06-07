package com.googlecode.jtiger.assess.task.statcfg.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.googlecode.jtiger.core.model.BaseIdModel;
import com.googlecode.jtiger.modules.hr.employee.model.Employee;

/**
 * 警员任务
 * 
 * @author DELPHI
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "assess_employee_task")
public class EmployeeTask extends BaseIdModel {
	/** 关联警员 */
	private Employee employee;
	/** 关联中队任务 */
	private LochusTask lochusTask;
	/** 得分 */
	private Float score;
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
	@JoinColumn(name = "lochusTask_id")
	public LochusTask getLochusTask() {
		return lochusTask;
	}

	public void setLochusTask(LochusTask lochusTask) {
		this.lochusTask = lochusTask;
	}

	public Float getScore() {
		return score;
	}

	public void setScore(Float score) {
		this.score = score;
	}

	public String getYearAndMonth() {
		return yearAndMonth;
	}

	public void setYearAndMonth(String yearAndMonth) {
		this.yearAndMonth = yearAndMonth;
	}
}
