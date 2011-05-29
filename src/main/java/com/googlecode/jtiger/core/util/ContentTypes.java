package com.googlecode.jtiger.core.util;

import java.util.ResourceBundle;
/**
 * 根据扩展名，取得ContentType,例如：<br>
 * <pre>
 * //下面的代码返回image/jpeg
 * ContentTypes.get("jpg");
 * 
 * </pre>
 * @author catstiger@gmail.com
 *
 */
public abstract class ContentTypes {
  public static String get(String key) {
    ResourceBundle rb = ResourceBundle.getBundle("content-types");
    String type = new StringBuilder(40).append("type.")
      .append(key).toString();
    return ResBundleUtil.getString(rb, type , "text/html");
  }
}
