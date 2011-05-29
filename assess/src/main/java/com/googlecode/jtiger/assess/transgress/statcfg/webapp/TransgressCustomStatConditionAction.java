package com.googlecode.jtiger.assess.transgress.statcfg.webapp;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.googlecode.jtiger.assess.transgress.statcfg.model.TransgressCustomStatCondition;
import com.googlecode.jtiger.assess.transgress.statcfg.service.TransgressCustomStatConditionManager;
import com.googlecode.jtiger.core.webapp.struts2.action.DefaultCrudAction;

@SuppressWarnings("serial")
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class TransgressCustomStatConditionAction
		extends
		DefaultCrudAction<TransgressCustomStatCondition, TransgressCustomStatConditionManager> {
	/** 开始时分 */
	private String beginHourMinute;
	/** 结束时分 */
	private String endHourMinute;

	public String list() {
		setModel(getManager().get().get(0));
		//beginHourMinute = getManager().get().get(0).getBeginHourMinute();
		//endHourMinute = getManager().get().get(0).getEndHourMinute();
		
		return "list";
	}

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
