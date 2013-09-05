Ext.onReady(function() {	
	Ext.QuickTips.init();
	var projectId = Ext.get('projectId').dom.value;
	var param_priority = Ext.get('priority').dom.value;
	var canUpdatePriority = Ext.get('canUpdatePriority').dom.value;
	var bookPackageId_global = null;
	
	var selectChannelId_global = null;
	var selectAdvbarId_global = null;

	//选择位置--------------------------开始-----------------------
    var locationForm;
    var siteStore;
    var channelStore;
    var channelGrid;
    var advbarStore;
    var advbarGrid;
    var saleTypeCombStore;
    var saleTypeComb;
    var priceIdComb;
    var priceFormatCombStore;
    var useTypeCombStore;
    //网站
    siteStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({url: '/myads/HTML/sysfun/SiteAction_getSiteList.action'}),
		reader: new Ext.data.ArrayReader({}, [{name: 'siteId'},{name: 'siteName'}])
	});
	siteStore.load();
    // 频道
	channelStore = new Ext.data.ArrayStore({
		fields : [{name: 'channelId'},{name: 'name'}]
	});
      
      // 广告条
	advbarStore = new Ext.data.ArrayStore({
	      fields: [{name: 'advbarId'},{name: 'advbarName'}, {name: 'isBackground'}]
	});
      
    // 售卖方式
	saleTypeCombStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({url: '/myads/HTML/basic/OptionsAction_getAdvbarSaleTypes.action'}),
		reader: new Ext.data.ArrayReader({}, [
			{name: 'value'},{name: 'text'}
		])
	});
	  
	  // 售卖方式对应的价格
	priceFormatCombStore = new Ext.data.ArrayStore({
		proxy: new Ext.data.HttpProxy({url: '/myads/HTML/basic/OptionsAction_getAdvbarSaleTypeFormat.action'}),
		fields: [{name: 'value'},{name: 'text'}, {name: 'saleTypeBaseData'}]
	});
	  
	useTypeCombStore = new Ext.data.Store({
			proxy: new Ext.data.HttpProxy({url: '/myads/HTML/basic/OptionsAction_getBaseDataNamesByType.action'}),
			reader: new Ext.data.ArrayReader({}, [{name: 'value'},{name: 'text'}])
	});
	useTypeCombStore.load({params: {dataType: 'usetype'}});
	
	var advflightRelationPanelWin;
	var advflightRelationPanel;
	
	var selRelationBPBtn = new Ext.Button({
		text: '关联排期选择',
		disabled : true,
		handler: function() {
			if(!advflightRelationPanelWin) {
				advflightRelationPanel = new com.myads.advflight.AdvflightRelationPanel();
				advflightRelationPanel.on('okClick', function(selectData) {
					locationForm.getForm().findField("scrBPackageId").setValue(selectData.id);
					locationForm.getForm().findField("scrBPackageName").setValue(selectData.id + '--' + selectData.channelName + "--" + selectData.advbarName);
					advflightRelationPanelWin.hide();
				});
				advflightRelationPanelWin = new Ext.Window({
					title: '关联排期',
			        layout:'fit',
			        width:700,
			    	height:400,
			        closeAction:'hide',
			        plain: true,
			        items: [advflightRelationPanel]
				});
			}
			advflightRelationPanel.loadData();
			advflightRelationPanelWin.show();
		}
	});
							
	function verifyShowContentTab(advbarId) {
		Ext.Ajax.request({
				method: 'post',
				url: '/myads/HTML/advert/AdvbarAction_getAdvbarPageType.action',
				params: {advbarId: advbarId},
			   	success:function(resp){
			   		tabs.unhideTabStripItem(3);
			   		var obj=Ext.util.JSON.decode(resp.responseText);
			   		if(obj.data.dataValue == 1) {//1:页面广告，不能选择内容定向
			   			tabs.hideTabStripItem(3);
			   		}
			   	}
		});
	}
      
    locationForm = new Ext.FormPanel({
		frame: true,
		labelWidth: 60,
		labelAlign: 'right',
		items: [
			{
				layout: 'column',
				items: [
				siteGrid = new Ext.grid.GridPanel({
						columnWidth: .20,
						el: 'site_grid',
						height:120,
						viewConfig: {
					        forceFit: true
					    },
					    frame:true,
						store: siteStore,
						cm: new Ext.grid.ColumnModel([
							{header:'选择网站', dataIndex:'siteName', menuDisabled:true, sortable:false}
						]),
						listeners : {
			                rowclick : function(grid, rowIndex, event) {
			                	var row = grid.getSelectionModel().getSelected();
			                	var siteId = row.data.siteId;
			                	Ext.Ajax.request({
									method: 'post',
									url: '/myads/HTML/basic/ChannelAction_getChannelListBySiteId.action',
								   	success:function(resp){
								    	var obj=Ext.util.JSON.decode(resp.responseText);
							      		channelStore.loadData(obj);
								    },
								   	params: {siteId : siteId}
								});
			                }
			            }
					}),
					channelGrid = new Ext.grid.GridPanel({
						columnWidth: .20,
						el: 'prod_grid',
						height:120,
						viewConfig: {
					        forceFit: true
					    },
					    frame:true,
						store: channelStore,
						cm: new Ext.grid.ColumnModel([
							{header:'选择频道', dataIndex:'name', menuDisabled:true, sortable:false}
						]),
						listeners : {
			                rowclick : function(grid, rowIndex, event) {
			                	var row = grid.getSelectionModel().getSelected();
			                	var channelIds = row.data.channelId;
			                	Ext.Ajax.request({
									method: 'post',
									url: '/myads/HTML/basic/OptionsAction_getAllAdvBarInChannel.action',
								   	success:function(resp){
								    	var obj=Ext.util.JSON.decode(resp.responseText);
							      		advbarStore.loadData(obj);
								    },
								   	params: {channelId : channelIds}
								});
			                }
			            }
					}),
					advbarGrid = new Ext.grid.GridPanel({
						columnWidth: .20,
						el: 'adbar_grid',
						height:120,
						viewConfig: {
					        forceFit: true
					    },
					    frame:true,
						store: advbarStore,
						cm: new Ext.grid.ColumnModel([
							{header:'选择广告条', dataIndex:'advbarName',menuDisabled:true, sortable:false}
						]),
						listeners : {
			                rowclick : function(grid, rowIndex, event) {
			                	var row = grid.getSelectionModel().getSelected();
			                	var advbarId = row.data.advbarId;
			                	saleTypeCombStore.load({params: {advbarId : advbarId}});
			                	refreshCalandar();
			                	//判断广告位类型，如果是普通页面广告，则没有"内容策略"
			                	verifyShowContentTab(advbarId);
			                	
			                	//位置改变，初始化其它控件
			                	locationForm.form.reset();
								tab_item_directForm.form.reset();
								tab_item_content.form.reset();
								tab_item_point.form.reset();
								area_result_store.removeAll();
								point_ds.removeAll();
								
								selRelationBPBtn.setDisabled(false);
			                }
			            }
					})
				]
			},
			{
				layout: 'column',
				items: [
					{
						columnWidth: .20, layout: 'form',
						items:[
							saleTypeComb = new Ext.form.ComboBox({
		        	       		fieldLabel: '售卖方式',
		        	       		id: 'saleTypeCombId',
								name: 'saleType',
								hiddenName: 'saleType', 
								mode: 'local',
								triggerAction: 'all', 
								editable: false,
								allowBlank : false,
								valueField: 'value',
								displayField: 'text',
								anchor : '99%',
								store: saleTypeCombStore,
								listeners : {
					                select : function(ComboBox, record, index) {
					                	var value = record.data.value;
						                var advbarId = advbarGrid.getSelectionModel().getSelected().data.advbarId;
						                var saleTypeName = Ext.get('saleTypeCombId').dom.value;
						                
					                	priceFormatCombStore.load({params:{advbarId: advbarId, saleTypeValue : value, saleTypeName: saleTypeName}});
					                	
					                	priceFormatCombStore.addListener('load', function(st, rds, opts) {
											if(rds.length == 1) {
												priceIdComb.setValue(rds[0].data.value);
							                	Ext.Ajax.request({
													method: 'post',
													url: '/myads/HTML/advert/AdvbarPriceAction_getAdvbarPriceById.action',
												   	success:function(resp){
												    	var result=Ext.util.JSON.decode(resp.responseText);
														locationForm.getForm().findField("price").setValue(result);
												    },
												   	params: {id : rds[0].data.value}
												});
											}
											priceFormatCombStore.removeListener('load');
										});
					                	
										selectDayArr = new Array();
										
					                	refreshCalandar();
					                	
					                	locationForm.form.reset();
					                	ComboBox.setValue(value);
					                	
										tab_item_directForm.form.reset();
										tab_item_content.form.reset();
										tab_item_point.form.reset();
										
										
										area_result_store.removeAll();
										point_ds.removeAll();
										
										var saleType = Ext.get('saleTypeCombId').dom.value;
					                	if(saleType == 'CPD') {
					                		Ext.get('flightNumBtn').hide();
					                		Ext.get('flightNumDomId').hide();
					                	}
					                	else if(saleType == 'CPM') {
					                		 Ext.get('flightNumBtn').show();
					                		 Ext.get('flightNumDomId').show();
					                	}
					                	
					                	tab_item_point.doLayout();
					                }
					            }
		        	       })
						]
					},
					{
						columnWidth: .20, layout: 'form',labelAlign: 'left',
						items:[
							priceIdComb = new Ext.form.ComboBox({
								columnWidth: .20,
								id: 'priceIdCombId',
								name: 'priceId',
								hiddenName: 'format',
								mode: 'local',
								triggerAction: 'all', 
								editable: false,
								valueField: 'value',
								displayField: 'text',
								anchor : '99%',
								store: priceFormatCombStore,
								listeners : {
					                select : function(ComboBox, record, index) {
					                	var value = record.data.value;
					                	Ext.Ajax.request({
											method: 'post',
											url: '/myads/HTML/advert/AdvbarPriceAction_getAdvbarPriceById.action',
										   	success:function(resp){
										    	var result=Ext.util.JSON.decode(resp.responseText);
												locationForm.getForm().findField("price").setValue(result);
										    },
										   	params: {id : value}
										});
					                }
					            }
		        	       })
						]
					},
					{
						columnWidth: .20, layout: 'form',
						items: [{
							xtype : 'textfield',
							name : 'price',
							fieldLabel : '刊例单价',
							anchor : '99%',
							style:'color:red;',
							readOnly:true,
							allowBlank : false
//							disabled:true
						}]
					}
				]
			},
			{
				columnWidth: .01, layout: 'form',
	            	items: [
	            	        { xtype: 'hidden', name: 'id', hidden:true, hiddenLabel:true}
	            	] 
			}
			,
			{
				layout: 'column',
				items: [
					{
						columnWidth: .20, layout: 'form',
						items: [{
							xtype : 'textfield',
							name : 'discount',
							fieldLabel : '折扣(%)',
							anchor : '99%',
							listeners:{blur:function(){
								var discount = locationForm.getForm().findField("discount").getValue();
								var advbarPrice = locationForm.getForm().findField("price").getValue();
								var result = discount * advbarPrice / 100;
								locationForm.getForm().findField("disprice").setValue(result);						
							}}
						}]
					},
					{
						columnWidth: .20, layout: 'form',
						items: [{
							xtype : 'textfield',
							name : 'disprice',
							fieldLabel : '折扣单价',
							anchor : '99%',
							style:'color:red;',
							readOnly:true
//							disabled:true
						}]
					},
					{
						columnWidth: .20, layout: 'form',
						items:[
							new Ext.form.ComboBox({
		        	       		fieldLabel: '使用方式',
		        	       		id: 'useTypeCombId', 
								name: 'useType',
								hiddenName: 'useType', 
								allowBlank : false,
								editable: false,
								mode: 'local',
								triggerAction: 'all', 
								valueField: 'value',
								displayField: 'text',
								anchor : '99%',
								store: useTypeCombStore
		        	       })
						]
					}
				]
			},
			{
				layout: 'column',
				items: [
					{
						columnWidth: .4, layout: 'form',
						items: [
							{xtype : 'textfield',fieldLabel: '关联排期',name: 'scrBPackageName',anchor : '99%',maxLength:100}
						]
					},
					{
						columnWidth: .4, layout: 'form',
						items: [selRelationBPBtn]
					},
					{
						columnWidth: .01, layout: 'form',
						items: [
							{ xtype: 'hidden', name: 'scrBPackageId', hidden:true, hiddenLabel:true}
						]
					}
				]
			},
			{
				layout: 'column',
				items: [
					{
						columnWidth: .6, layout: 'form',
						items: [
							{xtype : 'textfield',fieldLabel: '备注',name: 'remark',anchor : '99%',maxLength:100}
						]
					}
				]
			}
		]
		
    });
    
	//选择位置--------------------------结束-----------------------
    
    
    //定向策略 -----------------------------------开始
    var hour_group;
    var area_city_store;
    var area_city_grid;
    var area_prinvice_store;
    var area_prinvice_grid;
    var area_result_grid;
    var area_result_store;
    var area_result_data;
    var area_group;
    var frequencyType_group;
    var frequencyTypeCombStore;
    var tab_item_directForm;
    
	hour_group = {
    	xtype: 'fieldset',
		title: '小时定向',
		layout: 'form',
		items:[
			{
				layout:'column',columnWidth: 1,
				items:[
					{ boxLabel: '0_1', name: 'hour_chk_0_1', id: 'hour_chk_24', inputValue: '24', xtype: 'checkbox', width: 70},
		            { boxLabel: '1_2', name: 'hour_chk_1_2', id: 'hour_chk_1', inputValue: '1', xtype: 'checkbox', width: 70 },
		            { boxLabel: '2_3', name: 'hour_chk_2_3', id: 'hour_chk_2', inputValue: '2', xtype: 'checkbox', width: 70 },
		            { boxLabel: '3_4', name: 'hour_chk_3_4', id: 'hour_chk_3', inputValue: '3', xtype: 'checkbox', width: 70 },
		            { boxLabel: '4_5', name: 'hour_chk_4_5', id: 'hour_chk_4', inputValue: '4', xtype: 'checkbox', width: 70 },
		            { boxLabel: '5_6', name: 'hour_chk_5_6', id: 'hour_chk_5', inputValue: '5', xtype: 'checkbox', width: 70 }
				]
			},
			{
				layout:'column',columnWidth: 1,
				items:[
					{ boxLabel: '6_7', name: 'hour_chk_6_7', id: 'hour_chk_6', inputValue: '6', xtype: 'checkbox', width: 70 },
					{ boxLabel: '7_8', name: 'hour_chk_7_8', id: 'hour_chk_7', inputValue: '7', xtype: 'checkbox', width: 70 },
		            { boxLabel: '8_9', name: 'hour_chk_8_9', id: 'hour_chk_8', inputValue: '8', xtype: 'checkbox', width: 70 },
		            { boxLabel: '9_10', name: 'hour_chk_9_10', id: 'hour_chk_9', inputValue: '9', xtype: 'checkbox', width: 70 },
		            { boxLabel: '10_11', name: 'hour_chk_10_11', id: 'hour_chk_10', inputValue: '10', xtype: 'checkbox', width: 70 },
		            { boxLabel: '11_12', name: 'hour_chk_11_12', id: 'hour_chk_11', inputValue: '11', xtype: 'checkbox', width: 70 }
		            
				]
			},
			{
				layout:'column',columnWidth: 1,
				items:[
					{ boxLabel: '12_13', name: 'hour_chk_12_13', id: 'hour_chk_12', inputValue: '12', xtype: 'checkbox', width: 70 },
		            { boxLabel: '13_14', name: 'hour_chk_13_14', id: 'hour_chk_13', inputValue: '13', xtype: 'checkbox', width: 70 },
		            { boxLabel: '14_15', name: 'hour_chk_14_15', id: 'hour_chk_14', inputValue: '14', xtype: 'checkbox', width: 70 },
		            { boxLabel: '15_16', name: 'hour_chk_15_16', id: 'hour_chk_15', inputValue: '15', xtype: 'checkbox', width: 70 },
		            { boxLabel: '16_17', name: 'hour_chk_16_17', id: 'hour_chk_16', inputValue: '16', xtype: 'checkbox', width: 70 },
		            { boxLabel: '17_18', name: 'hour_chk_17_18', id: 'hour_chk_17', inputValue: '17', xtype: 'checkbox', width: 70 }
				]
			},
			{
				layout:'column',columnWidth: 1,
				items:[
		            { boxLabel: '18_19', name: 'hour_chk_18_19', id: 'hour_chk_18', inputValue: '18', xtype: 'checkbox', width: 70 },
		            { boxLabel: '19_20', name: 'hour_chk_19_20', id: 'hour_chk_19', inputValue: '19', xtype: 'checkbox', width: 70 },
		            { boxLabel: '20_21', name: 'hour_chk_20_21', id: 'hour_chk_20', inputValue: '20', xtype: 'checkbox', width: 70 },
		            { boxLabel: '21_22', name: 'hour_chk_21_22', id: 'hour_chk_21', inputValue: '21', xtype: 'checkbox', width: 70 },
		            { boxLabel: '22_23', name: 'hour_chk_22_23', id: 'hour_chk_22', inputValue: '22', xtype: 'checkbox', width: 70 },
		            { boxLabel: '23_24', name: 'hour_chk_23_24', id: 'hour_chk_23', inputValue: '23', xtype: 'checkbox', width: 70 }
				]
			}
		 ]
    };
	
    var area_prinvice_cm = new Ext.grid.ColumnModel([
	    {header:'省份选择', dataIndex:'province', menuDisabled:true, sortable:true, width:60}
	]);
	
    var area_prinvice_store = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({url: '/myads/HTML/basic/ProviceAction_getAllProvice.action'}),
		remoteSort: false,
		reader: new Ext.data.JsonReader(
			{totalProperty: 'total',idProperty:'id',root: 'invdata'},
			[{name: 'id'},{name: 'pcode'},{name: 'ccode'},{name: 'province'}]
		)
	});
	
	area_prinvice_grid = new Ext.grid.GridPanel({
		el: 'area_prinvice_grid',
		height: 220,
		width: 98,
	    frame:true,
	    store: area_prinvice_store,
		cm: area_prinvice_cm
	});	
	area_prinvice_grid.render();
	
	area_prinvice_grid.addListener('rowclick', function(grid, rowindex, e) {
		var record = area_prinvice_grid.getStore().getAt(rowindex);
		area_city_store.load({params: {start:0, limit:100, pcode:record.data.pcode}});
	});
	area_prinvice_grid.addListener('rowdblclick', function(grid, rowindex, e) {
		var record = area_prinvice_grid.getStore().getAt(rowindex);
		
		var _rs = new Ext.data.Record({
			id: record.id, pcode:record.data.pcode, province:record.data.province, display:record.data.province
		}) ;
		area_result_store.add(_rs);
	});
	
	area_prinvice_store.load();
	area_prinvice_grid.getView().refresh();
    
	var area_city_cm = new Ext.grid.ColumnModel([
	    {header:'城市选择', dataIndex:'city', menuDisabled:true, sortable:true}
	]);
    var area_city_store = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({url: '/myads/HTML/basic/ProviceAction_getAllCityInProvice.action'}),
		remoteSort: false,
		reader: new Ext.data.JsonReader(
			{totalProperty: 'total',idProperty:'id',root: 'invdata'},
			[{name: 'id'},{name: 'dcode'},{name: 'pcode'},{name: 'city'}]
		)
	});
	
	area_city_grid = new Ext.grid.GridPanel({
		el: 'area_city_grid',
		height: 220,
		width: 98,
	    frame:true,
	    store: area_city_store,
		cm: area_city_cm
	});
	area_city_grid.addListener('rowdblclick', function(grid, rowindex, e) {
		var record = area_city_grid.getStore().getAt(rowindex);
		
		var pr = area_prinvice_grid.getSelectionModel().getSelected();
		
		var _rs = new Ext.data.Record({
			id: record.id, pcode:record.data.pcode, dcode:record.data.dcode, city: record.data.city, display: pr.data.province + ' -> ' + record.data.city
		}) ;
		area_result_store.add(_rs);
	});
	
	area_result_data = [];
	area_result_store = new Ext.data.Store({
		proxy: new Ext.data.MemoryProxy(area_result_data),
		reader: new Ext.data.ArrayReader({},[
			{name:'id'},{name:'city'}
		])
	});
	var area_result_cm = new Ext.grid.ColumnModel([
		{header:'已选择', dataIndex:'display', menuDisabled:true, sortable:true,width:160}
	]);
	area_result_grid = new Ext.grid.GridPanel({
		el: 'area_result_grid',
		height: 220,
		width: 98,
	    frame:true,
	    store: area_result_store,
		cm: area_result_cm
	})
	area_result_grid.addListener('rowdblclick', function(grid, rowindex, e) {
		var record = area_result_grid.getStore().getAt(rowindex);
		area_result_store.remove(record);
	});
	area_result_grid.render();
	
    area_group = {
    	 xtype: 'fieldset',
		 title: '地域定向',
		 layout:'table',
		 layoutConfig: {columns:7},
		 height: 250,
		 defaults: {frame:false, width:100, height: 250},
		 items:[
		 	{
		        colspan:2,
		        width:100,
		        items:[area_prinvice_grid]
		    },
		    {
		    	colspan:2,
		    	width:100,
		        items:[area_city_grid]
		    },
		    {
		    	width:50,
		    	layout:'fit',
		        layout: {type:'vbox',padding:'5',pack:'center',align:'center'},
	            defaults:{margins:'0 0 5 0'},
				items: [
					new Ext.Button({
						text: ' >> ',
						handler: function () {
							if(area_city_grid.getSelectionModel().hasSelection()) {
								var record = area_city_grid.getSelectionModel().getSelected();
								var pr = area_prinvice_grid.getSelectionModel().getSelected();
								var _rs = new Ext.data.Record({
									id: record.id, pcode:record.data.pcode, dcode:record.data.dcode, city: record.data.city, display: pr.data.province + ' -> ' + record.data.city
								}) ;
								area_result_store.add(_rs);
							}
							else if(area_prinvice_grid.getSelectionModel().hasSelection()) {
								var record = area_prinvice_grid.getSelectionModel().getSelected();
								var _rs = new Ext.data.Record({
									id: record.id, pcode:record.data.pcode, province:record.data.province, display:record.data.province
								}) ;
								area_result_store.add(_rs);
							}
						}
					}),
					new Ext.Button({
						text: ' << ',
						handler: function () {
							var record =area_result_grid.getSelectionModel().getSelected();
							area_result_store.remove(record);
						}
					})
				]
		    },
		    {
		        colspan:2,
		        width:100,
		        items:[area_result_grid]
		    }
	    ]
    };
    
    frequencyTypeCombStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({url: '/myads/HTML/basic/OptionsAction_getBaseDataNamesByType.action'}),
		reader: new Ext.data.ArrayReader({}, [{name: 'value'},{name: 'text'}])
    });
    frequencyTypeCombStore.load({params: {dataType: 'frequencytype'}});
    
    frequencyType_group = {
    	xtype: 'fieldset',
		title: '频次定向',
		labelWidth: 70,
		layout: 'form',
		items: [
			{//行1
				layout: 'column',
				items: [
					{
						columnWidth: 1, layout: 'form',labelWidth: 80,
						items: [
							{	
								xtype: 'checkbox',fieldLabel: '是否频次定向',name: 'isFrequency',id: 'isFrequency', inputValue: '1',
								listeners: {
									check: function (obj, ischecked) {
										if(ischecked) {
											var a = tab_item_directForm.getForm().findField("frequencyType");
											var b = tab_item_directForm.getForm().findField("frequencyNum");
											b.allowBlank = false;
											a.allowBlank = false;
											b.minValue = 1;
											b.minText = '值必须大于0！';
											b.show();
											a.show();
										}
										else {
											var a = tab_item_directForm.getForm().findField("frequencyType");
											var b = tab_item_directForm.getForm().findField("frequencyNum");
											a.allowBlank = true;
											b.allowBlank = true;
											delete b.minValue;
											delete b.minText;
											b.hide();
											a.hide();
										}
									}
								}
							}
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
							new Ext.form.ComboBox({
		        	       		fieldLabel: '频次定向',
		        	       		width: 100,
								name: 'frequencyType',
								hiddenName: 'frequencyType',
								mode: 'local',
								triggerAction: 'all', 
								valueField: 'value',
								hidden: true,
								editable: false,
								allowBlank : true,
								displayField: 'text',
								store: frequencyTypeCombStore
		        	       })
						]
					},
					{
						layout:'column',columnWidth: .5,
						items: [
							{
								layout: 'form',
				            	items: [
				            	        { xtype: 'numberfield', name: 'frequencyNum', hidden: true,allowBlank : true, fieldLabel: '次数 ',width: 100}
				            	]
							}
						]
					}
				]
			}
		]
		
	};
	
	tab_item_directForm = new Ext.FormPanel({
		frame: true,
		labelAlign: 'right',
		layout:'table',
        layoutConfig: {columns:2},
        defaults: {frame:true},
		items:[
			{
				height: 150,
				width:470,
				items:[hour_group]
			},
			{
				height: 260,
				rowspan:2,
				width:500,
				layout:'fit',
				items:[area_group]
			},
			{
				height: 100,
				width:470,
				items:[frequencyType_group]
			}
		]
    });
    //定向策略 -----------------------------------结束-----------------------------------///
    
    //内容策略------------------------------------开始--------------------------------------
	var tab_item_content;
	tab_item_content = new Ext.FormPanel({
		frame: true,
		labelWidth: 90,
		labelAlign: 'right',
		region: 'center',
		items:[
			{
				layout: 'form',
            	items: [
            		{	
						xtype: 'checkbox',fieldLabel: '是否空广告',name: 'isNull', inputValue: '1'
					}
            	]
			},
			{
				xtype: 'checkbox',fieldLabel: '是否内容定向',name: 'isContentDirect',id: 'isContent', inputValue: '1',
				listeners: {
					check: function (obj, ischecked) {
						if(ischecked) {
							tab_item_content.getForm().findField("keyword").show();
							tab_item_content.getForm().findField("user").show();
							tab_item_content.getForm().findField("video").show();
							tab_item_content.getForm().findField("program").show();
							tab_item_content.getForm().findField("activity").show();
							tab_item_content.getForm().findField("subject").show();
						}
						else {
							tab_item_content.getForm().findField("keyword").setValue('');
							tab_item_content.getForm().findField("user").setValue('');
							tab_item_content.getForm().findField("video").setValue('');
							tab_item_content.getForm().findField("program").setValue('');
							tab_item_content.getForm().findField("activity").setValue('');
							tab_item_content.getForm().findField("subject").setValue('');
							tab_item_content.getForm().findField("keyword").hide();
							tab_item_content.getForm().findField("user").hide();
							tab_item_content.getForm().findField("video").hide();
							tab_item_content.getForm().findField("program").hide();
							tab_item_content.getForm().findField("activity").hide();
							tab_item_content.getForm().findField("subject").hide();
						}
						refreshCalandar();
					}
				}
			},
			{
				layout: 'form',
            	items: [
            	        { xtype: 'textfield', name: 'keyword', fieldLabel: '关键字 ', width:500, hidden: true}
            	]
			},
			{
				layout: 'form',
            	items: [
            	        { xtype: 'textfield', name: 'user', fieldLabel: '用户 ', width:500, hidden: true}
            	]
			},
			{
				layout: 'form',
            	items: [
            	        { xtype: 'textfield', name: 'video', fieldLabel: '视频ID ', width:500, hidden: true}
            	]
			},
			{
				layout: 'form',
            	items: [
            	        { xtype: 'textfield', name: 'program', fieldLabel: '节目 ', width:500, hidden: true}
            	]
			},
			{
				layout: 'form',
            	items: [
            	        { xtype: 'textfield', name: 'activity', fieldLabel: '活动 ', width:500,hidden: true}
            	]
			},
			{
				layout: 'form',
            	items: [
            	        { xtype: 'textfield', name: 'subject', fieldLabel: '专题 ', width:500,hidden: true}
            	]
			}
		]
    });
    //内容策略------------------------------------结束-----------------------------------
    
    //选择点位------------------------------------开始-----------------------------------
    
    //TODO 日历选择器-----------------------------开始---------------------------------------
    var selectDayArr = new Array();
    function refreshCalandar() {
    	if(advbarGrid == null) return;
    	
		var advbarId = advbarGrid.getSelectionModel().getSelected().data.advbarId;
		var saleType = Ext.get('saleTypeCombId').dom.value;
		
		var saleTypeValue = saleTypeComb.getValue();
		
		if(advbarId == '' || saleType == '')
			return;
		
		var isContentDirect = false;
		if(tab_item_directForm && tab_item_directForm.getForm().getEl())
			isContentDirect = tab_item_content.getForm().findField("isContentDirect").getValue();
		var areaResult = [];	
		if(tab_item_directForm && tab_item_directForm.getForm().getEl()) {
			area_result_store.each(function(rec){
		    	areaResult.push(rec.data);
		   	});
		}
	   	var directArea = Ext.util.JSON.encode(areaResult);
		calander_ds.load({params: {start:0, limit:50, showDate:calander_dataPicker.value, advbarId: advbarId, saleType:saleType,saleTypeValue:saleTypeValue, isContentDirect:isContentDirect,directArea:'',paramPriority:param_priority}});
    }
    
    function calander_renderOp(value, cellmeta, record, rowIndex, columnIndex, stor) {
		var colu = 'weekDay_' + (columnIndex - 1);
		var dayText = "";
		var allSize = "";
		var lastSize = "";
		
		if(record.data[colu].text == '') {
			return '';
		}
		else {
			dayText = record.data[colu].text;
			allSize = record.data[colu].allSize;
			lastSize = record.data[colu].lastSize;
		}
		return String.format('<b>{0}&nbsp;<input type="checkbox" id="chk_day_' + record.data[colu].day + '" onclick="calanderSelectDay(' + rowIndex + ',' + columnIndex + ',this.checked)" /></b><br/><a style="color: #3A9A39">{1}</a>', dayText, lastSize);
	}
	
	function calander_renderRowSel(value, cellmeta, record, rowIndex, columnIndex, stor) {
		return String.format('<input type="checkbox" id="chk_week_'+ rowIndex + '" onclick="selectWeek(' + rowIndex + ', this.checked)"/>');
	}
	
	//选中一周
	window.selectWeek = function(week, sel ) {
		var record = calander_grid.getStore().getAt(week);
		var columnCount = calander_grid.getColumnModel().getColumnCount();
		for(var i = 1; i < columnCount; i ++) {
			var fieldName = calander_grid.getColumnModel().getDataIndex(i);
			if(record.get(fieldName).canSelect) {
				record.get(fieldName).select = sel;
				if(document.getElementById('chk_day_' + record.get(fieldName).day) != null)
					document.getElementById('chk_day_' + record.get(fieldName).day).checked = sel; 
					
				refreshSelectDayArr(record.get(fieldName).dateStr, sel);	
			}
		}
	}
	
	//选中一天
	window.calanderSelectDay = function(rowIndex, columnIndex, isChecked) {
		var record = calander_grid.getStore().getAt(rowIndex);
		var fieldName = calander_grid.getColumnModel().getDataIndex(columnIndex)
		record.get(fieldName).select = isChecked; 
		
		refreshSelectDayArr(record.get(fieldName).dateStr, isChecked);
	}
	
	//选中当月的所有周几
	window.calanderSelectWeekday = function(weekDay, sel) {
		for(var i = 0; i < calander_grid.getStore().getTotalCount(); i++) {
			var record = calander_grid.getStore().getAt(i);
			var fieldName = calander_grid.getColumnModel().getDataIndex(weekDay);
			if(record.get(fieldName).canSelect && record.get(fieldName).weekday == weekDay) {
				record.get(fieldName).select = sel;
				if(document.getElementById('chk_day_' + record.get(fieldName).day) != null)
					document.getElementById('chk_day_' + record.get(fieldName).day).checked = sel; 
				refreshSelectDayArr(record.get(fieldName).dateStr, sel);
			}
		}
	}
	
	//选中整月
	window.calanderSelectMonth = function(isChecked) {
		for(var i = 0; i < calander_grid.getStore().getTotalCount(); i++) {
			var record = calander_grid.getStore().getAt(i);
			
			document.getElementById('chk_week_' + i).checked = isChecked;
			document.getElementById('chk_weekDay_0').checked = isChecked;
			
			for(var j = 1; j < calander_grid.getColumnModel().getColumnCount(); j++) {
				var fieldName = calander_grid.getColumnModel().getDataIndex(j);
				if(record.get(fieldName).canSelect == false) {
					continue;
				}
				
				if(document.getElementById('chk_weekDay_' + j) != null)
					document.getElementById('chk_weekDay_' + j).checked = isChecked;
				record.get(fieldName).select = isChecked;
				if(document.getElementById('chk_day_' + record.get(fieldName).day) != null)
					document.getElementById('chk_day_' + record.get(fieldName).day).checked = isChecked; 
				
				refreshSelectDayArr(record.get(fieldName).dateStr, isChecked);
			}
		}
	}
	
	//更新临时选择数组
	function refreshSelectDayArr(dateStr, isChecked) {
		if(dateStr == '')
			return;
			
		selectDayArr.remove(dateStr);	
		if(isChecked) {
			selectDayArr.push(dateStr);
		}
	}
	
	//刷新CheckBox状态
	function refreshChkBox() {
		calander_ds.each(function(r){
			if(r.data[p].dateStr != '') {
				var selIndex = selectDayArr.indexOf(r.data[p].dateStr);
				if(selIndex >= 0) {
					document.getElementById('chk_day_' + r.data[p].day).checked = true;
				}
				else {
					document.getElementById('chk_day_' + r.data[p].day).checked = false;
				}
			}
		});
	}
	
	var calander_cm = new Ext.grid.ColumnModel([
		{header:'<input type="checkbox" onclick="calanderSelectMonth(this.checked)" />', dataIndex:'op', renderer: calander_renderRowSel, sortable:false, resizable:false, menuDisabled:true },
	    {header:'日&nbsp;<input type="checkbox" id="chk_weekDay_0" onclick="calanderSelectWeekday(' + 1 + ',this.checked)"/>', dataIndex:'weekDay_0', renderer: calander_renderOp, sortable:false, resizable:false, menuDisabled:true },
	    {header:'一&nbsp;<input type="checkbox" id="chk_weekDay_1" onclick="calanderSelectWeekday(' + 2 + ',this.checked)"/>', dataIndex:'weekDay_1', renderer: calander_renderOp, sortable:false, resizable:false, menuDisabled:true},
	    {header:'二&nbsp;<input type="checkbox" id="chk_weekDay_2" onclick="calanderSelectWeekday(' + 3 + ',this.checked)"/>', dataIndex:'weekDay_2', renderer: calander_renderOp, sortable:false, resizable:false, menuDisabled:true},
	    {header:'三&nbsp;<input type="checkbox" id="chk_weekDay_3" onclick="calanderSelectWeekday(' + 4 + ',this.checked)"/>', dataIndex:'weekDay_3', renderer: calander_renderOp, sortable:false, resizable:false, menuDisabled:true},
	    {header:'四&nbsp;<input type="checkbox" id="chk_weekDay_4" onclick="calanderSelectWeekday(' + 5 + ',this.checked)"/>', dataIndex:'weekDay_4', renderer: calander_renderOp, sortable:false, resizable:false, menuDisabled:true},
	    {header:'五&nbsp;<input type="checkbox" id="chk_weekDay_5" onclick="calanderSelectWeekday(' + 6 + ',this.checked)"/>', dataIndex:'weekDay_5', renderer: calander_renderOp, sortable:false, resizable:false, menuDisabled:true},
	    {header:'六&nbsp;<input type="checkbox" id="chk_weekDay_6" onclick="calanderSelectWeekday(' + 7 + ',this.checked)"/>', dataIndex:'weekDay_6', renderer: calander_renderOp, sortable:false, resizable:false, menuDisabled:true}
	]);
	
	var calander_ds = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({url: '/myads/HTML/advflight/BookCalendarAction_getCalendar.action'}),
		remoteSort: false,
		reader: new Ext.data.JsonReader(
			{
				totalProperty: 'total',
				idProperty:'day',
				root: 'invdata'
			},
			[
				{name: 'weekDay_0'},
				{name: 'weekDay_1'},
				{name: 'weekDay_2'},
				{name: 'weekDay_3'},
				{name: 'weekDay_4'},
				{name: 'weekDay_5'},
				{name: 'weekDay_6'}
			]
		)
	});

