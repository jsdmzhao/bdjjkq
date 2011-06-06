<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<%@include file="/common/extjs.jsp"%>
<%@include file="/common/meta.jsp"%>
<LINK href="${ctx}/styles/thinCombobox.css" type='text/css' rel='stylesheet'>
	
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
<div class="x-panel-header">
<s:if test="model.id != null">
 编辑部门 (当前节点：${model.name})
</s:if>
<s:else> 
新建部门
</s:else>

</div>
<s:if test="model.id != null">
<div id='toolbar'>
</div>
</s:if>
<div class="x-panel-body">
<div><%@ include file="/common/messages.jsp"%>
</div>
<table width="500" align="center">
	<tr>
		<td align="center"><s:form namespace="/hr" action="dept/save"
			method="post" validate="true" theme="simple">
			<s:hidden id="model.id" name="model.id" />
			<s:hidden id="model.deptSort" name="model.deptSort" />
			<s:hidden id="model.serialNo" name="model.serialNo" />
			<fieldset style="margin: 10px;"><legend>部门信息</legend>
			<table width="400">
				<tr>
					<td style="text-align: right;">部门名称：</td>
					<td style="text-align: left;"><s:textfield id="model.name"
						name="model.name" /> <font color="red">*</font></td>
				</tr>
				<tr>
					<td style="text-align: right;">电话：</td>
					<td style="text-align: left;"><s:textfield id="model.phone"
						name="model.phone" /></td>
				</tr>
				<tr>
					<td style="text-align: right;">传真：</td>
					<td style="text-align: left;"><s:textfield id="model.fax"
						name="model.fax" /></td>
				</tr>
				<tr>
					<td style="text-align: right;">上级部门：</td>
					<td style="text-align: left;padding:5px;" valign="middle">
						<div id="comboxWithTree" style="float: left;"></div>
						&nbsp;<font color="red">*</font> 
						<s:hidden id="parentId" name="model.parentDept.id" />
				  </td>
				</tr>
				<!-- tr>
					<td style="text-align:right;">部门主管：</td>
					<td style="text-align:left;"><s:textfield id="model.leader.id" name="model.leader.id" /></td>
				</tr>
				<tr>
					<td style="text-align:right;">上级主管领导：</td>
					<td style="text-align:left;"><s:textfield id="model.superior.id" name="model.superior.id" /></td>
				</tr>
				<tr>
					<td style="text-align:right;">上级分管领导：</td>
					<td style="text-align:left;"><s:textfield id="model.subSuperior.id" name="model.subSuperior.id" /></td>
				</tr-->
				<tr>
					<td style="text-align: right;">部门职能：</td>
					<td style="text-align: left;"><s:textarea id="model.descn"
						name="model.descn" cols="30" rows="3" /></td>
				</tr>
			</table>
			</fieldset>
			<table width="100%" style="margin-bottom: 10px;">
				<tr>
					<td style="text-align: center;">
						<input class="btn-gray-s" type="submit" value='保存'/>
					</td>
				</tr>
			</table>
		</s:form></td>
	</tr>
</table>
</div>
</div>
<script type="text/javascript">
Ext.onReady(function(){
  //刷新树状列表
  <s:if test="runState == 1">
  	if (window.parent.deptTree) {
  	  window.parent.deptTree.root.reload();
  	}
  </s:if>
});
</script>

<script>
   var initValue = '';
   <s:if test="model.parentDept != null">
     initValue = '${model.parentDept.name}';
   </s:if>
   var deptName = "${model.name}";
   var param = "${model.id}";
</script>

<script type="text/javascript" src="${ctx}/scripts/custom/hr/dept-combo.js"></script>
<script type="text/javascript" src="${ctx}/scripts/custom/hr/dept-edit.js"></script>
<script type="text/javascript">
nodeIdCallback = function(nodeId) {
	Ext.get('parentId').dom.value = nodeId;
};
</script>
</body>
</html>