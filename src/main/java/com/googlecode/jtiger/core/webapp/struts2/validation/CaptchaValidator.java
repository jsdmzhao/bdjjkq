package com.googlecode.jtiger.core.webapp.struts2.validation;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.octo.captcha.service.CaptchaService;
import com.octo.captcha.service.CaptchaServiceException;

/**
 * 验证Captcha类，注入即可直接使用。
 * @author catstiger@gmail.com
 *
 */
@Component
public class CaptchaValidator {
  protected Logger logger = LoggerFactory.getLogger(getClass());
  @Autowired
  protected CaptchaService captchaService;
  /**
   * 验证Captcha，如果验证通过，返回<code>true</code>，否则，<code>false</code>
   * @param request HttpServletRequest
   * @param captcha 页面输入的验证码，如果为空，则从request中获取
   */
  public boolean validateCaptcha(HttpServletRequest request, String captcha) {
    Boolean isResponseCorrect = Boolean.FALSE;
    //remenber that we need an id to validate!
    String captchaId = request.getSession().getId();
    String jcaptcha = StringUtils.isBlank(captcha) ? request.getParameter("jcaptcha") : captcha;
    // Call the Service method
     try {
         isResponseCorrect = captchaService.validateResponseForID(captchaId,
             jcaptcha);
     } catch (CaptchaServiceException e) {
       logger.info("JCaptch validate faild! Cause: [{}]", e.getMessage());
     }
     return isResponseCorrect;
  }
 
}
