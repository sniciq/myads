Ext.onReady(function() {
    
	// 初始化鼠标停留时的显示框,支持tips提示
	Ext.QuickTips.init();
	// 提示的方式，枚举值为"qtip","title","under","side",id(元素id)
	Ext.form.Field.prototype.msgTarget='side';
	// 广告位规格
	var positionsizeCombStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({url: '/myads/HTML/advert/AdvpositionAction_getPositionsizeList.action'}),
		reader: new Ext.data.ArrayReader({}, [
			{name: 'value'},{name: 'text'}
		])
	});
	positionsizeCombStore.load();
	
	var positionsizeCombo = new Ext.form.ComboBox({
		fieldLabel: '广告位规格',
		hiddenName: 'positionsizeId',
		store: positionsizeCombStore,
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
	var postemCombStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({url: '/myads/HTML/advert/AdvpositionAction_getPostemplateList.action'}),
		reader: new Ext.data.ArrayReader({}, [
			{name: 'value'},{name: 'text'}
		])
	});
	postemCombStore.load();
	
	var postemCombo = new Ext.form.ComboBox({
		fieldLabel: '广告位模板',
		hiddenName: 'postemId',
		store: postemCombStore,
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
    
    // 状态store
    var statusStore = new Ext.data.SimpleStore({
				fields : ['value', 'text'],
				data : [['0', '启用'], ['1', '删除'], ['2', '停用']]
			});
			
	var statusCombo = new Ext.form.ComboBox({
				allowBlank : false,
				blankText : '此项不能为空!',
				fieldLabel : '状态',
				emptyText : '选择状态',
				editable : false,
				mode : 'local',
				hiddenName : 'status',
				store : statusStore,
				triggerAction : 'all',
				displayField : 'text',
				valueField : 'value',
				anchor : '60%',
				value : 2	// 设置默认值
			})
	
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
						text: '广告位名称:'
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
							AdvpositionForm.form.reset();
							formWin.show();
						}
					})
				]
			}
		]
	});
	searchForm.render('north-div');
	
	var advpositionName = true;	// 验证name临时标签
	var AdvpositionForm = new Ext.form.FormPanel({
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
		            	        { xtype: 'hidden', name: 'id', hidden:true, hiddenLabel:true}
		            	] 
			        }
				]
			},
			{
				items: [
			        {
								columnWidth : .25,
								layout : 'form',
								items : [{
								xtype : 'textfield',
								id : 'name',
								name : 'name',
								fieldLabel : '名称',
								anchor : '95%',
								allowBlank : false,
								blankText : '此项不能为空!',
								validationEvent : 'blur',
								validator : function(thisText) {
									if (thisText != '') {
										Ext.Ajax.request({
											url : '/myads/HTML/advert/AdvpositionAction_checkAdvpositionName.action',
											method : 'post',
											params : {
												name : thisText
											},
											success : function(response, options) {
												if (response.responseText == 'use') {
													advpositionName = false;
													Ext.getCmp('name').markInvalid('广告位名称已被使用');
												} else if (response.responseText == 'empty') {
													advpositionName = false;
													Ext.getCmp('name').markInvalid('广告位名称不能为空');
												} else {
													advpositionName = true;
													Ext.getCmp('name').clearInvalid();
												}
											}
										});
										return advpositionName;
									} else {
									}
								}
							}]
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
		            	items: [positionsizeCombo] 
			        },
			        {	
						columnWidth: .25, layout: 'form',
		            	items: [postemCombo] 
			        },
			        {
						columnWidth : .25,
						layout : 'form',
						items : [{
									xtype : 'textfield',
									name : 'type',
									fieldLabel : '模板类型',
									anchor : '95%',
									allowBlank : false,
									blankText : "不能为空!",
									regex : /\d/,
									regexText : "只能输入数字!"
								}]
					}, {
						columnWidth : .25,
						layout : 'form',
						items : [{
									xtype : 'textfield',
									name : 'num',
									fieldLabel : '广告条数 ',
									anchor : '95%',
									allowBlank : false,
									blankText : "不能为空!",
									regex : /\d/,
									regexText : "只能输入数字!"
								}]
					},
			        {
						columnWidth: .5, layout: 'form',
						items : [statusCombo]
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
					AdvpositionForm.form.doAction('submit', {
						url: '/myads/HTML/advert/AdvpositionAction_save.action',
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
					AdvpositionForm.form.reset();
					formWin.hide();
				}
			}
		]
		
	});
	
	function renderOp(value, cellmeta, record, rowIndex, columnIndex, stor) {
		var id = record.data.id;
		var name = record.data.name; 
		var editStr = '<img alt=\"编辑\" src=\"/myads/HTML/images/edit.gif\" style=\"cursor: pointer;\" onclick=\"showInfo(\''+id+'\');\">';
		var delStr = '<img alt=\"删除\" src=\"/myads/HTML/images/del.gif\" style=\"cursor: pointer;\" onclick=\"deleteAdvposition(\''+id+'\', \'' + name + '\');\">';
		return editStr + '&nbsp;&nbsp;&nbsp;&nbsp;' + delStr;
	}
	
	window.deleteAdvposition = function(id, name) {
		Ext.MessageBox.confirm('提示', '确定删除  ' + name + ' ？', function(btn) {
			if(btn == 'yes') {
				var param = [];
				param.push(id);
				Ext.Ajax.request({
					method: 'post',
					url: '/myads/HTML/advert/AdvpositionAction_delete.action',
				   	success:function(resp){
				    	var obj=Ext.util.JSON.decode(resp.responseText);
				      	if(obj.result == 'success') {
				      		grid.getStore().reload();
				      		Ext.MessageBox.alert('提示', '删除成功！');
				      	}
				      	if (obj.result == 'use') {
							grid.getStore().reload();
							Ext.MessageBox.alert('提示','删除失败，此广告位其它功能正在使用中！');
						}
				    },
				   	params: {advpositionList: param.join(',')}
				});
			}
		});
	};
	
	window.showInfo = function(id) {
		AdvpositionForm.load({
			url: '/myads/HTML/advert/AdvpositionAction_getAdvpostionDetail.action',
			params: {id: id}
		});
		formWin.show();
	};
	
	var formWin = new Ext.Window({
		title: '广告位管理',
        applyTo:'AdvpositionFormWin',
        layout:'fit',
        width:480,
    	height:300,
        closeAction:'hide',
        plain: true,
        layout: 'border',
        items: [AdvpositionForm]
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
	    {header:'广告位规格', dataIndex:'positionsizeName', sortable:true},
	    {header:'广告位模板', dataIndex:'postemplateName', sortable:true},
	    {header:'状态', dataIndex:'status', sortable:true,renderer:rendStauts},
	    {header:'创建人', dataIndex:'creator', sortable:true},
	    {header:'创建时间', dataIndex:'createTime', sortable:true},
	    {header:'操作', dataIndex:'op', renderer: renderOp,width:100, align:'left'}
	]);
	
	var ds = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({url: '/myads/HTML/advert/AdvpositionAction_showAll.action'}),
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
				{name: 'positionsizeName'},
				{name: 'postemplateName'},
				{name: 'status'},
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
										url: '/myads/HTML/advert/AdvpositionAction_delete.action',
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
									   	params: {advpositionList: param.join(',')}
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
		AdvpositionForm.load({
			url: '/myads/HTML/advert/AdvpositionAction_getAdvpostionDetail.action',
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
