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

import com.googlecode.jtiger.modules.ecside.core.TableModel;
import com.googlecode.jtiger.modules.ecside.core.bean.Table;
import com.googlecode.jtiger.modules.ecside.util.HtmlBuilder;

/**
 * @author Jeff Johnston
 */
public abstract class TwoColumnTableLayout {
    private HtmlBuilder html; 
    private TableModel model;

    public TwoColumnTableLayout(HtmlBuilder html, TableModel model) {
        this.html = html;
        this.model = model;
    }
    
    protected HtmlBuilder getHtmlBuilder() {
        return html;
    }

    protected TableModel getTableModel() {
        return model;
    }
    
    public void layout() {
        if (!showLayout(model)) {
            return;
        }

        html.table(0).border("0").cellPadding("0").cellSpacing("0");
        Table table = model.getTable();
        html.width(table.getWidth()).close();

        html.tr(1).close();

        // layout area left
        columnLeft(html, model);

        // layout area right
        columnRight(html, model);

        html.trEnd(1);

        html.tableEnd(0);
        html.newline();
    }
    
    public String toString() {
        return html.toString();
    }

    protected abstract boolean showLayout(TableModel model);

    protected abstract void columnLeft(HtmlBuilder html, TableModel model);

    protected abstract void columnRight(HtmlBuilder html, TableModel model);
}
