package com.googlecode.jtiger.modules.security.ext;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import net.spy.memcached.MemcachedClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.util.CollectionUtils;

import com.googlecode.jtiger.core.dao.hibernate.BaseHibernateDao;
import com.googlecode.jtiger.modules.security.user.model.Resource;
import com.googlecode.jtiger.modules.security.user.model.Role;

/**
 * 将角色和资源的对应关系保存在缓存中，以提高性能
 * @author catstiger@gmail.com
 *
 */
@SuppressWarnings("unchecked")
//@Component
public class MemcachedResourceCache implements ResourceCache {
  private static Logger logger = LoggerFactory.getLogger(MemcachedResourceCache.class);
  public static final String KEY_RESOURCES_IN_CACHE = "__key__resources__in__cache__";
  @Autowired
  private BaseHibernateDao dao;
  
  @Autowired
  private MemcachedClient cache;
  
  @Override
  public Map<String, Collection<ConfigAttribute>> getConfigAttributesFromCache() {
    Map<String, Collection<ConfigAttribute>> resourcesMap = 
      (Map<String, Collection<ConfigAttribute>>) cache.get(KEY_RESOURCES_IN_CACHE);
    if(resourcesMap == null) {
      resourcesMap = loadResources(); //因为refresh方法异步执行，所以这里要用同步的方法获取resources
      refresh();
    }
    
    return resourcesMap;
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
   * 将资源和角色的对应关系存入缓存。
   */
  @Override
  @Async
  public void refresh() {
    cache.set(KEY_RESOURCES_IN_CACHE, 6 * 3600, loadResources());
    logger.info("Refresh resource cache.");
  }
  
  /**
   * 从数据库中加载资源和角色的对应关系
   */
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
