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
package com.googlecode.jtiger.modules.ecside.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;

import com.googlecode.jtiger.modules.ecside.core.TableConstants;
import com.googlecode.jtiger.modules.ecside.core.TableModel;
import com.googlecode.jtiger.modules.ecside.core.bean.Tr;

/**
 * @author Wei Zijun
 * 
 */

@SuppressWarnings("unchecked")
public class ECSideUtils {


    public static String getApplicationName(String servletPath){
    	int first=servletPath.indexOf("/");
    	if (first<0) return "";
    	int second=servletPath.indexOf("/",first+1);
    	if (second<0) return "";
    	return servletPath.substring(first+1, second);
    	
    }
    
//	public static String encodeFileNameForMozilla(String fileName)
//			throws UnsupportedEncodingException {
//
//		return "=?UTF-8?B?"+(new String(Base64.encodeBase64(fileName.getBytes("UTF-8"))))+"?=";
//
//	}

	
	public static String encodeFileName(String fileName, String agent) throws UnsupportedEncodingException{
		String codedfilename = null;
		fileName=fileName.replaceAll("\n|\r", " ").trim();

		if (null != agent && -1 != agent.indexOf("MSIE")) {

			codedfilename = URLEncoder.encode(fileName, "UTF8");
//			String prefix = fileName.lastIndexOf(".") != -1 ? fileName
//				.substring(0, fileName.lastIndexOf(".")) : fileName;
//			String extension = fileName.lastIndexOf(".") != -1 ? fileName
//				.substring(fileName.lastIndexOf(".")) : "";
//			int limit = 150 - extension.length();
//			if (codedfilename.length() > limit) {
//				codedfilename = java.net.URLEncoder.encode(prefix.substring(0, Math.min(
//						prefix.length(), limit / 9)), "UTF-8");
//				codedfilename = codedfilename + extension;
//			}
		} else if (null != agent && -1 != agent.indexOf("Mozilla")) {
			codedfilename = "=?UTF-8?B?"+(new String(Base64.encodeBase64(fileName.getBytes("UTF-8"))))+"?=";
		} else {
			codedfilename = fileName;
		}
		return codedfilename;
	}

	
	public static String GBToISO(String gb) {
		try {
			return gb == null ? gb : new String(gb.getBytes("GB2312"),
					"ISO-8859-1");
		} catch (Exception e) {
			return gb;
		}
	}

	public static String ISOToGB(String iso) {
		try {
			return iso == null ? iso : new String(iso.getBytes("ISO-8859-1"),
					"GB2312");
		} catch (Exception e) {
			return iso;
		}
	}

	public static int parseInt(String val, int defaultValue) {
		int result = 0;
		try {
			result = Integer.parseInt(val);
		} catch (Exception e) {
			result = defaultValue;
		}
		return result;
	}

	public static int parseInt(String val) {
		int result = 0;
		try {
			result = Integer.parseInt(val);
		} catch (Exception e) {
		}
		return result;
	}

	public static long parseLong(String value, long defaultValue) {
		long result = 0;
		try {
			result = Long.parseLong(value);
		} catch (Exception ex) {
			result = defaultValue;
		}
		return result;
	}

	public static long parseLong(String value) {
		long result = 0;
		try {
			result = Long.parseLong(value);
		} catch (Exception ex) {
		}
		return result;
	}

	public static float parseFloat(String value) {
		float result = 0.0f;
		try {
			result = Float.parseFloat(value);
		} catch (Exception ex) {
		}
		return result;
	}

	public static double parseDouble(String value) {
		double result = 0.0;
		try {
			result = Double.parseDouble(value);
		} catch (Exception ex) {
		}
		return result;
	}

	public static String nullToBlank(String value) {
		return value == null ? "" : value;
	}

	public static Object nullToBlank(Object value) {
		return value == null ? "" : value;
	}

	public static String toNull(String value) {
		return (value == null || value.length() == 0) ? null : value;
	}

	public static String htmlConvert(String htmlStr) {
		if (htmlStr == null)
			return "";
		htmlStr = htmlStr.replaceAll("&", "&amp;");
		htmlStr = htmlStr.replaceAll(" ", "&nbsp;");
		htmlStr = htmlStr.replaceAll("\"", "&quot;");
		htmlStr = htmlStr.replaceAll("<", "&lt;");
		htmlStr = htmlStr.replaceAll(">", "&gt;");
		htmlStr = htmlStr.replaceAll("\r\n", "<br>");
		return htmlStr;
	}

