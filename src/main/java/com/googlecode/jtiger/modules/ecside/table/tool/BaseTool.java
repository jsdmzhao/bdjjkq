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

import com.googlecode.jtiger.modules.ecside.core.Messages;
import com.googlecode.jtiger.modules.ecside.core.TableModel;
import com.googlecode.jtiger.modules.ecside.util.HtmlBuilder;


/**
 * @author Wei Zijun
 *
 */

public abstract class BaseTool {

	private TableModel tableModel;
	private HtmlBuilder htmlBuilder;
	private Messages messages;
	
	public BaseTool(){	}
	public BaseTool(HtmlBuilder html, TableModel model) {
		this.htmlBuilder=html;
		this.tableModel=model;
		this.messages=model.getMessages();
	}
	
	public abstract void buildTool();
	
    protected Messages getMessages() {
        return messages;
    }
	public void setMessages(Messages messages) {
		this.messages = messages;
	}

	
	public void setTableModel(TableModel model) {
		this.tableModel = model;
	}

	public TableModel getTableModel(){
		return tableModel;
	}
    public HtmlBuilder getHtmlBuilder() {
        return htmlBuilder;
    }
    

	public void setHtmlBuilder(HtmlBuilder htmlBuilder) {
		this.htmlBuilder = htmlBuilder;
	}
	
    public String getPrefixWithTableId(){
    	return tableModel.getTableHandler().prefixWithTableId();
    }


	
}
