<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="/common/taglibs.jsp" %>

<script type="text/javascript">
//用以保存用户所选择的统计条件的描述
var statConditionName = '';
var statConditionId = '';

function showSaveStatConditionWindow(){
	SaveStatCondtionWindow.show();

}
var SaveStatCondtionWindow = new Ext.Window({
	el:'saveStatCondtionWindow',
	width:800,
	height:600,
	layout:'fit',
	closeAction:'hide',
	buttonAlign:'center',
	modal:'true',
	buttons:[{
		text:'确定',
		id:'btnOK',
		handler:function(){
			if(SaveStatCondtionWindow.check()){
				$('#emps').html('');
				
				$("[name='empId'][checked]").each(function(){
					$('#empIds').val($('#empIds').val()+','+$(this).val());								
					$('#emps').html($('#emps').html()+','+$(this).next().text());													
				});
				
				if($('#empIds').val().substr(0,1)==','){
					$('#empIds').val($('#empIds').val().substr(1,$('#empIds').val().length-1));
				}
			
				if($('#emps').html().substr(0,1)==','){
					$('#emps').html($('#emps').html().substr(1,$('#emps').html().length-1));
				}
	
				SaveStatCondtionWindow.hide();
				SaveStatCondtionWindow.clear();
			}
		}
	},{
		text:'取消',
		handler:function(){
			SaveStatCondtionWindow.hide();
			SaveStatCondtionWindow.clear();
		}
	}]
});
SaveStatCondtionWindow.clear = function(){
	$("[name='empId']").attr("checked",false);
}
SaveStatCondtionWindow.check = function(){
	return true;
}

</script>
<style type="text/css">
.employeesTd{
	 border: 1px solid #000;
	 border-width: 0 1px 1px 0;
}
.employeesClass{
	 border: 1px solid #000;
	 border-width: 1px 0 0 1px;
}
.title{
	height: 30px;
	font-size: 30px;
	text-align:  center;
}
.left{
	width: 20%;
	float: left;
}
.right{
	width: 80%;
	float: left;
}
</style>
<!-- 保存查询条件 -->
<div id="saveStatCondtionWindow" class="x-hidden" >
<div class="x-window-header">保存统计条件</div>
<div class="x-window-body" style=" overflow: auto;">
<div style="width: 95%;" >
<div style="width: 100%">
	<div class="title left" >部门</div><div class="title right">警员</div>
</div>
<div style="clear: both;"></div>
<c:forEach items="${depts}" var="dept" varStatus="status">
<hr size="1"/>
<div style="line-height: 100%;">
	<div style="float: left; width:20%; line-height:100%； height:auto;   text-align: center;">${dept.name}</div>
	<div style="float: left; width:80%;">
		<c:forEach items="${dept.employees}"  var="emp" varStatus="empStatus">
			<div style="float: left; width: 70px;">
				<input type="checkbox" name="empId" id="empId" value="${emp.id }"/><span>${emp.name }</span>					
			</div>										
		</c:forEach>
	</div>
</div>
						
</c:forEach>
	<%--
	<table align="center" class="employeesClass" cellspacing="1"  style="width: 750px;">
	<tr>
		<td class="employeesTd" style="width: 20%;height: 30px;" align="center" >部门</td>
		<td class="employeesTd" style="width: 80%;height: 30px;" align="center" >警员</td>
	</tr>
	<c:forEach items="${depts}" var="dept" varStatus="status">
		<tr>
			<td class="employeesTd" style="width: 10%" align="center" >${dept.name}</td>
			<td class="employeesTd" >
				<table style="border: 0">
					<c:forEach items="${dept.employees}"  var="emp" varStatus="empStatus">
						<c:if test="${empStatus.index % 10 == 0}">
							<tr style="border: 1px #eee;">
						</c:if>
						<td>
							<input type="checkbox" value="${emp.id }">${emp.name }</input>							
						</td>
						<c:if test="${empStatus.index % 10 == 9}">
							</tr>
						</c:if>						
					</c:forEach>
				</table>
			</td>
		</tr>
	</c:forEach>
</table>
	--%>

</div>
</div>
</div>
<script type="text/javascript">
$(function(){		

});
</script>