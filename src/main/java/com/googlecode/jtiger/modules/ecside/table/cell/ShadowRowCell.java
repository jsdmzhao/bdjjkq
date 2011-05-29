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

import com.googlecode.jtiger.modules.ecside.core.ECSideConstants;
import com.googlecode.jtiger.modules.ecside.core.TableModel;
import com.googlecode.jtiger.modules.ecside.core.bean.Column;
import com.googlecode.jtiger.modules.ecside.util.HtmlBuilder;
import com.googlecode.jtiger.modules.ecside.view.html.ColumnBuilder;


/**
 * @author Wei Zijun
 *
 */

public class ShadowRowCell implements Cell  {
	

    public String getHtmlDisplay(TableModel model, Column column) {
    	column.setEditable(Boolean.FALSE);
    	column.setSortable(Boolean.FALSE);
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
    	HtmlBuilder html=new HtmlBuilder();
		html.span().id("srb_"+model.getRowHandler().getRow().getId()).styleClass("shadowRowButtonClose");
		html.onclick(ECSideConstants.UTIL_FUNCTION_NAME + ".showShadowRow(this.parentNode.parentNode,this,'"+ model.getTable().getTableId() + "');");
		html.close();
		html.append("&#160;");

		html.spanEnd();
		
		return html.toString();

    }
}
