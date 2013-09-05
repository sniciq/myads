package com;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.ku6ads.util.AjaxOut;

public class HelloAction {
	public void test() {
		JSONObject obj = new JSONObject();
		obj.put("name", "���");
		obj.put("success",true);
//		failure:true
		AjaxOut.responseText(ServletActionContext.getResponse(), obj.toString());
	}
}
