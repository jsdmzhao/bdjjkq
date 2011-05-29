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

import com.googlecode.jtiger.modules.ecside.core.ECSideConstants;
import com.googlecode.jtiger.modules.ecside.core.TableConstants;
import com.googlecode.jtiger.modules.ecside.core.TableModel;
import com.googlecode.jtiger.modules.ecside.util.HtmlBuilder;
import com.googlecode.jtiger.modules.ecside.view.html.BuilderConstants;
import com.googlecode.jtiger.modules.ecside.view.html.BuilderUtils;

/**
 * @author Wei Zijun
 *
 */

public class PageNavigationTool extends BaseTool {
	
	public PageNavigationTool(){
		super();
	}
	
	
	public PageNavigationTool(HtmlBuilder html,TableModel model) {
		super(html,model);
	}
	
    public void buildTool() {
    	
//    	if (nearPageNum<1) {
//    		getHtmlBuilder().td(1).styleClass("pageNavigationTool").nowrap().close();
//    	}else{
//    		getHtmlBuilder().td(1).styleClass("pageNavigationNearPageTool").nowrap().close();
//    		
//    	}
		
    	getHtmlBuilder().td(1).styleClass("pageNavigationTool").nowrap().close();
		firstPageItemAsButton();
		prevPageItemAsButton();
		getHtmlBuilder().tdEnd();
		
//    	String nearPageNumS=getTableModel().getTable().getNearPageNum();
//    	int nearPageNum=new Integer(nearPageNumS).intValue();
//		if (nearPageNum>0){
//	    	int page=getTableModel().getLimit().getPage();
//	    	int lastPage = BuilderUtils.getTotalPages(getTableModel());
//	    	int pi1=0;
//	    	int startP=page-nearPageNum;
//	    	int endP=page+nearPageNum;
//	    	
//			if ( startP<1){
//				endP=endP+(1-startP);
//				startP=1;
//			}
//			if ( endP>lastPage){
//				startP=startP-(endP-lastPage);
//				endP=lastPage;
//			}
//			startP=startP<1?1:startP;
//			
//			getHtmlBuilder().td(1).nowrap().styleClass("pageNavigationNearPageTool").close();
//			getHtmlBuilder().span().close();
//	    	for (pi1=startP;pi1<=endP;pi1++){
//	
//	    		if (pi1==page){
//	    			getHtmlBuilder().append(" <b>"+pi1+"</b> ");
//	    		}else{
//	    			getHtmlBuilder().append(" <a href=\"#\" onclick=\"").append(getPageNavAction(pi1)).append("return false;\" >"+pi1+"</a> ");
//	    		}
//	    	}
//	    	getHtmlBuilder().spanEnd();
//	    	getHtmlBuilder().tdEnd();
//		}
		
    	getHtmlBuilder().td(1).styleClass("pageNavigationTool").nowrap().close();
		nextPageItemAsButton();
		lastPageItemAsButton();
		getHtmlBuilder().tdEnd();
    }
    
    
    public void firstPageItemAsButton() {
    	
        boolean showTooltips = getTableModel().getTable().isShowTooltips();
    	boolean isEnabled=BuilderUtils.isFirstPageEnabled(getTableModel().getLimit().getPage());
	
    	
		getHtmlBuilder().input().type("button");
		
        String disabled=null;
        if (!isEnabled){
        	disabled="D";
            getHtmlBuilder().disabled();
        }else{
        	disabled="";
        }
        
		getHtmlBuilder().styleClass(TableConstants.FIRST_PAGE_CSS+disabled);
		getHtmlBuilder().onclick(getPageNavAction(1));
        if (showTooltips) {
        	getHtmlBuilder().title(getMessages().getMessage(BuilderConstants.TOOLBAR_FIRST_PAGE_TOOLTIP));
        } 
        

		getHtmlBuilder().xclose();
		


    }
    
