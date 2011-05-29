package com.googlecode.jtiger.modules.security.listeners;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.googlecode.jtiger.modules.security.user.model.User;

/**
 * 
 * @author catstiger@gmail.com
 *
 */
public interface LogoutSuccessListener {
  /**
   * 当用户退出登录后，被自动调用，传入当前退出用户
   */
  public void onLogout(HttpServletRequest request, HttpServletResponse response, User user);
}
