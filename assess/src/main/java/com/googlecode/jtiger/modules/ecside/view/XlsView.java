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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jxl.Cell;
import jxl.Workbook;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.CellFormat;
import jxl.format.Colour;
import jxl.write.Blank;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.NumberFormat;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.tags.TableColumn;
import org.htmlparser.tags.TableRow;
import org.htmlparser.tags.TableTag;
import org.htmlparser.util.NodeList;

import com.googlecode.jtiger.modules.ecside.common.log.LogHandler;
import com.googlecode.jtiger.modules.ecside.core.ECSideContext;
import com.googlecode.jtiger.modules.ecside.core.TableModel;
import com.googlecode.jtiger.modules.ecside.core.bean.Column;
import com.googlecode.jtiger.modules.ecside.core.context.ContextUtils;
import com.googlecode.jtiger.modules.ecside.preferences.PreferencesConstants;
import com.googlecode.jtiger.modules.ecside.table.calc.CalcResult;
import com.googlecode.jtiger.modules.ecside.table.calc.CalcUtils;
import com.googlecode.jtiger.modules.ecside.util.ECSideUtils;
import com.googlecode.jtiger.modules.ecside.util.ExportViewUtils;
import com.googlecode.jtiger.modules.ecside.util.ExtremeUtils;

/**
 * @author Wei Zijun
 * 
 */

/**
 * com.extremecomp.table.view.XlsView.java -
 * 
 * @author paul horn
 */

@SuppressWarnings("unchecked")
public class XlsView implements View {
  private Log logger = LogFactory.getLog(XlsView.class);

  public static final int WIDTH_MULT = 240; // width per char

  public static final int MIN_CHARS = 8; // minimum char width

  public static final short DEFAULT_FONT_HEIGHT = 8;

  public static final double NON_NUMERIC = -.99999;

  public static final String DEFAULT_MONEY_FORMAT = "$###,###,##0.00";

  public static final String DEFAULT_PERCENT_FORMAT = "##0.0%";

  public static final String NBSP = "&nbsp;";

  public static final int colWidth = 15;

  private WritableWorkbook wb;

  private WritableSheet sheet;

  private int rownum;

  private short cellnum;

  private String moneyFormat;

  private String percentFormat;

  private OutputStream outputStream;

  private String encoding;

  public XlsView() {
    encoding = ECSideContext.ENCODING;
  }

  public void beforeBody(TableModel model) {
    logger.debug("XlsView.init()");

    outputStream = ContextUtils.getResponseOutputStream(model.getContext());
    if (outputStream == null) {
      outputStream = new ByteArrayOutputStream();
    }

    moneyFormat = model.getPreferences().getPreference(
        PreferencesConstants.TABLE_EXPORTABLE + "format.money");
    if (StringUtils.isEmpty(moneyFormat)) {
      moneyFormat = DEFAULT_MONEY_FORMAT;
    }
    percentFormat = model.getPreferences().getPreference(
        PreferencesConstants.TABLE_EXPORTABLE + "format.percent");
    if (StringUtils.isEmpty(percentFormat)) {
      percentFormat = DEFAULT_PERCENT_FORMAT;
    }

    // encoding = model.getExportHandler().getCurrentExport().getEncoding();

    try {
      wb = Workbook.createWorkbook(outputStream);
      sheet = wb.createSheet("Export Workbook", 0);
      createHeader(model);
      // int totalCol=model.getColumnHandler().getColumns().size();

      rownum++;

      String extendRowBefore = (String) (model.getTable().getAttribute("ExtendRowBefore"));
      rownum += createRow(sheet, getRows(extendRowBefore, encoding),
          (CellFormat) WritableWorkbook.NORMAL_STYLE, rownum, 0) - 1;

    } catch (Exception e) {
      LogHandler.errorLog(logger, e);
    }

  }

