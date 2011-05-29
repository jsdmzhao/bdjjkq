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
package com.googlecode.jtiger.modules.ecside.view.html;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.googlecode.jtiger.modules.ecside.core.TableConstants;
import com.googlecode.jtiger.modules.ecside.core.TableModel;
import com.googlecode.jtiger.modules.ecside.core.bean.Column;
import com.googlecode.jtiger.modules.ecside.core.bean.Table;
import com.googlecode.jtiger.modules.ecside.table.limit.Sort;
import com.googlecode.jtiger.modules.ecside.util.ECSideUtils;
import com.googlecode.jtiger.modules.ecside.util.HtmlBuilder;

/**
 * @author Wei Zijun
 *
 */

@SuppressWarnings("unchecked")
public class FormBuilder {
    private HtmlBuilder html;
    private TableModel model;
    private Table table;
    
    //private boolean isClassic;

    public FormBuilder(TableModel model) {
        this(new HtmlBuilder(), model);
    }

    public FormBuilder(HtmlBuilder html, TableModel model) {
        this.html = html;
        this.model = model;
        this.table = model.getTable();
        //isClassic=table.isClassic();
    }

    public HtmlBuilder getHtmlBuilder() {
        return html;
    }

    protected TableModel getTableModel() {
        return model;
    }

    public void formStart() {
        formAttributes();
        html.newline();
        html.div().close();
        instanceParameter();
        exportTableIdParameter();
        exportParameters();
        rowsDisplayedParameter();
        filterParameter();
        pageParameters();
        sortParameters();
        aliasParameters();
        userDefinedParameters();
        
        hiddenTotalField(); 
        
        filterField();
        
        html.newline();
        html.divEnd();
    }
    
    public void hiddenTotalField(){
        int currentRowsDisplayed = getTableModel().getLimit().getCurrentRowsDisplayed();
 		int totalPages = 0;
		int totalRows = getTableModel().getLimit().getTotalRows();
		if (currentRowsDisplayed > 0) {
			totalPages =(int)Math.ceil((double)totalRows / currentRowsDisplayed);
		} else {
			totalPages = 1;
		}
		 html.newline();
		html.input("hidden").name(model.getTableHandler().prefixWithTableId() +TableConstants.HIDDEN_TOTAL_PAGES).value(""+totalPages).xclose();
		 html.newline();
		html.input("hidden").name(model.getTableHandler().prefixWithTableId() +TableConstants.HIDDEN_TOTAL_ROWS).value(""+totalRows).xclose();

    }

    public void formEnd() {
    	String shadowRow=(String)table.getAttribute("shadowRow");
        if (StringUtils.isNotBlank(shadowRow)) {
            html.div();
            html.id(model.getTableHandler().prefixWithTableId()+"shadowRow");
            html.style("display:none;");
            html.close();
            html.append(shadowRow);
            html.divEnd();
        }
    	
        html.newline().append(ECSideUtils.getAjaxEnd(table.getTableId()));
        html.newline().divEnd();
        extendTableBottom();
        
        String form = table.getForm();
        if (StringUtils.isBlank(form)) {
            html.formEnd();
        }
    }

