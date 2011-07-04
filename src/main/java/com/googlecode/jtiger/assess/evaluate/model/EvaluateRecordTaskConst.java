package com.googlecode.jtiger.assess.evaluate.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.googlecode.jtiger.assess.task.statcfg.model.Task;
import com.googlecode.jtiger.core.model.BaseIdModel;

/**
 * 任务常量评分记录
 * 
 * @author DELPHI
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "assess_taskconst_eval_record")
public class EvaluateRecordTaskConst extends BaseIdModel {
	/** 关联任务常量 */
	private Task task;
	/** 实际完成数量 */
	private Integer factCount;
	/** 得分 */
	private Float point;
	/** 录入时间 */
	private Date recordDate;
	/***/
	private EvaluateRecord evaluateRecord;

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumn(name = "task_id")
	public Task getTask() {
		return task;
	}

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumn(name = "task_id")
	public void setTask(Task task) {
		this.task = task;
	}

	public Integer getFactCount() {
		return factCount;
	}

	public void setFactCount(Integer factCount) {
		this.factCount = factCount;
	}

	public Float getPoint() {
		return point;
	}

	public void setPoint(Float point) {
		this.point = point;
	}

	public Date getRecordDate() {
		return recordDate;
	}

	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumn(name = "evaluate_record_id")
	public EvaluateRecord getEvaluateRecord() {
		return evaluateRecord;
	}

	public void setEvaluateRecord(EvaluateRecord evaluateRecord) {
		this.evaluateRecord = evaluateRecord;
	}

}
