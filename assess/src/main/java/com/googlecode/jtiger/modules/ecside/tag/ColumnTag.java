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

import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;

import org.apache.commons.lang.StringUtils;

import com.googlecode.jtiger.modules.ecside.core.ECSideConstants;
import com.googlecode.jtiger.modules.ecside.core.TableConstants;
import com.googlecode.jtiger.modules.ecside.core.TableModel;
import com.googlecode.jtiger.modules.ecside.core.TableModelUtils;
import com.googlecode.jtiger.modules.ecside.core.bean.BaseBean;
import com.googlecode.jtiger.modules.ecside.core.bean.Column;
import com.googlecode.jtiger.modules.ecside.table.interceptor.ColumnInterceptor;
import com.googlecode.jtiger.modules.ecside.util.ECSideUtils;
import com.googlecode.jtiger.modules.ecside.util.ExceptionUtils;

/**
 * @author Wei Zijun
 *
 */

/**
 * @jsp.tag name="column" display-name="ColumnTag" body-content="JSP"
 *          description="The container which holds all the column specific
 *          information. A copy of each column will be fed to the Model."
 * 
 */

@SuppressWarnings("unchecked")
public class ColumnTag extends BaseBodyTagSupport implements ColumnInterceptor {
	/**
	 * 
	 */
	// private Column column;
	private static final long serialVersionUID = 1L;
	private String alias;
	private String calc;
	// private String calcspan;
	private String calcSpan;
	private String calcTitle;
	private String cell;
	// private String cellEdit;
	// private String cellName;
	private String cellValue;

	private String ellipsis;
	private String escapeAutoFormat;
	private String filterable;
	private String filterCell;
	private String filterClass;
	private Object filterOptions;
	private String filterStyle;
	private String format;
	private String group;
	private String headerCell;
	private String headerClass;

	private String headerSpan;
	private String headerStyle;
	private String headerStyleClass;
	private String interceptor;
	private Object mappingDefaultKey;

	private Object mappingDefaultValue;

	private Object mappingItem;
	private String minWidth;
	private String nowrap;

	private String onclick;
	private String ondblclick;
	private String onmouseout;

	private String onmouseover;

	private String parse;

	private String property;

	private String resizeColWidth;

	private String sortable;

	private String style;

	private String styleClass;

	private String title;

	private String viewsAllowed;

	private String viewsDenied;

	private String width;

	private String columnId;

	private String editTemplate;
	private String editEvent;
	private String editable;

	private String tipTitle;

	public void addColumnAttributes(TableModel model, Column column) {
	}

	public int doStartTag() throws JspException {
		model = TagUtils.getModel(this);
		// Column column=null;
		// if (!TagUtils.isIteratingBody(this)) {
		// column = new Column(model);
		// // return SKIP_BODY;
		// }else{
		// column =
		// model.getColumnHandler().getColumnByAlias(TableModelUtils.getAlias(alias,
		// property));
		// }
		return EVAL_BODY_BUFFERED;
	}

