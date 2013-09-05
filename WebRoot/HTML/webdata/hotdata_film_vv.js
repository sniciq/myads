Ext.onReady(function() {
	//频道选择对话框----------------------------------开始---------------------------------------------------------------
	var pdWin;
	var splitePanel;
	var prodNameRoot;
	var prodNameTree;
	var selChanelTree;
	var selTreeRoot;
	
	function openPdWin() {//频道选择对话框
		if(!pdWin) {
			prodNameRoot = new Ext.tree.AsyncTreeNode({
				id : '0',
				text: 'root'
			});
			
			prodNameTree = new Ext.tree.TreePanel({
				rootVisible:false,
				region: 'west',
				margins:'3 0 3 3',
		        cmargins:'3 3 3 3',
				width:220,
				border : false,
				autoScroll : true, 
				loader: new Ext.tree.TreeLoader({dataUrl: '/myads/HTML/webdata/HotFilmDataVVAction_getProndList.action'}),
				loadMask: {msg:'正在加载数据，请稍侯……'},
				listeners: {  
					'dblclick':function(node,e){ 
						selTreeRoot.appendChild(node);
					}
				}
			});
			prodNameTree.setRootNode(prodNameRoot);
			
			splitePanel = new Ext.Panel({
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
				        	var node = prodNameTree.getSelectionModel().getSelectedNode();
				        	selTreeRoot.appendChild(node);
				        }
					}),
					new Ext.Button({
						text: '&nbsp;&nbsp;<<&nbsp;&nbsp;',
						handler: function(){
				          	var node = selChanelTree.getSelectionModel().getSelectedNode();
				        	prodNameRoot.appendChild(node);
				        }
					})
				]
			});
			
			selTreeRoot = new Ext.tree.TreeNode({id:'0', text:'root'});
			selChanelTree = new Ext.tree.TreePanel({
				rootVisible:false,
				region: 'east',
				margins:'3 0 3 3',
		        cmargins:'3 3 3 3',
				width:220,
				border : false,
				autoScroll : true,
				listeners: {  
					'dblclick':function(node,e){ 
						prodNameRoot.appendChild(node);
					}
				}
			}); 
			selChanelTree.setRootNode(selTreeRoot);
			
			var opPanel = new Ext.Panel({
				region: 'south',
				layout: {
                    type:'hbox',
                    padding:'5',
                    pack:'center',
                    align:'center'
                },
				buttons: [
					{
						text: '确定',
						handler: function() {
							var nodes = selTreeRoot.childNodes;
							var nodesStr = '';
							var nodeIdStr = '';
							for (var i = 0; i < nodes.length; i++) {  
								nodesStr += nodes[i].text + ',';
								nodeIdStr += nodes[i].id + ',';
							}
							pdArea.setValue(nodesStr);
							searchForm.getForm().findField("prodIdList").setValue(nodeIdStr);
							pdWin.hide();
						}
					},
					{
						text: '重置',
						handler: function() {
							prodNameTree.root.reload();
							selTreeRoot.removeAll();
//							pdWin.hide();
						}
					}
				]
			});
			
			pdWin = new Ext.Window({
				title: '频道选择',
		        applyTo:'pdWin',
		        layout:'fit',
		        width:520,
            	height:400,
		        closeAction:'hide',
		        plain: true,
		        layout: 'border',
		        items: [prodNameTree, splitePanel, selChanelTree, opPanel]
		    });
		}
		pdWin.show();
	}
	//频道选择对话框-------------------------------------结束------------------------------------------------------------
	
	//地区选择对话框-------------------------------------开始------------------------------------------------------------
	
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
	//地区选择对话框-------------------------------------结束------------------------------------------------------------
	
	var searchForm = new Ext.form.FormPanel({
		labelAlign: 'right',
		region: 'north',
		labelWidth: 80,
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
								editable : false,
								allowBlank:false,
								format:'Y-m-d',
								anchor : '95%'
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
									editable: false,
									store: new Ext.data.SimpleStore({
										fields: ['value', 'text'],
										data: [['每天详细', '每天详细'],['周期平均', '周期平均']]
									})
		            	       })
		            	]
		            }
				]
			},
			{//行2
				layout: 'column',columnWidth: .5,
				items: [
					{	
						columnWidth: 1, layout: 'form',
		            	items: [
		            	        { xtype: 'textfield', name: 'filmName', fieldLabel: '内容项选择 ',anchor : '99%'}
		            	]
		            }
				]
			},
			
