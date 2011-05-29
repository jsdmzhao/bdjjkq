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

import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

import org.apache.commons.lang.StringUtils;

import com.googlecode.jtiger.modules.ecside.common.QueryResult;
import com.googlecode.jtiger.modules.ecside.core.TableConstants;
import com.googlecode.jtiger.modules.ecside.core.TableModel;
import com.googlecode.jtiger.modules.ecside.core.TableModelImpl;
import com.googlecode.jtiger.modules.ecside.core.bean.BaseBean;
import com.googlecode.jtiger.modules.ecside.core.bean.Export;
import com.googlecode.jtiger.modules.ecside.core.bean.Table;
import com.googlecode.jtiger.modules.ecside.core.context.JspPageContext;
import com.googlecode.jtiger.modules.ecside.table.interceptor.TableInterceptor;
import com.googlecode.jtiger.modules.ecside.util.ExceptionUtils;
import com.googlecode.jtiger.modules.ecside.view.html.BuilderConstants;

/**
 * @author Wei Zijun
 * 
 */

public class TableTag extends BaseTagSupport implements TableInterceptor {

	private static final long serialVersionUID = 1L;

	private Table table;
	private Object items;
	@SuppressWarnings("unchecked")
	private Iterator iterator;
	private QueryResult queryResult;

	protected String classic;

	private String action;

	private String insertAction;
	private String updateAction;
	private String deleteAction;
	private String shadowRowAction;

	private String alwaysShowExtend;
	private String autoIncludeParameters;
	private String border;
	private String bufferView;
	private String cellpadding;
	private String cellspacing;
	private String csvFileName;
	private String editable;
	private String excludeParameters;
	private String filterable;
	private String filterRowsCallback;
	private String form;
	private String height;
	private String includeParameters;
	private String interceptor;
	private String listHeight;
	private String listWidth;
	private String locale;
	private String maxRowsExported;
	private String method;
	private String minColWidth;
	private String minWidth;
	private String nearPageNum;
	private String onInvokeAction;
	private String pageSizeList;
	private String paginationLocation;
	private String pdfFileName;
	private String resizeColWidth;
	private String retrieveRowsCallback;
	private String rowsDisplayed;
	private String scope;
	private String scrollList;
	private String showExports;
	private String showHeader;
	private String showPrint;
	private String showTitle;
	private String showTooltips;
	private String sortable;
	private String sortRowsCallback;
	private String state;
	private String stateAttr;
	private String style;
	private String styleClass;
	private String theme;
	private String title;
	private String toolbarContent;
	private String toolbarLocation;
	private String var;
	private String view;
	private String width;
	private String xlsFileName;

	private String oddRowBgColor;
	private String evenRowBgColor;

	private String generateScript;
	private String useAjax;
	private String doPreload;

	private String excludeTool;

	private String minHeight;

	private String batchUpdate;

	private String enctype;

	public String getBatchUpdate() {
		return batchUpdate;
	}

	public void setBatchUpdate(String batchUpdate) {
		this.batchUpdate = batchUpdate;
	}

	public void addTableAttributes(TableModel model, Table table) {

	}

	public int doAfterBody() throws JspException {

		Table table = model.getTable();
		table.setAttribute(TableConstants.EXTEND_ATTRIBUTES,
				getExtendAttributesAsString());
		resetExtendAttribute();
		table.afterBody();

		try {

			if (iterator == null) {
				iterator = model.execute().iterator();
			}
			if (iterator != null && iterator.hasNext()) {
				Object bean = iterator.next();
				model.setCurrentRowBean(bean);
				return EVAL_BODY_AGAIN;
			}

		} catch (Exception e) {
			throw new JspException("TableTag.doAfterBody() Problem: "
					+ ExceptionUtils.formatStackTrace(e));
		}

		return SKIP_BODY;
	}

	public void doCatch(Throwable e) throws Throwable {
		throw new JspException("TableTag Problem: "
				+ ExceptionUtils.formatStackTrace(e));
	}

	public void doFinally() {
		if (queryResult != null) {
			queryResult.closeAll();
			queryResult = null;
		}
		items = null;
		iterator = null;
		model = null;
		table = null;

	}

