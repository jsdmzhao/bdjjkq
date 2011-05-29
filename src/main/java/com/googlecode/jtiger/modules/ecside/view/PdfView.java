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
package com.googlecode.jtiger.modules.ecside.view;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.googlecode.jtiger.modules.ecside.core.TableModel;
import com.googlecode.jtiger.modules.ecside.core.bean.Column;
import com.googlecode.jtiger.modules.ecside.core.bean.Export;
import com.googlecode.jtiger.modules.ecside.table.calc.CalcResult;
import com.googlecode.jtiger.modules.ecside.table.calc.CalcUtils;
import com.googlecode.jtiger.modules.ecside.util.ExportViewUtils;
import com.googlecode.jtiger.modules.ecside.util.ExtremeUtils;


/**
 * @author Wei Zijun
 *
 */

@SuppressWarnings("unchecked")
public class PdfView implements View {
    public final static String FONT = "exportPdf.font";
    public final static String HEADER_BACKGROUND_COLOR = "headerBackgroundColor";
    public final static String HEADER_TITLE = "headerTitle";
    public final static String HEADER_COLOR = "headerColor";

    public final static int  COLUMN_T_WIDTH=10;
    
    private StringBuffer xlsfo = new StringBuffer();
    private String font;


    public PdfView() {
    }

    public void beforeBody(TableModel model) {
        this.font = model.getPreferences().getPreference(FONT);

        xlsfo.append(startRoot());
        xlsfo.append(regionBefore(model));
        xlsfo.append(regionAfter());
        xlsfo.append(columnDefinitions(model));
        xlsfo.append(header(model));
        xlsfo.append(" <fo:table-body> ");
    }

    public void body(TableModel model, Column column) {
        if (column.isFirstColumn()) {
            xlsfo.append(" <fo:table-row> ");
        }

        String value = ExportViewUtils.parsePDF(column.getCellDisplay());

        xlsfo.append(" <fo:table-cell language=\"zh\" wrap-option=\"wrap\"  border=\"solid silver .5px\" display-align=\"center\" padding=\"3pt\"> ");
        xlsfo.append(" <fo:block language=\"zh\" wrap-option=\"wrap\" " + getFont() + ">" + value + "</fo:block> ");
        xlsfo.append(" </fo:table-cell> ");

        if (column.isLastColumn()) {
            xlsfo.append(" </fo:table-row> ");
        }
    }

    public Object afterBody(TableModel model) {
        if (model.getLimit().getTotalRows() != 0) {
            xlsfo.append(totals(model));
        }
        xlsfo.append(" </fo:table-body> ");
        xlsfo.append(endRoot());
        return xlsfo.toString();
    }

    public String startRoot() {
        StringBuffer sb = new StringBuffer();

        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");

        sb.append("<fo:root xmlns:fo=\"http://www.w3.org/1999/XSL/Format\">");

        sb.append(" <fo:layout-master-set> ");
        sb.append(" <fo:simple-page-master master-name=\"simple\" ");
        sb.append(" page-height=\"8.5in\" ");
        sb.append(" page-width=\"11in\" ");
        sb.append(" margin-top=\".5in\" ");
        sb.append(" margin-bottom=\".25in\" ");
        sb.append(" margin-left=\".5in\" ");
        sb.append(" margin-right=\".5in\"> ");
        sb.append(" <fo:region-body margin-top=\".5in\" margin-bottom=\".25in\"/> ");
        sb.append(" <fo:region-before extent=\".5in\"/> ");
        sb.append(" <fo:region-after extent=\".25in\"/> ");
        sb.append(" </fo:simple-page-master> ");
        sb.append(" </fo:layout-master-set> ");

        sb.append(" <fo:page-sequence master-reference=\"simple\" initial-page-number=\"1\" language=\"zh\"> ");

        return sb.toString();
    }

    public String regionBefore(TableModel model) {
        StringBuffer sb = new StringBuffer();

        Export export = model.getExportHandler().getCurrentExport();

        String headerColor = export.getAttributeAsString(HEADER_COLOR);
        
        if (StringUtils.isBlank(headerColor)){
        	headerColor="#000000";
        }

        sb.append(" <fo:static-content flow-name=\"xsl-region-before\"> ");

        String title = export.getAttributeAsString(HEADER_TITLE);
        
        if (StringUtils.isBlank(title)){
        	title=model.getTable().getTitle();
        }

        sb.append(" <fo:block space-after.optimum=\"15pt\" color=\"" + headerColor + "\" font-size=\"17pt\" font-family=\"" + getHeadFont() + "'Times'\">" + title + "</fo:block> ");

        sb.append(" </fo:static-content> ");

        return sb.toString();
    }

    public String regionAfter() {
        StringBuffer sb = new StringBuffer();

        sb.append(" <fo:static-content flow-name=\"xsl-region-after\" display-align=\"after\"> ");

        sb.append(" <fo:block text-align=\"end\">Page <fo:page-number/></fo:block> ");

        sb.append(" </fo:static-content> ");

        return sb.toString();
    }

