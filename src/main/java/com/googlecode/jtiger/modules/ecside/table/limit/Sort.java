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

/**
 * @author Jeff Johnston
 */

@SuppressWarnings("unchecked")
public final class Sort {
    private final String alias;
    private final String property;
    private final String sortOrder;

    public Sort() {
        this.alias = null;
        this.property = null;
        this.sortOrder = null;
    }

    public Sort(String alias, String property, String sortOrder) {
        this.alias = alias;
        this.property = property;
        this.sortOrder = sortOrder;
    }

    public String getAlias() {
        return alias;
    }

    public String getProperty() {
        return property;
    }

    public String getSortOrder() {
        return sortOrder;
    }


	public Map getSortValueMap(){
		String sortProperty = getProperty();
		String sortOrder = getSortOrder();
		
		Map sortValueMap = new HashMap();
		if (sortProperty!=null){
			sortValueMap.put(sortProperty, sortOrder);
		}
		return sortValueMap;
		
	}
    
    public boolean isSorted() {
        return sortOrder != null;
    }
    
    public boolean isAliased() {
        return !alias.equals(property);
    }
    
    public String toString() {
        ToStringBuilder builder = new ToStringBuilder(this);
        builder.append("alias", alias);
        builder.append("property", property);
        builder.append("sortOrder", sortOrder);
        return builder.toString();
    }
}