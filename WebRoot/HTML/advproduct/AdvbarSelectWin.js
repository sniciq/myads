Ext.namespace("com.myads.AdvbarSelectWin"); 

com.myads.AdvbarSelectWin = function(config) {
	var win = this;
	this.addEvents('select');
	var advproductType = this.advproductType = config.advproductType;
	
	var searchForm = new Ext.FormPanel({
		frame: true,
		region: 'north',
		collapsed: false,
		collapseMode:'mini',
		autoHeight: true,
		labelWidth: 80,
		labelAlign: 'right',
		items: [
			{
				layout: 'column',
				items: [
					{
						columnWidth: .5, layout: 'form',
						items: [
							{xtype: 'textfield',name: 'name', fieldLabel: '广告条名称',anchor : '95%'}
						]
					},
					{
						columnWidth: .5, layout: 'form',
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
								value: config.nowStateValue,
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
	
	var sm = new Ext.grid.CheckboxSelectionModel({});
	var cm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		sm,
		{header:'id', dataIndex:'id', sortable:true},
		{header:'广告条名称', dataIndex:'name', sortable:true},
		{header:'广告容量', dataIndex:'content', sortable:true},
		{header:'规格', dataIndex:'positionName', sortable:true},
		{header:'频道', dataIndex:'channelName', sortable:true},
		{header:'状态', dataIndex:'status', sortable:true,renderer:rendStauts, align:'center'},
		{header:'排序数', dataIndex:'num', sortable:true}
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
		proxy: new Ext.data.HttpProxy({url: '/myads/HTML/advproduct/AdvproductAdvbarAction_searchAdvbar.action'}),
		remoteSort: false,
		reader: new Ext.data.JsonReader(
			{
				totalProperty: 'total',
				idProperty:'id',
				root: 'invdata'
			},
			[
				{name: 'id'},
				{name: 'name'},
				{name: 'positionName'},
				{name: 'barsizeId'},
				{name: 'channelId'},
				{name: 'channelName'},
				{name: 'status'},
				{name: 'content'},
				{name: 'num'}
			]
		)
	});
	
	var grid = new Ext.grid.GridPanel({
		region: 'center',
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
					text: '确定'	,
					iconCls: 'ok',
					handler: function() {
						var rs = grid.getSelectionModel().getSelections();
						if(rs.length <= 0) {
							win.hide();
						}
						
						var firstRC = rs[0];
						if(win.chkAdvbar) {
							if(win.advproductType == 'A') {//广告条规格必须相同
								if(firstRC.data.barsizeId != win.chkAdvbar.data.barsizeId) {
									Ext.MessageBox.alert('提示', '新增A类广告条规格必须与原来的相同 ！');
										return;
								}
							}
							else if(win.advproductType == 'B') {//广告条必须是同一频道下
								if(firstRC.data.channelId != win.chkAdvbar.data.channelId) {
									Ext.MessageBox.alert('提示', '新增B类广告条必须与原来属于同一频道 ！');
										return;
								}
							}
						}
						
						for(var i = 1; i < rs.length; i++) {
							if(win.advproductType == 'A') {//广告条规格必须相同
								if(rs[i].data.barsizeId != firstRC.data.barsizeId) {
									Ext.MessageBox.alert('提示', 'A类广告条规格必须相同！');
									return;
								}
							}
							else if(win.advproductType == 'B') {//广告条必须是同一频道下
								if(rs[i].data.channelId != firstRC.data.channelId) {
									Ext.MessageBox.alert('提示', '新增B类广告条必须与原来属于同一频道 ！');
										return;
								}
							}
						}
						
						sm.clearSelections();
						win.fireEvent('select', rs);
						win.hide();
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
	
	Ext.apply(this, {
		title: '查询广告条',
		layout:'fit',
		width:600,
		height:400,
		modal: true,
		closeAction:'hide',
		plain: true,
		layout: 'border',
		items: [searchForm, grid]
	});
	
	com.myads.AdvbarSelectWin.superclass.constructor.apply(this, arguments);
}

Ext.extend(com.myads.AdvbarSelectWin, Ext.Window, {
	onRender: function() {
		com.myads.AdvbarSelectWin.superclass.onRender.apply(this, arguments);
	},
	
	setCheckAdvbar: function(chkAdvbar) {
		this.chkAdvbar = chkAdvbar;
	}
});
