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

import org.apache.commons.lang.StringUtils;

import com.googlecode.jtiger.modules.ecside.core.TableConstants;
import com.googlecode.jtiger.modules.ecside.core.TableModel;
import com.googlecode.jtiger.modules.ecside.core.bean.Column;
import com.googlecode.jtiger.modules.ecside.core.bean.Table;
import com.googlecode.jtiger.modules.ecside.util.ECSideUtils;
import com.googlecode.jtiger.modules.ecside.util.HtmlBuilder;


/**
 * @author Wei Zijun
 *
 */

@SuppressWarnings("unchecked")
public class TableBuilder {
    private HtmlBuilder html;
    private TableModel model;
    private Table table;
    
    private boolean isClassic;

    public TableBuilder(TableModel model) {
        this(new HtmlBuilder(), model);
    }
    
    public TableBuilder(HtmlBuilder html, TableModel model) {
        this.html = html;
        this.model = model;
        this.table = model.getTable();
        isClassic=table.isClassic();
    }

    public HtmlBuilder getHtmlBuilder() {
        return html;
    }

    protected TableModel getTableModel() {
        return model;
    }

    protected Table getTable() {
        return table;
    }


    public void tableStart() {
    	
//        html.table(0);
//        html.border("0");
//        html.cellSpacing("0");
//        html.cellPadding("0");
//        html.styleClass("tableBox");
//        width();
//        
// 
//        
//        html.close();
//        html.tr(1).close();
//        html.td(1).width("100%").close();
        
    	

    	  	
        html.table(0);
        id();
        border();
        cellSpacing();
        cellPadding();
        styleClass();
        style();
        width();
        height();
        
        html.append(" ").append(ECSideUtils.nullToBlank(table.getTagAttributes())).append(" ");
        html.append(table.getAttribute(TableConstants.EXTEND_ATTRIBUTES));

        html.close();
    }

    public void tableStartH() {
  	  	
      html.table(0);
//      id();
      border();
      cellSpacing();
      cellPadding();
      styleClass();
      style();
      width();
      height();
      
      html.append(" ").append(ECSideUtils.nullToBlank(table.getTagAttributes())).append(" ");
      html.append(table.getAttribute(TableConstants.EXTEND_ATTRIBUTES));

      html.close();
  }
    
    public void tableEnd() {
        html.tableEnd(0);
        
//        html.tdEnd();
//        html.trEnd(0);
//        html.tableEnd(0);
        
    }
    public String getPrefixWithTableId(){
    	return getTableModel().getTableHandler().prefixWithTableId();
    }
    public void id() {
        html.id(model.getTableHandler().prefixWithTableId() + BuilderConstants.TABLE);
    }

    public void border() {
        String border = table.getBorder();
        html.border(border);
    }

    public void cellSpacing() {
        String cellSpacing = table.getCellspacing();
        html.cellSpacing(cellSpacing);
    }

    public void cellPadding() {
        String cellPadding = table.getCellpadding();
        html.cellPadding(cellPadding);
    }

    public void styleClass() {
        String styleClass = table.getStyleClass();
        html.styleClass(styleClass);
    }

    public void style() {
    	
//    	String style=table.getStyle();
//    	if (StringUtils.isNotBlank(table.getStyle())){
//    		 html.style(style);
//    	}
    	
        String style = StringUtils.isNotBlank(table.getStyle())?table.getStyle():"";
        String fixed="";
        
        boolean canResizeColWidth=model.getTable().isResizeColWidth();
    	
        if (canResizeColWidth){
        	fixed="table-layout:fixed;";
    	}
        html.style(fixed+style);

    	
    }

    
    public void height() {
		String height = table.getHeight();
		if (isClassic && StringUtils.isNotBlank(height)) {
			html.height(height);
		}
	}
    
    public void width() {
        String listWidth=table.getListWidth();
        String width = table.getWidth();
        
    if (isClassic){
    	html.width(width);
    }else{
   		html.width(listWidth);
    }
        
//        if (isClassic){
//        	html.width(StringUtils.isNotBlank(width)?width:listWidth);
//        }else{
//        	if (StringUtils.isNotBlank(listWidth) ){
//        		html.width(listWidth);
//        	}else{
//        		// TODO:
//        	}
//        }
    }

    public void title() {
        boolean showTitle = BuilderUtils.showTitle(model);
        if (showTitle) {
            String title = table.getTitle();
            if (StringUtils.isNotBlank(title)) {
            	String width=table.getWidth();
            	if (width.indexOf("px")==-1 && width.indexOf("%")==-1){
            		width=width+"px";
            	}
            	html.newline();
                html.div().style("width:"+width+";").styleClass(BuilderConstants.TITLE_CSS).close().append(title).divEnd();
            }
        }
    }
    
    
//    public void titleRowSpanColumns() {
//        boolean showTitle = BuilderUtils.showTitle(model);
//        if (showTitle) {
//            String title = model.getTable().getTitle();
//            if (StringUtils.isNotBlank(title)) {
//                int columnCount = model.getColumnHandler().columnCount();
//                html.tr(1).styleClass(BuilderConstants.TITLE_ROW_CSS).close();
//                html.td(2).colSpan("" + columnCount).close();
//                html.span().close().append(title).spanEnd();
//                html.tdEnd();
//                html.trEnd(1);
//            }
//        }
//    }

