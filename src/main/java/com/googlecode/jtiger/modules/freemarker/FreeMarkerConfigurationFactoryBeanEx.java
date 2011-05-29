package com.googlecode.jtiger.modules.freemarker;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;
import org.springframework.ui.freemarker.SpringTemplateLoader;

import freemarker.cache.FileTemplateLoader;
import freemarker.cache.MultiTemplateLoader;
import freemarker.cache.TemplateLoader;

/**
 * 覆盖了@{link FreeMarkerConfigurationFactoryBean#getTemplateLoaderForPath(String)}方法，
 * 使得templateLoaderPaths属性可以接受以classpath*:开头的变量，以批量匹配路径。例如：<br>
 * <pre>
 * &lt;property name="templateLoaderPaths"&gt;
 *           &lt;list&gt;
 *               &lt;value&gt;classpath*:jtiger/** /ftl/&lt;/value&gt;
 *               &lt;value&gt;classpath:ftl/&lt;/value&gt;
 *           &lt;/list&gt;
 *      &lt;/property&gt;
 * </pre>
 * @author catstiger@gmail.com
 *
 */
public class FreeMarkerConfigurationFactoryBeanEx extends FreeMarkerConfigurationFactoryBean {
  private static Logger logger = LoggerFactory.getLogger(FreeMarkerConfigurationFactoryBeanEx.class);
  private ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();


  @Override
  protected TemplateLoader getTemplateLoaderForPath(String templateLoaderPath) {
    if (templateLoaderPath.startsWith(ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX)) {
      if (isPreferFileSystemAccess()) {
        try {
          Resource[] paths = resourcePatternResolver.getResources(templateLoaderPath);
          if(paths != null) {
            List<TemplateLoader> templateLoaders = new ArrayList<TemplateLoader>(paths.length);
            for(Resource path : paths) {
              File file = path.getFile(); // will fail if not resolvable in the file
              templateLoaders.add(new FileTemplateLoader(file));
            }
            logger.debug("Get template loaders {}", templateLoaders.size());
            
            TemplateLoader[] loaders = templateLoaders.toArray(new TemplateLoader[]{});
            return new MultiTemplateLoader(loaders);
          }          
        } catch (IOException ex) {
          return new SpringTemplateLoader(getResourceLoader(), templateLoaderPath);
        }
      } 
    }
    return super.getTemplateLoaderForPath(templateLoaderPath);
  }
  
}
