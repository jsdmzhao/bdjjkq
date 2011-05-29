<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib  prefix="s" uri="/struts-tags"%>
<%-- ActionError Messages - usually set in Actions --%>
<script type="text/javascript">
  function hiddenDiv(divId){
    document.getElementById(divId).style.display="none";
  }
</script>
<style>
<!--
.err-div {
	border:1px solid red;
	margin:0 auto;
	padding:5px;
	marging:5px;
}

.msg-div { 
	border:1px solid #009900;
}
-->
</style>
<s:if test="hasActionErrors()">
    <div id="errorMessages" class="err-div">
    <table width="100%" style="border:none;">
    	<tr>
    	  <td  style="border:none;" width="%97">
   			<s:iterator value="actionErrors">
	          <img src="${ctx}/images/icons/warning.gif" class="icon" />
	          <s:property escape="false"/><br>
	        </s:iterator>
    	  </td>
    	  <td  style="border:none;" align="right" valign="top">
    	  	<a href="#" onclick="hiddenDiv('errorMessages')" title="关闭">
    	  		<img src="${ctx}/images/icons/close.gif"/>
    	  	</a>
    	  </td>
    	</tr>
    </table>    
   </div>
</s:if>
<%-- FieldError Messages - usually set by validation rules --%>
<s:if test="hasFieldErrors()">
   <div id="fieldErrorsMessages" class="err-div">  
   		<table width="100%" style="border:none;">
    	<tr>
    	  <td  style="border:none;" width="%97">
	         <s:iterator value="fieldErrors">
	           <s:iterator value="value">
	              <img src="${ctx}/images/icons/warning.gif" class="icon" />
	              <s:property escape="false"/><br>
	           </s:iterator>
	         </s:iterator>
    	  </td>
    	  <td  style="border:none;" align="right" valign="top">
    	  	<a href="#" onclick="hiddenDiv('fieldErrorsMessages')" title="关闭">
    	  		<img src="${ctx}/images/icons/close.gif"/>
    	  	</a>
    	  </td>
    	</tr>
    </table>    
   </div>
</s:if>
<s:if test="hasActionMessages()">
  <div id="actionMessages"  class="err-div msg-div">  
   		<table width="100%" style="border:none;">
    	<tr>
    	  <td  style="border:none;" width="%97">
	         <s:iterator value="actionMessages">
	           <img src="${ctx}/images/icons/accept.gif" class="icon" />
	           <s:property escape="false"/><br>	           
	         </s:iterator>
    	  </td>
    	  <td  style="border:none;" align="right" valign="top">
    	  	<a href="#" onclick="hiddenDiv('actionMessages')" title="关闭">
    	  		<img src="${ctx}/images/icons/close.gif"/>
    	  	</a>
    	  </td>
    	</tr>
    </table>    
   </div>
</s:if>

