<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<html>
<head>
<%@include file="/common/ec.jsp" %>
<%@include file="/common/extjs.jsp" %>
<%@include file="/common/meta.jsp" %>
<title></title>

</head>
<body>
<div style="display:none;" id="hiddenForm">
<s:form action="employee/index" theme="simple" id="queryFrm">
<s:hidden name="deptId" id="deptId"/>
员工姓名：<s:textfield name="model.name" size="15"/>
</s:form>
</div>
<div class="x-panel">
  <div class="x-panel-header">
  	员工管理&nbsp;&nbsp;
  	<s:if test="deptName == null">
  		所有部门下的员工 列表
  	</s:if>
  	<s:else>
  		(${deptName})部门下的员工列表
  	</s:else>
  </div>
  <div class="x-toolbar">
	<div id="toolbar"></div>
  </div>
  <div class="x-panel-body">
    <div style="margin-left:-3px;" align="center">
      <ec:table items="items" var="item" retrieveRowsCallback="process"
	    action="index.do"
	    useAjax="false" doPreload="false"
	    xlsFileName="员工列表.xls" 
	    maxRowsExported="10000000"
	    pageSizeList="30,50,100,500,1000" 
		editable="false" 
		sortable="true"	
		rowsDisplayed="30"	
		generateScript="true"	
		resizeColWidth="true"	
		classic="false"	
		width="100%" 	
		height="360px"	
		minHeight="360" 
		>
		  <ec:row>
		    <ec:column width="50" property="_s" title="选择" style="text-align:center" viewsAllowed="html" sortable="false">
	       		<input type="checkbox" name="selectedItems" id="selectedItems" value="${item.id}" class="checkbox"/>
	    	</ec:column>
	    	<ec:column width="60" property="user.loginId" title="登录名" />
			<ec:column width="60" property="name" title="姓名" />
			<!-- 都是同一部门不用显示 -->
			<s:if test="deptId == null">
			<ec:column width="70" property="dept.name" title="所属部门" sortable="false"/>
			</s:if>
			<ec:column width="70" property="place" title="职位" sortable="true"/>
			<ec:column width="70" property="mobil" title="手机" sortable="false"/>
			<ec:column width="100" property="user.email" title="电子邮箱" sortable="false"/>
			<ec:column width="40" property="_0" title="编辑" style="text-align:center" sortable="false"  viewsAllowed="html">
		  	  <a href="edit.do?model.id=${item.id}"><img src="${ctx}/images/icons/modify.gif" border="0"/></a>		  
			</ec:column>
			<ec:column width="40" property="_0" title="角色" style="text-align:center" sortable="false">
			  <a href="#" onclick="javascript:assignRoles('${item.user.id}')">
				<img src="${ctx}/images/icons/role.gif" border="0" title="分配角色"/>
			  </a>
			</ec:column>
	  	  </ec:row>
		</ec:table>
      </div>
    </div>
  </div>
  <div id="win" class="x-hidden">
    <div class="x-window-header">角色列表</div>
    <div id="role_grid"></div>
  </div>
<script type="text/javascript" src="${ctx}/scripts/custom/user/user.js"></script>
<script type="text/javascript" src="${ctx}/scripts/custom/hr/employee-index.js"></script>
<script type="text/javascript">
var deptName = '${deptName}';
Ext.onReady(function(){
  //刷新树状列表
  <s:if test="runState == 1">
  	if (window.parent.deptTree) {
  	  window.parent.deptTree.root.reload();
  	}
  </s:if>
});
</script>
</body>
</html>