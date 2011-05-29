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

import org.apache.commons.lang.StringUtils;
import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;

import com.googlecode.jtiger.modules.ecside.util.ExceptionUtils;

/**
 * @author Wei Zijun
 *
 */

@SuppressWarnings("unchecked")
public class MappingTag extends TagSupport {

	private static final long serialVersionUID = 1L;

    protected String tagAttributes=null;

    private Object mappingItem;
    private Object mappingDefaultKey;
    private Object mappingDefaultValue;
    private Object value;


    
    public int doStartTag() throws JspException {
        try {
            
        	Object propertyValue = getValue();

        	
            if (propertyValue != null) {
            	propertyValue = ExpressionEvaluatorManager.evaluate("result", propertyValue.toString(), Object.class, this, pageContext);
            }

            if (mappingItem!=null ){
            	Object mappingMap=pageContext.findAttribute((String)mappingItem);
            	
                Object outValue=null;
                if (mappingMap instanceof Map){
                	Map itemsMap=(Map)mappingMap;
                	outValue=itemsMap.get(propertyValue);
                	if (outValue==null && mappingDefaultKey!=null){
                		outValue=itemsMap.get(mappingDefaultKey);
                	}
                }
                if (outValue==null){
                	outValue=mappingDefaultValue;
                }
                propertyValue=outValue;
            }
            
            JspWriter w = pageContext.getOut();
            if (propertyValue == null || (propertyValue != null && propertyValue instanceof String && StringUtils.isBlank((String)propertyValue))) {
            	w.write("");
            }else{
            	w.write((String)propertyValue);
            }
            

            
            	 
        } catch (Exception e) {
            throw new JspException("MappingTag.doStartTag() Problem: " + ExceptionUtils.formatStackTrace(e));
        }

        return SKIP_BODY;
    }

    public void release() {
    	mappingItem = null;
    	mappingDefaultKey = null;
    	mappingDefaultValue=null;
    	tagAttributes=null;
        super.release();
    }


	
	public String getTagAttributes() {
		return tagAttributes;
	}

	public void setTagAttributes(String tagAttributes) {
		this.tagAttributes = tagAttributes;
	}

	public Object getMappingDefaultKey() {
		return mappingDefaultKey;
	}

	public void setMappingDefaultKey(Object mappingDefaultKey) {
		this.mappingDefaultKey = mappingDefaultKey;
	}

	public Object getMappingDefaultValue() {
		return mappingDefaultValue;
	}

	public void setMappingDefaultValue(Object mappingDefaultValue) {
		this.mappingDefaultValue = mappingDefaultValue;
	}

	public Object getMappingItem() {
		return mappingItem;
	}

	public void setMappingItem(Object mappingItem) {
		this.mappingItem = mappingItem;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}
}
