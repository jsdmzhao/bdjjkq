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
import com.googlecode.jtiger.modules.ecside.core.TableModel;
import com.googlecode.jtiger.modules.ecside.core.bean.Row;
import com.googlecode.jtiger.modules.ecside.core.bean.Table;
import com.googlecode.jtiger.modules.ecside.util.ECSideUtils;
import com.googlecode.jtiger.modules.ecside.util.HtmlBuilder;


/**
 * @author Wei Zijun
 *
 */

public class RowBuilder {
    
    private HtmlBuilder html;
    private TableModel model;
    private Row row;
    
    public RowBuilder(TableModel model) {
       this(new HtmlBuilder(),model);
    }

    public RowBuilder(HtmlBuilder html, TableModel model) {
        this.html = html;
        this.model = model;
        this.row = model.getRowHandler().getRow();
    }


    public void rowStart() {
        html.tr(0);
        if (StringUtils.isNotBlank(row.getId())){
        	html.append(" id=\""+row.getId()+"\" ");
        }
        styleClass();
        style();
        onclick();
        ondblclick();
        onmouseover();
        onmouseout();
        
        html.append(" ").append(ECSideUtils.nullToBlank(row.getTagAttributes())).append(" ");
        html.append(row.getAttribute(TableConstants.EXTEND_ATTRIBUTES));
        
        String recordKey=row.getRecordKey();
        if (StringUtils.isNotBlank(recordKey)){
        	html.append(" recordKey=\"").append(recordKey).append("\" ");
        }
        html.close();
    }

    public void ondblclick() {
        String ondblclick = row.getOndblclick();
        if (ondblclick==null || ondblclick.length()<1) return;
        html.append(" "+TableConstants.ON_DOUBLE_CLICK+"=\""+ondblclick+"\" ");
    }
    
    
    
    public HtmlBuilder getHtmlBuilder() {
        return html;
    }
    
    public void setHtmlBuilder(HtmlBuilder html) {
        this.html=html;
    }

    protected TableModel getTableModel() {
        return model;
    }

    protected Row getRow() {
        return row;
    }

    public void rowEnd() {
        html.trEnd(1);
    }

    public void style() {
        String style = row.getStyle();
        html.style(style);
    }

    public void styleClass() {
        String styleClass = ECSideUtils.convertString(getStyleClass(), "");
        Table table=model.getTable();
        String oddBgcolor=table.getOddRowBgColor();
        String evenBgcolor=table.getEvenRowBgColor();
        
        if (model.getRowHandler().isRowEven()){
	        if (StringUtils.isNotBlank(evenBgcolor)){
	        	html.append(" bgcolor=\""+evenBgcolor+"\" ");
	        }else{
	        	styleClass+=BuilderConstants.ROW_EVEN_CSS+" ";
	        }
        } else {

			if (StringUtils.isNotBlank(oddBgcolor)) {
				html.append(" bgcolor=\"" + oddBgcolor + "\" ");
			} else {
				styleClass += BuilderConstants.ROW_ODD_CSS+" ";
			}
		}
        
        html.styleClass(styleClass);
    }

    public void onclick() {
    	String onclick="";
    	if (row.getSelectlightRow().booleanValue()){
    		onclick+=ECSideConstants.UTIL_FUNCTION_NAME+".selectRow(this,'"+model.getTable().getTableId()+"');";
    	}
    	if (StringUtils.isNotBlank(row.getOnclick())){
    		onclick+=row.getOnclick();
    	}
        html.onclick(onclick);
    }

    public void onmouseover() {
        boolean highlightRow = row.isHighlightRow();
        if (highlightRow) {
              html.onmouseover(ECSideConstants.UTIL_FUNCTION_NAME+".lightRow(this,'"+model.getTable().getTableId()+"');" + ECSideUtils.convertString(row.getOnmouseover(),"") );
        } else {
            html.onmouseover(row.getOnmouseover());
        }
    }

    public void onmouseout() {
        boolean highlightRow = row.isHighlightRow();
        if (highlightRow) {
            html.onmouseout(ECSideConstants.UTIL_FUNCTION_NAME+".unlightRow(this,'"+model.getTable().getTableId()+"');" + ECSideUtils.convertString(row.getOnmouseout(),""));
        } else {
            html.onmouseout(row.getOnmouseout());
        }
    }

    protected String getStyleClass() {
        String styleClass = row.getStyleClass();
        if (StringUtils.isNotBlank(styleClass)) {
            return styleClass;
        }
        return null;

    }
    
    public String toString() {
        return html.toString();
    }
}
