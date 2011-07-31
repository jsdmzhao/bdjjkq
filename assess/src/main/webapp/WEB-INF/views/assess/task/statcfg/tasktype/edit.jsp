<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="/common/taglibs.jsp" %>
<html>
<head>
<%@include file="/common/meta.jsp" %>
<title></title>
<%@include file="/common/validator.jsp" %>
<%@include file="/common/ec.jsp" %>
<%@include file="/common/extjs.jsp" %>
<script type="text/javascript" src="${ctx}/scripts/jquery.form.js"></script>
<style type="text/css">
.transgressTypeTitle{
	text-align:left;
	font-size:18px;
	font-weight:bold;
	color:#0066CC;
}
.transgressActionCheckBox{
	text-align: left;
}
.sstTable td{
    border-collapse: collapse;
    margin-left:-9px;
    border:solid 1px #97B7E7;
}
.clossBtn{
	margin-left: 20px;
	
}
</style>
</head>
<body>
<div class="x-panel">
    <div class="x-panel-header">考核标准类别管理</div>
    <div class="x-toolbar" style="text-align: right;">
		<a href="index.htm">管理首页</a>
    </div>
	<s:form id="saveFrm" action="save" method="post">
	<s:hidden id="model.id" name="model.id"/>
	<table width="800px" align="center">
		<tr>
			<td align="center">
			<fieldset> 
              <legend>考核标准类别信息</legend>
                <table cellpadding="3" cellspacing="2" width="100%">
                  <tr>
                     <td align="right" width="15%">名称：</td>
                     <td align="left" width="80%"  ><s:textfield name="model.name"></s:textfield><span style="color: #ff0000;">*</span></td>
                  </tr>
                  <tr>
                  	<td align="right" width="15%">备注：</td>
                  	<td align="left" width="80%"  >
                  		<s:textarea name="model.remark" cols="60" rows="6"></s:textarea>
                  	</td>
                  </tr>
                </table> 
              </fieldset>
              <table width="100%" style="margin-bottom:10px;">
				<tr>
					<td style="text-align:center;">
					 <s:submit value="保存" cssClass="button"/> 						
						<input type="button" value="保存" class="button" onclick="saveIt()"></input>		
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
$("#saveFrm").validate({
	rules: {
		'model.name':  {
			required : true
		},
		'model.state':  {
			required : true
		},
		'model.integral':  {
			required : true,
			number : true
		}
	},
	messages: {
		'model.name': {
			required: "请输入类别名称"
		},
		'model.state':  {
			required: "选择审核结果"
		}
	}
});

/*
$(function(){	
    $('#saveFrm').ajaxForm({    	
   	 success:function(data) {   		 
   		
   		Ext.my().msg('', '保存统计条件信息成功' );
   		pause(2000); 
   		location.href = '${ctx}/assess/transgress/statcfg/statItem/index.htm';
   		
   	 },
   	 error:function(xhr) {
   		 Ext.my().msg('', '保存统计条件信息失败' );
   	 }
    });
});*/


</script>
</body>
</html>