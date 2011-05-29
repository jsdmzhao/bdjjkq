<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<html>
<head>

<%@include file="/common/ec.jsp" %>
<%@include file="/common/extjs.jsp" %>
<%@include file="/common/meta.jsp" %>
<%@include file="/common/validator.jsp" %>
<style>
input[type=checkbox] {
   margin-left:8px;
}

</style>
</head>
<body>
<div class="x-panel">
  <div class="x-panel-header">编辑栏目</div>
    <div class="x-toolbar" style="padding:2px 15px 2px 0px;text-align:right;">	   
	   <a href="index.htm"> 栏目管理首页</a>&nbsp;|&nbsp;
	   <a href="javascript:void(0);" onclick="history.back()">返回</a>
    </div>
<table width="100%" border="0" cellpadding="5" cellspacing="1">
	<tr>
		<td><%@ include file="/common/messages.jsp"%></td>
	</tr>
</table>
<div class="x-panel-body" style="text-align:center;padding:10px;">
   <s:form action="save.htm" method="post" id="frm">
   <s:hidden name="model.id"/>
   <s:hidden name="model.parent.id" id="parentId"/>
   
   <div style="margin:0 auto;width:630px;border:1px solid #ccc; padding:10px;clear:both;">
   <table width="600" align="center">
      <tr>
          <td width="150">上级栏目:</td>
          <td>
             <div id="comboxWithTree"></div>
             <jsp:include page="catalogSel.jsp">
                <jsp:param value="${parent.name}" name="initValue"/>
             </jsp:include>
             <script type="text/javascript">
             var nodeIdCallback = function(nodeId) {
            	 Ext.get('parentId').dom.value = nodeId;
             };
             </script>
	      </td>
      </tr>
      <tr>
          <td>栏目名称</td>
          <td><s:textfield name="model.name" id="name" cssStyle="width:300px;" cssClass="m_t_b required"/>
              &nbsp;<span style="color:red;">*</span>
          </td>
      </tr>
      <tr>
          <td>栏目介绍</td>
          <td><s:textarea name="model.descn" cssStyle="width:300px;height:120px;" cssClass="m_t_b"/></td>
      </tr>
      <tr id="extUrl" <s:if test="!model.isExternal">style="display:none;"</s:if>>
         <td>外部栏目地址</td>
         <td><s:textfield name="model.url" cssStyle="width:300px;" cssClass="m_t_b"/></td>         
      </tr>
      <tr>
         <td colspan="2">
            <s:checkbox name="model.isEnabled"></s:checkbox>可用栏目
            <s:checkbox name="model.isVisible"></s:checkbox>可见栏目
            <s:checkbox name="model.isListInNav"></s:checkbox>在导航条显示
            <s:checkbox name="model.isExternal" id="isExt"></s:checkbox>外部栏目
         </td>
      </tr>
      
      <tr>
         <td colspan="2" style="text-align:center;"><br>
            <s:submit value="保存" cssClass="btn-blue"></s:submit>
         </td>
      </tr>
   </table>
   </div>
   </s:form>
</div>
</div>
<script type="text/javascript">
$(function(){
	$('#frm').validate();
	$('#name').focus();
	$('#isExt').click(function(){		   
		   if($(this).attr('checked')) {
			   $('#extUrl').show();
		   } else {
			   $('#extUrl').hide();
		   }
	   });
});
   
</script>
</body>
</html>