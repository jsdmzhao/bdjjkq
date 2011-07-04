<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%request.setAttribute("ctx", request.getContextPath()); %>
<%@ taglib prefix="jtiger" uri="http://code.google.com/p/jtiger"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<div id="menu" style="display:none">    
	<div id="hr">
		<div style="padding-left:5px;">
			<div style="padding-top:2px">
				<img src="${ctx}/images/arrow_3.png" class="icon">
				<a href="${ctx}/hr/dept/index.htm" target="main">部门管理</a>
			</div>
			
			<div style="padding-top:2px">
				<img src="${ctx}/images/arrow_3.png" class="icon">
				<a href="${ctx}/hr/employee/layout.htm" target="main">警员管理</a>
			</div>					
		</div>
	</div>	
    <div id="menu_constStat">
		<div style="padding-left:5px;">  
			 <div style="padding-top:2px">
					<img src="${ctx}/images/icons/resource.gif" class="icon">
					<a href="${ctx}/assess/transgress/stat/index.htm" target="main">违法统计</a>
			</div>
			 <div style="padding-top:2px">
					<img src="${ctx}/images/icons/resource.gif" class="icon">
					<a href="${ctx}/assess/transgress/simpleStat/index.htm" target="main">单项统计</a>
			</div>						
		</div>
	</div>
	<div id="menu_constStat_cfg">
		<div style="padding-left:5px;">
		  <div style="padding-top:2px">
					<img src="${ctx}/images/icons/resource.gif" class="icon">
					<a href="${ctx}/assess/transgress/statcfg/condition/list.htm" target="main">统计时间设置</a>
			</div>	
			<div style="padding-top:2px">
					<img src="${ctx}/images/icons/resource.gif" class="icon">
					<a href="${ctx}/assess/transgress/statcfg/statItem/index.htm" target="main">统计条件</a>
			</div>		
			<div style="padding-top:2px">
					<img src="${ctx}/images/icons/resource.gif" class="icon">
					<a href="${ctx}/assess/transgress/properties/edit.htm" target="main">报表表头及备注</a>
			</div>	 
		</div>
	</div>
	 <div id="menu_task_cfg">
		<div style="padding-left:5px;">
		 	<div style="padding-top:2px">
					<img src="${ctx}/images/icons/resource.gif" class="icon">
					<a href="${ctx}/assess/task/statcfg/tasktype/index.htm" target="main">考核标准类别管理</a>
			</div>		  
			<div style="padding-top:2px">
					<img src="${ctx}/images/icons/resource.gif" class="icon">
					<a href="${ctx}/assess/task/statcfg/task/index.htm" target="main">考核标准管理</a>
			</div>
			<div style="padding-top:2px">
					<img src="${ctx}/images/icons/resource.gif" class="icon">
					<a href="${ctx}/assess/task/statcfg/task/taskConstIndex.htm" target="main">任务常量管理</a>
			</div>
		</div>
	</div>
	<div id="menu_sys">
		<div style="padding-left:5px;">   		
			<div style="padding-top:2px">
				<img src="${ctx}/images/icons/user.gif" class="icon">
				<a href="${ctx}/security/user/index.htm" target="main">超级用户</a>
			</div>			
			<div style="padding-top:2px">
					<img src="${ctx}/images/icons/role.gif" class="icon">
					<a href="${ctx}/security/role/index.htm" target="main">角色管理</a>
			</div>			
			<div style="padding-top:2px">
					<img src="${ctx}/images/icons/resource.gif" class="icon">
					<a href="${ctx}/security/resource/index.htm" target="main">资源管理</a>
			</div>			
			
		</div>
	</div>
	<div id="assess_input">
		<div style="padding-left:5px;">   		
			<div style="padding-top:2px">
				<img src="${ctx}/images/icons/resource.gif" class="icon">
				<a href="${ctx}/assess/task/empduty/index.htm" target="main">考核记录管理</a>
			</div>						
		</div>
		
	</div>
	<div id="assess_query">	
		<div style="padding-left:5px;">   		
			<div style="padding-top:2px">
				<img src="${ctx}/images/icons/resource.gif" class="icon">
				<a href="${ctx}/assess/evaluate/index.htm" target="main">考核查询</a>
			</div>						
		</div>
		<div style="padding-left:5px;">   		
			<div style="padding-top:2px">
				<img src="${ctx}/images/icons/resource.gif" class="icon">
				<a href="${ctx}/assess/evaluate/front.htm" target="_blank">前台首页</a>
			</div>						
		</div>
	</div>
</div>
	