    public void formAttributes() {
        String form = table.getForm();
        String tableId=table.getTableId();
        
    	String width=table.getWidth();
    	if (width!=null&&width.indexOf("px")==-1 && width.indexOf("%")==-1){
    		width=width+"px";
    	}
    	
        if (StringUtils.isBlank(form)) {
            html.form();
            html.id(tableId);
            html.method(table.getMethod());

//	     	String width=table.getWidth();
//	     	if (StringUtils.isBlank(width)){
//	     		width="100%";
//	     	}
//	     	
//	     	if (width!=null){
//	     		width=width.trim();
//	     		
//	     		if (width.indexOf("%")==width.length()-1){
//			     	html.append(" widthPercent=\"").append(width.substring(0,width.length()-1 )).append("\" ");
//	     		}
//	     	}
//	     	
//	    	
	    	html.style("width:"+width+";visibility :hidden;");
            
        	String nearPageNumS=getTableModel().getTable().getNearPageNum();
        	int nearPageNum=new Integer(nearPageNumS).intValue();

        	if (nearPageNum>0){
        		html.append(" nearPages=\""+nearPageNum+"\" ");
        	}

        	

            if (table.isFilterable()) {
            	html.append(" filterable=\"true\" ");
            }
            
            boolean canResizeColWidth=table.isResizeColWidth();
        	if (canResizeColWidth){
        		html.append(" canResizeColWidth=\"").append(canResizeColWidth+"").append("\" ");
        	}
            
        	String maxRowsExportedS=getTableModel().getTable().getMaxRowsExported();
        	int maxRowsExported=new Integer(maxRowsExportedS).intValue();
        	
        	if (maxRowsExported >0) {
        		html.append(" maxRowsExported=\"").append(maxRowsExported+"").append("\" ");
        	}
            int minColWidth=table.getMinColWidth();
            html.append(" minColWidth=\"").append(minColWidth+"").append("\" ");
            
            html.newline();
            html.action(table.getAction());
            html.enctype(table.getEnctype());
            
            html.attribute("insertAction",table.getInsertAction());
            html.attribute("updateAction",table.getUpdateAction());
            html.attribute("deleteAction",table.getDeleteAction());
            html.attribute("shadowRowAction",table.getShadowRowAction());        
            
            
            html.close();
        }
        

        //extendTableTop();
        String theme = table.getTheme();
        html.newline().div().styleClass(theme).id(model.getTableHandler().prefixWithTableId()+TableConstants.MAIN_CONTENT_ID);

        html.style("width:"+width+";").close();
        html.newline().append(ECSideUtils.getAjaxBegin(tableId));
       // TODO :
        extendTableTop();
    }

    public void instanceParameter() {
        html.newline();
        html.input("hidden");
        html.name(TableConstants.EXTREME_COMPONENTS_INSTANCE);
        html.value(table.getTableId());
        html.xclose();
    }

    public void filterParameter() {
        if (BuilderUtils.filterable(model)) {
            html.newline();
            html.input("hidden");
            html.name(model.getTableHandler().prefixWithTableId() + TableConstants.FILTER + TableConstants.ACTION);
            if (model.getLimit().isFiltered()) {
                html.value(TableConstants.FILTER_ACTION);
            }
            html.xclose();
        }
    }
    
    public void filterField() {
        if (!table.isFilterable()) {
            return;
        }
        List columns = model.getColumnHandler().getFilterColumns();
        if (columns.size()>0){
        	html.newline();
	        for (Iterator iter = columns.iterator(); iter.hasNext();) {
	        	 html.append(filterHiddenInput(model,(Column) iter.next()));
	        }
        	html.newline();
        }

    }
    
    public static String filterHiddenInput(TableModel model, Column column) {
        HtmlBuilder html = new HtmlBuilder();
        if (column.isFilterable()){
	        html.input("hidden");
	        html.name(model.getTableHandler().prefixWithTableId() + TableConstants.FILTER + column.getAlias());
	
	        String value = column.getValueAsString();
	        if (StringUtils.isNotBlank(value)) {
	            html.value(value);
	        }
	        html.xclose();
        }
        return html.toString();
    }

    public void rowsDisplayedParameter() {
        html.newline();
        html.input("hidden");
        html.name(model.getTableHandler().prefixWithTableId() + TableConstants.CURRENT_ROWS_DISPLAYED);
        int currentRowsDisplayed = model.getLimit().getCurrentRowsDisplayed();
        html.value(String.valueOf(currentRowsDisplayed));
        html.xclose();
    }

    public void pageParameters() {
        html.newline();
        html.input("hidden");
        html.name(model.getTableHandler().prefixWithTableId() + TableConstants.PAGE);
        int page = model.getLimit().getPage();
        if (page > 0) {
            html.value(String.valueOf(page));
        }
        html.xclose();
    }

    /**
     * The exported table id parameter is used to uniquely identify this table when exporting.
     * If there is more than one table in the form then make sure the other table did
     * not already set the exported table id parameter.
     */
    public void exportTableIdParameter() {
        if (!BuilderUtils.showExports(model)) {
            return;
        }

        String form = BuilderUtils.getForm(model);
        String existingForm = (String)model.getContext().getRequestAttribute(TableConstants.EXPORT_TABLE_ID);
        if (!form.equals(existingForm)) {
            html.newline();
            html.input("hidden");
            html.name(TableConstants.EXPORT_TABLE_ID);
            html.xclose();
            // set to key off to other tables in the same form
            model.getContext().setRequestAttribute(TableConstants.EXPORT_TABLE_ID, form); 
        }

        String existingForm2 = (String)model.getContext().getRequestAttribute(TableConstants.EXPORT_PAGE_FLAG);
        if (!form.equals(existingForm2)) {
            html.newline();
            html.input("hidden");
            html.name(TableConstants.EXPORT_PAGE_FLAG);
            html.xclose();

            model.getContext().setRequestAttribute(TableConstants.EXPORT_PAGE_FLAG, form);
        }


    }

