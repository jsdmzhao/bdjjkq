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
<script type="text/javascript" src="${ctx}/scripts/jqueryui/jquery-ui.js"></script>
<style type="text/css">
.ecSide .headZone {
    background-color: #fff;
    border-color: #333;
}

/*表格斜线*/ 
.out{ 
   border-top:50px #e3e3e3 solid;/*上边框宽度等于表格第一行行高*/ 
   width:0px;/*让容器宽度为0*/ 
   height:0px;/*让容器高度为0*/ 
   border-left:100px #fff solid;/*左边框宽度等于表格第一行第一格宽度*/     
   position:relative;/*让里面的两个子容器绝对定位*/ 
} 
b{font-style:normal;display:block;position:absolute;top:-40px;left:-40px;width:35px;} 
em{font-style:normal;display:block;position:absolute;top:-25px;left:-90px;width:55x;} 

</style>
</head>
<body>
<s:form id="removeForm" action="role/remove" method="POST"></s:form>
<div class="x-panel">
  <div class="x-panel-header">管理统计项</div>
    <div class="x-toolbar" style="height:26px;">
      <table width="99%" >
  	  <tr style="margin-top:  7px;"> 		
  		<s:form name="queryForm" id="queryForm" namespace="/assess/transgress/statcfg/statItem" action="index" theme="simple">	    	
			<td width="10">
				<table style="padding-top: 3px;"> 
					<tr> 
						<td>
							名称:<s:textfield name="model.name"></s:textfield>
						</td> 
						<td>
							类别:
							<s:select list="firstLevelTypes" id="topType" headerKey ="" headerValue = "请选择" 
                     			name="firstLevelTypeId" listKey="id" listValue="descns" cssClass="m_t_b" 
                     			cssStyle="width:252px;padding-left:2px;"  
                     			onchange="initSubTypes($(this).val())"></s:select>
						</td>
						<td>
							分类:<select name="secondLevelTypes"  class="m_t_b " id="secondLevelTypes" style="width:252px;padding-left:2px;" >
						</select><span style="margin-left:5px;display:none;" id="l_typeB"><img src="${ctx}/images/loading.gif"></span>
						</td>
					</tr> 
			</table> 
		</td>
		<td>
			&nbsp;&nbsp;&nbsp;&nbsp;		
			<input type="submit" value="查询" class="button" style="margin-top: 3px;">			   			
		</td>	 
		</s:form>  		
  	  </tr>
  	</table>
   </div>   

   <div class="x-panel-body">
     <div style="margin-left:-3px;" align="center">
	<ec:table items="items" var="item" retrieveRowsCallback="process" sortRowsCallback="process" 
	action="stat.htm"
	useAjax="true" doPreload="false"
	maxRowsExported="1000" 
	pageSizeList="20,50,100" 
	editable="false" 
	sortable="false"	
	rowsDisplayed="20"	
	generateScript="true"	
	resizeColWidth="true"	
	classic="false"	
	width="100%" 	
	height="500px"	
	minHeight="300" 
	showHeader="true"	
	toolbarContent="navigation|pagejump|pagesize|export|refresh|extend|status">
    
	<ec:row>
	   	<ec:column width="30" property="_s" title="No." value="${GLOBALROWCOUNT}" sortable="false" style="text-align:center"/>	
		<ec:column width="350" property="name" title="项目名称" tipTitle="${item.name}" ellipsis="true" sortable="false"/>
 	
	   	
	</ec:row>
	</ec:table>
  </div>
  </div>
</div>
<script type="text/javascript">
/**
 * 根据选中的topType值初始化subTypes选择框
 */
function initSubTypes(firstLevelTypeId,curVal){
	$('secondLevelTypes').html('');
	if(firstLevelTypeId == ''){
		return;
	}
	$.ajax({
		url:'${ctx}/assess/transgress/statcfg/statItem/getSecondLevelTypesByFirstLevel.htm',
		data:{'firstLevelTypeId':firstLevelTypeId},
		dataType:'json',
		success:function(data){
			var html = [];
			html.push("<option value=''>请选择</option>");
			$.each(data,function(idx,item){
				if(!curVal){
					html.push("<option value='" + item.id + "'>" + item.code + "</option>");
				}else{
					if(curVal == item.id){
						html.push("<option selected value='" + item.id + "'>" + item.code + "</option>"); 
					}
				}
			});
			$('#secondLevelTypes').html(html.join(''));
			$('#l_typeB').hide();
		},
		beforeSend:function(){
			$('#l_typeB').show();
		},
		error:function(){
			$('#l_typeB').hide();
		}
	});
}
</script>
</body>
</html>