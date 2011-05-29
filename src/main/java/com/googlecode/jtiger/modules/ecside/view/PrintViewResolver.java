/*
 * Copyright 2006-2007 original author or authors.
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
package com.googlecode.jtiger.modules.ecside.view;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.googlecode.jtiger.modules.ecside.core.Preferences;


/**
 * @author Wei Zijun
 *
 */

public class PrintViewResolver implements ViewResolver {
    public void resolveView(ServletRequest request, ServletResponse response, Preferences preferences, Object viewData) throws Exception {
//        byte[] contents = ((String) viewData).getBytes();
//        response.setContentLength(contents.length);
//        response.getWriter().println((String) viewData);
    }
}
