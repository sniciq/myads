Ext.onReady(function() {
	Ext.QuickTips.init();
	
	var projectId;
	var projectName;
	
	Ext.form.Field.prototype.msgTarget='side';
	
	// 客户列表
	var consumerCombStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({url: '/myads/HTML/advflight/ProjectAction_getConsumerList.action'}),
		reader: new Ext.data.ArrayReader({}, [
			{name: 'value'},{name: 'text'}
		]),
		listeners:{  
			load : function(store, records, options ){  
				var data = {'value':'','text':''};
				var rs = [new Ext.data.Record(data)];  
				store.insert(0,rs);
			}  
		}
	});
	consumerCombStore.load();
	
	// 广告主列表
	var advertiserCombStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({url: '/myads/HTML/advflight/ProjectAction_getAdvertiserList.action'}),
		reader: new Ext.data.ArrayReader({}, [
			{name: 'value'},{name: 'text'}
		]),
		listeners:{  
			load : function(store, records, options ){  
				var data = {'value':'','text':''};
				var rs = [new Ext.data.Record(data)];  
				store.insert(0,rs);
			}  
		}
	});
	advertiserCombStore.load();
	
	// 广告主产品线
	var productLineCombStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({url: '/myads/HTML/sysfun/ProductLineAction_getProductLineById.action'}),
		reader: new Ext.data.ArrayReader({}, [
			{name: 'value'},{name: 'text'}
		])
	});
	
	// 产品
	var productCombStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({url: '/myads/HTML/sysfun/ProductAction_getProductByLineId.action'}),
		reader: new Ext.data.ArrayReader({}, [
			{name: 'value'},{name: 'text'}
		])
	});
	
	// 销售大区
	var areaCombStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({url: '/myads/HTML/sysfun/AreaAction_getAreaList.action'}),
		reader: new Ext.data.ArrayReader({}, [
			{name: 'value'},{name: 'text'}
		])
	});
	areaCombStore.load();
	
	// 渠道销售
	var ditchCombStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({url: '/myads/HTML/sysfun/RoleAction_getUserInfoList.action'}),
		reader: new Ext.data.ArrayReader({}, [
			{name: 'value'},{name: 'text'}
		]),
		listeners:{  
			load : function(store, records, options ){  
				var data = {'value':'','text':''};
				var rs = [new Ext.data.Record(data)];  
				store.insert(0,rs);
			}  
		}
	});
	ditchCombStore.load({params: {roleName : encodeURI('渠道销售')}});
	
	// 直客销售
	var saleCombStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({url: '/myads/HTML/sysfun/RoleAction_getUserInfoList.action'}),
		reader: new Ext.data.ArrayReader({}, [
			{name: 'value'},{name: 'text'}
		]),
		listeners:{  
			load : function(store, records, options ){  
				var data = {'value':'','text':''};
				var rs = [new Ext.data.Record(data)];  
				store.insert(0,rs);
			}  
		}
	});
	saleCombStore.load({params: {roleName : encodeURI('直客销售')}});
	
	// 执行单类型
	var projectTypeStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({url: '/myads/HTML/basic/OptionsAction_getBaseDataNamesByType.action?dataType=usetype'}),
		reader: new Ext.data.ArrayReader({}, [
			{name: 'value'},{name: 'text'}
		]),
		listeners:{  
			load : function(store, records, options ){  
				var data = {'value':'','text':''};
				var rs = [new Ext.data.Record(data)];  
				store.insert(0,rs);
			}  
		}
	});
	projectTypeStore.load();
	
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
						items: [{
							disabled : true,
							xtype : 'textfield',
							name : 'impression',
							fieldLabel : '曝光',
							anchor : '90%',
							regex : /^\d*$/,
							regexText : "只能输入数字!"
						}]
					},
					{
						columnWidth: .5, layout: 'form',
						items: [{
							disabled : true,
							xtype : 'numberfield',
							name : 'click',
							fieldLabel : '点击率',
							anchor : '90%',
							allowDecimals : true,
							decimalPrecision : 2,
							nanText :'请输入有效的小数',
							allowNegative : false
						}]
					}
			 	]
			 },
			 {
			 	layout:'column',
			 	items:[
			 		{
						columnWidth: 1, layout: 'form',
						items: [{
							disabled : true,
							xtype : 'textfield',
							name : 'note',
							fieldLabel : '备注',
							anchor : '99%'
						}]
					}
			 	]
			 }
		 ]
	};
	
			
	//排期GRID----------------------------------------开始------------------------
	var cm = new Ext.grid.ColumnModel([
		{header:'序号', dataIndex:'id', sortable:true},
		{header:'频道', dataIndex:'channelName', sortable:true},
		{header:'广告条', dataIndex:'advbarName', sortable:true},
		{header:'使用类型', dataIndex:'useTypeName', sortable:true},
	    {header:'售卖方式', dataIndex:'saleTypeName', sortable:true},
	    {header:'刊例价', dataIndex:'price', sortable:true},
	    {header:'折扣', dataIndex:'discount', sortable:true},
	    {header:'定向策略', dataIndex:'dataName', renderer: renderOp1, sortable:true},
	    {header:'内容策略', dataIndex:'dataValue', renderer: renderOp2, sortable:true},
	    {header:'投放时期', dataIndex:'startTime', sortable:true, 
	    	renderer: function(value, metadata, record, rowIndex, colIndex, store) {
            	return record.get("startTime") + "---" + record.get("endTime");
        	}
	    }
	]);
	
	// 定向策略
	function renderOp1(value, cellmeta, record, rowIndex, columnIndex, stor) {
		var title = '定向策略:';
		
		var frequencyStr = '';
		if(record.get('isFrequency') == 1) {//频次定向
			var frequencyType = record.get('frequencyTypeName');
			frequencyStr = frequencyType + ', ' + record.get('frequencyNum');
		}
			
		var areaDirectStr = '';
		var areaDirectObj = Ext.util.JSON.decode(record.get('areaDirect'));
		for(var j = 0; j < areaDirectObj.length; j++) {
			areaDirectStr += areaDirectObj[j].display + '; ';
		}
		
		var tip = '区域定向：' +  areaDirectStr + '<br>' 
			+ '时间定向：' +  record.get('hourDirect') + '<br>' 
			+ '频次定向: ' + frequencyStr;    
		cellmeta.attr = 'ext:qtitle="' + title + '"' + ' ext:qtip="' + tip + '"';    
		return '<a style="color: #44CC44;cursor: pointer;">详细</a>'; 
	}
	
	// 内容策略
	function renderOp2(value, cellmeta, record, rowIndex, columnIndex, stor) {
		if(record.get('isContentDirect') != 1)
			return '';
		var title = '内容策略';    
		var tip = '关键字：' +  record.get('keyword') + '<br>' 
				+ '用户：' +  record.get('user') + '<br>'
				+ '视频id：' +  record.get('video') + '<br>'
				+ '节目：' +  record.get('program') + '<br>'
				+ '活动：' +  record.get('activity') + '<br>'
				+ '专题：' +  record.get('subject') + '<br>';
		cellmeta.attr = 'ext:qtitle="' + title + '"' + ' ext:qtip="' + tip + '"';    
		return '<a style="color: #44CC44;cursor: pointer;">详细</a>'; 
	}
	
	var bookPackage_ds = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({url: '/myads/HTML/advflight/BookAction_showAllBookPackage.action'}),
		remoteSort: false,
		reader: new Ext.data.JsonReader(
			{
				totalProperty: 'total',
				idProperty:'id',
				root: 'invdata'
			},
			[
				{name: 'id'},
				{name: 'channelName'},
				{name: 'advbarName'},
				{name: 'useType'},
				{name: 'useTypeName'},
				{name: 'saleType'},
				{name: 'saleTypeName'},
				{name: 'price'},
				{name: 'discount'},
				{name: 'startTime'},
				{name: 'endTime'},
				{name: 'areaDirect'},
				{name: 'hourDirect'},
				{name: 'isContentDirect'},
				{name: 'isFrequency'},
				{name: 'frequencyType'},
				{name: 'frequencyTypeName'},
				{name: 'frequencyNum'},
				{name: 'keyword'},
				{name: 'user'},
				{name: 'video'},
				{name: 'program'},
				{name: 'activity'},
				{name: 'subject'}
			]
		)
	});
	
	var bookPackage_grid = new Ext.grid.GridPanel({
		el: 'bookPackage_grid',
		ds: bookPackage_ds,
		cm: cm,
	    viewConfig: {
	    	forceFit: true
	    },
	    bbar: new Ext.PagingToolbar({
		    pageSize: 20,
		    store: bookPackage_ds,
			displayInfo: true,
			displayMsg: '显示第{0}条到{1}条记录,一共{2}条',
			emptyMsg: '没有记录'
	    })
	});
	
	//排期信息tab
	var gridTab = new Ext.TabPanel({
		region:'center',
		frame:true,
		autoHeight : false,
		height:350,
        activeTab: 0,
        split: true,
        layoutOnTabChange : true ,
        defaults:{autoScroll: true},
        deferredRender :false,
		items: [
			{
				title: '排期信息',
				layout: 'fit',
				items:[bookPackage_grid]
			}
		]
	});
	
	//排期GRID----------------------------------------结束------------------------
	
	
	
	//生成点位预览--------------------------开始-------------
	var test_grid = null; 
	function refrshPreviewGridTab() {
		Ext.Ajax.request({
			method: 'post',
			url: '/myads/HTML/advflight/PointGridPreviewAction_getPointGrid.action',
			params:{projectId:projectId},
		   	success:function(resp){
		   		var jsonData = Ext.util.JSON.decode(resp.responseText);
		   		test_grid = createGrid(jsonData);
		   		gridTab.remove("点位预览");
				gridTab.add({
					id: '点位预览',
					title: '点位预览',
					layout: 'fit',
					items:[test_grid]
				});
		   	}
		});
	}
	
	function createGrid(jsonData) {
		var cm = new Ext.grid.ColumnModel(jsonData.columModle);
        var ds = new Ext.data.JsonStore({
	        data:jsonData.data,
	        fields:jsonData.fieldsNames
        });
                                   
        var grid = new Ext.grid.GridPanel({
	        region: 'center',
	        split: true,
	        border:false,
	        cm:cm,
	        ds:ds
        });
        return grid;
	}
	
	//生成点位预览--------------------------结束-------------
	
	var edit_form = new Ext.form.FormPanel({
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
							{xtype : 'textfield',name : 'contractNum',fieldLabel : '合同号',allowBlank:false,disabled:true,anchor : '99%'}
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
							{xtype : 'textfield',name : 'useTypeName',fieldLabel : '执行单类型',disabled:true,anchor : '99%'}
						]
					},
					{
						columnWidth: .34, layout: 'form',
						items: [
								{xtype : 'textfield',name : 'name',fieldLabel : '项目名称',allowBlank:false,disabled:true,anchor : '99%'}
						]
					},
					{
						columnWidth: .33, layout: 'form',
						items: [{xtype : 'textfield',name : 'consumerName',fieldLabel : '客户',disabled:true,anchor : '99%'}]
					}
				]
			},
			{
				layout: 'column',
				items: [
					{
						columnWidth: .33, layout: 'form',
						items: [{xtype : 'textfield',name : 'advertiserName',fieldLabel : '广告主名称',disabled:true,anchor : '99%'}]
					},
					{
						columnWidth: .34, layout: 'form',
						items: [{xtype : 'textfield',name : 'productLineName',fieldLabel : '产品线',disabled:true,anchor : '99%'}]
					},
					{
						columnWidth: .33, layout: 'form',
						items: [{xtype : 'textfield',name : 'productName',fieldLabel : '产品',disabled:true,anchor : '99%'}]
					}
				]
			},
			{
				layout: 'column',
				items: [
					{
						columnWidth: .33, layout: 'form',
						items: [
							{xtype : 'textfield',name : 'area',fieldLabel : '销售大区',disabled:true,anchor : '99%'}
						]
					},
					{
						columnWidth: .34, layout: 'form',
						items: [
							{xtype : 'textfield',name : 'ditchName',fieldLabel : '渠道销售',disabled:true,anchor : '99%'}
						]
					},
					{
						columnWidth: .33, layout: 'form',
						items: [
							{xtype : 'textfield',name : 'saleName',fieldLabel : '直客销售',disabled:true,anchor : '99%'}
						]
					}
				]
			},
			{
				layout: 'column',
				items: [
					{
						columnWidth: .33, layout: 'form',
						items: [{xtype : 'textfield',name : 'sum',fieldLabel : '金额',disabled:true,anchor : '99%'}]
					},
					{
						columnWidth: .34, layout: 'form',
						items: [{xtype : 'textfield',name : 'discount',fieldLabel : '折扣',disabled:true,anchor : '99%'}]
					},
					{
						columnWidth: .33, layout: 'form',
						items: [{xtype : 'textfield',name : 'sendRate',fieldLabel : '配送比例',disabled:true,anchor : '99%'}]
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
								fieldLabel: '开始日期',name: 'startTime',disabled:true,format:'Y-m-d',anchor : '99%'
							})
						]
					},
					{
						columnWidth: .34, layout: 'form',
						items: [
							new Ext.form.DateField({
								fieldLabel: '结束日期',name: 'endTime',disabled:true,format:'Y-m-d',anchor : '99%'
							})
						]
					},
					{
						columnWidth: .33, layout: 'form',
						items: [{xtype : 'textfield',name : 'explains',fieldLabel : '形式需求',disabled:true,anchor : '99%'}]
					}
				]
			},
			{
				layout: 'column',
				items: [
					{
						columnWidth: 1, layout: 'form',items:[kpiGroup]
					}
				]
			},
			{
				layout: 'column',
				items: [
					{
						columnWidth: 1, layout: 'fit',items:[gridTab]
					}
				]
			}
		]
	});
	
	
	var edit_win = new Ext.Window({
		title: '执行单',
        applyTo:'edit_win_div',
        width:900,
    	height:450,
        closeAction:'hide',
        plain: true,
        layout: 'border',
        items: [edit_form],
        defaults:{autoScroll: true}
    });
	
	var searchForm = new Ext.FormPanel({
		frame: true,
		region: 'north',
		labelAlign: 'right',
		items: [
			{xtype : 'hidden',name : 'type',hidden : true,hiddenLabel : true},
			{
				layout: 'column',
				items: [
					{
						columnWidth: .25, layout: 'form',
							items: [
								new Ext.form.ComboBox({
			        	       		fieldLabel: '执行单类型',
									hiddenName: 'type',
									mode: 'local',
									editable : false,
									triggerAction: 'all', 
									valueField: 'value',
									displayField: 'text',
									anchor : '99%',
									tpl:'<tpl for=".">' +  
										    '<div class="x-combo-list-item" >' +  
										            '{text}&nbsp;' +  
										    '</div>'+  
									    '</tpl>',
									store: projectTypeStore
			        	       })
							]
					},
					{
						columnWidth: .25, layout: 'form',
						items: [
							{
								xtype : 'textfield',
								name : 'name',
								fieldLabel : '项目名称',
								anchor : '99%'
							}
						]
					},
					{
						columnWidth: .25, layout: 'form',
						items: [
							new Ext.form.ComboBox({
		        	       		fieldLabel: '广告主名称',
								hiddenName: 'advertiserId',
								mode: 'local',
								editable : false,
								triggerAction: 'all', 
								valueField: 'value',
								displayField: 'text',
								anchor : '99%',
								tpl:'<tpl for=".">' +  
									    '<div class="x-combo-list-item" >' +  
									            '{text}&nbsp;' +  
									    '</div>'+  
								    '</tpl>',
								store: advertiserCombStore
		        	       })
						]
					},
					{
						columnWidth: .25, layout: 'form',
						items: [
							new Ext.form.ComboBox({
		        	       		fieldLabel: '客户',
								hiddenName: 'consumerId',
								mode: 'local',
								editable : false,
								triggerAction: 'all', 
								valueField: 'value',
								displayField: 'text',
								anchor : '99%',
								tpl:'<tpl for=".">' +  
									    '<div class="x-combo-list-item" >' +  
									            '{text}&nbsp;' +  
									    '</div>'+  
								    '</tpl>',
								store: consumerCombStore
		        	       })
						]
					}
				]
			},
			{
				layout: 'column',
				items: [
					{
						columnWidth: .25, layout: 'form',
						items: [
							new Ext.form.ComboBox({
		        	       		fieldLabel: '渠道销售',
								hiddenName: 'ditchId',
								mode: 'local',
								editable : false,
								triggerAction: 'all', 
								valueField: 'value',
								displayField: 'text',
								anchor : '99%',
								tpl:'<tpl for=".">' +  
									    '<div class="x-combo-list-item" >' +  
									            '{text}&nbsp;' +  
									    '</div>'+  
								    '</tpl>',
								store: ditchCombStore
		        	       })
						]
					},
					{
						columnWidth: .25, layout: 'form',
						items: [
							new Ext.form.ComboBox({
		        	       		fieldLabel: '直客销售',
								hiddenName: 'saleId',
								mode: 'local',
								editable : false,
								triggerAction: 'all', 
								valueField: 'value',
								displayField: 'text',
								anchor : '99%',
								tpl:'<tpl for=".">' +  
									    '<div class="x-combo-list-item" >' +  
									            '{text}&nbsp;' +  
									    '</div>'+  
								    '</tpl>',
								store: saleCombStore
		        	       })
						]
					},
					{
						columnWidth: .25, layout: 'form',
						items: [{
							xtype : 'textfield',
							name : 'contractNum',
							fieldLabel : '合同号',
							anchor : '99%'
						}]
					}
				]
			}
		],
		buttons: [
			{
				text : '查询',
				width : 70,
				handler : function() {
					var fv = searchForm.getForm().getValues();
					ds.baseParams = fv;
					ds.load({params : {start : 0,limit : 20}});
					edit_win.hide();
				}
			},
			{
				text: '重置',
				width : 70,
				handler: function() {
					searchForm.form.reset();
				}
			}
		]
	});
	searchForm.render('north-div');
	
	
	//判断执行单类型
	function rendType(value){
		if(value==1){
			return "售卖";
		}else if(value==2){
			return "配送";
		}else if(value==3){
			return "补量";
		}else if(value==4){
			return "测试";
		}else if(value==5){
			return "推广";
		}else if(value==6){
			return "软性";
		}else if(value==7){
			return "电商";
		}else if(value==8){
			return "置换";
		}else if(value==9){
			return "游戏";
		}else if(value==10){
			return "内部使用";
		}
	}
	
	var cmo = new Ext.grid.ColumnModel([
		{header:'ID', dataIndex:'id', sortable:true},
		{header:'项目名称', dataIndex:'name', sortable:true},
		{header:'开始时间', dataIndex:'startTime', sortable:true},
		{header:'结束时间', dataIndex:'endTime', sortable:true},
	    {header:'执行单类型', dataIndex:'type', sortable:true, renderer:rendType},
	    {header:'广告主', dataIndex:'advertiserName', sortable:true},
	    {header:'直客销售', dataIndex:'saleName', sortable:true},
	    {header:'渠道销售', dataIndex:'ditchName', sortable:true},
	    {header:'创建人', dataIndex:'creator', sortable:true},
	    {header:'创建时间', dataIndex:'createTime', sortable:true},
	    {header:'操作', dataIndex:'op', renderer: renderOp,width:150, align:'left'}
	]);
	
	function renderOp(value, cellmeta, record, rowIndex, columnIndex, stor) {
		var id = record.data.id;
		var name = record.data.name; 
		projectName = name;
		var audit = '<a href="#" onclick=\"verify(\''+id+'\');\">查看</a>';
		var updateStr = '<a href="#" onclick=\"update_breif(\''+id+'\');\">编辑</a>';
		var advflight = '<a href="/myads/HTML/book/book.jsp?projectId='+id+'">排期</a>';
		var pointPreview = '<a href="#" onclick=\"showPointPreview(\''+id+'\');\">预览</a>';
		return audit+ '&nbsp;&nbsp;&nbsp;&nbsp;' + updateStr + '&nbsp;&nbsp;&nbsp;&nbsp;' + advflight + '&nbsp;&nbsp;&nbsp;&nbsp;' + pointPreview;
	}
	
	window.showPointPreview = function(projectId) {
		window.open('advflight_point_preview.jsp?projectId=' + projectId);
	}
	
	var edit_form1 = new Ext.form.FormPanel({
		labelAlign: 'right',
		region: 'center',
		labelWidth: 70,
		frame: true,
		items: [
			{
				items: [
					{	
						columnWidth: .01, layout: 'form',
			        	items: [
			        	        { xtype: 'hidden', name: 'id', hidden:true, hiddenLabel:true},
			        	        { xtype: 'hidden', name: 'consumerName', hidden:true, hiddenLabel:true},
			        	        { xtype: 'hidden', name: 'advertiserName', hidden:true, hiddenLabel:true},
			        	        { xtype: 'hidden', name: 'productLineName', hidden:true, hiddenLabel:true},
			        	        { xtype: 'hidden', name: 'productName', hidden:true, hiddenLabel:true},
			        	        { xtype: 'hidden', name: 'ditchName', hidden:true, hiddenLabel:true},
			        	        { xtype: 'hidden', name: 'saleName', hidden:true, hiddenLabel:true}
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
							xtype : 'textfield',
							name : 'contractNum',
							fieldLabel : '合同号',
							regex : /^\d*$/,
							regexText : "只能输入数字!",
							anchor : '95%'
						}]
					}
				]
			},
			{
				layout: 'column',
				items: [
					{
						columnWidth: .33, layout: 'form',
						items: [
							new Ext.form.ComboBox({
		        	       		fieldLabel: '执行单类型',
								hiddenName: 'type',
								mode: 'local',
								triggerAction: 'all', 
								valueField: 'value',
								editable : false,
								allowBlank:false,
								blankText: '此项不能为空!',
								displayField: 'text',
								anchor : '90%',
								tpl:'<tpl for=".">' +  
									    '<div class="x-combo-list-item" >' +  
									            '{text}&nbsp;' +  
									    '</div>'+  
								    '</tpl>',
								store: projectTypeStore
		        	       })
						]
					},
					{
						columnWidth: .34, layout: 'form',
						items: [
							{
								xtype : 'textfield',
								name : 'name',
								fieldLabel : '项目名称',
								allowBlank:false,
								blankText: '此项不能为空!',
								anchor : '90%'
							}
						]
					},
					{
						columnWidth: .33, layout: 'form',
						items: [
							new Ext.form.ComboBox({
		        	       		fieldLabel: '客户',
		        	       		editable : false,
								allowBlank:false,
								blankText: '此项不能为空!',
								hiddenName: 'consumerId',
								mode: 'local',
								triggerAction: 'all', 
								valueField: 'value',
								displayField: 'text',
								anchor : '90%',
								tpl:'<tpl for=".">' +  
									    '<div class="x-combo-list-item" >' +  
									            '{text}&nbsp;' +  
									    '</div>'+  
								    '</tpl>',
								store: consumerCombStore,
								listeners : {
									select : function(postemCombo, record, index) {
										var consumerName = record.data.text;
										edit_form1.getForm().findField("consumerName").setValue(consumerName);
									}
								}
		        	       })
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
							new Ext.form.ComboBox({
		        	       		fieldLabel: '广告主名称',
								hiddenName: 'advertiserId',
								editable : false,
								allowBlank:false,
								blankText: '此项不能为空!',
								mode: 'local',
								triggerAction: 'all', 
								valueField: 'value',
								displayField: 'text',
								anchor : '90%',
								tpl:'<tpl for=".">' +  
									    '<div class="x-combo-list-item" >' +  
									            '{text}&nbsp;' +  
									    '</div>'+  
								    '</tpl>',
								store: advertiserCombStore,
								listeners : {
					                select : function(ComboBox, record, index) {
					                	edit_form1.getForm().findField("productLineId").clearValue();
					                	var id = record.data.value;
					                	productLineCombStore.load({params: {id : id}});
					                	
					                	var advertiserName = record.data.text;
										edit_form1.getForm().findField("advertiserName").setValue(advertiserName);
					                }
					            }
		        	       })
						]
					},
					{
						columnWidth: .34, layout: 'form',
						items: [
							new Ext.form.ComboBox({
		        	       		fieldLabel: '产品线',
		        	       		editable : false,
								allowBlank:false,
								blankText: '此项不能为空!',
								hiddenName: 'productLineId',
								mode: 'local',
								triggerAction: 'all', 
								valueField: 'value',
								displayField: 'text',
								anchor : '90%',
								store: productLineCombStore,
								listeners : {
					                select : function(ComboBox, record, index) {
					                	edit_form1.getForm().findField("productId").clearValue();
					                	var id = record.data.value;
					                	productCombStore.load({params: {id : id}});
					                	
					                	var productLineName = record.data.text;
										edit_form1.getForm().findField("productLineName").setValue(productLineName);
					                }
					            }
		        	       })
						]
					},
					{
						columnWidth: .33, layout: 'form',
						items: [
							new Ext.form.ComboBox({
		        	       		fieldLabel: '产品',
		        	       		editable : false,
								allowBlank:false,
								blankText: '此项不能为空!',
								hiddenName: 'productId',
								mode: 'local',
								triggerAction: 'all', 
								valueField: 'value',
								displayField: 'text',
								anchor : '90%',
								store: productCombStore,
								listeners : {
								select : function(postemCombo, record, index) {
									var productName = record.data.text;
									edit_form1.getForm().findField("productName").setValue(productName);
								}
							}
		        	       })
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
							new Ext.form.ComboBox({
		        	       		fieldLabel: '销售大区',
								hiddenName: 'area',
								mode: 'local',
								triggerAction: 'all', 
								valueField: 'value',
								displayField: 'text',
								anchor : '90%',
								editable : false,
								allowBlank:false,
								blankText: '此项不能为空!',
								store: areaCombStore
		        	       })
						]
					},
					{
						columnWidth: .34, layout: 'form',
						items: [
							new Ext.form.ComboBox({
		        	       		fieldLabel: '渠道销售',
								hiddenName: 'ditchId',
								id : 'dId',
								mode: 'local',
								triggerAction: 'all', 
								valueField: 'value',
								displayField: 'text',
								anchor : '90%',
								editable : false,
								tpl:'<tpl for=".">' +  
									    '<div class="x-combo-list-item" >' +  
									            '{text}&nbsp;' +  
									    '</div>'+  
								    '</tpl>',
								store: ditchCombStore,
								listeners : {
									select : function(postemCombo, record, index) {
										var ditchName = record.data.text;
										edit_form1.getForm().findField("ditchName").setValue(ditchName);
									}
								}
		        	       })
						]
					},
					{
						columnWidth: .33, layout: 'form',
						items: [
							new Ext.form.ComboBox({
		        	       		fieldLabel: '直客销售',
		        	       		editable : false,
								hiddenName: 'saleId',
								id : 'sId',
								mode: 'local',
								triggerAction: 'all', 
								valueField: 'value',
								displayField: 'text',
								anchor : '90%',
								tpl:'<tpl for=".">' +  
									    '<div class="x-combo-list-item" >' +  
									            '{text}&nbsp;' +  
									    '</div>'+  
								    '</tpl>',
								store: saleCombStore,
								listeners : {
									select : function(postemCombo, record, index) {
										var saleName = record.data.text;
										edit_form1.getForm().findField("saleName").setValue(saleName);
									}
								}
		        	       })
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
							{
								xtype : 'numberfield',
								name : 'sum',
								fieldLabel : '金额',
								allowBlank:false,
								blankText: '此项不能为空!',
								anchor : '90%'
							}
						]
					},
					{
						columnWidth: .34, layout: 'form',
						items: [
							{
								xtype : 'numberfield',
								name : 'discount',
								fieldLabel : '折扣',
								anchor : '90%'
							}
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
							{
								xtype : 'numberfield',
								name : 'sendRate',
								fieldLabel : '配送比例',
								anchor : '90%'
							}
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
							new Ext.form.DateField({
								id:'dt1',
								fieldLabel: '开始日期',
								name: 'startTime',
								editable : false,
								allowBlank:false,
								blankText: '此项不能为空!',
								format:'Y-m-d',
								value : new Date(),
								anchor : '90%',
								listeners : {
									beforerender : function() {
										Ext.getCmp('dt1').setMinValue(Ext.getCmp('dt1').getValue());
									},
									select : function() {
										Ext.getCmp('dt2').setMinValue(Ext.getCmp('dt1').getValue());
									}
								}
							})
						]
					},
					{
						columnWidth: .33, layout: 'form',
						items: [
							new Ext.form.DateField({
								id:'dt2',
								fieldLabel: '结束日期',
								name: 'endTime',
								editable : false,
								allowBlank:false,
								blankText: '此项不能为空!',
								format:'Y-m-d',
								anchor : '90%'
							})
						]
					}
				]
			},
			{
				layout: 'column',
				items: [
					{
						columnWidth: 1, layout: 'form',
						items: [
							{
								xtype : 'textfield',
								name : 'explains',
								fieldLabel : '广告形式需求',
								anchor : '60%'
							}
						]
					}
				]
			},
			{
				layout: 'column',
				items: [
					{
						columnWidth: 1, layout: 'form',items:[kpiGroup]
					}
				]
			}
		],
		buttons: [
			{
				text: '保存',
				handler: function() {
					var ditchId = Ext.get('dId').dom.value;
					var saleId = Ext.get('sId').dom.value;
					
					if (ditchId == "" && saleId == "") {
						Ext.MessageBox.alert('提示', '渠道销售和直客销售请至少选择一项!');
					} else {
						edit_form1.form.doAction('submit', {
							url: '/myads/HTML/advflight/ProjectAction_save.action',
							method: 'post',
							params: '',
							success: function(form, action) {
								Ext.MessageBox.alert('结果', '保存成功！');
								form.reset();
								grid.getStore().reload();
								edit_win1.hide();
							}
						});
					}
				}
			},
			{
				text: '重置',
				handler: function() {
					edit_form1.form.reset();
				}
			}
		]
	});
	
	window.update_breif = function(id) {
		edit_form1.load({
			url: '/myads/HTML/advflight/ProjectAction_getProjectDetail.action',
			params: {id: id},
			success:function(form,action){
				productLineCombStore.addListener('load', function() {
					var value = edit_form1.getForm().findField("productLineId").getValue();
				   	edit_form1.getForm().findField("productLineId").setValue(value);
				   	advertiserCombStore.removeListener('load');
				});
				productLineCombStore.load({params: {id : action.result.data.advertiserId}});
				
				productCombStore.addListener('load', function() {
					var productId = edit_form1.getForm().findField("productId").getValue();
				   	edit_form1.getForm().findField("productId").setValue(productId);
				   	productCombStore.removeListener('load');
				});
				productCombStore.load({params: {id : action.result.data.productLineId}});
			}
		});
		edit_win1.show();
	}
	
	var edit_win1 = new Ext.Window({
		title: '执行单',
        applyTo:'edit_win1',
        width:720,
    	height:420,
        closeAction:'hide',
        plain: true,
        layout: 'border',
        items: [edit_form1]
    });
	
	window.verify = function(id) {
		projectId = id;
		edit_form.load({
			url: '/myads/HTML/advflight/BookAction_getProjectInfo.action',
			params: {id: id}
		});
		refrshPreviewGridTab();
		bookPackage_ds.load({params: {start:0, limit:20,projectId: id}});
		edit_win.show();
	}
	
	var ds = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({url: '/myads/HTML/advflight/ProjectAction_showAll.action?p=7'}),
		remoteSort: false,
		reader: new Ext.data.JsonReader(
			{
				totalProperty: 'total',
				idProperty:'id',
				root: 'invdata'
			},
			[
				{name: 'id'},
				{name: 'name'},
				{name: 'startTime'},
				{name: 'endTime'},
				{name: 'type'},
				{name: 'advertiserName'},
				{name: 'saleName'},
				{name: 'ditchName'},
				{name: 'creator'},
				{name: 'createTime'}
			]
		)
	});

	ds.load({params: {start:0, limit:20}});
	
	var grid = new Ext.grid.GridPanel({
		el: 'grid',
		region: 'center',
		ds: ds,
		cm: cmo,
	    viewConfig: {
	    	forceFit: true
	    },
	    bbar: new Ext.PagingToolbar({
		    pageSize: 20,
		    store: ds,
			displayInfo: true,
			displayMsg: '显示第{0}条到{1}条记录,一共{2}条',
			emptyMsg: '没有记录'
	    })
	});
	
	new Ext.Viewport({
		layout: 'border',
		items:[
		       searchForm,grid
		]
	});
});
