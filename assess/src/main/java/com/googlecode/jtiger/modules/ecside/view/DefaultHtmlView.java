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

import com.googlecode.jtiger.modules.ecside.core.TableConstants;
import com.googlecode.jtiger.modules.ecside.core.TableModel;
import com.googlecode.jtiger.modules.ecside.core.bean.Column;
import com.googlecode.jtiger.modules.ecside.core.bean.Table;
import com.googlecode.jtiger.modules.ecside.table.tool.ExportTool;
import com.googlecode.jtiger.modules.ecside.table.tool.ExtendTool;
import com.googlecode.jtiger.modules.ecside.table.tool.ToolBarBuilder;
import com.googlecode.jtiger.modules.ecside.util.HtmlBuilder;
import com.googlecode.jtiger.modules.ecside.view.html.CalcBuilder;
import com.googlecode.jtiger.modules.ecside.view.html.FormBuilder;
import com.googlecode.jtiger.modules.ecside.view.html.RowBuilder;
import com.googlecode.jtiger.modules.ecside.view.html.TableBuilder;


/**
 * @author Wei Zijun
 *
 */

public class DefaultHtmlView implements View {
	
	public static String HEAD_ZONE_ID ="headZone";
	public static String HEAD_TABLE_ID ="headTable";
	public static String HEAD_THEAD_ID ="headThead";
	
	public static String BODY_ZONE_ID ="bodyZone";
	public static String BODY_TABLE_ID ="bodyTable";
	public static String BODY_THEAD_ID ="bodyThead";
	public static String BODY_TBODY_ID ="bodyTbody";
	public static String BODY_TFOOT_ID ="bodyTfoot";

	public static String FOOT_ZONE_ID ="footZone";
	public static String FOOT_TABLE_ID ="footTable";
	public static String FOOT_THEAD_ID ="footThead";
	public static String FOOT_TBODY_ID ="footTbody";
	public static String FOOT_TFOOT_ID ="footTfoot";
	

	
    protected HtmlBuilder html;
    protected TableBuilder tableBuilder;
    protected RowBuilder rowBuilder;
    protected FormBuilder formBuilder;
    protected CalcBuilder calcBuilder;
    
    protected Table table;
    
//    private String noneData=null;
    
    protected ToolBarBuilder toolBarBuilder=null;
    
    protected String toolbarLocation=null;
    protected String alwaysShowExtend=null;
    
    protected boolean bufferView;
    
    protected boolean showToolBar=false;
    
    protected String height=null;
    
    protected boolean splitTable=false;
    
    protected boolean isClassic=false;
    
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
         
        toolBarBuilder = new ToolBarBuilder(html, model,table.getToolbarContent());

        toolbarLocation=table.getToolbarLocation();
        alwaysShowExtend=table.getAlwaysShowExtend();
        

       height=table.getHeight();
       isClassic=table.isClassic();

       

    }
    
    
    public void beforeBody(TableModel model) {

    	init(model);
    	
        formBuilder.formStart();
        
//        tableBuilder.themeStart();

        
        tableBuilder.title();
        
        
        String extendBarTop=(String)(model.getTable().getAttribute("ExtendBarTop"));
    	
        if (StringUtils.isNotBlank(extendBarTop)){
        	html.div().styleClass("extendBarTop").close();
        	html.append(extendBarTop);
        	html.divEnd();
        }
        
         toolbar(model,TableConstants.TOOLBAR_LOCATION_TOP);
         extendTool(model,TableConstants.TOOLBAR_LOCATION_TOP);


     	String prefixWithTableId=model.getTableHandler().prefixWithTableId();

    	
     	html.newline();
     	
     	
     	if (!isClassic){
     		html.div().styleClass(HEAD_ZONE_ID);
     		html.id(prefixWithTableId+HEAD_ZONE_ID).close();
     	}
     	
//     	if (scrollList){
//     		html.div().styleClass("headerScrollX").id(prefixWithTableId+"headerScrollX").close();
//     	}
    	
     	 if (!isClassic){
     		 tableBuilder.tableStartH();
     	 }else{
     		tableBuilder.tableStart();
     	 }

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
        }else if ( !showHeader && isClassic){
           	if (hasExtendRowTop){
        		html.append(extendRowTop);
        	}
           	tableBuilder.headerHideRow();
        }else if ( !showHeader && !isClassic){
           	tableBuilder.headerHideRow();
        	if (hasExtendRowTop){
        		html.append(extendRowTop);
        	}
        }
        
        
        
        tableBuilder.theadEnd();
    	
        if (!isClassic){
	        html.tableEnd(0);
	        
	        html.divEnd();
	    	
	    	
	        html.newline();

	     	String sWidth=table.getWidth();
	     	if (StringUtils.isBlank(sWidth)){
	     		sWidth="100%";
	     	}
	    	if (sWidth.indexOf("px")==-1 && sWidth.indexOf("%")==-1){
	    		sWidth=sWidth+"px";
	    	}
	    	String sHeight;
	        if (StringUtils.isNotBlank(height)){
	        	sHeight="height:"+height;
	        }else{
	         	sHeight="";
	        }
	    	
	    	html.div().style("overflow:auto;width:"+sWidth+";"+sHeight);
	    	//html.append(" onscroll=\""+ECSideConstants.UTIL_FUNCTION_NAME+".handleScroll('"+table.getTableId()+"')\" ");
	    	html.styleClass(BODY_ZONE_ID).id(prefixWithTableId+BODY_ZONE_ID).close();
        
	    	tableBuilder.tableStart();
    	
        }

    	 tableBuilder.tbodyStart();
