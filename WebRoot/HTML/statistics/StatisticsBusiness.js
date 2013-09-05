Ext.onReady(function() {
	// 频道
	var searchForm = new Ext.form.FormPanel({
		width: 320,
		labelWidth: 30,
		frame: true,
		labelAlign: 'right',
		items: [
			{//行1
				layout: 'column',
				items: [
					{
		            	columnWidth: .5, layout: 'form',
						items: [
							new Ext.form.DateField({
								id:'dt1',
								fieldLabel: 'start',
								name: 'startTime',
								editable : false,
								allowBlank:false,
								format:'Y-m-d',
								anchor : '98%',
								listeners:{
								  'select': function(){
								      Ext.getCmp('dt2').setMinValue(Ext.getCmp('dt1').getValue());
								   }
								} 
							})
						]
		            },
		            {
		            	columnWidth: .5, layout: 'form',
						items: [
							new Ext.form.DateField({
								id:'dt2',
								fieldLabel: 'end',
								minValue: '2010-12-12',
								editable : false,
								name: 'endTime',
								allowBlank:false,
								format:'Y-m-d',
								anchor : '98%'
							})
						]
		            }
				]
			},
			{
				layout: 'column',
				items: [
					{
						columnWidth: .5, layout: 'form',
						items: [
							{ xtype: 'textfield', name: 'id', fieldLabel: 'ID',anchor : '95%'}
						]
					},
					{
						columnWidth: .5, layout: 'form',
						items: [
							{ xtype: 'textfield', name: 'name', fieldLabel: 'name',anchor : '95%'}
						]
					}
				]
			}
		],
		buttons: [
			{
				text: '查询执行单',
				handler: function() {
					var fv = searchForm.getForm().getValues();
					projectGird_ds.baseParams= fv;
					projectGird_ds.load({params: {start:0, limit:20}});
				}
			},
			{
				text: '查询排期',
				handler: function() {
					var fv = searchForm.getForm().getValues();
					bookPackageGird_ds.baseParams= fv;
					bookPackageGird_ds.load({params: {start:0, limit:20}});
				}
			}
		]
	});
	
	// 执行单
	var projectGird_cm = new Ext.grid.ColumnModel([
	    {header:'执行单ID', dataIndex:'id', sortable:true},
   	    {header:'执行单名称', dataIndex:'name', sortable:true}
	]);
	
	var projectGird_ds = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({url: '/myads/HTML/statistics/StatisticsBusinessSearchAction_searchProject.action'}),
		remoteSort: false,
		reader: new Ext.data.JsonReader(
			{
				totalProperty: 'total',
				idProperty:'id',
				root: 'invdata'
			},
			[
				{name: 'id'},
   				{name: 'name'}
			]
		)
	});
	
	var projectGird = new Ext.grid.GridPanel({
		width: 320,
		height: 130,
		el: 'projectGird-div',
		ds: projectGird_ds,
		cm: projectGird_cm,
		frame: true,
	    viewConfig: {
	    	forceFit: true
	    },
	    bbar: new Ext.PagingToolbar({
		    pageSize: 20,
		    store: projectGird_ds,
			displayInfo: false
	    }),
	    listeners : {
            rowclick : function(grid, rowIndex, event) {
            	var row = grid.getSelectionModel().getSelected();
            	var projectId = row.data.id;
            	
            	projectBookPackageGrid_ds.baseParams = {projectId: projectId};
            	projectBookPackageGrid_ds.load({params: {start:0, limit:20}});
            }
		}
	});
	
	var projectBookPackageGrid_ds = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({url: '/myads/HTML/statistics/StatisticsBusinessSearchAction_searchProduct.action'}),
		remoteSort: false,
		reader: new Ext.data.JsonReader(
			{
				totalProperty: 'total',
				idProperty:'id',
				root: 'invdata'
			},
			[
				{name: 'id'},
				{name: 'projectId'},
				{name: 'advproductId'},
   				{name: 'bookpackageType'},
   				{name: 'advbarName'},
   				{name: 'advproductName'}
			]
		)
	});
	
	var projectBookPackageGrid_cm = new Ext.grid.ColumnModel([
	    {header:'排期包ID', dataIndex:'id', sortable:true},
   	    {header:'名称', dataIndex:'advproductName',sortable:true,  renderer: projectBookPackageName}
	]);
	
	function projectBookPackageName(value, cellmeta, record, rowIndex, columnIndex, stor) {
		if(record.data.bookpackageType == 1) {
			return '<a style="color: #CC44CC;cursor: pointer;">' + record.data.advbarName + '</a>';
		}
		else if(record.data.bookpackageType == 2) {
			return '<a style="color: #44CC44;cursor: pointer;">' + record.data.advproductName + '</a>';
		} 
	}
	
	var projectBookPackageGrid = new Ext.grid.GridPanel({
		width: 320,
		height: 120,
		ds: projectBookPackageGrid_ds,
		cm: projectBookPackageGrid_cm,
		frame: true,
		bbar: new Ext.PagingToolbar({
		    pageSize: 20,
		    store: projectBookPackageGrid_ds,
			displayInfo: false
	    }),
	    listeners : {
            rowclick : function(grid, rowIndex, event) {
            	var row = grid.getSelectionModel().getSelected();
            	var projectId = row.data.projectId;
            	
            	bookPackageAdvbarGird_ds.baseParams = {bookPackageId: row.data.id};
            	bookPackageAdvbarGird_ds.load({params: {start:0, limit:20}});
            }
	    }
	});
	
	//排期包
	var bookPackageAdvbarGird_cm = new Ext.grid.ColumnModel([
	    {header:'广告条ID', dataIndex:'advbarId', sortable:true},
   	    {header:'广告条名称', dataIndex:'advbarName', sortable:true}
	]);
	
	var bookPackageAdvbarGird_ds = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({url: '/myads/HTML/statistics/StatisticsBusinessSearchAction_searchBookPackageAdvbar.action'}),
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
   				{name: 'useTypeName'},
   				{name: 'bookpackageType'},
   				{name: 'advbarId'},
   				{name: 'advbarName'},
   				{name: 'saleTypeName'}
			]
		)
	});
	
	var bookPackageAdvbarGird = new Ext.grid.GridPanel({
		width: 320,
		height: 120,
		el: 'bookPackageGird-div',
		ds: bookPackageAdvbarGird_ds,
		cm: bookPackageAdvbarGird_cm,
		frame: true,
	    viewConfig: {
	    	forceFit: true
	    },
	    bbar: new Ext.PagingToolbar({
		    pageSize: 20,
		    store: bookPackageAdvbarGird_ds,
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
			searchForm,
			projectGird,
			projectBookPackageGrid,
//			bookPackageAdvbarGird,
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
					{
						layout: 'column',
						items: [
							{
								columnWidth: .5, layout: 'form',width:70,labelWidth: 55,
								items: [
									new Ext.form.DateField({
										id:'td2',
										fieldLabel: '日报开始',
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
							}
						]
					}
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
							searchForm.getForm().reset();
							projectGird_ds.removeAll();
							bookPackageGird_ds.removeAll();
							Ext.getCmp('td1').setValue(new Date());
							Ext.getCmp('td2').setValue("");
						}
					})
				]
			}
		]
	});
	
	function showReprot() {
		var statMonthTime = Ext.getCmp('td1').getRawValue();
		var statDayTime = Ext.getCmp('td2').getRawValue();
//		
		var viewGridUrl;
		var viewGridPam;
		
		if(statMonthTime != '') {//月报
			var projectGirdRecord = projectGird.getSelectionModel().getSelected();
			var bookPackageGirdRecord = projectBookPackageGrid.getSelectionModel().getSelected();
			
			var stateTimeMonthEnd = Ext.getCmp('td1End').getRawValue();
			
			if(bookPackageGirdRecord) {//排期包
				if(bookPackageGirdRecord.data.bookpackageType == 1) {
					dataGrid_ds.proxy = new Ext.data.HttpProxy({url:'/myads/HTML/statistics/StatisticsBusinessAction_getBookpackageMonthData.action'});
					dataGrid_ds.load({params: {start:0, limit:200000, bookPackageId: bookPackageGirdRecord.data.id, statTime: statMonthTime, stateTimeMonthEnd: stateTimeMonthEnd}});
					
					viewGridUrl = '/myads/HTML/statistics/StatisticsBusinessAction_getViewDataBookpackage.action';
					viewGridPam = {start:0, limit:20, bookPackageId: bookPackageGirdRecord.data.id, stateMonthTimeStart: statMonthTime, stateMonthTimeEnd: stateTimeMonthEnd, bookpackageType: bookPackageGirdRecord.data.bookpackageType};
				}
				else if(bookPackageGirdRecord.data.bookpackageType == 2) {
					dataGrid_ds.proxy = new Ext.data.HttpProxy({url:'/myads/HTML/statistics/StatisticsBusinessAction_getProductBPMonthData.action'});
					dataGrid_ds.load({params: {start:0, limit:200000, productId: bookPackageGirdRecord.data.advproductId, statTime: statMonthTime, stateTimeMonthEnd: stateTimeMonthEnd}});
					
					viewGridUrl = '/myads/HTML/statistics/StatisticsBusinessAction_getProductBPMonthViewData.action';
					viewGridPam = {start:0, limit:20, productId: bookPackageGirdRecord.data.advproductId, stateMonthTimeStart: statMonthTime, stateMonthTimeEnd: stateTimeMonthEnd, bookpackageType: bookPackageGirdRecord.data.bookpackageType};
				}
			}
			else if(projectGirdRecord) {//执行单
				dataGrid_ds.proxy = new Ext.data.HttpProxy({url:'/myads/HTML/statistics/StatisticsBusinessAction_getProjectMonthData.action'});
				dataGrid_ds.load({params: {start:0, limit:200000, projectId: projectGirdRecord.data.id, statTime: statMonthTime, stateTimeMonthEnd: stateTimeMonthEnd}});
				
				viewGridUrl = '/myads/HTML/statistics/StatisticsBusinessAction_getViewDataProject.action';
				viewGridPam = {start:0, limit:20, projectId: projectGirdRecord.data.id, stateMonthTimeStart: statMonthTime, stateMonthTimeEnd: stateTimeMonthEnd};
			}
		}
		else if(statDayTime != '') {//日报
			var projectGirdRecord = projectGird.getSelectionModel().getSelected();
			var bookPackageGirdRecord = projectBookPackageGrid.getSelectionModel().getSelected();
			if(bookPackageGirdRecord) {//排期包
				if(bookPackageGirdRecord.data.bookpackageType == 1) {
					dataGrid_ds.proxy = new Ext.data.HttpProxy({url:'/myads/HTML/statistics/StatisticsBusinessAction_getBookPackageDayData.action'});
					dataGrid_ds.load({params: {start:0, limit:200000, bookPackageId: bookPackageGirdRecord.data.id, statTime: statDayTime}});
					
					viewGridUrl = '/myads/HTML/statistics/StatisticsBusinessAction_getViewDataBookpackage.action';
					viewGridPam = {start:0, limit:20, bookPackageId: bookPackageGirdRecord.data.id, stateDayTime: statDayTime};
				}
				else if(bookPackageGirdRecord.data.bookpackageType == 2) {
					dataGrid_ds.proxy = new Ext.data.HttpProxy({url:'/myads/HTML/statistics/StatisticsBusinessAction_getProductBPDayData.action'});
					dataGrid_ds.load({params: {start:0, limit:200000, productId: bookPackageGirdRecord.data.advproductId, statTime: statDayTime}});
					
					viewGridUrl = '/myads/HTML/statistics/StatisticsBusinessAction_getProductBPMonthViewData.action';
					viewGridPam = {start:0, limit:20, productId: bookPackageGirdRecord.data.advproductId, stateDayTime: statDayTime};
				}
			}
			else if(projectGirdRecord) {//执行单
				dataGrid_ds.proxy = new Ext.data.HttpProxy({url:'/myads/HTML/statistics/StatisticsBusinessAction_getProjectDayData.action'});
				dataGrid_ds.load({params: {start:0, limit:200000, projectId: projectGirdRecord.data.id, statTime: statDayTime}});
				
				viewGridUrl = '/myads/HTML/statistics/StatisticsBusinessAction_getViewDataProject.action';
				viewGridPam = {start:0, limit:20, projectId: projectGirdRecord.data.id, stateDayTime: statDayTime};
			}
		}
		
		if(viewGridUrl) {
			viewsGrid_ds.proxy = new Ext.data.HttpProxy({url: viewGridUrl});
			viewsGrid_ds.load({params: viewGridPam});
		}
	}
	
	function doExport(strColoumnIndexs, strColoumnNames) {
		var statMonthTime = Ext.getCmp('td1').getRawValue();
		var statDayTime = Ext.getCmp('td2').getRawValue();
		if(statMonthTime != '') {//导出月报
			var stateTimeMonthEnd = Ext.getCmp('td1End').getRawValue();
			var projectGirdRecord = projectGird.getSelectionModel().getSelected();
			var bookPackageGirdRecord = projectBookPackageGrid.getSelectionModel().getSelected();
			if(bookPackageGirdRecord) {//排期包
				if(bookPackageGirdRecord.data.bookpackageType == 1) {
					window.location.href = '/myads/HTML/statistics/StatisticsBusinessAction_exportBookpackageMonthData.action?exp_name=data.xls' 
							+ '&exp_column_names=' + encodeURI(encodeURI(strColoumnNames)) 
							+ '&exp_column_indexs=' + strColoumnIndexs 
							+ '&bookPackageId=' + bookPackageGirdRecord.data.id
							+ '&stateTime=' + statMonthTime
							+ '&stateTimeMonthEnd=' + stateTimeMonthEnd;
				}
				else if(bookPackageGirdRecord.data.bookpackageType == 2) {
					window.location.href = '/myads/HTML/statistics/StatisticsBusinessAction_exportProductBPMonthData.action?exp_name=data.xls' 
							+ '&exp_column_names=' + encodeURI(encodeURI(strColoumnNames)) 
							+ '&exp_column_indexs=' + strColoumnIndexs 
							+ '&productId=' + bookPackageGirdRecord.data.advproductId
							+ '&stateTime=' + statMonthTime
							+ '&stateTimeMonthEnd=' + stateTimeMonthEnd;
				}
			}
			else if(projectGirdRecord) {
				window.location.href = '/myads/HTML/statistics/StatisticsBusinessAction_exportProjectMonthData.action?exp_name=data.xls' 
							+ '&exp_column_names=' + encodeURI(encodeURI(strColoumnNames)) 
							+ '&exp_column_indexs=' + strColoumnIndexs 
							+ '&projectId=' + projectGirdRecord.data.id
							+ '&statTime=' + statMonthTime
							+ '&stateTimeMonthEnd=' + stateTimeMonthEnd;
			}
		}
		else if(statDayTime != '') {//日报
			var projectGirdRecord = projectGird.getSelectionModel().getSelected();
			var bookPackageGirdRecord = projectBookPackageGrid.getSelectionModel().getSelected();
			if(bookPackageGirdRecord) {//排期包
				if(bookPackageGirdRecord.data.bookpackageType == 1) {
					window.location.href = '/myads/HTML/statistics/StatisticsBusinessAction_exportBookpackageDayData.action?exp_name=data.xls' 
							+ '&bookpackageId=' + bookPackageGirdRecord.data.id
							+ '&statTime=' + statDayTime; 
				}
				else if(bookPackageGirdRecord.data.bookpackageType == 2) {
					window.location.href = '/myads/HTML/statistics/StatisticsBusinessAction_exportProductBPDayData.action?exp_name=data.xls' 
							+ '&productId=' + bookPackageGirdRecord.data.advproductId
							+ '&exp_column_names=' + encodeURI(encodeURI(strColoumnNames)) 
							+ '&exp_column_indexs=' + strColoumnIndexs 
							+ '&statTime=' + statDayTime; 
				}
			}
			else if(projectGirdRecord) {//执行单
				window.location.href = '/myads/HTML/statistics/StatisticsBusinessAction_exportProjectDayData.action?exp_name=data.xls' 
							+ '&projectId=' + projectGirdRecord.data.id
							+ '&statTime=' + statDayTime; 
			}
		}
	}
	
	var chartStore = new Ext.data.JsonStore({
		fields: ['xField', 'pv', 'uv']
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