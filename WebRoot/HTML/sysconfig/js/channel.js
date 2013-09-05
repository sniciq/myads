Ext.onReady(function() {

	var dataTypeCombStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
					url : '/myads/HTML/sysfun/ChannelAction_channelList.action'
				}),
		reader : new Ext.data.ArrayReader({}, [{
							name : 'id'
						}, {
							name : 'value'
						}])
	});
	dataTypeCombStore.load();
	
	var siteCombStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
					url : '/myads/HTML/sysfun/SiteAction_getSiteList.action'
				}),
		reader : new Ext.data.ArrayReader({}, [{
							name : 'id'
						}, {
							name : 'value'
						}])
	});
	siteCombStore.load();	
	
	// 网站
	var siteComboStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({url: '/myads/HTML/advert/AdvpositionAction_getSiteList.action'}),
		reader: new Ext.data.ArrayReader({}, [
			{name: 'value'},{name: 'text'}
		])
	});
	siteComboStore.load();
	
	var combo = new Ext.form.ComboBox({
				fieldLabel : '所属频道',
				store : dataTypeCombStore,
				mode : 'local',
				hiddenName : 'parentName',
				triggerAction : 'all',
				displayField : 'value',
				valueField : 'id',
				listeners : {
					select : function(combo, record, index) {
						var channelIds = record.data.id;
						channelForm.getForm().findField("ids")
								.setValue(channelIds);
					}
				}
			});
	
	var siteCombo = new Ext.form.ComboBox({
		fieldLabel : '所属网站',
		editable : false,
		store : siteCombStore,
		mode : 'local',
		allowBlank : false,
		hiddenName : 'siteName',
		triggerAction : 'all',
		displayField : 'value',
		valueField : 'id',
		listeners : {
		select : function(siteCombo, record, index) {
			var siteId = record.data.id;
			channelForm.getForm().findField("siteId")
			.setValue(siteId);
		  }
	   }
	});
	
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
					items : [
								{
							    	layout: 'column',
									items: [
								        {	columnWidth: .5, layout: 'form',
							            	items: [
							            	        { xtype: 'textfield', name: 'name', fieldLabel: '频道名称',anchor : '95%'}
							            	]
							            },
								        
							            {	columnWidth: .5, layout: 'form',
							            	items: [
							            		new Ext.form.ComboBox({
							            	       		fieldLabel: '网站名称',
							            	       		hiddenName: 'siteId',
														mode: 'local',
														editable : false,
														triggerAction: 'all', 
														valueField: 'value',
														displayField: 'text',
														anchor : '95%',
														store: siteComboStore
																})
							            
							            	]
							            }
							]
				}]}],
				buttons:[new Ext.Button({
					text : '查询',
					width : 70,
					handler : function() {
						var fv = searchForm.getForm()
								.getValues();
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
						channelForm.form.reset();
						formWin.show();
					}
				})]
			});

	searchForm.render('north-div');

	var channelForm = new Ext.form.FormPanel({
		labelAlign : 'right',
		labelWidth : 60,
		region : 'center',
		frame : true,
		xtype : 'fieldset',
		items : [{
					layout : 'column',
					items : [{
								layout : 'form',
								items : [{
											xtype : 'hidden',
											name : 'siteId',
											hidden : true,
											hiddenLabel : true
										},{
											xtype : 'hidden',
											name : 'channelId',
											hidden : true,
											hiddenLabel : true
										},{
											xtype : 'hidden',
											name : 'ids',
											hidden : true,
											hiddenLabel : true
										}]
							}]
				}
				,{
					layout : 'form',
					items : [{
								columnWidth : .25,
								layout : 'form',
								items : [{
											xtype : 'textfield',
											name : 'name',
											fieldLabel : '频道名称',
											allowBlank : false,
											blankText : '频道名称不能为空!'
										}]
							}, 
//								{
//								columnWidth : .25,
//								layout : 'form',
//								items : [combo]
//							},
								{
								columnWidth : .25,
								layout : 'form',
								items : [siteCombo]
							},{
								columnWidth : .25,
								layout : 'form',
								items : [
								{
									xtype : 'textfield',
									vtype : 'alphanum',
									name : 'sourceId',
									fieldLabel : '来源id',
									anchor : '50%',
									maxLength : 50,
									allowBlank : false,
									blankText : "不能为空!",
									vtypeText: "只能输入字母和数字"
								}]
							}]
				}],
		buttons : [{
			text : '保存',
			handler : function() {
				var channelId = channelForm.getForm().findField("channelId").getValue();
				var siteId = channelForm.getForm().findField("siteId").getValue();
				var name = channelForm.getForm().findField("name").getValue();
				Ext.Ajax.request({
					url : '/myads/HTML/sysconfig/ChannelAction_vldChannelNameRepeated.action',
					method : 'post',
					params : {
						channelId : channelId,
						siteId : siteId,
						name : name
					},
					success : function(response, options) {
						var obj = Ext.util.JSON.decode(response.responseText);
						if (obj.result == 'use') {
							Ext.MessageBox.alert('提示', '该网站下已经存在相同名称的频道！');
						} else if (obj.result == 'success') {
							channelForm.form.doAction('submit', {
								url : '/myads/HTML/sysfun/ChannelAction_save.action',
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
						} else {
							Ext.MessageBox.alert('提示', '新建频道操作异常,请重试！');
						}
					}
				});
			}
		}, {
			text : '取消',
			handler : function() {
				channelForm.form.reset();
				formWin.hide();
			}
		}]

	});

	function renderOp(value, cellmeta, record, rowIndex, columnIndex, stor) {
		var channelId = record.data.channelId;
		var name = record.data.name;
		var editStr = '<img alt=\"编辑\" src=\"/myads/HTML/images/edit.gif\" style=\"cursor: pointer;\" onclick=\"showInfo(\''
				+ channelId + '\');\">';
		var delStr = '<img alt=\"删除\" src=\"/myads/HTML/images/del.gif\" style=\"cursor: pointer;\" onclick=\"deleteChannel(\''
				+ channelId + '\', \'' + name + '\');\">';
		return editStr + '&nbsp;&nbsp;&nbsp;&nbsp;' + delStr;
	}

	window.deleteChannel = function(channelId, name) {
		Ext.MessageBox.confirm('提示', '确定删除  ' + name + ' ？', function(btn) {
			if (btn == 'yes') {
				var param = [];
				param.push(channelId);
				Ext.Ajax.request({
							method : 'post',
							url : '/myads/HTML/sysfun/ChannelAction_delete.action',
							success : function(resp) {
								var obj = Ext.util.JSON
										.decode(resp.responseText);
								if (obj.delMessage != '') {
										grid.getStore().reload();
										Ext.MessageBox.alert('提示',obj.delMessage+'的频道有其他功能正在使用中，无法删除！');
								}
								if(obj.result == 'success'){
										grid.getStore().reload();
										Ext.MessageBox.alert('提示', '删除成功！');
									}
								if (obj.result == 'use') {
									grid.getStore().reload();
									Ext.MessageBox.alert('提示','无法删除,此频道其它功能正在使用中！');
								}
								if(obj.result == 'error'){
									grid.getStore().reload();
									Ext.MessageBox.alert('提示','操作失败，请联系系统管理员！');
								}
							},
							params : {
								channelList : param.join(',')
							}
						});
			}
		});
	};

	window.showInfo = function(channelId) {
		channelForm.load({
					url : '/myads/HTML/sysfun/ChannelAction_detail.action',
					params : {
						channelId : channelId
					}
				});
		formWin.show();
	};

	var formWin = new Ext.Window({
				title : '频道',
				applyTo : 'ChannelFormWin',
				layout : 'fit',
				width : 450,
				height : 160,
				closeAction : 'hide',
				plain : true,
				layout : 'border',
				items : [channelForm]
			});

	var sm = new Ext.grid.CheckboxSelectionModel();
	var cm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(), sm, {
				header : '频道名称',
				dataIndex : 'name',
				sortable : true
			}, 
//				{
//				header : '上级频道名称',
//				dataIndex : 'parentName',
//				sortable : true
//			}, 
				{
				header : '所属网站',
				dataIndex : 'siteName',
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
							url : '/myads/HTML/sysfun/ChannelAction_showAll.action'
						}),
				remoteSort : true,
				reader : new Ext.data.JsonReader({
							totalProperty : 'total',
							idProperty : 'channelId',
							root : 'invdata'
						}, [{
									name : 'channelId'
								}, {
									name : 'name'
								}, 
//									{
//									name : 'parentName'
//								}, 
									{
									name : 'siteName'
								}])
			});

	ds.load({
				params : {
					start : 0,
					limit : 20
				}
			});

	var grid = new Ext.grid.GridPanel({
		el : 'grid',
		region : 'center',
		ds : ds,
		cm : cm,
		sm : sm,
		viewConfig : {
			forceFit : true
		},
		bbar : new Ext.PagingToolbar({
			pageSize : 20,
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
								param.push(this.get("channelId"));
							});
					if(param.length>0){
						Ext.MessageBox.confirm("提示", "确定删除？", function(channelId) {
							if (channelId == 'yes') {
								Ext.Ajax.request({
									method : 'post',
									url : '/myads/HTML/sysfun/ChannelAction_delete.action',
									success : function(resp) {
										var obj = Ext.util.JSON
												.decode(resp.responseText);
										if (obj.delMessage != '') {
													grid.getStore().reload();
													Ext.MessageBox.alert('提示','频道名称为'+obj.delMessage+'的频道有其他功能正在使用中，无法删除！');
													}
												if(obj.result == 'success'){
													grid.getStore().reload();
													Ext.MessageBox.alert('提示', '删除成功！');
													}
												if (obj.result == 'use') {
													grid.getStore().reload();
													Ext.MessageBox.alert('提示','无法删除,此频道其它功能正在使用中！');
													}
												if(obj.result == 'error'){
													grid.getStore().reload();
													Ext.MessageBox.alert('提示','操作失败，请联系系统管理员！');
													}
									},
									params : {
										channelList : param.join(',')
									}
								});
							} else {
								
							}
						});
					}else{
						Ext.MessageBox.alert('提示', '请选择需要删除的频道！');
					}
				}
			}]
		})
	});

	grid.addListener('rowdblclick', function(grid, rowindex, e) {
				var record = grid.getStore().getAt(rowindex);
				channelForm.load({
							url : '/myads/HTML/sysfun/ChannelAction_detail.action',
							params : {
								channelId : record.data.channelId
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
