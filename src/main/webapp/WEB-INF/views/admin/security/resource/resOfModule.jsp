<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<s:iterator value="#request.resources">
   <div title="${url}" class="res">
      <table cellpadding="0" cellspacing="0" width="100%" id="line_${id}">
         <tr>
            <td width="70%" align="left" style="padding-left:1px;">
            <s:if test="changed">
              <input type="checkbox" value="${id}" class="res_chk" checked>
            </s:if>
            <s:else>
              <input type="checkbox" value="${id}" class="res_chk">
            </s:else>
            <s:property value="name"/>
            </td>
            <td align="right" style="padding-right:6px;">
                <a href="javascript:void(0);" 
                onclick="editRes('${id}','${name}','${url}', '${parent.id}','${parent.name}');">改</a>&nbsp;|&nbsp;                
                <a href="javascript:void(0);" onclick="delRes('${id}','${name}');">删</a>
            </td>
         </tr>
      </table>      
   </div>
</s:iterator>
<s:if test="#request.isAll">
<script>$('#module_chk_${parentId}').attr('checked', true);</script>
</s:if>
<s:else>
<script>$('#module_chk_${parentId}').attr('checked', false);</script>
</s:else>