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
package com.googlecode.jtiger.modules.ecside.table.tool;

import org.apache.commons.lang.StringUtils;

import com.googlecode.jtiger.modules.ecside.core.ECSideConstants;
import com.googlecode.jtiger.modules.ecside.core.TableConstants;
import com.googlecode.jtiger.modules.ecside.core.TableModel;
import com.googlecode.jtiger.modules.ecside.preferences.PreferencesConstants;
import com.googlecode.jtiger.modules.ecside.util.HtmlBuilder;

/**
 * @author Wei Zijun
 *
 */

public class PageSizeTool extends BaseTool {
	
	public PageSizeTool(){
		super();
	}
	
	
	public PageSizeTool(HtmlBuilder html,TableModel model) {
		super(html,model);
	}
	
    public void buildTool() {
    	
    	getHtmlBuilder().td(1).styleClass("pageSizeTool").nowrap().close();
    	
        
        getHtmlBuilder().append(getTableModel().getMessages().getMessage(PreferencesConstants.TOOLBAR_PAGE_PERPAGE));
        rowsDisplayedDroplist();
        getHtmlBuilder().append(getTableModel().getMessages().getMessage(PreferencesConstants.TOOLBAR_PAGE_RECORD));
        
        
        getHtmlBuilder().tdEnd();

    }
    
    public void rowsDisplayedDroplist() {


        int allRowsDisplayed = getTableModel().getLimit().getTotalRows();
        int currentRowsDisplayed = getTableModel().getLimit().getCurrentRowsDisplayed();
        int rowsDisplayed = getTableModel().getTable().getRowsDisplayed();
        
        
        String pageSizeList=getTableModel().getTable().getPageSizeList();
        
        String[] sizeList=null;
        
        if (pageSizeList != null) {
        	sizeList=StringUtils.split(pageSizeList, ",");
        }
        
        getHtmlBuilder().select().name(getTableModel().getTableHandler().prefixWithTableId() + TableConstants.ROWS_DISPLAYED);
        
        String formId=getTableModel().getTable().getTableId();

        StringBuffer onchange = new StringBuffer();
        
        onchange.append(ECSideConstants.UTIL_FUNCTION_NAME+".changeRowsDisplayed(");
        
        onchange.append("'"+formId+"',this);");

        getHtmlBuilder().onchange(onchange.toString());

        getHtmlBuilder().close();
        
        getHtmlBuilder().newline();
        getHtmlBuilder().tabs(4);

        
        
        int i=0;
        int maxSize=Integer.MAX_VALUE;
        
        boolean hasDefaultSize=false;
        if (sizeList!=null) {
	        for (i=0;i<sizeList.length;i++){
	        	int size=0;
	        	String text;
	        	if (sizeList[i].trim().toLowerCase().startsWith("max:")){
	
	        		try{
	            		if (maxSize==Integer.MAX_VALUE ){
	            			maxSize=new Integer(sizeList[i].substring(4)).intValue();
	            		}
	        		}catch (Exception e) {
	        			maxSize=Integer.MAX_VALUE;
					}
	        		continue;
	        	}else if (allRowsDisplayed<maxSize && "all".equalsIgnoreCase(sizeList[i].trim())){
	        		size=allRowsDisplayed;
	        		text=getTableModel().getMessages().getMessage(PreferencesConstants.TOOLBAR_PAGE_ALL);
	        	
	        	}else{
	        		try{
	        			size=Integer.parseInt(sizeList[i].trim());
	        			text=String.valueOf(size);
	        		}catch(Exception ei){
	        			continue;
	        		}	
	        	}
	        	if (!hasDefaultSize) {
	        		hasDefaultSize=size==currentRowsDisplayed;
	        	}
	        	if (size<=maxSize){
	  	            getHtmlBuilder().option().value(String.valueOf(size));
		            if (currentRowsDisplayed == size) {
		            	getHtmlBuilder().selected();
		            }
		            getHtmlBuilder().close();
		            getHtmlBuilder().append(text);
		            getHtmlBuilder().optionEnd();
	        	}
	        }
        }
        
        if (i==0 || !hasDefaultSize){
            // default rows
            if (rowsDisplayed>allRowsDisplayed){
            	rowsDisplayed=allRowsDisplayed;
            }
            getHtmlBuilder().option().value(String.valueOf(rowsDisplayed));
            if (currentRowsDisplayed == rowsDisplayed) {
            	getHtmlBuilder().selected();
            }
            getHtmlBuilder().close();
            getHtmlBuilder().append(String.valueOf(rowsDisplayed));
            getHtmlBuilder().optionEnd();
        }


        getHtmlBuilder().newline();
        getHtmlBuilder().tabs(4);

        getHtmlBuilder().selectEnd();
    }

}