  @SuppressWarnings("serial")
public static TableRow[] getRows(String inputHtml, String encode) throws Exception {
    if (StringUtils.isBlank(inputHtml)) {
      return null;
    }
    inputHtml = inputHtml.trim();
    if (!inputHtml.startsWith("<table>") && !inputHtml.startsWith("<TABLE>")) {
      inputHtml = "<table>" + inputHtml + "</table>";
    }
    Parser parser = Parser.createParser(inputHtml, encode);
    NodeList nodes = parser.extractAllNodesThatMatch(new NodeFilter() {
      public boolean accept(Node node) {
        return node instanceof TagNode;
      }
    });
    TagNode node = (TagNode) nodes.elementAt(0);

    return ((TableTag) node).getRows();
  }

  public static int getColumnNum(TableRow row) {
    int totalCol = 0;

    TableColumn[] columns = row.getColumns();

    for (int cn = 0; cn < columns.length; cn++) {
      String colspan = columns[cn].getAttribute("colspan");
      if (colspan != null && colspan.length() > 0) {
        try {
          totalCol += Integer.parseInt(colspan);
          continue;
        } catch (Exception e) {
        }
      }
      totalCol++;
    }

    return totalCol;

  }

  public static int createRow(WritableSheet sheet, TableRow[] tableRows, CellFormat cellFormat,
      int startRow, int startCol) throws RowsExceededException, WriteException {

    if (tableRows == null || tableRows.length < 1) {
      return 0;
    }
    int totalCol = getColumnNum(tableRows[0]);
    // CellFormat cellFormat=(CellFormat)WritableWorkbook.NORMAL_STYLE;
    int colWidth = 15;

    List mergeCells = new ArrayList();
    for (int rowNo = startRow; rowNo < startRow + tableRows.length; rowNo++) {
      int idx = 0;
      for (int colNo = startCol; colNo < startCol + totalCol; colNo++) {
        Cell cell = sheet.getCell(colNo, rowNo);
        if (cell instanceof Blank) {
          continue;
        }
        TableColumn tdBean = ((TableRow) tableRows[rowNo - startRow]).getColumns()[idx];
        String title = ECSideUtils.specialHTMLToShowTEXT(tdBean.toPlainTextString());

        Label label = new Label(colNo, rowNo, title, cellFormat);
        sheet.addCell(label);
        sheet.setColumnView(colNo, colWidth);
        idx++;
        int ce = Integer.parseInt(ECSideUtils.convertString(tdBean.getAttribute("colspan"), "1")) - 1;
        int re = Integer.parseInt(ECSideUtils.convertString(tdBean.getAttribute("rowspan"), "1")) - 1;

        if (ce >= 1 || re >= 1) {
          ce = ce < 0 ? 0 : ce;
          re = re < 0 ? 0 : re;
          mergeCells.add(new int[] { colNo, rowNo, colNo + ce, rowNo + re });
          if (ce < 1 && re >= 1) {
            for (int srowNo = 1; srowNo <= re; srowNo++) {
              sheet.addCell(new Blank(colNo, rowNo + srowNo));
              sheet.setColumnView(colNo, colWidth);
            }
            continue;
          } else if (re < 1 && ce >= 1) {
            for (int scolNo = 1; scolNo <= ce; scolNo++) {
              sheet.addCell(new Blank(colNo + scolNo, rowNo));
              sheet.setColumnView(colNo + scolNo, colWidth);
            }
            colNo += ce;
            continue;
          } else if (ce >= 1 && re >= 1) {
            for (int scolNo = 1; scolNo <= ce; scolNo++) {
              for (int srowNo = 1; srowNo <= re; srowNo++) {
                sheet.addCell(new Blank(colNo + scolNo, rowNo + srowNo));
                sheet.setColumnView(colNo + scolNo, colWidth);
              }
            }
            colNo += ce;
            continue;
          }
        }
      }

    }

    for (int i = 0; i < mergeCells.size(); i++) {
      int[] mc = (int[]) mergeCells.get(i);
      sheet.mergeCells(mc[0], mc[1], mc[2], mc[3]);
    }

    return tableRows.length;
  }

