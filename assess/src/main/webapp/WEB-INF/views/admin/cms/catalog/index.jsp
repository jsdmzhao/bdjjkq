<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<html>
<head>
<style type="text/css">
td {padding:3px;}
</style>
<title></title>
<%@include file="/common/ec.jsp" %>
<%@include file="/common/extjs.jsp" %>
<%@include file="/common/meta.jsp" %>
<script type="text/javascript" src="${ctx}/scripts/jqueryui/jquery-ui.js"></script>

</head>
<body>
<s:form id="removeForm" action="role/remove" method="POST"></s:form>
<div class="x-panel">
  <div class="x-panel-header">管理【${parent.name}】下的栏目</div>
    <div class="x-toolbar">
      <table width="99%">
        <tr>
         <td style="padding:5px;"> 
            <div id="comboxWithTree"></div>
            <s:form action="index.htm" id="frm"><s:hidden name="parent.id" id="parentId"/></s:form>
         </td>
         <td align="right" style="vertical-align: center;height:24px;line-height:24px;">
         <a href="editNew.htm?parent.id=${parent.id}"> 新建栏目</a> 
         &nbsp;|&nbsp;
         <a href="javascript:void(0)" onclick="sort()">栏目排序</a>
         <s:if test="#request.parent.parent != null">
         &nbsp; | &nbsp;
          <a href="index.htm?parent.id=${parent.parent.id}"> 返回<b>${parent.parent.name}</b></a> 
         </s:if>
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
	    <ec:column width="120" property="_y" title="操作" style="text-align:center;">
	   	   <a href="edit.htm?model.id=${item.id}" class="oa">编辑</a>
	   	   |
	   	   <a href="javascript:void(0);" onclick="del('${item.id}', '${item.name}')" class='oa'>删除</a>
	   	   
	   	</ec:column>
	   	
	   	<ec:column width="100" property="_xt" title="序号">
	   	  <span style="color:#888;cursor:pointer;" onclick="sort()" title="点击排序">${item.sortOrder}</span>
	   	</ec:column>
	   	
	   	<ec:column width="200" property="_0" title="栏目名称">
	   	   <a href="index.htm?parent.id=${item.id}" class="oa" title="点击查看下级栏目">${item.name}</a>
	   	</ec:column>
	   	<ec:column width="200" property="_x" title="栏目属性">
	   	   <s:if test="#attr.item.isEnabled"><b style="color:#607eb0;">可用</b></s:if>
	   	   &nbsp;
	   	   <s:if test="#attr.item.isVisible"><b style="color:#a197b3;">可见</b></s:if>
	   	   &nbsp;
	   	   <s:if test="#attr.item.isListInNav"><b style="color:#488048;">导航</b></s:if>
	   	    &nbsp;
	   	   <s:if test="#attr.item.isExternal"><b style="color:#339933;">外部</b></s:if>
	   	</ec:column>
	   	<ec:column width="60" property="_t" title="下级栏目" style="text-align:center;">
	   	   <a href="index.htm?parent.id=${item.id}" class="oa"><s:property value="#attr.item.children.size()"/></a>
	   	</ec:column>
	   	<ec:column width="250" property="url" title="外部栏目URL"/>
	   	
	</ec:row>
	</ec:table>
  </div>
  </div>
  </div>
<div id="sort" class="x-hidden">
   <div class="x-window-header">栏目排序</div>
   <div class="x-window-body" >
     <div style="padding:10px;">
      <div style="marging-bottom:5px;padding-bottom:5px;">拖动下面的栏目即可排序。</div>
      <ul id="sortable">
       <s:iterator value="#request.items" status="st">
       <li id="<s:property value="id"/>">
          <div style="padding:3px;margin:3px;border:1px solid #99bbe8;">
          <s:property value="name"/>
          </div>
       </li>
       </s:iterator>
   </ul>
     </div>
   </div>
</div>  
<jsp:include page="catalogSel.jsp">
   <jsp:param value="${parent.name}" name="initValue"/>
</jsp:include>
<script type="text/javascript">
$(function() {
	$( "#sortable" ).sortable();
});

var win = new Ext.Window({
	  el : 'sort',
	  layout : 'fit',
	  width : 250,
	  height : 350,
	  closeAction : 'hide',
	  plain : false,
	  modal : true,
	  bodyStyle : 'padding:5px;',
	  buttonAlign : 'center',
	  buttons : [{
	    text:'关闭',
	    handler:function(){
	      win.hide();
	    }
	  },
	  {
		    text:'确定',
		    handler:function(){
		      $.ajax({
		    	  url:'sort.htm',
		    	  data:{'sortedIds' : $('#sortable').sortable('toArray').join(",")},
		    	  success: function(data) {
		  			if(data == 'success') {
		  				ECSideUtil.reload('ec');
		  				win.hide();
		  			} else {
		  				alert(data);
		  			}
			  	  }, 
			  	  error: function(xhr) {
			  		alert('栏目排序错误：' + xhr.status);
			  	  }
		      });
		      
		    }
		  }]
	});
function sort() {
	win.show();
}
var nodeIdCallback = function(nodeId) {
	 $('#parentId').val(nodeId);
	 $('#frm').submit();
};
function del(id, name) {
	if(!confirm("确定要删除栏目\"" + name + "\"吗？")) {
		return;
	}
	$.ajax({
		url : 'remove.htm',
		data: {'model.id' : id},
		success: function(data) {
			if(data == 'success') {
				ECSideUtil.reload('ec');
			} else {
				alert(data);
			}
		}, 
		error: function(xhr) {
			alert('刪除欄目錯誤：' + xhr.status);
		}
	});
}
</script>
</body>
</html>