<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<%@include file="/common/meta.jsp"%>
<%@include file="/common/validator.jsp"%>
<style type="text/css">
<!--
.Message {
	color: #FF0000;
	font-style: italic;
}
#mytable {
	border: 1px solid #A6C9E2;
	width: 80%;
	border-collapse: collapse;
}

#mytable td {
	border: 1px solid #A6C9E2;
	height: 26px;
}
-->
</style>
<title>编辑用户</title>
</head>
<body>


<div class="x-panel-body">
<table width="80%" align="center" style="margin: 5px">
	<tr>
		<td height="20px"></td>
	</tr>
</table>
<s:form action="user/save" theme="simple" id="save" validate="true"
	method="POST">
	<s:hidden name="model.id" id="uId" />
	<s:hidden name="model.version" />
	<s:hidden name="model.status" />
	<s:hidden name="selfEdit" />
	<table align="center" style="width:80%;margin-bottom:2px;">
		<tr>
	   		<td style="border:none;">
	   			<%@ include file="/common/messages.jsp"%>
	   		</td>
		</tr>
	</table>
	<table id="mytable" align="center">
	
		<tr>
			<td class="simple" align="right">登录名：</td>
			<td class="simple" width="250px">&nbsp;
			<s:if test="model.isSys == 1">
				<s:textfield name="model.loginId" id="loginId"
				theme="simple" size="25" cssClass="userName required" title="系统用户不可修改用户名！" disabled="true"/>
			</s:if>
			<s:else>
				<s:textfield name="model.loginId" id="loginId"
				theme="simple" size="25" cssClass="userName required"/> <font color="red">*</font>
			</s:else>
			</td>
			<td id="denglu" algin="left" colspan="2">
				请填写登录名
			</td>
		</tr>
		
		<tr>
			<td class="simple" align="right">输入密码：</td>
			<td class="simple">&nbsp;<s:password name="model.password" id="pwd"
				theme="simple" size="26" cssClass="pwdCheck required"/> <font color="red">*</font></td>
			<td algin="left" colspan="2" id="pswdck">
				请输入不小于六位的密码
			</td>
		</tr>
		<tr>
			<td class="simple" align="right">确认密码：</td>
			<td class="simple">&nbsp;<s:password name="model.confirmPwd"
				id="repwd" theme="simple" size="26" cssClass="passwordCheck required"/> <font color="red">*</font></td>
			<td algin="left" colspan="2" id="pswd">
				请再次输入密码
			</td>
		</tr>
		
		
		<tr>
			<td class="simple" align="right">真实姓名：</td>
			<td class="simple">&nbsp;<s:textfield name="model.name"
				cssClass="required" theme="simple" size="25" /> <font color="red">*</font></td>
			<td colspan="2">请输入真实的姓名</td>
		</tr>
		<tr>
			<td class="simple" align="right" width="100">性&nbsp;别：</td>
			<td class="simple">&nbsp;<s:radio list="sexMap" 
				name="model.sex" cssStyle="border:0px;" /> <font color="red">*</font></td>
			<td colspan="2"></td>
		</tr>
		<tr>
			<td class="simple" align="right">学&nbsp;历：</td>
			<td class="simple">&nbsp;<s:select list="degreeMap"
				 name="model.degree" headerKey=""
				headerValue="--请选择--" cssStyle="width:156px;" /> <font color="red">*</font></td>
			<td colspan="2"></td>
		</tr>
		<tr>
			<td class="simple" align="right">职务/职称：</td>
			<td class="simple">&nbsp;<s:textfield name="model.post" theme="simple"
				size="25" /></td>
			<td colspan="2"></td>
		</tr>
		<tr>
			<td class="simple" align="right">联系电话：</td>
			<td class="simple">&nbsp;<s:textfield name="model.mobile"
				 theme="simple" size="25" /> <font color="red">*</font></td>
			<td colspan="2">请输入区号和真实的电话，以便我们与您联系。</td>
		</tr>
		<tr>
			<td class="simple" align="right">电子信箱：</td>
			<td class="simple">&nbsp;<s:textfield name="model.email" id="email"
				theme="simple" size="25" /> <font color="red">*</font></td>
			<td algin="left" colspan="2" id="usemail">
				请输入您常用的电子邮箱
				<c:if test="${model.loginId == 'admin'}">
					,<font color="red">修改邮箱后请同时修改</font><a href="${ctx}/admin/mail/view.htm" target="main" title="SMTP配置"><font color="blue">SMTP配置</font></a><font color="red">中的用户名及密码。</font>
				</c:if>
			</td>
		</tr>
		<tr>
			<td class="simple" align="right">工作单位：</td>
			<td class="simple">&nbsp;<s:textfield name="model.unit"
				 theme="simple" size="25" /> <font color="red">*</font></td>
			<td colspan="2"></td>
		</tr>
		
		<tr>
			<td class="simple" align="right">通信地址：</td>
			<td class="simple">&nbsp;<s:textfield name="model.address"
				 theme="simple" size="25" /> <font color="red">*</font></td>
			<td colspan="2"></td>
		</tr>
		<tr>
			<td class="simple" align="right" width="100">邮&nbsp;编：</td>
			<td class="simple">&nbsp;<s:textfield name="model.zip"
				 theme="simple" size="25" /> <font color="red">*</font></td>
			<td colspan="2"></td>
		</tr>
	</table>
	<table width="100%" style="margin-bottom: 10px;">
		<tr>
			<td colspan="2" align="center" class="font_white">
			    <br>
			    <s:submit
				value="保存" cssClass="btn-gray-s"></s:submit>&nbsp;&nbsp; <s:reset
				value="清空" cssClass="btn-gray-s" /></td>
		</tr>
	</table>
