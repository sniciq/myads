Ext.onReady(function() {
	
	var advActiveId = Ext.get('advActiveId').dom.value;// 跨页面拿到广告活动id
	var adv_win;
	var grid;
	var ds;
	
	var combStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
					url : '/myads/HTML/advert/BartemplateAction_getList.action'
				}),
		reader : new Ext.data.ArrayReader({}, [{
							name : 'id'
						}, {
							name : 'name'
						}])
	});
	combStore.load();

	var ds123 = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url : '/myads/HTML/advflight/AdvertisementAction_getTemplateList.action'
		}),
		remoteSort : false,
		reader : new Ext.data.JsonReader({
					totalProperty : 'total',
					idProperty : 'id',
					root : 'invdata'
				}, [{
							name : 'itemtype'
						}, {
							name : 'url'
						}])
	});

	var urlcm = new Ext.grid.ColumnModel([{
				dataIndex : 'itemtype',
				header : '物料类型',
				width : 70,
				resizable : false,
				menuDisabled : true
			}
	// {dataIndex:'url', header:'物料地址(双击栏位添加)',width:405,resizable
	// :false,menuDisabled : true, editor: new Ext.form.TextField({allowBlank:
	// false})}
	]);
	var urlsGrid = new Ext.grid.EditorGridPanel({
		ds : ds123,
		cm : urlcm,
		width : 200,
		height : 200,
		frame : true
	});

	//定向策略 -----------------------------------开始		
	var frequencyType_group;
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
											var a = adv_form.getForm().findField("frequencyType");
											var b = adv_form.getForm().findField("frequencyNum");
											b.allowBlank = false;
											a.allowBlank = false;
											b.minValue = 1;
											b.minText = '值必须大于0！';
											b.show();
											a.show();
										}
										else {
											var a = adv_form.getForm().findField("frequencyType");
											var b = adv_form.getForm().findField("frequencyNum");
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

	var adv_form = new Ext.form.FormPanel({
		labelAlign : 'left',
		labelWidth : 100,
		frame : true,
		layout : 'form',
		items : [{
					xtype : 'hidden',
					name : 'id'
				}, {
					width : 250,
					xtype : 'textfield',
					name : 'name',
					fieldLabel : '广告名称',
					allowBlank : false,
					anchor : '.9'
				}, {
					width : 250,
					allowBlank : false,
					fieldLabel : '广告条模板',
					xtype : 'combo',
					hiddenName : 'bartemplateId',
					name : 'bartemplateId',
					store : combStore,
					mode : 'local',
					editable : false,
					triggerAction : 'all',
					valueField : 'id',
					displayField : 'name',
					listeners : {
						select : function(combo, record, index) {
							urlsGrid.getStore().removeAll();
							var templateId = record.data.id;
							Ext.Ajax.request({
								method : 'post',
								url : '/myads/HTML/advert/BartemplateAction_detail.action?id='
										+ templateId,
								success : function(resp) {
									var obj = Ext.util.JSON
											.decode(resp.responseText);
									for (var i = 0; i < obj.data.textNum; i++) {
										var MyUrl = urlsGrid.getStore().recordType;
										var u = new MyUrl({
													itemtype : 'text：',
													url : ''
												});
										urlsGrid.stopEditing();
										ds123.insert(0, u);
									}
									for (var i = 0; i < obj.data.videoNum; i++) {
										var MyUrl = urlsGrid.getStore().recordType;
										var u = new MyUrl({itemtype : 'video：',url : ''});
										urlsGrid.stopEditing();
										ds123.insert(0, u);
									}
									for (var i = 0; i < obj.data.imgNum; i++) {
										var MyUrl = urlsGrid.getStore().recordType;
										var u = new MyUrl({itemtype : 'image：',url : ''});
										urlsGrid.stopEditing();
										ds123.insert(0, u);
									}
									for (var i = 0; i < obj.data.materialSum; i++) {
										var MyUrl = urlsGrid.getStore().recordType;
										var u = new MyUrl({itemtype : 'all：',url : ''});
										urlsGrid.stopEditing();
										ds123.insert(0, u);
									}
								}
							});
						}
					}
				}, {
					xtype : 'textfield',
					name : 'redirect',
					allowBlank : true,
					fieldLabel : '跳转地址',
					width : 250,
					anchor : '.9'
				}, {
					xtype : 'textfield',
					name : 'monition',
					allowBlank : true,
					fieldLabel : '第三方监测',
					width : 250,
					anchor : '.9'
				}, {
					fieldLabel : '状态',
					width : 250,
					xtype : 'combo',
					hiddenName : 'status',
					mode : 'local',
					editable : false,
					store : [[0, '启用'], [2, '停用']],
					editable : false,
					allowBlank : false,
					triggerAction : 'all'
				},{
					xtype: 'checkbox',
					fieldLabel: '是否关联广告产品',
					width : 150 ,
					name: 'isProduct',
					id: 'isProduct', 
					inputValue: '1'
				}
				, {
					xtype : 'hidden',
					name : 'editable',
					allowBlank : false,
					anchor : '.9'
				}, {
					xtype : 'hidden',
					name : 'isAdmin',
					fieldLabel : '管理员',
					anchor : '.9'
				},{
					items:[frequencyType_group]
				}
		],
		buttons : [{
			text : '保存',
			id : 'savebtn',
			handler : function() {
				var flag = true;
				var selFuns = [];
				ds123.each(function(recods) {
					selFuns.push(recods.data);
				});
				if (flag == true) {
					var fo = new Object();
					fo.urlDataAry = Ext.util.JSON.encode(selFuns);
					adv_form.form.doAction('submit', {
						url : '/myads/HTML/advflight/AdvertisementAction_save.action?advActiveId='
								+ advActiveId,
						method : 'post',
						params : fo,
						success : function(form, action) {
							Ext.MessageBox.alert('结果', '保存成功！');
							form.reset();
							grid.getStore().reload();
							urlsGrid.getStore().removeAll();
							adv_win.hide();
						}
					});
				} else {
					alert('请将全物料地址全部添加完毕！');
					return;
				}
			}
		}, {
			text : '取消',
			handler : function() {
				adv_win.hide()
			}
		}]
	});

	adv_win = new Ext.Window({
		title : '增加广告',
		layout : 'fit',
		width : 400,
		height :340,
		modal : true,
		closeAction : 'hide',
		items : [adv_form],
		plain : true
	});

	var searchForm = new Ext.form.FormPanel({
		labelAlign : 'right',
		region : 'north',
		labelWidth : 70,
		frame : true,
		items : [{
					layout : 'column',
					items : [
						new Ext.Button({
						text : '返回',
						width : 70,
						handler : function() {
							window.history.back();
						}
					})]
				}, {
					layout : 'column',
					items : [{
								columnWidth : .25,
								layout : 'form',
								items : [{
											xtype : 'textfield',
											name : 'id',
											fieldLabel : '广告ID',
											anchor : '95%'
										}]
							}, {
								columnWidth : .25,
								layout : 'form',
								items : [{
											xtype : 'textfield',
											name : 'name',
											fieldLabel : '广告名称',
											anchor : '95%'
										}]
							}, {
								columnWidth : .25,
								layout : 'form',
								items : [{
											columnWidth : .25,
											layout : 'form',
											items : [{
														xtype : 'textfield',
														name : 'bartemplateName',
														fieldLabel : '模板名称',
														anchor : '95%'
													}]
										}]
							}]
				}],
		buttons : [{
					text : '查询',
					handler : function() {
						var fv = searchForm.getForm().getValues();
						ds.baseParams = fv;
						ds.load({
									params : {
										start : 0,
										limit : 15
									}
								});
					}
				}, {
					// 添加广告开始
					text : '添加',
					handler : function() {
						adv_form.getForm().reset();
						adv_form.getForm().findField("bartemplateId").setDisabled(false);
						adv_form.getForm().findField("name").setDisabled(false);
						adv_form.getForm().findField("redirect").setDisabled(false);
						adv_form.getForm().findField("monition").setDisabled(false);
						adv_form.getForm().findField("status").setDisabled(false);
						adv_form.getForm().findField("redirect").setValue("http://");
						adv_form.getForm().findField("monition").setValue("http://");
//						adv_form.getForm().findField("weight").setValue("10");
//						adv_form.getForm().findField("priority").setValue("3");
//						adv_form.getForm().findField("weight").hide();
//						adv_form.getForm().findField("priority").hide();
						Ext.getCmp('savebtn').show();
						adv_win.show();
					}
				}]
	});
	searchForm.render('north-div');

	// 判断状态
	function rendStauts(value) {
		if (value == 0) {
			return "<span style='color:GREEN'>启用</span>";
		} else if (value == 1) {
			return "<span style='color:GRAY'>已删除</span>";
		} else if (value == 2) {
			return "<span style='color:RED'>停用</span>";
		}
	}

	var cm = new Ext.grid.ColumnModel([{
				header : 'ID',
				dataIndex : 'id',
				sortable : true,
				width : 20
			}, {
				header : '名称',
				dataIndex : 'name',
				width : 60,
				sortable : true
			}, {
				header : '模板名称',
				dataIndex : 'bartemplateName',
				width : 60,
				sortable : true
			}, {
				header : '创建日期',
				dataIndex : 'createTime',
				width : 60,
				sortable : true
			}, {
				header : '状态',
				dataIndex : 'status',
				width : 30,
				renderer : rendStauts,
				sortable : true
			}, {
				header : '操作',
				dataIndex : 'op',
				renderer : renderOp,
				width : 200,
				align : 'left'
			}]);

	function renderOp(value, cellmeta, record, rowIndex, columnIndex, stor) {
		var id = record.data.id;
		var dataValue = record.data.name;
		var bartemplateId = record.data.bartemplateId;
	
		var relationStr = '';
		
		if(record.data.isProduct == 1){
			
			var relationPructB = '<a href="#" onclick=\"relationPruct(\'' + id + '\', \''	+ bartemplateId + '\', \'B\');\">关联产品</a>';
			var relationPructA = '<a href="#" onclick=\"relationPruct(\'' + id + '\', \''	+ bartemplateId + '\', \'A\');\">关联排期</a>';
			relationStr = relationPructB + "&nbsp;&nbsp;&nbsp;&nbsp;" + relationPructA;
		}
		else {
			relationStr = '<a href="#" onclick=\"relation_pc(\'' + id + '\', \''	+ bartemplateId + '\');\">关联排期</a>';
		}
		
		var thingStr = '<a href="#" onclick=\"setThing(\'' + id + '\', \''	+ dataValue + '\');\">物料设置</a>';
		var editStr = '<a href="#" onclick=\"edit(\'' + id + '\', \''+ dataValue + '\');\">编辑</a>';
		var flightStr = '<a href="#" onclick=\"flightAdv(\'' + id + '\', \''+ dataValue + '\');\">预投放</a>';
		var FlightingStr = '<a href="#" onclick=\"FlightingAdv(\'' + id + '\', \'' + dataValue + '\');\">投放</a>';
		var stopFlightStr = '<a href="#" onclick=\"stopFlightAdv(\'' + id+ '\', \'' + dataValue + '\');\">停止投放</a>';
		var flightDetail = '<a href="#" onclick=\"flightDetail(\'' + id	+ '\', \'' + dataValue + '\');\">预览</a>';
		var clickAdress = '<a href="#" onclick=\"clickAdress(\'' + id+ '\', \'' + advActiveId + '\');\">点击地址</a>';
		var deleteStr = '<a href="#" onclick=\"deleteAdv(\'' + id + '\', \''+ dataValue + '\');\">删除</a>';
		
		return relationStr + '&nbsp;&nbsp;&nbsp;&nbsp;' + thingStr + '&nbsp;&nbsp;&nbsp;&nbsp;' + editStr + '&nbsp;&nbsp;&nbsp;&nbsp;' + flightStr+ '&nbsp;&nbsp;&nbsp;&nbsp;' + FlightingStr
				+ '&nbsp;&nbsp;&nbsp;&nbsp;' + stopFlightStr+ '&nbsp;&nbsp;&nbsp;&nbsp;' + flightDetail	+ '&nbsp;&nbsp;&nbsp;&nbsp;' + clickAdress+ '&nbsp;&nbsp;&nbsp;&nbsp;' + deleteStr;
	}

	var relation_win = new Ext.Window({
		id : 'relation_win',
		title : '关联排期',
		applyTo : 'relation-win',
		layout : 'fit',
		width : 390,
		height : 320,
		closeAction : 'hide',
		plain : true
	});
	
	var relationProduct_win = new Ext.Window({
		id : 'relationProduct_win',
		title : '关联产品',
		applyTo : 'relationProduct_win',
		layout : 'fit',
		width : 390,
		height : 320,
		closeAction : 'hide',
		plain : true
	});
	
	window.relationPruct = function(id, bartemplateId, relationType) {
		var url = '/myads/HTML/advflight/relation_product.jsp?advertisementId=' + id + '&bartemplateId=' + bartemplateId + '&relationType=' + relationType;
		relationProduct_win.update({
			title : '关联产品',
			html : '<iframe scrolling="auto" frameborder="0" width="100%" height="100%" src="'+ url + '"></iframe>'
		});
		relationProduct_win.show();
	}
			
	
	//关联排期		
	window.relation_pc = function(id, bartemplateId) {
		var url = '/myads/HTML/advflight/relation_flight.jsp?advertisementId=' + id + '&bartemplateId=' + bartemplateId;
		relation_win.update({
			title : '关联排期',
			html : '<iframe scrolling="auto" frameborder="0" width="100%" height="100%" src="'+ url + '"></iframe>'
		});
		relation_win.show();
	};
	
	window.relation_Ok = function(id) {
		relation_win.hide();
	}

	window.setThing = function(id, dataValue) {
		var param = encodeURI(encodeURI(dataValue));

		window.location.href = '/myads/HTML/advflight/adv_material.jsp?advId='
				+ id + '&advName=' + param;
	};

	window.edit = function(id, dataValue) {
		ds123.removeAll();
		adv_form.load({
			url : '/myads/HTML/advflight/AdvertisementAction_getDetail.action',
			params : {
				id : id
			},
			success:function(form, action){
			//	var obj = Ext.util.JSON.decode(text.responseText);
					//alert(action.result.data.isAdmin)
//					if (form.findField("isAdmin").getValue()== 0) {
//						adv_form.getForm().findField("weight").hide();
//						adv_form.getForm().findField("priority").hide();
//						
//					}else if(form.findField("isAdmin").getValue()== 1){
//						adv_form.getForm().findField("weight").show();
//						adv_form.getForm().findField("priority").show();
//						adv_win.height = 100;
//					}
				
					if (form.findField("editable").getValue() == 1) {
						adv_form.getForm().findField("bartemplateId").setDisabled(true);
						adv_form.getForm().findField("name").setDisabled(true);
						adv_form.getForm().findField("redirect").setDisabled(true);
						adv_form.getForm().findField("monition").setDisabled(true);
						adv_form.getForm().findField("status").setDisabled(true);
						Ext.getCmp('savebtn').hide();
					} else {
						adv_form.getForm().findField("bartemplateId").setDisabled(false);
						adv_form.getForm().findField("name").setDisabled(false);
						adv_form.getForm().findField("redirect").setDisabled(false);
						adv_form.getForm().findField("monition").setDisabled(false);
						adv_form.getForm().findField("status").setDisabled(false);
						Ext.getCmp('savebtn').show();
					};
			}
			
		});
		ds123.load({
					params : {
						id : id
					}
				});
		
		adv_win.show();
		
		
	};

	window.deleteAdv = function(id, name) {
		Ext.MessageBox.confirm('提示', '确定删除  ' + name + ' ？', function(btn) {
			if (btn == 'yes') {
				Ext.Ajax.request({
					method : 'post',
					url : '/myads/HTML/advflight/AdvertisementAction_delete.action',
					success : function(resp) {
						var obj = Ext.util.JSON.decode(resp.responseText);
						if (obj.result == 'success') {
							grid.getStore().reload();
							Ext.MessageBox.alert('提示', '删除成功！');
						}
					},
					params : {
						id : id
					}
				});
			}
		});
	};
	// 预先投放
	window.flightAdv = function(id, name) {

		Ext.Ajax.request({
					method : 'post',
					url : '/myads/HTML/advflight/AdvFlightAction_save.action',
					success : function(resp) {
						var obj = Ext.util.JSON.decode(resp.responseText);

						if (obj.result == 'success') {
							grid.getStore().reload();
							Ext.MessageBox.alert('提示', '预投放成功！');
						} else if (obj.result == 'advMaterialFail') {
							grid.getStore().reload();
							Ext.MessageBox.alert('提示', '预投放失败！物料未关联');
						} else if (obj.result == 'BookFail') {
							grid.getStore().reload();
							Ext.MessageBox.alert('提示', '预投放失败！未关联排期');
						} else if (obj.result == 'codeFail') {
							grid.getStore().reload();
							Ext.MessageBox.alert('提示', '预投放失败！广告条代码为空');
						} else if (obj.result == 'multiple') {
							grid.getStore().reload();
							Ext.MessageBox.alert('提示', '广告已经在预投放中……');
						} else if (obj.result == 'flighting') {
							grid.getStore().reload();
							Ext.MessageBox.alert('提示', '广告已经在投放中……');
						} else if (obj.result == 'advfail') {
							grid.getStore().reload();
							Ext.MessageBox.alert('提示', '预投放失败！广告Id错误');
						} else {
							grid.getStore().reload();
							Ext.MessageBox.alert('提示',
									'预投放失败！添加投放表失败，请联系系统管理员。');
						}
					},
					params : {
						id : id
					}
				});

	};
	// 投放
	window.FlightingAdv = function(id, name) {

		Ext.Ajax.request({
			method : 'post',
			url : '/myads/HTML/advflight/AdvFlightAction_filghtingStatus.action',
			success : function(resp) {
				
				var obj = Ext.util.JSON.decode(resp.responseText);
			
				if (obj.result == 'success') {
					grid.getStore().reload();
					Ext.MessageBox.alert('提示', '投放成功！');
				} else if (obj.result == 'multiple') {
					grid.getStore().reload();
					Ext.MessageBox.alert('提示', '广告已经在投放中。');
				}else if (obj.result == 'advMaterialFail') {
					grid.getStore().reload();
					Ext.MessageBox.alert('提示', '投放失败！物料未关联');
				}else if (obj.result == 'BookFail') {
					grid.getStore().reload();
					Ext.MessageBox.alert('提示', '投放失败！排期未关联');
				}else if (obj.result == 'bindFail') {
					grid.getStore().reload();
					Ext.MessageBox.alert('提示', '投放失败！绑定的排期错误');
				} else if (obj.result == 'memoryfail') {
					grid.getStore().reload();
					Ext.MessageBox.alert('提示', '推入内存失败，请联系系统管理员');
				}else {
					grid.getStore().reload();
					Ext.MessageBox.alert('提示', '投放失败，后台异常。');
				}
			},
			params : {
				id : id
			}
		});

	};
	// 停止投放
	window.stopFlightAdv = function(id, name) {

		Ext.Ajax.request({
					method : 'post',
					url : '/myads/HTML/advflight/AdvFlightAction_changeStatus.action',
					success : function(resp) {
						var obj = Ext.util.JSON.decode(resp.responseText);
						//Ext.MessageBox.alert(obj.result);
						if (obj.result == 'success') {
							grid.getStore().reload();
							Ext.MessageBox.alert('提示', '停止投放成功！');
						}else if (obj.result == 'multiple') {
							grid.getStore().reload();
							Ext.MessageBox.alert('提示', '还没有投放广告。');
						}else if (obj.result == 'advIdFail') {
							grid.getStore().reload();
							Ext.MessageBox.alert('提示', '广告Id不存在。');
						}else if (obj.result == 'memoryfail') {
							grid.getStore().reload();
							Ext.MessageBox.alert('提示', '清除内存失败。');
						}else {
							grid.getStore().reload();
							Ext.MessageBox.alert('提示', '停止投放失败，后台异常。');
						}
					},
					params : {
						id : id
					}
				});

	};
	// 预览	
	var code_win = new Ext.Window({
				id : 'relation_win',
				title : '预览',
				applyTo : 'code_win',
				layout : 'fit',
				width : 700,
				height : 300,
				closeAction : 'hide'
				
			});
	window.flightDetail = function(id, name) {
			var url = '/myads/HTML/advflight/AdvFlightAction_getAdvFlightDetail.action?id='+ id;
			code_win.update({
				title : '预览',
				html : '<iframe scrolling="auto" frameborder="0" width="100%" height="100%" src="'
						+ url + '"></iframe>'
			});
			code_win.show();
	
	};
	// 获取点击地址
	window.clickAdress = function(id, advActiveId) {
		var adv_win;
		Ext.Ajax.request({
					method : 'post',
					url : '/myads/HTML/advflight/AdvFlightAction_clickAdress.action',
					success : function(resp) {
						var obj = Ext.util.JSON.decode(resp.responseText);
						if (obj.result == 'bookFail') {
							grid.getStore().reload();
							Ext.MessageBox.alert('提示', '请关联排期');
						} else if (obj.result == 'err') {
							grid.getStore().reload();
							Ext.MessageBox.alert('提示', '获取代码失败');
						} else {
							grid.getStore().reload();
							// Ext.MessageBox.alert('点击地址', obj.result);
							
							var label1 = new Ext.form.Label({
										html : obj.result
										})
							
							var clickForm = new Ext.form.FormPanel({
										labelAlign : 'left',
										region : 'north',
										labelWidth : 450,
										frame : true,
										items : [label1],
										buttons : [{
													text : '关闭',
													handler : function() {
														clickAdress_win.hide();
													}
												}]
									});
							clickAdress_win = new Ext.Window({
										title : '点击地址',
										layout : 'fit',
										width : 600,
										height : 350,
										closeAction : 'hide',
										items : [clickForm],
										plain : true
									});
							clickAdress_win.show();
						}
						
					},
					params : {
						id : id,
						advActiveId : advActiveId
					}
				});

	};
	ds = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url : '/myads/HTML/advflight/AdvertisementAction_showAll.action?advActiveId='+ advActiveId
		}),
		remoteSort : false,
		reader : new Ext.data.JsonReader({
					totalProperty : 'total',
					idProperty : 'id',
					root : 'invdata'
				}, [{
							name : 'id'
						}, {
							name : 'name'
						}, {
							name : 'bartemplateName'
						}, {
							name : 'bartemplateId'
						}, {
							name : 'status'
						}, {
							name : 'isProduct'
						}, {
							name : 'createTime'
						}])
	});

	ds.load({
				params : {
					start : 0,
					limit : 20
				}
			});

	grid = new Ext.grid.GridPanel({
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

	grid.addListener('rowdblclick', function(grid, rowindex, e) {
				var record = grid.getStore().getAt(rowindex);
				edit(record.data.id, record.data);
			});

	new Ext.Viewport({
				layout : 'border',
				items : [searchForm, grid]
			});
});