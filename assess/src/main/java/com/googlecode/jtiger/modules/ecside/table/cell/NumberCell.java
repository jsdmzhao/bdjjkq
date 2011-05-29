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
package com.googlecode.jtiger.modules.ecside.table.cell;

import java.util.Locale;

import org.apache.commons.lang.StringUtils;

import com.googlecode.jtiger.modules.ecside.core.TableModel;
import com.googlecode.jtiger.modules.ecside.core.bean.Column;
import com.googlecode.jtiger.modules.ecside.util.ExtremeUtils;

/**
 * Visually represents a column that will be displayed with a custom number
 * format.
 * 
 * @author Jeff Johnston
 */
public class NumberCell extends AbstractCell {
    protected String getCellValue(TableModel model, Column column) {
        String value = column.getPropertyValueAsString();
        if (StringUtils.isNotBlank(value)) {
            Locale locale = model.getLocale();
            value = ExtremeUtils.formatNumber(column.getFormat(), value, locale);
        }

        return value;
    }
}
