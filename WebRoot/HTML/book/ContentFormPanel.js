Ext.namespace("com.myads.book.ContentFormPanel"); 
com.myads.book.ContentFormPanel = function(config) {
	var contentFormPanel = this;
	this.addEvents('isContentChange');
	
	Ext.apply(this, {
		frame: true,
		labelWidth: 90,
		labelAlign: 'right',
		region: 'center',
		items:[
//			{
//				layout: 'form',
//            	items: [
//            		{	
//						xtype: 'checkbox',fieldLabel: '是否空广告',name: 'isNull', inputValue: '1'
//					}
//            	]
//			},
			{
				xtype: 'checkbox',fieldLabel: '是否内容定向',name: 'isContentDirect',id: 'isContent', inputValue: '1',
				listeners: {
					check: function (obj, ischecked) {
						if(ischecked) {
							contentFormPanel.getForm().findField("keyword").show();
							contentFormPanel.getForm().findField("user").show();
							contentFormPanel.getForm().findField("video").show();
							contentFormPanel.getForm().findField("program").show();
							contentFormPanel.getForm().findField("activity").show();
							contentFormPanel.getForm().findField("subject").show();
						}
						else {
							contentFormPanel.getForm().findField("keyword").setValue('');
							contentFormPanel.getForm().findField("user").setValue('');
							contentFormPanel.getForm().findField("video").setValue('');
							contentFormPanel.getForm().findField("program").setValue('');
							contentFormPanel.getForm().findField("activity").setValue('');
							contentFormPanel.getForm().findField("subject").setValue('');
							contentFormPanel.getForm().findField("keyword").hide();
							contentFormPanel.getForm().findField("user").hide();
							contentFormPanel.getForm().findField("video").hide();
							contentFormPanel.getForm().findField("program").hide();
							contentFormPanel.getForm().findField("activity").hide();
							contentFormPanel.getForm().findField("subject").hide();
						}
						contentFormPanel.fireEvent('isContentChange', ischecked);
					}
				}
			},
			{
				layout: 'form',
            	items: [
            	        { xtype: 'textfield', name: 'keyword', fieldLabel: '关键字 ', width:500, hidden: true}
            	]
			},
			{
				layout: 'form',
            	items: [
            	        { xtype: 'textfield', name: 'user', fieldLabel: '用户 ', width:500, hidden: true}
            	]
			},
			{
				layout: 'form',
            	items: [
            	        { xtype: 'textfield', name: 'video', fieldLabel: '视频ID ', width:500, hidden: true}
            	]
			},
			{
				layout: 'form',
            	items: [
            	        { xtype: 'textfield', name: 'program', fieldLabel: '节目 ', width:500, hidden: true}
            	]
			},
			{
				layout: 'form',
            	items: [
            	        { xtype: 'textfield', name: 'activity', fieldLabel: '活动 ', width:500,hidden: true}
            	]
			},
			{
				layout: 'form',
            	items: [
            	        { xtype: 'textfield', name: 'subject', fieldLabel: '专题 ', width:500,hidden: true}
            	]
			}
		]
	});
	
	com.myads.book.ContentFormPanel.superclass.constructor.apply(this, arguments);
}

Ext.extend(com.myads.book.ContentFormPanel, Ext.FormPanel, {
	onRender: function() {
		com.myads.book.ContentFormPanel.superclass.onRender.apply(this, arguments);
	},
	
	getIsContentDirect: function() {
		var isContentDirect = false;
		if(this && this.getForm().getEl())
			isContentDirect = this.getForm().findField("isContentDirect").getValue();
		return 	isContentDirect;
	},
	
	myValid: function() {
		var isContentDirectChk = this.getForm().findField("isContentDirect");
		if(isContentDirectChk.checked) {
			if(this.getForm().findField("keyword").getValue() == '' 
				&& this.getForm().findField("user").getValue() == '' 
				&& this.getForm().findField("video").getValue() == ''
				&& this.getForm().findField("program").getValue() == ''
				&& this.getForm().findField("activity").getValue() == ''
				&& this.getForm().findField("subject").getValue() == '' 
			) {
				return false;
			}
		}
		return true;
	},
	
	getFormValues: function() {
		return this.getForm().getValues();
	},
	
	setFormData: function(data) {
		this.getForm().reset()
		this.getForm().setValues(data);
	}
});