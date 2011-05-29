package com.googlecode.jtiger.assess.transgress.statcfg.webapp;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.googlecode.jtiger.assess.transgress.statcfg.model.TransgressStatItem;
import com.googlecode.jtiger.assess.transgress.statcfg.service.TransgressStatItemManager;
import com.googlecode.jtiger.core.webapp.struts2.action.DefaultCrudAction;

@SuppressWarnings("serial")
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class TransgressStatItemAction extends
		DefaultCrudAction<TransgressStatItem, TransgressStatItemManager> {
	public String edit() {

		return INPUT;
	}

	public String save() {
		return SUCCESS;
	}

	public String remove() {
		return SUCCESS;
	}
}