    public void headerRow() {
    	boolean showHeader=model.getTable().isShowHeader();
    	String hideStyle="";
    	if (!showHeader){
    		hideStyle=" style=\"display:none;\" ";
    	}
        html.tr(1).append(hideStyle).close();

        List columns = model.getColumnHandler().getHeaderColumns();
        for (Iterator iter = columns.iterator(); iter.hasNext();) {
            Column column = (Column) iter.next();
           	html.append(column.getCellDisplay());
           	html.newline();
        }

        html.trEnd(1);
    }
    
    public void headerHideRow() {

        html.tr(1).id(model.getTableHandler().prefixWithTableId()+"hideListRow").styleClass("hideListRow").close();

        List columns = model.getColumnHandler().getHeaderColumns();
        for (Iterator iter = columns.iterator(); iter.hasNext();) {
            Column column = (Column) iter.next();
    		html.td(2).valign("middle").append(" columnName=\"").append(column.getAlias()).append("\" ");
            html.width(column.getWidth());
            html.styleClass(column.getStyleClass());
            html.style(column.getStyle());
            if (StringUtils.isNotBlank(column.getNowrap())){
            	html.append(" nowrap=\""+column.getNowrap()+"\" ");
            }
        	if ("true".equalsIgnoreCase(column.getGroup())){
        		html.append(" group=\"true\" ");
        	}
        	if (column.isSortable()){
        		html.append(" sortable=\"true\" ");
        	}
            html.close();
            html.append("&#160;");
            html.tdEnd();
        }

        html.trEnd(1);
    }

    public void bodyHideRow() {

        html.tr(1).styleClass("hideListRow").close();

        List columns = model.getColumnHandler().getHeaderColumns();
        for (Iterator iter = columns.iterator(); iter.hasNext();) {
            Column column = (Column) iter.next();
            html.td(2);
            html.width(column.getWidth());
            html.styleClass(column.getStyleClass());
            html.style(column.getStyle());
            if (StringUtils.isNotBlank(column.getNowrap())){
            	html.append(" nowrap=\""+column.getNowrap()+"\" ");
            }
            html.close();
            html.append("&#160;");
            html.tdEnd();
        }

        html.trEnd(1);
    }
    

    public void theadStart() {
        html.thead(1).id(model.getTableHandler().prefixWithTableId() + BuilderConstants.TABLE+"_head").close();
    }

    public void theadEnd() {
        html.theadEnd(1);
    }

    public void tbodyStart() {
        //html.tbody(1).styleClass(BuilderConstants.TABLE_BODY_CSS).close();
        html.tbody(1).id(model.getTableHandler().prefixWithTableId() + BuilderConstants.TABLE+"_body").close();
    }

    public void tbodyEnd() {
        html.tbodyEnd(1);
    }

    public void themeStart() {
        html.newline();
        String theme = model.getTable().getTheme();
        html.div().styleClass(theme);
        html.close();
    }

    public void themeEnd() {
        html.newline();
        html.divEnd();
    }
    
    public void buildWaitingBar(){
    	html.div().id( model.getTableHandler().prefixWithTableId()+"waitingBar");
    	html.styleClass("waitingBar").close();
   	    	
    	html.divEnd();
    	
    	html.div().id( model.getTableHandler().prefixWithTableId()+"waitingBarCore");
    	html.styleClass("waitingBarCore").close();
    	html.divEnd();
    }
    
    public void buildScript(){
    	boolean generateScript=table.isGenerateScript();

    	if (generateScript){
    		
        boolean useAjax=table.isUseAjax();
        boolean doPreload=table.isDoPreload();
    	boolean isClassic=table.isClassic();
    	
        StringBuffer jscript=new StringBuffer();
    	jscript.append("\n<script type=\"text/javascript\" >\n");
    	jscript.append("(function(){ \n");
    	jscript.append(" var gird=ECSideUtil.createGird('"+table.getTableId()+"');").append(" \n");
    	jscript.append(" gird.useAjax="+useAjax+"; ").append(" \n");
    	jscript.append(" gird.doPreload="+doPreload+";").append(" \n");
    	jscript.append(" gird.isClassic="+isClassic+";").append(" \n");
    	
    	if (StringUtils.isNotBlank(table.getMinHeight())){
    		jscript.append(" gird.minHeight='"+table.getMinHeight()+"';").append(" \n");
    	}
    	
    	jscript.append("})();");
    	jscript.append("\n</script>\n");

    	html.append(jscript.toString());
    	
    	}

    
    }
    
    public String toString() {
        return html.toString();
    }
    

}