	public static String toDate(String value) {
		return "to_date( '" + trimToDay(value) + "','yyyy-mm-dd' )";
	}

	public static String trimToDay(String value) {
		if (isEmpty(value))
			return "9999-01-01";
		int idx = value.indexOf(" ");
		return idx == -1 ? value : value.substring(0, idx);
	}

	public static String preventImmit(String tmpStr) {
		if (tmpStr == null)
			return "";
		tmpStr = tmpStr.replaceAll("'", "''");
		return tmpStr;
	}

	public static String getFormatNowDateTime(String formatStr) {
		Calendar nowtime = Calendar.getInstance();
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
				formatStr);
		return (String) sdf.format(nowtime.getTime());
	}

	public static String getNowDate() {
		return getFormatNowDateTime("yyyy-MM-dd");
	}

	public static String getNowTime() {
		return getFormatNowDateTime("HH:mm:ss");
	}

	public static String getNowDateTime() {
		return getFormatNowDateTime("yyyy-MM-dd HH:mm:ss");
	}

	public static boolean isEmpty(String str) {
		return str == null || str.equals("");
	}

	public static Object nullToNew(Object obj, Object objNew) {
		return obj == null ? objNew : obj;
	}

	public static synchronized long getSEQSN(Connection conn, String SEQName) {

		long seqSN = 0;
		long startPoint = 1125983190000L;

		try {
			String query = "select " + SEQName + ".nextval from dual";
			Statement stmt = conn.createStatement();
			ResultSet rest = stmt.executeQuery(query);
			if (rest.next()) {
				seqSN = rest.getInt(1);
			}
		} catch (Exception e) {
			long t1 = System.currentTimeMillis();
			while (t1 == System.currentTimeMillis()) {
				// Thread.sleep(1);
			}
			seqSN = System.currentTimeMillis() - startPoint;
		}
		return seqSN;
	}

	public static String GBToUTF(String ostr) {
		try {
			return ostr == null ? ostr : new String(ostr.getBytes("GB2312"),
					"UTF-8");
		} catch (Exception e) {
			return ostr;
		}
	}

	public static String UTFToGB(String ostr) {
		try {
			return ostr == null ? ostr : new String(ostr.getBytes("UTF-8"),
					"GB2312");
		} catch (Exception e) {
			return ostr;
		}
	}

	public static String toHEX(String ostr) {
		StringBuffer rst = new StringBuffer();
		for (int i = 0; i < ostr.length(); i++) {
			rst.append(Integer.toHexString(ostr.charAt(i)).toUpperCase());
		}
		return rst.toString();
	}

	public static String prepareXml(String s) {
		if (s == null || s.length() == 0)
			return "";

		StringBuffer stringbuffer = new StringBuffer(s.length() + 50);
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if ('>' == c) {
				stringbuffer.append("&gt;");
				continue;
			} else if ('<' == c) {
				stringbuffer.append("&lt;");
				continue;
			} else if ('&' == c) {
				stringbuffer.append("&amp;");
				continue;
			} else if (c == '"') {
				stringbuffer.append("&quot;");
				continue;
			} else if ('\'' == c) {
				stringbuffer.append("&apos;");
				continue;
			} else
				stringbuffer.append(c);
		}
		return stringbuffer.toString();
	}

	/**
	 * 分页信息封装
	 * 
	 * @param totalRow
	 *            总行数
	 * @param pageSize
	 *            每页行数
	 * @param pageNo
	 *            欲显示行数
	 * @return 数组:{总页数 ,开始行数,结束行数}
	 */

	public static int[] pageInfo(int totalRow, int pageSize, int pageNo) {
		int offset = 1;
		if (pageSize <= 0) {
			return new int[] { 1, 0 + offset, totalRow };
		}
		int totalPage = (int) Math.ceil((double) totalRow / pageSize);
		int startRow = pageSize * (pageNo - 1) + offset;
		int endRow = startRow + pageSize - 1;
		return new int[] { totalPage, startRow, endRow };
	}

	public static int[] pageInfo(Object totalRow, Object pageSize, Object pageNo) {
		try {
			int tr = Integer.parseInt((String) totalRow);
			int ps = Integer.parseInt((String) pageSize);
			int pn = Integer.parseInt((String) pageNo);
			return pageInfo(tr, ps, pn);
		} catch (Exception e) {
			return pageInfo(-1, -1, -1);
		}
	}

	public static int[] rowNoDesc(int totalRows, int startNo, int endNo) {
		int startNoNew = totalRows - endNo + 1;
		int endNoNew = totalRows - startNo + 1;
		return new int[] { startNoNew, endNoNew };
	}

	public static int[] pageInfoDesc(int totalRow, int pageSize, int pageNo) {
		int offset = 1;
		if (pageSize <= 0) {
			return new int[] { 1, 0 + offset, totalRow };
		}
		int totalPage = (int) Math.ceil((double) totalRow / pageSize);
		int endRow = totalRow - pageSize * (pageNo - 1);
		int startRow = endRow - pageSize + offset;
		return new int[] { totalPage, startRow, endRow };

	}

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

	public static String replace(String inString, String oldPattern,
			String newPattern) {
		if (inString == null) {
			return null;
		}
		if (oldPattern == null || newPattern == null) {
			return inString;
		}

		StringBuffer sbuf = new StringBuffer();
		// output StringBuffer we'll build up
		int pos = 0; // our position in the old string
		int index = inString.indexOf(oldPattern);
		// the index of an occurrence we've found, or -1
		int patLen = oldPattern.length();
		while (index >= 0) {
			sbuf.append(inString.substring(pos, index));
			sbuf.append(newPattern);
			pos = index + patLen;
			index = inString.indexOf(oldPattern, pos);
		}
		sbuf.append(inString.substring(pos));

		// remember to append any characters to the right of a match
		return sbuf.toString();
	}

	public static String specialHTMLToShowTEXT(String html){
		if (!StringUtils.isBlank(html)) {
			html = StringUtils.replace(html, "&nbsp;", " ");
			html = StringUtils.replace(html, "&#160;", " ");
			html = StringUtils.replace(html, "&lt;", "<");
			html = StringUtils.replace(html, "&gt;", ">");
			html = StringUtils.replace(html, "&quot;", "\"");
			html = StringUtils.replace(html, "&amp;", "&");
		}
		return html;
	}
	
	public static String HTMLToTEXT(String html) {

		html = html.replaceAll("<([^<>]+)>", "");

		// html=html.replaceAll("<[^\"]*(=\"[^\"]*\")[^(<|>)]*>","").replaceAll("<[^(<|>)]*>","");

		html = specialHTMLToShowTEXT(html);

		return html.replaceAll("<([^<>]+)>", "");

	}

	public static String escRex(String in) {
		return in.replaceAll("\\{", "\\\\{").replaceAll("\\}", "\\\\}");
	}

	public static String getAjaxBegin(String ectableId) {
		return TableConstants.AJAX_ZONE_PREFIX + TableConstants.AJAX_ZONE_BEGIN
				+ ectableId + TableConstants.AJAX_ZONE_SUFFIX;
	}

	public static String getAjaxEnd(String ectableId) {
		return TableConstants.AJAX_ZONE_PREFIX + TableConstants.AJAX_ZONE_END
				+ ectableId + TableConstants.AJAX_ZONE_SUFFIX;
	}
	
	public static String getTrHTML(List trList,TableModel model){
		String extendTr=null;
		if (trList!=null&& trList.size()>0){
			StringBuffer eb=new StringBuffer();
			for (int i=0;i<trList.size();i++){
				Tr trBean=(Tr)trList.get(i);
				eb.append(Tr.buildTr(trBean, model));
			}
			extendTr=eb.toString();
		}
		return extendTr;
	}
	public static boolean isSearchMatchCaseIgnore(String value, String search) {
	    value = value.toLowerCase().trim();
	    search = search.toLowerCase().trim();
		return isSearchMatch(value,search);
	}
//	includeOnceParameters
	public static boolean isSearchMatch(String value, String search) {
		if (search.startsWith("*") && search.endsWith("*") && StringUtils.contains(value, search.substring(1,search.length()-1))) {
			return true;
		}else if (search.startsWith("*") && value.endsWith(StringUtils.replace(search, "*", ""))) {
	        return true;
	    } else if (search.endsWith("*") && value.startsWith(StringUtils.replace(search, "*", ""))) {
	        return true;
	    }else if (value.equals(search)){
	    	return true;
	    }
	
	    return false;
	}
	public static String convertString(Object obj,String nullTo){
		return obj==null?nullTo:obj.toString();
	}
	
	public static String getDefaultSortSQL(Map map){
		
		StringBuffer rs=new StringBuffer();
		if (map!=null && !map.isEmpty()){
			for (Iterator itor=map.keySet().iterator();itor.hasNext();){
				Object field=(String)itor.next();
				String ord=(String)map.get(field);
				rs.append(" ORDER BY ").append(field).append(" ").append(ord);
				break;
			}
		}
		
		return rs.toString();
	}
	


}
