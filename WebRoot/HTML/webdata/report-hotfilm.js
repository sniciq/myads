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
						editable : false,
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
						editable : false,
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
							allowBlank:false,
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
				text: '重置',
				handler: function() {
					editForm.form.reset();
				}
			}
		]
    });
    
     var formWin = new Ext.Window({
		title: '编辑',
        applyTo:'editFormWin',
        layout:'fit',
        width:450,
    	height:220,
        closeAction:'hide',
        plain: true,
        layout: 'border',
        items: [editForm]
    });
    
    var searchForm = new Ext.FormPanel({
    	labelWidth: 90,
		frame: true,
		labelAlign: 'right',
		height:50,
		items: [
			{
				layout:'hbox',
				layoutConfig: {
                    align:'middle'
                },
				defaults:{margins:'0 10 0 0'},
				items: [
					new Ext.form.Label({
						text: '名称'
					}),
					new Ext.form.TextField({
						layout: 'form',
						name: 'film_name'
					}),
					new Ext.Button({
						text: '查询',
						width: 70,
						handler: function() {
							var fv = searchForm.getForm().getValues();
							ds.baseParams= fv;
							ds.load({params: {start:0, limit:20}});
						}
					}),
					new Ext.Button({
						text: '重置',
						width: 70,
						handler: function() {
							searchForm.form.reset();
							ds.baseParams= {};
							ds.load({params: {start:0, limit:20}});
						}
					}),
					new Ext.Button({
						text: '新增',
						width: 70,
						handler: function() {
							editForm.form.reset();
							formWin.show();
						}
					})
				]
			}
		]
	});
	
	function renderOp(value, cellmeta, record, rowIndex, columnIndex, stor) {
		var id = record.data.id;
		var dataValue = record.data.film_name; 
		var editStr = '<img alt=\"编辑\" src=\"/myads/HTML/images/edit.gif\" style=\"cursor: pointer;\" onclick=\"showInfo(\''+id+'\');\">';
		var delStr = '<img alt=\"删除\" src=\"/myads/HTML/images/del.gif\" style=\"cursor: pointer;\" onclick=\"deleteBaseData(\''+id+'\', \'' + dataValue + '\');\">';
		var addStr = '<img alt=\"详细\" src=\"/myads/HTML/images/find.gif\" style=\"cursor: pointer;\" onclick=\"showVideos(\''+id+'\', \'' + dataValue + '\');\">';
		return editStr + '&nbsp;&nbsp;&nbsp;&nbsp;' + delStr + '&nbsp;&nbsp;&nbsp;&nbsp;' + addStr;
	}
	
	function renderState(value, cellmeta, record, rowIndex, columnIndex, stor) {
		var dataValue = record.data.isstate; 
		if(dataValue == 'true') {
			return '统计';
		}
		else {
			return '不统计';
		}
	}
	
	window.deleteBaseData = function(id, dataValue) {
		Ext.MessageBox.confirm('提示', '确定删除  ' + dataValue + ' ？', function(btn) {
			if(btn == 'yes') {
				var param = [];
				Ext.Ajax.request({
					method: 'post',
					url: '/myads/HTML/basic/HotFilmAction_deleteById.action',
				   	success:function(resp){
				    	var obj=Ext.util.JSON.decode(resp.responseText);
				      	if(obj.result == 'success') {
				      		grid.getStore().reload();
				      		Ext.MessageBox.alert('提示', '删除成功！');
				      	}
				      	else {
				      		Ext.MessageBox.alert('出错了！！！', '删除失败！！！！');
				      	}
				    },
				   	params: {id:id}
				});
			}
		});
	};
	
	window.showInfo = function(id) {
		editForm.load({
			url: '/myads/HTML/basic/HotFilmAction_showDetail.action',
			params: {id: id}
		});
		formWin.show();
	};
	
	window.showVideos = function(id, dataValue) {
		var n = tabs.add({
			title: dataValue + '相关视频',
			closable:true,
			html: '<iframe scrolling="auto" frameborder="0" width="100%" height="100%" src="report-hotfilm-video.jsp?filmId='+ id +'"></iframe>'
		});
		tabs.setActiveTab(n);
	}
	
	var cm = new Ext.grid.ColumnModel([
  	    new Ext.grid.RowNumberer(),
  	    {header:'名称', dataIndex:'film_name', sortable:true},
  	    {header:'开始时间', dataIndex:'time_start', sortable:true},
  	    {header:'结束时间', dataIndex:'time_end', sortable:true},
  	    {header:'状态', dataIndex:'stateText', sortable:true},
  	    {header:'操作', dataIndex:'op', renderer: renderOp,width:100, align:'left'}
  	]);
	
   	var ds = new Ext.data.Store({
   		proxy: new Ext.data.HttpProxy({url: '/myads/HTML/webdata/HotFilmAction_search.action'}),
   		remoteSort: true,
   		reader: new Ext.data.JsonReader(
   			{
   				totalProperty: 'total',
   				idProperty:'id',
   				root: 'invdata'
   			},
   			[
   				{name: 'id'},
   				{name: 'film_name'},
   				{name: 'time_start'},
   				{name: 'time_end'},
   				{name: 'isstate'},
   				{name: 'stateText'}
   			]
   		)
   	});
   	
    ds.load({params: {start:0, limit:20}});
   	
	var grid = new Ext.grid.GridPanel({
		flex:10,
       	margins:'0',
		ds: ds,
		cm: cm,
		loadMask: {msg:'正在加载数据，请稍侯……'},
	    viewConfig: {
	    	forceFit: true
	    },
	    bbar: new Ext.PagingToolbar({
		    pageSize: 30,
		    store: ds,
			displayInfo: true,
			displayMsg: '显示第{0}条到{1}条记录,一共{2}条',
			emptyMsg: '没有记录',
			items: [
				'-',
				{
					text: '导出EXCEL',
					handler: function() {
						var cms = grid.getColumnModel();
						var strColoumnNames = "";
						var strColoumnIndexs = "";
						for(var i = 0; i < cms.getColumnCount(); i++) {
							if(cms.getColumnHeader(i) == '' || cms.getColumnHeader(i) == '操作')
   								continue;
   								
							if (!cms.isHidden(i)) {
								strColoumnIndexs += cms.getDataIndex(i) + ',';
								strColoumnNames += cms.getColumnHeader(i) + ',';
							}
						}
						window.location.href = '/myads/HTML/webdata/HotFilmAction_search.action?exp_name=hotfilm.xls&exp_column_names=' 
							+ encodeURI(encodeURI(strColoumnNames)) 
							+ '&exp_column_indexs=' + strColoumnIndexs 
							+ '&' + searchForm.getForm().getValues(true); 
					}
				}
		    ]
	    })
	});
	
	grid.addListener('rowdblclick', function(grid, rowindex, e) {
		var record = grid.getStore().getAt(rowindex);
		editForm.load({
			url: '/myads/HTML/basic/HotFilmAction_showDetail.action',
			params: {id: record.id}
		});
		formWin.show();
	});
	
	var tabs = new Ext.TabPanel({
		renderTo: 'mainTab',
		frame:true,
		region:'center',
        activeTab: 0,
        layoutOnTabChange : true ,
        defaults:{autoScroll: true},
        deferredRender :false,
		items: [
			{
				title: '热播剧列表',
				layout: {
                    type:'vbox',
                    align:'stretch'
                },
	            items:[searchForm,grid]
			}
		]
	});
	
	var mainViewPort = new Ext.Viewport({
		layout: 'border',
		items:[tabs]
	});
	
});