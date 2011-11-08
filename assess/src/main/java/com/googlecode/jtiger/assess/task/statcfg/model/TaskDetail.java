package com.googlecode.jtiger.assess.task.statcfg.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.googlecode.jtiger.core.model.BaseIdModel;

/**
 * 任务(考核标准)明细
 * 
 * @author DELPHI
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "assess_task_detail")
public class TaskDetail extends BaseIdModel {
	/** 关联任务 */
	private Task task;
	/** 任务明细名称 */
	private String name;
	/** 加分/减分 */
	private String addOrDecrease;
	/** 分值 */
	private Float point;
	/** 是否包岗领导同扣 */
	private String decreaseLeader;

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumn(name = "stat_id")
	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Float getPoint() {
		return point;
	}

	public void setPoint(Float point) {
		this.point = point;
	}

	@Column(columnDefinition = "char(1)")
	public String getAddOrDecrease() {
		return addOrDecrease;
	}

	public void setAddOrDecrease(String addOrDecrease) {
		this.addOrDecrease = addOrDecrease;
	}

	@Column(columnDefinition = "char(1) default ''")
	public String getDecreaseLeader() {
		return decreaseLeader;
	}

	public void setDecreaseLeader(String decreaseLeader) {
		this.decreaseLeader = decreaseLeader;
	}
}
