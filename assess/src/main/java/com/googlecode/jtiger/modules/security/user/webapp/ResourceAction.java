package com.googlecode.jtiger.modules.security.user.webapp;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.MatchMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;

import com.googlecode.jtiger.core.webapp.struts2.action.DefaultCrudAction;
import com.googlecode.jtiger.modules.security.user.UserConstants;
import com.googlecode.jtiger.modules.security.user.model.Resource;
import com.googlecode.jtiger.modules.security.user.model.Role;
import com.googlecode.jtiger.modules.security.user.service.ResourceManager;
import com.googlecode.jtiger.modules.security.user.service.RoleManager;

@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ResourceAction extends DefaultCrudAction<Resource, ResourceManager> {
  @Autowired
  private RoleManager roleMgr;
  
  private Role role = new Role();
  /**
   * comma分隔的资源id
   */
  private String resIds;
  
  
  public String index() {
    List<Resource> items = getManager().query("from Resource r where r.parent is null order by r.sortNo");
    getRequest().setAttribute("items", items);
    getRequest().setAttribute("roles", getRoles());
    return INDEX;
  }
  
  /**
   * 保存模块信息
   */
  public String saveModule() {  
    getManager().save(getModel());  
    
    return SUCCESS;    
  }
  
  /**
   * 保存资源信息
   */
  public String saveRes() {    
    Assert.notNull(getModel());
    Assert.notNull(getModel().getParent());
    Assert.hasLength(getModel().getParent().getId());
    try {
      getManager().save(getModel());
      render(getModel().getParent().getId(), "text/plain");
    } catch (Exception e) {
      render(e.getMessage(), "text/plain");
    }
    return null;    
  }
  
  /**
   * 列出某个模块的资源
   * @return
   */
  public String resOfModule() {
    List<Resource> items = getManager().query("from Resource r where r.parent.id=? order by r.sortNo",
        getModel().getId());
    Boolean isAll = false; //是否模块下的所有资源都可以被给定角色访问
    
    if (StringUtils.isNotBlank(role.getId())) {
      logger.debug("加载角色资源对应关系，用于页面checkbox复位{}", role.getId());
      List<String> resIds = getManager().loadResIdOfRole(role); //加载角色可访问的资源
      if (CollectionUtils.isNotEmpty(resIds) && CollectionUtils.isNotEmpty(items)) {
        int has = 0;
        for (Resource r : items) {
          if (resIds.contains(r.getId())) {
            has++; // 记录匹配的数量
            r.setChanged(true);
          } else {
            r.setChanged(false);
          }
        }
        // 如果都匹配，则标记为all
        if (has == items.size()) {
          isAll = true;
        }
      }
    }
    getRequest().setAttribute("parentId", getModel().getId());
    getRequest().setAttribute("isAll", isAll);
    getRequest().setAttribute("resources", items);
    return "resOfModule";
  }
  
  /**
   * 删除模块及其资源
   * @return
   */
  public String del() {
    if(getModel() == null || StringUtils.isBlank(getModel().getId())) {
      throw new IllegalArgumentException();
    }
    getManager().del(getModel().getId());  
    render("success", "text/plain");
    return null;
  }
  
  /**
   * 返回角色列表
   */
  @SuppressWarnings("unchecked")
  private List<Role> getRoles() {
    //return getManager().getDao().query("from Role r where r.name=? or r.name like ? order by r.name", 
    //    UserConstants.ROLE_ADMIN, MatchMode.START.toMatchString("ROLE_VIP_"));
	  return getManager().getDao().query("from Role r order by r.name");
  }
  
  /**
   * 为角色分配资源
   * @return
   */
  public String assignRes() {
    try {
      logger.debug("为{}分配资源", role.getId());
      roleMgr.assignRes(role, StringUtils.split(resIds, ','));
      render("success", "text/plain");
    } catch (Exception e) {
      e.printStackTrace();
      render(e.getMessage(), "text/plain");
    }
    
    return null;
  }


  /**
   * @return the resIds
   */
  public String getResIds() {
    return resIds;
  }

  /**
   * @param resIds the resIds to set
   */
  public void setResIds(String resIds) {
    this.resIds = resIds;
  }

  /**
   * @return the role
   */
  public Role getRole() {
    return role;
  }

  /**
   * @param role the role to set
   */
  public void setRole(Role role) {
    this.role = role;
  }
}