	public int doEndTag() throws JspException {

		// boolean isExported = ExportFilterUtils.isExported(
		// model.getContext());
		// boolean isPrint="_print_".equals(ExportFilterUtils.getExportFileName(
		// model.getContext()));

		Column column = null;
		try {
			if (!TagUtils.isIteratingBody(this)) {
				column = new Column(model);

				column.setAlias(TagUtils.evaluateExpressionAsString("alias",
						this.alias, this, pageContext));
				column.setProperty(TagUtils.evaluateExpressionAsString(
						"property", this.property, this, pageContext));

				column.setCalc(TagUtils.evaluateExpressionAsString("calc",
						this.calc, this, pageContext));
				column.setCalcTitle(TagUtils.evaluateExpressionAsString(
						"calcTitle", calcTitle, this, pageContext));
				column.setCell(TagUtils.evaluateExpressionAsString("cell",
						this.cell, this, pageContext));
				column.setEscapeAutoFormat(TagUtils
						.evaluateExpressionAsBoolean("escapeAutoFormat",
								this.escapeAutoFormat, this, pageContext));
				column.setFormat(TagUtils.evaluateExpressionAsString("format",
						this.format, this, pageContext));
				column.setInterceptor(TagUtils.evaluateExpressionAsString(
						"interceptor", this.interceptor, this, pageContext));
				column.setParse(TagUtils.evaluateExpressionAsString("parse",
						this.parse, this, pageContext));
				column.setTitle(TagUtils.evaluateExpressionAsString("title",
						this.title, this, pageContext));

				// TODO:
				TagUtils.initExpression("cellValue", ECSideUtils.convertString(
						getColumnValue(), null), column);

				column.setStyle(TagUtils.evaluateExpressionAsString("style",
						this.style, this, pageContext));
				TagUtils.initExpression("style", column.getStyle(), column);

				column.setViewsAllowed(TagUtils.evaluateExpressionAsString(
						"viewsToAllow", this.viewsAllowed, this, pageContext));
				column.setViewsDenied(TagUtils.evaluateExpressionAsString(
						"viewsToDeny", this.viewsDenied, this, pageContext));

				column.setFilterable(TagUtils.evaluateExpressionAsBoolean(
						"filterable", this.filterable, this, pageContext));
				column.setFilterCell(TagUtils.evaluateExpressionAsString(
						"filterCell", this.filterCell, this, pageContext));
				column.setFilterClass(TagUtils.evaluateExpressionAsString(
						"filterClass", this.filterClass, this, pageContext));
				column
						.setFilterOptions(TagUtils.evaluateExpressionAsObject(
								"filterOptions", this.filterOptions, this,
								pageContext));
				column.setFilterStyle(TagUtils.evaluateExpressionAsString(
						"filterStyle", this.filterStyle, this, pageContext));

				column.setHeaderCell(TagUtils.evaluateExpressionAsString(
						"headerCell", this.headerCell, this, pageContext));
				column.setHeaderClass(TagUtils.evaluateExpressionAsString(
						"headerClass", this.headerClass, this, pageContext));
				column.setHeaderStyle(TagUtils.evaluateExpressionAsString(
						"headerStyle", this.headerStyle, this, pageContext));
				column.setHeaderStyleClass(TagUtils.evaluateExpressionAsString(
						"headerStyleClass", this.headerStyleClass, this,
						pageContext));
				column.setSortable(TagUtils.evaluateExpressionAsBoolean(
						"sortable", this.sortable, this, pageContext));
				column.setEditable(TagUtils.evaluateExpressionAsBoolean(
						"editable", this.editable, this, pageContext));

				String widthT = TagUtils.evaluateExpressionAsString("width",
						this.width, this, pageContext);

				if (!model.getTable().isClassic()) {
					int tempWidth = 0;
					if (StringUtils.isBlank(widthT)) {
						widthT = "" + ECSideConstants.DEFAULT_COLUMNWIDTH;
						// widthT="";
						tempWidth = ECSideConstants.DEFAULT_COLUMNWIDTH;
					} else {
						try {
							if (widthT.indexOf("%") > 0) {
								tempWidth = ECSideConstants.DEFAULT_COLUMNWIDTH;
							} else if (widthT.toLowerCase().endsWith("px")) {
								tempWidth = Integer.parseInt(widthT.substring(
										0, widthT.length() - 2));
							} else {
								tempWidth = Integer.parseInt(widthT);
							}
						} catch (Exception e) {
							tempWidth = ECSideConstants.DEFAULT_COLUMNWIDTH;
						}

					}
					model.getTable().addTotalWidth(tempWidth);
				}

				column.setWidth(widthT);
				column.setEllipsis(TagUtils.evaluateExpressionAsBoolean(
						"ellipsis", this.ellipsis, this, pageContext));

				column.setResizeColWidth(TagUtils.evaluateExpressionAsBoolean(
						"resizeColWidth", this.resizeColWidth, this,
						pageContext));
				column.setMinWidth(TagUtils.evaluateExpressionAsInt("minWidth",
						this.minWidth, this, pageContext));

				column.setEditEvent(TagUtils.evaluateExpressionAsString(
						"editEvent", this.editEvent, this, pageContext));
				column.setEditTemplate(TagUtils.evaluateExpressionAsString(
						"editTemplate", this.editTemplate, this, pageContext));
				// column.setCellName(TagUtils.evaluateExpressionAsString("cellName",
				// this.cellName, this, pageContext));

				column.setNowrap(TagUtils.evaluateExpressionAsString("nowrap",
						this.nowrap, this, pageContext));

				column.setGroup(TagUtils.evaluateExpressionAsString("group",
						this.group, this, pageContext));

				if (StringUtils.isBlank(this.headerSpan)) {
					column.setHeaderSpan(-1);
				} else {
					column.setHeaderSpan(TagUtils.evaluateExpressionAsInt(
							"headerSpan", this.headerSpan, this, pageContext));
				}

				if (StringUtils.isNotBlank(this.calcSpan)) {
					Column fcolumn;
					List columns = TagUtils.getModel(this).getColumnHandler()
							.getColumns();
					if (columns == null || columns.size() < 1
							|| columns.get(0) == null) {
						fcolumn = column;
					} else {
						fcolumn = (Column) columns.get(0);
					}

					fcolumn.setCalcSpan(TagUtils.evaluateExpressionAsInt(
							"calcSpan", this.calcSpan, this, pageContext));
				}

				column.setTagAttributes(TagUtils.evaluateExpressionAsString(
						TableConstants.TAG_ATTRIBUTES, this.tagAttributes,
						this, pageContext));

				// column.setAttribute(TDTableConstants.EXTEND_ATTRIBUTES,getExtendAttributesAsString());
				// resetExtendAttribute();

				addColumnAttributes(model, column);
				model.getColumnHandler().addColumn(column);

			} else {

				column = model.getColumnHandler().getColumnByAlias(
						TableModelUtils.getAlias(alias, property));

				if (column != null) { // null if view not allowed
					Object currentBean = TagUtils.getModel(this)
							.getCurrentRowBean();
					// Object previousBean =
					// TagUtils.getModel(this).getPreviousRowBean();

					Object propertyValue = TableModelUtils
							.getColumnPropertyValue(currentBean, property);
					Object cellValue;

					// column.setCellValue(TagUtils.evaluateExpressionAsString("cellValue",
					// this.cellValue, this, pageContext));

					column.setCellValue(TagUtils.evaluateExpressionAsString(
							"cellValue", this.cellValue, this, pageContext));

					cellValue = TagUtils.runExpression("cellValue", column,
							currentBean);

					// column.setCellValue(propertyValue==null?null:String.valueOf(propertyValue));

					if (cellValue == null) {
						cellValue = getColumnValue(propertyValue);
					}
					if (propertyValue == null) {
						propertyValue = cellValue;
					}

					// Object[]
					// expressionPropertys=column.getExpressionPropertys("cellValue");
					// if (expressionPropertys!=null){
					// GirdExpression
					// expression=column.getExpression("cellValue");
					// for (int i=0;i<expressionPropertys.length;i++){
					// String ep=(String)expressionPropertys[i];
					// expression.setArgument(ep,
					// TableModelUtils.getColumnPropertyValue(currentBean, ep));
					// }
					// cellValue=expression.call();
					// if (propertyValue==null){
					// propertyValue=cellValue;
					// }
					// }else{
					// cellValue=getColumnValue(propertyValue);
					// }

					column.setTipTitle(TagUtils.evaluateExpressionAsString(
							"tipTitle", this.tipTitle, this, pageContext));

					Object preCellValue = column.getPreCellValue();
					// if (previousBean!=null){
					// prePropertyValue=TableModelUtils.getColumnPropertyValue(previousBean,
					// property);
					//                    	
					// if ("hide".equalsIgnoreCase(column.getGroup())){
					// if (propertyValue!=null &&
					// propertyValue.equals(prePropertyValue)){
					// cellValue="";
					// }
					// }
					// }

					boolean hideCell = false;
					boolean isGroup = false;
					if ("true".equalsIgnoreCase(column.getGroup())) {
						isGroup = true;
					}

					if (isGroup) {
						column.setPreCellValue(cellValue);
						if (cellValue != null && cellValue.equals(preCellValue)) {
							cellValue = "";
							column.setStyle("display:none;");
							hideCell = true;
							// column.setStyle(column.getStyle()+";display:none;");
						}

					}

					column.setValue(cellValue);
					column.setPropertyValue(propertyValue);

					if (!hideCell) {
						column.setOnclick(TagUtils.evaluateExpressionAsString(
								TableConstants.ON_CLICK, this.onclick, this,
								pageContext));
						column.setOndblclick(TagUtils
								.evaluateExpressionAsString(
										TableConstants.ON_DOUBLE_CLICK,
										this.ondblclick, this, pageContext));

						column.setOnmouseover(TagUtils
								.evaluateExpressionAsString(
										TableConstants.ON_MOUSE_OVER,
										this.onmouseover, this, pageContext));
						column.setOnmouseout(TagUtils
								.evaluateExpressionAsString(
										TableConstants.ON_MOUSE_OUT,
										this.onmouseout, this, pageContext));

						String styleClassName = TagUtils
								.evaluateExpressionAsString("styleClass",
										this.styleClass, this, pageContext);
						if (isGroup) {
							styleClassName = "groupColumn "
									+ ECSideUtils.convertString(styleClassName,
											"");
						}

						column.setStyleClass(styleClassName);

						String style = ECSideUtils.convertString(TagUtils
								.runExpression("style", column, currentBean),
								null);
						if (style == null) {
							column.setStyle(TagUtils
									.evaluateExpressionAsString("style",
											this.style, this, pageContext));
						} else {
							column.setStyle(style);
						}

					}
					// column.setCellValue(TagUtils.evaluateExpressionAsString("cellValue",
					// this.cellValue, this, pageContext));
					column.setTagAttributes(TagUtils
							.evaluateExpressionAsString(
									TableConstants.TAG_ATTRIBUTES,
									this.tagAttributes, this, pageContext));
					column.setId(TagUtils.evaluateExpressionAsString(
							"columnId", columnId, this, pageContext));

					modifyColumnAttributes(model, column);
					model.getColumnHandler().modifyColumnAttributes(column);
					model.getViewHandler().addColumnValueToView(column);
					// model.setPreviousRowBean(currentBean);

				}
				//               

			}

			if (bodyContent != null) {
				bodyContent.clearBody();
			}
			// column.setAttribute(TDTableConstants.EXTEND_ATTRIBUTES,"");
		} catch (Exception e) {
			throw new JspException("ColumnTag.doEndTag() Problem: "
					+ ExceptionUtils.formatStackTrace(e));
		} finally {
			doFinally();
		}
		return EVAL_PAGE;
	}

