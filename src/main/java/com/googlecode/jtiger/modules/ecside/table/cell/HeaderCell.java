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
import com.googlecode.jtiger.modules.ecside.core.MessagesConstants;
import com.googlecode.jtiger.modules.ecside.core.TableConstants;
import com.googlecode.jtiger.modules.ecside.core.TableModel;
import com.googlecode.jtiger.modules.ecside.core.TableModelUtils;
import com.googlecode.jtiger.modules.ecside.core.bean.Column;
import com.googlecode.jtiger.modules.ecside.util.HtmlBuilder;

/**
 * @author Wei Zijun
 * 
 */

public class HeaderCell implements Cell {

	public String getExportDisplay(TableModel model, Column column) {
		return column.getTitle();
	}

	public String getHtmlDisplay(TableModel model, Column column) {
		HtmlBuilder html = new HtmlBuilder();

		String headerClass = column.getHeaderClass();
		String sortDivCss = null;
		String sortOrder = null;


		if (TableModelUtils.isSorted(model, column.getAlias())) {
			sortOrder = model.getLimit().getSort().getSortOrder();

			if (sortOrder.equals(TableConstants.SORT_DEFAULT)) {
				sortOrder = TableConstants.SORT_ASC;
			} else if (sortOrder.equals(TableConstants.SORT_ASC)) {
//				headerClass = model.getPreferences().getPreference(
//						PreferencesConstants.TABLE_HEADER_SORT_CLASS);
				sortDivCss = "sortASC";
				sortOrder = TableConstants.SORT_DESC;
			} else if (sortOrder.equals(TableConstants.SORT_DESC)) {
//				headerClass = model.getPreferences().getPreference(
//						PreferencesConstants.TABLE_HEADER_SORT_CLASS);
				sortDivCss = "sortDESC";
				sortOrder = TableConstants.SORT_DEFAULT;
			}
		} else {
			sortOrder = TableConstants.SORT_ASC; // the default
		}

		buildHeaderHtml(html, model, column, headerClass, sortDivCss, sortOrder);

		return html.toString();
	}

	public void buildHeaderHtml(HtmlBuilder html, TableModel model,
			Column column, String headerClass, String sortDivCss,
			String sortOrder) {
		html.append(buildHeaderHtml(model, column, null, headerClass,
				sortDivCss, sortOrder));
	}

	public static String buildHeaderHtml(TableModel model, Column column,
			String content, String headerClass, String sortDivCss,
			String sortOrder) {
		
		HtmlBuilder html = new HtmlBuilder();
		if (content == null) {
			content = column.getTitle();
		}

		
		boolean canResizeColWidth = model.getTable().isResizeColWidth() && column.isResizeColWidth() ;

		int spanNum = column.getHeaderSpan();
		String display = "";
		String span = "";
		if (spanNum == 0) {
			return "";
		} else if (spanNum > 0) {
			span = " colspan=\"" + spanNum + "\" ";
		} else {
			span = "";
		}


		html.td(2).valign("middle").append(" columnName=\"").append(column.getAlias()).append("\" ");
		if ("true".equalsIgnoreCase(column.getGroup())) {
			html.append(" group=\"true\" ");
		}
    	if (column.isSortable()){
    		html.append(" sortable=\"true\" ");
    	}
    	if (canResizeColWidth){
    		html.append(" resizeColWidth=\"true\" ");
    	}
    	if (StringUtils.isNotBlank(column.getEditTemplate())){
    		html.append(" editTemplate=\""+column.getEditTemplate()+"\" ");
    	}
    	
		int minWidth = column.getMinWidth();
		if (canResizeColWidth && minWidth > 0) {
			html.append(" minWidth=\"" + minWidth + "\" ");
		}
	
		if (StringUtils.isNotEmpty(column.getWidth())) {
			html.width(column.getWidth());
		}
		
		StringBuffer styleClass=new StringBuffer();

		
		
		if (StringUtils.isNotEmpty(headerClass)) {
			styleClass.append(headerClass);
		}

		if (StringUtils.isNotEmpty(column.getHeaderStyleClass())) {
			styleClass.append(" ").append(column.getHeaderStyleClass());
		}

		if (canResizeColWidth){
			if (column.isLastColumn()){
				styleClass.append(" ").append(" tableLastResizeableHeader");
			}else{
				styleClass.append(" ").append(" tableResizeableHeader");
			}
		}

		if (column.isEditable()) {
			styleClass.append(" ").append("editableColumn");
		} 
		
	String tableId=model.getTable().getTableId();
	
	html.styleClass(styleClass.toString().trim());
	html.onmouseover(ECSideConstants.UTIL_FUNCTION_NAME+".lightHeader(this,'"+ tableId +"');" );
	html.onmouseout(ECSideConstants.UTIL_FUNCTION_NAME+".unlightHeader(this,'"+ tableId+"');"
//		+ECSideConstants.UTIL_FUNCTION_NAME+".hideColmunMenu(event,this,'"+tableId+"');"			
	);
	html.append(" oncontextmenu=\""+ECSideConstants.UTIL_FUNCTION_NAME+".showColmunMenu(event,this,'"+tableId+"');\" ");

	
	
		if (StringUtils.isNotEmpty(column.getHeaderStyle())) {
			html.style(column.getHeaderStyle() + display);
		}
		
		if (column.isSortable()) {

			html.append(" onmouseup=\"");
//			html.append(" onclick=\"");
			html.append(getSortAction(model, column, sortOrder)).append("\" ");

			boolean showTooltips = model.getTable()
					.isShowTooltips();
			if (showTooltips) {
				String headercellTooltip = model.getMessages().getMessage(
						MessagesConstants.COLUMN_HEADERCELL_TOOLTIP_SORT);
				html.title(headercellTooltip + " " + column.getTitle());
			}
		}

		html.append(span);
		html.close();
		
	/////////////////////////////////////////////////	



		html.span();
		String cstyle = "columnSeparator";
		if (canResizeColWidth
		// && !model.getTable().isFilterable() &&
		// !column.isLastColumn()
		) {
			html.append(" onmousedown=\"" + ECSideConstants.UTIL_FUNCTION_NAME
					+ ".StartResize(event,this,'" + tableId + "');\" onmouseup=\""
					+ ECSideConstants.UTIL_FUNCTION_NAME
					+ ".EndResize(event);\"");
			cstyle += " columnResizeableSeparator";
		}
		html.styleClass(cstyle);
		html.close().append("&#160;");
		html.spanEnd();

		html.div().styleClass("headerTitle").close();
		html.append(content);
		if (column.isSortable()) {
			if (StringUtils.isNotEmpty(sortDivCss)) {
				html.nbsp();
				html.div();
				html.styleClass(sortDivCss);
				html.close();
				html.divEnd();
			}
		}
		html.divEnd();

		html.tdEnd();

		return html.toString();
	}

	public static String getSortAction(TableModel model, Column column,
			String sortOrder) {
		String formid = model.getTable().getTableId();
		StringBuffer action = new StringBuffer(
				ECSideConstants.UTIL_FUNCTION_NAME + ".doSort(event,");
		action.append("'").append(column.getAlias()).append("',");
		action.append("'").append(sortOrder).append("',");
		action.append("'").append(formid).append("'");
		// Sort sort = model.getLimit().getSort();

		/*
		 * if (sort.isSorted()) {
		 * action.append(",'").append(sort.getAlias()).append("',");
		 * action.append("''"); }
		 */

		action.append(");");
		// set sort on current column

		return action.toString();
	}

}
