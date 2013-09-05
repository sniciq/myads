Ext.namespace("com.myads.book.BookTypeFormPanel"); 
com.myads.book.BookTypeFormPanel = function(config) {
	var bookTypeFormPanel = this;
	this.addEvents('selectType');
	
	Ext.apply(this, {
		frame: true,
		items: [
			{
				xtype: 'fieldset',
		        title: '选择类别',
		        autoHeight: true,
		        items: [
			        {
			        	xtype: 'radiogroup',
			            fieldLabel: '类别',
			            labelAlign: 'right',
			            columns: 1,
			            items: [
			                {
			                	boxLabel: '广告条预订', name: 'rb-col', inputValue: 1, checked: true
			                },
			                {
			                	boxLabel: '广告产品预订', name: 'rb-col', inputValue: 2,
			                	listeners: { 
			                		'check' : function(checkbox, checked){ 
			                			var a = bookTypeFormPanel.getForm().findField("rb-col").getGroupValue();
			                			bookTypeFormPanel.fireEvent('selectType', a);
			                		}
			                	}
			                }
			            ]
			        }
		        ]
			}
		]
	});
	
	com.myads.book.BookTypeFormPanel.superclass.constructor.apply(this, arguments);
}

Ext.extend(com.myads.book.BookTypeFormPanel, Ext.FormPanel, {
	onRender: function() {
		com.myads.book.BookTypeFormPanel.superclass.onRender.apply(this, arguments);
	},
	
	getBookType: function() {
		return this.getForm().findField("rb-col").getGroupValue();
	},
	
	setFormData: function(data) {
		this.getForm().findField("rb-col").setValue(data.bookpackageType);
	}
})
	

