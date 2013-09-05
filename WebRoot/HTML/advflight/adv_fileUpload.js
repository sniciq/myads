Ext.onReady(function(){   
	 // var advId = Ext.get('advId').dom.value;//跨页面拿到广告id
	 // var advName = Ext.get('advName').dom.value;//跨页面拿到广告名称
	
	Ext.form.Field.prototype.msgTarget = 'side';// 统一指定错误信息提示方式
	
	Ext.util.Observable.observeClass(Ext.data.Connection);
    Ext.data.Connection.on("requestcomplete", function(c, d, b){
        if (d && d.getResponseHeader) {
            if (d.getResponseHeader("sessionstatus")) {
            	alert("操作超时, 请重新登录!");
                window.location.href = "../index.html";
            }
        }
    });
	
	// 广告主列表
	var advertiserCombStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({url: '/myads/HTML/sysfun/AdvertiserAction_list.action'}),
		reader: new Ext.data.ArrayReader({}, [
			{name: 'value'},{name: 'text'}
		])
	});
	advertiserCombStore.load();

	
    var myForm = new Ext.form.FormPanel({  
    	
		height : 150,
        region: 'north',
        frame : true,  
        layout: 'form',
        monitorValid:true,  
        fileUpload:true, // 需上传文件
        enctype:'multipart/form-data',
       
        method:'POST',  
        items : [
        	{  
	            xtype : 'textfield',  
	            name : 'name',  
	            fieldLabel : '文件名称',
	            maxLength:20,
				maxLengthText:'输入文本过长',
				anchor : '85%'
	        },
	        {  
	            xtype : 'textfield',  
	            name : 'note',  
	            fieldLabel : '备注' ,
	            maxLength:20,
				maxLengthText:'输入文本过长',
				anchor : '85%'
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
				anchor : '85%'
	        }
	        ,
	        {  
	        	xtype:'textfield',  
	            fieldLabel : '选择文件',  
	            name : 'upload',  
	            inputType : 'file',   // 关键
	            allowBlank:false,
	            anchor : '90%'
//	            ,  
//	            blankText:'请选择文件!',  
//	            emptyText:'请选择上传文件'  
	      	}
	    ],  
		buttons : [
				{  
		            formBind:true,  
		            text : '上传',  
		            handler:function(){  
		            	var FileName = myForm.getForm().findField("upload").getValue();
		            	//alert(FileName);
						myForm.form.submit({  
							clientValidation : true,// 进行客户端验证
							url : '/myads/HTML/advflight/FileUploadAction_save.action',// 请求的url地址
							method : 'post',
							params : {FileName:FileName},
							waitMsg : '正在上传文件,请稍等……',// 提示信息
							waitTitle : '提示',// 标题
							success : function(form, action) {// 加载成功的处理函数
							Ext.Msg.alert('提示', action.result.result);    
								myForm.form.reset();
								grid.getStore().reload();
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
    //myForm.render('north-div');
	    
    
    var searchForm = new Ext.form.FormPanel({
		labelAlign : 'right',
		labelWidth : 80,
		frame : true,
		xtype : 'fieldset',
		region: 'north',
		items : [{
					layout : 'column',
					items : [{
								columnWidth : .01,
								layout : 'form',
								items : [{
											xtype : 'hidden',
											name : 'id',
											hidden : true,
											hiddenLabel : true
										}]
							}]
				}, {
					layout : 'column',
					items : [{
								columnWidth : .25,
								layout : 'form',
								items : [{
											xtype : 'textfield',
											name : 'name',
											fieldLabel : '名称 ',
											anchor : '95%'
										}]
							},{
									columnWidth: .25, 
									layout: 'form',
				            		items: [
					            	   new Ext.form.ComboBox({
					        	       		fieldLabel: '广告主',
											hiddenName: 'advertiserId',
											mode: 'local',
											editable : false,
											triggerAction: 'all', 
											valueField: 'value',
											displayField: 'text',
											anchor : '90%',
											store: advertiserCombStore
					        	       })
				            		]
								}
								]
				}],
		buttons : [{
			
			text : '查询',
			handler : function() {
				var fv = searchForm.getForm().getValues();
				ds.baseParams = fv;
				ds.load({params : {start : 0,limit : 15}});
			}
		}, {
			text : '重置',
			handler : function() {
				searchForm.form.reset();
			}
		}, {
			text : '上传物料',
			handler : function() {
				showUpload();
			}
		}]

	});
	
	searchForm.render('north-div');
	
	//TODO 添加物料
	function showUpload() {
		myForm.form.reset();	
		//addadvertiserForm.getForm().findField("status").setValue(0);
		winAdd.show();
	}
	var winAdd = new Ext.Window({
					width : 400,
					height : 185,
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
		
		var id = record.data.materialId;
		var name = record.data.materialName;
		var path = record.data.path;
		var delStr = '<a href="#" onclick=\"deleteMaterial(\''+ id + '\', \'' + name + '\');">删除</a>';	
		var copyStr = '<a href="#" onclick=\"copyToClipboard(\''+ path + '\');">复制路径</a>';	
		return  delStr+'&nbsp&nbsp&nbsp&nbsp'+ copyStr;
	}
	
	function renderName(value, cellmeta, record, rowIndex, columnIndex, stor) {
		
		var id = record.data.materialId;
		var name = record.data.name;
		var path = record.data.path;
		var textContent = record.data.textContent;
//		var pureCode = '<xmp>'+record.data.pureCode+'</xmp>';

		
		var meterialType = record.data.meterialType;
		var advertiserName = record.data.advertiserName;
		var showStr = '<a href="#" onclick=\"showDetail(\''+ id + '\', \''+ name + '\', \'' + advertiserName + '\', \'' + path + '\', \'' + meterialType + '\');">'+name+'</a>';	
	
		return  showStr;
	}
	

	
	var sm = new Ext.grid.CheckboxSelectionModel();
	var cm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(), 
//		sm, 
		{header : 'ID',dataIndex : 'materialId',sortable : true}, 
		{header : '名称',dataIndex : 'name',renderer:renderName}, 
		{header : '广告主',dataIndex : 'advertiserName'}, 
		//{header : '路径',dataIndex : 'path',width:300,sortable : true},
		{header : '物料类型',dataIndex : 'meterialType'}, 
		{header : '备注',dataIndex : 'note'}, 
		{header : '物料状态',dataIndex : 'isSuccess',renderer : renderView,sortable:true, width:100},
		{header : '时长',dataIndex : 'playTime',sortable:true, width:50},
		//{header : '创建人',dataIndex : 'creator',sortable : true}, 
		{header : '创建时间',dataIndex : 'createTime',sortable : true}
		//{header : '操作',dataIndex : 'op',renderer : renderOp,width : 100,align : 'left'}
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
	
	var ds = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({url : '/myads/HTML/advflight/FileUploadAction_showAll.action'}),
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
				{name : 'advertiserId'}, 
				{name : 'path'}, 
				{name : 'textContent'}, 
				{name : 'pureCode'}, 
				{name : 'meterialType'}, 
				{name : 'isSuccess'}, 
				{name : 'playTime'}, 
				{name : 'note'}, 
				{name : 'creator'}, 
				{name : 'createTime'}
			]
		)
	});

	ds.load({params : {start : 0,limit : 15}});

	var grid = new Ext.grid.GridPanel({
		el : 'grid',
		region: 'center',
		ds : ds,
		cm : cm,
		//sm : sm,
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
//,
//			items : ['-', {
//				text : '删除',
//				handler : function() {
//					var rs = grid.getSelectionModel().getSelections();
//					var param = [];
//					Ext.each(rs, function() {
//								param.push(this.get("materialId"));
//							});
//					if(param.length>0){
//							Ext.MessageBox.confirm("提示", "确定删除？",
//							function(id) {
//								if (id == 'yes') {
//									
//									Ext.Ajax.request({
//										method : 'post',
//										url : '/myads/HTML/advflight/FileUploadAction_delete.action',
//										success : function(resp) {
//											var obj = Ext.util.JSON
//													.decode(resp.responseText);
//											if (obj.result == 'success') {
//												grid.getStore().reload();
//												Ext.MessageBox.alert('提示', '删除成功！');
//											}else if (obj.result == 'use') {
//												grid.getStore().reload();
//												Ext.MessageBox.alert('提示', '选中的物料中某些正在使用！');
//											}
//										},
//										params : {
//											materialList : param.join(',')
//										}
//									});
//								} else {
//									
//								}
//							});
//						}else{
//								Ext.MessageBox.alert('提示', '请选择要删除的物料.');
//						}
//				
//				}
//			}]
		})
	});
	grid.render();	
	
	
    
	//删除物料
	window.deleteMaterial = function(id, name) {
		Ext.MessageBox.confirm('提示', '确定删除  ' + name + ' ？', function(btn) {
			if (btn == 'yes') {
				var param = [];
				param.push(id);
				Ext.Ajax.request({
					method : 'post',
					url : '/myads/HTML/advflight/FileUploadAction_delete.action',
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
						materialList : param.join(',')
					}
				});
			}
		});
	};

	//展示预览
	window.showDetail = function(id, name,advertiserName,path,meterialType) {
	
		var copyForm = new Ext.form.FormPanel({  
			height : 215,
			labelWidth : 60,
	        frame : true,  
	        layout: 'form',
	        monitorValid:true,  
	        items : [
	        	{ 
		            xtype : 'textfield',  
		            name : 'name',  
		            fieldLabel : '名称' ,
		            anchor : '90%',
		            readOnly : true
		           
		        },{ 
		            xtype : 'textfield',  
		            name : 'advertiserName',  
		            fieldLabel : '广告主' ,
		            anchor : '90%',
		            value:advertiserName,
		            readOnly : true
		          
		        },
		        { 
		            xtype : 'textfield',  
		            name : 'note',  
		            fieldLabel : '备注' ,
		            anchor : '90%',
		           
		            readOnly : true
		          
		        },{ 
		            xtype : 'textarea',  
		            name : 'path',  
		            fieldLabel : '文件路径' ,
		            anchor : '90%',
		            readOnly : true
		            
		        },{ 
		            xtype : 'textarea',  
		            name : 'textContent',  
		            fieldLabel : '内容' ,
		            anchor : '90%',
		            height : 90,
		            readOnly : true
		           
		        }
		    ],  
			buttons : [
			        {  
			            text : '复制',  
			            id : 'copyButton',
			            handler:function(){  
								copyToClipboard(path);
			            }  
			        },
			        {  
			            text : '预览', 
			            id : 'view',
			            handler:function(){  
			            	var path = copyForm.getForm().findField("path").getValue();
			            	
			            	if(meterialType=='IMAGE'){     		
			            		showAframe('<iframe src=/myads/HTML/advflight/showIMAGEMaterial.jsp?path='+path+' width="98%" height=450px></iframe>');			            					       
			            	}else{
			            		//window.location.href='/myads/HTML/advflight/showMaterial.jsp?path='+path;
			            		showAframe('<iframe src=/myads/HTML/advflight/showMaterial.jsp?path='+path+' width="98%" height=450px></iframe>');
			            	}
						
			            }  
			        }
			   ]  
	    });		

		var winCopy = new Ext.Window({
				width : 460,
				height : 250,
				modal : true,
				title : "文件路径",
				closeAction:'hide',
				items : copyForm	
									
			});
			
		copyForm.load({
					url : '/myads/HTML/advflight/FileUploadAction_getDetail.action',
					params : {
						id : id
					}
				});
		if(meterialType=='TEXT' || meterialType=='CODE'){
	   		copyForm.getForm().findField("path").hide();
	   		var butt = Ext.getCmp('view');
	   		butt.hide();
	   		var Cbutt = Ext.getCmp('copyButton');
	   		Cbutt.hide();
	    }
	    else{
	   		copyForm.getForm().findField("textContent").hide();
	    }
		winCopy.show();
	
	};
	
	//A<fream>
	window.showAframe = function(path) {
		
		var showForm = new Ext.form.FormPanel({  
			height : 390,
			width:550,
			labelWidth : 60,
	        frame : true,  
	        layout: 'form',
	        monitorValid:true,  
	        items : [
	        	{ 
		           //html:'<iframe src=/myads/HTML/advflight/showIMAGEMaterial.jsp?path='+path+' width="95%" height=450px></iframe>'  
	        		html:path
		        }
		    ]
	    });	
    	var showWin = new Ext.Window({
			width : 560,
			height : 400,
			modal : true,
			title : "文件预览",
			closeAction:'hide',
			items : showForm	
		});
		showWin.show();
	}
	//兼容浏览器的复制
	window.copyToClipboard = function(txt) {        
     if(window.clipboardData) {        
              window.clipboardData.clearData();        
              window.clipboardData.setData("Text", txt);
              Ext.MessageBox.alert('提示', '复制成功')
      } else if(navigator.userAgent.indexOf("Opera") != -1) {        
           window.location = txt;        
      } else if (window.netscape) {        
          try {        
                netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect");        
           } catch (e) {        
                alert("被浏览器拒绝！\n请在浏览器地址栏输入'about:config'并回车\n然后将'signed.applets.codebase_principal_support'设置为'true'");        
           }        
          var clip = Components.classes['@mozilla.org/widget/clipboard;1'].createInstance(Components.interfaces.nsIClipboard);        
          if (!clip)        
               return;        
          var trans = Components.classes['@mozilla.org/widget/transferable;1'].createInstance(Components.interfaces.nsITransferable);        
          if (!trans)        
               return;        
           trans.addDataFlavor('text/unicode');        
          var str = new Object();        
          var len = new Object();        
          var str = Components.classes["@mozilla.org/supports-string;1"].createInstance(Components.interfaces.nsISupportsString);        
          var copytext = txt;        
           str.data = copytext;        
           trans.setTransferData("text/unicode",str,copytext.length*2);        
          var clipid = Components.interfaces.nsIClipboard;        
          if (!clip){return false;}
          else{
           clip.setData(trans,null,clipid.kGlobalClipboard);        
            Ext.MessageBox.alert('提示', '复制成功');
          }
      }else {
      	  Ext.MessageBox.alert('提示', '对不起，该浏览器不支持一键复制。请使用以IE、火狐为内核的浏览器，或者双击路径后手动复制。')
       }        
	} 
	//预览
	window.showMaterial = function(path) { 
		alert(path);
	}
	
	var mainViewPort = new Ext.Viewport({
		layout: 'border',
		items:[ searchForm, grid]
	});
  });   
	
	




