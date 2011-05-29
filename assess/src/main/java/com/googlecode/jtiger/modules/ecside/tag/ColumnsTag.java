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
package com.googlecode.jtiger.modules.ecside.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.googlecode.jtiger.modules.ecside.common.log.LogHandler;
import com.googlecode.jtiger.modules.ecside.core.ECSideConstants;
import com.googlecode.jtiger.modules.ecside.core.TableConstants;
import com.googlecode.jtiger.modules.ecside.core.TableModel;
import com.googlecode.jtiger.modules.ecside.util.ExceptionUtils;


/**
 * @author Wei Zijun
 *
 */

/**
 * @jsp.tag name="columns" display-name="ColumnsTag" body-content="JSP"
 *          description="Auto generate the columns."
 */

public class ColumnsTag extends TagSupport {

	private static final long serialVersionUID = 1L;

	private Log logger = LogFactory.getLog(ColumnsTag.class);

    private String autoGenerateColumns;
    
    private String titles;
    
    private String cellValues;
    
    private String cellNames;
    
    private String editEvents;
    
    private String editTemplates;
    
    private String editables;
    
    private String widths;

    /**
     * @jsp.attribute description="A fully qualified class name to a custom
     *                AutoGenerateColumns implementation. Could also be a named
     *                type in the preferences. Used to generate columns on the
     *                fly." required="true" rtexprvalue="true"
     */
    public void setAutoGenerateColumns(String autoGenerateColumns) {
        this.autoGenerateColumns = autoGenerateColumns;
    }

    public int doEndTag() throws JspException {
        try {
            TableModel model = TagUtils.getModel(this);

            if (!TagUtils.isIteratingBody(this)) {
                if (StringUtils.isNotBlank(titles)){
                	String[] titlesA=(" "+titles+" ").split(",");
                    model.getTable().setAttribute(ECSideConstants.TABLE_TITLES_KEY,titlesA);
                }
                
                if (StringUtils.isNotBlank(widths)){
                	String[] cellWidthsA=(" "+widths+" ").split(",");
                    model.getTable().setAttribute(ECSideConstants.TABLE_WIDTHS_KEY,cellWidthsA);
                }
                
                if (StringUtils.isNotBlank(cellValues)){
                	String[] cellValuesA=(" "+cellValues+" ").split(",");
                    model.getTable().setAttribute(ECSideConstants.TABLE_CELL_VALUES_KEY,cellValuesA);
                }
                
//                if (StringUtils.isNotBlank(cellNames)){
//                	String[] cellNamesA=(" "+cellNames+" ").split(",");
//                    model.getTable().setAttribute(ECSideConstants.TABLE_CELLNAMES_KEY,cellNamesA);
//                }
                
                if (StringUtils.isNotBlank(editEvents)){
                	String[] cellEventsA=(" "+editEvents+" ").split(",");
                    model.getTable().setAttribute(ECSideConstants.TABLE_EDIT_EVENTS_KEY,cellEventsA);

                }

                if (StringUtils.isNotBlank(editTemplates)){
                	String[] cellTemplatesA=(" "+editTemplates+" ").split(",");
                    model.getTable().setAttribute(ECSideConstants.TABLE_EDIT_TEMPLATES_KEY,cellTemplatesA);

                }
                if (StringUtils.isNotBlank(editables)){
                	String[] cellEditablesA=(" "+editables+" ").split(",");
                    model.getTable().setAttribute(ECSideConstants.TABLE_EDITABLES_KEY,cellEditablesA);

                }
                
                
                
            	String autoGenerateColumns = TagUtils.evaluateExpressionAsString("autoGenerateColumns", this.autoGenerateColumns, this, pageContext);
                if (StringUtils.isBlank(autoGenerateColumns)){
                	
                	autoGenerateColumns=TableConstants.DEFAULT_AUTOGENERATECOLUMNS;
                }
            	
            	model.addColumns(autoGenerateColumns);


            } else {
                model.setColumnValues();
            }

            
        } catch (Exception e) {
        	LogHandler.errorLog(logger, e);
            throw new JspException("ColumnsTag.doEndTag() Problem: " + ExceptionUtils.formatStackTrace(e));
        }finally{
        	
        }
        return EVAL_PAGE;
    }

    public void release() {
        autoGenerateColumns = null;
        titles=null;
        cellValues=null;
        cellNames=null;
        editEvents=null;
        editTemplates=null;
        editables=null;
        widths=null;
        super.release();
    }

	public String getTitles() {
		return titles;
	}

	public void setTitles(String titles) {
		this.titles = titles;
	}


	public String getEditEvents() {
		return editEvents;
	}

	public void setEditEvents(String editEvents) {
		this.editEvents = editEvents;
	}

	public String getEditTemplates() {
		return editTemplates;
	}

	public void setEditTemplates(String editTemplates) {
		this.editTemplates = editTemplates;
	}

	public String getCellNames() {
		return cellNames;
	}

	public void setCellNames(String cellNames) {
		this.cellNames = cellNames;
	}

	public String getCellValues() {
		return cellValues;
	}

	public void setCellValues(String cellValues) {
		this.cellValues = cellValues;
	}

	public String getWidths() {
		return widths;
	}

	public void setWidths(String widths) {
		this.widths = widths;
	}

	public String getEditables() {
		return editables;
	}

	public void setEditables(String editables) {
		this.editables = editables;
	}
}
