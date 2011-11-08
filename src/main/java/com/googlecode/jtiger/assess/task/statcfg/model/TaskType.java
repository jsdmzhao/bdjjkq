package com.googlecode.jtiger.assess.task.statcfg.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.googlecode.jtiger.core.model.BaseIdModel;

/**
 * 考核任务类型
 * 
 * @author DELPHI
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "assess_task_type")
public class TaskType extends BaseIdModel {
	/** 名称 */
	private String name;
	/** 备注 */
	private String remark;

	/** 是否是任务常量 */
	private String type;
	/** 关联任务集合 */
	private Set<Task> tasks = new HashSet<Task>(0);

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(cascade = {}, fetch = FetchType.LAZY, mappedBy = "taskType")
	public Set<Task> getTasks() {
		return tasks;
	}

	public void setTasks(Set<Task> tasks) {
		this.tasks = tasks;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(columnDefinition = "char(1) default '0'")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
