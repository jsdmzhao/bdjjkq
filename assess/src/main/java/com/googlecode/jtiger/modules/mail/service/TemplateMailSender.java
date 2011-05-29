package com.googlecode.jtiger.modules.mail.service;


import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;

/**
 * 扩展JavaMailSender,额外提供了一些简单的方法，可以通过<pre>@Async</pre>标记来异步发送邮件。也可以使用freemarker模板
 * 来生成邮件内容。AsyncMailSender扩展了SmtpConfigAware接口，可以在运行时动态设置SMTP。
 * @author catstiger@gmail.com
 *
 */
public interface TemplateMailSender extends JavaMailSender, SmtpConfigAware {

  /**
   * 使用freemarker模板，异步发送MimeMessage邮件。from数据由SmtpConfig中解析得到
   * @param to To address 
   * @param subject the subject of the message, using the correct encoding.
   * @param template 发送邮件内容所使用的模板名称，例如:mytemplate.ftl
   * @param model 模板中的数据
   * @see @{link jtiger.core.web.ui.freemarker.FreeMarkerService}
   */
  @Async
  public abstract void send(final String to, final String subject,
      final String template, final Object model);
  
  /**
   * 使用freemarker模板，异步发送MimeMessage邮件。callback中{@link MimeMessageCallback#doMimeMessageHelper(MimeMessageHelper)}
   * 传入的参数是一个已经设置了UTF-8编码和并将模板数据解析为String之后传给了setText()方法的MimeMessageHelper对象。
   * @param template 发送邮件内容所使用的模板名称，例如:mytemplate.ftl
   * @param model 模板中的数据
   * @param callback 可以用来设置to,from,cc,subject等信息
   */
  @Async
  public abstract void send(final String template, final Object model, MimeMessageCallback callback);
}