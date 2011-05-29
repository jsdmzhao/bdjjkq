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

import java.util.Map;

import com.googlecode.jtiger.modules.ecside.table.state.State;

/**
 * @author Jeff Johnston
 */

@SuppressWarnings("unchecked")
public final class TableRegistry extends AbstractRegistry {
    public TableRegistry(TableModel model) {
        this.context = model.getContext();
        this.tableId = model.getTable().getTableId();
        this.prefixWithTableId = model.getTableHandler().prefixWithTableId();
        this.state = model.getTable().getState();
        this.stateAttr = model.getTable().getStateAttr();
        this.autoIncludeParameters = model.getTable().isAutoIncludeParameters();
        setParameterMap();
    }
    
    /**
     * Save the state of the parameters.
     */
    protected void handleStateInternal(State state, Map tableParameterMap) {
        state.saveParameters(context, tableId, tableParameterMap);
    }
}
