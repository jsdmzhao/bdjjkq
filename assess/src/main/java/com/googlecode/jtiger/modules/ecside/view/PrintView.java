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

import org.apache.commons.lang.StringUtils;

import com.googlecode.jtiger.modules.ecside.core.TableModel;
import com.googlecode.jtiger.modules.ecside.core.bean.Column;
import com.googlecode.jtiger.modules.ecside.core.bean.Table;
import com.googlecode.jtiger.modules.ecside.util.HtmlBuilder;
import com.googlecode.jtiger.modules.ecside.view.html.CalcBuilder;
import com.googlecode.jtiger.modules.ecside.view.html.FormBuilder;
import com.googlecode.jtiger.modules.ecside.view.html.RowBuilder;
import com.googlecode.jtiger.modules.ecside.view.html.TableBuilder;

/**
 * @author Wei Zijun
 *
 */

public class PrintView implements View {

    protected Table table;
    private HtmlBuilder html;
    private TableBuilder tableBuilder;
    private RowBuilder rowBuilder;
    private FormBuilder formBuilder;
    private CalcBuilder calcBuilder;
    

    
    boolean bufferView;
    
    protected HtmlBuilder getHtmlBuilder() {
        return html;
    }

    protected CalcBuilder getCalcBuilder() {
        return calcBuilder;
    }

    protected FormBuilder getFormBuilder() {
        return formBuilder;
    }

    protected RowBuilder getRowBuilder() {
        return rowBuilder;
    }

    protected TableBuilder getTableBuilder() {
        return tableBuilder;
    }
    
   
    
    ////////////////////////////////////////////////////
    

    
    public void themeStart(TableModel model) {
        html.newline();
        String theme = model.getTable().getTheme()+"_pring";
        html.div().styleClass(theme);
        html.close();
    }

    public void tableTitle(TableModel model){
    	
    }
    
    public void tableStart(TableModel model){
    	
    }

    public void theadStart(TableModel model){
    	
    }
    
    public void theadEnd(TableModel model){
    	
    } 
    public void tbodyStart(TableModel model){
    	
    }
    
    public void tbodyEnd(TableModel model){
    	
    } 
    public void tableEnd(TableModel model){
    	
    }
    
    public void themeEnd(TableModel model) {
        html.newline();
        html.divEnd();
    }
    public void formEnd(TableModel model){
    	
    }
    
    
    ////////////////////////////////////////////////////
    
    public void init(TableModel model){
    	table=model.getTable();
        bufferView = table.isBufferView();
        if (bufferView) {
            html = new HtmlBuilder();
        } else {
            html = new HtmlBuilder(model.getContext().getWriter());
        }

        tableBuilder = new TableBuilder(html, model);
        rowBuilder = new RowBuilder(html, model);
        calcBuilder = new CalcBuilder(model);
        
        formBuilder = new FormBuilder(html, model);
       

    }
    
    public void beforeBody(TableModel model) {

    	init(model);
    	
        formBuilder.formStart();
        
//        tableBuilder.themeStart();

        
        tableBuilder.title();
        
    	
     	html.newline();
     	
     	
//     	if (scrollList){
//     		html.div().styleClass("headerScrollX").id(prefixWithTableId+"headerScrollX").close();
//     	}
    	
        tableBuilder.tableStart();

        tableBuilder.theadStart();
    
                

    	String extendRowTop=(String)(model.getTable().getAttribute("ExtendRowTop"));
        
        boolean showHeader=model.getTable().isShowHeader();
        boolean hasExtendRowTop=StringUtils.isNotBlank(extendRowTop);

        if (showHeader){
        	if (hasExtendRowTop){
            	tableBuilder.headerHideRow();
        		html.append(extendRowTop);
        	}
        	tableBuilder.headerRow();
        }else if ( !showHeader){
           	if (hasExtendRowTop){
        		html.append(extendRowTop);
        	}
           	tableBuilder.headerHideRow();
        }        
        
        
        tableBuilder.theadEnd();
    	
    	 tableBuilder.tbodyStart();
//    	 new TableBuilder(html,model).bodyHideRow();
		  	String extendRowBefore=(String)(model.getTable().getAttribute("ExtendRowBefore"));
		    
		    	
		        if (StringUtils.isNotBlank(extendRowBefore)){

		        	html.append(extendRowBefore);
		        }
        
    }

	
    public Object afterBody(TableModel model) {
    	
//    	String prefixWithTableId=model.getTableHandler().prefixWithTableId();
    	boolean hasCalc=false;
    	if (new Integer(model.getLimit().getTotalRows()).intValue()>0){
    		hasCalc=calcBuilder.defaultCalcLayout();
    	}
        
    	html.append(calcBuilder.getHtmlBuilder());
        
    	String extendRowAfter=(String)(model.getTable().getAttribute("ExtendRowAfter"));

        if (StringUtils.isNotBlank(extendRowAfter)){
        	html.append(extendRowAfter);
        }
        if (hasCalc){
        	html.tfootEnd(1);
        }else{
        	tableBuilder.tbodyEnd();
        }
        tableBuilder.tableEnd();

  
       // tableBuilder.themeEnd();


        formBuilder.formEnd();
        
        tableBuilder.buildScript();

        
        if (this.bufferView) {
            return html.toString();
        }

        return "";
    }
    


    public void body(TableModel model, Column column) {
        if (column.isFirstColumn()) {
            rowBuilder.rowStart();
            html.newline();
        }

        html.append(column.getCellDisplay());
    	html.newline();

        if (column.isLastColumn()) {
            rowBuilder.rowEnd();
        }
    }


}
