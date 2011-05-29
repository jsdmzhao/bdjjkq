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

import org.apache.commons.lang.StringUtils;

import com.googlecode.jtiger.modules.ecside.core.TableConstants;
import com.googlecode.jtiger.modules.ecside.core.TableModel;
import com.googlecode.jtiger.modules.ecside.core.TableModelUtils;
import com.googlecode.jtiger.modules.ecside.core.bean.Column;
import com.googlecode.jtiger.modules.ecside.preferences.PreferencesConstants;
import com.googlecode.jtiger.modules.ecside.table.cell.Cell;
import com.googlecode.jtiger.modules.ecside.util.ECSideUtils;
import com.googlecode.jtiger.modules.ecside.view.View;

/**
 * @author Jeff Johnston
 */

@SuppressWarnings("unchecked")
public class ViewHandler {
    private TableModel model;
    private View view;

    public ViewHandler(TableModel model) {
        this.model = model;
    }

    public View getView() {
        return view;
    }

    public void setView() throws Exception {
        boolean isExported = model.getLimit().isExported();

        String currentView = null;
        if (isExported) {
            currentView = model.getExportHandler().getCurrentExport().getView();
            String preference = model.getPreferences().getPreference(PreferencesConstants.EXPORT_VIEW + currentView);
            if (StringUtils.isNotBlank(preference)) {
                currentView = preference;
            }
        } else {
            currentView = model.getTable().getView();
            String preference = model.getPreferences().getPreference(PreferencesConstants.TABLE_VIEW + currentView);
            if (StringUtils.isNotBlank(preference)) {
                currentView = preference;
            }
        }

        Class classDefinition = Class.forName(currentView);
        this.view = (View) classDefinition.newInstance();
        getView().beforeBody(model);
    }
    
    public void addColumnValueToView(Column column) {
        Cell cell = TableModelUtils.getCell(column);

        boolean isExported = model.getLimit().isExported();
        if (!isExported) {
            column.setCellDisplay(cell.getHtmlDisplay(model, column));
        } else {
        	boolean isPrint=model.getExportHandler().getCurrentExport().getView().equals(TableConstants.VIEW_PRINT);
            if (isPrint){
            	column.setCellDisplay(cell.getHtmlDisplay(model, column));
            }else{
             	column.setCellDisplay(ECSideUtils.HTMLToTEXT(cell.getHtmlDisplay(model, column)).trim());
             	//column.setCellDisplay(cell.getExportDisplay(model, column));
            }
        }

        getView().body(model, column);
    }
    

}
