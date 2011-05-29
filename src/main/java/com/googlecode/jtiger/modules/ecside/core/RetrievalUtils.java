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
package com.googlecode.jtiger.modules.ecside.core;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.googlecode.jtiger.modules.ecside.core.context.WebContext;

/**
 * @author Jeff Johnston
 */

@SuppressWarnings("unchecked")
public class RetrievalUtils {
    private static Log logger = LogFactory.getLog(RetrievalUtils.class);

    /**
     * @see #retrieve(WebContext, Object, String)
     */
    public static Object retrieve(WebContext context, String name) {
        return retrieve(context, name, null);
    }

    /**
     * Look in the specified servlet scope for the Object. If the scope is null
     * look through the scopes in order (page, request, session, and
     * application).
     */
    public static Object retrieve(WebContext context, String name, String scope) {
        if (StringUtils.isNotBlank(scope)) {
            if (scope.equalsIgnoreCase(TableConstants.PAGE_SCOPE)) {
                return context.getPageAttribute(name);
            } else if (scope.equalsIgnoreCase(TableConstants.REQUEST_SCOPE)) {
                return context.getRequestAttribute(name);
            } else if (scope.equalsIgnoreCase(TableConstants.SESSION_SCOPE)) {
                return context.getSessionAttribute(name);
            } else if (scope.equalsIgnoreCase(TableConstants.APPLICATION_SCOPE)) {
                return context.getApplicationAttribute(name);
            }
        }

        Object value = context.getPageAttribute(name);
        if (value == null) {
            value = context.getRequestAttribute(name);
        }
        if (value == null) {
            value = context.getSessionAttribute(name);
        }
        if (value == null) {
            value = context.getApplicationAttribute(name);
        }

        return value;
    }

    /**
     * @see #retrieveCollection(WebContext, Object, String)
     */
    public static Collection retrieveCollection(WebContext context, Object collection)
            throws Exception {
        return retrieveCollection(context, collection, null);
    }

    /**
     * If the collection variable passed in is in fact a Collection then just
     * return it. If it is a Map then return the Map values.
     * 
     * If it is a String then look in the specified servlet scope for the
     * Collection. If the scope is null look through the scopes in order (page,
     * request, session, and application).
     * 
     * If the collection variable is specified with a dot (.) notation then will
     * look for a nested collection. For example foo.values will first look for
     * an object called foo in the various servlet scopes (as described above).
     * Once it finds it then it will look for an attribute called values, which
     * is assumed to be a Collection.
     * 
     * @param collection Either a String or Object that will represent the Collection.
     * @return A Collection.
     */
    public static Collection retrieveCollection(WebContext context, Object collection, String scope)
            throws Exception {
        if (collection instanceof Collection) {
            return (Collection) collection;
        } else if (collection instanceof Map) {
            return ((Map) collection).values();
        } else if (collection instanceof String) {
            return retrieveCollectionFromScope(context, String.valueOf(collection), scope);
        } else {
            if (logger.isDebugEnabled()) {
                logger.debug("Could not find the Collection.");
            }
            return Collections.EMPTY_LIST;
        }
    }

    static Collection retrieveCollectionFromScope(WebContext context, String collection, String scope)
            throws Exception {
        Collection results = null;

        if (StringUtils.isBlank(collection) || "null".equals(collection)) {
            if (logger.isDebugEnabled()) {
                logger.debug("The collection is not defined.");
            }

            return Collections.EMPTY_LIST;
        }

        if (StringUtils.contains(collection, ".")) {
            results = retrieveNestedCollection(context, collection, scope);
        } else {
            results = retrieveCollectionAsObject(context, collection, scope);
        }

        if (results == null) {
            if (logger.isDebugEnabled()) {
                logger.debug("Could not find the Collection.");
            }

            return Collections.EMPTY_LIST;
        }

        return results;
    }

    static Collection retrieveNestedCollection(WebContext context, String collection, String scope)
            throws Exception {
        String split[] = StringUtils.split(collection, ".");
        Object obj = RetrievalUtils.retrieve(context, split[0], scope);
        String collectionToFind = StringUtils.substringAfter(collection, ".");
        
        Object value=null;
        try {
			// if (ExtremeUtils.isBeanPropertyReadable(bean, property)) {
			value = PropertyUtils.getProperty(obj, collectionToFind);
			obj = value;
			// }
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("Could not find the property [" + collectionToFind
						+ "]. Either the bean or property is null");
			}
		}
              
        if (!(obj instanceof Collection)) {
            if (logger.isDebugEnabled()) {
                logger.debug("The object is not of type Collection.");
            }

            return Collections.EMPTY_LIST;
        }

        return (Collection) obj;
    }

    static Collection retrieveCollectionAsObject(WebContext context, String collection, String scope)
            throws Exception {
        Object obj = RetrievalUtils.retrieve(context, collection, scope);
        if (obj instanceof Collection) {
            return (Collection) obj;
        } else if (obj instanceof Map) {
            return ((Map) obj).values();
        } else {
            if (logger.isDebugEnabled()) {
                logger.debug("The object is not of type Collection.");
            }

            return Collections.EMPTY_LIST;
        }
    }
}
