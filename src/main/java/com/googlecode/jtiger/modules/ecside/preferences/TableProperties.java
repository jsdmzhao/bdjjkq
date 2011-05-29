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
package com.googlecode.jtiger.modules.ecside.preferences;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.googlecode.jtiger.modules.ecside.common.log.LogHandler;
import com.googlecode.jtiger.modules.ecside.core.Preferences;
import com.googlecode.jtiger.modules.ecside.core.TableModel;
import com.googlecode.jtiger.modules.ecside.core.context.WebContext;


/**
 * @author Wei Zijun
 *
 */

public class TableProperties implements Preferences {
    private Log logger = LogFactory.getLog(TableProperties.class);

    
    private Properties properties = new Properties();
    
    public void init(WebContext context, String preferencesLocation) {
        try {
        	InputStream resourceAsStream = getInputStream(PreferencesConstants.ECSIDE_PROPERTIES, context);
        	properties.load(resourceAsStream);
            if (StringUtils.isNotBlank(preferencesLocation)) {
            	resourceAsStream = getInputStream(preferencesLocation, context);
                if (resourceAsStream != null) {
                    properties.load(resourceAsStream);
                }
            }
        } catch (Exception e) {
            	LogHandler.errorLog(logger, "Could not load the ECSide preferences.", e);
          }
    }

    /**
     * Get the property.
     */
    public String getPreference(String name) {
        return (String) properties.get(name);
    }
    
    public static InputStream getInputStream(String preferencesLocation,WebContext context)
            throws IOException {
    	if (preferencesLocation.startsWith("WEB-INF")) {
    		preferencesLocation="/"+preferencesLocation;
    	}
        if (preferencesLocation.startsWith("/WEB-INF")) {
            String path = context.getRealPath("/WEB-INF");
            String name = StringUtils.substringAfter(preferencesLocation, "/WEB-INF/");
            return new FileInputStream(path + "/" + name);
        }

        return TableProperties.class.getResourceAsStream(preferencesLocation);
    }

	
    public static String getDefaultPreference(TableModel model, String preferenceName , String nowValue) {
        if (StringUtils.isBlank(nowValue)) {
            return model.getPreferences().getPreference(preferenceName);
        }
        return nowValue;
    }
    
    




}
