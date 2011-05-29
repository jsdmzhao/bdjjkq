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
package com.googlecode.jtiger.modules.ecside.view.html;

import org.apache.commons.lang.StringUtils;

import com.googlecode.jtiger.modules.ecside.core.TableConstants;
import com.googlecode.jtiger.modules.ecside.core.TableModel;
import com.googlecode.jtiger.modules.ecside.core.bean.Column;
import com.googlecode.jtiger.modules.ecside.table.limit.Sort;

/**
 * @author Jeff Johnston
 */
public class TableActions {
    private TableModel model;

    public TableActions(TableModel model) {
        this.model = model;
    }
    
    protected TableModel getTableModel() {
        return model;
    }

    public String getOnInvokeAction() {
        String onInvokeAction = model.getTable().getOnInvokeAction();
        if (StringUtils.isNotBlank(onInvokeAction)) {
            return onInvokeAction;
        }
        
        return getSubmitAction();
    }
    
    public String getSubmitAction() {
        StringBuffer result = new StringBuffer();

        String form = BuilderUtils.getForm(model);
        String action = model.getTable().getAction();
        result.append("document.forms.").append(form).append(".setAttribute('action','").append(action).append("');");
        
        String method = model.getTable().getMethod();
        result.append("document.forms.").append(form).append(".setAttribute('method','").append(method).append("');");

        result.append("document.forms.").append(form).append(".submit()");
        
        return result.toString();
    }

    public String getFormParameter(String name, String value) {
        StringBuffer result = new StringBuffer();
        
        String form = BuilderUtils.getForm(model);
        result.append("document.forms.").append(form).append(".");
        result.append(model.getTableHandler().prefixWithTableId()).append(name);
        result.append(".value='").append(value).append("';");
        
        return result.toString(); 
    }
    
    public String getExportTableIdParameter(String value) {
        StringBuffer result = new StringBuffer();
        
        String form = BuilderUtils.getForm(model);
        
        result.append("document.forms.").append(form).append(".");
        result.append(TableConstants.EXPORT_TABLE_ID);
        result.append(".value='").append(value).append("';");
        
        return result.toString();
    }
    
    public String getExportAction(String exportView, String exportFileName) {
        StringBuffer action = new StringBuffer("javascript:");
        
        action.append(getExportTableIdParameter(model.getTable().getTableId())); 
        action.append(getFormParameter(TableConstants.EXPORT_VIEW, exportView)); 
        action.append(getFormParameter(TableConstants.EXPORT_FILE_NAME, exportFileName));
        
        action.append(getSubmitAction());

        return action.toString();
    }

    public String getPageAction(int page) {
        StringBuffer action = new StringBuffer("javascript:");

        action.append(getClearedExportTableIdParameters());

        action.append(getFormParameter(TableConstants.PAGE, "" + page));
        action.append(getOnInvokeAction());
        
        return action.toString();
    }

    public String getFilterAction() {
        StringBuffer action = new StringBuffer("javascript:");

        action.append(getClearedExportTableIdParameters());

        action.append(getFormParameter(TableConstants.FILTER + TableConstants.ACTION, TableConstants.FILTER_ACTION));
        action.append(getFormParameter(TableConstants.PAGE, "1"));
        action.append(getOnInvokeAction());

        return action.toString();
    }

    public String getClearAction() {
        StringBuffer action = new StringBuffer("javascript:");

        action.append(getClearedExportTableIdParameters());

        action.append(getFormParameter(TableConstants.FILTER + TableConstants.ACTION, TableConstants.CLEAR_ACTION));
        action.append(getFormParameter(TableConstants.PAGE, "1"));
        action.append(getOnInvokeAction());

        return action.toString();
    }

    public String getSortAction(Column column, String sortOrder) {
        StringBuffer action = new StringBuffer("javascript:");

        Sort sort = model.getLimit().getSort();
        if (sort.isSorted()) {
            // set the old sort back
            action.append(getFormParameter(TableConstants.SORT + sort.getAlias(), ""));
        }

        action.append(getClearedExportTableIdParameters());

        // set sort on current column
        action.append(getFormParameter(TableConstants.SORT + column.getAlias(), sortOrder));
        action.append(getFormParameter(TableConstants.PAGE, "1"));
        action.append(getOnInvokeAction());

        return action.toString();
    }

    public String getRowsDisplayedAction() {
        StringBuffer action = new StringBuffer("javascript:");

        action.append(getClearedExportTableIdParameters());

        action.append(getRowsDisplayedFormParameter(TableConstants.CURRENT_ROWS_DISPLAYED));
        action.append(getFormParameter(TableConstants.PAGE, "1"));
        action.append(getOnInvokeAction());
        
        return action.toString();
    }
    
    /**
     * Need to clear the export table id parameter.
     * 
     * @return The javascript to clear the table id
     */
    public String getClearedExportTableIdParameters() {
        if (BuilderUtils.showExports(model)) {
            return getExportTableIdParameter("");
        }
        
        return "";
    }
    
    protected String getRowsDisplayedFormParameter(String name) {
        StringBuffer result = new StringBuffer();
        
        String form = BuilderUtils.getForm(model);
        String selectedOption = "this.options[this.selectedIndex].value";
        result.append("document.forms.").append(form).append(".");
        result.append(model.getTableHandler().prefixWithTableId()).append(name);
        result.append(".value=").append(selectedOption).append(";");
        
        return result.toString();
    }
}
