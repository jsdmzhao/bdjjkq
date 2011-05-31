package com.googlecode.jtiger.assess.transgress.statcfg.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.googlecode.jtiger.core.model.BaseIdModel;

/**
 * 机动车使用性质代码
 * 
 * @author DELPHI
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "vehicle_use_code")
public class VehicleUseCode extends BaseIdModel {
	private String code;
	private String name;
	private String descns;

	@Column(columnDefinition = "varchar2(1)")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescns() {
		return descns;
	}

	public void setDescns(String descns) {
		this.descns = descns;
	}
}
