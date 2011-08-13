package com.googlecode.jtiger.assess.evaluate.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.googlecode.jtiger.core.model.BaseIdModel;
import com.googlecode.jtiger.modules.hr.dept.model.Dept;
import com.googlecode.jtiger.modules.hr.employee.model.Employee;

/**
 * <pre>
 * 评分记录明细,记录各类考核标准,任务常量分别得到多少分(总分)
 * 
 * 评分记录记载xx年xx月各个部门考核得到的总分
 * 评分记录明细则记载这个总分的构成:
 * 
 * 	 任务常量总分
 *   日常勤务总分
 *   B组考核标准总分
 *   重奖总分
 *   一票否决总分
 *   其他奖项总分
 *   
 * @author DELPHI
 * </pre>
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "assess_evaluate_rec_det")
public class EvaluateRecordDetail extends BaseIdModel {
	/** 关联评分记录 */
	private EvaluateRecord evaluateRecord;
	/** 被评分部门 */
	private Dept dept;
	/** 被评分警员 */
	private Employee employee;
	/** 类别,任务常量-日常勤务-B组-重奖-一票否决-其他 */
	private String type;
	/** 小计 */
	private Float total;

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumn(name = "evaluate_Record_id")
	public EvaluateRecord getEvaluateRecord() {
		return evaluateRecord;
	}

	public void setEvaluateRecord(EvaluateRecord evaluateRecord) {
		this.evaluateRecord = evaluateRecord;
	}

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumn(name = "employee_id")
	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Dept getDept() {
		return dept;
	}

	public void setDept(Dept dept) {
		this.dept = dept;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Float getTotal() {
		return total;
	}

	public void setTotal(Float total) {
		this.total = total;
	}
}
