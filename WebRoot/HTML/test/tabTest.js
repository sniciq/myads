Ext.onReady(function() {
	var editForm = new Ext.FormPanel({
    	labelAlign: 'right',
		region: 'center',
		labelWidth: 90,
		frame: true,
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
			{	layout: 'form',
            	items: [
            	        { xtype: 'textfield', name: 'film_name', fieldLabel: '名称 ',anchor : '95%'}
            	]
            },
            {	
    			layout: 'form',
            	items: [
            	      new Ext.form.DateField({
						fieldLabel: '开始日期',
						name: 'time_start',
						allowBlank:false,
						format:'Y-m-d',
						anchor : '95%'
					})
            	]
            },
            {	
    			layout: 'form',
            	items: [
            	      new Ext.form.DateField({
						fieldLabel: '结束日期',
						name: 'time_end',
						allowBlank:false,
						format:'Y-m-d',
						anchor : '95%'
					})
            	]
            },
            {	
            	layout: 'form',
            	items: [
            	       new Ext.form.ComboBox({
		    	       		fieldLabel: '状态',
							hiddenName:"isstate",
							emptyText: '选择',
							mode: 'local',
							triggerAction: 'all', 
							valueField: 'value',
							displayField: 'text',
							store: new Ext.data.SimpleStore({
								fields: ['value', 'text'],
								data: [['false', '不统计'],['true', '统计']]
							}),
							listeners: {    
								load: function() {    
							   		this.setValue(getValue());    
								}    
							}  
		    	       })
            	]
            },
            {	columnWidth: .5, layout: 'form',
            	items: [
            	        { xtype: 'textfield', name: 'remark', fieldLabel: '备注 ',anchor : '95%'}
            	]
            }
		],
		buttons: [
			{
				text: '保存',
				handler: function() {
					editForm.form.doAction('submit', {
						url: '/myads/HTML/basic/HotFilmAction_save.action',
						method: 'post',
						params: '',
						success: function(form, action) {
							ds.load({params: {start:0, limit:20}});
							Ext.MessageBox.alert('结果', '保存成功！');
							editForm.form.reset();
							formWin.hide();
						}
					});
				}
			},
			{
				text: '取消',
				handler: function() {
					editForm.form.reset();
					formWin.hide();
				}
			}
		]
    });
    
	var tabs = new Ext.TabPanel({
		renderTo: 'mainTab',
		frame:true,
        defaults:{autoHeight: true},
        activeTab: 0,
		items: [
			{
				title: 'Normal Tab',
	            html: "My content was added during construction."
			},
			{
				title: 'Normal Tab',
	            html: "My content was added during construction."
			},
			{
				title: 'Normal Tab',
				items: [editForm]
			}
		]
	});
});