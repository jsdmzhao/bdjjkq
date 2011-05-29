package com.googlecode.jtiger.modules.security.ext;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

/**
 * 
 * @author catstiger@gmail.com
 *
 */
@Service
public class DatabaseInvocationSecurityMetadataSource implements
    FilterInvocationSecurityMetadataSource {
  private static Logger logger = LoggerFactory.getLogger(DatabaseInvocationSecurityMetadataSource.class);
  
  @Autowired
  private ResourceCache resCache;
     
  private PathMatcher pathMatcher = new AntPathMatcher();
  
  @Override
  public Collection<ConfigAttribute> getAttributes(Object object)
      throws IllegalArgumentException {
    //从缓存中得到URL和角色的对应关系
    Map<String, Collection<ConfigAttribute>> resourceMap = resCache
        .getConfigAttributesFromCache();
    //要访问的URL
    String url = ((FilterInvocation) object).getRequestUrl();
    //查找匹配的ConfigAttribute
    Iterator<String> ite = resourceMap.keySet().iterator();
    while (ite.hasNext()) {
      String resURL = ite.next();
      if (pathMatcher.match(resURL, url)) {        
        Collection<ConfigAttribute> configAttrs = resourceMap.get(resURL);
        logger.debug("Matched config attribute '{}' founded for {}", configAttrs, resURL);
        return configAttrs; //有匹配的ConfigAttribute，则返回它们
      }
    }
    logger.debug("No resource match {}", url);
    return null;
  }

  @Override
  public Collection<ConfigAttribute> getAllConfigAttributes() {
    return null;
  }

  @Override
  public boolean supports(Class<?> clazz) {
    return true;
  }

}