	public int doEndTag() throws JspException {
		model.getTable().setAttribute(TableConstants.EXTEND_ATTRIBUTES,
				getExtendAttributesAsString());
		try {
			pageContext.getOut().println(model.getViewData());
		} catch (Exception e) {
			// LogHandler.errorLog(logger, msg);
			throw new JspException("TableTag.doEndTag() Problem: "
					+ ExceptionUtils.formatStackTrace(e));
		} finally {
			doFinally();
		}

		return EVAL_PAGE;
	}

	public int doStartTag() throws JspException {
		try {
			// initialize the attributes
			iterator = null;
			pageContext.setAttribute(TableConstants.ROWCOUNT, "0");
			// fire up the model with the context, preferences and messages
			model = new TableModelImpl(new JspPageContext(pageContext),
					TagUtils.evaluateExpressionAsString("locale", this.locale,
							this, pageContext));

			// boolean isExported =
			// ExportFilterUtils.isExported(model.getContext());
			// boolean isPrint =
			// "_print_".equals(ExportFilterUtils.getExportFileName(model.getContext()));

			table = new Table(model);
			table.setView(TagUtils.evaluateExpressionAsString("view",
					this.view, this, pageContext));

			table.setGenerateScript(TagUtils.evaluateExpressionAsBoolean(
					"generateScript", this.generateScript, this, pageContext));
			table.setUseAjax(TagUtils.evaluateExpressionAsBoolean("useAjax",
					this.useAjax, this, pageContext));
			table.setDoPreload(TagUtils.evaluateExpressionAsBoolean(
					"doPreload", this.doPreload, this, pageContext));
			table.setClassic(TagUtils.evaluateExpressionAsBoolean("classic",
					this.classic, this, pageContext));

			table.setAction(TagUtils.evaluateExpressionAsString("action",
					action, this, pageContext));
			table.setEnctype(TagUtils.evaluateExpressionAsString("enctype",
					enctype, this, pageContext));

			table.setInsertAction(TagUtils.evaluateExpressionAsString(
					"insertAction", insertAction, this, pageContext));
			table.setUpdateAction(TagUtils.evaluateExpressionAsString(
					"updateAction", updateAction, this, pageContext));
			table.setDeleteAction(TagUtils.evaluateExpressionAsString(
					"deleteAction", deleteAction, this, pageContext));
			table.setShadowRowAction(TagUtils.evaluateExpressionAsString(
					"shadowRowAction", shadowRowAction, this, pageContext));

			table.setBatchUpdate(TagUtils.evaluateExpressionAsBoolean(
					"batchUpdate", this.batchUpdate, this, pageContext));
			table.setAutoIncludeParameters(TagUtils
					.evaluateExpressionAsBoolean("autoIncludeParameters",
							this.autoIncludeParameters, this, pageContext));
			table.setBorder(TagUtils.evaluateExpressionAsString("border",
					this.border, this, pageContext));
			table.setBufferView(TagUtils.evaluateExpressionAsBoolean(
					"bufferView", this.bufferView, this, pageContext));
			table.setCellpadding(TagUtils.evaluateExpressionAsString(
					"cellpadding", this.cellpadding, this, pageContext));
			table.setCellspacing(TagUtils.evaluateExpressionAsString(
					"cellspacing", this.cellspacing, this, pageContext));
			table.setFilterable(TagUtils.evaluateExpressionAsBoolean(
					"filterable", this.filterable, this, pageContext));
			table.setFilterRowsCallback(TagUtils.evaluateExpressionAsString(
					"filterRowsCallback", this.filterRowsCallback, this,
					pageContext));
			table.setForm(TagUtils.evaluateExpressionAsString("form",
					this.form, this, pageContext));
			table.setInterceptor(TagUtils.evaluateExpressionAsString(
					"interceptor", this.interceptor, this, pageContext));
			table.setItems(TagUtils.evaluateExpressionAsObject("items",
					this.items, this, pageContext));
			table.setLocale(TagUtils.evaluateExpressionAsString("locale",
					this.locale, this, pageContext));
			table.setMethod(TagUtils.evaluateExpressionAsString("method",
					this.method, this, pageContext));
			table.setOnInvokeAction(TagUtils.evaluateExpressionAsString(
					"onInvokeAction", this.onInvokeAction, this, pageContext));
			table.setRetrieveRowsCallback(TagUtils.evaluateExpressionAsString(
					"retrieveRowsCallback", this.retrieveRowsCallback, this,
					pageContext));
			table.setRowsDisplayed(TagUtils.evaluateExpressionAsInt(
					"rowsDisplayed", this.rowsDisplayed, this, pageContext));
			table.setScope(TagUtils.evaluateExpressionAsString("scope", scope,
					this, pageContext));
			table.setShowTitle(TagUtils.evaluateExpressionAsBoolean(
					"showTitle", this.showTitle, this, pageContext));
			table.setShowTooltips(TagUtils.evaluateExpressionAsBoolean(
					"showTooltips", this.showTooltips, this, pageContext));
			table.setSortRowsCallback(TagUtils.evaluateExpressionAsString(
					"sortRowsCallback", this.sortRowsCallback, this,
					pageContext));
			table.setSortable(TagUtils.evaluateExpressionAsBoolean("sortable",
					this.sortable, this, pageContext));
			table.setState(TagUtils.evaluateExpressionAsString("state",
					this.state, this, pageContext));
			table.setStateAttr(TagUtils.evaluateExpressionAsString("stateAttr",
					this.stateAttr, this, pageContext));
			table.setStyle(TagUtils.evaluateExpressionAsString("style", style,
					this, pageContext));
			table.setStyleClass(TagUtils.evaluateExpressionAsString(
					"styleClass", this.styleClass, this, pageContext));
			table.setTableId(TagUtils.evaluateExpressionAsString("tableId",
					getTableId(), this, pageContext));
			table.setTheme(TagUtils.evaluateExpressionAsString("theme",
					this.theme, this, pageContext));
			table.setTitle(TagUtils.evaluateExpressionAsString("title",
					this.title, this, pageContext));
			table.setVar(TagUtils.evaluateExpressionAsString("var", this.var,
					this, pageContext));

			table.setWidth(TagUtils.evaluateExpressionAsString("width",
					this.width, this, pageContext));

			table.setExcludeTool(TagUtils.evaluateExpressionAsString(
					"excludeTool", this.excludeTool, this, pageContext));

			table.setOddRowBgColor(TagUtils.evaluateExpressionAsString(
					"oddRowBgColor", this.oddRowBgColor, this, pageContext));
			table.setEvenRowBgColor(TagUtils.evaluateExpressionAsString(
					"evenRowBgColor", this.evenRowBgColor, this, pageContext));

			table.setHeight(TagUtils.evaluateExpressionAsString("height",
					this.height, this, pageContext));
			if (StringUtils.isBlank(table.getHeight())) {
				table.setHeight(TagUtils.evaluateExpressionAsString(
						"listHeight", this.listHeight, this, pageContext));
			}
			table.setMinHeight(TagUtils.evaluateExpressionAsString("minHeight",
					this.minHeight, this, pageContext));

			table.setListWidth(TagUtils.evaluateExpressionAsString("listWidth",
					this.listWidth, this, pageContext));

			table.setScrollList(TagUtils.evaluateExpressionAsBoolean(
					"scrollList", this.scrollList, this, pageContext));

			table.setResizeColWidth(TagUtils.evaluateExpressionAsBoolean(
					"resizeColWidth", this.resizeColWidth, this, pageContext));
			table.setMinColWidth(TagUtils.evaluateExpressionAsInt(
					"minColWidth", this.minColWidth, this, pageContext));

			table.setNearPageNum(TagUtils.evaluateExpressionAsString(
					"nearPageNum", this.nearPageNum, this, pageContext));
			table
					.setMaxRowsExported(TagUtils.evaluateExpressionAsString(
							"maxRowsExported", this.maxRowsExported, this,
							pageContext));

			table.setToolbarContent(TagUtils.evaluateExpressionAsString(
					"toolbarContent", this.toolbarContent, this, pageContext));
			table
					.setToolbarLocation(TagUtils.evaluateExpressionAsString(
							"toolbarLocation", this.toolbarLocation, this,
							pageContext));
			table.setAlwaysShowExtend(TagUtils.evaluateExpressionAsString(
					"alwaysShowExtend", this.alwaysShowExtend, this,
					pageContext));

			table.setTagAttributes(TagUtils.evaluateExpressionAsString(
					TableConstants.TAG_ATTRIBUTES, this.tagAttributes, this,
					pageContext));

			table.setIncludeParameters(TagUtils.evaluateExpressionAsString(
					"includeParameters", this.includeParameters, this,
					pageContext));
			table.setExcludeParameters(TagUtils.evaluateExpressionAsString(
					"excludeParameters", this.excludeParameters, this,
					pageContext));

			table.setShowHeader(TagUtils.evaluateExpressionAsBoolean(
					"showHeader", this.showHeader, this, pageContext));
			table.setPageSizeList(TagUtils.evaluateExpressionAsString(
					"pageSizeList", this.pageSizeList, this, pageContext));

			table.setMinWidth(TagUtils.evaluateExpressionAsString("minWidth",
					this.minWidth, this, pageContext));

			table.setEditable(TagUtils.evaluateExpressionAsBoolean("editable",
					this.editable, this, pageContext));
			if (StringUtils.isBlank(table.getAction())) {
				table.setAction(((HttpServletRequest) pageContext.getRequest())
						.getRequestURL().toString());
			}

			table.setAttribute(TableConstants.XLS_FILE, TagUtils
					.evaluateExpressionAsString(TableConstants.XLS_FILE,
							this.xlsFileName, this, pageContext));

			table.setAttribute(TableConstants.CSV_FILE, TagUtils
					.evaluateExpressionAsString(TableConstants.CSV_FILE,
							this.csvFileName, this, pageContext));

			table.setAttribute(TableConstants.PDF_FILE, TagUtils
					.evaluateExpressionAsString(TableConstants.PDF_FILE,
							this.pdfFileName, this, pageContext));

			table.setAttribute(TableConstants.SHOW_PRINT, TagUtils
					.evaluateExpressionAsString(TableConstants.SHOW_PRINT,
							this.showPrint, this, pageContext));

			exportsMaker(model, table);

			// }

			addTableAttributes(model, table);

			model.addTable(table);

			Object tempQueryResult = pageContext.findAttribute(String
					.valueOf(table.getItems()));
			if (tempQueryResult instanceof QueryResult) {
				queryResult = (QueryResult) tempQueryResult;
			} else {
				queryResult = null;
				tempQueryResult = null;
			}
			table.beforeBody();
		} catch (Exception e) {
			throw new JspException("TableTag.doStartTag() Problem: "
					+ ExceptionUtils.formatStackTrace(e));
		}

		return EVAL_BODY_INCLUDE;
	}

