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
import com.googlecode.jtiger.modules.ecside.view.PdfView;
import com.googlecode.jtiger.modules.ecside.view.html.BuilderConstants;


/**
 * @author Wei Zijun
 *
 */

/**
 * @jsp.tag name="exportPdf" display-name="ExportPdfTag" body-content="JSP"
 *          description="Export data for a pdf view."
 * 
 */

public class ExportPdfTag extends ExportTag {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String headerBackgroundColor;
    private String headerTitle;
    private String headerColor;

    /**
     * @jsp.attribute description="The background color on the header column."
     *                required="false" rtexprvalue="true"
     */
    public void setHeaderBackgroundColor(String headerBackgroundColor) {
        this.headerBackgroundColor = headerBackgroundColor;
    }

    /**
     * @jsp.attribute description="The font color for the header column."
     *                required="false" rtexprvalue="true"
     */
    public void setHeaderColor(String headerColor) {
        this.headerColor = headerColor;
    }

    /**
     * @jsp.attribute description="The title displayed at the top of the page."
     *                required="false" rtexprvalue="true"
     */
    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
    }

    public void addExportAttributes(TableModel model, Export export) {
        if (StringUtils.isBlank(export.getView())) {
            export.setView(TableConstants.VIEW_PDF);
        }

        if (StringUtils.isBlank(export.getViewResolver())){
            export.setViewResolver(TableConstants.VIEW_PDF);
        }

        if (StringUtils.isBlank(export.getImageName())) {
            export.setImageName(TableConstants.VIEW_PDF);
        }
        
        if (StringUtils.isBlank(export.getText())) {
            export.setText(BuilderConstants.TOOLBAR_PDF_TEXT);
        }

        export.setAttribute(PdfView.HEADER_BACKGROUND_COLOR, TagUtils.evaluateExpressionAsString("headerBackgroundColor", headerBackgroundColor, this, pageContext));
        export.setAttribute(PdfView.HEADER_COLOR, TagUtils.evaluateExpressionAsString("headerColor", headerColor, this, pageContext));
        export.setAttribute(PdfView.HEADER_TITLE, TagUtils.evaluateExpressionAsString("headerTitle", headerTitle, this, pageContext));
    }

    public void release() {
        headerBackgroundColor = null;
        headerTitle = null;
        headerColor = null;
        super.release();
    }
}
