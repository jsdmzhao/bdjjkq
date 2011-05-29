package com.googlecode.jtiger.core.webapp.i18n;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.Resource;
import org.springframework.util.ReflectionUtils;

/**
 * 扩展@{ReloadableResourceBundleMessageSource}，可以通过classpath*:或者/WEB-INF/**的形式使用多个
 * properties文件，这样更有利于模块的划分
 * @author catstiger@gmail.com
 *
 */
public class ReloadableResourceBundleMessageSourceEx extends ReloadableResourceBundleMessageSource 
  implements InitializingBean {
  private static Logger logger = LoggerFactory.getLogger(ReloadableResourceBundleMessageSourceEx.class);
  
  public static final String PROPERTIES_SUFFIX = "/messages";
  private static final String ROOT = "WEB-INF/";
  
  
  @Value("/WEB-INF/**/messages*.properties")
  private Resource[] bundleResources;

  @Override
  public void afterPropertiesSet() throws Exception {
    if(bundleResources != null) {
      Set<String> resources = new HashSet<String>(bundleResources.length);
      //将每一个资源文件转换为符合ResourceBundle风格的格式
      for(int i = 0; i < bundleResources.length; i++) {
        String path = bundleResources[i].getURI().getPath();
        path = path.substring(path.indexOf(ROOT), path.length());
        path = path.substring(0, path.lastIndexOf("/"));
        path += PROPERTIES_SUFFIX;
        resources.add(path);
        logger.debug("load bundle message [{}]", path);
      }
      //取得私有的field
      Field field = ReflectionUtils.findField(ReloadableResourceBundleMessageSource.class, "basenames");
      ReflectionUtils.makeAccessible(field);
      String []basenames = (String[]) ReflectionUtils.getField(field, this);
            
      basenames = (String[]) ArrayUtils.addAll(basenames, resources.toArray());
      super.setBasenames(basenames);
      //ReflectionUtils.setField(field, this, basenames);
    }
  }

  public void setBundleResources(Resource[] bundleResources) {
    this.bundleResources = bundleResources;
  }

}
