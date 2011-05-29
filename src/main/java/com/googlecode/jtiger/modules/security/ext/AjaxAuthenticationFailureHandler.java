package com.googlecode.jtiger.modules.security.ext;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import com.googlecode.jtiger.core.util.RequestUtil;

/**
 * 用于处理Ajax登录错误信息
 * @author catstiger@gmail.com
 *
 */
public class AjaxAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
  private static Logger logger = LoggerFactory.getLogger(AjaxAuthenticationFailureHandler.class);

  @Override
  public void onAuthenticationFailure(HttpServletRequest request,
      HttpServletResponse response, AuthenticationException exception)
      throws IOException, ServletException {
    if(RequestUtil.isJsonRequest(request)) {
      logger.debug("Do Ajax Authentication Failure Handler'{}'", exception.getMessage());
      try {
        response.setContentType("application/x-json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(exception.getMessage());
      } catch (IOException e) {
        logger.error(e.getMessage(), e);
      }
    } else {    
      super.onAuthenticationFailure(request, response, exception);
    }
  }

}
