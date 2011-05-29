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

import com.googlecode.jtiger.modules.ecside.core.TableModel;
import com.googlecode.jtiger.modules.ecside.view.html.BuilderUtils;

/**
 * @author Jeff Johnston
 */
public final class ModelLimitFactory extends AbstractLimitFactory {
    TableModel model;
    
    public ModelLimitFactory(TableModel model) {
        this.model = model;
        this.tableId = model.getTable().getTableId();
        this.prefixWithTableId = model.getTableHandler().prefixWithTableId();
        this.context = model.getContext();
        this.registry = model.getRegistry();
        this.isExported = getExported();
    }
    
    protected boolean showPagination() {
        return BuilderUtils.showPagination(model);
    }
}
