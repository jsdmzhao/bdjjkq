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
import javax.servlet.jsp.tagext.TagSupport;

import com.googlecode.jtiger.modules.ecside.core.TableConstants;
import com.googlecode.jtiger.modules.ecside.core.bean.BaseBean;
import com.googlecode.jtiger.modules.ecside.core.bean.Td;
import com.googlecode.jtiger.modules.ecside.core.bean.Tr;


/**
 * @author Wei Zijun
 *
 */

/**
 * @jsp.tag name="row" display-name="RowTag" body-content="JSP" description="The
 *          container which holds all the row specific information."
 * 
 */

public class TdTag extends BaseBodyTagSupport {

 	private static final long serialVersionUID = 1L;

    private String onclick;
    private String ondblclick;
    private String onmouseout;
    private String onmouseover;
    private String style;
    private String styleClass;
    private String id;
    private String type;
    private String colspan;
    private String rowspan;
    private String nowrap;
    private String align;
    private String valign;
    private String width;
    private String height;

    private Td tdBean;

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

	protected Object getBodyValue() throws JspException {
		if (getBodyContent()==null) return null;
        Object result = getBodyContent().getString();
        return result;
    }

    public int doStartTag() throws JspException {
    	model = TagUtils.getModel(this);
    	tdBean=new Td(model);
    	return EVAL_BODY_BUFFERED;
    }

    public int doEndTag() throws JspException {
      
//        	if (!TagUtils.isIteratingBody(this)) {
	        	tdBean.setType(TagUtils.evaluateExpressionAsString("type", type, this, pageContext));
	        	tdBean.setId(TagUtils.evaluateExpressionAsString("id", id, this, pageContext));
	    		tdBean.setName(TagUtils.evaluateExpressionAsString("name", name, this, pageContext));
	        	tdBean.setOnclick(TagUtils.evaluateExpressionAsString("onclick", onclick, this, pageContext));
	        	tdBean.setOndblclick(TagUtils.evaluateExpressionAsString("ondblclick", ondblclick, this, pageContext));
	        	tdBean.setOnmouseout(TagUtils.evaluateExpressionAsString("onmouseout", onmouseout, this, pageContext));
	        	tdBean.setOnmouseover(TagUtils.evaluateExpressionAsString("onmouseover", onmouseover, this, pageContext));
	            
	        	tdBean.setTagAttributes(TagUtils.evaluateExpressionAsString(TableConstants.TAG_ATTRIBUTES, this.tagAttributes, this, pageContext));
	            tdBean.setStyle(TagUtils.evaluateExpressionAsString("style", style, this, pageContext));
	            tdBean.setStyleClass(TagUtils.evaluateExpressionAsString("styleClass", styleClass, this, pageContext));
	            tdBean.setColspan(TagUtils.evaluateExpressionAsInt("colspan", colspan, this, pageContext));
	            tdBean.setRowspan(TagUtils.evaluateExpressionAsInt("rowspan", rowspan, this, pageContext));
	            tdBean.setNowrap(TagUtils.evaluateExpressionAsString("nowrap", nowrap, this, pageContext));
	            
	            tdBean.setWidth(TagUtils.evaluateExpressionAsString("width", this.width, this, pageContext));
	            tdBean.setHeight(TagUtils.evaluateExpressionAsString("height", this.height, this, pageContext));
	            tdBean.setAlign(TagUtils.evaluateExpressionAsString("align", this.align, this, pageContext));
	            tdBean.setValign(TagUtils.evaluateExpressionAsString("valign", this.valign, this, pageContext));  
	            tdBean.setContent((String)getBodyValue());
	            
	            TrTag trTag = (TrTag) TagSupport.findAncestorWithClass(this, TrTag.class);
	            if (trTag!=null){
	            	Tr trBean=trTag.getTrBean();
	            	trBean.addTd(tdBean);
	            }
//        	}

        return EVAL_PAGE;
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
        tdBean=null;
        super.release();
    }
    
	public BaseBean getTagBean() {
		return getTdBean();
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

	public Td getTdBean() {
		return tdBean;
	}

	public void setTdBean(Td tdBean) {
		this.tdBean = tdBean;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getNowrap() {
		return nowrap;
	}

	public void setNowrap(String nowrap) {
		this.nowrap = nowrap;
	}

	public String getAlign() {
		return align;
	}

	public void setAlign(String align) {
		this.align = align;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getValign() {
		return valign;
	}

	public void setValign(String valign) {
		this.valign = valign;
	}


}
