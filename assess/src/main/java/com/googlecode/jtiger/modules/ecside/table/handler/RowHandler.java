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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.googlecode.jtiger.modules.ecside.core.TableCache;
import com.googlecode.jtiger.modules.ecside.core.TableModel;
import com.googlecode.jtiger.modules.ecside.core.TableModelUtils;
import com.googlecode.jtiger.modules.ecside.core.bean.Row;
import com.googlecode.jtiger.modules.ecside.preferences.PreferencesConstants;

/**
 * @author Jeff Johnston
 */
public class RowHandler {
    private Log logger = LogFactory.getLog(RowHandler.class);
    
    private TableModel model;
    private Row row;

    public RowHandler(TableModel model) {
        this.model = model;
    }

    public TableModel getModel() {
        return model;
    }

    public Row getRow() {
        return row;
    }

    public void addRow(Row row) {
        this.row = row;
        addRowAttributes();
        row.defaults();
    }
    
    public void addRowAttributes() {
        String interceptor = TableModelUtils.getInterceptPreference(model, row.getInterceptor(), PreferencesConstants.ROW_INTERCEPTOR);
        row.setInterceptor(interceptor);
        TableCache.getInstance().getRowInterceptor(interceptor).addRowAttributes(model, row);
    }

    public void modifyRowAttributes() {
        TableCache.getInstance().getRowInterceptor(row.getInterceptor()).modifyRowAttributes(model, row);
    }

    /**
     * Find out if the column is sitting on an even row.
     */
    public boolean isRowEven() {
        if (row.getRowCount() != 0 && (row.getRowCount() % 2) == 0) {
            return true;
        }

        return false;
    }

    /**
     * Find out if the column is sitting on an odd row.
     */
    public boolean isRowOdd() {
        if (row.getRowCount() != 0 && (row.getRowCount() % 2) == 0) {
            return false;
        }

        return true;
    }

    public int increaseRowCount() {
        if (row == null) {
            String msg = "There is no row defined. The row (Row or RowTag) is now required " +
                    "and needs to be put around the columns.";
            logger.error(msg);
            throw new IllegalStateException(msg);
        }

        row.setRowCount(row.getRowCount() + 1);
        return row.getRowCount();
    }
}
