<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<html>
<head>

<%@include file="/common/ec.jsp" %>
<%@include file="/common/extjs.jsp" %>
<%@include file="/common/meta.jsp" %>
<%@include file="/common/validator.jsp" %>
<%@include file="/common/swfupload.jsp" %>
<script type="text/javascript"	src="${ctx}/scripts/kindeditor/kindeditor.js"></script>
<style>
input[type=checkbox] {
   margin-left:8px;
}



td.td_label{
   width:100px;
}


fieldset {
	width: 200px;	
	height:130;
	padding:10px;
	border:1px solid #efefef;
}

legend {
   padding:10px;
}

.progressWrapper {
	width: 180px;
}

#atts div {
   padding:7px;
   width:350px;   
}

#atts div a {
   margin-left:5px;
}


</style>
</head>
<body>
<div class="x-panel">
  <div class="x-panel-header">编辑文章</div>
    <div class="x-toolbar" style="padding:2px 15px 2px 0px;text-align:right;">	   
	   <a href="index.htm"> 文章管理首页</a>&nbsp;|&nbsp;
	   <a href="javascript:void(0);" onclick="history.back()">返回</a>
    </div>
<table width="100%" border="0" cellpadding="5" cellspacing="1">
	<tr>
		<td><%@ include file="/common/messages.jsp"%></td>
	</tr>
</table>
<div class="x-panel-body" style="text-align:center;padding:10px;">
   <s:form action="save.htm" method="post" id="frm" enctype="multipart/form-data">
   <s:hidden name="model.id"/>
   
   <table width="830" align="center">
   <tr>
      <td>
		  <ul class="tabs">
				<li><a href="#cont">文章内容</a></li>
				<li><a href="#attr">文章属性</a></li>
				<li><a href="#atta">附件管理</a></li>							
		  </ul>
		  <div class="panes">
		      <div class="tab">
		          <table width="100%">
		                
						<tr>
							<td class="td_label">文章标题：</td>
							<td style="text-align: left;">
							   <s:textfield name="model.title" id="title" cssClass="inputborder m_t_b tit required" cssStyle="width:450px;" />
							   <span style="color: #ff0000;">*</span>
							</td>
						</tr>
						
						<tr>
							<td class="td_label">简短标题：</td>
							<td style="text-align: left;">
							   <s:textfield name="model.shortTitle" id="st" cssClass="inputborder m_t_b short tit required" cssStyle="width:450px;" />
							   <span style="color: #ff0000;">*</span>
							</td>
						</tr>
						
						<tr>
							<td style="text-align: left;" colspan="2">
							 <br>文章内容：<br><br>
							   <s:textarea name="model.content" id="cont" cssClass="inputborder m_t_b" cssStyle="width:780px;height:200px;" />
							   
							</td>
						</tr>
						<tr><td>&nbsp;</td></tr>
						<tr>
							<td class="td_label">所属栏目：</td>
							<td style="text-align: left;">
							   
							   <div id="comboxWithTree" style="float:left;"></div>
							   <s:hidden name="model.catalog.id" cssClass="required" id="catalogId"/><span style="color: #ff0000;">*</span>
							   
							</td>
						</tr>
						
				   </table>
		      </div>
		      <div class="tab">
		          <table width="100%">
		               <tr>
							<td class="td_label">副标题：</td>
							<td style="text-align: left;">
							   <s:textfield name="model.subhead" cssClass="inputborder m_t_b" cssStyle="width:450px;" />
							   
							</td>
						</tr>
		               <tr>
							<td class="td_label">关键字：</td>
							<td style="text-align: left;">
							   <s:textfield name="model.keyWord" cssClass="inputborder m_t_b" cssStyle="width:450px;" />
							   
							</td>
						</tr>
						<tr>
							<td style="text-align: left;">
							   <br>内容摘要：
							</td>
							<td>
							   <s:textarea name="model.abstraction" cssClass="inputborder m_t_b" cssStyle="width:450px;height:50px;" />
							   
							</td>
						</tr>
		                <tr>
							<td class="td_label">标题图片：</td>
							<td style="text-align: left;">
							   <s:file name="tPic" cssClass="inputborder m_t_b" cssStyle="width:450px;" />
							   <s:if test="model.titlePic != null && model.titlePic != ''">
							      <a href="${ctx}<s:property value="model.titlePic"/>" title="点击查看原图" target="_blank">
							      <img src="${ctx}<s:property value="model.titlePic"/>" style="height:30px;width:30px;" class="pborder">
							      </a>
							   </s:if>
							</td>
						</tr>
						<tr>
							<td class="td_label">内容图片：</td>
							<td style="text-align: left;">
							   <s:file name="cPic" cssClass="inputborder m_t_b" cssStyle="width:450px;" />
							   <s:if test="model.contentPic != null && model.contentPic != ''">
							      <a href="${ctx}<s:property value="model.contentPic"/>" title="点击查看原图" target="_blank">
							      <img src="${ctx}<s:property value="model.contentPic"/>" style="height:30px;width:30px;" class="pborder">
							      </a>
							   </s:if>
							</td>
						</tr>
		          </table>
		      </div>
		      <div class="tab">
		         <table width="100%">
		         <tr>
		         <td width="250">
			     	 <div  style="display:none;">
							<input id="btnCancel" type="button" value="取消上传" onclick="swfu.cancelQueue();" disabled="disabled" class="btn-blue"/>
			         </div>
			         <fieldset> 
			             <legend><span id="spanButtonPlaceHolder" class="btn-blue"></span></legend>
			                      <div id="divStatus">请选择附件</div>
			                      <div id="fsUploadProgress"></div>
			         </fieldset>
		         </td>
		         <td id="atts" style="pading:10px" valign="top">
		            <s:hidden name="urls" id="urls"></s:hidden>
		            
		           <s:iterator value="model.attachments">
		               <div id="<s:property value="id"/>">
		                      <a href="${ctx}<s:property value="url"/>" class="oa" style="float:left;"><s:property value="name"/></a>
		                      <a href="javascript:;" style="float:right;" onclick="delAtt('<s:property value="url"/>','<s:property value="id"/>')">
		                        <img src="${ctx}/images/icons/delete.gif">
		                      </a>
		               </div>
		           </s:iterator>
		           
		         </td>
		         </tr>
		         </table>
		         
		      </div>
		  </div>
	  </td>
   </tr>
   </table>
   <div style="text-align:center;padding:5px;"><s:submit value="保存" cssClass="btn-blue"/></div>
   </s:form>
