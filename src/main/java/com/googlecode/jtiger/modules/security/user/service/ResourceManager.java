package com.googlecode.jtiger.modules.security.user.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.googlecode.jtiger.core.ApplicationException;
import com.googlecode.jtiger.core.service.BaseGenericsManager;
import com.googlecode.jtiger.modules.security.user.model.Resource;
import com.googlecode.jtiger.modules.security.user.model.Role;

/**
 * 资源管理类
 * @author sam
 *
 */
@Service
public class ResourceManager extends BaseGenericsManager<Resource> {
  @Transactional
  public void save(Resource res) {
    Assert.notNull(res);
    if(StringUtils.isBlank(res.getId())) {
      res.setId(null);
    }
    if(getDao().exists(res, "url")) {
      throw new ApplicationException("URL'" + res.getUrl() + "'已经存在了");
    }
    if(res.getParent() != null && StringUtils.isNotBlank(res.getParent().getId())) {
      Resource parent = get(res.getParent().getId());
      res.setParent(parent);
      parent.getChildren().add(res);
    } else {
      res.setParent(null);
    }
    getDao().merge(res);
  }
  
  /**
   * 删除模块及其资源
   */
  @Transactional
  public void del(String id) {
    Resource m = get(id);
    getDao().delete(m);
  }
  
  
  /**
   * 加载某个角色的资源ID
   * 
   */
  @SuppressWarnings("unchecked")
  public List<String> loadResIdOfRole(Role role) {
    Assert.notNull(role);
    Assert.hasLength(role.getId());
    
    return getDao().query("select re.id from Resource re inner join re.roles ro where ro.id=?", role.getId());
  }
  
  /**
   * 加载某个角色的资源
   * 
   */
  @SuppressWarnings("unchecked")
  public List<Resource> loadResOfRole(Role role) {
    Assert.notNull(role);
    Assert.hasLength(role.getId());
    
    return getDao().query("select re from Resource re inner join r.roles ro where ro.id=?", role.getId());
  }

}
