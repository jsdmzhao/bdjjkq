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
  <div class="x-panel-header">管理文章</div>
    <div class="x-toolbar" style="height:36px;">
      <table width="99%">
        <tr>
         <td style="padding:5px;"> 
            <s:form action="index.htm" id="frm">
            <s:hidden name="model.catalog.id" id="catalogId"/>
            <div style="float:left;padding-left:5px;">所属栏目：</div><div id="comboxWithTree" style="float:left;"></div>
            <div style="float:left;padding-left:5px;">
                 <img src="${ctx}/scripts/ec/images/clear.png" style="cursor:pointer;" class="clear_tree" title="清除选中的栏目" >
            </div>
            <div style="float:left;padding-left:5px;">文章标题：</div>
            <div style="float:left;padding-left:5px;">
                <s:textfield name="model.title" cssClass="m_b_t" cssStyle="width:300px;"></s:textfield>
            </div>
            <div style="float:left;padding-left:5px;">
            	<s:submit value="查询" cssClass="btn-gray-s"></s:submit>
            </div>
            </s:form>
         </td>
         <td align="right" style="vertical-align: center;height:24px;line-height:24px;">
         <a href="editNew.htm?model.catalog.id=${catalog.id}" target="_self"> 新建文章</a> 
         <s:if test="#request.tops.size()>0">
           &nbsp;|&nbsp;
           <a href="javascript:void(0)" onclick="sort()">置顶文章排序</a>
         </s:if>
         <s:else>
            &nbsp;|&nbsp;
           <span style="color:#999;" title="请进入具体栏目下执行此操作">置顶文章排序</span>
         </s:else>
         <s:if test="#request.parent.parent != null">
         &nbsp; | &nbsp;
          <a href="index.htm?parent.id=${parent.parent.id}"> 返回<b>${parent.parent.name}</b></a> 
         </s:if>
         </td>
       </tr>
     </table>
   </div>   

   <div class="x-panel-body">
     <div style="margin-left:-3px;" align="center">
	<ec:table items="items" var="item" retrieveRowsCallback="limit" sortRowsCallback="limit" 
	action="index.htm"
	useAjax="true" doPreload="false"
	maxRowsExported="1000" 
	pageSizeList="20,50,100" 
	editable="false" 
	sortable="false"	
	rowsDisplayed="20"	
	generateScript="true"	
	resizeColWidth="true"	
	classic="false"	
	width="100%" 	
	height="500px"	
	minHeight="300" 
	toolbarContent="navigation|pagejump|pagesize|refresh|extend|status"  
	>
	<ec:row>
	   	<ec:column width="100" property="_3" title="操作"  style="text-align:center;">
	   	    <a href="edit.htm?model.id=${item.id}" class="oa">编辑</a>
	   	    |
	   	    <a href="remove.htm?model.id=${item.id}" class="oa" onclick="javascript:return confirm('确定删除吗？');">删除</a>
	   	</ec:column>
	   	<ec:column width="100" property="_4" title="审核"  style="text-align:center;">
	   	   <a href="javascript:;" onclick="check('${item.id}', true);" class="oa" style="color:green;">通过</a>
	   	   |
	   	   <a href="javascript:;" onclick="check('${item.id}', false);" class="oa" style="color:red;">拒绝</a>
	   	</ec:column>
	   	<ec:column width="100" property="_5" title="置顶"  style="text-align:center;">
	   	   <s:if test="!#attr.item.isOnTop">
	   	     <span style="color:#ccc;">未置顶</span>
	   	     <a href="javascript:;" onclick="top('${item.id}', true);" class="oa" style="color:green;">置顶</a>
	   	   </s:if>
	   	   <s:else>
	   	     <span style="color:#666;">已置顶(${item.sortOrder<1000?item.sortOrder:"?"})</span>
	   	     <a href="javascript:;" onclick="top('${item.id}', false)" class="oa" style="color:red;">取消</a>
	   	   </s:else>
	   	   
	   	</ec:column>
	   	<ec:column width="60" property="_6" title="附件"  style="text-align:center;">
	   	   <s:if test="#attr.item.attachments.size()>0">
	   	       <a href="edit.htm?model.id=${item.id}#atta">
	   	       <span class="attach" style="cursor:pointer;"
	   	       title="<s:iterator value="#attr.item.attachments">《<s:property value="name"/>》</s:iterator>">
	   	       
	   	       </span>
	   	       </a>
	   	   </s:if>
	   	</ec:column>
	   	<ec:column width="300" property="_0" title="文章标题">
	   	   <a href="edit.htm?model.id=${item.id}" class="oa">${item.title}</a>
	   	</ec:column>
	   	<ec:column width="100" property="_1" title="所属栏目">
	   	   <a href="index.htm?model.catalog.id=${item.catalog.id}" class="oa" title="点击查看该栏目下文章">${item.catalog.name}</a>
	   	</ec:column>
	   	<%--
	   	<ec:column width="70" property="createUser" title="创建者"></ec:column>
	   	<ec:column width="110" property="createTime" title="创建时间" cell="date" format="yyyy-MM-dd hh:mm"></ec:column>
	   	 --%>
	   	<ec:column width="70" property="updateUser" title="更新者"></ec:column>
	   	<ec:column width="110" property="updateTime" title="更新时间" cell="date" format="yyyy-MM-dd hh:mm"></ec:column>
	   	<ec:column width="70" property="_2" title="审核情况" style="text-align:center;">
	   	    <s:if test="#attr.item.isChecked">
	   	        <span style="color:green;">已通过</span>
	   	    </s:if>
	   	    <s:else>
	   	        <span style="color:red;">未通过</span>
	   	    </s:else>
	   	</ec:column>
	   	<ec:column width="70" property="checkUser" title="审核者" />	   	
	   	
	</ec:row>
	</ec:table>
  </div>
  </div>
  </div>
