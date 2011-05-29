package com.googlecode.jtiger.modules.security.user.service.init;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.RandomStringUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.jtiger.core.Constants;
import com.googlecode.jtiger.modules.security.user.UserConstants;
import com.googlecode.jtiger.modules.security.user.model.Role;
import com.googlecode.jtiger.modules.security.user.model.User;

/**
 * 初始化工具类，Spring在启动后会自动执行init方法
 * 
 */
@SuppressWarnings("rawtypes")
@Service
@Lazy(false)
public class SysInitializer {

  protected Logger logger = LoggerFactory.getLogger(getClass());
  
  /**
   * 缺省的账号
   */
  public static final String ADMIN = "admin";
  
  /**
   * 缺省的密码
   */
  public static final String DEFAULT_PWD = "manager";

  @Autowired(required = true)
  private SessionFactory sessionFactory;
  
  private PasswordEncoder passwordEncoder;

  private String adminPwd = DEFAULT_PWD;

  private String adminName;
  
  private String adminDesc;

  private String adminEmail = getRandomEmail();
  
  public static final String DEFAULT_EMAIL_SUBFIX = "@gmail.com";
  
  @PostConstruct
  @Transactional
  public void init() {
    logger.info("系统正在初始化...");
    Session session = sessionFactory.openSession();
    Transaction tx = session.beginTransaction();
    Role adminRole = null;
    try {
      // 初始化角色
      for (Role role : UserConstants.SYS_ROLES) {
        if(!hasSysRole(session, role.getName())){
          session.save(role);
        }
        //记录ROLE_ADMIN
        if(UserConstants.ROLE_ADMIN.equals(role.getName())) {
          adminRole = role;
        }
      }
      
      // 初始化系统管理员
      if (!hasAdmin(session)) {
        User admin = new User();
        admin.setLoginId(ADMIN);
        admin.setPassword(passwordEncoder.encodePassword(adminPwd, null));
        admin.setName(adminName);
        admin.setDescn(adminDesc);
        admin.setEmail(adminEmail);
        //是否可用：可用
        admin.setStatus(Constants.STATUS_AVAILABLE);
        //用户类型：后台用户
        admin.setUserType(UserConstants.USER_TYPE_SYS);
        //是否系统用户：是
        admin.setIsSys(Constants.STATUS_AVAILABLE);
        //分配角色
        admin.getRoles().add(adminRole);
        adminRole.getUsers().add(admin);
        
        session.save(admin);
      }
      
      //重置所有用户在线状态，避免因当机造成的脏数据
      //注释原因：集群状态下不能这样
      //session.createQuery("update User u set u.online='0', u.videoOnline='0'").executeUpdate();
    } finally {
      session.flush();
      tx.commit();
      session.close();
    }
  }
  
  /**
   * 是否已经有admin帐户
   */
  private boolean hasAdmin(Session session) {
    List list = session.createQuery("from User u where u.loginId='admin'")
        .list();
    return list != null && list.size() > 0;
  }
  /**
   * 是否已经有系统角色
   */
  private boolean hasSysRole(Session session, String roleName) {
    List list = session.createQuery("from Role r where r.isSys=? and r.name=?")
    .setString(0, Constants.STATUS_AVAILABLE)
    .setString(1, roleName).list();
    return list != null && list.size() > 0;
  }
  
  /**
   * 得到一个随机的电子邮件用户，用于初始化用户电子邮件，邮件后缀用{@link #DEFAULT_EMAIL_SUBFIX}
   */
  private static String getRandomEmail() {
    return new StringBuffer(20).append(RandomStringUtils.randomNumeric(6)).append(
        DEFAULT_EMAIL_SUBFIX).toString();
  }
  
  @Autowired
  public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
    this.passwordEncoder = passwordEncoder;
  }

  public void setAdminPwd(String adminPwd) {
    this.adminPwd = adminPwd;
  }

  public void setAdminName(String adminName) {
    this.adminName = adminName;
  }
  
  public void setAdminDesc(String adminDesc) {
    this.adminDesc = adminDesc;
  }
  
  public void setAdminEmail(String adminEmail) {
    this.adminEmail = adminEmail;
  }
}
