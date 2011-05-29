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
import com.googlecode.jtiger.modules.ecside.view.html.BuilderUtils;

/**
 * @author Wei Zijun
 *
 */

public class BlankSeparatorTool extends BaseTool {
	public BlankSeparatorTool(){
		super();
	}
	public BlankSeparatorTool(HtmlBuilder html,TableModel model) {
		super(html,model);
	}
	
    public void buildTool() {
    	getHtmlBuilder().td(1).close();
        getHtmlBuilder().img();
        getHtmlBuilder().src(BuilderUtils.getImage(getTableModel(), BuilderConstants.TOOLBAR_SEPARATOR_IMAGE));
        getHtmlBuilder().styleClass(BuilderConstants.TOOLBAR_SEPARATOR_CSS);
        getHtmlBuilder().alt("Separator");
        getHtmlBuilder().xclose();
        getHtmlBuilder().tdEnd();
    }

}
