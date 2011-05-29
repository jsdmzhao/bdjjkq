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

import org.apache.commons.lang.StringUtils;

import com.googlecode.jtiger.modules.ecside.core.TableModel;
import com.googlecode.jtiger.modules.ecside.util.HtmlBuilder;

/**
 * @author Wei Zijun
 *
 */

@SuppressWarnings("serial")
public class Td extends BaseBean {
	
	public static String buildTd(Td tdBean,TableModel model){
		HtmlBuilder html=new HtmlBuilder();
		if (tdBean.getType()!=null && tdBean.getType().equalsIgnoreCase("blank")){
			return html.toString();
		}
		html.td(1);
		html.id(tdBean.getId());
		html.name(tdBean.getName());
		if (tdBean.getColspan()>1){
			html.colSpan(""+tdBean.getColspan());
		}
		if (tdBean.getRowspan()>1){
			html.rowSpan(""+tdBean.getRowspan());
		}
        if (StringUtils.isNotBlank(tdBean.getNowrap())){
        	html.append(" nowrap=\""+tdBean.getNowrap()+"\" ");
        }
		html.style(tdBean.getStyle());
		html.styleClass(tdBean.getStyleClass());
		html.width(tdBean.getWidth());
		html.height(tdBean.getHeight());
		html.align(tdBean.getAlign());
		html.valign(tdBean.getValign());
		html.onclick(tdBean.getOnclick());
		html.ondblclick(tdBean.getOndblclick());
		html.onmouseover(tdBean.getOnmouseover());
		html.onmouseout(tdBean.getOnmouseout());
		html.tagAttributes(tdBean.getTagAttributes());
		html.close();
		html.append(tdBean.getContent());
		html.tdEnd();
		return html.toString();
	}

    private String onclick;
    private String ondblclick;
    private String onmouseout;
    private String onmouseover;
    private String style;
    private String styleClass;
    private String id;
    private String name;
    private String content;
    private String type;
    private int colspan;
    private int rowspan;
    private String nowrap;
    
    private String align;
    private String valign;
    private String width;
    private String height;
    

    public Td(TableModel model) {
       setModel(model);
    }


    public String getOnclick() {
        return onclick;
    }

    public void setOnclick(String onclick) {
        this.onclick = onclick;
    }

	public String getOndblclick() {
		return ondblclick;
	}

	public void setOndblclick(String ondblclick) {
		this.ondblclick = ondblclick;
	}
	
    public String getOnmouseout() {
        return onmouseout;
    }

    public void setOnmouseout(String onmouseout) {
        this.onmouseout = onmouseout;
    }

    public String getOnmouseover() {
        return onmouseover;
    }

    public void setOnmouseover(String onmouseover) {
        this.onmouseover = onmouseover;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getStyleClass() {
        return styleClass;
    }

    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }


	public int getColspan() {
		return colspan;
	}


	public void setColspan(int colspan) {
		this.colspan = colspan;
	}


	public int getRowspan() {
		return rowspan;
	}


	public void setRowspan(int rowspan) {
		this.rowspan = rowspan;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
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
