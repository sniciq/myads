Ext.onReady(function() {

	var saleTypeComb;
	var saleTypeCombStore;
	// 售卖方式
	saleTypeCombStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({url: '/myads/HTML/basic/OptionsAction_getBaseDataNamesByType.action'}),
		reader: new Ext.data.ArrayReader({}, [
			{name: 'value'},{name: 'text'}
		])
	});
	saleTypeCombStore.load({params: {dataType:'saletype'}});
	
	var formatComb;
	var formatCombStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({url: '/myads/HTML/basic/OptionsAction_getBaseDataNamesByType.action'}),
		reader: new Ext.data.ArrayReader({}, [
			{name: 'value'},{name: 'text'}
		])
	});
	
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
							{xtype: 'textfield',name: 'priceName', allowBlank : false, fieldLabel: '名&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;称',anchor : '95%'}
						]
					},
					{
						layout: 'form',
						items: [
							saleTypeComb = new Ext.form.ComboBox({
		        	       		fieldLabel: '售卖方式',
		        	       		id: 'saleTypeCombId',
								name: 'saleTypeName',
								hiddenName: 'saleTypeValue', 
								mode: 'local',
								triggerAction: 'all', 
								editable: false,
								allowBlank : false,
								valueField: 'value',
								displayField: 'text',
								anchor : '95%',
								store: saleTypeCombStore,
								listeners: {
									 select : function(ComboBox, record, index) {
									 	var value = record.data.text;
					                	formatCombStore.load({params:{dataType: value}});
									 }
								}
							})
						]
					},
					{
						layout: 'form',
						items: [
							formatComb = new Ext.form.ComboBox({
		        	       		fieldLabel: '形&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;式',
		        	       		id: 'formatCombId',
								name: 'format',
								hiddenName: 'format', 
								mode: 'local',
								triggerAction: 'all', 
								editable: false,
								allowBlank : false,
								valueField: 'value',
								displayField: 'text',
								anchor : '95%',
								store: formatCombStore
							})
						]
					},
					{
						layout: 'form',
						items: [
							{xtype: 'textfield',name: 'price',  allowBlank : false, fieldLabel: '价&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;格',anchor : '95%'}
						]
					},
					
					{
						layout: 'form',
						items: [
							{xtype: 'textfield',name: 'clickRate',  allowBlank : false, fieldLabel: '点&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;击',anchor : '95%'}
						]
					},
					{
						layout: 'form',
						items: [
							{xtype: 'textfield',name: 'impression',  allowBlank : false, fieldLabel: '曝&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;光',anchor : '95%'}
						]
					},
					{
						layout: 'form',
						items: [
							{xtype: 'textfield',name: 'materiel',  allowBlank : false, fieldLabel: '物料要求',anchor : '95%'}
						]
					},
					
					{
						layout: 'form',
						items: [
							{xtype: 'textfield',name: 'remark', fieldLabel: '备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注',anchor : '95%'}
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
						url: '/myads/html/struts/PriceAction_save.action',
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
		height:300,
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
							{xtype: 'textfield',name: 'priceName', fieldLabel: '名称',anchor : '95%'}
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
		sm,
		new Ext.grid.RowNumberer(),
		{header:'名称', dataIndex:'priceName', sortable:true},
		{header:'售卖方式', dataIndex:'saleTypeName', sortable:true},
		{header:'形式', dataIndex:'formatName', sortable:true},
		{header:'价格', dataIndex:'price', sortable:true},
		{header:'点击', dataIndex:'clickRate', sortable:true},
		{header:'曝光', dataIndex:'impression', sortable:true},
		{header:'物料要求', dataIndex:'materiel', sortable:true},
		{header:'状态', dataIndex:'status', sortable:true, renderer:rendStauts},
		{header:'备注', dataIndex:'remark', sortable:true}
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
		proxy: new Ext.data.HttpProxy({url: '/myads/html/struts/PriceAction_search.action'}),
		remoteSort: false,
		reader: new Ext.data.JsonReader(
			{
				totalProperty: 'total',
				idProperty:'id',
				root: 'invdata'
			},
			[
				{name: 'id'},
				{name: 'priceName'},
				{name: 'saleTypeValue'},
				{name: 'saleTypeName'},
				{name: 'format'},
				{name: 'formatName'},
				{name: 'price'},
				{name: 'clickRate'},
				{name: 'impression'},
				{name: 'materiel'},
				{name: 'status'},
				{name: 'remark'}
			]
		)
	});
	ds.load({params: {start:0, limit:20}});
	var grid = new Ext.grid.GridPanel({
		el: 'grid',
		region: 'center',
		ds: ds,
		sm: sm,
		cm: cm,
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
							url: '/myads/html/struts/PriceAction_getDetailInfo.action',
							params: {id: rs.data.id},
							success : function(form, action) {
								formatCombStore.removeAll();
								var record = new Ext.data.Record({text:action.result.data.formatName, value:action.result.data.format});
								formatCombStore.add(record);
								formatComb.setValue(action.result.data.format);
							}
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
							tableName: 't_price', 
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
					text: '删除'	,
					iconCls: 'del',
					handler: function() {
						var rs = grid.getSelectionModel().getSelected();
						Ext.MessageBox.confirm('提示', '确定删除  ' + rs.data.priceName + ' ?', function(btn) {
							if(btn != 'yes') {
								return;
							}
							
							Ext.Ajax.request({
								method: 'post',
								url: '/myads/html/struts/PriceAction_delete.action',
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