	public void exportsMaker(TableModel model, Table table) {
		String xlsFile = (String) table.getAttribute(TableConstants.XLS_FILE);
		String csvFile = (String) table.getAttribute(TableConstants.CSV_FILE);
		String pdfFile = (String) table.getAttribute(TableConstants.PDF_FILE);
		String showPrint = (String) table
				.getAttribute(TableConstants.SHOW_PRINT);

		if (StringUtils.isNotBlank(xlsFile)) {
			Export export = new Export(model);
			export.setFileName(TagUtils.evaluateExpressionAsString("fileName",
					xlsFile, this, pageContext));
			export.setInterceptor(TagUtils.evaluateExpressionAsString(
					"interceptor", this.interceptor, this, pageContext));

			export.setView(TableConstants.VIEW_XLS);
			export.setViewResolver(TableConstants.VIEW_XLS);
			export.setImageName(TableConstants.VIEW_XLS);
			export.setText(BuilderConstants.TOOLBAR_XLS_TEXT);
			export.setTooltip("toolbar.tooltip.xls");
			model.addExport(export);
		}
		if (StringUtils.isNotBlank(csvFile)) {
			Export export = new Export(model);
			export.setFileName(TagUtils.evaluateExpressionAsString("fileName",
					csvFile, this, pageContext));
			export.setInterceptor(TagUtils.evaluateExpressionAsString(
					"interceptor", this.interceptor, this, pageContext));

			export.setView(TableConstants.VIEW_CSV);
			export.setViewResolver(TableConstants.VIEW_CSV);
			export.setImageName(TableConstants.VIEW_CSV);
			export.setText(BuilderConstants.TOOLBAR_CSV_TEXT);
			export.setTooltip("toolbar.tooltip.csv");
			// export.setAttribute(CsvView.DELIMITER,
			// TagUtils.evaluateExpressionAsString("delimiter", delimiter, this,
			// pageContext));

			model.addExport(export);
		}
		if (StringUtils.isNotBlank(pdfFile)) {
			Export export = new Export(model);
			export.setFileName(TagUtils.evaluateExpressionAsString("fileName",
					pdfFile, this, pageContext));
			export.setInterceptor(TagUtils.evaluateExpressionAsString(
					"interceptor", this.interceptor, this, pageContext));

			export.setView(TableConstants.VIEW_PDF);
			export.setViewResolver(TableConstants.VIEW_PDF);
			export.setImageName(TableConstants.VIEW_PDF);
			export.setText(BuilderConstants.TOOLBAR_PDF_TEXT);
			export.setTooltip("toolbar.tooltip.pdf");
			// export.setAttribute(PdfView.HEADER_BACKGROUND_COLOR,
			// TagUtils.evaluateExpressionAsString("headerBackgroundColor",
			// headerBackgroundColor, this, pageContext));
			// export.setAttribute(PdfView.HEADER_COLOR,
			// TagUtils.evaluateExpressionAsString("headerColor", headerColor,
			// this, pageContext));
			// export.setAttribute(PdfView.HEADER_TITLE,
			// TagUtils.evaluateExpressionAsString("headerTitle", headerTitle,
			// this, pageContext));

			model.addExport(export);
		}

		if (StringUtils.isNotBlank(showPrint) && showPrint.equals("" + true)) {
			Export export = new Export(model);
			export.setFileName(TagUtils.evaluateExpressionAsString("fileName",
					"_print_", this, pageContext));
			export.setInterceptor(TagUtils.evaluateExpressionAsString(
					"interceptor", this.interceptor, this, pageContext));

			export.setView(TableConstants.VIEW_PRINT);
			export.setViewResolver(TableConstants.VIEW_PRINT);
			export.setImageName(TableConstants.VIEW_PRINT);
			export.setText(TableConstants.TOOLBAR_PRINT_TEXT);
			export.setTooltip("toolbar.tooltip.print");
			model.addExport(export);
		}
	}

