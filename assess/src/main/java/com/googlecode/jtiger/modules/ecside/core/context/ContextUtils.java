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
package com.googlecode.jtiger.modules.ecside.core.context;

import java.io.OutputStream;
import java.util.List;


/**
 * @since 2.0
 * @author Jeff Johnston
 */

@SuppressWarnings("unchecked")
public class ContextUtils {
    /**
     * The value needs to be a String[]. A String, Null, or List will be
     * converted to a String[]. In addition it will attempt to do a String
     * conversion for other object types.
     * 
     * @param value
     *            The value to convert to an String[]
     * @return A String[] value.
     */
    public static String[] getValueAsArray(Object value) {
        if (value == null) {
            return new String[] {}; // put in a placeholder
        }

        if (value instanceof String[]) {
            return (String[]) value;
        } else if (value instanceof List) {
            List valueList = (List) value;
            return (String[]) valueList.toArray(new String[valueList.size()]);
        }

        return new String[] { value.toString() };
    }
    
    public static final String RESPONSE_OUTPUTSTREAM_KEY="RESPONSE_OUTPUTSTREAM_KEY";
	public static OutputStream getResponseOutputStream(Context context) {
		return (OutputStream)context.getRequestAttribute(RESPONSE_OUTPUTSTREAM_KEY);
	}

}
