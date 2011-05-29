<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.googlecode.jtiger.modules.security.user.model.User"%>
<%@page
	import="org.springframework.security.authentication.UsernamePasswordAuthenticationToken"%>
<html>
<head>
<%@include file="/common/meta.jsp"%>
<%@include file="/common/validator.jsp"%>
<title>密码管理</title>
<style type="text/css">
.simple {
	font-size: 12px;
	background-color: #FFFFFF;
	padding: 4px;
	color: #0099FF;
}

input,textarea {
	border: 1px #CECECE solid;
}
</style>
<script type="text/javascript" src="${ctx}/scripts/custom/user/changePassword.js"></script>
</head>
<body>

<table width="60%" align="center">
	<tr>
		<td><%@ include file="/common/messages.jsp"%></td>
	</tr>
</table>

<table width="70%" align="center">
	<tr>
		<td align="center"><s:form action="user/changePassword"
			theme="simple" validate="true" method="POST" id="change">
			<input type="hidden" name="model.id"
				value='<%=((User) ((UsernamePasswordAuthenticationToken) request.getUserPrincipal())
              .getPrincipal()).getId()%>'>
			<fieldset style="margin: 10px;"><legend>修改密码</legend>
			<table>
				<tr>
					<td class="simple" width="60">旧密码：</td>
					<td width="220" class="simple" style="text-align: left;"><s:password cssClass="required"
						name="oldPassword" id="oldPassword" theme="simple" size="25" /></td>
					<td width="130"></td>
				</tr>
				<tr>
					<td class="simple">新密码：</td>
					<td class="simple" style="text-align: left;"><s:password
						name="model.password" id="pwd" theme="simple" size="25" cssClass="required"
						onkeyup="pwStrength(this.value);"
						onblur="pwStrength(this.value);" /></td>
					<td>
					  <table width="100" border="0" cellspacing="1" cellpadding="1"
						bordercolor="#cccccc" height="20" style='display: inline'>
						<tr align="center" bgcolor="#eeeeee">
							<td width="33%" id="strength_L">弱</td>
							<td width="33%" id="strength_M">中</td>
							<td width="33%" id="strength_H">强</td>
						</tr>
					  </table>
					</td>
				</tr>
				<tr>
					<td class="simple">重复密码：</td>
					<td class="simple" style="text-align: left;"><s:password
						name="model.confirmPwd" id="repwd" theme="simple" size="25" cssClass="passwordCheck required" /></td>
					<td id="pwdRes"></td>
				</tr>
			</table>
			</fieldset>
			<table width="100%" style="margin-bottom: 10px;">
				<tr>
					<td colspan="2" align="center" class="font_white"><s:submit
						value="保存" cssClass="button"></s:submit></td>
				</tr>
			</table>
		</s:form></td>
	</tr>
</table>
<script type="text/javascript">
$(function() {
	//验证密码一致性
	$.validator.addMethod("passwordCheck", function(value, element) {
        var res;
        var pwd1 = document.getElementById('pwd').value;
  		var pwd2 = document.getElementById('repwd').value;
  		if(pwd1 !=null & pwd2 !=null & pwd1 !='' & pwd2 !='') {
  			if(pwd1 != pwd2) {
  				res = "err";
  				document.getElementById('pwdRes').innerHTML = '<font color="red">'+'两次输入的密码不一致'+'</font>';
  			}else{
  				res = "ok";
  				document.getElementById('pwdRes').innerHTML = '';
  			}
  		}
        return res != "err";
    },"");
});
</script>
<script type="text/javascript">
	$(document).ready(function() {
	$("#change").validate();
});
</script>
</body>
</html>