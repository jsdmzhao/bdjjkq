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

import com.googlecode.jtiger.modules.ecside.core.bean.BaseBean;
import com.googlecode.jtiger.modules.ecside.util.ExceptionUtils;

/**
 * @author Wei Zijun
 *
 */

/**
 * @jsp.tag name="parameter" display-name="ParameterTag" body-content="JSP"
 *          description="Append any attributes to the Sorting, Filtering,
 *          Pagination, and Form Submission. On the URL's will resolve to
 *          &name=value. On the <form>attribute will be added as hidden fields
 *          <input type=hidden name= value=>"
 * 
 */

public class ParameterTag extends BaseTagSupport {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
    private Object value;

    /**
     * @jsp.attribute description="The name of the parameter." required="true"
     *                rtexprvalue="true"
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @jsp.attribute description="The value of the parameter." required="false"
     *                rtexprvalue="true"
     */
    public void setValue(Object value) {
        this.value = value;
    }

    public int doEndTag() throws JspException {
        try {
            if (TagUtils.isIteratingBody(this)) {
                return EVAL_PAGE;
            }

            String name = TagUtils.evaluateExpressionAsString("name", this.name, this, pageContext);
            Object value = TagUtils.evaluateExpressionAsObject("value", this.value, this, pageContext);

            if (value == null) {
                value = pageContext.getRequest().getParameterValues(name);
            }

            TagUtils.getModel(this).addParameter(name, value);
            
        } catch (Exception e) {
            throw new JspException("ParameterTag.doEndTag() Problem: " + ExceptionUtils.formatStackTrace(e));
        }

        return EVAL_PAGE;
    }

    public void release() {
        name = null;
        value = null;
        super.release();
    }

	public BaseBean getTagBean() {
		return null;
	}

}
