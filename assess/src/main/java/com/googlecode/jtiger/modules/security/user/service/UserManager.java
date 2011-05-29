package com.googlecode.jtiger.modules.security.user.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.googlecode.jtiger.core.ApplicationException;
import com.googlecode.jtiger.core.Constants;
import com.googlecode.jtiger.core.service.BaseGenericsManager;
import com.googlecode.jtiger.core.util.UUIDHex;
import com.googlecode.jtiger.core.util.ValidationUtil;
import com.googlecode.jtiger.modules.security.user.UserConstants;
import com.googlecode.jtiger.modules.security.user.model.Role;
import com.googlecode.jtiger.modules.security.user.model.User;

/**
 * 用户管理类
 * @author Sam Lee
 *
 */
@SuppressWarnings({"unchecked", "rawtypes"})
@Service
public class UserManager extends BaseGenericsManager<User> implements UserDetailsService {
  /**
   * Admin账户，不能删除。
   */
  public static final String ADMIN_USER = "admin";
  
  @Autowired
  private UserCache userCache;
  /**
   * 用于加密密码
   */
  private PasswordEncoder passwordEncoder;
  /**
   * JdbcTemplate，用户获取密码
   */
  private JdbcTemplate jdbcTemplate;

   /**
     * 根据登录名和密码得到<code>User</code>
     * @param loginId 登录名
     * @param password 密码
     * @return Instance of <code>User</code> or null.
     */
  public User getUser(String loginId, String password) {
    List<User> users = getDao().query(
        "from User user where user.loginId=? and user.password=?",
        new Object[] { loginId, password });
    if (users.isEmpty()) {
      return null;
    }
    return users.get(0);
  }
   /**
     * 根据用户登录名获得<code>User</code>对象
     * @param loginId 登录ID
     * @return Instance of <code>User</code> or null.
     */
   public User getUser(String loginId) {
     List<User> users = getDao().query("from User user where user.loginId=?", 
         loginId);
     if (users.isEmpty()) {
       return null;
     }
     return users.get(0);
   }
   /**
     * 更新用户的登录时间（当前时间）和IP地址
     * @param user 给定用户
     * @param loginIp 给定用户登录所在IP
     * @deprecated use {@link DefaultSignOnListener} instead
     */
   @Transactional
   public void updateLoginInformation(User user, String loginIp) {
     if (user == null || user.getId() == null) {
       logger.error("No user login, return only.");
       return;
     }
     
     user.setLastLoginIp(loginIp);
     user.setLastLoginTime(new Date());
     if (user.getLoginTimes() == null) {
       user.setLoginTimes(1);
     } else {
       user.setLoginTimes(user.getLoginTimes().intValue() + 1);
     }
     
     update(user);
   }
   
   /**
    * 更新用户最后登录IP
    */
   @Transactional
   public void updateLastLoginIp(String loginId, String ip) {
     Assert.notNull(loginId);
     User user = getUser(loginId);
     if(user != null) {
       user.setLastLoginIp(ip);
       getDao().merge(user);
     }
   }
   
   /**
    * 更新用户的online字段，在用户登录或者退出的时候被调用   
    * @param user 被更新的用户
    * @param isOnline <code>true</code>表示在线，<code>false</code>表示不在线
    */
   @Transactional
   public void setUserOnline(User user, boolean isOnline) {
     user = get(user.getId());
	   user.setOnline((isOnline) ? Constants.YES : Constants.NO);
	   logger.info("用户{}在线状态更新为{}", user.getLoginId(), user.getOnline());
	   getDao().merge(user);
   }
   
   /**
    * 判断某个用户是否在线
    * @param user 用户实体
    * @return 如果在线(online=1),返回true
    */
   public boolean isUserOnline(User user) {
     if(user == null || user.getId() == null) {
       logger.warn("用户对象为null或者id为null，无法判断用户是否在线。");
       return false;
     }
     user = get(user.getId());
     return Constants.YES.equals(user.getOnline());
   }
   
