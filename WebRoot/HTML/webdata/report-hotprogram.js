Ext.onReady(function() {
	var searchForm = new Ext.FormPanel({
		frame: true,
		region: 'north',
		labelAlign: 'right',
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
						name: 'name'
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
	searchForm.render('searchForm');
	
    var editForm = new Ext.FormPanel({
    	labelAlign: 'right',
		region: 'center',
		labelWidth: 50,
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
			 {	columnWidth: .25, layout: 'form',
            	items: [
            	        { xtype: 'textfield', name: 'name', fieldLabel: '名称 ',anchor : '95%'}
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
						url: '/myads/HTML/basic/HotProgramAction_save.action',
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
    	height:140,
        closeAction:'hide',
        plain: true,
        layout: 'border',
        items: [editForm]
    });
    
	var cm = new Ext.grid.ColumnModel([
   	    new Ext.grid.RowNumberer(),
   	    {header:'ID', dataIndex:'id', sortable:true, width: 20},
   	    {header:'名称', dataIndex:'name', sortable:true},
   	    {header:'操作', dataIndex:'op', renderer: renderOp,width:100, align:'left'}
   	]);
	
	function renderOp(value, cellmeta, record, rowIndex, columnIndex, stor) {
		var id = record.data.id;
		var dataValue = record.data.name; 
		var editStr = '<img alt=\"编辑\" src=\"/myads/HTML/images/edit.gif\" style=\"cursor: pointer;\" onclick=\"showInfo(\''+id+'\');\">';
		var delStr = '<img alt=\"删除\" src=\"/myads/HTML/images/del.gif\" style=\"cursor: pointer;\" onclick=\"deleteBaseData(\''+id+'\', \'' + dataValue + '\');\">';
		return editStr + '&nbsp;&nbsp;&nbsp;&nbsp;' + delStr;
	}
	
	window.deleteBaseData = function(id, dataValue) {
		Ext.MessageBox.confirm('提示', '确定删除  ' + dataValue + ' ？', function(btn) {
			if(btn == 'yes') {
				var param = [];
				Ext.Ajax.request({
					method: 'post',
					url: '/myads/HTML/basic/HotProgramAction_deleteById.action',
				   	success:function(resp){
				    	var obj=Ext.util.JSON.decode(resp.responseText);
				      	if(obj.result == 'success') {
				      		grid.getStore().reload();
				      		Ext.MessageBox.alert('提示', '删除成功！');
				      	}
				    },
				   	params: {id:id}
				});
			}
		});
	};
	
	window.showInfo = function(id) {
		editForm.load({
			url: '/myads/HTML/basic/HotProgramAction_showDetail.action',
			params: {id: id}
		});
		formWin.show();
	}
	
   	var ds = new Ext.data.Store({
   		proxy: new Ext.data.HttpProxy({url: '/myads/HTML/webdata/HotProgramAction_search.action'}),
   		remoteSort: true,
   		reader: new Ext.data.JsonReader(
   			{
   				totalProperty: 'total',
   				idProperty:'id',
   				root: 'invdata'
   			},
   			[
   				{name: 'id'},
   				{name: 'name'}
   			]
   		)
   	});
   	
    ds.load({params: {start:0, limit:20}});
   	
	var grid = new Ext.grid.GridPanel({
		el: 'grid',
		region: 'center',
		ds: ds,
		cm: cm,
		loadMask: {msg:'正在加载数据，请稍侯……'},
	    height:400,
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
						window.location.href = '/myads/HTML/webdata/HotProgramAction_search.action?exp_name=hotprogram.xls&exp_column_names=' + encodeURI(encodeURI(strColoumnNames)) + '&exp_column_indexs=' + strColoumnIndexs;
					}
				}
		    ]
	    })
	});
	
	grid.render();
	
	var mainViewPort = new Ext.Viewport({
		layout: 'border',
		items:[
		       grid, searchForm
		]
	});
});