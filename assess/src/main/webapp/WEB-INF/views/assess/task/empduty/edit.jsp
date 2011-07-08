<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="/common/taglibs.jsp" %>
<html>
<head>
<%@include file="/common/meta.jsp" %>
<title></title>
<%@include file="/common/validator.jsp" %>
<%@include file="/common/ec.jsp" %>
<%@include file="/common/extjs.jsp" %>
<script type="text/javascript" src="${ctx}/scripts/jquery.form.js"></script>
<style type="text/css">
.transgressTypeTitle{
	text-align:left;
	font-size:18px;
	font-weight:bold;
	color:#0066CC;
}
.transgressActionCheckBox{
	text-align: left;
}
.sstTable td{
    border-collapse: collapse;
    margin-left:-9px;
    border:solid 1px #97B7E7;
}
.clossBtn{
	margin-left: 20px;
	
}
</style>
</head>
<body>
<div class="x-panel">
    <div class="x-panel-header">考核记录管理</div>
    <div class="x-toolbar" style="text-align: right;">
		<a href="index.htm">考核记录首页</a>
    </div>
	<s:form id="saveFrm" action="save" method="post">
	<s:hidden id="model.id" name="model.id"/>
	<table width="800px" align="center">
		<tr>
			<td align="center">
			<fieldset> 
              <legend>考核记录信息</legend>
                <table cellpadding="3" cellspacing="2" width="100%">
                 <tr>
                     <td align="right" width="15%">考核标准类型：</td>
                     <td align="left" width="80%"  >
                     	<s:select list="taskTypes" id="taskTypes" headerKey="" headerValue="请选择" name="model.task.taskType.id" listKey="id" listValue="name"></s:select>
                     </td>
                  </tr>
                  <tr>
                     <td align="right" width="15%">选择考核标准：</td>
                     <td align="left" width="80%"  >
                     	<s:select list="tasks"  id="tasks" headerKey="" headerValue="请选择" name="model.task.id" listKey="id" listValue="name"></s:select>                     	
                     </td>
                  </tr>
                   <tr>
                     <td align="right" width="15%">考核标准：</td>
                     <td align="left" width="80%" id="tdTask" >
                     	<s:hidden value="model.task.id"></s:hidden>
                     	<s:property value="model.task.name"/>                    	
                     </td>
                  </tr>
                  <tr>
                  	<td align="right" width="15%">评分标准：</td>
                  	<td>
                  		<div id="taskName"></div>
                  		<table id="taskDetail" width="100%">
                  			<c:if test="${model.taskDetail ne null}">
                  			<tr><td>${model.taskDetail.name} ${model.taskDetail.addOrDecrease eq '0' ? '减分':'加分'} ${model.taskDetail.point } ${ model.taskDetail.decreaseLeader eq '0'?'':'包岗领导同扣'}</td></tr>
                  			</c:if>
                  			
                  		</table>
                  	</td>
                  </tr>
                  <tr>
                  	<td align="right" width="15%">选择警员：</td>
                  	<td align="left" width="80%"  >
                  		<input type="hidden" id="empIds" name="empIds"></input>
                  		<div id="emps" style="width: 352px; color:fuchsia; float: left;">请点击"选择警员"按钮，添加警员</div>
              
                  		<input type="button" value=" 选择警员 " id="btnSaveStatCondtion" onclick="javascript:showSaveStatConditionWindow()" class="button" >
                  		
                  	</td>
                  </tr>
                  <tr>
                  	<td align="right" width="15%">备注：</td>
                  	<td align="left" width="80%"  >
                  		<textarea rows="5" cols="60" name="remark"></textarea>
                  	</td>
                  </tr>
                </table> 
              </fieldset>
              <table width="100%" style="margin-bottom:10px;">
				<tr>
					<td style="text-align:center;">
					 <s:submit value="保存" cssClass="button"/> 						
						<!--<input type="button" value="保存" class="button" onclick="saveIt()"></input>		
						--><s:reset value="重置" cssClass="button"/>
                    </td>
              	</tr>
              </table>
		</tr>
	</table>
	</s:form>
</div>
<script type="text/javascript">
$(function(){
	$('#taskTypes').change(function(){
		var taskTypeId = $('#taskTypes').val();
		if(taskTypeId != ''){
			$('#tasks').html('');
			$.ajax({
				url:'${ctx}/assess/task/empduty/getTasksByType.htm',
				data:{'taskTypeId':taskTypeId},
				success:function(data){
					//alert(1);
					var html = [];
					html.push("<option value = ''>请选择</option>");
					$.each(data,function(idx,item){					
						html.push("<option value='" + item.id + "'>" + item.name + "</option>");						
					});
					$('#tasks').html(html.join(''));
				},
				failure: function() {
			          Ext.Msg.alert('错误', "得到考核标准失败");
			    }
			});
		}
	});
	$('#tasks').change(function(){
		var taskId = $('#tasks').val();
		var html = [];
		if(taskId != ''){
			$('#taskDetail').html('');
			$.ajax({
				url:'${ctx}/assess/task/empduty/getTaskById.htm',
				data:{'taskId':taskId},
				success:function(data){
					$('#taskName').val(data.name);
					$.each(data,function(idx,item){
						$('#tdTask').html(data.name);
					});
				},
				failure:function(){
					Ext.Msg.alert('错误','得到考核标准明细失败');
				}
			});
			$.ajax({
				url:'${ctx}/assess/task/empduty/getTaskDetails.htm',
				data:{'taskId':taskId},
				success:function(data){					
					$.each(data,function(idx,item){
						var addOrDecrease = item.addOrDecrease=='0'?'减分':'加分';
						var decreaseLeader = item.decreaseLeader=='0'?'同扣':'不同扣';
						
						html.push("<tr><td>明细项目：</td><td><input type='radio' name='model.taskDetail.id' value="+item.id+" id='"+item.id+"'><input type='hidden' value='"+item.id+"'/>"+item.name+"</td><td>加/减分：</td><td>"+addOrDecrease+"</td><td>分值：</td><td>"+item.point+"</td><td>包岗领导同扣：</td><td>"+decreaseLeader+"</td></tr>");
					});
					$('#taskDetail').append(html.join(''));
				},
				failure:function(){
					Ext.Msg.alert('错误','得到考核标准明细失败');
				}
			});
		}
	});
});
</script>
<script type="text/javascript">
$("#saveFrm").validate({
	rules: {
		'model.task.taskType.id':  {
			required : true
		},
		'model.task.id':  {
			required : true
		},
		'model.integral':  {
			required : true,
			number : true
		},'model.taskDetail.id':{
			required : true
		},'empIds':{
			required : true
		}
	},
	messages: {
		'model.task.taskType.id': {
			required: "请选择考核标准类型"
		},
		'model.task.id':  {
			required: "选择考核标准"
		},'model.taskDetail.id':{
			required : "选择评分标准"
		},'empIds':{
			required : "选择警员"
		}
	}
});

/*
$(function(){	
    $('#saveFrm').ajaxForm({    	
   	 success:function(data) {   		 
   		
   		Ext.my().msg('', '保存统计条件信息成功' );
   		pause(2000); 
   		location.href = '${ctx}/assess/transgress/statcfg/statItem/index.htm';
   		
   	 },
   	 error:function(xhr) {
   		 Ext.my().msg('', '保存统计条件信息失败' );
   	 }
    });
});*/
function saveIt(){
	
}

</script>
<jsp:include page="employees.jsp"></jsp:include>	
</body>
</html>