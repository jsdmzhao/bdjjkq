package com.googlecode.jtiger.assess.task.statcfg.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.googlecode.jtiger.core.model.BaseIdModel;

@SuppressWarnings("serial")
@Entity
@Table(name = "assess_task_detail")
public class TaskDutyDetail extends BaseIdModel {
	private Task task;
	private String name;
	private Boolean addOrDecrease;
	private Float point;

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
	@Column(columnDefinition = "SMALLINT default 0")
	public Boolean getAddOrDecrease() {
		return addOrDecrease;
	}

	public void setAddOrDecrease(Boolean addOrDecrease) {
		this.addOrDecrease = addOrDecrease;
	}
}
