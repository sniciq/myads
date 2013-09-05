Ext.onReady(function() {
	var lastDay = new Date();
	var time = lastDay.getTime();
	lastDay.setTime(time - 24*60*60*1000);
	lastDay = Ext.util.Format.date(lastDay, "Y-m-d");
	
	var searchForm = new Ext.form.FormPanel({
		labelAlign: 'right',
		region: 'north',
		frame: true,
		items: [
			{//行1
				layout: 'column',
				items: [
					{
						columnWidth: .33, layout: 'form',
						items: [
							new Ext.form.DateField({
								fieldLabel: '开始日期',
								name: 'startTime',
								allowBlank:false,
								editable : false,
								format:'Y-m-d',
								value: lastDay,
								anchor : '95%'
							})
						]
					},
		            {	columnWidth: .34, layout: 'form',
		            	items: [
		            	      new Ext.form.DateField({
								fieldLabel: '结束日期',
								name: 'endTime',
								allowBlank:false,
								editable : false,
								format:'Y-m-d',
								value: lastDay,
								anchor : '95%'
							})
		            	]
		            },
					{	
						columnWidth: .33, layout: 'form',
		            	items: [
		            	        { xtype: 'textfield', name: 'programName', fieldLabel: '专项活动名称 ', allowBlank:false, anchor : '95%'}
		            	]
		            }
				]
			}
		],
		buttons: [
			{
				text: '查询',
				handler: function() {
					var fv = searchForm.getForm().getValues();
					ds.baseParams= fv;
					ds.load({params: {start:0, limit:20}});
				}
			},
			{
				text: '重置',
				handler: function() {
					searchForm.form.reset();
				}
			}
		]
		
	});
	
	searchForm.render('searchForm');
	
	var cm = new Ext.grid.ColumnModel([
		{header:'名称', dataIndex:'custom_code', sortable:true},
	    {header:'日期', dataIndex:'day_time', sortable:true},
	    {header:'PV', dataIndex:'pv', sortable:true},
	    {header:'UV', dataIndex:'uv', sortable:true}
	]);
	
	var ds = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({url: '/myads/HTML/webdata/ProgramDataAction_search.action'}),
		remoteSort: true,
		reader: new Ext.data.JsonReader(
			{
				totalProperty: 'total',
				idProperty:'id',
				root: 'invdata'
			},
			[
				{name: 'id'},
				{name: 'custom_code'},
				{name: 'pv'},
				{name: 'uv'},
				{name: 'hour_id'},
				{name: 'day_time'}
			]
		)
	});

	var grid = new Ext.grid.GridPanel({
		el: 'grid',
		region: 'center',
		ds: ds,
		cm: cm,
	    viewConfig: {
	    	forceFit: true
	    },
	    bbar: new Ext.PagingToolbar({
		    pageSize: 20,
		    store: ds,
			displayInfo: true,
			displayMsg: '显示第{0}条到{1}条记录,一共{2}条',
			emptyMsg: '没有记录',
			items: [
				'-',
				{
					text: '导出EXCEL',
					handler: function() {
						if(ds.getCount() == 0)
   							return;
   							
						var cms = grid.getColumnModel();
						var strColoumnNames = "";
						var strColoumnIndexs = "";
						for(var i = 0; i < cms.getColumnCount(); i++) {
							if(cms.getColumnHeader(i) == '' || cms.getColumnHeader(i) == '操作')
   								continue;
   								
							if (!cms.isHidden(i)) {
								strColoumnIndexs += cms.getDataIndex(i) + ',';
								strColoumnNames += cms.getColumnHeader(i) + ',';
							}
						}
						
						window.location.href = '/myads/HTML/webdata/ProgramDataAction_export.action?exp_name=HotProgram.xls&exp_column_names=' + encodeURI(encodeURI(strColoumnNames)) 
							+ '&exp_column_indexs=' + strColoumnIndexs 
							+ '&' + encodeURI(searchForm.getForm().getValues(true)); 
					}
				}
		    ]
	    })
	});
	
	grid.render();
	
	var mainViewPort = new Ext.Viewport({
		layout: 'border',
		items:[
		       searchForm, grid
		]
	});
});