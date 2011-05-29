package com.googlecode.jtiger.modules.security.user;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.googlecode.jtiger.core.Constants;
import com.googlecode.jtiger.modules.security.user.model.Role;

/**
 * 用户管理及相关模块的常量类
 * @author Sam Lee
 *
 */
public final class UserConstants {
  /**
   * 在Web目录下存放照片的路径
   */
  public static final String USER_PHOTO_PATH = "/uploaded/photo";
  /**
   * 权限操作－URL
   */
  public static final String PERMISSION_OPERATION_URL = "target_url";
  /**
   * 权限操作－函数
   */
  public static final String PERMISSION_OPERATION_FUNC = "target_function";

  /**
   * 用户类型－系统用户
   */
  public static final String USER_TYPE_SYS = "2";
  /**
   * 用户状态－未审核
   */
  public static final String USER_STATUS_UNUSABLE = "0";
  /**
   * 用户状态－可用
   */
  public static final String USER_STATUS_USABLE = "1";
  
  /**
   * 用户状态－待激活
   */
  public static final String USER_STATUS_UNENABLED = "2";
  
  /**
   * 性别常量，M-男性
   */
  public static final String GENT = "M";

  /**
   * 性别常量，F-女性
   */
  public static final String LADY = "F";
  
  /**
   * 学历，1-博士
   */
  public static final String USER_DEGREE_DOCTOR = "1";
  /**
   * 学历，2-硕士
   */
  public static final String USER_DEGREE_MASTER = "2";
  /**
   * 学历，3-学士
   */
  public static final String USER_DEGREE_BACHELOR = "3";
  /**
   * 学历，4-其他
   */
  public static final String USER_DEGREE_OTHERS = "4";
  
  /**
   * 用户单位性质 1-政府机构
   */
  public static final String USER_UNITKIND_GOV = "1";
  /**
   * 用户单位性质 2-科研机构
   */
  public static final String USER_UNITKIND_SCI = "2";
  /**
   * 用户单位性质 3-教育院校
   */
  public static final String USER_UNITKIND_EDU = "3";
  /**
   * 用户单位性质 4-商业公司
   */
  public static final String USER_UNITKIND_BUS = "4";
  /**
   * 用户单位性质 5-民间组织
   */
  public static final String USER_UNITKIND_POP = "5";
  /**
   * 用户单位性质 6-其他
   */
  public static final String USER_UNITKIND_OTH = "6";
  /**
  
  /**
   * 系统管理员角色
   */
  public static final String ROLE_ADMIN = "ROLE_ADMIN";
  
  
  private static Role sysRole(String roleName, String descn) {
    Role role = new Role();
    role.setName(roleName);
    role.setDescn(descn);
    role.setIsSys(Constants.STATUS_AVAILABLE); //标记为系统角色
    return role;
  }
  
  /**
   * 系统角色列表
   */
  public static final List<Role> SYS_ROLES = new ArrayList<Role>();
  
  static {
    SYS_ROLES.add(sysRole(ROLE_ADMIN, "系统管理员"));
  }
  

  /**
   * 注册用户省份Map
   */
  public static final List<String> PROVINCE_LIST = Collections
      .synchronizedList(new LinkedList<String>());
  static {
    PROVINCE_LIST.add("北京");
    PROVINCE_LIST.add("天津");
    PROVINCE_LIST.add("河北");
    PROVINCE_LIST.add("山西");
    PROVINCE_LIST.add("内蒙古");
    PROVINCE_LIST.add("辽宁");
    PROVINCE_LIST.add("新疆");
    PROVINCE_LIST.add("黑龙江");
    PROVINCE_LIST.add("上海");
    PROVINCE_LIST.add("江苏");
    PROVINCE_LIST.add("浙江");
    PROVINCE_LIST.add("安徽");
    PROVINCE_LIST.add("福建");
    PROVINCE_LIST.add("江西");
    PROVINCE_LIST.add("山东");
    PROVINCE_LIST.add("河南");
    PROVINCE_LIST.add("宁夏");
    PROVINCE_LIST.add("陕西");
    PROVINCE_LIST.add("广东");
    PROVINCE_LIST.add("广西");
    PROVINCE_LIST.add("海南");
    PROVINCE_LIST.add("四川");
    PROVINCE_LIST.add("甘肃");
    PROVINCE_LIST.add("云南");
    PROVINCE_LIST.add("湖南");
    PROVINCE_LIST.add("重庆");
    PROVINCE_LIST.add("湖北");
    PROVINCE_LIST.add("西藏");
    PROVINCE_LIST.add("青海");
    PROVINCE_LIST.add("贵州");
    PROVINCE_LIST.add("吉林");
    PROVINCE_LIST.add("香港");
    PROVINCE_LIST.add("澳门");
    PROVINCE_LIST.add("台湾");
  }
  /**
   * 权限操作列表
   */
  public static final Map<String, String> PERMISSION_OPERATIONS = 
    Collections.synchronizedMap(new HashMap<String, String>());
  static {
    PERMISSION_OPERATIONS.put(PERMISSION_OPERATION_FUNC, "函数权限");
    PERMISSION_OPERATIONS.put(PERMISSION_OPERATION_URL, "URL权限");
  }
  
  /**
   * 注册用户状态Map
   */
  public static final Map<String, String> USER_STATUS = 
    Collections.synchronizedMap(new HashMap<String, String>());
  static {
    USER_STATUS.put(USER_STATUS_UNUSABLE, "未审核");
    USER_STATUS.put(USER_STATUS_USABLE, "已通过");
    USER_STATUS.put(USER_STATUS_UNENABLED, "待激活");
  }
  
  /**
   * 性别常量Map
   */
  public static final Map<String, String> SEX_MAP = Collections
      .synchronizedMap(new LinkedHashMap<String, String>());
  static {
    SEX_MAP.put(GENT, "男");
    SEX_MAP.put(LADY, "女");
  }

  
  /**
   * 学历常量Map
   */
  public static final Map<String, String> DEGREE_MAP = Collections
      .synchronizedMap(new LinkedHashMap<String, String>());
  static {
    DEGREE_MAP.put(USER_DEGREE_DOCTOR, "博士");
    DEGREE_MAP.put(USER_DEGREE_MASTER, "硕士");
    DEGREE_MAP.put(USER_DEGREE_BACHELOR, "学士");
    DEGREE_MAP.put(USER_DEGREE_OTHERS, "其他");
  }
  
  /**
   * 用户单位性质常量Map
   */
  public static final Map<String, String> USER_UNITKIND_MAP = Collections
      .synchronizedMap(new LinkedHashMap<String, String>());
  static {
    USER_UNITKIND_MAP.put(USER_UNITKIND_GOV, "政府机构");
    USER_UNITKIND_MAP.put(USER_UNITKIND_SCI, "科研机构");
    USER_UNITKIND_MAP.put(USER_UNITKIND_EDU, "教育院校");
    USER_UNITKIND_MAP.put(USER_UNITKIND_BUS, "商业公司");
    USER_UNITKIND_MAP.put(USER_UNITKIND_POP, "民间组织");
    USER_UNITKIND_MAP.put(USER_UNITKIND_OTH, "其他...");
  }
  


  /**
   * 缺省密码，在修改的时候，如果输入缺省密码，则表示使用原来的密码。
   */
  public static final String DEFAULT_PASSWORD = "*********";
  
  /**
   * 缺省的库银
   */
  public static final Integer DEFAULT_POINT = 10;
  
  /**
   * 重置默认密码
   */
  public static final String RESET_PASSWORD = "88888888";
  /**
   * 防止实例化
   */
  private UserConstants() {    
  }
}
