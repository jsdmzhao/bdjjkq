<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<html>
<head>

<title></title>
<%@include file="/common/ec.jsp" %>
<%@include file="/common/extjs.jsp" %>
<%@include file="/common/meta.jsp" %>
</head>
<body>
<s:form id="removeForm" action="role/remove" method="POST"></s:form>
<div class="x-panel">
  <div class="x-panel-header">角色管理</div>
    <div class="x-toolbar">
      <table width="99%">
        <tr>
          <td> 
            <s:form action="role/index" theme="simple">
	         角色名称：<s:textfield theme="simple" name="model.name" size="15"></s:textfield>
	         角色描述：<s:textfield theme="simple" name="model.descn" size="15"></s:textfield>
	        &nbsp;&nbsp;<s:submit value="查询" cssClass="button"></s:submit>
         </s:form>
         </td>
         <td align="right">
         <table> 
           <tr>
             <td><a href="${ctx}/security/role/index.htm"><img src="${ctx}/images/icons/house.gif"/> 角色管理首页</a></td>
             <td><span class="ytb-sep"></span></td>
             <td><a href="${ctx}/security/role/editNew.htm"><img src="${ctx}/images/icons/add.gif"/> 新建角色</a></td>
             <td><span class="ytb-sep"></span></td>
             <td><a href="#" onclick="onRemove({noneSelectedMsg:'请至少选择一个角色.',
             confirmMsg:'确认要删除角色吗？'})"><img src="${ctx}/images/icons/delete.gif"/> 删除角色</a></td>
           </tr>
         </table>
         <td>
       </tr>
     </table>
   </div>   

   <div class="x-panel-body">
     <div style="margin-left:-3px;" align="center">
	<ec:table items="items" var="item" retrieveRowsCallback="process" sortRowsCallback="process" 
	action="index.htm"
	useAjax="true" doPreload="false"
	maxRowsExported="1000" 
	pageSizeList="5,10,20,100" 
	editable="false" 
	sortable="true"	
	rowsDisplayed="10"	
	generateScript="true"	
	resizeColWidth="true"	
	classic="false"	
	width="100%" 	
	height="277px"	
	minHeight="200" 
	toolbarContent="navigation|pagejump|pagesize|refresh|extend|status"  
	>
	<ec:row>
	    <ec:column width="50" property="_s" title="选择" style="text-align:center" viewsAllowed="html" sortable="false">
	       <s:if test="#attr.item.isSys==1">
	           <input type="checkbox" class="checkbox" disabled="disabled"/>
	       </s:if>
	       <s:else>
	       <input type="checkbox" name="selectedItems" id="selectedItems" value="${item.id}" class="checkbox"/>
	       </s:else>
	    </ec:column>
		<ec:column width="40" property="_0" title="序号" value="${GLOBALROWCOUNT}"  sortable="false"/>
		<ec:column width="100" property="name" title="角色名称" />
		<ec:column width="150" property="descn" title="角色描述" />
		<ec:column width="100" property="_1" title="可查测项数量" sortable="false">
		   <s:if test="#attr.item.methods.size() == 0">
		      <font color='red'>0</font>
		   </s:if>
		   <s:else>
  		       <s:property value="#attr.item.methods.size()"/>
		   </s:else>
		</ec:column>
		<ec:column width="60" property="_2" title="系统角色" style="text-align:center;">
		   <s:if test="#attr.item.isSys==1">
		      <span style="color:red">是</span>
		   </s:if>
		   <s:else>
		      <span style="">否</span>
		   </s:else>
		</ec:column>
		<ec:column width="40" property="_0" title="权限" style="text-align:center" sortable="false">
		 <a href="#" onclick="javascript:assignPermissions('${item.id}')">
		       <img src="${ctx}/images/icons/authority.gif" border="0" title="分配权限"/>
		   </a>
		</ec:column>		
		<ec:column width="40" property="_0" title="操作" style="text-align:center" viewsAllowed="html" sortable="false">
		  
		   <a href="edit.htm?model.id=${item.id}"> 
		      <img src="${ctx}/images/icons/modify.gif" style="border:0px" title="编辑"/>
		   </a>
		  
		</ec:column>
	</ec:row>
	</ec:table>
  </div>
  </div>
  </div>
<%--
<%@include file="/pages/admin/security/role/assignPermissions.jsp" %>
<%@include file="/common/dwrLoadingMessage.jsp" %>
--%>
<div id="win" class="x-hidden">
    <div class="x-window-header">许可列表</div>
    <div id="perm_grid"></div>
</div>
<script type="text/javascript" src="${ctx}/scripts/custom/role/role.js">
</script>
</body>
</html>