package com.googlecode.jtiger.modules.demo.webapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.googlecode.jtiger.core.dao.hibernate.BaseHibernateDao;
import com.googlecode.jtiger.core.webapp.struts2.action.BaseAction;
import com.googlecode.jtiger.modules.security.user.model.User;

@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class DemoAction extends BaseAction {
  @Autowired
  BaseHibernateDao dao;
  public String export() {
    getRequest().setAttribute("items", dao.get(User.class));
    return "export";
  }
}
