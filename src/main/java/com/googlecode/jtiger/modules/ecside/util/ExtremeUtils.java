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
package com.googlecode.jtiger.modules.ecside.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.PageContext;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Jeff Johnston
 */

@SuppressWarnings("unchecked")
public final class ExtremeUtils {
    private static Log logger = LogFactory.getLog(ExtremeUtils.class);

    private ExtremeUtils() {
    }

    /**
     * Convert camelCase text to a readable word. example: camelCaseToWord -->
     * Camel Case To Word
     */
    public static String camelCaseToWord(String camelCaseText) {
        if (StringUtils.isEmpty(camelCaseText)) {
            return camelCaseText;
        }

        if (camelCaseText.equals(camelCaseText.toUpperCase())) {
            return camelCaseText;
        }

        char[] ch = camelCaseText.toCharArray();
        String first = "" + ch[0];
        String build = first.toUpperCase();

        for (int i = 1; i < ch.length; i++) {
            String test = "" + ch[i];

            if (test.equals(test.toUpperCase())) {
                build += " ";
            }

            build += test;
        }

        return build;
    }

    public static String formatDate(String parse, String format, Object value) {
        return formatDate(parse, format, value, Locale.getDefault());
    }

    public static String formatDate(String parse, String format, Object value, Locale locale) {
        if (value == null) {
            return null;
        }

        if (StringUtils.isBlank(format)) {
            String valueAsString = value.toString();
            logger.error("The format was not defined for date [" + valueAsString + "].");
            return valueAsString;
        }

        Date date = null;
        if (value instanceof Date) {
            date = (Date) value;
        } else {
            String valueAsString = value.toString();

            if (StringUtils.isBlank(valueAsString)) {
                return valueAsString;
            }

            if (StringUtils.isBlank(parse)) {
                logger.error("The parse was not defined for date String [" + valueAsString + "].");
                return valueAsString;
            }

            try {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(parse, locale);
                date = simpleDateFormat.parse(valueAsString);
            } catch (Exception e) {
                logger.error("The parse was incorrectly defined for date String [" + valueAsString + "].");
                return valueAsString;
            }
        }

        return DateFormatUtils.format(date, format, locale);
    }

    public static String formatNumber(String format, Object value) {
        return formatNumber(format, value, Locale.getDefault());
    }

    public static String formatNumber(String format, Object value, Locale locale) {
        String result = null;

        if (value == null) {
            return result;
        }

        if (StringUtils.isBlank(format)) {
//            logger.warn("The format was not defined for number [" + value.toString() + "].");
            return value.toString();
        }

        NumberFormat nf = NumberFormat.getNumberInstance(locale);
        DecimalFormat df = (DecimalFormat) nf;
        df.applyLocalizedPattern(format);

        return df.format(Double.parseDouble(value.toString()));
    }

    /**
     * Get the bean out of the proper scope.
     */
    public static Object retrieveFromScope(PageContext pageContext, String name) {
        return retrieveFromScope(pageContext, name, null);
    }

    /**
     * Get the bean out of the proper scope.
     */
    public static Object retrieveFromScope(PageContext pageContext, String name, String scope) {
        if (StringUtils.isBlank(scope)) {
            return pageContext.findAttribute(name);
        }

        int scopeType = PageContext.REQUEST_SCOPE;

        if (scope.equalsIgnoreCase("page")) {
            scopeType = PageContext.PAGE_SCOPE;
        } else if (scope.equalsIgnoreCase("application")) {
            scopeType = PageContext.APPLICATION_SCOPE;
        } else if (scope.equalsIgnoreCase("session")) {
            scopeType = PageContext.SESSION_SCOPE;
        }

        return pageContext.getAttribute(name, scopeType);
    }

    public static int sessionSize(HttpSession session) {
        int total = 0;

        try {
            java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
            java.io.ObjectOutputStream oos = new java.io.ObjectOutputStream(baos);
            Enumeration enumeration = session.getAttributeNames();

            while (enumeration.hasMoreElements()) {
                String name = (String) enumeration.nextElement();
                Object obj = session.getAttribute(name);
                oos.writeObject(obj);

                int size = baos.size();
                total += size;
                logger.debug("The session name: " + name + " and the size is: " + size);
            }

            logger.debug("Total session size is: " + total);
        } catch (Exception e) {
            logger.error("Could not get the session size - " + ExceptionUtils.formatStackTrace(e));
        }

        return total;
    }

    /**
     * Get a list of bean or map property names
     */
    public static List beanProperties(Object bean) throws Exception {
        List properties = new ArrayList();

        if (bean instanceof Map) {
            properties.addAll(((Map) bean).keySet());
        } else {
            properties.addAll(BeanUtils.describe(bean).keySet());
        }

        return properties;
    }

    /**
     * Check to see if it is safe to grab the property from the bean via
     * reflection.
     * 
     * PropertyUtils.isReadable() Will throw a runtime IllegalArgumentException
     * if bean or property is null. This is a problem when trying to reflect
     * further down into an object.
     */
    public static boolean isBeanPropertyReadable(Object bean, String property) {
        if (bean instanceof Map) {
            return ((Map) bean).containsKey(property);
        }

        boolean isReadable;
        try {
            isReadable = PropertyUtils.isReadable(bean, property);
        } catch (IllegalArgumentException e) {
            if (logger.isDebugEnabled()) {
                logger.debug("Could not find the property [" + property + "]. Either the bean or property is null");
            }
            isReadable = false;
        }

        return isReadable;
    }

    /**
     * Go through the request parameters and figure out which checkboxes are
     * selected.
     * 
     * @param startsWithValue
     *            A way to identify all the checkboxes. For example ckbox_.
     */
    public static List checkboxesSelected(HttpServletRequest request, String startsWithValue) {
        List results = new ArrayList();

        Enumeration parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String parameterName = (String) parameterNames.nextElement();
            if (parameterName.startsWith(startsWithValue)) {
                results.add(StringUtils.substringAfter(parameterName, startsWithValue));
            }
        }

        return results;
    }
    
    /**
     * Take a map of parameter key/value pairs and create
     * a URI query string out of it.
     */
    public static String getQueryString(Map parameterMap) {
        StringBuffer results = new StringBuffer();

        Iterator iterator = parameterMap.keySet().iterator();
        for (Iterator iter = iterator; iter.hasNext();) {
            String key = (String) iter.next();
            String value[] = (String[]) parameterMap.get(key);
            
            if (results.length() == 0) {
                results.append("?");
            } else {
                results.append("&");
            }
            
            results.append(key + "=");
            
            if (value != null && value.length > 0) {
                results.append(value[0]);
            }
        }

        return results.toString();
    }
}
