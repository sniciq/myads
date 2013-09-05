Ext.onReady(function() {
	var lastDay = new Date();
	var time = lastDay.getTime();
	lastDay.setTime(time - 24*60*60*1000);
	lastDay = Ext.util.Format.date(lastDay, "Y-m-d");
	
	var searchForm = new Ext.form.FormPanel({
		frame: true,
		region: 'north',
		labelAlign: 'right',
		items: [
			{
				columnWidth: .33,
				layout:'hbox',
				layoutConfig: {
                    align:'middle'
                },
				defaults:{margins:'0 10 0 0'},
				items: [
					new Ext.form.Label({
						text: '开始日期'
					}),
					new Ext.form.DateField({
						fieldLabel: '开始日期',
						name: 'startTime',
						allowBlank:false,
						value: lastDay,
						editable : false,
						format:'Y-m-d'
					}),
					new Ext.form.Label({
						text: '结束日期'
					}),
					new Ext.form.DateField({
						fieldLabel: '结束日期',
						name: 'endTime',
						allowBlank:false,
						editable : false,
						value:lastDay,
						format:'Y-m-d'
					}),
					new Ext.Button({
						text: '查询',
						width: 70,
						handler: function() {
							var dayTime = searchForm.getForm().findField("startTime").getRawValue();
							var endTime = searchForm.getForm().findField("endTime").getRawValue();
							ds.baseParams= {startTime: dayTime,endTime:endTime};
							ds.load({params: {start:0, limit:30}});
						}
					})
				]
			}
		]
	});
	searchForm.render('searchPanel');

	
	var cm = new Ext.grid.ColumnModel([
   	    {header:'时段', dataIndex:'hour_id', sortable:true},
   	    {header:'PV', dataIndex:'pv', sortable:true},
   	    {header:'UV', dataIndex:'uv', sortable:true},
   	 	{header:'VV', dataIndex:'vv', sortable:true}
   	]);
   	
   	var ds = new Ext.data.Store({
   		proxy: new Ext.data.HttpProxy({url: '/myads/HTML/webdata/FlowDataAction_searchHourReportData.action'}),
   		remoteSort: true,
   		reader: new Ext.data.JsonReader(
   			{
   				totalProperty: 'total',
   				idProperty:'id',
   				root: 'invdata'
   			},
   			[
   				{name: 'id'},
   				{name: 'hour_id'},
   				{name: 'pv'},
   				{name: 'uv'},
   				{name: 'vv'}
   			]
   		)
   	});
   	
   	ds.baseParams= {startTime: lastDay,endTime:lastDay};
    ds.load({params: {start:0, limit:20}});
   	
	var grid = new Ext.grid.GridPanel({
		el: 'grid',
		region: 'center',
		ds: ds,
		cm: cm,
		loadMask: {msg:'正在加载数据，请稍侯……'},
	    height:400,
	    viewConfig: {
	    	forceFit: true
	    },
	    bbar: new Ext.PagingToolbar({
		    pageSize: 30,
		    store: ds,
			displayInfo: true,
			displayMsg: '显示第{0}条到{1}条记录,一共{2}条',
			emptyMsg: '没有记录',
			items: [
				'-',
				{
					text: '导出EXCEL',
					handler: function() {
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
						var dayTime = searchForm.getForm().findField("startTime").getRawValue();
						var endTime = searchForm.getForm().findField("endTime").getRawValue();
						window.location.href = '/myads/HTML/webdata/FlowDataAction_searchHourReportData.action?exp_name=hour.xls&exp_column_names=' + encodeURI(encodeURI(strColoumnNames)) + '&exp_column_indexs=' + strColoumnIndexs + '&startTime=' + dayTime + "&endTime=" + endTime;
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