<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<html>
<head>

<title></title>
<%@include file="/common/ec.jsp" %>
<%@include file="/common/extjs.jsp" %>
<%@include file="/common/meta.jsp" %>
<%@include file="/common/validator.jsp" %>
<style type="text/css">
.module {
   width:19%;
   margin:10px 0px 0px 10px;
   height:220px;
   border:1px solid #ccc;
   float:left;
}

.module h2 {
  width:100%;
  height:24px;
  line-height:24px;
  background:url(${ctx}/images/titbg.png) repeat-x;
  color:#333;
  padding:1px 6px 1px 1px;
  border-bottom:1px solid #ccc;
}
.module div {
  padding:5px 0px 0px 0px;
}
.module div .res {
  padding:1px 0px 2px 0px;
}
.option {
  padding:3px;
}
</style>
</head>
<body>
<div class="x-panel">
  <div class="x-panel-header">资源管理</div>
    <div class="x-toolbar">
      <table width="100%" cellpadding="0" cellspacing="0">
         <tr>
            <td style="padding-left:10px;">
            给角色分配资源：<s:select list="#request.roles" listKey="id" listValue="descn" headerKey="" headerValue="请选择..." name="role" id="roles"
            onchange="selRole()"></s:select>
            <input type="button" value="保存" onclick="assignRes();">
            </td>
            <td align="right" style="padding:5px;">
               <a href="javascript:void(0);" onclick="createModule()">
               <img src="${ctx}/images/icons/add.gif" style="margin:0px 3px 0px 0px;">新建模块</a>              
            </td>
         </tr>
      </table>
    </div>  
    <s:iterator value="#request.items">
       <div class="module" id="${id}">
          <h2 title="${url}">
             <span style="float:left">
             <input type="checkbox" value="${id}" id="module_chk_${id}" class="module_chk" onclick="selModuleRes(this)">
             <s:property value="name"/> &nbsp;&nbsp;&nbsp;&nbsp;
             </span>
             <span style="font-weight:normal;float:right">
                 <a href="javascript:void(0);" title="修改模块属性" onclick="editModule('${id}','${name}','${url}');">改</a>&nbsp;|&nbsp;
                 <a href="javascript:void(0);" title="删除模块和下属的资源" onclick="delModule('${id}','${name}');">删</a>&nbsp;|&nbsp;
                 <a href="javascript:void(0);" title="添加资源" onclick="addRes('${id}','${name}','${url}');">加</a>
             </span>
          </h2>
          <div id="c_${id}"></div>
       </div>
    </s:iterator> 
 </div>
  <div id="module" class="x-hidden">
    <div class="x-window-header">编辑模块</div>
      <div class="x-window-body" >
        <div style="padding:15px;">
            <s:form action="resource/saveModule.htm" method="post" id="mFrm">
            <input type="hidden" name="model.id" id="moduleId">
            <table width="100%" cellpadding="0" cellspacing="0">
               <tr>
                  <td>模块名称:</td>
                  <td><input name="model.name" id="moduleName" style="width:300px;border:1px solid #cecece;" class="required">
                      <span style="color:red;">*</span>
                  </td>
               </tr>
               <tr>
                  <td>模块URL:</td>
                  <td><input name="model.url" id="moduleUrl" style="width:300px;border:1px solid #cecece;" class="required">
                      <span style="color:red;">*</span>
                  </td>
               </tr>
            </table>
            </s:form>
        </div>
      </div>
   </div>
   <div id="res" class="x-hidden">
    <div class="x-window-header">编辑资源<span id="xm" style="margin-left:10px;color:#999;"></span></div>
      <div class="x-window-body" >
        <div style="padding:15px;">
            <s:form action="resource/saveRes.htm" method="post" id="rFrm">
            <input type="hidden" name="model.id" id="resId">
            <input type="hidden" name="model.parent.id" id="parentId">
            <table width="100%" cellpadding="0" cellspacing="0">
               <tr>
                  <td>资源名称:</td>
                  <td><input name="model.name" id="resName" style="width:300px;border:1px solid #cecece;" class="required">
                      <span style="color:red;">*</span>
                  </td>
               </tr>
               <tr>
                  <td>资源URL:</td>
                  <td><input name="model.url" id="resUrl" style="width:300px;border:1px solid #cecece;" class="required">
                      <span style="color:red;">*</span>
                  </td>
               </tr>
            </table>
            </s:form>
        </div>
      </div>
   </div>
<script type="text/javascript">
$(function(){
	$('#mFrm').validate();
	$('#rFrm').validate();
	
	//加载各个模块
	$(".module").each(function(idx, item){
		var id = $(item).attr('id');
		loadResOfModule(id);
	});
});
//加载一个模块的资源
function loadResOfModule(parentId) {
	$.ajax({
		url:'resOfModule.htm?v=' + Math.random(5),
		data:{'model.id':parentId, 'role.id' : $('#roles').val()},
		beforeSend:function() {
			$('#c_' + parentId).html('<div style="padding:5px;">数据加载中...</div>');
		},
		success:function(data) {
			$('#c_' + parentId).html(data);
		},
		error : function(xhr) {
			$('#c_' + parentId).html('<div style="padding:5px;">数据加载错误：' + xhr.status + "</div>");
		}
	});
}

