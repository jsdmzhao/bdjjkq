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
package com.googlecode.jtiger.modules.ecside.table.limit;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.googlecode.jtiger.modules.ecside.core.ECSideContext;
import com.googlecode.jtiger.modules.ecside.core.TableConstants;

/**
 * @author Jeff Johnston
 */

public final class TableLimit implements Limit {
    protected LimitFactory limitFactory;
    protected FilterSet filterSet;
    protected Sort sort;
    protected boolean exported;
    protected int rowStart;
    protected int rowEnd;
    protected int currentRowsDisplayed;
    protected int page;
    protected int totalRows;

    public TableLimit(LimitFactory limitFactory) {
        this.limitFactory = limitFactory;
        this.filterSet = limitFactory.getFilterSet();
        this.sort = limitFactory.getSort();
        this.page = limitFactory.getPage();
        this.exported = limitFactory.isExported();
    }

    public FilterSet getFilterSet() {
        return filterSet;
    }

    public int getRowEnd() {
        return rowEnd;
    }

    public int getRowStart() {
        return rowStart;
    }

    public Sort getSort() {
        return sort;
    }

    public int getPage() {
        return page;
    }

    public int getCurrentRowsDisplayed() {
        return currentRowsDisplayed;
    }

    public int getTotalRows() {
        return totalRows;
    }

    public boolean isFiltered() {
        return filterSet.isFiltered();
    }

    public boolean isCleared() {
        return filterSet.isCleared();
    }

    public boolean isSorted() {
        return sort.isSorted();
    }

    public boolean isExported() {
        return exported;
    }

    /**
     * When using the Limit you must call this method to set the following row
     * attributes: rowStart, rowEnd, currentRowsDisplayed and page
     * 
     * @param totalRows The total results for the table.
     * @param rowsDisplayed The default rows displayed on the table. This
     *            value is either defined on the table, or defaulted in the
     *            Preferences.
     */
    public void setRowAttributes(int totalRows, int rowsDisplayed) {
    	
    	
		if (rowsDisplayed == 0){
			rowsDisplayed=ECSideContext.DEFAULT_PAGE_SIZE;
		}else if (rowsDisplayed <0){
			rowsDisplayed = totalRows;
		}
		    	
        int currentRowsDisplayed = limitFactory.getCurrentRowsDisplayed(totalRows, rowsDisplayed);

        int page = getValidPage(this.page, totalRows, currentRowsDisplayed);

        int rowStart = (page - 1) * currentRowsDisplayed;

        int rowEnd = rowStart + currentRowsDisplayed;

        if (rowEnd > totalRows) {
            rowEnd = totalRows;
        }

        this.page = page;
        this.currentRowsDisplayed = currentRowsDisplayed;
        this.totalRows = totalRows;
        this.rowStart = rowStart;
        this.rowEnd = rowEnd;
        
        setTotalRows();
    }
    
	public void setTotalRows(){
		String tableId = limitFactory.getTableId();
		tableId = tableId == null ? "" : tableId + "_";
		limitFactory.getWebContext().setRequestAttribute(tableId + TableConstants.TOTAL_ROWS, 
				new Integer(totalRows));
	}
	

    /**
     * The page returned that is not greater than the pages that can display.
     */
    private int getValidPage(int page, int totalRows, int currentRowsDisplayed) {
        if (!isValidPage(page, totalRows, currentRowsDisplayed)) {
            return getValidPage(--page, totalRows, currentRowsDisplayed);
        }

        return page;
    }

    /**
     * Testing that the page returned is not greater than the pages that are
     * able to be displayed. The problem arises if using the state feature and
     * rows are deleted.
     */
    private boolean isValidPage(int page, int totalRows, int currentRowsDisplayed) {
        if (page == 1) {
            return true;
        }
        
        int rowStart = (page - 1) * currentRowsDisplayed;
        int rowEnd = rowStart + currentRowsDisplayed;
        if (rowEnd > totalRows) {
            rowEnd = totalRows;
        }
        return rowEnd > rowStart;
    }

    public String toString() {
        ToStringBuilder builder = new ToStringBuilder(this);
        builder.append("rowStart", rowStart);
        builder.append("rowEnd", rowEnd);
        builder.append("currentRowsDisplayed", currentRowsDisplayed);
        builder.append("page", page);
        builder.append("totalRows", totalRows);
        builder.append("exported", exported);
        builder.append("sort", sort);
        builder.append("filterSet", filterSet);
        return builder.toString();
    }
}
