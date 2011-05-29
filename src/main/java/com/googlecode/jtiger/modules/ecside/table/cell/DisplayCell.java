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
package com.googlecode.jtiger.modules.ecside.table.cell;

import org.apache.commons.lang.StringUtils;

import com.googlecode.jtiger.modules.ecside.core.TableModel;
import com.googlecode.jtiger.modules.ecside.core.bean.Column;
import com.googlecode.jtiger.modules.ecside.util.ECSideUtils;
import com.googlecode.jtiger.modules.ecside.view.html.ColumnBuilder;


/**
 * @author Wei Zijun
 *
 */

public class DisplayCell implements Cell  {
	
    public String getHtmlDisplay(TableModel model, Column column) {
    	ColumnBuilder columnBuilder = new ColumnBuilder(column);
        columnBuilder.tdStart();
        columnBuilder.tdBody(getCellValue(model, column));
        columnBuilder.tdEnd();
        return columnBuilder.toString();
    }
    
    public String getExportDisplay(TableModel model, Column column) {
        return column.getPropertyValueAsString();
    }

    protected String getCellValue(TableModel model, Column column) {
    	boolean useEllipsis=column.getEllipsis()==null?false:column.getEllipsis().booleanValue();
    	
    	if (useEllipsis){
    		StringBuffer cellHtml=new StringBuffer();

    		cellHtml.append("<div class=\"ellipsis\" ");
    		String width=column.getWidth();
    		if (StringUtils.isNotBlank(width)&& !width.endsWith("%")){
    			cellHtml.append(" style=\"width:"+width+"px\" ");
    		}
    		cellHtml.append("title=\"").append(ECSideUtils.HTMLToTEXT(column.getValueAsString())).append("\" >");
    		cellHtml.append(column.getValueAsString());
    		cellHtml.append("</div>");
    		return cellHtml.toString();
    	}
        return column.getValueAsString();
    }
}
