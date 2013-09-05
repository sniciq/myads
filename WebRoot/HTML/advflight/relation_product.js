Ext.onReady(function() {
	
	var advertisementId = Ext.get('advertisementId').dom.value;
	var bartemplateId = Ext.get('bartemplateId').dom.value;
	var relationType = Ext.get('relationType').dom.value;
	
	
	var root = new Ext.tree.AsyncTreeNode({
		id : '0',
		text: 'root'
	});
	
	var tree = new Ext.tree.TreePanel({
		rootVisible:false,
		region: 'center',
		margins:'3 0 3 3',
        cmargins:'3 3 3 3',
		width:220,
		border : false,
		autoScroll : true, 
		loader: new Ext.tree.TreeLoader(
			{
				dataUrl: '/myads/HTML/advflight/AdvRelationProductBookAction_getRelationProductBook.action',
				baseParams: {advertisementId:advertisementId, bartemplateId:bartemplateId, relationType: relationType}
			}
		),
		listeners:{
			"checkchange":function(node){
				checkChildren(node, node.attributes.checked);
			}
	　　},
		buttons:[
	        {
	        	text:'保存',
	        	handler: function() {
	        		var selectNodes = getNodeChildrenIds(root);
	        		Ext.Ajax.request({
						method: 'post',
						url: '/myads/HTML/advflight/AdvRelationProductBookAction_save.action',
					   	success:function(resp){
					    	var obj=Ext.util.JSON.decode(resp.responseText);
					      	if(obj.result == 'success') {
					      		Ext.MessageBox.alert('提示', '保存成功！', function() {
					      			var win = parent.Ext.getCmp('relation_win');
					      			win.hide();
					      		});
					      	}
					      	else {
					      		Ext.MessageBox.alert('提示', '保存失败！/n' + obj.info);
					      	}
					    },
					   	params: {advertisementId:advertisementId, bartemplateId:bartemplateId,selectNodes: selectNodes}
					});
	        	}
	        }
        ]
	});
	tree.setRootNode(root);
			
	function checkChildren(node, checked) {
		node.attributes.checked = checked;
		node.ui.checkbox.checked = checked;
		node.attributes.checked = checked; 

		var childrens = node.childNodes;
		for(var i = 0; i < childrens.length; i++) {
			checkChildren(childrens[i], checked);
		}
	}
	
	function getNodeChildrenIds(node) {
		var ids = '';
		if(node.isLeaf() && node.attributes.checked) {
			return node.id;
		}
		else {
			var childrens = node.childNodes;
			for(var i = 0; i < childrens.length; i++) {
				ids += getNodeChildrenIds(childrens[i]) + ',';
			}
		}
		return ids;
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
	
	new Ext.Viewport({
		layout: 'border',
		items:[
			tree
		]
	});
});