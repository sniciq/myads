Ext.onReady(function() {
	var dataTypeCombStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({url: '/myads/HTML/basic/OptionsAction_getBaseDataTypes.action'}),
		reader: new Ext.data.ArrayReader({}, [
			{name: 'value'},{name: 'text'}
		])
	});
	dataTypeCombStore.load();
	
	var combo = new Ext.form.ComboBox({
		fieldLabel: '类型',
		name: 'dataType',
		store: dataTypeCombStore,
		allowBlank : false,
		mode: 'local',
		triggerAction: 'all',
		valueField: 'value',
		displayField: 'text',
		anchor : '95%'
    });
    
    
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
						text: '类型:'
					}),
					new Ext.form.ComboBox({
						fieldLabel: '类型',
						name: 'dataType',
						store: dataTypeCombStore,
						mode: 'local',
						triggerAction: 'all',
						valueField: 'value',
						displayField: 'text',
						anchor : '95%'
				    }),
					new Ext.form.Label({
						text: '名称:'
					}),
					new Ext.form.TextField({
						layout: 'form',
						name: 'dataName'
					})
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
					}),
					new Ext.Button({
						text: '新增',
						width: 70,
						handler: function() {
							BaseDataForm.form.reset();
							formWin.show();
						}
					})
				]
			}
		]
	});
	
	searchForm.render('north-div');
	
	var BaseDataForm = new Ext.form.FormPanel({
		labelAlign: 'right',
		region: 'center',
		labelWidth: 50,
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
						columnWidth: .25, layout: 'form',
		            	items: [
		            	        combo
		            	] 
			        },
			        {	columnWidth: .25, layout: 'form',
		            	items: [
		            	        { xtype: 'textfield', name: 'dataName', fieldLabel: '名称',anchor : '95%'}
		            	]
		            },
			        
		            {	columnWidth: .25, layout: 'form',
		            	items: [
		            	        { xtype: 'textfield', name: 'dataValue', fieldLabel: '值 ',anchor : '95%'}
		            	]
		            },
		            {	columnWidth: .5, layout: 'form',
		            	items: [
		            	        { xtype: 'textfield', name: 'remark', fieldLabel: '备注 ',anchor : '95%'}
		            	]
		            }
				]
			}
		],
		buttons: [
			{
				text: '保存',
				handler: function() {
					BaseDataForm.form.doAction('submit', {
						url: '/myads/HTML/basic/BaseDataAction_save.action',
						method: 'post',
						params: '',
						success: function(form, action) {
							Ext.MessageBox.alert('结果', '保存成功！');
							form.reset();
							grid.getStore().reload();
							dataTypeCombStore.load();
							formWin.hide();
						}
					});
				}
			},
			{
				text: '取消',
				handler: function() {
					BaseDataForm.form.reset();
					formWin.hide();
				}
			}
		]
		
	});
	
	function renderOp(value, cellmeta, record, rowIndex, columnIndex, stor) {
		var id = record.data.id;
		var dataValue = record.data.dataName + ' ' + record.data.dataValue; 
		var editStr = '<img alt=\"编辑\" src=\"/myads/HTML/images/edit.gif\" style=\"cursor: pointer;\" onclick=\"showInfo(\''+id+'\');\">';
		var delStr = '<img alt=\"删除\" src=\"/myads/HTML/images/del.gif\" style=\"cursor: pointer;\" onclick=\"deleteBaseData(\''+id+'\', \'' + dataValue + '\');\">';
		return editStr + '&nbsp;&nbsp;&nbsp;&nbsp;' + delStr;
	}
	
	window.deleteBaseData = function(id, dataValue) {
		Ext.MessageBox.confirm('提示', '确定删除  ' + dataValue + ' ？', function(btn) {
			if(btn == 'yes') {
				var param = [];
				param.push(id);
				Ext.Ajax.request({
					method: 'post',
					url: '/myads/HTML/basic/BaseDataAction_delete.action',
				   	success:function(resp){
				    	var obj=Ext.util.JSON.decode(resp.responseText);
				      	if(obj.result == 'success') {
				      		grid.getStore().reload();
				      		dataTypeCombStore.load();
				      		Ext.MessageBox.alert('提示', '删除成功！');
				      	}
				      	else {
				      		Ext.MessageBox.alert('报错了！！！', '删除失败！！！');
				      	}
				    },
				   	params: {baseDataList: param.join(',')}
				});
			}
		});
	}
	
	window.showInfo = function(id) {
		BaseDataForm.load({
			url: '/myads/HTML/basic/BaseDataAction_getBaseDataDetail.action',
			params: {baseDataId: id}
		});
		formWin.show();
	}
	
	var formWin = new Ext.Window({
		title: '数据字典',
        applyTo:'BaseDataFormWin',
        layout:'fit',
        width:450,
    	height:190,
        closeAction:'hide',
        plain: true,
        layout: 'border',
        items: [BaseDataForm]
    });
	
	var sm = new Ext.grid.CheckboxSelectionModel();
	var cm = new Ext.grid.ColumnModel([
		sm,
	    {header:'类型', dataIndex:'dataType', sortable:true},
	    {header:'名称', dataIndex:'dataName', sortable:true},
	    {header:'值', dataIndex:'dataValue', sortable:true},
	    {header:'操作', dataIndex:'op', renderer: renderOp,width:100, align:'left'}
	]);
	
	var ds = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({url: '/myads/HTML/basic/BaseDataAction_search.action'}),
		remoteSort: false,
		reader: new Ext.data.JsonReader(
			{
				totalProperty: 'total',
				idProperty:'id',
				root: 'invdata'
			},
			[
				{name: 'id'},
				{name: 'dataType'},
				{name: 'dataName'},
				{name: 'dataValue'}
			]
		)
	});

	ds.load({params: {start:0, limit:20}});
	
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
		    pageSize: 20,
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
										url: '/myads/HTML/basic/BaseDataAction_delete.action',
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
									   	params: {baseDataList: param.join(',')}
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
		BaseDataForm.load({
			url: '/myads/HTML/basic/BaseDataAction_getBaseDataDetail.action',
			params: {baseDataId: record.id}
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
