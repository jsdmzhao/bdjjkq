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
package com.googlecode.jtiger.modules.ecside.table.handler;

import com.googlecode.jtiger.modules.ecside.core.TableCache;
import com.googlecode.jtiger.modules.ecside.core.TableConstants;
import com.googlecode.jtiger.modules.ecside.core.TableModel;
import com.googlecode.jtiger.modules.ecside.core.TableModelUtils;
import com.googlecode.jtiger.modules.ecside.core.bean.Table;
import com.googlecode.jtiger.modules.ecside.preferences.PreferencesConstants;

/**
 * The first pass over the table just loads up the column properties. The
 * properties will be loaded up and held here.
 * 
 * @author Jeff Johnston
 */
public class TableHandler {
    protected TableModel model;
    private Table table;

    public TableHandler(TableModel model) {
        this.model = model;
    }

    public Table getTable() {
        return table;
    }

    public void addTable(Table table) {
        this.table = table;
        addTableAttributes();
        table.defaults();
    }
    
    public void addTableAttributes() {
        String interceptor = TableModelUtils.getInterceptPreference(model, table.getInterceptor(), PreferencesConstants.TABLE_INTERCEPTOR);
        table.setInterceptor(interceptor);
        TableCache.getInstance().getTableInterceptor(interceptor).addTableAttributes(model, table);
    }

    public String prefixWithTableId() {
        return table.getTableId() + "_";
    }
    
    /**
     * @see #setTotalRows(Integer)
     * 
     * @return The totalRows that will be used for the Limit.setAttributes().
     */
    public Integer getTotalRows() {
        return (Integer) table.getAttribute(TableConstants.TOTAL_ROWS);
    }
    
    /**
     * If using the Limit to filter and sort the rows ahead of time then need
     * a place to store the total rows that was passed to the table.
     * 
     * @param totalRows The totalRows that will be used for the Limit.setAttributes().
     */
    public void setTotalRows(Integer totalRows) {
        table.setAttribute(TableConstants.TOTAL_ROWS, totalRows);
    }
}
