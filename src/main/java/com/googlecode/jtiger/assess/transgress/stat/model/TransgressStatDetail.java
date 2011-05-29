package com.googlecode.jtiger.assess.transgress.stat.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.googlecode.jtiger.core.model.BaseIdModel;

@SuppressWarnings("serial")
@Entity
@Table(name = "transgress_stat_detail")
public class TransgressStatDetail extends BaseIdModel {
	/** 关联统计 */
	private TransgressStat transgressStat;

	@ManyToOne
	@JoinColumn(name = "transgress_stat_id")
	public TransgressStat getTransgressStat() {
		return transgressStat;
	}

	public void setTransgressStat(TransgressStat transgressStat) {
		this.transgressStat = transgressStat;
	}

}
