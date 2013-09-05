Ext.onReady(function(){
	
	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget='side';
	var cp = new Ext.state.CookieProvider();
	
	var loginForm = new Ext.form.FormPanel({
			frame: true,
			labelAlign : 'right',
			labelWidth : 70,
			items : [
				new Ext.form.TextField({
					id : 'username',
					name : 'username',
					value: 'admin',
					fieldLabel : '用户名',
					allowBlank : false,
					anchor : '90%',
					blankText : "用户名不能为空!"
					
				}), 
				new Ext.form.TextField({
					id : 'password',
					name : 'password',
					value: 'admin',
					fieldLabel : '密&nbsp;&nbsp;&nbsp;码',
					allowBlank : false,
					anchor : '90%',
					blankText : "密码不能为空!",
					inputType : 'password'
				}),
				new Ext.form.Checkbox({
					id: 'saveNameChk',
					inputValue: 1,
					boxLabel: '记住用户名'
				})
			],
			keys:[
				{
					key: [13],
                	fn: dologin	
				}
			],
			buttons : [
				{
					text : '登录',
					id: 'loginBtn',
					handler : function(btn) {
						dologin();
					}
				}, 
				{
					text : '清空',
					handler : function() {
						loginForm.getForm().reset();
					}
				}
			]
		});
		
		function dologin() {
			Ext.getCmp('loginBtn').disable();
			loginForm.form.doAction('submit', {
				url : './HTML/sysfun/LoginLocalAction_login.action',
				method: 'post',
				params: '',
				success: function(resp, action) {
					if (action.result.result == 'success') {
						
						if(Ext.getCmp('saveNameChk').checked) {
							cp.set("ku6sUserName", loginForm.form.findField("username").getValue());
						}
						else {
							cp.clear("ku6sUserName");
						}
						window.location.href = '/myads/HTML/main.html';
					} else {
						Ext.MessageBox.alert("提示", action.result.result, function() {
							window.location.href = '/myads/HTML/index.jsp';
						});
						
						
					}
				}
			});
		}
	
		var loginWin = new Ext.Window({
			title : '登录',
			width : 320,
			modal : true,
			autoHeight : true,
			closable : false,
			resizable : false,
			draggable : false,
			closeAction : 'hide',
			items : [loginForm]
		});
		loginWin.show();
		
		var cpUsername = cp.get("ku6sUserName");
		if(cpUsername != null && cpUsername != '') {
			loginForm.form.findField("username").setValue(cpUsername);
			Ext.getCmp('saveNameChk').setValue(1);
			Ext.getCmp('saveNameChk').el.dom.checked = true;
		}
});