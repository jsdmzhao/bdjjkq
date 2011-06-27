<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="/common/taglibs.jsp" %>
<html>
<head>
<title></title>
<script type="text/javascript">
var statConditionName = '';
var statConditionDescn = '';
var deptId;
function showSaveStatConditionWindow(){
	SaveStatCondtionWindow.show();
}
var SaveStatCondtionWindow = new Ext.Window({
	el:'saveStatCondtionWindow',
	width:500,
	height:300,
	layout:'fit',
	closeAction:'hide',
	buttonAlign:'center',
	modal:'true',
	buttons:[{
		text:'保存',
		handler:function(){
			if(SaveStatCondtionWindow.check()){
				var taCodes = $("#taCodes").val();
				var secondLevelTypes = $('#secondLevelTypes').val();
				var vehicleUseCodes ='';
				$("[name='vehicleUseCodes'][checked]").each(function(){  
					vehicleUseCodes+=$(this).val()+",";  
					//alert($(this).val());  
				});
				$.ajax({
					url:'${ctx}/assess/transgress/simplestat/checkName.htm',
					data:{'statConditionName':statConditionName},
					success:function(){
						if(result.result == 'false'){
							$.ajax({
								url:'${ctx}/assess/transgress/simplestat/saveStatCondition',
								data:{'statConditionName':statConditionName,'statConditionDescn':statConditionDescn,'taCodes':taCodes,'secondLevelTypes':secondLevelTypes,'vehicleUseCodes':vehicleUseCodes},
								success:function(){
									Ext.my().msg('','您已经成功保存查询条件.');
								}
							});
						}else{
							Ext.MessageBox.show({
								title :'提示',
								minWidth:220,
								msg : '该统计条件名称已经存在!',
								buttons:Ext.MessageBox.OK,
								icon:Ext.MessageBox.INFO
							});
						}
					},
					failure: function() {
				          Ext.Msg.alert('错误', "保存失败");
				    }					
				});
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
	$('#statConditionName').attr("value",'');
	$('#statConditionDescn').attr("value",'');
}
SaveStatCondtionWindow.check = function(){
	if($('#statConditionName').val() == ''){
		Ext.MessageBox.show({
			title:'提示',
			minWidth:220,
			msg:'请输入统计条件名称!',
			buttons:Ext.MessageBox.OK,
			icon:Ext.MessageBox.INFO
		});
	}
}
</script>
</head>
<body>
<!-- 保存查询条件 -->
<div id="saveStatCondtionWindow" class="x-hidden">
<div class="x-window-header">保存统计条件</div>
<div class="x-window-body">
<table align="center" cellspacing="6">
	<tr><td></td><td>&nbsp;</td></tr>
	<tr><td></td><td>&nbsp;</td></tr>
	<tr>
		<td  align="right">统计条件名称：</td>
		<td>
			<input type="text" name="statConditionName" id="statConditionName"></input><font color="red">*</font>
		</td>
	</tr>
	<tr><td></td><td>&nbsp;</td></tr>
	<tr>
		<td align="right">描述：</td>
		<td>
			<textarea rows="3" cols="60" name="statConditionDescn" id = "statConditionDescn"></textarea>
		</td>
	</tr>	
</table>
</div>
</div>
</body>
</html>