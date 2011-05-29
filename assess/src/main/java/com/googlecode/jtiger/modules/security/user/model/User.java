package com.googlecode.jtiger.modules.security.user.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.googlecode.jtiger.core.Constants;
import com.googlecode.jtiger.core.model.BasePicModel;
import com.googlecode.jtiger.core.util.DateUtil;
import com.googlecode.jtiger.modules.security.user.UserConstants;

/**
 * 用户表 The persistent class for the users database table.
 * @author BEA Workshop
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "users", uniqueConstraints = {})
public class User extends BasePicModel implements UserDetails, Serializable {

  /**
   * 用户名|昵称
   */
  private String name;

  /**
   * 登录ID
   */
  private String loginId;
  
  /**
   * 密码
   */
  private String password;

  /**
   * 确认密码
   */
  private String confirmPwd;

  /**
   * 电子邮件
   */
  private String email;
  
  /**
   * 关注次数
   */
  private Integer attention = 0;
  

  /**
   * 状态
   */

  private String status;

  /**
   * 用户类型
   */
  private String userType;

  /**
   * 用户级别
   */
  private String level;
  


  /**
   * 是否系统用户
   */
  private String isSys;

  /**
   * 用戶是否在线，0（缺省）表示不在线，1表示在线。
   */
  private String online;

  /**
   * 用户是否视频在线，0（缺省）表示否，1表示在线等待，2表示在线聊天
   */
  private String videoOnline;

  /**
   * 注册时间
   */
  private Date registTime;

  /**
   * 最后登录IP
   */
  private String lastLoginIp;

  /**
   * 最后登录时间
   */
  private Date lastLoginTime;

  /**
   * 登录次数
   */
  private Integer loginTimes = 0;
  
  /**
   * 最后一次登录历史的ID
   */
  private String lastOhId;
  
  /**
   * 在线时长
   */
  private Long howLong = 0L;

  /**
   * 用户描述
   */
  private String descn;

  /**
   * 用户可以共享的最多文件数
   */
  private Integer maxFiles;
  
  /**
   * 用户分数
   */
  private Integer point = UserConstants.DEFAULT_POINT;
  
  /**
   * 用户上传资源数
   */
  private Integer uploads = 0;
  
  /**
   * 用户头像
   */
  private String faceUrl;
  
  /**
   * 是否临时账户
   */
  private Boolean isTemp = false;
  
  /**
   * 临时密码
   */
  private String tempPwd;


  /**
   * 所具有的角色
   */
  private Set<Role> roles = new HashSet<Role>(0);
  
    
  /**
   * 机构类型名称
   */
  private transient String insType;

  /**
   * 缺省构造器
   */
  public User() {
  }

  public String getName() {
  	return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescn() {
    return this.descn;
  }

  public void setDescn(String descn) {
    this.descn = descn;
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Column(name = "last_login_ip")
  public String getLastLoginIp() {
    return this.lastLoginIp;
  }

  public void setLastLoginIp(String lastLoginIp) {
    this.lastLoginIp = lastLoginIp;
  }

  @Column(name = "last_login_time")
  public Date getLastLoginTime() {
    return this.lastLoginTime;
  }

  public void setLastLoginTime(Date lastLoginTime) {
    this.lastLoginTime = lastLoginTime;
  }

  @Column(name = "login_id")
  public String getLoginId() {
    return this.loginId;
  }

  public void setLoginId(String loginId) {
    this.loginId = loginId;
  }

  @Column(name = "login_times", columnDefinition="integer default 0")
  public Integer getLoginTimes() {
    return this.loginTimes;
  }

  public void setLoginTimes(Integer loginTimes) {
    this.loginTimes = loginTimes;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getStatus() {
    return this.status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  @Column(name = "user_type")
  public String getUserType() {
    return this.userType;
  }

  public void setUserType(String userType) {
    this.userType = userType;
  }
  @Column(name = "levels")
  public String getLevel() {
    return level;
  }

  public void setLevel(String level) {
    this.level = level;
  }


  @ManyToMany(targetEntity = Role.class, cascade = {}, fetch = FetchType.LAZY)
  @JoinTable(name = "user_role", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = { @JoinColumn(name = "role_id") })
  public Set<Role> getRoles() {
    return this.roles;
  }

  public void setRoles(Set<Role> roles) {
    this.roles = roles;
  }

  /**
   * @see Object#equals(Object)
   */
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    if (!(other instanceof User)) {
      return false;
    }
    User castOther = (User) other;
    return new EqualsBuilder().append(this.getId(), castOther.getId())
        .isEquals();
  }

  /**
   * @see Object#hashCode()
   */
  public int hashCode() {
    return new HashCodeBuilder().append(getId()).toHashCode();
  }

  /**
   * @see Object#toString()
   */
  public String toString() {
    return new ToStringBuilder(this).append("id", getId()).toString();
  }

  // Methods from UserDetails
  /**
   * 用户所具有的权限，可用通过角色-权限对应关系得到
   */
  private transient List<GrantedAuthority> authorities;

  /**
   * @see {@link UserDetails#getAuthorities()}
   */
  @Transient
  public List<GrantedAuthority> getAuthorities() {
    return this.authorities;
  }

  /**
   * @param authorities the authorities to set
   */
  public void setAuthorities(List<GrantedAuthority> authorities) {
    this.authorities = authorities;
  }

  /**
   * @see {@link UserDetails#getUsername()}
   */
  @Transient
  public String getUsername() {
    return this.loginId;
  }

  /**
   * @see {@link UserDetails#isAccountNonExpired()}
   */
  @Transient
  public boolean isAccountNonExpired() {
    return true;
  }

  /**
   * @see {@link UserDetails#isAccountNonLocked()}
   */
  @Transient
  public boolean isAccountNonLocked() {
    return true;
  }

  /**
   * {@link UserDetails#isCredentialsNonExpired()}
   */
  @Transient
  public boolean isCredentialsNonExpired() {
    return true;
  }

  /**
   * {@link UserDetails#isEnabled()}
   */
  @Transient
  public boolean isEnabled() {
    return StringUtils.equalsIgnoreCase(status, Constants.STATUS_AVAILABLE);
  }

  @Transient
  public String getConfirmPwd() {
    return confirmPwd;
  }

  public void setConfirmPwd(String confirmPwd) {
    this.confirmPwd = confirmPwd;
  }

  /**
   * 是否有角色？
   */
  @Transient
  public boolean getHasRoles() {
    return roles != null && roles.size() > 0;
  }
  
  @Transient
  public boolean hasRole(String role) {
    if(!getHasRoles()) {
      return false;
    }
    for(Role r : roles) {
      if(StringUtils.equals(r.getName(), role)) {
        return true;
      }
    }
    return false;
  }

  
  /**
   * @return the registTime
   */
  @Column(name = "regist_time")
  public Date getRegistTime() {
    return registTime;
  }

  /**
   * @param registTime the registTime to set
   */
  public void setRegistTime(Date registTime) {
    this.registTime = registTime;
  }

  /**
   * @return the isSys
   */
  @Column(name = "is_sys", columnDefinition = "char(1) default '0'")
  public String getIsSys() {
    return isSys;
  }

  /**
   * @param isSys the isSys to set
   */
  public void setIsSys(String isSys) {
    this.isSys = isSys;
  }

  @Column(name = "on_line", columnDefinition = "char(1) default '0'")
  public String getOnline() {
    return online;
  }

  public void setOnline(String online) {
    this.online = online;
  }

  @Column(name = "video_online", columnDefinition = "char(1) default '0'")
  public String getVideoOnline() {
    return videoOnline;
  }

  public void setVideoOnline(String videoOnline) {
    this.videoOnline = videoOnline;
  }

  /**
   * @return the maxFiles
   */
  @Column(name = "max_files", columnDefinition = "integer default 1")
  public Integer getMaxFiles() {
    return maxFiles;
  }

  /**
   * @param maxFiles the maxFiles to set
   */
  public void setMaxFiles(Integer maxFiles) {
    this.maxFiles = maxFiles;
  }

  /**
   * @return the pointer
   */
  @Column(columnDefinition="integer default 10")
  public Integer getPoint() {
    return point;
  }

  /**
   * @param pointer the pointer to set
   */
  public void setPoint(Integer pointer) {
    this.point = pointer;
  }

  /**
   * @return the uploads
   */
  @Column(columnDefinition="integer default 0")
  public Integer getUploads() {
    return uploads;
  }

  /**
   * @param uploads the uploads to set
   */
  public void setUploads(Integer uploads) {
    this.uploads = uploads;
  }

  /**
   * @return the faceUrl
   */
	@Column(name = "face_url")
  public String getFaceUrl() {
    return faceUrl;
  }

  /**
   * @param faceUrl the faceUrl to set
   */
  public void setFaceUrl(String faceUrl) {
    this.faceUrl = faceUrl;
  }

  /**
   * @return the nickname
   */
  @Transient
  public String getNickname() {
    return (StringUtils.isBlank(name)) ? loginId : name;
  }

  @Override
  @Transient
  public int getLW() { 
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  @Transient
  public int getLH() {
    throw new java.lang.UnsupportedOperationException();
  }

  @Override
  @Transient
  public int getMW() {
    
    return 120;
  }

  @Override
  @Transient
  public int getMH() {
    
    return 120;
  }

  @Override
  @Transient
  public int getSW() {
    
    return 50;
  }

  @Override
  @Transient
  public int getSH() {
    
    return 50;
  }

  /**
   * @return the attention
   */
  public Integer getAttention() {
    return attention;
  }

  /**
   * @param attention the attention to set
   */
  public void setAttention(Integer attention) {
    this.attention = attention;
  }

  /**
   * @return the insType
   */
  @Transient
  public String getInsType() {
    return insType;
  }

  /**
   * @param insType the insType to set
   */
  public void setInsType(String insType) {
    this.insType = insType;
  }

  /**
   * @return the howLong
   */
  public Long getHowLong() {
    return howLong;
  }

  /**
   * @param howLong the howLong to set
   */
  public void setHowLong(Long howLong) {
    this.howLong = howLong;
  }

  /**
   * @return the isTemp
   */
  @Column(columnDefinition = "SMALLINT default 0")
  public Boolean getIsTemp() {
    return isTemp;
  }

  /**
   * @param isTemp the isTemp to set
   */
  public void setIsTemp(Boolean isTemp) {
    this.isTemp = isTemp;
  }

  /**
   * @return the tempPwd
   */
  public String getTempPwd() {
    return tempPwd;
  }

  /**
   * @param tempPwd the tempPwd to set
   */
  public void setTempPwd(String tempPwd) {
    this.tempPwd = tempPwd;
  }

  /**
   * @return the onlineHistoryId
   */
  public String getLastOhId() {
    return lastOhId;
  }

  /**
   * @param onlineHistoryId the onlineHistoryId to set
   */
  public void setLastOhId(String onlineHistoryId) {
    this.lastOhId = onlineHistoryId;
  }
  
  @Transient
  public String getHowLongTxt() {
    return DateUtil.millisecond2String(howLong);
  }

}