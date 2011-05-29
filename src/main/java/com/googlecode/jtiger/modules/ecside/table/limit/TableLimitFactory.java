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

import org.apache.commons.lang.StringUtils;

import com.googlecode.jtiger.modules.ecside.core.Preferences;
import com.googlecode.jtiger.modules.ecside.core.TableConstants;
import com.googlecode.jtiger.modules.ecside.core.TableModelUtils;
import com.googlecode.jtiger.modules.ecside.core.context.WebContext;
import com.googlecode.jtiger.modules.ecside.preferences.PreferencesConstants;
import com.googlecode.jtiger.modules.ecside.preferences.TableProperties;

/**
 * The LimitFactory that is used outside the context of a TableModel. What could
 * easily be retrieved from the TableModel will have to be created now.
 * 
 * @author Jeff Johnston
 */
public final class TableLimitFactory extends AbstractLimitFactory {
    /**
     * Will default the tableId to 'ec'. Used if you have one table per JSP. Not
     * recommended as a best practice, but works fine if you understand that it
     * defaults to 'ec' and is only used if have one table per JSP.
     */
    public TableLimitFactory(WebContext context) {
        this(context, TableConstants.EXTREME_COMPONENTS);
    }

    /**
     * Explicitly set the tableId. Recommended as a best practice.
     */
    public TableLimitFactory(WebContext context, String tableId) {
        this(context, tableId, TableConstants.STATE_DEFAULT, null);
    }

    /**
     * To use the State feature you must specify a tableId. The table
     * state is keyed by the tableId. The tableId should be unique across all
     * JSP pages for the State to work properly.
     * 
     * @param state Defined as one of following: TableConstants.STATE_PERSIST,
     *            TableConstants.STATE_NOTIFY_TO_DEFAULT,
     *            TableConstants.STATE_NOTIFY_TO_PERSIST
     * @param stateAttr The attribute to use with the State
     *            TableConstants.STATE_NOTIFY_TO_DEFAULT,
     *            TableConstants.STATE_NOTIFY_TO_PERSIST. If defaulted in
     *            preferences, or not defined then can be null.
     */
    public TableLimitFactory(WebContext context, String tableId, String state, String stateAttr) {
        this.tableId = tableId;

        String prefixWithTableId = tableId + "_";
        this.prefixWithTableId = prefixWithTableId;

        Preferences preferences = new TableProperties();
        preferences.init(null, TableModelUtils.getPreferencesLocation(context));

        state = preferences.getPreference(PreferencesConstants.TABLE_STATE + state);

        if (StringUtils.isBlank(stateAttr)) {
            stateAttr = preferences.getPreference(PreferencesConstants.TABLE_STATE_ATTR);
        }

        this.context = context;

        this.registry = new LimitRegistry(context, tableId, prefixWithTableId, state, stateAttr);

        this.isExported = getExported();
    }

    /**
     * Not neccessary to know whether or not paginating as that concept is
     * retrieved via the current rows displayed parameter.
     */
    protected boolean showPagination() {
        return true;
    }
}
