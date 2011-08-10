<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<style>
#table_info {width:360px; text-align:center;border:1px solid #ddd; border-width:0 0 1px 1px;}

#table_info th {background:#ececec;}
#table_info th,#table_info td {height:22px; border:1px solid #ddd; border-width:1px 1px 0 0;}
</style>

  

</head>
<body>
	<table id="table_info" align="center" width="60%">
		<caption>截至${year}年${month}月${date }日,保定交警支队考核结果</caption>
		<tr>
			<th>名次</th>
			<th>单位</th>
			<th>总分</th>
		</tr>
		<c:forEach items="${list}" var="er" varStatus="status">			
			<tr>
				<td>${status.index +1 }</td>
				<td>${er.dept.name}</td>
				<td>${er.total }</td>				
			</tr>
		</c:forEach>
	</table>
</body>
</html>