package com.googlecode.jtiger.modules.security.ext;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.util.CollectionUtils;

import com.googlecode.jtiger.modules.security.listeners.AuthenticationSuccessListener;
import com.googlecode.jtiger.modules.security.user.model.User;

/**
 * 处理登录成功，首先响应登录成功事件，然后再处理URL重定向等事宜。
 * @author catstiger@gmail.com
 *
 */
public class AuthenticationSuccessHandlerEx extends SavedRequestAwareAuthenticationSuccessHandler {
  private static Logger logger = LoggerFactory.getLogger(AuthenticationSuccessHandlerEx.class);
  
  @Autowired
  private List<AuthenticationSuccessListener> listeners;
  /**
   * 登录成功，响应登录成功的事件
   */
  @Override
  public void onAuthenticationSuccess(HttpServletRequest request,
      HttpServletResponse response, Authentication authentication)
      throws ServletException, IOException {
    if(authentication == null) {
      return;
    }
    Object object = authentication.getPrincipal();
    
    if(object != null && object instanceof User) {
      User user = (User) object;
      logger.debug("{}登录成功。", user.getLoginId());
      doListeners(request, response, user);
    }
    
    super.onAuthenticationSuccess(request, response, authentication);
  }
  
  @Async
  private void doListeners(HttpServletRequest request,
      HttpServletResponse response, User user) {
    if(CollectionUtils.isEmpty(listeners)) {
      return;
    }
    for(AuthenticationSuccessListener listener : listeners) {
      listener.onLogin(request, response, user);
    }
  }
  
  
}
