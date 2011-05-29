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
package com.googlecode.jtiger.modules.ecside.core.bean;

import java.util.Collection;

import org.apache.commons.lang.StringUtils;

import com.googlecode.jtiger.modules.ecside.core.TableConstants;
import com.googlecode.jtiger.modules.ecside.core.TableModel;
import com.googlecode.jtiger.modules.ecside.preferences.PreferencesConstants;
import com.googlecode.jtiger.modules.ecside.preferences.TableProperties;


/**
 * @author Wei Zijun
 *
 */

/**
 * An exact copy of the column tag. The Model will only reference this copy.
 * 
 */

@SuppressWarnings({ "unchecked", "serial" })
public class Column extends BaseBean {


    private String alias;
    private String calc[];
    private String calcTitle[];
    private String cell;
    private String cellDisplay;
    private Object filterOptions;
    private Boolean escapeAutoFormat;
    private Boolean filterable;
    private String filterCell;
    private String filterClass;
    private String filterStyle;
    private String format;
    private String headerCell;
    private String headerClass;
    private String headerStyle;
    private String headerStyleClass;
    private String interceptor;
    private String parse;
    private String property;
    private Object propertyValue;
    private Boolean sortable;
    private String style;
    private String styleClass;
    private String title;
    private Object value;
    private String viewsAllowed[];
    private String viewsDenied[];
    private String width;
    private Boolean resizeColWidth;
    private int minWidth;
    private String tipTitle;

    
    private Object preCellValue;
    
    private String nowrap;
    
    private boolean isFirstColumn;
    private boolean isLastColumn;

	private String onclick;
	private String ondblclick;
	private String onmouseover;
	private String onmouseout;
	private int headerSpan=-1;
	private int calcSpan;
	
	private String cellValue;
//	private String cellName;
//	private String cellEdit;

	private Boolean ellipsis=null;
	
    private String editTemplate=null;
    private String editEvent=null;
    private Boolean editable;
    
    private String group;
    
    private int rowspan;
    
//    private CellExpression calcExpression;
    

    
//    private Object[] expressionPropertys;
    
    
    
