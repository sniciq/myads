Ext.onReady(function() {
	
	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget='side';
	
	// 广告位
	var advpositionCombStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({url: '/myads/HTML/advert/AdvpositionAction_getAdvpositionList.action'}),
		reader: new Ext.data.ArrayReader({}, [
			{name: 'value'},{name: 'text'}
		])
	});
	advpositionCombStore.load();
	
	var advpositionCombo = new Ext.form.ComboBox({
		fieldLabel: '所属广告位',
		hiddenName: 'posId',
		store: advpositionCombStore,
		allowBlank : false,
		blankText: '此项不能为空!',
		editable : false,
		emptyText: '选择',
		mode: 'local',
		triggerAction: 'all',
		valueField: 'value',
		displayField: 'text',
		anchor : '95%',
		listeners : {
			select : function(postemCombo, record, index) {
				var posName = record.data.text;
				AdvbarForm.getForm().findField("posName").setValue(posName);
			}
		}
    });
	
	// 频道
	var channelComboStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({url: '/myads/HTML/sysfun/DepartmentAction_departmentList.action'}),
		reader: new Ext.data.ArrayReader({}, [
			{name: 'value'},{name: 'text'}
		])
	});
	channelComboStore.load();
	
	var channelCombo = new Ext.form.ComboBox({
		fieldLabel: '所属频道',
		hiddenName: 'channelId',
		store: channelComboStore,
		allowBlank : false,
		blankText: '此项不能为空!',
		editable : false,
		emptyText: '选择',
		mode: 'local',
		triggerAction: 'all',
		valueField: 'value',
		displayField: 'text',
		anchor : '95%'
    });
	
	// 网站
	var siteComboStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({url: '/myads/HTML/sysfun/DepartmentAction_departmentList.action'}),
		reader: new Ext.data.ArrayReader({}, [
			{name: 'value'},{name: 'text'}
		])
	});
	siteComboStore.load();
	
	var siteCombo = new Ext.form.ComboBox({
		fieldLabel: '所属网站',
		hiddenName: 'siteId',
		store: siteComboStore,
		allowBlank : false,
		blankText: '此项不能为空!',
		editable : false,
		emptyText: '选择',
		mode: 'local',
		triggerAction: 'all',
		valueField: 'value',
		displayField: 'text',
		anchor : '95%'
    });
	
	// 广告条规格
	var advbarComboStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({url: '/myads/HTML/advert/AdvpositionAction_getPositionsizeList.action'}),
		reader: new Ext.data.ArrayReader({}, [
			{name: 'value'},{name: 'text'}
		])
	});
	advbarComboStore.load();
	
	var advbarCombo = new Ext.form.ComboBox({
		fieldLabel: '广告条规格',
		hiddenName: 'barsizeId',
		store: advbarComboStore,
		allowBlank : false,
		blankText: '此项不能为空!',
		editable : false,
		emptyText: '选择',
		mode: 'local',
		triggerAction: 'all',
		valueField: 'value',
		displayField: 'text',
		anchor : '95%'
    });
	
	// 广告位模板
	var bartemComboStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({url: '/myads/HTML/advert/AdvpositionAction_getPostemplateList.action'}),
		reader: new Ext.data.ArrayReader({}, [
			{name: 'value'},{name: 'text'}
		])
	});
	bartemComboStore.load();
	
	var bartemCombo = new Ext.form.ComboBox({
		fieldLabel: '广告条模板',
		hiddenName: 'bartemId',
		store: bartemComboStore,
		allowBlank : false,
		blankText: '此项不能为空!',
		editable : false,
		emptyText: '选择',
		mode: 'local',
		triggerAction: 'all',
		valueField: 'value',
		displayField: 'text',
		anchor : '95%'
    });
    
    // 广告条列表
	var advbarStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({url: '/myads/HTML/advert/AdvbarAction_getAdvbarList.action'}),
		reader: new Ext.data.ArrayReader({}, [
			{name: 'value'},{name: 'text'}
		])
	});
	advbarStore.load();
	
	var advbarComboBox = new Ext.form.ComboBox({
		fieldLabel: '关联广告条',
		hiddenName: 'srcposId',
		store: advbarStore,
		allowBlank : false,
		blankText: '此项不能为空!',
		editable : false,
		emptyText: '选择',
		mode: 'local',
		triggerAction: 'all',
		valueField: 'value',
		displayField: 'text',
		anchor : '95%'
    });
    
    // 搜索栏
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
						text: '广告条名称:'
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
							formWin.show();
						}
					})
				]
			}
		]
	});
	searchForm.render('north-div');
	
	var AdvbarForm = new Ext.form.FormPanel({
		labelAlign: 'right',
		region: 'center',
		labelWidth: 70,
		frame: true,
		xtype: 'fieldset',
		items: [
			{
				items: [
					{	
						columnWidth: .01, layout: 'form',
		            	items: [
		            	        { xtype: 'hidden', name: 'id', hidden:true, hiddenLabel:true},
		            	        { xtype: 'hidden', name: 'posName', hidden:true, hiddenLabel:true}
		            	] 
			        }
				]
			},
			{
				items: [
			        {	columnWidth: .25, layout: 'form',
		            	items: [
		            	        { xtype: 'textfield', name: 'name', fieldLabel: '名称',anchor : '95%',allowBlank : false, blankText: '此项不能为空!'}
		            	]
		            },
		            {
								columnWidth : .25,
								layout : 'form',
								items : [{
											xtype : 'textfield',
											name : 'nameEn',
											fieldLabel : '英文名称',
											anchor : '95%',
											allowBlank : false,
											blankText : "只能输入英文和字符!",
											regex : /[^\d]/g,
											regexText : "只能输入英文和字符!"
										}]
							},
		            {	
						columnWidth: .25, layout: 'form',
		            	items: [
		            	        siteCombo
		            	] 
			        },
			        {	
						columnWidth: .25, layout: 'form',
		            	items: [
		            	        channelCombo
		            	] 
			        },
		            {	
						columnWidth: .25, layout: 'form',
		            	items: [
		            	        advpositionCombo
		            	] 
			        },
			        {	
						columnWidth: .25, layout: 'form',
		            	items: [
		            	        advbarCombo
		            	] 
			        },
			        {	
						columnWidth: .25, layout: 'form',
		            	items: [
		            	        bartemCombo
		            	] 
			        },
		            {
								columnWidth : .5,
								layout : 'form',
								items : [{
											xtype : 'textfield',
											name : 'content',
											fieldLabel : '广告容量 ',
											anchor : '95%',
											allowBlank : false,
											blankText : "只能输入数字!",
											regex : /\d/,
											regexText : "只能输入数字!"
										}]
							},
		            {
								columnWidth : .5,
								layout : 'form',
								items : [{
											xtype : 'textfield',
											name : 'num',
											fieldLabel : '广告条排序 ',
											anchor : '95%',
											allowBlank : false,
											blankText : "只能输入数字!",
											regex : /\d/,
											regexText : "只能输入数字!"
										}]
							},
		            {
								columnWidth : .5,
								layout : 'form',
								items : [{
											xtype : 'textfield',
											name : 'advType',
											fieldLabel : '物料类型',
											anchor : '95%',
											allowBlank : false,
											blankText : "只能输入数字!",
											regex : /\d/,
											regexText : "只能输入数字!"
										}]
							},
		            {
								columnWidth : .25,
								layout : 'form',
								items : [{
											xtype : 'textfield',
											name : 'srcposId',
											fieldLabel : '关联广告条 ',
											anchor : '95%',
											allowBlank : false,
											blankText : "只能输入数字!",
											regex : /\d/,
											regexText : "只能输入数字!"
										}]
							},
			        {
						columnWidth: .25, layout: 'form',
						items : [new Ext.form.ComboBox({
									allowBlank : false,
									blankText: '此项不能为空!',
									layout : 'form',
									fieldLabel : '状态',
									emptyText : '选择状态',
									editable : false,
									mode : 'local',
									hiddenName : 'status',
									triggerAction : 'all',
									displayField : 'text',
									valueField : 'value',
									anchor : '60%',
									store : (new Ext.data.SimpleStore({
												fields : ['value', 'text'],
												data : [['0', '启用'], ['1', '删除'],
														['2', '停用']]
											})),
									value : 2
								})]
					},
		            {	columnWidth: .25, layout: 'form',
		            	items: [
		            	        { xtype: 'textfield', name: 'note', fieldLabel: '备注 ',anchor : '95%'}
		            	]
		            }
				]
			}
		],
		buttons: [
			{
				text: '保存',
				handler: function() {
					AdvbarForm.form.doAction('submit', {
						url: '/myads/HTML/advert/AdvbarAction_save.action',
						method: 'post',
						params: '',
						success: function(form, action) {
							Ext.MessageBox.alert('结果', '保存成功！');
							form.reset();
							grid.getStore().reload();
							formWin.hide();
						}
					});
				}
			},
			{
				text: '取消',
				handler: function() {
					AdvbarForm.form.reset();
					formWin.hide();
				}
			}
		]
		
	});
	
	function renderOp(value, cellmeta, record, rowIndex, columnIndex, stor) {
		var id = record.data.id;
		var name = record.data.name; 
		var editStr = '<img alt=\"编辑\" src=\"/myads/HTML/images/edit.gif\" style=\"cursor: pointer;\" onclick=\"showInfo(\''+id+'\');\">';
		var delStr = '<img alt=\"删除\" src=\"/myads/HTML/images/del.gif\" style=\"cursor: pointer;\" onclick=\"deleteAdvbar(\''+id+'\', \'' + name + '\');\">';
		return editStr + '&nbsp;&nbsp;&nbsp;&nbsp;' + delStr;
	}
	
	window.deleteAdvbar = function(id, name) {
		Ext.MessageBox.confirm('提示', '确定删除  ' + name + ' ？', function(btn) {
			if(btn == 'yes') {
				var param = [];
				param.push(id);
				Ext.Ajax.request({
					method: 'post',
					url: '/myads/HTML/advert/AdvbarAction_delete.action',
				   	success:function(resp){
				    	var obj=Ext.util.JSON.decode(resp.responseText);
				      	if(obj.result == 'success') {
				      		grid.getStore().reload();
				      		Ext.MessageBox.alert('提示', '删除成功！');
				      	}
				      	if(obj.result == 'use') {
				      		grid.getStore().reload();
				      		Ext.MessageBox.alert('提示', '删除失败，此广告条外联使用中！！');
				      	}
				    },
				   	params: {advbarList: param.join(',')}
				});
			}
		});
	};
	
	window.showInfo = function(id) {
		AdvbarForm.load({
			url: '/myads/HTML/advert/AdvbarAction_getAdvbarDetail.action',
			params: {id: id}
		});
		formWin.show();
	};
	
	var formWin = new Ext.Window({
		title: '广告条管理',
        applyTo:'AdvbarFormWin',
        layout:'fit',
        width:480,
    	height:430,
        closeAction:'hide',
        plain: true,
        layout: 'border',
        items: [AdvbarForm]
    });
	
    //判断状态
	function rendStauts(value){
		if(value==0){
			return "<span style='color:GREEN'>可用</span>";
		}else if(value==1){
			return "<span style='color:GRAY'>已删除</span>";
		}else if(value==2){
			return "<span style='color:RED'>已停用</span>";
		}
	}
	
	var sm = new Ext.grid.CheckboxSelectionModel();
	var cm = new Ext.grid.ColumnModel([
		sm,
	    {header:'名称', dataIndex:'name', sortable:true},
	    {header:'英文名称', dataIndex:'nameEn', sortable:true},
	    {header:'状态', dataIndex:'status', sortable:true,renderer:rendStauts},
	    {header:'所属网站', dataIndex:'siteId', sortable:true},
	    {header:'所属频道', dataIndex:'channelId', sortable:true},
	    {header:'所属广告位', dataIndex:'posName', sortable:true},
	    {header:'创建人', dataIndex:'creator', sortable:true},
	    {header:'创建时间', dataIndex:'createTime', sortable:true},
	    {header:'操作', dataIndex:'op', renderer: renderOp,width:100, align:'left'}
	]);
	
	var ds = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({url: '/myads/HTML/advert/AdvbarAction_showAll.action'}),
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
				{name: 'nameEn'},
				{name: 'status'},
				{name: 'siteId'},
				{name: 'channelId'},
				{name: 'posName'},
				{name: 'creator'},
				{name: 'createTime'}
			]
		)
	});

	ds.load({params: {start:0, limit:50}});
	
	var grid = new Ext.grid.GridPanel({
		el: 'grid',
		region: 'center',
		ds: ds,
		cm: cm,
		sm: sm,
	    viewConfig: {
	    	forceFit: true
	    },
	    bbar: new Ext.PagingToolbar({
		    pageSize: 50,
		    store: ds,
			displayInfo: true,
			displayMsg: '显示第{0}条到{1}条记录,一共{2}条',
			emptyMsg: '没有记录',
			items: [
				'-',
				{
					text: '删除',
					handler: function() {
						var rs=grid.getSelectionModel().getSelections();
						var param = [];
						Ext.each(rs,function(){
							param.push(this.get("id"));
						});
						
						Ext.MessageBox.confirm("提示", "确定删除？", function(id) {
								if(id == 'yes') {
									Ext.Ajax.request({
										method: 'post',
										url: '/myads/HTML/advert/AdvbarAction_delete.action',
									   	success:function(resp){
									    	var obj=Ext.util.JSON.decode(resp.responseText);
									      	if(obj.result == 'success') {
									      		grid.getStore().reload();
									      		Ext.MessageBox.alert('提示', '删除成功！');
									      	}
									      	else {
									      		Ext.MessageBox.alert('报错了！！！', '删除失败！！！');
									      	}
									    },
									   	params: {advbarList: param.join(',')}
									});
								}
								else {
									alert(id);
								}
							}
						);
					}
				}
			]
	    })
	});
	
	grid.addListener('rowdblclick', function(grid, rowindex, e) {
		var record = grid.getStore().getAt(rowindex);
		AdvbarForm.load({
			url: '/myads/HTML/advbar/AdvbarAction_getAdvbarDetail.action',
			params: {id: record.id}
		});
		formWin.show();
	});

	new Ext.Viewport({
		layout: 'border',
		items:[
		       searchForm,grid
		]
	});
});