   /**
    * 根据用户类型，得到当前登录用户在线列表
    * @param userType 用户类型。
    * @see {@link UserConstants#USER_TYPE_SYS}
    * @see {@link UserConstants#USER_TYPE_MEMVER}
    * @see {@link UserConstants#USER_TYPE_CORP}
    * @return
    */
   public List<User> getOnlines(String userType) {
     return getDao().query("from User u where u.type=? and u.online=?", userType, Constants.YES);
   }

   
   /**
    * 更新用户积分和数量
    */
   public void updatePoint(Integer point, Integer userId) {
     Session session = getDao().getSessionFactory().getCurrentSession();
     session.createQuery("update User u set u.point = u.point + ? where u.id=?")
     .setInteger(0, point).setInteger(1, userId).executeUpdate();     
     
   }
   
   /**
    * 为用户上传文件数+1
    */
   public void updateUploads(Integer userId) {
     Session session = getDao().getSessionFactory().getCurrentSession();
     session.createQuery("update User u set u.uploads = u.uploads + 1 where u.id=?")
     .setInteger(0, userId).executeUpdate();     
   }
   
  /**
   * @see BaseManager#save(java.lang.Object)
   */
  @Override
  @Transactional
  public void save(User user) {
    save(user, true);
  }
  /**
   * 保存用户信息，并根据下面的情况决定是否加密密码:<br><pre>
   * <ul>
   *    <li>encodePassword属性不得为null.</li>
   *    <li>如果是新建用户。</li>
   *    <li>如果是修改用户，并且输入的密码为空字符串或UserConstants.DEFAULT_PASSWORD。
   *    此时使用原来的密码</li>
   * </ul>
   * </pre>
   * 
   * @param user <code>User</code> to save.
   * @param encodePassword true if encode password.
   */
  @Transactional
  public void save(User user, boolean encodePassword) {    
    /*if (user.getId() == null) { //新建用户的状态为正常
      user.setStatus(Constants.STATUS_AVAILABLE);
    }*/
    
    User oldUser = new User(); //用于同步缓存
    oldUser.setLoginId(user.getLoginId());
    oldUser.setId(user.getId());
    //验证重复的登录名
    if(getDao().exists(oldUser, new String[]{"loginId"})) {
      throw new ApplicationException("您输入的登录名'" 
          + oldUser.getLoginId() + "'已经存在.");
    }
    //验证重复的email
    if(getDao().exists(user, new String[]{"email"})) {
      throw new ApplicationException("您的E-mail地址已经注册了,请更换一个.");
    }
    //如果是修改，并且输入的密码为空或UserConstants.DEFAULT_PASSWORD，则表示使用原来的密码
    if (user.getId() != null
        && (user.getPassword().equals(UserConstants.DEFAULT_PASSWORD) 
            || StringUtils.isBlank(user.getPassword()))) {
      String password = getOldPassword(user.getId());
      logger.debug("Use old password '{}'", password);
      user.setPassword(password);
      //解决a different object with the same identifier value 
      //was already associated with the session的问题
      getDao().evict(get(user.getId()));
      getDao().getHibernateTemplate().update(user);
    } else {
      if (passwordEncoder != null && encodePassword) { // 密码加密
        user.setPassword(passwordEncoder.encodePassword(user.getPassword(),
            null));
      }
      user.setStatus(Constants.STATUS_AVAILABLE);
      user.setRegistTime(new Date());
      getDao().saveOrUpdate(user);
    }
    userCache.removeUserFromCache(oldUser.getUsername());
    userCache.putUserInCache(user);
  }
  
