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
<script type="text/javascript" src="${ctx}/amcharts/swfobject.js"></script>
<script type="text/javascript">
function strParser(){
	var csvData = "${csvData}";
	//alert(csvData);
	var itemList = csvData.split("\n");
	var staticInfo = "";
	var itemNums = new Array();
	
	for(var i = 0; i < itemList.length - 1; i++){
		var item = itemList[i].split(";");
		itemNums[i] = item[1];
		staticInfo += '<b>' + item[0] + '</b><b>' + item[1] + '分</b>' + '；';
		}
	//alert(staticInfo);
	document.getElementById("staticInfo").innerHTML = staticInfo;
}
</script>
</head>
<body onload="strParser()">
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
<div style="margin-left: auto;margin-right: auto;margin-top: 30px; margin-bottom: 20px;text-align: center;">
	<font size="5" face="宋体" >${title }</font>	
</div>
<table width="100%">
	<tr>
		<td align="center">
			<div id="flashcontent"><strong>你需要更新你的flash了。</strong></div>
		</td>
	</tr>
	<tr>
		<td align="left">
			<p style="line-height: 20px"><font size="2" face=宋体><br><span>&nbsp;&nbsp;&nbsp;&nbsp;</span><span id="staticInfo"></span><span></span></font></p>
		</td>
	</tr>
</table>

<script type="text/javascript">
	// <![CDATA[		
	var so = new SWFObject("${ctx}/amcharts/amcolumn.swf", "amcolumn", "700", "400",
			"8", "#FFFFFF");
	so.addVariable("path", "${ctx}/amcharts/");
	so.addVariable("settings_file",
			encodeURIComponent("${ctx}/amcharts/linkRelatie.xml"));
	//alert("${csvData}");
	so.addVariable("chart_data", "${csvData}");
	so.write("flashcontent");
	// ]]>
	
</script>

</body>
</html>