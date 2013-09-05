Ext.onReady(function() {

	var searchForm = new Ext.FormPanel({
		frame: true,
		region: 'north',
		labelAlign: 'right',
		buttons:[new Ext.Button({
			text: '新增',
			width: 70,
			handler: function() {
				siteForm.form.reset();
				formWin.show();
			}
		})]
//		items: [
//			{
//				columnWidth: .33,
//				layout:'hbox',
//				layoutConfig: {
//                    align:'middle'
//                },
//				defaults:{margins:'0 10 0 0'},
//				items: [
////					new Ext.form.Label({
////						text: '网站名称:'
////					}),
////					new Ext.form.TextField({
////						layout: 'form',
////						name: 'siteName'
////					}),
////					new Ext.Button({
////						text: '查询',
////						width: 70,
////						handler: function() {
////							var fv = searchForm.getForm().getValues();
////							ds.baseParams= fv;
////							ds.load({params: {start:0, limit:20}});
////						}
////					}),
////					new Ext.Button({
////						text: '清空',
////						width: 70,
////						handler: function() {
////							searchForm.form.reset();
////							ds.baseParams= {};
////							ds.load({params: {start:0, limit:20}});
////						}
////					}),
//				]
//			}
//		]
	});
	searchForm.render('north-div');
			
	var siteForm = new Ext.form.FormPanel({
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
											name : 'siteId',
											hidden : true,
											hiddenLabel : true
										}, {
											xtype : 'hidden',
											name : 'ids',
											hidden : true,
											hiddenLabel : true
										}, {
											xtype : 'textfield',
											name : 'siteName',
											fieldLabel : '网站名称',
											allowBlank : false,
											blankText : '网站名称不能为空!',
											anchor : '95%'
										}, {
											xtype : 'textfield',
											name : 'nameEn',
											fieldLabel : '英文名',
											allowBlank : false,
											blankText : '网站英文名称不能为空!',
											anchor : '95%'
										},{
											xtype : 'textfield',
											name : 'modulus',
											fieldLabel : '网站系数',
											allowBlank : false,
											blankText : '网站系数不能为空!',
											anchor : '95%'
										}]
							}]
				}],
		buttons : [{
			text : '保存',
			handler : function() {
				siteForm.form.doAction('submit', {
							url : '/myads/HTML/sysfun/SiteAction_save.action',
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
				siteForm.form.reset();
				formWin.hide();
			}
		}]

	});

	function renderOp(value, cellmeta, record, rowIndex, columnIndex, stor) {
		var siteId = record.data.siteId;
		var siteName = record.data.siteName;
		var editStr = '<img alt=\"编辑\" src=\"/myads/HTML/images/edit.gif\" style=\"cursor: pointer;\" onclick=\"showInfo(\''
				+ siteId + '\');\">';
		var delStr = '<img alt=\"删除\" src=\"/myads/HTML/images/del.gif\" style=\"cursor: pointer;\" onclick=\"deleteSite(\''
				+ siteId + '\', \'' + siteName + '\');\">';
		return editStr + '&nbsp;&nbsp;&nbsp;&nbsp;' + delStr;
	}

	window.deleteSite = function(siteId, siteName) {
		Ext.MessageBox.confirm('提示', '确定删除  ' + siteName + ' ？', function(btn) {
			if (btn == 'yes') {
				var param = [];
				param.push(siteId);
				Ext.Ajax.request({
							method : 'post',
							url : '/myads/HTML/sysfun/SiteAction_delete.action',
							success : function(resp) {
								var obj = Ext.util.JSON.decode(resp.responseText);
								if (obj.delMessage != '') {
										grid.getStore().reload();
										Ext.MessageBox.alert('提示','网站名称为'+obj.delMessage+'的网站有其他功能正在使用中，无法删除！');
								}
								if(obj.result == 'success'){
										grid.getStore().reload();
										Ext.MessageBox.alert('提示', '删除成功！');
									}
								if (obj.result == 'use') {
									grid.getStore().reload();
									Ext.MessageBox.alert('提示','无法删除,此网站其它功能正在使用中！');
								}
								if(obj.result == 'error'){
									grid.getStore().reload();
									Ext.MessageBox.alert('提示','操作失败，请联系系统管理员！');
								}
							},
							params : {
								siteList : param.join(',')
							}
						});
			}
		});
	};

	window.showInfo = function(siteId) {
		siteForm.load({
					url : '/myads/HTML/sysfun/SiteAction_detail.action',
					params : {
						siteId : siteId
					}
				});
				formWin.show();
	};
	
	var formWin = new Ext.Window({
		title: '网站',
        applyTo:'SiteFormWin',
        layout:'fit',
        width:450,
    	height:160,
        closeAction:'hide',
        plain: true,
        layout: 'border',
        items: [siteForm]
    });

	var sm = new Ext.grid.CheckboxSelectionModel();
	var cm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(), sm, {
				header : '网站名称',
				dataIndex : 'siteName',
				sortable : true
			}, {
				header : '英文名',
				dataIndex : 'nameEn',
				sortable : true
			}, {
				header : '网站系数',
				dataIndex : 'modulus',
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
							url : '/myads/HTML/sysfun/SiteAction_showAll.action'
						}),
				remoteSort : true,
				reader : new Ext.data.JsonReader({
							totalProperty : 'total',
							idProperty : 'siteId',
							root : 'invdata'
						}, [{
									name : 'siteId'
								}, {
									name : 'siteName'
								}, {
									name : 'nameEn'
								}, {
									name : 'modulus'
								},{
									name : 'status'
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
						param.push(this.get("siteId"));
					});
					if(param.length>0){
						Ext.MessageBox.confirm("提示", "确定删除？",
							function(siteId) {
								if (siteId == 'yes') {
									
									Ext.Ajax.request({
										method : 'post',
										url : '/myads/HTML/sysfun/SiteAction_delete.action',
										success : function(resp) {
											var obj = Ext.util.JSON.decode(resp.responseText);
											if (obj.delMessage != '') {
												grid.getStore().reload();
												Ext.MessageBox.alert('提示',obj.delMessage+'的网站有其他功能正在使用中，无法删除！');
												}
											if(obj.result == 'success'){
												grid.getStore().reload();
												Ext.MessageBox.alert('提示', '删除成功！');
												}
											if (obj.result == 'use') {
												grid.getStore().reload();
												Ext.MessageBox.alert('提示','无法删除,此网站其它功能正在使用中！');
												}
											if(obj.result == 'error'){
												grid.getStore().reload();
												Ext.MessageBox.alert('提示','操作失败，请联系系统管理员！');
												}
										},
										params:{siteList: param.join(',')}
									});
								} else {
								}
							});
					}else{
						Ext.MessageBox.alert('提示', '请选择需要删除的网站！');
					}
				}
			}]
		})
	});

	grid.addListener('rowdblclick', function(grid, rowindex, e) {
				var record = grid.getStore().getAt(rowindex);
				siteForm.load({
							url : '/myads/HTML/sysfun/SiteAction_detail.action',
							params : {
								siteId : record.data.siteId
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
