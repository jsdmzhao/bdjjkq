package com.googlecode.jtiger.assess.transgress.statcfg.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.googlecode.jtiger.assess.task.statcfg.model.Task;
import com.googlecode.jtiger.core.model.BaseIdModel;
import com.googlecode.jtiger.modules.hr.dept.model.Dept;

/**
 * 违法统计项目,项目中关联违法行为
 * 
 * @author DELPHI
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "transgress_stat_item")
public class TransgressStatItem extends BaseIdModel {
	/** 名称 */
	private String name;
	/**
	 * 关联违法行为集合 private Set<TransgressAction> transgressActions = new
	 * HashSet<TransgressAction>( 0);
	 */
	/** 关联违法行为代码字符串 */
	private String transgressActionCodes;
	/** 关联车辆使用性质代码字符串 */
	private String vehicleUseCodes;
	/**号牌种类字符串*/
	private String flapperTypes;
	/** 保存二级类别的id,为的是在修改统计项时,能够显示用户已经关联到的违法行为,并且以这些违法行为以二级类别"分组" */
	private String secondLevelTypeIds;
	/** 是否关联到vio_force表,还是单独查询vio_force表 */
	private String unionForce = "false";
	/** 是否关联到vioSurveil表,还是单独查询vioSurveil表 */
	private String vioSurveil = "false";

	/** 违法时间/处理时间 */
	private String findOrDealWith = "WFSJ";
	/** 统计项关联的部门 */
	private Dept dept;
	/** 自定义统计条件描述 */
	private String descn;
	/** 统计条件类别(任务常量/自定义统计条件) */
	private String type;
	/**关联任务*/
	private Set<Task> tasks = new HashSet<Task>();
	/**关联任务特殊项*/
	private Set<Task> taskSpecial = new HashSet<Task>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/*
	 * @OneToMany(cascade = {}, fetch = FetchType.LAZY, mappedBy =
	 * "transgressStatItem") public Set<TransgressAction> getTransgressActions()
	 * { return transgressActions; }
	 * 
	 * public void setTransgressActions(Set<TransgressAction> transgressActions)
	 * { this.transgressActions = transgressActions; }
	 */

	@Column(columnDefinition = "varchar2(1000)")
	public String getTransgressActionCodes() {
		return transgressActionCodes;
	}

	public void setTransgressActionCodes(String transgressActionCodes) {
		this.transgressActionCodes = transgressActionCodes;
	}

	public String getSecondLevelTypeIds() {
		return secondLevelTypeIds;
	}

	public void setSecondLevelTypeIds(String secondLevelTypeIds) {
		this.secondLevelTypeIds = secondLevelTypeIds;
	}

	public String getVehicleUseCodes() {
		return vehicleUseCodes;
	}

	public void setVehicleUseCodes(String vehicleUseCodes) {
		this.vehicleUseCodes = vehicleUseCodes;
	}

	@Column(columnDefinition = "varchar2(6) default 'false'")
	public String getUnionForce() {
		return unionForce;
	}

	public void setUnionForce(String unionForce) {
		this.unionForce = unionForce;
	}

	@Column(columnDefinition = "varchar2(10) default 'WFSJ'")
	public String getFindOrDealWith() {
		return findOrDealWith;
	}

	public void setFindOrDealWith(String findOrDealWith) {
		this.findOrDealWith = findOrDealWith;
	}

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumn(name = "dept_id")
	public Dept getDept() {
		return dept;
	}

	public void setDept(Dept dept) {
		this.dept = dept;
	}

	@Column(name = "item_descn", columnDefinition = "varchar2(2000)")
	public String getDescn() {
		return descn;
	}

	public void setDescn(String descn) {
		this.descn = descn;
	}

	@Column(name = "item_type", columnDefinition = "varchar2(1)")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	@Column(columnDefinition = "varchar2(6) default 'false'")
	public String getVioSurveil() {
		return vioSurveil;
	}

	public void setVioSurveil(String vioSurveil) {
		this.vioSurveil = vioSurveil;
	}
	@Column(name = "flapper_types", columnDefinition = "varchar2(1000)")
	public String getFlapperTypes() {
		return flapperTypes;
	}

	public void setFlapperTypes(String flapperTypes) {
		this.flapperTypes = flapperTypes;
	}
	@OneToMany(cascade = {}, fetch = FetchType.LAZY, mappedBy = "transgressStatItem")
	public Set<Task> getTasks() {
		return tasks;
	}

	public void setTasks(Set<Task> tasks) {
		this.tasks = tasks;
	}
	@OneToMany(cascade = {}, fetch = FetchType.LAZY, mappedBy = "specialTransgressStatItem")
	public Set<Task> getTaskSpecial() {
		return taskSpecial;
	}

	public void setTaskSpecial(Set<Task> taskSpecial) {
		this.taskSpecial = taskSpecial;
	}

}
