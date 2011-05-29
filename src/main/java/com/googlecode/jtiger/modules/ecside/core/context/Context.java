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

/**
 * @author Jeff Johnston
 */

@SuppressWarnings("unchecked")
public interface Context {

    public Object getApplicationInitParameter(String name);

    public Object getApplicationAttribute(String name);

    public void setApplicationAttribute(String name, Object value);

    public void removeApplicationAttribute(String name);

    public Object getPageAttribute(String name);

    public void setPageAttribute(String name, Object value);

    public void removePageAttribute(String name);

    public String getParameter(String name);

    public Map getParameterMap();

    public Object getRequestAttribute(String name);

    public void setRequestAttribute(String name, Object value);

    public void removeRequestAttribute(String name);

    public Object getSessionAttribute(String name);

    public void setSessionAttribute(String name, Object value);

    public void removeSessionAttribute(String name);

    public Writer getWriter();

    public Locale getLocale();

    public String getContextPath();

    public Object getContextObject();

}