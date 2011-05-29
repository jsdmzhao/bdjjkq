package com.googlecode.jtiger.modules.security.user.service;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.spy.memcached.MemcachedClient;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.jtiger.core.Constants;
import com.googlecode.jtiger.core.dao.hibernate.BaseHibernateDao;
import com.googlecode.jtiger.modules.security.listeners.AuthenticationSuccessListener;
import com.googlecode.jtiger.modules.security.listeners.LogoutSuccessListener;
import com.googlecode.jtiger.modules.security.user.model.OnlineHistory;
import com.googlecode.jtiger.modules.security.user.model.User;

/**
 * 用户登录或注销后记录用户登录时间、次数、时长（注销后）、登录IP等信息。
 * AuthenticationSuccessHandlerEx和LogoutSuccessHanderEx分别调用#onLogIn和#onLogout方法。
 * @author catstiger@gmail.com
 *
 */
@Service
public class UserLoginAndLogoutListener implements AuthenticationSuccessListener, LogoutSuccessListener {
  private static Logger logger = LoggerFactory.getLogger(UserLoginAndLogoutListener.class);
  /**
   * 机构在线人数在Cache中的Key
   */
  public static final String KEY_INS_ONLINES = "__key_ins_onlines__";  
  
  /**
   * 总用户在线人数
   */
  public static final String KEY_USER_ONLINES = "_key_user_onlines__";
  
  @Autowired
  private BaseHibernateDao dao;
  @Autowired
  private OnlineHistoryManager ohMgr;

  @Autowired(required = false)
  private MemcachedClient cache;

  @Override
  @Transactional
  public void onLogin(HttpServletRequest request,
      HttpServletResponse response, final User user) {
    if(user == null || request == null) {
      return;
    }
    Session session = dao.getSessionFactory().openSession();
    try {
      User u = (User) session.get(User.class, user.getId());
      
      //记录用户登录历史
      OnlineHistory oh = ohMgr.create(user);
      
      Integer lt = (u.getLoginTimes() == null) ? 1 : u.getLoginTimes() + 1;
      //更新当前登录用户的登录时间和次数以及登录状态
      session.createQuery("update User u set u.lastLoginTime=?," +
      		"u.loginTimes=?,u.online='1',u.lastLoginIp=?,u.lastOhId=? where u.id=?")
      .setTimestamp(0, new Date()).setInteger(1, lt)
      .setString(2, request.getRemoteAddr()).setString(3, oh.getId())
      .setString(4, user.getId())
      .executeUpdate();
      
      //在线人数计数器
      onlineCounter(u, true);
    } finally {
      session.close();
    }    
  }
  
  @Override
  @Transactional  
  public void onLogout(HttpServletRequest request,
      HttpServletResponse response, User user) {
    Session session = dao.getSessionFactory().openSession();
    try {
      User u = (User) session.get(User.class, user.getId());
      //记录退出登录历史
      OnlineHistory oh = ohMgr.logout(user);
      //累计登录时长
      Long howLong = u.getHowLong();
      if(howLong == null) {
        howLong = 0L;
      }
      if(oh.getDuration() != null) {
        howLong += oh.getDuration();
      }
      
      //更新登录用户的登录时长和登录状态
      session.createQuery("update User u set u.howLong=?,u.online='0' where u.id=?")
      .setLong(0, howLong).setString(1, user.getId()).executeUpdate();
      //在线人数计数器
      onlineCounter(u, false);
    } finally {
      session.close();
    }
    logger.info("User '{}', logout", user.getLoginId());
  }
  
  
  
  /**
   * 在线人数计数器
   * @param user 登录用户
   * @param isOnline 是否在线
   */
  private void onlineCounter(User user, Boolean isOnline) {   
    if(cache == null) {
      logger.debug("No memcached avalible, can't calc online.");
      return;
    }
    Long total = (Long) cache.get(KEY_USER_ONLINES);
    if(isOnline) {
      //总在线人数      
      if(total == null) {
        total = getTotalOnlines();
      } else {
        total++;
      }      
      
    } else {
      //总在线人数      
      if(total == null) {
        total = Long.valueOf(getTotalOnlines());
      } else {
        total--;
      }      
    }
    if(total != null) {
      cache.set(KEY_USER_ONLINES, 21600, total); //缓存6小时
    }
   
    logger.debug("Online users {}", total);
  }
  
  /**
   * 从数据库中统计当前在线人数
   * @return
   */
  private Long getTotalOnlines() {
    return (Long) dao.findObject("select count(id) from User u where u.online=?", Constants.YES);
  } 

}
