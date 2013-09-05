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
	
	var combo2 = new Ext.form.ComboBox({
		
		layout : 'form',
		fieldLabel : '类型',
		editable : false,
		mode : 'local',
		hiddenName : 'type',
		triggerAction : 'all',
		displayField : 'text',
		valueField : 'value',

		store : (new Ext.data.SimpleStore({
					fields : ['value', 'text'],
					data : [['0', '广告位规格'], ['1', '广告条规格']]
				}))
	});

	var combo1 = new Ext.form.ComboBox({
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
	

	
	var positionsizeForm = new Ext.form.FormPanel({
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
								columnWidth : .25,
								layout : 'form',
								items : [combo2]
							},{
								columnWidth : .25,
								layout : 'form',
								items : [combo1]
							}]
				}],
		buttons : [{
			text : '查询',
			handler : function() {
				
				var fv = positionsizeForm.getForm().getValues();
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
				positionsizeForm.form.reset();
			}
		}, {
			text : '新增广告规格',
			handler : function() {
				showAddpositionsize();
			}
		}]

	});
	

			
	var addpositionsizeForm = new Ext.form.FormPanel({
			labelAlign : 'right',
			labelWidth : 60,
			height : 220,
			frame : true,
			xtype : 'fieldset',
			items : [
					{
						
						xtype : 'textfield',
						name : 'name',
						allowBlank : false,
						blankText: '此项不能为空!',
						fieldLabel : '名称',
						anchor : '90%'
					}, {
						xtype : 'hidden',
						name : 'id',
						fieldLabel : 'id'			
					},{
					
					layout : 'form',
					items : [new Ext.form.ComboBox({
								allowBlank : false,
								blankText: '此项不能为空!',
								layout : 'form',
								fieldLabel : '类型',
								editable : false,
								mode : 'local',
								hiddenName : 'type',
								triggerAction : 'all',
								displayField : 'text',
								valueField : 'value',
								anchor : '90%',
								store : (new Ext.data.SimpleStore({
											fields : ['value', 'text'],
											data : [['0', '广告位规格'], ['1', '广告条规格']]
										}))
							})]
				}, {
						xtype : 'textfield',
						name : 'width',
						fieldLabel : '宽度',
						allowBlank : false,
						blankText : "此项不能为空",
						regex : /\d/,
						regexText : "只能输入数字!",
						anchor : '90%',
						maxLength:10,
						maxLengthText:'输入文本过长'
					}, {
						xtype : 'textfield',
						name : 'high',
						fieldLabel : '高度',
						allowBlank : false,
						blankText : "此项不能为空!",
						regex : /\d/,
						regexText : "只能输入数字!",
						anchor : '90%',
						maxLength:10,
						maxLengthText:'输入文本过长'
					}, {
						xtype : 'textfield',
						name : 'note',
						fieldLabel : '备注',
						anchor : '90%',
						maxLength:10,
						maxLengthText:'输入文本过长'
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
									anchor : '90%',
									value:0,
									store: new Ext.data.SimpleStore({
												fields: ['value', 'text'],
												data: [['0', '启用'],['2', '停用']]
											})
								})]
					}],
			buttons : [{
				text : '保存',
				handler : function() {
					addpositionsizeForm.form.doAction('submit', {
						url : '/myads/HTML/advert/PositionsizeAction_save.action',
						method : 'post',
						params : '',
						success : function(form, action) {
						if(action.result.result == 'success') {
							Ext.MessageBox.alert('结果', '保存成功！');
							form.reset();
							grid.getStore().reload();
							winAdd.hide();
						}else if(action.result.result == 'multiple'){
							Ext.MessageBox.alert('结果', '保存失败，存在相同的宽和高！');
							grid.getStore().reload();
						}else if(action.result.result == 'nameMul'){
							Ext.MessageBox.alert('结果', '保存失败，存在相同名字！');
							grid.getStore().reload();
						}
							
						}
					});
				}
			}, {
				text : '取消',
				handler : function() {
					addpositionsizeForm.form.reset();
					winAdd.hide();
				}
			}]

		});
		
	//添加广告位
	function showAddpositionsize() {
		addpositionsizeForm.form.reset();	
		winAdd.show();
	}
	
	var winAdd = new Ext.Window({
				width : 460,
				height : 255,
				modal : true,
				title : "广告规格管理",
				closeAction:'hide',
				items : addpositionsizeForm 
			});

	positionsizeForm.render('positionsizeForm');

	function renderOp(value, cellmeta, record, rowIndex, columnIndex, stor) {
		var id = record.data.id;
		var name = record.data.name;
		var editStr = '<img alt=\"编辑广告规格\" src=\"/myads/HTML/images/edit.gif\" style=\"cursor: pointer;\" onclick=\"showUpdate(\''
				+ id + '\');\">';
		var delStr = '<img alt=\"删除\" src=\"/myads/HTML/images/del.gif\" style=\"cursor: pointer;\" onclick=\"deletePostemplate(\''
				+ id + '\', \'' + name + '\');\">';
		return editStr + '&nbsp;&nbsp;&nbsp;&nbsp;' + delStr;
	}

	window.deletePostemplate = function(id, name) {
		Ext.MessageBox.confirm('提示', '确定删除  ' + name + ' ？', function(btn) {
			if (btn == 'yes') {
				var param = [];
				param.push(id);
				Ext.Ajax.request({
					method : 'post',
					url : '/myads/HTML/advert/PositionsizeAction_delete.action',
					success : function(resp) {
						var obj = Ext.util.JSON.decode(resp.responseText);
						if (obj.result == 'success') {
							grid.getStore().reload();

							Ext.MessageBox.alert('提示', '删除成功！');
						}else if (obj.result == 'use') {
							grid.getStore().reload();
							Ext.MessageBox.alert('提示', '选中的模板正在使用！');
						}
					},
					params : {
						positionsizeList : param.join(',')
					}
				});
			}
		});
	};
	//修改
	window.showUpdate = function(id) {
		addpositionsizeForm.load({
					url : '/myads/HTML/advert/PositionsizeAction_detail.action',
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
	
	//判断类型
	function rendType(value){
	
		if(value==0){
			return "广告位规格";
		}else if(value==1){
			return "广告条规格";
		
		}
	}
	
	var sm = new Ext.grid.CheckboxSelectionModel();
	var cm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(), sm, {
				header : '名称',
				dataIndex : 'name',
				sortable : true
			}, {
				header : '类型',
				dataIndex : 'type',
				renderer:rendType,
				sortable : true
			}, {
				header : '状态',
				dataIndex : 'status',
				renderer:rendStauts,
				sortable : true
			}, {
				header : '宽度',
				dataIndex : 'width',
				sortable : true
			}, {
				header : '高度',
				dataIndex : 'high',
				sortable : true
			}, {
				header : '备注',
				dataIndex : 'note',
				sortable : true
			}, {
				header : '修改人',
				dataIndex : 'modifier',
				sortable : true
			}, {
				header : '修改时间',
				dataIndex : 'modifyTime',
				sortable : true
			}, {
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
				width : 100,
				align : 'left'
			}]);

	var ds = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
					url : '/myads/HTML/advert/PositionsizeAction_showAll.action'
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
							name : 'status'
						}, {
							name : 'type'
						}, {
							name : 'high'
						}, {
							name : 'width'
						}, {
							name : 'creator'
						}, {
							name : 'createTime'
						}, {
							name : 'modifier'
						}, {
							name : 'modifyTime'
						}, {
							name : 'note'
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
										url : '/myads/HTML/advert/PositionsizeAction_delete.action',
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
											positionsizeList : param.join(',')
										}
									});
								} else {
								}
							});
						}else{
							Ext.MessageBox.alert('提示', '请选择需要删除的规格！');
						}
				}
			}]
		})
	});

	grid.addListener('rowdblclick', function(grid, rowindex, e) {
		var record = grid.getStore().getAt(rowindex);
			addpositionsizeForm.load({
					url : '/myads/HTML/advert/PositionsizeAction_detail.action',
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
		       positionsizeForm, grid
		]
	});
});