	public String getAlwaysShowExtend() {
		return alwaysShowExtend;
	}

	public String getCsvFileName() {
		return csvFileName;
	}

	public String getEditable() {
		return editable;
	}

	public String getExcludeParameters() {
		return excludeParameters;
	}

	public String getHeight() {
		return height;
	}

	public String getIncludeParameters() {
		return includeParameters;
	}

	public String getListHeight() {
		return listHeight;
	}

	public String getListWidth() {
		return listWidth;
	}

	public String getMaxRowsExported() {
		return maxRowsExported;
	}

	public String getMinColWidth() {
		return minColWidth;
	}

	public String getMinWidth() {
		return minWidth;
	}

	public String getNearPageNum() {
		return nearPageNum;
	}

	public String getPageSizeList() {
		return pageSizeList;
	}

	public String getPaginationLocation() {
		return paginationLocation;
	}

	public String getPdfFileName() {
		return pdfFileName;
	}

	public String getResizeColWidth() {
		return resizeColWidth;
	}

	public String getScrollList() {
		return scrollList;
	}

	public String getShowExports() {
		return showExports;
	}

	public String getShowHeader() {
		return showHeader;
	}

	public String getShowPrint() {
		return showPrint;
	}

	public String getTableId() {
		return getElementId();
	}

