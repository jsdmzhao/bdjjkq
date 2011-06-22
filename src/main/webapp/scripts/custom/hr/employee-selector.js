Ext.override(Ext.tree.TreeFilter, {
	filterBy : function(fn, scope, startNode) {
		startNode = startNode || this.tree.root;
		if (this.autoClear) {
			this.clear();
		}
		var af = this.filtered, rv = this.reverse;
		var bf = {};
		var pf = function(n) {
			if (!n.parentNode) {
				return;
			}
			bf[n.parentNode.id] = n.parentNode;
			pf(n.parentNode);
		}
		var f = function(n) {
			var m = fn.call(scope || n, n);
			if (m) {
				bf[n.id] = n;
				pf(n);
			}
			return true;
		}
		startNode.cascade(f);
		for (var id in this.tree.nodeHash) {
			var ns = bf[id];
			if (ns == undefined) {
				var nh = this.tree.nodeHash[id];
				af[nh.id] = nh;
				nh.ui.hide();
			} else {
				ns.expand();
			}
		}
		if (this.remove) {
			for (var id in af) {
				if (typeof id != "function") {
					var n = af[id];
					if (n && n.parentNode) {
						n.parentNode.removeChild(n);
					}
				}
			}
		}
	}
});

var EmployeeSelector = function(options) {
	this.url = options.url;
	if (options.params != null) {
		this.params = options.params;
	}
	this.multiSel = (options.multiSel == null) ? false : options.multiSel;
	this.width = (options.width == null) ? this.width : options.width;
	this.height = (options.height == null) ? this.height : options.height;
	this.el = (options.el == null) ? this.el : options.el;
	this.id = (options.id == null) ? Ext.id() : options.id;
	this.idsEl = (options.idsEl == null) ? this.idsEl : options.idsEl;
	this.textEl = (options.textEl == null) ? this.textEl : options.textEl;
	this.INPUT_ID += this.id;
	this.init();
	this.expandAll();
};

EmployeeSelector.prototype = {
	url : '',
	params : {},
	id : null,
	multiSel : false,// 是否可以多选
	width : 580,
	height : 420,
	el : 'emp_sel_container',
	win : {},// Ext.Window
	tree : {},// Ext.Tree.TreePanel
	treeFilter : {},
	INPUT_ID : 'user_name_input_', // 姓名输入框ID

	setUrl : function(url) {
		this.url = url;
	},

	setBaseParams : function(baseParams) {
		this.store.baseParams = baseParams;
	},

	addParam : function(key, value) {
		this.params[key] = value;
	},

	init : function() {
		var _this = this;
		_this.tree = new Ext.tree.TreePanel({
			checkModel : _this.multiSel ? 'cascade' : 'single', // 单选多选
			onlyLeafCheckable : !_this.multiSel,
			rootVisible : false,
			animate : false,
			autoScroll : true,
			border : false,
			split : false,
			loader : new Ext.tree.XTreeLoader({
				dataUrl : _this.url,
				baseAttrs : {
					uiProvider : Ext.tree.TreeCheckNodeUI
				},
				listeners : {
					"beforeCreateNode" : function(l, attr) {
						// 所有的默认展开
						attr.expanded = true;
						// 叶子为部门的图标
						if (attr.descn == "DEPT" && !attr.childNodes) {
							attr.iconCls = "dept-tree-node-icon";
						}
						if (attr.descn == "EMP") {
							if (attr.sex == 'F') {
								attr.iconCls = "emp-M-tree-node-icon";
							} else {
								attr.iconCls = "emp-F-tree-node-icon";
							}
						}
					}
				}
			}),
			root : new Ext.tree.AsyncTreeNode({
				id : '0',
				text : 'root'
			})
		});

		_this.tree.getChecked = function(a, startNode) {
			startNode = startNode || this.root;
			var r = [];
			var f = function() {
				// 只能得选中的员工，部门排除
				if (this.attributes.checked && this.attributes.descn == 'EMP') {
					r.push(!a ? this : (a == 'id'
							? this.id
							: this.attributes[a]));
				}
			}
			startNode.cascade(f);
			return r;
		}

		_this.tree.on("check", function(node, checked) {
			var ids = _this.tree.getChecked('id');
			var txts = _this.tree.getChecked('text');
			Ext.get(_this.textEl).dom.value = txts;
			Ext.get(_this.idsEl).dom.value = ids;
		});

		// tree过虑
		_this.treeFilter = new Ext.tree.TreeFilter(_this.tree, {
			clearBlank : true,
			autoClear : true
		});

		var tbar = []; // 工具条
		tbar.push(this.tbarItems());
		tbar.push({// 查询按钮
			text : '查询',
			handler : function() {
				var text = Ext.get(_this.INPUT_ID).dom.value;
				_this.treeFilter.filterBy(function(n) {
					if (n.text.indexOf(text) == -1) {
						return false;
					} else {
						return true;
					}
				});
			}
		});

		_this.win = new Ext.Window({
			id : _this.id,
			el : _this.el,
			tbar : tbar,
			border : true,
			layout : 'fit',
			modal : true,
			plain : false,
			width : _this.width,
			height : _this.height,
			closeAction : 'hide',
			items : [_this.tree],
			buttons : []
		});
	},

	tbarItems : function() {
		var div = document.createElement("div");
		var txt = document.createElement("input");
		div.style.margin = "2px;";
		div.style.padding = "2px";
		txt.type = "text";
		txt.size = 10;
		txt.name = "model.name";
		txt.id = this.INPUT_ID;
		var msg = document.createTextNode(" 部门/姓名:");
		div.appendChild(msg);
		div.appendChild(txt);
		return div;
	},
	
	show : function(reload) {
		if (reload) {
			var input = Ext.getDom(this.INPUT_ID);
			if (input) {
				input.value = '';
			}
			this.treeFilter.clear();
		}
		//设置TreeNode选框中的状态
		var str = Ext.get(this.idsEl).dom.value;
		if (str) {
			var ids = str.split(',');
			for (var i = 0; i < ids.length; i++) {
				var node = this.tree.nodeHash[ids[i]];
				node.ui.check(true);
			}
		}
		this.win.show();
	},
	expandAll : function() {
		//不用延时加载的组件
		this.win.render();
		//树展开
		this.tree.expandAll();
	}
};