//	refreshCalandar();
	
	calander_ds.on("load", function(s,records) {
		var rowIndex=0;
		
		var todayRow;
		var todayCol;
		
		s.each(function(r){
			var columnIndex=0;
			var saleType = Ext.get('saleTypeCombId').dom.value;
			for(p in r.data) {
				if(r.data[p].canSelect == false) {//如果不可选择，则设置颜色为灰色
					if(document.getElementById('chk_day_' + r.data[p].day) != null)
						document.getElementById('chk_day_' + r.data[p].day).style.display = 'none';
				}
				else {
					if(document.getElementById('chk_day_' + r.data[p].day) != null)
						document.getElementById('chk_day_' + r.data[p].day).style.display = '';
				}
				
				if(saleType == 'CPD') {
					if(r.data[p].contentStatus == 'full') 
						calander_grid.getView().getCell(rowIndex, columnIndex + 1).style.backgroundColor='#ECEEEF';
					else if(r.data[p].contentStatus == 'part') 
						calander_grid.getView().getCell(rowIndex, columnIndex + 1).style.backgroundColor='#CDF7E4';
					else if(r.data[p].contentStatus == 'empty') 
						calander_grid.getView().getCell(rowIndex, columnIndex + 1).style.backgroundColor='#FFFFFF';
				}
				
				columnIndex = columnIndex + 1;
				
				if(r.data[p].today == true) {//得到今天
					todayRow = rowIndex;
					todayCol = columnIndex - 1;
				}
				
				if(r.data[p].dateStr != '') {
					var selIndex = selectDayArr.indexOf(r.data[p].dateStr);
					if(selIndex >= 0) {
						document.getElementById('chk_day_' + r.data[p].day).checked = true;
					}
				}
			}
			rowIndex = rowIndex + 1;
		});
	});
	
	var selectionModel = new Ext.grid.CellSelectionModel({
	});
	
	selectionModel.on("cellselect",function(sel,row,col) {//单元格选择事件，如果点击了不可选择的单元格，则不让选择
		if(col == 0) {
			return;
		}
			
		var record = calander_grid.getStore().getAt(row);
		var fieldName = calander_grid.getColumnModel().getDataIndex(col);
		if(!record.get(fieldName).canSelect)
			selectionModel.clearSelections(false);
		else 
			record.get(fieldName).select = true;
	});
	
	var calander_dataPicker;
	
	var toolBar = new Ext.Toolbar({
//		buttonAlign: 'center',
	    items:[
	    	new Ext.Button({
	    		text: '上月',
	    		handler: function() {
	    			var dt = calander_dataPicker.value + '-01';
	    			var preMonthDate = Date.parseDate(dt, 'Y-m-d');
	    			preMonthDate = preMonthDate.add(Date.MONTH, -1);
	    			calander_dataPicker.setValue(preMonthDate);
	    			refreshCalandar();
	    		}
	    	}),
	    	new Ext.Button({
	    		text: '下月',
	    		handler: function() {
	    			var dt = calander_dataPicker.value + '-01';
	    			var nextMonthDate = Date.parseDate(dt, 'Y-m-d');
	    			nextMonthDate = nextMonthDate.add(Date.MONTH, 1);
	    			calander_dataPicker.setValue(nextMonthDate);
	    			refreshCalandar();
	    		}
	    	}),
	    	calander_dataPicker = new Ext.form.DateField({
				fieldLabel: '日期',
				name: 'selTime',
				width: 80,
				allowBlank:false,
				plugins: 'monthPickerPlugin',  
				format: 'Y-m',
				editable: false,
				value: new Date(),
				listeners:{
					'select':  function() {
						refreshCalandar();
					}
				} 
			})
	    ]
   	});
   	
	var calander_grid = new Ext.grid.GridPanel({
		region:'north',
		trackMouseOver:false,
		sm: selectionModel,
		width:390,
		height:280,
		ds: calander_ds,
		cm: calander_cm,
	    viewConfig: {
	    	forceFit: true
	    },
	    tbar: toolBar
	});
    //日历选择器-----------------------------结束---------------------------------------
	
	
    var point_ds; //点位数据
    var pointGrid;
    var tab_item_point;//点位Form
    var flightLable;
	var selFlightBtn;
	
	var point_sm = new Ext.grid.CheckboxSelectionModel();
	var point_cm = new Ext.grid.ColumnModel([
		point_sm,
		new Ext.grid.RowNumberer(),
		{header:'日期', dataIndex:'startTime', sortable:true},
		{header:'投放量', dataIndex:'flightNum', sortable:true, editor: new Ext.form.TextField({allowBlank: false})}
	]);
	
	function point_renderOp(value, cellmeta, record, rowIndex, columnIndex, stor) {
		if(record.data.canEdit != 'true')
			return '';
			
		var id = record.data.id;
		var dataValue = record.data.startTime; 
		var delStr = '<a href="#" onclick=\"del_book(\''+rowIndex+'\', \'' + dataValue + '\');\">删除</a>';
		return delStr;
	}
	
	//刷新合计
	function refreshSumFlight() {
		var sumFlight = 0;
    	point_ds.each(function(rec){
    		sumFlight = sumFlight + parseInt(rec.data.flightNum);
	   	});
    	Ext.get('flightSumFieldId').dom.innerHTML = '合计：' + sumFlight;
	}
	
	window.del_book = function(rowIndex, info) {
		var record = point_ds.getAt(rowIndex);
		point_ds.remove(record);
		selectDayArr.remove(record.data.startTime);
		refreshChkBox();
		pointGrid.getView().refresh();
	}
	
	point_ds = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({url: '/myads/HTML/advflight/BookAction_getBookList.action'}),
		remoteSort: false,
		reader: new Ext.data.JsonReader(
			{
				totalProperty: 'total',
				idProperty:'startTime',
				root: 'invdata'
			},
			[
				{name: 'id'},
				{name: 'startTime'},
				{name: 'flightNum'},
				{name: 'canEdit'}
			]
		)
	});
	point_ds.setDefaultSort("startTime",　"ASC")
	
    tab_item_point = new Ext.form.FormPanel({
	    id:'main-panel',
	    baseCls:'x-plain',
	    layout:'table',
	    layoutConfig: {columns:5},
	    defaults: {frame:true, width:300, height: 280},
	    items:[{
	        colspan:2,
	        width:400,
	        items:[
	        	calander_grid
	        ]
	    },{
	        width:100,
	        layout: {
                type:'vbox',
                padding:'5',
                pack:'center',
                align:'center'
            },
            //defaults:{margins:'0 0 5 0'},
			items: [
				selFlightBtn = new Ext.Button({
					text: '选择点位',
			        handler: function(){
			        	var saleType = Ext.get('saleTypeCombId').dom.value;
	                	//删除原来的
	                	point_ds.each(function(rec){
	                		var selIndex = selectDayArr.indexOf(rec.data.startTime);
	                		if(selIndex < 0 && rec.data.canEdit == 'true')
								point_ds.remove(rec);
					   	});
	                	
	                	for(var k = 0; k < selectDayArr.length; k++) {
	                		var _dayStr = selectDayArr[k];
	                		var _record = point_ds.getById(_dayStr);
							var _id = '';
							if(saleType == 'CPD') {
		                		flightNum = 1;
		                	}
		                	else if(saleType == 'CPM') {
		                		flightNum = 0;
		                	}
		                	
							if(_record != null) {
								_id = _record.data.id;
								flightNum = _record.data.flightNum;
								if(_record.data.canEdit == 'true') {
									point_ds.remove(_record);
								}
								else {
									continue;
								}
							}
							
							var _rs = new Ext.data.Record({
								startTime: _dayStr, flightNum: flightNum, id:_id, canEdit: 'true'
							},_dayStr) ;
							point_ds.add(_rs);
	                	}
	                	
	                	point_ds.sort('startTime', 'ASC');
			        	pointGrid.getView().refresh();
			        	
			        	refreshSumFlight();
			        }
				})
			]
	    },{
	        colspan:2,
	        width:600,
	        layout:'fit',
	        items:[
	        	pointGrid = new Ext.grid.EditorGridPanel({
					region: 'center',
					sm: point_sm,
					ds: point_ds,
					cm: point_cm,
					autoExpandColumn: 'startTime',
				    viewConfig: {
				    	forceFit: true
				    },
				    listeners:{
				        beforeedit: function(e){
				           return e.record.data.canEdit == 'true';
				        }
				    },
				    bbar: new Ext.Toolbar({
				    	buttons: [
							'-',
							'投放量',
							{xtype: 'numberfield', name: 'flightNum', id:'flightNumDomId', fieldLabel: '投放量',width:50},
							{
								text:'设置',
								id:'flightNumBtn',
								handler: function() {
									var flightNum = Ext.get('flightNumDomId').dom.value;
									if(flightNum == 0)
										return;
										
									var saleType = Ext.get('saleTypeCombId').dom.value;
						        	if(saleType == 'CPD') {
				                		flightNum = 1;
				                	}
				                	
									var rs=point_sm.getSelections();
									Ext.each(rs,function(){
										var _record = point_ds.getById(this.get('startTime'));
										point_ds.remove(_record);
										var _rs = new Ext.data.Record({
											startTime: this.get('startTime'), flightNum: flightNum, id:this.get('id'), canEdit: 'true'
										},this.get('startTime')) ;
										point_ds.add(_rs);
									});
									
									pointGrid.getView().refresh();
									refreshSumFlight();
								}
							},
							'-',
							{
								xtype: 'label',
								id: 'flightSumFieldId',
								text:'合计：0'
							}
						]
				    })
				})
	        ]
	    }]
	});
    //选择点位------------------------------------结束-----------------------------------
    
     var kpiGroup = {
		 xtype: 'fieldset',
		 title: 'KPI',
		 layout: 'form',
		 items: [
			 {
			 	layout:'column',
			 	items:[
			 		{
						columnWidth: .5, layout: 'form',
						items: [{xtype : 'textfield',name : 'impression',fieldLabel : '曝光',disabled:true,anchor : '99%'}]
					},
					{
						columnWidth: .5, layout: 'form',
						items: [{xtype : 'textfield',name : 'click',fieldLabel : '点击率',disabled:true,anchor : '99%'}]
					}
			 	]
			 },
			 {
			 	layout:'column',
			 	items:[
			 		{
						columnWidth: 1, layout: 'form',
						items: [{xtype : 'textfield',name : 'note',fieldLabel : '备注',disabled:true,anchor : '99%'}]
					}
			 	]
			 }
		 ]
	};
	
	var basicinfo_form = new Ext.form.FormPanel({
		labelAlign: 'right',
		region: 'center',
		labelWidth: 80,
		frame: true,
		items: [
			{
				layout: 'column',
				items: [
					{
						columnWidth: 1, layout: 'form',
						items: [
							{xtype : 'textfield',name : 'contractNum',fieldLabel : '合同号',allowBlank:false,disabled:true,anchor : '99%'}
						]
					}
				]
			},
			{
				layout: 'column',
				items: [
					{
						columnWidth: .33, layout: 'form',
						items: [
							{xtype : 'textfield',name : 'useTypeName',fieldLabel : '执行单类型',disabled:true,anchor : '99%'}
						]
					},
					{
						columnWidth: .34, layout: 'form',
						items: [
								{xtype : 'textfield',name : 'name',fieldLabel : '项目名称',allowBlank:false,disabled:true,anchor : '99%'}
						]
					},
					{
						columnWidth: .33, layout: 'form',
						items: [{xtype : 'textfield',name : 'consumerName',fieldLabel : '客户',disabled:true,anchor : '99%'}]
					}
				]
			},
			{
				layout: 'column',
				items: [
					{
						columnWidth: .33, layout: 'form',
						items: [{xtype : 'textfield',name : 'advertiserName',fieldLabel : '广告主名称',disabled:true,anchor : '99%'}]
					},
					{
						columnWidth: .34, layout: 'form',
						items: [{xtype : 'textfield',name : 'productLineName',fieldLabel : '产品线',disabled:true,anchor : '99%'}]
					},
					{
						columnWidth: .33, layout: 'form',
						items: [{xtype : 'textfield',name : 'productName',fieldLabel : '产品',disabled:true,anchor : '99%'}]
					}
				]
			},
			{
				layout: 'column',
				items: [
					{
						columnWidth: .33, layout: 'form',
						items: [
							{xtype : 'textfield',name : 'areaName',fieldLabel : '销售大区',disabled:true,anchor : '99%'}
						]
					},
					{
						columnWidth: .34, layout: 'form',
						items: [
							{xtype : 'textfield',name : 'ditchName',fieldLabel : '渠道销售',disabled:true,anchor : '99%'}
						]
					},
					{
						columnWidth: .33, layout: 'form',
						items: [
							{xtype : 'textfield',name : 'saleName',fieldLabel : '直客销售',disabled:true,anchor : '99%'}
						]
					}
				]
			},
			{
				layout: 'column',
				items: [
					{
						columnWidth: .33, layout: 'form',
						items: [{xtype : 'textfield',name : 'sum',fieldLabel : '金额',disabled:true,anchor : '99%'}]
					},
					{
						columnWidth: .34, layout: 'form',
						items: [{xtype : 'textfield',name : 'discount',fieldLabel : '折扣',disabled:true,anchor : '99%'}]
					},
					{
						columnWidth: .33, layout: 'form',
						items: [{xtype : 'textfield',name : 'sendRate',fieldLabel : '配送比例',disabled:true,anchor : '99%'}]
					}
				]
			},
			{
				layout: 'column',
				items: [
					{
						columnWidth: .33, layout: 'form',
						items: [
							new Ext.form.DateField({
								fieldLabel: '开始日期',name: 'startTime',disabled:true,format:'Y-m-d',anchor : '99%'
							})
						]
					},
					{
						columnWidth: .34, layout: 'form',
						items: [
							new Ext.form.DateField({
								fieldLabel: '结束日期',name: 'endTime',disabled:true,format:'Y-m-d',anchor : '99%'
							})
						]
					},
					{
						columnWidth: .33, layout: 'form',
						items: [{xtype : 'textfield',name : 'explains',fieldLabel : '形式需求',disabled:true,anchor : '99%',value: '40个'}]
					}
				]
			},
			{
				layout: 'column',
				items: [{columnWidth: 1, layout: 'form',items:[kpiGroup]}]
			}
		]
	});
	basicinfo_form.form.load({url: '/myads/HTML/advflight/BookAction_getProjectInfo.action',params: {id: projectId}});
    //基础信息------------------------------------结束------------------------
    
	//TODO 初始化排期信息TAB标签
	function initTabContainer(title, data, tab) {
		if(title == '选择位置') {
			if(!locationForm)
				createlocationForm();
			if(data != null) {
				locationForm.getForm().reset();
				advbarStore.removeAll();
				
				//广告条选择
				var _rs = new Ext.data.Record({
					advbarId: data.advbarId, advbarName: data.advbarName
				},data.advbarId) ;
				advbarStore.add(_rs);
				
				advbarGrid.getSelectionModel().selectFirstRow();
				
				//频道选择
				selectChannelId_global = data.channelId;
				selectAdvbarId_global = data.advbarId;
				
				tab.items.add(locationForm);
				locationForm.getForm().setValues(data);
				
				saleTypeCombStore.addListener('load', function(st, rds, opts) {
					saleTypeComb.setValue(data.saleType);
					saleTypeCombStore.removeListener('load');
				});
				
				priceFormatCombStore.addListener('load', function(st, rds, opts) {
					priceIdComb.setValue(data.priceId);
					priceFormatCombStore.removeListener('load');
				});
				
				saleTypeCombStore.load({params: {advbarId : data.advbarId}});
				priceFormatCombStore.load({params:{advbarId: data.advbarId, saleTypeValue : data.saleType, saleTypeName: data.saleTypeName}});
			}
			return locationForm;
		}
		else if(title == '定向策略') {
			if(!tab_item_directForm)
				createTabItemForm2();
			if(data != null) {
				tab_item_directForm.getForm().reset()
				tab_item_directForm.getForm().setValues(data);
				
				area_result_store.removeAll();
				var areaDirectObj = Ext.util.JSON.decode(data.areaDirect);
				for(var j = 0; j < areaDirectObj.length; j++) {
					var _rs = new Ext.data.Record(areaDirectObj[j]) ;
					area_result_store.add(_rs);
				}
				
				//设置为都不选中
				for(var i = 0; i < 24; i++) {
			   		var achk = tab_item_directForm.getForm().findField("hour_chk_" + i + '_' + (i+1));
			   		achk.setValue(false);
			   	}
				   	
			   	//选中已经有的
				var hours = data.hourDirect.split(',');
				for(var j = 0; j < hours.length; j++) {
					var achk = tab_item_directForm.getForm().findField("hour_chk_" + hours[j]);
					if(achk != null)
						achk.setValue(true);
				}
				
				tab.items.add(tab_item_directForm);
			}
			return tab_item_directForm;
		}
		else if(title == '内容策略') {
			if(!tab_item_content)
				createTabItemContent();
			if(data != null) {
				tab_item_content.getForm().reset()
				tab_item_content.getForm().setValues(data);
				tab.items.add(tab_item_content);
			}
			return tab_item_content;
		}
		else if(title == '选择点位') {
			if(!tab_item_point)
				createTabItemPoint();
			return tab_item_point;
		}
	}
	
    //TODO 删除排期包
    window.del_bookPackage = function(bookPackageId, delInfo) {
    	Ext.MessageBox.confirm('提示', '确定删除' + delInfo + ' ?', function(id) {
    		if(id != 'yes') return;
    		Ext.Ajax.request({
				method: 'post',
				url: '/myads/HTML/advflight/BookAction_deleteBookPackage.action',
				params: {bookPackageId: bookPackageId, projectId:projectId},
			   	success:function(resp){
			   		var obj=Ext.util.JSON.decode(resp.responseText);
			      	if(obj.result == 'success') {
			      		Ext.MessageBox.alert('提示', '删除成功！');
			      		bookPackage_ds.load({params: {start:0, limit:20}});
			      	}
			      	else {
			      		Ext.MessageBox.alert('错误', '删除错误！<br>' + obj.info);
			      	}
			   	}
			});
    	});
    }
    
    //TODO 修改优先级
    var updatePriorityWin;
    var updatePriorityForm;
    var oldPriority = 1;
    window.updatePriority = function(bookPackageId, priority) {
    	oldPriority = priority;
    	if(!updatePriorityForm) {
    		updatePriorityForm = new Ext.FormPanel({
    			labelAlign: 'right',
				region: 'center',
				autoScroll:true, 
				labelWidth: 100,
				frame: true,
				xtype: 'fieldset',
				items: [
					new Ext.form.NumberField({
						name: 'priority', fieldLabel: '优先级',allowBlank : false,anchor : '95%',
						listeners: {
							'change' : function(tf) {
								if(tf.getValue() != oldPriority)
									Ext.getCmp('updatePriorityBtn').setDisabled(false);
								else
									Ext.getCmp('updatePriorityBtn').setDisabled(true);
								
							}
						}
					})
				],
    			buttons: [
	    			{
	    				id: 'updatePriorityBtn',
	    				text: '保存',
	    				disabled: true,
	    				handler: function() {
	    					if(updatePriorityForm.getForm().findField('priority').getValue() == oldPriority)
	    						return;
	    						
	    					if(!updatePriorityForm.form.isValid())
	    						return;
	    						
	    					updatePriorityForm.form.submit( {
	    						url: '/myads/HTML/advflight/BookAction_updatePriority.action',
								method: 'post',
								waitTitle:'请等待',
								waitMsg: '正在提交...',
								params: {bookPackageId: bookPackageId},
								success: function(form, action) {
									if(action.result.result == 'success') {
										Ext.MessageBox.alert('结果', '保存成功！');
										form.reset();
										bookPackage_grid.getStore().reload();
										updatePriorityWin.hide();
							      	}
							      	else {
							      		if(action.result.info == 'BookConflict') {
							      			if(action.result.detailInfo.ConflictInfo == 'noLastContent') {
							      				Ext.MessageBox.show({
													title:'请等待',  
										            msg:'余量不足！！' + action.result.detailInfo.bookTime,  
										            width:240,  
										            progress:false,  
										            closable:true  
												});
							      			}
							      			else if(action.result.detailInfo.ConflictInfo == 'SaledByOtherType') {
							      				Ext.MessageBox.show({
													title:'请等待',  
										            msg:'已经被其它方式售卖！！' + action.result.detailInfo.bookTime,  
										            width:240,  
										            progress:false,  
										            closable:true  
												});
							      			}
							      		}
							      		else {
							      			Ext.MessageBox.alert('出错了！！！', '保存失败！！！！');
							      		}
							      	}
								}
	    					});
	    				}
	    			},
	    			{
	    				text: '取消',
	    				handler: function() {
	    					updatePriorityForm.getForm().reset();
	    					updatePriorityWin.hide();
	    				}
	    			}
    			]
    		})
    	}
    	
    	if(!updatePriorityWin) {
    		updatePriorityWin = new Ext.Window({      
    			title:'优先级修改',                          
	   			width:300,                            
	   			height:100,                            
	   			draggable:true,                           
	   			modal : true,
	   			closeAction: 'hide',
	   			items: [updatePriorityForm]
    		});
    	}
    	
    	updatePriorityWin.show();
    	updatePriorityForm.getForm().findField('priority').setValue(priority);
    }
    
    //TODO 加载排期包的信息
	window.loadBookPackageInof = function(bookPackageId, scrBPackageId) {
		//1. 位置信息
		//2. 定向策略
		//3. 内容策略
		//4. 点位信息
		bookPackageId_global = bookPackageId;
		Ext.MessageBox.show({  
            title:'请等待',  
            msg:'读取数据中……',  
            width:240,  
            progress:false,  
            closable:false  
        });  
	                
		Ext.Ajax.request({
			method: 'post',
			url: '/myads/HTML/advflight/BookAction_getBookPackageInfo.action',
			params: {bookPackageId: bookPackageId},
		   	success:function(resp){
		   		var obj=Ext.util.JSON.decode(resp.responseText);
		   		param_priority = obj.data.priority;
		   		
		   		var saveBtn = Ext.getCmp('saveBtn');
		   		if(obj.CanSave) {
		   			saveBtn.show();
		   		}
		   		else {
		   			saveBtn.hide();
		   		}
		   		
		   		for(var i = 0; i < tabs.items.length; i++) {
					tabContent = initTabContainer(tabs.getItem(i).title, obj.data, tabs.getItem(i));
				}
				tabs.setActiveTab(1);
				tabs.doLayout();
				
				point_ds.load({params:{start:0, limit:200000, bookPackageId:bookPackageId}});
				
				point_ds.on("load", function(s,records) {
					selectDayArr = new Array();
					point_ds.each(function(rec){
				    	selectDayArr.push(rec.data.startTime);
				   	});
					point_ds.removeListener('load');
					refreshCalandar();
					refreshSumFlight();
				});
				
				saleTypeComb.disabled = true;
				channelGrid.hide();
				siteGrid.hide();
				advbarGrid.disabled = true;
				
            	Ext.MessageBox.hide();
		   	}
		});
		
		getRelationBookPackageInfo(scrBPackageId);
	}
	
	window.getRelationBookPackageInfo = function (scrBPackageId) {
		if(scrBPackageId == '')
			return;
			
		Ext.Ajax.request({
			method: 'post',
			url: '/myads/HTML/advflight/BookAction_getBookPackageInfo.action',
			params: {bookPackageId: scrBPackageId},
		   	success:function(resp){
		   		var obj=Ext.util.JSON.decode(resp.responseText);
		   		locationForm.getForm().findField("scrBPackageId").setValue(obj.data.id);
				locationForm.getForm().findField("scrBPackageName").setValue(obj.data.id + '--' + obj.data.channelName + "--" + obj.data.advbarName);
				selRelationBPBtn.setDisabled(false);
		   	}
		});
	}
	
	function handleActivate(tab){
		if(!locationForm.getForm().isValid()) {
			tabs.setActiveTab(1);
		}
		
		tab.doLayout();
    }
	
    var tabs = new Ext.TabPanel({
		renderTo: 'north-div',
		autoHeight : false,
		height:350,
		frame:true,
		region:'north',
        activeTab: 0,
        split: true,
        layoutOnTabChange : true ,
        defaults:{autoScroll: true},
        deferredRender :false,
		items: [
			{
				title: '基础信息',
				items:[basicinfo_form]
			},
			{
				id: 'locationTab',
				title: '选择位置',
				items:[locationForm]
			},
			{
				id: 'directTab',
				title: '定向策略',
				listeners: {activate: handleActivate},
				items:[tab_item_directForm]
			},
			{
				title: '内容策略',
				listeners: {activate: handleActivate},
				items:[tab_item_content]
			},
			{
				title: '选择点位',
				listeners: {activate: handleActivate},
				items:[tab_item_point]
			}
		],
		buttons: [
//			{
//				text: '新建',
//				handler: function() {
//					newBook();
//				}
//			},
			{
				id: 'saveBtn',
				text: '保存',
				handler: function() {
					Ext.MessageBox.confirm("提示", "确定保存？", function(id) {
						if(id == 'yes') {
							submitBook();
						}
					});
				}
			}
		]
	});
	
	//排期GRID----------------------------------------开始------------------------
	var cm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{header:'频道', dataIndex:'channelName', sortable:true, menuDisabled:true},
		{header:'广告条', dataIndex:'advbarName', sortable:true, menuDisabled:true},
		{header:'使用类型', dataIndex:'useTypeName', sortable:true, menuDisabled:true},
	    {header:'售卖方式', dataIndex:'saleTypeName', sortable:true, menuDisabled:true},
	    {header:'刊例价', dataIndex:'price', sortable:true, menuDisabled:true},
	    {header:'折扣', dataIndex:'discount', sortable:true, menuDisabled:true},
	    {header:'定向策略', dataIndex:'dataName', renderer: renderOp1, menuDisabled:true},
	    {header:'内容策略', dataIndex:'dataValue', renderer: renderOp2, menuDisabled:true},
	    {header:'优先级', id:'priorityCM', dataIndex:'priority', sortable:true, menuDisabled:true},
	    {header:'投放时期', dataIndex:'startTime', sortable:true, menuDisabled:true,
	    	renderer: function(value, metadata, record, rowIndex, colIndex, store) {
            	return record.get("startTime") + "---" + record.get("endTime");
        	}
	    },
	    {header:'操作', dataIndex:'op', renderer: renderOp,width:150, align:'left', menuDisabled:true}
	]);
	
	if(canUpdatePriority != 'true') {
		cm.setHidden(9, true);
	}
			
	// 定向策略
	function renderOp1(value, cellmeta, record, rowIndex, columnIndex, stor) {
		var title = '定向策略:';
		
		var frequencyStr = '';
		if(record.get('isFrequency') == 1) {//频次定向
			var frequencyType = record.get('frequencyTypeName');
			frequencyStr = frequencyType + ', ' + record.get('frequencyNum');
		}
			
		var areaDirectStr = '';
		var areaDirectObj = Ext.util.JSON.decode(record.get('areaDirect'));
		for(var j = 0; j < areaDirectObj.length; j++) {
			areaDirectStr += areaDirectObj[j].display + '; ';
		}
		
		var tip = '区域定向：' +  areaDirectStr + '<br>' 
			+ '时间定向：' +  record.get('hourDirect') + '<br>' 
			+ '频次定向: ' + frequencyStr;    
		cellmeta.attr = 'ext:qtitle="' + title + '"' + ' ext:qtip="' + tip + '"';    
		return '<a style="color: #44CC44;cursor: pointer;">详细</a>'; 
	}
	
	// 内容策略
	function renderOp2(value, cellmeta, record, rowIndex, columnIndex, stor) {
		if(record.get('isContentDirect') != 1)
			return '';
		var title = '内容策略';    
		var tip = '关键字：' +  record.get('keyword') + '<br>' 
				+ '用户：' +  record.get('user') + '<br>'
				+ '视频id：' +  record.get('video') + '<br>'
				+ '节目：' +  record.get('program') + '<br>'
				+ '活动：' +  record.get('activity') + '<br>'
				+ '专题：' +  record.get('subject') + '<br>';
		cellmeta.attr = 'ext:qtitle="' + title + '"' + ' ext:qtip="' + tip + '"';    
		return '<a style="color: #44CC44;cursor: pointer;">详细</a>'; 
	}
		
	function renderOp(value, cellmeta, record, rowIndex, columnIndex, stor) {
		var bookPackageId = record.data.id;
		var scrBPackageId = record.data.scrBPackageId;
		var delInfo = record.data.advbarName ; 
		
		var startTimeStr = '' + record.data.startTime;
		startTimeStr = startTimeStr.replace(/-/g, "/");
		var startTime = new Date(startTimeStr);
		
		var endTimeStr = '' + record.data.endTime;
		endTimeStr = endTimeStr.replace(/-/g, "/");
		var endTime = new Date(endTimeStr);
		
		var nowTime = new Date();
		var _temps1 = Ext.util.Format.date(nowTime, "Y/m/d");
		var nowDate = new Date(_temps1);
		
		var delStr = '';
		if (Date.parse(startTime) > Date.parse(nowDate) || Date.parse(startTime) < Date.parse(nowDate)) {
			if (Date.parse(endTime)-Date.parse(nowDate) > 0) { //有未执行的，可以删除
				delStr = '<a href="#" onclick=\"del_bookPackage(\''+bookPackageId+'\', \'' + delInfo + '\');\">删除</a>';
			}
		}
		
		var updateStr = '<a href="#" onclick=\"loadBookPackageInof(\''+bookPackageId+'\', \'' + scrBPackageId + '\');\">修改</a>';
		if(Date.parse(startTime)-Date.parse(nowDate) < 0)
			updateStr = '<a href="#" onclick=\"loadBookPackageInof(\''+bookPackageId+'\', \'' + scrBPackageId + '\');\">查看</a>';
		
		var updatePriority = '';
		if(canUpdatePriority == 'true')	
			updatePriority = '<a href="#" onclick=\"updatePriority(\''+bookPackageId+'\', \''+record.data.priority+'\');\">修改优先级</a>';
		
		return updateStr + '&nbsp;&nbsp;&nbsp;&nbsp;' + delStr + '&nbsp;&nbsp;&nbsp;&nbsp;' + updatePriority;
	}
	
	var bookPackage_ds = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({url: '/myads/HTML/advflight/BookAction_showAllBookPackage.action'}),
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
				{name: 'price'},
				{name: 'discount'},
				{name: 'startTime'},
				{name: 'endTime'},
				{name: 'areaDirect'},
				{name: 'hourDirect'},
				{name: 'priority'},
				{name: 'isContentDirect'},
				{name: 'isFrequency'},
				{name: 'frequencyType'},
				{name: 'frequencyTypeName'},
				{name: 'frequencyNum'},
				{name: 'scrBPackageId'},
				{name: 'keyword'},
				{name: 'user'},
				{name: 'video'},
				{name: 'program'},
				{name: 'activity'},
				{name: 'subject'}
			]
		)
	});

	bookPackage_ds.baseParams = {projectId: projectId};
	bookPackage_ds.load({params: {start:0, limit:20}});
	
	var bookPackage_grid = new Ext.grid.GridPanel({
		el: 'bookPackage_grid',
		ds: bookPackage_ds,
		cm: cm,
	    viewConfig: {
	    	forceFit: true
	    },
	    bbar: new Ext.PagingToolbar({
		    pageSize: 20,
		    store: bookPackage_ds,
			displayInfo: true,
			displayMsg: '显示第{0}条到{1}条记录,一共{2}条',
			emptyMsg: '没有记录'
	    }),
	    tbar: new Ext.Toolbar({
			buttons : [
				{
					id: 'ProjectVerifySubmitBtn',
					text: '提交审核',
					handler: function(btn) {
							Ext.MessageBox.confirm("提示", "确定提交审核？", function(id) {
								if(id == 'yes') {
									btn.disable();
									Ext.Ajax.request({
										method: 'post',
										url: '/myads/HTML/verify/ProjectVerifyAction_submit.action',
									   	success:function(resp){
									    	var obj=Ext.util.JSON.decode(resp.responseText);
									      	if(obj.result == 'success') {
									      		Ext.MessageBox.alert('提示', '提交成功！',function() {
									      			window.history.back();
									      		});
									      	}else{
									      		Ext.MessageBox.alert('提示', '提交失败！',function() {
									      			window.history.back();
									      		});
									      	}
						    			},
						   				params: {projectId: projectId}
								});
							}
						});
					}
				},
				'-', 
				{
					text: '生成点位预览',
					handler: function() {
						refrshPreviewGridTab();
					}
				}
			]
		})
	});
	//排期GRID----------------------------------------结束-----------------------
	
	//---------------------------------------
	var test_grid = null; 
	function refrshPreviewGridTab() {
		Ext.MessageBox.show({  
            title:'请等待',  
            msg:'读取数据中……',  
            width:240,  
            progress:false,  
            closable:false  
        });  
        
		Ext.Ajax.request({
			method: 'post',
			url: '/myads/HTML/advflight/PointGridGroupPreviewAction_getPointGrid.action',
			params:{projectId:projectId},
		   	success:function(resp){
		   		var jsonData = Ext.util.JSON.decode(resp.responseText);
		   		preview_grid = createGroupGrid(jsonData);
		   		gridTab.remove("点位预览");
				gridTab.add({
					id: '点位预览',
					title: '点位预览',
					layout: 'fit',
					items:[preview_grid]
				});
				gridTab.setActiveTab("点位预览");
				
				(function(){
					var girdcount = 0;
					preview_grid.getStore().each(function(r){
						
			            if(r.get('useTypeName')=='配送'){
			                preview_grid.getView().getRow(girdcount).style.backgroundColor='#99CC00';
			            }
			            
			            for(var j = 0; j < preview_grid.plugins.config.rows[1].length; j++) {
			            	if(preview_grid.plugins.config.rows[1][j].header == 'S') {
			            		preview_grid.getView().getCell(girdcount, j).style.backgroundColor='#FFFF00';
			            	}
			            }
			            
			            girdcount=girdcount+1;
			        });
				}).defer(500);
				
				Ext.MessageBox.hide();
		   	}
		});
	}
	
	function createGroupGrid(jsonData) {
        var groupRows = [];
        groupRows[0] = jsonData.topGroup;
        groupRows[1] = jsonData.secondGroup;
        
        var group = new Ext.ux.grid.ColumnHeaderGroup({
            rows: groupRows
        });

        var grid = new Ext.grid.GridPanel({
        	region: 'center',
        	split: true,
	        border:false,
            store: new Ext.data.JsonStore({
                fields: jsonData.fields,
                data: jsonData.data
            }),
            columns: jsonData.columns,
            plugins: group,
            tbar: new Ext.Toolbar({
				items : [
					{
						text: '导出Excel',
						handler: function() {
							window.location.href = '/myads/HTML/advflight/PointGridGroupPreviewAction_exportExcel.action?projectId=' + projectId; 
						}
					}
				]
	        })
        });
        
        return grid;
	}
	
	//---------------------------------------
	var gridTab = new Ext.TabPanel({
		region:'center',
		frame:true,
        activeTab: 0,
        layoutOnTabChange : true ,
        defaults:{autoScroll: true},
        deferredRender :false,
		items: [
			{
				title: '排期信息',
				layout: 'fit',
				items:[bookPackage_grid]
			}
		]
	});
	
	new Ext.Viewport({
		layout: 'border',
		items:[
		       tabs,gridTab
		]
	});
	
	//TODO 保存排期
	function submitBook() {
		var fo = new Object();
		if(locationForm) {
			
			if(!locationForm.getForm().isValid()) {
				tabs.setActiveTab('locationTab');
				return;
			}
			
			Ext.apply(fo, locationForm.getForm().getValues());
			
			fo.saleTypeName = Ext.get('saleTypeCombId').dom.value;
			fo.useTypeName = Ext.get('useTypeCombId').dom.value;
			
			if(selectAdvbarId_global == null && !advbarGrid.getSelectionModel().hasSelection()) {
				Ext.MessageBox.alert('提示', '必须选择广告条!');
				return;
			}
			
			var record = advbarGrid.getSelectionModel().getSelected();
			if(record) {
				fo.advbarId = record.data.advbarId;
				fo.advbarName = record.data.advbarName;
			}
			else {
				fo.advbarId = selectAdvbarId_global;
			}
			
			var channelRecord = channelGrid.getSelectionModel().getSelected();
			if(channelRecord) {
				fo.channelId = channelRecord.data.channelId;
			}
			else {
				fo.channelId = selectChannelId_global;
			}
			
			var rrr = priceIdComb.findRecord(priceIdComb.valueField || priceIdComb.displayField, priceIdComb.getValue());
			fo.priceId = rrr.data.value;
			fo.format = rrr.data.saleTypeBaseData;
		}
		else {
			Ext.MessageBox.alert('提示', '必须选择广告条!');
			return;
		}
		
		if(tab_item_directForm && tab_item_directForm.getForm().getEl()) {
			if(!tab_item_directForm.getForm().isValid()) {
				tabs.setActiveTab('directTab');
				return;
			}
			
			Ext.apply(fo, tab_item_directForm.getForm().getValues());
			//area_result_store 区域定向结果框
			var areaResult = [];
		    area_result_store.each(function(rec){
		    	areaResult.push(rec.data);
		   	});
		   	fo.areaDataAry = Ext.util.JSON.encode(areaResult);
		   	
		   	var _chhours = '';
		   	for(var i = 0; i < 24; i++) {
		   		var achk = tab_item_directForm.getForm().findField("hour_chk_" + i + '_' + (i+1));
		   		if(achk && achk.checked) {
		   			_chhours += achk.inputValue;
		   		}
		   	}
		   	fo.hourDirect = _chhours;
		}
		if(tab_item_content && tab_item_content.getForm().getEl()) {
			var isContentDirectChk = tab_item_content.getForm().findField("isContentDirect");
			if(isContentDirectChk.checked) {
				if(tab_item_content.getForm().findField("keyword").getValue() == '' 
					&& tab_item_content.getForm().findField("user").getValue() == '' 
					&& tab_item_content.getForm().findField("video").getValue() == ''
					&& tab_item_content.getForm().findField("program").getValue() == ''
					&& tab_item_content.getForm().findField("activity").getValue() == ''
					&& tab_item_content.getForm().findField("subject").getValue() == '' 
				) {
					Ext.MessageBox.alert('提示', '请填写内容定向!');
					return;
				}
			}
			
			Ext.apply(fo, tab_item_content.getForm().getValues());
		}
			
		//点位数据	
		var selFuns = [];
		if(tab_item_point) {
		    point_ds.each(function(rec){
		    	selFuns.push(rec.data);
		   	});
		   	fo.pointDataAry = Ext.util.JSON.encode(selFuns);
			fo.bookPackageId = bookPackageId_global;
		}
		
		if(selFuns.length == 0) {
			Ext.MessageBox.alert('提示', '请选择点位!');
			return;
		}
		
		fo.projectId = projectId;
		fo.paramPriority = param_priority;
			
		Ext.MessageBox.show({
			title:'请等待',  
            msg:'保存数据中……',  
            width:240,  
            progress:false,  
            closable:false  
		});
        
		Ext.Ajax.request({
			method: 'post',
			url: '/myads/HTML/advflight/BookAction_save.action',
			params: fo,
		   	success:function(resp){
		   		Ext.MessageBox.hide();
		    	var obj=Ext.util.JSON.decode(resp.responseText);
		      	if(obj.result == 'success') {
		      		Ext.MessageBox.alert('提示', '保存成功！', function(id) {
							resetBook();
							bookPackage_ds.baseParams = {projectId: projectId};
							bookPackage_ds.load({params: {start:0, limit:20}});
						}
					);
		      	}
		      	else {
		      		if(obj.info == 'BookConflict') {
		      			if(obj.detailInfo.ConflictInfo == 'noLastContent') {
		      				Ext.MessageBox.show({
								title:'请等待',  
					            msg:'余量不足！！' + obj.detailInfo.bookTime,  
					            width:240,  
					            progress:false,  
					            closable:true  
							});
		      			}
		      			else if(obj.detailInfo.ConflictInfo == 'SaledByOtherType') {
		      				Ext.MessageBox.show({
								title:'请等待',  
					            msg:'已经被其它方式售卖！！' + obj.detailInfo.bookTime,  
					            width:240,  
					            progress:false,  
					            closable:true  
							});
		      			}
		      		}
		      		else {
		      			Ext.MessageBox.alert('出错了！！！', '保存失败！！！！');
		      		}
		      	}
		    }
		});
	}
	
	function newBook() {
		saleTypeComb.disabled = false;
		channelGrid.show();
		advbarGrid.disabled = false;
		advbarGrid.removeAll();
		locationForm.form.reset();
		tab_item_directForm.form.reset();
		tab_item_content.form.reset();
		tab_item_point.form.reset();
		advbarStore.removeAll();
		area_result_store.removeAll();
		point_ds.removeAll();
	}
	
	//保存成功后的处理
	function resetBook() {
		window.location.href = '/myads/HTML/advflight/advflight.jsp?projectId=' + projectId;
//		newBook();
	}
});