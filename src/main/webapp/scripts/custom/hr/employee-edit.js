Ext.onReady(function() {
	var tabs = new Ext.TabPanel({
		renderTo : 'tabs',
		width : '95%',
		activeTab : 0,
		frame : true,
		defaults : { 
			autoHeight : true
		},
		items : [{
			contentEl : 'login',
			title : '登陆信息'
		},{
			contentEl : 'Personal',
			title : '个人信息',
			labelWidth : 160
		},{
			contentEl : 'contact',
			title : '联系方式'
		},{
			contentEl : 'other',
			title : '其它信息'
		}]
	});
});