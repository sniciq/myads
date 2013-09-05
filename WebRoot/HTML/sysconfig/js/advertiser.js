Ext.onReady(function() {
	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget='side';

	var dataTypeCombStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({url : '/myads/HTML/basic/OptionsAction_getBaseDataNamesByType.action?dataType=industrytype'}),
		
		reader : new Ext.data.ArrayReader({}, 
			[
				{name : 'value'}, 
				{name : 'text'}
			]
		)
	});
	dataTypeCombStore.load();
	
	var dataTypeCombStore1 = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
					url : '/myads/HTML/basic/OptionsAction_getBaseDataNamesByType.action?dataType=area'
					
				}),
		reader : new Ext.data.ArrayReader({}, [{
							name : 'value'
						}, {
							name : 'text'
						}])
	});
	dataTypeCombStore1.load();

	var combo = new Ext.form.ComboBox({
		fieldLabel : '行业',
		hiddenName : 'industry',
		store : dataTypeCombStore,
		editable : false,
		
		mode : 'local',
		triggerAction : 'all',
		valueField : 'value',
		displayField : 'text'
	});

	var combo1 = new Ext.form.ComboBox({
		fieldLabel : '区域',
		hiddenName : 'area',
		store : dataTypeCombStore1,
		editable : false,
		
		mode : 'local',
		triggerAction : 'all',
		valueField : 'value',
		displayField : 'text'
	});

	var dataTypeCombStore3 = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
					url : '/myads/HTML/basic/OptionsAction_getBaseDataNamesByType.action?dataType=status'
					
				}),
		reader : new Ext.data.ArrayReader({}, [{
							name : 'value'
						}, {
							name : 'text'
						}])
	});
	dataTypeCombStore3.load();
	
	var combo3 = new Ext.form.ComboBox({
		fieldLabel : '状态',
		hiddenName : 'status',
		editable : false,
		value:0,
		store: new Ext.data.SimpleStore({
				fields: ['value', 'text'],
				data: [['0', '启用'],['2', '停用'],['', '全部']]
			}),
		mode : 'local',
		triggerAction : 'all',
		valueField : 'value',
		displayField : 'text'
	});
	
	var advertiserForm = new Ext.form.FormPanel({
		labelAlign : 'right',
		labelWidth : 60,
		frame : true,
		xtype : 'fieldset',
		region: 'north',
		items : [{
					layout : 'column',
					items : [{
								columnWidth : .01,
								layout : 'form',
								items : [{
											xtype : 'hidden',
											name : 'id',
											hidden : true,
											hiddenLabel : true
										}, {
											xtype : 'hidden',
											name : 'id',
											hidden : true,
											hiddenLabel : true
										}]
							}]
				}, {
					layout : 'column',
					items : [{
								columnWidth : .22,
								layout : 'form',
								items : [{
											xtype : 'textfield',
											name : 'name',
											fieldLabel : '名称 ',
											anchor : '95%'
										}]
							}, {
								columnWidth : .22,
								layout : 'form',
								items : [combo]
							}, {
								columnWidth : .22,
								layout : 'form',
								items : [combo1]
							}, {
								columnWidth : .22,
								layout : 'form',
								items : [combo3]
							}]
				}],
		buttons : [{
			text : '查询',
			handler : function() {
				var fv = advertiserForm.getForm().getValues();
				ds.baseParams = fv;
				ds.load({params : {start : 0,limit : 15}});
			}
		}, {
			text : '重置',
			handler : function() {
				advertiserForm.form.reset();
			}
		}, {
			text : '新建广告主',
			handler : function() {
				showAddAdvertiser();
			}
		}]

	});
	
		
		var addadvertiserForm = new Ext.form.FormPanel({
			labelAlign : 'right',
			labelWidth : 80,
			frame : true,
			xtype : 'fieldset',
			items : [

			{
						xtype : 'textfield',
						name : 'name',
						fieldLabel : '广告主名称',
						allowBlank : false,
						allowText : '此项不能为空',
						maxLength:20,
						maxLengthText:'输入文本过长',
						anchor : '90%'
					}, {
						xtype : 'hidden',
						name : 'id',
						fieldLabel : '广告主Id'			
					},{

						fieldLabel : '行业',
						xtype : 'combo',
						allowBlank : false,
						editable : false,
						hiddenName : 'industry',
						store : dataTypeCombStore,
						mode : 'local',
						triggerAction : 'all',
						valueField : 'value',
						displayField : 'text',
						anchor : '90%'
					}, {
						fieldLabel : '所属区域',
						xtype : 'combo',
						hiddenName : 'area',
						allowBlank : false,
						editable : false,
						store : dataTypeCombStore1,
						mode : 'local',
						triggerAction : 'all',
						valueField : 'value',
						displayField : 'text',
						anchor : '90%'
					},{
						xtype : 'textfield',
						name : 'company',
						fieldLabel : '所属公司',
						allowBlank : false,
						allowText : '此项不能为空',
						anchor : '90%'
					}, {
							columnWidth: .25, 
							layout: 'form',
		            		items: [
			            	   new Ext.form.ComboBox({
			        	       		fieldLabel: '状态',
									hiddenName: 'status',
									mode: 'local',
									editable : false,
									triggerAction: 'all', 
									valueField: 'value',
									displayField: 'text',
									anchor : '90%',
									
									store: new Ext.data.SimpleStore({
										fields: ['value', 'text'],
										data: [['0', '启用'],['2', '停用']]
									})
			        	       })
		            		]
		            

						
					}],
			buttons : [{
				text : '保存',
				handler : function() {
					addadvertiserForm.form.doAction('submit', {
						url : '/myads/HTML/sysfun/AdvertiserAction_save.action',
						method : 'post',
						params : '',
						success : function(form, action) {
							Ext.MessageBox.alert('action', action.result);
							Ext.MessageBox.alert('结果', '保存成功！');
							form.reset();
							grid.getStore().reload();
							dataTypeCombStore.load();
							winAdd.hide();
						}
					});
				}
			}, {
				text : '取消',
				handler : function() {
					advertiserForm.form.reset();
					winAdd.hide();
				}
			}]

		});
		
		
	//添加广告主
	function showAddAdvertiser() {
		addadvertiserForm.form.reset();	
		addadvertiserForm.getForm().findField("status").setValue(0);
		winAdd.show();
	}
	var winAdd = new Ext.Window({
					width : 400,
					height : 220,
					modal : true,
					title : "广告主管理",
					closeAction:'hide',
					items : addadvertiserForm	
										
				});
	//添加产品线
	window.showAddBrand = function(id,name) {

		var addProLineForm = new Ext.form.FormPanel({
			labelAlign : 'right',
			labelWidth : 80,
			frame : true,
			xtype : 'fieldset',
			items : [{
						xtype : 'textfield',
						name : 'name',
						fieldLabel : '所属广告主',
						value:name,
						readOnly:true,
						anchor : '50%'
					},
					{
						xtype : 'hidden',
						name : 'advertiserId',
						fieldLabel : '所属广告主Id',
						value:id,
						readOnly:true,
						anchor : '50%'
					}, {
						xtype : 'textfield',
						name : 'productLineName',
						fieldLabel : '产品线名称',
						allowBlank : false,
						maxLength:20,
						maxLengthText:'输入文本过长',
						anchor : '50%'
					}],
					
			buttons : [{
				text : '保存',

				handler : function() {
					addProLineForm.form.doAction('submit', {
						url : '/myads/HTML/sysfun/ProductLineAction_save.action',
						method : 'post',
						params : '',
						success : function(form, action) {
							Ext.MessageBox.alert('结果', '保存成功！');
							//addProLineForm.getForm().findField("productLineName").setValue("");
							grid1.getStore().reload();
							dataTypeCombStore.load();
							//win.hide();
						}
					});
				}
			}, {
				text : '取消',
				handler : function() {
					addProLineForm.form.reset();
					win.hide();
				}
			}]

		});
			
	var sm1 = new Ext.grid.CheckboxSelectionModel();
	var cm1 = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(), 
			
				{
				header : '产品线ID',
				dataIndex : 'productLineId',
				sortable : true
			},
			{
				header : '产品线名称',
				dataIndex : 'productLineName',
				sortable : true
			},{
				header : '操作',
				dataIndex : 'op',
				renderer : renderOp1,
				width : 100,
				align : 'left'
			}]);

	var ds1 = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
					url : '/myads/HTML/sysfun/ProductLine_SelectByAdvertiserId.action?advertiserId='+id
				}),
		remoteSort : true,
		reader : new Ext.data.JsonReader({
					totalProperty : 'total',
					idProperty : 'productLineId',
					root : 'invdata'
				}, [{
							name : 'productLineId'
						}, {
							name : 'productLineName'
						}])
	});

	ds1.load({
				params : {
					start : 0,
					limit : 15
				}
			});

	var grid1 = new Ext.grid.GridPanel({
		ds : ds1,
		cm : cm1,
		region: 'center',
		height :170,
		viewConfig : {
			forceFit : true
		},
		bbar : new Ext.PagingToolbar({
			pageSize : 15,
			store : ds1,
			displayInfo : true,
			displayMsg : '显示第{0}条到{1}条记录,一共{2}条',
			emptyMsg : '没有记录',
			items : []
		})
	});
	
	
	window.deleteProductLine = function(id, name) {
		
		Ext.MessageBox.confirm('提示', '确定删除  ' + name + '以及其相关产品 ？', function(btn) {
			if (btn == 'yes') {
				var param = [];
				param.push(id);
				Ext.Ajax.request({
					method : 'post',
					url : '/myads/HTML/sysfun/ProductLineAction_delete.action',
					success : function(resp) {
						var obj = Ext.util.JSON.decode(resp.responseText);
						if (obj.result == 'success') {
							grid1.getStore().reload();
							Ext.MessageBox.alert('提示', '删除成功！');
						}else{
							Ext.MessageBox.alert('提示', '删除失败！');
						}
					},
					params : {
						productLineList : param.join(',')
					}
				});
			}
		});
	};
	
	window.showAddProduct = function(id, name) {
		AddProduct(id,name);
	};

		var win = new Ext.Window({
			
					width : 600,
					height : 300,
					modal : true,
					closeAction:'hide',
					title : "新建产品线",
					items : [addProLineForm,grid1]
				});
				
	
		win.show();
	};

	

	
	advertiserForm.render('advertiserForm');

	function renderOp1(value, cellmeta, record, rowIndex, columnIndex, stor) {
		
		var id = record.data.productLineId;
		var name = record.data.productLineName;
		var addProduct = '<a href="#" onclick=\"showAddProduct(\''+ id + '\', \'' + name + '\');">产品</a>';
	
		var delStr = '<a href="#" onclick=\"deleteProductLine(\''+ id + '\', \'' + name + '\');">删除</a>';
		
		return addProduct + '&nbsp;&nbsp;&nbsp;&nbsp;'+delStr ;
	}
	
	function renderOp2(value, cellmeta, record, rowIndex, columnIndex, stor) {
		
		var id = record.data.productId;
		var name = record.data.productName;
		var delStr = '<a href="#" onclick=\"deleteProduct(\''+ id + '\', \'' + name + '\');">删除</a>';		
		return  delStr ;
	}
	
	function renderOp(value, cellmeta, record, rowIndex, columnIndex, stor) {
		
		
		var id = record.data.id;
		var name = record.data.name;
		
		var editStr = '<a href="#" onclick=\"showUpdate(\''+ id + '\', \'' + name + '\');">编辑</a>';
		var addPerson = '<a href="#" onclick=\"showPerson(\''+ id + '\', \'' + name + '\');">联系人</a>';
		
		var addLine = '<a href="#" onclick=\"showAddBrand(\''+ id + '\', \'' + name + '\');">产品线</a>';
				
		var delStr = '<a href="#" onclick=\"deleteAdvertiser(\''+ id + '\', \'' + name + '\');">删除</a>';
				
		return editStr + '&nbsp;&nbsp;&nbsp;&nbsp;'+addPerson+'&nbsp;&nbsp;&nbsp;&nbsp;' + addLine +'&nbsp;&nbsp;&nbsp;&nbsp;'+ delStr;
	}

	window.deleteAdvertiser = function(id, name) {
		Ext.MessageBox.confirm('提示', '确定删除  ' + name + '&nbsp 以及相关产品线、联系人等？', function(btn) {
			if (btn == 'yes') {
				var param = [];
				param.push(id);
				Ext.Ajax.request({
					method : 'post',
					url : '/myads/HTML/sysfun/AdvertiserAction_delete.action',
					success : function(resp) {
						var obj = Ext.util.JSON.decode(resp.responseText);
						if (obj.result == 'success') {
							grid.getStore().reload();
							dataTypeCombStore.load();
							Ext.MessageBox.alert('提示', '删除成功！');
						}
					},
					params : {
						advertiserList : param.join(',')
					}
				});
			}
		});
	};

	
	
	//修改
	window.showUpdate = function(id) {

		addadvertiserForm.load({
					url : '/myads/HTML/sysfun/AdvertiserAction_detail.action',
					params : {
						id : id
					}
				});
			
		winAdd.show();
	};

	var sm = new Ext.grid.CheckboxSelectionModel();
	var cm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(), sm, {
				header : '名称',
				dataIndex : 'name',
				sortable : true
			}, {
				header : '行业',
				dataIndex : 'industryName',
				sortable : true
			}, {
				header : '所属地区',
				dataIndex : 'areaName',
				sortable : true
			}, {
				header : '所属公司',
				dataIndex : 'company',
				sortable : true
			}, {
				header : '状态',
				dataIndex : 'status',
				renderer:rendStauts,
				sortable : true
			}, {
				header : '操作',
				dataIndex : 'op',
				renderer : renderOp,
				width : 100,
				align : 'left'
			}]);
	//判断状态
	function rendStauts(value){
	
		if(value==0){
			return "<span style='color:GREEN'>启用</span>";
		}else if(value==1){
			return "<span style='color:GRAY'>删除</span>";
		}else if(value==2){
			return "<span style='color:RED'>停用</span>";
		}
	}
	var ds = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
					url : '/myads/HTML/sysfun/AdvertiserAction_showAll.action'
				}),
		remoteSort : true,
		reader : new Ext.data.JsonReader({
					totalProperty : 'total',
					idProperty : 'id',
					root : 'invdata'
				}, [{
							name : 'name'
						}, {
							name : 'id'
						}, {
							name : 'industryName'
						}, {
							name : 'areaName'
						}, {
							name : 'contactpersonName'
						}, {
							name : 'company'
						}, {
							name : 'status'
						}])
	});

	ds.load({
				params : {
					start : 0,
					limit : 15,
					status:0
				}
			});

	var grid = new Ext.grid.GridPanel({
		el : 'grid',
		ds : ds,
		cm : cm,
		sm : sm,
		region: 'center',
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
					var param = [];
					Ext.each(rs, function() {
								param.push(this.get("id"));
							});
					if(param.length>0){
					Ext.MessageBox.confirm("提示", "确定删除？",
							function(id) {
								if (id == 'yes') {
									Ext.Ajax.request({
										method : 'post',
										url : '/myads/HTML/sysfun/AdvertiserAction_delete.action',
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
											advertiserList : param.join(',')
										}
									});
								} else {
								}
							});
					}else{
						Ext.MessageBox.alert('提示', '请选择需要删除的广告主！');
					}
				}
			}]
		})
	});

	grid.addListener('rowdblclick', function(grid, rowindex, e) {
		var record = grid.getStore().getAt(rowindex);
		
		addadvertiserForm.load({
					url : '/myads/HTML/sysfun/AdvertiserAction_detail.action',
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
		       advertiserForm, grid
		]
	});
	
	
	
	
	//TODO 添加产品
	window.AddProduct = function(id,name) {
		
		var addProductForm = new Ext.form.FormPanel({
			labelAlign : 'right',
			labelWidth : 80,
			frame : true,
			xtype : 'fieldset',
			items : [{
						xtype : 'textfield',
						name : 'productLineName',
						fieldLabel : '所属产品线',
						value:name,
						readOnly:true,
						anchor : '50%'
					},
					{
						xtype : 'hidden',
						name : 'productLineId',
						fieldLabel : '所属产品线Id',
						value:id,
						readOnly:true,
						anchor : '50%'
					}, {
						xtype : 'textfield',
						name : 'productName',
						fieldLabel : '产品名称',
						allowBlank : false,
						anchor : '50%'
					}],
					
			buttons : [{
				text : '保存',

				handler : function() {
						addProductForm.form.doAction('submit', {
						url : '/myads/HTML/sysfun/ProductAction_save.action',
						method : 'post',
						params : '',
						success : function(form, action) {
							Ext.MessageBox.alert('结果', '保存成功！');
							//addProductForm.getForm().findField("productName").setValue("");
							grid2.getStore().reload();
							dataTypeCombStore.load();
						}
					});
				}
			}, {
				text : '取消',
				handler : function() {
					addProductForm.form.reset();
					win1.hide();
				}
			}]

		});
			
	var sm2 = new Ext.grid.CheckboxSelectionModel();
	var cm2 = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(), 
	
			{
				header : '产品名称',
				dataIndex : 'productName',
				sortable : true
			},{
				header : '操作',
				dataIndex : 'op',
				renderer : renderOp2,
				width : 100,
				align : 'left'
			}]);

	var ds2 = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
					url : '/myads/HTML/sysfun/ProductAction_SelectByProductLineId.action?productLineId='+id
				}),
		remoteSort : true,
		reader : new Ext.data.JsonReader({
					totalProperty : 'total',
					idProperty : 'productId',
					root : 'invdata'
				}, [{
							name : 'productId'
						}, {
							name : 'productName'
						}])
	});

	ds2.load({
				params : {
					start : 0,
					limit : 15
				}
			});

	var grid2 = new Ext.grid.GridPanel({
		ds : ds2,
		cm : cm2,
		
		region: 'center',
		height :160,
		viewConfig : {
			forceFit : true
		},
		bbar : new Ext.PagingToolbar({
			pageSize : 15,
			store : ds2,
			displayInfo : true,
			displayMsg : '显示第{0}条到{1}条记录,一共{2}条',
			emptyMsg : '没有记录',
			items : []
		})
	});
	
	
	window.deleteProduct = function(id, name) {
		Ext.MessageBox.confirm('提示', '确定删除  ' + name + ' ？', function(btn) {
			if (btn == 'yes') {
				var param = [];
				param.push(id);
				Ext.Ajax.request({
					method : 'post',
					url : '/myads/HTML/sysfun/ProductAction_delete.action',
					success : function(resp) {
						var obj = Ext.util.JSON.decode(resp.responseText);
						if (obj.result == 'success') {
							grid2.getStore().reload();
							Ext.MessageBox.alert('提示', '删除成功！');
						}
					},
					params : {
						productList : param.join(',')
					}
				});
			}
		});
	};
		var win1 = new Ext.Window({
			
					width : 600,
					height : 300,
					closeAction:'hide',
					modal : true,
					title : "新建产品",
					items : [addProductForm,grid2]
				});
				
	
		win1.show();
	};
	
	
	
	//TODO 添加联系人
	var cmPerson;
	var contactGridPanel;
	var winPerson;
	var dsPerson;
	var temp;
	var tempName;
	var addPersonForm = new Ext.form.FormPanel({
		labelAlign : 'right',
		region: 'north',
		labelWidth : 70,
		height : 135,
		frame : true,
		items: [
				{//行1
				layout: 'column',labelWidth : 80,
				items: [
					{
						columnWidth: .5, layout: 'form',
						items: [
							{
								xtype : 'textfield',
								layout: 'form',
								readOnly : true,
								name : 'advertiserName',
								fieldLabel : '所属广告主 '
							},{
								xtype : 'hidden',
								layout: 'form',
								readOnly : true,
								name : 'type',
								value:'A',
								fieldLabel : '类型 '
						}
						]
					}
				]
			},
				{//行1
				layout: 'column',labelWidth : 80,
				items: [
					{
						columnWidth: .5, layout: 'form',
						items: [
							{
								xtype : 'textfield',
								layout: 'form',
								name : 'contactPersonName',
								fieldLabel : '姓名 ',
								allowBlank :false
							},
							{
								xtype : 'hidden',
								layout: 'form',
								name : 'contactPersonId',
								fieldLabel : '联系人ID '
								
							}
						]
					},
					{
						columnWidth: .5, layout: 'form',
						items: [
							{
								xtype : 'textfield',
								layout: 'form',
								name : 'contactPersonTel',
								allowBlank :false,
								regex: /^[0-9]{11}|[0-9]{8}|[0-9]{7}|0(([1-9]{1}\d{1,2}\-{1})|([3-9]\d{2}))\d{7,8}$/,
								regexText:'电话号码不合法！',
								fieldLabel : '电话 ',
								maxLength:20,
								maxLengthText:'输入文本过长'
							}
						]
					}
				]
			},
			{//行2
				layout: 'column',labelWidth : 80,
				items: [
					{
						columnWidth: .5, layout: 'form',
						items: [
							{
								xtype : 'textfield',
								layout: 'form',
								name : 'contactPersonOnline',
								allowBlank :false,
								fieldLabel : '在线方式 '
							}
						]
					},
					{
						columnWidth: .5, layout: 'form',
						items : [{
									xtype : 'textfield',
									name : 'contactPersonTitle',
									allowBlank :false,
									fieldLabel : '职称 '
					}]}]
			},{
				items:[
						{
							xtype : 'hidden',
							name : 'consumerId',
							hidden : true,
							hiddenLabel : true
						}
				       ]
			}
		],
		buttons:[{
			layout : 'form',
			xtype : 'button',
			name : 'name',
			text : '保存联系人 ',
			anchor : '10%',
			handler : function() {
			addPersonForm.getForm().findField("consumerId").setValue(temp);
			//addPersonForm.getForm().findField("type").setValue('A');
			
			addPersonForm.form.doAction('submit', {
				url : '/myads/HTML/sysfun/ContactPerson_save.action',
				method : 'post',
				params : '',
				success : function(form, action) {
					form.reset();
					contactGridPanel.getStore().reload();
					Ext.MessageBox.alert('结果', '保存成功！');
					}
			  });					
		    }
		},{text : '取消',
			handler : function() {
				addPersonForm.form.reset();
				winPerson.hide();
			}
		}]
	});
	//删除联系人
	window.deleteCP = function(contactPersonId, contactPersonName){
		Ext.MessageBox.confirm('提示', '确定删除  ' + contactPersonName + ' ？', function(btn) {
			if (btn == 'yes') {
				Ext.Ajax.request({
							method : 'post',
							url : '/myads/HTML/sysfun/ContactPerson_delete.action',
							success : function(resp) {
								var obj = Ext.util.JSON.decode(resp.responseText);
								if (obj.result == 'success') {
									contactGridPanel.getStore().reload();
									Ext.MessageBox.alert('提示', '删除成功！');
								}
							},
							params : {
								contactPersonId : contactPersonId
							}
						});
			}
		});
	};
	//修改联系人
	window.UpdatePerson = function(contactPersonId,contactPersonName,contactPersonTel,contactPersonTitle,contactPersonOnline){
		
		addPersonForm.getForm().findField("contactPersonId").setValue(contactPersonId);
		addPersonForm.getForm().findField("contactPersonName").setValue(contactPersonName);
		addPersonForm.getForm().findField("contactPersonTel").setValue(contactPersonTel);
		addPersonForm.getForm().findField("contactPersonTitle").setValue(contactPersonTitle);
		addPersonForm.getForm().findField("contactPersonOnline").setValue(contactPersonOnline);
	};
	
	function renderOpt(value, cellmeta, record, rowIndex, columnIndex, stor) {
		var contactPersonId = record.data.contactPersonId;
		var contactPersonName = record.data.contactPersonName;
		var contactPersonTel = record.data.contactPersonTel;
		var contactPersonTitle = record.data.contactPersonTitle;
		var contactPersonOnline = record.data.contactPersonOnline;
		
		var editStr = '<a href="#" onclick=\"UpdatePerson(\''+ contactPersonId + '\', \'' + contactPersonName + '\', \'' + contactPersonTel + '\', \'' + contactPersonTitle + '\', \'' + contactPersonOnline + '\');">修改</a>';
		var delStr = '<a href="#" onclick=\"deleteCP(\''+ contactPersonId + '\', \'' + contactPersonName + '\');">删除</a>';
	

		return  editStr +  '&nbsp;&nbsp;&nbsp;&nbsp;'+delStr;
	}
	
	
	
	cmPerson = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(), {
		header : '联系人姓名',
		dataIndex : 'contactPersonName',
		sortable : true
	}, {
		header : '电话',
		dataIndex : 'contactPersonTel',
		sortable : true
	}, {
		header : '在线方式',
		dataIndex : 'contactPersonOnline',
		sortable : true
	}, {
		header : '职称',
		dataIndex : 'contactPersonTitle',
		sortable : true
	}, {
		header : '操作',
		dataIndex : 'op',
		renderer : renderOpt,
		width : 100,
		align : 'left'
	}]);
	
	dsPerson = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
					url : '/myads/HTML/sysfun/ContactPerson_showAll.action?contactPersonType=A'
				}),
		remoteSort : true,
		reader : new Ext.data.JsonReader({
					totalProperty : 'total',
					idProperty : 'contactPersonId',
					root : 'invdata'
				}, [{
					name : 'contactPersonId'
				}, {
					name : 'contactPersonName'
				}, {
					name : 'contactPersonTel'
				}, {
					name : 'contactPersonOnline'
				}, {
					name : 'contactPersonTitle'
				}])
	});

	contactGridPanel = new Ext.grid.GridPanel({
		region: 'center',
		ds : dsPerson,
		cm : cmPerson
	});
	//TODO 
	contactGridPanel.addListener('rowdblclick', function(contactGridPanel, rowindex, e) {
		var record = contactGridPanel.getStore().getAt(rowindex);
		//alert(record.data.contactPersonId);
		addPersonForm.getForm().findField("contactPersonId").setValue(record.data.contactPersonId);
		addPersonForm.getForm().findField("contactPersonName").setValue(record.data.contactPersonName);
		addPersonForm.getForm().findField("contactPersonTel").setValue(record.data.contactPersonTel);
		addPersonForm.getForm().findField("contactPersonTitle").setValue(record.data.contactPersonTitle);
		addPersonForm.getForm().findField("contactPersonOnline").setValue(record.data.contactPersonOnline);
//		addadvertiserForm.load({
//					url : '/myads/HTML/sysfun/AdvertiserAction_detail.action',
//					params : {
//						id : record.data.contactPersonId
//					}
//				});
	
				
	});
	
	winPerson = new Ext.Window({
		width : 550,
		height : 500,
		layout:'border',
		title:'添加联系人',
		modal : true,
		closeAction:'hide',
		items : [addPersonForm,contactGridPanel]
	});
	
	window.showPerson = function(id,name){
		addPersonForm.form.reset();	
		temp=id;
		tempName=name;
		addPersonForm.getForm().findField("advertiserName").setValue(name);
		addPersonForm.getForm().findField("type").setValue('A');
		//winPerson.title= name+'的联系人';
		dsPerson.load({
			params : {
				start : 0,
				limit : 20,
				type : 'A',
				consumerId :id
			}
		});
		winPerson.show();
	};
});
