Ext.onReady(function(){   
	 var advId = Ext.get('advId').dom.value;//跨页面拿到广告id
	 var advName = Ext.get('advName').dom.value;//跨页面拿到广告名称
	 var DoubleType;
	Ext.form.Field.prototype.msgTarget = 'side';// 统一指定错误信息提示方式
	var gridAdd;
    var myForm = new Ext.form.FormPanel({  
    	
		height : 120,
        region: 'north',
        frame : true,  
        layout: 'form',
        monitorValid:true,  
        fileUpload:true, // 需上传文件
        enctype:'multipart/form-data',
        url : '/myads/HTML/advflight/FileUploadAction_save.action',// 请求的url地址
        method:'POST',  
        items : [
        	{  
	            xtype : 'textfield',  
	            name : 'note',  
	            fieldLabel : '文件备注' 
	        }
	        ,
	        {  
	            xtype:'textfield',  
	            fieldLabel : '选择文件',  
	            name : 'upload',  
	            inputType : 'file',   // 关键
	            allowBlank:false

	      	}
	    ],  
		buttons : [
				{  
		            formBind:true,  
		            text : '上传',  
		            handler:function(){  
						myForm.form.submit({  
							clientValidation : true,// 进行客户端验证
							waitMsg : '正在上传文件,请稍等……',// 提示信息
							waitTitle : '提示',// 标题
							success : function(form, action) {// 加载成功的处理函数
							Ext.Msg.alert('提示', action.result.result);    
								myForm.form.reset();
								gridAdd.getStore().reload();
							}      
						});  
					}  
		        }, 
		        {  
		            text : '重置',  
		            handler:function(){  
		        		myForm.form.reset();
		            }  
		        }
		   ]  
    });
   
	 
	
	var searchForm = new Ext.form.FormPanel({
		labelAlign : 'right',
		labelWidth : 80,
		frame : true,
		xtype : 'fieldset',
		region: 'north',
		items : [{
					layout : 'column',
					items : []
				}, 	{
				layout: 'column',
				items: [
					new Ext.Button({
						text: '返回',
						width:70,
				        handler: function(){
				        	window.history.back();
				        }
					})
				]
			},
				{
						xtype : 'textfield',
						name : 'advName',
						fieldLabel : '所属广告 ',
						anchor : '24%',
						readOnly:true,
						value:advName
				}	,
				{
						xtype : 'hidden',
						name : 'advId',
						fieldLabel : '所属广告ID ',
						anchor : '24%',
						readOnly:true,
						value:advId
				}	
			]
		
	});
	searchForm.render('north-div');
	
	//TODO 添加物料
	function showUpload() {
		myForm.form.reset();	
		//addadvertiserForm.getForm().findField("status").setValue(0);
		winAddFile.show();
	}
	var winAddFile = new Ext.Window({
					width : 400,
					height : 155,
					modal : true,
					title : "上传物料",
					closeAction:'hide',
					items : myForm	
										
				});
	
	// TODO 判断状态
	function rendStauts(value){
		if(value==0){
			return "<span style='color:GREEN'>可用</span>";
		}else if(value==1){
			return "<span style='color:GRAY'>删除</span>";
		}else if(value==2){
			return "<span style='color:RED'>停用</span>";
		}
	}
	

	function renderOp(value, cellmeta, record, rowIndex, columnIndex, stor) {
		
		var id = record.data.id;
		var name = record.data.materialName;
		var delStr = '<a href="#" onclick=\"deleteAdvMaterial(\''+ id + '\', \'' + name + '\');">删除</a>';		
		return  delStr ;
	}
	
	
	//显示查询页面
	function renderAddMaterial(value, cellmeta, record, rowIndex, columnIndex, stor) {
		
		var materialId = record.data.materialId;
		var type = record.data.type;
		var id = record.data.id;
		var addStr = '<a href="#" onclick=\"addAdvMaterial(\''+ id+ '\', \'' + materialId + '\', \'' + type + '\');">关联物料</a>';		
		return  addStr ;
	}
	

	
	var sm = new Ext.grid.CheckboxSelectionModel();
	var cm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(), 
		
		{header : '关联物料',renderer : renderAddMaterial,width:70}, 
		{header : '物料类型',dataIndex : 'type',width:70}, 
		{header : '顺序号',dataIndex : 'sNumber',width:70}, 
		{header : '物料名称',dataIndex : 'materialName',width:150,sortable : true}, 
		{header : '路径',dataIndex : 'path',width:300},
		{header : '文字链/VID',dataIndex : 'textContent',width:150},
		{header : '物料备注',dataIndex : 'note'}, 
		{header : '物料创建人',dataIndex : 'creator'}, 
		{header : '物料创建时间',dataIndex : 'createTime'}, 
		{header : '操作',dataIndex : 'op',renderer : renderOp,width : 100,align : 'left'}
	]);

	var ds = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({url : '/myads/HTML/advflight/AdvMaterialAction_showAll.action'}),
		remoteSort : true,
		reader : new Ext.data.JsonReader(
			{
				totalProperty : 'total',
				idProperty : 'id',
				root : 'invdata'
			}, 
			[
				{name : 'id'},
				{name : 'materialId'}, 
				{name : 'materialName'}, 
				{name : 'path'}, 
				{name : 'type'}, 
				{name : 'sNumber'}, 
				{name : 'status'}, 
				{name : 'textContent'},
				{name : 'note'}, 
				{name : 'creator'}, 
				{name : 'createTime'}
			]
		)
	});

	ds.load({params : {start : 0,limit : 15,advId:advId}});

	var grid = new Ext.grid.GridPanel({
		el : 'grid',
		region: 'center',
		ds : ds,
		cm : cm,
		sm : sm,
		height : 300,
		viewConfig : {
			forceFit : true
		},
		bbar : new Ext.PagingToolbar({
			pageSize : 15,
			store : ds,
			displayInfo : true,
			displayMsg : '显示第{0}条到{1}条记录,一共{2}条',
			emptyMsg : '没有记录'
		})
	});
	grid.render();

	
	var smAdd = new Ext.grid.CheckboxSelectionModel({singleSelect:true});
	var cmAdd = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(), 
		smAdd, 
		{header : '名称',dataIndex : 'name',width:150,sortable : true}, 
		{header : '所属广告主',dataIndex : 'advertiserName',width:150}, 
