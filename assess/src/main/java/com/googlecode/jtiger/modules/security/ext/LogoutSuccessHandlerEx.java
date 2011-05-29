package com.googlecode.jtiger.modules.security.ext;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AbstractAuthenticationTargetUrlRequestHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.util.CollectionUtils;

import com.googlecode.jtiger.modules.security.listeners.LogoutSuccessListener;
import com.googlecode.jtiger.modules.security.user.model.User;

/**
 * 首先处理用户注销监听器事件，然后处理URL重定向等事宜
 * @author catstiger@gmail.com
 *
 */
public class LogoutSuccessHandlerEx extends AbstractAuthenticationTargetUrlRequestHandler implements
    LogoutSuccessHandler {
  @Autowired
  private List<LogoutSuccessListener> listeners;
  /**
   * @see AbstractAuthenticationTargetUrlRequestHandler#handle(HttpServletRequest,
   *      HttpServletResponse, Authentication)
   */
  @Override
  public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
      throws IOException, ServletException {
    if(authentication == null) {
      return;
    }
    
    Object object = authentication.getPrincipal();
    
    if(object != null && object instanceof User) {
      User user = (User) object;
      logger.debug("{}注销了。" + user.getLoginId());
      doListeners(request, response, user);
    }
    super.handle(request, response, authentication);
  }
  
  @Async
  private void doListeners(HttpServletRequest request,
      HttpServletResponse response, User user) {
    if(CollectionUtils.isEmpty(listeners)) {
      return;
    }
    for(LogoutSuccessListener listener : listeners) {
      listener.onLogout(request, response, user);
    }
  }

}
