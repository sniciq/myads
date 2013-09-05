Ext.namespace("com.myads.book.AdvprudctSelectPanel"); 
com.myads.book.AdvprudctSelectPanel = function(config) {
	var advprudctSelectForm = this;
	this.addEvents('selectSaleType');
	this.addEvents('selectAdvproduct');
	
	var advproductStore = this.advproductStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({url: '/myads/HTML/advproduct/AdvproductAction_searchByName.action'}),
		reader: new Ext.data.JsonReader(
			{
				totalProperty: 'total',
				idProperty:'id',
				root: 'invdata'
			},
			[
				{name: 'id'},
				{name: 'advproductName'},
				{name: 'type'},
				{name: 'advCount'},
				{name: 'createTime'},
				{name: 'creator'},
				{name: 'advbarCount'},
				{name: 'status'}
			]
		)
	});
	
	var advproductComb = this.advproductComb = new Ext.form.ComboBox({
		fieldLabel: '产品名称',
		name: 'advproductName',
		anchor : '99%',
		store: advproductStore,
		typeAhead: true,
		mode: 'local',
		triggerAction: 'all',
		emptyText:'输入产品名称...',
		displayField: 'advproductName',
		selectOnFocus:true,
		hideTrigger: true,
		listeners : {
			select : function(comboBox, record, index) {
				advprudctSelectForm.fireEvent('selectAdvproduct', record.data);
			},
			keyUp: function(comboBox, e){
				var type = advprudctSelectForm.getForm().findField("type").getValue();
				if(e.button == 12 || e.button == 36 || e.button == 37 || e.button == 38 || e.button == 39)
					return;
				advproductStore.load({params: {status: 0, type: type,advproductName: comboBox.getValue(), start: 0, limit: 10}});
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
	
	var priceCombox = this.priceCombox = new Ext.form.ComboBox({
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
				comboBox.setValue(record.data.priceName);
				saleTypeCombStore.removeAll();
				var _rs = new Ext.data.Record({
					text: record.data.saleTypeName, value: record.data.saleTypeValue
				},record.data.saleTypeValue) ;
				saleTypeCombStore.add(_rs);
				saleTypeComb.setValue(record.data.saleTypeValue);
				
				formatCombStore.removeAll();
				var _rs_format = new Ext.data.Record({
					text: record.data.formatName, value: record.data.format
				},record.data.format) ;
				formatCombStore.add(_rs_format);
				formatComb.setValue(record.data.format);
				
				advprudctSelectForm.fireEvent('selectSaleType');
				advprudctSelectForm.getForm().findField("price").setValue(record.data.price);
			},
			keyUp: function(comboBox, e){
				if(e.button == 12 || e.button == 36 || e.button == 37 || e.button == 38 || e.button == 39)
					return;
				priceNameStore.load({params: {priceName: comboBox.getValue(), start: 0, limit: 10}});
			}
		}
	});

	// 售卖方式
	var saleTypeCombStore = this.saleTypeCombStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({url: '/myads/HTML/basic/OptionsAction_getBaseDataNamesByType.action'}),
		reader: new Ext.data.ArrayReader({}, [
			{name: 'value'},{name: 'text'}
		])
	});
	
	var saleTypeComb = this.saleTypeComb = new Ext.form.ComboBox({
		fieldLabel: '售卖方式',
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
	        	var value = record.data.text;
	        }
		}
	});
	
	var formatCombStore = this.formatCombStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({url: '/myads/HTML/basic/OptionsAction_getBaseDataNamesByType.action'}),
		reader: new Ext.data.ArrayReader({}, [
			{name: 'value'},{name: 'text'}
		])
	});
	
	var formatComb = this.formatComb = new Ext.form.ComboBox({
   		fieldLabel: '形&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;式',
		name: 'format',
		hiddenName: 'format', 
		mode: 'local',
		triggerAction: 'all', 
		editable: false,
		allowBlank : false,
		valueField: 'value',
		displayField: 'text',
		anchor : '99%',
		store: formatCombStore
	});
	
	var useTypeCombStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({url: '/myads/HTML/basic/OptionsAction_getBaseDataNamesByType.action'}),
		reader: new Ext.data.ArrayReader({}, [{name: 'value'},{name: 'text'}])
	});
	useTypeCombStore.load({params: {dataType: 'usetype'}});
	
	var useTypeCom = new Ext.form.ComboBox({
   		fieldLabel: '使用方式',
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
   });
	
	Ext.apply(this, {
		frame: true,
		labelWidth: 60,
		labelAlign: 'right',
		xtype: 'fieldset',
		layout: 'form',
		items: [
			{
					layout: 'column',
					items: [
						{
							columnWidth: .20, layout: 'form',
							items:[
								new Ext.form.ComboBox({
									fieldLabel: '产品类别',
									name: 'type',
									hiddenName: 'type',
									anchor : '99%',
									triggerAction: 'all', 
									editable: false,
									mode: 'local',
									allowBlank : false,
									valueField: 'value',
									displayField: 'text',
									store: new Ext.data.SimpleStore({
										fields: ['value', 'text'],
										data: [['A', 'A类'],['B', 'B类']]
									})
								})
							]
						},
						{
							columnWidth: .40, layout: 'form',
							items:[
								advproductComb
							]
						}
					]
			},
			{
				layout: 'column',
				items: [
					{
						columnWidth: .20, layout: 'form',
						items:[
							priceCombox
						]
					},
					{
						columnWidth: .20, layout: 'form',
						items:[
							saleTypeComb
						]
					},
					{
						columnWidth: .20, layout: 'form',
						items:[
							formatComb
						]
					}
				]
			},
			{
				layout: 'column',
				items: [
					{
						columnWidth: .20, layout: 'form',
						items:[
							{ xtype: 'textfield', name: 'price', readOnly:true, fieldLabel: '刊例价格',allowBlank : false, anchor : '99%'}
						]
					},
					{
						columnWidth: .20, layout: 'form',
						items:[
							{ 
								xtype: 'textfield', name: 'discount', fieldLabel: '折扣(%)',allowBlank : false, anchor : '99%', 
								listeners:{
									blur: function(){
										var discount = advprudctSelectForm.getForm().findField("discount").getValue();
										var advbarPrice = advprudctSelectForm.getForm().findField("price").getValue();
										var result = discount * advbarPrice / 100;
										advprudctSelectForm.getForm().findField("disprice").setValue(result);
									}
								}
							}
						]
					},
					{
						columnWidth: .20, layout: 'form',
						items:[
							{ xtype: 'textfield', name: 'disprice', readOnly:true, fieldLabel: '折扣单价',allowBlank : false, anchor : '99%'}
						]
					}
				]
			},
			{
				layout: 'column',
				items: [
					{
						columnWidth: .20, layout: 'form',
						items:[
							useTypeCom
						]
					}
				]
			},
			{
				layout: 'column',
				items: [
					{
						columnWidth: .60, layout: 'form',
						items:[
							{ xtype: 'textfield', name: 'remark', fieldLabel: '备注',allowBlank : true, anchor : '99%'}
						]
					}
				]
			}
		]
	});
	
	com.myads.book.AdvprudctSelectPanel.superclass.constructor.apply(this, arguments);
}

