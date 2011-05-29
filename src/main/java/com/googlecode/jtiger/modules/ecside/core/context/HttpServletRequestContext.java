/*
 * Copyright 2004 original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.googlecode.jtiger.modules.ecside.core.context;

import java.io.StringWriter;
import java.io.Writer;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @since 2.0
 * @author Jeff Johnston
 */

@SuppressWarnings("unchecked")
public class HttpServletRequestContext implements WebContext {
	protected static Log logger = LogFactory
			.getLog(HttpServletRequestContext.class);
	private HttpServletRequest request;
	private Map parameterMap;
	private Locale locale;

	public HttpServletRequestContext(HttpServletRequest request) {
		this.request = request;
	}

	public Object getApplicationInitParameter(String name) {
		try {
			return request.getSession().getServletContext().getInitParameter(
					name);
		} catch (Exception e) {
			logger.warn(e.getMessage());
			return null;
		}
	}

	public Object getApplicationAttribute(String name) {
		return request.getSession().getServletContext().getAttribute(name);
	}

	public void setApplicationAttribute(String name, Object value) {
		request.getSession().getServletContext().setAttribute(name, value);
	}

	public void removeApplicationAttribute(String name) {
		request.getSession().getServletContext().removeAttribute(name);
	}

	public Object getPageAttribute(String name) {
		return request.getAttribute(name);
	}

	public void setPageAttribute(String name, Object value) {
		request.setAttribute(name, value);
	}

	public void removePageAttribute(String name) {
		request.removeAttribute(name);
	}

	public String getParameter(String name) {
		if (parameterMap != null) {
			String[] values = ContextUtils.getValueAsArray(parameterMap
					.get(name));
			if (values != null && values.length > 0) {
				return values[0];
			}
		}

		return request.getParameter(name);
	}

	public Map getParameterMap() {
		if (parameterMap != null) {
			return parameterMap;
		}

		return request.getParameterMap();
	}

	public void setParameterMap(Map parameterMap) {
		this.parameterMap = parameterMap;
	}

	public Object getRequestAttribute(String name) {
		return request.getAttribute(name);
	}

	public void setRequestAttribute(String name, Object value) {
		request.setAttribute(name, value);
	}

	public void removeRequestAttribute(String name) {
		request.removeAttribute(name);
	}

	public Object getSessionAttribute(String name) {
		return request.getSession().getAttribute(name);
	}

	public void setSessionAttribute(String name, Object value) {
		request.getSession().setAttribute(name, value);
	}

	public void removeSessionAttribute(String name) {
		request.getSession().removeAttribute(name);
	}

	public Writer getWriter() {
		return new StringWriter();
	}

	public Locale getLocale() {
		if (locale != null) {
			return locale;
		}

		return request.getLocale();
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public String getContextPath() {
		return request.getContextPath();
	}

	public String getRealPath(String path) {
		return request.getSession().getServletContext().getRealPath(path);
	}

	public Object getBackingObject() {
		return request;
	}

	public Object getContextObject() {
		return getBackingObject();
	}

}
