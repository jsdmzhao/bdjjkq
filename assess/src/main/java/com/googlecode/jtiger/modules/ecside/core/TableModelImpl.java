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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.googlecode.jtiger.modules.ecside.core.bean.Column;
import com.googlecode.jtiger.modules.ecside.core.bean.Export;
import com.googlecode.jtiger.modules.ecside.core.bean.Row;
import com.googlecode.jtiger.modules.ecside.core.bean.Table;
import com.googlecode.jtiger.modules.ecside.core.context.WebContext;
import com.googlecode.jtiger.modules.ecside.preferences.TableProperties;
import com.googlecode.jtiger.modules.ecside.table.handler.ColumnHandler;
import com.googlecode.jtiger.modules.ecside.table.handler.ExportHandler;
import com.googlecode.jtiger.modules.ecside.table.handler.RowHandler;
import com.googlecode.jtiger.modules.ecside.table.handler.TableHandler;
import com.googlecode.jtiger.modules.ecside.table.handler.ViewHandler;
import com.googlecode.jtiger.modules.ecside.table.limit.Limit;
import com.googlecode.jtiger.modules.ecside.table.limit.LimitFactory;
import com.googlecode.jtiger.modules.ecside.table.limit.ModelLimitFactory;
import com.googlecode.jtiger.modules.ecside.table.limit.TableLimit;

/**
 * @author Wei Zijun
 *
 */

@SuppressWarnings("unchecked")
public final class TableModelImpl implements TableModel {
    private Log logger = LogFactory.getLog(TableModel.class);

    // model interfaces
    private WebContext context;
    private Preferences preferences;
    private Messages messages;
    private Registry registry;

    // model handlers
    private TableHandler tableHandler = new TableHandler(this);
    private RowHandler rowHandler = new RowHandler(this);
    private ColumnHandler columnHandler = new ColumnHandler(this);
    private ViewHandler viewHandler = new ViewHandler(this);
    private ExportHandler exportHandler = new ExportHandler(this);

    // model objects
    private Object currentRowBean;
//    private Object previousRowBean;
    private Collection collectionOfBeans;
    private Collection collectionOfFilteredBeans;
    private Collection collectionOfPageBeans;
    private Limit limit;
    private Locale locale;

    public TableModelImpl(WebContext context) {
        this(context, null);
    }

    public TableModelImpl(WebContext context, String locale) {
        this.context = context;

        Preferences preferences = new TableProperties();
        preferences.init(context, TableModelUtils.getPreferencesLocation(context));
        this.preferences = preferences;

        this.locale = TableModelUtils.getLocale(context, preferences, locale);

        Messages messages = TableModelUtils.getMessages(this);
        messages.init(context, this.locale);
        this.messages = messages;
    }

    public WebContext getContext() {
        return context;
    }

    public Preferences getPreferences() {
        return preferences;
    }

    public Messages getMessages() {
        return messages;
    }

    public Registry getRegistry() {
        return registry;
    }

    public Table getTableInstance() {
        return new Table(this);
    }

    public Export getExportInstance() {
        return new Export(this);
    }

    public Row getRowInstance() {
        return new Row(this);
    }

    public Column getColumnInstance() {
        return new Column(this);
    }

    public void addTable(Table table) {
        tableHandler.addTable(table);

        // now set the registry
        this.registry = new TableRegistry(this);
        
        //then set the limit
        LimitFactory limitFactory = new ModelLimitFactory(this);
        this.limit = new TableLimit(limitFactory);
    }

    public void addExport(Export export) {
        exportHandler.addExport(export);
    }

    public void addRow(Row row) {
        rowHandler.addRow(row);
    }

    public void addColumn(Column column) {
        columnHandler.addAutoGenerateColumn(column);
    }

    public void addColumns(String autoGenerateColumns) {
        autoGenerateColumns = TableModelUtils.getAutoGenerateColumnsPreference(this, autoGenerateColumns);
        TableCache.getInstance().getAutoGenerateColumns(autoGenerateColumns).addColumns(this);
    }

    /**
     * The parameter value can be null, String, String[], or a List. The
     * parameter value will be converted to a String[] internally.
     * 
     * @param name The parameter name
     * @param value The parameter value
     */
    public void addParameter(String name, Object value) {
        registry.addParameter(name, value);
    }