	public void doFinally() {
		super.doFinally();
	}

	protected Object getColumnValue() throws JspException {
		Object result = getValue();

		if (result == null && bodyContent != null) {
			result = getBodyContent().getString();
		}

		if (result != null) {
			result = TagUtils.evaluateExpressionAsObject("result", result,
					this, pageContext);
		}
		return result;
	}

	protected Object getColumnValue(Object propertyValue) throws JspException {

		Object result = getColumnValue();

		if (result == null && mappingItem != null && propertyValue != null) {
			Object mappingMap = pageContext.findAttribute((String) mappingItem);

			Object outValue = null;
			if (mappingMap instanceof Map) {
				Map itemsMap = (Map) mappingMap;
				outValue = itemsMap.get(String.valueOf(propertyValue));
				if (outValue == null && mappingDefaultKey != null) {
					outValue = itemsMap.get(mappingDefaultKey);
				}
			}
			if (outValue == null) {
				propertyValue = mappingDefaultValue;
			}
			result = outValue;
		}

		if (result == null
				|| (result != null && result instanceof String && StringUtils
						.isBlank(result.toString()))) {
			result = propertyValue;
		}

		return result;
	}

	// //////////////////////////////////

	public BaseBean getTagBean() {
		return model.getColumnHandler().getColumnByAlias(
				TableModelUtils.getAlias(alias, property));
	}

