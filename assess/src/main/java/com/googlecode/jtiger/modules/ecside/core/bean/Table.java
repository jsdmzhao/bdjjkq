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
import com.googlecode.jtiger.modules.ecside.preferences.TableProperties;

/**
 * @author Wei Zijun
 *
 */

@SuppressWarnings("serial")
public class Table extends BaseBean {

    private String action;
    
    private String enctype;
    
	private String insertAction;
	private String updateAction;
	private String deleteAction;
	private String shadowRowAction;
	private Boolean batchUpdate;
	
    private Boolean autoIncludeParameters;
    private String border;
    private Boolean bufferView;
    private String cellpadding;
    private String cellspacing;
    private Boolean filterable;
    private String filterRowsCallback;
    private String form;
    private String imagePath;
    private String interceptor;
    private Object items;
    private String locale;
//    private int maxRowsDisplayed;
//    private int medianRowsDisplayed;
    private String method;
    private String onInvokeAction;
    private String retrieveRowsCallback;
    private int rowsDisplayed;
    private Boolean saveFilterSort;
    private String scope;
    private Boolean showTitle;
    private Boolean showTooltips;
    private String sortRowsCallback;
    private Boolean sortable;
    private String state;
    private String stateAttr;
    private String style;
    private String styleClass;
    private String tableId;
    private String title;
    private String theme;
    private String var;
    private String view;
    private String width;
    private String height;
    
    private String pageSizeList;
    private Boolean resizeColWidth;
    private Boolean editable;
    private int minColWidth;
    
    private String nearPageNum;
    
    
    private String includeParameters;
    private String excludeParameters;
    
    private String maxRowsExported;
    
    private Boolean scrollList;
    private String listWidth;
//    private String listHeight;
    private String minWidth;
    
    private Boolean showHeader;
    
	private String oddRowBgColor;
	private String evenRowBgColor;
	
	protected Boolean classic;
   
	private int totalWidth=0;
	
	private Boolean generateScript;
	private Boolean useAjax;
	private Boolean doPreload;
	
	private String excludeTool;
	
	private String minHeight;
	
    public Table(TableModel model) {
        setModel(model);
    }

