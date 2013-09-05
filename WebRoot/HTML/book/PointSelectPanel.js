Ext.namespace("com.myads.book.PointSelectPanel"); 
com.myads.book.PointSelectPanel = function(config) {
	var nowPointSelectPanel = this;
	this.addEvents('loadPointListOver');
	
	this.bookType = 1;//1：广告条预订，2: 广告产品预订
	this.bookId;
	this.saleTypeValue;
	this.saleTypeName;
	this.param_priority;
	var isContentDirect = 0;
	
	var selectDayArr = this.selectDayArr = new Array();
    
    function calander_renderOp(value, cellmeta, record, rowIndex, columnIndex, stor) {
		var colu = 'weekDay_' + (columnIndex - 1);
		var dayText = "";
		var allSize = "";
		var lastSize = "";
		
		if(record.data[colu].text == '') {
			return '';
		}
		else {
			dayText = record.data[colu].text;
			allSize = record.data[colu].allSize;
			lastSize = record.data[colu].lastSize;
		}
		return String.format('<b>{0}&nbsp;<input type="checkbox" id="chk_day_' + record.data[colu].day + '" onclick="calanderSelectDay(' + rowIndex + ',' + columnIndex + ',this.checked)" /></b><br/><a style="color: #3A9A39">{1}</a>', dayText, lastSize);
	}
	
	function calander_renderRowSel(value, cellmeta, record, rowIndex, columnIndex, stor) {
		return String.format('<input type="checkbox" id="chk_week_'+ rowIndex + '" onclick="selectWeek(' + rowIndex + ', this.checked)"/>');
	}
	
	//选中一周
	window.selectWeek = function(week, sel ) {
		var record = calander_grid.getStore().getAt(week);
		var columnCount = calander_grid.getColumnModel().getColumnCount();
		for(var i = 1; i < columnCount; i ++) {
			var fieldName = calander_grid.getColumnModel().getDataIndex(i);
			if(record.get(fieldName).canSelect) {
				record.get(fieldName).select = sel;
				if(document.getElementById('chk_day_' + record.get(fieldName).day) != null)
					document.getElementById('chk_day_' + record.get(fieldName).day).checked = sel; 
					
				refreshSelectDayArr(record.get(fieldName).dateStr, sel);	
			}
		}
	}
	
	//选中一天
	window.calanderSelectDay = function(rowIndex, columnIndex, isChecked) {
		var record = calander_grid.getStore().getAt(rowIndex);
		var fieldName = calander_grid.getColumnModel().getDataIndex(columnIndex)
		record.get(fieldName).select = isChecked; 
		
		refreshSelectDayArr(record.get(fieldName).dateStr, isChecked);
	}
	
	//选中当月的所有周几
	window.calanderSelectWeekday = function(weekDay, sel) {
		for(var i = 0; i < calander_grid.getStore().getTotalCount(); i++) {
			var record = calander_grid.getStore().getAt(i);
			var fieldName = calander_grid.getColumnModel().getDataIndex(weekDay);
			if(record.get(fieldName).canSelect && record.get(fieldName).weekday == weekDay) {
				record.get(fieldName).select = sel;
				if(document.getElementById('chk_day_' + record.get(fieldName).day) != null)
					document.getElementById('chk_day_' + record.get(fieldName).day).checked = sel; 
				refreshSelectDayArr(record.get(fieldName).dateStr, sel);
			}
		}
	}
	
	//选中整月
	window.calanderSelectMonth = function(isChecked) {
		for(var i = 0; i < calander_grid.getStore().getTotalCount(); i++) {
			var record = calander_grid.getStore().getAt(i);
			
			document.getElementById('chk_week_' + i).checked = isChecked;
			document.getElementById('chk_weekDay_0').checked = isChecked;
			
			for(var j = 1; j < calander_grid.getColumnModel().getColumnCount(); j++) {
				var fieldName = calander_grid.getColumnModel().getDataIndex(j);
				if(record.get(fieldName).canSelect == false) {
					continue;
				}
				
				if(document.getElementById('chk_weekDay_' + j) != null)
					document.getElementById('chk_weekDay_' + j).checked = isChecked;
				record.get(fieldName).select = isChecked;
				if(document.getElementById('chk_day_' + record.get(fieldName).day) != null)
					document.getElementById('chk_day_' + record.get(fieldName).day).checked = isChecked; 
				
				refreshSelectDayArr(record.get(fieldName).dateStr, isChecked);
			}
		}
	}
	
	//更新临时选择数组
	function refreshSelectDayArr(dateStr, isChecked) {
		if(dateStr == '')
			return;
			
		nowPointSelectPanel.selectDayArr.remove(dateStr);	
		if(isChecked) {
			nowPointSelectPanel.selectDayArr.push(dateStr);
		}
	}
	
	//刷新CheckBox状态
	function refreshChkBox() {
		var weekDays = ['weekDay_0', 'weekDay_1', 'weekDay_2', 'weekDay_3', 'weekDay_4', 'weekDay_5', 'weekDay_6'];
		calander_ds.each(function(r){
			for(var w = 0; w < weekDays.length; w++) {
				if(r.data[weekDays[w]].dateStr != '') {
					var selIndex = nowPointSelectPanel.selectDayArr.indexOf(r.data[weekDays[w]].dateStr);
					if(selIndex >= 0) {
						document.getElementById('chk_day_' + r.data[weekDays[w]].day).checked = true;
					}
					else {
						document.getElementById('chk_day_' + r.data[weekDays[w]].day).checked = false;
					}
				}
			}
		});
		
		var sumFlight = 0;
    	point_ds.each(function(rec){
    		sumFlight = sumFlight + parseInt(rec.data.flightNum);
	   	});
    	Ext.get('flightSumFieldId').dom.innerHTML = '合计：' + sumFlight;
	}
	
	var calander_cm = new Ext.grid.ColumnModel([
		{header:'<input type="checkbox" onclick="calanderSelectMonth(this.checked)" />', dataIndex:'op', renderer: calander_renderRowSel, sortable:false, resizable:false, menuDisabled:true },
	    {header:'日&nbsp;<input type="checkbox" id="chk_weekDay_0" onclick="calanderSelectWeekday(' + 1 + ',this.checked)"/>', dataIndex:'weekDay_0', renderer: calander_renderOp, sortable:false, resizable:false, menuDisabled:true },
	    {header:'一&nbsp;<input type="checkbox" id="chk_weekDay_1" onclick="calanderSelectWeekday(' + 2 + ',this.checked)"/>', dataIndex:'weekDay_1', renderer: calander_renderOp, sortable:false, resizable:false, menuDisabled:true},
	    {header:'二&nbsp;<input type="checkbox" id="chk_weekDay_2" onclick="calanderSelectWeekday(' + 3 + ',this.checked)"/>', dataIndex:'weekDay_2', renderer: calander_renderOp, sortable:false, resizable:false, menuDisabled:true},
	    {header:'三&nbsp;<input type="checkbox" id="chk_weekDay_3" onclick="calanderSelectWeekday(' + 4 + ',this.checked)"/>', dataIndex:'weekDay_3', renderer: calander_renderOp, sortable:false, resizable:false, menuDisabled:true},
	    {header:'四&nbsp;<input type="checkbox" id="chk_weekDay_4" onclick="calanderSelectWeekday(' + 5 + ',this.checked)"/>', dataIndex:'weekDay_4', renderer: calander_renderOp, sortable:false, resizable:false, menuDisabled:true},
	    {header:'五&nbsp;<input type="checkbox" id="chk_weekDay_5" onclick="calanderSelectWeekday(' + 6 + ',this.checked)"/>', dataIndex:'weekDay_5', renderer: calander_renderOp, sortable:false, resizable:false, menuDisabled:true},
	    {header:'六&nbsp;<input type="checkbox" id="chk_weekDay_6" onclick="calanderSelectWeekday(' + 7 + ',this.checked)"/>', dataIndex:'weekDay_6', renderer: calander_renderOp, sortable:false, resizable:false, menuDisabled:true}
	]);
	
	var calander_ds = this.calander_ds = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({url: '/myads/HTML/advflight/BookCalendarAction_getCalendar.action'}),
		remoteSort: false,
		reader: new Ext.data.JsonReader(
			{
				totalProperty: 'total',
				idProperty:'day',
				root: 'invdata'
			},
			[
				{name: 'weekDay_0'},
				{name: 'weekDay_1'},
				{name: 'weekDay_2'},
				{name: 'weekDay_3'},
				{name: 'weekDay_4'},
				{name: 'weekDay_5'},
				{name: 'weekDay_6'}
			]
		)
	});

	calander_ds.on("load", function(s,records) {
		var rowIndex=0;
		
		var todayRow;
		var todayCol;
		
		s.each(function(r){
			var columnIndex=0;
			for(p in r.data) {
				if(r.data[p].canSelect == false) {//如果不可选择，则设置颜色为灰色
					if(document.getElementById('chk_day_' + r.data[p].day) != null)
						document.getElementById('chk_day_' + r.data[p].day).style.display = 'none';
				}
				else {
					if(document.getElementById('chk_day_' + r.data[p].day) != null)
						document.getElementById('chk_day_' + r.data[p].day).style.display = '';
				}
				
				if(nowPointSelectPanel.saleTypeName == 'CPD') {
					if(r.data[p].contentStatus == 'full') 
						calander_grid.getView().getCell(rowIndex, columnIndex + 1).style.backgroundColor='#ECEEEF';
					else if(r.data[p].contentStatus == 'part') 
						calander_grid.getView().getCell(rowIndex, columnIndex + 1).style.backgroundColor='#CDF7E4';
					else if(r.data[p].contentStatus == 'empty') 
						calander_grid.getView().getCell(rowIndex, columnIndex + 1).style.backgroundColor='#FFFFFF';
				}
				
				columnIndex = columnIndex + 1;
				
				if(r.data[p].today == true) {//得到今天
					todayRow = rowIndex;
					todayCol = columnIndex - 1;
				}
				
				if(r.data[p].dateStr != '') {
					var selIndex = nowPointSelectPanel.selectDayArr.indexOf(r.data[p].dateStr);
					if(selIndex >= 0) {
						document.getElementById('chk_day_' + r.data[p].day).checked = true;
					}
				}
			}
			rowIndex = rowIndex + 1;
		});
	});
	
	var selectionModel = new Ext.grid.CellSelectionModel({
	});
	
	selectionModel.on("cellselect",function(sel,row,col) {//单元格选择事件，如果点击了不可选择的单元格，则不让选择
		if(col == 0) {
			return;
		}
			
		var record = calander_grid.getStore().getAt(row);
		var fieldName = calander_grid.getColumnModel().getDataIndex(col);
		if(!record.get(fieldName).canSelect)
			selectionModel.clearSelections(false);
		else 
			record.get(fieldName).select = true;
	});
	
	var calander_dataPicker = this.calander_dataPicker = new Ext.form.DateField({
		fieldLabel: '日期',
		name: 'selTime',
		width: 80,
		allowBlank:false,
		plugins: 'monthPickerPlugin',  
		format: 'Y-m',
		editable: false,
		value: new Date(),
		listeners:{
			'select':  function() {
				nowPointSelectPanel.refreshCalandar();
			}
		} 
	});
	
	var toolBar = new Ext.Toolbar({
//		buttonAlign: 'center',
	    items:[
	    	new Ext.Button({
	    		text: '上月',
	    		handler: function() {
	    			var dt = calander_dataPicker.value + '-01';
	    			var preMonthDate = Date.parseDate(dt, 'Y-m-d');
	    			preMonthDate = preMonthDate.add(Date.MONTH, -1);
	    			calander_dataPicker.setValue(preMonthDate);
	    			nowPointSelectPanel.refreshCalandar();
	    		}
	    	}),
	    	new Ext.Button({
	    		text: '下月',
	    		handler: function() {
	    			var dt = calander_dataPicker.value + '-01';
	    			var nextMonthDate = Date.parseDate(dt, 'Y-m-d');
	    			nextMonthDate = nextMonthDate.add(Date.MONTH, 1);
	    			calander_dataPicker.setValue(nextMonthDate);
	    			nowPointSelectPanel.refreshCalandar();
	    		}
	    	}),
	    	calander_dataPicker
	    ]
   	});
   	
	var calander_grid = new Ext.grid.GridPanel({
		region:'north',
		trackMouseOver:false,
		sm: selectionModel,
		width:390,
		height:280,
		ds: calander_ds,
		cm: calander_cm,
	    viewConfig: {
	    	forceFit: true
	    },
	    tbar: toolBar
	});
	
	var point_ds; //点位数据
    var pointGrid;
    var flightLable;
	var selFlightBtn;
	
	var point_sm = new Ext.grid.CheckboxSelectionModel();
	var point_cm = new Ext.grid.ColumnModel([
		point_sm,
		new Ext.grid.RowNumberer(),
		{header:'日期', dataIndex:'startTime', sortable:true},
		{header:'投放量', dataIndex:'flightNum', sortable:true, editor: new Ext.form.TextField({allowBlank: false})},
		{header:'操作', dataIndex:'startTime', sortable:false, renderer: point_renderOp}
	]);
	
	function point_renderOp(value, cellmeta, record, rowIndex, columnIndex, stor) {
		if(record.data.canEdit != 'true')
			return '';
			
		var id = record.data.id;
		var dataValue = record.data.startTime; 
		var delStr = '<a href="#" onclick=\"del_book(\''+rowIndex+'\', \'' + dataValue + '\');\">删除</a>';
		return delStr;
	}
	
	//刷新合计
	function refreshSumFlight() {
		var sumFlight = 0;
    	point_ds.each(function(rec){
    		sumFlight = sumFlight + parseInt(rec.data.flightNum);
	   	});
    	Ext.get('flightSumFieldId').dom.innerHTML = '合计：' + sumFlight;
	}
	
	window.del_book = function(rowIndex, info) {
		var record = point_ds.getAt(rowIndex);
		point_ds.remove(record);
		nowPointSelectPanel.selectDayArr.remove(record.data.startTime);
		refreshChkBox();
		pointGrid.getView().refresh();
	}
	
	point_ds = this.point_ds = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({url: '/myads/HTML/advflight/BookAction_getBookList.action'}),
		remoteSort: false,
		reader: new Ext.data.JsonReader(
			{
				totalProperty: 'total',
				idProperty:'startTime',
				root: 'invdata'
			},
			[
				{name: 'id'},
				{name: 'startTime'},
				{name: 'flightNum'},
				{name: 'canEdit'}
			]
		)
	});
	point_ds.setDefaultSort("startTime", "ASC");
	
	point_ds.on("load", function(s,records) {
		nowPointSelectPanel.selectDayArr = new Array();
		nowPointSelectPanel.point_ds.each(function(rec){
	    	nowPointSelectPanel.selectDayArr.push(rec.data.startTime);
	   	});
	   	refreshChkBox();
	   	nowPointSelectPanel.refreshSumFlight();
		nowPointSelectPanel.fireEvent('loadPointListOver');
	});
	
	Ext.apply(this, {
		id:'main-panel',
	    baseCls:'x-plain',
	    layout:'table',
	    layoutConfig: {columns:5},
	    defaults: {frame:true, width:300, height: 280},
	    items:[{
	        colspan:2,
	        width:400,
	        items:[
	        	calander_grid
	        ]
	    },{
	        width:100,
	        layout: {
                type:'vbox',
                padding:'5',
                pack:'center',
                align:'center'
            },
			items: [
				selFlightBtn = new Ext.Button({
					text: '选择点位',
			        handler: function(){
	                	//删除原来的
	                	point_ds.each(function(rec){
	                		var selIndex = nowPointSelectPanel.selectDayArr.indexOf(rec.data.startTime);
	                		if(selIndex < 0 && rec.data.canEdit == 'true')
								point_ds.remove(rec);
					   	});
	                	
					   	var flightNum = 0;
	                	for(var k = 0; k < nowPointSelectPanel.selectDayArr.length; k++) {
	                		var _dayStr = nowPointSelectPanel.selectDayArr[k];
	                		var _record = point_ds.getById(_dayStr);
							var _id = '';
							if(nowPointSelectPanel.saleTypeName == 'CPD') {
		                		flightNum = 1;
		                	}
		                	else if(nowPointSelectPanel.saleTypeName == 'CPM') {
		                		flightNum = 0;
		                	}
		                	
							if(_record != null) {
								_id = _record.data.id;
								flightNum = _record.data.flightNum;
								if(_record.data.canEdit == 'true') {
									point_ds.remove(_record);
								}
								else {
									continue;
								}
							}
							
							var _rs = new Ext.data.Record({
								startTime: _dayStr, flightNum: flightNum, id:_id, canEdit: 'true'
							},_dayStr) ;
							point_ds.add(_rs);
	                	}
	                	
	                	point_ds.sort('startTime', 'ASC');
			        	pointGrid.getView().refresh();
			        	
			        	refreshSumFlight();
			        }
				})
			]
	    },{
	        colspan:2,
	        width:600,
	        layout:'fit',
	        items:[
	        	pointGrid = new Ext.grid.EditorGridPanel({
					region: 'center',
					sm: point_sm,
					ds: point_ds,
					cm: point_cm,
					autoExpandColumn: 'startTime',
				    viewConfig: {
				    	forceFit: true
				    },
				    listeners:{
				        beforeedit: function(e){
				           return e.record.data.canEdit == 'true';
				        }
				    },
				    bbar: new Ext.Toolbar({
				    	buttons: [
							'-',
							'投放量',
							{xtype: 'numberfield', name: 'flightNum', id:'flightNumDomId', fieldLabel: '投放量',width:50},
							{
								text:'设置',
								id:'flightNumBtn',
								handler: function() {
									var flightNum = Ext.get('flightNumDomId').dom.value;
									if(flightNum == 0)
										return;
										
						        	if(nowPointSelectPanel.saleTypeName == 'CPD') {
				                		flightNum = 1;
				                	}
				                	
									var rs=point_sm.getSelections();
									Ext.each(rs,function(){
										var _record = point_ds.getById(this.get('startTime'));
										point_ds.remove(_record);
										var _rs = new Ext.data.Record({
											startTime: this.get('startTime'), flightNum: flightNum, id:this.get('id'), canEdit: 'true'
										},this.get('startTime')) ;
										point_ds.add(_rs);
									});
									
									pointGrid.getView().refresh();
									refreshSumFlight();
								}
							},
							'-',
							{
								xtype: 'label',
								id: 'flightSumFieldId',
								text:'合计：0'
							}
						]
				    })
				})
	        ]
	    }]
	});
	
	com.myads.book.PointSelectPanel.superclass.constructor.apply(this, arguments);
}

