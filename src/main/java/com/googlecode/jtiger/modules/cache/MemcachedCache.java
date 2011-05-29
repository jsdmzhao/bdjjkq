package com.googlecode.jtiger.modules.cache;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import net.spy.memcached.AddrUtil;
import net.spy.memcached.MemcachedClient;
import net.spy.memcached.transcoders.Transcoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

//@Component
public class MemcachedCache implements Cache<String>, InitializingBean {
  private static Logger logger = LoggerFactory.getLogger(MemcachedCache.class);
  
  @Autowired
  private MemcachedClient client;
  
  @Autowired
  private Transcoder<Object> transcoder;
  
  /**
   * Memcached主机字符串，用空格分隔。例如:server1:11211 server2:11211
   */
  @Value("${memcached.hosts}")
  private String hosts;
  
  /**
   * 异步处理Memcached返值的时候所设定的超时时间
   */
  @Value("${memcached.expiredSec}")
  private int expiredSec;
  
  @Override
  public void afterPropertiesSet() throws Exception {
    if (client == null) {
      client = new MemcachedClient(AddrUtil.getAddresses(hosts));
    }
  }  

  @Override
  public Object get(String key) {
    return client.get(key, transcoder);
  }  

  @Override
  public void put(String key, Object value, int exp) {
    client.set(key.toString(), exp, value, transcoder);
    //doFutureBoolean(future);
  }

  @Override
  public void remove(String key) {
   client.delete(key.toString());
    //doFutureBoolean(future);
  }
  
  @Override
  public void update(String key, Object value, int exp) {
    client.replace(key, exp, value, transcoder);
    //doFutureBoolean(future);
  }
  
  @SuppressWarnings("unused")
  private void doFutureBoolean(Future<Boolean> future) {
    try {
      Boolean result = future.get(expiredSec, TimeUnit.SECONDS);
      if(!result) {
        logger.warn("Failed to do someting with memcached.");
      }
    } catch(Exception e) {
      future.cancel(false);
      logger.warn("Exception occurs when doing someting with memcached, cause : {}", e.getMessage());
    }
  }
  
  @Override
  public int getTimeout() {    
    return 0;
  }
  
  @Override
  public void clear() {
    
  }

  @Override
  public void destroy() {
  }
  

  

}
