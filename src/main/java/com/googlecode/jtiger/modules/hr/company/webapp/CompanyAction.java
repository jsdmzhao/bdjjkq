package com.googlecode.jtiger.modules.hr.company.webapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Validator;

import com.googlecode.jtiger.core.webapp.struts2.action.ExtJsCrudAction;
import com.googlecode.jtiger.modules.hr.company.service.CompanyManager;
import com.googlecode.jtiger.modules.hr.company.webapp.validation.CompanyValidator;
import com.googlecode.jtiger.modules.hr.dept.model.Dept;

/**
 * 公司管理Action类
 * @author WBB
 * @version 3.0
 */
@SuppressWarnings("serial")
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CompanyAction extends ExtJsCrudAction<Dept, CompanyManager> {
  private CompanyValidator companyValidator;

  @Override
  public String index() {
    // 设置当前公司对象到Model
    setModel(getManager().getCompany());
    return INDEX;
  }
  
  @Override
  public String save() {
    String rtn = super.save();
    if (SUCCESS.equals(rtn)) {
      addActionMessage("公司信息保存成功");
    }
    return rtn;
  }

  @Override
  public Validator getValidator() {
    return companyValidator;
  }

  @Autowired
  public void setCompanyValidator(CompanyValidator companyValidator) {
    this.companyValidator = companyValidator;
  }
}