//		{header : '路径',dataIndex : 'path',width:300,sortable : true},
		{header : '物料类型',dataIndex : 'meterialType'}, 
		{header : '备注',dataIndex : 'note'}, 
		{header : '物料状态',dataIndex : 'isSuccess',renderer : renderView,sortable:true, width:100},
		{header : '创建人',dataIndex : 'creator',sortable : true}, 
		{header : '创建时间',dataIndex : 'createTime',sortable : true}
//		, 
//		{header : '操作',dataIndex : 'op',renderer : renderOp,width : 100,align : 'left'}
	]);
	
	function renderView(value, cellmeta, record, rowIndex, columnIndex, stor){
			var dataValue = record.data.isSuccess;
			var viewStr = "";
			if(dataValue=="1"){
				viewStr = "处理完成" ;
			}else if(dataValue=="0"){
				viewStr = "处理失败" ;
			}else if(dataValue=="2"){
				viewStr = "转码失败" ;
			}else if(dataValue=="3"){
				viewStr = "分发失败" ;
			}else if(dataValue=="4"){
				viewStr = "处理中" ;
			}else{
				viewStr = "非法" ;
			}
			return viewStr;
	}
	
	//dsAdd.load({params : {start : 0,limit : 15,meterialType:type}});

	
	//TODO 显示添加物料关系
	window.addAdvMaterial = function(id,materialId, type) {
		
		//addForm.form.reset();
		//如果是文字链
		if(type=='text'){
		// 广告主列表
		var advertiserCombStore = new Ext.data.Store({
			proxy: new Ext.data.HttpProxy({url: '/myads/HTML/sysfun/AdvertiserAction_list.action'}),
			reader: new Ext.data.ArrayReader({}, [
				{name: 'value'},{name: 'text'}
			])
		});
		advertiserCombStore.load();
		  var addTextForm = new Ext.form.FormPanel({
					labelAlign : 'right',
					labelWidth : 80,
					height : 210,
					frame : true,
					xtype : 'fieldset',
					region: 'north',
					items : [
							{
								xtype : 'textfield',
								name : 'advName',
								fieldLabel : '所属广告 ',
								anchor : '95%',
								readOnly:true,
								value:advName
							},{
								xtype : 'hidden',
								name : 'advId',
								fieldLabel : '所属广告ID ',
								anchor : '95%',
								readOnly:true,
								value:advId
							},
							{
								xtype : 'hidden',
								name : 'id',
								fieldLabel : 'ID ',
								anchor : '95%',
								readOnly:true
									
							}
							,{
								xtype : 'textfield',
								name : 'name',
								fieldLabel : '物料名称 ',
								anchor : '95%',
								allowBlank : false,
								maxLength:20,
								maxLengthText:'输入文本过长'
								
							},{
								xtype : 'textfield',
								name : 'note',
								fieldLabel : '备注 ',
								anchor : '95%',
								maxLength:20,
								maxLengthText:'输入文本过长'
								
							}
							,{
								fieldLabel : '广告主',
								xtype : 'combo',
								allowBlank : false,
								editable : false,
								hiddenName : 'advertiserId',
								store : advertiserCombStore,
								mode : 'local',
								triggerAction : 'all',
								valueField : 'value',
								displayField : 'text',
								anchor : '95%'
							},
							{
								xtype : 'textarea',
								name : 'textContent',
								fieldLabel : '文字链内容/VID',
								anchor : '95%',
								height:80,
								allowBlank : false,
								blankText : "此项不能为空哦O(∩_∩)O~",
								maxLength:120,
								maxLengthText:'输入文本过长,120个字以内.'
							}		
							],
						buttons : [{
								text : '保存',
								handler : function() {
									addTextForm.form.doAction('submit', {
										url : '/myads/HTML/advflight/FileUploadAction_saveText.action',
										method : 'post',
										params : '',
										success : function(form, action) {
											if(action.result.result == 'success') {
												Ext.MessageBox.alert('结果', '保存成功！');
												form.reset();
												grid.getStore().reload();
												winAddText.hide();
											}
											else {
												Ext.MessageBox.alert('错误', '保存失败！' );
											}
											
										}
									});
								}
							}
						]

				});
			addTextForm.getForm().findField("id").setValue(id);
			
			var winAddText = new Ext.Window({
					width : 400,
					height : 240,
					modal : true,
					title : "关联物料",
					closeAction:'hide',
					items : [addTextForm]	
										
				});
			
			
			winAddText.show();
		}else{
			var dsAdd = new Ext.data.Store({
			proxy : new Ext.data.HttpProxy({url : '/myads/HTML/advflight/FileUploadAction_showAll.action?DoubleType='+type}),
			remoteSort : true,
			reader : new Ext.data.JsonReader(
				{
					totalProperty : 'total',
					idProperty : 'id',
					root : 'invdata'
				}, 
				[
					
					{name : 'materialId'}, 
					{name : 'name'}, 
					{name : 'advertiserName'}, 
					{name : 'path'}, 
					{name : 'meterialType'}, 
					{name : 'isSuccess'}, 
					{name : 'note'}, 
					{name : 'creator'}, 
					{name : 'createTime'}
				]
				)
			});
			dsAdd.load({params : {start : 0,limit : 15,meterialType:type}});
			gridAdd = new Ext.grid.GridPanel({
					ds : dsAdd,
					cm : cmAdd,
					sm : smAdd,
					height : 320,
					viewConfig : {
						forceFit : true
					},
					bbar : new Ext.PagingToolbar({
						pageSize : 15,
						store : dsAdd,
						displayInfo : true,
						displayMsg : '显示第{0}条到{1}条记录,一共{2}条',
						emptyMsg : '没有记录'
					})
				});
				
				 var kpiGroup = {
						 xtype: 'fieldset',
						 title: '查询物料',
						 layout: 'form',
						 labelWidth : 80,
						 items:[
						 	{
									layout : 'column',
									items : [{
												columnWidth : .25,
												layout : 'form',	
												items : [{
															xtype : 'textfield',
															name : 'name',
															fieldLabel : '物料名称 ',
															anchor : '95%'
														}]
											},
												{
													columnWidth: .25, 
													layout: 'form',
								            		items: [			            		
														{
															xtype : 'textfield',
															name : 'materialType',
															fieldLabel : '类型',
															anchor : '95%',
															readOnly:true
														}
								            		]
												}
												],
									buttons : [{
										
										text : '查询',
										handler : function() {
											var fv = addForm.getForm().getValues();
											dsAdd.baseParams = fv;
											dsAdd.load({params : {start : 0,limit : 15}});
										}
									}
	//								, {
	//									text : '上传物料',
	//									handler : function() {
	//										showUpload();
	//									}
	//								}
									]
								}
						 ]
					}
				    
				     var addForm = new Ext.form.FormPanel({
						labelAlign : 'right',
						labelWidth : 80,
						frame : true,
						xtype : 'fieldset',
						region: 'north',
						items : [{
									layout : 'column',
									items : []
								}, 
								{
										xtype : 'textfield',
										name : 'advName',
										fieldLabel : '所属广告 ',
										anchor : '24%',
										readOnly:true,
										value:advName
								},{
										xtype : 'hidden',
										name : 'adv',
										fieldLabel : '所属广告ID ',
										anchor : '24%',
										readOnly:true,
										value:advId
								},
								{
										xtype : 'hidden',
										name : 'id',
										fieldLabel : 'ID ',
										anchor : '24%',
										readOnly:true
										
								}
								//查询
								,{
									layout: 'column',
									items: [{columnWidth: 1, layout: 'form',items:[kpiGroup]}]
								}				
								],	
								buttons : [{
										//TODO 
										text : '关联选中的物料',
										handler : function() {
											var id = addForm.getForm().findField("id").getValue();
											var rs = gridAdd.getSelectionModel().getSelections();
											if(rs.length>1){
												alert("每次只能关联一个物料");
											}
											else if(rs.length<1){
												alert("请选择需要关联的物料");
											}
											else{
												var param = [];
												Ext.each(rs, function() {
													param.push(this.get("materialId"));
												});
												//alert(advId);
												Ext.Ajax.request({
													method : 'post',
													url : '/myads/HTML/advflight/AdvMaterialAction_save.action',
													success : function(resp) {
														var obj = Ext.util.JSON
																.decode(resp.responseText);
														if (obj.result == 'success') {
															grid.getStore().reload();
															Ext.MessageBox.alert('提示', '关联成功！');
															winAdd.hide();
														}else if (obj.result == 'use') {
															grid.getStore().reload();
															Ext.MessageBox.alert('提示', '关联失败！');
															winAdd.hide();
														}
													},
													params : {
														materialIdList : param.join(','),
														advId:advId,
														id:id
													}
												});										
											}
										}
									}
									]
						
					});
			addForm.getForm().findField("materialType").setValue(type);
			addForm.getForm().findField("id").setValue(id);
			var winAdd = new Ext.Window({
					width : 1000,
					height : 540,
					modal : true,
					title : "关联物料",
					closeAction:'hide',
					items : [addForm,gridAdd]	
										
				});
			
			
			winAdd.show();
		};
	}
	
	//删除物料关系
	window.deleteAdvMaterial = function(id, name) {
		Ext.MessageBox.confirm('提示', '确定删除  ' + name + ' ？', function(btn) {
			if (btn == 'yes') {
				var param = [];
				param.push(id);
				Ext.Ajax.request({
					method : 'post',
					url : '/myads/HTML/advflight/AdvMaterialAction_delete.action',
					success : function(resp) {
						var obj = Ext.util.JSON.decode(resp.responseText);
						if (obj.result == 'success') {
							grid.getStore().reload();
							Ext.MessageBox.alert('提示', '删除成功！');
						}else if (obj.result == 'use') {
							grid.getStore().reload();
							Ext.MessageBox.alert('提示', '删除失败！物料正在使用中...');
						}
					},
					params : {
						advMaterialList : param.join(',')
					}
				});
			}
		});
	};
	

	
	
	
	var mainViewPort = new Ext.Viewport({
		layout: 'border',
		items:[ searchForm, grid]
	});
  });   
	
	




