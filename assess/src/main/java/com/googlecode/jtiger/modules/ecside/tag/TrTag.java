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
import java.util.List;

import javax.servlet.jsp.JspException;

import org.apache.commons.lang.StringUtils;

import com.googlecode.jtiger.modules.ecside.core.TableConstants;
import com.googlecode.jtiger.modules.ecside.core.bean.BaseBean;
import com.googlecode.jtiger.modules.ecside.core.bean.Tr;
import com.googlecode.jtiger.modules.ecside.util.ExceptionUtils;


/**
 * @author Wei Zijun
 *
 */

/**
 * @jsp.tag name="row" display-name="RowTag" body-content="JSP" description="The
 *          container which holds all the row specific information."
 * 
 */

@SuppressWarnings("unchecked")
public class TrTag extends BaseBodyTagSupport {

 	private static final long serialVersionUID = 1L;

    private String onclick;
    private String ondblclick;
    private String onmouseout;
    private String onmouseover;
    private String style;
    private String styleClass;
    private String id;
    
    private String location;
    
    private String colspan;
    private String rowspan;

    private Tr trBean;

    public void setOnclick(String onclick) {
        this.onclick = onclick;
    }

	public String getOndblclick() {
		return ondblclick;
	}

	public void setOndblclick(String ondblclick) {
		this.ondblclick = ondblclick;
	}

    public void setOnmouseout(String onmouseout) {
        this.onmouseout = onmouseout;
    }


    public void setOnmouseover(String onmouseover) {
        this.onmouseover = onmouseover;
    }


    public void setStyle(String style) {
        this.style = style;
    }


    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }

    public int doStartTag() throws JspException {
        try {
        	model = TagUtils.getModel(this);
        		trBean=new Tr(model);
        		trBean.setId(TagUtils.evaluateExpressionAsString("id", id, this, pageContext));
        		trBean.setName(TagUtils.evaluateExpressionAsString("name", name, this, pageContext));
            	trBean.setOnclick(TagUtils.evaluateExpressionAsString("onclick", onclick, this, pageContext));
            	trBean.setOndblclick(TagUtils.evaluateExpressionAsString("ondblclick", ondblclick, this, pageContext));
            	trBean.setOnmouseout(TagUtils.evaluateExpressionAsString("onmouseout", onmouseout, this, pageContext));
            	trBean.setOnmouseover(TagUtils.evaluateExpressionAsString("onmouseover", onmouseover, this, pageContext));
                
            	trBean.setTagAttributes(TagUtils.evaluateExpressionAsString(TableConstants.TAG_ATTRIBUTES, this.tagAttributes, this, pageContext));
                trBean.setStyle(TagUtils.evaluateExpressionAsString("style", style, this, pageContext));
                trBean.setStyleClass(TagUtils.evaluateExpressionAsString("styleClass", styleClass, this, pageContext));

                
        } catch (Exception e) {
            throw new JspException("TrTag.doStartTag() Problem: " + ExceptionUtils.formatStackTrace(e));
        }
        
        return EVAL_BODY_INCLUDE;
    }

    public int doEndTag() throws JspException {
    	try{
    		if (!TagUtils.isIteratingBody(this)) {
		    	if (StringUtils.isBlank(location)|| location.equalsIgnoreCase("top")){
		    		location="Top";
		    	}else if (StringUtils.isBlank(location)|| location.equalsIgnoreCase("before")){
		    		location="Before";
		    	}else if (StringUtils.isBlank(location)|| location.equalsIgnoreCase("After")){
		    		location="After";
		    	}
		    	List trList=(List)model.getTable().getAttribute("ExtendTableTrList"+location);
		    	if (trList==null){
		    		trList=new ArrayList();
		    	}
		    	trList.add(trBean);
		    	model.getTable().setAttribute("ExtendTableTrList"+location, trList);
    		}
    } catch (Exception e) {
        throw new JspException("TrTag.doEndTag() Problem: " + ExceptionUtils.formatStackTrace(e));
    }
    	return super.doEndTag();
    }

    public void release() {

        onclick = null;
        ondblclick=null;
        onmouseout = null;
        onmouseover = null;
        style = null;
        styleClass = null;
        colspan = null;
        rowspan = null;
        trBean=null;
        super.release();
    }
    
	public BaseBean getTagBean() {
		return getTrBean();
	}

	public String getColspan() {
		return colspan;
	}

	public void setColspan(String colspan) {
		this.colspan = colspan;
	}

	public String getRowspan() {
		return rowspan;
	}

	public void setRowspan(String rowspan) {
		this.rowspan = rowspan;
	}

	public Tr getTrBean() {
		return trBean;
	}

	public void setTrBean(Tr trBean) {
		this.trBean = trBean;
	}

	public String getOnclick() {
		return onclick;
	}

	public String getOnmouseout() {
		return onmouseout;
	}

	public String getOnmouseover() {
		return onmouseover;
	}

	public String getStyle() {
		return style;
	}

	public String getStyleClass() {
		return styleClass;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}




}
