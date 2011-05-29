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

import com.googlecode.jtiger.modules.ecside.core.TableModel;
import com.googlecode.jtiger.modules.ecside.util.HtmlBuilder;
import com.googlecode.jtiger.modules.ecside.view.html.BuilderConstants;

/**
 * @author Wei Zijun
 *
 */

public class StatusTool extends BaseTool {
	
	public StatusTool(){
		super();
	}
	
	public StatusTool(HtmlBuilder html,TableModel model) {
		super(html,model);
	}
	
    public void buildTool() {
    	getHtmlBuilder().td(1).nowrap().styleClass("statusTool").close();
    	getHtmlBuilder().append("<nobr>");
        
        if (getTableModel().getLimit().getTotalRows() == 0) {
            getHtmlBuilder().append(getTableModel().getMessages().getMessage(BuilderConstants.STATUSBAR_NO_RESULTS_FOUND));
        } else {
            Integer total = new Integer(getTableModel().getLimit().getTotalRows());
            Integer from = new Integer(getTableModel().getLimit().getRowStart() + 1);
            Integer to = new Integer(getTableModel().getLimit().getRowEnd());
            Object[] messageArguments = { total, from, to };
            getHtmlBuilder().append(getTableModel().getMessages().getMessage(BuilderConstants.STATUSBAR_RESULTS_FOUND, messageArguments));
        }
        
        getHtmlBuilder().append("</nobr>");
        getHtmlBuilder().tdEnd();
    }
    

}
