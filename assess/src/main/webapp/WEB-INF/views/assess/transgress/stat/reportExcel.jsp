<%@ page language="java"%>
<%@ page contentType="application/vnd.ms-excel; charset=UTF-8" %>

<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	//只写上contentType="application/vnd.ms-excel; charset=gb2312"可以弹出"文件下载"对话框
	
	//1.选择"打开"，在浏览器打开
	//response.setHeader("Content-disposition","inline; filename=test1.xls");
	//2.选择"打开"，在Excel中打开
	//"test2.xls"是文件名，好像只能写英文名
	response.setHeader("Content-disposition","attachment; filename=85CostCalculate.xls"); 
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns:o="urn:schemas-microsoft-com:office:office"
xmlns:x="urn:schemas-microsoft-com:office:excel"
xmlns="http://www.w3.org/TR/REC-html40">
<head>
<meta name=ProgId content=Excel.Sheet>
<meta name=Generator content="Microsoft Excel 11">
<!-- 添加以下XML数据解决直接打开后表格外边没有边框 -->
<!--[if gte mso 9]><xml>
 <x:ExcelWorkbook>
  <x:ExcelWorksheets>
   <x:ExcelWorksheet>
    <x:Name>Excel报表</x:Name>
    <x:WorksheetOptions>
     <x:DefaultRowHeight>285</x:DefaultRowHeight>
     <x:Selected/>
     <x:ProtectContents>False</x:ProtectContents>
     <x:ProtectObjects>False</x:ProtectObjects>
     <x:ProtectScenarios>False</x:ProtectScenarios>
    </x:WorksheetOptions>
   </x:ExcelWorksheet>
 </x:ExcelWorkbook>
</xml><![endif]-->

<script type="text/javascript"
	src="${ctx}/dwr/interface/CostCollection85Action.js"></script>
<style type="text/css">
body{font-size:16px}
.cost td{ 
	font-family:宋体;
	font-size:10pt;
	border:solid 0.5pt #000;
	height:17px
}
.cost th{
	font-family:宋体;
	font-size:10pt;
	font-weight:bold;
	border:solid 0.5pt #000;
	text-align:center
	}
.item td{ 
	font-family:宋体;
	font-size:10pt;
	border:solid 0.5pt #000;
	height:17px;
	width:160px;
	text-align:right
}
.item th{
	font-family:宋体;
	font-size:10pt;
	font-weight:bold;
	border:solid 0.5pt #000;
	text-align:center
	}
.title{
	font-family:宋体;
	font-size:12pt;
	font-weight:bold;
	background:#C0C0C0;
	text-align:center
}
.date{
	font-family:宋体;
	font-size:12pt;
	font-weight:bold
}
.grey{
	background:#C0C0C0;
	font-weight:bold;
	text-align:right
	}
	
.red{
	background:#FF6600;
	font-weight:bold;
	text-align:right
	}
.yellow{
	background:#FF6600;
	font-weight:bold;
	text-align:right
	}
.left{
	text-align:left
	}
.right{
	text-align:right
	}
.v
	{mso-style-parent:style0;
	text-align:center;
	vertical-align:middle;
	layout-flow:vertical;}
.toolbar table{
	border-style:none;
	}
.toolbar td{
	border-style:none}
body,td,th {
	font-size: 9px;
}
</style>

</head>

<body>
<div class="x-panel-header" style="width:2000px;">81车间成本计算汇总表</div>
<div style="width:2000px;" align="left">

