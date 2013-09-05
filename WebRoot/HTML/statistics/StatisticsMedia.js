Ext.onReady(function() {
	
	// 频道
	var siteStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({url: '/myads/HTML/sysfun/SiteAction_getSiteList.action'}),
		reader: new Ext.data.ArrayReader({}, [
			{name: 'value'},{name: 'text'}
		])
	});
	siteStore.load();
	
	var siteCombobox = new Ext.form.ComboBox({
		fieldLabel: '选择网站',
		store: siteStore,
		mode: 'local',
		triggerAction: 'all',
		valueField: 'value',
		anchor : '99%',
		displayField: 'text',
		editable: false,
		listeners : {
			select : function(ComboBox, record, index) {
				channelStore.removeAll(true);
				channelCombobox.setValue();
				var value = record.data.value;
				channelStore.load({params:{siteId:value}});
			}
		}
	});
	
	var channelStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({url: '/myads/HTML/sysfun/ChannelAction_getChannelListBySiteId.action'}),
		reader: new Ext.data.ArrayReader({}, [
			{name: 'value'},{name: 'text'}
		])
	});
	
	var channelCombobox = new Ext.form.ComboBox({
		fieldLabel: '选择频道',
		store: channelStore,
		mode: 'local',
		triggerAction: 'all',
		valueField: 'value',
		anchor : '99%',
		displayField: 'text',
		editable: false,
		listeners : {
			select : function(ComboBox, record, index) {
				advposition_ds.baseParams = {siteId: siteCombobox.getValue(), channelId: channelCombobox.getValue()};
				advposition_ds.load({params: {start:0, limit:20}});
			}
		}
	});
	
	// 广告位
	var davposition_cm = new Ext.grid.ColumnModel([
	    {header:'广告位ID', dataIndex:'id', sortable:true},
	    {header:'广告位名称', dataIndex:'name', sortable:true}
	]);
	
	var advposition_ds = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({url: '/myads/HTML/advert/AdvpositionAction_showAll.action'}),
		remoteSort: false,
		reader: new Ext.data.JsonReader(
			{
				totalProperty: 'total',
				idProperty:'id',
				root: 'invdata'
			},
			[
				{name: 'id'},
				{name: 'name'},
				{name: 'nameEn'}
			]
		)
	});
	
	var advpositionGird = new Ext.grid.GridPanel({
		width: 300,
		height: 150,
		el: 'advpositionGird-div',
		ds: advposition_ds,
		cm: davposition_cm,
		frame: true,
	    viewConfig: {
	    	forceFit: true
	    },
	    bbar: new Ext.PagingToolbar({
		    pageSize: 20,
		    store: advposition_ds,
			displayInfo: false
	    }),
		listeners : {
            rowclick : function(grid, rowIndex, event) {
            	var row = grid.getSelectionModel().getSelected();
            	var advpositionId = row.data.id;
            	advbar_ds.baseParams = {advpositionId: advpositionId};
            	advbar_ds.load({params: {start:0, limit:20}});
            }
		}
	});
	
	// 广告条
	var advbar_cm = new Ext.grid.ColumnModel([
	    {header:'广告条ID', dataIndex:'id', sortable:true},
   	    {header:'广告条名称', dataIndex:'name', sortable:true}
	]);
	
	var advbar_ds = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({url: '/myads/HTML/advert/AdvbarAction_showAll.action'}),
		remoteSort: false,
		reader: new Ext.data.JsonReader(
			{
				totalProperty: 'total',
				idProperty:'id',
				root: 'invdata'
			},
			[
				{name: 'id'},
   				{name: 'name'},
   				{name: 'positionName'},
   				{name: 'num'}
			]
		)
	});
	
	var advbarGird = new Ext.grid.GridPanel({
		width: 300,
		height: 150,
		el: 'advbarGird-div',
		ds: advbar_ds,
		cm: advbar_cm,
		frame: true,
	    viewConfig: {
	    	forceFit: true
	    },
	    bbar: new Ext.PagingToolbar({
		    pageSize: 20,
		    store: advbar_ds,
			displayInfo: false
	    })
	});
	
	var searchpane = new Ext.Panel({
		width: 340,
		region: 'west',
		title: '统计',
		split: true,
		collapsible:true,
		layout: {
            type:'vbox',
            padding:'5',
            align:'center'
        },
        defaults:{margins:'0 0 5 0'},
		contentEl: 'searchpane-div',
		frame: true,
		items:[
			{
				layout:'form',
				width:300,
				labelAlign: 'right',
				labelWidth: 70,
				frame: true,
				items:[siteCombobox]
			},
			{
				layout:'form',
				width:300,
				labelAlign: 'right',
				labelWidth: 70,
				frame: true,
				items:[channelCombobox]
			},
			advpositionGird,advbarGird,
			{
				layout:'form',
				labelAlign: 'right',
				width:300,
				labelWidth: 10,
				items:[
					{
						layout: 'column',
						items: [
							{
								columnWidth: .5, layout: 'form',width:80,labelWidth: 55,
								items: [
									new Ext.form.DateField({
										id:'td1',
										fieldLabel: '月报开始',
										name: 'stateTime',
										editable : false,
										value: new Date(),
										format:'Y-m-d',
										anchor : '99%',
										listeners:{
										  'select': function(){
										      Ext.getCmp('td2').setValue("");
										   }
										}
									})
								]
							},
							{
								columnWidth: .5, layout: 'form',width:80,labelWidth: 55,
								items: [
									new Ext.form.DateField({
										id:'td1End',
										fieldLabel: '结束',
										name: 'stateTimeEnd',
										editable : false,
										value: new Date(),
										format:'Y-m-d',
										anchor : '99%',
										listeners:{
										  'select': function(){
										  		Ext.getCmp('td2').setValue("");
										   }
										}
									})
								]
							}
						]
					}
				]
			},
			{
				layout:'form',
				labelAlign: 'right',
				width:250,
				labelWidth: 70,
				items:[
					new Ext.form.DateField({
						id:'td2',
						fieldLabel: '日期(日报)',
						name: 'stateDayTime',
						editable : false,
						format:'Y-m-d',
						anchor : '99%',
						listeners:{
						  'select': function(){
						      Ext.getCmp('td1').setValue("");
						      Ext.getCmp('td1End').setValue("");
						   }
						}
					})
				]
			},
			new Ext.Button({
				text: '查看报表',
				width: 70,
				handler: function() {
					showReprot();
				}
			})
		]
	});
	
	function showReprot() {
		dataGrid.removeAll();
		var cv = channelCombobox.getValue();
		
		var statMonthTime = Ext.getCmp('td1').getRawValue();
		var statDayTime = Ext.getCmp('td2').getRawValue();
		
		var viewGridUrl;
		var viewGridPam;
		
		if(statMonthTime != '') {//月报
			var advpositionRecord = advpositionGird.getSelectionModel().getSelected();
			var advbarRecord = advbarGird.getSelectionModel().getSelected();
			var stateTimeMonthEnd = Ext.getCmp('td1End').getRawValue();
			
			if(advbarRecord) {//广告条
				dataGrid_ds.proxy = new Ext.data.HttpProxy({url:'/myads/HTML/statistics/StatisticsMediaAction_getAdvbarMonthData.action'});
				dataGrid_ds.load({params: {start:0, limit:200000, advbarId: advbarRecord.data.id, statTime: statMonthTime, stateTimeMonthEnd: stateTimeMonthEnd}});
				
				viewGridUrl = '/myads/HTML/statistics/StatisticsMediaAction_getViewDataAdvbar.action';
				viewGridPam = {start:0, limit:20, advbarId: advbarRecord.data.id, stateMonthTime: statMonthTime};
			}
			else if(advpositionRecord) {//广告位
				dataGrid_ds.proxy = new Ext.data.HttpProxy({url:'/myads/HTML/statistics/StatisticsMediaAction_getAdvpositionMonthData.action'});
				dataGrid_ds.load({params: {start:0, limit:200000, advPositionId: advpositionRecord.data.id, statTime: statMonthTime, stateTimeMonthEnd: stateTimeMonthEnd}});
				
				viewGridUrl = '/myads/HTML/statistics/StatisticsMediaAction_getViewDataAdvposition.action';
				viewGridPam = {start:0, limit:20, advPositionId: advpositionRecord.data.id, stateMonthTime: statMonthTime, stateTimeMonthEnd: stateTimeMonthEnd};
			}
			else if(cv != '') {//频道
				dataGrid_ds.proxy = new Ext.data.HttpProxy({url:'/myads/HTML/statistics/StatisticsMediaAction_getChanelMonthData.action'});
				dataGrid_ds.load({params: {start:0, limit:200000, channelId: cv, statTime: statMonthTime, stateTimeMonthEnd: stateTimeMonthEnd}});
				
				viewGridUrl = '/myads/HTML/statistics/StatisticsMediaAction_getViewDataChanel.action';
				viewGridPam = {start:0, limit:20, channelId: cv, stateMonthTime: statMonthTime, stateTimeMonthEnd: stateTimeMonthEnd};
			}
		}
		else if(statDayTime != '') {//日报
			var advpositionRecord = advpositionGird.getSelectionModel().getSelected();
			var advbarRecord = advbarGird.getSelectionModel().getSelected();
			if(advbarRecord) {//广告条
				dataGrid_ds.proxy = new Ext.data.HttpProxy({url:'/myads/HTML/statistics/StatisticsMediaAction_getAdvbarDailyData.action'});
				dataGrid_ds.load({params: {start:0, limit:200000, advbarId: advbarRecord.data.id, statTime: statDayTime}});
				
				viewGridUrl = '/myads/HTML/statistics/StatisticsMediaAction_getViewDataAdvbar.action';
				viewGridPam = {start:0, limit:20, advbarId: advbarRecord.data.id, stateDayTime: statDayTime};
			}
			else if(advpositionRecord) {//广告位
				dataGrid_ds.proxy = new Ext.data.HttpProxy({url:'/myads/HTML/statistics/StatisticsMediaAction_getAdvpositionDailyData.action'});
				dataGrid_ds.load({params: {start:0, limit:200000, advPositionId: advpositionRecord.data.id, statTime: statDayTime}});
				
				viewGridUrl = '/myads/HTML/statistics/StatisticsMediaAction_getViewDataAdvposition.action';
				viewGridPam = {start:0, limit:20, advPositionId: advpositionRecord.data.id, stateDayTime: statDayTime};
			}
			else if(cv != '') {//频道
				dataGrid_ds.proxy = new Ext.data.HttpProxy({url:'/myads/HTML/statistics/StatisticsMediaAction_getChanelDailyData.action'});
				dataGrid_ds.load({params: {start:0, limit:200000, channelId: cv, statTime: statDayTime}});
				
				viewGridUrl = '/myads/HTML/statistics/StatisticsMediaAction_getViewDataChanel.action';
				viewGridPam = {start:0, limit:20, channelId: cv, stateDayTime: statDayTime};
			}
		}
		
		if(viewGridUrl) {
			viewsGrid_ds.proxy = new Ext.data.HttpProxy({url: viewGridUrl});
			viewsGrid_ds.load({params: viewGridPam});
		}
	}
	
	function doExport(strColoumnIndexs, strColoumnNames) {
		var cv = channelCombobox.getValue();
		var statMonthTime = Ext.getCmp('td1').getRawValue();
		var statDayTime = Ext.getCmp('td2').getRawValue();
		var stateTimeMonthEnd = Ext.getCmp('td1End').getRawValue();
		
		if(statMonthTime != '') {//导出月报
			var advpositionRecord = advpositionGird.getSelectionModel().getSelected();
			var advbarRecord = advbarGird.getSelectionModel().getSelected();
			if(advbarRecord) {//广告条
				window.location.href = '/myads/HTML/statistics/StatisticsMediaAction_exportAdvbarMonthData.action?exp_name=data.xls' 
							+ '&exp_column_names=' + encodeURI(encodeURI(strColoumnNames)) 
							+ '&exp_column_indexs=' + strColoumnIndexs 
							+ '&advbarId=' + advbarRecord.data.id
							+ '&statTime=' + statMonthTime
							+ '&stateTimeMonthEnd=' + stateTimeMonthEnd;
			}
			else if(advpositionRecord) {//广告位
				window.location.href = '/myads/HTML/statistics/StatisticsMediaAction_exportAdvpositionMonthData.action?exp_name=data.xls' 
							+ '&exp_column_names=' + encodeURI(encodeURI(strColoumnNames)) 
							+ '&exp_column_indexs=' + strColoumnIndexs 
							+ '&advPositionId=' + advpositionRecord.data.id
							+ '&statTime=' + statMonthTime
							+ '&stateTimeMonthEnd=' + stateTimeMonthEnd;
			}
			else if(cv != '') {//频道
				window.location.href = '/myads/HTML/statistics/StatisticsMediaAction_exportChanelMonthData.action?exp_name=data.xls' 
							+ '&exp_column_names=' + encodeURI(encodeURI(strColoumnNames)) 
							+ '&exp_column_indexs=' + strColoumnIndexs 
							+ '&channelId=' + cv
							+ '&statTime=' + statMonthTime
							+ '&stateTimeMonthEnd=' + stateTimeMonthEnd;
			}
		}
		else if(statDayTime != '') {//日报
			var advpositionRecord = advpositionGird.getSelectionModel().getSelected();
			var advbarRecord = advbarGird.getSelectionModel().getSelected();
			if(advbarRecord) {//广告条
				window.location.href = '/myads/HTML/statistics/StatisticsMediaAction_exportAdvbarDailyData.action?exp_name=data.xls' 
							+ '&advbarId=' + advbarRecord.data.id
							+ '&statTime=' + statDayTime; 
			}
			else if(advpositionRecord) {//广告位
				window.location.href = '/myads/HTML/statistics/StatisticsMediaAction_exportAdvpositionDailyData.action?exp_name=data.xls' 
							+ '&advPositionId=' + advpositionRecord.data.id
							+ '&statTime=' + statDayTime;
			}
			else if(cv != '') {//频道
				window.location.href = '/myads/HTML/statistics/StatisticsMediaAction_exportChanelDailyData.action?exp_name=data.xls' 
							+ '&channelId=' + cv
							+ '&statTime=' + statDayTime; 
			}
		}
	}
	
	var chartStore = new Ext.data.JsonStore({
		fields: ['xField', 'pv', 'uv', 'click', 'uc']
	});
	
	var chart = new Ext.chart.ColumnChart({
		store: chartStore,
		xField: 'xField',
		series:[
			{yField:'pv',displayName:'PV'},
			{yField:'uv',displayName:'UV'}
		],
		yAxis: new Ext.chart.NumericAxis({
			labelRenderer: Ext.util.Format.number()
		}),
		xAxis: new Ext.chart.CategoryAxis({
			labelRenderer: function(value){
				return value;
			}
		}),
		extraStyle:{
			legend:{
				display: 'bottom'
			}
		}
	});
	
	var chartPane = new Ext.Panel({
		region: 'north',
		title: '图表',
		height: 300,
		split: true,
		collapsible:true,
		frame: true,
		items:[chart]
	});
	
	var dataGrid_cm = new Ext.grid.ColumnModel([
	    {header:'日期', dataIndex:'statTime', sortable:true},
	    {header:'名称', dataIndex:'name', sortable:true},
	    {header:'xField', dataIndex:'xField', sortable:true},
	    {header:'PV', dataIndex:'pv', sortable:true},
	    {header:'UV', dataIndex:'uv', sortable:true},
	    {header:'click', dataIndex:'click', sortable:true},
	    {header:'uc', dataIndex:'uc', sortable:true}
	]);
	
	var dataGrid_ds = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({url: '/myads/HTML/statistics/StatisticsMediaAction_getChanelMonthData.action'}),
		remoteSort: false,
		reader: new Ext.data.JsonReader(
			{
				totalProperty: 'total',
				idProperty:'id',
				root: 'invdata'
			},
			[
				{name: 'id'},
				{name: 'name'},
				{name: 'statTime'},
				{name: 'xField'},
				{name: 'pv'},
				{name: 'uv'},
				{name: 'click'},
				{name: 'uc'}
			]
		)
	});
	
	dataGrid_ds.on("load", function(s,records) {
		var data = [];
		for(var i = 0; i < records.length; i++) {
			var aobject = {};
			aobject.xField =  records[i].data.xField;
			aobject.pv =  records[i].data.pv;
			aobject.uv =  records[i].data.uv;
			aobject.click =  records[i].data.click;
			aobject.uc =  records[i].data.uc;
			data.push(aobject);
		}
		chartStore.loadData(data);
	});

	var dataGrid = new Ext.grid.GridPanel({
		el: 'dataGrid-div',
		region: 'center',
		ds: dataGrid_ds,
		cm: dataGrid_cm,
	    viewConfig: {
	    	forceFit: true
	    },
	    bbar: new Ext.PagingToolbar({
		    pageSize: 200000,
		    store: dataGrid_ds,
			displayInfo: true,
			displayMsg: '显示第{0}条到{1}条记录,一共{2}条',
			emptyMsg: '没有记录',
			items: [
				'-',
				{
					text: '导出EXCEL',
					handler: function() {
						if(dataGrid_ds.getCount() == 0)
   							return;
   							
						var strColoumnNames = "";
						var strColoumnIndexs = "";
						for(var i = 0; i < dataGrid_cm.getColumnCount(); i++) {
							if (!dataGrid_cm.isHidden(i)) {
								strColoumnIndexs += dataGrid_cm.getDataIndex(i) + ',';
								strColoumnNames += dataGrid_cm.getColumnHeader(i) + ',';
							}
						}
						
						doExport(strColoumnIndexs, strColoumnNames);
					}
				}
			]
	    })
	});
	
	var viewsGrid_cm = new Ext.grid.ColumnModel([
	    {header:'名称', dataIndex:'name', sortable:true},
		{header:'1+', dataIndex:'onceMore', sortable:true},
		{header:'2+', dataIndex:'twiceMore', sortable:true},
		{header:'3+', dataIndex:'threeMore', sortable:true},
		{header:'4+', dataIndex:'fourthMore', sortable:true},
		{header:'5+', dataIndex:'fiveMore', sortable:true},
		{header:'6+', dataIndex:'sixMore', sortable:true},
		{header:'7+', dataIndex:'sevenMore', sortable:true},
		{header:'8+', dataIndex:'eightMore', sortable:true},
		{header:'9+', dataIndex:'nineMore', sortable:true},
		{header:'10+', dataIndex:'teenMore', sortable:true},
	    {header:'生成时间', dataIndex:'updateTime', sortable:true}
	]);
	
	var viewsGrid_ds = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({url: '/myads/HTML/statistics/StatisticsMediaAction_getViewDataChanel.action'}),
		remoteSort: false,
		reader: new Ext.data.JsonReader(
			{
				totalProperty: 'total',
				idProperty:'id',
				root: 'invdata'
			},
			[
				{name: 'id'},
				{name: 'channelId'},
				{name: 'name'},
				{name: 'updateTime'},
				{name: 'onceMore'},
				{name: 'twiceMore'},
				{name: 'threeMore'},
				{name: 'fourthMore'},
				{name: 'fiveMore'},
				{name: 'sixMore'},
				{name: 'sevenMore'},
				{name: 'eightMore'},
				{name: 'nineMore'},
				{name: 'teenMore'}
			]
		)
	});
	
	var viewsGrid = new Ext.grid.GridPanel({
		region: 'center',
		ds: viewsGrid_ds,
		cm: viewsGrid_cm,
		viewConfig: {
		    forceFit: true
		},
		bbar: new Ext.PagingToolbar({
			pageSize: 20,
			store: viewsGrid_ds,
			displayInfo: true,
			displayMsg: '显示第{0}条到{1}条记录,一共{2}条',
			emptyMsg: '没有记录'			
		})
	});
	
	var dataPane = new Ext.TabPanel({
		region: 'center',
		items: [
			{
				id: 'dataTab',
				title: '数据列表',
				layout: 'fit',
				items: [dataGrid]	
			},
			{
				id: 'viewsab',
				title: '类电视数据',
				layout: 'fit',
				items: [viewsGrid]
			}
		]
	});
	
	dataPane.setActiveTab('dataTab');
	
	var mainpane = new Ext.Panel({
		region: 'center',
		layout: 'border',
		contentEl: 'datapane-div',
		frame: true,
		items:[
			chartPane, dataPane
		]
	});
	
	new Ext.Viewport({
		layout: 'border',
		items:[
		       searchpane,mainpane
		]
	});
	
});