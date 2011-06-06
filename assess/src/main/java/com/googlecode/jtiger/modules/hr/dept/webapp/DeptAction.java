package com.googlecode.jtiger.modules.hr.dept.webapp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.validation.SkipValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Validator;

import com.googlecode.jtiger.core.util.RequestUtil;
import com.googlecode.jtiger.core.webapp.struts2.action.ExtJsCrudAction;
import com.googlecode.jtiger.modules.hr.dept.model.Dept;
import com.googlecode.jtiger.modules.hr.dept.service.DeptManager;
import com.googlecode.jtiger.modules.hr.dept.webapp.validation.DeptValidator;

/**
 * 部门管理Action类
 * @author WBB
 * @version 3.0
 */
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@SuppressWarnings({"serial" })
public class DeptAction extends ExtJsCrudAction<Dept, DeptManager> {
  private DeptValidator deptValidator;

  /**
   * 当前上级部门ID
   */
  private Integer parentId;

  

  /**
   * 保存部门Tree
   */
  private List<Map<String, Object>> depts;

  /**
   * 操作状态,以刷新菜单Tree
   */
  private String runState = null;

  @Override
  public String save() {
    String rtn = super.save();
    if (SUCCESS.equals(rtn)) {
      addActionMessage("部门信息保存成功");
      runState = "1";
    }
    return rtn;
  }

  /**
   * 处理新增部门，设置上级部门为公司
   */
  @Override
  @SkipValidation
  public String edit() {
    // 为新增部门
    if (getModel().getId() == null) {
      // 没有上级部门
      if (parentId == null) {
        Dept company = getManager().getCompanyManager().getCompany();
        // 不存在公司信息时
        if (company == null) {
          addActionError("请先添加公司信息");
        } else {
          getModel().setParentDept(company);
        }
      } else {
        Dept patDept = getManager().get(parentId);
        if (patDept == null) {
          addActionError("无此部门，此部门可能已被删除");
        } else {
          getModel().setParentDept(patDept);
        }
      }
    }
    //删除部门后，反回edit.do方法传递参数runState=1表示部门已删除
    if ("1".equals(runState)) {
      //添加提示信息
      addActionMessage("部门信息已删除成功");
    }
    return INPUT;
  }

  /**
   * 删除部门信息，部门存在关联不能删除
   */
  @Override
  @SkipValidation
  public String remove() {
    // 部门删除都为单条删除
    if (getModel().getId() == null) {
      return INPUT;
    }
    // 得到此部门对象
    Dept dept = getManager().get(getModel().getId());
    if (dept == null) {
      return INPUT;
    }

    if (!dept.getChildDepts().isEmpty()) {
      addActionError("此部门下存在子部门不能删除");
    }

    if (!dept.getEmployees().isEmpty()) {
      addActionError("此部门下存在员工不能删除");
    }

    if (hasActionErrors()) {
      return INPUT;
    }

    getManager().remove(dept);
    // 反回部门新建页面与保存不同
    return "success-rme";
  }

  /**
   * Build a tree as json format.
   */
  @SkipValidation
  public String deptTree() {
    if (RequestUtil.isJsonRequest(getRequest())) {
      //得到部门树，排除正在编辑的部门以级子部门
      Map<String, Object> deptTree = getManager().getDeptTree(getModel().getId());
      if (!deptTree.isEmpty()) {
    	  depts = new ArrayList<Map<String, Object>>();
          depts.add(deptTree);
      }
      return JSON;
    }
    return INDEX;
  }

  

  @Override
  public Validator getValidator() {
    return deptValidator;
  }

  @Autowired
  public void setDeptValidator(DeptValidator deptValidator) {
    this.deptValidator = deptValidator;
  }
  
  public List<Map<String, Object>> getDepts() {
    return depts;
  }

  public void setDepts(List<Map<String, Object>> depts) {
    this.depts = depts;
  }

  public Integer getParentId() {
    return parentId;
  }

  public void setParentId(Integer parentId) {
    this.parentId = parentId;
  }

  public String getRunState() {
    return runState;
  }

  public void setRunState(String runState) {
    this.runState = runState;
  }
}