<table>
<tr>
<td width="257" valign="top">
<table class="cost" width="100%" border="1"
	style="border-collapse:collapse;border:solid 1px #000">
	<tr class="grey">
		<th colspan="4" align="center"><font color="blue">【${beginYear}年  ${beginMonth }月 -- ${endMonth} 月】</font>成本项目</th>
		
	</tr>

	<tr>
		<td width="4%" rowspan="14" class="v" style="writing-mode: tb-rl">变动成本</td>
		<td colspan="3" class="left">原材料</td>	
	</tr>
	<tr>
		<td colspan="3" class="left">辅助材料</td>	
	</tr>		
	<tr>
		<td colspan="3" class="left">动力</td>
		
	</tr>
		<tr>
		<td colspan="3" class="left">外部加工费</td>
		
	</tr>
	<tr>
		<td width="5%" rowspan="9" class="v" style="writing-mode: tb-rl">变动制造费用</td>
		<td colspan="2">办公费</td>
	</tr>
	<tr>
		<td colspan="2">差旅费</td>
	</tr>
	<tr>
		<td colspan="2">水电费</td>
	</tr>
	<tr>
		<td colspan="2">修理费</td>
	</tr>
	<tr>
		<td colspan="2">机物料消耗</td>
	</tr>
	<tr>
		<td colspan="2">低值易耗品摊销</td>
	</tr>
	<tr>
		<td colspan="2">劳动保护费</td>
	</tr>
	<tr>
		<td colspan="2">其他</td>
	</tr>
	<tr>
		<td colspan="2">变动制造费用小计</td>
	</tr>
	<tr>
		<td colspan="3" >变动成本小计</td>
	</tr>
    <tr>
		<td rowspan="13" class="v" style="writing-mode: tb-rl">固定成本</td>
		<td colspan="3">工资</td>
		
	</tr>
	<tr>
		<td colspan="3">福利费</td>	
	</tr>
	<tr>
		<td colspan="3">误餐费</td>
	</tr>
	<tr>
		<td rowspan="8" class="v" style="writing-mode: tb-rl">固定制造费用</td>
		<td colspan="2">工资</td>
	</tr>
	<tr>
		<td colspan="2">福利费</td>	
	</tr>
	<tr>
		<td colspan="2">误餐费</td>
	</tr>
	<tr>
		<td width="7%" rowspan="4" class="v">折旧</td>
		<td width="33%">机器设备折旧</td>
	</tr>
	<tr>
		<td>房屋建筑物折旧</td>
	</tr>
	<tr>
		<td>其他固定资产折旧</td>
	</tr>
	<tr>
		<td>折旧小计</td>
	</tr>
	<tr>
		<td colspan="2">固定制造费用小计</td>
	</tr>
	<tr>
		<td colspan="3"><a href="#" onclick="onViewDetail()"
			title="查看其他固定成本明细"> <font color="blue">其他固定成本</font></a></td>
		
	</tr>
	<tr>
		<td colspan="3">固定成本小计</td>
	</tr>
	<tr>
		<td colspan="4" align="center">成本合计</td>		
	</tr>
	<tr>
		<td colspan="4" align="center">工时</td>		
	</tr>
	<tr>
		<td colspan="4" align="center">产量</td>		
	</tr>
</table>
</td>