    /**
     * The parameters neccessary to do the exports. This includes the
     * ViewResolver and the export file name.
     */
    public void exportParameters() {
    	
        html.newline();
        html.input("hidden");
        html.name(model.getTableHandler().prefixWithTableId() + TableConstants.EXPORT_FILE_NAME);
        html.xclose();
        
        if (!BuilderUtils.showExports(model)) {
            return;
        }

        html.newline();
        html.input("hidden");
        html.name(model.getTableHandler().prefixWithTableId() + TableConstants.EXPORT_VIEW);
        html.xclose();


    }

    public void sortParameters() {
        List columns = model.getColumnHandler().getColumns();
        for (Iterator iter = columns.iterator(); iter.hasNext();) {
            Column column = (Column) iter.next();
            if (column.isSortable()) {
                html.newline();
                html.input("hidden");
                html.name(model.getTableHandler().prefixWithTableId() + TableConstants.SORT + column.getAlias());
                Sort sort = model.getLimit().getSort();
                if (sort.isSorted() && sort.getAlias().equals(column.getAlias())) {
                    html.value(sort.getSortOrder());
                }
                html.xclose();
            }
        }
    }

    public boolean isInParameters(String parametersNames,String name ){
//    	return parametersNames.indexOf(","+name+",")>=0 
    	String[] t=parametersNames.split(",");
    	for (int i=0;i<t.length;i++){
    		if (ECSideUtils.isSearchMatch(name, t[i]) ){
    			return true;
    		}
    	}
    	return false;
    }
    public void userDefinedParameters() {
        Map parameterMap = model.getRegistry().getParameterMap();
        
       String includeParameters=table.getIncludeParameters();
       String excludeParameters=table.getExcludeParameters();
       
//       includeParameters=StringUtils.isNotBlank(includeParameters)?","+includeParameters+",":null;
//       excludeParameters=StringUtils.isNotBlank(excludeParameters)?","+excludeParameters+",":null;
       
        Set keys = parameterMap.keySet();
        String[] keyField=new String[]{
        		//ECSideConstants.EASY_DATA_ACCESS_FLAG,
        		//ECSideConstants.EASY_DATA_LIST_FLAG,
        		//ECSideConstants.EASY_DATA_EXPORT_FLAG
        };

        for (Iterator iter = keys.iterator(); iter.hasNext();) {
            String name = (String) iter.next();

            if (name.startsWith(model.getTableHandler().prefixWithTableId())
            		|| excludeParameters!=null && isInParameters(excludeParameters,name) 
            		|| includeParameters!=null && !isInParameters(includeParameters,name)
            		|| ArrayUtils.contains(keyField, name)
            		){
                continue;
            }

            String values[] = (String[]) parameterMap.get(name);
            if (values == null || values.length == 0) {
                html.newline();
                html.input("hidden").name(name).xclose();
            } else {
                for (int i = 0; i < values.length; i++) {
                    html.newline();
                    html.input("hidden").name(name).value(values[i]).xclose();
                }
            }
        }
    }

    /**
     * If the column has a alias, it will keep the column property by the parameter
     */
    public void aliasParameters() {
        List columns = model.getColumnHandler().getColumns();
        for (Iterator iter = columns.iterator(); iter.hasNext();) {
            Column column = (Column) iter.next();
            if (StringUtils.isNotBlank(column.getProperty()) && !column.getProperty().equals(column.getAlias())) {
                html.newline();
                html.input("hidden");
                html.name(model.getTableHandler().prefixWithTableId() + TableConstants.ALIAS + column.getAlias());
                html.value(column.getProperty());
                html.xclose();
            }
        }
    }
    
    
    public void extendTableTop(){
    	String extendTableTop = (String)table.getAttribute("ExtendTableTop");
    	if(StringUtils.isNotBlank(extendTableTop)){
    		html.newline();
    		html.append(extendTableTop);
    	}
    }
    
    public void extendTableBottom(){
    	String extendTableBottom = (String)table.getAttribute("ExtendTableBottom");
    	if(StringUtils.isNotBlank(extendTableBottom)){
    		html.newline();
    		html.append(extendTableBottom);
    	}
    }


    public String toString() {
        return html.toString();
    }
}
