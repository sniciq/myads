Ext.onReady(function() {
	
	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget='side';
	
	var advbarId = Ext.get('advbarId').dom.value;
	var edit_form = new Ext.form.FormPanel({
		labelAlign: 'right',
		region: 'center',
		labelWidth: 60,
		frame: true,
		items: [
			{
				items: [
					{	
						columnWidth: .01, layout: 'form',
			        	items: [
			        	        { xtype: 'hidden', name: 'advbarId', value: advbarId, hidden:true, hiddenLabel:true},
			        	] 
			        }
				]
			},
			{
				columnWidth: 1, layout: 'form',
				items: [{
					xtype : 'textfield',
					name : 'srcposId',
					fieldLabel : 'ID',
					anchor : '95%',
					allowBlank : false,
					blankText : "只能输入数字!",
					regex : /\d/,
					regexText : "只能输入数字!"
				}]
			}
		],
		buttons: [
			{
					text: '保存',
					handler: function() {
						Ext.Ajax.request({
							url: '/myads/HTML/advert/AdvbarAction_relationAdvbarId.action',
							method : 'post',
							params : {advbarId : advbarId,srcposId : edit_form.getForm().findField("srcposId").getValue()},
							success : function(response) {
								var obj=Ext.util.JSON.decode(response.responseText);
								if(obj.result == 'empty') {
						      		Ext.MessageBox.alert('提示', '您输入的广告条不存在！');
						      	} else {
						      		if (obj.result == 'success') {
										Ext.MessageBox.alert('提示','保存成功！');
									} else {
										Ext.MessageBox.alert('提示', '保存失败！');
									}
						      	}
							}
						});
					}
				}
		]
	});
	
	edit_form.render('edit_form');
	
	new Ext.Viewport({
		layout: 'border',
		items:[
		       edit_form	
		]
	});
});