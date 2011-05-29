package com.googlecode.jtiger.core;

import java.util.ResourceBundle;

import com.googlecode.jtiger.core.util.ResBundleUtil;

/**
 * 系统常量类.
 * @author Sam
 * 
 */
public final class Constants {
  /**
   * Prevent from initializing.
   * 
   */
  private Constants() {
  }

  /**
   * 字符串表示的true
   */
  public static final String CHAR_TRUE = "Y";

  /**
   * 字符串表示的false
   */
  public static final String CHAR_FALSE = "N";

  /**
   * 资源文件.
   */
  public static final String BUNDLE_KEY = "application";

  /**
   * 资源绑定对象
   */
  public static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
      .getBundle(BUNDLE_KEY);


  /**
   * 缺省的分页容量
   */
  public static final int DEFAULT_PAGE_SIZE = ResBundleUtil.getInt(
      RESOURCE_BUNDLE, "default.pagesize", 30);

  /**
   * 第一页的页码,缺省是1
   */
  public static final int DEFAULT_FIRST_PAGE_NO = ResBundleUtil.getInt(
      RESOURCE_BUNDLE, "defalut.firstPageNo", 1);


  /**
   * 状态可用
   */
  public static final String STATUS_AVAILABLE = ResBundleUtil.getString(
      RESOURCE_BUNDLE, "global.available", "1");

  /**
   * 状态不可用
   */
  public static final String STATUS_UNAVAILABLE = ResBundleUtil.getString(
      RESOURCE_BUNDLE, "global.unavailable", "0");

  /**
   * YES
   */
  public static final String YES = "1";

  /**
   * NO
   */
  public static final String NO = "0";
  
  /**
   * 用户信息在Session中的名字
   */
  public static final String USER_IN_SESSION = "userInSession";
}
