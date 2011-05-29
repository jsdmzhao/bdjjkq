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

import java.io.Writer;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * @since 2.0
 * @author Jeff Johnston
 */

@SuppressWarnings("unchecked")
public final class JspPageContext implements WebContext {
    private PageContext pageContext;
    private Map parameterMap;
    private Locale locale;

    public JspPageContext(PageContext pageContext) {
        this.pageContext = pageContext;
    }

    public Object getApplicationInitParameter(String name) {
        return pageContext.getServletContext().getInitParameter(name);
    }

    public Object getApplicationAttribute(String name) {
        return pageContext.getServletContext().getAttribute(name);
    }

    public void setApplicationAttribute(String name, Object value) {
        pageContext.getServletContext().setAttribute(name, value);
    }

    public void removeApplicationAttribute(String name) {
        pageContext.getServletContext().removeAttribute(name);
    }

    public Object getPageAttribute(String name) {
        return pageContext.getAttribute(name);
    }

    public void setPageAttribute(String name, Object value) {
        pageContext.setAttribute(name, value);
    }

    public void removePageAttribute(String name) {
        pageContext.removeAttribute(name);
    }

    public String getParameter(String name) {
        return pageContext.getRequest().getParameter(name);
    }

    public Map getParameterMap() {
        if (parameterMap != null) {
            return parameterMap;
        }

        return pageContext.getRequest().getParameterMap();
    }

    public void setParameterMap(Map parameterMap) {
        this.parameterMap = parameterMap;
    }

    public Object getRequestAttribute(String name) {
        return pageContext.getRequest().getAttribute(name);
    }

    public void setRequestAttribute(String name, Object value) {
        pageContext.getRequest().setAttribute(name, value);
    }

    public void removeRequestAttribute(String name) {
        pageContext.getRequest().removeAttribute(name);
    }

    public Object getSessionAttribute(String name) {
        return pageContext.getSession().getAttribute(name);
    }

    public void setSessionAttribute(String name, Object value) {
        pageContext.getSession().setAttribute(name, value);
    }

    public void removeSessionAttribute(String name) {
        pageContext.getSession().removeAttribute(name);
    }

    public Writer getWriter() {
        return pageContext.getOut();
    }

    public Locale getLocale() {
        if (locale != null) {
            return locale;
        }

        return pageContext.getRequest().getLocale();
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public String getContextPath() {
        ServletRequest request = pageContext.getRequest();
        if (request instanceof HttpServletRequest) {
            return ((HttpServletRequest) request).getContextPath();
        }

        throw new UnsupportedOperationException("There is no context path associated with the request.");
    }
    
    public String getRealPath(String path) {
        if (pageContext.getRequest() instanceof HttpServletRequest) {
            return ((HttpServletRequest) pageContext.getRequest()).getSession().getServletContext().getRealPath(path);
        }

        throw new UnsupportedOperationException("There is no real path associated with the request.");
    }

    public Object getBackingObject() {
        return pageContext;
    }
    
    public Object getContextObject() {
        return getBackingObject();
    }


}