	public BaseBean getTagBean() {
		return table;
	}

	public String getToolbarContent() {
		return toolbarContent;
	}

	public String getToolbarLocation() {
		return toolbarLocation;
	}

	public String getXlsFileName() {
		return xlsFileName;
	}

	public void release() {
		if (queryResult != null) {
			queryResult.closeAll();
			queryResult = null;
		}
		iterator = null;
		table = null;
		items = null;

		generateScript = null;
		useAjax = null;
		doPreload = null;

		classic = null;
		action = null;
		alwaysShowExtend = null;
		autoIncludeParameters = null;
		batchUpdate = null;
		border = null;
		// bufferView = null;
		cellpadding = null;
		cellspacing = null;
		csvFileName = null;
		editable = null;
		excludeParameters = null;
		filterable = null;
		filterRowsCallback = null;
		form = null;
		height = null;
		includeParameters = null;
		interceptor = null;
		listHeight = null;
		listWidth = null;
		locale = null;
		maxRowsExported = null;
		method = null;
		minColWidth = null;
		minWidth = null;
		nearPageNum = null;
		onInvokeAction = null;
		pageSizeList = null;
		paginationLocation = null;
		pdfFileName = null;
		resizeColWidth = null;
		retrieveRowsCallback = null;
		rowsDisplayed = null;
		scope = null;
		scrollList = null;
		showExports = null;
		showHeader = null;
		showPrint = null;
		showTitle = null;
		showTooltips = null;
		sortable = null;
		sortRowsCallback = null;
		state = null;
		stateAttr = null;
		style = null;
		styleClass = null;
		theme = null;
		title = null;
		toolbarContent = null;
		toolbarLocation = null;
		var = null;
		view = null;
		width = null;
		xlsFileName = null;

		super.release();
	}

