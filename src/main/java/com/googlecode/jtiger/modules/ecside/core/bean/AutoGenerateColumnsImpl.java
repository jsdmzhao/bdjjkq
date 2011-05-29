package com.googlecode.jtiger.modules.ecside.core.bean;

import org.apache.commons.lang.StringUtils;

import com.googlecode.jtiger.modules.ecside.core.AutoGenerateColumns;
import com.googlecode.jtiger.modules.ecside.core.ECSideConstants;
import com.googlecode.jtiger.modules.ecside.core.TableModel;

public class AutoGenerateColumnsImpl implements AutoGenerateColumns {   
  
    public void addColumns(TableModel model) {   
    	String[] fieldnames = (String[]) model.getContext().getRequestAttribute(ECSideConstants.TABLE_FILEDS_KEY);
    	String[] fieldtitles = (String[])model.getTable().getAttribute(ECSideConstants.TABLE_TITLES_KEY); 
    	String[] fieldwidths = (String[])model.getTable().getAttribute(ECSideConstants.TABLE_WIDTHS_KEY);
        
    	if (fieldtitles==null||fieldtitles.length<1){
    		fieldtitles = (String[]) model.getContext().getRequestAttribute(ECSideConstants.TABLE_TITLES_KEY);
    	}
    	if (fieldtitles==null||fieldtitles.length<1){
        	fieldtitles = fieldnames;
        }    

    	String[] cellValues = (String[])model.getTable().getAttribute(ECSideConstants.TABLE_CELL_VALUES_KEY);   
//    	String[] cellNames = (String[])model.getTable().getAttribute(ECSideConstants.TABLE_CELLNAMES_KEY);   
    	String[] editEvents = (String[])model.getTable().getAttribute(ECSideConstants.TABLE_EDIT_EVENTS_KEY);   
    	String[] editTemplates = (String[])model.getTable().getAttribute(ECSideConstants.TABLE_EDIT_TEMPLATES_KEY);
    	String[] editables=(String[])model.getTable().getAttribute(ECSideConstants.TABLE_EDITABLES_KEY);
    	
    	for (int i=0;i<fieldnames.length;i++)  { 
            String fieldname = fieldnames[i];   
            Column column = model.getColumnInstance();   
            column.setProperty(fieldname); 
            column.setTitle(fieldtitles[i].trim());

            if (fieldwidths!=null && i<fieldwidths.length && StringUtils.isNotBlank(fieldwidths[i]) ){
            	column.setWidth(fieldwidths[i].trim());
            }
            
            if (cellValues!=null && i<cellValues.length && StringUtils.isNotBlank(cellValues[i]) ){
            	column.setCellValue(cellValues[i].trim());
            }
//            if (cellNames!=null && i<cellNames.length && StringUtils.isNotBlank(cellNames[i]) ){
//            	column.setCellName(cellNames[i].trim());
//            }

            if (editEvents!=null && i<editEvents.length && StringUtils.isNotBlank(editEvents[i]) ){
            	column.setEditEvent(editEvents[i].trim());
            }
            if (editTemplates!=null && i<editTemplates.length && StringUtils.isNotBlank(editTemplates[i]) ){
            	column.setEditTemplate(editTemplates[i].trim());
            }
            if (editables!=null && i<editables.length && StringUtils.isNotBlank(editables[i]) ){
            	column.setEditable(Boolean.valueOf(editables[i].trim()));    
            }

            model.getColumnHandler().addAutoGenerateColumn(column); 
            
        }   
    } 
}
