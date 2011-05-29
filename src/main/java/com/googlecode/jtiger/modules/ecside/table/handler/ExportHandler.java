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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.googlecode.jtiger.modules.ecside.core.TableCache;
import com.googlecode.jtiger.modules.ecside.core.TableConstants;
import com.googlecode.jtiger.modules.ecside.core.TableModel;
import com.googlecode.jtiger.modules.ecside.core.TableModelUtils;
import com.googlecode.jtiger.modules.ecside.core.bean.Export;
import com.googlecode.jtiger.modules.ecside.preferences.PreferencesConstants;

/**
 * @author Jeff Johnston
 */

@SuppressWarnings("unchecked")
public class ExportHandler {
    private Log logger = LogFactory.getLog(ExportHandler.class);
    
    private TableModel model;
    private List exports = new ArrayList();

    public ExportHandler(TableModel model) {
        this.model = model;
    }

    public void addExport(Export export) {
        exports.add(export);
        addExportAttributes(export);
        export.defaults();
    }
    
    public void addExportAttributes(Export export) {
        String interceptor = TableModelUtils.getInterceptPreference(model, export.getInterceptor(), PreferencesConstants.EXPORT_INTERCEPTOR);
        export.setInterceptor(interceptor);
        TableCache.getInstance().getExportInterceptor(interceptor).addExportAttributes(model, export);
    }

    public Export getExport(String view) {
        for (Iterator iter = exports.iterator(); iter.hasNext();) {
            Export export = (Export) iter.next();

            if (export.getView().equals(view)) {
                return export;
            }
        }

        return null;
    }

    public Export getCurrentExport() {
        String prefixWithTableId = model.getTableHandler().prefixWithTableId();
        String exportView = model.getRegistry().getParameter(prefixWithTableId + TableConstants.EXPORT_VIEW);
        
        Export export = getExport(exportView);
        if (export == null) {
            String msg = "There is no export defined. This commonly happens if you do not " +
                    "declare the export (Export or ExportTag) before the row and columns.";
            logger.error(msg);
            throw new IllegalStateException(msg);
        }
        
        return export;
    }

    public List getExports() {
        return exports;
    }
    
    /**
     * @deprecated This functionality is available on the Limit. 
     */
    public boolean isExported() {
        return model.getExportHandler().isExported();
    }

    /**
     * @deprecated Specific logic to building html. Moved to BuilderUtils.showExports.
     */
//    public boolean showExports() {
//        if (!model.getTable().isShowExports()) {
//            return false;
//        }
//
//        return getExports().size() > 0;
//    }
}
