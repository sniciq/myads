Ext.onReady(function() {

	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget='side';
	
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
						text: '组名称:'
					}),
					new Ext.form.TextField({
						layout: 'form',
						name: 'name'
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
						text: '新增',
						width: 70,
						handler: function() {
							groupForm.form.reset();
							formWin.show();
						}
					})
				]
			}
		]
	});
	searchForm.render('north-div');
			
	var groupForm = new Ext.form.FormPanel({
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
											name : 'id',
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
											name : 'name',
											fieldLabel : '组名称 ',
											allowBlank : false,
											blankText : '组名称不能为空!',
											anchor : '95%'
										}]
							}, {
								columnWidth : .5,
								layout : 'form',
								items : [{
											xtype : 'textfield',
											name : 'remark',
											fieldLabel : '备注 ',
											anchor : '95%'
										}]
							}]
				}],
		buttons : [{
			text : '保存',
			handler : function() {
				groupForm.form.doAction('submit', {
					url : '/myads/HTML/sysfun/GroupAction_save.action',
					method : 'post',
					params : '',
					success : function(form, action) {
						Ext.MessageBox.alert('结果', '保存成功！');
						form.reset();
						grid.getStore().reload();
						formWin.hide();
					}
				});
			}
		}, {
			text : '取消',
			handler : function() {
				groupForm.form.reset();
				formWin.hide();
			}
		}]

	});

	function renderAdminstatus(value) {
		if(value==3){
			return "<font color='red'>管理员</font>";
		} else {
			return "<font color='green'>普通</font>";
		}
	}
	
	function renderOp(value, cellmeta, record, rowIndex, columnIndex, stor) {
		var id = record.data.id;
		var name = record.data.name;
		var status = record.data.status;
		var flag = record.data.flag;
		var editStr = '<img alt=\"编辑\" src=\"/myads/HTML/images/edit.gif\" style=\"cursor: pointer;\" onclick=\"showInfo(\''
				+ id + '\');\">';
		var delStr = '<img alt=\"删除\" src=\"/myads/HTML/images/del.gif\" style=\"cursor: pointer;\" onclick=\"deleteGroup(\''
				+ id + '\', \'' + name + '\');\">';
		var updateUser = '<img alt=\"用户操作\" src=\"/myads/HTML/images/add.gif\" style=\"cursor: pointer;\" onclick=\"updateUser(\''
				+ id + '\');\">';
		var mailGroup = '<img alt=\"MAIL\" src=\"/myads/HTML/images/post.gif\" style=\"cursor: pointer;\" onclick=\"mailGroup(\''
			+ id + '\');\">';
		var cancelMailGroup = '<img alt=\"MAIL\" src=\"/myads/HTML/images/refresh.gif\" style=\"cursor: pointer;\" onclick=\"cancelMailGroup(\''
			+ id + '\');\">';
		var superGroup = '<img alt=\"SUPERADMIN\" src=\"/myads/HTML/images/group.png\" style=\"cursor: pointer;\" onclick=\"superGroup(\''
			+ id + '\');\">';
		var cancelSuperGroup = '<img alt=\"SUPERADMIN\" src=\"/myads/HTML/images/refresh.gif\" style=\"cursor: pointer;\" onclick=\"cancelSuperGroup(\''
			+ id + '\');\">';
		
		var menu = editStr + '&nbsp;&nbsp;&nbsp;&nbsp;' + delStr + '&nbsp;&nbsp;&nbsp;&nbsp;' + updateUser + '&nbsp;&nbsp;&nbsp;&nbsp;';
		if (status == 3 && flag == 1) {
			return menu + cancelSuperGroup + '&nbsp;&nbsp;&nbsp;&nbsp;' + cancelMailGroup;
		} else if (status == 3 && flag == 0) {
			return menu + superGroup + '&nbsp;&nbsp;&nbsp;&nbsp;' + cancelMailGroup;
		} else if (status != 3 && flag == 1) {
			return menu + cancelSuperGroup + '&nbsp;&nbsp;&nbsp;&nbsp;' + mailGroup;
		} else if (status != 3 && flag == 0) {
			return menu + superGroup + '&nbsp;&nbsp;&nbsp;&nbsp;' + mailGroup;
		} else {
			return "";
		}
	}

	//----------------------添加用户开始
	var userCm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(), 
		{header : 'id',dataIndex : 'id',hidden : true},
		{header : '用户名',dataIndex : 'username', sortable : true},
		{header : '姓名',dataIndex : 'name', sortable : true},
		{header : '角色',dataIndex : 'status', sortable : true, renderer : renderAdminstatus}, 
		{header : '操作',dataIndex : 'op1',renderer : renderOp1,width : 100,align : 'left'}
	]);
	
	function renderOp1(value, cellmeta, record, rowIndex, columnIndex, stor) {
		var userGroupId = record.data.userGroupId;
		var id = record.data.id;
		var name = record.data.username;
		var status = record.data.status;
		var deleteUser = '<img alt=\"删除\" src=\"/myads/HTML/images/del.gif\" style=\"cursor: pointer;\" onclick=\"deleteUser(\''
				+ userGroupId + '\', \'' + name + '\');\">';
		var specifiedAdmin = '<img alt=\"管理员\" src=\"/myads/HTML/images/key.png\" style=\"cursor: pointer;\" onclick=\"specifiedAdmin(\''
			+ userGroupId + '\', \'' + name + '\');\">';
		var cancelSpecifiedAdmin = '<img alt=\"管理员\" src=\"/myads/HTML/images/delete.png\" style=\"cursor: pointer;\" onclick=\"cancelSpecifiedAdmin(\''
			+ userGroupId + '\', \'' + name + '\');\">';
		if (status == 3) {
			return deleteUser + '&nbsp;&nbsp;&nbsp;&nbsp;' + cancelSpecifiedAdmin;
		} else {
			return deleteUser + '&nbsp;&nbsp;&nbsp;&nbsp;' + specifiedAdmin;
		}
		
	}
	
	window.addUser = function(id, name) {
		Ext.MessageBox.confirm('提示', '确定添加用户  ' + name + ' ？', function(btn) {
			if (btn == 'yes') {
				Ext.Ajax.request({
					method : 'post',
					url : '/myads/HTML/sysfun/GroupAction_insertUserGroup.action',
					success : function(resp) {
						var obj = Ext.util.JSON.decode(resp.responseText);
						if (obj.result == 'success') {
							Ext.MessageBox.alert('提示', '添加用户成功！');
							userAddGrid.getStore().reload();
							userGrid.getStore().reload();
						} else Ext.MessageBox.alert('提示', '添加用户出错！');
					},
					params : {userId : id, groupId : rlId}
				});
			}
		});
	};
	
	window.deleteUser = function(userGroupId, name) {
		Ext.MessageBox.confirm('提示', '确定删除  ' + name + ' ？', function(btn) {
			if (btn == 'yes') {
				Ext.Ajax.request({
					method : 'post',
					url : '/myads/HTML/sysfun/GroupAction_deleteUserGroupById.action',
					success : function(resp) {
						var obj = Ext.util.JSON.decode(resp.responseText);
						if (obj.result == 'success') {
							userGrid.getStore().reload();
							Ext.MessageBox.alert('提示', '删除成功！');
						}else Ext.MessageBox.alert('提示', '删除用户出错！');
					},
					params : {id : userGroupId}
				});
			}
		});
	};
	
	window.specifiedAdmin = function(id, name) {
		Ext.MessageBox.confirm('提示', '确定指定  ' + name + ' 为该组管理员吗？', function(btn) {
			if (btn == 'yes') {
				Ext.Ajax.request({
					method : 'post',
					url : '/myads/HTML/sysfun/GroupAction_specifiedAdmin.action',
					success : function(resp) {
						var obj = Ext.util.JSON.decode(resp.responseText);
						if (obj.result == 'success') {
							userGrid.getStore().reload();
							Ext.MessageBox.alert('提示', '操作成功!');
						} else if (obj.result == 'exist') {
							Ext.MessageBox.alert('提示', '操作失败,成员组内只能存在一个管理员角色!');
						} else {
							Ext.MessageBox.alert('提示', '操作失败!');
						}
					},
					params : {id : id, groupId : rlId}
				});
			}
		});
	};
	
	window.cancelSpecifiedAdmin = function(id, name) {
		Ext.MessageBox.confirm('提示', '确定取消  ' + name + ' 的管理员授权吗？', function(btn) {
			if (btn == 'yes') {
				Ext.Ajax.request({
					method : 'post',
					url : '/myads/HTML/sysfun/GroupAction_cancelSpecifiedAdmin.action',
					success : function(resp) {
						var obj = Ext.util.JSON.decode(resp.responseText);
						if (obj.result == 'success') {
							userGrid.getStore().reload();
							Ext.MessageBox.alert('提示', '取消授权成功!');
						} else {
							Ext.MessageBox.alert('提示', '取消授权失败!');
						}
					},
					params : {id : id}
				});
			}
		});
	};
	
	window.mailGroup = function(id) {
		Ext.MessageBox.confirm('提示', '确定要设定该组为邮件群发组吗？', function(btn) {
			if (btn == 'yes') {
				Ext.Ajax.request({
					method : 'post',
					url : '/myads/HTML/sysfun/GroupAction_save.action',
					success : function(resp) {
						var obj = Ext.util.JSON.decode(resp.responseText);
						if (obj.result == 'success') {
							grid.getStore().reload();
							Ext.MessageBox.alert('提示', '设置邮件群发组成功!');
						} else if (obj.result == 'exist') {
							Ext.MessageBox.alert('提示', '设置邮件群发组失败,只能存在一个邮件群发组!');
						} else {
							Ext.MessageBox.alert('提示', '操作失败!');
						}
					},
					params : {id : id, status : 3}
				});
			}
		});
	};
	
	window.cancelMailGroup = function(id) {
		Ext.MessageBox.confirm('提示', '确定要取消邮件群发组状态吗？', function(btn) {
			if (btn == 'yes') {
				Ext.Ajax.request({
					method : 'post',
					url : '/myads/HTML/sysfun/GroupAction_save.action',
					success : function(resp) {
						var obj = Ext.util.JSON.decode(resp.responseText);
						if (obj.result == 'success') {
							grid.getStore().reload();
							Ext.MessageBox.alert('提示', '变更邮件群发组状态成功!');
						} else if (obj.result == 'exist') {
							Ext.MessageBox.alert('提示', '变更邮件群发组失败!');
						} else {
							Ext.MessageBox.alert('提示', '操作失败!');
						}
					},
					params : {id : id, status : 0}
				});
			}
		});
	};
	
	window.superGroup = function(id) {
		Ext.MessageBox.confirm('提示', '确定要设定该组为超级管理员组吗？ 该组可以查看全部人员数据, 请慎重!!', function(btn) {
			if (btn == 'yes') {
				Ext.Ajax.request({
					method : 'post',
					url : '/myads/HTML/sysfun/GroupAction_save.action',
					success : function(resp) {
						var obj = Ext.util.JSON.decode(resp.responseText);
						if (obj.result == 'success') {
							grid.getStore().reload();
							Ext.MessageBox.alert('提示', '设置成功!');
						} else if (obj.result == 'exist') {
							Ext.MessageBox.alert('提示', '设置失败!');
						} else {
							Ext.MessageBox.alert('提示', '操作失败!');
						}
					},
					params : {id : id, flag : 1}
				});
			}
		});
	};
	
	window.cancelSuperGroup = function(id) {
		Ext.MessageBox.confirm('提示', '确定要取消超级管理员组状态吗？', function(btn) {
			if (btn == 'yes') {
				Ext.Ajax.request({
					method : 'post',
					url : '/myads/HTML/sysfun/GroupAction_save.action',
					success : function(resp) {
						var obj = Ext.util.JSON.decode(resp.responseText);
						if (obj.result == 'success') {
							grid.getStore().reload();
							Ext.MessageBox.alert('提示', '变更组状态成功!');
						} else if (obj.result == 'exist') {
							Ext.MessageBox.alert('提示', '变更组状态失败!');
						} else {
							Ext.MessageBox.alert('提示', '操作失败!');
						}
					},
					params : {id : id, flag : 0}
				});
			}
		});
	};
	
	var userDS = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({url: '/myads/HTML/sysfun/GroupAction_getUserList.action'}),
		remoteSort: false,
		reader: new Ext.data.JsonReader(
			{
				totalProperty: 'total',
				idProperty:'id',
				root: 'invdata'
			},
			[
			 	{name: 'userGroupId'},
				{name: 'id'},
				{name: 'username'},
				{name: 'name'},
				{name: 'status'}
			]
		)
	});
	
	var userAddStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({url: '/myads/HTML/sysfun/GroupAction_getAddUserList.action'}),
		remoteSort: false,
		reader: new Ext.data.JsonReader(
			{
				totalProperty: 'total',
				idProperty:'id',
				root: 'invdata'
			},
			[
				{name: 'id'},
				{name: 'username'},
				{name: 'name'}
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
	};
	
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
	});
	
	var userAddCm = new Ext.grid.ColumnModel([
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
	
	window.deleteGroup = function(id, name) {
		Ext.MessageBox.confirm('提示', '确定删除  ' + name + ' ？', function(btn) {
			if (btn == 'yes') {
				var param = [];
				param.push(id);
				Ext.Ajax.request({
					method : 'post',
					url : '/myads/HTML/sysfun/GroupAction_delete.action',
					success : function(resp) {
						var obj = Ext.util.JSON.decode(resp.responseText);
						if (obj.result == 'success') {
							Ext.MessageBox.alert('提示', '删除成功!');
						} else if (obj.result == 'use') {
							Ext.MessageBox.alert('提示', '删除失败,用户组使用中,请先删除组内成员后在进行操作!');
						} else if (obj.result == 'error') {
							Ext.MessageBox.alert('提示', '删除失败!');
						} else {
						}
						grid.getStore().reload();
					},
					params : {
						groupList : param.join(',')
					}
				});
			}
		});
	};

	window.showInfo = function(id) {
		groupForm.load({
			url : '/myads/HTML/sysfun/GroupAction_getGroupDetail.action',
			params : {id : id}
		});
		formWin.show();
	};
	
	var formWin = new Ext.Window({
			title: '新增用户组',
	        applyTo:'groupFormWin',
	        layout:'fit',
	        width:430,
	    	height:150,
	        closeAction:'hide',
	        plain: true,
	        layout: 'border',
	        items: [groupForm]
	    });

	function renderMailStatus(value) {
		if(value==3){
			return "<font color='red'>邮件通知组</font>";
		} else {
			return "<font color='green'>普通</font>";
		}
	}
	
	var cm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(), {header : '组名称',dataIndex : 'name',sortable : true},
	                                   {header : '状态', dataIndex : 'status', sortable : true, renderer : renderMailStatus},
	                                   {header : '操作',dataIndex : 'op',renderer : renderOp,width : 100,align : 'left'}]);

	var ds = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url : '/myads/HTML/sysfun/GroupAction_showAll.action'
		}),
		remoteSort : true,
		reader : new Ext.data.JsonReader({
			totalProperty : 'total',
			idProperty : 'id',
			root : 'invdata'
		}, [{name : 'id'}, 
		    {name : 'name'},
		    {name: 'status'},
		    {name: 'flag'},
		    {name : 'createTime'}])
	});
	ds.load({params : {start : 0, limit : 50}});

	var grid = new Ext.grid.GridPanel({
		el : 'grid',
		region: 'center',
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
	grid.render();
	
	new Ext.Viewport({
		layout : 'border',
		items : [searchForm, grid]
	});
});