	/**
	 * @jsp.attribute description="The URI that will be called when the filter,
	 *                sort and pagination is used." required="false"
	 *                rtexprvalue="true"
	 */
	public void setAction(String action) {
		this.action = action;
	}

	public void setAlwaysShowExtend(String alwaysShowExtend) {
		this.alwaysShowExtend = alwaysShowExtend;
	}

	/**
	 * @jsp.attribute description="Specify whether or not to automatically
	 *                include the parameters, as hidden inputs, passed into the
	 *                JSP." required="false" rtexprvalue="true"
	 */
	public void setAutoIncludeParameters(String autoIncludeParameters) {
		this.autoIncludeParameters = autoIncludeParameters;
	}

	/**
	 * @jsp.attribute 
	 *                description="The table border attribute. The default is 0."
	 *                required="false" rtexprvalue="true"
	 */
	public void setBorder(String border) {
		this.border = border;
	}

	/**
	 * @jsp.attribute description=
	 *                "Whether of not to buffer the view. Boolean value with the default being false."
	 *                required="false" rtexprvalue="true"
	 */
	public void setBufferView(String bufferView) {
		this.bufferView = bufferView;
	}

	/**
	 * @jsp.attribute 
	 *                description="The table cellpadding attribute. The default is 0."
	 *                required="false" rtexprvalue="true"
	 */
	public void setCellpadding(String cellpadding) {
		this.cellpadding = cellpadding;
	}

	/**
	 * @jsp.attribute 
	 *                description="The table cellspacing attribute. The default is 0."
	 *                required="false" rtexprvalue="true"
	 */
	public void setCellspacing(String cellspacing) {
		this.cellspacing = cellspacing;
	}

	public void setCsvFileName(String cvsFileName) {
		this.csvFileName = cvsFileName;
	}

	public void setEditable(String editable) {
		this.editable = editable;
	}

	public void setExcludeParameters(String excludeParameters) {
		this.excludeParameters = excludeParameters;
	}

	/**
	 * @jsp.attribute description="Specify whether or not the table is
	 *                filterable. Boolean value with the default being true."
	 *                required="false" rtexprvalue="true"
	 */
	public void setFilterable(String filterable) {
		this.filterable = filterable;
	}

	/**
	 * @jsp.attribute description="A fully qualified class name to a custom
	 *                FilterRowsCallback implementation. Could also be a named
	 *                type in the preferences. Used to filter the Collection of
	 *                Beans or Collection of Maps." required="false"
	 *                rtexprvalue="true"
	 */
	public void setFilterRowsCallback(String filterRowsCallback) {
		this.filterRowsCallback = filterRowsCallback;
	}

