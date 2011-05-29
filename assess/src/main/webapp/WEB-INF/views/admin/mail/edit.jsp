<!--author hsf-->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>SMTP配置</title>
<%@include file="/common/extjs.jsp"%>
<%@include file="/common/meta.jsp"%>
<%@include file="/common/validator.jsp"%>

<script type="text/javascript">
$(function(){
	$('#save').validate();
	if($('#port').val() == '') {
		$('#port').val('25');
	}

	if($('#username').val() == '') {
		$('#username').val('${admin.email}');
	}
});
</script>
</head>

<body>
<div class="x-panel">
<div class="x-panel-header">编辑SMTP配置</div>

<div class="x-panel-body">
<div><%@ include file="/common/messages.jsp"%>
</div>

<s:form action="save" theme="simple" id="save" validate="true" method="POST">
	<fieldset style="margin: 30px;"><legend>SMTP配置信息</legend>
	<table width="100%">
		<tr>
			<td class="simple">主机地址：</td>
			<td class="simple"><s:textfield name="model.host"
				id="model.host" theme="simple" size="30" cssClass="required"/><font color='red'>*</font></td>
		</tr>
		<tr>
			<td class="simple">端口号：</td>
			<td class="simple"><s:textfield name="model.port"
				id="port" theme="simple" size="30" cssClass="required digits"/><font color='red'>*</font></td>
		</tr>
		<tr>
			<td class="simple">用户名：</td>
			<td class="simple"><s:textfield name="model.username"
				id="username" theme="simple" size="30" cssClass="required"
				title="请确保SMTP用户名和admin账户的email是一致的，否则可能造成邮件发送失败。"/>
				<font color='red'>*</font>
				
				</td>
		</tr>
		<tr>
			<td class="simple">口令：</td>
			<td class="simple"><s:password name="model.password"
				id="model.password" theme="simple" size="30" cssClass="required"></s:password><font color='red'>*</font></td>
		</tr>
		<tr>
			<td class="simple">SMTP验证：</td>
			<td class="simple"><s:checkbox name="model.auth" id="model.auth" theme="simple"></s:checkbox>
			</td>
		</tr>

	</table>
	<table width="100%" style="margin-bottom: 10px;">
		<tr>
			<td style="text-align: center;"><s:submit value="保存"
				cssClass="button"></s:submit> <s:reset value="重置" cssClass="button"></s:reset></td>
		</tr>
	</table>
	</fieldset>
</s:form>
</div>
</div>
</body>
</html>
