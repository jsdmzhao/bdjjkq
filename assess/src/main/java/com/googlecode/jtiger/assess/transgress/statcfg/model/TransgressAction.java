package com.googlecode.jtiger.assess.transgress.statcfg.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.googlecode.jtiger.core.model.BaseIdModel;

/**
 * 违法行为
 * 
 * @author DELPHI
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "transgress_action")
public class TransgressAction extends BaseIdModel {
	/** 违法行为代码 */
	private String code;
	/** 违法行为描述 */
	private String descns;
	/** 违法类别 */
	private TransgressType transgressType;
	
	//private TransgressStatItem transgressStatItem;

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
	@JoinColumn(name = "transgress_type_id")
	public TransgressType getTransgressType() {
		return transgressType;
	}

	public void setTransgressType(TransgressType transgressType) {
		this.transgressType = transgressType;
	}
	/*@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumn(name = "transgress_stat_id")
	public TransgressStatItem getTransgressStatItem() {
		return transgressStatItem;
	}

	public void setTransgressStatItem(TransgressStatItem transgressStatItem) {
		this.transgressStatItem = transgressStatItem;
	}*/
}
