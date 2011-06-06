<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%request.setAttribute("ctx", request.getContextPath()); %>
<%@ taglib prefix="jtiger" uri="http://code.google.com/p/jtiger"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<div id="menu" style="display:none">
    <!-- 
	<div id="catalog">
		<div style="padding-left:5px;">          
			<div style="padding-top:2px">
				<img src="${ctx}/images/icons/folder_add.gif" class="icon">
				<a href="${ctx}/admin/cms/catalog/editNew.htm" target="main">栏目增加</a>
			</div>
			<div style="padding-top:2px">
				<img src="${ctx}/images/icons/folder_table.gif" class="icon">
				<a href="${ctx}/admin/cms/catalog/index.htm" target="main">栏目管理</a>
			</div>
			
			<div style="padding-top:2px">
				<img src="${ctx}/images/icons/article_add.gif" class="icon">
				<a href="${ctx}/admin/cms/article/editNew.htm" target="main">文章添加</a>
			</div>
			<div style="padding-top:2px">
				<img src="${ctx}/images/icons/articles.gif" class="icon">
				<a href="${ctx}/admin/cms/article/index.htm" target="main">文章管理</a>
			</div>
			
		</div>
	</div>
	 -->	
	
	<div id="hr">
		<div style="padding-left:5px;">          
			<div style="padding-top:2px">
				<img src="${ctx}/images/arrow_3.png" class="icon">
				<a href="${ctx}/hr/company/index.htm" target="main">企业/公司/机关信息</a>
			</div>
			<div style="padding-top:2px">
				<img src="${ctx}/images/arrow_3.png" class="icon">
				<a href="${ctx}/hr/dept/index.htm" target="main">部门管理</a>
			</div>
			
			<div style="padding-top:2px">
				<img src="${ctx}/images/arrow_3.png" class="icon">
				<a href="${ctx}/hr/employee/layout.htm" target="main">职员管理</a>
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
			<!-- 
			<div style="padding-top:2px">
				<img src="${ctx}/images/icons/template.gif" class="icon">
				<a href="${ctx}/admin/mail/view.htm" target="main">SMTP设置</a>
			</div>
			 -->
			 <!-- 	
			<div style="padding-top:2px">
				<img src="${ctx}/images/icons/template.gif" class="icon">
				<a href="${ctx}/admin/cron/list.htm" target="main">定时任务设置</a>
			</div>	
			<div style="padding-top:2px">
				<img src="${ctx}/images/icons/template.gif" class="icon">
				<a href="${ctx}/admin/cron/list.htm" target="main">定时任务设置</a>
			</div>
			 -->	
			<div style="padding-top:2px">
					<img src="${ctx}/images/icons/resource.gif" class="icon">
					<a href="${ctx}/assess/transgress/stat/index.htm" target="main">违法统计</a>
			</div>
			<!--
			<div style="padding-top:2px">
					<img src="${ctx}/images/icons/resource.gif" class="icon">
					<a href="${ctx}/assess/transgress/statcfg/condition/list.htm" target="main">统计条件</a>
			</div>
			 -->	
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
</div>
	