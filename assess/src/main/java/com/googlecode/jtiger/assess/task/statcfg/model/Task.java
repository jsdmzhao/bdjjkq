package com.googlecode.jtiger.assess.task.statcfg.model;

import java.util.HashSet;
import java.util.Set;

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
	/** 关联大队任务 */
	private Set<BattalionTask> battalionTasks = new HashSet<BattalionTask>(0);

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

	@Column(columnDefinition = "varchar2(10)")
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

}
