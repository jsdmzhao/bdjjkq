<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp" %>
<%@page import="com.googlecode.jtiger.modules.security.user.UserUtil"%>
<%@page import="com.googlecode.jtiger.modules.security.user.model.User"%>

<html>
<head>
<title>后台管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@include file="/common/extjs.jsp"%>
<LINK href="${ctx}/styles/style.css" type='text/css' rel='stylesheet'>
<LINK href="${ctx}/styles/layout.css" type='text/css' rel='stylesheet'>
</head>
<body>
<script type="text/javascript">
    Ext.onReady(function(){

       Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
       
       var viewport = new Ext.Viewport({
            layout:'border',
            items:[
                new Ext.BoxComponent({ // raw
                    region:'north',
                    el: 'north',
                    height:32
                }),{
                    region:'west',
                    id:'west-panel',
                    title:'系统菜单',
                    split:true,
                    width: 150,
                    minSize: 150,
                    maxSize: 400,
                    collapsible: true,
                    margins:'0 0 0 5',
                    layout:'accordion',
                    layoutConfig:{
                        animate:true
                    },
                    items: [{                    
                        title:'组织机构',
                        html:document.getElementById('hr').innerHTML,
                        border:false,
                        iconCls:'hr'
                    },
                    
                    <sec:authorize ifAnyGranted ="ROLE_KHB,ROLE_ADMIN">
                    {                    
                        title:'常量考核查询统计',
                        html:document.getElementById('menu_constStat').innerHTML,
                        border:false,
                        iconCls:'permit'
                    },
                    </sec:authorize>
                    {                    
                        title:'常量考核统计设置',
                        html:document.getElementById('menu_constStat_cfg').innerHTML,
                        border:false,
                        iconCls:'permit'
                    },
                    {                    
                        title:'考核任务设置',
                        html:document.getElementById('menu_task_cfg').innerHTML,
                        border:false,
                        iconCls:'permit'
                    },
                    {                    
                        title:'系统配置',
                        html:document.getElementById('menu_sys').innerHTML,
                        border:false,
                        iconCls:'permit'
                    }
                    ]
                },
                
               {
                    region:'center',
                    contentEl: 'center',
                    split:true,
                    border:true,
                    margins:'0 5 0 0'
                }
             ]
        });
    });
	</script>
<%@include file="menu.jsp"%>
<div id="west"></div>
<div id="north" align="center" style="margin-top:4px;">
	<table width="100%" border="0">
	  <tr>
	  	<td align="left" width="60%">
	  	</td>
	  	
	  	<jtiger:ifLogin>
		  	<td align="right" style="color:#336699; padding-right: 20px">
				欢迎您：<sec:authentication property="name"></sec:authentication>
				&nbsp;&nbsp;|&nbsp;&nbsp;
				<a href="" target="main" style="color:#336699">
					<img src="${ctx}/images/icons/house.gif">&nbsp;后台管理首页
				</a>
				&nbsp;&nbsp;|&nbsp;&nbsp;
				<a href="${ctx}/static/j_spring_security_logout" target="_self" style="color:#336699">
					<img src="${ctx}/images/icons/lock_go.gif">&nbsp;注销
				</a>
		    </td>
	  	</jtiger:ifLogin>
	  	<jtiger:ifNotLogin>
		  	<td align="right" style="color:#336699; padding-right: 20px">
				<a href="${ctx}/login.jsp" target="_self" style="color:#336699">
					<img src="${ctx}/images/icons/user_go.gif">&nbsp;登录
				</a>
				<a href="${ctx}/restorePassword/edit.htm" target="_self" style="color:#336699">
					<img src="${ctx}/images/icons/zoom.gif">&nbsp;忘记密码
				</a>
		    </td>
	  	</jtiger:ifNotLogin>
	  </tr>
	</table>
</div>
<div id="center"><iframe src="${ctx}/main.jsp" id="main"
	name="main" style="width:100%; height:100%; border:0px;" scrolling="auto" frameborder="0"></iframe></div>
</body>
</html>
