package com.googlecode.jtiger.core.taglibs;

import javax.servlet.ServletContext;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.googlecode.jtiger.core.webapp.context.ApplicationContextAwareObject;
/**
 * 能够“获取Spring管理的bean”的Taglib的基类.
 * @author Sam
 *
 */
@SuppressWarnings("serial")
public class BaseSpringAwareBodyTagSupport extends BodyTagSupport implements
    ApplicationContextAwareObject {
  /**
   * log of the class
   */
  protected Log log = LogFactory.getLog(getClass());
  
  /**
   * 从spring中获取一个bean.
   */
  public Object getBean(String beanName) {
    ServletContext servletCtx = pageContext.getServletContext();
    ApplicationContext ctx = WebApplicationContextUtils
        .getWebApplicationContext(servletCtx);
    return ctx.getBean(beanName);
  }
  
}
