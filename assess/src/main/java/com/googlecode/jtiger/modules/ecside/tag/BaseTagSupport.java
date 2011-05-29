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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.jsp.tagext.TagSupport;

import com.googlecode.jtiger.modules.ecside.core.TableModel;
import com.googlecode.jtiger.modules.ecside.core.bean.BaseBean;


/**
 * @author Wei Zijun
 *
 */

@SuppressWarnings("unchecked")
public abstract class BaseTagSupport extends TagSupport implements BaseTag {


	private static final long serialVersionUID = 1L;
	
	protected TableModel model;
	   
		protected String name=null;
	    protected String tagAttributes=null;
	    protected Object value=null;
	    protected String elementId=null;
	    protected BaseBean bean=null;
	    
	    protected Map attributes=null;
	 
	    protected ArrayList extendAttributs = null ;

		public String getExtendAttributesAsString() {
			if (extendAttributs==null) return " ";
			StringBuffer attributsRS = new StringBuffer(" ");
			Iterator it = extendAttributs.iterator();
			while (it.hasNext()) {
				attributsRS.append(it.next()).append(" ");
			}
			return attributsRS.toString();
		}

		public void addExtendAttribute(String value) {
			if (extendAttributs==null){
				extendAttributs=new ArrayList();
			}
			extendAttributs.add(value);
		}

		public void resetExtendAttribute() {
			extendAttributs = new ArrayList();
		}
		
	    
	    public TableModel getModel() {
	        return model;
	    }
	    
		public Object getBeanAttribute(String attributeName) {
			return bean==null?null:bean.getAttribute(attributeName);
		}


		public String getTagAttributes() {
			return tagAttributes;
		}

		public void setTagAttributes(String tagAttributes) {
			this.tagAttributes = tagAttributes;
		}

		public String getName() {
			return name;
		}

		public Object getValue() {
			return value;
		}

		public void setBeanAttribute(String attributeName, Object attributeValue) {
			if (bean==null){
				// 新建
			}
			bean.setAttribute(attributeName, attributeValue);
		}



		public void setName(String name) {
			this.name=name;		
		}

		public void setValue(Object value) {
			this.value=value;		
		}

		public String getElementId() {
			return elementId;
		}

		public void setElementId(String elementId) {
			this.elementId=elementId;		
		}
		
		public Object getAttribute(String attributeName) {
			return attributes==null?null:attributes.get(attributeName);
		}

		public void setAttribute(String attributeName, Object attributeValue) {
			if (attributes==null){
				attributes=new HashMap();
			}
			attributes.put(attributeName, attributeValue);
		}
		
		public void release() {
			name=null;
			tagAttributes=null;
		    value=null;
		    elementId=null;
		    bean=null;
		    attributes=null;
		    extendAttributs=null;
		    model=null;
			super.release();
		}

		public void resetAttribute() {
			attributes=null;
		}
		
		public abstract BaseBean getTagBean();

		public void doCatch(Throwable throwable) throws Throwable {
			// TODO Auto-generated method stub
			
		}

		public void doFinally() {
			// TODO Auto-generated method stub
			
		}
}
