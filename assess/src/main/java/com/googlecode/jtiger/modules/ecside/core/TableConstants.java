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
package com.googlecode.jtiger.modules.ecside.core;


/**
 * @author Wei Zijun
 *
 */

public class TableConstants {

    private TableConstants() {
    }

    // need in set the column property to alias
    public final static String ALIAS="a_";

    // need in controller and tag
    public final static String ROWCOUNT = "ROWCOUNT";

    // ec attributes
    public final static int DEFAULT_PAGE_SIZE = 10;
    public final static String EXTREME_COMPONENTS = "ec";
    public final static String EXTREME_COMPONENTS_INSTANCE = "ec_i";
    

    // export attributes
    public final static String EXPORT_TABLE_ID = "eti"; // throw-away parameter for export
    public final static String EXPORT_PAGE_FLAG = "eti_p";
    
    // column attributes
    public final static String IS_AUTO_GENERATE_COLUMN = "isAutoGenerateColumn";
    public final static String DATE = "date";
    public final static String CURRENCY = "currency";

    // web.xml attributes
    public final static String MESSAGES_LOCATION = "ecsideMessagesLocation";
    public final static String PREFERENCES_LOCATION = "ecsidePreferencesLocation";

    // limit attributes
    public final static String FILTER = "f_";
    public final static String SORT = "s_";
    public final static String PAGE = "p";
    public final static String CURRENT_ROWS_DISPLAYED = "crd";
    public final static String EXPORT_VIEW = "ev";
    public final static String EXPORT_FILE_NAME = "efn";

    public final static String ACTION = "a";
    public final static String FILTER_ACTION = "fa";
    public final static String CLEAR_ACTION = "ca";

    public final static String SORT_ASC = "asc";
    public final static String SORT_DESC = "desc";
    public final static String SORT_DEFAULT = "default";

    // not used for calculations
    public final static String ROWS_DISPLAYED = "rd";

    // callback attributes
    public final static String CALLBACK_DEFAULT = "default";

    // view attributes
    public final static String VIEW_HTML = "html";
    public final static String VIEW_PDF = "pdf";
    public final static String VIEW_XLS = "xls";
    public final static String VIEW_CSV = "csv";
    public final static String VIEW_DATA = "viewData";
    public final static String VIEW_RESOLVER = "viewResolver";

    // state attributes
    public final static String STATE = "s_";
    public final static String STATE_DEFAULT = "default";
    public final static String STATE_NOTIFY_TO_DEFAULT = "notifyToDefault";
    public final static String STATE_PERSIST = "persist";
    public final static String STATE_NOTIFY_TO_PERSIST = "notifyToPersist";

    // cell attributes
    public final static String CELL_DISPLAY = "display";
    public final static String CELL_FILTER = "filter";
    public final static String CELL_HEADER = "header";

    // interceptors
    public final static String DEFAULT_INTERCEPT = "default";

    // calc attributes
    public final static String CALC_TOTAL = "total";

    //scope attributes
    public final static String PAGE_SCOPE = "page";
    public final static String REQUEST_SCOPE = "request";
    public final static String SESSION_SCOPE = "session";
    public final static String APPLICATION_SCOPE = "application";

    // total rows
    public final static String TOTAL_ROWS = "totalRows";
    
    //////////////////////////////////////////////////////
    
	//gotopages attributes
	public final static String PAGES = "pg";

	public final static String GLOBALROWCOUNT = "GLOBALROWCOUNT";
	
	//highlight attributes
	public final static String HIGHLIGHT_CLASS = "highlightClass";
	public final static String HIGHLIGHT = "highlight";
	
	public final static String EXTEND_ATTRIBUTES = "attributes";
	
	//mouse event attributes
	public final static String ON_CLICK = "onclick";
	public final static String ON_DOUBLE_CLICK = "ondblclick";
	public final static String ON_MOUSE_OUT = "onmouseout";
	public final static String ON_MOUSE_OVER = "onmouseover";

	public final static String HEADER_SPAN = "headerspan";
	public final static String CALC_SPAN = "calcspan";
	
