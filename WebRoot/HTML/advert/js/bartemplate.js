Ext.onReady(function() {
	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget='side';
	var bartemplateForm = new Ext.form.FormPanel({
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
											value:0,
											store :(new Ext.data.SimpleStore({
												fields : ['value', 'text'],
												data: [['0', '启用'],['2', '停用'],['', '全部']]
											}))
										})]
							}]
				}],
		buttons : [{
			text : '查询',
			handler : function() {
				
				var fv = bartemplateForm.getForm().getValues();
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
				bartemplateForm.form.reset();
			}
		}, {
			text : '新增广告条模板',
			handler : function() {
				showAddbartemplate();
			}
		}]

	});
	

			
	var addbartemplateForm = new Ext.form.FormPanel({
			
		
			labelAlign : 'right',
			labelWidth : 120,
			height : 350,
			frame : true,
			xtype : 'fieldset',
			items : [
					{
						
						xtype : 'textfield',
						name : 'name',
						allowBlank : false,
						blankText : "此项不能为空哦O(∩_∩)O~",
						fieldLabel : '名称',
						maxLength:40,
						maxLengthText:'输入文本过长',
						anchor : '95%'
					}, {
						xtype : 'hidden',
						name : 'id',
						fieldLabel : 'id'			
					},{
						xtype : 'textfield',
						name : 'nameEn',
						allowBlank : false,
						blankText : "只能输入英文和字符!",
						regex : /^[\w-\s]+$/,
						regexText : "只能输入英文和字符!",
						fieldLabel : '英文名称',
						maxLength:80,
						maxLengthText:'输入文本过长',
						anchor : '95%'
					},{
						xtype : 'textfield',
						name : 'textNum',
						regex : /^\d/,
						regexText : "只能输入数字!",
						fieldLabel : '文字链物料个数',
						maxLength:2,
						maxLengthText:'输入文本过长',
						anchor : '95%'
					},{
						xtype : 'textfield',
						name : 'imgNum',
						regex : /^\d/,
						regexText : "只能输入数字!",
						fieldLabel : '图片/Flash物料个数',
						maxLength:2,
						maxLengthText:'输入文本过长',
						anchor : '95%'
					},{
						xtype : 'textfield',
						name : 'videoNum',
						regex : /^\d/,
						regexText : "只能输入数字!",
						fieldLabel : '视频类型物料个数',
						maxLength:3,
						maxLengthText:'输入文本过长',
						anchor : '95%'
					},{
						xtype : 'textfield',
						name : 'materialSum',
						blankText : "此项不能为空哦!",
						regex : /^\d/,
						regexText : "只能输入数字!",
						fieldLabel : '不限类型物料个数',
						maxLength:3,
						maxLengthText:'输入文本过长',
						anchor : '95%'
					},{
					layout : 'form',
					items : [new Ext.form.ComboBox({
								allowBlank : false,
								layout : 'form',
								fieldLabel : '状态',
								
								editable : false,
								mode : 'local',
								hiddenName : 'status',
								triggerAction : 'all',
								displayField : 'text',
								valueField : 'value',
								anchor : '95%',
								store : (new Ext.data.SimpleStore({
											fields : ['value', 'text'],
											data : [['0', '启用'], 
													['2', '停用']]
										}))
							})
//							,new Ext.form.ComboBox({
//								layout : 'form',
//								fieldLabel : '类型',
//							
//								editable : false,
//								mode : 'local',
//								hiddenName : 'type',
//								triggerAction : 'all',
//								displayField : 'text',
//								valueField : 'value',
//								anchor : '95%',
//								store : (new Ext.data.SimpleStore({
//											fields : ['value', 'text'],
//											data : [['', '默认模板'],['1', '图片Flash模板'],['2', '前贴片模板'],['3', '暂停模板'],['3', '角标模板'],['4', '后贴片模板']																]
//										}))
//							})
							]
				}, {
					xtype : 'textarea',
					name : 'code',
					fieldLabel : 'CODE',
					anchor : '95%',
					height:80
					}],
			buttons : [{
				text : '保存',
				handler : function() {
					addbartemplateForm.form.doAction('submit', {
						url : '/myads/HTML/advert/BartemplateAction_save.action',
						method : 'post',
						params : '',
						success : function(form, action) {
							if(action.result.result == 'success') {
								Ext.MessageBox.alert('结果', '保存成功！');
								form.reset();
								grid.getStore().reload();
								winAdd.hide();
							}else if(action.result.result == 'useTemplate') {
								Ext.MessageBox.alert('结果', '保存失败，模板被广告位模板使用中！');
								form.reset();
								grid.getStore().reload();
								winAdd.hide();
							}else if(action.result.result == 'use') {
								Ext.MessageBox.alert('结果', '保存失败，模板正在被广告条使用中！');
								form.reset();
								grid.getStore().reload();
								winAdd.hide();
							}
							else {
								Ext.MessageBox.alert('错误', '保存失败！' );
								
							}
							
						}
					});
				}
			}, {
				text : '取消',
				handler : function() {
					addbartemplateForm.form.reset();
					winAdd.hide();
				}
			}]

		});
		
	//添加客户
	function showAddbartemplate() {
		addbartemplateForm.form.reset();	
		winAdd.show();
	}
	
	var winAdd = new Ext.Window({
				width : 500,
				height : 395,
				modal : true,
				title : "广告条模板管理",
				closeAction:'hide',
				items : addbartemplateForm
			});

	bartemplateForm.render('bartemplateForm');

	function renderOp(value, cellmeta, record, rowIndex, columnIndex, stor) {
		var id = record.data.id;
		var name = record.data.name;
		var editStr = '<img alt=\"编辑\" src=\"/myads/HTML/images/edit.gif\" style=\"cursor: pointer;\" onclick=\"showUpdate(\''
				+ id + '\');\">';
		var delStr = '<img alt=\"删除\" src=\"/myads/HTML/images/del.gif\" style=\"cursor: pointer;\" onclick=\"deleteBartemplate(\''
				+ id + '\', \'' + name + '\');\">';
		return editStr + '&nbsp;&nbsp;&nbsp;&nbsp;' + delStr;
	}

	window.deleteBartemplate = function(id, name) {
		Ext.MessageBox.confirm('提示', '确定删除  ' + name + ' ？', function(btn) {
			if (btn == 'yes') {
				var param = [];
				param.push(id);
				Ext.Ajax.request({
					method : 'post',
					url : '/myads/HTML/advert/BartemplateAction_delete.action',
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
						bartemplateList : param.join(',')
					}
				});
			}
		});
	};
	//修改
	window.showUpdate = function(id) {
		addbartemplateForm.load({
					url : '/myads/HTML/advert/BartemplateAction_detail.action',
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
			return "<span style='color:GRAY'>删除</span>";
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
					url : '/myads/HTML/advert/BartemplateAction_showAll.action'
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
							name : 'creator'
						}, {
							name : 'createTime'
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
										url : '/myads/HTML/advert/BartemplateAction_delete.action',
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
											bartemplateList : param.join(',')
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
			addbartemplateForm.load({
					url : '/myads/HTML/advert/BartemplateAction_detail.action',
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
		       bartemplateForm, grid
		]
	});
});
