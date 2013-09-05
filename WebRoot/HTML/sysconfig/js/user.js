Ext.onReady(function() {
	
	var searchForm = new Ext.form.FormPanel({
		labelAlign : 'right',
		region: 'north',
		labelWidth : 70,
		frame : true,
		xtype : 'fieldset',
		items : [{
					columnWidth : .33,
					layout : 'hbox',
					layoutConfig : {
						align : 'middle'
					},
					defaults : {
						margins : '0 10 0 0'
					},
					items : [new Ext.form.Label({
										text : '用戶名:'
									}), new Ext.form.TextField({
										layout : 'form',
										name : 'username'
									}), new Ext.Button({
										text : '查询',
										width : 70,
										handler : function() {
											var fv = searchForm.getForm().getValues();
											ds.baseParams = fv;
											ds.load({
														params : {
															start : 0,
															limit : 20
														}
													});
										}
									}), new Ext.Button({
										text : '重置',
										width : 70,
										handler : function() {
											searchForm.form.reset();
											ds.baseParams = {};
											ds.load({
														params : {
															start : 0,
															limit : 20
														}
													});
										}
									}), new Ext.Button({
										text : '新增',
										width : 70,
										handler : function() {
											userForm.form.reset();
											formWin.show();
										}
									})]
				}]
	});
	
	searchForm.render("searchForm");

	var userForm = new Ext.form.FormPanel({
		labelAlign : 'right',
		labelWidth : 60,
		region : 'center',
		frame : true,
		xtype : 'fieldset',
		items : [{
					layout : 'form',
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
					layout : 'form',
					items : [{
								columnWidth : .20,
								layout : 'form',
								items : [{
											xtype : 'textfield',
											name : 'username',
											vtype : 'email',
											fieldLabel : '用户名 ',
											allowBlank : false,
											blankText : '用户名不能为空!',
											anchor : '95%'
										}]
							}, {
								columnWidth : .20,
								layout : 'form',
								items : [{
											xtype : 'textfield',
											name : 'name',
											fieldLabel : '员工名称 ',
											allowBlank : false,
											blankText : '姓名不能为空!',
											anchor : '95%'
										}]
							}, {
								columnWidth : .20,
								layout : 'form',
								items : [new Ext.form.TextField({
									fieldLabel: 'Email',
			                        vtype: 'email' ,
			                        allowBlank : false,
			                        blankText : '邮箱地址不能为空!',
									anchor : '95%',
									name : 'mail'
								})]
							},{
								columnWidth : .20,
								layout : 'form',
								items : [{
											xtype : 'textfield',
											name : 'mobile',
											fieldLabel : '手机 ',
											blankText : '手机号不能为空!',
											anchor : '95%'
										}]
							},{
								columnWidth : .20,
								layout : 'form',
								items : [{
											xtype : 'textfield',
											name : 'password',
											fieldLabel : '密码 ',
											allowBlank : false,
											blankText : '密码不能为空!',
											anchor : '95%'
										}]
							}]
				}],
		buttons : [{
			text : '保存',
			handler : function() {
				userForm.form.doAction('submit', {
							url : '/myads/HTML/sysfun/UserAction_save.action',
							method : 'post',
							params : '',
							success : function(form, action) {
								if(action.result.result == 'success') {
									Ext.MessageBox.alert('结果', '保存成功！');
									form.reset();
									grid.getStore().reload();
									formWin.hide();
								}else{
									Ext.MessageBox.alert('结果', '保存失败！');
									form.reset();
									grid.getStore().reload();
									formWin.hide();
								}
							}
						});
			}
		}, {
			text : '取消',
			handler : function() {
				userForm.form.reset();
				formWin.hide();
			}
		}]

	});

	function renderOp(value, cellmeta, record, rowIndex, columnIndex, stor) {
		var id = record.data.id;
		var name = record.data.name;
		var editStr = '<img alt=\"编辑\" src=\"/myads/HTML/images/edit.gif\" style=\"cursor: pointer;\" onclick=\"showInfo(\''
				+ id + '\');\">';
		var delStr = '<img alt=\"删除\" src=\"/myads/HTML/images/del.gif\" style=\"cursor: pointer;\" onclick=\"deleteuser(\''
				+ id + '\', \'' + name + '\');\">';
		return editStr + '&nbsp;&nbsp;&nbsp;&nbsp;' + delStr;
	}

	window.deleteuser = function(id, username) {
		Ext.MessageBox.confirm('提示', '确定删除  ' + username + ' ？', function(btn) {
			if (btn == 'yes') {
				var param = [];
				param.push(id);
				Ext.Ajax.request({
							method : 'post',
							url : '/myads/HTML/sysfun/UserAction_delete.action',
							success : function(resp) {
								var obj = Ext.util.JSON
										.decode(resp.responseText);
								if (obj.result == 'success') {
									grid.getStore().reload();
									Ext.MessageBox.alert('提示', '删除成功！');
								}else{
									grid.getStore().reload();
									Ext.MessageBox.alert('提示', '删除失败！');
								}
							},
							params : {
								userList : param.join(',')
							}
						});
			}
		});
	}

	window.showInfo = function(id) {
		userForm.load({
					url : '/myads/HTML/sysfun/UserAction_detail.action',
					params : {
						id : id
					}
				});
		formWin.show();
	}

	var formWin = new Ext.Window({
				title : '用户信息',
				applyTo : 'UserFormWin',
				layout : 'fit',
				width : 400,
				height : 230,
				closeAction : 'hide',
				plain : true,
				layout : 'border',
				items : [userForm]
			});

	var cm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(), {
				header : '用户名',
				dataIndex : 'username',
				sortable : true
			}, {
				header : '员工姓名',
				dataIndex : 'name',
				sortable : true
			}, {
				header : '邮件',
				dataIndex : 'mail',
				sortable : true
			}, {
				header : '手机',
				dataIndex : 'mobile',
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
							url : '/myads/HTML/sysfun/UserAction_getUserInfo.action'
						}),
				remoteSort : true,
				reader : new Ext.data.JsonReader({
							totalProperty : 'total',
							idProperty : 'id',
							root : 'invdata'
						}, [{
									name : 'id'
								}, {
									name : 'username'
								}, {
									name : 'name'
								}, {
									name : 'mail'
								}, {
									name : 'mobile'
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
				userForm.load({
							url : '/myads/HTML/sysfun/UserAction_detail.action',
							params : {
								id : record.data.id
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
