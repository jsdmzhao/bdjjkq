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

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author Jeff Johnston
 */
public final class Filter {
    private final String alias;
    private final String property;
    private final String value;

    public Filter(String alias, String property, String value) {
        this.alias = alias;
        this.property = property;
        this.value = value;
    }

    public String getAlias() {
        return alias;
    }

    public String getProperty() {
        return property;
    }

    public String getValue() {
        return value;
    }
    
    public boolean isAliased() {
        return !alias.equals(property);
    }
    
    public String toString() {
        ToStringBuilder builder = new ToStringBuilder(this);
        builder.append("alias", alias);
        builder.append("property", property);
        builder.append("value", value);
        return builder.toString();
    }
}