    public TableHandler getTableHandler() {
        return tableHandler;
    }

    public RowHandler getRowHandler() {
        return rowHandler;
    }

    public ColumnHandler getColumnHandler() {
        return columnHandler;
    }

    public ViewHandler getViewHandler() {
        return viewHandler;
    }

    public ExportHandler getExportHandler() {
        return exportHandler;
    }

//    
//    public Object getPreviousRowBean() {
//        return previousRowBean;
//    }
//    
//    public void setPreviousRowBean(Object bean) {
//    	this.previousRowBean=bean;
//    }
    
    public Object getCurrentRowBean() {
        return currentRowBean;
    }

    public void setCurrentRowBean(Object bean) {
        int rowcount = rowHandler.increaseRowCount();
        this.currentRowBean = bean;
        context.setPageAttribute(TableConstants.ROWCOUNT, String.valueOf(rowcount));
        
        int globalRowcount = rowcount+limit.getRowStart();
        context.setPageAttribute(TableConstants.GLOBALROWCOUNT, String.valueOf(globalRowcount));
        context.setPageAttribute("TOTALROWCOUNT", String.valueOf(globalRowcount));
        
        context.setPageAttribute(tableHandler.getTable().getVar(), bean);
    }
    


    public Collection getCollectionOfBeans() {
        return collectionOfBeans;
    }

    public void setCollectionOfBeans(Collection collectionOfBeans) {
        this.collectionOfBeans = collectionOfBeans;
    }

    public Collection getCollectionOfFilteredBeans() {
        return collectionOfFilteredBeans;
    }

    public void setCollectionOfFilteredBeans(Collection collectionOfFilteredBeans) {
        this.collectionOfFilteredBeans = collectionOfFilteredBeans;
    }

    public Collection getCollectionOfPageBeans() {
        return collectionOfPageBeans;
    }

    public void setCollectionOfPageBeans(Collection collectionOfPageBeans) {
        this.collectionOfPageBeans = collectionOfPageBeans;
    }

    public Limit getLimit() {
        return limit;
    }

    public void setLimit(Limit limit) {
        this.limit = limit;
    }

    public Locale getLocale() {
        return locale;
    }

	private boolean hasExcuteQueryResult=false;
    public void queryResultExecute() throws Exception {

    	if (hasExcuteQueryResult) {
    		return ;
    	}

        Integer totalRows = getTableHandler().getTotalRows();
        int defaultRowsDisplayed = getTable().getRowsDisplayed();
        int totalRowsNum=0;
        if (totalRows != null) {
        	totalRowsNum=totalRows.intValue();
        } else {
        	totalRowsNum=0;
        }
        if (defaultRowsDisplayed<0) defaultRowsDisplayed=totalRowsNum;
        
        context.setPageAttribute(TableConstants.GLOBALROWCOUNT, String.valueOf(totalRowsNum));

        
        limit.setRowAttributes(totalRowsNum, defaultRowsDisplayed);

        if (logger.isDebugEnabled()) {
            logger.debug(limit.toString());
        }

        viewHandler.setView();
    	hasExcuteQueryResult=true;
    }
    
    public Collection execute() throws Exception {
        Collection rows = TableModelUtils.retrieveRows(this);


        
        if (!(rows instanceof List)) {
            rows = new ArrayList(rows); // copy for thread safety  ???
		}


        this.collectionOfBeans = rows;

        rows = TableModelUtils.filterRows(this, rows);

        rows = TableModelUtils.sortRows(this, rows);

        this.collectionOfFilteredBeans = rows;

        Integer totalRows = getTableHandler().getTotalRows();
        int defaultRowsDisplayed = getTable().getRowsDisplayed();
        int totalRowsNum=0;
        if (totalRows != null) {
        	totalRowsNum=totalRows.intValue();
        } else {
        	totalRowsNum=rows.size();
        }
        if (defaultRowsDisplayed<0) defaultRowsDisplayed=totalRowsNum;
        
        context.setPageAttribute(TableConstants.GLOBALROWCOUNT, String.valueOf(totalRowsNum));

        
        limit.setRowAttributes(totalRowsNum, defaultRowsDisplayed);

        if (logger.isDebugEnabled()) {
            logger.debug(limit.toString());
        }

        rows = TableModelUtils.getCurrentRows(this, rows);

        this.collectionOfPageBeans = rows;

        viewHandler.setView();

        return rows;
    }

