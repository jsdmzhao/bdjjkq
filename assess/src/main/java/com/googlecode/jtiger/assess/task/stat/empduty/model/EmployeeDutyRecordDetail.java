package com.googlecode.jtiger.assess.task.stat.empduty.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.googlecode.jtiger.assess.task.statcfg.model.TaskDetail;
import com.googlecode.jtiger.core.model.BaseIdModel;

/**
 * 警员日常勤务记录明细，以记录多个得分、扣分项
 * 
 * @author DELPHI
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "assess_emp_duty_rec_detail")
public class EmployeeDutyRecordDetail extends BaseIdModel {
	/** 关联记录 */
	private EmployeeDutyRecord employeeDutyRecord;
	/** 关联任务明细 */
	private TaskDetail taskDetail;
	/** 次数 */
	private int count;
	/** 实际扣、加分数 */
	private Float truePoint;

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumn(name = "emp_Duty_Rec_id")
	public EmployeeDutyRecord getEmployeeDutyRecord() {
		return employeeDutyRecord;
	}

	public void setEmployeeDutyRecord(EmployeeDutyRecord employeeDutyRecord) {
		this.employeeDutyRecord = employeeDutyRecord;
	}

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumn(name = "task_detail_id")
	public TaskDetail getTaskDetail() {
		return taskDetail;
	}

	public void setTaskDetail(TaskDetail taskDetail) {
		this.taskDetail = taskDetail;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Float getTruePoint() {
		return truePoint;
	}

	public void setTruePoint(Float truePoint) {
		this.truePoint = truePoint;
	}
}
