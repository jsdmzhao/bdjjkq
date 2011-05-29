/*
 * Copyright 2006-2007 original author or authors.
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
package com.googlecode.jtiger.modules.ecside.core.bean;

import com.googlecode.jtiger.modules.ecside.core.TableModel;
import com.googlecode.jtiger.modules.ecside.preferences.PreferencesConstants;


/**
 * @author Wei Zijun
 *
 */

/**
 * Pull the complicated values for the TableTag. Because the default values
 * could be coming from the properties or resource bundle this class will
 * abstract that out.
 */
public final class ECSideBeanDefaults {

	public static String setToolbarLocation(TableModel model, String toolbarLocation) {
        if (toolbarLocation == null) {
            return model.getPreferences().getPreference(PreferencesConstants.TABLE_TOOLBAR_LOCATION);
        }

        return toolbarLocation;
    }
	
	public static Boolean setShowRowsDisplayed(TableModel model, Boolean showRowsDisplayed) {
        if (showRowsDisplayed == null) {
            return Boolean.valueOf(model.getPreferences().getPreference(PreferencesConstants.TABLE_SHOW_ROWS_DISPLAYED));
        }

        return showRowsDisplayed;
    }

	public static Boolean setShowGotoPage(TableModel model, Boolean showGotoPage) {
        if (showGotoPage == null) {
            return Boolean.valueOf(model.getPreferences().getPreference(PreferencesConstants.TABLE_SHOW_GOTO_PAGE));
        }

        return showGotoPage;
    }
}
