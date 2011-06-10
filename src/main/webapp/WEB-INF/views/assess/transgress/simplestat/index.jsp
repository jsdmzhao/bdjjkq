<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="/common/taglibs.jsp" %>
<html>
<head>

<title></title>
<%@include file="/common/validator.jsp" %>
<%@include file="/common/ec.jsp" %>
<%--@include file="/common/extjs.jsp" --%>
<%@include file="/common/meta.jsp" %>
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
.SSTDIV{
	float: left;
	width : 50%;
	max-height:300px;
	
}
</style>
</head>
<body>
<div class="x-panel">
    <div class="x-panel-header">统计项信息管理</div>
    <div class="x-toolbar" style="text-align: right;">
		<a href="index.htm">管理首页</a>
    </div>
	<s:form id="statForm" namespace="/assess/transgress/simpleStat" action="simpleStat" method="post">
	<s:hidden id="model.id" name="model.id"/>
	<table width="900px" align="center">
		<tr>
			<td align="center">
			<fieldset> 
              <legend>编辑统计条件信息</legend>
                <table cellpadding="3" cellspacing="2" width="100%">
                  <tr>
                  	<td align="right" width="15%">
						起始时间：
					</td>
					<td align="left" width="15%">
						<input type="text" size="16"  name="beginTime" id="beginTime" value="<s:date name="beginTime"format="yyyy-MM-dd HH:mm"/>" />
						<a href="javascript:void(0)" onClick="if(self.gfPop)gfPop.fPopCalendar(document.getElementById('beginTime'));return false;" HIDEFOCUS>
					<img src="${ctx}/js/calendar/calbtn.gif" alt="" name="popcal" id="popcal" 
					width="34" height="22" border="0" align="absmiddle"></a>
					</td>
					<td align="right" width="15%">	
						截至时间：
					</td>
					<td align="left" width="55%">
						<input type="text" size="16"  name="endTime" id="endTime" value="<s:date name="endTime"format="yyyy-MM-dd HH:mm"/>" />
						<a href="javascript:void(0)" onClick="if(self.gfPop)gfPop.fPopCalendar(document.getElementById('endTime'));return false;" HIDEFOCUS>
					<img src="${ctx}/js/calendar/calbtn.gif" alt="" name="popcal" id="popcal" 
					width="34" height="22" border="0" align="absmiddle"></a>
					&nbsp;&nbsp;&nbsp;&nbsp;
					</td> 
                  </tr>
                  <tr>
                     <td align="right" width="15%">单位名称：</td>
                     <td align="left" width="15%"  >
                     	
                     	<select name="deptId" style="width:200px;padding-left:2px;">
                     		<option value="">请选择</option>
                     		<c:forEach items="${depts}" var="dept">
                     			<option value=${dept.id }>${dept.name }</option>
                     		</c:forEach>
                     	</select>
					 </td>
                     <td rowspan="3"  width="15%" align="right">车&nbsp;&nbsp;&nbsp;辆&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br/>使用性质：</td>
                     <td rowspan="3" width="55%">
                     	<table  width="100%" style="margin-left: -9px;">
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
                  </tr>
                                 
                  <TR>
                  	 <td align="right">违法类别：</td>
                     <td align="left">
                     	
                     	<select name="firstLevelType" onchange="initSubTypes($(this).val())" style="width:200px;padding-left:2px;">
                     		<option value="">请选择</option>
                     		<c:forEach items="${firstLevelTypes}" var="flt">
                     			<option value="${flt.code }">${flt.descns }</option>
                     		</c:forEach>
                     	</select>
                     </td>
                     
                  </TR>
                  <TR>
                  	 <td align="right">违法分类：</td>
                     <td align="left">
                     	<select name="secondLevelTypes"  class="m_t_b " id="secondLevelTypes" style="width:200px;padding-left:2px;"
                     	 onchange="initTransgressActionOptions($(this).val(),$(this).find('option:selected').text())" >
						</select>
                     	<span style="margin-left:5px;display:none;" id="l_typeB"><img src="${ctx}/images/loading.gif"></span>
                     </td>
                  </TR>   
                  
                   <TR>
                  	 <td align="right">时间依据：</td>
                  	 <td><input type="radio" name="timeCondition" value="FXSJ" checked="checked">发现时间</input>						
					<input type="radio" name="timeCondition" value="CLSJ">处理时间</input></td>
                     <td align="right">
                     	是否关联&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br/>违法强制表：</td>
                     	<td align="left">
						<input type="radio" name="unionForce"  value="true">关联</input>
						<input type="radio" name="unionForce" value="" checked="checked">不关联</input>
                     </td>
                  </TR>  
                  <tr>
                  	<td colspan="4">
                  		<table  border="1"  id="transgressActions" width="100%">                  			
                  			
                  		</table>
                  	</td>
                  </tr>               
                </table> 
              </fieldset>
              <table width="100%" style="margin-bottom:10px;">
				<tr>
					<td style="text-align:center;">						
						<input type="button" value="统计" class="button" onclick="statIt()"></input>					
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
			var rows = data.length;
			//var trId = $('#transgressActions>tbody>tr:last').attr("id");
			//trId++;
			//var	str = "<tr id = '"+trId+"'><td width='30%'></td></tr>";
			var html = [];
			html.push("<tr><td><table class='sstTable' id='SST"+secondLevelTypeId+"' width='100%'><tr><td class='transgressTypeTitle'>"+secondLevelTypeDescn+"<input type='hidden' disabled='disabled' name='secondLevelTypeIds' value='"+secondLevelTypeId+"'/></td></tr>");
			$.each(data,function(idx,item){
				html.push("<tr><td align='left'><input type='checkbox' name='transgressActionCodes' value='"+item.code+"'/>"+item.code+":"+item.descns + "</td></tr>");
			});
			html.push("</table><div></td></tr>");
			
			$('#transgressActions').append(html.join(''));
		}
	});
}
/**
 * 根据选中的子类别,列出该子类别下的所有违法行为
 */
 
</script>
<script type="text/javascript">
function statIt(){
	var checkArr ;
	checkArr = document.getElementsByName("transgressActionCodes");
	if(checkArr.length==0){
		alert("请选择违法行为!");
		return;
	}
	for(var i =0;i<checkArr.length;i++){
		if(checkArr[i].checked){
			document.getElementById("statForm").submit();
			return;
		}
	}
	alert("请选择违法行为!");
	return;
}
</script>
				<iframe width=188 height=166 
				name="gToday:datetime:${ctx}/js/calendar/agenda.js:gfPop:${ctx}/js/calendar/plugins_time.js" 
				id="gToday:datetime:${ctx}/js/calendar/agenda.js:gfPop:${ctx}/js/calendar/plugins_time.js" 
				src="${ctx}/js/calendar/ipopeng.html" scrolling="no" frameborder="0" 
				style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;">
			</iframe>
</body>
</html>