package com.googlecode.jtiger.assess.transgress.statcfg.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.googlecode.jtiger.core.model.BaseIdModel;

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
	/** 关联违法行为集合 */
	private Set<TransgressAction> transgressActions = new HashSet<TransgressAction>(
			0);
	/** 关联违法行为代码字符串 */
	private String transgressActionCodes;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(cascade = {}, fetch = FetchType.LAZY, mappedBy = "transgressStatItem")
	public Set<TransgressAction> getTransgressActions() {
		return transgressActions;
	}

	public void setTransgressActions(Set<TransgressAction> transgressActions) {
		this.transgressActions = transgressActions;
	}

	public String getTransgressActionCodes() {
		return transgressActionCodes;
	}

	public void setTransgressActionCodes(String transgressActionCodes) {
		this.transgressActionCodes = transgressActionCodes;
	}
}
