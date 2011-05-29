package com.googlecode.jtiger.modules.mail.service;

import javax.mail.MessagingException;

import org.springframework.mail.javamail.MimeMessageHelper;
/**
 *  用于设置MimeMessage的回调接口。子类不必考虑MimeMessageHelper参数如何获得，只需要
 *  设置各种属性即可。
 * @author catstiger@gmail.com
 */
public interface MimeMessageCallback {
  /**
   * @param mimeMessageHelper passed @{link MimeMessageHelper} with UTF-8 encoding.
   * @throws MessagingException if any message exception occurs.
   */
  public void doMimeMessageHelper(MimeMessageHelper mimeMessageHelper) throws MessagingException;
}
