package com.googlecode.jtiger.assess.task.statcfg.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
/**
 * 任务统计项目实体
 * @author DELPHI
 *
 */
import javax.persistence.Table;

import com.googlecode.jtiger.assess.transgress.statcfg.model.TransgressStatItem;
import com.googlecode.jtiger.core.model.BaseIdModel;

/**
 * 任务(考核标准)
 * 任务常量,日常勤务
 * 
 * @author DELPHI
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "assess_tasks")
public class Task extends BaseIdModel {
	/** 名称 */
	private String name;

	/** 关联违法行为统计项 */
	private TransgressStatItem transgressStatItem;

	/** 总分(完成任务得xx分) */
	private Float total;
	/** 目标数量--指标 (不低于xx) */
	private String aimCount;
	/** 减分 (少1人,扣xx分 ) */
	private Float decreasePoint;
	/** 是否有特殊(额外加分项) */
	private Boolean hasSpecial;
	/** 加分 (超额1人,加xx分) */
	private Float addPoint;
	/** 特殊项,(例如:醉酒拘留1人,加10分) */
	private String specialItem;
	/** 特殊项关联违法行为统计项 */
	private TransgressStatItem specialTransgressStatItem;
	/** 特殊项分值 */
	private Float specialPoint;

	/** 任务常量/日常 */
	private String taskConstOrDuty;
	/** 任务类别 */
	private TaskType taskType;

	/** 关联大队任务 */
	private Set<BattalionTask> battalionTasks = new HashSet<BattalionTask>(0);
	/** 关联任务明细 */
	private Set<TaskDetail> taskDutyDetails = new HashSet<TaskDetail>(0);

	/** 包岗领导同扣 */
	private String castLeader;

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

	public String getSpecialItem() {
		return specialItem;
	}

	public void setSpecialItem(String specialItem) {
		this.specialItem = specialItem;
	}

	public Float getSpecialPoint() {
		return specialPoint;
	}

	public void setSpecialPoint(Float specialPoint) {
		this.specialPoint = specialPoint;
	}

	@OneToMany(cascade = {}, fetch = FetchType.LAZY, mappedBy = "task")
	public Set<BattalionTask> getBattalionTasks() {
		return battalionTasks;
	}

	public void setBattalionTasks(Set<BattalionTask> battalionTasks) {
		this.battalionTasks = battalionTasks;
	}

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumn(name = "stat_item_id")
	public TransgressStatItem getTransgressStatItem() {
		return transgressStatItem;
	}

	public void setTransgressStatItem(TransgressStatItem transgressStatItem) {
		this.transgressStatItem = transgressStatItem;
	}

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumn(name = "special_stat_item_id")
	public TransgressStatItem getSpecialTransgressStatItem() {
		return specialTransgressStatItem;
	}

	public void setSpecialTransgressStatItem(
			TransgressStatItem specialTransgressStatItem) {
		this.specialTransgressStatItem = specialTransgressStatItem;
	}

	@Column(columnDefinition = "varchar2(20)")
	public String getTaskConstOrDuty() {
		return taskConstOrDuty;
	}

	public void setTaskConstOrDuty(String taskConstOrDuty) {
		this.taskConstOrDuty = taskConstOrDuty;
	}

	@Column(columnDefinition = "SMALLINT default 0")
	public Boolean getHasSpecial() {
		return hasSpecial;
	}

	public void setHasSpecial(Boolean hasSpecial) {
		this.hasSpecial = hasSpecial;
	}

	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "task")
	public Set<TaskDetail> getTaskDutyDetails() {
		return taskDutyDetails;
	}

	public void setTaskDutyDetails(Set<TaskDetail> taskDutyDetails) {
		this.taskDutyDetails = taskDutyDetails;
	}

	@Column(columnDefinition = "char(1) default '0'")
	public String getCastLeader() {
		return castLeader;
	}

	public void setCastLeader(String castLeader) {
		this.castLeader = castLeader;
	}

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumn(name = "task_type_id")
	public TaskType getTaskType() {
		return taskType;
	}

	public void setTaskType(TaskType taskType) {
		this.taskType = taskType;
	}

}
