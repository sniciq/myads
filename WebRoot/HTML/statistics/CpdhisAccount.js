Ext.onReady(function() {
	
	var searchpane = new Ext.form.FormPanel({
		labelAlign : 'right',
		region: 'north',
		labelWidth : 70,
		frame : true,
		xtype : 'fieldset',
		items : [{
					layout : 'column',
					items : [{
								columnWidth: .25, 
								layout: 'form',
								items: 
									[new Ext.form.DateField({
										id:'dt1',
										fieldLabel: '开始日期',
										name: 'startTime',
										editable : false,
										allowBlank:false,
										format:'Y-m-d',
										anchor : '95%',
										listeners:{
										  'select': function(){
										      Ext.getCmp('dt2').setMinValue(Ext.getCmp('dt1').getValue());
										   }
										} 
									})]
								},{
									columnWidth: .25, 
									layout: 'form',
				            		items: 
				            		[new Ext.form.DateField({
										id:'dt2',
										fieldLabel: '结束日期',
										minValue: '2010-12-12',
										editable : false,
										name: 'endTime',
										maxValue : new Date(), 
										allowBlank:false,
										format:'Y-m-d',
										anchor : '95%'
									})]
								},new Ext.Button({
									text : '查询',
									width : 70,
									handler : function() {
										var dt1 =  Ext.getCmp('dt1').value;
										var dt2 = Ext.getCmp('dt2').value;
										if(dt1 == null || dt2 == null)
										{
											alert("必须输入开始日期和结束日期!");
										}
										else
										{
											var fv = searchpane.getForm().getValues();
											ds.baseParams = fv;
											ds.load({params : {start : 0,limit : 15}});
										}
								}
							})]
					}]
				})
	
	searchpane.render('searchpane');
	
	var cm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(), {
				header : '广告条ID',
				dataIndex : 'advbarId',
				sortable : true
			}, {
				header : '广告条名称',
				dataIndex : 'advbarName',
				sortable : true
			}, {
				header : '频道',
				dataIndex : 'channelName',
				sortable : true
			}, {
				header : '广告位',
				dataIndex : 'advpositionName',
				sortable : true
			}, {
				header : '价格',
				dataIndex : 'price',
				sortable : true
			}, {
				header : '广告条容量',
				dataIndex : 'barContent',
				sortable : true
			},  {
				header : '广告执行量',
				dataIndex : 'advexecute',
				sortable : true
			}, {
				header : '广告执行率',
				dataIndex : 'advexecuteRate',
				sortable : true
			}, {
				header : '未执行预订量',
				dataIndex : 'advUnexecute',
				sortable : true
			}, {
				header : '未执行预订率',
				dataIndex : 'advUnexecuteRate',
				sortable : true
			}, {
				header : '执行预订量',
				dataIndex : 'execute',
				sortable : true
			}, {
				header : '执行预订率',
				dataIndex : 'executeRate',
				sortable : true
			}
			]);
	
			var ds = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : '/myads/HTML/statistics/CpdhisAccountAction_showHisCpdByDate.action'
						}),
				remoteSort : true,
				reader : new Ext.data.JsonReader({
							totalProperty : 'total',
							root : 'invdata'
						}, [{
									name : 'advbarId'
								}, {
									name : 'advbarName'
								}, {
									name : 'channelName'
								}, {
									name : 'advpositionName'
								}, {
									name : 'price'
								}, {
									name : 'barContent'
								}, {
									name : 'advexecute'
								}, {
									name : 'advexecuteRate'
								}, {
									name : 'advUnexecute'
								}, {
									name : 'advUnexecuteRate'
								}, {
									name : 'execute'
								}, {
									name : 'executeRate'
								}
								])
			});
			
		var grid = new Ext.grid.GridPanel({
			el : 'grid',
			region : 'center',
			ds : ds,
			cm : cm,
			viewConfig : {
				forceFit : true
			},
			bbar : new Ext.PagingToolbar({
				pageSize : 50,
				store : ds,
				displayInfo : true,
				displayMsg : '显示第{0}条到{1}条记录,一共{2}条',
				emptyMsg : '没有记录'
			})
		});
		
		grid.render();
	
		new Ext.Viewport({
			layout: 'border',
			items:[
			       searchpane,grid
			]
		});
		
	});
	
	
