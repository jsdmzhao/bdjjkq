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

import javax.servlet.ServletRequest;

/**
 * @since 2.0
 * @author Jeff Johnston
 */

@SuppressWarnings("unchecked")
public class ServletRequestContext implements WebContext {
    private ServletRequest request;
    private Map parameterMap;
    private Locale locale;

    public ServletRequestContext(ServletRequest request) {
        this.request = request;
    }

    public Object getApplicationInitParameter(String name) {
        throw new UnsupportedOperationException("There is no session associated with the request.");
    }

    public Object getApplicationAttribute(String name) {
        throw new UnsupportedOperationException("There is no session associated with the request.");
    }

    public void setApplicationAttribute(String name, Object value) {
        throw new UnsupportedOperationException("There is no session associated with the request.");
    }

    public void removeApplicationAttribute(String name) {
        throw new UnsupportedOperationException("There is no session associated with the request.");
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
        throw new UnsupportedOperationException("There is no session associated with the request.");
    }

    public void setSessionAttribute(String name, Object value) {
        throw new UnsupportedOperationException("There is no session associated with the request.");
    }

    public void removeSessionAttribute(String name) {
        throw new UnsupportedOperationException("There is no session associated with the request.");
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
        throw new UnsupportedOperationException("There is no context path associated with the request.");
    }
    
    public String getRealPath(String path) {
        throw new UnsupportedOperationException("There is no real path associated with the request.");
    }

    public Object getBackingObject() {
        return request;
    }
    
    public Object getContextObject() {
        return getBackingObject();
    }

}
