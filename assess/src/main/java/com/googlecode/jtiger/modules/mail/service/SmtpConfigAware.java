package com.googlecode.jtiger.modules.mail.service;

import com.googlecode.jtiger.modules.mail.model.SmtpConfig;

public interface SmtpConfigAware {

  /**
   * 重新设置{@link #activatedSmtpConfig}，并重新设置@{link JavaMailSenderImpl}的属性。线程安全。
   * @param activatedSmtpConfig the activatedSmtpConfig to set
   */
  public abstract void setActivatedSmtpConfig(SmtpConfig activatedSmtpConfig);

}