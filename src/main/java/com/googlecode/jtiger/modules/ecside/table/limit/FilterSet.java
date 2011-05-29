/*
 * Copyright 2004 original author or authors.
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
package com.googlecode.jtiger.modules.ecside.table.limit;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.googlecode.jtiger.modules.ecside.core.TableConstants;

/**
 * @author Jeff Johnston & Wei Zijun
 */

@SuppressWarnings("unchecked")
public class FilterSet {
    private String action;
    private Filter[] filters;
    
    private Map propertyValueMap;
    private Map aliasValueMap;
    
    public FilterSet() {
    }

    public FilterSet(String action, Filter filters[]) {
        this.action = action;
        this.filters = filters;
        
 	   if (filters.length>0){ 
 		  propertyValueMap = new HashMap();
 		  aliasValueMap = new HashMap();
 	        for (int i = 0; i < filters.length; i++) {
 	            aliasValueMap.put(filters[i].getAlias(), filters[i].getValue());
 	            propertyValueMap.put(filters[i].getProperty(), filters[i].getValue());
 	        }
 		}
    }

    public boolean isFiltered() {
        return (action != null && action.equals(TableConstants.FILTER_ACTION) && filters != null && filters.length > 0);
    }

    public boolean isCleared() {
        return action != null && action.equals(TableConstants.CLEAR_ACTION);
    }

    public String getAction() {
        return action;
    }
    
    public Filter[] getFilters() {
        return filters;
    }

    /**
     * For a given filter, referenced by the alias, retrieve the value. 
     * 
     * @param alias The Filter alias
     * @return The Filter value
     */
    public String getFilterValue(String alias) {
        for (int i = 0; i < filters.length; i++) {
            Filter filter = filters[i];
            if (filter.getAlias().equals(alias)) {
                return filter.getValue();
            }
        }

        return "";
    }
    
    /**
     * For a given filter, referenced by the alias, retrieve the Filter. 
     * 
     * @param alias The Filter alias
     * @return The Filter value
     */
    public Filter getFilter(String alias) {
        for (int i = 0; i < filters.length; i++) {
            Filter filter = filters[i];
            if (filter.getAlias().equals(alias)) {
                return filter;
            }
        }

        return null;
    }
    
    public String toString() {
        ToStringBuilder builder = new ToStringBuilder(this);
        builder.append("action", action);
        
        if (filters != null) {
            for (int i = 0; i < filters.length; i++) {
                Filter filter = filters[i];
                builder.append(filter.toString());
            }
        }
        
        return builder.toString();
    }


    
	public Map getAliasValueMap() {
		return aliasValueMap;
	}

	public Map getPropertyValueMap() {
		return propertyValueMap;
	}
}
