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
package com.googlecode.jtiger.modules.ecside.view;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.googlecode.jtiger.modules.ecside.core.TableModel;
import com.googlecode.jtiger.modules.ecside.core.bean.Column;
import com.googlecode.jtiger.modules.ecside.core.bean.Export;
import com.googlecode.jtiger.modules.ecside.core.context.ContextUtils;
import com.googlecode.jtiger.modules.ecside.table.calc.CalcResult;
import com.googlecode.jtiger.modules.ecside.table.calc.CalcUtils;
import com.googlecode.jtiger.modules.ecside.util.ExportViewUtils;
import com.googlecode.jtiger.modules.ecside.util.ExtremeUtils;

/**
 * For producing a delimited datafile view of the table. Default delimiter is
 * comma.
 * 
 * @author Wei Zijun
 */

@SuppressWarnings("unchecked")
public class CsvView implements View {
    public final static String DELIMITER = "delimiter";
    final static String DEFAULT_DELIMITER = ",";
//    private StringBuffer plainData = new StringBuffer();
    private OutputStream outputStream ;
    
    private OutputStream outputStreamOut;
    
    private StringBuffer rowBuffer =null;
    
    private PrintWriter out =null;
    
    private String delimiter;

    
    public void beforeBody(TableModel model) {
    	
		outputStream=ContextUtils.getResponseOutputStream(model.getContext());
		
		outputStreamOut=null;
		if (outputStream==null){
			outputStream=new ByteArrayOutputStream();
			outputStreamOut=outputStream;
		}
		out=new PrintWriter(outputStream);
        Export export = model.getExportHandler().getCurrentExport();
        delimiter = export.getAttributeAsString(DELIMITER);
        
    	List columns = model.getColumnHandler().getHeaderColumns();
        if (StringUtils.isBlank(delimiter)) {
            delimiter = DEFAULT_DELIMITER;
        }
        boolean isFirstColumn=true;
        
        rowBuffer = new StringBuffer();
        for (Iterator iter = columns.iterator(); iter.hasNext();) {
            Column column = (Column) iter.next();
            
            String value = ExportViewUtils.parseCSV(column.getCellDisplay());
            if (!isFirstColumn){
            	rowBuffer.append(delimiter);
            }
            rowBuffer.append(value);
            isFirstColumn=false;            
        }
        if (columns.size()>0){
        	rowBuffer.append(ExportViewUtils.BR);
            writeToOutputStream(rowBuffer.toString());
        }
        

    }

    
    public void body(TableModel model, Column column) {
 
        String value = ExportViewUtils.parseCSV(column.getCellDisplay());
        rowBuffer.append(value);
        if (column.isLastColumn()) {
        	rowBuffer.append(ExportViewUtils.BR);
        	writeToOutputStream(rowBuffer.toString());
        }else{
        	rowBuffer.append(delimiter);
        }
   	 	
    }

    public Object afterBody(TableModel model) {
    	totals(model);
    	out.flush();
    	out.close();
        return outputStreamOut;
    }
    
    public void totals(TableModel model) {
    	
    	rowBuffer = new StringBuffer();
    	
        Column firstCalcColumn = model.getColumnHandler().getFirstCalcColumn();

        if (firstCalcColumn != null) {
            int rows = firstCalcColumn.getCalc().length;
            for (int i = 0; i < rows; i++) {

                for (Iterator iter = model.getColumnHandler().getColumns().iterator(); iter.hasNext();) {

                    Column column = (Column) iter.next();
                    if (column.isFirstColumn()) {
                        String calcTitle = CalcUtils.getFirstCalcColumnTitleByPosition(model, i);
                        rowBuffer.append(ExportViewUtils.parseCSV(calcTitle) );
                        continue;
                    }
                    rowBuffer.append(delimiter);
                    if (column.isCalculated()) {
                        CalcResult calcResult = CalcUtils.getCalcResultsByPosition(model, column, i);
                        java.lang.Number value = calcResult.getValue();
                        if (value != null){
                        	if (StringUtils.isNotBlank(column.getFormat())){
                        		rowBuffer.append(ExportViewUtils.parseCSV(ExtremeUtils.formatNumber(column.getFormat(), value, model.getLocale())));
                        	}else{
                        		rowBuffer.append(ExportViewUtils.parseCSV(value.toString()) );
                        	}
                        } else {
                        	rowBuffer.append(ExportViewUtils.parseCSV(""));
                        }
                    } else {
                    	rowBuffer.append(ExportViewUtils.parseCSV(""));
                    }
                }
                
                rowBuffer.append(ExportViewUtils.BR);
                writeToOutputStream(rowBuffer.toString());
            }
        }

    }


    public void writeToOutputStream(String rowContent){
    		out.print(rowContent);
			rowBuffer = new StringBuffer();
    }

}
