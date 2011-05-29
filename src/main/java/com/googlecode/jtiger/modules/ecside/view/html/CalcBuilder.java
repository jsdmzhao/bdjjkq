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
package com.googlecode.jtiger.modules.ecside.view.html;

import java.util.Iterator;

import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.googlecode.jtiger.modules.ecside.core.TableModel;
import com.googlecode.jtiger.modules.ecside.core.bean.Column;
import com.googlecode.jtiger.modules.ecside.core.bean.Table;
import com.googlecode.jtiger.modules.ecside.preferences.PreferencesConstants;
import com.googlecode.jtiger.modules.ecside.table.calc.CalcResult;
import com.googlecode.jtiger.modules.ecside.table.calc.CalcUtils;
import com.googlecode.jtiger.modules.ecside.util.ExtremeUtils;
import com.googlecode.jtiger.modules.ecside.util.HtmlBuilder;


/**
 * @author Wei Zijun
 *
 */

@SuppressWarnings("unchecked")
public class CalcBuilder {
	
	private Log logger = LogFactory.getLog(CalcBuilder.class);

    private HtmlBuilder html;
    private TableModel model;
    private Table table;
    
    private boolean isClassic;

    public CalcBuilder(TableModel model) {
//        this(new HtmlBuilder(), model);
        this.model = model;
        this.html=new HtmlBuilder();
        this.table = model.getTable();
        isClassic=table.isClassic();
    }
    
//    public CalcBuilder(HtmlBuilder html, TableModel model) {
//        this.html = html;
//        this.model = model;
//    }

    public void singleRowCalcResults() {
        
        
        int spanNum=0;
        int columnIndex=-1;
        
        
        	html.tbodyEnd(1);
            html.tfoot(1).id(model.getTableHandler().prefixWithTableId() + BuilderConstants.TABLE+"_foot").close();

        	new TableBuilder(html,model).bodyHideRow();
       
        html.tr(1).styleClass(BuilderConstants.CALC_ROW_CSS).close();
        for (Iterator iter = model.getColumnHandler().getColumns().iterator(); iter.hasNext();) {
            Column column = (Column) iter.next();
            
            columnIndex++;
            
            if (column.isFirstColumn()) {
            	spanNum=column.getCalcSpan();

            	spanNum=spanNum<1?1:spanNum;
                
                String calcTitle[] = CalcUtils.getFirstCalcColumnTitles(model);
                if (calcTitle != null && calcTitle.length > 0) {
                    html.td(2).styleClass(BuilderConstants.CALC_TITLE_CSS);
                    if (spanNum>1){
                    	html.append(" colspan=\""+spanNum+"\" ");
                    }
                    html.close();
                    for (int i = 0; i < calcTitle.length; i++) {
                        String title = calcTitle[i];
                        html.append(title);
                        if (calcTitle.length > 0 && i + 1 != calcTitle.length) {
                            html.append(" / ");
                        }
                    }
                    html.tdEnd();
                }

                continue;
            }
            if (columnIndex<spanNum) continue;
            
            if (column.isCalculated()) {
                html.td(2).styleClass(BuilderConstants.CALC_RESULT_CSS).close();
                CalcResult calcResults[] = CalcUtils.getCalcResults(model, column);
                for (int i = 0; i < calcResults.length; i++) {
                    CalcResult calcResult = calcResults[i];
                    Number value = calcResult.getValue();
                    if (value == null) {
                        html.append(calcResult.getName());
                    } else {
                        html.append(ExtremeUtils.formatNumber(column.getFormat(), value, model.getLocale()));
                    }

                    if (calcResults.length > 0 && i + 1 != calcResults.length) {
                        html.append(" / ");
                    }
                }
            } else {
                html.td(2).close();
                html.nbsp();
            }

            html.tdEnd();
        }
        html.trEnd(1);
    }

    public void multiRowCalcResults() {
        Column firstCalcColumn = model.getColumnHandler().getFirstCalcColumn();
        int rows = firstCalcColumn.getCalc().length;
        //if (rows>0){
        	html.tbodyEnd(1);
            html.tfoot(1).id(model.getTableHandler().prefixWithTableId() + BuilderConstants.TABLE+"_foot").close();
        	if (!isClassic){
            	new TableBuilder(html,model).bodyHideRow();
            }
       // }
        for (int i = 0; i < rows; i++) {
            html.tr(1).styleClass(BuilderConstants.CALC_ROW_CSS).close();

            int spanNum=0;
            int columnIndex=-1;
                
            for (Iterator iter = model.getColumnHandler().getColumns().iterator(); iter.hasNext();) {
                Column column = (Column) iter.next();
                
                columnIndex++;
                
                if (column.isFirstColumn()) {
                	
                	spanNum=column.getCalcSpan();

                	spanNum=spanNum<1?1:spanNum;
                    
                    String calcTitle = CalcUtils.getFirstCalcColumnTitleByPosition(model, i);
                    html.td(2).styleClass(BuilderConstants.CALC_TITLE_CSS);
                    if (spanNum>1){
                    	html.append(" colspan=\""+spanNum+"\" ");
                    }
                    html.close();
                    html.append(calcTitle);
                    html.tdEnd();

                    continue;
                }

                if (columnIndex<spanNum) continue;
                
                if (column.isCalculated()) {
                    html.td(2).styleClass(BuilderConstants.CALC_RESULT_CSS).close();
                    CalcResult calcResult = CalcUtils.getCalcResultsByPosition(model, column, i);
                    Number value = calcResult.getValue();
                    if (value == null) {
                        html.append(calcResult.getName());
                    } else {
                        html.append(ExtremeUtils.formatNumber(column.getFormat(), value, model.getLocale()));
                    }
                } else {
                    html.td(2).close();
                    html.nbsp();
                }

                html.tdEnd();
            }

            html.trEnd(1);
        }
    }
    
    public boolean defaultCalcLayout() {
    	boolean hasCalc=false;
        Column calcColumn = model.getColumnHandler().getFirstCalcColumn();
        if (calcColumn == null) {
            return hasCalc;
        }

        String layout = model.getPreferences().getPreference(PreferencesConstants.DEFAULT_CALC_LAYOUT);
        
        try {
        	MethodUtils.invokeExactMethod(this, layout, null);
        	hasCalc=true;;
        } catch (Exception e) {
            logger.error("There is no method with the layout [" + layout + "].", e);
        }
        return hasCalc;
    }

    public HtmlBuilder getHtmlBuilder() {
        return html;
    }

    protected TableModel getTableModel() {
        return model;
    }
    
    public String toString() {
        return html.toString();
    }
}