	public void modifyColumnAttributes(TableModel model, Column column) {
	}

	public void release() {
		alias = null;
		calc = null;
		// calcspan = null;
		calcSpan = null;
		calcTitle = null;
		cell = null;
		// cellEdit = null;
		// cellName = null;
		cellValue = null;
		editEvent = null;
		editTemplate = null;
		editable = null;

		ellipsis = null;
		escapeAutoFormat = null;
		filterable = null;
		filterCell = null;
		filterClass = null;
		// filterOptions = null;
		filterStyle = null;
		format = null;
		group = null;
		headerCell = null;
		headerClass = null;
		headerSpan = null;
		headerStyle = null;
		headerStyleClass = null;
		interceptor = null;
		mappingDefaultKey = null;
		mappingDefaultValue = null;
		mappingItem = null;
		minWidth = null;
		nowrap = null;
		onclick = null;
		ondblclick = null;
		onmouseout = null;
		onmouseover = null;
		parse = null;
		property = null;
		resizeColWidth = null;
		sortable = null;
		style = null;
		styleClass = null;
		title = null;
		viewsAllowed = null;
		viewsDenied = null;
		width = null;
		tipTitle = null;
		super.release();
	}

	/**
	 * @jsp.attribute description="Used to uniquely identify the column when the
	 *                same property is used for more than one column."
	 *                required="false" rtexprvalue="true"
	 */
	public void setAlias(String alias) {
		this.alias = alias;
	}

