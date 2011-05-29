package com.googlecode.jtiger.modules.ecside.util;


import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.googlecode.jtiger.modules.ecside.core.TableConstants;
import com.googlecode.jtiger.modules.ecside.core.context.HttpServletRequestContext;
import com.googlecode.jtiger.modules.ecside.core.context.WebContext;
import com.googlecode.jtiger.modules.ecside.resource.MimeUtils;
import com.googlecode.jtiger.modules.ecside.table.limit.Limit;
import com.googlecode.jtiger.modules.ecside.table.limit.LimitFactory;
import com.googlecode.jtiger.modules.ecside.table.limit.TableLimit;
import com.googlecode.jtiger.modules.ecside.table.limit.TableLimitFactory;

public class RequestUtils {


    public static String successfulInfo(HttpServletRequest request){
    	return successfulInfo(request,null,null);
    }
    public static String failedInfo(HttpServletRequest request){
    	return failedInfo(request,null,null);
    }
    public static String successfulInfo(HttpServletRequest request,String otherMessage){
    	return successfulInfo(request,null,otherMessage);
    }
    public static String failedInfo(HttpServletRequest request,String otherMessage){
    	return failedInfo(request,null,otherMessage);
    }
    
    public static String successfulInfo(HttpServletRequest request,String tableIdT,String otherMessage){
    	return successfulOrFailedInfo("Success",getTableId(request,tableIdT),otherMessage);
    }

    public static String failedInfo(HttpServletRequest request,String tableIdT,String otherMessage){
    	return successfulOrFailedInfo("Fail",getTableId(request,tableIdT),otherMessage);
    }
    
    public static String successfulOrFailedInfo(String flag,String tableIdT,String otherMessage){
    	StringBuffer sbuf=new StringBuffer();
    	sbuf.append(tableIdT).append("\n").append(flag);
    	if (StringUtils.isNotBlank(otherMessage)){
    		sbuf.append("\n").append(otherMessage);
    	}
    	return sbuf.toString();
    }
    
    
	public static String getTableId(HttpServletRequest request) {
		return getTableId(request, null);
	}

	public static int getTotalRowsFromRequest(HttpServletRequest request) {
		return getTotalRowsFromRequest(request, null);
	}

	public static int[] getRowStartEnd(HttpServletRequest request,
			int totalRows, int defaultPageSize) {
		return getRowStartEnd(request, null, totalRows, defaultPageSize);
	}

	public static int[] getRowStartEnd(HttpServletRequest request,
			int totalRows, int defaultPageSize, int offset) {
		return getRowStartEnd(request, null, totalRows, defaultPageSize, offset);
	}

	public static String getTableId(HttpServletRequest request, String cTableId) {
		if (cTableId != null) {
			return cTableId;
		}
		String tableId = request
				.getParameter(TableConstants.EXTREME_COMPONENTS_INSTANCE);
		if (tableId == null) {
			tableId = TableConstants.EXTREME_COMPONENTS;
		}
		return tableId;

	}
	


	public static int getTotalRowsFromRequest(HttpServletRequest request,
			String cTableId) {
		int totalRows = -1;
		String tableId = getTableId(request, cTableId);
		tableId = tableId == null ? "" : tableId + "_";
		Integer totalRowsI = (Integer) request.getAttribute(tableId
				+ TableConstants.TOTAL_ROWS);

		try {
			if (totalRowsI != null) {
				totalRows = totalRowsI.intValue();
			} else {
				String totalRowsS = request.getParameter(tableId + "totalrows");
				totalRows = Integer.parseInt(totalRowsS);
			}
		} catch (Exception e) {
			totalRows = -1;
		}
		totalRows = totalRows < 0 ? -1 : totalRows;
		return totalRows;
	}

	public static int[] getRowStartEnd(HttpServletRequest request,
			String cTableId, int totalRows, int defaultPageSize) {
		int offset = 0;
		return getRowStartEnd(request, cTableId, totalRows, defaultPageSize,
				offset);
	}

	public static int[] getRowStartEnd(HttpServletRequest request,
			String cTableId, int totalRows, int defaultPageSize, int offset) {

		Limit limit = getLimit(request, cTableId, totalRows, defaultPageSize);
		return new int[] { limit.getRowStart() + offset,
				limit.getRowEnd() + offset };
	}

	public static int getPageNo(HttpServletRequest request) {
		int pageNo = -1;
		WebContext context = new HttpServletRequestContext(request);
		String tableId = request
				.getParameter(TableConstants.EXTREME_COMPONENTS_INSTANCE);
		if (tableId == null) {
			tableId = TableConstants.EXTREME_COMPONENTS;
		}
		LimitFactory limitFactory = new TableLimitFactory(context, tableId);
		TableLimit limit = new TableLimit(limitFactory);
		pageNo = limit.getPage();
		return pageNo;
	}

	
	public static Limit getLimit(HttpServletRequest request){
		return getLimit(request,null);
	}
	public static Limit getLimit(HttpServletRequest request, String cTableId){
		WebContext context = new HttpServletRequestContext(request);
		String tableId = getTableId(request, cTableId);
		LimitFactory limitFactory = new TableLimitFactory(context, tableId);
		TableLimit limit = new TableLimit(limitFactory);
		return limit;
	}
	
