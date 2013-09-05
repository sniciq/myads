Ext.onReady(function() {
	
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
		
		return String.format('<b>{0}&nbsp;<input type="checkbox" id="chk_day_' + record.data[colu].day + '" onclick="calanderSelectDay(' + rowIndex + ',' + columnIndex + ',this.checked)" /></b><br/><a style="color: #245EDC">{1}</a><br/><a style="color: #3A9A39">{2}</a>', dayText, allSize, lastSize);
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
			}
		}
	}
	
	//选中一天
	window.calanderSelectDay = function(rowIndex, columnIndex, isChecked) {
		var record = calander_grid.getStore().getAt(rowIndex);
		var fieldName = calander_grid.getColumnModel().getDataIndex(columnIndex)
		record.get(fieldName).select = isChecked; 
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
			}
		}
	}
	
	window.calanderSelectMonth = function(isChecked) {
		for(var i = 0; i < calander_grid.getStore().getTotalCount(); i++) {
			var record = calander_grid.getStore().getAt(i);
			
			document.getElementById('chk_week_' + i).checked = isChecked;
			document.getElementById('chk_weekDay_0').checked = isChecked;
			
			for(var j = 1; j < calander_grid.getColumnModel().getColumnCount(); j++) {
				if(document.getElementById('chk_weekDay_' + j) != null)
					document.getElementById('chk_weekDay_' + j).checked = isChecked;
				
				var fieldName = calander_grid.getColumnModel().getDataIndex(j);
				record.get(fieldName).select = isChecked;
				if(document.getElementById('chk_day_' + record.get(fieldName).day) != null)
					document.getElementById('chk_day_' + record.get(fieldName).day).checked = isChecked; 
			}
		}
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
	
	var calander_ds = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({url: '/myads/HTML/basic/CalendarDataAction_getCalendar.action'}),
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

	calander_ds.load({params: {start:0, limit:50}});
	
	calander_ds.on("load", function(s,records) {
		var rowIndex=0;
		
		var todayRow;
		var todayCol;
		
		s.each(function(r){
			var columnIndex=0;
			for(p in r.data) {
				if(r.data[p].canSelect == false) {//如果不可选择，则设置颜色为灰色
					calander_grid.getView().getCell(rowIndex, columnIndex).style.backgroundColor='#DCDCDC';
				}
				columnIndex = columnIndex + 1;
				
				if(r.data[p].today == true) {//得到今天
					todayRow = rowIndex;
					todayCol = columnIndex - 1;
				}
			}
			rowIndex = rowIndex + 1;
		});
		
		if(todayRow) {//标记今天
			(function() {
				calander_grid.getView().getCell(todayRow, todayCol).style.backgroundColor='#CCFFCC';
			}).defer(50);
		}
		
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
	
	var dataPicker = new Ext.form.DateField({
		fieldLabel: '日期',
		name: 'selTime',
		allowBlank:false,
		plugins: 'monthPickerPlugin',  
		format: 'Y-m',
		editable: false,
		value: new Date(),
		listeners:{
			'select':  function() {
				calander_ds.load({params: {start:0, limit:50, showDate:dataPicker.value}});
			}
		} 
	});
	
	var toolBar = new Ext.Toolbar({
		buttonAlign: 'center',
	    items:[
	    	new Ext.Button({
	    		text: '上月',
	    		handler: function() {
	    			var date = Date.parseDate(dataPicker.value, 'Y-m');
	    			date = date.add(Date.MONTH, -1);
	    			dataPicker.setValue(date);
	    			calander_ds.load({params: {start:0, limit:50, showDate:dataPicker.value}});
	    		}
	    	}),
	    	dataPicker,
	    	new Ext.Button({
	    		text: '下月',
	    		handler: function() {
	    			var date = Date.parseDate(dataPicker.value, 'Y-m');
	    			date = date.add(Date.MONTH, 1);
	    			dataPicker.setValue(date);
	    			calander_ds.load({params: {start:0, limit:50, showDate:dataPicker.value}});
	    		}
	    	}),
	    	new Ext.Button({
	    		text: '测试',
	    		handler: function() {
	    			var selCells = '';
	    			for(var i = 0; i < calander_grid.getStore().getTotalCount(); i++) {
	    				var record = calander_grid.getStore().getAt(i);
	    				
	    				var columnCount = calander_grid.getColumnModel().getColumnCount();
						for(var j = 1; j < columnCount; j ++) {
							var fieldName = calander_grid.getColumnModel().getDataIndex(j);
							if(record.get(fieldName).select) {
								selCells += record.get(fieldName).day + '|';
							}
						}
	    			}
	    			alert(selCells);
	    		}
	    	})
	    ]
   	});
   	
	var calander_grid = new Ext.grid.GridPanel({
		region:'north',
		el: 'calendar-grid',
		trackMouseOver:false,
		sm: selectionModel,
		width:360,
		height:360,
		ds: calander_ds,
		cm: calander_cm,
	    viewConfig: {
	    	forceFit: true
	    },
	    tbar: toolBar
	});
	calander_grid.render();
	
});