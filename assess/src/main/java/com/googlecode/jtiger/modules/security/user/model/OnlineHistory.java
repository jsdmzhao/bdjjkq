package com.googlecode.jtiger.modules.security.user.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.googlecode.jtiger.core.model.BaseIdModel;
import com.googlecode.jtiger.core.util.DateUtil;

/**
 * 用户上线记录
 * @author catstiger
 *
 */
@Entity
@Table(name = "online_histories")
public class OnlineHistory extends BaseIdModel {

  private Date loginTime;
  
  private Date logoutTime;
  
  private Long duration;
  
  /**
   * 是否换算为积分
   */
  private Boolean isRec = false;
  
  private User user;

  /**
   * @return the loginTime
   */
  public Date getLoginTime() {
    return loginTime;
  }

  /**
   * @param loginTime the loginTime to set
   */
  public void setLoginTime(Date loginTime) {
    this.loginTime = loginTime;
  }

  /**
   * @return the logoutTime
   */
  public Date getLogoutTime() {
    return logoutTime;
  }

  /**
   * @param logoutTime the logoutTime to set
   */
  public void setLogoutTime(Date logoutTime) {
    this.logoutTime = logoutTime;
  }

  /**
   * @return the duration
   */
  public Long getDuration() {
    return duration;
  }

  /**
   * @param duration the duration to set
   */
  public void setDuration(Long duration) {
    this.duration = duration;
  }

  /**
   * @return the user
   */
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  public User getUser() {
    return user;
  }

  /**
   * @param user the user to set
   */
  public void setUser(User user) {
    this.user = user;
  }

  /**
   * @return the isRec
   */
  @Column(columnDefinition = "SMALLINT default 0")
  public Boolean getIsRec() {
    return isRec;
  }

  /**
   * @param isRec the isRec to set
   */
  public void setIsRec(Boolean isRec) {
    this.isRec = isRec;
  }
  
  @Transient
  public String getDurationTxt() {
    return DateUtil.millisecond2String(duration);
  }
  
}
