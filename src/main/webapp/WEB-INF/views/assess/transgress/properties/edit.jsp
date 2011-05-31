<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<html>
<head>


<%@include file="/common/ec.jsp" %>
<%@include file="/common/extjs.jsp" %>
<%@include file="/common/meta.jsp" %>
<script type="text/javascript" src="${ctx}/scripts/jquery.form.js"></script>
<style>
input[type=checkbox] {
   margin-left:8px;
}



td.td_label{
   width:100px;
}


fieldset {
	width: 200px;	
	height:130;
	padding:10px;
	border:1px solid #efefef;
}

legend {
   padding:10px;
}

.progressWrapper {
	width: 180px;
}

#atts div {
   padding:7px;
   width:350px;   
}

#atts div a {
   margin-left:5px;
}


</style>
</head>
<body>
<div class="x-panel">
  <div class="x-panel-header">报表属性</div>
    <div class="x-toolbar" style="padding:2px 15px 2px 0px;text-align:right;">	   
	   <a href="index.htm"> 报表属性管理首页</a>&nbsp;|&nbsp;
	   <a href="javascript:void(0);" onclick="history.back()">返回</a>
    </div>
<table width="100%" border="0" cellpadding="5" cellspacing="1">
	<tr>
		<td><%@ include file="/common/messages.jsp"%></td>
	</tr>
</table>
<div class="x-panel-body" style="text-align:center;padding:10px;">
   <s:form action="save.htm" method="post" id="frm" enctype="multipart/form-data">
   <s:hidden name="model.id"/>
   
   <table width="830" align="center">
   <tr>
      <td>
		  <div class="panes">
	
		          <table width="100%">		                
						<tr>
							<td class="td_label">报表表头：</td>
							<td style="text-align: left;">
							   <s:textfield name="model.title" id="title" cssClass="inputborder m_t_b tit required" cssStyle="width:450px;" />
							   <span style="color: #ff0000;">*</span>
							</td>
						</tr>																
						<tr>
							<td style="text-align: left;" colspan="2">
							 <br>报表备注：<br><br>
							   <s:textarea name="model.remark" id="cont" cssClass="inputborder m_t_b" cssStyle="width:780px;height:200px;" />
							   
							</td>
						</tr>
						
						
				   </table>
		      </div>

	  </td>
   </tr>
   </table>
    <table width="100%" style="margin-bottom:10px;">
				<tr>
					<td style="text-align:center;">
						<s:submit value="保存" cssClass="button"/> 
						<s:reset value="重置" cssClass="button"/>
                    </td>
              	</tr>
              </table>
   </s:form>
</div>
</div>
<script type="text/javascript">

$(function(){
    $('#frm').ajaxForm({
   	 success:function(data) {
   		 Ext.my().msg('', '保存报表信息成功' );
   	 },
   	 error:function(xhr) {
   		 Ext.my().msg('', '保存报表信息失败' );
   	 }
    });
});
</script>

</body>
</html>