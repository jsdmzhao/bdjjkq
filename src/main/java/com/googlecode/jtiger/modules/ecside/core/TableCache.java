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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.googlecode.jtiger.modules.ecside.table.calc.Calc;
import com.googlecode.jtiger.modules.ecside.table.callback.FilterRowsCallback;
import com.googlecode.jtiger.modules.ecside.table.callback.RetrieveRowsCallback;
import com.googlecode.jtiger.modules.ecside.table.callback.SortRowsCallback;
import com.googlecode.jtiger.modules.ecside.table.cell.Cell;
import com.googlecode.jtiger.modules.ecside.table.interceptor.ColumnInterceptor;
import com.googlecode.jtiger.modules.ecside.table.interceptor.ExportInterceptor;
import com.googlecode.jtiger.modules.ecside.table.interceptor.RowInterceptor;
import com.googlecode.jtiger.modules.ecside.table.interceptor.TableInterceptor;
import com.googlecode.jtiger.modules.ecside.table.state.State;

/**
 * @author Jeff Johnston
 */

@SuppressWarnings("unchecked")
public class TableCache {
    private Log logger = LogFactory.getLog(TableCache.class);

    public static TableCache tableCache = new TableCache();
    private Map cache = Collections.synchronizedMap(new HashMap());

    private TableCache() {
    }

    public static TableCache getInstance() {
        return tableCache;
    }

    private Object getCachedObject(String className) {
        Object cachedObject = null;

        try {
           // TODO:wzj
            cachedObject = cache.get(className);
            if (cachedObject == null) {
            	 Class classForName = Class.forName(className);
                cachedObject = classForName.newInstance(); //possible to create extra class, which is acceptable.
                cache.put(className, cachedObject);
            }

        } catch (Exception e) {
            String msg = "Could not create the object [" + className + "]. The class was not found or does not exist.";
            logger.error(msg, e);
            throw new IllegalStateException(msg);
        }

        return cachedObject;
    }

    public Cell getCell(String cell) {
        return (Cell) getCachedObject(cell);
    }

    public State getState(String state) {
        return (State) getCachedObject(state);
    }

    public RetrieveRowsCallback getRetrieveRowsCallback(TableModel model) {
        String retrieveRowsCallback = model.getTable().getRetrieveRowsCallback();
        return (RetrieveRowsCallback) getCachedObject(retrieveRowsCallback);
    }

    public FilterRowsCallback getFilterRowsCallback(TableModel model) {
        String filterRowsCallback = model.getTable().getFilterRowsCallback();
        return (FilterRowsCallback) getCachedObject(filterRowsCallback);
    }

    public SortRowsCallback getSortRowsCallback(TableModel model) {
        String sortRowsCallback = model.getTable().getSortRowsCallback();
        return (SortRowsCallback) getCachedObject(sortRowsCallback);
    }

    public Calc getCalc(String calc) {
        return (Calc) getCachedObject(calc);
    }

    public TableInterceptor getTableInterceptor(String tableInterceptor) {
        return (TableInterceptor) getCachedObject(tableInterceptor);
    }

    public RowInterceptor getRowInterceptor(String rowInterceptor) {
        return (RowInterceptor) getCachedObject(rowInterceptor);
    }

    public ColumnInterceptor getColumnInterceptor(String columnInterceptor) {
        return (ColumnInterceptor) getCachedObject(columnInterceptor);
    }
    
    public ExportInterceptor getExportInterceptor(String exportInterceptor) {
        return (ExportInterceptor) getCachedObject(exportInterceptor);
    }

    public AutoGenerateColumns getAutoGenerateColumns(String autoGenerateColumns) {
        return (AutoGenerateColumns) getCachedObject(autoGenerateColumns);
    }
}
