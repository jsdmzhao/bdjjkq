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

import com.googlecode.jtiger.modules.ecside.core.TableModel;
import com.googlecode.jtiger.modules.ecside.util.HtmlBuilder;

/**
 * @author Wei Zijun
 *
 */

public class ExtendTool extends BaseTool {

	public ExtendTool() {
		super();
	}

	public ExtendTool(HtmlBuilder html, TableModel model) {
		super(html, model);
	}

	public void buildTool() {

		String extend = (String) getTableModel().getTable()
				.getAttribute("ExtendTool");
		getHtmlBuilder().td(1).styleClass("extendTool").close();
		if (StringUtils.isNotBlank(extend)) {
			getHtmlBuilder().append(extend);
		}
		getHtmlBuilder().tdEnd();

	}

	public static void buildExtendTool(HtmlBuilder html,TableModel model) {
		String extend = (String) model.getTable()
				.getAttribute("ExtendTool");
		if (StringUtils.isNotBlank(extend)) {

			String width = model.getTable().getWidth();
			if (width.indexOf("px") == -1 && width.indexOf("%") == -1) {
				width = width + "px";
			}

			html.newline();

			html.div().styleClass("toolbar").style("width:" + width + ";")
					.close();

			html.table(1).styleClass("toolbarTable").cellPadding("0")
					.cellSpacing("0").close();
			html.tr(1).close();

			html.td(1).styleClass("extendTool").style("width:" + width + ";")
					.close();
			html.append(extend);
			html.tdEnd();

			html.trEnd(1);
			html.tableEnd(1);
			html.divEnd();
		}
	}

}
