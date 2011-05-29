/*
 * Copyright 2006-2007 original author or authors.
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
package com.googlecode.jtiger.modules.ecside.common;

import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang.StringUtils;


/**
 * @author Wei Zijun
 *
 */

@SuppressWarnings("unchecked")
public class HTMLOptionsUtil {
	public static String getOptionsList(Map imap){
		return HTMLOptionsUtil.getOptionsList(imap,null);
	}
	public static String getOptionsList(Map imap,Object defaultKey){
		return HTMLOptionsUtil.getOptionsList(imap,defaultKey,null);
	}
	
	//LinkedHashMap is good
	public static String getOptionsList(Map imap,Object defaultKey ,String otherAttribute){
		StringBuffer rs=new StringBuffer();
		Iterator itor=imap.keySet().iterator();
		
		while (itor.hasNext()){
//			String key=(String)itor.next();
//			String value=(String)imap.get(key);
			String key=String.valueOf(itor.next()); 
			
			String value=convertString(imap.get(key),""); 
			
			String selected="";
			if (key.equals(defaultKey)){
				selected="selected=\"selected\"";
			}
			otherAttribute=StringUtils.isBlank(otherAttribute)?"":" "+otherAttribute+" ";

			rs.append("<option value=\"").append(key).append("\" ").append(selected).append(otherAttribute).append(" >")
				.append(value).append("</option>\n");
		}
		
		return rs.toString();
	}
	public static String convertString(Object obj,String nullTo){
		return obj==null?nullTo:obj.toString();
	}
	
}
