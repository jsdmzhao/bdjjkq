package com.googlecode.jtiger.modules.ecside.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.googlecode.jtiger.modules.ecside.core.ECSideConstants;
import com.googlecode.jtiger.modules.ecside.core.TableConstants;

@SuppressWarnings("unchecked")
public class ServletUtils {

	public static void defaultAjaxResopnse(List parameterMapList,int[] resultCodes,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		defaultAjaxResopnse(null,parameterMapList,resultCodes,null,request,response);
	}
	public static void defaultAjaxResopnse(String ctableId,List parameterMapList,int[] resultCodes,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		defaultAjaxResopnse(ctableId,parameterMapList,resultCodes,null,request,response);
	}
	public static void defaultAjaxResopnse(List parameterMapList,int[] resultCodes,String[] messages,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		defaultAjaxResopnse(null,parameterMapList,resultCodes,messages,request,response);
	}
	public static void defaultAjaxResopnse(String ctableId,List parameterMapList,int[] resultCodes,String[] messages,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.flush();
		out.println(RequestUtils.getTableId(request,ctableId));
		if (messages==null) {
			messages=new String[resultCodes.length];
		}
		Map parameterMap;
		String recordKey;
		
		for (int i=0;i<parameterMapList.size()&&i<resultCodes.length;i++){
			parameterMap=(HashMap)parameterMapList.get(i);
			recordKey=ECSideUtils.convertString(parameterMap.get(TableConstants.RECORDKEY_NAME),null );
				out.println(resultCodes[i]);
				out.println(recordKey);
				out.println(ECSideUtils.convertString(messages[i],"" ));
		}
		out.print("END OF com.googlecode.jtiger.modules.ecside.defaultAjaxResopnse");
		out.flush();
		out.close();

	}
	
	
	public static void writeDefaultTextToClient(Map parameterMap,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String recordKey=ECSideUtils.convertString(parameterMap.get(TableConstants.RECORDKEY_NAME),null );
		String code=ECSideUtils.convertString(request.getAttribute(ECSideConstants.C_UPDATE_RESULT_CODE),null );
		String message=ECSideUtils.convertString(request.getAttribute(ECSideConstants.C_UPDATE_RESULT_MESSAGE),null );
		writeDefaultTextToClient(recordKey,code, message,request,response);
	}

	// code = RequestUtil.successfulInfo(request) or RequestUtil.failedInfo(request);
	public static void writeDefaultTextToClient(
			String recordKey,String code,String message,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.flush();
		out.println(code);
		out.println(recordKey);
		out.print(message);
		out.flush();
		out.close();
	}
	
	public static Map getParameterMap(HttpServletRequest request) {
		Map parameterMap = new HashMap();
		Enumeration names = request.getParameterNames();
		while (names.hasMoreElements()) {
			String name = (String) names.nextElement();
			String[] s = request.getParameterValues(name);
			if (s != null) {
				if (s.length == 1) {
					parameterMap.put(name, request.getParameterValues(name)[0]);
				} else {
					parameterMap.put(name, request.getParameterValues(name));
				}
			}
		}
		return parameterMap;
	}
	
	public static List getParameterMaps(HttpServletRequest request) {
		List maplist=new ArrayList(); 
		
		Enumeration names = request.getParameterNames();
		while (names.hasMoreElements()) {
			String name = (String) names.nextElement();
			String[] s = request.getParameterValues(name);
			if (s != null) {
				for (int i=0;i<s.length;i++){
					Map parameterMap;
					if (i+1>maplist.size()){
						parameterMap=new HashMap();
						maplist.add(parameterMap);
					}else{
						parameterMap =(HashMap)maplist.get(i);
					}
					
					parameterMap.put(name, request.getParameterValues(name)[i]);
				}
				
			}
		}
		
		
		return maplist;
	}

	public static Object getAttributeFromMapList(ServletRequest request,
			String para) {
	
		String[] paras = para.split("\\.");
		String listName = null;
		int idx = 0;
		String propertyname = null;
		if (paras.length > 2) {
			listName = paras[0];
			try {
				idx = Integer.parseInt(paras[1]);
			} catch (Exception e) {
				idx = 0;
			}
			propertyname = paras[2];
		}
	
		List recordList = (List) request.getAttribute(listName);
		if (recordList != null && recordList.size() > idx) {
			Map map = (Map) recordList.get(idx);
			return map != null ? map.get(propertyname) : null;
		}
		return null;
	}



}
