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

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.googlecode.jtiger.modules.ecside.common.log.LogHandler;



/**
 * @author Wei Zijun
 *
 */

@SuppressWarnings("unchecked")
public class ECSideFormTag extends BodyTagSupport {

	// ---------------------------------------------------------------- tag parameters

	/**
	 * 
	 */
	private Log logger = LogFactory.getLog(ECSideFormTag.class);
	
	private static final long serialVersionUID = 1L;
	private String beanNames = null;
	

	public void setBeans(String v) {
		beanNames = v;
	}

	public String getBeans() {
		return beanNames;
	}

	private String scopes = null;

	public void setScopes(String v) {
		scopes = v;
	}

	public String getScopes() {
		return scopes;
	}


	// ---------------------------------------------------------------- tag methods

	private HashMap beansValues = null;

	public int doStartTag() {
		beansValues = new HashMap();
		
		String[] b = beanNames.split(",");
		String[] s = scopes.toLowerCase().split(",");

		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		HttpSession session = (HttpSession) pageContext.getSession();
		
		int j=0;
		for (int i = 0; i < b.length; i++) {
			Object bean = null;
			if (i>=s.length){
				j=s.length-1;
			}else{
				j=i;
			}
			if ((s[j].length() == 0) || (s[j].equals("page"))) {
				bean = pageContext.getAttribute(b[i]);
			} else if (s[j].equals("request")) {
				bean = request.getAttribute(b[i]);
			} else if (s[j].equals("session")) {
				bean = session.getAttribute(b[i]);
			}
			if (bean != null) {
				beansValues.putAll((Map)ECSideFormTagUtil.getAllProperties(bean));
			}
		}
		return EVAL_BODY_AGAIN;
	}

	public int doAfterBody() {
		BodyContent body = getBodyContent();
		try	 {
			JspWriter out = body.getEnclosingWriter();
			String bodytext = body.getString();
			if ((beansValues != null) && (beansValues.size() > 0)) {
				bodytext = populateForm(bodytext, beansValues);
			}
			out.print(bodytext);
		} catch (Exception ex) {
			LogHandler.errorLog(logger, ex);
		}
		return SKIP_BODY;
	}


	public int doEndTag() {
		return EVAL_PAGE;
	}


	// ---------------------------------------------------------------- populate

	private String populateForm(String html, HashMap values) {

		int i = 0, s = 0;
		StringBuffer result = new StringBuffer(html.length());
		String currentSelectName = null;
		while (true) {
			// find starting tag
			i =	html.indexOf('<', s);
			if (i == -1) {
				result.append(html.substring(s));
				break;		// input tag not found
			}
			result.append(html.substring(s, i));	// tag found, all before tag is stored
			s = i;

			// find closing tag
			i =	html.indexOf('>', i);
			if (i == -1) {
				result.append(html.substring(s));
				break;		// closing tag not found
			}
			i++;

			// match tags
			String tag = html.substring(s, i);
			String tagName = ECSideFormTagUtil.getTagName(tag);

			if (tagName.equalsIgnoreCase("input") == true) {
				String tagType = ECSideFormTagUtil.getAttribute(tag, "type");
				if (tagType != null) {
					String name = ECSideFormTagUtil.getAttribute(tag, "name");
					if (values.containsKey(name)) {
						String value = ECSideFormTagUtil.toString(values.get(name));
						tagType = tagType.toLowerCase();
						if (tagType.equals("text")) {
							tag = ECSideFormTagUtil.addAttribute(tag, "value", value);
						} if (tagType.equals("hidden")) {
							tag = ECSideFormTagUtil.addAttribute(tag, "value", value);
						} if (tagType.equals("image")) {
							tag = ECSideFormTagUtil.addAttribute(tag, "value", value);
						} if (tagType.equals("password")) {
							tag = ECSideFormTagUtil.addAttribute(tag, "value", value);
						} if (tagType.equals("checkbox")) {
							String tagValue = ECSideFormTagUtil.getAttribute(tag, "value");
							if (tagValue == null) {
								tagValue = "true";
							}
							if (tagValue.equals(value)) {
								tag = ECSideFormTagUtil.addAttribute(tag, "checked");
							}
						} if (tagType.equals("radio")) {
							String tagValue = ECSideFormTagUtil.getAttribute(tag, "value");
							if (tagValue != null) {
								if (tagValue.equals(value)) {
									tag = ECSideFormTagUtil.addAttribute(tag, "checked");
								}
							}
						}
					}
				}
			} else if (tagName.equalsIgnoreCase("textarea") == true) {
				String name = ECSideFormTagUtil.getAttribute(tag, "name");
				if (values.containsKey(name)) {
					Object value = values.get(name);
					if (value != null) {
						//tag += ServletUtil.encodeHtml(StringUtil.toString(value));
						tag += ECSideFormTagUtil.encode(ECSideFormTagUtil.toString(value));
					}
				}
			} else if (tagName.equalsIgnoreCase("select") == true) {
				currentSelectName = ECSideFormTagUtil.getAttribute(tag, "name");
			} else if (tagName.equalsIgnoreCase("/select") == true) {
				currentSelectName = null;
			} else if (tagName.equalsIgnoreCase("option") == true) {
				if (currentSelectName != null) {
					String tagValue = ECSideFormTagUtil.getAttribute(tag, "value");
					if (tagValue != null) {
						if (values.containsKey(currentSelectName)) {
							Object vals = values.get(currentSelectName);
							if (vals != null) {
								if (vals.getClass().isArray() == false) {
									String value = ECSideFormTagUtil.toString(vals);
									if (value.equals(tagValue)) {
										tag = ECSideFormTagUtil.addAttribute(tag, "selected");
									}
								} else {
									String vs[] = ECSideFormTagUtil.convertToStringArray(vals);
									for (int k = 0; k < vs.length; k++) {
										String vsk = vs[k];
										if (vsk != null) {
											if (vsk.equals(tagValue)) {
												tag = ECSideFormTagUtil.addAttribute(tag, "selected");
											}
										}
									}
								}
							}
						}
					}
				}
			}
			result.append(tag);
			s = i;
		}
		return result.toString();
	}
	

	
}
