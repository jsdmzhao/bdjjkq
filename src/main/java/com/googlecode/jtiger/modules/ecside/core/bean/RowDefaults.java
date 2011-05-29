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

import org.apache.commons.lang.StringUtils;

import com.googlecode.jtiger.modules.ecside.core.TableModel;
import com.googlecode.jtiger.modules.ecside.preferences.PreferencesConstants;


/**
 * @author Wei Zijun
 *
 */

/**
 * Pull some values for the RowTag. Because the default values could be coming
 * from the properties or resource bundle this class will abstract that out.
 */
final class RowDefaults {

    private RowDefaults() {
    }
    
    static String getHighlightClass(TableModel model, String highlightClass) {
        if (StringUtils.isEmpty(highlightClass)) {
            return model.getPreferences().getPreference(PreferencesConstants.ROW_HIGHLIGHT_CLASS);
        }

        return highlightClass;
    }

    static Boolean isHighlightRow(TableModel model, Boolean highlightRow) {
        if (highlightRow == null) {
            return Boolean.valueOf(model.getPreferences().getPreference(PreferencesConstants.ROW_HIGHLIGHT_ROW));
        }

        return highlightRow;
    }
    
    static String getSelectlightClass(TableModel model, String selectlightClass) {
        if (StringUtils.isEmpty(selectlightClass)) {
            return model.getPreferences().getPreference(PreferencesConstants.ROW_SELECTLIGHT_CLASS);
        }

        return selectlightClass;
    }

    static Boolean isSelectlightRow(TableModel model, Boolean selectlightRow) {
        if (selectlightRow == null) {
            return Boolean.valueOf(model.getPreferences().getPreference(PreferencesConstants.ROW_SELECTLIGHT_ROW));
        }

        return selectlightRow;
    }
}
