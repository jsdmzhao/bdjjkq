Ext.onReady(function() {
	var createMsg = "建立新部门";
	var delMsg = "删除部门";
	if(deptName) {
		createMsg = '在" ' + deptName + '"下建立子部门';
		delMsg = '删除"' + deptName + '"，如果有子部门或者员工，则不能删除';
	}
	
	Ext.QuickTips.init();
	var bt = new Ext.Toolbar();
	bt.render('toolbar');
	bt.add([{
        text : "新建部门",
        handler : newChild,
        tooltip :{text : createMsg, title:'提示:'}
    }, {
        text : "删除部门",
        handler : remove,
        tooltip :{text: delMsg, title:'提示:'}
    }]);
});
function remove() {
	var id = Ext.get('model.id').dom.value;
	if (!id) {
		Ext.MessageBox.alert("提示", "请从左侧列表中选择要删除的部门。");
		return;
	}
	Ext.MessageBox.confirm("提示", '是否确定删除此部门?', function(btn) {
		if (btn == 'yes') {
			window.location.href = URL_PREFIX + "/hr/dept/remove.htm?model.id=" + id;
		}
	});
}

function newChild() {
	var parentId = Ext.get('model.id').dom.value;
	if (!parentId) {
		return;
	}
	window.location.href = URL_PREFIX + '/hr/dept/edit.htm?parentId=' + parentId;
}


