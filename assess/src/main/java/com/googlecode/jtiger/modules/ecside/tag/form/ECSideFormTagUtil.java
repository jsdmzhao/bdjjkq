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
package com.googlecode.jtiger.modules.ecside.tag.form;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.googlecode.jtiger.modules.ecside.common.log.LogHandler;


/**
 * @author Wei Zijun
 *
 */

/**
 * Various character utilities.
 */

@SuppressWarnings("unchecked")
public class ECSideFormTagUtil {

	private static Log logger = LogFactory.getLog(ECSideFormTagUtil.class);
	
	public final static String GET_PREFIX = "get";
	public final static String SET_PREFIX = "set";
	public final static String IS_PREFIX = "is";
	
	private static float newSizeFactor = 1.3f;
	private static String[] TABLE_HTML = new String[256];
	static {
		for (int i = 0; i < 10; i++) {
			TABLE_HTML[i] = "&#00" + i + ";";
		}
		for (int i = 10; i < 32; i++) {
			TABLE_HTML[i] = "&#0" + i + ";";
		}
		for (int i = 32; i < 128; i++) {
			TABLE_HTML[i] = String.valueOf((char)i);
		}
		for (int i = 128; i < 256; i++) {
			TABLE_HTML[i] = "&#" + i + ";";
		}

		// special characters
		TABLE_HTML['\''] = "&#039;";	// apostrophe ('&apos;' doesn't work - it is not by the w3 specs)
		TABLE_HTML['\"'] = "&quot;";	// double quote
		TABLE_HTML['&'] = "&amp;";		// ampersand
		TABLE_HTML['<'] = "&lt;";		// lower than
		TABLE_HTML['>'] = "&gt;";		// greater than


	}
	
	
	public static Map getAllProperties(Object bean) {
		if (bean instanceof Map){
			return new HashMap((Map)bean);
		}
		HashMap data = new HashMap();
		try {
			Method[] ms = bean.getClass().getMethods();
			for (int i = 0; i < ms.length; i++) {
				String name = ms[i].getName();
				if (name.startsWith(GET_PREFIX)) {
					String firstL=name.substring(GET_PREFIX.length(),GET_PREFIX.length()+1).toLowerCase();
					String pname = firstL+name.substring(GET_PREFIX.length()+1);
					
					data.put(pname,	ms[i].invoke(bean));
				}else if (name.startsWith(IS_PREFIX)){
					String firstL=name.substring(IS_PREFIX.length(),IS_PREFIX.length()+1).toLowerCase();
					String pname = firstL+name.substring(IS_PREFIX.length()+1);
					data.put(pname,	ms[i].invoke(bean));
				}
			}
		} catch (Exception e){
			LogHandler.errorLog(logger, e);
		}
		return data;
	}

	public static String toString(Object obj) {
		if (obj == null) {
			return (String)null;
		}
		return obj.toString();
	}

	
	
	public static String[] convertToStringArray(Object value) {

		if (value == null) {
			return null;
		}

		if (value.getClass().isArray() == true) {
			if (value instanceof String[]) {
				return (String[]) value;
			} else {
				Object[] valueArray = (Object[]) value;
				String[] result = new String[valueArray.length];
				for (int i = 0; i < valueArray.length; i++) {
					result[i] = valueArray[i].toString();
				}
				return result;
			}
		}

		return new String[] { value.toString() };
	}
	
	


	public static String encode(String string) {
		if ((string == null) || (string.length() == 0)) {
			return "";
		}
		int n = string.length();
		StringBuffer buffer = new StringBuffer((int) (n * newSizeFactor));
		int tableLen = TABLE_HTML.length;
		char c;
		for (int i = 0; i < n; i++) {
			c = string.charAt(i);
			if (c < tableLen) {
				buffer.append(TABLE_HTML[c]);
			} else {
				buffer.append("&#").append((int)c).append(';');
			}
		}
		return buffer.toString();
	}
	