</s:form>
</div>
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
  				document.getElementById('pswd').innerHTML = '<font color="red">'+'两次输入的密码不一致。'+'</font>';
  			}else{
  				res = "ok";
  				document.getElementById('pswd').innerHTML = '请再次输入密码';
  			}
  		}
        return res != "err";
    },"");
    //验证密码
	$.validator.addMethod("pwdCheck", function(value, element) {
        var res;
        var pwd1 = document.getElementById('pwd').value;
  		if(pwd1.length < 6) {
  			res = "err";
  			document.getElementById('pswdck').innerHTML = '<font color="red">'+'密码小于6位。'+'</font>';
  		}else{
  			res = "ok";
  			document.getElementById('pswdck').innerHTML = '请输入不小于六位的密码';
  		}
        return res != "err";
    },"");
    //验证用户名
	$.validator.addMethod("userName", function(value, element) {
        var res;
        var userName = document.getElementById('loginId').value;
        var userId = document.getElementById('uId').value;
     	/**
     	var re = /^(?!_)(?!.*?_$)[a-zA-Z0-9_-\u4e00-\u9fa5]+$/;
     	var result = re.exec(userName);
     	if (result != null) {
       		document.getElementById('denglu').innerHTML = '请填写登录名(仅限字母、数字、横线和下划线。)';
     	}else {
        	document.getElementById('denglu').innerHTML = '<font color="red">'+'仅限字母、数字、横线和下划线，请重新输入。'+'</font>';
     	}
     	*/
     	if (userName != null && userName != ""){
     	   var u = encodeURI('${ctx}/regist/checkName.htm');
      	   $.ajax({
			 url: u,
			 type: 'post',
			 async : false,
			 dataType: 'json',
			 data: {uName : userName, uId : userId},
			 success: function(rst, textStatus){
				res = rst.result;
				if (rst.result == "exist") {
       	   			document.getElementById('denglu').innerHTML = '<font color="red">'+'您输入的登录名 '+userName+' 已存在，请重新输入。'+'</font>';
       	    	}else {
       	    		document.getElementById('denglu').innerHTML = '请填写登录名';
       	    	}
			 }
	  	   });
	  	   return res != "exist";
	  	 }
  	  },"");
  	  //验证用户邮箱
	$.validator.addMethod("regEmail", function(value, element) {
        var res;
        var emailStr = document.getElementById('email').value;
        var userId = document.getElementById('uId').value;
		var r = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;
		if (emailStr != '' && !r.test(emailStr)) {
			document.getElementById('usemail').innerHTML = '<font color="red">'+'请输入正确的邮箱地址！'+'</font>';
		}else{
			document.getElementById('usemail').innerHTML = '请输入您常用的电子邮箱';
       		$.ajax({
				url: '${ctx}/regist/checkEmail.htm',
				type: 'post',
				async : false,
				dataType: 'json',
				data: {uName : emailStr, uId : userId},
				success: function(rst, textStatus){
					res = rst.result;
					if (rst.result == "exist") {
       	   		  		document.getElementById('usemail').innerHTML = '<font color="red">'+'您输入的Email已存在，请重新输入。'+'</font>';
       	   			}
				}
			});
			return res != "exist";
		}
  	  },"");
}); 
<c:if test="${model.id != null}">
  $(function() {
      $('#pwd').val("*********");
      $('#repwd').val("*********");
  }); 
</c:if>
</script>
<script type="text/javascript">
	$(document).ready(function() {
	$("#save").validate();
});
</script>
</body>
</html>