package com.googlecode.jtiger.modules.security.listeners;

import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
/**
 * 
 * @author catstiger@gmail.com
 * @deprecated use AuthenticationSuccessHandlerEx instead.
 */
 @Deprecated
//@Component
public class AuthenticationSuccessApplicationListener implements ApplicationListener<AuthenticationSuccessEvent> {
   @Override
  public void onApplicationEvent(AuthenticationSuccessEvent event) {
    
      
  }

}
