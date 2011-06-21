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
<style type="text/css">
.ecSide .headZone {
    background-color: #fff;
    border-color: #333;
}

/*表格斜线*/ 
.out{ 
   border-top:50px #e3e3e3 solid;/*上边框宽度等于表格第一行行高*/ 
   width:0px;/*让容器宽度为0*/ 
   height:0px;/*让容器高度为0*/ 
   border-left:100px #fff solid;/*左边框宽度等于表格第一行第一格宽度*/     
   position:relative;/*让里面的两个子容器绝对定位*/ 
} 
b{font-style:normal;display:block;position:absolute;top:-40px;left:-40px;width:35px;} 
em{font-style:normal;display:block;position:absolute;top:-25px;left:-90px;width:55x;} 

</style>
</head>
<body>
<s:form id="removeForm" action="role/remove" method="POST"></s:form>
<div class="x-panel">
  <div class="x-panel-header">管理任务常量</div>
    <div class="x-toolbar" style="height:26px;">
      <table width="99%" >
  	  <tr style="margin-top:  7px;"> 		
  		<s:form name="queryForm" id="queryForm" namespace="/assess/task/statcfg" action="taskConstIndex" theme="simple">	    	
			<td width="10">
				<table style="padding-top: 3px;"> 
					<tr> 
						<td>
							&nbsp;&nbsp;&nbsp;&nbsp;名称:&nbsp;&nbsp;<s:textfield name="model.name"></s:textfield>
						</td> 
						
						
						
					</tr> 
			</table> 
		</td>
		<td>
			&nbsp;&nbsp;&nbsp;&nbsp;		
			<input type="submit" value="查询" class="button" style="margin-top: 3px;">			   			
		</td>	 
		</s:form>  	
		<td align="right">
		<table>
			<tr>
				<td><span class="ytb-sep"></span></td>
				<td>
					<a href="${ctx}/assess/task/statcfg/editTaskConst.htm"> 添加任务常量</a>	
				</td>
			</tr>
		</table>
		</td>	
  	  </tr>
  	</table>
   </div>   

   <div class="x-panel-body">
     <div style="margin-left:-3px;" align="center">
	<ec:table items="items" var="item" retrieveRowsCallback="process" sortRowsCallback="process" 
	action="taskConstIndex.htm"
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
	showHeader="true"	
	toolbarContent="navigation|pagejump|pagesize|export|refresh|extend|status">    
	<ec:row>
	   	<ec:column width="30" property="_s" title="No." value="${GLOBALROWCOUNT}" sortable="false" style="text-align:center"/>	
		<ec:column width="100" property="name" title="任务名称" tipTitle="${item.name}" ellipsis="true" sortable="false"/>
		<ec:column width="100" property="transgressStatItem.name" title="任务统计项" tipTitle="${item.specialTransgressStatItem.name}" ellipsis="true" sortable="false"/>
		<ec:column width="60" property="total" title="总分" tipTitle="${item.total}" ellipsis="true" sortable="false"/>
		<ec:column width="60" property="aimCount" title="目标数量" tipTitle="${item.aimCount}" ellipsis="true" sortable="false"/>
		<ec:column width="90" property="addPoint" title="超1例加分" tipTitle="${item.addPoint}" ellipsis="true" sortable="false"/>
		<ec:column width="90" property="decreasePoint" title="少1例减分" tipTitle="${item.decreasePoint}" ellipsis="true" sortable="false"/>
		<ec:column width="100" property="_4" title="额外加分项"  style="text-align:center;">
	   	   <c:choose>
	   	   	<c:when test="${item.hasSpecial}">
	   	   		${item.specialTransgressStatItem.name }
	   	   	</c:when>
	   	   	<c:otherwise>
	   	   		无
	   	   	</c:otherwise>
	   	   </c:choose>
	   	</ec:column>
	   	<ec:column width="100" property="_4" title="额外加分分值"  style="text-align:center;">
	   	   <c:choose>
	   	   	<c:when test="${item.hasSpecial}">
	   	   		${item.specialPoint }
	   	   	</c:when>
	   	   	<c:otherwise>
	   	   		--
	   	   	</c:otherwise>
	   	   </c:choose>
	   	</ec:column>
	   		
	 	<ec:column width="160" property="_1" title="操作" style="text-align:center" sortable="false">
	 	<%--
			 <a title="查看" href="${ctx}/assess/transgress/statcfg/statItem/view.htm?model.id=${item.id}">查看 </a> | --%>
			 <a title="编辑" href="${ctx}/assess/task/statcfg/editTaskConst.htm?model.id=${item.id}">编辑 </a> |
			 <a title="删除" href="#" onclick="remove('${item.id}')">删除</a>
		</ec:column>	   	
	</ec:row>
	</ec:table>
  </div>
  </div>
</div>
<script type="text/javascript">
function remove(id) {
    /*Ext.MessageBox.confirm('提示','确认要删除此项目吗？删除后不能恢复！', function(btn){
        if (btn == 'yes') {
          window.location = "${ctx}/assess/transgress/statcfg/statItem/remove.htm?model.id=" + id;
        }
    });*/
    if(!confirm("确定要删除该任务吗?")){
        return;
    }
    window.location = "${ctx}/assess/task/statcfg/removeTaskConst.htm?model.id=" + id;
}
</script>
<script type="text/javascript">

/**
 * 根据选中的topType值初始化subTypes选择框
 */
function initSubTypes(firstLevelTypeId,curVal){
	$('secondLevelTypes').html('');
	if(firstLevelTypeId == ''){
		return;
	}
	$.ajax({
		url:'${ctx}/assess/transgress/statcfg/statItem/getSecondLevelTypesByFirstLevel.htm',
		data:{'firstLevelTypeId':firstLevelTypeId},
		dataType:'json',
		success:function(data){
			var html = [];
			html.push("<option value=''>请选择</option>");
			$.each(data,function(idx,item){
				if(!curVal){
					html.push("<option value='" + item.id + "'>" + item.code + "</option>");
				}else{
					if(curVal == item.id){
						html.push("<option selected value='" + item.id + "'>" + item.code + "</option>"); 
					}
				}
			});
			$('#secondLevelTypes').html(html.join(''));
			$('#l_typeB').hide();
		},
		beforeSend:function(){
			$('#l_typeB').show();
		},
		error:function(){
			$('#l_typeB').hide();
		}
	});
}
</script>
</body>
</html>