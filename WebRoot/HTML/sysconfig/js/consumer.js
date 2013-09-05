Ext.onReady(function() {
	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget='side';

	var dataTypeCombStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
					url : '/myads/HTML/basic/OptionsAction_getBaseDataNamesByType.action?dataType=area'
					
				}),
		reader : new Ext.data.ArrayReader({}, [{
							name : 'value'
						}, {
							name : 'text'
						}])
	});
	dataTypeCombStore.load();
	
	var dataTypeCombStore1 = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
					url : '/myads/HTML/basic/OptionsAction_getBaseDataNamesByType.action?dataType=consumertype'
					
				}),
		reader : new Ext.data.ArrayReader({}, [{
							name : 'value'
						}, {
							name : 'text'
						}])
	});
	dataTypeCombStore1.load();
			var dataTypeCombStore2 = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
					url : '/myads/HTML/sysfun/ConsumerAction_getCompanylist.action'
					
				}),
		reader : new Ext.data.ArrayReader({}, [{
							name : 'value'
						}, {
							name : 'text'
						}])
	});
	dataTypeCombStore2.load();
	
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

	var dataStoreCompany = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
					url : '/myads/HTML/sysfun/AdvertiserAction_Companylist.action'
				}),
		reader : new Ext.data.ArrayReader({}, [{
							name : 'value'
						}, {
							name : 'text'
						}])
	});
	dataStoreCompany.load();
	
	var combo = new Ext.form.ComboBox({
				fieldLabel : '客户类型',
				hiddenName : 'categoryId',
				store : dataTypeCombStore1,
				//
				mode : 'local',
				editable : false,
				triggerAction : 'all',
				valueField : 'value',
				displayField : 'text'
			});

	var combo1 = new Ext.form.ComboBox({
				fieldLabel : '区域',
				hiddenName : 'areaId',
				store : dataTypeCombStore,
				//
				mode : 'local',
				editable : false,
				triggerAction : 'all',
				valueField : 'value',
				displayField : 'text'
			});

	var consumerForm = new Ext.form.FormPanel({
		labelAlign : 'right',
		region: 'north',
		labelWidth : 60,
		frame : true,
		xtype : 'fieldset',
		items : [{
					layout : 'column',
					items : [{
								columnWidth : .01,
								layout : 'form',
								items : [{
											xtype : 'hidden',
											name : 'consumerId',
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
											name : 'consumerName',
											fieldLabel : '名称 ',
											anchor : '90%'
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
				var fv = consumerForm.getForm().getValues();
						ds.baseParams = fv;
						ds.load({
								params : {
										start : 0,
										limit : 10
									}
								});
			}
		}, {
			text : '重置',
			handler : function() {
				consumerForm.form.reset();
			}
		}, {
			text : '新增客户',
			handler : function() {
				dataTypeCombStore2.load();
				showAddconsumer();
			}
		}]

	});
	var addconsumerForm = new Ext.form.FormPanel({
			region: 'center',
			labelAlign : 'right',
			labelWidth : 80,
			frame : true,
			xtype : 'fieldset',
			items : [

					{
						xtype : 'textfield',
						name : 'consumerName',
						allowBlank : false,
						blankText : "不能为空!",
						allowBlank : false,
						anchor : '95%',
						maxLength:60,
						maxLengthText:'输入文本过长',
						fieldLabel : '客户名称'
							
					},{
						xtype : 'hidden',
						name : 'consumerId'			
					}, {
						fieldLabel : '所属公司',
						xtype : 'combo',
						editable : false,
						allowBlank : false,
						hiddenName : 'consumerPid',
						anchor : '95%',
						store : dataTypeCombStore2,
						
						mode : 'local',
						triggerAction : 'all',
						valueField : 'value',
						displayField : 'text'
					}, {
						fieldLabel : '客户类型',
						xtype : 'combo',
						editable : false,
						hiddenName : 'categoryId',
						allowBlank : false,
						blankText : "不能为空!",
						anchor : '95%',
						store : dataTypeCombStore1,
						
						mode : 'local',
						triggerAction : 'all',
						valueField : 'value',
						displayField : 'text'
					}, {
						fieldLabel : '所属地域',
						xtype : 'combo',
						editable : false,
						hiddenName : 'areaId',
						allowBlank : false,
						blankText : "不能为空!",
						anchor : '95%',
						store : dataTypeCombStore,
						
						mode : 'local',
						triggerAction : 'all',
						valueField : 'value',
						displayField : 'text'
					},{
						xtype : 'textfield',
						name : 'consumerAdress',
						anchor : '95%',
						maxLength:60,
						maxLength:60,
						maxLengthText:'输入文本过长',
						fieldLabel : '公司地址 '
					}, {
						columnWidth : .30,
						layout : 'form',
						xtype : 'textfield',
						anchor : '95%',
						maxLength:10,
						maxLengthText:'输入文本过长',
						name : 'consumerZip',
//						regex : /[^\d] /,
//						regexText : "只能输入英文和字符!",
						fieldLabel : '邮编 '
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
									anchor : '95%',
									
									store: new Ext.data.SimpleStore({
										fields: ['value', 'text'],
										data: [['0', '启用'],['1', '删除'],['2', '停用']]
									})
			        	       })
		            		]
		            

						
					}],
			buttons : [{
				text : '保存',
				handler : function() {
					addconsumerForm.form.doAction('submit', {
						url : '/myads/HTML/sysfun/ConsumerAction_save.action',
						method : 'post',
						params : '',
						success : function(form, action) {
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
					addconsumerForm.form.reset();
					winAdd.hide();
				}
			}]

		});
		
	//TODO 添加客户
	function showAddconsumer() {
		addconsumerForm.form.reset();	
		addconsumerForm.getForm().findField("status").setValue(0);
		winAdd.show();

	}
	
	var winAdd = new Ext.Window({
				width:400,
				height:280,
				layout:'border',
				modal : true,
				title : "客户管理",
				closeAction:'hide',
				items : addconsumerForm
			});

	consumerForm.render('consumerForm');

	
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
						},{
							xtype : 'hidden',
							name : 'type',
							value:'C',
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
		addPersonForm.getForm().findField("type").setValue('C');
		//winPerson.title= name+'的联系人';
		dsPerson.load({
			params : {
				start : 0,
				limit : 20,
				type : 'C',
				consumerId :id
			}
		});
		winPerson.show();
	};
	//TODO 添加联系人结束
	function renderOp(value, cellmeta, record, rowIndex, columnIndex, stor) {
		var consumerId = record.data.consumerId;
		var name = record.data.consumerName;
		var editStr = '<a href="#" onclick=\"showInfo(\''
				+ consumerId + '\');">编辑</a>';
		var pStr = '<a href="#" onclick=\"showPerson(\''
			+ consumerId + '\', \'' + name + '\');">联系人</a>';
		var delStr = '<a href="#" onclick=\"deleteConsumer(\''
				+ consumerId + '\', \'' + name + '\');">删除</a>';
		return editStr + '&nbsp;&nbsp;&nbsp;&nbsp;'+ pStr +'&nbsp;&nbsp;&nbsp;&nbsp;' + delStr;
	}

	window.deleteConsumer = function(consumerId, name) {
		Ext.MessageBox.confirm('提示', '确定删除  ' + name + ' ？', function(btn) {
			if (btn == 'yes') {
				var param = [];
				param.push(consumerId);
				Ext.Ajax.request({
					method : 'post',
					url : '/myads/HTML/sysfun/ConsumerAction_delete.action',
					success : function(resp) {
						var obj = Ext.util.JSON.decode(resp.responseText);
						if (obj.result == 'success') {
							grid.getStore().reload();
							dataTypeCombStore.load();
							Ext.MessageBox.alert('提示', '删除成功！');
						}else if (obj.result == 'use') {
							grid.getStore().reload();
							dataTypeCombStore.load();
							Ext.MessageBox.alert('提示', '删除失败，客户正在使用中！');
						}
					},
					params : {
						consumerList : param.join(',')
					}
				});
			}
		});
	};
	//修改
	window.showInfo = function(consumerId) {
		dataTypeCombStore2.load();
		addconsumerForm.load({
					url : '/myads/HTML/sysfun/ConsumerAction_detail.action',
					params : {
						consumerId : consumerId
					}
				});
				winAdd.show();
	};
	
	var sm = new Ext.grid.CheckboxSelectionModel();
	var cm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(), sm, {
				header : '名称',
				dataIndex : 'consumerName',
				
				sortable : true
			}, {
				header : '类别',
				dataIndex : 'categoryName',
				sortable : true
			}, {
				header : '所属地区',
				dataIndex : 'areaName',
				sortable : true
			}, {
				header : '上级公司',
				dataIndex : 'companyName',
				sortable : true
			}, {
				header : '状态',
				dataIndex : 'status',
				sortable : true,
				renderer:rendStauts
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
					url : '/myads/HTML/sysfun/ConsumerAction_showAll.action'
				}),
		remoteSort : true,
		reader : new Ext.data.JsonReader({
					totalProperty : 'total',
					idProperty : 'consumerId',
					root : 'invdata'
				}, [{
							name : 'consumerId'
						}, {
							name : 'areaName'
						}, {
							name : 'categoryName'
						}, {
							name : 'companyName'
						}, {
							name : 'consumerName'
						}, {
							name : 'status'
						}])
	});

	ds.load({
				params : {
					start : 0,
					limit : 10,
					status:0
				}
			});

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
			pageSize : 10,
			store : ds,
			displayInfo : true,
			displayMsg : '显示第{0}条到{1}条记录,一共{2}条',
			emptyMsg : '没有记录'