	/**
	 * @jsp.attribute description="A fully qualified class name to a custom Calc
	 *                implementation. Could also be a named type in the
	 *                preferences. Used to do math on a column."
	 *                required="false" rtexprvalue="true"
	 */
	public void setCalc(String calc) {
		this.calc = calc;
	}

	public void setCalcspan(String calcspan) {
		this.calcSpan = calcspan;
		// this.calcspan=calcspan;
	}

	public void setCalcSpan(String calcSpan) {
		this.calcSpan = calcSpan;
	}

	/**
	 * @jsp.attribute description="The title of the calc." required="false"
	 *                rtexprvalue="true"
	 */
	public void setCalcTitle(String totalTitle) {
		this.calcTitle = totalTitle;
	}

	/**
	 * @jsp.attribute description="Display for the column. The valid values are
	 *                display, currency, rowCount, and date. The default value
	 *                is display. The cell can also be a fully qualified class
	 *                name to a custom Cell. Be sure to implement the Cell
	 *                interface or extend AbstractCell if making a custom cell."
	 *                required="false" rtexprvalue="true"
	 */
	public void setCell(String cell) {
		this.cell = cell;
	}

	public void setCellEdit(String cellEdit) {
		// this.cellEdit = cellEdit;
	}

	public void setCellValue(String cellValue) {
		this.cellValue = cellValue;
	}

	public void setEllipsis(String ellipsis) {
		this.ellipsis = ellipsis;
	}

	/**
	 * @jsp.attribute description="Specify whether auto format of value will be
	 *                skipped. False by default, and is only effective if
	 *                autoformatting is implement in the view." required="false"
	 *                rtexprvalue="true"
	 */
	public void setEscapeAutoFormat(String escapeAutoFormat) {
		this.escapeAutoFormat = escapeAutoFormat;
	}

	/**
	 * @jsp.attribute description="Specify whether or not the column should be
	 *                filterable. Acceptable values are true or false. The
	 *                default is to use the value for the table filterable
	 *                attribute." required="false" rtexprvalue="true"
	 */
	public void setFilterable(String filterable) {
		this.filterable = filterable;
	}

	/**
	 * @jsp.attribute description="Displays the filter column. The valid values
	 *                are filter and droplist. The default is filter. The cell
	 *                can also be a fully qualified class name to a custom
	 *                cell." required="false" rtexprvalue="true"
	 */
	public void setFilterCell(String filterCell) {
		this.filterCell = filterCell;
	}

	/**
	 * @jsp.attribute description="The css class style sheet used to define what
	 *                the table filter column looks like." required="false"
	 *                rtexprvalue="true"
	 */
	public void setFilterClass(String filterClass) {
		this.filterClass = filterClass;
	}

	/**
	 * @jsp.attribute description="The object that contains the collection of
	 *                elements that implement the Option interface."
	 *                required="false" rtexprvalue="true"
	 */
	public void setFilterOptions(Object filterOptions) {
		this.filterOptions = filterOptions;
	}

	/**
	 * @jsp.attribute 
	 *                description="The css class style sheet to use for the filter column."
	 *                required="false" rtexprvalue="true"
	 */
	public void setFilterStyle(String filterStyle) {
		this.filterStyle = filterStyle;
	}