    public void setColumnValues() throws Exception {
        List columns = columnHandler.getColumns();
        Iterator iter = columns.iterator();
        
    	String[] cellValues = (String[])getTable().getAttribute(ECSideConstants.TABLE_CELL_VALUES_KEY);   
//    	String[] cellNames = (String[])getTable().getAttribute(ECSideConstants.TABLE_CELLNAMES_KEY);   
    	
    	String[] editEvents = (String[])getTable().getAttribute(ECSideConstants.TABLE_EDIT_EVENTS_KEY);   
    	String[] editTemplates = (String[])getTable().getAttribute(ECSideConstants.TABLE_EDIT_TEMPLATES_KEY);
    	String[] editables=(String[])getTable().getAttribute(ECSideConstants.TABLE_EDITABLES_KEY);
    	
    	int i=0;
        while (iter.hasNext()) {
            Column column = (Column) iter.next();
            if ("true".equals(column.getAttribute(TableConstants.IS_AUTO_GENERATE_COLUMN))) {
                String property = column.getProperty();
                Object propertyValue = TableModelUtils.getColumnPropertyValue(currentRowBean, property);
                column.setValue(propertyValue);
                column.setPropertyValue(propertyValue);
                
                if (cellValues!=null && i<cellValues.length && StringUtils.isNotBlank(cellValues[i]) ){
                	column.setCellValue(cellValues[i].trim());
                }
//                if (cellNames!=null && i<cellNames.length && StringUtils.isNotBlank(cellNames[i]) ){
//                	column.setCellName(cellNames[i].trim());
//                }
                
                if (editEvents!=null && i<editEvents.length && StringUtils.isNotBlank(editEvents[i]) ){
                	column.setEditEvent(editEvents[i].trim());
                }
                if (editTemplates!=null && i<editTemplates.length && StringUtils.isNotBlank(editTemplates[i]) ){
                	column.setEditTemplate(editTemplates[i].trim());
                }
                if (editables!=null && i<editables.length && StringUtils.isNotBlank(editables[i]) ){
                	column.setEditable(Boolean.valueOf(editables[i].trim()));    
                }
                String propertyValueString=propertyValue+"";
                if (propertyValueString.length()>ECSideConstants.SHOW_TITLE_MIN_LENGTH){
                	column.setAttribute(TableConstants.EXTEND_ATTRIBUTES, " title=\""+propertyValue+"\" ");
                }
                columnHandler.modifyColumnAttributes(column);
                viewHandler.addColumnValueToView(column);
                i++;
            }
        }
    }

    public Object getViewData() throws Exception {
        Object viewData = viewHandler.getView().afterBody(this);

        if (limit.isExported()) {
            context.setRequestAttribute(TableConstants.VIEW_DATA, viewData);
            context.setRequestAttribute(TableConstants.VIEW_RESOLVER, exportHandler.getCurrentExport().getViewResolver());
            context.setRequestAttribute(TableConstants.EXPORT_FILE_NAME, exportHandler.getCurrentExport().getFileName());
            if (!exportHandler.getCurrentExport().getView().equals(TableConstants.VIEW_PRINT) ){
            	return "";
            }
        }

        return viewData;
    }

    /**
     * Will execute the model and interate over the rows. Very useful for
     * assembling a table using java code.
     */
    public Object assemble() throws Exception {
        Iterator iterator = execute().iterator();
        for (Iterator iter = iterator; iter.hasNext();) {
            Object bean = iterator.next();
            setCurrentRowBean(bean);

            // call to modify attributes
            getRowHandler().modifyRowAttributes();

            // call columns to set values
            setColumnValues();
        }

        return getViewData();
    }
    
    List propertyNameList=new ArrayList();
	public void addPropertyName(String propertyName){
		propertyNameList.add(propertyName);		
	}

	public String getPropertyName(int idx) {
		return (String)propertyNameList.get(idx);
	}

	public List getPropertyNameList() {
		return propertyNameList;
	}

	public Table getTable() {
		return this.getTableHandler().getTable();
	}

}
