Ext.onReady(function() {
	var advproductId = Ext.getDom("advproductId").value;
	var advbarSelectWin;
	
	var adproductInfoForm = new Ext.FormPanel({
		frame: true,
		region: 'north',
		labelAlign: 'right',
		labelWidth: 70,
		items: [
			{
				layout: 'column',
				items: [
					{
						columnWidth: .25, layout: 'form',
						items: [
							{xtype: 'textfield',name: 'advproductName', fieldLabel: '产品名称', disabled:true, cls:'form-textfield-show', anchor : '95%'}
						]
					},
					{
						columnWidth: .25, layout: 'form',
						items: [
							{xtype: 'textfield',name: 'type', fieldLabel: '类型', disabled:true, cls:'form-textfield-show', anchor : '95%'}
						]
					},
					{
						columnWidth: .25, layout: 'form',
						items: [
							new Ext.form.ComboBox({
								fieldLabel: '状态',
								name: 'status',
								hiddenName: 'status',
								anchor : '95%',
								triggerAction: 'all', 
								editable: true,
								mode: 'local',
								allowBlank : true,
								disabled:true, 
								cls:'form-textfield-show',
								valueField: 'value',
								displayField: 'text',
								store: new Ext.data.SimpleStore({
									fields: ['value', 'text'],
									data: [['0', '启用'],['2', '停用']]
								})
							})
						]
					},
					{
						columnWidth: .25, layout: 'form',
						items: [
							{xtype: 'textfield',name: 'createTime', fieldLabel: '创建时间', disabled:true, cls:'form-textfield-show', anchor : '95%'}
						]
					}
				]
			}
		]
	});
	adproductInfoForm.render('north-div');
	
	adproductInfoForm.load({
		url: '/myads/HTML/advproduct/AdvproductAction_getDetailInfo.action',
		params: {id: advproductId}
	});
	
	var cm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{header:'广告条ID', dataIndex:'advbarId', sortable:true},
		{header:'广告条名称', dataIndex:'advbarName', sortable:true},
		{header:'规格', dataIndex:'barsizeName', sortable:true},
		{header:'广告容量', dataIndex:'content', sortable:true},
		{header:'排序号', dataIndex:'sortOrder', sortable:true},
		{header:'状态', dataIndex:'status', sortable:true, renderer:rendStauts}
	]);
	
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
	
	var ds = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({url: '/myads/HTML/advproduct/AdvproductAdvbarAction_search.action'}),
		remoteSort: false,
		reader: new Ext.data.JsonReader(
			{
				totalProperty: 'total',
				idProperty:'id',
				root: 'invdata'
			},
			[
				{name: 'id'},
				{name: 'advbarId'},
				{name: 'advbarName'},
				{name: 'barsizeName'},
				{name: 'barsizeId'},
				{name: 'channelId'},
				{name: 'content'},
				{name: 'status'},
				{name: 'sortOrder'}
			]
		)
	});
	
	ds.baseParams = {advproductId: advproductId};
	ds.load({params: {start:0, limit:20}});
	var grid = new Ext.grid.GridPanel({
		el: 'grid',
		region: 'center',
		ds: ds,
		cm: cm,
		viewConfig: {
			forceFit: true
		},
		tbar: {
			buttons: [
				{
					text: '新增关联'	,
					iconCls: 'add',
					handler: function() {
						if(!advbarSelectWin) {
							advbarSelectWin = new com.myads.AdvbarSelectWin({
								advproductType: adproductInfoForm.getForm().findField('type').getValue()
							});
							
							advbarSelectWin.on('select', function(rs) {
								var advbarIds = [];
								for(var i = 0; i < rs.length; i++) {
									advbarIds.push(rs[i].data.id);
								}
								Ext.Ajax.request({
									method: 'post',
									url: '/myads/HTML/advproduct/AdvproductAdvbarAction_save.action',
									params: {advproductId: advproductId, advbarIds: advbarIds},
									success:function(resp){
										ds.reload();
									}
								});
							});
						}
						
						if(ds.data.items.length >= 1) {
							advbarSelectWin.setCheckAdvbar(ds.data.items[0]);
						}
							
							
						advbarSelectWin.show();
					}
				},
				{
					text: '取消关联'	,
					iconCls: 'del',
					handler: function() {
						var rs = grid.getSelectionModel().getSelected();
						
						Ext.MessageBox.confirm('提示', '确定取消关联  ' + rs.data.advbarId + ' ' + rs.data.advbarName + ' ？', function(btn) {
						if(btn != 'yes') {
							return;
						}
						Ext.Ajax.request({
							method: 'post',
							url: '/myads/HTML/advproduct/AdvproductAdvbarAction_delete.action',
							params: {id: rs.data.id},
							success:function(resp){
								var obj=Ext.util.JSON.decode(resp.responseText);
								if(obj.result == 'success') {
									grid.getStore().reload();
									Ext.MessageBox.alert('提示', '取消关联成功！');
								}
								else {
									Ext.MessageBox.alert('报错了！！！', '取消关联失败！！！' + obj.info);
								}
							}
						});
					});
					}
				}
			]
		},
		bbar: new Ext.PagingToolbar({
			pageSize: 20,
			store: ds,
			displayInfo: true,
			displayMsg: '显示第{0}条到{1}条记录,一共{2}条',
			emptyMsg: '没有记录'
		})
	});

	new Ext.Viewport({
		layout: 'border',
		items:[
			adproductInfoForm,grid
		]
	});
});