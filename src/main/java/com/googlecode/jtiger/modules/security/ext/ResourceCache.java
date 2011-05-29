package com.googlecode.jtiger.modules.security.ext;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.access.ConfigAttribute;

public interface ResourceCache {
  public Map<String, Collection<ConfigAttribute>> getConfigAttributesFromCache();
  public void refresh();
}
