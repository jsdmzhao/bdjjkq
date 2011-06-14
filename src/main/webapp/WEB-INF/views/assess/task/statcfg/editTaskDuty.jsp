<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="/common/taglibs.jsp" %>
<html>
<head>
<%@include file="/common/meta.jsp" %>
<title></title>
<%@include file="/common/validator.jsp" %>
<%@include file="/common/ec.jsp" %>
<%@include file="/common/extjs.jsp" %>
<script type="text/javascript" src="${ctx}/scripts/jquery.form.js"></script>
<style type='text/css' rel='stylesheet'>
.fakelink {color: #0000cc; cursor: pointer; white-space: nowrap;}
</style>
<style type="text/css">
.transgressTypeTitle{
	text-align:left;
	font-size:18px;
	font-weight:bold;
	color:#0066CC;
}
.transgressActionCheckBox{
	text-align: left;
}
.sstTable td{
    border-collapse: collapse;
    margin-left:-9px;
    border:solid 1px #97B7E7;
}
.clossBtn{
	margin-left: 20px;
	
}
</style>
</head>
<body>
<div class="x-panel">
    <div class="x-panel-header">日常勤务管理</div>
    <div class="x-toolbar" style="text-align: right;">
		<a href="taskDutyIndex.htm">管理首页</a>
    </div>
	<s:form id="saveFrm" action="saveTaskDuty" method="post">
	<s:hidden id="model.id" name="model.id"/>
	<table width="800px" align="center">
		<tr>
			<td align="center">
			<fieldset> 
              <legend>日常勤务信息</legend>
                <table cellpadding="3" cellspacing="2" width="100%" id="formTable">
                  <tr>
                     <td align="right" width="20%">名&nbsp;&nbsp;&nbsp;&nbsp;称：</td>
                     <td align="left" width="80%" colspan="6"  >
                     	<s:textfield name="model.name"  cssStyle="width:597px;" cssClass="required"></s:textfield>
                     </td>
                     
                  </tr>   
                  <c:forEach items="${model.taskDutyDetails}" var="detail">
                  	<tr>
                  		<td align="right" width="20%">评分项目：</td>
	                     <td align="left" width="30%">
	                     	<s:textfield  name="detail.name" cssStyle="width:250px;"></s:textfield>
	                     	<s:hidden name="detail.id"></s:hidden>
	                     </td>
	                     <td align="right" width="10%" >加/减分：</td>	                     
	                     <td align="left" width="10%">
		                      <select name="detail.addOrDecrease" >
		                      	<option value="">请选择</option>
		                     	<option value="0">减分</option>
		                     	<option value="1">加分</option>
		                     </select>
	                     </td>
	                     <td align="right" width="10%">分值：</td>
	                     <td align="left" width="8%"><s:textfield name="detail.point"></s:textfield></td>
                  	</tr>
                  </c:forEach>                                                                           
                  <tr>
                  		<td align="right" width="20%">评分项目：</td>
	                     <td align="left" width="30%">
	                     	<s:textfield id="dutyItemName1" name="model.dutyItemName1" cssStyle="width:250px;"></s:textfield>
	                     	<s:hidden name="detail.id"></s:hidden>
	                     </td>
	                     <td align="right" width="10%" >加/减分：</td>	                     
	                     <td align="left" width="10%">
		                     <s:select list="addOrDecreaseMap" value="model.addOrDecrease1"  headerKey="" headerValue="请选择" name="model.addOrDecrease1" ></s:select>
	                     </td>
	                     <td align="right" width="10%">分值：</td>
	                     <td align="left" width="8%"><s:textfield id="dutyItemPoint1" name="model.dutyItemPoint1"></s:textfield></td>
	                     <td id="labelTdRow"><u id="labelURow" class="fakelink" onclick="addLabelRow(this)"></u></td>
                  	</tr>
                 <tr id="dutyItem2" >
                  		<td align="right" width="20%">评分项目：</td>
	                     <td align="left" width="30%">
	                     	<s:textfield id="dutyItemName2" name="model.dutyItemName2" cssStyle="width:250px;"></s:textfield>
	                     	<s:hidden name="detail.id"></s:hidden>
	                     </td>
	                     <td align="right" width="10%" >加/减分：</td>	                     
	                     <td align="left" width="10%">
		                     <s:select list="addOrDecreaseMap" value="model.addOrDecrease2"  headerKey="" headerValue="请选择" name="model.addOrDecrease2" ></s:select>
	                     </td>
	                     <td align="right" width="10%">分值：</td>
	                     <td align="left" width="8%"><s:textfield  id="dutyItemPoint2" name="model.dutyItemPoint2"></s:textfield></td>
	                     <td id="labelTdRow"><input type="checkbox" id="checkDutyItemName2" onclick="enableIt('dutyItem2',this)">启用 </td>
                  	</tr>
                  	<tr id="dutyItem3">
                  		<td align="right" width="20%">评分项目：</td>
	                     <td align="left" width="30%">
	                     	<s:textfield id="dutyItemName3" name="model.dutyItemName3" cssStyle="width:250px;"></s:textfield>
	                     	<s:hidden name="detail.id"></s:hidden>
	                     </td>
	                     <td align="right" width="10%" >加/减分：</td>	                     
	                     <td align="left" width="10%">		                               	             
							<s:select list="addOrDecreaseMap" value="model.addOrDecrease3"  headerKey="" headerValue="请选择" name="model.addOrDecrease3" ></s:select>
	                     </td>
	                     <td align="right" width="10%">分值：</td>
	                     <td align="left" width="8%"><s:textfield  id="dutyItemPoint3" name="model.dutyItemPoint3"></s:textfield></td>
	                     <td id="labelTdRow"><input type="checkbox" id="checkDutyItemName3" onclick="enableIt('dutyItem3',this)">启用 </td>
                  	</tr>                                                    
                 </table> 
              </fieldset>
              <table width="100%" style="margin-bottom:10px;">
				<tr>
					<td style="text-align:center;">
					 <s:submit value="保存" cssClass="button"/> 				
					<%--	<input type="button" value="保存" class="button" onclick="saveIt()"></input>	--%>			
						<s:reset value="重置" cssClass="button"/>
                    </td>
              	</tr>
              </table>
			</td>
		</tr>
	</table>
	</s:form>
</div>

<script type="text/javascript">
$(document).ready(
		<c:if test="${empty model.dutyItemName2}">
		 function(){
			 	//$("#dutyItemName2").children().attr("disabled","disabled")
				//$("#dutyItemName2").children().attr("disabled","disabled");
				$("#dutyItem2" +" select").attr("disabled","disabled");
				$("#dutyItem2" +" select option").attr("disabled","disabled");
				$("#dutyItem2:" +" input not([type='checkbox'])").attr("disabled","disabled");
				//$("#checkDutyItemName2").attr("disabled","");				
			   }
        </c:if>
  
);
$(document).ready(
		<c:if test="${empty model.dutyItemName3}">
		 function(){
			 	$("#dutyItem3").children().attr("disabled","disabled")
				$("#dutyItem3").children().attr("disabled","disabled");
				$("#dutyItem3" +" select").attr("disabled","disabled");
				$("#dutyItem3" +" select option").attr("disabled","disabled");
				$("#dutyItem3" +" input").attr("disabled","disabled");
				$("#checkDutyItemName3").attr("disabled","");
			   }
        </c:if>
  
);
function enableIt(arg1,arg2){
	if(arg2.checked){
		$("#"+arg1).children().attr("disabled","");
		$("#"+arg1 +" select").attr("disabled","");
		$("#"+arg1 +" select option").attr("disabled","");
		$("#"+arg1 +" input").attr("disabled","");
	}else{
		$("#"+arg1).children().attr("disabled","disabled")
		$("#"+arg1).children().attr("disabled","disabled");
		$("#"+arg1 +" select").attr("disabled","disabled");
		$("#"+arg1 +" select option").attr("disabled","disabled");
		$("#"+arg1 +" input").attr("disabled","disabled");
		arg2.disabled = "";
	}
	vali();
}
function vali(){
$("#saveFrm").validate({
	rules: {
		'model.name':  {
			required : true
		},
		'model.dutyItemName1':  {
			required : true
		},
		'model.dutyItemName2':  {
			required : true
		},
		'model.dutyItemName3':  {
			required : true
		},
		'model.addOrDecrease1':{
			required : true
		},
		'model.addOrDecrease2':{
			required : true
		},
		'model.addOrDecrease3':{
			required : true
		}
		,'model.dutyItemPoint1':{
			required : true,
			number   : true,
			min      : 0
		}
		,'model.dutyItemPoint2':{
			required : true,
			number   : true,
			min      : 0
		},
		'model.dutyItemPoint3':{
			required : true,
			number   : true,
			min      : 0
		}
	},
	messages: {
		'model.name': {
			required: "请输入名称"			
		},
		'model.dutyItemName1':  {
			required : "请输入名称"
		},
		'model.dutyItemName2':  {
			required : "请输入名称"
		},
		'model.dutyItemName3':  {
			required : "请输入名称"
		},
		'model.addOrDecrease1':{
			required : "请选择加/减分"
		},
		'model.addOrDecrease2':{
			required : "请选择加/减分"
		},
		'model.addOrDecrease3':{
			required : "请选择加/减分"
		}
		,'model.dutyItemPoint1':{
			required : "请输入分值",
			number   : "请输入数字",
			min      : "请输入有效数字"
		}
		,'model.dutyItemPoint2':{
			required : "请输入分值",
			number   : "请输入数字",
			min      : "请输入有效数字"
		},
		'model.dutyItemPoint3':{
			required : "请输入分值",
			number   : "请输入数字",
			min      : "请输入有效数字"
		}
	}
});
}
$(function(){
	vali();
});
function addLabelRow(arg){
  var html = '<tr><td align="right" width="20%">评分项目：</td><td align="left" width="30%"><s:textfield name="detailItem" cssStyle="width:250px;"></s:textfield></td><td align="right" width="10%" >加/减分：</td><td align="left" width="10%"><select name="addOrDecrease"><option value="">请选择</option><option value="0">减分</option><option value="1">加分</option></select></td><td align="right" width="8%" >分值：</td><td align="left" width="8%"><s:textfield name="detail.point" cssStyle="width:60px;"></s:textfield></td><td id="labelTdRow"></td></tr>';
  $('#formTable').append(html);
  vali();
}


function initTransgressActionOptions(secondLevelTypeId,secondLevelTypeDescn){
	if(secondLevelTypeId  == '' || $('#SST'+secondLevelTypeId).length > 0){return;}
	$.ajax({
		url:'${ctx}/assess/transgress/statcfg/statItem/getTransgressActionsBySecondType.htm',
		data:{'secondLevelTypeId':secondLevelTypeId},
		dataType:'json',
		success:function(data){
			var tableId = '"'+'SST'+secondLevelTypeId+'"';
			var rows = data.length;
			//var trId = $('#transgressActions>tbody>tr:last').attr("id");
			//trId++;
			//var	str = "<tr id = '"+trId+"'><td width='30%'></td></tr>";
			var html = [];
			html.push("<tr><td><table class='sstTable' id='SST"+secondLevelTypeId+"' width='100%'><tr><td class='transgressTypeTitle'>"+secondLevelTypeDescn+"&nbsp;&nbsp;&nbsp;&nbsp;<input type='checkbox'  onclick='checkAll("+tableId+",this)'>全选<input type='hidden' disabled='disabled' name='secondLevelTypeIds' value='"+secondLevelTypeId+"'/><img  class='clossBtn' src='${ctx}/images/icons/delete.gif' onclick='closeTable("+tableId+")'>关闭</td></tr>");
			$.each(data,function(idx,item){
				html.push("<tr><td align='left'><input type='checkbox' name='transgressActionIds' value='"+item.id+"'/>"+item.code+":"+item.descns + "</td></tr>");
			});
			html.push("</table></td></tr>");
			
			$('#transgressActions').append(html.join(''));
		}
	});
}
/**
 * 根据选中的子类别,列出该子类别下的所有违法行为
 */
 
</script>
<script type="text/javascript">
/*
$(function(){	
    $('#saveFrm').ajaxForm({    	
   	 success:function(data) {   		 
   		
   		Ext.my().msg('', '保存统计条件信息成功' );
   		pause(2000); 
   		location.href = '${ctx}/assess/transgress/statcfg/statItem/index.htm';
   		
   	 },
   	 error:function(xhr) {
   		 Ext.my().msg('', '保存统计条件信息失败' );
   	 }
    });
});*/
function pause(milliSecond){
	var now = new Date();
	var exitTime = now.getTime()+milliSecond;
	while(true){
		now = new Date();
		if(now.getTime()>exitTime){
			return;
		}
	}
}

function closeTable(arg){
	 $("#"+arg).parent().parent().remove();
}
function saveIt(){
	var checkArr ;
	checkArr = document.getElementsByName("transgressActionIds");
	if(checkArr.length==0){
		alert("请选择违法行为!");
		return;
	}
	for(var i =0;i<checkArr.length;i++){
		if(checkArr[i].checked){
			document.getElementById("saveFrm").submit();
			return;
		}
	}
	alert("请选择违法行为!");
	return;
}
</script>
</body>
</html>