package com.ku6ads.struts.controller;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.ku6ads.util.AjaxOut;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 控制器
 * @author xuxianan
 *
 */
public class ControllerAction extends ActionSupport {

	private static final long serialVersionUID = -3943546514690984154L;
	private Logger logger = Logger.getLogger(ControllerAction.class);

	public void processingAdvposition() {

		try {
			String pid = ServletActionContext.getRequest().getParameter("p");
			String gid = ServletActionContext.getRequest().getParameter("gid");
			String aid = ServletActionContext.getRequest().getParameter("aid");

			String advposition = "<a href='http://www.ku6ads.com:8080/ku6ads/HTML/controller/" +
					"ControllerAction_show.action?gid="+gid+"&aid="+aid+"&pid="+pid+"'><img src='image/1.jpg' title='test1' /></a>";
			String advposition1 = "<img src='image/2.jpg' title='test2' />";
			String advposition3 = "<a href='http://www.ku6ads.com:8080/ku6ads/HTML/controller/" +
					"ControllerAction_show.action?gid="+gid+"&aid="+aid+"&pid="+pid+"'><img src='image/3.jpg' title='test3' /></a>";
			String advposition4 = "<img src='image/4.jpg' title='test4' />";

			List<String> advList = new ArrayList<String>();
			
			if (gid == null && aid == null && pid == null) {
				AjaxOut.responseText(ServletActionContext.getResponse(), "param is null");
			} else {
				
				if (aid.equals("1")) {
					advList.add(advposition);
					advList.add(advposition1);
				} else {
					advList.add(advposition3);
					advList.add(advposition4);
				}
				
				String[] pids = StringUtils.split(pid, ",");
				List<Object> objList = new ArrayList<Object>();
				for (int i = 0; i < pids.length; i++) {
					JSONObject json = new JSONObject();
					for (int j = 0; j < advList.size(); j++) {
						if (i == j) {
							json.put("text", pids[i]);
							json.put("value", advList.get(j));
						} else {
							continue;
						}
					}
					objList.add(json);
				}
				
				AjaxOut.responseText(ServletActionContext.getResponse(), JSONArray.fromObject(objList).toString());
			}
		} catch (Exception e) {
			logger.error("", e);
		}
	}

}
