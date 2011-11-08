package com.googlecode.jtiger.assess.transgress.statcfg.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import com.googlecode.jtiger.core.model.BaseIdModel;

/**
 * 违法种类
 * 
 * @author DELPHI
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "transgress_type")
public class TransgressType extends BaseIdModel {
	/** 类别代码 */
	private String code;
	/** 类别描述 */
	private String descns;
	/** 类别类型 种类 分类 */
	private String type;
	/** 父类别 */
	private TransgressType parentTransgressType;
	/** 子类别 */
	private Set<TransgressType> childsTransgressTypes = new HashSet<TransgressType>();
	/** 关联违法行为 */
	private Set<TransgressAction> transgressActions = new HashSet<TransgressAction>(
			0);

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescns() {
		return descns;
	}

	public void setDescns(String descns) {
		this.descns = descns;
	}

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id")
	public TransgressType getParentTransgressType() {
		return parentTransgressType;
	}

	public void setParentTransgressType(TransgressType parentTransgressType) {
		this.parentTransgressType = parentTransgressType;
	}

	@OneToMany(cascade = {}, fetch = FetchType.LAZY, mappedBy = "parentTransgressType")
	@OrderBy(value = "id ASC")
	public Set<TransgressType> getChildsTransgressTypes() {
		return childsTransgressTypes;
	}

	public void setChildsTransgressTypes(
			Set<TransgressType> childsTransgressTypes) {
		this.childsTransgressTypes = childsTransgressTypes;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@OneToMany(cascade = {}, fetch = FetchType.LAZY, mappedBy = "transgressType")
	public Set<TransgressAction> getTransgressActions() {
		return transgressActions;
	}

	public void setTransgressActions(Set<TransgressAction> transgressActions) {
		this.transgressActions = transgressActions;
	}

}
