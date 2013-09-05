Ext.onReady(function() {
	var sm = new Ext.grid.CheckboxSelectionModel();
	var cm = new Ext.grid.ColumnModel([
		sm,
	    {header:'类型', dataIndex:'dataType', sortable:true},
	    {header:'名称', dataIndex:'dataName', sortable:true},
	    {header:'值', dataIndex:'dataValue', sortable:true}
	]);
	
	var ds = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({url: '/myads/HTML/basic/BaseDataAction_search.action'}),
		remoteSort: false,
		reader: new Ext.data.JsonReader(
			{
				totalProperty: 'total',
				idProperty:'id',
				root: 'invdata'
			},
			[
				{name: 'id'},
				{name: 'dataType'},
				{name: 'dataName'},
				{name: 'dataValue'}
			]
		)
	});

	ds.load({params: {start:0, limit:50}});
	
	var grid = new Ext.grid.GridPanel({
		el: 'grid',
		region: 'center',
		ds: ds,
		cm: cm,
		sm: sm,
	    viewConfig: {
	    	forceFit: true
	    },
	    bbar: new Ext.PagingToolbar({
		    pageSize: 50,
		    store: ds,
			displayInfo: true,
			displayMsg: '显示第{0}条到{1}条记录,一共{2}条',
			emptyMsg: '没有记录'
	    })
	});
	
	new Ext.Viewport({
		layout: 'border',
		items:[
		       calander_grid,grid
		]
	});
});