package com.googlecode.jtiger.modules.security.ext;

import java.util.Collection;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

/** 
 * http://blog.csdn.net/remote_roamer/archive/2010/07/05/5713777.aspx
 * @author sam
 *
 */
@Service
public class AccessDecisionManagerEx implements AccessDecisionManager {
  private static Logger logger = LoggerFactory.getLogger(AccessDecisionManagerEx.class);
  
  @Override
  public void decide(Authentication auth, Object object,
      Collection<ConfigAttribute> configAttributes)
      throws AccessDeniedException, InsufficientAuthenticationException {
    if (configAttributes == null) {
      logger.debug("No config attributes match the url {}", object.toString());
    }

    Iterator<ConfigAttribute> ite = configAttributes.iterator();
    while (ite.hasNext()) {
      ConfigAttribute ca = ite.next();
      String needRole = ((SecurityConfig) ca).getAttribute();
      for (GrantedAuthority ga : auth.getAuthorities()) {
        if (needRole.equals(ga.getAuthority())) { // ga is user's role.
          logger.debug("Needed role is {}, user's role is:{}，it's matched.", needRole, ga.getAuthority());
          return;
        }
      }
    }
    
    logger.debug("{} has no permission to accessing {}", auth.getPrincipal(), object.toString());
    throw new AccessDeniedException("没有权限");
  }

  @Override
  public boolean supports(ConfigAttribute cfgAttr) {
    return true;
  }

  @Override
  public boolean supports(Class<?> clazz) {
    return true;
  }

}
