Ext.onReady(function() {
	//维护对话框---------------开始---------------------------------------------
	var maintainWin;
	var maintainForm;
	var maintainGrid;
	var maintainGrid_ds;
	
	function maintainGridOp(value, cellmeta, record, rowIndex, columnIndex, stor) {
		var id = record.data.id;
		var delInfo = record.data.startTime + ' ' + record.data.endTime + ' ' + record.data.buyType; 
		var delStr = '<img alt=\"删除\" src=\"/myads/HTML/images/del.gif\" style=\"cursor: pointer;\" onclick=\"deletemaintain(\''+id+'\', \'' + delInfo + '\');\">';
		return delStr;
	}
	
	window.deletemaintain = function(id, delInfo) {
		Ext.MessageBox.confirm('提示', '确定删除  ' + delInfo + ' ？', function(btn) {
			if(btn == 'yes') {
				Ext.Ajax.request({
					method: 'post',
					url: '/myads/HTML/webdata/Movies/WebdataMoviesAction_deleteMovieMaintain.action',
					params: {id:id},
				   	success:function(resp){
				   		var obj=Ext.util.JSON.decode(resp.responseText);
				      	if(obj.result == 'success') {
				      		maintainGrid_ds.reload();
				      		Ext.MessageBox.alert('提示', '删除成功！');
				      		grid.getStore().reload();
				      	}
				      	else {
				      		Ext.MessageBox.alert('报错了！！！', '删除失败！！！');
				      		grid.getStore().reload();
				      	}
				   	}
				});
			}
		});
	}
			
	function showMaintainWin(movieId) {
		if(!maintainWin) {
			maintainForm = new Ext.form.FormPanel({
				labelAlign: 'right',
				region: 'north',
				labelWidth: 75,
				frame: true,
				split: true,
				height: 100,
				items: [
					{
						items: [
		            	        { xtype: 'hidden', id:'movieId', name: 'movieId', hidden:true, hiddenLabel:true}
		            	] 
					},
					{//行1
						layout: 'column',
						items: [
							 {
				            	columnWidth: .5, layout: 'form',
								items: [
									new Ext.form.DateField({
										id:'dt1',
										fieldLabel: '开始日期',
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
										fieldLabel: '结束日期',
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
					}
					,
					{
						layout: 'column',
						items:[
							 {
				            	columnWidth: .5, layout: 'form',
								items: [
									new Ext.form.ComboBox({
				        	       		fieldLabel: '购买方式',
										hiddenName: 'buyType',
										mode: 'local',
										triggerAction: 'all', 
										valueField: 'value',
										editable : false,
										allowBlank:false,
										blankText: '此项不能为空!',
										displayField: 'text',
										anchor : '98%',
										store: new Ext.data.SimpleStore({
											fields: ['value', 'text'],
											data: [['部份购买', '部份购买'],['包段', '包段']]
										})
				        	       })
								]
				            },
				            {	
								columnWidth: .5, layout: 'form',
				            	items: [
				            	        { xtype: 'textfield', name: 'customerName', fieldLabel: '客户 ',anchor : '95%'}
				            	]
				            }
						]
					}
				],
				buttons: [
					{
						text:'保存',
						handler: function() {
							maintainForm.form.doAction('submit', {
								url: '/myads/HTML/webdata/Movies/WebdataMoviesAction_saveMovieMaintain.action',
								method: 'post',
								params: '',
								success: function(form, action) {
									if(action.result.result == 'success') {
										Ext.MessageBox.alert('结果', '保存成功！');
										var loadMovieId = maintainForm.getForm().findField("movieId").getValue();
										form.reset();
										maintainForm.getForm().findField("movieId").setValue(loadMovieId)
										maintainGrid_ds.baseParams = {movieId:loadMovieId};
										maintainGrid_ds.load({params: {start:0, limit:20}});
									}
									else {
										Ext.MessageBox.alert('错误', '保存失败！<br>' + action.result.info);
									}
								}
							});
						}
					},
					{
						text:'重置',
						handler: function() {
							var movieId = maintainForm.getForm().findField("movieId").getValue();
							maintainForm.form.reset();
							maintainForm.getForm().findField("movieId").setValue(movieId);
						}
					}
				]
			});
			
			var maintainGrid_sm = new Ext.grid.CheckboxSelectionModel();
			var maintainGrid_cm = new Ext.grid.ColumnModel([
			    {header:'开始时间', dataIndex:'startTime', sortable:true},
			    {header:'结束时间', dataIndex:'endTime', sortable:true},
			    {header:'购买方式', dataIndex:'buyType', sortable:true},
			    {header:'客户', dataIndex:'customerName', sortable:true},
			    {header:'操作', dataIndex:'op', renderer: maintainGridOp, width:100, align:'left'}
			]);
			
			maintainGrid_ds = new Ext.data.Store({
				proxy: new Ext.data.HttpProxy({url: '/myads/HTML/webdata/Movies/WebdataMoviesAction_getMovieMaintainList.action'}),
				remoteSort: false,
				reader: new Ext.data.JsonReader(
					{
						totalProperty: 'total',
						idProperty:'id',
						root: 'invdata'
					},
					[
						{name: 'id'},
						{name: 'startTime'},
						{name: 'endTime'},
						{name: 'movieId'},
						{name: 'buyType'},
						{name: 'customerName'}
					]
				)
			});
		
			maintainGrid = new Ext.grid.GridPanel({
				region: 'center',
				ds: maintainGrid_ds,
				cm: maintainGrid_cm,
			    viewConfig: {
			    	forceFit: true
			    },
			    bbar: new Ext.PagingToolbar({
				    pageSize: 20,
				    store: maintainGrid_ds,
					displayInfo: true,
					displayMsg: '显示第{0}条到{1}条记录,一共{2}条',
					emptyMsg: '没有记录'
			    })
			});
			
			maintainWin = new Ext.Window({
		    	title: '维护',
		        layout:'fit',
		        width:600,
		    	height:400,
		        closeAction:'hide',
		        plain: true,
		        layout: 'border',
		        items: [maintainForm, maintainGrid]
		    });
		}
		maintainWin.show();
		maintainForm.getForm().findField("movieId").setValue(movieId);
		maintainGrid_ds.baseParams = {movieId:movieId};
		maintainGrid_ds.load({params: {start:0, limit:20}});
	}
	//维护对话框---------------结束---------------------------------------------
	
	//销售工具对话框---------------开始---------------------------------------------
	var sellStateWin;
	var sellStateForm;
	var sellStateGrid;
	var sellStateGrid_ds;
	
	/*
	function sellStateGridOp(value, cellmeta, record, rowIndex, columnIndex, stor) {
		var id = record.data.id;
		var delInfo = record.data.startTime + ' ' + record.data.endTime + ' ' + record.data.sellType; 
		var delStr = '<img alt=\"删除\" src=\"/myads/HTML/images/del.gif\" style=\"cursor: pointer;\" onclick=\"deleteSellState(\''+id+'\', \'' + delInfo + '\');\">';
		return delStr;
	}
	
	window.deleteSellState = function(id, delInfo) {
		Ext.MessageBox.confirm('提示', '确定删除  ' + delInfo + ' ？', function(btn) {
			if(btn == 'yes') {
				Ext.Ajax.request({
					method: 'post',
					url: '/myads/HTML/webdata/Movies/WebdataMoviesAction_deleteMovieSellstate.action',
					params: {id:id},
				   	success:function(resp){
				   		var obj=Ext.util.JSON.decode(resp.responseText);
				      	if(obj.result == 'success') {
				      		sellStateGrid_ds.reload();
				      		Ext.MessageBox.alert('提示', '删除成功！');
				      	}
				      	else {
				      		Ext.MessageBox.alert('报错了！！！', '删除失败！！！');
				      	}
				   	}
				});
			}
		})
	}
	*/

	function showSellStateWin(movieId) {
		if(!sellStateWin) {
			sellStateForm = new Ext.form.FormPanel({
				labelAlign: 'right',
				region: 'north',
				labelWidth: 75,
				frame: true,
				split: true,
				height: 100,
				items: [
					{
						items: [
		            	        { xtype: 'hidden', id:'movieId', name: 'movieId', hidden:true, hiddenLabel:true}
		            	] 
					},
					{//行1
						layout: 'column',
						items: [
							 {
				            	columnWidth: .5, layout: 'form',
								items: [
									new Ext.form.DateField({
										fieldLabel: '开始日期',
										name: 'startTime',
										editable : false,
										allowBlank:true,
										format:'Y-m-d',
										anchor : '98%'
									})
								]
				            },
				            {
				            	columnWidth: .5, layout: 'form',
								items: [
									new Ext.form.DateField({
										fieldLabel: '结束日期',
										editable : false,
										name: 'endTime',
										allowBlank:true,
										format:'Y-m-d',
										anchor : '98%'
									})
								]
				            }
						]
					}
					,
					{
						layout: 'column',
						items:[
							 {
				            	columnWidth: .5, layout: 'form',
								items: [
									new Ext.form.ComboBox({
				        	       		fieldLabel: '购买方式',
										hiddenName: 'buyType',
										mode: 'local',
										triggerAction: 'all', 
										valueField: 'value',
										editable : false,
										allowBlank:true,
										blankText: '此项不能为空!',
										displayField: 'text',
										anchor : '98%',
										store: new Ext.data.SimpleStore({
											fields: ['value', 'text'],
											data: [['部份购买', '部份购买'],['包段', '包段']]
										})
				        	       })
								]
				            },
				            {	
								columnWidth: .5, layout: 'form',
				            	items: [
				            	        { xtype: 'textfield', name: 'customerName', fieldLabel: '客户 ',anchor : '95%'}
				            	]
				            }
						]
					}
				],
				buttons: [
					{
						text:'查询',
						handler: function() {
							var movieId = sellStateForm.getForm().findField("movieId").getValue();
							var fv = sellStateForm.getForm().getValues();
							sellStateGrid_ds.baseParams= fv;
							sellStateGrid_ds.load({params: {start:0, limit:20, movieId: movieId}});
							
//							sellStateForm.form.doAction('submit', {
//								url: '/myads/HTML/webdata/Movies/WebdataMoviesAction_saveMovieSellstate.action',
//								method: 'post',
//								params: '',
//								success: function(form, action) {
//									if(action.result.result == 'success') {
//										Ext.MessageBox.alert('结果', '保存成功！');
//										var loadMovieId = sellStateForm.getForm().findField("movieId").getValue();
//										form.reset();
//										sellStateForm.getForm().findField("movieId").setValue(loadMovieId)
//										sellStateGrid_ds.baseParams = {movieId:loadMovieId};
//										sellStateGrid_ds.load({params: {start:0, limit:20}});
//									}
//									else {
//										Ext.MessageBox.alert('错误', '保存失败！');
//									}
//								}
//							});
						}
					},
					{
						text:'重置',
						handler: function() {
							var movieId = sellStateForm.getForm().findField("movieId").getValue();
							sellStateForm.form.reset();
							sellStateForm.getForm().findField("movieId").setValue(movieId);
						}
					}
				]
			});
			
			var sellStateGrid_sm = new Ext.grid.CheckboxSelectionModel();
			var sellStateGrid_cm = new Ext.grid.ColumnModel([
			    {header:'开始时间', dataIndex:'startTime', sortable:true},
			    {header:'结束时间', dataIndex:'endTime', sortable:true},
			    {header:'购买方式', dataIndex:'buyType', sortable:true},
			    {header:'客户', dataIndex:'customerName', sortable:true}
//			    {header:'操作', dataIndex:'op', renderer: sellStateGridOp, width:100, align:'left'}
			]);
			
			sellStateGrid_ds = new Ext.data.Store({
				proxy: new Ext.data.HttpProxy({url: '/myads/HTML/webdata/Movies/WebdataMoviesAction_getMovieMaintainList.action'}),
				remoteSort: false,
				reader: new Ext.data.JsonReader(
					{
						totalProperty: 'total',
						idProperty:'id',
						root: 'invdata'
					},
					[
						{name: 'id'},
						{name: 'startTime'},
						{name: 'endTime'},
						{name: 'movieId'},
						{name: 'buyType'},
						{name: 'customerName'}
					]
				)
			});
		
			var sellStateGrid = new Ext.grid.GridPanel({
				region: 'center',
				ds: sellStateGrid_ds,
				cm: sellStateGrid_cm,
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
			
			sellStateWin = new Ext.Window({
		    	title: '销售工具',
		        layout:'fit',
		        width:600,
		    	height:400,
		        closeAction:'hide',
		        plain: true,
		        layout: 'border',
		        items: [sellStateForm, sellStateGrid]
		    });
		}
		sellStateWin.show();
		sellStateForm.getForm().findField("movieId").setValue(movieId);
		sellStateGrid_ds.baseParams = {movieId:movieId};
		sellStateGrid_ds.load({params: {start:0, limit:20}});
	}
	//销售工具对话框---------------结束---------------------------------------------
	
	var searchForm = new Ext.form.FormPanel({
		labelAlign: 'right',
		labelWidth: 70,
		region: 'north',
		frame: true,
		items: [
			{//行1
				layout: 'column',
				items: [
					{
						columnWidth: .20, layout: 'form',
						items: [
							new Ext.form.DateField({
								fieldLabel: '开始日期',
								name: 'startTime',
								allowBlank:true,
								editable : false,
								anchor : '95%',
								format:'Y-m-d'
							})
						]
					},
					{
						columnWidth: .20, layout: 'form',
						items: [
							new Ext.form.DateField({
								fieldLabel: '结束日期',
								name: 'endTime',
								allowBlank:true,
								editable : false,
								anchor : '95%',
								format:'Y-m-d'
							})
						]
					},
					{
						columnWidth: .25, layout: 'form',
		            	items: [
		            		new Ext.form.ComboBox({
	            	       		fieldLabel: '购买方式',
								name: 'buyType',
								mode: 'local',
								editable : false,
								triggerAction: 'all', 
								valueField: 'value',
								displayField: 'text',
								store: new Ext.data.SimpleStore({
									fields: ['value', 'text'],
									data: [['部份购买', '部份购买'],['包段', '包段']]
								})
	            	       })
		            	]
					},
					{	
						columnWidth: .35, layout: 'form',
		            	items: [
		            	        { xtype: 'textfield', name: 'name', fieldLabel: '名称 ',anchor : '95%'}
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
		{header:'名称', dataIndex:'name', sortable:true},
		{header:'开始时间', dataIndex:'startTime', sortable:true},
		{header:'结束时间', dataIndex:'endTime', sortable:true},
		{header:'客户', dataIndex:'customerName', sortable:true},
		{header:'购买方式', dataIndex:'buyType', sortable:true},
	    {header:'类型', dataIndex:'type', sortable:true},
	    {header:'类别', dataIndex:'classType', sortable:true},
	    {header:'产地', dataIndex:'productionPlace', sortable:true},
	    {header:'级别', dataIndex:'level', sortable:true},
	    {header:'播出安排', dataIndex:'playPlan', sortable:true},
	    {header:'售卖期限', dataIndex:'sellLength', sortable:true},
	    {header:'导演', dataIndex:'director', sortable:true},
	    {header:'主演', dataIndex:'mainActor', sortable:true},
	    {header:'是否排他', dataIndex:'exclusive', sortable:true},
	    {header:'预估流量', dataIndex:'expectedFlow', sortable:true},
//	    {header:'售卖状态', dataIndex:'canSell', sortable:true},
	    {header:'简介', dataIndex:'introduction', sortable:true},
	    {header:'操作', dataIndex:'id', renderer: renderOp, width:120, align:'left'}
	]);
	
	function renderOp(value, cellmeta, record, rowIndex, columnIndex, stor) {
		var id = record.data.id;
		var dataValue = record.data.videoName; 
		var editStr = '<a href="#" style="cursor: pointer;" onclick="maintain(\''+id+'\');\">维护</a>';
		return editStr;
	}
	
	window.maintain = function(id) {
		showMaintainWin(id);
	};
	
	window.showSellState = function(id) {
		showSellStateWin(id);
	};
	
	var ds = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({url: '/myads/HTML/webdata/Movies/WebdataMoviesAction_search.action'}),
		remoteSort: true,
		reader: new Ext.data.JsonReader(
			{
				totalProperty: 'total',
				idProperty:'displayId',
				root: 'invdata'
			},
			[
				{name: 'displayId'},
				{name: 'id'},
				{name: 'name'},
				{name: 'buyType'},
				{name: 'customerName'},
				{name: 'startTime'},
				{name: 'endTime'},
				{name: 'type'},
				{name: 'fitScene'},
				{name: 'classType'},
				{name: 'productionPlace'},
				{name: 'level'},
				{name: 'playPlan'},
				{name: 'sellLength'},
				{name: 'director'},
				{name: 'mainActor'},
				{name: 'exclusive'},
				{name: 'expectedFlow'},
				{name: 'introduction'},
				{name: 'canSell'}
			]
		)
	});

	ds.load({params: {start:0, limit:20}});
	
	var grid = new Ext.grid.GridPanel({
		el: 'grid',
		region: 'center',
		ds: ds,
		cm: cm,
//	    viewConfig: {
//	    	forceFit: true
//	    },
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
   						window.location.href = '/myads/HTML/webdata/Movies/WebdataMoviesAction_export.action?exp_name=films.xls&exp_column_names=' + encodeURI(encodeURI(strColoumnNames)) 
   							+ '&exp_column_indexs=' + strColoumnIndexs 
   							+ '&' + encodeURI(searchForm.getForm().getValues(true)); 
   					}
   				}
   			]
	    })
	});
	
	grid.addListener('rowdblclick', function(grid, rowindex, e) {
		var record = grid.getStore().getAt(rowindex);
		BaseDataForm.load({
			url: '/myads/HTML/basic/BaseDataAction_getBaseDataDetail.action',
			params: {baseDataId: record.id}
		});
	});

	grid.render();
	
	var mainViewPort = new Ext.Viewport({
		layout: 'border',
		items:[
		       searchForm, grid
		]
	});
});