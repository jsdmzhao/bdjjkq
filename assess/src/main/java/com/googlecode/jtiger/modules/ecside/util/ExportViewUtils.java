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
package com.googlecode.jtiger.modules.ecside.util;

import org.apache.commons.lang.StringUtils;

/**
 * org.extremecomponents.base.ViewUtils - Utility methods for parsing and
 * replacing characters and Strings.
 * 
 * @author phorn
 */
public class ExportViewUtils {
	
	public static final String BR = "\r\n";

	public static final char LF = '\n';
	
	public static final char CR = '\r';

	public static final char QUOTE = '"';

	public static final char COMMA = ',';

	public static final char SPACE = ' ';

	public static final char TAB = '\t';

	public static final char POUND = '#';

	public static final char BACKSLASH = '\\';

	public static final char NULL = '\0';
	
	public static final String SPACE_C1 ="&nbsp;";
	
	public static final String SPACE_C2 = "&#160;";
	
    public static String parseXLS(String value) {
        if (StringUtils.isBlank(value))
            return "";

        value = replaceNonBreakingSpaces(value);

        return value;
    }

    public static String parsePDF(String value) {
        if (StringUtils.isBlank(value))
            return "";

        value = replaceNonBreakingSpaces(value);
        value = escapeChars(value);

        return value;
    }


    
	public static String parseCSV(String content) {

		boolean needPrefix= true;
		boolean needTrim = true;
		boolean needQuote = false;
		
		if (content == null) {
			return needPrefix?String.valueOf(TAB):"";
		}
		content=needTrim?content.trim():content;
		
		if (content.indexOf("\r") != -1 || content.indexOf("\n") != -1
				|| content.indexOf(",") != -1) {
			needQuote = true;
		}
		
        content = StringUtils.replace(content, SPACE_C1, ""+SPACE);
        content = StringUtils.replace(content, SPACE_C2, ""+SPACE);
		content = StringUtils.replace(content, "" + QUOTE, ""+ QUOTE + QUOTE);
		content = StringUtils.replace(content, "" + CR + LF, ""	+ CR);
		content = StringUtils.replace(content, "" + LF, "" + CR);
		
		content=needPrefix?TAB+content:content;
		if (needQuote) {
			content = QUOTE + content + QUOTE;
		}
		return content;
	}

    public static String replaceNonBreakingSpaces(String value) {
        if (StringUtils.isBlank(value))
            return "";

        if (StringUtils.contains(value, "&nbsp;")) {
            value = StringUtils.replace(value, "&nbsp;", "");
        }

        return value;
    }

    public static String escapeChars(String value) {
        if (StringUtils.isBlank(value))
            return "";

        if (StringUtils.contains(value, "&")) {
            value = StringUtils.replace(value, "&", "&#38;");
        }

        if (StringUtils.contains(value, ">")) {
            value = StringUtils.replace(value, ">", "&gt;");
        }

        if (StringUtils.contains(value, "<")) {
            value = StringUtils.replace(value, "<", "&lt;");
        }

        return value;
    }
}
