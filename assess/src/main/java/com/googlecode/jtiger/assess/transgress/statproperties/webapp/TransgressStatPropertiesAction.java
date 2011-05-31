package com.googlecode.jtiger.assess.transgress.statproperties.webapp;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.googlecode.jtiger.assess.transgress.statproperties.model.TransgressStatProperties;
import com.googlecode.jtiger.assess.transgress.statproperties.service.TransgressStatPropertiesManager;
import com.googlecode.jtiger.core.webapp.struts2.action.DefaultCrudAction;

@SuppressWarnings("serial")
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class TransgressStatPropertiesAction
		extends
		DefaultCrudAction<TransgressStatProperties, TransgressStatPropertiesManager> {
	@Override
	public String edit() {
		setModel(getManager().getIt());

		return "edit";
	}

	@Override
	public String save() {
		try {
			getManager().save(getModel());
			render("success", "text/plain");
		} catch (Exception e) {
			e.printStackTrace();
			render(e.getMessage(), "text/plain");
		}

		return null;
	}
}
