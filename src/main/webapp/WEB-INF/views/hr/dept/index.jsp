<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<title></title>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/extjs.jsp" %>
<%@ include file="/common/meta.jsp" %>
<link href="${ctx}/scripts/layout.css" type='text/css' rel='stylesheet'/>
<style type="text/css">
.x-layout-split{
	border: 1px solid #abc;
	border-top: 0 none;
	border-buttom: 0 none;
}
</style>
<script type="text/javascript">
Ext.onReady( function() {
	deptTree=new Ext.tree.TreePanel({
		region :'west',
		title :'部门列表',
		width :200,
		minSize :150,
		maxSize :280,
		split :true,
		autoScroll :true,
		autoHeight :false,
		collapsible :true,
		border:false,
	       loader:new Ext.tree.TreeLoader({
	         dataUrl:"/hr/dept/deptTree.htm"
	       }),
       rootVisible : false,
       root : new Ext.tree.AsyncTreeNode({
  		  text : 'systop',
  		  id : '0',
  		  expanded:true
	   }),
	   listeners:{
           "click":function(node,e){
	        	if (node.id == 0 || node.parentNode.id == 0) {
					return;
	        	}
			    Ext.get('deptMain').dom.src = '${ctx}/hr/dept/edit.htm?model.id='+node.id;
			}
		}
	   });
	 
	new Ext.Viewport( {
		layout :'border',
		defaults : {
			activeItem :0
		},
		items : [ deptTree, {
                  region:'center',
                  contentEl: 'center',
                  border:false,
                  style :'border: 0px solid red',
                  margins:'0 0 0 0'
              } ]
	});
});
</script>
</head>
<body>
<div id="center"><iframe src="${ctx}/hr/dept/edit.htm" id="deptMain"
	name="deptMain" style="width:100%; height:100%; border:0px;" frameborder="0"></iframe></div>
</body>
</html>
