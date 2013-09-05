Ext.onReady(function() {

	var dataTypeCombStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
					url : '/myads/HTML/sysfun/DepartmentAction_departmentList.action'
				}),
		reader : new Ext.data.ArrayReader({}, [{
							name : 'id'
						}, {
							name : 'value'
						}])
	});
	dataTypeCombStore.load();

	var combo = new Ext.form.ComboBox({
				fieldLabel : '上级部门',
				name : 'parentName',
				store : dataTypeCombStore,
				emptyText : '选择',
				mode : 'local',
				triggerAction : 'all',
				valueField : 'id',
				displayField : 'value',
				listeners : {
					select : function(combo, record, index) {
						var departId = record.data.id;
						departmentForm.getForm().findField("ids").setValue(departId);
					}
				}
			});

	var searchForm = new Ext.FormPanel({
		frame: true,
		region: 'north',
		labelAlign: 'right',
		items: [
			{
				columnWidth: .33,
				layout:'hbox',
				layoutConfig: {
                    align:'middle'
                },
				defaults:{margins:'0 10 0 0'},
				items: [
					new Ext.form.Label({
						text: '部门名称:'
					}),
					new Ext.form.TextField({
						layout: 'form',
						name: 'departmentName'
					}),
					new Ext.Button({
						text: '查询',
						width: 70,
						handler: function() {
							var fv = searchForm.getForm().getValues();
							ds.baseParams= fv;
							ds.load({params: {start:0, limit:20}});
						}
					}),
					new Ext.Button({
						text: '清空',
						width: 70,
						handler: function() {
							searchForm.form.reset();
							ds.baseParams= {};
							ds.load({params: {start:0, limit:20}});
						}
					}),
					new Ext.Button({
						text: '新增',
						width: 70,
						handler: function() {
							departmentForm.form.reset();
							formWin.show();
						}
					})
				]
			}
		]
	});
	searchForm.render('north-div');
			
	var departmentForm = new Ext.form.FormPanel({
		labelAlign : 'right',
		labelWidth : 60,
		region: 'center',
		frame : true,
		xtype : 'fieldset',
		items : [{
					layout : 'form',
					items : [{
								columnWidth : .01,
								layout : 'form',
								items : [{
											xtype : 'hidden',
											name : 'departmentId',
											hidden : true,
											hiddenLabel : true
										}, {
											xtype : 'hidden',
											name : 'ids',
											hidden : true,
											hiddenLabel : true
										}]
							}]
				}, {
					layout : 'form',
					items : [{
								columnWidth : .25,
								layout : 'form',
								items : [{
											xtype : 'textfield',
											name : 'departmentName',
											fieldLabel : '部门名称 ',
											allowBlank : false,
											blankText : '部门名称不能为空!',
											anchor : '95%'
										}]
							}, {
								columnWidth : .25,
								layout : 'form',
								items : [combo]
							}]
				}],
		buttons : [{
			text : '保存',
			handler : function() {
				departmentForm.form.doAction('submit', {
							url : '/myads/HTML/sysfun/DepartmentAction_save.action',
							method : 'post',
							params : '',
							success : function(form, action) {
								Ext.MessageBox.alert('结果', '保存成功！');
								form.reset();
								grid.getStore().reload();
								dataTypeCombStore.load();
								formWin.hide();
							}
						});
			}
		}, {
			text : '取消',
			handler : function() {
				departmentForm.form.reset();
				formWin.hide();
			}
		}]

	});

	function renderOp(value, cellmeta, record, rowIndex, columnIndex, stor) {
		var departmentId = record.data.departmentId;
		var departmentName = record.data.departmentName;
		var editStr = '<img alt=\"编辑\" src=\"/myads/HTML/images/edit.gif\" style=\"cursor: pointer;\" onclick=\"showInfo(\''
				+ departmentId + '\');\">';
		var delStr = '<img alt=\"删除\" src=\"/myads/HTML/images/del.gif\" style=\"cursor: pointer;\" onclick=\"deleteDepartment(\''
				+ departmentId + '\', \'' + departmentName + '\');\">';
		return editStr + '&nbsp;&nbsp;&nbsp;&nbsp;' + delStr;
	}

	window.deleteDepartment = function(departmentId, departmentName) {
		Ext.MessageBox.confirm('提示', '确定删除  ' + departmentName + ' ？', function(btn) {
			if (btn == 'yes') {
				var param = [];
				param.push(departmentId);
				Ext.Ajax.request({
							method : 'post',
							url : '/myads/HTML/sysfun/DepartmentAction_delete.action',
							success : function(resp) {
								var obj = Ext.util.JSON.decode(resp.responseText);
								if (obj.result == 'success') {
									grid.getStore().reload();
									dataTypeCombStore.load();
									Ext.MessageBox.alert('提示', '删除成功！');
								}
								if (obj.result == 'use') {
									grid.getStore().reload();
									Ext.MessageBox.alert('提示','无法删除,此部门其它功能正在使用中！');
								}
							},
							params : {
								departmentList : param.join(',')
							}
						});
			}
		});
	};

	window.showInfo = function(departmentId) {
		departmentForm.load({
					url : '/myads/HTML/sysfun/DepartmentAction_detail.action',
					params : {
						departmentId : departmentId
					}
				});
				formWin.show();
	};
	
	var formWin = new Ext.Window({
		title: '新增部门',
        applyTo:'DepartmentFormWin',
        layout:'fit',
        width:450,
    	height:160,
        closeAction:'hide',
        plain: true,
        layout: 'border',
        items: [departmentForm]
    });

	var sm = new Ext.grid.CheckboxSelectionModel();
	var cm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(), sm, {
				header : '部门名称',
				dataIndex : 'departmentName',
				sortable : true
			}, {
				header : '上级部门名称',
				dataIndex : 'parentName',
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
							url : '/myads/HTML/sysfun/DepartmentAction_showAll.action'
						}),
				remoteSort : true,
				reader : new Ext.data.JsonReader({
							totalProperty : 'total',
							idProperty : 'departmentId',
							root : 'invdata'
						}, [{
									name : 'departmentId'
								}, {
									name : 'departmentName'
								}, {
									name : 'parentName'
								}, {
									name : 'parentDepartId'
								}])
			});

	ds.load({
				params : {
					start : 0,
					limit : 50
				}
			});

	var grid = new Ext.grid.GridPanel({
		el : 'grid',
		region: 'center',
		ds : ds,
		cm : cm,
		sm : sm,
		viewConfig : {
			forceFit : true
		},
		bbar : new Ext.PagingToolbar({
			pageSize : 50,
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
								param.push(this.get("departmentId"));
							});

					Ext.MessageBox.confirm("提示", "确定删除？",
							function(departmentId) {
								if (departmentId == 'yes') {
									Ext.Ajax.request({
										method : 'post',
										url : '/myads/HTML/sysfun/DepartmentAction_delete.action',
										success : function(resp) {
											var obj = Ext.util.JSON.decode(resp.responseText);
											if (obj.result == 'success') {
												grid.getStore().reload();
												Ext.MessageBox.alert('提示',
														'删除成功！');
											}
											if (obj.result == 'use') {
												grid.getStore().reload();
												Ext.MessageBox.alert('提示','无法删除,此部门其它功能正在使用中！');
											}
										},
										params : {
											departmentList : param.join(',')
										}
									});
								} else {
								}
							});
				}
			}]
		})
	});

	grid.addListener('rowdblclick', function(grid, rowindex, e) {
				var record = grid.getStore().getAt(rowindex);
				departmentForm.load({
							url : '/myads/HTML/sysfun/DepartmentAction_detail.action',
							params : {
								departmentId : record.data.departmentId
							}
						});
						formWin.show();
			});

	grid.render();
	
	new Ext.Viewport({
			layout : 'border',
			items : [searchForm, grid]
		});
});
