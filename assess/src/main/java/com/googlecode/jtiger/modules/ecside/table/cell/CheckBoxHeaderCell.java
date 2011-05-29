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

import com.googlecode.jtiger.modules.ecside.core.ECSideConstants;
import com.googlecode.jtiger.modules.ecside.core.TableModel;
import com.googlecode.jtiger.modules.ecside.core.bean.Column;
import com.googlecode.jtiger.modules.ecside.preferences.PreferencesConstants;
import com.googlecode.jtiger.modules.ecside.util.HtmlBuilder;

/**
 * @author Wei Zijun
 * 
 */

public class CheckBoxHeaderCell implements Cell {
	public String getExportDisplay(TableModel model, Column column) {
		// return column.getTitle();
		return "";
	}

	public String getHtmlDisplay(TableModel model, Column column) {
		HtmlBuilder html = new HtmlBuilder();
		String tableId = model.getTable().getTableId();
		String selectableControlName = column.getAlias();

		
		html.td(2).valign("middle").append(" columnName=\"").append(column.getAlias()).append("\" ");
		if ("true".equalsIgnoreCase(column.getGroup())) {
			html.append(" group=\"true\" ");
		}

		if (StringUtils.isNotEmpty(column.getWidth())) {
			html.width(column.getWidth());
		}
		
		String headerClass=column.getHeaderClass();

		StringBuffer styleClass=new StringBuffer();
		if (StringUtils.isNotEmpty(headerClass)) {
			styleClass.append(headerClass);
		}

		if (StringUtils.isNotEmpty(column.getHeaderStyleClass())) {
			styleClass.append(" ").append(column.getHeaderStyleClass());
		}
		
		html.styleClass(styleClass.toString().trim());
		
			
			
			if (StringUtils.isNotEmpty(column.getHeaderStyle())) {
				html.style(column.getHeaderStyle());
			}
			
			
			html.onmouseover(ECSideConstants.UTIL_FUNCTION_NAME+".lightHeader(this,'"+tableId+"');" );
			html.onmouseout(ECSideConstants.UTIL_FUNCTION_NAME+".unlightHeader(this,'"+tableId+"');" );

			
			
			html.close();	



			html.span();
			String cstyle = "columnSeparator";
			html.styleClass(cstyle);
			html.close().append("&#160;");
			html.spanEnd();
     
		html.div().styleClass("headerTitle").close();
			html.span().styleClass("checkboxHeader");
			html.title(model.getMessages().getMessage(PreferencesConstants.HEAD_TIP_CHECKALL));
			html.onclick(ECSideConstants.UTIL_FUNCTION_NAME + ".checkAll(this,'" + selectableControlName + "','"	+ tableId + "');");
			html.close();
			html.append("&#160;");
//		
//		html.input("checkbox");
//		if (column.getStyleClass() != null) {
//			html.styleClass(column.getStyleClass());
//		} else {
////			html.styleClass("checkboxHeader");
//		}
//
//		html.xclose();
//		
		
		
		

		html.spanEnd();
		//html.append(column.getTitle());
		html.divEnd();

		html.tdEnd();
		

		return html.toString();
	}
}
