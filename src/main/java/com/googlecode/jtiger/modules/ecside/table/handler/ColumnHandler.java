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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.googlecode.jtiger.modules.ecside.core.TableCache;
import com.googlecode.jtiger.modules.ecside.core.TableConstants;
import com.googlecode.jtiger.modules.ecside.core.TableModel;
import com.googlecode.jtiger.modules.ecside.core.TableModelUtils;
import com.googlecode.jtiger.modules.ecside.core.bean.Column;
import com.googlecode.jtiger.modules.ecside.preferences.PreferencesConstants;
import com.googlecode.jtiger.modules.ecside.table.calc.CalcResult;
import com.googlecode.jtiger.modules.ecside.table.calc.CalcUtils;
import com.googlecode.jtiger.modules.ecside.table.cell.Cell;

/**
 * The first pass over the table just loads up the column properties. The
 * properties will be loaded up and held here.
 * 
 * @author Jeff Johnston
 */

@SuppressWarnings("unchecked")
public class ColumnHandler {
    private TableModel model;
    private List columns = new ArrayList();
    private Column firstColumn;
    private Column lastColumn;

    public ColumnHandler(TableModel model) {
        this.model = model;
    }

    public void addAutoGenerateColumn(Column column) {
        column.setAttribute(TableConstants.IS_AUTO_GENERATE_COLUMN, "true");
        addColumn(column);
    }

    public void addColumn(Column column) {
        column.defaults();
        addColumnAttributes(column);

        if (!isViewAllowed(column)) {
            return;
        }

        if (firstColumn == null) {
            firstColumn = column;
            column.setFirstColumn(true);
        }

        if (lastColumn != null) {
            lastColumn.setLastColumn(false);
        }
        lastColumn = column;
        column.setLastColumn(true);

        columns.add(column);
        model.addPropertyName(column.getProperty());
    }
    
    public void addColumnAttributes(Column column) {
        String interceptor = TableModelUtils.getInterceptPreference(model, column.getInterceptor(), PreferencesConstants.COLUMN_INTERCEPTOR);
        column.setInterceptor(interceptor);
        TableCache.getInstance().getColumnInterceptor(interceptor).addColumnAttributes(model, column);
    }

    public void modifyColumnAttributes(Column column) {
        TableCache.getInstance().getColumnInterceptor(column.getInterceptor()).modifyColumnAttributes(model, column);
    }

    public int columnCount() {
        return columns.size();
    }

    public List getColumns() {
        return columns;
    }

    public Column getColumnByAlias(String alias) {
        for (Iterator iter = columns.iterator(); iter.hasNext();) {
            Column column = (Column) iter.next();
            String columnAlias = column.getAlias();
            if (columnAlias != null && columnAlias.equals(alias)) {
                return column;
            }
        }

        return null;
    }

    
    public boolean hasMetatData() {
        return columnCount() > 0;
    }

    public List getFilterColumns() {
        boolean cleared = model.getLimit().isCleared();

        for (Iterator iter = columns.iterator(); iter.hasNext();) {
            Column column = (Column) iter.next();
            String value = model.getLimit().getFilterSet().getFilterValue(column.getAlias());
            if (cleared || StringUtils.isEmpty(value)) {
                value = "";
            }
            Cell cell = TableModelUtils.getFilterCell(column, value);
            column.setCellDisplay(cell.getHtmlDisplay(model, column));
        }

        return columns;
    }

    public List getHeaderColumns() {
        for (Iterator iter = columns.iterator(); iter.hasNext();) {
            Column column = (Column) iter.next();
            Cell cell = TableModelUtils.getHeaderCell(column, column.getTitle());

            boolean isExported = model.getLimit().isExported();
            
            
            if (!isExported) {
                column.setCellDisplay(cell.getHtmlDisplay(model, column));
            } else {
            	boolean isPrint=model.getExportHandler().getCurrentExport().getView().equals(TableConstants.VIEW_PRINT);
                if (isPrint){
                	column.setCellDisplay(cell.getHtmlDisplay(model, column));
                }else{
                	column.setCellDisplay(cell.getExportDisplay(model, column));
                }
            }
        }

        return columns;
    }

    private boolean isViewAllowed(Column column) {
        String view = model.getTable().getView();

        boolean isExported = model.getLimit().isExported();
        if (isExported) {
            view = model.getExportHandler().getCurrentExport().getView();
        }

        boolean allowView = allowView(column, view);
        boolean denyView = denyView(column, view);

        if (allowView & !denyView) {
            return true;
        }

        return false;
    }

    private boolean allowView(Column column, String view) {
        String viewsAllowed[] = column.getViewsAllowed();
        if (viewsAllowed == null || viewsAllowed.length == 0) {
            return true;
        }

        for (int i = 0; i < viewsAllowed.length; i++) {
            if (view.equals(viewsAllowed[i])) {
                return true;
            }
        }

        return false;
    }

    private boolean denyView(Column column, String view) {
        String viewsDenied[] = column.getViewsDenied();
        if (viewsDenied == null || viewsDenied.length == 0) {
            return false;
        }

        for (int i = 0; i < viewsDenied.length; i++) {
            if (view.equals(viewsDenied[i])) {
                return true;
            }
        }

        return false;
    }
    
    public Column getFirstCalcColumn() {
        for (Iterator iter = columns.iterator(); iter.hasNext();) {
            Column column = (Column) iter.next();
            if (column.isCalculated()) {
                return column;
            }
        }

        return null;
    }    

    public CalcResult[] getCalcResults(Column column) {
        if (!column.isCalculated()) {
            return null;
        }

        return CalcUtils.getCalcResults(model, column);
    }
}