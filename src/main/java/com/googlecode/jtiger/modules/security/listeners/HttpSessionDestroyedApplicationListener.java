package com.googlecode.jtiger.modules.security.listeners;

import org.springframework.context.ApplicationListener;
import org.springframework.security.web.session.HttpSessionDestroyedEvent;

@Deprecated
//@Component
public class HttpSessionDestroyedApplicationListener implements ApplicationListener<HttpSessionDestroyedEvent> {

  @Override
  public void onApplicationEvent(HttpSessionDestroyedEvent arg0) {
   
    
  }
  
}
