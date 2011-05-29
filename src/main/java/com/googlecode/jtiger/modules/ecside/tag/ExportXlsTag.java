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
package com.googlecode.jtiger.modules.ecside.tag;

import org.apache.commons.lang.StringUtils;

import com.googlecode.jtiger.modules.ecside.core.TableConstants;
import com.googlecode.jtiger.modules.ecside.core.TableModel;
import com.googlecode.jtiger.modules.ecside.core.bean.Export;
import com.googlecode.jtiger.modules.ecside.view.html.BuilderConstants;

/**
 * @author Wei Zijun
 *
 */

/**
 * @jsp.tag name="exportXls" display-name="ExportXlsTag" body-content="JSP"
 *          description="Export data for a xls view."
 * 
 */

public class ExportXlsTag extends ExportTag {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void addExportAttributes(TableModel model, Export export) {
        if (StringUtils.isBlank(export.getView())) {
            export.setView(TableConstants.VIEW_XLS);
        }

        if (StringUtils.isBlank(export.getViewResolver())){
            export.setViewResolver(TableConstants.VIEW_XLS);
        }

        if (StringUtils.isBlank(export.getImageName())) {
            export.setImageName(TableConstants.VIEW_XLS);
        }
        
        if (StringUtils.isBlank(export.getText())) {
            export.setText(BuilderConstants.TOOLBAR_XLS_TEXT);
        }
    }
}
