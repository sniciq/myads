Ext.onReady(function() {
	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget='side';
	
	var dataTypeCombStore1 = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
					url : '/myads/HTML/basic/OptionsAction_getBaseDataNamesByType.action?dataType=status'
					
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
					url : '/myads/HTML/basic/OptionsAction_getBaseDataNamesByType.action?dataType=postemtype'
					
				}),
		reader : new Ext.data.ArrayReader({}, [{
							name : 'value'
						}, {
							name : 'text'
						}])
	});
	dataTypeCombStore2.load();
	
	// 模板种类
	var postemTypeStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({url: '/myads/HTML/advert/AdvpositionAction_getPostemTypeByPageType.action'}),
		reader: new Ext.data.ArrayReader({}, [
			{name: 'value'},{name: 'text'}
		])
	});
	

	
	var postemplateForm = new Ext.form.FormPanel({
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
											name : 'id',
											hidden : true,
											hiddenLabel : true
										}]
							}]
				}, {
					layout : 'column',
					items : [{
								columnWidth : .25,
								layout : 'form',
								items : [{
											xtype : 'textfield',
											name : 'name',
											fieldLabel : '名称 ',
											anchor : '95%'
										}]
							},{
								
								layout : 'form',
								items : [new Ext.form.ComboBox({
										
											layout : 'form',
											fieldLabel : '页面类型',
											editable : false,
											mode : 'local',
											hiddenName : 'type',
											triggerAction : 'all',
											displayField : 'text',
											valueField : 'value',
											anchor : '95%',
											store : dataTypeCombStore2
											
										})]
							},{
								
								layout : 'form',
								items : [new Ext.form.ComboBox({
											
											layout : 'form',
											fieldLabel : '状态',
											editable : false,
											mode : 'local',
											hiddenName : 'status',
											triggerAction : 'all',
											displayField : 'text',
											valueField : 'value',
											anchor : '95%',
											value:0,
											store: new Ext.data.SimpleStore({
														fields: ['value', 'text'],
														data: [['0', '启用'],['2', '停用'],['', '全部']]
													})
										})]
							}]
				}],
				
		buttons : [{
			text : '查询',
			handler : function() {
				
				var fv = postemplateForm.getForm().getValues();
						ds.baseParams = fv;
						ds.load({
								params : {
										start : 0,
										limit : 15
									}
								});
			}
		}, {
			text : '重置',
			handler : function() {
				postemplateForm.form.reset();
				
			}
		}, {
			text : '新增广告位模板',
			handler : function() {
				showAddpostemplate();
			}
		}]

	});
	

			
	var addpostemplateForm = new Ext.form.FormPanel({
			labelAlign : 'right',
			labelWidth : 80,
			height : 280,
			frame : true,
			xtype : 'fieldset',
			items : [
					{
						
						xtype : 'textfield',
						name : 'name',
						allowBlank : false,
						maxLength:80,
						maxLengthText:'输入文本过长',
						blankText: '此项不能为空!',
						fieldLabel : '名称',
						anchor : '95%'
					}, {
						xtype : 'hidden',
						name : 'id',
						fieldLabel : 'id'			
					},{
						xtype : 'textfield',
						name : 'nameEn',
						allowBlank : false,
						blankText : "不能为空!",
						regex : /^[\w-\s]+$/,
						regexText : "只能输入英文和字符!",
						fieldLabel : '英文名称',
						anchor : '95%',
						maxLength:40,
						maxLengthText:'输入文本过长'
					}, {
						xtype : 'textfield',
						name : 'barNum',
						fieldLabel : '广告条数',
						anchor : '95%',
						allowBlank : false,
						blankText : "只能输入数字",
						regex : /\d/,
						regexText : "只能输入数字",
						maxLength:2,
						maxLengthText:'输入文本过长'
					},{
					
					layout : 'form',
					items : [new Ext.form.ComboBox({
								allowBlank : false,
								blankText: '此项不能为空!',
								layout : 'form',
								fieldLabel : '页面类型',
								
								editable : false,
								mode : 'local',
								hiddenName : 'type',
								triggerAction : 'all',
								displayField : 'text',
								valueField : 'value',
								anchor : '95%',
								store : dataTypeCombStore2,
								listeners : {
					                select : function(ComboBox, record, index) {
					                	
					                	var text = record.data.text;
					                	
					                	postemTypeStore.load({params: {dataName : encodeURI(text)}});
					                }
					            }
							})]
				},{
					
					layout : 'form',
					items : [new Ext.form.ComboBox({
								allowBlank : false,
								blankText: '此项不能为空!',
								layout : 'form',
								fieldLabel : '模板种类',
							
								editable : false,
								mode : 'local',
								name : 'ptype',
								triggerAction : 'all',
								displayField : 'text',
								valueField : 'value',
								anchor : '95%',
								store : postemTypeStore
							})]
				},{
					
					layout : 'form',
					items : [new Ext.form.ComboBox({
								allowBlank : false,
								blankText: '此项不能为空!',
								layout : 'form',
								fieldLabel : '状态',
							
								editable : false,
								mode : 'local',
								hiddenName : 'status',
								triggerAction : 'all',
								displayField : 'text',
								valueField : 'value',
								anchor : '95%',
								value :0,
								store: new Ext.data.SimpleStore({
														fields: ['value', 'text'],
														data: [['0', '启用'],['2', '停用']]
													})
							})]
				},{
					xtype : 'textarea',
					name : 'code',
					fieldLabel : 'CODE',
					anchor : '95%',
					height:80
						
				}],
			buttons : [{
				text : '保存',
				handler : function() {
					addpostemplateForm.form.doAction('submit', {
						url : '/myads/HTML/advert/PostemplateAction_save.action',
						method : 'post',
						params : '',
						success : function(form, action) {
							if(action.result.result=='success'){
								Ext.MessageBox.alert('结果', '保存成功！');
								form.reset();
								grid.getStore().reload();
								winAdd.hide();
							}else if(action.result.result=='use'){
								Ext.MessageBox.alert('结果', '保存失败，模板正在使用中，无法停用！');
							}
							
						}
					});
				}
			}, {
				text : '取消',
				handler : function() {
					addpostemplateForm.form.reset();
					winAdd.hide();
				}
			}]

		});
	

	
		
	//添加广告位模板
	function showAddpostemplate() {
		addpostemplateForm.form.reset();	
		winAdd.show();
	}
	
	var winAdd = new Ext.Window({
				width : 500,
				height : 310,
				modal : true,
				title : "广告位模板管理",
				closeAction:'hide',
				items : addpostemplateForm
			});

	postemplateForm.render('postemplateForm');

	function renderOp(value, cellmeta, record, rowIndex, columnIndex, stor) {
		var id = record.data.id;
		var name = record.data.name;
	
		var editStr = '<a href="#" onclick=\"showUpdate(\''+ id + '\', \'' + name + '\');">编辑</a>';
		var delStr = '<a href="#" onclick=\"deletePostemplate(\''+ id + '\', \'' + name + '\');">删除</a>';
		var bartemplate = '<a href="#" onclick=\"showBartemplate(\''+ id + '\', \'' + name + '\');">广告条模板</a>';
		
		return editStr + '&nbsp;&nbsp;&nbsp;&nbsp;' +  bartemplate+ '&nbsp;&nbsp;&nbsp;&nbsp;' +delStr ;
	}
	//TODO 删除广告条模板
	function renderOp2(value, cellmeta, record, rowIndex, columnIndex, stor) {
		
		var id = record.data.id;
		var name = record.data.bartemName;
		
		var delStr = '<a href="#" onclick=\"deleteProduct(\''+ id + '\', \'' + name + '\');">删除</a>';		
		return  delStr ;
	}


	
	//删除广告位模板
	window.deletePostemplate = function(id, name) {
		Ext.MessageBox.confirm('提示', '确定删除  ' + name + '  及与广告条模板的关系 ？', function(btn) {
			if (btn == 'yes') {
				var param = [];
				param.push(id);
				Ext.Ajax.request({
					method : 'post',
					url : '/myads/HTML/advert/PostemplateAction_delete.action',
					success : function(resp) {
						var obj = Ext.util.JSON.decode(resp.responseText);
						if (obj.result == 'success') {
							grid.getStore().reload();

							Ext.MessageBox.alert('提示', '删除成功！');
						}else if (obj.result == 'use') {
							grid.getStore().reload();
							Ext.MessageBox.alert('提示', '删除失败，选中的模板正在使用！');
						}
					},
					params : {
						postemplateList : param.join(',')
					}
				});
			}
		});
	};
	//修改
	window.showUpdate = function(id) {
		addpostemplateForm.load({
					url : '/myads/HTML/advert/PostemplateAction_detail.action',
					params : {
						id : id
					}
				});
				winAdd.show();
	};
	
	

	//判断状态
	function rendStauts(value){
	
		if(value==0){
			return "<span style='color:GREEN'>启用</span>";
		}else if(value==1){
			return "<span style='color:GRAY'>已删除</span>";
		}else if(value==2){
			return "<span style='color:RED'>停用</span>";
		}
	}
	
	
	var sm = new Ext.grid.CheckboxSelectionModel();
	var cm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(), sm, {
				header : '名称',
				dataIndex : 'name',
				sortable : true
			}, {
				header : '英文名称',
				dataIndex : 'nameEn',
				sortable : true
			}, {
				header : '状态',
				dataIndex : 'status',
				renderer:rendStauts,
				sortable : true
			}, {
				header : '页面类型',
				dataIndex : 'typeString',
				sortable : true
			}
			, {
				header : '模板种类',
				dataIndex : 'ptype',
				sortable : true
			}
			, {
				header : '广告条数',
				dataIndex : 'barNum',
				sortable : true
			}, 
