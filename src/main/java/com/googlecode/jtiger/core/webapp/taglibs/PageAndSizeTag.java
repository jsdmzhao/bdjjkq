package com.googlecode.jtiger.core.webapp.taglibs;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.lang.StringUtils;

import com.googlecode.jtiger.core.Constants;

/**
 * 用于将ServletRequest对象中的parameters以<pre>&lt;input type="hidden"...</pre>或
 * <pre>key1=value1&key2=...</pre>的形式输出在页面上。通常用于保留本次参数以便于下一次使用。
 * 于struts2的chaining action结合可以实现跨请求的action chain.
 * @author Sam Lee
 *
 */
@SuppressWarnings({"serial"})
public class PageAndSizeTag extends BodyTagSupport {
  //private static Logger logger = LoggerFactory.getLogger(PageAndSizeTag.class);
  /**
   * 输出为input
   */
  public static final String TYPE_INPUT_TAG = "inputTag";
  /**
   * 输出为query string
   */
  public static final String TYPE_QUERY_STRING = "queryString";
  
  private String pageKey = "page";
  private String sizeKey = "rows";
  /**
   * @see #TYPE_INPUT_TAG
   * @see #TYPE_QUERY_STRING
   */
  private String type = TYPE_INPUT_TAG;
  

  /**
   * @see javax.servlet.jsp.tagext.BodyTagSupport#release()
   */
  @Override
  public void release() {
    pageKey = "page";
    sizeKey = "rows";
    
    type = null;
    super.release();
  }

  /**
   * @see javax.servlet.jsp.tagext.BodyTagSupport#doEndTag()
   */
  @Override
  public int doEndTag() throws JspException { 
    String str = "";
    
    
    if(StringUtils.isBlank(type) || StringUtils.equals(TYPE_INPUT_TAG, type)) {
      str = buildInputTags();
    } else {
      str = buildQueryString();
    }
    try {
      pageContext.getOut().write(str);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return BodyTagSupport.EVAL_PAGE;
  }
  
  /**
   * 根据参数构造queryString
   * @param keys 参数名称
   */
  private String buildQueryString() {
    StringBuffer buf = new StringBuffer();
    //page
    String value = pageContext.getRequest().getParameter(getPageKey());
    if(StringUtils.isBlank(value)) {
      value = String.valueOf(Constants.DEFAULT_FIRST_PAGE_NO);
    }
    buf.append(getPageKey()).append("=").append(value);
    buf.append("&");
    //size
    value = pageContext.getRequest().getParameter(getSizeKey());
    if(StringUtils.isBlank(value)) {
      value = String.valueOf(Constants.DEFAULT_PAGE_SIZE);
    }
    buf.append(getSizeKey()).append("=").append(value);
    return buf.toString();
  }

  /**
   * 根据参数构造Input
   * @param keys 参数名称
   */
  private String buildInputTags() {
    StringBuffer buf = new StringBuffer();
    //page
    String value = pageContext.getRequest().getParameter(getPageKey());
    buf.append("<input type='hidden' name='").append(getPageKey()).append("' value='").append(
        (value == null) ? String.valueOf(Constants.DEFAULT_FIRST_PAGE_NO) : value).append("'/>");
    //size
    value = pageContext.getRequest().getParameter(getSizeKey());
    buf.append("<input type='hidden' name='").append(getSizeKey()).append("' value='").append(
        (value == null) ? String.valueOf(Constants.DEFAULT_PAGE_SIZE) : value).append("'/>");

    return buf.toString();
  }
 
 
  /**
   * @return the type
   */
  public String getType() {
    return type;
  }

  /**
   * @param type the type to set
   */
  public void setType(String type) {
    this.type = type;
  }

  public String getPageKey() {
    return pageKey;
  }

  public void setPageKey(String pageKey) {
    this.pageKey = pageKey;
  }

  public String getSizeKey() {
    return sizeKey;
  }

  public void setSizeKey(String sizeKey) {
    this.sizeKey = sizeKey;
  }

}
