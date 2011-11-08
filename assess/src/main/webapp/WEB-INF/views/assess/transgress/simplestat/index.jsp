<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="/common/taglibs.jsp" %>
<%@page import="java.util.Date" %>
<%@page import="java.util.Calendar" %>
<html>
<head>

<title></title>
<%@include file="/common/validator.jsp" %>
<%@include file="/common/ec.jsp" %>
<%@include file="/common/extjs.jsp" %>
<%@include file="/common/meta.jsp" %>
<script type="text/javascript" src="${ctx}/scripts/jquery.form.js"></script>

<script type="text/javascript" src="${ctx}/scripts/jqueryui/jquery.ui.js"></script>
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
.SSTDIV{
	float: left;
	width : 50%;
	max-height:300px;
	
}
.clossBtn{
	margin-left: 20px;
	
}
</style>
</head>
<body>
<div class="x-panel">
    <div class="x-panel-header">单项统计</div>
    <div class="x-toolbar" style="text-align: right;">
		<a href="index.htm">管理首页</a>
    </div>
	<s:form id="statForm" namespace="/assess/transgress/simpleStat" action="simpleStat" method="post">
	<s:hidden id="model.id" name="model.id"/>
	<table width="1000px" align="center">
		<tr>
			<td align="center">
			<fieldset> 
              <legend>单项统计</legend>
                <table cellpadding="3" cellspacing="2" width="100%">
                  <tr>
                  	<td align="right" width="15%">
						起始时间：
					</td>
					<td align="left" width="15%">
						<input type="text" size="16"  name="beginTime" id="beginTime" value="<fmt:formatDate value="${beginTime }" pattern="yyyy-MM-dd HH:mm"/>" />
						<a href="javascript:void(0)" onClick="if(self.gfPop)gfPop.fPopCalendar(document.getElementById('beginTime'));return false;" HIDEFOCUS>
					<img src="${ctx}/js/calendar/calbtn.gif" alt="" name="popcal" id="popcal" 
					width="34" height="22" border="0" align="absmiddle"></a>
					</td>
					<td align="right" width="15%">	
						截至时间：
					</td>
					<td align="left" width="55%"><%-- <s:date name="endTime"format="yyyy-MM-dd HH:mm"/>--%>
						<input type="text" size="16"  name="endTime" id="endTime" value="<fmt:formatDate value="${endTime }" pattern="yyyy-MM-dd HH:mm"/>" />
						<a href="javascript:void(0)" onClick="if(self.gfPop)gfPop.fPopCalendar(document.getElementById('endTime'));return false;" HIDEFOCUS>
					<img src="${ctx}/js/calendar/calbtn.gif" alt="" name="popcal" id="popcal" 
					width="34" height="22" border="0" align="absmiddle"></a>
					&nbsp;&nbsp;&nbsp;&nbsp;
					
					</td> 
                  </tr>
                  <tr>
                     <td align="right" width="15%">单位名称：</td>
                     <td align="left" width="15%"  >
                     	<c:choose>
                     		<c:when test="${param['arg'] eq 'khb'}">
	                     		<select name="deptId" style="width:200px;padding-left:2px;">
		                     		<option value="">请选择</option>
		                     		<c:forEach items="${depts}" var="dept">
		                     			<option value=${dept.id }>${dept.name }</option>
		                     		</c:forEach>
	                     		</select>
                     		</c:when>
                     		<c:otherwise>
                     			<input type="hidden" name="deptId" value="${depts[0].id }">
                     			${depts[0].name }
                     		</c:otherwise>
                     	</c:choose>
                     	
					 </td>
                    <td align="right" width="15%">已保存的统计条件：</td>
                     <td align="left" width="15%"  >                     	
                     	<select name="selectStatItem" id="selectStatItem" style="width:200px;padding-left:2px;">
                     		<option value="">请选择</option>
                     		<c:forEach items="${simpleTsItems}" var="sti">
                     			<option value=${sti.id }>${sti.name }</option>
                     		</c:forEach>
                     	</select>
                     	<input type="button" value="保存" id="btnSaveStatCondtion" onclick="javascript:showSaveStatConditionWindow()" class="button" >
                     	<input type="button" value="删除" id="btnRemoveStatCondtion" disabled="disabled" onclick="onRemoveStatCondition()" class="button" >
					 </td>
                  </tr>       
                  <tr>
                  	<td align="right" width="15%">违法代码：</td>
                  	<td colspan="2" align="left">
                  		<textarea rows="6" cols="50"  id="taCodes" name="taCodes"  ></textarea>                  		
                  	</td>
                  	<td align="left">
                  		<span style="color: #070100;">
                  		备注：您可以输入一系列违法行为代码,用逗号分隔开,例如:<font color="red">1712,17111</font></br>
                  		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;也可以通过点选违法行为来添加违法行为代码
                  		</span>
                  		
                  	</td>
                  </tr>         
                                        
                  <TR>
                  	 <td align="right" id="selectType1TdLable">违法类别：</td>
                     <td align="left" id="selectType1Td">
                     	
                     	<select name="firstLevelType" id="firstLevelType" onchange="initSubTypes($(this).val())" style="width:200px;padding-left:2px;">
                     		<option value="">请选择</option>
                     		<c:forEach items="${firstLevelTypes}" var="flt">
                     			<option value="${flt.code }">${flt.descns }</option>
                     		</c:forEach>
                     	</select>
                     </td>
                      <td rowspan="2"  width="15%" align="right">车&nbsp;&nbsp;&nbsp;辆&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br/>使用性质：</td>
                     <td rowspan="2" width="55%">
                     	<table  width="100%" style="margin-left: px;">
                     		<c:forEach items="${allVehicleUseCodes}" var="vuc" varStatus="status">              
                     				<c:if test="${status.index%5== 0}">
                     					<tr>
                     				</c:if>
                     				<td align="left">                     				
	                  						<input type="checkbox"  name="vehicleUseCodes" value="${vuc.code}"/>${vuc.name}                  				
                     				</td>
                     				<c:if test="${status.index%5== 4}">
                     					</tr>
                     				</c:if>               
                     		</c:forEach>
                     	</table>
                     </td>
                  </TR>
                  <TR>
                  	 <td align="right" id="selectType2TdLable">违法分类：</td>
                     <td align="left" id="selectType2Td">
                     	<select name="secondLevelTypes" id="secondLevelTypes" class="m_t_b " id="secondLevelTypes" style="width:200px;padding-left:2px;"
                     	 onchange="initTransgressActionOptions($(this).val(),$(this).find('option:selected').text())" >
						</select>
                     	<span style="margin-left:5px;display:none;" id="l_typeB"><img src="${ctx}/images/loading.gif"></span>
                     </td>
                  </TR>   
                  
                   <TR>
                  	 <td align="right">时间依据：</td>
                  	 <td><input type="radio" name="timeCondition" id="WFSJ" value="WFSJ" checked="checked">违法时间</input>						
						 <input type="radio" name="timeCondition" id="CLSJ" value="CLSJ">处理时间</input></td>
                     <td align="right">
                     	</td>
                     	<td align="left">
						
                     </td>
                  </TR>  
                  <tr>
                   <td align="right">
                     	是否关联&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br/>违法强制表：</td>
                     	<td align="left">
						<input type="radio" name="unionForce" id="unionForceTrue" value="true">关联</input>
						<input type="radio" name="unionForce" id="unionForceFalse" value="false" checked="checked">不关联</input>
						<input type="radio" name="unionForce" id="unionForceOnly" value="only" >仅统计强制表</input>
                     </td>
                  	<td align="right" rowspan="2">号牌种类：</td>
                  	<td rowspan="2">
                  		<table  width="100%" style="margin-left: px;">
                     		<c:forEach items="${flapperTypes}" var="ft" varStatus="status">              
                     				<c:if test="${status.index%5== 0}">
                     					<tr>
                     				</c:if>
                     				<td align="left">                     				
	                  						<input type="checkbox"  name="flapperTypes" value="${ft.code}"/>${ft.name}                  				
                     				</td>
                     				<c:if test="${status.index%5== 4}">
                     					</tr>
                     				</c:if>               
                     		</c:forEach>
                     	</table>
                  	</td>
                  </tr>
                  <tr>
                  	 <td align="right">
                     	是否关联&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br/>非现场文本记录表：</td>
                     	<td align="left">
						<input type="radio" name="vioSurveil" id="vioSurveilTrue" value="true">关联</input>
						<input type="radio" name="vioSurveil" id="vioSurveilFalse" value="false" checked="checked">不关联</input>
						<!--<input type="radio" name="vioSurveil" id="vioSurveilOnly" value="only" >仅统计非现场文本记录表</input>
                     --></td>
                  </tr>
                  <tr>
                  	<td colspan="4">
                  		<table  border="1"  id="transgressActions" width="100%">                  			
                  			
                  		</table>
                  	</td>
                  </tr><!--  
                  <tr>
						<td align="right">统计条件名称：</td>
						<td><input type="text" name=""></input></td>
						<td align="right">描述：</td>
						<td>
							<textarea rows="3" cols="30" name="descn"></textarea>
						</td>						
					</tr>    
					<tr>
					<td colspan="4" align="center"><input type="button" value="保存统计条件" id="btnSaveStatCondtion" class="button" ></input></td>
					</tr>         
                --></table> 
              </fieldset>
              <table width="100%" style="margin-bottom:10px;">
				<tr>
					<td style="text-align:center;">						
						<input type="button" value="统计" class="button" onclick="statIt()"></input>			
						<input type="reset"  id="btnReset" value="重置" class="button" ></input>													
                    </td>
              	</tr>
              </table>
			</td>
		</tr>		
	</table>
	</s:form>
