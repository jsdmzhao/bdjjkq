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
package com.googlecode.jtiger.modules.ecside.view;

import com.googlecode.jtiger.modules.ecside.core.TableModel;
import com.googlecode.jtiger.modules.ecside.core.bean.Column;
import com.googlecode.jtiger.modules.ecside.util.HtmlBuilder;
import com.googlecode.jtiger.modules.ecside.view.html.CalcBuilder;
import com.googlecode.jtiger.modules.ecside.view.html.FormBuilder;
import com.googlecode.jtiger.modules.ecside.view.html.RowBuilder;
import com.googlecode.jtiger.modules.ecside.view.html.TableBuilder;

/**
 * @author jeff johnston
 */
public abstract class AbstractHtmlView implements View {
    private HtmlBuilder html;
    private TableModel model;
    private FormBuilder formBuilder;
    private boolean bufferView;

    // meant to be plugable
    private TableBuilder tableBuilder;
    private RowBuilder rowBuilder;
    private CalcBuilder calcBuilder;

    protected HtmlBuilder getHtmlBuilder() {
        return html;
    }

    protected TableModel getTableModel() {
        return model;
    }

    protected TableBuilder getTableBuilder() {
        return tableBuilder;
    }
    
    protected void setTableBuilder(TableBuilder tableBuilder) {
        this.tableBuilder = tableBuilder;
    }

    public RowBuilder getRowBuilder() {
        return rowBuilder;
    }

    protected void setRowBuilder(RowBuilder rowBuilder) {
        this.rowBuilder = rowBuilder;
    }

    public CalcBuilder getCalcBuilder() {
        return calcBuilder;
    }

    protected void setCalcBuilder(CalcBuilder calcBuilder) {
        this.calcBuilder = calcBuilder;
    }
    
    public final void beforeBody(TableModel model) {
        this.model = model;
        
        bufferView = model.getTable().isBufferView();
        if (bufferView) {
            html = new HtmlBuilder();
        } else {
            html = new HtmlBuilder(model.getContext().getWriter());
        }

        formBuilder = new FormBuilder(html, model);

        init(html, model);
        
        formBuilder.formStart();

        tableBuilder.themeStart();
        
        beforeBodyInternal(model);
    }

    public void body(TableModel model, Column column) {
        if (column.isFirstColumn()) {
            rowBuilder.rowStart();
        }
        
        html.append(column.getCellDisplay());

        if (column.isLastColumn()) {
            rowBuilder.rowEnd();
        }
    }

    public final Object afterBody(TableModel model) {
        afterBodyInternal(model);
        
        tableBuilder.themeEnd();

        formBuilder.formEnd();
        
        if (bufferView) {
            return html.toString();
        }

        return "";
    }
    
    protected void init(HtmlBuilder html, TableModel model) {
        setTableBuilder(new TableBuilder(html, model));
        setRowBuilder(new RowBuilder(html, model));
        setCalcBuilder(new CalcBuilder(model));
    }
    
    protected abstract void beforeBodyInternal(TableModel model);
    
    protected abstract void afterBodyInternal(TableModel model);
    
}
