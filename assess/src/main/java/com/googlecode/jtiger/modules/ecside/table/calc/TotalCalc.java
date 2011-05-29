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
package com.googlecode.jtiger.modules.ecside.table.calc;

import java.math.BigDecimal;
import java.util.Collection;

import com.googlecode.jtiger.modules.ecside.core.TableModel;
import com.googlecode.jtiger.modules.ecside.core.bean.Column;

/**
 * @author Jeff Johnston
 */

@SuppressWarnings("unchecked")
public class TotalCalc implements Calc {
    public Number getCalcResult(TableModel model, Column column) {
        Collection rows = model.getCollectionOfFilteredBeans();
        String property = column.getProperty();
        TotalValue totalValue = new TotalValue();
        CalcUtils.eachRowCalcValue(totalValue, rows, property);

        return totalValue.getTotalValue();
    }
    
    private static class TotalValue implements CalcHandler {
        double total = 0.00;
        
        public void processCalcValue(Number calcValue) {
            total += calcValue.doubleValue();
        }
        
        public Number getTotalValue() {
            return new BigDecimal(total);
        }
    }
}
