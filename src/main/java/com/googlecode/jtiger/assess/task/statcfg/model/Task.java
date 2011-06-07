package com.googlecode.jtiger.assess.task.statcfg.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
/**
 * 任务统计项目实体
 * @author DELPHI
 *
 */
import javax.persistence.Table;

import com.googlecode.jtiger.core.model.BaseIdModel;

@SuppressWarnings("serial")
@Entity
@Table(name = "assess_task")
public class Task extends BaseIdModel {
	/** 名称 */
	private String name;
	/** 关联违法行为代码字符串 */
	private String transgressActionCodes;
	/** 关联车辆使用性质代码字符串 */
	private String vehicleUseCodes;
	/** 总分(完成任务得xx分) */
	private Float total;
	/** 目标数量--指标 (不低于xx) */
	private String aimCount;
	/** 减分 (少1人,扣xx分 ) */
	private Float decreasePoint;
	/** 加分 (超额1人,加xx分) */
	private Float addPoint;
	/** 特殊项,(例如:醉酒拘留1人,加10分) */
	private String specialItem;
	/** 特殊项关联的违法行为 */
	private String specialItemTransgressActionCodes;
	/** 特殊项关联的车辆使用性质 */
	private String specialVehicleUseCodes;
	/** 特殊项分值 */
	private Float specialPoint;
	/** 组别,A组/B组 */
	private String teamType;
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

	public String getTransgressActionCodes() {
		return transgressActionCodes;
	}

	public void setTransgressActionCodes(String transgressActionCodes) {
		this.transgressActionCodes = transgressActionCodes;
	}

	public String getVehicleUseCodes() {
		return vehicleUseCodes;
	}

	public void setVehicleUseCodes(String vehicleUseCodes) {
		this.vehicleUseCodes = vehicleUseCodes;
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

	public String getSpecialItemTransgressActionCodes() {
		return specialItemTransgressActionCodes;
	}

	public void setSpecialItemTransgressActionCodes(
			String specialItemTransgressActionCodes) {
		this.specialItemTransgressActionCodes = specialItemTransgressActionCodes;
	}

	public String getSpecialVehicleUseCodes() {
		return specialVehicleUseCodes;
	}

	public void setSpecialVehicleUseCodes(String specialVehicleUseCodes) {
		this.specialVehicleUseCodes = specialVehicleUseCodes;
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

}