    public String columnDefinitions(TableModel model) {
        StringBuffer sb = new StringBuffer();

        sb.append(" <fo:flow flow-name=\"xsl-region-body\"> ");

        sb.append(" <fo:block" + getFont() + ">");

        sb.append(" <fo:table table-layout=\"fixed\" font-size=\"10pt\"> ");

        double columnCount = model.getColumnHandler().columnCount();

        double colwidth = COLUMN_T_WIDTH / columnCount;

        for (int i = 1; i <= columnCount; i++) {
            sb.append(" <fo:table-column column-number=\"" + i + "\" column-width=\"" + colwidth + "in\"/> ");
        }

        return sb.toString();
    }

    public String header(TableModel model) {
        StringBuffer sb = new StringBuffer();

        Export export = model.getExportHandler().getCurrentExport();
        String headerColor = export.getAttributeAsString(HEADER_COLOR);
        String headerBackgroundColor = export.getAttributeAsString(HEADER_BACKGROUND_COLOR);

        if (StringUtils.isBlank(headerColor)){
        	headerColor="#000000";
        }
        
        if (StringUtils.isBlank(headerBackgroundColor)){
        	headerBackgroundColor="#cccccc";
        }
        sb.append(" <fo:table-header background-color=\"" + headerBackgroundColor + "\" color=\"" + headerColor + "\"> ");

        sb.append(" <fo:table-row> ");

        List columns = model.getColumnHandler().getHeaderColumns();
        for (Iterator iter = columns.iterator(); iter.hasNext();) {
            Column column = (Column) iter.next();
            String title = column.getCellDisplay();
            sb.append(" <fo:table-cell border=\"solid silver .5px\" text-align=\"center\" display-align=\"center\" padding=\"3pt\"> ");
            sb.append(" <fo:block" + getFont() + ">" + title + "</fo:block> ");
            sb.append(" </fo:table-cell> ");
        }

        sb.append(" </fo:table-row> ");

        sb.append(" </fo:table-header> ");

        return sb.toString();
    }

    public String endRoot() {
        StringBuffer sb = new StringBuffer();

        sb.append(" </fo:table> ");

        sb.append(" </fo:block> ");

        sb.append(" </fo:flow> ");

        sb.append(" </fo:page-sequence> ");

        sb.append(" </fo:root> ");

        return sb.toString();
    }

    protected String getFont() {
        return font == null ? "" : " font-family=\"" + font + "\"";
    }

    protected String getHeadFont() {
        return font == null ? "" : font + ",";
    }

    /**
     * TWEST - New Method that answers a StringBuffer containing the totals
     * information. If no totals exist on the model answer an empty buffer.
     * 
     * The totals row will be given the same style as the header row.
     * 
     * @param model
     * @return StringBuffer containing the complete fo statement for totals
     */
    public StringBuffer totals(TableModel model) {

        StringBuffer sb = new StringBuffer();
        Export export = model.getExportHandler().getCurrentExport();
        String headerColor = export.getAttributeAsString(HEADER_COLOR);
        String headerBackgroundColor = export.getAttributeAsString(HEADER_BACKGROUND_COLOR);

        if (StringUtils.isBlank(headerColor)){
        	headerColor="#000000";
        }
        
        if (StringUtils.isBlank(headerBackgroundColor)){
        	headerBackgroundColor="#cccccc";
        }
        
        Column firstCalcColumn = model.getColumnHandler().getFirstCalcColumn();

        if (firstCalcColumn != null) {
            int rows = firstCalcColumn.getCalc().length;
            for (int i = 0; i < rows; i++) {
                sb.append("<fo:table-row>");
                for (Iterator iter = model.getColumnHandler().getColumns().iterator(); iter.hasNext();) {
                    Column column = (Column) iter.next();
                    if (column.isFirstColumn()) {
                        String calcTitle = CalcUtils.getFirstCalcColumnTitleByPosition(model, i);
                        sb.append(" <fo:table-cell border=\"solid silver .5px\" text-align=\"center\" display-align=\"center\" padding=\"3pt\" background-color=\"");
                        sb.append(headerBackgroundColor + "\" color=\"" + headerColor + "\">");
                        sb.append(" <fo:block " + getFont() + ">" + calcTitle);
                        sb.append(" </fo:block></fo:table-cell> ");
                        continue;
                    }
                    if (column.isCalculated()) {
                        sb.append(" <fo:table-cell border=\"solid silver .5px\" text-align=\"center\" display-align=\"center\" padding=\"3pt\" background-color=\"");
                        sb.append(headerBackgroundColor + "\" color=\"" + headerColor + "\"> ");
                        sb.append(" <fo:block " + getFont() + ">");
                        CalcResult calcResult = CalcUtils.getCalcResultsByPosition(model, column, i);
                        Number value = calcResult.getValue();
                        if (value != null) {
                            sb.append(ExtremeUtils.formatNumber(column.getFormat(), value, model.getLocale()));
                        } else {
                            sb.append("n/a");
                        }
                        sb.append("</fo:block> ");
                    } else {
                        sb.append(" <fo:table-cell border=\"solid silver .5px\" text-align=\"center\" display-align=\"center\" padding=\"3pt\" background-color=\"");
                        sb.append(headerBackgroundColor + "\" color=\"" + headerColor + "\"> ");
                        sb.append(" <fo:block " + getFont() + ">");
                        sb.append(" ");
                        sb.append("</fo:block> ");
                    }
                    sb.append(" </fo:table-cell> ");
                }
                sb.append("</fo:table-row>");

            }
        }
        return sb;
    }

}
