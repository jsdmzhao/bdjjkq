package com.googlecode.jtiger.modules.mail.service;

import java.io.InputStream;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.googlecode.jtiger.core.ApplicationException;
import com.googlecode.jtiger.core.dao.hibernate.BaseHibernateDao;
import com.googlecode.jtiger.modules.freemarker.FreeMarkerService;
import com.googlecode.jtiger.modules.mail.model.SmtpConfig;


/**
 * 扩展了JavaMailSenderImpl，异步发送邮件，自动读取SmtpConfig的设置信息。并且，可以在运行时动态修改设置。
 * MailService实现了TemplateMailSender接口，可以使用freemarker模板生成邮件内容
 * <b>不要</b>直接注入这个类，如果需要使用freemarker模板，请注入@{link TemplateMailSender}
 * 接口；如果不需要模板，请注入@{link JavaMailSender}接口。
 * @author catstiger@gmail.com
 * @see @{link TemplateMailSender}
 * @see @{link JavaMailSenderImpl}
 */
@Service
public class MailService implements TemplateMailSender {
  private Logger logger = LoggerFactory.getLogger(MailService.class);
  
  private final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
  private final Lock read = readWriteLock.readLock();
  private final Lock write = readWriteLock.writeLock();
  private SmtpConfig activatedSmtpConfig;
  @Autowired
  private BaseHibernateDao dao;
  @Autowired
  private FreeMarkerService freeMarkerService;
  
  private JavaMailSenderImpl sender = new JavaMailSenderImpl();
  
  /**
   * 返回当前激活的SmtpConfig.如果{@link #activatedSmtpConfig}为<code>null</code>则从数据库中查询，如果还是
   * 为<code>null</code>,则抛出IllegalStateException异常。线程安全。
   */
  protected SmtpConfig getActivatedSmtpConfig() {
    if(activatedSmtpConfig == null) {
      try {
        write.lock();
        activatedSmtpConfig = (SmtpConfig) dao.findObject("from SmtpConfig");
      } finally {
        write.unlock();
      }
    }
    if(activatedSmtpConfig == null) {
      logger.warn("No actived smtp config.Please config it first.");
    }
    
    try {
      read.lock();
      return activatedSmtpConfig;
    } finally {
      read.unlock();
    }
  }

  /**
   * @see TemplateMailSender#setActivatedSmtpConfig(SmtpConfig)
   */
  public void setActivatedSmtpConfig(SmtpConfig activatedSmtpConfig) {
    try {
      write.lock();
      this.activatedSmtpConfig = activatedSmtpConfig;
      initialize(); //重新初始化JavaMailSenderImpl
    } finally {
      write.unlock();
    }
  }
  
  @PostConstruct
  public void initialize() {
    if(activatedSmtpConfig == null) {
      getActivatedSmtpConfig();
    }
    if (activatedSmtpConfig != null) {
      try {
        write.lock();
        sender.setHost(activatedSmtpConfig.getHost());
        sender.setUsername(activatedSmtpConfig.getUsername());
        sender.setPassword(activatedSmtpConfig.getPassword());
        sender.setPort(activatedSmtpConfig.getPort());
        // 设置SMTP身份验证属性
        if (activatedSmtpConfig.getAuth() != null) {
          Properties javaMailProperties = new Properties();
          javaMailProperties.put("mail.smtp.auth", activatedSmtpConfig.getAuth()
              .toString());
          sender.setJavaMailProperties(javaMailProperties);
        }
        sender.setDefaultEncoding("UTF-8");
      } finally {
        write.unlock();
      }
    }
  }
  /**
   * @see TemplateMailSender#send(String, String, String, Object)
   */
  //@Async
  public void send(final String to, final String subject, final String template, final Object model) {
    send(template, model, new MimeMessageCallback(){
      @Override
      public void doMimeMessageHelper(MimeMessageHelper mimeMessageHelper)
          throws MessagingException {
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setFrom(SmtpConfigUtil.getFrom(activatedSmtpConfig));
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setSentDate(new Date());
      }      
    });
  }
  
  @Override
  //@Async
  public void send(final String template, final Object model, final MimeMessageCallback callback) {
    MimeMessage message = createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");
    try {
      helper.getMimeMessage().addHeader("Content-Type", "text/html; charset=UTF-8");
      callback.doMimeMessageHelper(helper);
      String text = freeMarkerService.processTemplate(template, model);
      helper.setText(text, true); //第二个参数表示发送HTML邮件
      callback.doMimeMessageHelper(helper); //设置其他的参数       
    } catch (MessagingException e) {
      e.printStackTrace();
      throw new ApplicationException(e);
    }
    send(message);
    logger.debug("Mail was sended asynchronously.");
    
  }
  

  /**
   * @see JavaMailSenderImpl#send(MimeMessage)
   */
  @Override
  @Async
  public void send(MimeMessage mimeMessage) throws MailException {
    if(activatedSmtpConfig == null) {
      throw new IllegalStateException("No actived smtp config.Please config it first.");
    }
    sender.send(mimeMessage);
  }

  /**
   * @see JavaMailSenderImpl#send(MimeMessage[])
   */
  @Override
  @Async
  public void send(MimeMessage[] mimeMessages) throws MailException {
    if(activatedSmtpConfig == null) {
      throw new IllegalStateException("No actived smtp config.Please config it first.");
    }
    sender.send(mimeMessages);
  }

  /**
   * @see JavaMailSenderImpl#send(MimeMessagePreparator)
   */
  @Override
  @Async
  public void send(MimeMessagePreparator mimeMessagePreparator)
      throws MailException {
    if(activatedSmtpConfig == null) {
      throw new IllegalStateException("No actived smtp config.Please config it first.");
    }
    sender.send(mimeMessagePreparator);
  }

  /**
   * @see JavaMailSenderImpl#send(MimeMessagePreparator[])
   */
  @Override
  @Async
  public void send(MimeMessagePreparator[] mimeMessagePreparators)
      throws MailException {
    if(activatedSmtpConfig == null) {
      throw new IllegalStateException("No actived smtp config.Please config it first.");
    }
    sender.send(mimeMessagePreparators);
  }

  /**
   * @see JavaMailSenderImpl#send(org.springframework.mail.SimpleMailMessage)
   */
  @Override
  @Async
  public void send(SimpleMailMessage simpleMessage) throws MailException {
    if(activatedSmtpConfig == null) {
      throw new IllegalStateException("No actived smtp config.Please config it first.");
    }
    sender.send(simpleMessage);
  }

  /**
   * @see JavaMailSenderImpl#send(org.springframework.mail.SimpleMailMessage[])
   */
  @Override
  @Async
  public void send(SimpleMailMessage[] simpleMessages) throws MailException {
    if(activatedSmtpConfig == null) {
      throw new IllegalStateException("No actived smtp config.Please config it first.");
    }
    sender.send(simpleMessages);
  }

  @Override
  public MimeMessage createMimeMessage() {
    return sender.createMimeMessage();
  }

  @Override
  public MimeMessage createMimeMessage(InputStream contentStream)
      throws MailException {
    return sender.createMimeMessage(contentStream);
  }

}