	public static void initLimit(HttpServletRequest request,int totalRows,int defaultPageSize){
		initLimit(request, null, totalRows,defaultPageSize);
	}
	public static void initLimit(HttpServletRequest request,String cTableId,int totalRows,int defaultPageSize){
		getLimit(request, cTableId).setRowAttributes(totalRows, defaultPageSize);
	}
	
	public static Limit getLimit(HttpServletRequest request, int totalRows, int defaultPageSize) {
		return getLimit(request,null,totalRows,defaultPageSize);
	}

	public static Limit getLimit(HttpServletRequest request, String cTableId,
			int totalRows, int defaultPageSize) {
		Limit limit = getLimit(request, cTableId);
		limit.setRowAttributes(totalRows, defaultPageSize);
		return limit;
	}
	
	public static void setTotalRows(HttpServletRequest request, int totalRows){
		setTotalRows(request,null,totalRows);
	}
	
	public static void setTotalRows(HttpServletRequest request, String cTableId,int totalRows){
		String tableId = getTableId(request, cTableId);
		tableId = tableId == null ? "" : tableId + "_";
		request.setAttribute(tableId + TableConstants.TOTAL_ROWS, new Integer(
				totalRows));
	}
	


	
	public static int getCurrentRowsDisplayed(HttpServletRequest request) {
		String tableId = request
				.getParameter(TableConstants.EXTREME_COMPONENTS_INSTANCE);
		String currentRowsDisplayed = request.getParameter(tableId + "_"
				+ TableConstants.CURRENT_ROWS_DISPLAYED);
		int crd = 0;
		if (StringUtils.isNotBlank(currentRowsDisplayed)) {
			try {
				crd = Integer.parseInt(currentRowsDisplayed);
			} catch (Exception e) {
			}
		}
		return crd;
	}

	public static boolean isAJAXRequest(ServletRequest servletRequest) {
	    HttpServletRequest request = (HttpServletRequest)servletRequest;
	    return Boolean.TRUE.toString().equalsIgnoreCase(request.getHeader(TableConstants.IS_AJAX_REQUEST))
	    	|| Boolean.TRUE.toString().equalsIgnoreCase(request.getHeader(TableConstants.USE_AJAX_PREP));
	}

	public static String encodeFileName(HttpServletRequest request, String fileName) throws UnsupportedEncodingException {
			String agent = request.getHeader("USER-AGENT");
	//    	exportFileName = URLEncoder.encode(exportFileName,response.getCharacterEncoding());
			
			return ECSideUtils.encodeFileName(fileName,agent);
	
	}
	
	
	
	
	
	
    public static boolean isExported(HttpServletRequest request) {
        return StringUtils.isNotBlank(getExportTableId(request));
    }
    
    public static String getExportTableId(HttpServletRequest request) {
        return request.getParameter(TableConstants.EXPORT_TABLE_ID);
    }
	public static String getExportFileName(HttpServletRequest request) {
		return getExportFileName(request,null);
	}
    public static String getExportFileName(HttpServletRequest request, String cTableId) {
        String tableId = getTableId(request,cTableId);

        if (StringUtils.isNotBlank(tableId)) {
            String exportFileNameStr = tableId + "_" + TableConstants.EXPORT_FILE_NAME;
            String exportFileName = request.getParameter(exportFileNameStr);

            return exportFileName;
        }

        return null;
    }
    
	public static void beforeExport(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException{
		beforeExport(request,response,getExportFileName(request));
	}
	
	public static void beforeExport(HttpServletRequest request,HttpServletResponse response,String exportFileName) throws UnsupportedEncodingException{
		
		exportFileName = encodeFileName(request,exportFileName);
		
		String mimeType = MimeUtils.getFileMimeType(exportFileName);

		if (StringUtils.isNotBlank(mimeType)) {
			response.setContentType(mimeType);
		}
		response.setHeader("Content-Disposition", "attachment;filename=\"" + exportFileName + "\"");
		response.setHeader("Content-Transfer-Encoding","binary");
		response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
		response.setHeader("Pragma", "public");
		response.setDateHeader("Expires", (System.currentTimeMillis() + 1000));
	}
	
	
	public static void afterExport(HttpServletRequest request,HttpServletResponse response) throws IOException{
		response.getOutputStream().flush();
		response.getOutputStream().close();
	}

}
