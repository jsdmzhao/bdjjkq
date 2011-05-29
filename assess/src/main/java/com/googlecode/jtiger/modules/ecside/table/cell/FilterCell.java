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
package com.googlecode.jtiger.modules.ecside.table.cell;

import org.apache.commons.lang.StringUtils;

import com.googlecode.jtiger.modules.ecside.core.TableConstants;
import com.googlecode.jtiger.modules.ecside.core.TableModel;
import com.googlecode.jtiger.modules.ecside.core.bean.Column;
import com.googlecode.jtiger.modules.ecside.util.HtmlBuilder;
import com.googlecode.jtiger.modules.ecside.view.html.TableActions;

/**
 * A filter cell.
 * 
 * @author Jeff Johnston
 */
public class FilterCell implements Cell {
    public String getExportDisplay(TableModel model, Column column) {
        return null;
    }

    public String getHtmlDisplay(TableModel model, Column column) {
        HtmlBuilder html = new HtmlBuilder();

        html.td(2);

        if (StringUtils.isNotEmpty(column.getFilterClass())) {
            html.styleClass(column.getFilterClass());
        }

        if (StringUtils.isNotEmpty(column.getFilterStyle())) {
            html.style(column.getFilterStyle());
        }

        if (StringUtils.isNotEmpty(column.getWidth())) {
            html.width(column.getWidth());
        }

        html.close();

        if (!column.isFilterable()) {
            html.append("");
        } else {
            html.append(input(model, column));
        }

        html.tdEnd();

        return html.toString();
    }

    /**
     * If the filter is specified the default is to use a <input type=text> tag.
     */
    private String input(TableModel model, Column column) {
        HtmlBuilder html = new HtmlBuilder();

        html.input("text");
        html.name(model.getTableHandler().prefixWithTableId() + TableConstants.FILTER + column.getAlias());

        String value = column.getValueAsString();
        if (StringUtils.isNotBlank(value)) {
            html.value(value);
        }

        StringBuffer onkeypress = new StringBuffer();
        onkeypress.append("if (event.keyCode == 13) {");
        onkeypress.append(new TableActions(model).getFilterAction());
        onkeypress.append("}");
        html.onkeypress(onkeypress.toString());

        html.xclose();

        return html.toString();
    }
}
