<%@ page pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>点位预览</title>
		<link rel="stylesheet" type="text/css" href="../../ExtJS/resources/css/ext-all.css"/>
		<script type="text/javascript" src="../../ExtJS/adapter/ext/ext-base.js"></script>
		<script type="text/javascript" src="../../ExtJS/ext-all.js"></script>
		<script type="text/javascript" src="../../ExtJS/MonthPickerPlugin.js"></script>
		
		<script type="text/javascript" src="../../ExtJS/examples/ux/ColumnHeaderGroup.js"></script>
		<link rel="stylesheet" type="text/css" href="../../ExtJS/examples/ux/css/ColumnHeaderGroup.css" />
		<link rel="stylesheet" type="text/css" href="../../ExtJS/examples/grid/grid-examples.css" />
    
		<script type="text/javascript">
			Ext.onReady(function() {	
				Ext.QuickTips.init();
				var projectId = Ext.get('projectId').dom.value;

				var centerPanel = new Ext.Panel({
					region:'center',
					frame:true,
					layout: 'fit',
			        defaults:{autoScroll: true},
			        deferredRender :false
				});
				
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
				   		var preview_grid = createGroupGrid(jsonData);
				   		centerPanel.remove("点位预览");
						centerPanel.add({
							id: '点位预览',
							title: '点位预览',
							layout: 'fit',
							items:[preview_grid]
						});
						centerPanel.doLayout();
				   		
				   		
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
						}).defer(1000);
						
						Ext.MessageBox.hide();
				   	}
				});

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

				new Ext.Viewport({
					layout: 'border',
					items:[
					       centerPanel
					]
				});
			});
		</script>
	</head>
	<body>
		<input type="hidden" id="projectId" name="projectId" value="${param.projectId}">
		<div id="grid"></div>
	</body>
</html>