//			items : ['-', {
//				text : '删除',
//				handler : function() {
//					var rs = grid.getSelectionModel().getSelections();
//					var param = [];
//					Ext.each(rs, function() {
//								param.push(this.get("consumerId"));
//							});
//				if(param.length>0){
//					Ext.MessageBox.confirm("提示", "确定删除？",
//							function(consumerId) {
//								if (consumerId == 'yes') {
//									Ext.Ajax.request({
//										method : 'post',
//										url : '/myads/HTML/sysfun/ConsumerAction_delete.action',
//										success : function(resp) {
//											var obj = Ext.util.JSON
//													.decode(resp.responseText);
//											if (obj.result == 'success') {
//												grid.getStore().reload();
//												Ext.MessageBox.alert('提示',
//														'删除成功！');
//											}
//										},
//										params : {
//											consumerList : param.join(',')
//										}
//									});
//								} else {
//								}
//							});
//					}else{
//						Ext.MessageBox.alert('提示', '请选择需要删除的客户！');
//					}
//				}
//			}]
		})
	});

	grid.addListener('rowdblclick', function(grid, rowindex, e) {
		var record = grid.getStore().getAt(rowindex);
			addconsumerForm.load({
					url : '/myads/HTML/sysfun/ConsumerAction_detail.action',
					params : {
						consumerId : record.data.consumerId
					}
				});
				winAdd.show();
				
	});

	grid.render();
		var mainViewPort = new Ext.Viewport({
		layout: 'border',
		items:[
		       consumerForm, grid
		]
	});
});
