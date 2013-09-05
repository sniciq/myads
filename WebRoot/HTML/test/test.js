Ext.onReady(function() {
	
	
//	var a = new Ext.form.ComboBox();
//	a.show();
//	
//	var areaDirectObj = Ext.util.JSON.decode('北京');
//	alert(areaDirectObj);
	
	var nowTime = new Date();
	var s1 = Ext.util.Format.date(nowTime, "Y/m/d");
	var d1 = new Date(s1);
	
	var s2 = '2011/1/2';
	var d2 = new Date(s2);
	
	alert(Date.parse(d1) - Date.parse(d2));
	
});