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
package com.googlecode.jtiger.modules.ecside.resource;

import java.io.File;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.googlecode.jtiger.modules.ecside.common.log.LogHandler;

/**
 * @author phorn
 */
public class MimeUtils {
	
	private static Log logger = LogFactory.getLog(MimeUtils.class);
	
    public final static String MIMETYPES_PROPERTIES = "mimeTypes.properties";
    private static Properties properties;

    /**
     * Return the mime type for a file. The extension is parsed out and the
     * method to get mime type by extension is called. Extension mappings are
     * found in the mimeTypes.properties file in the org.extremecomponents.util
     * package.
     */
    public static String getFileMimeType(File file) {
        if (file == null) {
            return null;
        }

        return getFileMimeType(file.getName());
    }

    /**
     * Return the mime type for a file name. The extension is parsed out and the
     * method to get mime type by extension is called. Extension mappings are
     * found in the mimeTypes.properties file in the org.extremecomponents.util
     * package.
     */
    public static String getFileMimeType(String fileName) {
        if (StringUtils.isBlank(fileName) || (fileName.indexOf(".") == -1)) {
            return null;
        }

        fileName = fileName.substring(fileName.lastIndexOf("."));

        return getExtensionMimeType(fileName);
    }

    /**
     * Return the mime type for a given extension. Extensions can include the
     * dot or just be the extension without the dot. Extension mappings are
     * found in the mimeTypes.properties file in the org.extremecomponents.util
     * package.
     */
    public static String getExtensionMimeType(String extension) {
        String result = null;

        if (StringUtils.isBlank(extension)) {
            return result;
        }

        init();

        extension = extension.toLowerCase();

        if (!extension.startsWith(".")) {
            extension = "." + extension;
        }

        result = (String) properties.get(extension);

        return result;
    }

    private static void init() {
        if (properties != null) {
            return;
        }

        try {
            properties = new Properties();
            properties.load(new MimeUtils().getClass().getResourceAsStream(MIMETYPES_PROPERTIES));
        } catch (Exception e) {
			LogHandler.errorLog(logger,e);	
        }
    }

    /**
     * Just a test harness.
     */
    public static void main(String[] args) {
        System.out.println("MimeUtils.getExtensionMimeType(.gif)=" + MimeUtils.getExtensionMimeType(".gif"));
        System.out.println("MimeUtils.getExtensionMimeType(.pdf)=" + MimeUtils.getExtensionMimeType(".pdf"));
        System.out.println("MimeUtils.getExtensionMimeType(.xls)=" + MimeUtils.getExtensionMimeType(".xls"));
        System.out.println("MimeUtils.getFileMimeType(foo.gif)=" + MimeUtils.getFileMimeType("foo.gif"));
        System.out.println("MimeUtils.getFileMimeType(foo.pdf)=" + MimeUtils.getFileMimeType("foo.pdf"));
        System.out.println("MimeUtils.getFileMimeType(foo.xls)=" + MimeUtils.getFileMimeType("foo.xls"));
        System.out.println("MimeUtils.getFileMimeType(foo.badextension)=" + MimeUtils.getFileMimeType("foo.badextension"));
    }
}
