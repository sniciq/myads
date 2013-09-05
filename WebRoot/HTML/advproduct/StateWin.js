Ext.namespace("com.myads.StateWin"); 
com.myads.StateWin = function(config) {
	var win = this;
	this.addEvents('saveOver');
	
	var editForm = new Ext.form.FormPanel({
		frame: true,
		region: 'center',
		labelWidth: 50,
		autoHeight: true,
		labelAlign: 'right',
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
		],
		buttons: [
			{
				text: '保存'	,
				handler: function() {
					var statusValue = editForm.form.findField("status").getValue();
					Ext.Ajax.request({
						method: 'post',
						url: '/myads/HTML/advproduct/UpdateStateAction_save.action',
						waitTitle:'请等待',
						waitMsg: '正在提交...',
						params: {statusValue: statusValue, tableName: win.tableName, dataId: win.dataId, idColumn: win.idColumn},
						success:function(resp){
							var obj=Ext.util.JSON.decode(resp.responseText);
							if(obj.result == 'success') {
								Ext.MessageBox.alert('提示', '保存成功！', function() {
									win.hide();
									win.fireEvent('saveOver', obj.result);
								});
							}
							else {
								Ext.MessageBox.alert('报错了！！！', '保存失败！！！' + obj.info);
							}
						}
					});
				}
			}
		]
	});
	
	Ext.apply(this, {
		title: '更新状态',
		layout:'fit',
		width:300,
		height:120,
		modal: true,
		closeAction:'hide',
		plain: true,
		layout: 'border',
		items: [editForm]
	});
	
	com.myads.StateWin.superclass.constructor.apply(this, arguments);
}

Ext.extend(com.myads.StateWin, Ext.Window, {
	onRender: function() {
		com.myads.StateWin.superclass.onRender.apply(this, arguments);
	}
});
