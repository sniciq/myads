Ext.onReady(function() {

	var editForm = new Ext.FormPanel({
		labelAlign: 'right',
		region: 'center',
		autoScroll:true, 
		labelWidth: 100,
		frame: true,
		xtype: 'fieldset',
		items: [
			{
				items: [
					{
						columnWidth: .01, layout: 'form',
						items: [
							{ xtype: 'hidden', name: 'id', hidden:true, hiddenLabel:true}
						]
					}
				]
			},
			{
				items: [
					{
						layout: 'form',
						items: [
							{xtype: 'textfield',name: 'advproductName', fieldLabel: '产品名称',anchor : '95%'}
						]
					},
					{
						layout: 'form',
						items: [
							new Ext.form.ComboBox({
								fieldLabel: '类型',
								name: 'type',
								hiddenName: 'type',
								anchor : '95%',
								triggerAction: 'all', 
								editable: false,
								mode: 'local',
								allowBlank : false,
								valueField: 'value',
								displayField: 'text',
								store: new Ext.data.SimpleStore({
									fields: ['value', 'text'],
									data: [['A', 'A类'],['B', 'B类']]
								})
							})
						]
					},
					{
						layout: 'form',
						items: [
							{xtype: 'textfield',name: 'remark', fieldLabel: '备注',anchor : '95%'}
						]
					}
				]
			}
		],
		buttons: [
			{
				text: '保存'	,
				handler: function() {
					if(!editForm.getForm().isValid())
						return;
					
					editForm.form.doAction('submit', {
						url: '/myads/HTML/advproduct/AdvproductAction_save.action',
						method: 'post',
						waitTitle:'请等待',
						waitMsg: '正在提交...',
						params: '',
						success: function(form, action) {
							if(action.result.result == 'success') {
								Ext.MessageBox.alert('结果', '保存成功！');
								form.reset();
								grid.getStore().reload();
								editWin.hide();
							}
						}
					});
				}
			},
			{
				text: '取消'	,
				handler: function() {
					editForm.form.reset();
					editWin.hide();
				}
			}
		]
	});
	var editWin = new Ext.Window({
		title: '编辑',
		modal: true,
		layout:'fit',
		width:450,
		height:180,
		closeAction:'hide',
		plain: true,
		layout: 'border',
		items: [editForm]
	});
	
	var searchForm = new Ext.FormPanel({
		frame: true,
		region: 'north',
		title: '查询',
		collapsible : true,
		collapsed: false,
		collapseMode:'mini',
		autoHeight: true,
		labelAlign: 'right',
		items: [
			{
				layout: 'column',
				items: [
					{
						columnWidth: .33, layout: 'form',
						items: [
							{xtype: 'textfield',name: 'advproductName', fieldLabel: '广告产品名称',anchor : '95%'}
						]
					},
					{
						columnWidth: .33, layout: 'form',
						items: [
							new Ext.form.ComboBox({
								fieldLabel: '类型',
								name: 'type',
								hiddenName: 'type',
								anchor : '95%',
								triggerAction: 'all', 
								editable: false,
								mode: 'local',
								allowBlank : true,
								valueField: 'value',
								displayField: 'text',
								store: new Ext.data.SimpleStore({
									fields: ['value', 'text'],
									data: [['A', 'A类'],['B', 'B类']]
								})
							})
						]
					},
					{
						columnWidth: .33, layout: 'form',
						items: [
							new Ext.form.ComboBox({
								fieldLabel: '状态',
								name: 'status',
								hiddenName: 'status',
								anchor : '95%',
								triggerAction: 'all', 
								editable: false,
								mode: 'local',
								allowBlank : true,
								valueField: 'value',
								displayField: 'text',
								store: new Ext.data.SimpleStore({
									fields: ['value', 'text'],
									data: [['0', '启用'],['2', '停用']]
								})
							})
						]
					}
				]
			}
		],
		buttons: [
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
			})
		]
	});
	
	searchForm.render('north-div');
	
	var sm = new Ext.grid.CheckboxSelectionModel({singleSelect:true});
	var cm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		sm,
		{header:'id', dataIndex:'id', sortable:true},
		{header:'广告产品名称', dataIndex:'advproductName', sortable:true},
		{header:'类型', dataIndex:'type', sortable:true},
		{header:'广告条个数', dataIndex:'advbarCount', sortable:true},
		{header:'创建人', dataIndex:'creator', sortable:true},
		{header:'创建时间', dataIndex:'createTime', sortable:true},
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
		proxy: new Ext.data.HttpProxy({url: '/myads/HTML/advproduct/AdvproductAction_search.action'}),
		remoteSort: false,
		reader: new Ext.data.JsonReader(
			{
				totalProperty: 'total',
				idProperty:'id',
				root: 'invdata'
			},
			[
				{name: 'id'},
				{name: 'advproductName'},
				{name: 'type'},
				{name: 'advCount'},
				{name: 'createTime'},
				{name: 'creator'},
				{name: 'advbarCount'},
				{name: 'status'}
			]
		)
	});
	ds.load({params: {start:0, limit:20}});
	var grid = new Ext.grid.GridPanel({
		region: 'center',
		el: 'grid',
		ds: ds,
		cm: cm,
		sm: sm,
		viewConfig: {
			forceFit: true
		},
		tbar: {
			buttons: [
	    		{
					text: '查询'	,
					iconCls: 'find',
					handler: function() {
						if(searchForm.collapsed)
							searchForm.expand();
						else
							searchForm.collapse();
					}
				},
				{
					text: '新增',
					iconCls: 'add',
					handler: function() {
						editForm.form.reset();
						editWin.show();
					}
				},
				{
					text: '修改'	,
					iconCls: 'edit',
					handler: function() {
						var rs = grid.getSelectionModel().getSelected();
						editForm.load({
							url: '/myads/HTML/advproduct/AdvproductAction_getDetailInfo.action',
							params: {id: rs.data.id}
						});
						editWin.show();
					}
				},
				{
					text: '状态'	,
					iconCls: 'status',
					handler: function() {
						var rs = grid.getSelectionModel().getSelected();
						var stateWin = new com.myads.StateWin({
							tableName: 't_advproduct', 
							dataId: rs.data.id, 
							idColumn: 'id',
							nowStateValue: rs.data.status
						});
						stateWin.on('saveOver', function(rst) {
							ds.reload();
						});
						stateWin.show();
					}
				},
				{
					text: '广告条'	,
					iconCls: 'detail',
					handler: function() {
						var rs = grid.getSelectionModel().getSelected();
						//window.location.href="advproduct_advbar.jsp?advproductId=" + rs.data.id;
						new Ext.Window({
							title: '广告条',
							layout:'fit',
							width:800,
							height:500,
							modal: true,
							closeAction:'hide',
							html: '<iframe scrolling="auto" frameborder="0" width="100%" height="100%" src="' +"advproduct_advbar.jsp?advproductId=" + rs.data.id + '"></iframe>',
							listeners : {
								'hide': function(rs) {
									ds.reload();
								}
							}
						}).show();
					}
				},
				{
					text: '删除'	,
					iconCls: 'del',
					handler: function() {
						var rs = grid.getSelectionModel().getSelected();
						Ext.MessageBox.confirm('提示', '确定删除  ' + rs.data.advproductName + ' ?', function(btn) {
							if(btn != 'yes') {
								return;
							}
							Ext.Ajax.request({
								method: 'post',
								url: '/myads/HTML/advproduct/AdvproductAction_delete.action',
								params: {id: rs.data.id},
								success:function(resp){
									var obj=Ext.util.JSON.decode(resp.responseText);
									if(obj.result == 'success') {
										grid.getStore().reload();
										Ext.MessageBox.alert('提示', '删除成功！');
									}
									else {
										Ext.MessageBox.alert('报错了！！！', '删除失败！！！' + obj.info);
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
			searchForm,grid
		]
	});
});