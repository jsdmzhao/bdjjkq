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
    <div class="x-panel-header">考核标准管理</div>
    <div class="x-toolbar" style="text-align: right;">
		<a href="taskDutyIndex.htm">管理首页</a>
    </div>
	<s:form id="saveFrm" action="save" method="post">
	<s:hidden id="model.id" name="model.id"/>
	<table width="800px" align="center">
		<tr>
			<td align="center">
			<fieldset> 
              <legend>考核标准信息</legend>
                <table cellpadding="3" cellspacing="2" width="100%" id="formTable">
                  <tr>
                     <td align="right" width="20%">名&nbsp;&nbsp;&nbsp;&nbsp;称：</td>
                     <td align="left" width="80%" colspan="7"  >
                     	<s:textfield name="model.name"  cssStyle="width:597px;" cssClass="required"></s:textfield><span style="color: #ff0000;">*</span>
                     </td>
                     
                  </tr> 
                  <tr>
                  	<td align="right" width="20%">类&nbsp;&nbsp;&nbsp;&nbsp;别：</td>
                  	 <td align="left" width="80%" colspan="7"  >
                  	 <%--
                     	<s:select list="taskTypes" id="taskType" headerKey="" headerValue="请选择" 
                     	name="taskTypes" listKey="id" listValue="name" cssClass="m_t_b" 
                     	cssStyle="width:200px;padding-left:2px;"  ></s:select> --%>
                     <select name="model.taskType.id">
                     	<option value="">请选择</option>
                     	<c:forEach items="${taskTypes}" var="tt">
                     		<option value="${tt.id }" ${tt.id eq model.taskType.id ? 'selected="selected"' : '' }>${tt.name }</option>
                     		
                     	</c:forEach>
                     </select><span style="color: #ff0000;">*</span>
                     </td>
                  </tr>
                  <tr>
                  	 <td align="right" width="20%">评分细则：</td>
                  	 <td colspan="6"></td>
                     <td align="left"  >
                     	<u id="labelURow" class="fakelink" onclick="addLabelRow(this)">添加</u>
                     </td>
                  </tr>  
                  <c:forEach items="${model.taskDutyDetails}" var="detail" varStatus="status">
                  	<tr>
                  		<td align="right" width="25%">评分项目：</td>
	                     <td align="left" width="20%">
	                     <%-- <s:textfield  name="detail.name${status.index}" cssClass="detailNameClass" cssStyle="width:250px;"></s:textfield>--%>	                     	
	                     	<input type="text" name="detailNames${status.index}" value="${detail.name }" class="detailNameClass" style="width: 230px;"></input>	                     	
	                     	<input type="hidden" name="detailIds" value="${detail.id }"></input>
	                     </td>
	                     <td align="right" width="10%" >加/减分：</td>	                     
	                     <td align="left" width="10%">
		                      <select name="detailAddOrDecreases${status.index }" id="saveFrm_detail_addOrDecrease" class="detailAddOrDecreaseClass">
		                      	<option value="">请选择1</option>
		                      	<c:forEach items="${addOrDecreaseMap}" var="item">
		                      		<option value="${item.key}" ${item.key eq detail.addOrDecrease ? ' selected="selected" ': '' }>${item.value}</option>
		                      	</c:forEach>
							</select>
	                     </td>
	                     <td align="right" width="10%">分值：</td>
	                     <td align="left" width="8%"><input type="text" name="detailPoints${status.index}" value="${detail.point }" class="detailPointClass" style="width: 60px;"></input></td>
	                     <td align="left" width="18%">
	                     	<input type="hidden" name="decreaseLeaders" value="${detail.decreaseLeader }" id="decreaseLeaders${detail.id }"/>
	                     	<input type="checkbox" name="decreaseLeader${status.index }" value="1" ${detail.decreaseLeader eq '1'? 'checked="checked"':'' } onclick="checkLeader('${detail.id }',this)"/>包岗领导同扣
	                     </td>
	                     <td id="labelTdRow">
	                     	<c:choose>
	                     		<c:when test="${status.index eq 0}"><u id="labelURow" class="fakelink" onclick="removeLabelRow(this)">删除</u></c:when>
	                     		<c:otherwise><u id="labelURow" class="fakelink" onclick="removeLabelRow(this)">删除</u></c:otherwise>
	                     	</c:choose>
	                     	
	                     	
	                     </td>
                  	</tr>
                  </c:forEach>  
                  
                  <%--如果没有明细项,则列出一行,当"种子" --%>
                  <c:if test="${empty model.taskDutyDetails}">
	                 <tr>
	                  		<td align="right" width="25%">评分项目：</td>
		                     <td align="left" width="20%">
		                     	<%--
		                     	<s:textfield id="dutyItemName1" name="detail.name"  cssClass="detailNameClass" cssStyle="width:230px;"></s:textfield> --%>
		                     	<input type="text" name="detailNames" class="detailNameClass" style="width: 230px;"></input>
		                     	<s:hidden name="detail.id"></s:hidden>
		                     </td>
		                     <td align="right" width="10%" >加/减分：</td>	                     
		                     <td align="left" width="10%">
			                     <select name="detailAddOrDecreases" id="saveFrm_detail_addOrDecrease" class="detailAddOrDecreaseClass">
		                      	<option value="">请选择</option>
		                      	<c:forEach items="${addOrDecreaseMap}" var="item">
		                      		<option value="${item.key}" >${item.value}</option>		                      	
		                      	</c:forEach>
							</select>
		                     </td>
		                     <td align="right" width="8%">分值：</td>
		                     <td align="left" width="8%">
		                     <%--
		                     <s:textfield id="detail.point" cssClass="detailPointClass" name="detail.point"></s:textfield> --%>
		                     <input type="text" name="detailPoints" class="detailPointClass" style="width:60px;"></input>
		                     </td>
		                     <td align="left" width="18%">
		                     	<input type="hidden" name="decreaseLeaders" value="0" id="decreaseLeaders"/>
		                     	<input type="checkbox" name="decreaseLeader" value="1" onclick="checkLeader('',this)" />包岗领导同扣
		                     </td>
		                     <td id="labelTdRow"><u id="labelURow" class="fakelink" onclick="removeLabelRow(this)">删除</u></td>
	                  	</tr>
                  </c:if>                                                                         
                                                     
                 </table> 
              </fieldset>
              <table width="100%" style="margin-bottom:10px;">
				<tr>
					<td style="text-align:center;">
					 <%--<s:submit value="保存" cssClass="button"/> 				--%>	
						<input type="button" value="保存" class="button" onclick="saveIt()"></input>			
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
		'model.taskType.id':{
			required : true
		},
		'detailNames':  {
			required : true
		},
		'detailNames2':  {
			required : true
		},
		'detailNames1':  {
			required : true
		},
		'detailNames0':  {
			required : true
		},
		'detailAddOrDecreases':{
			required : true
		},
		'detailAddOrDecreases2':{
			required : true
		},
		'detailAddOrDecreases1':{
			required : true
		},
		'detailAddOrDecreases0':{
			required : true
		}
		,'detailPoints':{
			required : true,
			number   : true,
			min      : 0
		}
		,'detailPoints2':{
			required : true,
			number   : true,
			min      : 0
		},
		'detailPoints1':{
			required : true,
			number   : true,
			min      : 0
		},
		'detailPoints0':{
			required : true,
			number   : true,
			min      : 0
		}
	},
	messages: {
		'model.name': {
			required: "请输入名称"			
		},
		'model.taskType.id':{
			required: "请选择类型"	
		},
		'detail.name':  {
			required : "请输入名称"
		},
		'detail.name2':  {
			required : "请输入名称"
		},
		'detail.name1':  {
			required : "请输入名称"
		},
		'detail.name0':  {
			required : "请输入名称"
		},
		'detailAddOrDecreases':{
			required : "请选择加/减分"
		},
		'detailAddOrDecreases2':{
			required : "请选择加/减分"
		},
		'detailAddOrDecreases1':{
			required : "请选择加/减分"
		},
		'detailAddOrDecreases0':{
			required : "请选择加/减分"
		}
		,'detailPoints':{
			required : "请输入分值",
			number   : "请输入数字",
			min      : "请输入有效数字"
		}
		,'detailPoints2':{
			required : "请输入分值",
			number   : "请输入数字",
			min      : "请输入有效数字"
		},
		'detailPoints1':{
			required : "请输入分值",
			number   : "请输入数字",
			min      : "请输入有效数字"
		},
		'detailPoints0':{
			required : "请输入分值",
			number   : "请输入数字",
			min      : "请输入有效数字"
		}
	},
	submitHandler: function(form) {
		$('.detailNameClass').attr("name","detailNames");
		$('.detailAddOrDecreaseClass').attr("name","detailAddOrDecreases");
		$('.detailPointClass').attr("name","detailPoints");     
		  form.submit();
		}
			
});
}
$(function(){
	vali();
});
var currentId = 2;
function addLabelRow(arg){
	
	if($('.detailNameClass').size() >= 3){
		currentId = 1;
		return;
	}
	
  var html = '<tr><td align="right" width="25%">评分项目：</td><td align="left" width="20%"><input type="text" name="detailNames'+currentId+'" class="detailNameClass" style="width:230px;"></input></td><td align="right" width="10%" >加/减分：</td><td align="left" width="10%"><select name="detailAddOrDecreases'+currentId+'" class="detailAddOrDecreaseClass"><option value="">请选择</option><option value="0">减分</option><option value="1">加分</option></select></td><td align="right" width="8%" >分值：</td><td align="left" width="8%"><input type="text" class="detailPointClass" style="width: 60px;" name="detailPoints'+currentId+'" style="width:60px;"></input></td><td align="left" width="18%"><input type="hidden" name="decreaseLeaders" id="decreaseLeaders'+currentId+'" value="0"/><input type="checkbox" name="decreaseLeader" onclick="checkLeader('+currentId+',this)" value="1" />包岗领导同扣</td><td id="labelTdRow"><u id="labelURow" class="fakelink" onclick="removeLabelRow(this)">删除</u></td></tr>';
  $('#formTable').append(html);
  currentId--;
  vali();
}
function checkLeader(arg1,arg2){
	if(arg2.checked){
		
		
		//$('#decreaseLeaders'+arg1).value="1";
		document.getElementById('decreaseLeaders'+arg1).value = "1";
		
	}else{	
		document.getElementById('decreaseLeaders'+arg1).value = "0";
	}
}
function saveIt(){
	/*$('.detailNameClass').attr("name","detail.name");
	$('.detailAddOrDecreaseClass').attr("name","detail.addOrDecrease");
	$('.detailPointClass').attr("name","detail.point");
*/
	$('#saveFrm').submit();
}
function removeLabelRow(arg){
	if($('.detailNameClass').size() <=1 ){
		return;
	}
	 $(arg).parent().parent().remove();
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

</script>
</body>
</html>