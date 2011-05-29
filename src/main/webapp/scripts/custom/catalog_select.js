
Ext.onReady(function() {
	Ext.tree.XTreeLoader = function(config) {
		config = config || {};
		Ext.tree.XTreeLoader.superclass.constructor.call(this, config);
		this.addEvents("beforeCreateNode");
	};

	Ext.extend(Ext.tree.XTreeLoader, Ext.tree.TreeLoader, {
		createNode : function(attr) {
			this.fireEvent("beforeCreateNode", this, attr);
			return Ext.tree.XTreeLoader.superclass.createNode.apply(this,
					arguments);
		}
	});
	var comboxWithTree = new Ext.form.ComboBox({
		store : new Ext.data.SimpleStore({
			fields : [],
			data : [[]]
		}),
		editable : false,
		mode : 'local',
		triggerAction : 'all',
		maxHeight : 200,
		tpl : "<tpl for='.'><div style='height:200px'><div id='inner-tree'></div></div></tpl>",
		selectedClass : '',
		onSelect : Ext.emptyFn
	});
	var root = new Ext.tree.AsyncTreeNode({
		text : 'Jtiger',
		id : '0'
	});
	//root.expand();
	var tree = new Ext.tree.TreePanel({
		loader : new Ext.tree.XTreeLoader({
			dataUrl : '/admin/cms/catalog/catalogTree.htm?v=' + Math.random(3),
			listeners : {
				"beforeCreateNode" : function(l, attr) {
				}
			}
		}),
		border : true,
		animate : false,
		autoHeight : true,
		rootVisible : false,
		root : root
	});
	tree.on('click', function(node) {
		nodeIdCallback(node.id);
		comboxWithTree.setValue(node.text);
		comboxWithTree.collapse();
	});
	comboxWithTree.on('expand', function() {
		tree.render('inner-tree');
		if($.browser.mozilla) {
			var w = $('#comboxWithTree input').css('width');
			$('.x-combo-list').css('width', w);
			$('.x-combo-list-inner').css('width',  w);
		}
	});
	if (initValue) {
		comboxWithTree.setValue(initValue);
	}
	
	comboxWithTree.render('comboxWithTree');
	$('.clear_tree').click(function() {
		comboxWithTree.setValue('');
		$('div.comboxWithTree .xx-form-text').val('');
		nodeIdCallback('');
	});
	
});


/**
 * Callback function that deal with the dept's id. When any dept is clicked, the
 * function will be invoked. Users can customize the funcation by override.
 */
nodeIdCallback = function(nodeId) {

};