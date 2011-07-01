package com.googlecode.jtiger.assess.task.stat.empduty.webapp;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;


import com.googlecode.jtiger.assess.task.stat.empduty.model.EmployeeDutyRecord;
import com.googlecode.jtiger.assess.task.stat.empduty.service.EmployeeDutyRecordManager;
import com.googlecode.jtiger.core.webapp.struts2.action.DefaultCrudAction;

@SuppressWarnings("serial")
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class EmployeeDutyRecordAction extends
		DefaultCrudAction<EmployeeDutyRecord, EmployeeDutyRecordManager> {
	public String index(){
		
		return INDEX;
	}
}
