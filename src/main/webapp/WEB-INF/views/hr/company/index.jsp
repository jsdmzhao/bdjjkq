<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<%@include file="/common/extjs.jsp"%>
<%@include file="/common/meta.jsp"%>
<style type="text/css">
input {
   margin:5px 3px 0px 3px;
   width:270px;
}
fieldset {
   padding:5px;
}


</style>
<title></title>
</head>
<body>
<div class="x-panel">
<div class="x-panel-header">公司管理</div>

<div class="x-panel-body">
<div><%@ include file="/common/messages.jsp"%>
</div>
<table width="500" align="center">
	<tr>
		<td align="center"><s:form namespace="/hr/company" action="save"
			validate="true" theme="simple">
			<s:hidden id="model.id" name="model.id" />
			<s:hidden id="model.deptSort" name="model.deptSort" />
			<fieldset style="margin: 10px;"><legend>公司信息</legend>
			<table width="400">
				<tr>
					<td style="text-align:right;">公司名称：</td>
					<td style="text-align:left;"><s:textfield id="model.name" name="model.name" /> <font
						color="red">*</font></td>
				</tr>
				<tr>
					<td style="text-align:right;">电话：</td>
					<td style="text-align:left;"><s:textfield id="model.phone" name="model.phone" /></td>
				</tr>
				<tr>
					<td style="text-align:right;">传真：</td>
					<td style="text-align:left;"><s:textfield id="model.fax" name="model.fax" /></td>
				</tr>
				<tr>
					<td style="text-align:right;">邮编：</td>
					<td style="text-align:left;"><s:textfield id="model.zip" name="model.zip" /></td>
				</tr>
				<tr>
					<td style="text-align:right;">地址：</td>
					<td style="text-align:left;"><s:textfield id="model.address" name="model.address" /></td>
				</tr>
				<tr>
					<td style="text-align:right;">网站：</td>
					<td style="text-align:left;"><s:textfield id="model.homePage" name="model.homePage" /></td>
				</tr>
				<tr>
					<td style="text-align:right;">电子信箱：</td>
					<td style="text-align:left;"><s:textfield id="model.email" name="model.email" /></td>
				</tr>
				<tr>
					<td style="text-align:right;">开户行：</td>
					<td style="text-align:left;"><s:textfield id="model.bank" name="model.bank" /></td>
				</tr>
				<tr>
					<td style="text-align:right;">账号：</td>
					<td style="text-align:left;"><s:textfield id="model.bankAccount"
						name="model.bankAccount" /></td>
				</tr>
			</table>
			</fieldset>
			<table width="100%" style="margin-bottom: 10px;">
				<tr>
					<td style="text-align: center;"><s:submit value="保存"
						cssClass="btn-gray-s"></s:submit> <s:reset value="重置"
						cssClass="btn-gray-s"></s:reset></td>
				</tr>
			</table>
		</s:form></td>
	</tr>
</table>
</div>
</div>
</body>
</html>