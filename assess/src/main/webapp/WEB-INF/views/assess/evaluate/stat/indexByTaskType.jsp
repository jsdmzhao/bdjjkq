<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<html>
<head>
<style type="text/css">
td {padding:3px;}
</style>
<title></title>
<%@include file="/common/ec.jsp" %>
<%@include file="/common/meta.jsp" %>

</head>
<body>
<div class="x-panel-header">考核数据环比</div>
<div class="x-toolbar">
<table width="99%">
  <tr>
   <td>
   
   <s:form name="frm" id="frm" namespace="/assess/evaluate/stat" action="linkRelatieByTaskType" theme="simple">
 <table> <tr>
 <td>
							考核标准类别：
							<s:select list="taskTypes" id="taskType" headerKey="" headerValue="请选择" 
                     	name="taskType.id" listKey="id" listValue="name" cssClass="m_t_b" 
                     	cssStyle="width:200px;padding-left:2px;"  ></s:select>
						</td> 
						<td>
		<td>考核单位:<s:select list="deptCodeList" id="deptCodeList" headerKey="" headerValue="请选择" 
                     	name="deptCode" listKey="key" listValue="value"  cssClass="m_t_b" 
                     	cssStyle="width:200px;padding-left:2px;"  ></s:select></td>
		
    <td><input type="submit" value="统计" class="button" /></td> 
    </tr>
    </table>
    </s:form>    
    </td> 
      
  </tr>
</table>
</div>
</body>
</html>