	public static int indexOfIgnoreCase(String src, String subS, int startIndex) {
		String sub = subS.toLowerCase();
		int sublen = sub.length();
		int total = src.length() - sublen + 1;
		for (int i = startIndex; i < total; i++) {
			int j = 0;
			while (j < sublen) {
				char source = Character.toLowerCase(src.charAt(i + j));
				if (sub.charAt(j) != source) {
					break;
				}
				j++;
			}
			if (j == sublen) {
				return i;
			}
		}
		return -1;
	}
	
///////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////



	public static String getTagName(String tagBody) {
		return getTagName(tagBody, 0);
	}


	public static String getTagName(String body, int i) {
		if (body == null) {
			return null;
		}
		if (body.charAt(i) != '<') {
			return null;		// no tag
		}
		int start = i + 1;		// skip '<'
		int len = body.length();
		boolean isEndTag = false;
		// skip all non-letters
		while (start < len) {
			char c = body.charAt(start);
			if (c == '>') {
				return null;	// tag end found => name not found
			}
			if (c == '/') {		// this is an end tag
				start++;
				isEndTag = true;
				continue;
			}
			if (!Character.isWhitespace(c)) {
				break;
			}
			start++;
		}
		if (start == len) {
			return null;		// tag name not found
		}
		int end = start;
		// skip all letters
		while (end < len) {
			char c = body.charAt(end);
			if ((Character.isWhitespace(c)) || (c == '>')) {
				break;
			}
			end++;
		}
		if (end == len) {
			return null;		// tag end not found
		}
	
		String tagName = body.substring(start, end);
		if (isEndTag) {
			tagName = "/" + tagName;
		}
		return tagName;
	}


	public static String getAttribute(String tagBody, String attrName) {
		return getAttribute(tagBody, attrName, 0);
	}


	public static String getAttribute(String body, String attrName, int start) {
		if (body == null) {
			return null;
		}
		char quote = '\"';
		int end = body.indexOf('>');
		if (end == -1) {
			return null;	// tag's end not found
		}
		int i = indexOfIgnoreCase(body, attrName + "=\"", start);
		if ((i == -1) || (i > end)) {
			i = indexOfIgnoreCase(body, attrName + "='", start);
			if ((i == -1) || (i > end)) {
				return null;
			}
			quote = '\'';
		}
		String value = null;
		i += attrName.length() + 2;
		int s = i;
		int j = -1;
		while (true) {
			j = body.indexOf(quote, s);
			if (j == -1) {
				break;		// closed quation not found
			}
			if (body.charAt(j - 1) == '\\') {
				s = j + 1;
				continue;
			} else {
				value = body.substring(i, j);
				break;
			}
		}
		return value;
	}


	public static String addAttribute(String tagBody, String name, String value) {
		return addAttribute(tagBody, name, value, 0);
	}
	public static String addAttribute(String tagBody, String name) {
		return addAttribute(tagBody, name, 0);
	}


	public static String addAttribute(String body, String name, int i) {
		if (body == null) {
			return null;
		}
		if (name == null) {
			return body;
		}
		int end = body.indexOf('>', i);
		if (end == -1) {
			return body;
		}
		StringBuffer result = new StringBuffer(body.length());
		result.append(body.substring(i, end)).append(' ');
		result.append(name).append(body.substring(end));
		return result.toString();
	}

	public static String addAttribute(String body, String name, String value, int i) {
		if (body == null) {
			return null;
		}
		if (name == null) {
			return body;
		}
		if (value == null) {
			value = "";
		}
		int end = body.indexOf('>', i);
		if (end == -1) {
			return body;
		}
		StringBuffer result = new StringBuffer(body.length());
		result.append(body.substring(i, end)).append(' ');
		result.append(name).append('=').append('"');
		//result.append(ServletUtil.encodeHtml(value)).append('"');
		result.append(ECSideFormTagUtil.encode(value)).append('"');
		result.append(body.substring(end));
		return result.toString();
	}






}
