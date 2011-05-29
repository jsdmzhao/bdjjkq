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
package com.googlecode.jtiger.modules.ecside.filter;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.googlecode.jtiger.modules.ecside.core.TableConstants;
import com.googlecode.jtiger.modules.ecside.core.context.WebContext;

/**
 * @author Jeff Johnston
 */
public final class ExportFilterUtils {
    private static Log logger = LogFactory.getLog(ExportFilterUtils.class);

    private ExportFilterUtils() {
    }

    public static boolean isExported(WebContext context) {
        return StringUtils.isNotBlank(getTableId(context));
    }

    public static String getExportFileName(WebContext context) {
        String tableId = getTableId(context);

        if (StringUtils.isNotBlank(tableId)) {
            String exportFileNameStr = tableId + "_" + TableConstants.EXPORT_FILE_NAME;
            String exportFileName = context.getParameter(exportFileNameStr);

            if (logger.isDebugEnabled()) {
                logger.debug("eXtremeTable export file name [" + exportFileNameStr + "] is [" + exportFileName + "]");
            }

            return exportFileName;
        }

        return null;
    }

    /**
     * There can only be one table instance (tableId) per form. If the instance
     * variable exists that means there is an export being done.
     * 
     * @param context
     * @return
     */
    public static String getTableId(WebContext context) {
        return context.getParameter(TableConstants.EXPORT_TABLE_ID);
    }
    /*
    public static Object getBean(HttpServletRequest request,String beanName){
    	Object bean=null;
    	ApplicationContext appContext=WebApplicationContextUtils.getRequiredWebApplicationContext(request.getSession(true).getServletContext());
    	bean=appContext.getBean(beanName);
    	if (bean==null){
    		LogHandler.warnLog(logger,ECSideFilter.class," Can't find DataAccess Bean named "+beanName);
    	}
    	return bean;
    }*/
}