  private void createHeader(TableModel model) throws RowsExceededException, WriteException {
    rownum = 0;
    cellnum = 0;
    int etr = 0;
    // HSSFRow row = sheet.createRow(rownum);

    WritableCellFormat cellFormat = new WritableCellFormat();
    WritableFont arial10font = new WritableFont(WritableFont.ARIAL,
        WritableFont.DEFAULT_POINT_SIZE, WritableFont.BOLD);
    cellFormat.setBackground(Colour.GRAY_25);
    cellFormat.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.GRAY_50);
    cellFormat.setFont(arial10font);

    boolean showHeader = model.getTable().isShowHeader();

    List columns = model.getColumnHandler().getHeaderColumns();

    String extendRowTop = (String) (model.getTable().getAttribute("ExtendRowTop"));

    try {
      etr = createRow(sheet, getRows(extendRowTop, encoding), cellFormat, rownum, 0);
    } catch (Exception e) {
      LogHandler.warnLog(logger, e);
      etr = 0;
    }
    rownum += etr;

    if (showHeader || etr < 1) {
      for (Iterator iter = columns.iterator(); iter.hasNext();) {
        Column column = (Column) iter.next();
        String title = column.getCellDisplay();

        Label label = new Label(cellnum, rownum, title, cellFormat);

        sheet.addCell(label);

        int valWidth = (title + "").length();
        valWidth = 1;
        sheet.setColumnView(cellnum, valWidth * colWidth);

        cellnum++;
      }
    } else {
      if (rownum > 0) {
        rownum--;
      }
    }

  }

  public void body(TableModel model, Column column) {
    if (column.isFirstColumn()) {
      rownum++;
      cellnum = 0;

    }

    try {

      String value = ExportViewUtils.parseXLS(column.getCellDisplay());

      if (column.isEscapeAutoFormat()) {

        writeToCellAsText(value, null);

      } else {
        writeToCellFormatted(value, null);
      }
      cellnum++;

    } catch (RowsExceededException e) {
      // TODO Auto-generated catch block
      LogHandler.errorLog(logger, e);
    } catch (WriteException e) {
      // TODO Auto-generated catch block
      LogHandler.errorLog(logger, e);
    }
  }

  public Object afterBody(TableModel model) {
    if (model.getLimit().getTotalRows() != 0) {
      try {
        totals(model);
      } catch (RowsExceededException e) {
        // TODO Auto-generated catch block
        LogHandler.errorLog(logger, e);
      } catch (WriteException e) {
        // TODO Auto-generated catch block
        LogHandler.errorLog(logger, e);
      }
    }

    try {
      // int totalCol=model.getColumnHandler().getColumns().size();
      rownum++;
      String extendRowAfter = (String) (model.getTable().getAttribute("ExtendRowAfter"));
      rownum += createRow(sheet, getRows(extendRowAfter, encoding),
          (CellFormat) WritableWorkbook.NORMAL_STYLE, rownum, 0);

      wb.write();
      wb.close();
    } catch (WriteException e) {
      LogHandler.warnLog(logger, e);
    } catch (IOException e) {
      LogHandler.warnLog(logger, e);
    } catch (Exception e) {
      LogHandler.warnLog(logger, e);
    } finally {
      wb = null;
      sheet = null;
      outputStream = null;
    }
    return outputStream;
  }

  private void writeToCellAsText(String value, WritableCellFormat styleModifier)
      throws RowsExceededException, WriteException {
    // format text
    if (value.trim().equals(NBSP)) {
      value = "";
    }
    Label label = new Label(cellnum, rownum, value);
    if (styleModifier != null) {
      label.setCellFormat(styleModifier);
    }
    sheet.addCell(label);

  }

  private void writeToCellFormatted(String value, WritableCellFormat styleModifier)
      throws RowsExceededException, WriteException {
    double numeric = NON_NUMERIC;

    try {
      numeric = Double.parseDouble(value);
    } catch (Exception e) {
      numeric = NON_NUMERIC;
    }

    if (value.startsWith("$") || value.endsWith("%") || value.startsWith("($")) {
      boolean moneyFlag = (value.startsWith("$") || value.startsWith("($"));
      boolean percentFlag = value.endsWith("%");

      value = StringUtils.replace(value, "$", "");
      value = StringUtils.replace(value, "%", "");
      value = StringUtils.replace(value, ",", "");
      value = StringUtils.replace(value, "(", "-");
      value = StringUtils.replace(value, ")", "");

      try {
        numeric = Double.parseDouble(value);
      } catch (Exception e) {
        numeric = NON_NUMERIC;
      }

      if (moneyFlag) {
        // format money
        NumberFormat fivedps = new NumberFormat(moneyFormat);
        WritableCellFormat fivedpsFormat = new WritableCellFormat(fivedps);
        Number number = new Number(cellnum, rownum, numeric, fivedpsFormat);
        if (styleModifier != null) {
          number.setCellFormat(styleModifier);
        }
        sheet.addCell(number);
      } else if (percentFlag) {
        // format percent
        numeric = numeric / 100;

        NumberFormat fivedps = new NumberFormat(percentFormat);
        WritableCellFormat fivedpsFormat = new WritableCellFormat(fivedps);
        Number number = new Number(cellnum, rownum, numeric, fivedpsFormat);
        if (styleModifier != null) {
          number.setCellFormat(styleModifier);
        }
        sheet.addCell(number);
      }
    } else if (Math.abs(numeric - NON_NUMERIC) >= .0000001) {
      // numeric != NON_NUMERIC
      // format numeric
      Number number = new Number(cellnum, rownum, numeric);
      if (styleModifier != null) {
        number.setCellFormat(styleModifier);
      }
      sheet.addCell(number);
    } else {
      // format text
      if (value.trim().equals(NBSP)) {
        value = "";
      }
      Label label = new Label(cellnum, rownum, value);
      if (styleModifier != null) {
        label.setCellFormat(styleModifier);
      }
      sheet.addCell(label);
    }

  }

  // Add to export totals
  public void totals(TableModel model) throws RowsExceededException, WriteException {
    Column firstCalcColumn = model.getColumnHandler().getFirstCalcColumn();

    WritableCellFormat cellFormatTotals = new WritableCellFormat();
    cellFormatTotals.setBackground(Colour.GRAY_25);
    cellFormatTotals.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.GRAY_50);

    if (firstCalcColumn != null) {
      int rows = firstCalcColumn.getCalc().length;
      for (int i = 0; i < rows; i++) {
        rownum++;
        cellnum = 0;
        for (Iterator iter = model.getColumnHandler().getColumns().iterator(); iter.hasNext();) {
          Column column = (Column) iter.next();
          if (column.isFirstColumn()) {
            String calcTitle = CalcUtils.getFirstCalcColumnTitleByPosition(model, i);

            if (column.isEscapeAutoFormat()) {

              writeToCellAsText(calcTitle, cellFormatTotals);

            } else {
              writeToCellFormatted(calcTitle, cellFormatTotals);
            }

            cellnum++;
            continue;
          }

          if (column.isCalculated()) {

            CalcResult calcResult = CalcUtils.getCalcResultsByPosition(model, column, i);
            java.lang.Number value = calcResult.getValue();

            if (value != null) {
              // if (column.isEscapeAutoFormat()) {
              // writeToCellAsText( value.toString(), cellFormatTotals);
              // } else {
              // writeToCellFormatted( ExtremeUtils.formatNumber(column.getFormat(), value,
              // model.getLocale()), cellFormatTotals);
              // }
              if (StringUtils.isNotBlank(column.getFormat())) {
                writeToCellFormatted(ExtremeUtils.formatNumber(column.getFormat(), value, model
                    .getLocale()), cellFormatTotals);
              } else {
                writeToCellAsText(value.toString(), cellFormatTotals);
              }
            } else {
              Label label = new Label(cellnum, rownum, "n/a");
              sheet.addCell(label);
            }
            cellnum++;
          } else {
            writeToCellFormatted("", cellFormatTotals);
            cellnum++;
          }
        }
      }
    }

  }

}