	/**
	 * @jsp.attribute description="The format to use for the cell. For instance
	 *                if used with a date cell then the format can be
	 *                MM/dd/yyyy." required="false" rtexprvalue="true"
	 */
	public void setFormat(String format) {
		this.format = format;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	/**
	 * @jsp.attribute description="Display for the header column. The default is
	 *                header. The cell can also be a fully qualified class name
	 *                to a custom cell." required="false" rtexprvalue="true"
	 */
	public void setHeaderCell(String headerCell) {
		this.headerCell = headerCell;
	}

	/**
	 * @jsp.attribute description="The css class style sheet used to define what
	 *                the table header column looks like." required="false"
	 *                rtexprvalue="true"
	 */
	public void setHeaderClass(String headerClass) {
		this.headerClass = headerClass;
	}

	public void setHeaderSpan(String headerSpan) {
		this.headerSpan = headerSpan;
	}

	/**
	 * @jsp.attribute 
	 *                description="The css class style sheet to use for the header column."
	 *                required="false" rtexprvalue="true"
	 */
	public void setHeaderStyle(String headerStyle) {
		this.headerStyle = headerStyle;
	}

	public void setHeaderStyleClass(String headerStyleClass) {
		this.headerStyleClass = headerStyleClass;
		this.headerClass = headerStyleClass;
	}

	/**
	 * @jsp.attribute description="A fully qualified class name to a custom
	 *                InterceptColumn implementation. Could also be a named type
	 *                in the preferences. Used to add or modify column
	 *                attributes." required="false" rtexprvalue="true"
	 */
	public void setInterceptor(String interceptor) {
		this.interceptor = interceptor;
	}

	public void setMappingDefaultKey(Object mappingDefaultKey) {
		this.mappingDefaultKey = mappingDefaultKey;
	}

	public void setMappingDefaultValue(Object mappingDefaultValue) {
		this.mappingDefaultValue = mappingDefaultValue;
	}

	public void setMappingItem(Object mappingItem) {
		this.mappingItem = mappingItem;
	}

	public void setMinWidth(String minWidth) {
		this.minWidth = minWidth;
	}

	public void setNowrap(String nowrap) {
		this.nowrap = nowrap;
	}

	public void setOnclick(String onclick) {
		this.onclick = onclick;
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

	/**
	 * @jsp.attribute description="Used if the format needs to be interpreted.
	 *                For instance, a date needs to be parsed in the specific
	 *                format, such as MM-dd-yyyy." required="false"
	 *                rtexprvalue="true"
	 */
	public void setParse(String parse) {
		this.parse = parse;
	}

	/**
	 * @jsp.attribute description="The bean attribute to use for the column."
	 *                required="false" rtexprvalue="true"
	 */
	public void setProperty(String property) {
		this.property = property;
	}

	/**
	 * @jsp.attribute description="Specify whether or not the column should be
	 *                sortable. The acceptable values are true or false. The
	 *                default is to use the value for the table sortable
	 *                attribute." required="false" rtexprvalue="true"
	 */
	public void setSortable(String sortable) {
		this.sortable = sortable;
	}

	/**
	 * @jsp.attribute description="The css inline style sheet." required="false"
	 *                rtexprvalue="true"
	 */
	public void setStyle(String style) {
		this.style = style;
	}

	/**
	 * @jsp.attribute description="The css class style sheet." required="false"
	 *                rtexprvalue="true"
	 */
	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}

	/**
	 * @jsp.attribute description="The display for the table column header. If
	 *                the title is not specified then it will default to the
	 *                name of the property, changing the camelcase syntax to
	 *                separate words." required="false" rtexprvalue="true"
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @jsp.attribute description="The comma separated list of views that this
	 *                column will be used in." required="false"
	 *                rtexprvalue="true"
	 */
	public void setViewsAllowed(String viewsAllowed) {
		this.viewsAllowed = viewsAllowed;
	}

	/**
	 * @jsp.attribute description="The comma separated list of views that this
	 *                column will not be used in." required="false"
	 *                rtexprvalue="true"
	 */
	public void setViewsDenied(String viewsDenied) {
		this.viewsDenied = viewsDenied;
	}

	/**
	 * @jsp.attribute description="Specify the column width." required="false"
	 *                rtexprvalue="true"
	 */
	public void setWidth(String width) {
		this.width = width;
	}

	public void setColumnId(String columnId) {
		this.columnId = columnId;
	}

	public void setCellName(String cellName) {
		// this.cellName = cellName;
	}

	public void setResizeColWidth(String resizeColWidth) {
		this.resizeColWidth = resizeColWidth;
	}

	public void setEditEvent(String editEvent) {
		this.editEvent = editEvent;
	}

	public void setEditTemplate(String editTemplate) {
		this.editTemplate = editTemplate;
	}

	public void setEditable(String editable) {
		this.editable = editable;
	}

	public void setTipTitle(String tipTitle) {
		this.tipTitle = tipTitle;
	}

}