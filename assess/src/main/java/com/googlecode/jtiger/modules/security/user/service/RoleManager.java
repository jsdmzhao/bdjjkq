package com.googlecode.jtiger.modules.security.user.service;

import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.googlecode.jtiger.core.ApplicationException;
import com.googlecode.jtiger.core.Constants;
import com.googlecode.jtiger.core.service.BaseGenericsManager;
import com.googlecode.jtiger.modules.security.user.model.Resource;
import com.googlecode.jtiger.modules.security.user.model.Role;
import com.googlecode.jtiger.modules.security.user.model.User;

/**
 * 角色管理
 * @author sam
 * 
 */
@SuppressWarnings("rawtypes")
@Service
public class RoleManager extends BaseGenericsManager<Role> {

  /**
   * @see RoleManager#getRolesByUser(User)
   */
  @SuppressWarnings("unchecked")
  public Role[] getRolesByUser(User user) {
    Set roleSet = user.getRoles();
    Role[] retRoles = new Role[roleSet.size()];
    roleSet.toArray(retRoles);
    return retRoles;
  }

  /**
   * 
   */
  @Override
  @Transactional
  public void save(Role role) {
    Assert.notNull(role);
    if (!getDao().exists(role, "name")) {
      getDao().saveOrUpdate(role);      
    } else {
      throw new ApplicationException("角色名称'" + role.getName() + "'已经存在.");
    }
  }

  /**
   * @see com.systop.common.service.BaseManager#remove(java.lang.Object)
   */
  @Override
  @Transactional
  public void remove(Role role) {
    Assert.notNull(role, "Role to delete must not be null.");
    
    if (role.getId() == null) {
      return;
    }
    //如果是系统角色则不删除
    if(StringUtils.equals(Constants.STATUS_AVAILABLE, role.getIsSys())) {
      logger.warn("Can't delete system role.");
      return;
    }

    // 删除与用户之间的关联关系
    Set<User> userSet = role.getUsers();
    User[] users = userSet.toArray(new User[] {});
    logger.debug("Remove the relations between Role and User");
    for (int i = 0; i < users.length; i++) {
      users[i].getRoles().remove(role);
      role.getUsers().remove(users[i]);
      // saveObject(users[i]);
    }
    super.remove(role);
  }
  
  /**
   * 为角色分配资源
   * @param role 角色
   * @param resIds 资源ID
   */
  @Transactional
  public void assignRes(Role role, String[] resIds) {
    Assert.notNull(resIds);
    Assert.notEmpty(resIds);
    Assert.hasLength(role.getId());
    
    role = get(role.getId());
    role.getResources().clear();
    
    for(String resId : resIds) {
      Resource res = getDao().get(Resource.class, resId);
      if(res != null) {
        logger.debug("为角色{},分配资源{}", role.getName(), res.getName());
        role.getResources().add(res);
      }
    }
    getDao().clear();
    getDao().saveOrUpdate(role);
  }


}
