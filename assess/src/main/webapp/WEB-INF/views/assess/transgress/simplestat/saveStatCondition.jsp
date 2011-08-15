<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="/common/taglibs.jsp" %>
<%@include file="/common/meta.jsp" %>
<script type="text/javascript">
//用以保存用户所选择的统计条件的描述
var statConditionName = '';
var statConditionId = '';

function showSaveStatConditionWindow(){
	SaveStatCondtionWindow.show();
	if(statConditionId == ''){
		$('#btnSave').attr('disabled','disabled');
	}
	
	if($('#selectStatItem').val() != ""){
		$('#statConditionName').val($("#selectStatItem").find('option:selected').text());
	}
	$('#statConditionDescn').val(statConditionName);
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
		id:'btnSave',
		handler:function(){
			if(SaveStatCondtionWindow.check()){
				
				var taCodes = $("#taCodes").val();
				//var secondLevelTypes = $('#secondLevelTypes').val();
				var secondLevelTypes = '';
				$('[name="secondLevelTypeIds"]').each(function(){
					secondLevelTypes += $(this).val() + ',';
				});
				var vehicleUseCodes ='';
				$("[name='vehicleUseCodes'][checked]").each(function(){  
					vehicleUseCodes+=$(this).val()+",";  
					//alert($(this).val());  
				});
				var flapperTypes = '';
				$("[name='flapperTypes'][checked]").each(function(){  
					flapperTypes+=$(this).val()+",";  
					//alert($(this).val());  
				});
				var statConditionName = $('#statConditionName').val();
				var statConditionDescn = $('#statConditionDescn').val();
				var timeCondition = $("[name='timeCondition'][checked]").val();
				var unionForce = $("[name='unionForce'][checked]").val();
				var statConditionId = $('#selectStatItem').val(); 
				$.ajax({					
					url:'${ctx}/assess/transgress/statcfg/statItem/saveStatCondition.htm',
					type:'post',
					data:{
						'statConditionName':statConditionName,
						'statConditionDescn':statConditionDescn,
						'taCodes':taCodes,
						'secondLevelTypes':secondLevelTypes,
						'vehicleUseCodes':vehicleUseCodes,
						'timeCondition':timeCondition,
						'unionForce':unionForce,
						'statConditionId':statConditionId,
						'flapperTypes':flapperTypes
						},
					success:function(result){
						if(result == 'success'){
							Ext.my().msg('提示','您已经成功保存统计条件.');
							location.reload();
						}else{
							Ext.Msg.alert('错误', "保存失败");
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
	$('#msg').html('');
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
		return false;
	}
	return true;
}
</script>

<!-- 保存查询条件 -->
<div id="saveStatCondtionWindow" class="x-hidden">
<div class="x-window-header">保存统计条件</div>
<div class="x-window-body">
<table align="center" cellspacing="6" style="width: 100%">
	<tr>
		<td style="width: 25%" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		<td style="width: 70%" >&nbsp;</td>
	</tr>
	<tr>
		<td style="width: 25%">&nbsp;</td>
		<td style="width: 70%">&nbsp;</td>
	</tr>
	<tr>
		<td  style="width: 25%" align="right">统计条件名称：</td>
		<td style="width: 70%">
			<input type="text" name="statConditionName" id="statConditionName"></input><font color="red">*</font>
			<span id="msg" style="color: red;"></span>
		</td>
	</tr>
	<tr>
		<td style="width: 25%">&nbsp;</td>
		<td style="width: 70%;">&nbsp;</td>
	</tr>
	<tr>
		<td style="width: 25%" align="right">描述：</td>
		<td  style="width: 70%">
			<textarea rows="6" cols="46" name="statConditionDescn" id = "statConditionDescn"></textarea>
		</td>
	</tr>	
</table>
<script type="text/javascript">

$(function(){	
	$('#statConditionName').blur(function(){	
		var statConditionName = $('#statConditionName').val();
		
		if($.trim(statConditionId) != ''){
			if($.trim(statConditionName)!= ''){
				$('#btnSave').attr('disabled','');	
			}
			
			return;
		}
		$.ajax({
			url:'${ctx}/assess/transgress/statcfg/statItem/checkName.htm',
			data:{'statConditionName':statConditionName},
			success:function(result){
				if(result == 'false'){
					$('#btnSave').attr('disabled','');
					$('#btnSave').removeAttr('disabled');					
					document.getElementById('btnSave').disabled = false;
					$('#msg').html('');
				}else{					
					$('#btnSave').attr('disabled','disabled');
					document.getElementById('btnSave').disabled = true;
					$('#msg').html('该统计条件名称已经存在!');
					$('#statConditionName').focus().select();
				}
			},
			failure: function() {
		          Ext.Msg.alert('错误', "检测统计条件名称失败");
		    }					
		});
	});
	$('#selectStatItem').bind('change',function(){
		$('#transgressActions table').remove();
		var seletedId = $('#selectStatItem').val();
		if(seletedId==''){
			//$('#statForm').reset();
			//$(form)[0].reset();
			$('#btnReset').click();
			statConditionName = '';
			$('#btnRemoveStatCondtion').attr('disabled','disabled');
			return;
		}
		$('#btnRemoveStatCondtion').attr('disabled','');
		$.ajax({
			url:'${ctx}/assess/transgress/statcfg/statItem/selectStatItemStatAjax.htm',
			data:{'id':seletedId},
			success:function(data){
				if(data) {	
					if(data.transgressActionCodes){
						$('#taCodes').val(replacestring(data.transgressActionCodes,"'",""));
						//$('#taCodes').val($('#taCodes').val().replace("'",""));
					}else{
						$('#taCodes').val('');
					}	
					if(data.descn){
						statConditionName = data.descn;
					}else{
						statConditionName = '';
					}				
					statConditionId = data.id;		
					//$("input[name='timeCondition']").attr('checked',data.findOrDealWith);//经典方法，但是怎么就不对呢？
					if(data.findOrDealWith ==  'WFSJ'){
						$("#WFSJ").attr('checked','checked');
					}else{						
						$("#CLSJ").attr('checked','checked');					
					}
					if(data.unionForce == 'true'){					
						$("#unionForceTrue").attr('checked','checked');
					}else if(data.unionForce == 'false'){											
						$("#unionForceFalse").attr('checked','checked');					
					}else {
						$("#unionForceOnly").attr('checked','checked');
					}
					if(data.vioSurveil == 'true' ){
						$("#vioSurveilTrue").attr('checked','checked');
					}else if(data.vioSurveil == 'false'){
						$("#vioSurveilFalse").attr('checked','checked');
					}else{
						$("#vioSurveilOnly").attr('checked','checked');
					}
					$("[name='vehicleUseCodes']").attr("checked",false);
					
					if(data.vehicleUseCodes){
						var vehicleUseCodesArr = data.vehicleUseCodes.split(",");						
						$.each(vehicleUseCodesArr,function(){
							var code = this.replace("'","").replace("'",""); 		
							//alert(code);											
							$("[name='vehicleUseCodes'][value='"+code+"']").attr("checked",true);
						});
					}
					$("[name='flapperTypes']").attr("checked",false);
					if(data.flapperTypes){
						var flapperTypes = data.flapperTypes.split(",");						
						$.each(flapperTypes,function(){
							var code = this.replace("'","").replace("'",""); 
							//alert(code);													
							$("[name='flapperTypes'][value='"+code+"']").attr("checked",true);
						});
					}
					/*if(data.secondLevelTypeIds){
						var secondLevelTypeArr = data.secondLevelTypeIds.split(",");						
						$.each(secondLevelTypeArr,function(){
							var id = this.replace("'","").replace("'",""); 		
							alert(id);	
							$.ajax({
								url:'${ctx}/assess/transgress/statcfg/statItem/getTransgressActionsBySecondType.htm',
								data:{'secondLevelTypeId':id},
								dataType:'json',
								success:function(data){
									var tableId = '"'+'SST'+id+'"';
									var rows = data.length;

									var html = [];
									html.push("<tr><td><table class='sstTable' id='SST"+id+"' width='100%'><tr><td class='transgressTypeTitle'>"+'待修改'+"&nbsp;&nbsp;&nbsp;&nbsp;<input type='checkbox'  onclick='checkAll("+tableId+",this)'>全选<input type='hidden' disabled='disabled' name='secondLevelTypeIds' value='"+id+"'/><img  class='clossBtn' src='${ctx}/images/icons/delete.gif' onclick='closeTable("+tableId+")'>关闭</td></tr>");
									$.each(data,function(idx,item){
										html.push("<tr><td align='left'><input class='ttaCode' type='checkbox' onclick='checkMe(this)' name='transgressActionCodes' value='"+item.code+"'/>"+item.code+":"+item.descns + "</td></tr>");
									});
									html.push("</table></td></tr>");
									
									$('#transgressActions').append(html.join(''));
								}
							});																	
						});
					}*/
				}
			},
			failure: function() {
		          Ext.Msg.alert('错误', "得到统计条件失败");
		    }			
		});
	});
	$('#btnRemoveStatCondtion').click(function(){
		onRemoveStatCondition();
	});
});
function onRemoveStatCondition(){
	Ext.MessageBox.confirm("提示", '是否确定删除统计条件?', function(btn) {
		if (btn == 'yes') {
			//window.location.href = URL_PREFIX + "/hr/dept/removeStatItemStatAjax.htm?id=" + id;
			
			$.ajax({
				url:'${ctx}/assess/transgress/statcfg/statItem/removeStatItemStatAjax.htm',
				data:{'id':statConditionId},
				success:function(result){
					if(result == 'success'){
						Ext.my().msg('提示','您已经成功删除统计条件.');
						location.reload();
					}else{
						Ext.Msg.alert('错误', "删除失败");
					}
				},
				failure: function() {
			          Ext.Msg.alert('错误', "删除失败");
			    }
			});
		}
	});
}
function replacestring(repstr, rgexp, replacetext){
	var str = repstr.replace(rgexp, replacetext);
	if(str.indexOf(rgexp) != -1 ){
		str = replacestring(str, rgexp, replacetext);
	}
	return str;
}
</script>
</div>
</div>
