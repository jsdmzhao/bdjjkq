<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<html>
<head>
<title>${table.description?default('')}管理</title>
<%@include file="/common/meta.jsp" %>
<%@include file="/common/ec.jsp" %>
</head>
<body>
<div class="x-panel">
  <div class="x-panel-header" style="height:18px;line-height:18px;font-size:12px;">${table.description?default('')}管理</div>
 <div class="x-toolbar" style="text-align:right;">
  	<span class="tb-item"><a href="editNew.htm">新建</a></span>
  	<span class="ytb-sep"></span>
    <span class="tb-item" style="padding-right:4px;">管理</span>
  </div>
  <div class="x-panel-body">
    <div style="margin-left:-3px;" align="center">
	<ec:table items="items" var="item" retrieveRowsCallback="process" sortRowsCallback="process" 
		action="index.htm"
		useAjax="true" doPreload="false"
		maxRowsExported="10000000" 
		pageSizeList="10,15,20,50,100,500,1000" 
		editable="false" 
		sortable="true"	
		rowsDisplayed="15"	
		generateScript="true"	
		resizeColWidth="true"	
		classic="false"	
		width="100%" 	
		height="500"	
		minHeight="200"
		toolbarContent="navigation|pagejump|pagesize|refresh|extend|status">
		<ec:row>
			<ec:column width="40" property="_x" title="No." value="${r"${GLOBALROWCOUNT}"}" style="text-align:center" sortable="false"/>
			<#list table.columns as column>
			<#if !column.primaryKey && column.sizeAsInt<300>
			<ec:column width="${column.size ?default('')}" property="${properties[column_index]}" title="${column.description ?default('')}" style="text-align:center"/> 
			</#if>
			</#list>
			
			<ec:column width="80" property="_0" title="操作" style="text-align:center" sortable="false">
			   <a href="edit.htm?model.id=${r"${item.id}"}">
			       <img src="${r"${ctx}"}/images/icons/modify.gif" border="0" title="编辑"/>
			   </a>
			   <span style="color:#ccc;margin:2px;">|</span>
			    <a href="remove.htm?model.id=${r"${item.id}"}" onclick="javascript:return confirm('是否确定删除？');">
			       <img src="${r"${ctx}"}/images/icons/delete.gif" border="0" title="删除"/>
			   </a>
			</ec:column>
		</ec:row>
	  </ec:table>
	</div>
  </div>
</div>
</body>
</html>