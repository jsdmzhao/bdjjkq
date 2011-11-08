package com.googlecode.jtiger.assess.transgress.statproperties.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.googlecode.jtiger.core.model.BaseIdModel;

/** 违法统计报表属性 */
@SuppressWarnings("serial")
@Entity
@Table(name = "transgress_stat_properties")
public class TransgressStatProperties extends BaseIdModel {

	/** 表头 */
	private String title;
	/** 备注 */
	private String remark;

	@Column(columnDefinition = "varchar2(500)")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(columnDefinition = "varchar2(1000)")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
