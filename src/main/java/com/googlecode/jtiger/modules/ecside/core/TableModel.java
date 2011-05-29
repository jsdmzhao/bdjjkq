/*
 * Copyright 2004 original author or authors.
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

import java.util.Collection;
import java.util.List;
import java.util.Locale;

import com.googlecode.jtiger.modules.ecside.core.bean.Column;
import com.googlecode.jtiger.modules.ecside.core.bean.Export;
import com.googlecode.jtiger.modules.ecside.core.bean.Row;
import com.googlecode.jtiger.modules.ecside.core.bean.Table;
import com.googlecode.jtiger.modules.ecside.core.context.WebContext;
import com.googlecode.jtiger.modules.ecside.table.handler.ColumnHandler;
import com.googlecode.jtiger.modules.ecside.table.handler.ExportHandler;
import com.googlecode.jtiger.modules.ecside.table.handler.RowHandler;
import com.googlecode.jtiger.modules.ecside.table.handler.TableHandler;
import com.googlecode.jtiger.modules.ecside.table.handler.ViewHandler;
import com.googlecode.jtiger.modules.ecside.table.limit.Limit;

/**
 * @author Wei Zijun
 */

@SuppressWarnings("unchecked")
public interface TableModel {
    public WebContext getContext();

    public Preferences getPreferences();

    public Messages getMessages();

    public Registry getRegistry();

    public Table getTableInstance();

    public Export getExportInstance();

    public Row getRowInstance();

    public Column getColumnInstance();

    public void addTable(Table table);

    public void addExport(Export export);

    public void addRow(Row row);

    public void addColumn(Column column);

    public void addColumns(String autoGenerateColumns);

    public void addParameter(String name, Object value);

    public TableHandler getTableHandler();
    
    public Table getTable();

    public RowHandler getRowHandler();

    public ColumnHandler getColumnHandler();

    public ViewHandler getViewHandler();

    public ExportHandler getExportHandler();

    public Object getCurrentRowBean();
    
//    public Object getPreviousRowBean();

    public void setCurrentRowBean(Object bean);

//    public void setPreviousRowBean(Object bean);
    
    public Collection getCollectionOfBeans();

    public void setCollectionOfBeans(Collection collectionOfBeans);

    public Collection getCollectionOfFilteredBeans();

    public void setCollectionOfFilteredBeans(Collection collectionOfFilteredBeans);

    public Collection getCollectionOfPageBeans();

    public void setCollectionOfPageBeans(Collection collectionOfPageBeans);

    public Limit getLimit();

    public void setLimit(Limit limit);

    public Locale getLocale();

    public Collection execute() throws Exception;

    public void setColumnValues() throws Exception;

    public Object getViewData() throws Exception;

    public Object assemble() throws Exception;
    
    public void addPropertyName(String propertyName) ;
    
    public String getPropertyName(int idx);
    
    public List getPropertyNameList();
    
    public void queryResultExecute() throws Exception ;
    
}