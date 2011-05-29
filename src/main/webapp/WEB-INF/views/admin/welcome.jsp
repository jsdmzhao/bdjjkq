<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%  
 String path = request.getContextPath();  
 String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";  
 %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<base href="<%=basePath%>">
<link href="styles/css/global_c.css" rel="stylesheet" style="text/css">
<title>欢迎页面</title>
</head>
<script type="text/javascript">
<!--
window.onload=function(){
	var h=parent.document.body.offsetHeight-600;
	if(h>0) {
		document.getElementById("blank").style.height=(h/2);
		}
};
-->
</script>
<body>
<div id="blank"></div>
<div style="width:839px;height:546px; background:url(ResRoot/htdl/images/lsbj.jpg) repeat-x;margin:auto;">
  <div style="width:631px;height:248px;margin:auto;padding-top:90px;">
      <div style="width:150px;height:26px;"><img src="ResRoot/htdl/images/zt.jpg"></img></div>
      <div class="clearIt"></div>
      <div style="width:628px;height:217px;padding-top:2px;"><img src="ResRoot/htdl/images/hydl.jpg"></div>
  </div>
</div>
</body>
</html>