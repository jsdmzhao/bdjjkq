package com.googlecode.jtiger.assess.transgress.statcfg.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
	/** 保存二级类别的id,为的是在修改统计项时,能够显示用户已经关联到的违法行为,并且以这些违法行为以二级类别"分组" */
	private String secondLevelTypeIds;
	/** 是否关联到vio_force表 */
	private Boolean unionForce = false;

	/** 发现时间/处理时间 */
	private String findOrDealWith = "FXSJ";
	/** 统计项关联的部门 */
	private Dept dept;

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

	@Column(columnDefinition = "SMALLINT default 0")
	public Boolean getUnionForce() {
		return unionForce;
	}

	public void setUnionForce(Boolean unionForce) {
		this.unionForce = unionForce;
	}

	@Column(columnDefinition = "varchar2(10) default 'FXSJ'")
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

}