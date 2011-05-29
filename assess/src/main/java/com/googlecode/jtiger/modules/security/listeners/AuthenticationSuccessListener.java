package com.googlecode.jtiger.modules.security.listeners;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.googlecode.jtiger.modules.security.user.model.User;

/**
 * 当用户登录成功时，{@link AuthenticationSuccessHandlerEx}将会
 * 自动调用所有<code>LoginSuccessListener</code>的实现类的{@link #onLogin(User)}
 * 方法。
 * 
 * @author catstiger@gmail.com
 *
 */
public interface AuthenticationSuccessListener {
  /**
   * 当用户登录后，被自动调用，传入当前登陆用户。
   */
  public void onLogin(HttpServletRequest request,
      HttpServletResponse response,User user);  
  
}
