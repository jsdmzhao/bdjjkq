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
package com.googlecode.jtiger.modules.ecside.table.callback;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.googlecode.jtiger.modules.ecside.common.log.LogHandler;
import com.googlecode.jtiger.modules.ecside.core.RetrievalUtils;
import com.googlecode.jtiger.modules.ecside.core.TableConstants;
import com.googlecode.jtiger.modules.ecside.core.TableModel;
import com.googlecode.jtiger.modules.ecside.core.bean.Table;

/**
 * 
 * @author Wei Zijun
 */

@SuppressWarnings("unchecked")
public final class CommonLimitCallback extends ProcessRowsCallback {
	
	private Log logger = LogFactory.getLog(CommonLimitCallback.class);

    public Collection retrieveRows(TableModel model) throws Exception {
        Table table = model.getTable();
        Collection rows = RetrievalUtils.retrieveCollection(model.getContext(), table.getItems(), table.getScope());
        Object totalRows = RetrievalUtils.retrieve(model.getContext(), model.getTableHandler().prefixWithTableId()+TableConstants.TOTAL_ROWS);
        if (totalRows == null) {
            totalRows = (Integer) RetrievalUtils.retrieve(model.getContext(), TableConstants.TOTAL_ROWS);
        }
        if (totalRows instanceof Integer) {
            model.getTableHandler().setTotalRows((Integer)totalRows);
        } else {
        	totalRows=new Integer(rows.size());
        	
            String message = "You don't specify the " + TableConstants.TOTAL_ROWS + " (as an Integer) to use the " + this.getClass().getName() + ".Now the collection.size() as "+TableConstants.TOTAL_ROWS ;
            model.getTableHandler().setTotalRows((Integer)totalRows);
            LogHandler.warnLog(logger, message);
            //throw new Exception(message);
        }

        return rows;
    }

}
