package com.googlecode.jtiger.modules.hr.dept.webapp.validation;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.googlecode.jtiger.core.webapp.struts2.validation.AbstractValidator;
import com.googlecode.jtiger.core.webapp.struts2.validation.ActionErrors;
import com.googlecode.jtiger.modules.hr.dept.model.Dept;

/**
 * 部门验证类
 * @author WBB
 * @version 3.0
 */
@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class DeptValidator extends AbstractValidator<Dept> {
  @Override
  public void doValidate(Dept dept, ActionErrors errors) {
    this.validateRequiredString(dept.getName(), "部门名称", errors);
    if (dept.getParentDept() == null || dept.getParentDept().getId() == null) {
      errors.rejectDirectly("{0}", "请先添加公司信息");
    }
  }
}
