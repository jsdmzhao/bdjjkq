package com.googlecode.jtiger.modules.commonpages;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.googlecode.jtiger.core.webapp.struts2.action.BaseAction;

/**
 * 通用页面Action
 * @author catstiger@gamil.com
 *
 */
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CommonPagesAction extends BaseAction {

  /**
   * 服务未开放
   */
  public String waiting() {
    return "waiting";
  }
  
  /**
   * 首页
   */
  public String index() {
    return "index";
  }
  /**
   * 登录页面
   */
  public String login() {
    return "login";
  }
  
  /**
   * 后台主页
   */
  public String main() {
    return "main";
  }
  
  /**
   * 后台框架
   */
  public String layout() {
    return "layout";
  }

}
