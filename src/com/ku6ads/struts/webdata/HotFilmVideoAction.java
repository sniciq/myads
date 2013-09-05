package com.ku6ads.struts.webdata;

import java.text.SimpleDateFormat;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.ku6ads.dao.entity.webdata.HotfilmVideo;
import com.ku6ads.services.iface.webdata.HotfilmVideoService;
import com.ku6ads.util.AjaxOut;
import com.ku6ads.util.EntityReflect;
import com.ku6ads.util.ExtLimit;
import com.opensymphony.xwork2.ActionSupport;

public class HotFilmVideoAction extends ActionSupport {
	
	private static final long serialVersionUID = 509594076667895929L;
	private Logger logger = Logger.getLogger(HotFilmVideoAction.class);
	
	private HotfilmVideoService hotfilmVideoService;
	
	/**
	 * 查询
	 */
	public void search() {
		try {
			ExtLimit limit = (ExtLimit) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), ExtLimit.class);
			HotfilmVideo searchEty = (HotfilmVideo) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), HotfilmVideo.class);
			searchEty.setExtLimit(limit);
			
			int count = hotfilmVideoService.selectLimitCount(searchEty);
			List<HotfilmVideo> list = hotfilmVideoService.selectByLimit(searchEty);
			AjaxOut.responseJSonGrid(ServletActionContext.getResponse(), list, count, new SimpleDateFormat("yyyy-MM-dd"));
		}
		catch (Exception e) {
			logger.error("", e);
		}
	}
	
	/**
	 * 保存(插入和修改)
	 */
	public void save() {
		JSONObject obj = new JSONObject();
		obj.put("success",true);
		try {
			HotfilmVideo ety = (HotfilmVideo) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), HotfilmVideo.class);
			if(ety.getId() == null) {
				hotfilmVideoService.insert(ety);
			}
			else {
				hotfilmVideoService.updateById(ety);
			}
		}
		catch (Exception e) {
			logger.error("", e);
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), obj.toString());
	}
	
	/**
	 * 查看详细信息
	 */
	public void showDetail() {
		JSONObject retObj = new JSONObject();
		retObj.put("success",true);
		try {
			String id = ServletActionContext.getRequest().getParameter("id");
			HotfilmVideo hotFilmEty = (HotfilmVideo) hotfilmVideoService.selectById(Integer.parseInt(id));
			retObj.put("data", hotFilmEty);
		}
		catch (Exception e) {
			logger.error("", e);
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), retObj.toString());
	}
	
	/**
	 * 单个删除
	 */
	public void deleteById() {
		JSONObject retObj = new JSONObject();
		retObj.put("success",true);
		try {
			String id = ServletActionContext.getRequest().getParameter("id");
			hotfilmVideoService.deleteById(Integer.parseInt(id));
			retObj.put("result", "success");
		}
		catch (Exception e) {
			logger.error("", e);
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), retObj.toString());
	}
	
	/**
	 * 导出
	 */
	public void export() {
		
	}

	public HotfilmVideoService getHotfilmVideoService() {
		return hotfilmVideoService;
	}
	public void setHotfilmVideoService(HotfilmVideoService hotfilmVideoService) {
		this.hotfilmVideoService = hotfilmVideoService;
	}
	
}
