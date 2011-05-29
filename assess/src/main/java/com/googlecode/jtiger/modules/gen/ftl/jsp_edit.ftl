<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="/common/taglibs.jsp" %>
<html>
<head>
<%@include file="/common/meta.jsp" %>
<%@include file="/common/validator.jsp" %>
<style type="text/css">
input, textarea{
   margin:2px;
}
</style>
</head>
<body>
<div class="x-panel">
    <div class="x-panel-header">${table.description?default('')}管理</div>
    <div class="x-toolbar" style="text-align:right;">
    <span class="tb-item" style="padding-right:4px;">
    <a href="index.htm">管理首页</a>
	</span>
	<s:form id="save" action="save" method="post">
	<s:hidden id="model.id" name="model.id"/>
	<table width="600px" align="center">
		<tr>
			<td align="center">
			<fieldset style="margin:10px;"> 
              <legend>编辑${table.description?default('')}信息</legend>
                <table cellpadding="3" cellspacing="2">
                <#list table.columns as column>
			      <#if !column.primaryKey>
			      <tr>
                     <td align="right">${column.description ?default('')}：</td>
                     <td align="left">
                       <#if javaTypes[column_index] == 'Date'>
                       <s:textfield name="model.${properties[column_index]}" cssStyle="width:250px;" cssClass="Wdate" onfocus="WdatePicker({skin:'whyGreen',isShowClear:false,readOnly:true})"/>
			           <#else>
			           <s:textfield name="model.${properties[column_index]}" cssStyle="width:250px;"/>
			           </#if>                     
                     </td>
                  </tr> 
			     </#if>
			    </#list>
                  
                </table> 
              </fieldset>
              <table width="100%" style="margin-bottom:10px;">
				<tr>
					<td style="text-align:center;">
						<s:submit value="保存" cssClass="button"/> 
						<s:reset value="重置" cssClass="button"/>
                    </td>
              	</tr>
              </table>
			</td>
		</tr>
	</table>
	</s:form>
</div>

<script type="text/javascript">
$(function(){
  $("#save").validate();
});

</script>
</body>
</html>