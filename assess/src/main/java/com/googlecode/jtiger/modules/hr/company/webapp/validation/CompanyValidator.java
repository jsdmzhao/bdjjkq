package com.googlecode.jtiger.modules.hr.company.webapp.validation;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.googlecode.jtiger.core.webapp.struts2.validation.AbstractValidator;
import com.googlecode.jtiger.core.webapp.struts2.validation.ActionErrors;
import com.googlecode.jtiger.modules.hr.dept.model.Dept;

/**
 * 公司验证类
 * @author WBB
 * @version 3.0
 */
@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CompanyValidator extends AbstractValidator<Dept> {
  @Override
  public void doValidate(Dept dept, ActionErrors errors) {
    this.validateRequiredString(dept.getName(), "公司名称", errors);
    this.validateInt(dept.getZip(), "邮编", errors);
    this.validateEmail(dept.getEmail(), errors);
  }
}
