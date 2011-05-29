package com.googlecode.jtiger.modules.security.ext;

import net.sf.ehcache.Ehcache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.googlecode.jtiger.modules.cache.CacheHelper;

@Component
public class EhCacheUserCache implements UserCache {
  @Autowired
  private Ehcache cache;
  
  @Override
  public UserDetails getUserFromCache(String username) {    
    return (UserDetails) CacheHelper.get(cache, username);
  }

  @Override
  public void putUserInCache(UserDetails user) {
    CacheHelper.put(cache, user.getUsername(), user);
  }

  @Override
  public void removeUserFromCache(String username) {
    cache.remove(username);
  }

}
