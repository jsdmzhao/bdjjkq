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
	/** 特殊项关联的车辆使用性质 */
	// private String specialVehicleUseCodes;
	/** 特殊项分值 */
	private Float specialPoint;
	/** 组别,A组/B组 */
	private String teamType;
	/** 任务常量/日常 */
	private String taskConstOrDuty;
	/** 常规项/重奖项/一票否决项/其他奖项 */
	private String itemtype;
	private TaskType taskType;

	/** 关联大队任务 */
	private Set<BattalionTask> battalionTasks = new HashSet<BattalionTask>(0);
	private Set<TaskDetail> taskDutyDetails = new HashSet<TaskDetail>(0);

	private String dutyItemName1;
	private String addOrDecrease1;
	private Float dutyItemPoint1;
	private String decreaseLeader1;

	private String dutyItemName2;
	private String addOrDecrease2;
	private Float dutyItemPoint2;
	private String decreaseLeader2;

	private String dutyItemName3;
	private String addOrDecrease3;
	private Float dutyItemPoint3;
	private String decreaseLeader3;

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

	@Column(columnDefinition = "char(1) default 'A'")
	public String getTeamType() {
		return teamType;
	}

	public void setTeamType(String teamType) {
		this.teamType = teamType;
	}

	@Column(columnDefinition = "char(1) default '0'")
	public String getItemtype() {
		return itemtype;
	}

	public void setItemtype(String itemtype) {
		this.itemtype = itemtype;
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

	public String getDutyItemName1() {
		return dutyItemName1;
	}

	public void setDutyItemName1(String dutyItemName1) {
		this.dutyItemName1 = dutyItemName1;
	}

	@Column(columnDefinition = "char(1) default ''")
	public String getAddOrDecrease1() {
		return addOrDecrease1;
	}

	public void setAddOrDecrease1(String addOrDecrease1) {
		this.addOrDecrease1 = addOrDecrease1;
	}

	public Float getDutyItemPoint1() {
		return dutyItemPoint1;
	}

	public void setDutyItemPoint1(Float dutyItemPoint1) {
		this.dutyItemPoint1 = dutyItemPoint1;
	}

	public String getDutyItemName2() {
		return dutyItemName2;
	}

	public void setDutyItemName2(String dutyItemName2) {
		this.dutyItemName2 = dutyItemName2;
	}

	@Column(columnDefinition = "char(1) default ''")
	public String getAddOrDecrease2() {
		return addOrDecrease2;
	}

	public void setAddOrDecrease2(String addOrDecrease2) {
		this.addOrDecrease2 = addOrDecrease2;
	}

	public Float getDutyItemPoint2() {
		return dutyItemPoint2;
	}

	public void setDutyItemPoint2(Float dutyItemPoint2) {
		this.dutyItemPoint2 = dutyItemPoint2;
	}

	public String getDutyItemName3() {
		return dutyItemName3;
	}

	public void setDutyItemName3(String dutyItemName3) {
		this.dutyItemName3 = dutyItemName3;
	}

	@Column(columnDefinition = "char(1) default ''")
	public String getAddOrDecrease3() {
		return addOrDecrease3;
	}

	public void setAddOrDecrease3(String addOrDecrease3) {
		this.addOrDecrease3 = addOrDecrease3;
	}

	public Float getDutyItemPoint3() {
		return dutyItemPoint3;
	}

	public void setDutyItemPoint3(Float dutyItemPoint3) {
		this.dutyItemPoint3 = dutyItemPoint3;
	}

	@Column(columnDefinition = "char(1) default '0'")
	public String getCastLeader() {
		return castLeader;
	}

	public void setCastLeader(String castLeader) {
		this.castLeader = castLeader;
	}

	@Column(columnDefinition = "char(1) default ''")
	public String getDecreaseLeader1() {
		return decreaseLeader1;
	}

	public void setDecreaseLeader1(String decreaseLeader1) {
		this.decreaseLeader1 = decreaseLeader1;
	}

	@Column(columnDefinition = "char(1) default ''")
	public String getDecreaseLeader2() {
		return decreaseLeader2;
	}

	public void setDecreaseLeader2(String decreaseLeader2) {
		this.decreaseLeader2 = decreaseLeader2;
	}

	@Column(columnDefinition = "char(1) default ''")
	public String getDecreaseLeader3() {
		return decreaseLeader3;
	}

	public void setDecreaseLeader3(String decreaseLeader3) {
		this.decreaseLeader3 = decreaseLeader3;
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
