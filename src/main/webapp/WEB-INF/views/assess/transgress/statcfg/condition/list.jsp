<!--author hsf-->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>统计条件配置</title>
<%@include file="/common/extjs.jsp"%>
<%@include file="/common/meta.jsp"%>
<%@include file="/common/validator.jsp"%>

<script type="text/javascript">
$("#statCfgForm").validate({
	rules: {
		'model.beginHourMinute':  {
			required : true
		},
		'model.endHourMinute':  {
			required : true
		}
	},
	messages: {
		'model.beginHourMinute': {
			required: "请输入起始时间"
		},
		'model.endHourMinute':  {
			required: "请输入截至时间"
		}
	}
});
</script>
</head>

<body>
<div class="x-panel">
<div class="x-panel-header">编辑统计条件</div>

<div class="x-panel-body">
<div><%@ include file="/common/messages.jsp"%>
</div> 
<s:form action="save" namespace="/assess/transgress/statcfg/condition" theme="simple" name="statCfgForm" id="statCfgForm" validate="true" method="POST">
<s:hidden name="model.id"></s:hidden>
	<fieldset style="margin: 30px;"><legend>统计条件信息</legend>
	<table width="100%">
		<tr>
			<td class="simple">起始时间：</td>
			<td class="simple">
			<input type="text"
								name="model.beginHourMinute"
								
								value='${model.beginHourMinute }'
								onfocus="WdatePicker({skin:'whyGreen',dateFmt:'HH:mm:ss'})"
								class="Wdate" style="width: 128px; height: 16px"
								readonly="readonly" class="required"/><font color='red'>*</font></td>
		</tr>
		<tr>
			<td class="simple">截至时间：</td>
			<td class="simple"><input type="text"
								name="model.endHourMinute"
								value='${model.endHourMinute}'
								onfocus="WdatePicker({skin:'whyGreen',dateFmt:'HH:mm:ss'})"
								class="Wdate" style="width: 128px; height: 16px"
								readonly="readonly" class="required"/><font color='red'>*</font></td>
		</tr>		
	</table>
	<table width="100%" style="margin-bottom: 10px;">
		<tr>
			<td style="text-align: center;"><s:submit value="保存"
				cssClass="button"></s:submit> <s:reset value="重置" cssClass="button"></s:reset></td>
		</tr>
	</table>
	</fieldset>
</s:form>
</div>
</div>
</body>
</html>
