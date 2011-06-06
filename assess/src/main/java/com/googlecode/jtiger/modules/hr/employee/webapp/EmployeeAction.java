package com.googlecode.jtiger.modules.hr.employee.webapp;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.Validator;

import com.googlecode.jtiger.core.Constants;
import com.googlecode.jtiger.core.util.DateUtil;
import com.googlecode.jtiger.core.util.RequestUtil;
import com.googlecode.jtiger.core.webapp.struts2.action.ExtJsCrudAction;
import com.googlecode.jtiger.modules.hr.HrConstants;
import com.googlecode.jtiger.modules.hr.dept.model.Dept;
import com.googlecode.jtiger.modules.hr.employee.model.Employee;
import com.googlecode.jtiger.modules.hr.employee.service.EmployeeManager;
import com.googlecode.jtiger.modules.hr.employee.webapp.validation.EmployeeValidator;

/**
 * 员工管理Action类
 * @author WBB
 * @version 3.0
 */
@SuppressWarnings({"serial"})
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class EmployeeAction extends ExtJsCrudAction<Employee, EmployeeManager> {
  /**
   *上传文件保存路径
   */
  public static final String EMPLOYEE_PHOTO_FOLDER = "/userfiles/employee";

  /**
   * 上传照片
   */
  private File picture;

  /**
   * 照片原来名称
   */
  private String pictureFileName;

  /**
   * 查询的部门名称
   */
  private Integer deptId;

  /**
   * 员工验证类
   */
  private EmployeeValidator employeeValidator;

  /**
   * 员工Tree
   */
  private List<Map<String, Object>> deptEmps;

  /**
   * 操作状态，1刷新树状列表
   */
  private String runState = null;

  /**
   * 员工离职方法
   */
  @SkipValidation
  public String dimission() {
    if (ArrayUtils.isEmpty(selectedItems)) {
      if (getModel() != null) {
        Serializable id = extractId(getModel());
        if (id != null) {
          selectedItems = new Serializable[] { id };
        }
      }
    }

    if (selectedItems != null) {
      for (Serializable id : selectedItems) {
        if (id != null) {
          Employee employee = getManager().get(convertId(id));
          if (employee != null) {
            employee.getUser().setStatus(Constants.STATUS_UNAVAILABLE);
            getManager().updateUser(employee.getUser());
          } else {
            logger.debug("试图修改null数据.");
          }
        }
      }

      logger.debug("{} items removed.", selectedItems.length);
    }

    return SUCCESS;
  }

  /**
   * 上传文件
   * @param source 原文件
   * @param originalName 原文件名称
   * @return
   */
  public File doUpload(File source, String originalName) {
    Assert.hasText(EMPLOYEE_PHOTO_FOLDER, "Please setup target path.");
    Assert.notNull(source, "Uploaded file must not be null.");
    Assert.isTrue(source.exists(), "Uploaded file must exist.");
    File folder = new File(EMPLOYEE_PHOTO_FOLDER);
    if (!folder.exists()) {
      folder.mkdirs();
    }
    String ext = originalName;
    if (originalName.indexOf(".") > 0) {
      ext = originalName.substring(originalName.lastIndexOf("."));
    }
    String rename = null;
    if (StringUtils.isBlank(rename)) {
      rename = DateUtil.getDateTime("yyyyMMddhhmmss", new Date())
          + RandomStringUtils.randomNumeric(3);
    }
    File dest = new File(new StringBuffer(EMPLOYEE_PHOTO_FOLDER).append(
        File.separatorChar).append(rename).append(ext).toString());
    try {
      FileCopyUtils.copy(source, dest);
      return dest;
    } catch (IOException e) {
      logger.error(e.getMessage());
      return null;
    }
  }

  /**
   * 保存员工
   * @see BaseManager#save(Dept)
   */
  @Override
  public String save() {
    logger.debug("保存员工信息。");
    
    if (StringUtils.isNotBlank(pictureFileName) && picture != null) {
      if (picture.exists()) {
        picture = doUpload(picture, pictureFileName);
        if (picture != null) {
          StringBuffer buf = new StringBuffer(EMPLOYEE_PHOTO_FOLDER)
              .append("/").append(picture.getName());
          getModel().setPhoto(buf.toString());
          logger.debug("用户照片 '{}' 上传成功.", buf.toString());
        }
      }
    }
    // 设置保存后返回的部门ID
    deptId = getModel().getDept().getId();
    return super.save();
  }

  /**
   * 编辑员工
   */
  @Override
  public String edit() {
    // 新建部门时
    if (this.getModel().getId() == null && deptId != null) {
      Dept dept = getManager().getDao().get(Dept.class, deptId);
      this.getModel().setDept(dept);
    }
    return super.edit();
  }

  /**
   * Build a tree as json format.
   */
  @SkipValidation
  public String deptEmpTree() {
    logger.debug("获取部门-员工列表");
    if (RequestUtil.isJsonRequest(getRequest())) {      
      logger.debug("Json Request.");
      // 得到部门树，排除正在编辑的部门以级子部门
      Map<String, Object> deptTree = getManager().getDeptEmpTree();
      if (!deptTree.isEmpty()) {
        deptEmps = new ArrayList<Map<String, Object>>();
        deptEmps.add(deptTree);
      }
      return JSON;
    }
    return INDEX;
  }

  @Override
  @SkipValidation
  public String index() {
    // 排除已离职人员
    StringBuffer sbf = new StringBuffer(
        "from Employee e where e.user.status = ")
        .append(Constants.STATUS_AVAILABLE);
    if (deptId != null) {
      sbf.append(" and e.dept.id = ").append(deptId);
    }

    if (StringUtils.isNotBlank(getModel().getName())) {
      sbf.append(" and e.name like '%").append(getModel().getName()).append(
          "%'");
    }

    items = getManager().query(sbf.toString(), new Object[] {});
    return INDEX;
  }
  
  @SkipValidation
  public String layout() {
    logger.debug("进入员工管理");
    return "layout";
  }
  
  /**
   * 返回学历操作（学历）列表
   */
  public Map<String, String> getDegreeMap() {
    return HrConstants.DEGREE_MAP;
  }

  /**
   * 返回性别操作
   */
  public Map<String, String> getSexMap() {
    return HrConstants.SEX_MAP;
  }

  /**
   * 返回婚姻操作
   */
  public Map<String, String> getMarriedMap() {
    return HrConstants.MARRIED_MAP;
  }

  /**
   * 返回政治面貌操作
   */
  public Map<String, String> getPoliticalMap() {
    return HrConstants.POLITICAL_MAP;
  }

  public File getPicture() {
    return picture;
  }

  public void setPicture(File picture) {
    this.picture = picture;
  }

  public void setPictureFileName(String pictureFileName) {
    this.pictureFileName = pictureFileName;
  }

  public String getPictureFileName() {
    return pictureFileName;
  }

  public Integer getDeptId() {
    return deptId;
  }

  public void setDeptId(Integer deptId) {
    this.deptId = deptId;
  }

  public List<Map<String, Object>> getDeptEmps() {
    return deptEmps;
  }

  public String getRunState() {
    return runState;
  }

  public void setRunState(String runState) {
    this.runState = runState;
  }

  /**
   * 得到当前员工列表所属部门
   * @return
   */
  public String getDeptName() {
    // 返回null所有部门下的员工
    if (deptId == null) {
      return null;
    }

    Dept dept = getManager().getDao().get(Dept.class, deptId);
    return dept == null ? null : dept.getName();
  }

  @Override
  public Validator getValidator() {
    return employeeValidator;
  }

  @Autowired
  public void setEmployeeValidator(EmployeeValidator employeeValidator) {
    this.employeeValidator = employeeValidator;
  }
}