<s:iterator value="result">
<td width="130" valign="top" align="center">
	<table class=item width="100%" border="1" style="border-collapse:collapse;border:solid 1px #000">
		<tr align="center">
		<th class="grey">
		${plateName}${productName }
		</th>
		  
		</tr>
		<tr>
			<td height="17px">
				<fmt:formatNumber value="${rawMaterials}" pattern="###,##0.00" />
			</td>
		</tr>
		
		<tr>
			<td height="17px">
				<fmt:formatNumber value="${accessories}" pattern="###,##0.00" />
			</td>
		</tr>						
		<tr>
			<td height="17px">
				<fmt:formatNumber value="${power}" pattern="###,##0.00" />
			</td>
		</tr>
		<tr>
			<td height="17px">
				<fmt:formatNumber value="${exteriorWorkCosts}" pattern="###,##0.00" />
			</td>
		</tr>
		<tr>
			<td height="17px">
				<fmt:formatNumber value="${officeCosts}" pattern="###,##0.00" />
			</td>
		</tr>
		<tr>
			<td height="17px">
				<fmt:formatNumber value="${travelCosts}" pattern="###,##0.00" />
			</td>
		</tr>
		<tr>
			<td height="17px">
				<fmt:formatNumber value="${hydropowerCosts}" pattern="###,##0.00" />
			</td>
		</tr>
		<tr>
			<td height="17px">
				<fmt:formatNumber value="${repairCosts}" pattern="###,##0.00" />
			</td>
		</tr>
		<tr>
			<td height="17px">
				<fmt:formatNumber value="${materialsCosts}" pattern="###,##0.00" />
			</td>
		</tr>
		<tr>
			<td height="17px">
				<fmt:formatNumber value="${lowConsumption}" pattern="###,##0.00" />
			</td>
		</tr>
		<tr>
			<td height="17px">
				<fmt:formatNumber value="${laborCosts}" pattern="###,##0.00" />
			</td>
		</tr>
		<tr>
			<td height="17px">
				<fmt:formatNumber value="${otherCosts}" pattern="###,##0.00" />
			</td>
		</tr>
		<tr>
			<td height="17px" class="yellow">
				<fmt:formatNumber value="${makeCostsTotal}" pattern="###,##0.00" />
			</td>
		</tr>
		<tr>
			<td height="17px" class="yellow">
				<fmt:formatNumber value="${changesCostsTotal}" pattern="###,##0.00" />
			</td>
		</tr>
		<tr>
			<td height="17px">
				<fmt:formatNumber value="${costWage}" pattern="###,##0.00" />
			</td>
		</tr>
		<tr>
			<td height="17px">
				<fmt:formatNumber value="${costWelfare}" pattern="###,##0.00" />
			</td>
		</tr>
		<tr>
			<td height="17px">
				<fmt:formatNumber value="${costMisusemeals}" pattern="###,##0.00" />
			</td>
		</tr>
		<tr>
			<td height="17px">
				<fmt:formatNumber value="${makeWage}" pattern="###,##0.00" />
			</td>
		</tr>
		<tr>
			<td>
				<fmt:formatNumber value="${makeWelfare}" pattern="###,##0.00" />
			</td>
		</tr>
		<tr>
			<td>
				<fmt:formatNumber value="${makeMisusemeals}" pattern="###,##0.00" />
			</td>
		</tr>
		<tr>
			<td>
				<fmt:formatNumber value="${equipDepreciation}" pattern="###,##0.00" />
			</td>
		</tr>
		<tr>
			<td>
				<fmt:formatNumber value="${buildingDeperciation}" pattern="###,##0.00" />
			</td>
		</tr>
		<tr>
			<td>
				<fmt:formatNumber value="${otherDeperciation}" pattern="###,##0.00" />
			</td>
		</tr>
		<tr>
			<td class="yellow">
				<fmt:formatNumber value="${deperciationTotal}" pattern="###,##0.00" />
			</td>
		</tr>
		<tr>
			<td class="yellow">
				<fmt:formatNumber value="${fixedCostsTotal}" pattern="###,##0.00" />
			</td>
		</tr>
		<tr>
			<td class="yellow">
				<fmt:formatNumber value="${otherCost}" pattern="###,##0.00" />
			</td>
		</tr>
		<tr>
			<td class="yellow">
				<fmt:formatNumber value="${fixedCostTotal}" pattern="###,##0.00" />
			</td>
		</tr>
		<tr>
			<td class="yellow">
				<fmt:formatNumber value="${costTotal}" pattern="###,##0.00" />
			</td>
		</tr>
		<tr>
			<td class="grey">
				<fmt:formatNumber value="${workingHours}" pattern="###,##0.00" />
			</td>
		</tr>
		<tr>
			<td class="grey">
				<fmt:formatNumber value="${production}" pattern="###,##0.00" />
			</td>
		</tr>
		
	</table>
</td>
</s:iterator>
</tr>
</table>
</div>
</body>

</html>