    public void defaults() {
        this.tableId = TableDefaults.getTableId(tableId); //must be first
        
        
        this.generateScript = TableDefaults.isGenerateScript(getModel(), generateScript);
        this.useAjax =TableDefaults.isUseAjax(getModel(), useAjax);
        this.doPreload = TableDefaults.isDoPreload(getModel(), doPreload);
    	
        this.classic=TableDefaults.isClassic(getModel(), classic);
        this.autoIncludeParameters = TableDefaults.getAutoIncludeParameters(getModel(), autoIncludeParameters);
        
        this.batchUpdate = TableDefaults.getBatchUpdate(getModel(), batchUpdate);
        
        this.border = TableDefaults.getBorder(getModel(), border);
        this.bufferView = TableDefaults.isBufferView(getModel(), bufferView);
        this.cellpadding = TableDefaults.getCellpadding(getModel(), cellpadding);
        this.cellspacing = TableDefaults.getCellspacing(getModel(), cellspacing);
        this.filterable = TableDefaults.isFilterable(getModel(), filterable);
        this.filterRowsCallback = TableDefaults.getFilterRowsCallback(getModel(), filterRowsCallback);
//        this.imagePath = TableDefaults.getImagePath(getModel(), imagePath);
//        this.maxRowsDisplayed = TableDefaults.getMaxRowsDisplayed(getModel());
//        this.medianRowsDisplayed = TableDefaults.getMedianRowsDisplayed(getModel());
        this.method = TableDefaults.getMethod(getModel(), method);
        this.retrieveRowsCallback = TableDefaults.getRetrieveRowsCallback(getModel(), retrieveRowsCallback);
        this.rowsDisplayed = TableDefaults.getRowsDisplayed(getModel(), rowsDisplayed);

        this.showTitle = TableDefaults.isShowTitle(getModel(), showTitle);
        this.showTooltips = TableDefaults.isShowTooltips(getModel(), showTooltips);
        this.state = TableDefaults.getState(getModel(), state);
        this.stateAttr = TableDefaults.getStateAttr(getModel(), stateAttr);
        this.sortable = TableDefaults.isSortable(getModel(), sortable);
        this.sortRowsCallback = TableDefaults.getSortRowsCallback(getModel(), sortRowsCallback);
        this.styleClass = TableDefaults.getStyleClass(getModel(), styleClass);
        this.theme = TableDefaults.getTheme(getModel(), theme);
        this.title = TableDefaults.getTitle(getModel(), title);
        this.var = TableDefaults.getVar(var, tableId);
        this.view = TableDefaults.getView(view);
        this.width = TableDefaults.getWidth(getModel(), width);
        
        this.toolbarLocation=TableDefaults.getToolbarLocation(getModel(), toolbarLocation);
        this.alwaysShowExtend=TableDefaults.getAlwaysShowExtend(getModel(), alwaysShowExtend);
        this.toolbarContent=TableDefaults.getToolbarContent(getModel(), toolbarContent);
        this.pageSizeList=TableDefaults.getPageSizeList(getModel(), pageSizeList);
        
        this.resizeColWidth=TableDefaults.getResizeColWidth(getModel(), resizeColWidth);
        this.minColWidth=TableDefaults.getMinColWidth(getModel(), minColWidth);
        this.nearPageNum=TableDefaults.getNearPageNum(getModel(), nearPageNum);
        this.maxRowsExported=TableDefaults.getMaxRowsExported(getModel(), maxRowsExported);
        
        this.editable = TableDefaults.isEditable(getModel(), editable);
        
        this.excludeTool = TableProperties.getDefaultPreference(model, "table.excludeTool", excludeTool);
        

    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public boolean isAutoIncludeParameters() {
        return autoIncludeParameters.booleanValue();
    }

    public void setAutoIncludeParameters(Boolean autoIncludeParameters) {
        this.autoIncludeParameters = autoIncludeParameters;
    }

    public String getBorder() {
        return border;
    }

    public void setBorder(String border) {
        this.border = border;
    }

    public boolean isBufferView() {
        return bufferView.booleanValue();
    }

    public void setBufferView(Boolean bufferView) {
        this.bufferView = bufferView;
    }

    public String getCellpadding() {
        return cellpadding;
    }

    public void setCellpadding(String cellpadding) {
        this.cellpadding = cellpadding;
    }

    public String getCellspacing() {
        return cellspacing;
    }

    public void setCellspacing(String cellspacing) {
        this.cellspacing = cellspacing;
    }

    public boolean isFilterable() {
        return filterable.booleanValue();
    }

    public void setFilterable(Boolean filterable) {
        this.filterable = filterable;
    }

    public String getFilterRowsCallback() {
        return filterRowsCallback;
    }

    public void setFilterRowsCallback(String filterRowsCallback) {
        this.filterRowsCallback = filterRowsCallback;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getInterceptor() {
        return interceptor;
    }

    public void setInterceptor(String interceptor) {
        this.interceptor = interceptor;
    }

    public Object getItems() {
        return items;
    }

    public void setItems(Object items) {
        this.items = items;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getOnInvokeAction() {
        return onInvokeAction;
    }

    public void setOnInvokeAction(String onInvokeAction) {
        this.onInvokeAction = onInvokeAction;
    }

    public String getRetrieveRowsCallback() {
        return retrieveRowsCallback;
    }

    public void setRetrieveRowsCallback(String retrieveRowsCallback) {
        this.retrieveRowsCallback = retrieveRowsCallback;
    }

    public boolean isSaveFilterSort() {
        return saveFilterSort.booleanValue();
    }

    public void setSaveFilterSort(Boolean saveFilterSort) {
        this.saveFilterSort = saveFilterSort;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public boolean isShowTitle() {
        return showTitle.booleanValue();
    }

    public void setShowTitle(Boolean showTitle) {
        this.showTitle = showTitle;
    }

    public boolean isShowTooltips() {
        return showTooltips.booleanValue();
    }

    public void setShowTooltips(Boolean showTooltips) {
        this.showTooltips = showTooltips;
    }

    public boolean isSortable() {
        return sortable.booleanValue();
    }

    public void setSortable(Boolean sortable) {
        this.sortable = sortable;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStateAttr() {
        return stateAttr;
    }

    public void setStateAttr(String stateAttr) {
        this.stateAttr = stateAttr;
    }

    public String getSortRowsCallback() {
        return sortRowsCallback;
    }

    public void setSortRowsCallback(String sortRowsCallback) {
        this.sortRowsCallback = sortRowsCallback;
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

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getVar() {
        return var;
    }

    public void setVar(String var) {
        this.var = var;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public int getRowsDisplayed() {
        return rowsDisplayed;
    }

    public void setRowsDisplayed(int rowsDisplayed) {
        this.rowsDisplayed = rowsDisplayed;
    }

//    public int getMedianRowsDisplayed() {
//        return medianRowsDisplayed;
//    }
//
//    public void setMedianRowsDisplayed(int medianRowsDisplayed) {
//        this.medianRowsDisplayed = medianRowsDisplayed;
//    }
//
//    public int getMaxRowsDisplayed() {
//        return maxRowsDisplayed;
//    }
//
//    public void setMaxRowsDisplayed(int maxRowsDisplayed) {
//        this.maxRowsDisplayed = maxRowsDisplayed;
//    }


    private String toolbarLocation=null;
    private String toolbarContent=null;
    private String alwaysShowExtend=null;

	public String getToolbarContent() {
		return toolbarContent;
	}

	public void setToolbarContent(String toolbarContent) {
		this.toolbarContent = toolbarContent;
	}

	public String getToolbarLocation() {
		return toolbarLocation;
	}

	public void setToolbarLocation(String toolbarLocation) {
		this.toolbarLocation = toolbarLocation;
	}

	public String getPageSizeList() {
		return pageSizeList;
	}

	public void setPageSizeList(String pageSizeList) {
		this.pageSizeList = pageSizeList;
	}

	public int getMinColWidth() {
		return minColWidth;
	}

	public void setMinColWidth(int minColWidth) {
		this.minColWidth = minColWidth;
	}

	public Boolean getResizeColWidth() {
		return resizeColWidth;
	}

	public boolean isResizeColWidth() {
		return resizeColWidth==null?false:resizeColWidth.booleanValue();
	}
	
	public void setResizeColWidth(Boolean resizeColWidth) {
		this.resizeColWidth = resizeColWidth;
	}

	public String getAlwaysShowExtend() {
		return alwaysShowExtend;
	}

	public void setAlwaysShowExtend(String alwaysShowExtend) {
		this.alwaysShowExtend = alwaysShowExtend;
	}

	public String getExcludeParameters() {
		return excludeParameters;
	}

	public void setExcludeParameters(String excludeParameters) {
		this.excludeParameters = excludeParameters;
	}

	public String getIncludeParameters() {
		return includeParameters;
	}

	public void setIncludeParameters(String includeParameters) {
		this.includeParameters = includeParameters;
	}

//	public String getListHeight() {
//		return listHeight;
//	}
//
//	public void setListHeight(String listHeight) {
//		this.listHeight = listHeight;
//	}

	public String getListWidth() {
		return listWidth;
	}

	public void setListWidth(String listWidth) {
		this.listWidth = listWidth;
	}

	public Boolean getScrollList() {
		return scrollList;
	}

	public void setScrollList(Boolean scrollList) {
		this.scrollList = scrollList;
	}

	public String getNearPageNum() {
		return nearPageNum;
	}

	public void setNearPageNum(String nearPageNum) {
		this.nearPageNum = nearPageNum;
	}

	public String getMaxRowsExported() {
		return maxRowsExported;
	}

	public void setMaxRowsExported(String maxRowsExported) {
		this.maxRowsExported = maxRowsExported;
	}

	public boolean isEditable() {
		return editable==null||editable.booleanValue();
	}
	
	public Boolean getEditable() {
		return editable;
	}

	public void setEditable(Boolean editable) {
		this.editable = editable;
	}

	public Boolean getShowHeader() {
		return showHeader;
	}
	public boolean isShowHeader() {
		return showHeader==null||showHeader.booleanValue();
	}
	

	public void setShowHeader(Boolean showHeader) {
		this.showHeader = showHeader;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getMinWidth() {
		return minWidth;
	}

	public void setMinWidth(String minWidth) {
		this.minWidth = minWidth;
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




	public Boolean getClassic() {
		return classic;
	}

	public void setClassic(Boolean classic) {
		this.classic = classic;
	}

	
	public boolean isClassic() {
		return classic==null?false:classic.booleanValue();
	}
	
	public boolean isGenerateScript() {
		return generateScript==null?false:generateScript.booleanValue();
	}

	public void setGenerateScript(Boolean generateScript) {
		this.generateScript = generateScript;
	}

	public boolean isDoPreload() {
		return doPreload==null?false:doPreload.booleanValue();
	}

	public void setDoPreload(Boolean doPreload) {
		this.doPreload = doPreload;
	}

	public boolean isUseAjax() {
		return useAjax==null?false:useAjax.booleanValue();
	}

	public void setUseAjax(Boolean useAjax) {
		this.useAjax = useAjax;
	}

	public int getTotalWidth() {
		return totalWidth;
	}

	public void setTotalWidth(int totalWidth) {
		this.totalWidth = totalWidth;
	}
	public void addTotalWidth(int width) {
		this.totalWidth += width;
	}
	

     public void beforeBody(){
    	    classic=getClassic();
//    	    || StringUtils.isNotBlank(listWidth)
    	     if (StringUtils.isNotBlank(height)){
    	     	if ("auto".equals(height.toLowerCase())){
    	     		height="100%";
    	     	}
    	     	classic=Boolean.FALSE;
    	     }else{
//    	     	classic=Boolean.TRUE;
    	     }
     }
	public void afterBody(){

        

	        
//	        if (StringUtils.isBlank(listWidth)){
//	        	listWidth=getWidth();
//	        }
	    	
		     if (StringUtils.isBlank(listWidth)){
		    	   listWidth=""+getTotalWidth();
		     }
		       
	    	if (height!=null&&height.indexOf("px")==-1 && height.indexOf("%")==-1){
	    		height=height+"px";
	    	}
	    	if (isClassic()){
	    		resizeColWidth=Boolean.FALSE;
	    	}
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

	public String getShadowRowAction() {
		return shadowRowAction;
	}

	public void setShadowRowAction(String shadowRowAction) {
		this.shadowRowAction = shadowRowAction;
	}

	public String getUpdateAction() {
		return updateAction;
	}

	public void setUpdateAction(String updateAction) {
		this.updateAction = updateAction;
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

	public boolean isBatchUpdate() {
		return batchUpdate==null?false:batchUpdate.booleanValue();
	}
	
	public Boolean getBatchUpdate() {
		return batchUpdate;
	}

	public void setBatchUpdate(Boolean batchUpdate) {
		this.batchUpdate = batchUpdate;
	}

	public String getEnctype() {
		return enctype;
	}

	public void setEnctype(String enctype) {
		this.enctype = enctype;
	}
   
    
}