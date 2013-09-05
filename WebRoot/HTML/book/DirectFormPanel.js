Ext.namespace("com.myads.book.DirectFormPanel"); 
com.myads.book.DirectFormPanel = function(config) {
	var tab_item_directForm = this;
	
	var hour_group;
    var area_city_store;
    var area_city_grid;
    var area_prinvice_store;
    var area_prinvice_grid;
    var area_result_grid;
    var area_result_store;
   
    var area_group;
    var frequencyType_group;
    var frequencyTypeCombStore;
   
    area_result_data = [];
	var area_result_store = this.area_result_store = new Ext.data.Store({
		proxy: new Ext.data.MemoryProxy(area_result_data),
		reader: new Ext.data.ArrayReader({},[
			{name:'id'},{name:'city'}
		])
	});
	
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
	
	var area_city_store = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({url: '/myads/HTML/basic/ProviceAction_getAllCityInProvice.action'}),
		remoteSort: false,
		reader: new Ext.data.JsonReader(
			{totalProperty: 'total',idProperty:'id',root: 'invdata'},
			[{name: 'id'},{name: 'dcode'},{name: 'pcode'},{name: 'city'}]
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
	
	Ext.apply(this, {
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
			}
//			,
//			{
//				height: 100,
//				width:470,
//				items:[frequencyType_group]
//			}
		]
	});
	
	com.myads.book.DirectFormPanel.superclass.constructor.apply(this, arguments);
}

Ext.extend(com.myads.book.DirectFormPanel, Ext.FormPanel, {
	onRender: function() {
		com.myads.book.DirectFormPanel.superclass.onRender.apply(this, arguments);
	},
	
	clearAreaResult: function() {
		this.area_result_store.removeAll();
	},
	
	getFormValues: function() {
		var myfo = new Object();
		Ext.apply(myfo, this.getForm().getValues());
		
		//area_result_store 区域定向结果框
		var areaResult = [];
	    this.area_result_store.each(function(rec){
	    	areaResult.push(rec.data);
	   	});
	   	myfo.areaDataAry = Ext.util.JSON.encode(areaResult);
		
	   	var _chhours = '';
	   	for(var i = 0; i < 24; i++) {
	   		var achk = this.getForm().findField("hour_chk_" + i + '_' + (i+1));
	   		if(achk && achk.checked) {
	   			_chhours += achk.inputValue + ",";
	   		}
	   	}
	   	myfo.hourDirect = _chhours;
	   	return myfo;
	},
	
	setFormData: function(data) {
		this.getForm().reset()
		this.getForm().setValues(data);
		
		this.area_result_store.removeAll();
		var areaDirectObj = Ext.util.JSON.decode(data.areaDirect);
		for(var j = 0; j < areaDirectObj.length; j++) {
			var _rs = new Ext.data.Record(areaDirectObj[j]) ;
			this.area_result_store.add(_rs);
		}
		
		//设置为都不选中
		for(var i = 0; i < 24; i++) {
	   		var achk = this.getForm().findField("hour_chk_" + i + '_' + (i+1));
	   		achk.setValue(false);
	   	}
		   	
	   	//选中已经有的
		var hours = data.hourDirect.split(',');
		for(var j = 0; j < hours.length; j++) {
			var achk = this.getForm().findField("hour_chk_" + hours[j]);
			if(achk != null)
				achk.setValue(true);
		}
	}
});
