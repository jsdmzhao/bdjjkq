package com.googlecode.jtiger.assess.task.statcfg.webapp;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.xwork.StringUtils;
import org.hibernate.criterion.MatchMode;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.googlecode.jtiger.assess.task.statcfg.model.TaskType;
import com.googlecode.jtiger.assess.task.statcfg.service.TaskTypeManager;
import com.googlecode.jtiger.core.webapp.struts2.action.DefaultCrudAction;

@SuppressWarnings("serial")
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class TaskTypeAction extends
		DefaultCrudAction<TaskType, TaskTypeManager> {
	public String index() {
		StringBuffer buf = new StringBuffer("from TaskType tt where tt.type = '0' ");
		List<Object> args = new ArrayList<Object>();
		if (StringUtils.isNotBlank(getModel().getName())) {
			buf.append("and tt.name like ?");
			args.add(MatchMode.ANYWHERE.toMatchString(getModel().getName()));
		}
		page = getManager().pageQuery(pageOfBlock(), buf.toString(),
				args.toArray());
		restorePageData(page);
		return INDEX;
	}
}
