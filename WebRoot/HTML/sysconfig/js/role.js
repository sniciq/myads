Ext.onReady(function() {
	var rlId;

	var searchForm = new Ext.FormPanel({
				frame : true,
				region : 'north',
				labelAlign : 'right',
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
										text : '角色名称:'
									}), new Ext.form.TextField({
										layout : 'form',
										name : 'name'
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
											roleForm.form.reset();
											formWin.show();
										}
									})]
				}]
			});

	searchForm.render('north-div');

	var roleForm = new Ext.form.FormPanel({
		labelAlign : 'right',
		region : 'center',
		labelWidth : 60,
		frame : true,
		xtype : 'fieldset',
		items : [{
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
					items : [ {
								columnWidth : .25,
								layout : 'form',
								items : [{
											xtype : 'textfield',
											name : 'name',
											fieldLabel : '角色名称 ',
											allowBlank : false,
											blankText : '角色名称不能为空!',
											anchor : '95%'
										}]
							}, {
								columnWidth : .5,
								layout : 'form',
								items : [{
											xtype : 'textfield',
											name : 'note',
											fieldLabel : '备注 ',
											anchor : '95%'
										}]
							}]
				}],
		buttons : [{
			text : '保存',
			handler : function() {
				roleForm.form.doAction('submit', {
							url : '/myads/HTML/sysfun/RoleAction_save.action',
							method : 'post',
							params : '',
							success : function(form, action) {
								if(action.result.result== 'success')
								{
									Ext.MessageBox.alert('结果', '保存成功！');
									form.reset();
									grid.getStore().reload();
									formWin.hide();
								}else if(action.result.result == 'exist'){
									Ext.MessageBox.alert('结果', '角色名称不能相同！');
									form.reset();
									grid.getStore().reload();
									formWin.hide();
								}else
								{
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
				roleForm.form.reset();
				formWin.hide();
			}
		}]

	});

	function renderOp(value, cellmeta, record, rowIndex, columnIndex, stor) {
		var id = record.data.id;
		var name = record.data.name;
		var rlId = id;
		var editStr = '<img alt=\"编辑\" src=\"/myads/HTML/images/edit.gif\" style=\"cursor: pointer;\" onclick=\"showInfo(\''
				+ id + '\');\">';
		var delStr = '<img alt=\"删除\" src=\"/myads/HTML/images/del.gif\" style=\"cursor: pointer;\" onclick=\"deleteRole(\''
				+ id + '\', \'' + name + '\');\">';
		var updateUser = '<img alt=\"用户操作\" src=\"/myads/HTML/images/add.gif\" style=\"cursor: pointer;\" onclick=\"updateUser(\''
				+ id + '\');\">';
		return editStr + '&nbsp;&nbsp;&nbsp;&nbsp;' + delStr+ '&nbsp;&nbsp;&nbsp;&nbsp;' +updateUser;
	}
	
	//----------------------添加用户开始
	
	var userCm = new Ext.grid.ColumnModel
	([
		new Ext.grid.RowNumberer(), 
		{header : 'id',dataIndex : 'id',hidden : true},
		{header : '用户名',dataIndex : 'username', sortable : true}, 
		{header : '操作',dataIndex : 'op1',renderer : renderOp1,width : 100,align : 'left'}
	]);
	
	function renderOp1(value, cellmeta, record, rowIndex, columnIndex, stor) {
		var id = record.data.id;
		var name = record.data.username;
		var deleteUser = '<img alt=\"删除\" src=\"/myads/HTML/images/del.gif\" style=\"cursor: pointer;\" onclick=\"deleteUser(\''
				+ id + '\', \'' + name + '\');\">';
		return deleteUser;
	}
	
	window.addUser = function(id, name) {
		Ext.MessageBox.confirm('提示', '确定添加用户  ' + name + ' ？', function(btn) {
			if (btn == 'yes') {
				Ext.Ajax.request({
					method : 'post',
					url : '/myads/HTML/sysfun/RoleAction_insertUserByRole.action',
					success : function(resp) {
						var obj = Ext.util.JSON.decode(resp.responseText);
						if (obj.result == 'exist') {
							Ext.MessageBox.alert('提示', '不能添加相同用户名用户！');
							userAddGrid.getStore().reload();
							userGrid.getStore().reload();
						}
						else if (obj.result == 'success') {
							Ext.MessageBox.alert('提示', '添加用户成功！');
							userAddGrid.getStore().reload();
							userGrid.getStore().reload();
						}else Ext.MessageBox.alert('提示', '添加用户出错！');
					},
					params : {userId : id,username : name,roleId : rlId}
				});
			}
		});
	};
	
	window.deleteUser = function(id, name) {
		Ext.MessageBox.confirm('提示', '确定删除  ' + name + ' ？', function(btn) {
			if (btn == 'yes') {
				Ext.Ajax.request({
					method : 'post',
					url : '/myads/HTML/sysfun/RoleAction_deleteUserByRole.action',
					success : function(resp) {
						var obj = Ext.util.JSON.decode(resp.responseText);
						if (obj.result == 'success') {
							userGrid.getStore().reload();
							Ext.MessageBox.alert('提示', '删除成功！');
						}else Ext.MessageBox.alert('提示', '删除用户出错！');
					},
					params : {id : id}
				});
			}
		});
	};
	
	var userDS = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({url: '/myads/HTML/sysfun/RoleAction_getUserList.action'}),
		remoteSort: false,
		reader: new Ext.data.JsonReader(
			{
				totalProperty: 'total',
				idProperty:'id',
				root: 'invdata'
			},
			[
				{name: 'id'},
				{name: 'userId'},
				{name: 'username'}
			]
		)
	});
	
	var userAddStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({url: '/myads/HTML/sysfun/RoleAction_getUserAddList.action'}),
		remoteSort: false,
		reader: new Ext.data.JsonReader(
			{
				totalProperty: 'total',
				idProperty:'id',
				root: 'invdata'
			},
			[
				{name: 'id'},
				{name: 'username'}
			]
		)
	});
	
	var userGrid = new Ext.grid.GridPanel({
		el : 'userGrid',
		region: 'center',
		ds : userDS,
		cm : userCm,
		height : 300,
		viewConfig : {
			forceFit : true
		}	
	});
	
	window.updateUser =function(id){
		rlId = id;
		userShowWin.show();
		userDS.load({params: {start:0, limit:20, id : id}});
	}
	
	var userAddForm = new Ext.form.FormPanel({
		labelAlign : 'left',
		region: 'north',
		frame : true,
		buttons : [{
			text : '增加用户',
			handler : function() {
				userAddStore.load({params: {start:0, limit:20, id : rlId}});
				userAddWin.show();
			}
		}]
	})
	
	var userAddCm = new Ext.grid.ColumnModel
	([
		new Ext.grid.RowNumberer(), 
		{header : 'id',dataIndex : 'id',hidden : true},
		{header : '用户名',dataIndex : 'username', sortable : true}, 
		{header : '操作',dataIndex : 'op2',renderer : renderOp2,width : 100,align : 'left'}
	]);
	
	function renderOp2(value, cellmeta, record, rowIndex, columnIndex, stor) {
		var id = record.data.id;
		var name = record.data.username;
		var addUser = '<img alt=\"增加\" src=\"/myads/HTML/images/add.gif\" style=\"cursor: pointer;\" onclick=\"addUser(\''
				+ id + '\', \'' + name + '\');\">';
		return addUser;
	}
	
	var userAddGrid = new Ext.grid.GridPanel({
		el : 'userAddGrid',
		region: 'center',
		ds : userAddStore,
		cm : userAddCm,
		height : 300,
		viewConfig : {
			forceFit : true
		}	
	});
	
	var userAddWin = new Ext.Window({
				title : '增加用户',
				modal: true,
				applyTo : 'userAddWin',
				layout : 'fit',
				width : 500,
				height : 300,
				closeAction : 'hide',
				plain : true,
				layout : 'border',
				items : [userAddGrid]
			});
			
	userAddGrid.render();
	
	var userShowWin = new Ext.Window({
				title : '已关联用户',
				modal: true,
				applyTo : 'userShowWin',
				layout : 'fit',
				width : 700,
				height : 500,
				closeAction : 'hide',
				plain : true,
				layout : 'border',
				items : [userAddForm,userGrid]
			});
			
	userAddForm.render();
	userGrid.render();
	//---------------------添加用户结束

	window.deleteRole = function(id, name) {
		Ext.MessageBox.confirm('提示', '确定删除  ' + name + ' ？', function(btn) {
			if (btn == 'yes') {
				var param = [];
				param.push(id);
				Ext.Ajax.request({
							method : 'post',
							url : '/myads/HTML/sysfun/RoleAction_delete.action',
							success : function(resp) {
								var obj = Ext.util.JSON
										.decode(resp.responseText);
								if (obj.result == 'success') {
									grid.getStore().reload();
									Ext.MessageBox.alert('提示', '删除成功！');
								}
								if (obj.result == 'use') {
									grid.getStore().reload();
									Ext.MessageBox.alert('提示',
											'无法删除,此角色其它功能正在使用中！');
								}
							},
							params : {
								roleList : param.join(',')
							}
						});
			}
		});
	};

	window.showInfo = function(id) {
		roleForm.load({
					url : '/myads/HTML/sysfun/RoleAction_detail.action',
					params : {
						id : id
					}
				});
		formWin.show();
	};

	var formWin = new Ext.Window({
				title : '角色信息',
				applyTo : 'RoleFormWin',
				layout : 'fit',
				width : 450,
				height : 160,
				closeAction : 'hide',
				plain : true,
				layout : 'border',
				items : [roleForm]
			});

	var cm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(), {
				header : '角色名称',
				dataIndex : 'name',
				sortable : true
			}, {
				header : '操作',
				dataIndex : 'op',
				renderer : renderOp,
				width : 100,
				align : 'left'
			}]);

	var ds = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({url : '/myads/HTML/sysfun/RoleAction_getRoleInfo.action'}),
				remoteSort : false,
				reader : new Ext.data.JsonReader({
							totalProperty : 'total',
							idProperty : 'id',
							root : 'invdata'
						}, [{
									name : 'id'
								}, {
									name : 'name'
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
				roleForm.load({
							url : '/myads/HTML/sysfun/RoleAction_detail.action',
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
