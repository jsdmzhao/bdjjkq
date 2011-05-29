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
package com.googlecode.jtiger.modules.ecside.tag;

import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.googlecode.jtiger.modules.ecside.common.HTMLOptionsUtil;
import com.googlecode.jtiger.modules.ecside.util.ExceptionUtils;

/**
 * @author Wei Zijun
 *
 */

@SuppressWarnings("unchecked")
public class OptionsTag extends TagSupport {

	private static final long serialVersionUID = 1L;
	private String items;
    private Object defaultKey;
    protected String tagAttributes=null;



    public int doStartTag() throws JspException {
        try {

            Object outItems=pageContext.findAttribute(items);
            if (outItems!=null && outItems instanceof Map){
            	 JspWriter w = pageContext.getOut();
            	 w.write(HTMLOptionsUtil.getOptionsList((Map)outItems, defaultKey, tagAttributes));
            }
        } catch (Exception e) {
            throw new JspException("MappingTag.doStartTag() Problem: " + ExceptionUtils.formatStackTrace(e));
        }

        return SKIP_BODY;
    }

    public void release() {
    	items = null;
    	defaultKey = null;
    	tagAttributes=null;
        super.release();
    }


	public String getItems() {
		return items;
	}

	public void setItems(String items) {
		this.items = items;
	}

	public Object getDefaultKey() {
		return defaultKey;
	}

	public void setDefaultKey(Object defaultKey) {
		this.defaultKey = defaultKey;
	}
	
	public String getTagAttributes() {
		return tagAttributes;
	}

	public void setTagAttributes(String tagAttributes) {
		this.tagAttributes = tagAttributes;
	}
}
