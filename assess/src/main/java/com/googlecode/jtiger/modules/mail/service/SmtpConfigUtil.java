package com.googlecode.jtiger.modules.mail.service;

import com.googlecode.jtiger.modules.mail.model.SmtpConfig;

public abstract class SmtpConfigUtil {
  /**
   * 从SmtpConfig中获取发送邮件时所需的from数据
   * @param smtpConfig 给定一个SmtpConfig的实例
   * @return 如果smtpConfig为<code>null</code>,返回<code>null</code>
   */
  public static String getFrom(SmtpConfig smtpConfig) {
    if(smtpConfig == null) {
      return null;
    }
    if(smtpConfig.getUsername().indexOf("@") > 0) {
      return smtpConfig.getUsername();
    }
    String host = smtpConfig.getHost();
    if((host.indexOf(".") + 1) < host.length()) {
      host = host.substring(host.indexOf(".") + 1);
    }
    return smtpConfig.getUsername() + "@" + host;
  }
}
