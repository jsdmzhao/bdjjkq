package com.googlecode.jtiger.modules.ecside.util;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.googlecode.jtiger.core.util.WebMockUtil;
import com.googlecode.jtiger.modules.ecside.core.TableModel;
import com.googlecode.jtiger.modules.ecside.core.context.WebContext;

/**
 * 
 * @author catstiger@gmail.com
 *
 */
public class TableModelUtils {
  public static ApplicationContext getApplicationContext(TableModel model) {
    WebContext webCtx = model.getContext();
    Object ctx = webCtx.getContextObject();
    if(ctx instanceof PageContext) {
      ServletContext servletCtx = ((PageContext) ctx).getServletContext();
      return WebApplicationContextUtils.getWebApplicationContext(servletCtx);
    } else if(ctx instanceof HttpServletRequest) {
      ServletContext servletCtx = ((HttpServletRequest) ctx).getSession().getServletContext();
      return WebApplicationContextUtils.getWebApplicationContext(servletCtx);
    } else {
      return new ClassPathXmlApplicationContext(new String[]{
          "META-INF/spring/applicationContext.xml"
      });
    }
  }
  
  public static HttpServletRequest getRequest(TableModel model) {
    WebContext webCtx = model.getContext();
    Object ctx = webCtx.getContextObject();
    if(ctx instanceof PageContext) {
      return (HttpServletRequest) ((PageContext) ctx).getRequest();      
    } else if(ctx instanceof HttpServletRequest) {
      return ((HttpServletRequest) ctx);
    } else {
      return WebMockUtil.getHttpServletRequest();
    }
  }
}