Ext.extend(com.myads.book.AdvprudctSelectPanel, Ext.FormPanel, {
	onRender: function() {
		com.myads.book.AdvprudctSelectPanel.superclass.onRender.apply(this, arguments);
	},
	
	getFormValues: function() {
		var myfo = new Object();
		Ext.apply(myfo, this.getForm().getValues());
		myfo.saleTypeName = this.getForm().findField('saleType').el.dom.value;
		myfo.useTypeName = this.getForm().findField('useType').el.dom.value;
		
		var rrr = this.formatComb.findRecord(this.formatComb.valueField || this.formatComb.displayField, this.formatComb.getValue());
		if(rrr) {
			myfo.format = rrr.data.value;
		}
		
		var rp = this.priceCombox.findRecord(this.priceCombox.valueField || this.priceCombox.displayField, this.priceCombox.getValue());
		if(rp) {
			myfo.priceId = rp.data.id;
		}
		
		var ss = this.advproductComb.findRecord(this.advproductComb.valueField || this.advproductComb.displayField, this.advproductComb.getValue());
		if(ss) {
			myfo.advproductId = ss.data.id;
		}
		
		return myfo;
	},
	
	setFormData: function(data) {
		this.getForm().reset();
		this.getForm().setValues(data);
		var advprudctSelectPanel = this;
	
		this.formatCombStore.addListener('load', function(st, rds, opts) {
			advprudctSelectPanel.formatComb.setValue(data.format);
			advprudctSelectPanel.formatCombStore.removeListener('load');
		});
		
		var _rs = new Ext.data.Record({
			id: data.advproductId, advproductName: data.advproductName
		},data.advproductId) ;
		this.advproductStore.add(_rs);
		
		var _rs2 = new Ext.data.Record({
			id: data.priceId, priceName: data.priceName
		},data.priceId) ;
		this.priceNameStore.add(_rs2);
		
		this.formatCombStore.load({params:{dataType: data.saleTypeName}});
		
		var _rSaleType = new Ext.data.Record({
			text: data.saleTypeName, value: data.saleType
		},data.saleType) ;
		
		this.saleTypeCombStore.add(_rSaleType);
		this.saleTypeComb.setValue(data.saleType);
	}
})