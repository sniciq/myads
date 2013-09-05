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
										allowBlank	:	false,
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
										allowBlank	:	false,
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
	
	var cm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(),{
				header : '频道',
				dataIndex : 'channelName',
				sortable : true
			},{
				header : '版块',
				dataIndex : 'siteName',
				sortable : true
			}, {
				header : '广告条ID',
				dataIndex : 'advbarId',
				sortable : true
			}, {
				header : '广告条名称',
				dataIndex : 'advbarName',
				sortable : true
			}, {
				header : '日均曝光量',
				dataIndex : 'impression',
				sortable : true
			}, {
				header : '购买量',
				dataIndex : 'buy',
				sortable : true
			}, {
				header : '购买率',
				dataIndex : 'buyRate',
				sortable : true
			}, {
				header : '配送量',
				dataIndex : 'free',
				sortable : true
			},  {
				header : '配送率',
				dataIndex : 'freeRate',
				sortable : true
			}, {
				header : '补偿量',
				dataIndex : 'compensate',
				sortable : true
			}, {
				header : '补偿率',
				dataIndex : 'compensateRate',
				sortable : true
			}, {
				header : '软性量',
				dataIndex : 'pr',
				sortable : true
			}, {
				header : '软性率',
				dataIndex : 'prRate',
				sortable : true
			}, {
				header : '测试量',
				dataIndex : 'test',
				sortable : true
			}, {
				header : '测试率',
				dataIndex : 'testRate',
				sortable : true
			}, {
				header : '电商量',
				dataIndex : 'ecommerce',
				sortable : true
			}, {
				header : '电商率',
				dataIndex : 'ecommerceRate',
				sortable : true
			}, {
				header : '游戏量',
				dataIndex : 'game',
				sortable : true
			}, {
				header : '游戏率',
				dataIndex : 'gameRate',
				sortable : true
			}, {
				header : '置换量',
				dataIndex : 'replace',
				sortable : true
			}, {
				header : '置换率',
				dataIndex : 'replaceRate',
				sortable : true
			}, {
				header : 'BD推广量',
				dataIndex : 'bd',
				sortable : true
			}, {
				header : 'BD推广率',
				dataIndex : 'bdRate',
				sortable : true
			}, {
				header : '内部使用量',
				dataIndex : 'inuse',
				sortable : true
			}, {
				header : '内部使用率',
				dataIndex : 'inuseRate',
				sortable : true
			}, {
				header : '剩余量',
				dataIndex : 'leaving',
				sortable : true
			}, {
				header : '剩余率',
				dataIndex : 'leavingRate',
				sortable : true
			}, {
				header : '点击率',
				dataIndex : 'clickRate',
				sortable : true
			}
			]);
	
			var ds = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : '/myads/HTML/statistics/BookCountAction_showBookCountByDate.action'
						}),
				remoteSort : true,
				reader : new Ext.data.JsonReader({
							totalProperty : 'total',
							root : 'invdata'
						}, [	{	name : 'channelName'
								}, {
									name : 'siteName'
								}, {
									name : 'advbarId'
								}, {
									name : 'advbarName'
								}, {
									name : 'impression'
								}, {
									name : 'buy'
								}, {
									name : 'buyRate'
								}, {
									name : 'free'
								}, {
									name : 'freeRate'
								}, {
									name : 'compensate'
								}, {
									name : 'compensateRate'
								}, {
									name : 'pr'
								}, {
									name : 'prRate'
								}, {
									name : 'test'
								}, {
									name : 'testRate'
								}, {
									name : 'ecommerce'
								}, {
									name : 'ecommerceRate'
								}, {
									name : 'game'
								}, {
									name : 'gameRate'
								}, {
									name : 'replace'
								}, {
									name : 'replaceRate'
								}, {
									name : 'bd'
								}, {
									name : 'bdRate'
								}, {
									name : 'inuse'
								}, {
									name : 'inuseRate'
								}, {
									name : 'leaving'
								}, {
									name : 'leavingRate'
								}, {
									name : 'clickRate'
								}
								])
			});
			
		var grid = new Ext.grid.GridPanel({
			el : 'grid',
			region : 'center',
			ds : ds,
			cm : cm,
			viewConfig : {
				forceFit : false
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
	
	
