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

import javax.servlet.jsp.JspException;

import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;

import com.googlecode.jtiger.modules.ecside.core.TableConstants;
import com.googlecode.jtiger.modules.ecside.core.bean.BaseBean;
import com.googlecode.jtiger.modules.ecside.util.ExceptionUtils;


/**
 * @author Wei Zijun
 *
 */

public class AttributeTag extends BaseBodyTagSupport  {

	private static final long serialVersionUID = 1L;

	protected Object getBodyValue() throws JspException {
        Object result = getBodyContent().getString();

        return result;
    }

    public int doEndTag() throws JspException {
        try {
        		BaseTag tag = (BaseTag)this.getParent();

	        	Object result=getBodyValue();

	            if (result != null) {
	                result = ExpressionEvaluatorManager.evaluate("attributeResult", result.toString(), Object.class, this, pageContext);
	            }

	            	BaseBean bean=tag.getTagBean();
	            	if (bean!=null) {
//		            	StringBuffer sb=new StringBuffer(ECSideUtils.nullToBlank((String)bean.getAttribute(TDTableConstants.EXTEND_ATTRIBUTES)));
//		            	sb.append(" ").append((String)result);
//		            	bean.setAttribute(TDTableConstants.EXTEND_ATTRIBUTES,sb.toString());
	            		bean.setAttribute(TableConstants.EXTEND_ATTRIBUTES,result);
	            		
	            	}
           
	            if (result!=null){
	            	getBodyContent().clearBody();
	            	
	            }
        } catch (Exception e) {
            throw new JspException("AttributeTag.doEndTag() Problem: " + ExceptionUtils.formatStackTrace(e));
        }

        return EVAL_PAGE;
    }



    public void release() {

        super.release();
    }

	public BaseBean getTagBean() {
		return null;
	}




}