//			{
//				header : '修改人',
//				dataIndex : 'modifier',
//				sortable : true
//			}, 
//			{
//				header : '修改时间',
//				dataIndex : 'modifyTime',
//				sortable : true
//			},
			{
				header : '创建人',
				dataIndex : 'creator',
				sortable : true
			}, {
				header : '创建时间',
				dataIndex : 'createTime',
				sortable : true
			}, {
				header : '操作',
				dataIndex : 'op',
				renderer : renderOp,
				width : 120,
				align : 'left'
			}]);

	var ds = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
					url : '/myads/HTML/advert/PostemplateAction_showAll.action'
				}),								
		remoteSort : true,
		reader : new Ext.data.JsonReader({
					totalProperty : 'total',
					idProperty : 'id',
					root : 'invdata'
				}, [{
							name : 'id'
						}, {
							name : 'name'
						}, {
							name : 'nameEn'
						}, {
							name : 'status'
						}, {
							name : 'typeString'
						}, {
							name : 'ptype'
						}, {
							name : 'creator'
						}, {
							name : 'createTime'
						}, {
							name : 'barNum'
						}, {
							name : 'modifier'
						}, {
							name : 'modifyTime'
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
										url : '/myads/HTML/advert/PostemplateAction_delete.action',
										success : function(resp) {
											var obj = Ext.util.JSON
													.decode(resp.responseText);
											if (obj.result == 'success') {
												grid.getStore().reload();
												Ext.MessageBox.alert('提示', '删除成功！');
											}else if (obj.result == 'use') {
												grid.getStore().reload();
												Ext.MessageBox.alert('提示', '选中的模板中某些正在使用！');
											}
										},
										params : {
											postemplateList : param.join(',')
										}
									});
								} else {
								}
							});
					}else{
						Ext.MessageBox.alert('提示', '请选择需要删除的模板！');
					}
				}
			}]
		})
	});

	grid.addListener('rowdblclick', function(grid, rowindex, e) {
		var record = grid.getStore().getAt(rowindex);
			addpostemplateForm.load({
					url : '/myads/HTML/advert/PostemplateAction_detail.action',
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
		       postemplateForm, grid
		]
	});
		
		//TODO 广告条
		window.showBartemplate = function(id,name) {
			
			var dataTypeCombStore3 = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : '/myads/HTML/advert/BartemplateAction_getList.action'
							
						}),
				reader : new Ext.data.ArrayReader({}, [{
									name : 'value'
								}, {
									name : 'text'
								}])
			});
			dataTypeCombStore3.load();
			var addBartemplate = new Ext.form.FormPanel({
				labelAlign : 'right',
				labelWidth : 100,
				frame : true,
				xtype : 'fieldset',
				items : [{
							xtype : 'textfield',
							name : 'postemName',
							fieldLabel : '所属广告位模板',
							value:name,
							readOnly:true,
							anchor : '95%'
						},
						{
							xtype : 'hidden',
							name : 'postemId',
							fieldLabel : '所属广告位模板Id',
							value:id,
							readOnly:true,
							anchor : '95%'
						},{
							layout : 'form',
							items : [new Ext.form.ComboBox({
										layout : 'form',
										fieldLabel : '关联广告条模板',
										allowBlank : false,
     									blankText : "此项不能为空哦!",
										editable : false,
										mode : 'local',
										hiddenName : 'bartemId',
										triggerAction : 'all',
										displayField : 'text',
										valueField : 'value',
										anchor : '95%',
										store : dataTypeCombStore3
									})]
						}],
						
				buttons : [{
					text : '保存',

					handler : function() {
							addBartemplate.form.doAction('submit', {
							url : '/myads/HTML/advert/PostemplateAction_saveBartem.action',
							method : 'post',
							params : '',
							success : function(form, action) {
							if(action.result.result == 'success') {
								Ext.MessageBox.alert('结果', '保存成功！');
								grid2.getStore().reload();
															
							}else if(action.result.result == 'multiple'){
								Ext.MessageBox.alert('结果', '保存失败，广告位模板下已经有该广告条模板！');
								grid2.getStore().reload();
							}
								
							}
						});
					}
				}, {
					text : '取消',
					handler : function() {
						addBartemplate.form.reset();
						win1.hide();
					}
				}]

			});
				
		var sm2 = new Ext.grid.CheckboxSelectionModel();
		var cm2 = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(), 
		
				{
					header : '关系Id',
					dataIndex : 'id',
					sortable : true
				},{
					header : '关联广告条模板名称',
					dataIndex : 'bartemName',
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
						url : '/myads/HTML/sysfun/PostemplateAction_showBartemplate.action?postemplateId='+id
					}),
			remoteSort : true,
			reader : new Ext.data.JsonReader({
						totalProperty : 'total',
						idProperty : 'id',
						root : 'invdata'
					}, [{
								name : 'id'
							}, {
								name : 'bartemName'
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
			//sm : sm2,
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
				emptyMsg : '没有记录'
		
			})
		});
		
		
		window.deleteProduct = function(id, name) {
			
			Ext.MessageBox.confirm('提示', '确定删除  ' + name + ' ？', function(btn) {
			
				if (btn == 'yes') {
					var param = [];
					param.push(id);
					Ext.Ajax.request({
						method : 'post',
						url : '/myads/HTML/sysfun/PostemplateAction_deleteBartem.action',
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
						title : "广告位模板管理",
						items : [addBartemplate,grid2]
					});
					
		
			win1.show();
		};
});
