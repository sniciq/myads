Ext.onReady(function() {
	Ext.QuickTips.init();
	var projectId = Ext.get('projectId').dom.value;
	var param_priority = Ext.get('priority').dom.value;
	var canUpdatePriority = Ext.get('canUpdatePriority').dom.value;
	
	var bookPackageId_global = null;
	var bookpackageType_global = 1;
	var selectChannelId_global = null;
	var selectAdvbarId_global = null;
	var advproductId_global = null;
	
	function verifyShowContentTab(advbarId) {
		Ext.Ajax.request({
			method: 'post',
			url: '/myads/HTML/advert/AdvbarAction_getAdvbarPageType.action',
			params: {advbarId: advbarId},
		   	success:function(resp){
		   		tabs.unhideTabStripItem('contentTab');
		   		var obj=Ext.util.JSON.decode(resp.responseText);
		   		if(obj.data.dataValue == 1) {//1:页面广告，不能选择内容定向
		   			tabs.hideTabStripItem('contentTab');
		   		}
		   	}
		});
	}
	
	//基本内容
	var basicinfoFormPanel = new com.myads.book.BasicinfoFormPanel();
	basicinfoFormPanel.load(projectId);

	//选择位置
	var locationFormPanel = new com.myads.book.LocationFormPanel();
	
	//广告产品选择
	var advprudctSelectPanel = new com.myads.book.AdvprudctSelectPanel();
	advprudctSelectPanel.on('selectSaleType', function() {
		var aa = advprudctSelectPanel.getFormValues();
		var isContentDirect = contentFormPanel.getIsContentDirect();
		pointSelectPanel.configCalandarData(2, aa.advproductId, aa.saleType, aa.saleTypeName, isContentDirect, param_priority);
		pointSelectPanel.refreshCalandar();
		pointSelectPanel.virefBBar(aa.saleTypeName);
	});
	
	advprudctSelectPanel.on('selectAdvproduct', function(data) {
		var aa = advprudctSelectPanel.getFormValues();
		var isContentDirect = contentFormPanel.getIsContentDirect();
		pointSelectPanel.configCalandarData(2, aa.advproductId, aa.saleType, aa.saleTypeName, isContentDirect, param_priority);
		pointSelectPanel.refreshCalandar();
	});
	
	//选择位置----选择广告条
	locationFormPanel.on('selectAdvbar', function(advbarId) {
		//判断广告位类型，如果是普通页面广告，则没有"内容策略"
    	verifyShowContentTab(advbarId);
    	//位置改变，初始化其它控件
    	locationFormPanel.form.reset();
		directFormPanel.form.reset();
		directFormPanel.clearAreaResult();
		
		contentFormPanel.form.reset();
		pointSelectPanel.form.reset();
		pointSelectPanel.removeAllPoint();
	});
	
	//选择位置----选择售卖方式
	locationFormPanel.on('selectSaleType', function(advbarId, saleTypeValue, saleTypeName) {
		var isContentDirect = contentFormPanel.getIsContentDirect();
		pointSelectPanel.configCalandarData(1, advbarId, saleTypeValue, saleTypeName, isContentDirect, param_priority);
		pointSelectPanel.refreshCalandar();
		pointSelectPanel.virefBBar(saleTypeName);
		directFormPanel.form.reset();
		directFormPanel.clearAreaResult();
		contentFormPanel.form.reset();
		pointSelectPanel.form.reset();
		pointSelectPanel.removeAllPoint();
    	pointSelectPanel.doLayout();
	});
	
	//定向策略
	var directFormPanel = new com.myads.book.DirectFormPanel();
	
	//内容策略
	var contentFormPanel = new com.myads.book.ContentFormPanel();
	//内容策略----更改是否内容定向
	contentFormPanel.on('isContentChange', function(isContentDirect) {
		pointSelectPanel.setIsContentDirect(isContentDirect);
		pointSelectPanel.refreshCalandar();
	});
	
	//点位选择
	var pointSelectPanel = new com.myads.book.PointSelectPanel();
	pointSelectPanel.on('loadPointListOver', function() {
//		var isContentDirect = contentFormPanel.getIsContentDirect();
//		var locationData = locationFormPanel.getFormValues();
//		pointSelectPanel.configCalandarData(1, locationData.advbarId, locationData.saleType, locationData.saleTypeName, isContentDirect, param_priority);
//		pointSelectPanel.refreshCalandar();
	});
	
	var bookTypeFormPanel = new com.myads.book.BookTypeFormPanel();
	bookTypeFormPanel.on('selectType', function(type) {
		if(type == '1') {//广告条预订
			tabs.hideTabStripItem('advprudctTab');
			tabs.unhideTabStripItem('locationTab');
		}
		else if(type == '2') {//广告产品预订
			tabs.unhideTabStripItem('advprudctTab');
			tabs.hideTabStripItem('locationTab');
		} 
	});
	
	var tabs = new Ext.TabPanel({
		renderTo: 'north-div',
		autoHeight : false,
		height:350,
		frame:true,
		region:'north',
        activeTab: 0,
        split: true,
        layoutOnTabChange : true ,
        defaults:{autoScroll: true},
        deferredRender :false,
		items: [
			{
				title: '基础信息',
				items:[basicinfoFormPanel]
			},
			{
				title: '预订类别',
				items:[bookTypeFormPanel]
			},
			{
				id: 'locationTab',
				title: '选择位置',
				items:[locationFormPanel]
			},
			{
				id: 'advprudctTab',
				title: '选择产品',
				items:[advprudctSelectPanel]
			},
			{
				id: 'directTab',
				title: '定向策略',
				listeners: {activate: handleActivate},
				items:[directFormPanel]
			},
			{
				id: 'contentTab',
				title: '内容策略',
				listeners: {activate: handleActivate},
				items:[contentFormPanel]
			},
			{
				title: '选择点位',
				listeners: {activate: handleActivate},
				items:[pointSelectPanel]
			}
		],
		buttons: [
			{
				id: 'saveBtn',
				text: '保存',
				handler: function() {
					Ext.MessageBox.confirm("提示", "确定保存？", function(id) {
						if(id == 'yes') {
							submitBook();
						}
					});
				}
			}
		]
	});
	
	function initTab() {
		tabs.hideTabStripItem('advprudctTab');
	}
	
	initTab();
	
	function handleActivate(tab){
		if(bookTypeFormPanel.getBookType() == '1' && !locationFormPanel.getForm().isValid()) {
			tabs.setActiveTab('locationTab');
		}
		else if(bookTypeFormPanel.getBookType() == '2' && !advprudctSelectPanel.getForm().isValid()) {
			tabs.setActiveTab('advprudctTab');
		} 
		tab.doLayout();
    }
    
    //排期GRID----------------------------------------开始------------------------
	var cm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{header:'频道', dataIndex:'channelName', sortable:true, menuDisabled:true},
		{
			header:'广告条/产品', dataIndex:'advbarName', sortable:true, menuDisabled:true,
			renderer: function(value, metadata, record, rowIndex, colIndex, store) {
				if(record.data.bookpackageType == 1)
					return record.data.advbarName;
				else if(record.data.bookpackageType == 2)
					return '<a style="color: #00CC00;cursor: pointer;">' + record.data.advproductName + '</a>';
			}
		},
		{header:'使用类型', dataIndex:'useTypeName', sortable:true, menuDisabled:true},
	    {header:'售卖方式', dataIndex:'saleTypeName', sortable:true, menuDisabled:true},
	    {header:'刊例价', dataIndex:'price', sortable:true, menuDisabled:true},
	    {header:'折扣', dataIndex:'discount', sortable:true, menuDisabled:true},
	    {header:'定向策略', dataIndex:'dataName', renderer: renderOp1, menuDisabled:true},
	    {header:'内容策略', dataIndex:'dataValue', renderer: renderOp2, menuDisabled:true},
	    {header:'优先级', id:'priorityCM', dataIndex:'priority', sortable:true, menuDisabled:true},
	    {header:'投放时期', dataIndex:'startTime', sortable:true, menuDisabled:true,
	    	renderer: function(value, metadata, record, rowIndex, colIndex, store) {
            	return record.get("startTime") + "---" + record.get("endTime");
        	}
	    },
	    {header:'操作', dataIndex:'op', renderer: renderOp,width:150, align:'left', menuDisabled:true}
	]);
	
	if(canUpdatePriority != 'true') {
		cm.setHidden(9, true);
	}
	
	// 定向策略
	function renderOp1(value, cellmeta, record, rowIndex, columnIndex, stor) {
		var title = '定向策略:';
		
		var frequencyStr = '';
		if(record.get('isFrequency') == 1) {//频次定向
			var frequencyType = record.get('frequencyTypeName');
			frequencyStr = frequencyType + ', ' + record.get('frequencyNum');
		}
			
		var areaDirectStr = '';
		var areaDirectObj = Ext.util.JSON.decode(record.get('areaDirect'));
		for(var j = 0; j < areaDirectObj.length; j++) {
			areaDirectStr += areaDirectObj[j].display + '; ';
		}
		
		var tip = '区域定向：' +  areaDirectStr + '<br>' 
			+ '时间定向：' +  record.get('hourDirect') + '<br>' 
			+ '频次定向: ' + frequencyStr;    
		cellmeta.attr = 'ext:qtitle="' + title + '"' + ' ext:qtip="' + tip + '"';    
		return '<a style="color: #44CC44;cursor: pointer;">详细</a>'; 
	}
	
	// 内容策略
	function renderOp2(value, cellmeta, record, rowIndex, columnIndex, stor) {
		if(record.get('isContentDirect') != 1)
			return '';
		var title = '内容策略';    
		var tip = '关键字：' +  record.get('keyword') + '<br>' 
				+ '用户：' +  record.get('user') + '<br>'
				+ '视频id：' +  record.get('video') + '<br>'
				+ '节目：' +  record.get('program') + '<br>'
				+ '活动：' +  record.get('activity') + '<br>'
				+ '专题：' +  record.get('subject') + '<br>';
		cellmeta.attr = 'ext:qtitle="' + title + '"' + ' ext:qtip="' + tip + '"';    
		return '<a style="color: #44CC44;cursor: pointer;">详细</a>'; 
	}
	
	function renderOp(value, cellmeta, record, rowIndex, columnIndex, stor) {
		var bookPackageId = record.data.id;
		var scrBPackageId = record.data.scrBPackageId;
		var delInfo = record.data.advbarName ; 
		
		var startTimeStr = '' + record.data.startTime;
		startTimeStr = startTimeStr.replace(/-/g, "/");
		var startTime = new Date(startTimeStr);
		
		var endTimeStr = '' + record.data.endTime;
		endTimeStr = endTimeStr.replace(/-/g, "/");
		var endTime = new Date(endTimeStr);
		
		var nowTime = new Date();
		var _temps1 = Ext.util.Format.date(nowTime, "Y/m/d");
		var nowDate = new Date(_temps1);
		
		var delStr = '';
		if (Date.parse(startTime) > Date.parse(nowDate) || Date.parse(startTime) < Date.parse(nowDate)) {
			if (Date.parse(endTime)-Date.parse(nowDate) > 0) { //有未执行的，可以删除
				delStr = '<a href="#" onclick=\"del_bookPackage(\''+bookPackageId+'\', \'' + delInfo + '\');\">删除</a>';
			}
		}
		
		var updateStr = '<a href="#" onclick=\"loadBookPackageInof(\''+bookPackageId+'\', \'' + scrBPackageId + '\', \'' + record.data.bookpackageType + '\', \'' + record.data.advproductId + '\');\">修改</a>';
		if(Date.parse(startTime)-Date.parse(nowDate) < 0)
			updateStr = '<a href="#" onclick=\"loadBookPackageInof(\''+bookPackageId+'\', \'' + scrBPackageId + '\', \'' + record.data.bookpackageType + '\', \'' + record.data.advproductId + '\');\">查看</a>';
		
		var updatePriority = '';
		if(canUpdatePriority == 'true')	
			updatePriority = '<a href="#" onclick=\"updatePriority(\''+bookPackageId+'\', \''+record.data.priority+'\');\">修改优先级</a>';
		
		return updateStr + '&nbsp;&nbsp;&nbsp;&nbsp;' + delStr + '&nbsp;&nbsp;&nbsp;&nbsp;' + updatePriority;
	}
	
	var bookPackage_ds = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({url: '/myads/HTML/advflight/BookAction_showAllBookPackage.action'}),
		remoteSort: false,
		reader: new Ext.data.JsonReader(
			{
				totalProperty: 'total',
				idProperty:'id',
				root: 'invdata'
			},
			[
				{name: 'id'},
				{name: 'channelName'},
				{name: 'advbarName'},
				{name: 'useType'},
				{name: 'useTypeName'},
				{name: 'saleType'},
				{name: 'saleTypeName'},
				{name: 'price'},
				{name: 'discount'},
				{name: 'startTime'},
				{name: 'endTime'},
				{name: 'areaDirect'},
				{name: 'hourDirect'},
				{name: 'priority'},
				{name: 'isContentDirect'},
				{name: 'isFrequency'},
				{name: 'frequencyType'},
				{name: 'frequencyTypeName'},
				{name: 'frequencyNum'},
				{name: 'scrBPackageId'},
				{name: 'advproductId'},
				{name: 'bookpackageType'},
				{name: 'keyword'},
				{name: 'user'},
				{name: 'video'},
				{name: 'program'},
				{name: 'activity'},
				{name: 'subject'},
				{name: 'advproductName'}
			]
		)
	});

	bookPackage_ds.baseParams = {projectId: projectId};
	bookPackage_ds.load({params: {start:0, limit:20}});
	
	var bookPackage_grid = new Ext.grid.GridPanel({
		el: 'bookPackage_grid',
		ds: bookPackage_ds,
		cm: cm,
	    viewConfig: {
	    	forceFit: true
	    },
	    bbar: new Ext.PagingToolbar({
		    pageSize: 20,
		    store: bookPackage_ds,
			displayInfo: true,
			displayMsg: '显示第{0}条到{1}条记录,一共{2}条',
			emptyMsg: '没有记录'
	    }),
	    tbar: new Ext.Toolbar({
			buttons : [
				{
					id: 'ProjectVerifySubmitBtn',
					text: '提交审核',
					handler: function(btn) {
							Ext.MessageBox.confirm("提示", "确定提交审核？", function(id) {
								if(id == 'yes') {
									btn.disable();
									Ext.Ajax.request({
										method: 'post',
										url: '/myads/HTML/verify/ProjectVerifyAction_submit.action',
									   	success:function(resp){
									    	var obj=Ext.util.JSON.decode(resp.responseText);
									      	if(obj.result == 'success') {
									      		Ext.MessageBox.alert('提示', '提交成功！',function() {
									      			window.history.back();
									      		});
									      	}else{
									      		Ext.MessageBox.alert('提示', '提交失败！',function() {
									      			window.history.back();
									      		});
									      	}
						    			},
						   				params: {projectId: projectId}
								});
							}
						});
					}
				},
				'-', 
				{
					text: '生成点位预览',
					handler: function() {
						refrshPreviewGridTab();
					}
				}
			]
		})
	});
	
	var gridTab = new Ext.TabPanel({
		region:'center',
		frame:true,
        activeTab: 0,
        layoutOnTabChange : true ,
        defaults:{autoScroll: true},
        deferredRender :false,
		items: [
			{
				title: '排期信息',
				layout: 'fit',
				items:[bookPackage_grid]
			}
		]
	});
	
	new Ext.Viewport({
		layout: 'border',
		items:[
		       tabs,gridTab
		]
	});
	
	
	//加载排期包的信息
	window.loadBookPackageInof = function(bookPackageId, scrBPackageId, bookpackageType, advproductId) {
		bookPackageId_global = bookPackageId;
		Ext.MessageBox.show({  
            title:'请等待',  
            msg:'读取数据中……',  
            width:240,  
            progress:false,  
            closable:false  
        });
        
        var actionURL;
        var paraObj;
        if(bookpackageType == 1) {
        	actionURL = '/myads/HTML/advflight/BookAction_getBookPackageInfo.action';
        	paraObj = {bookPackageId: bookPackageId};
        }
        else if(bookpackageType == 2) {
        	actionURL = '/myads/HTML/advflight/BookAction_getBookPackageAdvProductInfo.action';
        	paraObj = {bookPackageId: bookPackageId, advproductId: advproductId};
        }
	                
		Ext.Ajax.request({
			method: 'post',
			url: actionURL,
			params: paraObj,
		   	success:function(resp){
		   		var obj=Ext.util.JSON.decode(resp.responseText);
		   		param_priority = obj.data.priority;
		   		
		   		var saveBtn = Ext.getCmp('saveBtn');
		   		if(obj.CanSave) {
		   			saveBtn.show();
		   		}
		   		else {
		   			saveBtn.hide();
		   		}
		   		
		   		directFormPanel.setFormData(obj.data);
		   		contentFormPanel.setFormData(obj.data);
		   		bookpackageType_global = obj.data.bookpackageType;
		   		
		   		bookTypeFormPanel.setFormData(obj.data);
		   		
		   		if(obj.data.bookpackageType == 1) {
		   			selectChannelId_global = obj.data.channelId;
					selectAdvbarId_global = obj.data.advbarId;
		   			locationFormPanel.setFormData(obj.data);
		   			pointSelectPanel.configCalandarData(1, obj.data.advbarId, obj.data.saleType, obj.data.saleTypeName, obj.data.isContentDirect, obj.data.priority);
		   			pointSelectPanel.refreshCalandar();
		   			tabs.hideTabStripItem('advprudctTab');
					tabs.unhideTabStripItem('locationTab');
		   			tabs.setActiveTab('locationTab');
		   		}
		   		else if(obj.data.bookpackageType == 2) {
		   			advproductId_global = obj.data.advproductId;
		   			advprudctSelectPanel.setFormData(obj.data);
		   			pointSelectPanel.configCalandarData(2, obj.data.advproductId, obj.data.saleType, obj.data.saleTypeName, obj.data.isContentDirect, obj.data.priority);
		   			pointSelectPanel.refreshCalandar();
		   			tabs.unhideTabStripItem('advprudctTab');
					tabs.hideTabStripItem('locationTab');
		   			tabs.setActiveTab('advprudctTab');
		   		} 
		   		
		   		pointSelectPanel.virefBBar(obj.data.saleTypeName);
		   		pointSelectPanel.loadPointData(bookPackageId);
				tabs.doLayout();
		   		Ext.MessageBox.hide();
		   		getRelationBookPackageInfo(scrBPackageId);
		   	}
		});
	}
	
	window.getRelationBookPackageInfo = function (scrBPackageId) {
		locationFormPanel.selRelationBPBtn.setDisabled(false);
		if(scrBPackageId == '')
			return;
			
		Ext.Ajax.request({
			method: 'post',
			url: '/myads/HTML/advflight/BookAction_getBookPackageInfo.action',
			params: {bookPackageId: scrBPackageId},
		   	success:function(resp){
		   		var obj=Ext.util.JSON.decode(resp.responseText);
		   		if(obj.data) {
		   			locationFormPanel.getForm().findField("scrBPackageId").setValue(obj.data.id);
					locationFormPanel.getForm().findField("scrBPackageName").setValue(obj.data.id + '--' + obj.data.channelName + "--" + obj.data.advbarName);
		   		}
		   	}
		});
	}
	
	//删除排期包
    window.del_bookPackage = function(bookPackageId, delInfo) {
    	Ext.MessageBox.confirm('提示', '确定删除' + delInfo + ' ?', function(id) {
    		if(id != 'yes') return;
    		Ext.Ajax.request({
				method: 'post',
				url: '/myads/HTML/advflight/BookAction_deleteBookPackage.action',
				params: {bookPackageId: bookPackageId, projectId:projectId},
			   	success:function(resp){
			   		var obj=Ext.util.JSON.decode(resp.responseText);
			      	if(obj.result == 'success') {
			      		Ext.MessageBox.alert('提示', '删除成功！');
			      		bookPackage_ds.load({params: {start:0, limit:20}});
			      	}
			      	else {
			      		Ext.MessageBox.alert('错误', '删除错误！<br>' + obj.info);
			      	}
			   	}
			});
    	});
    }
	
	//保存排期
	function submitBook() {
		var bookType = bookTypeFormPanel.getBookType();
		var fo = new Object();
		if(bookType == 1) {//广告条预订
			if(!locationFormPanel.getForm().isValid()) {
				tabs.setActiveTab('locationTab');
				return;
			}
			Ext.apply(fo, locationFormPanel.getFormValues());
			
			if(selectAdvbarId_global == null && !fo.advbarId) {
				Ext.MessageBox.alert('提示', '必须选择广告条!');
				return;
			}
			if(!fo.advbarId) 
				fo.advbarId = selectAdvbarId_global;
			if(!fo.channelId) 
				fo.channelId = selectChannelId_global;
		}
		else if(bookType == 2) {//广告产品预订
			if(!advprudctSelectPanel.getForm().isValid()) {
				tabs.setActiveTab('advprudctTab');
				return;
			}
			
			Ext.apply(fo, advprudctSelectPanel.getFormValues());
		}
		else {
			return;
		}
		
		fo.bookpackageType = bookType;
		
		if(!directFormPanel.getForm().isValid()) {
			tabs.setActiveTab('directTab');
			return;
		}
		Ext.apply(fo, directFormPanel.getFormValues());
		
		if(!contentFormPanel.myValid()) {
			tabs.setActiveTab('contentTab');
			Ext.MessageBox.alert('提示', '请填写内容定向!');
			return;
		}
		Ext.apply(fo, contentFormPanel.getFormValues());
		
		//点位数据	
		var selectPoints = pointSelectPanel.getFormValues();
		if(selectPoints.length == 0) {
			Ext.MessageBox.alert('提示', '请选择点位!');
			return;
		}
		
		fo.pointDataAry = Ext.util.JSON.encode(selectPoints);
		fo.bookPackageId = bookPackageId_global;
		fo.projectId = projectId;
		fo.paramPriority = param_priority;
		
		Ext.MessageBox.show({
			title:'请等待',  
            msg:'保存数据中……',  
            width:240,  
            progress:false,  
            closable:false  
		});
        
		Ext.Ajax.request({
			method: 'post',
			url: '/myads/HTML/advflight/BookAction_save.action',
			params: fo,
		   	success:function(resp){
		   		Ext.MessageBox.hide();
		    	var obj=Ext.util.JSON.decode(resp.responseText);
		      	if(obj.result == 'success') {
		      		Ext.MessageBox.alert('提示', '保存成功！', function(id) {
							resetBook();
						}
					);
		      	}
		      	else {
		      		if(obj.info == 'BookConflict') {
		      			if(obj.detailInfo.ConflictInfo == 'noLastContent') {
		      				Ext.MessageBox.show({
								title:'请等待',  
					            msg:'余量不足！！' + obj.detailInfo.bookTime,  
					            width:240,  
					            progress:false,  
					            closable:true  
							});
		      			}
		      			else if(obj.detailInfo.ConflictInfo == 'SaledByOtherType') {
		      				Ext.MessageBox.show({
								title:'请等待',  
					            msg:'已经被其它方式售卖！！' + obj.detailInfo.bookTime,  
					            width:240,  
					            progress:false,  
					            closable:true  
							});
		      			}
		      		}
		      		else {
		      			Ext.MessageBox.alert('出错了！！！', '保存失败！！！！');
		      		}
		      	}
		    }
		});
	}
	
	//保存成功后的处理
	function resetBook() {
		window.location.href = '/myads/HTML/book/book.jsp?projectId=' + projectId;
	}
	
	//修改优先级
    var updatePriorityWin = new com.myads.UpdatePriorityWin();
    updatePriorityWin.on('updatePriorityOver', function() {
    	bookPackage_grid.getStore().reload();
    });
    
    window.updatePriority = function(bookPackageId, priority) {
    	updatePriorityWin.setPriority(priority);
    	updatePriorityWin.setBookPackageId(bookPackageId);
    	updatePriorityWin.show();
    }
    
	function refrshPreviewGridTab() {
		Ext.MessageBox.show({  
            title:'请等待',  
            msg:'读取数据中……',  
            width:240,  
            progress:false,  
            closable:false  
        });  
        
		Ext.Ajax.request({
			method: 'post',
			url: '/myads/HTML/advflight/PointGridGroupPreviewAction_getPointGrid.action',
			params:{projectId:projectId},
		   	success:function(resp){
		   		var jsonData = Ext.util.JSON.decode(resp.responseText);
		   		preview_grid = createGroupGrid(jsonData);
		   		gridTab.remove("点位预览");
				gridTab.add({
					id: '点位预览',
					title: '点位预览',
					layout: 'fit',
					items:[preview_grid]
				});
				gridTab.setActiveTab("点位预览");
				
				(function(){
					var girdcount = 0;
					preview_grid.getStore().each(function(r){
						
			            if(r.get('useTypeName')=='配送'){
			                preview_grid.getView().getRow(girdcount).style.backgroundColor='#99CC00';
			            }
			            
			            for(var j = 0; j < preview_grid.plugins.config.rows[1].length; j++) {
			            	if(preview_grid.plugins.config.rows[1][j].header == 'S') {
			            		preview_grid.getView().getCell(girdcount, j).style.backgroundColor='#FFFF00';
			            	}
			            }
			            
			            girdcount=girdcount+1;
			        });
				}).defer(500);
				
				Ext.MessageBox.hide();
		   	}
		});
	}
	
	function createGroupGrid(jsonData) {
        var groupRows = [];
        groupRows[0] = jsonData.topGroup;
        groupRows[1] = jsonData.secondGroup;
        
        var group = new Ext.ux.grid.ColumnHeaderGroup({
            rows: groupRows
        });

        var grid = new Ext.grid.GridPanel({
        	region: 'center',
        	split: true,
	        border:false,
            store: new Ext.data.JsonStore({
                fields: jsonData.fields,
                data: jsonData.data
            }),
            columns: jsonData.columns,
            plugins: group,
            tbar: new Ext.Toolbar({
				items : [
					{
						text: '导出Excel',
						handler: function() {
							window.location.href = '/myads/HTML/advflight/PointGridGroupPreviewAction_exportExcel.action?projectId=' + projectId; 
						}
					}
				]
	        })
        });
        
        return grid;
	}
	
});