package com.googlecode.jtiger.core.webapp.struts2.action;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.StrutsStatics;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.util.WebUtils;

import com.googlecode.jtiger.core.ApplicationException;
import com.googlecode.jtiger.core.util.WebMockUtil;
import com.googlecode.jtiger.modules.security.user.UserUtil;
import com.googlecode.jtiger.modules.security.user.model.User;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * BaseAction of the struts2
 * 
 * @author Sam
 * 
 */
@SuppressWarnings({"serial"})
public abstract class BaseAction extends ActionSupport {

	/**
	 * 为子类提供Log功能，方便子类使用
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * struts2的ActionContext
	 */
	private ActionContext actionContext;
	
	@Autowired
	private UserUtil userUtil;	
	
	private ObjectMapper objectMapper = new ObjectMapper();


	/**
	 * 获得struts2的ActionContext
	 */
	protected final ActionContext getActionContext() {
		return (actionContext == null) ? actionContext = ActionContext
				.getContext() : actionContext;
	}

	/**
	 * 获得当前Action的名称
	 */
	public final String getActionName() {
		return getActionContext().getName();
	}

	/**
	 * 获得当前Http Servlet Request.
	 * 如果在单元测试环境下，则每次调用都返回一个新的MockHttpServletRequest的实例
	 */
	protected final HttpServletRequest getRequest() {
		HttpServletRequest request = null;
		if (getActionContext() != null) { // 从Struts2中取得Request
			request = (HttpServletRequest) getActionContext().get(
					StrutsStatics.HTTP_REQUEST);
		}

		if (request == null) {
			request = WebMockUtil.getHttpServletRequest();
		}

		return request;
	}

	/**
	 * 获得当前HttpServletResponse.
	 * 如果在单元测试环境下，则每次调用都返回一个新的MockHttpServletResponse的实例
	 */
	protected final HttpServletResponse getResponse() {
		HttpServletResponse response = null;
		if (getActionContext() != null) { // 从Struts2中取得Response
			response = (HttpServletResponse) getActionContext().get(
					StrutsStatics.HTTP_RESPONSE);
		}

		if (response == null) {
			response = WebMockUtil.getHttpServletResponse();
		}

		return response;
	}

	/**
	 * 获得当前ServletContext. 如果在单元测试环境下，则每次调用都返回一个新的MockServletContext的实例.
	 */
	protected final ServletContext getServletContext() {
		ServletContext ctx = (ServletContext) getActionContext().get(
				StrutsStatics.SERVLET_CONTEXT);

		if (ctx == null) {
			ctx = WebMockUtil.getServletContext();
		}
		return ctx;
	}

	/**
	 * Return the real path of the given path within the web application, as
	 * provided by the servlet container.
	 * <p>
	 * Prepends a slash if the path does not already start with a slash.
	 * 
	 * @param path the path within the web application
	 * @return the corresponding real path or <code>null</code> if the path
	 *         cannot be resolved to a resource.
	 */
	protected String getRealPath(String path) {
		try {
			return WebUtils.getRealPath(getServletContext(), path);
		} catch (FileNotFoundException e) {
			logger.warn("Can't find real path '{}' in web context", path);
			e.printStackTrace();
			return null;
		}
	}

/**
	 * 直接输出数据，而不是通过struts重新定向.
	 * @param response The HttpServletResponse object to be write in
	 * @param text Content to be writed.
	 * @param contentType.
	 * @see {@link #renderJson(HttpServletResponse, String)
	 * @see {@link #renderXml(HttpServletResponse, String)
	 */
	protected void render(HttpServletResponse response, String text,
			String contentType) {
	  preventFromCaching();
		try {
			response.setContentType(contentType);
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(text);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	protected void render( String text, String contentType) {
	  render(getResponse(), text, contentType);
	}

	/**
	 * 直接输出Json.
	 */
	protected void renderJson(HttpServletResponse response, String text) {
		render(response, text, "text/x-json;charset=UTF-8");
	}
	
	protected void renderJson(String text) {
    render(text, "text/x-json;charset=UTF-8");
  }

	/**
	 * 直接输出纯XML.
	 */
	protected void renderXml(HttpServletResponse response, String text) {
		render(response, text, "text/xml;charset=UTF-8");
	}
	
	protected void renderXml(String text) {
    render(text, "text/xml;charset=UTF-8");
  }
  
	/**
	 * 防止内容被Browser缓存
	 */
	protected void preventFromCaching() {
	  getResponse().addHeader("Cache-Control", "no-cache");
    getResponse().addHeader("Pragma", "no-cache");
    getResponse().addHeader("Expires", "0");
	}

	/**
	 * 获取当前登录的用户信息
	 * @see {@link UserUtil#getPrincipal(HttpServletRequest)}
	 */
	 public User getPrincipal() {
	   return UserUtil.getPrincipal(getRequest());
	 }
	 
	 /**
	  * 与{@link #getPrincipal()}类似，但是可以从数据库中重新获得最新数据
	  * @return
	  */
	 public User getUser() {
	   return userUtil.getUser(getRequest());
	 }
	 
	 
	 
  /**
   * 将一个对象转变为json字符串 
   */
  protected String toJson(Object target) {
    try {
      return objectMapper.writeValueAsString(target);
    } catch (JsonGenerationException e) {      
      e.printStackTrace();
      throw new ApplicationException(e);
    } catch (JsonMappingException e) {      
      e.printStackTrace();
      throw new ApplicationException(e);
    } catch (IOException e) {      
      e.printStackTrace();
      throw new ApplicationException(e);
    }
  }
}
