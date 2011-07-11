package com.googlecode.jtiger.assess.transgress.statcfg.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.googlecode.jtiger.core.model.BaseIdModel;

/**
 * 号牌种类
 * 
 * @author DELPHI
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "assess_flapper_type")
public class FlapperType extends BaseIdModel {
	private String code;
	private String name;

	public String getCode() {
		return code;
	}
	@Column(columnDefinition = "varchar2(4)")
	public void setCode(String code) {
		this.code = code;
	}
	@Column(columnDefinition = "varchar2(50)")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
