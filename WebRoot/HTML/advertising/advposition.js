Ext.onReady(function() {
	
	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget='side';
	
	Ext.util.Observable.observeClass(Ext.data.Connection);
    Ext.data.Connection.on("requestcomplete", function(c, d, b){
        if (d && d.getResponseHeader) {
            if (d.getResponseHeader("sessionstatus")) {
            	alert("操作超时, 请重新登录!");
                window.location.href = "../index.html";
            }
        }
    });
	
	// 网站
	var siteComboStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({url: '/myads/HTML/advert/AdvpositionAction_getSiteList.action'}),
		reader: new Ext.data.ArrayReader({}, [
			{name: 'value'},{name: 'text'}
		])
	});
	siteComboStore.load();
	
	// 频道
	var channelComboStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({url: '/myads/HTML/sysfun/ChannelAction_getChannelListBySiteId.action'}),
		reader: new Ext.data.ArrayReader({}, [
			{name: 'value'},{name: 'text'}
		])
	});
	
	// 广告位规格
	var positionsizeCombStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({url: '/myads/HTML/advert/AdvpositionAction_getPositionsizeList.action'}),
		reader: new Ext.data.ArrayReader({}, [
			{name: 'value'},{name: 'text'}
		])
	});
	positionsizeCombStore.load({params: {type : '0'}});
	
	// 广告位模板
	var postemCombStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({url: '/myads/HTML/advert/AdvpositionAction_getAdvpositionPostemplateType.action'}),
		reader: new Ext.data.ArrayReader({}, [
			{name: 'dataType'},{name: 'dataValue'}
		])
	});
	
	// 模板种类
	var postemTypeStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({url: '/myads/HTML/advert/AdvpositionAction_getPostemTypeByPageType.action'}),
		reader: new Ext.data.ArrayReader({}, [
			{name: 'dataType'},{name: 'dataValue'}
		])
	});
	
	// 页面类型
	var pageTypeStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({url: '/myads/HTML/basic/OptionsAction_getBaseDataNamesByType.action'}),
		reader: new Ext.data.ArrayReader({}, [
			{name: 'value'},{name: 'text'}
		])
	});
	pageTypeStore.load({params: {dataType : 'postemtype'}});
	
	var ads_location_form = new Ext.form.FormPanel({
		labelAlign: 'right',
		region: 'center',
		labelWidth: 75,
		frame: true,
		xtype: 'fieldset',
		items: [
			{
				items: [
					{	
						columnWidth: .01, layout: 'form',
		            	items: [
		            	        { xtype: 'hidden', name: 'id', hidden:true, hiddenLabel:true},
		            	        { xtype: 'hidden', name: 'pageValue', hidden:true, hiddenLabel:true}
		            	] 
			        }
				]
			},
			{
				items: [
			        {	layout: 'form',
		            	items: [
		            	        new Ext.form.ComboBox({
	            	       		fieldLabel: '网站',
								hiddenName: 'siteId',
								allowBlank : false,
								blankText: '此项不能为空!',
								editable : false,
								mode: 'local',
								triggerAction: 'all', 
								valueField: 'value',
								displayField: 'text',
								anchor : '95%',
								store: siteComboStore,
								listeners : {
					                select : function(ComboBox, record, index) {
					                	ads_location_form.getForm().findField("channelId").clearValue();
					                	var siteId = record.data.value;
					                	channelComboStore.load({params: {siteId : siteId}});
					                }
					            }
	            	       })
		            	]
		            },
		            {	layout: 'form',
		            	items: [
		            	    new Ext.form.ComboBox({
	            	       		fieldLabel: '频道',
								hiddenName: 'channelId',
								allowBlank : false,
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
		            },
		            {	layout: 'form',
		            	items: [
		            	        {
											xtype : 'textfield',
											name : 'name',
											fieldLabel : '名称 ',
											anchor : '95%',
											allowBlank : false,
											blankText : '此项不能为空!'
										}
		            	]
		            },
		            {	layout: 'form',
		            	items: [
		            	        {
								xtype : 'textfield',
								vtype : 'alphanum',
								name : 'nameEn',
								fieldLabel : '英文名称 ',
								anchor : '95%',
								allowBlank : false,
								blankText : "只能输入英文和字符!",
								vtypeText : '只能输入英文和字符!'
							}
		            	]
		            },
		            {	layout: 'form',
		            	items: [
		            	   new Ext.form.ComboBox({
	            	       		fieldLabel: '规格',
								hiddenName: 'positionsizeId',
								allowBlank : false,
								blankText: '此项不能为空!',
								editable : false,
								mode: 'local',
								triggerAction: 'all', 
								valueField: 'value',
								displayField: 'text',
								anchor : '95%',
								store: positionsizeCombStore
	            	       })
		            	]
		            },
		            {
		            	layout: 'form',
		            	items: [
			            	new Ext.form.ComboBox({
	            	       		fieldLabel: '页面类型',
	            	       		id: 'pageTypes',
	            	       		hiddenName: 'pageType',
								allowBlank : false,
								blankText: '此项不能为空!',
								editable : false,
								mode: 'local',
								triggerAction: 'all', 
								valueField: 'value',
								displayField: 'text',
								anchor : '95%',
								store: pageTypeStore,
								listeners : {
					                select : function(ComboBox, record, index) {
					                	ads_location_form.getForm().findField("type").clearValue();
					                	var text = record.data.text;
					                	postemTypeStore.load({params: {dataName : encodeURI(text)}});
					                }
					            }
	            	       })
		            	]
		            },
		            {
		            	layout: 'form',
		            	items: [
			            	new Ext.form.ComboBox({
	            	       		fieldLabel: '模板种类',
	            	       		name: 'type',
								allowBlank : false,
								blankText: '此项不能为空!',
								editable : false,
								mode: 'local',
								triggerAction: 'all', 
								valueField: 'dataType',
								displayField: 'dataValue',
								anchor : '95%',
								store: postemTypeStore,
								listeners : {
					                select : function(ComboBox, record, index) {
					                	ads_location_form.getForm().findField("postemId").clearValue();
					                	var dataType = record.data.dataValue;
					                	var type = ads_location_form.getForm().findField("pageTypes").getValue();
					                	ads_location_form.getForm().findField("pageValue").setValue(record.data.dataType);
					                	postemCombStore.load({params: {dataType : dataType, type : encodeURI(type)}});
					                }
					            }
	            	       })
		            	]
		            },
		            {
		            	layout: 'form',
		            	items: [
			            	new Ext.form.ComboBox({
	            	       		fieldLabel: '模板',
								hiddenName: 'postemId',
								allowBlank : false,
								blankText: '此项不能为空!',
								editable : false,
								mode: 'local',
								triggerAction: 'all', 
								valueField: 'dataType',
								displayField: 'dataValue',
								anchor : '95%',
								store: postemCombStore,
								listeners : {
					                select : function(ComboBox, record, index) {
					                	var dataType = record.data.dataType;
					                	Ext.Ajax.request({
											method: 'post',
											url: '/myads/HTML/advert/PostemplateAction_detail.action',
										   	success:function(resp){
										    	var obj=Ext.util.JSON.decode(resp.responseText);
									      		ads_location_form.getForm().findField("num").setValue(obj.data.barNum);
										    },
										   	params: {id : dataType}
										});
					                }
					            }
	            	       })
		            	]
		            },
		            {	layout: 'form',
		            	items : [ {
							xtype : 'textfield',
							name : 'num',
							fieldLabel : '广告条数 ',
							anchor : '95%',
							allowBlank : false,
							readOnly: true,
							blankText : "不能为空!"
						} ]
		            },
//		            {layout: 'form',
//		            	items : [ {
//								xtype : 'textarea',
//								name : 'code',
//								fieldLabel : 'CODE',
//								anchor : '95%',
//								height : 80
//							}]},
		            {	layout: 'form',
		            	items: [
		            	        { xtype: 'textfield', name: 'note', fieldLabel: '描述 ',anchor : '95%'}
		            	]
		            }
				]
			}
		],
		buttons: [
				{
					text: '保存',
					handler: function() {
						var id = ads_location_form.getForm().findField("id").getValue();
						var channelId = ads_location_form.getForm().findField("channelId").getValue();
						var name = ads_location_form.getForm().findField("name").getValue();
						
						if (ads_location_form.getForm().isValid()) {
							Ext.Ajax.request({
								url : '/myads/HTML/advert/AdvpositionAction_vldAdvNameRepeated.action',
								method : 'post',
								params : {
									id : id,
									channelId : channelId,
									name : name
								},
								success : function(response, options) {
									var obj = Ext.util.JSON.decode(response.responseText);
									if (obj.result == 'use') {
										Ext.MessageBox.alert('提示', '该频道下已经存在相同名称的广告位！');
									} else if (obj.result == 'success') {
										ads_location_form.form.doAction('submit', {
											url: '/myads/HTML/advert/AdvpositionAction_save.action',
											method: 'post',
											params: '',
											success: function(form, action) {
												Ext.MessageBox.alert('结果', '保存成功！');
												form.reset();
												grid.getStore().reload();
												ads_win.hide();
											}
										});
									} else {
										Ext.MessageBox.alert('提示', '新建广告位操作异常,请重试！');
									}
								}
							});
						} else {
							Ext.MessageBox.alert('提示', '请先输入广告位基础信息！');
						}
					}
				},
				{
					text: '取消',
					handler: function() {
					ads_location_form.form.reset();
						ads_win.hide();
					}
				}
			]
	});
	
	var searchForm = new Ext.form.FormPanel({
		flex:2,
       	margins:'0',
		labelAlign: 'right',
		region: 'north',
		labelWidth: 70,
		frame: true,
		items: [
			{
				layout: 'column',
				items: [
					{
						columnWidth: .25, layout: 'form',
						items: [{
							xtype : 'textfield',
							name : 'name',
							fieldLabel : '名称',
							anchor : '95%'
						}]
					},
					{
						columnWidth: .25, layout: 'form',
						items: [
							new Ext.form.ComboBox({
	            	       		fieldLabel: '网站',
	            	       		hiddenName: 'siteId',
								mode: 'local',
								editable : false,
								triggerAction: 'all', 
								valueField: 'value',
								displayField: 'text',
								anchor : '95%',
								store: siteComboStore,
								listeners : {
					                select : function(ComboBox, record, index) {
					                	searchForm.getForm().findField("channelId").clearValue();
					                	var siteId = record.data.value;
					                	channelComboStore.load({params: {siteId : siteId}});
					                }
					            }
	            	       })
						]
					},
					{
						columnWidth: .25, layout: 'form',
						items: [
							new Ext.form.ComboBox({
	            	       		fieldLabel: '频道',
	            	       		hiddenName: 'channelId',
								mode: 'local',
								editable : false,
								triggerAction: 'all', 
								valueField: 'value',
								displayField: 'text',
								anchor : '95%',
								store: channelComboStore
	            	       })
	            	    ]
					},
					{	columnWidth: .25, layout: 'form',
		            	items: [
		            	   new Ext.form.ComboBox({
		        	       		fieldLabel: '状态',
								hiddenName: 'status',
								mode: 'local',
								editable : false,
								triggerAction: 'all', 
								valueField: 'value',
								displayField: 'text',
								anchor : '95%',
								value : 2,
								store: new Ext.data.SimpleStore({
									fields: ['value', 'text'],
									data: [['', '全部'],['1', '新建'],['2', '启用'],['3', '停用']]
								})
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
					var fv = searchForm.getForm().getValues();
					ds.baseParams= fv;
					ds.load({params: {start:0, limit:50}});
				}
			},
			new Ext.Button({
				text: '重置',
				width: 70,
				handler: function() {
					searchForm.form.reset();
					ds.baseParams= {};
					searchForm.getForm().findField("status").setValue(2);
				}
			}),
			{
				text: '创建广告位',
				handler: function() {
					ads_location_form.form.reset();
					ads_win.show();
				}
			}
		]
	});
	searchForm.render('searchform-div');
	
	//判断状态
	function rendStauts(value){
		if(value==0){
			return "删除";
		}else if(value==1){
			return "新建";
		}else if(value==2){
			return "<span style='color:GREEN'>启用</span>";
		}else if(value==3){
			return "<span style='color:RED'>停用</span>";
		}
	}
	
	var cm = new Ext.grid.ColumnModel([
	    {header:'ID', dataIndex:'id', sortable:true},
	    {header:'名称', dataIndex:'name', sortable:true},
	    {header:'英文名称', dataIndex:'nameEn', sortable:true},
	    {header:'网站', dataIndex:'siteName', sortable:true},
	    {header:'频道', dataIndex:'channelName', sortable:true},
	    {header:'广告条数', dataIndex:'num', sortable:true},
	    {header:'状态', dataIndex:'status', sortable:true,renderer:rendStauts},
	    {header:'创建时间', dataIndex:'createTime', sortable:true},
	    {header:'操作', dataIndex:'op', renderer: renderOp,width:260, align:'left'}
	]);
	
	function renderOp(value, cellmeta, record, rowIndex, columnIndex, stor) {
		var id = record.data.id;
		var name = record.data.name; 
		var status = record.data.status;
		var postemId = record.data.postemId;
		var pageType = record.data.pageType;
		var detailStr = '<a href="/myads/HTML/advertising/advbar.jsp?advpositionId='+id+'&status='+status+'&postemId='+postemId+'">广告条</a>';
		var delStr = '<a href="#" onclick=\"delete_Ads(\''+id+'\', \'' + name + '\');\">删除</a>';
		var updateStr = '<a href="#" onclick=\"update_Ads(\''+id+'\');\">编辑</a>';
		var stateStr = '<a href="#" onclick=\"update_Adstate(\''+id+'\', \'' + status + '\');\">状态</a>';
		var advpositionCode = '<a href="#" onclick=\"show_AdvpositionCode(\''+id+'\');\">代码</a>';
		var codeStr = '<a href="#" onclick=\"show_AdCode(\''+id+'\');\">CODE</a>';
		
		var result = detailStr+ '&nbsp;&nbsp;&nbsp;&nbsp;' + delStr + '&nbsp;&nbsp;&nbsp;&nbsp;' + 
			updateStr + "&nbsp;&nbsp;&nbsp;&nbsp;" + stateStr + "&nbsp;&nbsp;&nbsp;&nbsp;" + codeStr;
		
		if (pageType == 1)
			return result + "&nbsp;&nbsp;&nbsp;&nbsp;" + advpositionCode;
		else
			return result;
	}
	
	window.update_Adstate = function(id,status) {
		ads_updatestate_form.getForm().findField("id").setValue(id);
		ads_updatestate_form.getForm().findField("status").setValue(status);
		ads_win_updateState.show();
	}
	
	window.delete_Ads = function(id, name) {
		Ext.MessageBox.confirm('提示', '确定删除  ' + name + ' ？', function(btn) {
			if(btn == 'yes') {
				var param = [];
				param.push(id);
				Ext.Ajax.request({
					method: 'post',
					url: '/myads/HTML/advert/AdvpositionAction_delete.action',
				   	success:function(resp){
				    	var obj=Ext.util.JSON.decode(resp.responseText);
				      	if(obj.result == 'success') {
				      		grid.getStore().reload();
				      		Ext.MessageBox.alert('提示', '删除成功！');
				      	}
				      	if (obj.result == 'use') {
							grid.getStore().reload();
							Ext.MessageBox.alert('提示','删除失败，此广告位其它功能正在使用中！');
						}
				    },
				   	params: {advpositionList: param.join(',')}
				});
			}
		});
	}
	
	window.update_Ads = function(id) {
		
		ads_location_form.load({
			url: '/myads/HTML/advert/AdvpositionAction_getAdvpostionDetail.action',
			params: {id: id},
			success:function(form,action){
				
				var pageType = Ext.get('pageTypes').dom.value;
				var ptype = ads_location_form.getForm().findField("pageType").getValue();
				
				// 频道
				channelComboStore.addListener('load', function() {
					var value = ads_location_form.getForm().findField("channelId").getValue();
				   	ads_location_form.getForm().findField("channelId").setValue(value);
				   	channelComboStore.removeListener('load');
				});
				channelComboStore.load({params: {siteId : action.result.data.siteId}});
				
				// 模板种类
				postemTypeStore.addListener('load', function() {
				   	postemTypeStore.removeListener('load');
				});
				postemTypeStore.load({params: {dataName : encodeURI(pageType)}});
				
				// 广告位模板
				postemCombStore.addListener('load', function() {
					var dataType = ads_location_form.getForm().findField("postemId").getValue();
				   	ads_location_form.getForm().findField("postemId").setValue(dataType);
				   	postemCombStore.removeListener('load');
				});
				postemCombStore.load({params: {dataType : action.result.data.type, type : ptype}});
			}
		});
		
		ads_win.show();
	}
	
	window.show_AdCode = function(id) {
		ads_code_form.load({
			url: '/myads/HTML/advert/AdvpositionAction_getAdvpostionDetail.action',
			params: {id: id}
		});
		ads_win_showCode.show();
	};
	
	window.show_AdvpositionCode = function(id) {
		var code = '<div style="margin:0px;padding:0px;" id="ad_'+id+'"></div>';
		ads_advpositionCode_form.getForm().findField("dm").setValue(code);
		ads_advposition_showCode.show();
	};
	
	var ads_code_form = new Ext.form.FormPanel({
		labelAlign : 'right',
		region : 'center',
		labelWidth : 35,
		frame : true,
		buttonAlign : 'center',
		xtype : 'fieldset',
		items : [{
				layout : 'form',
				items : [{
							xtype : 'textarea',
							id : 'code',
							name : 'code',
							readOnly : true,
							fieldLabel : 'CODE',
							anchor : '99%',
							height : 200
						}]
				}],
		buttons : [{
					text : '关闭',
					handler : function() {
						ads_code_form.form.reset();
						ads_win_showCode.hide();
					}
				}]
	});
	
	var ads_updatestate_form = new Ext.form.FormPanel({
	    	labelAlign: 'right',
			region: 'center',
			labelWidth: 75,
			frame: true,
			xtype: 'fieldset',
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
				{	
					layout: 'fit',
					height: 100,
					items:[
						{
							layout: 'form',
			            	items: [
			            	   new Ext.form.ComboBox({
			        	       		fieldLabel: '状态',
									hiddenName: 'status',
									mode: 'local',
									editable : false,
									triggerAction: 'all', 
									valueField: 'value',
									displayField: 'text',
									anchor : '95%',
									store: new Ext.data.SimpleStore({
										fields: ['value', 'text'],
										data: [['1', '新建'],['2', '启用'],['3', '停用']]
									})
			        	       })
			            	]
						}
					]
	            }
			],
			buttons: [
					{
						text: '保存',
						handler: function() {
							var id = ads_updatestate_form.getForm().findField("id").getValue();
							var status = ads_updatestate_form.getForm().findField("status").getValue();
							Ext.Ajax.request({
								url: '/myads/HTML/advert/AdvpositionAction_updateStatus.action',
								method : 'post',
								params : {
									id : id,
									status : status
								},
								success : function(response, options) {
									var obj = Ext.util.JSON.decode(response.responseText);
									if (obj.result == 'under') {
										Ext.MessageBox.alert('提示', '该广告位下的广告条数量小于广告位规定数量!');
									} else if (obj.result == 'statusStart') {
										Ext.MessageBox.alert('提示', '该广告位下的广告条有未启用的!');
									} else {
									}
									grid.getStore().reload();
									ads_win_updateState.hide();
								}
							});
						}
					},
					{
						text: '取消',
						handler: function() {
						ads_updatestate_form.form.reset();
							ads_win_updateState.hide();
						}
					}
			]
	    });
	    
	var ads_advpositionCode_form = new Ext.form.FormPanel({
		labelAlign : 'right',
		region : 'center',
		labelWidth : 35,
		frame : true,
		buttonAlign : 'center',
		xtype : 'fieldset',
		items : [{layout : 'form',
				items : [{
							xtype : 'textarea',
							id : 'dm',
							readOnly : true,
							fieldLabel : '代码',
							anchor : '99%',
							height : 200
						}]
				}],
		buttons : [{
					text : '关闭',
					handler : function() {
						ads_advpositionCode_form.form.reset();
						ads_advposition_showCode.hide();
					}
				}]
	});
	    
	var ads_advposition_showCode = new Ext.Window({
    	title: '代码',
        applyTo:'ads_advposition_win',
        layout:'fit',
        width:400,
    	height:200,
        closeAction:'hide',
        plain: true,
        layout: 'border',
        items: [ads_advpositionCode_form]
    });
	    
    var ads_win_updateState = new Ext.Window({
    	title: '修改状态',
        applyTo:'ads_updatestate_win',
        layout:'fit',
        width:230,
    	height:120,
        closeAction:'hide',
        plain: true,
        layout: 'border',
        items: [ads_updatestate_form]
    });
    
    var ads_win_showCode = new Ext.Window({
    	title: 'CODE',
        applyTo:'ads_code_win',
        layout:'fit',
        width:400,
    	height:200,
        closeAction:'hide',
        plain: true,
        layout: 'border',
        items: [ads_code_form]
    });
	
	var ds = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({url: '/myads/HTML/advert/AdvpositionAction_showAll.action'}),
		baseParams : {status : 2},
		remoteSort: false,
		reader: new Ext.data.JsonReader(
			{
				totalProperty: 'total',
				idProperty:'id',
				root: 'invdata'
			},
			[
				{name: 'id'},
				{name: 'postemId'},
				{name: 'name'},
				{name: 'nameEn'},
				{name: 'siteName'},
				{name: 'channelName'},
				{name: 'num'},
				{name: 'status'},
				{name: 'createTime'},
				{name: 'pageType'}
			]
		)
	});
	ds.load({params: {start:0, limit:50}});
	
	var grid = new Ext.grid.GridPanel({
		el: 'grid',
		region: 'center',
		ds: ds,
		cm: cm,
	    viewConfig: {
	    	forceFit: true
	    },
	    bbar: new Ext.PagingToolbar({
		    pageSize: 50,
		    store: ds,
			displayInfo: true,
			displayMsg: '显示第{0}条到{1}条记录,一共{2}条',
			emptyMsg: '没有记录'
	    })
	});
	
	var ads_win = new Ext.Window({
		title: '广告位',
        applyTo:'win_div',
        layout:'fit',
        width:520,
    	//height:430,
        height:350,
        closeAction:'hide',
        plain: true,
        layout: 'border',
        items: [ads_location_form]
    });
	
	new Ext.Viewport({
		layout: 'border',
		items:[
		       searchForm,grid
		]
	});
});