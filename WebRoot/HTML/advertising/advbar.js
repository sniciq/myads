Ext.onReady(function() {
	
	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget='side';
	
	// 广告位id
	var advpositionId = Ext.get('advpositionId').dom.value;
	
	// 广告位状态
	var advpositionStatus = Ext.get('status').dom.value;
    
	var postemId = Ext.get('postemId').dom.value;
	
    // 规格
	var advbarsizeStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({url: '/myads/HTML/advert/AdvpositionAction_getPositionsizeList.action'}),
		reader: new Ext.data.ArrayReader({}, [
			{name: 'value'},{name: 'text'}
		])
	});
	advbarsizeStore.load({params: {type: '1'}});
	
	// 广告条模板
	var bartemComboStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({url: '/myads/HTML/advert/AdvbarAction_getBartemplateList.action'}),
		reader: new Ext.data.ArrayReader({}, [
			{name: 'value'},{name: 'text'}
		])
	});
	bartemComboStore.load({params: {id : postemId}});
	
	var edit_form = new Ext.form.FormPanel({
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
		            	        { xtype: 'hidden', name: 'id', hidden:true, hiddenLabel:true},
		            	        { xtype: 'hidden', name: 'siteId', hidden:true, hiddenLabel:true},
		            	        { xtype: 'hidden', name: 'siteName', hidden:true, hiddenLabel:true},
		            	        { xtype: 'hidden', name: 'channelId', hidden:true, hiddenLabel:true},
		            	        { xtype: 'hidden', name: 'channelName', hidden:true, hiddenLabel:true},
		            	        { xtype: 'hidden', name: 'advpositionNum', hidden:true, hiddenLabel:true},
		            	        { xtype: 'hidden', name: 'posId', hidden:true, hiddenLabel:true}
		            	] 
			        }
				]
			},
			{
				layout: 'form',
            	items: [
            	        { xtype: 'textfield', name: 'posName', fieldLabel: '所属广告位 ', anchor : '95%',readOnly:true}
            	]
			},
			{
					layout : 'form',
					items : [{
								xtype : 'textfield',
								name : 'name',
								fieldLabel : '广告条名称 ',
								anchor : '95%',
								allowBlank : false,
								blankText : "此项不能为空!"
							}]
				},
			{
					layout : 'form',
					items : [{
								xtype : 'textfield',
								vtype : 'alphanum',
								name : 'nameEn',
								fieldLabel : '英文名称 ',
								anchor : '95%',
								allowBlank : false,
								blankText : "只能输入英文和字符!",
								vtypeText : '只能输入英文和字符!'
							}]
				},
			{	layout: 'form',
            	items: [
            	   new Ext.form.ComboBox({
        	       		fieldLabel: '规格',
						hiddenName: 'barsizeId',
						mode: 'local',
						editable : false,
						triggerAction: 'all', 
						valueField: 'value',
						displayField: 'text',
						anchor : '95%',
						allowBlank : false,
						blankText : "此项不能为空!",
						store: advbarsizeStore
        	       })
            	]
            },
            {
					layout : 'form',
					items : [{
								xtype : 'textfield',
								name : 'content',
								fieldLabel : '广告容量 ',
								anchor : '95%',
								allowBlank : false,
								blankText : "只能输入数字!",
								regex : /\d/,
								regexText : "只能输入数字!"
							}]
				},
			{	layout: 'form',
            	items: [
            	   new Ext.form.ComboBox({
        	       		fieldLabel: '广告条模板',
						hiddenName: 'bartemId',
						mode: 'local',
						allowBlank : false,
						blankText: '此项不能为空!',
						editable : false,
						triggerAction: 'all', 
						valueField: 'value',
						displayField: 'text',
						anchor : '95%',
						store: bartemComboStore
        	       })
            	]
            },
			{
				layout : 'form',
				items : [{
							xtype : 'textfield',
							name : 'num',
							fieldLabel : '排序数 ',
							anchor : '95%',
							allowBlank : false,
							blankText : "只能输入数字!",
							regex : /^\d/,
							maxLength:2,
							maxLengthText:'输入文本过长',
							regexText : "只能输入数字!"
						}]
			},
			{
				layout : 'form',
				items: [{
							xtype : 'numberfield',
							name : 'futureFlight',
							fieldLabel : '默认CPM预估值',
							anchor : '95%',
							allowDecimals : false,
							nanText :'请输入有效的小数',
							allowNegative : false
						}]
			},
