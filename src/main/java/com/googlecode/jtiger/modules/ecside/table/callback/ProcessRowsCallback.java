/*
 * Copyright 2004 original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.googlecode.jtiger.modules.ecside.table.callback;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.comparators.NullComparator;
import org.apache.commons.collections.comparators.ReverseComparator;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.googlecode.jtiger.modules.ecside.core.RetrievalUtils;
import com.googlecode.jtiger.modules.ecside.core.TableConstants;
import com.googlecode.jtiger.modules.ecside.core.TableModel;
import com.googlecode.jtiger.modules.ecside.core.bean.Table;
import com.googlecode.jtiger.modules.ecside.table.limit.Sort;

/**
 * The default implementation of the callbacks that handle the retrieving,
 * filtering, and sorting of the collection that gets passed to the
 * eXtremeTable.
 *
 * @author Jeff Johnston
 */

@SuppressWarnings("unchecked")
public class ProcessRowsCallback implements RetrieveRowsCallback, FilterRowsCallback, SortRowsCallback {
    private Log logger = LogFactory.getLog(ProcessRowsCallback.class);

    public Collection retrieveRows(TableModel model) throws Exception {
        Table table = model.getTable();
        return RetrievalUtils.retrieveCollection(model.getContext(), table.getItems(), table.getScope());
    }

    public Collection filterRows(TableModel model, Collection rows) throws Exception {
        boolean filtered = model.getLimit().isFiltered();
        boolean cleared = model.getLimit().isCleared();

        if (!filtered || cleared) {
            return rows;
        }

        if (filtered) {
            Collection collection = new ArrayList();
            FilterPredicate filterPredicate = new FilterPredicate(model);
            CollectionUtils.select(rows, filterPredicate, collection);

            return collection;
        }

        return rows;
    }

    public Collection sortRows(TableModel model, Collection rows) throws Exception {
        boolean sorted = model.getLimit().isSorted();

        if (!sorted) {
            return rows;
        }

        Sort sort = model.getLimit().getSort();
        String property = sort.getProperty();
        String sortOrder = sort.getSortOrder();
        
        if (StringUtils.contains(property, ".")) {
            try {
                if (sortOrder.equals(TableConstants.SORT_ASC)) {
                    Collections.sort((List) rows, new NullSafeBeanComparator(property, new NullComparator()));
                } else if (sortOrder.equals(TableConstants.SORT_DESC)) {
                    NullSafeBeanComparator reversedNaturalOrderBeanComparator = new NullSafeBeanComparator(property,
                            new ReverseComparator(new NullComparator()));
                    Collections.sort((List) rows, reversedNaturalOrderBeanComparator);
                }
            } catch (NoClassDefFoundError e) {
                String msg = "The column property [" + property + "] is nested and requires BeanUtils 1.7 or greater for proper sorting.";
                logger.error(msg);
                throw new NoClassDefFoundError(msg); //just rethrow so it is not hidden
            }
        } else {
            if (sortOrder.equals(TableConstants.SORT_ASC)) {
                BeanComparator comparator = new BeanComparator(property, new NullComparator());
                Collections.sort((List) rows, comparator);
            } else if (sortOrder.equals(TableConstants.SORT_DESC)) {
                BeanComparator reversedNaturalOrderBeanComparator = new BeanComparator(property, new ReverseComparator(new NullComparator()));
                Collections.sort((List) rows, reversedNaturalOrderBeanComparator);
            }
        }

        return rows;
    }
}