	/**
	 * @jsp.attribute description="The reference to a surrounding form element."
	 *                required="false" rtexprvalue="true"
	 */
	public void setForm(String form) {
		this.form = form;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public void setIncludeParameters(String includeParameters) {
		this.includeParameters = includeParameters;
	}

	/**
	 * @jsp.attribute description="A fully qualified class name to a custom
	 *                InterceptTable implementation. Could also be a named type
	 *                in the preferences. Used to add table attributes."
	 *                required="false" rtexprvalue="true"
	 */
	public void setInterceptor(String interceptor) {
		this.interceptor = interceptor;
	}

	/**
	 * @jsp.attribute 
	 *                description="Reference the collection that will be retrieved."
	 *                required="false" rtexprvalue="true"
	 */
	public void setItems(Object items) {
		this.items = items;
	}

	public void setListHeight(String listHeight) {
		this.listHeight = listHeight;
	}

	public void setListWidth(String listWidth) {
		this.listWidth = listWidth;
	}

	/**
	 * @jsp.attribute description="The locale for this table. For example fr_FR
	 *                is used for the French translation." required="false"
	 *                rtexprvalue="true"
	 */
	public void setLocale(String locale) {
		this.locale = locale;
	}

	public void setMaxRowsExported(String maxRowsExported) {
		this.maxRowsExported = maxRowsExported;
	}

	/**
	 * @jsp.attribute 
	 *                description="Used to invoke the table action using a POST or GET."
	 *                required="false" rtexprvalue="true"
	 */
	public void setMethod(String method) {
		this.method = method;
	}

	public void setMinColWidth(String minColWidth) {
		this.minColWidth = minColWidth;
	}

	public void setMinWidth(String minWidth) {
		this.minWidth = minWidth;
	}

	public void setNearPageNum(String nearPageNum) {
		this.nearPageNum = nearPageNum;
	}

	/**
	 * @jsp.attribute description=
	 *                "The javascript that will be invoked when a table action enabled."
	 *                required="false" rtexprvalue="true"
	 */
	public void setOnInvokeAction(String onInvokeAction) {
		this.onInvokeAction = onInvokeAction;
	}

	public void setPageSizeList(String pageSizeList) {
		this.pageSizeList = pageSizeList;
	}

	public void setPaginationLocation(String paginationLocation) {
		this.paginationLocation = paginationLocation;
		this.toolbarLocation = paginationLocation;
	}

	public void setPdfFileName(String pdfFileName) {
		this.pdfFileName = pdfFileName;
	}

	public void setResizeColWidth(String resizeColWidth) {
		this.resizeColWidth = resizeColWidth;
	}

	/**
	 * @jsp.attribute description="A fully qualified class name to a custom
	 *                RetrieveRowsCallback implementation. Could also be a named
	 *                type in the preferences. Used to retrieve the Collection
	 *                of Beans or Collection of Maps." required="false"
	 *                rtexprvalue="true"
	 */
	public void setRetrieveRowsCallback(String retrieveRowsCallback) {
		this.retrieveRowsCallback = retrieveRowsCallback;
	}

	/**
	 * @jsp.attribute description="The number of rows to display in the table."
	 *                required="false" rtexprvalue="true"
	 */
	public void setRowsDisplayed(String rowsDisplayed) {
		this.rowsDisplayed = rowsDisplayed;
	}

	/**
	 * @jsp.attribute description="The scope (page, request, session, or
	 *                application) to find the Collection of beans or Collection
	 *                of Maps defined by the collection attribute."
	 *                required="false" rtexprvalue="true"
	 */
	public void setScope(String scope) {
		this.scope = scope;
	}

	public void setScrollList(String scrollList) {
		this.scrollList = scrollList;
	}

	public void setShowExports(String showExports) {
		this.showExports = showExports;
	}

	public void setShowHeader(String showHeader) {
		this.showHeader = showHeader;
	}

	public void setShowPrint(String showPrint) {
		this.showPrint = showPrint;
	}

	/**
	 * @jsp.attribute description="Specify whether or not to show the title.
	 *                Boolean value with the default being true."
	 *                required="false" rtexprvalue="true"
	 */
	public void setShowTitle(String showTitle) {
		this.showTitle = showTitle;
	}

	/**
	 * @jsp.attribute description="Specify whether or not to show the tooltips.
	 *                Boolean value with the default being true."
	 *                required="false" rtexprvalue="true"
	 */
	public void setShowTooltips(String showTooltips) {
		this.showTooltips = showTooltips;
	}

	/**
	 * @jsp.attribute description="Specify whether or not the table is sortable.
	 *                Boolean value with the default being true."
	 *                required="false" rtexprvalue="true"
	 */
	public void setSortable(String sortable) {
		this.sortable = sortable;
	}

	/**
	 * @jsp.attribute description="A fully qualified class name to a custom
	 *                SortRowsCallback implementation. Could also be a named
	 *                type in the preferences. Used to sort the Collection of
	 *                Beans or Collection of Maps." required="false"
	 *                rtexprvalue="true"
	 */
	public void setSortRowsCallback(String sortRowsCallback) {
		this.sortRowsCallback = sortRowsCallback;
	}

	/**
	 * @jsp.attribute description="The table state to use when returning to a
	 *                table. Acceptable values are default, notifyToDefault,
	 *                persist, notifyToPersist." required="false"
	 *                rtexprvalue="true"
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @jsp.attribute description=
	 *                "The table attribute used to invoke the state change of the table."
	 *                required="false" rtexprvalue="true"
	 */
	public void setStateAttr(String stateAttr) {
		this.stateAttr = stateAttr;
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
	 * @jsp.attribute description="The unique identifier for the table."
	 *                required="false" rtexprvalue="true"
	 */
	public void setTableId(String tableId) {
		// this.tableId = tableId;
		setElementId(tableId);
	}

	/**
	 * @jsp.attribute 
	 *                description="The theme to style the table. The default is eXtremeTable."
	 *                required="false" rtexprvalue="true"
	 */
	public void setTheme(String theme) {
		this.theme = theme;
	}

	/**
	 * @jsp.attribute description="The title of the table. The title will
	 *                display above the table." required="false"
	 *                rtexprvalue="true"
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	public void setToolbarContent(String toolbarContent) {
		this.toolbarContent = toolbarContent;
	}

	public void setToolbarLocation(String toolbarLocation) {
		this.toolbarLocation = toolbarLocation;
	}

	/**
	 * @jsp.attribute description="The name of the variable to hold the current
	 *                row bean." required="false" rtexprvalue="true"
	 */
	public void setVar(String var) {
		this.var = var;
	}

	/**
	 * @jsp.attribute description="Generates the output. The default is the
	 *                HtmlView to generate the HTML. Also used by the exports to
	 *                generate XLS-FO, POI, and CSV." required="false"
	 *                rtexprvalue="true"
	 */
	public void setView(String view) {
		this.view = view;
	}

	/**
	 * @jsp.attribute description="Width of the table." required="false"
	 *                rtexprvalue="true"
	 */
	public void setWidth(String width) {
		this.width = width;
	}

	public void setXlsFileName(String xlsFileName) {
		this.xlsFileName = xlsFileName;
	}

	public String getEvenRowBgColor() {
		return evenRowBgColor;
	}

	public void setEvenRowBgColor(String evenRowBgColor) {
		this.evenRowBgColor = evenRowBgColor;
	}

	public String getOddRowBgColor() {
		return oddRowBgColor;
	}

	public void setOddRowBgColor(String oddRowBgColor) {
		this.oddRowBgColor = oddRowBgColor;
	}

	public String getClassic() {
		return classic;
	}

	public void setClassic(String classic) {
		this.classic = classic;
	}

	public String getDoPreload() {
		return doPreload;
	}

	public void setDoPreload(String doPreload) {
		this.doPreload = doPreload;
	}

	public String getGenerateScript() {
		return generateScript;
	}

	public void setGenerateScript(String generateScript) {
		this.generateScript = generateScript;
	}

	public String getUseAjax() {
		return useAjax;
	}

	public void setUseAjax(String useAjax) {
		this.useAjax = useAjax;
	}

	public String getDeleteAction() {
		return deleteAction;
	}

	public void setDeleteAction(String deleteAction) {
		this.deleteAction = deleteAction;
	}

	public String getInsertAction() {
		return insertAction;
	}

	public void setInsertAction(String insertAction) {
		this.insertAction = insertAction;
	}

	public String getUpdateAction() {
		return updateAction;
	}

	public void setUpdateAction(String updateAction) {
		this.updateAction = updateAction;
	}

	public String getShowTooltips() {
		return showTooltips;
	}

	public String getShadowRowAction() {
		return shadowRowAction;
	}

	public void setShadowRowAction(String shadowRowAction) {
		this.shadowRowAction = shadowRowAction;
	}

	public String getExcludeTool() {
		return excludeTool;
	}

	public void setExcludeTool(String excludeTool) {
		this.excludeTool = excludeTool;
	}

	public String getMinHeight() {
		return minHeight;
	}

	public void setMinHeight(String minHeight) {
		this.minHeight = minHeight;
	}

	public String getEnctype() {
		return enctype;
	}

	public void setEnctype(String enctype) {
		this.enctype = enctype;
	}

}