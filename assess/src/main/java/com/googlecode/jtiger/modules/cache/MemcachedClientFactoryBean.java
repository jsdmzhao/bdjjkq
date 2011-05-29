package com.googlecode.jtiger.modules.cache;

import java.net.InetSocketAddress;
import java.util.List;

import net.spy.memcached.AddrUtil;
import net.spy.memcached.MemcachedClient;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.util.StringUtils;

/**
 * 可以通过"host:port"串或者InetSocketAddress创建MemcachedClient对象。
 * 如果@{link {@link #getHosts()}不为空，则通过该属性创建，否则通过
 * @{link {@link #getInetAddresses()}属性创建
 * @author catstiger@gmail.com
 *
 */
public class MemcachedClientFactoryBean implements FactoryBean<MemcachedClient> {
  private static Logger logger = LoggerFactory.getLogger(MemcachedClientFactoryBean.class);
  private static final String DEFAULT_HOST = "localhost:11211";
  
  private MemcachedClient memcachedClient;
  private String hosts;
  private List<InetSocketAddress> inetAddresses;
  
  public MemcachedClient getObject() throws Exception {
    if(StringUtils.hasText(getHosts())) {
      memcachedClient =  new MemcachedClient(AddrUtil.getAddresses(getHosts()));
    } else if(CollectionUtils.isNotEmpty(inetAddresses)) {
      memcachedClient = new MemcachedClient(inetAddresses);
    } else {
      memcachedClient = new MemcachedClient(AddrUtil.getAddresses(DEFAULT_HOST));
    }
    return memcachedClient;
  }
  
  public void destroy() {
    if(memcachedClient != null) {
      memcachedClient.shutdown();   
      logger.info("Memcached shutdown");
    }
  }

  @Override
  public Class<?> getObjectType() {
   return MemcachedClient.class;
  }

  @Override
  public boolean isSingleton() {
    return true;
  }

  public String getHosts() {
    return hosts;
  }
  
  /**
   * 空格分隔的host:port串，例如server1:11211 server2:11211
   */
  public void setHosts(String hosts) {
    this.hosts = hosts;
  }
  
  public List<InetSocketAddress> getInetAddresses() {
    return inetAddresses;
  }

  public void setInetAddresses(List<InetSocketAddress> inetAddresses) {
    this.inetAddresses = inetAddresses;
  }

}
