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
import com.googlecode.jtiger.modules.ecside.util.HtmlBuilder;

/**
 * @author Jeff Johnston
 */
public abstract class TwoColumnRowLayout {
    private HtmlBuilder html; 
    private TableModel model;
    
    public TwoColumnRowLayout(HtmlBuilder html, TableModel model) {
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

        html.tr(1).style("padding: 0px;").close();

        html.td(2).colSpan(String.valueOf(model.getColumnHandler().columnCount())).close();

        html.table(2).border("0").cellPadding("0").cellSpacing("0").width("100%").close();
        html.tr(3).close();

        // layout area left
        columnLeft(html, model);

        // layout area right
        columnRight(html, model);

        html.trEnd(3);
        html.tableEnd(2);
        html.newline();
        html.tabs(2);

        html.tdEnd();
        html.trEnd(1);
        html.tabs(2);
        html.newline();
    }
    
    public String toString() {
        return html.toString();
    }

    protected abstract boolean showLayout(TableModel model);

    protected abstract void columnLeft(HtmlBuilder html, TableModel model);

    protected abstract void columnRight(HtmlBuilder html, TableModel model);
}
