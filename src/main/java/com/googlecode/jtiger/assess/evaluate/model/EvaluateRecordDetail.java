package com.googlecode.jtiger.assess.evaluate.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.googlecode.jtiger.assess.task.statcfg.model.Task;
import com.googlecode.jtiger.assess.task.statcfg.model.TaskDetail;
import com.googlecode.jtiger.core.model.BaseIdModel;
import com.googlecode.jtiger.modules.hr.employee.model.Employee;

/**
 * 评分记录明细
 * 
 * @author DELPHI
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "assess_evaluate_rec_det")
public class EvaluateRecordDetail extends BaseIdModel {
	/** 关联评分记录 */
	private EvaluateRecord evaluateRecord;
	/** 被评分警员 */
	private Employee employee;
	/** 评分依据任务明细 */
	private TaskDetail taskDetail;
		

	/** 关联任务 */
	private Task task;

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumn(name = "evaluate_Record_id")
	public EvaluateRecord getEvaluateRecord() {
		return evaluateRecord;
	}

	public void setEvaluateRecord(EvaluateRecord evaluateRecord) {
		this.evaluateRecord = evaluateRecord;
	}

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumn(name = "employee_id")
	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumn(name = "task_detail_id")
	public TaskDetail getTaskDetail() {
		return taskDetail;
	}

	public void setTaskDetail(TaskDetail taskDetail) {
		this.taskDetail = taskDetail;
	}

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumn(name = "task_id")
	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}
}
