/*
 * Copyright 2004 original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.googlecode.jtiger.modules.ecside.table.callback;

import java.util.Comparator;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.beanutils.NestedNullException;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Override BeanComparator to workaround the NestedNullException thrown by
 * PropertyUtils.getProperty(...) when a parent object in the hierarchy is null.
 * 
 * @author vbala
 * @version 1.0
 */

@SuppressWarnings("unchecked")
public class NullSafeBeanComparator extends BeanComparator {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Log log = LogFactory.getLog(NullSafeBeanComparator.class);

    protected boolean nullsAreHigh = true;

    protected String property;

    protected Comparator comparator;

    public Comparator getComparator() {
        return comparator;
    }

    public void setComparator(Comparator comparator) {
        this.comparator = comparator;
    }

    public boolean isNullsAreHigh() {
        return nullsAreHigh;
    }

    public void setNullsAreHigh(boolean nullsAreHigh) {
        this.nullsAreHigh = nullsAreHigh;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    /**
     * Compare beans safely. Handles NestedNullException thrown by PropertyUtils
     * when the parent object is null
     */
    public int compare(Object o1, Object o2) {

        if (property == null) {
            // use the object's compare since no property is specified
            return this.comparator.compare(o1, o2);
        }

        Object val1 = null, val2 = null;

        try {
            try {
                val1 = PropertyUtils.getProperty(o1, property);
            } catch (NestedNullException ignored) {
            }

            try {
                val2 = PropertyUtils.getProperty(o2, property);
            } catch (NestedNullException ignored) {
            }

            if (val1 == val2 || (val1 == null && val2 == null)) {
                return -1;
            }

            if (val1 == null) {
                return this.nullsAreHigh ? 1 : -1;
            }

            if (val2 == null) {
                return this.nullsAreHigh ? -1 : 1;
            }

            return this.comparator.compare(val1, val2);
        } catch (Exception e) {
            e.printStackTrace();
            log.warn(e);
            return 0;
        }
    }

    public NullSafeBeanComparator(String property, Comparator c) {
        this.comparator = c;
        this.property = property;
    }

    public NullSafeBeanComparator(String property, Comparator c, boolean nullAreHigh) {
        this.comparator = c;
        this.property = property;
        this.nullsAreHigh = nullAreHigh;

    }
}