<div id="sort" class="x-hidden">
   <div class="x-window-header">置顶文章排序</div>
   <div class="x-window-body" >
     <div style="padding:10px;">
      <div style="marging-bottom:5px;padding-bottom:5px;">拖动下面的文章即可排序。</div>
      <ul id="sortable">
       <s:iterator value="#request.tops" status="st">
       <li id="<s:property value="id"/>">
          <div style="padding:3px;margin:3px;border:1px solid #99bbe8;">
          <s:property value="title"/>
          </div>
       </li>
       </s:iterator>
     </ul>
     </div>
   </div>
</div>  
<jsp:include page="/WEB-INF/views/admin/cms/catalog/catalogSel.jsp">
   <jsp:param value="${catalog.name}" name="initValue"/>
</jsp:include>
<script type="text/javascript">
$(function(){
	$( "#sortable" ).sortable();
});
var win = new Ext.Window({
	  el : 'sort',
	  layout : 'fit',
	  width : 400,
	  height : 450,
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
		    	  data:{'sortIds' : $('#sortable').sortable('toArray').join(",")},
		    	  success: function(data) {
		  			if(data == 'success') {
		  				ECSideUtil.reload('ec');
		  				win.hide();
		  			} else {
		  				alert(data);
		  			}
			  	  }, 
			  	  error: function(xhr) {
			  		alert('置顶文章排序错误：' + xhr.status);
			  	  }
		      });
		      
		    }
		  }]
	});
	
function sort() {
	win.show();
}
var nodeIdCallback = function(nodeId) {
	 Ext.get('catalogId').dom.value = nodeId;
}; 

function check(id, passed) {
	$.ajax({
		url:'check.htm',
		data:{'model.id' : id, 'model.isChecked' : passed},
		success:function(data) {
			if(data == 'success') {
				ECSideUtil.reload('ec');
			} else {
				alert(data);
			}
		},
		error:function(xhr) {
			alert('审核错误:' + xhr.status);
		}
	});
}
function top(id, ontop) {
	$.ajax({
		url:'check.htm',
		data:{'model.id' : id, 'model.isOnTop' : ontop},
		success:function(data) {
		   ECSideUtil.reload('ec');			
		},
		error:function(xhr) {
			alert('置顶错误:' + xhr.status);
		}
	});
}

</script>
</body>
</html>