//编辑模块的Window
var mWin = new Ext.Window({
	  el : 'module',
	  layout : 'fit',
	  width : 500,
	  height : 220,
	  closeAction : 'hide',
	  plain : false,
	  modal : true,
	  bodyStyle : 'padding:5px;',
	  buttonAlign : 'center',
	  buttons : [{
	    text:'关闭',
	    handler:function(){
	        mWin.hide();
	    }
	  },
	  {
		    text:'确定',
		    handler:function(){
		    	$('#mFrm').submit();
		    	mWin.hide();
		    }
		  }]
	});
//编辑资源的Window
var rWin = new Ext.Window({
	  el : 'res',
	  layout : 'fit',
	  width : 500,
	  height : 220,
	  closeAction : 'hide',
	  plain : false,
	  modal : true,
	  bodyStyle : 'padding:5px;',
	  buttonAlign : 'center',
	  buttons : [{
	    text:'关闭',
	    handler:function(){
	        rWin.hide();
	    }
	  },
	  {
		    text:'确定',
		    handler:function(){
		    	$.ajax({
		    		url:'saveRes.htm',
		    		data: {'model.id' : $('#resId').val(),
		    			'model.parent.id' : $('#parentId').val(),
		    			'model.name' : $('#resName').val(),
		    			'model.url' : $('#resUrl').val()},
		    		success:function(moduleId) {
		    			if(moduleId) {
		    				loadResOfModule(moduleId);
		    			}
		    		}, error:function(xhr) {
		    			alert("保存资源错误:" + xhr.status);
		    		}
		    	});
		    	rWin.hide();
		    }
		  }]
	});
//创建模块
function createModule() {
	$('#moduleId').val('');
	$('#moduleName').val('');
	$('#moduleUrl').val('');
	mWin.show();
}
//编辑模块
function editModule(id, name, url) {
	$('#moduleId').val(id);
	$('#moduleName').val(name);
	$('#moduleUrl').val(url);
	mWin.show();
}
//删除模块
function delModule(id,name) {
	if(!confirm('确定要删除模块"' + name + '"吗?')) {
		return;
	}
	$.ajax({
		url:'del.htm',
		data:{'model.id' : id},
	    success:function(data) {
	    	if(data == 'success') {
	    		$('#' + id).hide();
	    	}
	    },
	    error:function(xhr) {
	    	alert('删除模块错误：' + xhr.status);
	    }
	});
}
//删除资源
function delRes(id,name) {
	if(!confirm('确定要删除资源"' + name + '"吗?')) {
		return;
	}
	$.ajax({
		url:'del.htm',
		data:{'model.id' : id},
	    success:function(data) {
	    	if(data == 'success') {
	    		$('#line_' + id).hide();
	    	}
	    },
	    error:function(xhr) {
	    	alert('删除资源错误：' + xhr.status);
	    }
	});
}
//添加资源
function addRes(id, name, url) { //参数都是模块的数据
	$('#resId').val('');
	$('#resName').val('');
	$('#parentId').val(id);
	$('#resUrl').val(url);
	$('#xm').html('[' + name + ']');
	rWin.show();
}
//编辑资源
function editRes(id, name, url, parentId, parentName) {
	$('#resId').val(id);
	$('#resName').val(name);
	$('#parentId').val(parentId);
	$('#resUrl').val(url);
	$('#xm').html('[' + parentName + ']');
	rWin.show();
}
//给角色分配资源
function assignRes() {
	if($('#roles').val() == '') {
		alert('请选择角色！');
		return;
	}
	var roleId = $('#roles').val();
	var resStr = [];
	//遍历所有资源
	$('.res_chk').each(function(idx, item){
		if($(item).attr('checked')) {
		  resStr.push($(item).val());
		}
	});
	if(resStr.length == 0) {
		alert('请选择资源！');
		return;
	}
	$.ajax({
		url : 'assignRes.htm',
		data : {'role.id' : roleId, 'resIds' : resStr.join(",")},
		success : function(data) {
			if(data == "success") {
				Ext.my().msg('', '您已经成功的为角色分配了资源.');
			} else {
				Ext.my().msg('', data);
			}
		}, error:function(xhr) {
			Ext.my().msg('', '出错了:' + xhr.status);
		}
	});
}
/**
 * 选择一个role，重新加载资源（刷新资源选中）
 */
function selRole() {
    var roleId = $('#roles').val();
    if(roleId) {
    	$(".module").each(function(idx, item){
    		var id = $(item).attr('id');
    		loadResOfModule(id);
    	});
    }
}
//选中模块checkbox的时候，选择下属的资源
function selModuleRes(obj) {
	var module = $(obj);
	var c = $('#c_' + module.val() + ' input');
	c.each(function(idx, item) {
		$(item).attr('checked', module.attr('checked'));
	})
}

</script>
</body>
</html>