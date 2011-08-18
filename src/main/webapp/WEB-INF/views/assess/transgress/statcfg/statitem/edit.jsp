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
    <div class="x-panel-header">统计条件信息管理</div>
    <div class="x-toolbar" style="text-align: right;">
		<a href="index.htm">管理首页</a>
    </div>
	<s:form id="saveFrm" action="save" method="post">
	<s:hidden id="model.id" name="model.id"/>
	<table width="1000px" align="center">
		<tr>
			<td align="center">
			<fieldset> 
              <legend>编辑统计条件信息</legend>
                <table cellpadding="3" cellspacing="2" width="100%">
                  <tr>
                     <td align="right" width="15%">名称：</td>
                     <td align="left" width="20%"  ><s:textfield name="model.name"></s:textfield><span style="color: #ff0000;">*</span></td>
                     <td></td>
                     <td></td>
                  </tr><!--
                  
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
                  --><TR>
                  	 <td align="right">类别：</td>
                     <td align="left">
                     	<s:select list="firstLevelTypes" id="firstLevelType"  headerKey ="" headerValue = "请选择" 
                     	name="firstLevelType" listKey="id" listValue="descns" cssClass="m_t_b" 
                     	cssStyle="width:200px;padding-left:2px;"  
                     	onchange="initSubTypes($(this).val())"></s:select>
                     </td>
                     <td rowspan="2" width="15%" align="right">车辆使用性质：</td>
                     <td rowspan="2" width="55%">
                     	<table  width="100%">
                     		<c:forEach items="${allVehicleUseCodes}" var="vuc" varStatus="status">              
                     				<c:if test="${status.index%5== 0}">
                     					<tr>
                     				</c:if>
                     				<td align="left">                     				
	                  					<c:choose>
	                  						<c:when test="${fn:indexOf(model.vehicleUseCodes,vuc.code)!= -1}">
	                  						<input type="checkbox" checked="checked" name="vehicleUseCodes" value="${vuc.code}"/>${vuc.name}
	                  						</c:when>
	                  						<c:otherwise>
	                  						<input type="checkbox"  name="vehicleUseCodes" value="${vuc.code}"/>${vuc.name}
	                  						</c:otherwise>
	                  					</c:choose>                     				
                     				</td>
                     				<c:if test="${status.index%5== 4}">
                     					</tr>
                     				</c:if>               
                     		</c:forEach>
                     	</table>
                     </td>
                  </TR>
                  <TR>
                  	 <td align="right">分类：</td>
                     <td align="left">
                     	<select name="secondLevelTypes"  class="m_t_b " id="secondLevelTypes" style="width:200px;padding-left:2px;"
                     	 onchange="initTransgressActionOptions($(this).val(),$(this).find('option:selected').text())" >
						</select>
                     	<span style="margin-left:5px;display:none;" id="l_typeB"><img src="${ctx}/images/loading.gif"></span>
                     </td>
                  </TR>   
                  <TR>
                  	 <td align="right">时间依据：</td>
                  	 <td align="left">
                  	 	<input type="radio" name="timeCondition" value="WFSJ" ${model.findOrDealWith ne 'CLSJ' ?  'checked="checked"' : ''} >违法时间</input>						
						<input type="radio" name="timeCondition" value="CLSJ" ${model.findOrDealWith eq 'CLSJ' ?  'checked="checked"' : ''}>处理时间</input>
					 </td>

                  	<td></td>
                  	<td></td>
                  </TR>  
                  <tr>
                   <td align="right">
                     	是否关联&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br/>违法强制表：</td>
                     	<td align="left">
						<input type="radio" name="unionForce" value="true"  id="unionForceTrue"   ${model.unionForce eq "true" ? 'checked="checked"' : '' }>关联</input>
						<input type="radio" name="unionForce" value="false" id="unionForceFalse"  ${model.unionForce eq "false" or empty model.unionForce ? 'checked="checked"' : '' }>不关联</input>
						<input type="radio" name="unionForce" value="only"  id="unionForceOnly"   ${model.unionForce eq "only" ? 'checked="checked"' : '' } >仅统计强制表</input>
                     </td>
                     <td align="right" rowspan="2">号牌种类：</td>
                     <td rowspan="2">
                  		<table  width="100%" style="margin-left: px;">
                     		<c:forEach items="${flapperTypes}" var="ft" varStatus="status">              
                     				<c:if test="${status.index%5== 0}">
                     					<tr>
                     				</c:if>
                     				<td align="left">                     				
	                  						<!--<input type="checkbox"  name="flapperTypes" value="${ft.code}"/>${ft.name}     
	                  						
	                  						-->
	                  						<c:choose>
	                  						<c:when test="${fn:indexOf(model.flapperTypes,ft.code)!= -1}">
	                  						<input type="checkbox" checked="checked" name="flapperTypes" value="${ft.code}"/>${ft.name}
	                  						</c:when>
	                  						<c:otherwise>
	                  						<input type="checkbox"  name="flapperTypes" value="${ft.code}"/>${ft.name}
	                  						</c:otherwise>
	                  					</c:choose>              				
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
						<input type="radio" name="vioSurveil" id="vioSurveilTrue"  value="true"  ${model.vioSurveil eq "true" ? 'checked="checked"' : '' }>关联</input>
						<input type="radio" name="vioSurveil" id="vioSurveilFalse" value="false" ${model.vioSurveil eq "false"  or empty model.vioSurveil ? 'checked="checked"' : '' }>不关联</input>
						<!--<input type="radio" name="vioSurveil" id="vioSurveilOnly"  value="only"  ${model.vioSurveil eq "only" ? 'checked="checked"' : '' }>仅统计非现场文本记录表</input>
	
                     --></td>
                  </tr>
                   <TR>
                  	 <td align="right"></td>
                     <td align="left">
                     	
                     </td>
                  </TR>  
                  <tr>
                  	<td colspan="4">
                  		<table id="transgressActions" border="1" width="100%">                  			
                  			<c:forEach items="${selectedSecondTypes}" var="sst">
                  				<tr>
                  					<td colspan="4">
                  						<table width="100%" class="sstTable"  id="SST${sst.id}">
                  							<tr><td class="transgressTypeTitle">${sst.descns }&nbsp;&nbsp;&nbsp;&nbsp;<input type='checkbox'  onclick='checkAll("SST${sst.id}",this)'>全选<img  class='clossBtn' src='${ctx}/images/icons/delete.gif' onclick='closeTable("SST${sst.id}")'>关闭</td></tr>
                  							<c:forEach items="${sst.transgressActions}" var="ta">
                  								<c:set value="'${ta.code}'" var="co"></c:set>
                  								<tr>
                  									<td align="left">
                  										<c:choose>
                  											<c:when test="${fn:indexOf(model.transgressActionCodes,co)!= -1}">
                  												<input type="checkbox" checked="checked" class="transgressActionCheckBox" name="transgressActionIds" value="${ta.id}"/>${ta.code }:${ta.descns}
                  											</c:when>
                  											<c:otherwise>
                  												<input  type="checkbox" class="transgressActionCheckBox" name="transgressActionIds" value="${ta.id}"/>${ta.code}:${ta.descns}
                  											</c:otherwise>
                  										</c:choose>                  										
                  									</td>
                  								</tr>
                  							</c:forEach>
                  						</table>
                  					</td>
                  				</tr>                  				
                  			</c:forEach>
                  		</table>
                  	</td>
                  </tr>               
                </table> 
              </fieldset>
              <table width="100%" style="margin-bottom:10px;">
				<tr>
					<td style="text-align:center;">
					 <s:submit value="保存" cssClass="button"/> 				
						<%--<input type="button" value="保存" class="button" onclick="saveIt()"></input>		--%>		
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
		'model.type.id':  {
			required : true
		},
		'model.name':  {
			required : true
		},
		'model.integral':  {
			required : true,
			number : true
		}
	},
	messages: {
		'model.type.id': {
			required: "请选择类型"
		},
		'model.name':  {
			required: "请输入统计条件名称"
		}
	}
});


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