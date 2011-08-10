package com.googlecode.jtiger.assess.evaluate.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.googlecode.jtiger.core.model.BaseIdModel;
import com.googlecode.jtiger.modules.hr.dept.model.Dept;

/**
 * 评分记录
 * 
 * @author DELPHI
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "assess_evaluate_record")
public class EvaluateRecord extends BaseIdModel {
	/** 记录日期 */
	private Date recordTime;
	/** 部门 */
	private Dept dept;
	/** 总分 */
	private Float total;
	private int year;
	private int month;
	/** 关联评分明细 */
	private Set<EvaluateRecordDetail> details = new HashSet<EvaluateRecordDetail>(
			0);

	private Set<EvaluateRecordTaskConst> consts = new HashSet<EvaluateRecordTaskConst>(
			0);
	
	private String recordType;

	public Date getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}
	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumn(name = "dept_id")
	public Dept getDept() {
		return dept;
	}

	public void setDept(Dept dept) {
		this.dept = dept;
	}

	public Float getTotal() {
		return total;
	}

	public void setTotal(Float total) {
		this.total = total;
	}

	@OneToMany(cascade = {}, fetch = FetchType.LAZY, mappedBy = "evaluateRecord")
	public Set<EvaluateRecordDetail> getDetails() {
		return details;
	}

	public void setDetails(Set<EvaluateRecordDetail> details) {
		this.details = details;
	}

	@OneToMany(cascade = {}, fetch = FetchType.LAZY, mappedBy = "evaluateRecord")
	public Set<EvaluateRecordTaskConst> getConsts() {
		return consts;
	}

	public void setConsts(Set<EvaluateRecordTaskConst> consts) {
		this.consts = consts;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}
	@Column(name = "record_type",columnDefinition = "varchar2(8) default 'monthly'")
	public String getRecordType() {
		return recordType;
	}

	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}

}
