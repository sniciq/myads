Ext.onReady(function() {
	var resourcesForm = new Ext.form.FormPanel({
		labelAlign: 'right',
        labelWidth: 60,
        frame:true,
        xtype: 'fieldset',
		items: [
				{
					layout:'column',
					items: [
						{	columnWidth: .25, layout: 'form',
			            	items: [
			            	        { xtype: 'hidden', name: 'resourceId', hidden:true, hiddenLabel:true}
			            	] 
			            }
					]
				},
		        { //行1
		            layout:'column',
		            items: [
			            {	columnWidth: .25, layout: 'form',
			            	items: [
			            	        { xtype: 'textfield', name: 'id', fieldLabel: '资 源 ID',anchor : '95%'}
			            	] 
			            },
			            {	columnWidth: .25, layout: 'form',
			            	items: [
			            	        { xtype: 'textfield', name: 'parantId', fieldLabel: '父 结 点 ',anchor : '95%'}
			            	]
			            },
			            {	columnWidth: .25, layout: 'form',
			            	items: [
			            	        { xtype: 'textfield', name: 'menuOrder', fieldLabel: '排 序 号 ',anchor : '95%'}
			            	] 
			            }
			            ,
			            {	columnWidth: .25, layout: 'form',
			            	items: [
			            	        { xtype: 'textfield', name: 'isValidate', fieldLabel: '是否可用 ',anchor : '95%' }
			            	] 
			            }
		            ]
		        },
		        {////行2
		        	layout:'column',
		            items:
		        	[
						{
							columnWidth: .25, layout: 'form', 
							items: [{ xtype: 'textfield', name: 'openIcon', fieldLabel: '展开图标',anchor : '95%'}]
						},
						{
							columnWidth: .25, layout: 'form', 
							items: [{ xtype: 'textfield', name: 'icon', fieldLabel: '关闭图标',anchor : '95%'}]
						},
		        	 	{
		        	 		columnWidth: .50, layout: 'form', 
							items: [{ xtype: 'textfield', name: 'menuName', fieldLabel: '资源名称', anchor : '98%'}]
		        	 	}
		        	]
		        }, 
		        {//行3
		        	layout:'column',
		        	items: [
			        	{
			        		columnWidth: 1, layout: 'form', 
							items: [{ xtype: 'textfield', name: 'actionPath', fieldLabel: '资源路径',anchor : '99%'}]
			        	}
		        	]
		        }, 
		         {//行4
		        	layout:'column',
		        	items: [
			        	{
			        		columnWidth: 1, layout: 'form', 
							items: [{ xtype: 'textfield', name: 'description', fieldLabel: '描述',anchor : '99%'}]
			        	}
		        	]
		        } 
		       
		],
		buttons: [
			{
	            text: '保存',
	            handler: function() {
					resourcesForm.form.doAction('submit', {
						url: '/myads/HTML/basic/ResourcesAction_save.action',
						method: 'post',
						params: '',
						success: function(form, action) {
							Ext.MessageBox.alert('结果', '保存成功！');
							form.reset();
							grid.getStore().reload();
						}
					});
	            }
        	},
        	{
	            text: '取消',
	            handler: function() {
	            	resourcesForm.form.reset();
	            }
        	},
        	{
	            text: '删除',
	            handler: function() {
	            	alert('删除');
	            }
        	}
        ]
	});
	
	resourcesForm.render('resourcesForm');
	
	var sm = new Ext.grid.CheckboxSelectionModel();
	var cm = new Ext.grid.ColumnModel([
		sm,
	    {header:'ID', dataIndex:'id', sortable:true},
	    {header:'名称', dataIndex:'menuName', sortable:true},
	    {header:'父结点', dataIndex:'parantId', sortable:true},
	    {header:'路径', width: 300, dataIndex:'actionPath', sortable:true},
	    {header:'是否可用', dataIndex:'isValidate', sortable:true}
	]);
	
	var ds = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({url: '/myads/HTML/basic/ResourcesAction_showAll.action'}),
		remoteSort: true,
		reader: new Ext.data.JsonReader(
			{
				totalProperty: 'total',
				idProperty: 'resourceId',
				root: 'invdata'
			},
			[
				{name: 'id'},
				{name: 'menuName'},
				{name: 'parantId'},
				{name: 'actionPath'},
				{name: 'isValidate'}
			]
		)
	});

	ds.load({params: {start:0, limit:10}});
	
	var grid = new Ext.grid.GridPanel({
		el: 'resourcesGrid',
		ds: ds,
		cm: cm,
		sm: sm,
	    height:300,
	    viewConfig: {
	    	forceFit: true
	    },
	    bbar: new Ext.PagingToolbar({
		    pageSize: 10,
		    store: ds,
			displayInfo: true,
			displayMsg: '显示第{0}条到{1}条记录,一共{2}条',
			emptyMsg: '没有记录'
	    })
	});
	
	grid.addListener('rowdblclick', function(grid, rowindex, e) {
		var record = grid.getStore().getAt(rowindex);
		resourcesForm.load({
			url: '/myads/HTML/basic/ResourcesAction_getResourceDetail.action',
			params: {resourceId: record.id}
		});
	});

	grid.render();
});