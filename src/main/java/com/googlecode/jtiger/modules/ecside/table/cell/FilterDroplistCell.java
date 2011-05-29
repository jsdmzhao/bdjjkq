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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.comparators.NullComparator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.googlecode.jtiger.modules.ecside.core.TableConstants;
import com.googlecode.jtiger.modules.ecside.core.TableModel;
import com.googlecode.jtiger.modules.ecside.core.bean.Column;
import com.googlecode.jtiger.modules.ecside.util.ExtremeUtils;
import com.googlecode.jtiger.modules.ecside.util.HtmlBuilder;
import com.googlecode.jtiger.modules.ecside.view.html.TableActions;

/**
 * A filter cell that is a droplist
 * 
 * @author Jeff Johnston
 */

@SuppressWarnings("unchecked")
public class FilterDroplistCell implements Cell {
    private Log logger = LogFactory.getLog(FilterDroplistCell.class);

    public String getExportDisplay(TableModel model, Column column) {
        return null;
    }

    public String getHtmlDisplay(TableModel model, Column column) {
        HtmlBuilder html = new HtmlBuilder();

        if (!column.isFilterable()) {
            html.append("");
        } else {
            Collection filterOptions = column.getFilterOptions();
            if (filterOptions == null || filterOptions.isEmpty()) {
                filterOptions = getFilterDropList(model, column);
            }
            html.append(dropListHtml(model, column, filterOptions));
        }

        return html.toString();
    }

    protected Collection getFilterDropList(TableModel model, Column column) {
        List droplist = new ArrayList();
        
        Set options = new HashSet();
        
        Collection beans = model.getCollectionOfBeans();
        for (Iterator iter = beans.iterator(); iter.hasNext();) {
            Object bean = iter.next();
            try {
                Object obj = getFilterOption(column, bean); 
                if ((obj != null) && !options.contains(obj)) {
                    droplist.add(new Option(obj));
                    options.add(obj);
                }
            } catch (Exception e) {
                logger.debug("Problems getting the droplist.", e);
            }
        }

        BeanComparator comparator = new BeanComparator("label", new NullComparator());
        Collections.sort(droplist, comparator);

        return droplist;
    }
    
    protected Object getFilterOption(Column column, Object bean) 
            throws Exception {
        return PropertyUtils.getProperty(bean, column.getProperty());
    }

    protected String dropListHtml(TableModel model, Column column, Collection droplist) {
        HtmlBuilder html = new HtmlBuilder();

        html.td(2).close();

        html.newline();
        html.tabs(2);
        html.select().name(model.getTableHandler().prefixWithTableId() + TableConstants.FILTER + column.getAlias());

        StringBuffer onkeypress = new StringBuffer();
        onkeypress.append(new TableActions(model).getFilterAction());
        html.onchange(onkeypress.toString());

        html.close();

        html.newline();
        html.tabs(2);
        html.option().value("").close();
        html.optionEnd();

        Locale locale = model.getLocale();

        for (Iterator iter = droplist.iterator(); iter.hasNext();) {
            FilterOption filterOption = (FilterOption) iter.next();
            String value = String.valueOf(filterOption.getValue());
            String label = String.valueOf(filterOption.getLabel());

            if (column.isDate()) {
                value = ExtremeUtils.formatDate(column.getParse(), column.getFormat(), filterOption.getValue(), locale);
            }

            html.newline();
            html.tabs(2);
            html.option().value(value);

            if (value.equals(column.getValueAsString())) {
                html.selected();
            }

            html.close();
            html.append(label);
            html.optionEnd();
        }

        html.newline();
        html.tabs(2);
        html.selectEnd();

        html.tdEnd();

        return html.toString();
    }

    protected static class Option implements FilterOption {
        private final Object label;
        private final Object value;

        public Option(Object obj) {
            this.label = obj;
            this.value = obj;
        }

        public Option(Object label, Object value) {
            this.label = label;
            this.value = value;
        }
        
        public Object getLabel() {
            return label;
        }

        public Object getValue() {
            return value;
        }
    }
}
