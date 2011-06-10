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
<%@include file="/common/extjs.jsp" %>
<%@include file="/common/meta.jsp" %>
<script type="text/javascript" src="${ctx}/scripts/jqueryui/jquery-ui.js"></script>
<style type="text/css">
.ecSide .headZone {
    background-color: #fff;
    border-color: #333;
}

/*表格斜线*/ 
.out{ 
   border-top:50px #e3e3e3 solid;/*上边框宽度等于表格第一行行高*/ 
   width:0px;/*让容器宽度为0*/ 
   height:0px;/*让容器高度为0*/ 
   border-left:100px #fff solid;/*左边框宽度等于表格第一行第一格宽度*/     
   position:relative;/*让里面的两个子容器绝对定位*/ 
} 
b{font-style:normal;display:block;position:absolute;top:-40px;left:-40px;width:35px;} 
em{font-style:normal;display:block;position:absolute;top:-25px;left:-90px;width:55x;} 

</style>
</head>
<body>
<s:form id="removeForm" action="role/remove" method="POST"></s:form>
<div class="x-panel">
  <div class="x-panel-header">管理文章</div>
    <div class="x-toolbar" style="height:36px;">
      <table width="99%">
        <tr>
         <td style="padding:5px;"> 
          
         </td>
       </tr>
     </table>
   </div>   

   <div class="x-panel-body">
     <div style="margin-left:-3px;" align="center">
	<ec:table items="items" var="item" retrieveRowsCallback="process" sortRowsCallback="process" 
	action="export.htm"
	useAjax="true" doPreload="false"
	maxRowsExported="1000" 
	pageSizeList="20,50,100" 
	editable="false" 
	sortable="false"	
	rowsDisplayed="20"	
	generateScript="true"	
	resizeColWidth="true"	
	classic="false"	
	width="100%" 	
	height="500px"	
	minHeight="300" 
	showHeader="false"
	xlsFileName="export.xls"
	toolbarContent="navigation|pagejump|pagesize|export|refresh|extend|status"  
	>
    <ec:extendrow location="top">
        <tr>
           <td rowspan="2" align="center">
                <div class="out"> 
		            <b>类别</b> 
		            <em>姓名</em> 
		        </div>
           </td>
           <td colspan="2" align="center"><div style="color:#ff0000;">AB</div></td>          
           <td colspan="2" align="center">CD</td> 
        </tr>
        <tr>
           <td>A</td>
           <td>B</td>
           <td>C</td>
           <td>D</td>
        </tr>
    </ec:extendrow>
	<ec:row>
	   	<ec:column property="loginId" title="Name"></ec:column>
	   	<ec:column property="name" title="Nickname"></ec:column>
	   	<ec:column property="email" title="Email"></ec:column>
	   	<ec:column property="id" title="ID"></ec:column>
	   	<ec:column property="_x" title="x"><span style="color:#ff0000;">X</span></ec:column>
	</ec:row>
	<ec:extendrow location="bottom">
	   <tr>
	      <td colspan="3">紫檀，黄杨、犀牛角、象牙...</td>
	   </tr>
	</ec:extendrow>
	</ec:table>
  </div>
  </div>
  .ecSide .headZone
</div>
</body>
</html>