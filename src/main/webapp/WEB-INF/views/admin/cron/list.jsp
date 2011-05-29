<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<html>
<head>
<style type="text/css">
td {padding:3px;}
</style>
<title></title>
<%@include file="/common/ec.jsp" %>
<%@include file="/common/extjs.jsp" %>
<%@include file="/common/meta.jsp" %>
<script type="text/javascript" src="${ctx}/scripts/jquery.form.js"></script>

</head>
<body>

<div class="x-panel">
  <div class="x-panel-header">管理定时任务</div>
   
  <div class="x-panel-body">
     <div style="width:600px;margin:0 auto;">
         <s:iterator value="#request.items" var="item">
             <div style="height:50px;border:1px solid #ccc;margin:10px;padding:10px 5px;">
                 <form action="save.htm" id="frm${item.marker}">
                     <span style="margin-left:10px;">任务名称:</span>
                     <input type="text" style="width:150px;" 
                            class="m_b_t" name="c.name" value="${item.name}">
                     <input type="hidden" name="c.id" value="${item.id}">      
                     <input type="hidden" name="c.marker" value="${item.marker}">        
                     <span style="margin-left:10px;">执行频率：</span>                     
                     <select name="c.cron">
                        <s:iterator value="crons" var="cron">
                            <s:if test="#cron.cron == #item.cron">
                                <option value="<s:property value="#cron.cron"/>" selected>
                                    <s:property value="#cron.name"/>
                                </option>
                            </s:if>
                            <s:else>
                                <option value="<s:property value="#cron.cron"/>">
                                     <s:property value="#cron.name"/>
                                </option>
                            </s:else>
                            
                        </s:iterator>
                     </select>
                     
                     <span style="margin-left:10px;">
                         <s:submit value="保存"/>
                     </span>
                 </form>
                 <script type="text/javascript">
                 $(function(){
                     $('#frm${item.marker}').ajaxForm({
                    	 success:function(data) {
                    		 Ext.my().msg('', '修改运行频率成功' );
                    	 },
                    	 error:function(xhr) {
                    		 Ext.my().msg('', '修改运行频率失败' );
                    	 }
                     });
                 });
                 </script>
             </div>
         </s:iterator>
     </div>    
  </div>     
</div>  

<script type="text/javascript">

</script>
</body>
</html>