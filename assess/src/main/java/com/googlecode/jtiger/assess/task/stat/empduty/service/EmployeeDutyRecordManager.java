package com.googlecode.jtiger.assess.task.stat.empduty.service;

import org.springframework.stereotype.Service;


import com.googlecode.jtiger.assess.task.stat.empduty.model.EmployeeDutyRecord;
import com.googlecode.jtiger.core.service.BaseGenericsManager;
@Service
public class EmployeeDutyRecordManager extends
		BaseGenericsManager<EmployeeDutyRecord> {
	/**
	 * 评分
	 * 逻辑:
	 * 1.根据任务常量,给大队评分,每一项计入评分明细
	 * 2.根据日常勤务记录,记录每个警员的
	 */
	public void eval(){
		
	}
}
