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
import com.googlecode.jtiger.modules.ecside.preferences.PreferencesConstants;
import com.googlecode.jtiger.modules.ecside.util.HtmlBuilder;

/**
 * @author Wei Zijun
 *
 */

public class PageJumpTool extends BaseTool {

	public PageJumpTool(){
		super();
	}
	
	public PageJumpTool(HtmlBuilder html,TableModel model) {
		super(html,model);
	}
	
	
    public void buildTool() {
    	
        int currentRowsDisplayed = getTableModel().getLimit().getCurrentRowsDisplayed();
        int currentPage = getTableModel().getLimit().getPage();
		int totalPages = 0;
		int totalRows = getTableModel().getLimit().getTotalRows();
		if (currentRowsDisplayed > 0) {
			totalPages =(int)Math.ceil((double)totalRows / currentRowsDisplayed);
		} else {
			totalPages = 1;
			currentPage = 1;
		}
		
		boolean showTooltips = getTableModel().getTable().isShowTooltips();
    	
		String action=getGotoPagesAction();
        
        getHtmlBuilder().td(1).styleClass("pageJumpTool").nowrap();
        getHtmlBuilder().onmouseover(ECSideConstants.UTIL_FUNCTION_NAME+".NearPagesBar.showMe(this,'"+getTableModel().getTable().getTableId()+"');");
        getHtmlBuilder().onmouseout(ECSideConstants.UTIL_FUNCTION_NAME+".NearPagesBar.hideMe(this,'"+getTableModel().getTable().getTableId()+"');");
        getHtmlBuilder().close();
        getHtmlBuilder().append("<nobr>");
		getHtmlBuilder().input().type("button");
		getHtmlBuilder().styleClass(TableConstants.JUMP_PAGE_CSS);
		getHtmlBuilder().onclick(action);
		if (showTooltips) {
			getHtmlBuilder().title(getTableModel().getMessages().getMessage(PreferencesConstants.TOOLBAR_GOTO_PAGE_TOOLTIP));
		}
		getHtmlBuilder().xclose();
        getHtmlBuilder().input("text").name(getPrefixWithTableId() + TableConstants.PAGES);
        getHtmlBuilder().value(""+currentPage);
        getHtmlBuilder().styleClass(TableConstants.JUMP_PAGE_INPUT_CSS);
        getHtmlBuilder().append(" onkeydown=\"if (event.keyCode && event.keyCode==13 ) {"+action+";return false; } \" ");
        getHtmlBuilder().xclose();
        getHtmlBuilder().append("/").append(""+totalPages).append(getTableModel().getMessages().getMessage(PreferencesConstants.TOOLBAR_PAGE_PAGENAME));
        getHtmlBuilder().append("</nobr>");
        getHtmlBuilder().tdEnd();

    }
    
    public String getGotoPagesAction() {
    	
    	String formId=getTableModel().getTable().getTableId();
        
        StringBuffer action = new StringBuffer(ECSideConstants.UTIL_FUNCTION_NAME+".gotoPageByInput(");
        action.append("this,'"+formId+"'");
        action.append(");");
        
        
        String onInvokeAction = getTableModel().getTable().getOnInvokeAction();
        if (onInvokeAction!=null&&onInvokeAction.length()>0){
        	action.append(onInvokeAction);
        }

        
        return action.toString();
    }
}
