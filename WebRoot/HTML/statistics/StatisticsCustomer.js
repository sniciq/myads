Ext.onReady(function() {
	
	// 广告活动
	var advactiveStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({url: '/myads/HTML/advflight/AdvActiveAction_getAdvactiveList.action'}),
		reader: new Ext.data.ArrayReader({}, [
			{name: 'value'},{name: 'text'}
		])
	});
	advactiveStore.load();
	  
	var advactiveCombobox = new Ext.form.ComboBox({
		fieldLabel: '广告活动',
		store: advactiveStore,
		mode: 'local',
		triggerAction: 'all',
		valueField: 'value',
		anchor : '99%',
		displayField: 'text',
		editable: false,
		listeners : {
			select : function(ComboBox, record, index) {
				advStore.removeAll(true);
				advCombobox.setValue();
				var value = record.data.value;
				advStore.load({params:{id:value}});
			}
		}
	});
	
	var advStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({url: '/myads/HTML/advflight/AdvertisementAction_getAdvertisementList.action'}),
		reader: new Ext.data.ArrayReader({}, [
			{name: 'value'},{name: 'text'}
		])
	});
	
	var advCombobox = new Ext.form.ComboBox({
		fieldLabel: '选择广告',
		store: advStore,
		mode: 'local',
		triggerAction: 'all',
		valueField: 'value',
		anchor : '99%',
		displayField: 'text',
		editable: false
	});
	
	var searchpane = new Ext.Panel({
		width: 320,
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
				items:[advactiveCombobox]
			},
			{
				layout:'form',
				width:300,
				labelAlign: 'right',
				labelWidth: 70,
				frame: true,
				items:[advCombobox]
			},
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
								columnWidth: .5, layout: 'form',width:70,labelWidth: 55,
								items: [
									new Ext.form.DateField({
										id:'td1',
										fieldLabel: '月报开始',
										name: 'stateTime',
//										plugins: 'monthPickerPlugin',  
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
								columnWidth: .5, layout: 'form',width:70,labelWidth: 55,
								items: [
									new Ext.form.DateField({
										id:'td1End',
										fieldLabel: '结束',
										name: 'stateTimeEnd',
//										plugins: 'monthPickerPlugin',  
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
				width:300,
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
			{
				layout: 'hbox',
				width:300,
				layoutConfig: {
                    padding:'5',
                    pack:'center',
                    align:'middle'
                },
                defaults:{margins:'0 5 0 0'},
				items: [
					new Ext.Button({
						text: '查看报表',
						width: 70,
						handler: function() {
							showReprot();
						}
					}),
					new Ext.Button({
						text: '重置',
						width: 70,
						handler: function() {
							advactiveCombobox.setValue('');
							advStore.removeAll(true);
							advCombobox.setValue();
							Ext.getCmp('td1').setValue(new Date());
							Ext.getCmp('td1End').setValue("");
							Ext.getCmp('td2').setValue("");
						}
					})
				]
			}
		]
	});
	
	function showReprot() {
		dataGrid.removeAll();
		var advertiseId = advCombobox.getValue();
		var advActiveId = advactiveCombobox.getValue();

		var statMonthTime = Ext.getCmp('td1').getRawValue();
		var statDayTime = Ext.getCmp('td2').getRawValue();
		
		var viewGridUrl;
		var viewGridPam;
		
		if(statMonthTime != '') {//月报
			var stateTimeEnd  = Ext.getCmp('td1End').getRawValue();
			if(advertiseId != '') {//广告
				dataGrid_ds.proxy = new Ext.data.HttpProxy({url:'/myads/HTML/statistics/StatisticsCustomerAction_getAdvertisementMonthData.action'});
				dataGrid_ds.load({params: {start:0, limit:200000, advertiseId: advertiseId, statTime: statMonthTime, stateTimeEnd: stateTimeEnd}});
				
				viewGridUrl = '/myads/HTML/statistics/StatisticsCustomerAction_getViewAdvertisement.action';
				viewGridPam = {start:0, limit:20, advertiseId: advertiseId, stateMonthTimeStart: statMonthTime, stateMonthTimeEnd: stateTimeEnd};
			}
			else if(advActiveId != '') {//广告活动
				dataGrid_ds.proxy = new Ext.data.HttpProxy({url:'/myads/HTML/statistics/StatisticsCustomerAction_getAdvActiveMonthData.action'});
				dataGrid_ds.load({params: {start:0, limit:200000, advActiveId: advActiveId, statTime: statMonthTime, stateTimeEnd: stateTimeEnd}});
				
				viewGridUrl = '/myads/HTML/statistics/StatisticsCustomerAction_getViewAdvActive.action';
				viewGridPam = {start:0, limit:20, advActiveId: advActiveId, stateMonthTimeStart: statMonthTime, stateMonthTimeEnd: stateTimeEnd};
			}
		}
		else if(statDayTime != '') {//日报
			if(advertiseId != '') {//广告
				dataGrid_ds.proxy = new Ext.data.HttpProxy({url:'/myads/HTML/statistics/StatisticsCustomerAction_getAdvertisementDailyData.action'});
				dataGrid_ds.load({params: {start:0, limit:200000, advertiseId: advertiseId, statTime: statDayTime}});
				
				viewGridUrl = '/myads/HTML/statistics/StatisticsCustomerAction_getViewAdvertisement.action';
				viewGridPam = {start:0, limit:20, advertiseId: advertiseId,stateDayTime: statDayTime};
			}
			else if(advActiveId != '') {//广告活动
				dataGrid_ds.proxy = new Ext.data.HttpProxy({url:'/myads/HTML/statistics/StatisticsCustomerAction_getAdvActiveDailyData.action'});
				dataGrid_ds.load({params: {start:0, limit:200000, advActiveId: advActiveId, statTime: statDayTime}});
				
				viewGridUrl = '/myads/HTML/statistics/StatisticsCustomerAction_getViewAdvActive.action';
				viewGridPam = {start:0, limit:20, advActiveId: advActiveId,stateDayTime: statDayTime};
			}
		}
		
		if(viewGridUrl) {
			viewsGrid_ds.proxy = new Ext.data.HttpProxy({url: viewGridUrl});
			viewsGrid_ds.load({params: viewGridPam});
		}
	}
	
	function doExport(strColoumnIndexs, strColoumnNames) {
		var advertiseId = advCombobox.getValue();
		var advActiveId = advactiveCombobox.getValue();
		
		var statMonthTime = Ext.getCmp('td1').getRawValue();
		var statDayTime = Ext.getCmp('td2').getRawValue();
		if(statMonthTime != '') {//导出月报
			var stateTimeEnd  = Ext.getCmp('td1End').getRawValue();
			if(advertiseId != '') {//广告
				window.location.href = '/myads/HTML/statistics/StatisticsCustomerAction_exportAdvertisementMonthData.action?exp_name=data.xls' 
							+ '&exp_column_names=' + encodeURI(encodeURI(strColoumnNames)) 
							+ '&exp_column_indexs=' + strColoumnIndexs 
							+ '&advertiseId=' + advertiseId
							+ '&statTime=' + statMonthTime
							+ '&stateTimeEnd=' + stateTimeEnd;
			}
			else if(advActiveId != '') {//广告活动
				window.location.href = '/myads/HTML/statistics/StatisticsCustomerAction_exportAdvActiveMonthData.action?exp_name=data.xls' 
							+ '&exp_column_names=' + encodeURI(encodeURI(strColoumnNames)) 
							+ '&exp_column_indexs=' + strColoumnIndexs 
							+ '&advActiveId=' + advActiveId
							+ '&statTime=' + statMonthTime
							+ '&stateTimeEnd=' + stateTimeEnd;
			}
		}
		else if(statDayTime != '') {//日报
			if(advertiseId != '') {//广告
				window.location.href = '/myads/HTML/statistics/StatisticsCustomerAction_exportAdvertisementDailyData.action?exp_name=data.xls' 
							+ '&advertiseId=' + advertiseId
							+ '&statTime=' + statDayTime; 
			}
			else if(advActiveId != '') {//广告活动
				window.location.href = '/myads/HTML/statistics/StatisticsCustomerAction_exportAdvActiveDailyData.action?exp_name=data.xls' 
							+ '&advActiveId=' + advActiveId
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
	    {header:'xField', dataIndex:'xField', sortable:true},
	    {header:'PV', dataIndex:'pv', sortable:true},
	    {header:'UV', dataIndex:'uv', sortable:true},
	    {header:'click', dataIndex:'click', sortable:true},
	    {header:'uc', dataIndex:'uc', sortable:true}
	]);
	
	var dataGrid_ds = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({url: '/myads/HTML/statistics/StatisticsCustomerAction_getAdvActiveMonthData.action'}),
		remoteSort: false,
		reader: new Ext.data.JsonReader(
			{
				totalProperty: 'total',
				idProperty:'id',
				root: 'invdata'
			},
			[
				{name: 'id'},
				{name: 'statTime'},
				{name: 'xField'},
				{name: 'name'},
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
			aobject.statTime =  records[i].data.statTime;
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