//			{
//				layout: 'column',
//				items: [
//					new Ext.form.CheckboxGroup({
//						columns : 5, 
//						items: [
//							{boxLabel:'站内',inputValue:'1',name:'chanel',fieldLabel:'播放器选择', checked:true}, 
//							{boxLabel:'站外',inputValue:'2',name:'chanela', checked:true},
//							{boxLabel:'剧场',inputValue:'3',name:'asd', checked:true},
//							{boxLabel:'网吧',inputValue:'4',name:'chaneasdla', checked:true},
//							{boxLabel:'极速酷六',inputValue:'5',name:'sdfsd', checked:true}
//						]
//					}),
//					{
//						columnWidth: .75
//					}
//				]
//			},
			
			{
				layout: 'column',
				items: [
					{
						columnWidth: 1, layout: 'form',
						items: [
							new Ext.Button({
								text: '选择频道',
						        fieldLabel: '频道选择 ',
						        handler: function(){
						           openPdWin();
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
		            	columnWidth: 1, layout: 'form',
		            	items: [
			            	pdArea = new Ext.form.TextArea({
								height: 25, width:'100%', name:'prodNameList', emptyText: '默认全部频道',disabled:true
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
			},
			{
				layout: 'column',
				items: [
			        {	
						columnWidth: .01, layout: 'form',
		            	items: [
		            	        { xtype: 'hidden', name: 'cityDcodeList', hidden:true, hiddenLabel:true},
		            	        { xtype: 'hidden', name: 'prodIdList', hidden:true, hiddenLabel:true}
		            	] 
			        }
			        
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
	
	function renderOp(value, cellmeta, record, rowIndex, columnIndex, stor) {
		var id = record.data.id;
		var dataValue = record.data.dataValue; 
		var editStr = '<img alt=\"编辑\" src=\"/myads/HTML/images/edit.gif\" style=\"cursor: pointer;\" onclick=\"showInfo(\''+id+'\');\">';
		var delStr = '<img alt=\"删除\" src=\"/myads/HTML/images/del.gif\" style=\"cursor: pointer;\" onclick=\"deleteBaseData(\''+id+'\', \'' + dataValue + '\');\">';
		return editStr + '&nbsp;&nbsp;&nbsp;&nbsp;' + delStr;
	}
	
	var cm = new Ext.grid.ColumnModel([
		{header:'日期', dataIndex:'day_time', sortable:true},
   	   	{header:'剧名', dataIndex:'filmName', sortable:true},
   	   	{header:'剧集', dataIndex:'videoName', sortable:true},
	    {header:'频道', dataIndex:'prod_name', sortable:true},
	    {header:'省份', dataIndex:'province', sortable:true},
	    {header:'城市', dataIndex:'city', sortable:true},
	    {header:'VV', dataIndex:'vv', sortable:true}
   	]);
   	
   	var ds = new Ext.data.Store({
   		proxy: new Ext.data.HttpProxy({url: '/myads/HTML/webdata/HotFilmDataVVAction_search.action'}),
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
				{name: 'prod_name'},
				{name: 'vv'},
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
   						
   						
   						window.location.href = '/myads/HTML/webdata/HotFilmDataVVAction_export.action?exp_name=vv.xls&exp_column_names=' + encodeURI(encodeURI(strColoumnNames)) 
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