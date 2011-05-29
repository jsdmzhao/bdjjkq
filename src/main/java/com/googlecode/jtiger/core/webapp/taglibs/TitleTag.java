package com.googlecode.jtiger.core.webapp.taglibs;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.googlecode.jtiger.core.Constants;
import com.googlecode.jtiger.core.util.ResBundleUtil;


public class TitleTag  extends BodyTagSupport{
  private String title = "";
  private Boolean fromBegin = true;
  /**
   * @see BodyTagSupport#doEndTag()
   */
  @Override
  public int doEndTag() throws JspException {
    String appName = ResBundleUtil.getString(Constants.RESOURCE_BUNDLE, "app.name", "");
    StringBuilder tit = new StringBuilder(200);
    if(fromBegin) {
      tit.append(appName);
    }
    if(StringUtils.isNotBlank(title)) {
      String[] titles = StringUtils.split(title, ",");
      if(ArrayUtils.isNotEmpty(titles)) {
        for(int i = 0; i < titles.length; i++) {
          if(i != 0 || fromBegin) {
            tit.append("-");
          } 
          
          //String val = 
          //  (String) ExpressionEvaluatorManager.evaluate("title", titles[i], this.getClass(), pageContext);
          tit.append(titles[i]);
        }
      }
    }
    try {
      pageContext.getOut().write(tit.toString());
    } catch (IOException e) {
      e.printStackTrace();
    }
    return BodyTagSupport.EVAL_PAGE;
  }
  /**
   * @return the title
   */
  public String getTitle() {
    return title;
  }
  /**
   * @param title the title to set
   */
  public void setTitle(String title) {
    this.title = title;
  }
  
  @Override
  public void release() {
    title = "";
    fromBegin = false;
    super.release();
  }
  /**
   * @return the fromBegin
   */
  public Boolean getFromBegin() {
    return fromBegin;
  }
  /**
   * @param fromBegin the fromBegin to set
   */
  public void setFromBegin(Boolean fromBegin) {
    this.fromBegin = fromBegin;
  }

}