</div>

<script type="text/javascript">
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
				html.push("<tr><td align='left'><input class='ttaCode' type='checkbox' onclick='checkMe(this)' name='transgressActionCodes' value='"+item.code+"'/>"+item.code+":"+item.descns + "</td></tr>");
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
function checkMe(arg){
	if(arg.checked){
		if($('#taCodes').val() != ""){
			//alert($('#taCodes').val());
			$('#taCodes').val($('#taCodes').val()+","+arg.value);
		}else{
			$('#taCodes').val($('#taCodes').val()+arg.value);
		}	
		
	}else{
		$('#taCodes').val($('#taCodes').val().replace(arg.value+",",""));
	}
}
$(function(){
	$('#textAreaCodes').hide();
});
function statItOld(){
	//var checkArr ;
	//checkArr = document.getElementsByName("transgressActionCodes");
	var tmp = $('[name="vehicleUseCodes"]').val();
	//alert(tmp);
	return;
	if(checkArr.length==0){
		alert("请选择违法行为!");
		return;
	}
	/*for(var i =0;i<checkArr.length;i++){
		if(checkArr[i].checked){
			document.getElementById("statForm").submit();
			return;
		}
	}*/
	alert("请选择违法行为!");
	return;
}
$('#taCodes').css("ime-mode", "disabled");

