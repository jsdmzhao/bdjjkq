Ext.onReady( function() {
	var createMsg = "建立新员工";
	var delMsg = "员工离职操作";
	var queryMsg = "查询所有部门下的员工";
	if(deptName) {
		createMsg = '在" ' + deptName + '"部门下建立员工';
		queryMsg = '查询" ' + deptName + '"部门下的员工';
	}
	Ext.QuickTips.init();
	var tb = new Ext.Toolbar();
	tb.render('toolbar');
	tb.addElement(Ext.get('queryFrm').dom);
	tb.addButton( {
		text :'查询',
		handler : function() {
			Ext.get('queryFrm').dom.submit();
		},
		tooltip :{text : queryMsg, title:'提示:'}
	});
	tb.addButton( {
		text :'新建',
		handler : function() {
			window.location.href = URL_PREFIX + "/hr/employee/edit.htm?deptId=" + Ext.get('deptId').dom.value;
		},
		tooltip :{text : createMsg, title:'提示:'}
	});
	tb.addButton( {
		text :'离职',
		handler : onDimission,
		tooltip :{text : delMsg, title:'提示:'}
	});
});
function onDimission() {
	var selectItem = document.getElementsByName("selectedItems");
	var j = 0;
	for ( var i = 0; i < selectItem.length; i++) {
		if (selectItem[i].checked) {
			j++;
		}
	}
	if (j > 0) {
		Ext.MessageBox.confirm('提示', '确定所选择的员工离职吗？', function(btn) {
			if (btn == 'yes') {
				document.getElementById('ec').action = "dimission.htm";
				document.getElementById('ec').submit();
			}
		});
	} else {
		Ext.MessageBox.alert('提示', '请选择要离职的员工！');
	}
}