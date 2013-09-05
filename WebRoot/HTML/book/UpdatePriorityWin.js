Ext.namespace("com.myads.UpdatePriorityWin"); 
com.myads.UpdatePriorityWin = function(config) {
	this.addEvents('updatePriorityOver');
	
	var oldPriority = this.oldPriority = 1;
	var bookPackageId = this.oldPriority;
	var win = this;
	var updatePriorityForm = this.updatePriorityForm = new Ext.FormPanel({
		labelAlign: 'right',
		region: 'center',
		autoScroll:true, 
		labelWidth: 100,
		frame: true,
		xtype: 'fieldset',
		items: [
			new Ext.form.NumberField({
				name: 'priority', fieldLabel: '优先级',allowBlank : false,anchor : '95%',
				listeners: {
					'change' : function(tf) {
						if(tf.getValue() != oldPriority)
							Ext.getCmp('updatePriorityBtn').setDisabled(false);
						else
							Ext.getCmp('updatePriorityBtn').setDisabled(true);
					}
				}
			})
		],
		buttons: [
			{
				id: 'updatePriorityBtn',
				text: '保存',
				disabled: true,
				handler: function() {
					if(updatePriorityForm.getForm().findField('priority').getValue() == oldPriority)
						return;
						
					if(!updatePriorityForm.form.isValid())
						return;
						
					updatePriorityForm.form.submit( {
						url: '/myads/HTML/advflight/BookAction_updatePriority.action',
						method: 'post',
						waitTitle:'请等待',
						waitMsg: '正在提交...',
						params: {bookPackageId: win.bookPackageId},
						success: function(form, action) {
							if(action.result.result == 'success') {
								Ext.MessageBox.alert('结果', '保存成功！');
								form.reset();
								win.fireEvent('updatePriorityOver');
								win.hide();
					      	}
					      	else {
					      		if(action.result.info == 'BookConflict') {
					      			if(action.result.detailInfo.ConflictInfo == 'noLastContent') {
					      				Ext.MessageBox.show({
											title:'请等待',  
								            msg:'余量不足！！' + action.result.detailInfo.bookTime,  
								            width:240,  
								            progress:false,  
								            closable:true  
										});
					      			}
					      			else if(action.result.detailInfo.ConflictInfo == 'SaledByOtherType') {
					      				Ext.MessageBox.show({
											title:'请等待',  
								            msg:'已经被其它方式售卖！！' + action.result.detailInfo.bookTime,  
								            width:240,  
								            progress:false,  
								            closable:true  
										});
					      			}
					      		}
					      		else {
					      			Ext.MessageBox.alert('出错了！！！', '保存失败！！！！');
					      		}
					      	}
						}
					});
				}
			},
			{
				text: '取消',
				handler: function() {
					updatePriorityForm.getForm().reset();
					win.hide();
				}
			}
		]
	});
    		
	Ext.apply(this, {
		title:'优先级修改',                          
		width:300,                            
		height:100,                            
		draggable:true,                           
		modal : true,
		closeAction: 'hide',
		items: [updatePriorityForm]
	});
	
	com.myads.UpdatePriorityWin.superclass.constructor.apply(this, arguments);
}

Ext.extend(com.myads.UpdatePriorityWin, Ext.Window, {
	onRender: function() {
		com.myads.UpdatePriorityWin.superclass.onRender.apply(this, arguments);
	},
	
	setPriority: function(priority) {
		this.updatePriorityForm.getForm().findField('priority').setValue(priority);
	},
	
	setBookPackageId: function(bookPackageId) {
		this.bookPackageId = bookPackageId;
	}
});
