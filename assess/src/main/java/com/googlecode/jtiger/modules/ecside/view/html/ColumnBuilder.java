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
package com.googlecode.jtiger.modules.ecside.view.html;

import org.apache.commons.lang.StringUtils;

import com.googlecode.jtiger.modules.ecside.core.ECSideConstants;
import com.googlecode.jtiger.modules.ecside.core.TableConstants;
import com.googlecode.jtiger.modules.ecside.core.bean.Column;
import com.googlecode.jtiger.modules.ecside.util.ECSideUtils;
import com.googlecode.jtiger.modules.ecside.util.HtmlBuilder;



/**
 * @author Wei Zijun
 *
 */

public class ColumnBuilder {
    private HtmlBuilder html;
    private Column column;
    
    public ColumnBuilder(Column column) {
        this(new HtmlBuilder(), column);
    }

    public ColumnBuilder(HtmlBuilder html, Column column) {
    	this.html=html;
    	this.column = column;
    }

    public void cellEdit(){
    	String cellValue=column.getPropertyValueAsString();
	
		if (StringUtils.isNotBlank(cellValue)){
    		html.append(" cellValue=\"").append(cellValue.replaceAll("\"","\\\"")).append("\" ");
		}

//		
		html.attribute(column.getEditEvent(), ECSideConstants.UTIL_FUNCTION_NAME+".editCell(this,'"+column.getModel().getTable().getTableId()+"')");

    	
//		if (StringUtils.isNotBlank(cellName)){
//			html.append(" cellName=\"").append(cellName).append("\" ");
//		}
    	
    }
    
    public void tdStart() {

        html.td(2);
        
        if (StringUtils.isNotBlank(column.getId())){
        	html.append(" id=\""+column.getId()+"\" ");
        }
        
        styleClass();
        style();
        width();
        onclick();
        ondblclick();
        onmouseover();
        onmouseout();
        if (StringUtils.isNotBlank(column.getNowrap())){
        	html.append(" nowrap=\""+column.getNowrap()+"\" ");
        }
        if (StringUtils.isNotBlank(column.getTipTitle())){
        	html.append(" title=\""+column.getTipTitle()+"\" ");
        }
        
        
        if(column.isEditable()){
        	cellEdit();
        } else{
        	String cellValue=column.getCellValue();
        	if (StringUtils.isNotBlank(cellValue)){
            	html.append(" cellValue=\"").append(cellValue.replaceAll("\"","\\\"")).append("\" ");
        	}
        }
              
        html.append(" ").append(ECSideUtils.nullToBlank(column.getTagAttributes())).append(" ");
        String extendAttribute=(String)column.getAttribute(TableConstants.EXTEND_ATTRIBUTES);
        if (extendAttribute!=null){
        	html.append(extendAttribute.replaceAll("\r\n", " "));
        }

        
        html.close();

    }

    public void onclick() {
        String onclick = column.getOnclick();
        if (onclick==null || onclick.length()<1) return;
        html.append(" "+TableConstants.ON_CLICK+"=\""+onclick+"\" ");
    }
    
    public void ondblclick() {
        String ondblclick = column.getOndblclick();
        if (ondblclick==null || ondblclick.length()<1) return;
        html.append(" "+TableConstants.ON_DOUBLE_CLICK+"=\""+ondblclick+"\" ");
    }
    
    public void onmouseover() {
        String onmouseover = column.getOnmouseover();
        if (StringUtils.isBlank(onmouseover)) return;
        html.append(" "+TableConstants.ON_MOUSE_OVER+"=\""+onmouseover+"\" ");
    }
    
    public void onmouseout() {
        String onmouseout = column.getOnmouseout();
        if (StringUtils.isBlank(onmouseout)) return;
        html.append(" "+TableConstants.ON_MOUSE_OUT+"=\""+onmouseout+"\" ");
    }
    
    
    public HtmlBuilder getHtmlBuilder() {
        return html;
    }

    protected Column getColumn() {
        return column;
    }

    public void tdEnd() {
        html.tdEnd();
    }

    public void style() {
        String style = column.getStyle();
        html.style(style);
    }

    public void styleClass() {
        String styleClass = column.getStyleClass();
        html.styleClass(styleClass);
    }

    public void width() {
        String width = column.getWidth();
        html.width(width);
    }

    public void tdBody(String value) {
        if (StringUtils.isNotBlank(value)) {
            html.append(value);
        } else {
            html.nbsp();
        }
    }
    
    public String toString() {
        return html.toString();
    }
}
