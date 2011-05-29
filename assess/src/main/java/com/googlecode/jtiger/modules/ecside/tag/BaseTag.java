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

import javax.servlet.jsp.tagext.TryCatchFinally;

import com.googlecode.jtiger.modules.ecside.core.TableModel;
import com.googlecode.jtiger.modules.ecside.core.bean.BaseBean;


/**
 * @author Wei Zijun
 *
 */

public interface BaseTag extends TryCatchFinally {

	    
	    public TableModel getModel();
	    
	    public BaseBean getTagBean();

	    public Object getBeanAttribute(String attributeName);

		public String getTagAttributes();

		public String getName();
		
		public Object getValue();

		public void setBeanAttribute(String attributeName, Object attributeValue);

		public void setTagAttributes(String tagAttributes);

		public void setName(String name);
		
		public void setValue(Object value);
		
		public String getElementId();

		public void setElementId(String elementId);
		
		public Object getAttribute(String attributeName);

		public void setAttribute(String attributeName, Object attributeValue);
		
		public void resetAttribute();
		
		public String getExtendAttributesAsString() ;
		
		public void addExtendAttribute(String value) ;



}
