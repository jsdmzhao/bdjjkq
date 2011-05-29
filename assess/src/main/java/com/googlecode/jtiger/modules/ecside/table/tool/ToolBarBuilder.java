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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.googlecode.jtiger.modules.ecside.common.log.LogHandler;
import com.googlecode.jtiger.modules.ecside.core.TableModel;
import com.googlecode.jtiger.modules.ecside.preferences.PreferencesConstants;
import com.googlecode.jtiger.modules.ecside.util.ECSideUtils;
import com.googlecode.jtiger.modules.ecside.util.HtmlBuilder;
import com.googlecode.jtiger.modules.ecside.view.html.BuilderUtils;


/**
 * @author Wei Zijun
 *
 */

@SuppressWarnings("unchecked")
public class ToolBarBuilder  {
	
//	private static Map registeredTool=new HashMap();
	
	private Log logger = LogFactory.getLog(ToolBarBuilder.class);
	
    public final static String TOOL_NEWLINE=",";
    public final static String TOOL_SEPARATOR="|";
    
//    public final static String TOOL_BLANK="blank";
//    public final static String TOOL_STATUS="status";
//    public final static String TOOL_PAGENAV="navigation";
//    public final static String TOOL_PAGEJUMP="pagejump";
//    public final static String TOOL_PAGESIZE="pagesize";
//    public final static String TOOL_EXTEND="extend";
//    public final static String TOOL_EXPORT="export";
//    
//    public final static String TOOL_SAVE="save";
//    public final static String TOOL_ADD="add";
//    public final static String TOOL_DEL="del";
    
    public final static Map toolClassPool=Collections.synchronizedMap(new HashMap());
//    
//    static {
//    	   	
//    	
//    }
    private HtmlBuilder html; 
    private TableModel model;
     
    private String toolbarContent;

    private String[] toolNames;
    
    
    public static void registeredTool(String toolName,Class classDefinition){
    	toolClassPool.put(toolName, classDefinition);
    }
    
    public BaseTool loadTool(String toolName){
    	
    	String excludeTool=","+model.getTable().getExcludeTool()+",";
    	
    	if (excludeTool.indexOf(toolName)>=0){
    		return null;
    	}
    	
    	toolName=model.getPreferences().getPreference(PreferencesConstants.TOOL_PREFIX+toolName);
    	
    	if (toolName==null) return null;
    	

    	BaseTool tool=null;
    	 try {
			Class classDefinition =(Class)toolClassPool.get(toolName); 
			if (classDefinition==null){
				classDefinition=Class.forName(toolName);
				registeredTool(toolName,classDefinition);
			}
			tool=(BaseTool)classDefinition.newInstance();
			tool.setHtmlBuilder(html);
			tool.setTableModel(model);
			tool.setMessages(model.getMessages());
		} catch (Exception e) {
			LogHandler.errorLog(logger, e);
		}
    	

    	return tool;
    }

    
    public void buildToolBar(){

    	
    	boolean showToolBar=true;
        boolean filterable = BuilderUtils.filterable(model);
        boolean showExports = BuilderUtils.showExports(model);
        
        if (!showToolBar || toolNames==null && !filterable && !showExports){
        	return;
        }
        
    	String width=model.getTable().getWidth();
    	if (width.indexOf("px")==-1 && width.indexOf("%")==-1){
    		width=width+"px";
    	}
    	
    	html.newline();

    	html.div().id(model.getTableHandler().prefixWithTableId()+"toolbar").styleClass("toolbar").style("width:"+width+";").close();
    	
    	html.table(1).id(model.getTableHandler().prefixWithTableId()+"toolbarTable").styleClass("toolbarTable").cellPadding("0").cellSpacing("0").close();
    	html.tr(1).close();
//        boolean hasExports=false;
        BaseTool tempTool=null;
        if (toolNames!=null){
	    	for (int i=0;i<toolNames.length;i++){
//	    		if (TOOL_EXPORT.equals(toolNames[i])){
//	    			hasExports=true;    			
//	    		}
	    		if (toolNames[i].indexOf(" ")<0){
	    			tempTool=loadTool(toolNames[i]);
	    			if (tempTool!=null) {
	    				tempTool.buildTool();
	    				html.newline();
	    			}
	    		}else{
	    			String[] stoolNames=toolNames[i].trim().split(" ");
	    			for (int si=0;si<stoolNames.length;si++){
	    				tempTool=loadTool(stoolNames[si]);
		    			if (tempTool!=null) {
		    				tempTool.buildTool();
		    				html.newline();
		    			}
	    			}
	    		}
	    	}
        }
    	
//    	if (!hasExports && showExports){
//    		loadTool(TOOL_EXPORT).buildTool();
//    	}
    	html.trEnd(1);
    	html.tableEnd(1);
    	html.divEnd();
    	html.div().id(model.getTableHandler().prefixWithTableId()+"toolbarShadow").style("display:none;").close().divEnd();
    }

    
    public ToolBarBuilder(HtmlBuilder html, TableModel model,String toolbarContent) {
        this.html = html;
        this.model = model;
        this.toolbarContent=toolbarContent;
    	String toolbarContentT=ECSideUtils.replace(toolbarContent,TOOL_SEPARATOR, ";"+TOOL_SEPARATOR+";");
    	toolbarContentT=ECSideUtils.replace(toolbarContentT,TOOL_NEWLINE, ";"+TOOL_NEWLINE+";");
    	toolNames=toolbarContentT.split(";");
    }


    protected HtmlBuilder getHtmlBuilder() {
        return html;
    }

    protected TableModel getTableModel() {
        return model;
    }

	public String getToolbarContent() {
		return toolbarContent;
	}

	public void setToolbarContent(String toolbarContent) {
		this.toolbarContent = toolbarContent;
	}

    
}
