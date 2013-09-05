Ext.onReady(function() {
	var filmId = Ext.get('filmId').dom.value;
	
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
            	        { xtype: 'textfield', name: 'videoName', fieldLabel: '名称 ',anchor:'95%'}
            	]
            },
            {	
    			layout: 'form',
    			items: [
            	        { xtype: 'textfield', name: 'vid', fieldLabel: 'VID ',anchor:'95%'}
            	]
            	
            },
            {	layout: 'form',
            	items: [
            	        { xtype: 'textfield', name: 'remark', fieldLabel: '备注 ',anchor:'95%'}
            	]
            }
		],
		buttons: [
			{
				text: '保存',
				handler: function() {
					editForm.form.doAction('submit', {
						url: '/myads/HTML/basic/HotFilmVideoAction_save.action',
						method: 'post',
						params: {hotfilmId: filmId},
						success: function(form, action) {
							ds.load({params: {start:0, limit:20, hotfilmId: filmId}});
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
					formWin.hide();
				}
			}
		]
    });
    
     var formWin = new Ext.Window({
		title: '编辑',
        applyTo:'editFormWin',
        layout:'fit',
        width:450,
    	height:160,
        closeAction:'hide',
        plain: true,
        layout: 'border',
        items: [editForm]
    });
     
	var searchForm = new Ext.FormPanel({
    	labelWidth: 90,
		frame: true,
		region: 'north',
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
	searchForm.render('north-div');
	
	function renderOp(value, cellmeta, record, rowIndex, columnIndex, stor) {
		var id = record.data.id;
		var dataValue = record.data.videoName; 
		var editStr = '<img alt=\"编辑\" src=\"/myads/HTML/images/edit.gif\" style=\"cursor: pointer;\" onclick=\"showInfo(\''+id+'\');\">';
		var delStr = '<img alt=\"删除\" src=\"/myads/HTML/images/del.gif\" style=\"cursor: pointer;\" onclick=\"deleteById(\''+id+'\', \'' + dataValue + '\');\">';
		return editStr + '&nbsp;&nbsp;&nbsp;&nbsp;' + delStr;
	}
	
	window.showInfo = function(id) {
		editForm.load({
			url: '/myads/HTML/basic/HotFilmVideoAction_showDetail.action',
			params: {id: id}
		});
		formWin.show();
	};
	
	window.deleteById = function(id, dataValue) {
		Ext.MessageBox.confirm('提示', '确定删除  ' + dataValue + ' ？', function(btn) {
			if(btn == 'yes') {
				var param = [];
				Ext.Ajax.request({
					method: 'post',
					url: '/myads/HTML/basic/HotFilmVideoAction_deleteById.action',
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
	
	var cm = new Ext.grid.ColumnModel([
 	    {header:'名称', dataIndex:'videoName', sortable:true},
 	    {header:'VID', dataIndex:'vid', sortable:true},
 	    {header:'备注', dataIndex:'remark', sortable:true},
 	    {header:'操作', dataIndex:'op', renderer: renderOp,width:100, align:'left'}
 	]);

  	var ds = new Ext.data.Store({
  		proxy: new Ext.data.HttpProxy({
  			url: '/myads/HTML/webdata/HotFilmVideoAction_search.action'
  		}),
  		remoteSort: true,
  		reader: new Ext.data.JsonReader(
  			{
  				totalProperty: 'total',
  				idProperty:'id',
  				root: 'invdata'
  			},
  			[
  				{name: 'id'},
  				{name: 'videoName'},
  				{name: 'vid'},
  				{name: 'remark'},
  				{name: 'hotfilmId'}
  			]
  		)
  	});
  	
  	ds.baseParams = {hotfilmId: filmId};
    ds.load({params: {start:0, limit:20}});
  	
	var grid = new Ext.grid.GridPanel({
		el: 'videoGrid',
		region: 'center',
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
			emptyMsg: '没有记录'
	    })
	});
	
	grid.addListener('rowdblclick', function(grid, rowindex, e) {
		var record = grid.getStore().getAt(rowindex);
		editForm.load({
			url: '/myads/HTML/basic/HotFilmVideoAction_showDetail.action',
			params: {id: record.id}
		});
		formWin.show();
	});
	
	var mainViewPort = new Ext.Viewport({
		layout: 'border',
		items:[
		       searchForm, grid
		]
	});
});