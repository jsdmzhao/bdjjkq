<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<%@page import="java.util.Date" %>
<%@page import="java.util.Calendar" %>
<html>
<head>
<style type="text/css">
td {padding:3px;}
</style>
<title></title>
<%@include file="/common/ec.jsp" %>
<!--<%@include file="/common/extjs.jsp" %>
--><%@include file="/common/meta.jsp" %>
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
<%
	Calendar c = Calendar.getInstance();
	
	c.set(Calendar.HOUR_OF_DAY, 0);
	c.set(Calendar.MINUTE, 0);
	c.set(Calendar.SECOND,0);
	
	Date today= c.getTime();
	
	c.add(Calendar.DAY_OF_MONTH, -1);
	Date yesterday  = c.getTime();
%>
<s:form id="removeForm" action="role/remove" method="POST"></s:form>
<div class="x-panel">
  <div class="x-panel-header">考核记录</div>
    <div class="x-toolbar" style="height:26px;">
      <table width="99%" >
  	  <tr style="margin-top:  7px;"> 		
  		<s:form name="queryForm" id="queryForm" namespace="/assess/task/empduty" action="index" theme="simple">	    	
			<td width="10">
				<table style="padding-top: 3px;"> 
					<tr> 
						
						<td>
						
							警员姓名：<s:textfield name="model.employee.name" cssStyle="width:60px;"></s:textfield>
						</td> 	
						<td>
							考核部门
						</td>
						<td>
							<s:select list="deptCodeList" id="deptCodeList" headerKey="" headerValue="请选择" 
                     	name="deptCode" listKey="key" listValue="value"  cssClass="m_t_b" 
                     	cssStyle="width:200px;padding-left:2px;"  ></s:select>
						</td>
												<td>&nbsp;&nbsp;&nbsp;&nbsp;
						从<%--<s:date name="beginTime"format="yyyy-MM-dd HH:mm"/> --%>
						<input type="text" size="16" name="beginTime" id="beginTime" value="<fmt:formatDate value="${beginTime }" pattern="yyyy-MM-dd"/>" />
						<a href="javascript:void(0)" onClick="if(self.gfPop)gfPop.fPopCalendar(document.getElementById('beginTime'));return false;" HIDEFOCUS>
					<img src="${ctx}/js/calendar/calbtn.gif" alt="" name="popcal" id="popcal" 
					width="34" height="22" border="0" align="absmiddle"></a>
						<%--
						<input type="text"
								name="beginTime"
								value='<s:date name="beginTime"format="yyyy-MM-dd HH:mm"/>'
								onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm'})"
								class="Wdate" style="width: 128px; height: 16px"
								readonly="readonly" />-%>
								<input type="text" name="beginTime" value="<s:date name="beginTime"format="yyyy-MM-dd HH:mm"/>" readOnly onClick="setDayHM(this);">
								&nbsp;&nbsp;&nbsp;&nbsp;--%>
						&nbsp;&nbsp;&nbsp;&nbsp;到&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="text" size="16" name="endTime" id="endTime" value="<fmt:formatDate value="${endTime }" pattern="yyyy-MM-dd"/>" />
						<a href="javascript:void(0)" onClick="if(self.gfPop)gfPop.fPopCalendar(document.getElementById('endTime'));return false;" HIDEFOCUS>
					<img src="${ctx}/js/calendar/calbtn.gif" alt="" name="popcal" id="popcal" 
					width="34" height="22" border="0" align="absmiddle"></a>
						<%-- <input type="text"
								name="endTime"
								value='<s:date name="endTime" format="yyyy-MM-dd HH:mm"/>'
								onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm'})"
								class="Wdate" style="width: 128px; height: 16px"
								readonly="readonly" />  
								<input type="text" name="endTime" value="<s:date name="endTime"format="yyyy-MM-dd HH:mm"/>" readOnly onClick="setDayHM(this);">
--%>
	<iframe width=188 height=166 
	name="gToday:datetime:${ctx}/js/calendar/agenda.js:gfPop:${ctx}/js/calendar/plugins_time.js" 
	id="gToday:datetime:${ctx}/js/calendar/agenda.js:gfPop:${ctx}/js/calendar/plugins_time.js" 
	src="${ctx}/js/calendar/ipopeng.html" scrolling="no" frameborder="0" 
	style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;">
</iframe>
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
					<a href="${ctx}/assess/task/empduty/edit.htm"> 添加考核记录</a>	
				</td>
			</tr>
		</table>
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
	showHeader="true"	
	toolbarContent="navigation|pagejump|pagesize|export|refresh|extend|status">    
	<ec:row>
	   	<ec:column width="30" property="_s" title="No." value="${GLOBALROWCOUNT}" sortable="false" style="text-align:center"/>
	   	<ec:column width="300" property="taskDetail.task.name" title="考核项目名称" tipTitle="${task.name}" ellipsis="true" sortable="false"/>
	    <ec:column width="100" property="taskDetail.task.taskType.name" title="考核项目类别" tipTitle="${task.name}" ellipsis="true" sortable="false"/>
		<ec:column width="60" property="employee.name" title="警员姓名" tipTitle="${employee.name}" ellipsis="true" sortable="false"/>
		<ec:column width="100" property="employee.dept.name" title="所属部门" tipTitle="所属部门"></ec:column>
		<ec:column width="280" property="_5" title="考核项明细" tipTitle="考核项明细">
			${item.taskDetail.name} ${item.taskDetail.addOrDecrease eq '0' ? '减分':'加分'} ${item.taskDetail.point } ${ item.taskDetail.decreaseLeader eq '0'?'':'包岗领导同扣'}
		</ec:column>
		<ec:column width="150" property="_2" format="yyyy-MM-dd HH:mm:ss" title="记录时间" tipTitle="记录时间">
			<fmt:formatDate value="${item.recordTime}" pattern="yyyy年MM月dd日 HH:mm:ss"/>
		</ec:column>
	 	<ec:column width="110" property="_1" title="操作" style="text-align:center" sortable="false">
	 	
			 <a title="查看" href="${ctx}/assess/task/empduty/edit.htm?model.id=${item.id}">查看 </a> |
			 <a title="删除" href="#" onclick="remove('${item.id}')">删除</a><%--| 
			 <a title="编辑" href="${ctx}/assess/task/empduty/edit.htm?model.id=${item.id}">编辑 </a> |
			 --%>
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
    if(!confirm("确定要删除该任务类型吗?")){
        return;
    }
    window.location = "${ctx}/assess/task/empduty/remove.htm?model.id=" + id;
}
</script>

</body>
</html>