package com.googlecode.jtiger.assess.task.statcfg.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.googlecode.jtiger.core.model.BaseIdModel;
import com.googlecode.jtiger.modules.hr.dept.model.Dept;

/**
 * 大队任务
 * 
 * @author DELPHI
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "assess_battalion_task")
public class BattalionTask extends BaseIdModel {
	/** 所属大队 */
	private Dept dept;
	/** 关联支队制定的任务 */
	private Task task;
	/** 任务名称 */
	private String name;
	/** 总分(完成任务得xx分) */
	private Float total;
	/** 目标数量--指标 (不低于xx) */
	private String aimCount;
	/** 减分 (少1人,扣xx分 ) */
	private Float decreasePoint;
	/** 加分 (超额1人,加xx分) */
	private Float addPoint;
	/** 考核年月 */
	private String yearAndMonth;

	private Set<LochusTask> LochusTasks = new HashSet<LochusTask>(0);

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumn(name = "dept_id")
	public Dept getDept() {
		return dept;
	}

	public void setDept(Dept dept) {
		this.dept = dept;
	}

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumn(name = "task_id")
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

	public Float getTotal() {
		return total;
	}

	public void setTotal(Float total) {
		this.total = total;
	}

	public String getAimCount() {
		return aimCount;
	}

	public void setAimCount(String aimCount) {
		this.aimCount = aimCount;
	}

	public Float getDecreasePoint() {
		return decreasePoint;
	}

	public void setDecreasePoint(Float decreasePoint) {
		this.decreasePoint = decreasePoint;
	}

	public Float getAddPoint() {
		return addPoint;
	}

	public void setAddPoint(Float addPoint) {
		this.addPoint = addPoint;
	}

	@OneToMany(cascade = {}, fetch = FetchType.LAZY, mappedBy = "battalionTask")
	public Set<LochusTask> getLochusTasks() {
		return LochusTasks;
	}

	public void setLochusTasks(Set<LochusTask> lochusTasks) {
		LochusTasks = lochusTasks;
	}

	public String getYearAndMonth() {
		return yearAndMonth;
	}

	public void setYearAndMonth(String yearAndMonth) {
		this.yearAndMonth = yearAndMonth;
	}

}
