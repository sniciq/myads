Ext.namespace("com.myads.advflight.AdvflightRelationPanel"); 
com.myads.advflight.AdvflightRelationPanel = function(config) {
	var mypanel = this;
	var searchForm = this.searchForm = new Ext.FormPanel({
		frame: true,
		title: '查询',
		collapsible : true,
		collapsed: true,
		collapseMode:'mini',
		height: 100,
		labelWidth: 70,
		region: 'north',
		labelAlign: 'right',
		items: [
			{
				layout: 'column',
				items: [
					{
						columnWidth: .5, layout: 'form',
						items: [
							{ xtype: 'textfield', name: 'channelName', fieldLabel: '频道名称',anchor : '95%'}
						]
					},
					{
						columnWidth: .5, layout: 'form',
						items: [
							{ xtype: 'textfield', name: 'advbarName', fieldLabel: '广告条名称',anchor : '95%'}
						]
					}
				]
			}
		],
		buttons: [
			new Ext.Button({
				text: '查询',
				width: 70,
				handler: function() {
					var fv = searchForm.getForm().getValues();
					ds.baseParams= fv;
					ds.load({params: {start:0, limit:20}});
				}
			}),
			new Ext.Button({
					text: '重置',
					width: 70,
					handler: function() {
						searchForm.form.reset();
						ds.baseParams= {};
						ds.load({params: {start:0, limit:20}});
					}
			})
		]
	});
	
	var sm = new Ext.grid.CheckboxSelectionModel({singleSelect:true});
	var cm = new Ext.grid.ColumnModel([
		sm,
		{header:'ID', dataIndex:'id', width:30, sortable:true, menuDisabled:true},
	    {header:'频道', dataIndex:'channelName', width:80, sortable:true, menuDisabled:true},
		{header:'广告条', dataIndex:'advbarName', width:100, sortable:true, menuDisabled:true},
		{header:'使用类型', dataIndex:'useTypeName', width:50, sortable:true, menuDisabled:true},
	    {header:'售卖方式', dataIndex:'saleTypeName',width:50, sortable:true, menuDisabled:true},
	    {header:'投放时期', dataIndex:'startTime', sortable:true, menuDisabled:true,
	    	renderer: function(value, metadata, record, rowIndex, colIndex, store) {
            	return record.get("startTime") + "---" + record.get("endTime");
        	}
	    }
	]);
	
	var ds = this.ds = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({url: '/myads/HTML/advflight/BookAction_getBackgroudBookPkg.action'}),
		remoteSort: false,
		reader: new Ext.data.JsonReader(
			{
				totalProperty: 'total',
				idProperty:'id',
				root: 'invdata'
			},
			[
				{name: 'id'},
				{name: 'channelName'},
				{name: 'advbarName'},
				{name: 'useType'},
				{name: 'useTypeName'},
				{name: 'saleType'},
				{name: 'saleTypeName'},
				{name: 'discount'},
				{name: 'startTime'},
				{name: 'endTime'}
			]
		)
	});
	
	var grid = this.grid = new Ext.grid.GridPanel({
		region: 'center',
		ds: ds,
		cm: cm,
		sm: sm,
	    viewConfig: {
	    	forceFit: true
	    },
	    tbar: new Ext.Toolbar({
	    	buttons: [
	    		{
					text: '查询'	,
					iconCls: 'find',
					handler: function() {
						if(searchForm.collapsed)
							searchForm.expand();
						else
							searchForm.collapse();
					}
				},
				{
					text: '确定',
					iconCls: 'ok',
					handler: function() {
						var rs = grid.getSelectionModel().getSelected();
						mypanel.fireEvent('okClick', rs.data);
					}
				}
	    	]
	    }),
	    bbar: new Ext.PagingToolbar({
		    pageSize: 20,
		    store: ds,
			displayInfo: true,
			displayMsg: '显示第{0}条到{1}条记录,一共{2}条',
			emptyMsg: '没有记录'
	     })
	});
	
	Ext.apply(this, {
		frame: true,
		width: 300,
		height: 300,
		layout: 'border',
		items:[searchForm, grid]
	});
	
	this.addEvents('okClick');
	
	com.myads.advflight.AdvflightRelationPanel.superclass.constructor.apply(this, arguments);
};


Ext.extend(com.myads.advflight.AdvflightRelationPanel, Ext.Panel, {
	onRender: function() {
		com.myads.advflight.AdvflightRelationPanel.superclass.onRender.apply(this, arguments);
	},
	
	loadData : function() {
		this.ds.load({params: {start:0, limit:20}});
	}
});