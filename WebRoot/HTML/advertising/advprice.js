Ext.onReady(function() {
	
	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget='side';
	
	var barId = Ext.get('barId').dom.value;//跨页面拿到广告条id
	var fm = Ext.form;
	
	var saleTypeCombo = new Ext.form.ComboBox({
		fieldLabel : '售卖方式',
		name:'saleType',
		allowBlank :false,
		mode: 'local',
		editable:false,
		width:130,
		triggerAction: 'all', 
		valueField: 'value',
		displayField: 'text',
		store: new Ext.data.SimpleStore({
			fields: ['value', 'text'],
			data: [['2','CPM'],['1','CPD']]
		}),
		listeners : {
            select : function(ComboBox, record, index) {
            	var type = record.data.text;
            	typeNameCombo.setRawValue("");
            	typeNameCombo.store.reload({params: {dataType : type}});
            }
        }
   });
	
	var typeNameStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({url : '/myads/HTML/basic/BaseDataAction_search.action'}),
		reader : new Ext.data.JsonReader(
			{
				totalProperty: 'total',
				idProperty:'id',
				root: 'invdata'
			},
			[
				{name: 'id'},
				{name: 'dataType'},
				{name: 'dataName'}
			]
		)
	});
	
	var typeNameCombo = new Ext.form.ComboBox({
		fieldLabel : '方式名称',
		name:'format',
		store : typeNameStore,
		allowBlank :false,
		mode: 'local',
		editable:false,
		width:130,
		triggerAction: 'all', 
		valueField: 'id',
		displayField: 'dataName'
   });
   
   var priceBtn;
   var edit_form = new Ext.form.FormPanel({
		labelAlign: 'center',
		region: 'north',
		labelWidth: 60,
		frame: true,
	    items:[
		    {
		    	items: [
			    	{
			    		layout: 'form',
			    		items: [{xtype : 'hidden',name : 'id',hidden:true, hiddenLabel:true}]
			    	}
		    	]
		    },
		    {
		    	layout: 'column',
				items: [
			        {	columnWidth: .5, layout: 'form',
		            	items: [
		            	        { xtype: 'textfield', name: 'pos', fieldLabel: '位置',anchor : '95%'}
		            	]
		            },
			        
		            {	columnWidth: .5, layout: 'form',
		            	items: [
		            	        { xtype: 'textfield', name: 'storage', fieldLabel: '名称 ',anchor : '95%'}
		            	]
		            }
				]
			},
			{
				layout: 'column',
				items: [
			        {	columnWidth: .5, layout: 'form',
		            	items: [
		            	        { xtype: 'textfield', name: 'impression', fieldLabel: '曝光',anchor : '95%'}
		            	]
		            },
			        
		            {	columnWidth: .5, layout: 'form',
		            	items: [
		            	        { xtype: 'textfield', name: 'clickRate', fieldLabel: '点击率 ',anchor : '95%'}
		            	]
		            }
				]
			},
			{
				layout: 'column',
				items: [
					{
						columnWidth: 1, layout: 'form',
						items: [{
							xtype : 'textarea',
							name : 'materiel',
							fieldLabel : '物料要求',
							height:80,
							anchor : '97%'
							}
						]
					}
				]
			}
	    ],

	   buttons:[
			new Ext.Button({
				text: '提交',
				width: 70,
				handler: function() {
					edit_form.form.doAction('submit', {
						url : '/myads/HTML/advert/AdvbarPriceAction_save.action',
						method : 'post',
						params : {
							barId:barId
						},
						success : function(form, action) {
							edit_form.getForm().findField("id").setValue(action.result.priceId);
							Ext.MessageBox.alert('结果', '提交成功！');
						}
				  });
				}
			}),
			priceBtn = new Ext.Button({
				text: '增加价格',
				width: 70,
				hidden:true,
				handler: function() {
					formWin.show();
				}
			})
		]
	});
	edit_form.render('edit_form-div');
	
	if(barId != null) {
		edit_form.load({
				url : '/myads/HTML/advert/AdvbarPriceAction_getAdvbarPriceDetail.action',
			params: {barId: barId},
			success:function(form,action){priceBtn.show();}
		});
	}
	
	var cm = new Ext.grid.ColumnModel([
	    {header:'售卖方式', dataIndex:'saleType',renderer: renderView, sortable:true, width:10},
	    {header:'方式名称', dataIndex:'format', sortable:true,width:10},	   
        {header:'价格', dataIndex:'price', sortable:true,width:10},
	    {header:'操作', dataIndex:'op', renderer: renderOp,width:10, align:'left'}
	]);
	
	function renderOp(value, cellmeta, record, rowIndex, columnIndex, stor) {
		var id = record.data.id;
		var dataValue = record.data.dataValue; 
		var updateStr = '<img alt=\"编辑\"  src=\"/myads/HTML/images/edit.gif\" style=\"cursor: pointer;\" onclick=\"update_price(\''+id+'\');\">';
		var delStr = '<img alt=\"删除\" src=\"/myads/HTML/images/del.gif\" style=\"cursor: pointer;\" onclick=\"delete_price(\''+id+'\');\">';
		return updateStr+'&nbsp;&nbsp;&nbsp;&nbsp;'+delStr;
	}
	
	function renderView(value, cellmeta, record, rowIndex, columnIndex, stor){
		var viewStr = "";
		var dataValue = record.data.saleType;
		if(dataValue=="1"){
			viewStr ="CPD";
		}else if(value=="2"){
			viewStr ="CPM";
		}else {
			viewStr ="未知";
		}
		return viewStr;
	}
	var ds = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({url: '/myads/HTML/advert/AdvbarPriceAction_showAll.action?barId='+barId}),
		remoteSort: false,
		reader: new Ext.data.JsonReader(
			{
				totalProperty: 'total',
				idProperty:'id',
				root: 'invdata'
			},
			[
				{name: 'id'},
				{name: 'saleType'},
				{name: 'price'},
				{name: 'format'},
				{name: 'modulus'}
			]
		)
	});

	ds.load({params: {start:0, limit:10}});
	
	var grid = new Ext.grid.EditorGridPanel({
		el: 'grid',
		region: 'center',
		//		autoExpandColumn: 'common', // column with this id will be expanded
		clicksToEdit: 1,
		ds: ds,
		cm: cm,
	    viewConfig: {
	    	forceFit: true
	    }
//	    ,
//	    bbar: new Ext.PagingToolbar({
//		    pageSize: 10,
//		    store: ds,
//			displayInfo: true,
//			displayMsg: '显示第{0}条到{1}条记录,一共{2}条',
//			emptyMsg: '没有记录',
//			items: []
//	    })
	});
	
	
	window.update_price = function(id) {
		new_form.load({
			url:'/myads/HTML/advert/AdvbarPriceAction_showAdvbarPriceById.action',
			params: {id: id},
			success: function(form,action){
				var type = saleTypeCombo.getRawValue();
            	typeNameStore.load({params: {dataType : type}});
			}
		});
		formWin.show();
	}
	
	window.delete_price = function(id) {
		Ext.MessageBox.confirm('提示', '确定删除该价格 ？', function(btn) {
			if (btn == 'yes') {
				var param = [];
				param.push(id);
				Ext.Ajax.request({
					method : 'post',
					url : '/myads/HTML/advert/AdvbarPriceAction_delete.action',
					success : function(resp) {
						var obj = Ext.util.JSON.decode(resp.responseText);
						if(obj.result == 'success'){
							Ext.MessageBox.alert('提示', '删除成功！');
							grid.getStore().reload();
						}
						if(obj.result == 'error'){
							grid.getStore().reload();
							Ext.MessageBox.alert('提示','操作失败，请联系系统管理员！');
						}
					},
					params : {
						priceList : param.join(',')
					}
				});
			}
		});
	};
	
	new Ext.Viewport({
		layout: 'border',
		items:[
			edit_form,grid
		]
	});
	
	var new_form = new Ext.form.FormPanel({
		labelAlign: 'center',
		region: 'center',
		labelWidth: 60,
		frame: true,
		xtype: 'fieldset',
	   	items: [
				{
					layout: 'form',
					items: [
							{xtype : 'hidden',name : 'barId',hidden:true},
							{xtype : 'hidden',name : 'id',hidden:true}
						   ]
				},{
					columnWidth: .30, layout: 'form',
					items: [saleTypeCombo]
				},{
					columnWidth: .20, layout: 'form',
					items: [typeNameCombo]
				},{
					columnWidth: .40, layout: 'form',
					items: [{
						xtype : 'textfield',
						name : 'price',
						allowBlank :false,
						fieldLabel : '价格',
						blankText : "价格不是合法数字（正数，最多两位小数）！",
						regex : /(^[-+]?[1-9]\d*(\.\d{1,2})?$)|(^[-+]?[0]{1}(\.\d{1,2})?$)/,
						regexText : "价格不是合法数字（正数，最多两位小数）！"
				}]}],
				buttons:[{
					layout : 'form',
					xtype : 'button',
					text : '提交',
					anchor : '10%',
					handler : function() {
						new_form.form.doAction('submit', {
							url : '/myads/HTML/advert/AdvbarPriceAction_savePrice.action',
							method : 'post',
							params : {barId:barId},
							success : function(form, action) {
								if(action.result.result == 'success'){
									Ext.MessageBox.alert('结果', '提交成功！');
									formWin.hide();
									form.reset();
									grid.getStore().reload();
								}else if(action.result.result == 'exists'){
									Ext.MessageBox.alert('结果', '该售卖方式下已经存在相同的方式名称！');
									formWin.hide();
									form.reset();
									grid.getStore().reload();
								}else{
									Ext.MessageBox.alert('结果', '提交失败！');
									formWin.hide();
									form.reset();
									grid.getStore().reload();
								}
							}
						});					
				    }
				}
			]
	});
	
	var formWin = new Ext.Window({
		title: '价格',
        layout:'border',
        width:350,
    	height:190,
        closeAction:'hide',
        plain: true,
        items:[new_form]
    });
});