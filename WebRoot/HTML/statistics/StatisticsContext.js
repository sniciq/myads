Ext.onReady(function() {
	
	var channelComboStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({url: '/myads/HTML/statistics/StatisticsAAContextAction_searchAllChannel.action'}),
		reader: new Ext.data.ArrayReader({}, [
			{name: 'value'},{name: 'text'}
		])
	});
	
	channelComboStore.load();
	
	var searchForm = new Ext.form.FormPanel({
		labelAlign: 'right',
		region: 'north',
		labelWidth: 70,
		frame: true,
		items: [
			{//行1
				layout: 'column',
				items: [
					{
						columnWidth: .33, layout: 'form',
						items: [
							new Ext.form.DateField({
								fieldLabel: '开始日期',
								id: 'startTime',
								name: 'startTime',
								allowBlank:true,
								editable : false,
								format:'Y-m-d',
								anchor : '95%',
								listeners : {
									select : function(df) {
										Ext.getCmp('endTime').setMinValue(df.getValue());
										var aa = df.getValue().add('d', 4);
										Ext.getCmp('endTime').setMaxValue(aa);
									}
								}
							})
						]
					},
					{
						columnWidth: .33, layout: 'form',
						items: [
							new Ext.form.DateField({
								id: 'endTime',
								fieldLabel: '结束日期',
								name: 'endTime',
								allowBlank:true,
								editable : false,
								format:'Y-m-d',
								anchor : '95%'
							})
						]
					},
					{
						columnWidth: .33, layout: 'form',
						items:[
					       new Ext.form.ComboBox({
	            	       		fieldLabel: '频道',
								hiddenName: 'channelSourceId',
								allowBlank : true,
								blankText: '此项不能为空!',
								editable : false,
								mode: 'local',
								triggerAction: 'all', 
								valueField: 'value',
								displayField: 'text',
								anchor : '95%',
								store: channelComboStore
	            	       })
						]
					}
				]
			}
		],
		buttons: [
			{
				text: '查询',
				handler: function() {
					doSearch(searchForm.getForm().getValues());
				}
			}
		]
	});
	
	searchForm.render('searchForm');
	
	var gridTab = new Ext.TabPanel({
		region:'center',
		frame:true,
        activeTab: 0,
        layoutOnTabChange : true ,
        defaults:{autoScroll: true},
        deferredRender :false,
		items: [
		]
	});
	
	var vp = new Ext.Viewport({
		layout: 'border',
		items:[
		       searchForm, gridTab
		]
	});
	
	function doSearch(formValues) {
		Ext.MessageBox.show({  
	        title:'请等待',  
	        msg:'读取数据中……',  
	        width:240,  
	        progress:false,  
	        closable:false  
	    });  

		
		Ext.Ajax.request({
			method: 'post',
			url: '/myads/HTML/statistics/StatisticsAAContextAction_search.action',
			params:formValues,
		   	success:function(resp){
		   		var jsonData = Ext.util.JSON.decode(resp.responseText);
		   		gridTab.remove("数据表");
		   		
		   		if(jsonData.size > 0) {
		   			var grid = createGroupGrid(jsonData);
					gridTab.add({
						id: '数据表',
						title: '数据表',
						layout: 'fit',
						items:[grid]
					});
					gridTab.setActiveTab("数据表");
		   		}
		   		Ext.MessageBox.hide();
		   	}
		});
	}
	
	function createGroupGrid(jsonData) {
		var groupRows = [];
		groupRows[0] = jsonData.topGroup;
		groupRows[1] = jsonData.secondGroup;
		
		var group = new Ext.ux.grid.ColumnHeaderGroup({
		    rows: groupRows
		});
		
		var grid = new Ext.grid.GridPanel({
			region: 'center',
			split: true,
			border:false,
			store: new Ext.data.JsonStore({
			    fields: jsonData.fields,
			    data: jsonData.data
			}),
			columns: jsonData.columns,
			plugins: group
		});
		
		return grid;
	}
	
});