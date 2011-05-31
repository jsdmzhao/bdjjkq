<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="/common/taglibs.jsp" %>
<html>
<head>
<%@include file="/common/meta.jsp" %>
<title></title>
<%@include file="/common/validator.jsp" %>
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
</style>
</head>
<body>
<div class="x-panel">
    <div class="x-panel-header">统计项信息管理</div>
    <div class="x-toolbar" style="text-align: right;">
		<a href="index.htm">管理首页</a>
    </div>
	<s:form id="save" action="save" method="post">
	<s:hidden id="model.id" name="model.id"/>
	<table width="700px" align="center">
		<tr>
			<td align="center">
			<fieldset> 
              <legend>编辑统计项信息</legend>
                <table cellpadding="3" cellspacing="2">
                  <tr>
                     <td align="right" >名称：</td>
                     <td align="left" ><s:textfield name="model.name"></s:textfield></td>
                  </tr>
                                 
                  <TR>
                  	 <td align="right">类别：</td>
                     <td align="left">
                     	<s:select list="firstLevelTypes" id="firstLevelType" headerKey ="" headerValue = "请选择" 
                     	name="firstLevelType" listKey="id" listValue="descns" cssClass="m_t_b" 
                     	cssStyle="width:252px;padding-left:2px;"  
                     	onchange="initSubTypes($(this).val())"></s:select>
                     </td>
                  </TR>
                  <TR>
                  	 <td align="right">分类：</td>
                     <td align="left">
                     	<select name="secondLevelTypes"  class="m_t_b " id="secondLevelTypes" style="width:252px;padding-left:2px;"
                     	 onchange="initTransgressActionOptions($(this).val(),$(this).find('option:selected').text())" >
						</select>
                     	<span style="margin-left:5px;display:none;" id="l_typeB"><img src="${ctx}/images/loading.gif"></span>
                     </td>
                  </TR>   
                  <tr>
                  	<td colspan="2">
                  		<table id="transgressActions" border="1" width="700px">                  			
                  			<c:forEach items="${selectedSecondTypes}" var="sst">
                  				<tr>
                  					<td colspan="2">
                  						<table width="700px;" border="1">
                  							<tr><td class="transgressTypeTitle">${sst.descns }${sst.id}</td></tr>
                  							<c:forEach items="${sst.transgressActions}" var="ta">
                  								<tr>
                  									<td align="left">
                  										<c:choose>
                  											<c:when test="${fn:indexOf(model.transgressActionCodes,ta.code)!= -1}">
                  												<input type="checkbox" checked="checked" class="transgressActionCheckBox" name="transgressActionIds" value="${ta.id}"/>${ta.id}${ta.descns}${ta.code }
                  											</c:when>
                  											<c:otherwise>
                  												<input  type="checkbox" class="transgressActionCheckBox" name="transgressActionIds" value="${ta.id}"/>${ta.id}${ta.descns}${ta.code}
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
$("#save").validate({
	rules: {
		'model.type.id':  {
			required : true
		},
		'model.state':  {
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
		'model.state':  {
			required: "选择审核结果"
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
	if(secondLevelTypeId  == ''){return;}
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
			html.push("<tr><td><table border='1' width='100%'><tr><td class='transgressTypeTitle'>"+secondLevelTypeDescn+"<input type='hidden' disabled='disabled' name='secondLevelTypeIds' value='"+secondLevelTypeId+"'/></td></tr>");
			$.each(data,function(idx,item){
				html.push("<tr><td align='left'><input type='checkbox' name='transgressActionIds' value='"+item.id+"'/>"+item.descns + "</td></tr>");
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
</body>
</html>