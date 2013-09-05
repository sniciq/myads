Ext.namespace("com.myads.book.LocationFormPanel"); 
com.myads.book.LocationFormPanel = function(config) {
	var locationForm = this;
	this.addEvents('selectAdvbar');
	this.addEvents('selectSaleType');
	
      //网站
    var siteStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({url: '/myads/HTML/sysfun/SiteAction_getSiteList.action'}),
		reader: new Ext.data.ArrayReader({}, [{name: 'siteId'},{name: 'siteName'}])
	});
	siteStore.load();
	
	var siteGrid = this.siteGrid = new Ext.grid.GridPanel({
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
	});
	
    // 频道
	var channelStore = new Ext.data.ArrayStore({
		fields : [{name: 'channelId'},{name: 'name'}]
	});
	
	var channelGrid = this.channelGrid = new Ext.grid.GridPanel({
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
	});
      
    // 售卖方式
	var saleTypeCombStore = this.saleTypeCombStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({url: '/myads/HTML/basic/OptionsAction_getAdvbarSaleTypes.action'}),
		reader: new Ext.data.ArrayReader({}, [
			{name: 'value'},{name: 'text'}
		])
	});
	
	var formatCombStore_lo = this.formatCombStore_lo = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({url: '/myads/HTML/basic/OptionsAction_getBaseDataNamesByType.action'}),
		reader: new Ext.data.ArrayReader({}, [
			{name: 'value'},{name: 'text'}
		])
	});
	
	var formatComb_lo = this.formatComb_lo = new Ext.form.ComboBox({
   		fieldLabel: '形&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;式',
		name: 'format',
		mode: 'local',
		triggerAction: 'all', 
		editable: false,
		allowBlank : false,
		valueField: 'value',
		displayField: 'text',
		anchor : '99%',
		store: formatCombStore_lo
	});
	
	var useTypeCombStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({url: '/myads/HTML/basic/OptionsAction_getBaseDataNamesByType.action'}),
		reader: new Ext.data.ArrayReader({}, [{name: 'value'},{name: 'text'}])
	});
	useTypeCombStore.load({params: {dataType: 'usetype'}});
    
     // 广告条
	var advbarStore = this.advbarStore = new Ext.data.ArrayStore({
	      fields: [{name: 'advbarId'},{name: 'advbarName'}, {name: 'isBackground'}]
	});
	
    var advbarGrid = this.advbarGrid = new Ext.grid.GridPanel({
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
//            	saleTypeCombStore.load({params: {advbarId : advbarId}});
            	locationForm.fireEvent('selectAdvbar', advbarId);
            	selRelationBPBtn.setDisabled(false);
            }
        }
	});
	
    var saleTypeComb = this.saleTypeComb = new Ext.form.ComboBox({
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
            	ComboBox.setValue(value);
            }
        }
   });
   
   var priceNameStore = this.priceNameStore  = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({url: '/myads/html/struts/PriceAction_search.action'}),
		reader: new Ext.data.JsonReader(
			{
				totalProperty: 'total',
				idProperty:'id',
				root: 'invdata'
			},
			[
				{name: 'id'},
				{name: 'priceName'},
				{name: 'price'},
				{name: 'format'},
				{name: 'formatName'},
				{name: 'saleTypeValue'},
				{name: 'saleTypeName'}
			]
		)
	});
   
   var priceNameCombox = this.priceNameCombox = new Ext.form.ComboBox({
		fieldLabel: '刊例名称',
		name: 'priceName',
		anchor : '99%',
		store: priceNameStore,
		typeAhead: true,
		mode: 'local',
		triggerAction: 'all',
		emptyText:'输入刊例名称...',
		displayField: 'priceName',
		selectOnFocus:true,
		hideTrigger: true,
		listeners : {
			select : function(comboBox, record, index) {
				locationForm.getForm().reset();
				
				comboBox.setValue(record.data.priceName);
				locationForm.getForm().findField("price").setValue(record.data.price);
				
				saleTypeCombStore.removeAll();
				var _rs = new Ext.data.Record({
					text: record.data.saleTypeName, value: record.data.saleTypeValue
				},record.data.saleTypeValue) ;
				saleTypeCombStore.add(_rs);
				saleTypeComb.setValue(record.data.saleTypeValue);
				
				locationForm.formatCombStore_lo.removeAll();
				var _rs_format = new Ext.data.Record({
					text: record.data.formatName, value: record.data.format
				},record.data.format) ;
				locationForm.formatCombStore_lo.add(_rs_format);
				locationForm.formatComb_lo.setValue(record.data.format);
				
				if(advbarGrid.getSelectionModel().getSelected()) {
					var advbarId = advbarGrid.getSelectionModel().getSelected().data.advbarId;
					locationForm.fireEvent('selectSaleType', advbarId, record.data.saleTypeValue, record.data.saleTypeName);
				}
			},
			keyUp: function(comboBox, e){
				if(e.button == 12 || e.button == 36 || e.button == 37 || e.button == 38 || e.button == 39)
					return;
				priceNameStore.load({params: {priceName: comboBox.getValue(), start: 0, limit: 10}});
			}
		}
	});
    
    
	var advflightRelationPanelWin;
	var advflightRelationPanel;
	var selRelationBPBtn = this.selRelationBPBtn = new Ext.Button({
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
	
	Ext.apply(this, {
		frame: true,
		labelWidth: 60,
		labelAlign: 'right',
		items: [
			{
				layout: 'column',
				items: [
					siteGrid,
					channelGrid,
					advbarGrid
				]
			},
			{
				layout: 'column',
				items: [
					{
						columnWidth: .20, layout: 'form',
						items:[
							priceNameCombox
						]
					},
					{
						columnWidth: .20, layout: 'form',labelAlign: 'left',
						items:[
							saleTypeComb
						]
					},
					{
						columnWidth: .20, layout: 'form',
						items: [
							formatComb_lo
						]
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
						items:[
							{
								xtype : 'textfield',
								name : 'price',
								fieldLabel : '刊例单价',
								anchor : '99%',
								style:'color:red;',
								readOnly:true,
								allowBlank : false
							}
						]
					},
					{
						columnWidth: .20, layout: 'form',
						items:[
							{
								xtype : 'textfield',
								name : 'discount',
								fieldLabel : '折扣(%)',
								anchor : '99%',
								allowBlank : false,
//								value: '100',
								listeners:{
									blur: function(){
										var discount = locationForm.getForm().findField("discount").getValue();
										var advbarPrice = locationForm.getForm().findField("price").getValue();
										var result = discount * advbarPrice / 100;
										locationForm.getForm().findField("disprice").setValue(result);						
									}
								}
							}
						]
					},
					{
						columnWidth: .20, layout: 'form',
						items: [
							{
								xtype : 'textfield',
								name : 'disprice',
								fieldLabel : '折扣单价',
								anchor : '99%',
								style:'color:red;',
								readOnly:true
	//							disabled:true
							}
						]
					}
				]
			},
			{
				layout: 'column',
				items: [
					{
						columnWidth: .20, layout: 'form',
						items: [
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
					},
					{
						columnWidth: .20, layout: 'form',
						items: [
							{xtype : 'textfield',fieldLabel: '关联排期',name: 'scrBPackageName',anchor : '99%',maxLength:100}
						]
					},
					{
						columnWidth: .2, layout: 'form',
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
    
	com.myads.book.LocationFormPanel.superclass.constructor.apply(this, arguments);
}

Ext.extend(com.myads.book.LocationFormPanel, Ext.FormPanel, {
	onRender: function() {
		com.myads.book.LocationFormPanel.superclass.onRender.apply(this, arguments);
	},
	
	getFormValues: function() {
		var myfo = new Object();
		Ext.apply(myfo, this.getForm().getValues());
		
		myfo.saleTypeName = Ext.get('saleTypeCombId').dom.value;
		myfo.useTypeName = Ext.get('useTypeCombId').dom.value;
		
		var record = this.advbarGrid.getSelectionModel().getSelected();
		if(record) {
			myfo.advbarId = record.data.advbarId;
			myfo.advbarName = record.data.advbarName;
		}
		
		var channelRecord = this.channelGrid.getSelectionModel().getSelected();
		if(channelRecord) {
			myfo.channelId = channelRecord.data.channelId;
		}
		
		var rrr = this.formatComb_lo.findRecord(this.formatComb_lo.valueField || this.formatComb_lo.displayField, this.formatComb_lo.getValue());
		myfo.format = rrr.data.value;
		
		var rp = this.priceNameCombox.findRecord(this.priceNameCombox.valueField || this.priceNameCombox.displayField, this.priceNameCombox.getValue());
		if(rp) {
			myfo.priceId = rp.data.id;
		}	
			
		return myfo;
	},
	
	setFormData: function(data) {
		var locationForm = this;
		this.getForm().reset();
		this.advbarStore.removeAll();
		
		//广告条选择
		var _rs = new Ext.data.Record({
			advbarId: data.advbarId, advbarName: data.advbarName
		},data.advbarId) ;
		this.advbarStore.add(_rs);
		
		this.advbarGrid.getSelectionModel().selectFirstRow();
		
		this.getForm().setValues(data);
		
		
		this.formatCombStore_lo.addListener('load', function(st, rds, opts) {
			locationForm.formatComb_lo.setValue(data.format);
			locationForm.formatCombStore_lo.removeListener('load');
		});
		
		var _rs2 = new Ext.data.Record({
			id: data.priceId, priceName: data.priceName
		},data.priceId) ;
		this.priceNameStore.add(_rs2);
		
		var _rSaleType = new Ext.data.Record({
			text: data.saleTypeName, value: data.saleType
		},data.saleType) ;
		
		this.saleTypeCombStore.add(_rSaleType);
		this.saleTypeComb.setValue(data.saleType);
		
		this.formatCombStore_lo.load({params:{dataType: data.saleTypeName}});
		
		this.saleTypeComb.disabled = true;
		this.channelGrid.hide();
		this.siteGrid.hide();
		this.advbarGrid.disabled = true;
	}
});