    public void prevPageItemAsButton() {
    	int prevPage = getTableModel().getLimit().getPage()-1;
    	
    	
        boolean showTooltips = getTableModel().getTable().isShowTooltips();
    	boolean isEnabled=BuilderUtils.isPrevPageEnabled(getTableModel().getLimit().getPage());
    	
    	
    	
		getHtmlBuilder().input().type("button");
		
        String disabled=null;
        if (!isEnabled){
        	disabled="D";
            getHtmlBuilder().disabled();
        }else{
        	disabled="";
        }
        
		getHtmlBuilder().styleClass(TableConstants.PREV_PAGE_CSS+disabled);
		getHtmlBuilder().onclick(getPageNavAction(prevPage));
        if (showTooltips) {
        	getHtmlBuilder().title(getMessages().getMessage(BuilderConstants.TOOLBAR_PREV_PAGE_TOOLTIP));
        } 
		getHtmlBuilder().xclose();
		
		


    }
    
    public void nextPageItemAsButton() {
    	int nextPage = getTableModel().getLimit().getPage()+1;
    	int lastPage = BuilderUtils.getTotalPages(getTableModel());
    	
    	
        boolean showTooltips = getTableModel().getTable().isShowTooltips();
    	boolean isEnabled=BuilderUtils.isNextPageEnabled(getTableModel().getLimit().getPage(),lastPage);
    	
    	
		getHtmlBuilder().input().type("button");
		
        String disabled=null;
        if (!isEnabled){
        	disabled="D";
            getHtmlBuilder().disabled();
        }else{
        	disabled="";
        }
        
		getHtmlBuilder().styleClass(TableConstants.NEXT_PAGE_CSS+disabled);
		getHtmlBuilder().onclick(getPageNavAction(nextPage));
        if (showTooltips) {
        	getHtmlBuilder().title(getMessages().getMessage(BuilderConstants.TOOLBAR_NEXT_PAGE_TOOLTIP));
        } 
        if (!isEnabled){
            getHtmlBuilder().disabled();
        }
		getHtmlBuilder().xclose();
    	
       

    }
    
    public void lastPageItemAsButton() {
    	int lastPage = BuilderUtils.getTotalPages(getTableModel());
    	
   	
        boolean showTooltips = getTableModel().getTable().isShowTooltips();
    	boolean isEnabled=BuilderUtils.isLastPageEnabled(getTableModel().getLimit().getPage(),lastPage);
    	
    	
		getHtmlBuilder().input().type("button");
		
        String disabled=null;
        if (!isEnabled){
        	disabled="D";
            getHtmlBuilder().disabled();
        }else{
        	disabled="";
        }
        
		getHtmlBuilder().styleClass(TableConstants.LAST_PAGE_CSS+disabled);
		getHtmlBuilder().onclick(getPageNavAction(lastPage));
        if (showTooltips) {
        	getHtmlBuilder().title(getMessages().getMessage(BuilderConstants.TOOLBAR_LAST_PAGE_TOOLTIP));
        } 
        if (!isEnabled){
            getHtmlBuilder().disabled();
        }
		getHtmlBuilder().xclose();
      

    }
    
    public String getPageNavAction(int page) {
    	String formId=getTableModel().getTable().getTableId();
//    	String formAction=getTableModel().getTable().getAction();
//    	String formMethod=getTableModel().getTable().getMethod();
//    	String pageNoId=getTableModel().getTableHandler().prefixWithTableId()+TableConstants.PAGE;
    	
        StringBuffer action = new StringBuffer(ECSideConstants.UTIL_FUNCTION_NAME+".gotoPage(");

        action.append(page+",'"+formId+"'");
        
        action.append(");");
        
        
        String onInvokeAction = getTableModel().getTable().getOnInvokeAction();
        if (onInvokeAction!=null&&onInvokeAction.length()>0){
        	action.append(onInvokeAction);
        }

        
        return action.toString();
    }
}
