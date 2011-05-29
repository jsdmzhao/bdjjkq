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
package com.googlecode.jtiger.modules.ecside.table.cell;

import com.googlecode.jtiger.modules.ecside.core.TableModel;
import com.googlecode.jtiger.modules.ecside.core.TableModelUtils;
import com.googlecode.jtiger.modules.ecside.core.bean.Column;
import com.googlecode.jtiger.modules.ecside.util.ECSideUtils;
import com.googlecode.jtiger.modules.ecside.view.html.ColumnBuilder;


/**
 * @author Wei Zijun
 *
 */

public abstract class AbstractRowCalcCell implements Cell  {
	
    public String getHtmlDisplay(TableModel model, Column column) {
    	ColumnBuilder columnBuilder = new ColumnBuilder(column);
        columnBuilder.tdStart();
        columnBuilder.tdBody(getCellValue(model, column));
        columnBuilder.tdEnd();
        return columnBuilder.toString();
    }
    
    public String getExportDisplay(TableModel model, Column column) {
        return column.getPropertyValueAsString();
    }

    
    public static String getPropertyStringValue(Object rowBean,String propertyName){
    	Object propertyValue = TableModelUtils.getColumnPropertyValue(rowBean, propertyName);
    	return ECSideUtils.convertString(propertyValue, "");
    }
    
    public static double getPropertyNumberValue(Object rowBean,String propertyName){
    	Object propertyValue = TableModelUtils.getColumnPropertyValue(rowBean, propertyName);
    	
    	double numberValue=0.0;
    	
    	if (propertyValue==null) { return numberValue;}
    	try {
    	   	numberValue=Double.valueOf(propertyValue.toString()).doubleValue();
		} catch (Exception e) {
			numberValue=0.0;
		}
		return numberValue;
    	
    }
    
    public abstract String getCellValue(TableModel model, Column column) ;
}