  /**
   * 根据用户名更新用户的密码.
   * @param newPwd 
   * @throws ApplicationException 如果给定的emailAddr不存在.
   */
  @Transactional
  public void updatePasswordByEmail(String emailAddr, String loginId, String newPwd) {
    Assert.hasText(emailAddr, "E-Mail must not be empty.");
    
    User user = findObject("from User u where u.loginId=?", loginId);
    if(user == null) {
      throw new ApplicationException("用户名''" + loginId + "不存在");
    }
    if(!StringUtils.equalsIgnoreCase(emailAddr, user.getEmail())) {
      throw new ApplicationException("注册邮箱地址不匹配.");
    }
    user.setPassword(newPwd);
    save(user, true);
    
    //同步缓存
    userCache.removeUserFromCache(user.getLoginId());
  }
  /**
   * @see BaseManager#remove(java.lang.Object)
   */
  @Override
  @Transactional
  public void remove(User user) {
    if (Constants.STATUS_UNAVAILABLE.equals(user.getStatus())) {
      return;
    }
    //确保LoginID存在
    if(StringUtils.isBlank(user.getLoginId())) {
      user = get(user.getId());
    }
    //确保不能删除（禁用）Admin账户.
    if(ADMIN_USER.equals(user.getLoginId())) {
      logger.warn("不能删除或禁用admin账户，这是一个危险的动作哦。");
      return;
    }
    user.setStatus(Constants.STATUS_UNAVAILABLE);
    
    //userCache.removeUserFromCache(user.getLoginId());
  }
  
  /**
   * 启用用户
   * @param user
   */
  @Transactional
  public void unsealUser(User user) {
    if (Constants.STATUS_AVAILABLE.equals(user.getStatus())) {
      return;
    }
    user.setStatus(Constants.STATUS_AVAILABLE);    
    //同步缓存
    //userCache.removeUserFromCache(user.getLoginId());
  }
  
  /**
   * 更改用户状态
   * @param user
   */
  @Transactional
  public void updateStatus(User user) {
  	//保存用户信息
  	getDao().save(user);
    //同步缓存
    //userCache.removeUserFromCache(user.getLoginId());
  }

  
  /**
   * 返回指定UserId的密码
   */
  private String getOldPassword(String userId) {
    return (String) jdbcTemplate.query("select password from users where id=?",
        new Object[] { userId }, new ResultSetExtractor() {

          public Object extractData(ResultSet rs) throws SQLException {
            rs.next();
            return rs.getString(1);
          }

        });
  }
  /**
   * 修改密码
   * @param model 封装密码的User对象 
   * @param oldPassword 旧的密码
   * @throws Exception 
   */
  @Transactional
  public void changePassword(User model, String oldPassword) { 
    //获得加密后的密码
    String encodePasswrod = passwordEncoder.encodePassword(oldPassword, null);
    //判断输入的原密码和数据库中的原密码是否一致
    if (encodePasswrod.equals(getOldPassword(model.getId()))) {
      User user = getDao().get(User.class, model.getId());
      //设置新密码
      user.setPassword(passwordEncoder.encodePassword(
        model.getPassword(), null));
      getDao().getHibernateTemplate().update(user);
      //同步缓存
      userCache.removeUserFromCache(user.getLoginId());
    } else {
      throw new ApplicationException("旧密码不正确!");
    }
  }
  
  /**
   * 重置密码
   */
  @Transactional
  public void resetPassword(User model) {
  	User user = getDao().get(User.class, model.getId());
  	//设置新密码
    user.setPassword(passwordEncoder.encodePassword(
      model.getPassword(), null));
    getDao().getHibernateTemplate().update(user);
    //同步缓存
    userCache.removeUserFromCache(user.getLoginId());
  }
  
  /**
   * 管理员删除注册用户
   */
  @Transactional
  public void removeUser(User user) {
    //确保LoginID存在
    if(StringUtils.isBlank(user.getLoginId())) {
      user = get(user.getId());
    }
    //确保不能删除（禁用）Admin账户.
    if("admin".equals(user.getLoginId())) {
      logger.warn("不能删除或禁用admin账户，这是一个危险的动作哦。");
      return;
    }
   
    getDao().delete(user);
    //同步缓存
    //userCache.removeUserFromCache(user.getLoginId());
  }
  
  /**
   * 前台注册用户修改个人信息
   * @see BaseManager#update(java.lang.Object)
   */
  @Override
  @Transactional
  public void update(User user) {
    if(user != null) {
      getDao().update(user);
    }
    //同步缓存
    //userCache.removeUserFromCache(user.getLoginId());
  }
  
