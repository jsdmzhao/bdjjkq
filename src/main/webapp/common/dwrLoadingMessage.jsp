<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib  prefix="s" uri="/struts-tags"%>
<%--You should include this page at the end of body.--%>
<script type="text/javascript" src="${ctx}/scripts/dwrjs/loadingMsg.js" ></script>
<div id="disabledImageZone" style="width:100%;height:32px;position:absolute; 
	z-index: 1000;display:none; top:0px;left: 0px;text-align: right;margin-right:10px;margin-top:5px;">
<img src="${ctx}/images/loading.gif" border="0" style="padding-right:5px;">
<span style="padding-right:5px;font:normal;color:#6593cf;"><s:text name="global.loading"/></span>
</div>
