package com.googlecode.jtiger.assess.transgress.statcfg.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.googlecode.jtiger.core.model.BaseIdModel;

/**
 * 统计条件
 * 
 * @author DELPHI
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "transgress_stat_condition")
public class TransgressCustomStatCondition extends BaseIdModel {
	/** 开始时分 */
	private String beginHourMinute;
	/** 结束时分 */
	private String endHourMinute;

	public String getBeginHourMinute() {
		return beginHourMinute;
	}

	public void setBeginHourMinute(String beginHourMinute) {
		this.beginHourMinute = beginHourMinute;
	}

	public String getEndHourMinute() {
		return endHourMinute;
	}

	public void setEndHourMinute(String endHourMinute) {
		this.endHourMinute = endHourMinute;
	}

}
