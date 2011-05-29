<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="/common/taglibs.jsp" %>
<html>
<head>
<%@include file="/common/meta.jsp" %>
<title></title>
<%@include file="/common/validator.jsp" %>
</head>
<body>
<div class="x-panel">
    <div class="x-panel-header">统计项信息管理</div>
    <div class="x-toolbar" style="text-align: right;">
		<a href="index.htm">管理首页</a>
    </div>
	<s:form id="save" action="save" method="post">
	<s:hidden id="model.id" name="model.id"/>
	<table width="600px" align="center">
		<tr>
			<td align="center">
			<fieldset> 
              <legend>编辑统计项信息</legend>
                <table cellpadding="3" cellspacing="2">
                  <tr>
                     <td align="right" >名称：</td>
                     <td align="left" ><s:text name="model.name"></s:text></td>
                  </tr>
                                 
                  <TR>
                  	 <td align="right">类别：</td>
                     <td align="left">
                     	<s:select list="firstLevelTypes" id="firstLevelType" headerKey ="" headerValue = "请选择" 
                     	name="firstLevelType" listKey="id" listValue="name" cssClass="m_t_b" 
                     	cssStyle="width:252px;padding-left:2px;"  
                     	onchange="initSubTypes($(this).val())"></s:select>
                     </td>
                  </TR>
                  <TR>
                  	 <td align="right">分类：</td>
                     <td align="left">
                     	<select name="subTypeId"  class="m_t_b " id="subType" style="width:252px;padding-left:2px;" >
						</select>
                     	<span style="margin-left:5px;display:none;" id="l_typeB"><img src="${ctx}/images/loading.gif"></span>
                     </td>
                  </TR>                  
                </table> 
              </fieldset>
              <table width="100%" style="margin-bottom:10px;">
				<tr>
					<td style="text-align:center;">
						<s:submit value="保存" cssClass="button"/> 
						<s:reset value="重置" cssClass="button"/>
                    </td>
              	</tr>
              </table>
			</td>
		</tr>
	</table>
	</s:form>
</div>

<script type="text/javascript">
$("#save").validate({
	rules: {
		'model.type.id':  {
			required : true
		},
		'model.state':  {
			required : true
		},
		'model.integral':  {
			required : true,
			number : true
		}
	},
	messages: {
		'model.type.id': {
			required: "请选择类型"
		},
		'model.state':  {
			required: "选择审核结果"
		}
	}
});
/**
 * 根据选中的topType值初始化subTypes选择框
 */
function initSubTypes(topType,curVal){
	$('#subType').html('');
	if(topType == ""){
		$('#subType').html('');
		return;
	}	
	$.ajax({
		url:"${ctx}/admin/artinfo/artinfotype/subTypeByParent.htm",
		data:{'model.id':topType},
		dataType:'json',
		success:function(data){
			var html = [];
			html.push("<option value=''>请选择</option>");
			$.each(data,function(idx,item){				
				if(!curVal) {
	    			  html.push("<option value='" + item.id + "'>" + item.name + "</option>");
	    			} else {
	    			  if(curVal == item.id) {
	    				  html.push("<option selected value='" + item.id + "'>" + item.name + "</option>"); 
	    				}
	    			}
		 		});
	 		$('#subType').html(html.join(''));	 		
	 		$('#l_typeB').hide();	 		
			},
		beforeSend:function(){
			$('#_typeB').show();
		},
		error:function(){
			$('#l_typeB').hide();
		}
	});
}
</script>
<s:if test="model.id != null && model.type.id != null && model.type.id != '' && model.type.parent != null ">
<script>
$(function(){
	initSubTypes("${model.type.parent.id}","${model.type.id}");
	var topType = document.getElementById('topType');
	for(var i = 0;i<topType.options.length;i++){
		if(topType.options[i].value == '${model.type.parent.id}'){
			topType.options[i].selected = true;
		}
	}
});
</script>	
</s:if>
</body>
</html>