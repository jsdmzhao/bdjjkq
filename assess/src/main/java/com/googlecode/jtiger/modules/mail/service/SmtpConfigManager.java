package com.googlecode.jtiger.modules.mail.service;

import com.googlecode.jtiger.modules.mail.model.SmtpConfig;

/**
 * The manager of the SMTP config.
 * @author Sam Lee
 *
 */
public interface SmtpConfigManager {
  /**
   * @return the smpt
   */
  public SmtpConfig getSmtpConfig();

  /**
   * @param smpt the smpt to set
   */
  public void setSmtpConfig(SmtpConfig smtp);
  
}
