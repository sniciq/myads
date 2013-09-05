Ext.onReady(function() {
	var toolBar = new Ext.Toolbar({});
	var menuRoot = new Ext.tree.AsyncTreeNode({
		id : '0',
		text: 'root'
	});
	
	var menuTree = new Ext.tree.TreePanel({
		rootVisible: false,
		loader: new Ext.tree.TreeLoader({dataUrl: '/myads/HTML/sysfun/UserAction_getUserResourcesTreeNode.action',baseParams:{node:0}})
	});
	
	menuTree.addListener("click", function(node, event) {
		if(!node.attributes.leaf)
			return;
		event.stopEvent();
		centerPanel.update({
			html: '<iframe scrolling="auto" frameborder="0" width="100%" height="100%" src="' + node.attributes.url + '"></iframe>'
		});
	});
	
	menuTree.setRootNode(menuRoot);
	
	toolBar.add(new Ext.form.Label({
		text: '酷六广告系统',
		style : 'line-height: 25px'
	}));
	toolBar.addFill();
	
	Ext.Ajax.request({
		method: 'post',
		url: '/myads/HTML/sysfun/UserAction_getUserResourcesMenu.action',
		params: '',
	   	success:function(resp){
	    	var obj=Ext.util.JSON.decode(resp.responseText);
	      	
	    	for(var i = 0; i < obj.length; i++) {
				var aMenu = obj[i];
				var menuBut = new Ext.Toolbar.Button({
					id: aMenu.id,
					text: aMenu.text,
					handler: function(btn) {
						menuRoot.removeAll();
						centerPanel.update({
							html: '<iframe scrolling="auto" frameborder="0" width="100%" height="100%" src="welcome.html"></iframe>'
						});
						
						var tloader = menuTree.getLoader();
						tloader.baseParams = {menuId:btn.id, menuName:btn.text};
						tloader.load(menuRoot,function(pnode){});
					}
				});
				toolBar.addSpacer();
				toolBar.addSeparator();
				toolBar.addButton(menuBut);
			}
			
			toolBar.addSpacer();
			toolBar.addSeparator();
			toolBar.addButton(new Ext.Toolbar.Button({
				text: '退出登录',
				handler: function() {
					var a = Ext.MessageBox.confirm('提示',"确定退出登录？？", logout);
					function logout(btn) {
						if (btn == 'yes') {
							Ext.Ajax.request({
								method: 'post',
								params: '',
								url : '/myads/HTML/sysfun/LoginLocalAction_logout.action',
							   	success:function(resp){
									var obj=Ext.util.JSON.decode(resp.responseText);
									if (obj.result == 'success') {
										window.location.href = '/myads/HTML/index.html';
									} else {
										Ext.MessageBox.alert("提示", action.result);
									}
							    }
							});
						}
					}
				}
			}));
			toolBar.doLayout();
	    },
	    failure: function(response) {
            Ext.Msg.alert('错误', '无法访问服务器资源。');
        }
	});
	
	toolBar.render("north-div");
	
	var westPanel = new Ext.Panel({
		region: 'west',
		contentEl: 'west-div',
		split : true,// 为true时，布局边框变粗 ,默认为false
		border : false,
		collapsible : false,
		collapsed : false,// 初始化是否显示, 默认是显示
		width : 150,
		minSize : 10,// 最小宽度
		maxSize : 300,
		layout : 'accordion',
		items : [
			{
				title : '功能列表',
				iconCls : 'money_box',// 字面板样式
				layout : "fit",
				items : menuTree
			}
		]
	});
	
	var centerPanel = new Ext.Panel({
		region: 'center',
		contentEl: 'center-div',
		border: true,
		collapsible: false,
		html: '<iframe scrolling="auto" frameborder="0" width="100%" height="100%" src="welcome.html"></iframe>'
	});
	
	var mainViewPort = new Ext.Viewport({
		layout: 'border',
		items:[
			{
				region: 'north',
				contentEl: 'north-div',
				border: true,
				collapsible: false
			},
			westPanel,
			centerPanel
		]
	});
});