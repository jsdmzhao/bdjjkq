package com.googlecode.jtiger.modules.security.ext;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

public class FilterSecurityInterceptor extends AbstractSecurityInterceptor
    implements Filter {
  private static Logger logger = LoggerFactory.getLogger(FilterSecurityInterceptor.class);
  private FilterInvocationSecurityMetadataSource securityMetadataSource;
  

  @Override
  public void doFilter(ServletRequest request, ServletResponse response,
      FilterChain chain) throws IOException, ServletException {    
    logger.debug("Custom FilterSecurityInterceptor has execurted.");
    
    FilterInvocation fi = new FilterInvocation(request, response, chain);   
    invoke(fi); 
  }

  
  private void invoke(FilterInvocation fi) throws IOException, ServletException {
    InterceptorStatusToken token = super.beforeInvocation(fi);
    try {
      fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
    } finally {
      super.afterInvocation(token, null);
    }
  }


  @Override
  public Class<? extends Object> getSecureObjectClass() {    
    return FilterInvocation.class;
  }

  @Override
  public void destroy() {    
  }
  
  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
  }
  
  
  @Override
  public SecurityMetadataSource obtainSecurityMetadataSource() {    
    return this.securityMetadataSource;
  }

  /**
   * @return the securityMetadataSource
   */
  public FilterInvocationSecurityMetadataSource getSecurityMetadataSource() {
    return securityMetadataSource;
  }

  /**
   * @param securityMetadataSource the securityMetadataSource to set
   */
  public void setSecurityMetadataSource(
      FilterInvocationSecurityMetadataSource securityMetadataSource) {
    this.securityMetadataSource = securityMetadataSource;
  }

}
