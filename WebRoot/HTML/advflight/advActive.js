Ext.onReady(function() {
	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget='side';
	var dataTypeCombStore;
	var searchForm = new Ext.form.FormPanel({
		labelAlign: 'right',
		region: 'north',
		labelWidth: 70,
		frame: true,
		items: [
			{
				layout: 'column',
				items: [
					{
						columnWidth: .33, layout: 'form',
						items: [{
							xtype : 'textfield',
							name : 'projectId',
							fieldLabel : '执行单ID',
							anchor : '95%',
							regex : /^[0-9]*[1-9][0-9]*$/,
							regexText : "只能输入数字"
							
						}]
					},
		            {
						columnWidth: .33, layout: 'form',
						items: [{
							xtype : 'textfield',
							name : 'projectName',
							fieldLabel : '执行单名称',
							anchor : '95%'
						}]
					}
				]
			}
		],
		buttons: [
			{
				text: '查询',
				handler: function() {
				var fv = searchForm.getForm().getValues();
				ds.baseParams = fv;
				
				ds.load({
					params : {
							start : 0,
							limit : 15
						}
					});
				}
			},{
				text: '重置',
				handler: function() {
					searchForm.form.reset();
				}
			}
//			,
//			{
//				text: '新建广告活动',
//				handler: function() {
//					showAddAdvActive();
//				}
//			}
		]
	});
	searchForm.render('north-div');
	//判断状态
	function rendStauts(value){
	
		if(value==0){
			return "<span style='color:GREEN'>启用</span>";
		}else if(value==1){
			return "<span style='color:GRAY'>已删除</span>";
		}else if(value==2){
			return "<span style='color:RED'>停用</span>";
		}
	}
	var sm = new Ext.grid.CheckboxSelectionModel();
	var cm = new Ext.grid.ColumnModel([ 
		new Ext.grid.RowNumberer(),
		sm,
		{header:'ID', dataIndex:'id', sortable:true},
	    {header:'广告活动名称', dataIndex:'name', sortable:true},
   	    {header:'执行单名称', dataIndex:'projectName', sortable:true},
   	    {header:'执行单ID', dataIndex:'projectId', sortable:true},
   	    {header:'状态', dataIndex:'status', 	renderer:rendStauts,sortable:true},
   	    {header:'创建日期', dataIndex:'createTime', sortable:true},
   	    {header:'操作', dataIndex:'op', renderer: renderOp,width:100, align:'left'}
	]);
   	
   	function renderOp(value, cellmeta, record, rowIndex, columnIndex, stor) {
   		var id = record.data.id;
		var name = record.data.name;
		var delStr = '<a href="#" onclick=\"deleteAdvActive(\''+ id + '\', \'' + name + '\');">删除</a>';
		var showInfo = '<a href="#" onclick=\"showInfo(\''+ id + '\', \'' + name + '\');">详细信息</a>';
		var advMangerStr = '<a href="#" onclick=\"ShowAdvMangerStr(\''+ id + '\', \'' + name + '\');">广告管理</a>';
				
		return advMangerStr +'&nbsp;&nbsp;&nbsp;&nbsp;'+delStr;
   	}
   	
	window.deleteAdvActive = function(id, name) {
		Ext.MessageBox.confirm('提示', '确定删除  ' + name + ' ？', function(btn) {
			if (btn == 'yes') {
				var param = [];
				param.push(id);
				Ext.Ajax.request({
					method : 'post',
					url : '/myads/HTML/advflight/AdvActiveAction_delete.action',
					success : function(resp) {
						var obj = Ext.util.JSON.decode(resp.responseText);
						if (obj.result == 'success') {
							grid.getStore().reload();
							dataTypeCombStore.load();
							Ext.MessageBox.alert('提示', '删除成功！');
						}
					},
					params : {
						advActiveList : param.join(',')
					}
				});
			}
		});
	};
	
	window.ShowAdvMangerStr = function(id, name) {
		window.location.href='/myads/HTML/advflight/advertisement.jsp?advActiveId='+id;
	};
	window.showInfo = function(id, name) {
		window.location.href='/myads/HTML/welcome.html?advActiveId='+id;
	};
   	
   	var ds = new Ext.data.Store({
   		proxy: new Ext.data.HttpProxy({
   			url: '/myads/HTML/advflight/AdvActiveAction_showAll.action'
   				}),
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
   				{name: 'status'},
   				{name: 'projectId'},
   				{name: 'projectName'},
   				{name: 'createTime'}
   			]
   		)
   	});

   	ds.load({params: {start:0, limit:15}});
   	
   
   	var grid = new Ext.grid.GridPanel({
   		el: 'grid',
   		region: 'center',
   		ds: ds,
   		sm: sm,
   		cm: cm,
   	    viewConfig: {
   	    	forceFit: true
   	    },
		bbar : new Ext.PagingToolbar({
			pageSize : 15,
			store : ds,
			displayInfo : true,
			displayMsg : '显示第{0}条到{1}条记录,一共{2}条',
			emptyMsg : '没有记录',
			items : ['-', {
				text : '删除',
				handler : function() {
					var rs = grid.getSelectionModel().getSelections();
					var param = [];
					Ext.each(rs, function() {
								param.push(this.get("id"));
							});

				if(param.length>0){
					Ext.MessageBox.confirm("提示", "确定删除？",
							function(id) {
								if (id == 'yes') {
									Ext.Ajax.request({
										method : 'post',
										url : '/myads/HTML/advflight/AdvActiveAction_delete.action',
										success : function(resp) {
											var obj = Ext.util.JSON.decode(resp.responseText);
											if (obj.result == 'success') {
												grid.getStore().reload();
												Ext.MessageBox.alert('提示',
														'删除成功！');
											}
										},
										params : {
											advActiveList : param.join(',')
										}
									});
								} else {
								}
							});
					}else{
						Ext.MessageBox.alert('提示', '请选择要删除的广告活动.');
					}
				}
			}]
		})
   	});
   	
   	new Ext.Viewport({
		layout: 'border',
		items:[
			searchForm,grid
		]
	});
   	//显示添加广告活动
   	window.showAddAdvActive = function(){
   		var dataTypeCombStore = new Ext.data.Store({
   			proxy : new Ext.data.HttpProxy({
   						url : '/myads/HTML/advflight/ProjectAction_getProjectList.action'
   						
   					}),
   			reader : new Ext.data.ArrayReader({}, [{
   								name : 'value'
   							}, {
   								name : 'text'
   							}])
   		});
   		dataTypeCombStore.load();
   		
   		var addAdvActiveForm = new Ext.form.FormPanel({
   			labelAlign : 'right',
   			labelWidth : 80,
   			height : 160,
   			frame : true,
   			xtype : 'fieldset',
   			items : [

   			{
   						xtype : 'textfield',
   						name : 'name',
   						fieldLabel : '广告活动名称',
   						allowBlank : false,
   						anchor : '95%',
   						maxLength:80,
						maxLengthText:'输入文本过长'
   					}, {
   						xtype : 'hidden',
   						name : 'id',
   						allowBlank : false,
   						fieldLabel : '广告活动Id'			
   					},{

   						fieldLabel : '执行单',
   						xtype : 'combo',
   						hiddenName : 'projectId',
   						store : dataTypeCombStore,
   						emptyText : '选择',
   						mode : 'local',
   						triggerAction : 'all',
   						valueField : 'value',
   						displayField : 'text',
   						anchor : '95%'
   					}],
   			buttons : [{
   				text : '保存',
   				handler : function() {
   				addAdvActiveForm.form.doAction('submit', {
   						url : '/myads/HTML/sysfun/AdvActiveAction_save.action',
   						method : 'post',
   						params : '',
   						success : function(form, action) {
   							Ext.MessageBox.alert('结果', '保存成功！');
   							form.reset();
   							grid.getStore().reload();
   							dataTypeCombStore.load();
   							
   						}
   					});
   				}
   			}, {
   				text : '取消',
   				handler : function() {
   					addAdvActiveForm.form.reset();
   					WinAdd.hide();
   				}
   			}]

   		});
   		var WinAdd = new Ext.Window({
   			width : 500,
   			height : 200,
   			modal : true,
   			title : "编辑广告活动",
   			closeAction:'hide',
   			items : addAdvActiveForm	
   								
   		});
   		
   		WinAdd.show();
   	};
   	
	

   	
});