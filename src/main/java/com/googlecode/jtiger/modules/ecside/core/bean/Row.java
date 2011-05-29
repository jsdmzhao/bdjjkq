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

/**
 * @author Wei Zijun
 *
 */

@SuppressWarnings("serial")
public class Row extends BaseBean {

    private String highlightClass;
    private Boolean highlightRow;
    private String interceptor;
    private String onclick;
    private String ondblclick;
    private String onmouseout;
    private String onmouseover;
    private String style;
    private String styleClass;
    
    private String recordKey;

    private int rowCount;
    
    private String selectlightClass;
    private Boolean selectlightRow;

    public Row(TableModel model) {
       setModel(model);
    }

    public void defaults() {
        this.highlightClass = RowDefaults.getHighlightClass(model, highlightClass);
        this.highlightRow = RowDefaults.isHighlightRow(model, highlightRow);
        this.selectlightClass = RowDefaults.getSelectlightClass(model, selectlightClass);
        this.selectlightRow = RowDefaults.isSelectlightRow(model, selectlightRow);
    }

    public String getHighlightClass() {
        return highlightClass;
    }

    public void setHighlightClass(String highlightClass) {
        this.highlightClass = highlightClass;
    }

    public boolean isHighlightRow() {
        return highlightRow.booleanValue();
    }

    public void setHighlightRow(Boolean highlightRow) {
        this.highlightRow = highlightRow;
    }
    
    public String getInterceptor() {
        return interceptor;
    }

    public void setInterceptor(String interceptor) {
        this.interceptor = interceptor;
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

    public int getRowCount() {
        return rowCount;
    }

    public void setRowCount(int rowcount) {
        this.rowCount = rowcount;
    }

	public String getRecordKey() {
		return recordKey;
	}

	public void setRecordKey(String recordKey) {
		this.recordKey = recordKey;
	}

	public String getSelectlightClass() {
		return selectlightClass;
	}

	public void setSelectlightClass(String selectlightClass) {
		this.selectlightClass = selectlightClass;
	}

	public Boolean getSelectlightRow() {
		return selectlightRow;
	}

	public void setSelectlightRow(Boolean selectlightRow) {
		this.selectlightRow = selectlightRow;
	}
	
    public void addStyle(String style) {
    	if (StringUtils.isNotBlank(getStyle()) ){
    		setStyle(getStyle()+";"+style);
    	}else{
    		setStyle(style);
    	}
    }
    public void addStyleClass(String styleClass) {
    	if (StringUtils.isNotBlank(getStyleClass()) ){
    		setStyleClass(getStyleClass()+" "+styleClass);
    	}else{
    		setStyleClass(styleClass);
    	}
    }
}