</div>
</div>
<jsp:include page="/WEB-INF/views/admin/cms/catalog/catalogSel.jsp">
  <jsp:param value="${catalog.name}" name="initValue"/>
</jsp:include>

<script type="text/javascript">
var swfu;
$(function(){
	if($.browser.msie) {
		$('ul.tabs a').css('padding', '5px 30px 7px 30px');		
	} 
	$('#frm').validate();
	$("ul.tabs").tabs("div.panes > div.tab");
	var settings = {
			flash_url : "${ctx}/swf/upload/swfupload.swf",
			upload_url: "${ctx}/admin/cms/attachment/copyFileToTemp.htm",
			post_params: {"JSESSIONID" : "<%=session.getId()%>"},
			file_size_limit : "10 MB",
			file_types : "*.*",
			file_types_description : "所有文件",
			file_upload_limit : "5",
			file_queue_limit : "5",
			custom_settings : {
				progressTarget : "fsUploadProgress",
				cancelButtonId : "btnCancel"
			},
			debug: false,

			// Button settings
			button_image_url: "${ctx}/swf/upload/images/btn_en.png",
		    button_width: "61",
			button_height: "22",
			button_placeholder_id: "spanButtonPlaceHolder",
			//button_text: '上传文件',
			//button_text_style: ".theFont { font-size: 16; }",
			//button_text_left_padding: 12,
			//button_text_top_padding: 3,
			
			// The event handler functions are defined in handlers.js
			file_queued_handler : fileQueued,
			file_queue_error_handler : fileQueueError,
			file_dialog_complete_handler : fileDialogComplete,
			upload_start_handler : uploadStart,
			upload_progress_handler : uploadProgress,
			upload_error_handler : uploadError,
			upload_success_handler : uploadSuccess,
			upload_complete_handler : uploadComplete,
			queue_complete_handler : queueComplete	// Queue plugin event
		};

		swfu = new SWFUpload(settings);
	
});

KE.show({
	id : 'cont',
	resizeMode : 2,
	allowPreviewEmoticons : false,
	allowUpload : true,
	imageUploadJson:'${ctx}/kindeditor/kindeditorImgUpload.htm'
});

var nodeIdCallback = function(nodeId) {
	 Ext.get('catalogId').dom.value = nodeId;
};

function successCallback(obj) {
	var url = obj;
	$('#urls').val($('#urls').val() + "|" + url);
	var name = url.substring(url.lastIndexOf('/') + 1);
	var id = Math.floor(Math.random() * (50) + 50);
	$('#atts').append(['<div id="', id,'"><a style="float:left;" href="${ctx}', url ,'" class="oa">', name, '</a>',
	                   '<a href="javascript:;" style="float:right;" onclick="delAtt(\'',
	                		   url,'\',\'', id ,'\')"><img src="${ctx}/images/icons/delete.gif"></a>','</div>'].join(''));
}

function delAtt(u,id) {
	if(!confirm('确定要删除吗?')) {
		return;
	}
	$.ajax({
		url : '${ctx}/admin/cms/attachment/delFile.htm',
		data: {'model.url' : u},
		type: 'post',
		success:function(data) {
			if(data == 'success') {
				var urls = $('#urls').val();
				$('#urls').val(urls.replace(u, ''));
				$('#' + id).remove();
			} else {
				alert(data);
			}
		},
		error:function(xhr) {
			alert(xhr.responseText);
		}
	});
}
</script>
</body>
</html>