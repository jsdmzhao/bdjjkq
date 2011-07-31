<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="/common/taglibs.jsp" %>
<%@include file="/common/meta.jsp" %>
<%@include file="/common/validator.jsp" %>
<script src="${ctx}/scripts/jquery.form.js"></script>
<title><jtiger:title title="保定市交警支队勤务考核系统--用户登录"/></title>

<style type="text/css">
<!--
body {
	background-color: #fff;
}
.login_tt {
	color: #282828;
	font-size: 18px;
	font-weight: bold;
	line-height: 32px;
	font-family:"黑体";
}
.font_tt {
	font-size: 12px;
	font-weight: bold;
	color: #282828;
}
.inputbb {
	height: 24px;
	border: 1px solid #999999;
	line-height: 20px;
}
-->
</style>
</head>

<body>
<div style="width:1002px;margin:0 auto;padding-top:4px;">
   <div style="text-align:right;float:right;">
   </div>
   <div style="padding-top:20px;">
	   <a href="${ctx}/index.htm">
	   </a>
   </div>
</div>
<br>
<br>
<table width="100%" height="482" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td height="482" valign="top" bgcolor="#FFFFFF">      
      <table width="1002" height="264" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td width="365" valign="top">
          
          <table width="300" height="335" border="0" align="center" cellpadding="0" cellspacing="0" class="border_4">
            <tr>
              <td height="333" valign="top" bgcolor="#ededed">
              <%--用户登录开始 --%>
              <div id="login">
	                <table width="280" border="0" align="center" cellpadding="0" cellspacing="0" class="bottom_border">
	                <tr>
	                  <td height="48"><div align="center"><span class="login_tt">登录考核系统 </span></div></td>
	                </tr>
	              </table>
	                <br />
	                <form name="f" id="f" action="${ctx}/static/j_spring_security_check" method="post"> 
	                <table width="250" height="159" border="0" align="center" cellpadding="0" cellspacing="0">
	                 <tr>
				         <td colspan ="3" height="26">
				          <c:if test="${not empty param.login_error}">
								<div class="errors">
									<c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}"/>.
								</div>
						  </c:if>
								     
				         </td>
				    </tr>
	                <tr>
	                  <td width="60" class="font_tt">用户名：</td>
	                  <td width="190"><span class="font_tt">
	                    <input name="j_username" id="j_username" type="text" value="jjzd" class="inputbb required" size="24" style="margin:2px;"/>
	                  </span></td>
	                </tr>
	                <tr>
	                  <td class="font_tt">密&nbsp;&nbsp; 码：</td>
	                  <td><span class="font_tt">
	                    <input name="j_password" type="password" value="111111" class="inputbb required" size="24"  style="margin:2px;"/>
	                  </span></td>
	                </tr>
	                <tr>
	                  <td height="37"><div align="right"></div></td>
	                  <td><input type="checkbox" name="_spring_security_remember_me" />
	                   &nbsp; 记住我</td>
	                </tr>
	                <tr>
	                  <td height="48">&nbsp;</td>
	                  <td>
	                  <input type="submit" value="登      录" class="btn-blue"/>
	                  </td>
	                </tr>
	              </table>
	                <br />
	                 </form>
                </div>
                <%--用户注册开始 --%>
                <div id="divReg" style="display:none;">
	                <table width="280" border="0" align="center" cellpadding="0" cellspacing="0" class="bottom_border">
	                <tr>
	                  <!--<td height="48"><div align="center"><span class="login_tt">注册 </span></div></td>
	                --></tr>
	              </table>
	                <br />
	                <s:form id="reg" action="/reg/save.htm" method="POST">
	              <table width="290" height="192" border="0" align="center" cellpadding="0" cellspacing="0">
	              <tr>
	                <td width="75" class="font_tt" style="padding-left:10px;">用&nbsp; 户&nbsp; 名：</td>
	                <td width="217">
	                    <s:textfield name="user.loginId" id="username" cssClass="inputbb required" style="width:130px;" />
	                    <span style="color:red"> * </span>
	                </td>
	              </tr>
	              <tr>
	                <td width="75" class="font_tt" style="padding-left:10px;">密&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 码：</td>
	                <td>
	                  <s:password name="user.password" id="password" cssClass="inputbb required" style="width:130px;" />
	                  <span style="color:red"> * </span>
	                </td>
	              </tr>
	              <tr>
	                <td width="75" class="font_tt" style="padding-left:10px;">确认密码：</td>
	                <td>
	                 <s:password name="user.confirmPwd" id="repassword" cssClass="inputbb required" style="width:130px;" />
	                 <span style="color:red"> * </span>
	                </td>
	              </tr>
	              <tr>
	                <td width="75" class="font_tt" style="padding-left:10px;">电子邮箱：</td>
	                <td>
	                  <s:textfield name="user.email" id="email" cssClass="inputbb required email" style="width:130px;" />
	                  <span style="color:red"> * </span>
	                </td>
	              </tr>
	              
	              <tr>
	                <td colspan="2">&nbsp;</td>
	                </tr>
	            </table>
	              <br />
	                  <table width="290" height="32" border="0" align="center" cellpadding="0" cellspacing="0">
	                    <tr>
	                      <td align="center">
	                           <input type="submit" class="btn-blue" value="提交"/>                                  
	                      </td>
	                    </tr>
	              </table>
              </s:form>
	                <br />
                </div>
                 <%--密码找回开始 --%>
                <div id="findPwdX" style="display:none;">
                  <table width="280" border="0" align="center" cellpadding="0" cellspacing="0" class="bottom_border">
	                <tr><!--
	                  <td height="48"><div align="center"><span class="login_tt">找回密码 </span></div></td>
	                --></tr>
	              </table>
	                <br />
                <form action="${ctx}/restorePassword/findByAjax.htm" method="post" id="findPwd">
	              <table width="290" height="58" border="0" align="center" cellpadding="0" cellspacing="0">
		          
	               <tr>
	                <td width="75" class="font_tt" style="padding-left:10px;">用&nbsp;户&nbsp;名：</td>
	                <td width="200" style="padding:3px;">
	                  <input name="loginId" id="loginId" type="text" class="inputbb required" style="width:130px;" />
	                  <span style="color:red"> * </span>
	                </td>
	              </tr>
	              <tr>
	                <td width="75" class="font_tt" style="padding-left:10px;">邮箱地址：</td>
	                <td width="200"  style="padding:3px;">
	                  <input name="emailAddr" id="emailAddr" type="text" class="inputbb required" style="width:130px;" />
	                  <span style="color:red"> * </span>
	                </td>
	              </tr>
	              <tr>
	                <td width="75" class="font_tt" style="padding-left:10px;">验&nbsp;证&nbsp;码：</td>
	                <td width="200"  style="padding:3px;">
	                  <input name="captcha" id="captchaTxt" type="text" class="inputbb required" style="width:130px;" onfocus="getCaptcha(this)" />
	                  <span style="color:red"> * </span>      
	                  <span id="captcha" style="cursor:pointer;overflow:hidden;vertical-align:middle;"></span><span id="captchaInfo"></span>            
	                </td>
	              </tr>
	              <tr>
	                 <td colspan="2" align="center" style="padding:3px;">
	                    <input type="submit" class="btn-blue" value="提交" style="margin:40px 0px 30px; 0px;"/>
	                 </td>
	              </tr>
	            </table>
	            </form>
                </div>
                <%--
                <table width="280" height="24" border="0" align="center" cellpadding="0" cellspacing="0">
                  <tr>
                    <td><div align="center">
                    <!--<a href="javascript:;"  onclick="findPwd()">
                                                    忘记密码？</a>
                    &nbsp; 
                    --><!--<a href="javascript:;" onclick="regX()">注册 </a></div></td>
                  --></tr>
                </table>
                --%>
                </td>
            </tr>
          </table></td>
        </tr>
      </table>
    <br /></td>
  </tr>
