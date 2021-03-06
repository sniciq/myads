Ext.onReady(function() {
	function getNodeChildrenIds(node) {
		var text = '';
		if(node.isLeaf()) {
			return node.id;
		}
		else {
			var childrens = node.childNodes;
			for(var i = 0; i < childrens.length; i++) {
				text += getNodeChildrenIds(childrens[i]) + ',';
			}
		}
		return text;
	}
	
	function getNodeChildrenTexts(node) {
		var text = '';
		if(node.isLeaf()) {
			return node.text;
		}
		else {
			var childrens = node.childNodes;
			for(var i = 0; i < childrens.length; i++) {
				text += getNodeChildrenTexts(childrens[i]) + ',';
			}
		}
		return text;
	}
	
	function addNodeToTree(node, desTree) {
		var a = desTree.getNodeById(node.id);
		if(a != null)
			a.remove();
		
		if(!node.isLeaf()) {//不是叶子结点，将该结点全部复制
			node.reload(function() {
				var nNode = node.clone();
				desTree.root.appendChild(nNode);
			});
		}
		else {
			var newNode = new Ext.tree.TreeNode({id:node.id, text:node.text});
			newNode.leaf = true;
			desTree.root.appendChild(newNode);
		}
	}
	
	var dqArea;
	var areaWin;
	var areaSplitePanel;
	var allAreaTreeRoot;
	var selAreaTreeRoot = new Ext.tree.TreeNode({id:'0', text:'root'});
	function openAreaWin() {//地区选择对话框
		if(!areaWin) {
			allAreaTreeRoot = new Ext.tree.AsyncTreeNode({
				id : '0',
				text: 'root'
			});
			
			var allAreaTree = new Ext.tree.TreePanel({
				rootVisible:false,
				region: 'west',
				margins:'3 0 3 3',
		        cmargins:'3 3 3 3',
				width:220,
				border : false,
				autoScroll : true, 
				loader: new Ext.tree.TreeLoader({dataUrl: '/myads/HTML/webdata/HotFilmDataAction_getAreaCityList.action'}),
				listeners: {  
					'dblclick':function(node,e){ 
						addNodeToTree(node, selAreaTree);
					}
				}
			});
			allAreaTree.setRootNode(allAreaTreeRoot);
			
			var splitP = new Ext.Panel({
				region: 'center',
				width: 30,
				collapsible: false,
				layout: {
                    type:'vbox',
                    padding:'5',
                    pack:'center',
                    align:'center'
                },
                defaults:{margins:'0 0 5 0'},
				items: [
					new Ext.Button({
						text: '&nbsp;&nbsp;>>&nbsp;&nbsp;',
				        handler: function(){
				        	var node = allAreaTree.getSelectionModel().getSelectedNode();
							addNodeToTree(node, selAreaTree);
				        }
					}),
					new Ext.Button({
						text: '&nbsp;&nbsp;<<&nbsp;&nbsp;',
						handler: function(){
				          	var node = selAreaTree.getSelectionModel().getSelectedNode();
				        	node.remove();
				        }
					})
				]
			});
			
			var selAreaTree = new Ext.tree.TreePanel({
				rootVisible:false,
				region: 'east',
				width: 220,
				listeners: {  
					'dblclick':function(node,e){ 
						node.remove();
					}
				}
			});
			selAreaTree.setRootNode(selAreaTreeRoot);
			
			var areaOp = new Ext.Panel({
				region: 'south',
				buttons: [
					{
						text: '确定',
						handler: function() {
							var textStr = getNodeChildrenTexts(selAreaTreeRoot);
							var idsStr = getNodeChildrenIds(selAreaTreeRoot);
							dqArea.setValue(textStr);
							searchForm.getForm().findField("cityDcodeList").setValue(idsStr);
							areaWin.hide();
						}
					},
					{
						text: '重置',
						handler: function() {
							selAreaTreeRoot.removeAll();
						}
					}
				]
			});
			
			areaWin = new Ext.Window({
				title: '地区选择',
		        applyTo:'areaWin',
		        layout:'fit',
		        width:520,
	        	height:400,
		        closeAction:'hide',
		        plain: true,
		        layout: 'border',
		        items: [allAreaTree, splitP, selAreaTree, areaOp]
		    });
		}
	    areaWin.show();
	}
	
	var searchForm = new Ext.form.FormPanel({
		labelAlign: 'right',
		region: 'north',
		labelWidth: 90,
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
								name: 'startTime',
								allowBlank:false,
								editable : false,
								format:'Y-m-d',
								anchor : '99%'
							})
						]
					},
		            {	columnWidth: .34, layout: 'form',
		            	items: [
		            	      new Ext.form.DateField({
								fieldLabel: '结束日期',
								name: 'endTime',
								allowBlank:false,
								editable : false,
								format:'Y-m-d',
								anchor : '99%'
							})
		            	]
		            },
		            {	columnWidth: .33, layout: 'form',
		            	items: [
		            	       new Ext.form.ComboBox({
		            	       		fieldLabel: '周期选择',
									name: 'dataType',
									emptyText: '选择',
									allowBlank : false,
									mode: 'local',
									triggerAction: 'all', 
									editable: false,
									valueField: 'value',
									displayField: 'text',
									anchor : '99%',
									store: new Ext.data.SimpleStore({
										fields: ['value', 'text'],
										data: [['每天详细', '每天详细'],['周期平均', '周期平均']]
									})
		            	       })
		            	]
		            }
				]
			},
			{
				layout: 'column',
				items: [
			        {	
						columnWidth: .01, layout: 'form',
		            	items: [
		            	        { xtype: 'hidden', name: 'cityDcodeList', hidden:true, hiddenLabel:true}
		            	] 
			        }
				]
			},
			{
				layout: 'column',layout: 'form',
				items: [
				        { xtype: 'textfield', name: 'filmName', fieldLabel: '热播剧名称 ', anchor : '100%'}
				]
			},
			{
				layout: 'column',
				items: [
					{
						columnWidth: 1, layout: 'form',
						items: [new Ext.Button({
							text: '选择地区',
					        fieldLabel: '地区选择 ',
					        handler: function(){
					           openAreaWin();
					        }
						})]
					}
				]
			},
			{
				layout: 'column',layout: 'form',
				items: [
					dqArea = new Ext.form.TextArea({
						height: 25, width:'100%', name:'areaNameList', emptyText: '默认全部地区',disabled:true
					})
				]
			}
		],
		buttons: [
			{
				text: '查询',
				handler: function() {
					if(!searchForm.getForm().isValid())
						return;
					var fv = searchForm.getForm().getValues();
					ds.baseParams= fv;
					ds.load({params: {start:0, limit:20}});
				}
			},
			{
				text: '重置',
				handler: function() {
					searchForm.form.reset();
				}
			}
		]
		
	});
	
	searchForm.render('searchForm');
	
	var cm = new Ext.grid.ColumnModel([
		{header:'剧名', dataIndex:'filmName', sortable:true},
		{header:'剧集', dataIndex:'videoName', sortable:true},
	    {header:'日期', dataIndex:'day_time', sortable:true},
	    {header:'省份', dataIndex:'province', sortable:true},
	    {header:'城市', dataIndex:'city', sortable:true},
	    {header:'PV', dataIndex:'pv', sortable:true},
	    {header:'UV', dataIndex:'uv', sortable:true}
	]);
	
	function renderOp(value, cellmeta, record, rowIndex, columnIndex, stor) {
		var filmName = record.data.filmName;
		var videoName = record.data.videoName;
		return filmName + ' | ' + videoName;
	}
	
	var ds = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({url: '/myads/HTML/webdata/HotFilmDataAction_search.action'}),
		remoteSort: true,
		reader: new Ext.data.JsonReader(
			{
				totalProperty: 'total',
				idProperty:'id',
				root: 'invdata'
			},
			[
				{name: 'id'},
				{name: 'filmName'},
				{name: 'videoName'},
				{name: 'province'},
				{name: 'city'},
				{name: 'pv'},
				{name: 'uv'},
				{name: 'day_time'}
			]
		)
	});
	
	var grid = new Ext.grid.GridPanel({
		el: 'grid',
		region: 'center',
		ds: ds,
		cm: cm,
		loadMask: {msg:'正在加载数据，请稍侯……'},
	    height:250,
	    viewConfig: {
	    	forceFit: true
	    },
	    bbar: new Ext.PagingToolbar({
		    pageSize: 20,
		    store: ds,
			displayInfo: true,
			displayMsg: '显示第{0}条到{1}条记录,一共{2}条',
			emptyMsg: '没有记录',
			items: [
				'-',
				{
					text: '导出EXCEL',
					handler: function() {
						if(ds.getCount() == 0)
   							return;
   							
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
						
						window.location.href = '/myads/HTML/webdata/HotFilmDataAction_export.action?exp_name=hotdata.xls&exp_column_names=' + encodeURI(encodeURI(strColoumnNames)) 
							+ '&exp_column_indexs=' + strColoumnIndexs 
							+ '&' + encodeURI(searchForm.getForm().getValues(true)); 
					}
				}
		    ]
	    })
	});
	
	grid.render();
	
	var mainViewPort = new Ext.Viewport({
		layout: 'border',
		items:[
		       searchForm, grid
		]
	});
});