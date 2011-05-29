package com.googlecode.jtiger.modules.security.password;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.tuckey.web.filters.urlrewrite.utils.StringUtils;

import com.googlecode.jtiger.core.ApplicationException;
import com.googlecode.jtiger.core.webapp.struts2.action.BaseAction;
import com.googlecode.jtiger.core.webapp.struts2.validation.CaptchaValidator;
import com.googlecode.jtiger.modules.mail.service.TemplateMailSender;
import com.googlecode.jtiger.modules.security.user.model.User;
import com.googlecode.jtiger.modules.security.user.service.UserManager;
import com.opensymphony.xwork2.validator.annotations.EmailValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * Restore Password
 * 
 * @author Sam Lee
 * 
 */
@SuppressWarnings( { "serial", "unchecked", "rawtypes" })
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class RestorePasswordAction extends BaseAction {

  public static final int LENGTH_OF_NEWPWD = 8;
  /**
   * User email address, we will send a new password through the email.
   */
  private String emailAddr;

  private String loginId;
  
  private String captcha;


  /**
   * To retieve the SMTP configurations.
   */
  @Autowired
  private TemplateMailSender mailService;
  
  @Autowired
  private CaptchaValidator captchaValidator;
  /**
   * UserManager to handle the password.
   */
  @Autowired
  private UserManager userManager;
  /**
   * Subject of the password mail
   */
  private String subject = "密码确认信.";


 /**
   * 修改用户的密码并发送一个电子邮件.
   */
  @Validations(requiredStrings = { @RequiredStringValidator(fieldName = "emailAddr", message = "请输入电子邮件.") }, emails = { @EmailValidator(fieldName = "emailAddr", message = "请输入正确的e-Mail.") })
  public String sendPasswordMail() {
    try {
      findPassword();
    } catch (Exception e) {
      e.printStackTrace();
      logger.error(e.getMessage());
      addActionError("邮件发送失败." + e.getMessage());
      return INPUT;
    }

    return "index";
  }
  
  /**
   * 发送修改密码的邮件，并修改用户密码为随机数
   */
  private void findPassword() {
    try {
      String newPwd = RandomStringUtils.randomNumeric(LENGTH_OF_NEWPWD);

      User user = (User) userManager.getDao().findObject(
          "from User u where u.email=? and u.loginId=?", emailAddr, loginId);
      if (user == null) {
        throw new ApplicationException("邮件发送失败,用户名或Email地址错误.");
      }
      Map model = new HashMap();
      model.put("loginId", user.getLoginId());
      model.put("pwd", newPwd);
      
      mailService.send(user.getEmail(), this.subject, "findPwd.ftl", model);
      userManager.updatePasswordByEmail(emailAddr, loginId, newPwd);
    } catch (Exception e) {
      e.printStackTrace();
      logger.error(e.getMessage());
      throw new ApplicationException(e);
    }
  }
  
  /**
   * Ajax方式找回密码，支持JCaptcha
   * @return
   */
  public String findByAjax() {
    if(!captchaValidator.validateCaptcha(getRequest(), captcha)) {
      render(getResponse(), "验证码错误！", "text/plain");
      return null;
    }
    try {
      logger.debug("user {}, email '{}'", loginId, emailAddr);
      findPassword();
      render(getResponse(), SUCCESS, "text/plain");
      return null;
    } catch (Exception e) {
      logger.error(e.getMessage());
      //替换信息中的null
      String msg = (StringUtils.isBlank(e.getMessage())) ? "未知错误." : e.getMessage().replaceAll("null", "");
      render(getResponse(), msg, "text/plain");
      return null;
    }    
  }
  
  /**
   * 定位到支持Captcha验证的form
   * @return
   */
  public String findPwd() {
    return "findPwdForm";
  }


  /**
   * 直接定向到密码输入页面.
   */
  public String edit() {
    return INPUT;
  }

  /**
   * @return the emailAddr
   */
  public String getEmailAddr() {
    return emailAddr;
  }

  /**
   * @param emailAddr the emailAddr to set
   */
  public void setEmailAddr(String emailAddr) {
    this.emailAddr = emailAddr;
  }



  /**
   * @return the loginId
   */
  public String getLoginId() {
    return loginId;
  }

  /**
   * @param loginId the loginId to set
   */
  public void setLoginId(String loginId) {
    this.loginId = loginId;
  }
  

  public String getCaptcha() {
    return captcha;
  }

  public void setCaptcha(String captcha) {
    this.captcha = captcha;
  }

}
