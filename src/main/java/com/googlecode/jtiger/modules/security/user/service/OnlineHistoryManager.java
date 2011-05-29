package com.googlecode.jtiger.modules.security.user.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.googlecode.jtiger.core.dao.support.Page;
import com.googlecode.jtiger.core.service.BaseGenericsManager;
import com.googlecode.jtiger.modules.security.user.model.OnlineHistory;
import com.googlecode.jtiger.modules.security.user.model.User;

@Service
public class OnlineHistoryManager extends BaseGenericsManager<OnlineHistory> {
  /**
   * 创建一条历史记录
   * @param user 谁登录？
   * @param loginTime 登录时间
   * @return 持久化之后的OnlineHistory
   */
  @Transactional
  public OnlineHistory create(User user) {
    OnlineHistory oh = new OnlineHistory();
    oh.setUser(user);
    oh.setLoginTime(new Date());
    
    return getDao().merge(oh);
  }
  
  /**
   * 退出登录时进行记录
   * @param id 登录记录ID
   * @param logoutTime 注销时间
   * @param duration 登录时长（毫秒）
   */
  @Transactional
  public OnlineHistory logout(User user) {
    OnlineHistory oh = getLastByUser(user);
    if(oh != null && oh.getLogoutTime() == null) {
      Date now = new Date();
      oh.setLogoutTime(now);
      oh.setDuration(now.getTime() - oh.getLoginTime().getTime());
      
      return getDao().merge(oh);
    }
    
    return oh;
  }
  
  /**
   * 查找某个用户的最后一次登录记录
   */
  public OnlineHistory getLastByUser(User user) {
    Assert.notNull(user);
    Assert.hasText(user.getId());
    
    user = getDao().load(User.class, user.getId());
    return getDao().get(OnlineHistory.class, user.getLastOhId());
  }
  
  /**
   * 列出某个用户的登录历史记录
   * @param user
   * @return
   */
  public Page listByUser(Page page, User user, Date begin, Date end) {
    Assert.notNull(user);
    Assert.hasText(user.getId());
    String hql = "from OnlineHistory o where o.user.id=?";
    
    List<Object> args = new ArrayList<Object>();
    args.add(user.getId());
    
    if(begin != null) {
      hql += " and o.loginTime>=?";
      args.add(begin);
    }
    
    if(end != null) {
      hql += " and o.loginTime<=?";
      args.add(end);
    }
    
    hql += " order by o.loginTime desc";
    
    return pageQuery(page, hql, args.toArray());
  }
}
