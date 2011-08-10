<!--author hsf-->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>月考核时间配置</title>
<%@include file="/common/validator.jsp" %>
<%@include file="/common/ec.jsp" %>
<%@include file="/common/meta.jsp"%>
<script type="text/javascript" src="${ctx}/scripts/jquery.form.js"></script>

<script type="text/javascript">

</script>
</head>

<body>
<div class="x-panel">
<div class="x-panel-header">编辑月考核时间</div>

<div class="x-panel-body">
<div><%@ include file="/common/messages.jsp"%>
</div> 
<s:form action="save" namespace="/assess/task/statcfg/assessCron" theme="simple" name="statCfgForm" id="statCfgForm"  method="POST">
<s:hidden name="assessCron.id"></s:hidden>
<s:hidden name="cronType" value="assessQuartz"></s:hidden>
	<fieldset style="margin: 30px;"><legend>月考核时间信息</legend>
	<table width="100%" >
		<tr>
			<td class="simple" align="center">月考核时间：</td>
			<td class="simple">
			${cronStr}
			<input type="hidden" name="model.id" value="${assessCron.id }"></input>
			<input type="text" size="16" class="required" name="cronDate" id="cronDate" value='${cronDate}' />
						<a href="javascript:void(0)" onClick="if(self.gfPop)gfPop.fPopCalendar(document.getElementById('cronDate'));return false;" HIDEFOCUS>
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
		alert('请统计时间');
		return;
	}
	
	$('#statCfgForm').submit();
}
</script>
</body>
</html>
