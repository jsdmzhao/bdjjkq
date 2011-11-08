<!--author hsf-->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>统计时间配置</title>
<%@include file="/common/validator.jsp" %>
<%@include file="/common/ec.jsp" %>
<%@include file="/common/meta.jsp"%>
<script type="text/javascript" src="${ctx}/scripts/jquery.form.js"></script>

<script type="text/javascript">

</script>
</head>

<body>
<div class="x-panel">
<div class="x-panel-header">编辑统计时间</div>

<div class="x-panel-body">
<div><%@ include file="/common/messages.jsp"%>
</div> 
<s:form action="save" namespace="/assess/transgress/statcfg/condition" theme="simple" name="statCfgForm" id="statCfgForm"  method="POST">
<s:hidden name="model.id"></s:hidden>
	<fieldset style="margin: 30px;"><legend>统计时间信息</legend>
	<table width="100%" >
		<tr>
			<td class="simple" align="center">起始时间：</td>
			<td class="simple">
			<!--<input type="text"
								name="beginHourMinute"
								
								value='${model.beginHourMinute }'
								onfocus="WdatePicker({skin:'whyGreen',dateFmt:'HH:mm:ss'})"
								class="Wdate" style="width: 128px; height: 16px"
								readonly="readonly" class="required"/><font color='red'>*</font>
			-->
			<input type="text" size="16" class="required" name="beginHourMinute" id="beginHourMinute" value='${model.beginHourMinute}' />
						<a href="javascript:void(0)" onClick="if(self.gfPop)gfPop.fPopCalendar(document.getElementById('beginHourMinute'));return false;" HIDEFOCUS>
					<img src="${ctx}/js/calendar/calbtn.gif" alt="" name="popcal" id="popcal" 
					width="34" height="22" border="0" align="absmiddle"></a>
			</td>
		</tr>
		<tr>
			<td class="simple" align="center">截至时间：</td>
			<td class="simple"><!--<input type="text"
								name="model.endHourMinute"
								value='${model.endHourMinute}'
								onfocus="WdatePicker({skin:'whyGreen',dateFmt:'HH:mm:ss'})"
								class="Wdate" style="width: 128px; height: 16px"
								readonly="readonly" class="required"/><font color='red'>*</font>
								
							--><input type="text" size="16" class="required"  name="endHourMinute" id="endHourMinute" value='${model.endHourMinute}' />
						<a href="javascript:void(0)" class="required" onClick="if(self.gfPop)gfPop.fPopCalendar(document.getElementById('endHourMinute'));return false;" HIDEFOCUS>
					<img src="${ctx}/js/calendar/calbtn.gif" alt="" name="popcal" id="popcal" 
					width="34" height="22" border="0" align="absmiddle"></a>
			</td>
		</tr>		
	</table>
	<table width="100%" style="margin-bottom: 10px;">
		<tr>
			<td style="text-align: center;">
			<%--<s:submit value="保存" cssClass="button"></s:submit> --%>
			<input type="button" value="保存" class="button" onclick="saveIt()"></input> 
			<s:reset value="重置" cssClass="button"></s:reset></td>
		</tr>
	</table>
	</fieldset>
</s:form>

</div>

</div>
<iframe width=188 height=166 
	name="gToday:datetime:${ctx}/js/calendar/agenda.js:gfPop:${ctx}/js/calendar/plugins_time.js" 
	id="gToday:datetime:${ctx}/js/calendar/agenda.js:gfPop:${ctx}/js/calendar/plugins_time.js" 
	src="${ctx}/js/calendar/ipopeng.html" scrolling="no" frameborder="0" 
	style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;">
</iframe>	
<script type="text/javascript">
function saveIt(){
	if($('#beginHourMinute').val() == ''){
		alert('请选择起始时间');
		return;
	}
	if($('#endHourMinute').val() == ''){
		alert('请选择截至时间');
		return;
	}
	$('#statCfgForm').submit();
}
</script>
</body>
</html>
