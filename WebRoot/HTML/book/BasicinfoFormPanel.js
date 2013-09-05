Ext.namespace("com.myads.book.BasicinfoFormPanel"); 
com.myads.book.BasicinfoFormPanel = function(config) {
	
	var kpiGroup = {
		 xtype: 'fieldset',
		 title: 'KPI',
		 layout: 'form',
		 items: [
			 {
			 	layout:'column',
			 	items:[
			 		{
						columnWidth: .5, layout: 'form',
						items: [{xtype : 'textfield',name : 'impression',fieldLabel : '曝光',disabled:true,cls:'form-textfield-show',anchor : '99%'}]
					},
					{
						columnWidth: .5, layout: 'form',
						items: [{xtype : 'textfield',name : 'click',fieldLabel : '点击率',disabled:true,cls:'form-textfield-show',anchor : '99%'}]
					}
			 	]
			 },
			 {
			 	layout:'column',
			 	items:[
			 		{
						columnWidth: 1, layout: 'form',
						items: [{xtype : 'textfield',name : 'note',fieldLabel : '备注',disabled:true,cls:'form-textfield-show',anchor : '99%'}]
					}
			 	]
			 }
		 ]
	};
	
	Ext.apply(this, {
		labelAlign: 'right',
		region: 'center',
		labelWidth: 80,
		frame: true,
		items: [
			{
				layout: 'column',
				items: [
					{
						columnWidth: 1, layout: 'form',
						items: [
							{xtype : 'textfield',name : 'contractNum',fieldLabel : '合同号',allowBlank:false,disabled:true,cls:'form-textfield-show',anchor : '99%'}
						]
					}
				]
			},
			{
				layout: 'column',
				items: [
					{
						columnWidth: .33, layout: 'form',
						items: [
							{xtype : 'textfield',name : 'useTypeName',fieldLabel : '执行单类型',disabled:true,cls:'form-textfield-show',anchor : '99%'}
						]
					},
					{
						columnWidth: .34, layout: 'form',
						items: [
								{xtype : 'textfield',name : 'name',fieldLabel : '项目名称',allowBlank:false,disabled:true,cls:'form-textfield-show',anchor : '99%'}
						]
					},
					{
						columnWidth: .33, layout: 'form',
						items: [{xtype : 'textfield',name : 'consumerName',fieldLabel : '客户',disabled:true,cls:'form-textfield-show',anchor : '99%'}]
					}
				]
			},
			{
				layout: 'column',
				items: [
					{
						columnWidth: .33, layout: 'form',
						items: [{xtype : 'textfield',name : 'advertiserName',fieldLabel : '广告主名称',disabled:true,cls:'form-textfield-show',anchor : '99%'}]
					},
					{
						columnWidth: .34, layout: 'form',
						items: [{xtype : 'textfield',name : 'productLineName',fieldLabel : '产品线',disabled:true,cls:'form-textfield-show',anchor : '99%'}]
					},
					{
						columnWidth: .33, layout: 'form',
						items: [{xtype : 'textfield',name : 'productName',fieldLabel : '产品',disabled:true,cls:'form-textfield-show',anchor : '99%'}]
					}
				]
			},
			{
				layout: 'column',
				items: [
					{
						columnWidth: .33, layout: 'form',
						items: [
							{xtype : 'textfield',name : 'areaName',fieldLabel : '销售大区',disabled:true, cls:'form-textfield-show',anchor : '99%'}
						]
					},
					{
						columnWidth: .34, layout: 'form',
						items: [
							{xtype : 'textfield',name : 'ditchName',fieldLabel : '渠道销售',disabled:true, cls:'form-textfield-show',anchor : '99%'}
						]
					},
					{
						columnWidth: .33, layout: 'form',
						items: [
							{xtype : 'textfield',name : 'saleName',fieldLabel : '直客销售',disabled:true, cls:'form-textfield-show',anchor : '99%'}
						]
					}
				]
			},
			{
				layout: 'column',
				items: [
					{
						columnWidth: .33, layout: 'form',
						items: [{xtype : 'textfield',name : 'sum',fieldLabel : '金额',disabled:true, cls:'form-textfield-show',anchor : '99%'}]
					},
					{
						columnWidth: .34, layout: 'form',
						items: [{xtype : 'textfield',name : 'discount',fieldLabel : '折扣',disabled:true, cls:'form-textfield-show',anchor : '99%'}]
					},
					{
						columnWidth: .33, layout: 'form',
						items: [{xtype : 'textfield',name : 'sendRate',fieldLabel : '配送比例',disabled:true, cls:'form-textfield-show',anchor : '99%'}]
					}
				]
			},
			{
				layout: 'column',
				items: [
					{
						columnWidth: .33, layout: 'form',
						items: [
							new Ext.form.DateField({
								fieldLabel: '开始日期',name: 'startTime',disabled:true,format:'Y-m-d', cls:'form-textfield-show',anchor : '99%'
							})
						]
					},
					{
						columnWidth: .34, layout: 'form',
						items: [
							new Ext.form.DateField({
								fieldLabel: '结束日期',name: 'endTime',disabled:true,format:'Y-m-d', cls:'form-textfield-show',anchor : '99%'
							})
						]
					},
					{
						columnWidth: .33, layout: 'form',
						items: [{xtype : 'textfield',name : 'explains',fieldLabel : '形式需求',disabled:true, cls:'form-textfield-show',anchor:'99%',value: '40个'}]
					}
				]
			},
			{
				layout: 'column',
				items: [{columnWidth: 1, layout: 'form',items:[kpiGroup]}]
			}
		]
	});
	
	com.myads.book.BasicinfoFormPanel.superclass.constructor.apply(this, arguments);
}

Ext.extend(com.myads.book.BasicinfoFormPanel, Ext.FormPanel, {
	onRender: function() {
		com.myads.book.BasicinfoFormPanel.superclass.onRender.apply(this, arguments);
	},
	
	load: function(projectId) {
		this.form.load({url: '/myads/HTML/advflight/BookAction_getProjectInfo.action',params: {id: projectId}});
	}
});