</table>
<br></br>

<script type="text/javascript">
$(function() {
 $('#f').validate(); 
 $('#reg').validate(); 
 $('#j_username').focus();
 $('#reg').ajaxForm({
	 success: function(data) {
		 alert("注册成功，请使用您的账户登录。");
		 $('#login').show();
		 $('#divReg').hide();
		 $('#findPwdX').hide();
	 }
 });
 $('#findPwd').ajaxForm( {
	 success: function(data) {
		 alert("请登录您的注册邮箱，查询新密码。");
		 $('#login').show();
		 $('#divReg').hide();
		 $('#findPwdX').hide();
	 }
 });
});
function regX() {
	$('#login').hide();
	$('#divReg').show();
	$('#findPwdX').hide();
}
function findPwd() {
	$('#findPwdX').show();
	$('#login').hide();
	$('#divReg').hide();
}
function getCaptcha(obj) {
	if(obj != null && $('#captcha').html() != '') { //当焦点进入输入框，只获取一次验证码
		return;
	}
	//$('#_jcaptcha_error_id').hide();
	$('#captcha').html('<img src="${ctx}/captcha/' + Math.round(Math.random() * 10) + '" onclick="getCaptcha()" title="点击重新获取" align="middle"/>');
	$('#captchaInfo').html('点图片重新获取');
}
</script>
</body>
</html>
