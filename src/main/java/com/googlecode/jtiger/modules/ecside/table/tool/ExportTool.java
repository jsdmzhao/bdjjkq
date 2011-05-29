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

import java.util.Iterator;

import com.googlecode.jtiger.modules.ecside.core.ECSideConstants;
import com.googlecode.jtiger.modules.ecside.core.TableConstants;
import com.googlecode.jtiger.modules.ecside.core.TableModel;
import com.googlecode.jtiger.modules.ecside.core.bean.Export;
import com.googlecode.jtiger.modules.ecside.util.HtmlBuilder;

/**
 * @author Wei Zijun
 *
 */

@SuppressWarnings("unchecked")
public class ExportTool extends BaseTool {
	public ExportTool(){
		super();
	}
	public ExportTool(HtmlBuilder html,TableModel model) {
		super(html,model);
	}
	
    public void buildTool() {
    	getHtmlBuilder().td(1).nowrap().styleClass("exportTool").close();
    	getHtmlBuilder().append("<nobr>");
            Iterator iterator = getTableModel().getExportHandler().getExports().iterator();
            for (Iterator iter = iterator; iter.hasNext();) {
                Export export = (Export) iter.next();
                exportItemAsButton(export);
            }
//            buildExportIframe(getHtmlBuilder(),getTableModel());
         getHtmlBuilder().append("</nobr>");
         getHtmlBuilder().tdEnd();
    }
    
    public static void buildExportIframe(HtmlBuilder html,TableModel model){
    	html.append("<iframe style=\"border:0px;\" marginwidth=\"0\" marginheight=\"0\" frameborder=\"0\" border=\"0\" width=\"0\" height=\"0\"");
    	html.append(" id=\"").append(model.getTableHandler().prefixWithTableId()+TableConstants.EXPORT_IFRAME_ID);
    	html.append("\" name=\"").append(model.getTableHandler().prefixWithTableId()+TableConstants.EXPORT_IFRAME_ID);
    	html.append("\" ></iframe>");
    }
    public void exportItemAsButton(Export export) {
    	

    	String action =getExportAction(getTableModel().getTable().getTableId(),export.getView(),export.getFileName() );

		getHtmlBuilder().input().type("button");

    	if (TableConstants.VIEW_XLS.equals(export.getView()) ){
    		getHtmlBuilder().styleClass(TableConstants.EXPORTXLS_BUTTON_CSS);
    	}else if (TableConstants.VIEW_PDF.equals(export.getView()) ){
    		getHtmlBuilder().styleClass(TableConstants.EXPORTPDF_BUTTON_CSS);
    	}else if (TableConstants.VIEW_CSV.equals(export.getView()) ){
    		getHtmlBuilder().styleClass(TableConstants.EXPORTCSV_BUTTON_CSS);
    	}else if (TableConstants.VIEW_PRINT.equals(export.getView()) ){
    		getHtmlBuilder().styleClass(TableConstants.EXPORTPRINT_BUTTON_CSS);
    	}
		
        boolean showTooltips = getTableModel().getTable().isShowTooltips();

        
		getHtmlBuilder().onclick(action);
		getHtmlBuilder().alt(export.getText());
		if (showTooltips){
			getHtmlBuilder().title(export.getTooltip());
		}
 		getHtmlBuilder().xclose();
		
    }
    
    public String getExportAction(String formId, String exportView, String exportFileName) {
    	//(formid,action,method,etiid,type,fileName)
        StringBuffer action = new StringBuffer();
        String exportPage="";
        action.append(ECSideConstants.UTIL_FUNCTION_NAME+".doExport(");
        action.append("'"+exportView+"',");
        action.append("'"+exportFileName+"',");
        action.append("'"+exportPage+"',");
        action.append("'"+formId+"'"); 
        action.append(");");
        return action.toString();
    }

}
