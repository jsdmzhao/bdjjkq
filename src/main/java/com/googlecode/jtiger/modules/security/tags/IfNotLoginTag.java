package com.googlecode.jtiger.modules.security.tags;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
/**
 * 与{@link IfLoginTag}的作用相反，判断当前用户是否没有登录。
 * @author sam
 *
 */
@SuppressWarnings("serial")
public class IfNotLoginTag extends BodyTagSupport {
  @Override
  public int doStartTag() throws JspException {
    HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
    Object princiapl = request.getUserPrincipal();
    if (princiapl != null) {
      return SKIP_BODY;
    } else {
      return EVAL_BODY_INCLUDE;
    }
  }
}
