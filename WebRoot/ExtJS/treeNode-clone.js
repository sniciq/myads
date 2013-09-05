Ext.override(Ext.tree.TreeNode, {  
	clone: function() {  
		var atts = this.attributes;  
		if(this.childrenRendered || this.loaded || !this.attributes.children) {  
			var clone = new Ext.tree.TreeNode(Ext.apply({}, atts));  
		}  
		else {  
			var newAtts = Ext.apply({}, atts);  
			newAtts.children = this.cloneUnrenderedChildren();  
			var clone = new Ext.tree.AsyncTreeNode(newAtts);  
		}  
		clone.text = this.text;  
	              
		for(var i=0; i<this.childNodes.length; i++){  
			clone.appendChild(this.childNodes[i].clone());  
		}  
	    return clone;  
    },  
      
    cloneUnrenderedChildren: function() {  
    	unrenderedClone = function(n) {  
        //n.id = undefined;  
	        if(n.children)  {  
	        	for(var j=0; j<n.children.length; j++) {  
	        		n.children[j] = unrenderedClone(n.children[j]);  
	        	}  
	        }     
	        return n;  
    	};  
      
    	var c = [];  
	    for(var i=0; i<this.attributes.children.length; i++) {  
		    c[i] = Ext.apply({}, this.attributes.children[i]);  
		    c[i] = unrenderedClone(c[i]);  
	    }  
	    return c;  
    }  
});