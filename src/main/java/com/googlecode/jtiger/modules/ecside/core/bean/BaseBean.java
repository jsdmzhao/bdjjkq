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
package com.googlecode.jtiger.modules.ecside.core.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.googlecode.jtiger.modules.ecside.core.TableModel;


/**
 * @author Wei Zijun
 *
 */

@SuppressWarnings({ "unchecked", "serial" })
public class BaseBean implements Serializable {
   
	protected Map attributes = new HashMap();
    protected TableModel model;
    protected String tagAttributes=null;
    
    protected String id=null;

    private HashMap expressionMap=new HashMap();
    private HashMap expressionPropertysMap=new HashMap();
    
    public GirdExpression getExpression(String name){
    	return (GirdExpression)expressionMap.get(name);
    }

    public void addExpression(String name, GirdExpression expression){
    	expressionMap.put(name,expression);
    }
    
    public Object[] getExpressionPropertys(String name){
    	return (Object[])expressionPropertysMap.get(name);
    }

    public void addExpressionPropertys(String name, Object[] expressionPropertys){
    	expressionPropertysMap.put(name,expressionPropertys);
    }
    
    public String getTagAttributes() {
		return tagAttributes;
	}

	public void setTagAttributes(String tagAttributes) {
		this.tagAttributes = tagAttributes;
	}

	public Object getAttribute(String key) {
        return attributes.get(key);
    }

    public String getAttributeAsString(String key) {
        Object value = attributes.get(key);
        if (value != null) {
            return String.valueOf(value);
        }

        return null;
    }

    public int getAttributeAsInt(String key) {
        Object value = attributes.get(key);
        if (value != null) {
            return Integer.parseInt(String.valueOf(value));
        }

        return 0;
    }


    public void setAttribute(String key, Object value) {
    	attributes.put(key, value);
    }
    public void addAttribute(String key, Object value) {
    	setAttribute(key, value);
    }

    
	public TableModel getModel() {
		return model;
	}

	public void setModel(TableModel model) {
		this.model = model;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
