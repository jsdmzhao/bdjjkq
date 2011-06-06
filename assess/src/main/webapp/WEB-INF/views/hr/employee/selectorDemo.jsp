<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<%@include file="/common/extjs.jsp"%>
<%@include file="/common/meta.jsp"%>
<script type="text/javascript" src="${ctx}/js/extjs/diy/TreeCheckNodeUI.js"></script>
<script type="text/javascript" src="${ctx}/pages/hr/employee/selector.js"></script>
<title></title>
</head>
<body>
<div id="emp_sel_container" class="x-hidden">
    <div class="x-window-header">员工列表</div>
    <div id="emp_grid"></div>
</div>
<div id="emp_sel_container1" class="x-hidden">
    <div class="x-window-header">员工列表</div>
    <div id="emp_grid"></div>
</div>
<script>
var emp;
   emp = new EmployeeSelector({
       url: '${ctx}/hr/employee/deptEmpTree.htm',
       el: 'emp_sel_container',
       idsEl:'ids',
       textEl:'txts',
       width : 200,
	   height : 450,
	   multiSel : true,
	   id: '1'
   });
   
function showWin() {
   emp.url = '${ctx}/employee/index.htm';
   emp.show(true);
}

var emp1;
emp1 = new EmployeeSelector({
    url: '${ctx}/hr/employee/deptEmpTree.htm',
    el: 'emp_sel_container1',
    idsEl:'ids1',
    textEl:'txts1',
    width : 200,
	   height : 450,
	   multiSel : false,
	   id: '1'
});

function showWin() {
emp.show(true);
}

function showWin1() {
	emp1.show(true);
}
</script>
<input onclick="showWin()" value="全部" type="button">
<input id="txts" type="text"/>
<input id="ids" type="hidden"/>

<input onclick="showWin1()" value="全部" type="button">
<input id="txts1" type="text"/>
<input id="ids1" type="hidden"/>
</body>
</html>