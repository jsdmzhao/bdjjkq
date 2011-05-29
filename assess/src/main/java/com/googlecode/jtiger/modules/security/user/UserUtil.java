package com.googlecode.jtiger.modules.security.user;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.googlecode.jtiger.modules.security.user.model.User;
import com.googlecode.jtiger.modules.security.user.service.UserManager;

/**
 * 用户工具类
 * 
 * @author Sam Lee
 * 
 */
@Component
public class UserUtil {
  @Autowired
  private UserManager userManager;
  /**
   * 从HttpServletRequest中获得当前登录用户。返回的<code>User</code>
   * 并未关联Hibnerate的Session，如果需要获取其角色、部门等关联实体信息，则 必须重新与Session建立关联。
   * 
   * @param request
   *          给定<code>HttpServletRequest</code>对象
   * @return <code>User</code> or <code>null</code> if no user login.
   */
  public User getUser(final HttpServletRequest request) {
    User user = getPrincipal(request);
    
    if(user != null && StringUtils.isNotBlank(user.getId())) {
      user = userManager.get(user.getId());
    }
    return user;
  }
  
  public static User getPrincipal(final HttpServletRequest request) {
    User user = null;

    Principal principal = request.getUserPrincipal();
    if (principal != null) {
      if (principal instanceof Authentication) {
        Object p = ((Authentication) principal).getPrincipal();
        if (p instanceof User) {
          user = (User) p;
        }
      }
    } else {
      Object p = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      if (p instanceof User) {
        user = (User) p;
      }
    }
    
    return user;
  }

  /**
   * 私有构造器，防止实例化
   */
  private UserUtil() {
  }

}