	//page location attributes
	public final static String TOOLBAR_LOCATION = "toolbarLocation";
	public final static String TOOLBAR_LOCATION_TOP = "top";
	public final static String TOOLBAR_LOCATION_BOTTOM = "bottom";
	public final static String TOOLBAR_LOCATION_BOTH = "both";
	
	//show rows displayed attributes
	public final static String SHOW_ROWS_DISPLAYED = "showRowsDisplayed";
	public final static String SHOW_GOTO_PAGE = "showGotoPage";
	
	public final static String XLS_FILE = "xlsFileName";
	public final static String CSV_FILE = "cvsFileName";
	public final static String PDF_FILE = "pdfFileName";
	public final static String SHOW_PRINT = "showPrint";
	
	
	public final static String TAG_ATTRIBUTES = "tagAttributes";
	
	public final static String USE_AJAX_PREP = "useAjaxPrep";
	public final static String IS_AJAX_REQUEST = "isAjaxRequest";

	//image names
	public final static String TOOLBAR_GOTO_PAGE_IMAGE = "gotoPage";


	public final static String TOOLBAR_GOTO_PAGE_CSS = "gotopage";
	
	public final static String HIDDEN_TOTAL_PAGES = "totalpages";
	public final static String HIDDEN_TOTAL_ROWS = "totalrows";

	// text tooltip messages
	public final static String TOOLBAR_ROW_DISPLAYED_TOOLTIP = "toolbar.tooltip.rowsDisplayed";

	public final static String TOOLBAR_GOTO_PAGE_TOOLTIP = "toolbar.tooltip.gotoPages";
	
	public final static String TOOLBAR_PRINT_TEXT = "toolbar.tooltip.print";
	
	
	public final static String ROWSDISPLAYED_CSS = "rowsdisplayed";
	
	public final static String FIRST_PAGE_CSS = "pageNav firstPage";
	public final static String PREV_PAGE_CSS = "pageNav prevPage";
	public final static String NEXT_PAGE_CSS = "pageNav nextPage";
	public final static String LAST_PAGE_CSS = "pageNav lastPage";
	public final static String JUMP_PAGE_CSS = "pageNav jumpPage";
	public final static String JUMP_PAGE_INPUT_CSS = "jumpPageInput";

	
	public final static String REFRESH_BUTTON_CSS = "toolButton girdRefresh";
	public final static String SAVE_BUTTON_CSS = "toolButton girdSave";
	public final static String ADD_BUTTON_CSS = "toolButton girdAdd";
	public final static String DEL_BUTTON_CSS = "toolButton girdDel";
	
	public final static String EXPORTXLS_BUTTON_CSS = "toolButton exportXls";
	public final static String EXPORTPDF_BUTTON_CSS = "toolButton exportPdf";
	public final static String EXPORTCSV_BUTTON_CSS = "toolButton exportCsv";
	public final static String EXPORTPRINT_BUTTON_CSS = "toolButton exportPrint";
	
	public final static String VIEW_PRINT = "print";
	public final static String PRINT_VIEW_THEME = "eXtremeTablePrint";
	
	public final static String EXPORT_IFRAME_ID="ecs_export_iframe";
	
	public final static String RECORDKEY_NAME="recordKey";
	
	public final static String SERVLET_REAL_PATH="ecs_servletRealPath";
	
	public final static String AJAX_ZONE_BEGIN="_begin_ ";
	public final static String AJAX_ZONE_END=" _end_";
	public final static String AJAX_ZONE_PREFIX="<!-- ECS_AJAX_ZONE_PREFIX_";
	public final static String AJAX_ZONE_SUFFIX="_ECS_AJAX_ZONE_SUFFIX -->";

	public final static String AJAX_FINDZONE_CLIENT = "findAjaxZoneAtClient";
	
	public final static String MAIN_CONTENT_ID = "main_content";
	
	
	public final static String DEFAULT_AUTOGENERATECOLUMNS="com.googlecode.jtiger.modules.ecside.core.bean.AutoGenerateColumnsImpl";
	
//	public final static int SCROLLBAR_WIDTH=18;

}
