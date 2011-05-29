<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="/common/meta.jsp" %>
</head>
<body>

<fieldset>
   <legend><img src="${ctx}/images/icons/password_title.gif" border="0"></legend>
   <%@ include file="/common/messages.jsp"%>
   <div style="margin:10px;">
   已经成功的为您修改了密码，请登录您的邮箱(${emailAddr})获取新的密码。
   </div>
</fieldset>
</body>
</html>