//    	 new TableBuilder(html,model).bodyHideRow();
		     	
		    	String extendRowBefore=(String)(model.getTable().getAttribute("ExtendRowBefore"));
		    	
		        if (StringUtils.isNotBlank(extendRowBefore)){
		        	if (!isClassic){ 
		        		tableBuilder.headerHideRow();
		        	}
		        	html.append(extendRowBefore);
		        }else{
		        	int totalRows = model.getLimit().getTotalRows();
		        	if (totalRows<1 && !isClassic){
		        		tableBuilder.bodyHideRow();
		        	}
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

    	ExportTool.buildExportIframe(html, model);
        
        if (!isClassic){ 

        	html.divEnd();

        	html.newline();
//		html.div().styleClass("bodyScrollX").id(prefixWithTableId+"bodyScrollX");
//    	html.append(" onscroll=\""+ECSideConstants.UTIL_FUNCTION_NAME+".scrollListX(this,'"+table.getTableId()+"')\" ");
//		html.close();
//		html.span().id(prefixWithTableId+"bodyScrollXspan").close();
////		html.append("&#160;");
//		html.spanEnd();
//		html.divEnd();
        
        }


		toolbar(model,TableConstants.TOOLBAR_LOCATION_BOTTOM);
		extendTool(model,TableConstants.TOOLBAR_LOCATION_BOTTOM);
		
       // tableBuilder.themeEnd();


        formBuilder.formEnd();
        
        tableBuilder.buildWaitingBar();
        tableBuilder.buildScript();

        
        if (this.bufferView) {
            return html.toString();
        }

        return "";
    }
    
    
    private void extendTool(TableModel model,String pos){

    	
    	if (showToolBar||!pos.equalsIgnoreCase(alwaysShowExtend) 
    			&& !TableConstants.TOOLBAR_LOCATION_BOTH.equalsIgnoreCase(alwaysShowExtend)){
    		return ;
    	}
    	ExtendTool.buildExtendTool(html,model);

    	
    }
    protected void toolbar(TableModel model,String pos) {

    	
    	if (!pos.equalsIgnoreCase(toolbarLocation) 
    			&& !TableConstants.TOOLBAR_LOCATION_BOTH.equalsIgnoreCase(toolbarLocation)){
    		return;
    	}
    	 
        String extendBarBefore=(String)(model.getTable().getAttribute("ExtendBarBefore"));
    	
        if (StringUtils.isNotBlank(extendBarBefore)){
        	html.div().styleClass("extendBarBefore").close();
        	html.append(extendBarBefore);
        	html.divEnd();
        }
    	toolBarBuilder.buildToolBar();
    	
        String extendBarAfter=(String)(model.getTable().getAttribute("ExtendBarAfter"));
    	
        if (StringUtils.isNotBlank(extendBarAfter)){
        	html.div().styleClass("extendBarAfter").close();
        	html.append(extendBarAfter);
        	html.divEnd();
        }
        
        showToolBar=true;
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
//            if (true||splitTable){
//            	int rowCount=model.getRowHandler().increaseRowCount();
//            	if (rowCount%5==0){
//               	html.tbodyEnd(0);
//            	html.tableEnd(0);
//            	tableBuilder.tableStart();
//            	html.tbody(1).close();
//            	tableBuilder.headerHideRow();
//            	}
//     
//            }
        }
        

    }


}
