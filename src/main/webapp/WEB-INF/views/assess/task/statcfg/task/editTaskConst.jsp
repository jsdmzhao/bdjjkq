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
    <div class="x-panel-header">任务常量信息管理</div>
    <div class="x-toolbar" style="text-align: right;">
		<a href="taskConstIndex.htm">管理首页</a>
    </div>
	<s:form id="saveFrm" action="saveTaskConst" method="post">
	<s:hidden id="model.id" name="model.id"/>
	<table width="800px" align="center">
		<tr>
			<td align="center">
			<fieldset> 
              <legend>任务常量信息</legend>
                <table cellpadding="3" cellspacing="2" width="100%">
                  <tr>
                     <td align="right" width="20%">名&nbsp;&nbsp;称：</td>
                     <td align="left" width="30%"  ><s:textfield name="model.name" cssClass="required"></s:textfield><span style="color: #ff0000;">*</span></td>
                     <td  width="20%" align="right">完成任务得分：</td>
                     <td align="left" width="30%">
                     	<s:textfield name="model.total"></s:textfield><span style="color: #ff0000;">*</span>
                     </td>
                  </tr>
                                 
                  <TR>
                  	 <td align="right">违法项目：</td>
                     <td align="left">
                     	<s:select list="transgressStatItems" id="transgressStatItems"  headerKey ="" headerValue = "请选择" 
                     	name="model.transgressStatItem.id" listKey="id" listValue="name" cssClass="m_t_b" 
                     	cssStyle="width:300px;padding-left:2px;"  ></s:select><span style="color: #ff0000;">*</span>
                     </td>
                     <td align="right">目标数量：</td>
                     <td align="left"><s:textfield name="model.aimCount"></s:textfield><span style="color: #ff0000;">*</span></td>
                  </TR>                                   
                  <TR>
                  	 <td align="right">超1例加分：</td>
                     <td align="left"><s:textfield name="model.addPoint"></s:textfield><span style="color: #ff0000;">*</span></td>
                     <td align="right">少1例扣分：</td>
                     <td align="left"><s:textfield name="model.decreasePoint"></s:textfield><span style="color: #ff0000;">*</span></td>
                  </TR>
                  <tr>
                  	<td align="right">
                  	<s:checkbox name="model.hasSpecial" value="model.hasSpecial"   onclick='selectEx(this)'></s:checkbox>
                  	额外加分项</td>
                  	<td align="left" ></td>
                  	<td colspan="2"></td>
                  </tr>
                  <tr>
                  	<td colspan="4">
                  		<table width="100%" id="exItemsTable">
                  			<tr>
			                  	<td align="right" width="20%">名&nbsp;&nbsp;称：</td>
			                    <td align="left" colspan="3"><s:textfield name="model.specialItem" ></s:textfield></td>                    
		                  </tr>  
		                  <tr>
		                  	<td align="right">违法项目：</td>
		                     <td align="left" width="30%">
		                     	<s:select list="transgressStatItems" id="specialTransgressStatItems"  headerKey ="" headerValue = "请选择" 
		                     	name="model.specialTransgressStatItem.id" listKey="id" listValue="name" cssClass="m_t_b" 
		                     	cssStyle="width:300px;padding-left:2px;"  ></s:select>
		                     </td>
		                   <td align="right" width="20%">分数：</td>
		                    <td align="left" width="30%"><s:textfield  name="model.specialPoint"></s:textfield></td>
		                  </tr>
                  		</table>
                  	</td>
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
$("#saveFrm").validate({
	rules: {
		'model.name':  {
			required : true
		},
		'model.total':  {
			required : true,
			number   : true,
			min      : 0
		},
		'model.transgressStatItem.id':  {
			required : true
		},
		'model.aimCount':{
			required : true,
			digits   : true,
			min      : 0
		},
		'model.addPoint':{
			required : true,
			number   : true,
			min      : 0
		}
		,'model.decreasePoint':{
			required : true,
			number   : true,
			min      : 0
		},
		'model.specialItem':{
			required : true
		},
		'model.specialTransgressStatItem.id':{
			required : true
		},
		'model.specialPoint':{
			required : true,
			number   : true,
			min      : 0
		}
	},
	messages: {
		'model.name': {
			required: "请输入名称"			
		},
		'model.total':  {
			required: "请输入完成任务得分",
			number:   "请输入数字",
			min     : "请输入数字"
		},
		'model.transgressStatItem.id':{
			required:"请选择违法项目"
		},
		'model.aimCount':{
			required : "请输入目标数量",
			digits : "请输入数字",
			min     : "请输入数字"
		},
		'model.addPoint':{
			required : "请输入加分分数",
			number : "请输入数字",
			min     : "请输入数字"
		},
		'model.decreasePoint':{
			required : "请输入减分分数",
			number : "请输入数字",
			min     : "请输入数字"
		},
		'model.specialItem':{
			required : "请输入额外加分项名称"
		},
		'model.specialTransgressStatItem.id':{
			required:"请选择违法项目"
		},
		'model.specialPoint':{
			required : "请输入额外加分分数",
			number   : "请输入数字",
			min      : "请输入数字"
		}
	}
});

$(document).ready(
		<c:if test="${!model.hasSpecial}">
		 function(){
				   $("#exItemsTable").children().attr("disabled","disabled");
				   $("#exItemsTable select option").attr("disabled","disabled");
				   $("#exItemsTable input").attr("disabled","disabled");
				   $("#specialTransgressStatItems").attr("disabled","disabled");
				   $("#specialTransgressStatItems option").attr("disabled","disabled");
			   }
        </c:if>
  
);
function selectEx(arg){
	if(arg.checked){
		$("#exItemsTable").children().attr("disabled","");
		//$("#exItemsTable select option").attr("disabled","");
		$("#exItemsTable input").attr("disabled","");
		$("#specialTransgressStatItems").attr("disabled","");
		$("#specialTransgressStatItems option").attr("disabled","");
	}else{
		$("#exItemsTable input").val("");
		$("#exItemsTable").children().attr("disabled","disabled");
		$("#exItemsTable select option").attr("disabled","disabled");
		$("#exItemsTable input").attr("disabled","disabled");
		$("#specialTransgressStatItems").attr("disabled","disabled");
		$("#specialTransgressStatItems").attr("selectedIndex","0");
		$("#specialTransgressStatItems option").attr("disabled","disabled");
	}
}

/**
 * 根据选中的topType值初始化subTypes选择框
 */
function initSubTypes(firstLevelTypeId,curVal){
	$('secondLevelTypes').html('');
	if(firstLevelTypeId == ''){
		return;
	}
	$.ajax({
		url:'${ctx}/assess/transgress/statcfg/statItem/getSecondLevelTypesByFirstLevel.htm',
		data:{'firstLevelTypeId':firstLevelTypeId},
		dataType:'json',
		success:function(data){
			var html = [];
			html.push("<option value=''>请选择</option>");
			$.each(data,function(idx,item){
				if(!curVal){
					html.push("<option value='" + item.id + "'>" + item.descns + "</option>");
				}else{
					if(curVal == item.id){
						html.push("<option selected value='" + item.id + "'>" + item.descns + "</option>"); 
					}
				}
			});
			$('#secondLevelTypes').html(html.join(''));
			$('#l_typeB').hide();
		},
		beforeSend:function(){
			$('#l_typeB').show();
		},
		error:function(){
			$('#l_typeB').hide();
		}
	});
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
function checkAll(arg1,arg2){
	if(arg2.checked){
		$("#"+arg1+" :checkbox").attr("checked",true);
	}else{
		$("#"+arg1+" :checkbox").attr("checked",false);
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