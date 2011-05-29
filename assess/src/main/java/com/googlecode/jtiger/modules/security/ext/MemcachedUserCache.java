package com.googlecode.jtiger.modules.security.ext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetails;

import com.googlecode.jtiger.modules.cache.Cache;

//@Component
public class MemcachedUserCache implements UserCache {
  @Autowired
  private Cache<String> cache;
  
  @Autowired(required = false)
  @Value("${usercache.timeoutSec}")
  private Integer timeoutSeconds = 30 * 60;
  
  @Override
  public UserDetails getUserFromCache(String username) {
    return (UserDetails) cache.get(username);
  }

  @Override
  public void putUserInCache(UserDetails user) {
    cache.put(user.getUsername(), user, timeoutSeconds);
  }

  @Override
  public void removeUserFromCache(String username) {
    cache.remove(username);
  }

}