Ext.extend(com.myads.book.PointSelectPanel, Ext.FormPanel, {
	onRender: function() {
		com.myads.book.PointSelectPanel.superclass.onRender.apply(this, arguments);
	},
	
	configCalandarData: function(bookType, id, saleTypeValue, saleTypeName, isContentDirect, param_priority) {
		this.bookType = bookType;
		this.bookId = id;
		this.saleTypeValue = saleTypeValue;
		this.saleTypeName = saleTypeName;
		this.param_priority = param_priority;
	},
	
	setIsContentDirect: function(isContentDirect) {
		this.isContentDirect = isContentDirect;
	},
	
	refreshCalandar: function() {
		if(this.bookId == '' || this.saleTypeValue == '')
			return;
		this.calander_ds.load({params: {start:0, limit:50, bookType: this.bookType, showDate:this.calander_dataPicker.value, bookId: this.bookId, saleType: this.saleTypeName,saleTypeValue: this.saleTypeValue, isContentDirect: this.isContentDirect,directArea:'',paramPriority: this.param_priority}});
	},
	
	removeAllPoint: function() {
		this.point_ds.removeAll();
	},
	
	getFormValues: function() {
		var selFuns = [];
	    this.point_ds.each(function(rec){
	    	selFuns.push(rec.data);
	   	});
		return selFuns;
	},
	
	virefBBar: function(saleTypeName) {
		if(saleTypeName == 'CPD') {
    		Ext.get('flightNumBtn').hide();
    		Ext.get('flightNumDomId').hide();
    	}
    	else if(saleTypeName == 'CPM') {
    		 Ext.get('flightNumBtn').show();
    		 Ext.get('flightNumDomId').show();
    	}
	},
	
	refreshSumFlight: function() {
		var sumFlight = 0;
    	this.point_ds.each(function(rec){
    		sumFlight = sumFlight + parseInt(rec.data.flightNum);
	   	});
    	Ext.get('flightSumFieldId').dom.innerHTML = '合计：' + sumFlight;
	},
	
	loadPointData: function(bookPackageId) {
		this.point_ds.load({params:{start:0, limit:200000, bookPackageId:bookPackageId}});
	}
	
});
