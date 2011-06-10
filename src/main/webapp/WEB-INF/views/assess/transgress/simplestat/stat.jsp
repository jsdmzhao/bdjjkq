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
<%--不注释掉这行,时间控件不能用,真tmd郁闷 --%>
<%--@include file="/common/extjs.jsp"--%>
<%@include file="/common/meta.jsp" %>

<script type="text/javascript" src="${ctx}/scripts/jqueryui/jquery-ui.js"></script>
<%--<script type="text/javascript" src="${ctx }/scripts/wfcalendar/agenda.js"></script>
<script type="text/javascript"  src="${ctx }/scripts/wfcalendar/datetime.js"></script>
<script type="text/javascript"  src="${ctx }/scripts/wfcalendar/normal.js"></script>
<script type="text/javascript"  src="${ctx }/scripts/wfcalendar/plugins.js"></script>
<script type="text/javascript"  src="${ctx }/scripts/wfcalendar/plugins_time.js"></script>
<script type="text/javascript"  src="${ctx }/scripts/wfcalendar/plugins_timeSec.js"></script>--%>

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
<div class="x-panel">
  <div class="x-panel-header">违法统计</div>
    <div class="x-toolbar" style="height:26px;">
      <table width="99%" >
  	  <tr style="margin-top:  7px;"> 		
		
  	  </tr>
  	</table>
   </div>   

   <div class="x-panel-body">
     <div style="margin-left:-3px;" align="center">
	<ec:table items="items" var="item" retrieveRowsCallback="process" sortRowsCallback="process" 
	action="stat.htm"
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
	xlsFileName="勤务工作考核通报表.xls"
	toolbarContent="extend|status|refresh|export">

    <ec:extendrow location="top">
	  	<tr bgcolor="#FFFFFF" height="50px;">
	  		
	  		<td colspan="8" height="50px;"  >		  					
	 			<div align="center" style="float: left;width: 35%;vertical-align: bottom;height: 50px; padding-top: 30px;">${statDate }</div> 	
	  			<div align="center" style="font-size: 28;float: left;line-height: 50px;	">${transgressStatProperties.title }</div> 			
	  		</td>
	  	</tr>	  	
	  	<tr>
	  		<c:if test="${not empty( items)}">	  		
		  		<c:forEach var="i" begin="0" end="${fn:length(title)-1}">
		  			<c:choose>
						<c:when test="${i == 0}">
							<td align="center" >合计	</td>
						</c:when>					
						<c:otherwise>
							<td align="center" >${title[i]}</td>							
						</c:otherwise>
					</c:choose>
		  		</c:forEach>
	  		</c:if>
	  	</tr>
	  </ec:extendrow>
	<ec:row>			
			<c:forEach var="i" begin="0" end="${fn:length(title)-1}">
				<c:choose>
					<c:when test="${i == 0}">
						<ec:column width="280" property="合计" title="${title[i]}"
								style="text-align:center" sortable="false">
								${item[fn:length(title)-1]}
						</ec:column>
					</c:when>
					<c:when test="${i == 1}">
						<ec:column width="${600/(fn:length(title)-1)}" property="_s1" title="${title[i]}"
							style="text-align:center" sortable="false">
							${item[i-1]}
						</ec:column>
					</c:when>
					<c:otherwise>
						<ec:column width="${600/(fn:length(title)-1)}" property="_s3" title="${title[i]}"
							style="text-align:center" sortable="false">
							${item[i-1]}
						</ec:column>
					</c:otherwise>
				</c:choose>
				
			</c:forEach>			
		</ec:row>
		
		<ec:extendrow location="bottom" >
			<tr>
				<td colspan="${fn:length(title)}">				
					${transgressStatProperties.remark }							 
				</td>
			</tr>
		  
		  
		   <tr>		   
		   	<td colspan="${fn:length(title)==0?9:fn:length(title)}" width="800" align="right">
		   		统计日期区间: ${statBeginTime }  --  ${statEndTime }&nbsp;&nbsp;
		   	</td>
		   </tr>

		</ec:extendrow>

	</ec:table>
  </div>
  </div>
</div>
<script type="text/javascript">
function onStat(){
	var frmCustomStat = document.getElementById("customStatForm");
	var frmStat = document.getElementById("statForm");
	frmCustomStat.beginTime.value = frmStat.beginTime.value;
	frmCustomStat.endTime.value = frmStat.endTime.value;
	frmCustomStat.submit();
}
function statIt(){
	if(document.getElementById('beginTime').value >document.getElementById('endTime').value){
		alert('起始时间不能大于截至时间,请重新设置统计时间区间!');
		return;
	}
	$('#statForm').submit();
}
</script>
<s:form name="customStatForm" id="customStatForm" namespace="/assess/transgress/stat" action="reportExcel" theme="simple">
	<input type="hidden" name="beginTime"></input>	   
	<input type="hidden" name="endTime"></input>
</s:form>

</body>

</html>