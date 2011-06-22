Ext.onReady(function() {
	var comboxWithTree = new Ext.form.ComboBox({
		store : new Ext.data.SimpleStore({
			fields : [],
			data : [[]]
		}),
		editable : false,
		mode : 'local',
		triggerAction : 'all',
		maxHeight : 200,
		width : 155,
		tpl : "<tpl for='.'><div style='height:200px;'><div id='tree'></div></div></tpl>",
		selectedClass : '',
		onSelect : Ext.emptyFn
	});
	
	var tree = new Ext.tree.TreePanel({
		loader : new Ext.tree.TreeLoader({
			dataUrl : '/hr/dept/deptTree.htm',
			baseParams : {
				id : param
			},
			requestMethod : "POST"
		}),
		border : false,
		animate : false,
		autoHeight : true,
		rootVisible : false,
		root : new Ext.tree.AsyncTreeNode({
			text : 'Systop',
			id : '0'
		})
	});
	
	tree.on('click', function(node) {
		nodeIdCallback(node.id);
		comboxWithTree.setValue(node.text);
		comboxWithTree.collapse();
	});
	
	comboxWithTree.on('expand', function() {
		tree.render('tree');
	});
	
	if (initValue) {
		comboxWithTree.setValue(initValue);
	}
	
	comboxWithTree.render('comboxWithTree');
});
