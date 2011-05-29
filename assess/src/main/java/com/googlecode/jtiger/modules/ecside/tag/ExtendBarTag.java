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
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;

import com.googlecode.jtiger.modules.ecside.core.TableModel;
import com.googlecode.jtiger.modules.ecside.util.ExceptionUtils;


/**
 * @author Wei Zijun
 *
 */

public class ExtendBarTag extends BodyTagSupport   {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String before;


	protected Object getBodyValue() throws JspException {
        Object result = getBodyContent().getString();
        if (result != null) {
            result = ExpressionEvaluatorManager.evaluate("extendBarResult", result.toString(), Object.class, this, pageContext);
        }
        return result;
    }


    public int doAfterBody() throws JspException {
        try {
            TableModel model = TagUtils.getModel(this);

            if ("top".equalsIgnoreCase(before)) {
            	model.getTable().setAttribute("ExtendBarTop", getBodyValue());
            }else if ("true".equalsIgnoreCase(before)) {
            	model.getTable().setAttribute("ExtendBarBefore", getBodyValue());
            }else{
            	model.getTable().setAttribute("ExtendBarAfter", getBodyValue());
            }

        } catch (Exception e) {
            throw new JspException("TDExtendTag.doAfterBody() Problem: " + ExceptionUtils.formatStackTrace(e));
        }

        return SKIP_BODY;
    }



    public void release() {

        super.release();
    }


	public String getBefore() {
		return before;
	}


	public void setBefore(String before) {
		this.before = before;
	}

}