  /**
   * 更新用户昵称和头像
   */
  @Transactional
  public void updateNicknameAndFace(String nickname, String faceUri, String userId) {
    if((StringUtils.isBlank(nickname) && StringUtils.isBlank(faceUri)) ||
        StringUtils.isBlank(userId)) {
      return;
    }
    Session session = getDao().getSessionFactory().getCurrentSession();
    StringBuffer hql = new StringBuffer(200).append("update User u set u.loginId=u.loginId");
    List<String> args = new ArrayList<String>();
    if(StringUtils.isNotBlank(nickname)) {
      hql.append(",u.name=?");
      args.add(nickname);
    }
    
    if(StringUtils.isNotBlank(faceUri)) {
      hql.append(",u.faceUrl=?");
      args.add(faceUri);
    }
    
    hql.append(" where u.id=?");
    args.add(userId);
    
    Query query = session.createQuery(hql.toString());
    for(int i = 0; i < args.size(); i++) {
      query.setString(i, args.get(i));
    }
    query.executeUpdate();
  }
  
  /**
   * 得到loginId为{@link #ADMIN_USER}账户.
   * @return Instance of {@code User}
   * @throws NoSuchUserException 如果{@link #ADMIN_USER}账户不存在
   * @throws InvalidUserEmailException 如果{@link #ADMIN_USER}账户的email不合法
   */
  public User getAdmin() throws NoSuchUserException, InvalidUserEmailException {
    User admin = getUser(ADMIN_USER);
    if(admin == null) {
      throw new NoSuchUserException("admin 账户不存在.");
    }
    if(!ValidationUtil.isValidEmail(admin.getEmail())) {
      logger.warn("admin 账户电子邮件不合法-" + admin.getEmail());
    }
    
    return admin;
  }

  /**
   * 判断一个用户是否拥有某种角色
   */
  public Boolean hasRole(User user, String roleName) {
    Assert.notNull(user);
    Assert.notNull(user.getId());    
  
    List list = query("select r.id from Role r inner join r.users u where r.name=? and u.id=?", 
        roleName, user.getId());
    return !CollectionUtils.isEmpty(list);
  }
  /**
   * @param passwordEncoder the passwordEncoder to set
   */
  @Autowired
  public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
    this.passwordEncoder = passwordEncoder;
  }
  @Autowired
  public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
    User user = (User) userCache.getUserFromCache(username); //从缓存中获得用户
    User cloneUser = new User();
    //如果缓存中没有，或者用户未被授权，则重新加载
    if (user == null || CollectionUtils.isEmpty(user.getAuthorities())) { 
      user = findObject("from User u where u.loginId=? and u.status=?",
          username, Constants.STATUS_AVAILABLE);
      if(user == null) {
        throw new UsernameNotFoundException(username + " not found.");
      }
      Set<Role> roles = user.getRoles();
      List<GrantedAuthority> auths = new ArrayList<GrantedAuthority>(roles.size());
      for (Role role : roles) {
        auths.add(new GrantedAuthorityImpl(role.getName()));
        logger.debug("Auth of user '{}' is '{}'", user.getLoginId(), role.getName());
      }
      user.setAuthorities(auths);
      //ReflectUtil.copyProperties(cloneUser, user, new String[]{"id", "loginId", "password", "status"});
      cloneUser.setId(user.getId());
      cloneUser.setPassword(user.getPassword());
      cloneUser.setStatus(user.getStatus());
      cloneUser.setLoginId(user.getLoginId());
      cloneUser.setAuthorities(auths);
    }
    //同步缓存
    userCache.putUserInCache(cloneUser);
    return cloneUser;
  }
  
  /**
   * 生成一个随机的LoginId
   * @minLen LoginId的最小长度
   */
  public String randomLoginId(int minLen) {
    User user = new User();
    String id = null;
    do {
      int hash = UUIDHex.gen().hashCode();
      if (hash < 0) {
        hash = -hash;
      }
      id = String.valueOf(hash);
      //用户名长度必须大于等于minLen
      int len = 0;
      do {
        len = RandomUtils.nextInt(10);
      } while(len < minLen);
      
      if (id.length() > len) {
        id = id.substring(id.length() - len);
      }
      user.setLoginId(id);
      logger.debug(id);      
    } while (getDao().exists(user, "loginId"));
    
    return id;
  }
  
}
