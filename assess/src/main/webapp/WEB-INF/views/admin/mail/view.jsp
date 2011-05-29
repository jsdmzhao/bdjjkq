<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>SMTP配置</title>
<%@include file="/common/extjs.jsp" %>
<%@include file="/common/meta.jsp" %>
<script type="text/javascript">
$(function() {
  $('#check').click(function() {
      $.ajax({
          url:'check.htm',
          complete: function(xhr) {
              $('#loading').fadeOut();
              alert(xhr.responseText);},
          beforeSend: function() {
             $('#loading').show();
          }
      });
  });
});
</script>
</head>

<body>
<div class="x-panel">
    	<div class="x-panel-header">SMTP配置</div>

    	<div class="x-panel-body">
	    	<div>
				<%@ include file="/common/messages.jsp"%>
			</div>
			
			<s:form action="edit" theme="simple" validate="true" method="POST">

		   		<fieldset style="margin:30px;">
			    <legend>SMTP配置信息</legend>
				<table width="100%">
                <tr>
					<td class="simple">主机地址：</td>
					<td class="simple">
						<s:label name="model.host" id="model.host" theme="simple" /></td>
				</tr>
				<tr>
					<td class="simple">端口号：</td>
					<td class="simple">
						<s:label name="model.port" id="model.port" theme="simple" /></td>
				</tr>
				<tr>
					<td class="simple">用户名：</td>
					<td class="simple">
						<s:label name="model.username" id="model.username" theme="simple"  /></td>
				</tr>
				<tr>
					<td class="simple">口令：</td>
					<td class="simple">
					  <s:if test="model.password != null">
						<s:label name="model.password" value="******" id="model.password" theme="simple" />
					  </s:if>
					  <s:else> </s:else>
					</td>
				</tr>
				<tr>
				<td class="simple">SMTP验证：</td>
				<td class="simple">
				<s:if test="model.auth == 'true'">
				是
				</s:if>
				<s:else>
				否
				</s:else>
				</td>
				</tr>
                </table>                
  				<table width="100%" style="margin-bottom:10px;">
                       <tr>
                        <td style="text-align:center;">
                        <span id="loading" style="display:none"><img src="${ctx}/images/loading.gif"></span>
                        <input type="button" value="测试" class="button" id="check"/>
                            <s:submit value="编辑" cssClass="button"></s:submit>
                            
                        </tr>
                </table>
                </fieldset>
                </s:form>
                
    	</div>
  </div>


</body>
</html> 