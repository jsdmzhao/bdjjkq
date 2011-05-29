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
import com.googlecode.jtiger.modules.ecside.core.TableModelUtils;
import com.googlecode.jtiger.modules.ecside.preferences.PreferencesConstants;

/**
 * @author Wei Zijun
 *
 */
/**
 * Pull all the default values for the ExportTag. Because the default values
 * could be coming from the properties or resource bundle this class will
 * abstract that out.
 */
final class ExportDefaults {
    static String getEncoding(TableModel model, String encoding) {
        if (StringUtils.isBlank(encoding)) {
            return model.getPreferences().getPreference(PreferencesConstants.EXPORT_ENCODING);
        }

        return encoding;
    }

    static String getText(TableModel model, String text) {
        if (TableModelUtils.isResourceBundleProperty(text)) {
            String resourceValue = model.getMessages().getMessage(text);
            if (resourceValue != null) {
                return resourceValue;
            }
        }

        return text;
    }

    static String getTooltip(TableModel model, String tooltip) {
        if (TableModelUtils.isResourceBundleProperty(tooltip)) {
            String resourceValue = model.getMessages().getMessage(tooltip);
            if (resourceValue != null) {
                return resourceValue;
            }
        }

        return tooltip;
    }
    
    static String getviewResolver(TableModel model, String viewResolver) {
        String result = null;

        if (StringUtils.isNotBlank(viewResolver)) {
            result = model.getPreferences().getPreference(PreferencesConstants.EXPORT_VIEW_RESOLVER + viewResolver);
            if (StringUtils.isBlank(result)) {
                result = viewResolver;
            }
        }

        return result;
    }
}