//			{	layout: 'form',
//            	items: [
//            	   new Ext.form.ComboBox({
//        	       		fieldLabel: '背景广告条',
//						hiddenName: 'isBackground',
//						mode: 'local',
//						allowBlank : false,
//						blankText: '此项不能为空!',
//						editable : false,
//						triggerAction: 'all', 
//						valueField: 'value',
//						displayField: 'text',
//						anchor : '95%',
//						store : [[1, '是'], [0, '否']]
//        	       })
//            	]
//            },
//			{xtype : 'textarea',name : 'code',fieldLabel : 'CODE',anchor : '95%',height:80},
            {	layout: 'form',
            	items: [
            	        { xtype: 'textfield', name: 'note', fieldLabel: '描述 ',anchor : '95%'}
            	]
            }
		],
		buttons: [
				{
					text: '保存',
					handler: function() {
						var id = edit_form.getForm().findField("id").getValue();
						var posId = edit_form.getForm().findField("posId").getValue();
						var name = edit_form.getForm().findField("name").getValue();
						var posName = edit_form.getForm().findField("posName").getValue();
						
						Ext.Ajax.request({
							url : '/myads/HTML/advert/AdvbarAction_vldAdvbarNameRepeated.action',
							method : 'post',
							params : {
								id : id,
								posId : posId,
								name : name,
								posName : posName
							},
							success : function(response, options) {
								var obj = Ext.util.JSON.decode(response.responseText);
								if (obj.result == 'use') {
									Ext.MessageBox.alert('提示', '该广告位下已经存在相同名称的广告条！');
								} else if (obj.result == 'success') {
									edit_form.form.doAction('submit', {
										url: '/myads/HTML/advert/AdvbarAction_save.action',
										method: 'post',
										params : '',
										success: function(form, action) {
											Ext.MessageBox.alert('提示', '保存成功！');
											form.reset();
											grid.getStore().reload();
											edit_win.hide();
										}
									});
								} else {
									Ext.MessageBox.alert('提示', '新建广告条操作异常,请重试！');
								}
							}
						});
					}
				},
				{
					text: '重置',
					handler: function() {
						var valueTmp = edit_form.getForm().findField("posName").getValue();
						edit_form.form.reset();
						edit_form.getForm().findField("posName").setValue(valueTmp);
					}
				}
		]
	});
	
	var edit_win = new Ext.Window({
		title: '广告条',
        applyTo:'edit_win_div',
        width:520,
    	//height:370,
        height:310,
        closeAction:'hide',
        plain: true,
        layout: 'border',
        items: [edit_form]
    });
    
    var searchForm = new Ext.form.FormPanel({
		labelAlign: 'right',
		region: 'north',
		labelWidth: 70,
		frame: true,
		items: [
			{
				items: [
					{	
						columnWidth: .01, layout: 'form',
		            	items: [
		            	        { xtype: 'hidden', name: 'siteId', hidden:true, hiddenLabel:true},
		            	        { xtype: 'hidden', name: 'channelId', hidden:true, hiddenLabel:true},
		            	        { xtype: 'hidden', name: 'id', hidden:true, hiddenLabel:true},
		            	        { xtype: 'hidden', name: 'num', hidden:true, hiddenLabel:true}
		            	] 
			        }
				]
			},
			{
				layout: 'column',
				items: [
					{
						columnWidth: .20, layout: 'form',
						items: [{
							xtype : 'textfield',
							name : 'name',
							fieldLabel : '名称',
							anchor : '95%',
							disabled:true
						}]
					},
					{
						columnWidth: .20, layout: 'form',
						items: [{
							xtype : 'textfield',
							name : 'siteName',
							fieldLabel : '网站',
							anchor : '95%',
							disabled:true
						}]
					},
					{
						columnWidth: .20, layout: 'form',
						items: [{
							xtype : 'textfield',
							name : 'channelName',
							fieldLabel : '频道',
							anchor : '95%',
							disabled:true
						}]
					},
					{
						columnWidth: .20, layout: 'form',
						items: [{
							xtype : 'textfield',
							name : 'status',
							fieldLabel : '状态',
							anchor : '95%',
							disabled:true
						}]
					},
					{
						columnWidth: .20, layout: 'form',
						items: [{
							xtype : 'textfield',
							name : 'createTime',
							fieldLabel : '创建时间',
							anchor : '95%',
							disabled:true
						}]
					}
				]
			}
		],
		buttons: [
			{
				text: '返回',
				handler: function() {
					window.history.back();
				}
			},
			{
				text: '创建广告条',
				handler: function() {
					edit_form.form.reset();
					
					var advpositionName = searchForm.getForm().findField("name").getValue();
					edit_form.getForm().findField("posName").setValue(advpositionName);
					
					var siteId = searchForm.getForm().findField("siteId").getValue();
					edit_form.getForm().findField("siteId").setValue(siteId);
					
					var siteName = searchForm.getForm().findField("siteName").getValue();
					edit_form.getForm().findField("siteName").setValue(siteName);
					
					var channelId = searchForm.getForm().findField("channelId").getValue();
					edit_form.getForm().findField("channelId").setValue(channelId);
					
					var channelName = searchForm.getForm().findField("channelName").getValue();
					edit_form.getForm().findField("channelName").setValue(channelName);
					
					var id = searchForm.getForm().findField("id").getValue();
					edit_form.getForm().findField("posId").setValue(id);
					
					var content = searchForm.getForm().findField("num").getValue();
					edit_form.getForm().findField("advpositionNum").setValue(content);
					
					var storeCount = grid.getStore().getTotalCount();
					if (advpositionStatus == 3) {
						Ext.MessageBox.alert('提示', '广告位已被禁用,请启用后在进行操作!');
					} else {
						if (storeCount >= content) {
							Ext.MessageBox.alert('提示', '当前广告位容量已满,不能进行添加操作!');
						} else {
							edit_win.show();
						}
					}
				}
			}
		]
	});
	searchForm.render('north-div');
	
	searchForm.load({
		url: '/myads/HTML/advert/AdvpositionAction_getAdvpositionDetailInAdvbar.action',
		params: {id: advpositionId},
		success : function(form, action) {
			var status = searchForm.getForm().findField("status").getValue();
			if(status==0){
				searchForm.getForm().findField("status").setValue('删除');
			}else if(status==1){
				searchForm.getForm().findField("status").setValue('新建');
			}else if(status==2){
				searchForm.getForm().findField("status").setValue('启用');
			}else if(status==3){
				searchForm.getForm().findField("status").setValue('停用');
			}
        }
	});
	
	//判断状态
	function rendStauts(value){
		if(value==0){
			return "<span style='color:GREEN'>启用</span>";
		}else if(value==1){
			return "<span style='color:GRAY'>删除</span>";
		}else if(value==2){
			return "<span style='color:RED'>停用</span>";
		}
	}
	
	var cm = new Ext.grid.ColumnModel([
	    {header:'ID', dataIndex:'id', sortable:true},
	    {header:'名称', dataIndex:'name', sortable:true},
	    {header:'规格', dataIndex:'positionName', sortable:true},
	    {header:'广告容量', dataIndex:'content', sortable:true},
	    {header:'排序数', dataIndex:'num', sortable:true},
	    {header:'状态', dataIndex:'status', sortable:true, renderer:rendStauts},
	    {header:'创建时间', dataIndex:'createTime', sortable:true},
	    {header:'操作', dataIndex:'op', renderer: renderOp,width:260, align:'left'}
	]);
	
	function renderOp(value, cellmeta, record, rowIndex, columnIndex, stor) {
		var id = record.data.id;
		var name = record.data.name; 
		var status = record.data.status;
		var delStr = '<a href="#" onclick=\"delete_Ads(\''+id+'\', \'' + name + '\');\">删除</a>';
		var updateStr = '<a href="#" onclick=\"update_Ads(\''+id+'\');\">编辑</a>';
		var stateStr = '<a href="#" onclick=\"update_Adstate(\''+id+'\', \'' + status + '\');\">状态</a>';
		var modulusStr = '<a href="#" onclick=\"show_moduluse(\''+id+'\');\">系数</a>';
		var relationStr = '<a href="#" onclick=\"show_relation(\''+id+'\');\">关联</a>';
   		var codeStr = '<a href="#" onclick=\"show_AdCode(\''+id+'\');\">CODE</a>';
		return  delStr + '&nbsp;&nbsp;&nbsp;&nbsp;' + updateStr + "&nbsp;&nbsp;&nbsp;&nbsp;" + stateStr + "&nbsp;&nbsp;&nbsp;&nbsp;" + modulusStr
				+ "&nbsp;&nbsp;&nbsp;&nbsp;" + codeStr;
	}
	
	window.show_moduluse = function(id) {
		moduluse_form.getForm().reset();
		moduluse_form.getForm().findField("barId").setValue(id);
		moduluse_ds.load({params: {start:0, limit:50, barId:id}});
		moduluse_win.show();
	}
	
	window.update_Adstate = function(id, status) {
		updatestate_form.getForm().findField("id").setValue(id);
		updatestate_form.getForm().findField("status").setValue(status);
		updatestate_win.show();
	};
	
	window.delete_Ads = function(id, name) {
		Ext.MessageBox.confirm('提示', '确定删除  ' + name + ' ？', function(btn) {
			if(btn == 'yes') {
				var param = [];
				param.push(id);
				Ext.Ajax.request({
					method: 'post',
					url: '/myads/HTML/advert/AdvbarAction_delete.action',
				   	success:function(resp){
				    	var obj=Ext.util.JSON.decode(resp.responseText);
				      	if(obj.result == 'success') {
				      		grid.getStore().reload();
				      		Ext.MessageBox.alert('提示', '删除成功！');
				      	}
				      	if(obj.result == 'use') {
				      		grid.getStore().reload();
				      		Ext.MessageBox.alert('提示', '删除失败，此广告条外联使用中！！');
				      	}
				    },
				   	params: {advbarList: param.join(',')}
				});
			}
		});
	}
	
	window.update_Ads = function(id) {
		edit_form.load({
			url: '/myads/HTML/advert/AdvbarAction_getAdvbarDetail.action',
			params: {id: id}
		});
		edit_win.show();
	}
	
	window.showAds_items = function(id, name) {
		var n = tabs.add({
			title: name + '广告条',
			closable:true,
			html: '<iframe scrolling="auto" frameborder="0" width="100%" height="100%" src="ads_item.html?advertisingId='+ id +'"></iframe>'
		});
		tabs.setActiveTab(n);
	}
	
	window.show_AdCode = function(id) {
		ads_code_form.load({
			url: '/myads/HTML/advert/AdvbarAction_getAdvbarDetail.action',
			params: {id: id}
		});
		ads_win_showCode.show();
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
	
	var ads_win_showCode = new Ext.Window({
    	title: 'CODE',
        layout:'fit',
        width:400,
    	height:200,
        closeAction:'hide',
        plain: true,
        layout: 'border',
        items: [ads_code_form]
    });
	
	var ds = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({url: '/myads/HTML/advert/AdvbarAction_showAll.action?advpositionId='+advpositionId}),
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
				{name: 'positionName'},
				{name: 'content'},
				{name: 'num'},
				{name: 'status'},
				{name: 'createTime'}
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
	
	grid.addListener('rowdblclick', function(grid, rowindex, e) {
		var record = grid.getStore().getAt(rowindex);
		edit_form.load({
			url: '/myads/HTML/advbar/AdvbarAction_getAdvbarDetail.action',
			params: {id: record.id}
		});
		edit_win.show();
	});
	
	var updatestate_form = new Ext.form.FormPanel({
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
									data: [['0', '启用'],['2', '停用']]
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
					updatestate_form.form.doAction('submit', {
							url: '/myads/HTML/advert/AdvbarAction_updateStatus.action',
							method: 'post',
							params : '',
							success: function(form, action) {
								Ext.MessageBox.alert('提示', '状态变更成功！');
								grid.getStore().reload();
								updatestate_win.hide();
							}
						});
					}
				},
				{
					text: '取消',
					handler: function() {
					updatestate_form.form.reset();
						updatestate_win.hide();
					}
				}
		]
    });
    
    var updatestate_win = new Ext.Window({
    	title: '修改状态',
        applyTo:'updatestate_win',
        layout:'fit',
        width:230,
    	height:120,
        closeAction:'hide',
        plain: true,
        layout: 'border',
        items: [updatestate_form]
    });
    
    var moduluse_form = new Ext.form.FormPanel({
    	labelAlign: 'right',
		region: 'north',
		labelWidth: 60,
		height: 70,
		frame: true,
		xtype: 'fieldset',
		items: [
			{
				items: [
					{	
						columnWidth: .01, layout: 'form',
		            	items: [
		            	        { xtype: 'hidden', name: 'barId', hidden:true, hiddenLabel:true}
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
								fieldLabel: '开始日期',
								name: 'startTime',
								allowBlank:false,
								blankText : "开始日期不允许为空!",
								format:'Y-m-d',
								anchor : '90%'
							})
						]
					},
		            {	columnWidth: .34, layout: 'form',
		            	items: [
		            	      new Ext.form.DateField({
								fieldLabel: '结束日期',
								name: 'endTime',
								allowBlank:false,
								blankText : "结束日期不允许为空!",
								format:'Y-m-d',
								anchor : '90%'
							})
		            	]
		            },
		            {
						columnWidth : .33,
						layout : 'form',
						items : [ {
							xtype : 'numberfield',
							name : 'proportion',
							allowBlank : false,
							blankText : "不允许为空且只能输入数字!",
							maxValue : 999999.9,
							fieldLabel : '系数 ',
							anchor : '90%'
						} ]
					}
            	]
            }
		],
		buttons: [
			{
				text: '保存',
				handler: function() {
					var flag = moduluse_form.getForm().isValid();
					if (flag) {
						Ext.Ajax.request({
							url: '/myads/HTML/advert/BarProportionAction_save.action',
							method : 'post',
							params : {barId : moduluse_form.getForm().findField("barId").getValue(),
									  startTime: moduluse_form.getForm().findField("startTime").getValue(),
									  endTime: moduluse_form.getForm().findField("endTime").getValue(),
									  proportion: moduluse_form.getForm().findField("proportion").getValue()},
							success : function(response) {
								var obj=Ext.util.JSON.decode(response.responseText);
								if(obj.result == 'use') {
						      		Ext.MessageBox.alert('提示', '您输入的系数时间段已经存在,请检查后重新输入！');
						      	} else {
						      		if (obj.result == 'success') {
										Ext.MessageBox.alert('提示','保存成功！');
										moduluse_grid.getStore().reload();
									} else {
										Ext.MessageBox.alert('提示', '保存失败！');
									}
						      	}
							}
						});
					} else {
						Ext.MessageBox.alert('提示', '日期或系数不符合规则,请检查!');
					}
				}
			}
		]
    });
    
    var moduluse_sm = new Ext.grid.CheckboxSelectionModel();
	var moduluse_cm = new Ext.grid.ColumnModel([
	    {header:'开始日期', dataIndex:'startTime', sortable:true},
	    {header:'结束日期', dataIndex:'endTime', sortable:true},
	    {header:'系数', dataIndex:'proportion', sortable:true},
	    {header:'操作', dataIndex:'op', renderer: renderOp_moduluse, align:'left'}
	]);
	
	function renderOp_moduluse(value, cellmeta, record, rowIndex, columnIndex, stor) {
		var id = record.data.id;
		var proportion = record.data.proportion; 
		var delStr = '<a href="#" onclick=\"delete_Proportion(\''+id+'\', \'' + proportion + '\');\">删除</a>';
		return  delStr;
	}
	
	// 删除系数
	window.delete_Proportion = function(id, proportion) {
		Ext.MessageBox.confirm('提示', '确定删除  ' + proportion + ' ？', function(btn) {
			if(btn == 'yes') {
				var param = [];
				param.push(id);
				Ext.Ajax.request({
					method: 'post',
					url: '/myads/HTML/advert/BarProportionAction_delete.action',
				   	success:function(resp){
				    	var obj=Ext.util.JSON.decode(resp.responseText);
				      	if(obj.result == 'success') {
				      		moduluse_grid.getStore().reload();
				      		Ext.MessageBox.alert('提示', '删除成功！');
				      	}
				    },
				   	params: {barProportionList: param.join(',')}
				});
			}
		});
	}
	
	var moduluse_ds = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({url: '/myads/HTML/advert/BarProportionAction_showAll.action'}),
		remoteSort: false,
		reader: new Ext.data.JsonReader(
			{
				totalProperty: 'total',
				idProperty:'id',
				root: 'invdata'
			},
			[
				{name: 'id'},
				{name: 'startTime'},
				{name: 'endTime'},
				{name: 'proportion'}
			]
		)
	});
	
	moduluse_ds.on('beforeload', function() {
	      Ext.apply(this.baseParams, {barId : moduluse_form.getForm().findField("barId").getValue()});
	});
	
	var moduluse_grid = new Ext.grid.GridPanel({
		region: 'center',
		ds: moduluse_ds,
		cm: moduluse_cm,
	   
	    bbar: new Ext.PagingToolbar({
		    pageSize: 50,
		    store: moduluse_ds,
			displayInfo: true,
			displayMsg: '显示第{0}条到{1}条记录,一共{2}条',
			emptyMsg: '没有记录'
	    })
	});
    
    var moduluse_win = new Ext.Window({
    	title: '系数',
        applyTo:'moduluse_win',
       	width:550,
    	height:400,
        closeAction:'hide',
        layout: 'border',
        items: [moduluse_form, moduluse_grid]
    });
    
    window.show_relation = function(id) {
		var relationwin = new Ext.Window({                            
   			title:'关联',                          
   			width:300,                            
   			height:100,                            
   			draggable:true,                           
   			modal : true,                            
   			html:"<iframe src='ad_relation.jsp?advbarId="+id+"' frameborder=\"0\" width=\"100%\" height=\"100%\"></iframe>"                        
   		});                
   		relationwin.show(); 
	};
    
	window.show_price = function(id) {
   		win = new Ext.Window({                            
   			title:'刊例',     
   			width:500,                            
   			height:500,                            
   			draggable:true,                           
   			constrain:true,//将拖动范围限制在容器内                            
   			autoDestroy:false,                            
   			closeAction:'hide',                            
   			modal : true,                            
   			html:"<iframe src='advprice.jsp?barId="+id+"' frameborder=\"0\" width=\"100%\" height=\"100%\"></iframe>"                        
   		});                
   		win.show(); 
   	};
	
	new Ext.Viewport({
		layout: 'border',
		items:[
			searchForm,grid
		]
	});
});