    public Column(TableModel model) {
    	 setModel(model);
    	 rowspan=1;
    }
    

    
    public void defaults() {
        this.cell = ColumnDefaults.getCell(model, cell); //must be first
        
        this.alias = ColumnDefaults.getAlias(alias, property);
        this.calcTitle = ColumnDefaults.getCalcTitle(model, calcTitle);
        this.escapeAutoFormat = ColumnDefaults.isEscapeAutoFormat(model, escapeAutoFormat);
        this.format = ColumnDefaults.getFormat(model, this, format);
        this.filterable = ColumnDefaults.isFilterable(model, filterable);
        this.filterCell = ColumnDefaults.getFilterCell(model, filterCell);
        this.filterOptions = ColumnDefaults.getFilterOptions(model, filterOptions);
        this.headerCell = ColumnDefaults.getHeaderCell(model, headerCell);
        this.headerClass = ColumnDefaults.getHeaderClass(model, headerClass);
        this.parse = ColumnDefaults.getParse(model, this, parse);
        this.sortable = ColumnDefaults.isSortable(model, sortable);
        this.title = ColumnDefaults.getTitle(model, title, alias);
        
        this.editEvent = TableProperties.getDefaultPreference(model, "column.editEvent", editEvent);
        this.editTemplate = TableProperties.getDefaultPreference(model, "column.editTemplate", editTemplate);
        this.editable = ColumnDefaults.isEditable(model, editable);
        
        this.resizeColWidth = ColumnDefaults.resizeColWidth(model, resizeColWidth);
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
    
    public boolean isCalculated() {
        return (calc != null && calc.length > 0);
    }

    public String[] getCalc() {
        return calc;
    }

    public void setCalc(String calc) {
        if (calc != null) {
            this.calc = StringUtils.split(calc, ",");
        }
    }

    public String[] getCalcTitle() {
        return calcTitle;
    }

    public void setCalcTitle(String calcTitle) {
        if (calcTitle != null) {
            this.calcTitle = StringUtils.split(calcTitle, ",");
        }
    }

    public String getCell() {
        return cell;
    }

    public void setCell(String cell) {
        this.cell = cell;
    }

    public String getCellDisplay() {
        return cellDisplay;
    }

    public void setCellDisplay(String cellDisplay) {
        this.cellDisplay = cellDisplay;
    }
    
    public Collection getFilterOptions() {
		return (Collection)filterOptions;
	}

	public void setFilterOptions(Object filterOptions) {
		this.filterOptions = filterOptions;
	}
	
    public boolean isEscapeAutoFormat() {
        return escapeAutoFormat.booleanValue();
    }

    public void setEscapeAutoFormat(Boolean escapeAutoFormat) {
        this.escapeAutoFormat = escapeAutoFormat;
    }

    public boolean isFilterable() {
        return filterable.booleanValue();
    }

    public void setFilterable(Boolean filterable) {
        this.filterable = filterable;
    }

    public String getFilterCell() {
        return filterCell;
    }

    public void setFilterCell(String filterCell) {
        this.filterCell = filterCell;
    }

    public String getFilterClass() {
        return filterClass;
    }

    public void setFilterClass(String filterClass) {
        this.filterClass = filterClass;
    }

    public String getFilterStyle() {
        return filterStyle;
    }

    public void setFilterStyle(String filterStyle) {
        this.filterStyle = filterStyle;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getHeaderCell() {
        return headerCell;
    }

    public void setHeaderCell(String headerCell) {
        this.headerCell = headerCell;
    }

    public String getHeaderClass() {
        return headerClass;
    }

    public void setHeaderClass(String headerClass) {
        this.headerClass = headerClass;
    }

    public String getHeaderStyle() {
        return headerStyle;
    }

    public void setHeaderStyle(String headerStyle) {
        this.headerStyle = headerStyle;
    }

    public String getInterceptor() {
        return interceptor;
    }

    public void setInterceptor(String interceptor) {
        this.interceptor = interceptor;
    }

    public String getParse() {
        return parse;
    }

    public void setParse(String parse) {
        this.parse = parse;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public Object getPropertyValue() {
        return propertyValue;
    }

    public String getPropertyValueAsString() {
        Object value = getPropertyValue();
        if (value != null) {
            return String.valueOf(value);
        }

        return "";
    }

    public void setPropertyValue(Object propertyValue) {
        this.propertyValue = propertyValue;
    }

    public boolean isSortable() {
        return sortable.booleanValue();
    }

    public void setSortable(Boolean sortable) {
        this.sortable = sortable;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getStyleClass() {
        return styleClass;
    }

    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }


    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Object getValue() {
        return value;
    }

    public String getValueAsString() {
        Object value = getValue();
        if (value != null) {
            return String.valueOf(value);
        }

        return "";
    }

    public void setValue(Object value) {
        this.value = value;
    }

    
    public String[] getViewsAllowed() {
        return viewsAllowed;
    }

    public void setViewsAllowed(String viewsAllowed) {
        if (viewsAllowed != null) {
            this.viewsAllowed = StringUtils.split(viewsAllowed, ",");
        }
    }

    public String[] getViewsDenied() {
        return viewsDenied;
    }

    public void setViewsDenied(String viewsDenied) {
        if (viewsDenied != null) {
            this.viewsDenied = StringUtils.split(viewsDenied, ",");
        }
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }
    
    public boolean isDate() {
        if (StringUtils.isNotEmpty(getCell()) && getCell().equals(model.getPreferences().getPreference(PreferencesConstants.COLUMN_CELL + TableConstants.DATE))) {
            return true;
        }

        return false;
    }

    public boolean isCurrency() {
        if (StringUtils.isNotBlank(getCell()) && (getCell().equalsIgnoreCase(model.getPreferences().getPreference(PreferencesConstants.COLUMN_CELL + TableConstants.CURRENCY)))) {
            return true;
        }

        return false;
    }

    public boolean isFirstColumn() {
        return isFirstColumn;
    }

    public void setFirstColumn(boolean isFirstColumn) {
        this.isFirstColumn = isFirstColumn;
    }

    public boolean isLastColumn() {
        return isLastColumn;
    }

    public void setLastColumn(boolean isLastColumn) {
        this.isLastColumn = isLastColumn;
    }


	
	public int getHeaderSpan() {
		return headerSpan;
	}

	public void setHeaderSpan(int headerSpan) {
		this.headerSpan = headerSpan;
	}

	public String getOnclick() {
		return onclick;
	}

	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}

	public String getOndblclick() {
		return ondblclick;
	}

	public void setOndblclick(String ondblclick) {
		this.ondblclick = ondblclick;
	}

	public int getCalcSpan() {
		return calcSpan;
	}

	public void setCalcSpan(int calcSpan) {
		this.calcSpan = calcSpan;
	}

	public Boolean getEllipsis() {
		return ellipsis;
	}

	public void setEllipsis(Boolean ellipsis) {
		this.ellipsis = ellipsis;
	}

	public String getOnmouseout() {
		return onmouseout;
	}

	public void setOnmouseout(String onmouseout) {
		this.onmouseout = onmouseout;
	}

	public String getOnmouseover() {
		return onmouseover;
	}

	public void setOnmouseover(String onmouseover) {
		this.onmouseover = onmouseover;
	}


	public String getCellValue() {
		return cellValue;
	}

	public void setCellValue(String cellValue) {
		this.cellValue = cellValue;
	}

//	public String getCellName() {
//		return cellName;
//	}

//	public void setCellName(String cellName) {
//		this.cellName = cellName;
//	}

	public int getMinWidth() {
		return minWidth;
	}

	public void setMinWidth(int minWidth) {
		this.minWidth = minWidth;
	}


	public String getHeaderStyleClass() {
		return headerStyleClass;
	}

	public void setHeaderStyleClass(String headerStyleClass) {
		this.headerStyleClass = headerStyleClass;
	}



	public String getEditTemplate() {
		return editTemplate;
	}

	public void setEditTemplate(String editTemplate) {
		this.editTemplate = editTemplate;
	}

	public String getEditEvent() {
		return editEvent;
	}

	public void setEditEvent(String editEvent) {
		this.editEvent = editEvent;
	}

	public String getNowrap() {
		return nowrap;
	}

	public void setNowrap(String nowrap) {
		this.nowrap = nowrap;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}


	public Boolean getResizeColWidth() {
		return resizeColWidth;
	}
	public boolean isResizeColWidth() {
		return resizeColWidth==null?false:resizeColWidth.booleanValue();
	}
	public void setResizeColWidth(Boolean resizeColWidth) {
		this.resizeColWidth = resizeColWidth;
	}

	public int getRowspan() {
		return rowspan;
	}

	public void setRowspan(int rowspan) {
		this.rowspan = rowspan;
	}
	
    public void addStyle(String style) {
    	if (StringUtils.isNotBlank(getStyle()) ){
    		setStyle(getStyle()+";"+style);
    	}else{
    		setStyle(style);
    	}
    }
    public void addStyleClass(String styleClass) {
    	if (StringUtils.isNotBlank(getStyleClass()) ){
    		setStyleClass(getStyleClass()+" "+styleClass);
    	}else{
    		setStyleClass(styleClass);
    	}
    }

//	public CalcExpression getCalcExpression() {
//		return calcExpression;
//	}
//
//	public void setCalcExpression(CalcExpression calcExpression) {
//		this.calcExpression = calcExpression;
//	}
//
//	public Object[] getExpressionPropertys() {
//		return expressionPropertys;
//	}
//
//	public void setExpressionPropertys(Object[] expressionPropertys) {
//		this.expressionPropertys = expressionPropertys;
//	}

	public Object getPreCellValue() {
		return preCellValue;
	}

	public void setPreCellValue(Object preCellValue) {
		this.preCellValue = preCellValue;
	}

	public Boolean getEditable() {
		return editable;
	}

	public void setEditable(Boolean editable) {
		this.editable = editable;
	}
	
	public boolean isEditable(){
		return editable==null?false:editable.booleanValue();
	}

	public String getTipTitle() {
		return tipTitle;
	}

	public void setTipTitle(String tipTitle) {
		this.tipTitle = tipTitle;
	}
}