$('#taCodes').bind("dragenter", function() {
	return false;
});
$('#taCodes').bind("beforepaste", function() {
	//var s = window.clipboardData.getData('text');
	//if (!/[^\d\,\，]/.test(s)){
	//	s.replace(/[^\d\,\，]/,'');
	//	//alert(s);
	
	//window.clipboardData.setData(window.clipboardData.getData('text').replace(/[^\d\,\，]/,''));	
	//onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d\,\，]/,''))"
	clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d\,\，\、]/,''));
	//alert(clipboardData.getData('text'));
		//return false;
	//}
});
$('#taCodes').bind("paste", function() {

	//var s = window.clipboardData.getData('text');
	//if (!/[^\d\,\，]/.test(s)){
	//	s.replace(/[^\d\,\，]/,'');
	//	//alert(s);
	
	//window.clipboardData.setData(window.clipboardData.getData('text').replace(/[^\d\,\，]/,''));	
	//onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d\,\，]/,''))"
	clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d\,\，\、]/,''));
	//alert(clipboardData.getData('text'));
		//return false;
	//}
});
$('#taCodes').bind("keypress", function() {
	//document.getElementById('taCodes').value = document.getElementById('taCodes').value.replace(/[^\d\,\，]/,'');
	if(/[^\d\,\，\、]/.test(String.fromCharCode(event.keyCode ))){
		return false; 
	}
});
$('#taCodes').bind("blur", function() {
	document.getElementById('taCodes').value = document.getElementById('taCodes').value.replace(/[^\d\,\，\、]/,'');
});
function statIt(){
	/*var tmp='';
	$("[name='vehicleUseCodes'][checked]").each(function(){  
		 tmp+=$(this).val();  
 	});   
	alert(tmp);
	return;*/
	//alert(/[\d\,\，]/.test($('#taCodes').val()));
	if( /[^\d\,\，\、]/.test($('#taCodes').val()) ||$.trim($('#taCodes').val().replace(/[^\d\,\，\、]/,'')) == ''){
		//alert("请点选或者输入违法代码!");
		//return;	
	}
	//alert($.trim($('#taCodes').val()));
	$("#statForm").submit();
}
function checkAll(arg1,arg2){
	if(arg2.checked){
		$("#"+arg1+" :checkbox.ttaCode").attr("checked",true);
	}else{
		$("#"+arg1+" :checkbox.ttaCode").attr("checked",false);
	}	
	$("#"+arg1+" :checkbox.ttaCode").each(function() {		
		checkMe(this);     
     });      
	
}
function closeTable(arg){
	$("#"+arg+" :checkbox.ttaCode").attr("checked",false);
	$("#"+arg+" :checkbox.ttaCode").each(function() {		
		checkMe(this);     
     });
	 $("#"+arg).parent().parent().remove();
	 
}
function saveStatCondition(){
	//alert('a');
}

</script>
			<iframe width=188 height=166 
				name="gToday:datetime:${ctx}/js/calendar/agenda.js:gfPop:${ctx}/js/calendar/plugins_time.js" 
				id="gToday:datetime:${ctx}/js/calendar/agenda.js:gfPop:${ctx}/js/calendar/plugins_time.js" 
				src="${ctx}/js/calendar/ipopeng.html" scrolling="no" frameborder="0" 
				style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;">
			</iframe>
			
			

<jsp:include page="saveStatCondition.jsp"></jsp:include>			
</body>
</html>
