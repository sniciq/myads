Ext.onReady(function() {
	
	var data = [
		['0','启用'],
		['1','删除'],
		['2','停用']
	];
	
	var dataTypeCombStore = new Ext.data.Store({
		proxy : new Ext.data.MemoryProxy(data),
		reader : new Ext.data.ArrayReader({}, [{
							name : 'value'
						}, {
							name : 'text'
						}])
	});
	dataTypeCombStore.load();
	
	//参数类型
	var typeCombStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({url : '/myads/HTML/basic/OptionsAction_getBaseDataNamesByType.action'}),
		reader : new Ext.data.ArrayReader({}, [{name : 'value'}, {name : 'text'}]),
		baseParams : {dataType:'proportiontype'}
	});
	typeCombStore.load();
	
	//参数名称
	var nameCombStore = new Ext.data.ArrayStore({fields: [{name: 'value'},{name: 'text'}]});
	
	var typeCombo = new Ext.form.ComboBox({
		fieldLabel : '参数类型',
		hiddenName : 'type',
		store : typeCombStore,
		mode : 'local',
		editable : false,
		allowBlank : false ,
		triggerAction : 'all',
		valueField : 'text',
		displayField : 'text',
		anchor : '95%',
		listeners : {
            select : function(ComboBox, record, index) {
            	var type = record.data.value;
            	Ext.Ajax.request({
					method: 'post',
					url: '/myads/HTML/basic/OptionsAction_getBaseDataNamesByType.action',
				   	success:function(resp){
				   		var sd = AddProportionForm.findById("nameCombo");
				   		sd.setRawValue("");
				    	var obj=Ext.util.JSON.decode(resp.responseText);
			      		nameCombStore.loadData(obj);
				    },
				   	params: {dataType : type}
				});
            }
        }
	});
			
	var nameCombo = new Ext.form.ComboBox({
		id : 'nameCombo',
		fieldLabel : '参数名称',
		hiddenName : 'name',
		store : nameCombStore,
		allowBlank : false ,
		mode : 'local',
		editable : false,
		triggerAction : 'all',
		valueField : 'text',
		displayField : 'text',
		anchor : '95%'
	});
	
	var typeCombo1 = new Ext.form.ComboBox({
		fieldLabel : '参数类型',
		hiddenName : 'type',
		store : typeCombStore,
		mode : 'local',
		editable : false,
		triggerAction : 'all',
		valueField : 'text',
		displayField : 'text',
		anchor : '95%',
		listeners : {
            select : function(ComboBox, record, index) {
            	var type = record.data.value;
            	Ext.Ajax.request({
					method: 'post',
					url: '/myads/HTML/basic/OptionsAction_getBaseDataNamesByType.action',
				   	success:function(resp){
				   		var sd = proportionForm.findById("nameCombo1");
				   		sd.setRawValue("");
				    	var obj=Ext.util.JSON.decode(resp.responseText);
			      		nameCombStore.loadData(obj);
				    },
				   	params: {dataType : type}
				});
            }
        }
	});
			
	var nameCombo1 = new Ext.form.ComboBox({
		id : 'nameCombo1',
		fieldLabel : '参数名称',
		hiddenName : 'name',
		store : nameCombStore,
		mode : 'local',
		editable : false,
		triggerAction : 'all',
		valueField : 'text',
		displayField : 'text',
		anchor : '95%'
	});
	
	var proportionForm = new Ext.form.FormPanel({
		labelAlign : 'right',
		region: 'north',
		labelWidth : 70,
		frame : true,
		xtype : 'fieldset',
		items : [
			{
					layout : 'column',
					items : [{
								columnWidth : .01,
								layout : 'form',
								items : [{xtype : 'hidden',name : 'id',hidden : true,hiddenLabel : true}]
							}]
				}, {
					layout : 'column',
					items : [{
								columnWidth : .20,
								layout : 'form',
								items : [typeCombo1]
							},{
								columnWidth : .20,
								layout : 'form',
								items : [nameCombo1]
							},{
				            	columnWidth: .25, layout: 'form',
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
				            },{
				            	columnWidth: .25, layout: 'form',
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
				}],
		buttons : [{
			text : '查询',
			handler : function() {
				var fv = proportionForm.getForm().getValues();
				ds.baseParams = fv;
				ds.load({params : {start : 0,limit : 15}});
			}
		}, {
			text : '重置',
			handler : function() {
				proportionForm.form.reset();
			}
		}, {
			text : '新增权重参数',
			handler : function() {
				showAddProportion();
			}
		}]

	});
	
	var AddProportionForm = new Ext.form.FormPanel({
		labelAlign: 'right',
		region: 'center',
		labelWidth: 80,
		frame: true,
		xtype: 'fieldset',
		items: [
			{
				items: [
					{	
			        	layout: 'form',
		            	items: [
		            	        { xtype: 'textfield', name: 'id', fieldLabel: 'id',anchor : '95%', hidden : true}
		            	]
		            },{	
		            	columnWidth : .25,
						layout : 'form',
						items : [typeCombo]
		            },
			        {	
			        	columnWidth : .25,
						layout : 'form',
						items : [nameCombo]
		            },
		            {	
		            	layout: 'form',
		            	items: [
		            	        { xtype: 'textfield', name: 'value', fieldLabel: '参数值 ',anchor : '95%', allowBlank : false}
		            	]
		            }, {	
		            	columnWidth : .25,
		            	layout: 'form',
		            	items: [
		            	        { editable : false,fieldLabel : '状态',xtype : 'combo',hiddenName : 'status',anchor : '95%' , allowBlank : false , store : dataTypeCombStore,mode : 'local',triggerAction : 'all',valueField : 'value',displayField : 'text'}
		            	]
		            },
		            {
		            	columnWidth : .25,
						layout: 'form',
						items: [
									new Ext.form.DateField({
										id:'dt3',
										fieldLabel: '开始日期',
										name: 'startTime',
										editable : false,
										allowBlank:false,
										format:'Y-m-d',
										anchor : '98%',
										listeners:{
										  'select': function(){
										      Ext.getCmp('dt4').setMinValue(Ext.getCmp('dt3').getValue());
										   }
										} 
									})
								]
					},
					{	layout: 'form',
		            	items: [
									new Ext.form.DateField({
										id:'dt4',
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
		],
		buttons: [
			{
				text: '保存',
				handler: function() {
					AddProportionForm.form.doAction('submit', {
						url: '/myads/HTML/sysfun/ProportionAction_save.action',
						method: 'post',
						params: '',
						success: function(form, action) {
							if(action.result.result == 'success') {
								Ext.MessageBox.alert('结果', '保存成功！');
								grid.getStore().reload();
								winAdd.hide();
							}
							else if(action.result.result == 'isMoreThanOne') {
								Ext.MessageBox.alert('结果', '启用状态下，该类型名称在这段时间已经存在！');
								grid.getStore().reload();
								winAdd.hide();
							}
							else {
								Ext.MessageBox.alert('结果', '保存失败！');
								winAdd.hide();
							}
						}
					});
				}
			},
			{
				text: '取消',
				handler: function() {
					AddProportionForm.form.reset();
					winAdd.hide();
				}
			}
		]
		
	});
		
		
	//添加客户
	function showAddProportion() {
		AddProportionForm.form.reset();	
		winAdd.show();
	}
	
	var winAdd = new Ext.Window({
		width:350,
		height:280,
		layout:'border',
		modal : true,
		title : "权重参数",
		closeAction:'hide',
		items : [AddProportionForm]
	});

	proportionForm.render('proportionForm');
	
	function renderOp(value, cellmeta, record, rowIndex, columnIndex, stor) {
		var id = record.data.id;
		var name = record.data.name;
		var editStr = '<a href="#" onclick=\"showInfo(\'' + id + '\');">编辑</a>';
		var delStr = '<a href="#" onclick=\"deleteProportion(\'' + id + '\', \'' + name + '\');">删除</a>';
		return editStr + '&nbsp;&nbsp;&nbsp;&nbsp;' + delStr;
	}

	window.deleteProportion = function(id, name) {
		Ext.MessageBox.confirm('提示', '确定删除  ' + name + ' ？', function(btn) {
			if (btn == 'yes') {
				var param = [];
				param.push(id);
				Ext.Ajax.request({
					method : 'post',
					url : '/myads/HTML/sysfun/ProportionAction_delete.action',
					success : function(resp) {
						var obj = Ext.util.JSON.decode(resp.responseText);
						if (obj.result == 'success') {
							grid.getStore().reload();
							Ext.MessageBox.alert('提示', '删除成功！');
						}else{
							grid.getStore().reload();
							Ext.MessageBox.alert('提示', '删除失败！');
						}
					},
					params : {
						proportionList : param.join(',')
					}
				});
			}
		});
	};
	//修改
	window.showInfo = function(id) {
		AddProportionForm.load({
			url : '/myads/HTML/sysfun/ProportionAction_detail.action',
			params : {id : id}
		});
		winAdd.show();
	};
	
	var sm = new Ext.grid.CheckboxSelectionModel();
	var cm = new Ext.grid.ColumnModel
	([
		new Ext.grid.RowNumberer(), 
		sm, 
		{header : 'id',dataIndex : 'id',hidden : true},
		{header : '参数类型',dataIndex : 'type', sortable : true}, 
		{header : '参数名称',dataIndex : 'name', sortable : true}, 
		{header : '参数值',dataIndex : 'value', sortable : true }, 
		{header : '状态',dataIndex : 'status', sortable : true , renderer:function(value){
				if(value==0){
					return "<span style='color:GREEN'>启用</span>";
				}else if(value==1){
					return "<span style='color:GRAY'>已删除</span>";
				}else if(value==2){
					return "<span style='color:RED'>停用</span>";
				}
			}
		},
		{header : '开始时间',dataIndex : 'startTime',sortable : true},
		{header : '结束时间',dataIndex : 'endTime',sortable : true},
		{header : '操作',dataIndex : 'op',renderer : renderOp,width : 100,align : 'left'}
	]);

	var ds = new Ext.data.Store
	({
		proxy : new Ext.data.HttpProxy({url : '/myads/HTML/sysfun/ProportionAction_showAll.action'}),
		remoteSort : true,
		reader : new Ext.data.JsonReader
		(
			{totalProperty : 'total',idProperty : 'id',root : 'invdata'}, 
			[{name:'id',hidden : true},{name:'type'},{name : 'name'}, {name : 'value'}, {name : 'status'}, {name : 'startTime'}, {name : 'endTime'}]
		)
	});

	ds.load({params: {start:0, limit:15}});			

	var grid = new Ext.grid.GridPanel({
		el : 'grid',
		region: 'center',
		ds : ds,
		cm : cm,
		sm : sm,
		height : 300,
		viewConfig : {
			forceFit : true
		},
		bbar : new Ext.PagingToolbar({
			pageSize : 15,
			store : ds,
			displayInfo : true,
			displayMsg : '显示第{0}条到{1}条记录,一共{2}条',
			emptyMsg : '没有记录',
			items : ['-', {
				text : '删除',
				handler : function() {
					var rs = grid.getSelectionModel().getSelections();
					if(rs.length == 0)
						{
							grid.getStore().reload();
							Ext.MessageBox.alert('提示', '请选择需要删除的数据！');
						}else
						{
							var param = [];
							
							Ext.each(rs, function() {
										param.push(this.get("id"));
									});
		
							Ext.MessageBox.confirm("提示", "确定删除？",
									function(id) {
										if (id == 'yes') {
											Ext.Ajax.request({
												method : 'post',
												url : '/myads/HTML/sysfun/ProportionAction_delete.action',
												success : function(resp) {
													var obj = Ext.util.JSON
															.decode(resp.responseText);
													if (obj.result == 'success') {
														grid.getStore().reload();
														Ext.MessageBox.alert('提示',
																'删除成功！');
													}
												},
												params : {
													proportionList : param.join(',')
												}
											});
										} else {
										}
									});
						}
				}
			}]
		})
	});

	grid.addListener('rowdblclick', function(grid, rowindex, e) {
		var record = grid.getStore().getAt(rowindex);
		AddProportionForm.load({
			url : '/myads/HTML/sysfun/ProportionAction_detail.action',
			params : {
				id : record.data.id
			}
		});
		winAdd.show();
	});

	grid.render();
	
	var mainViewPort = new Ext.Viewport({
		layout: 'border',
		items:[
		       proportionForm, grid
		]
	});
}); 
