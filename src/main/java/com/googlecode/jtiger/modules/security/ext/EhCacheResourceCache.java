package com.googlecode.jtiger.modules.security.ext;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import net.sf.ehcache.Ehcache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.googlecode.jtiger.core.dao.hibernate.BaseHibernateDao;
import com.googlecode.jtiger.modules.cache.CacheHelper;
import com.googlecode.jtiger.modules.security.user.model.Resource;
import com.googlecode.jtiger.modules.security.user.model.Role;

@Component
public class EhCacheResourceCache implements ResourceCache {
  private static Logger logger = LoggerFactory.getLogger(EhCacheResourceCache.class);
  public static final String KEY_RESOURCES_IN_CACHE = "__key__resources__in__cache__";
  @Autowired
  private BaseHibernateDao dao;
  @Autowired
  private Ehcache cache;
  
  @SuppressWarnings("unchecked")
  @Override
  public Map<String, Collection<ConfigAttribute>> getConfigAttributesFromCache() {
    Map<String, Collection<ConfigAttribute>> resourcesMap = 
      (Map<String, Collection<ConfigAttribute>>) CacheHelper.get(cache, KEY_RESOURCES_IN_CACHE);
    if(resourcesMap == null) {
      resourcesMap = loadResources();
      refresh();
    }
    
    return resourcesMap;
  }

  @Override
  public void refresh() {
    CacheHelper.put(cache, KEY_RESOURCES_IN_CACHE, loadResources());
    logger.info("Refresh resource cache.");
  }
  
  /**
   * 系统启动的时候更新缓存
   */
  @PostConstruct
  public void initCache() {
    if(cache.get(KEY_RESOURCES_IN_CACHE) == null) {
      refresh();
    }
  }
  
  /**
   * 从数据库中加载资源和角色的对应关系
   */
  @SuppressWarnings({ "unchecked"})
  private Map<String, Collection<ConfigAttribute>> loadResources() {    
    List<Resource> resources = dao.query("from Resource r inner join fetch r.roles ro order by r.url desc");
    Map<String, Collection<ConfigAttribute>> resourcesMap = 
      new HashMap<String, Collection<ConfigAttribute>>(resources.size());
    for(Resource res : resources) {
      Set<Role> roles = res.getRoles();
      if(CollectionUtils.isEmpty(roles)) {
        continue;
      }
      Collection<ConfigAttribute> attrs = new ArrayList<ConfigAttribute>(roles.size());
      for(Role r : roles) {
        attrs.add(new SecurityConfig(r.getName()));        
      }
      resourcesMap.put(res.getUrl(), attrs);
    }
    return resourcesMap;
  }

}
