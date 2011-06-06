package com.googlecode.jtiger.modules.hr.employee.webapp.validation;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.googlecode.jtiger.core.webapp.struts2.validation.ActionErrors;
import com.googlecode.jtiger.core.webapp.struts2.validation.DaoValidateSupport;
import com.googlecode.jtiger.modules.hr.employee.model.Employee;

/**
 * 用户管理验证类
 * @author WBB
 * @version 3.0
 */
@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class EmployeeValidator extends DaoValidateSupport<Employee> {
  @Override
  public void doValidate(Employee employee, ActionErrors errors) {
    //判断登录名是否存在
    if (employee.getUser() != null
        && StringUtils.isNotBlank(employee.getUser().getLoginId())
        && getDao().exists(employee.getUser(), "loginId")) {
      errors.rejectDirectly("登录名：{0}已存在！", employee.getUser().getLoginId());
    }
    validateRequiredString(employee.getUser().getLoginId(), "登录名", errors);
    validateRequiredString(employee.getUser().getPassword(), "登录密码", errors);
    validateRequiredString(employee.getUser().getConfirmPwd(), "确认密码", errors);
    validateStringLength(employee.getUser().getPassword(), 3, null, "登录密码",
        errors);
    // 验证密码与确认密码是否一致
    if (StringUtils.isNotBlank(employee.getUser().getPassword())
        && StringUtils.isNotBlank(employee.getUser().getConfirmPwd())
        && !employee.getUser().getPassword().equals(
            employee.getUser().getConfirmPwd())) {
      errors.rejectDirectly("{0}", "登录密码与确认密码不一致！");
    }
    validateRequiredString(employee.getName(), "姓名", errors);
    validateRequired(employee.getDept().getId(), "所在部门", errors);
    validateRequiredString(employee.getPlace(), "职位", errors);
    validateRequiredString(employee.getUser().getEmail(), "电子信箱", errors);
    validateEmail(employee.getUser().getEmail(), errors);

  }
}
