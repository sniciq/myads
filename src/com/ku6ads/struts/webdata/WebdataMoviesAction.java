package com.ku6ads.struts.webdata;

import java.text.SimpleDateFormat;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.ku6ads.dao.entity.webdata.WebdataMoviesMaintain;
import com.ku6ads.dao.entity.webdata.WebdataMoviesSellstate;
import com.ku6ads.services.iface.webdata.WebdataMoviesMaintainService;
import com.ku6ads.services.iface.webdata.WebdataMoviesSellstateService;
import com.ku6ads.services.iface.webdata.WebdataMoviesService;
import com.ku6ads.util.AjaxOut;
import com.ku6ads.util.EntityReflect;
import com.ku6ads.util.ExtLimit;
import com.ku6ads.util.XLSExportor;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 片单维护管理
 * @author YangHanguang
 *
 */
public class WebdataMoviesAction extends ActionSupport {
	
	private static final long serialVersionUID = 2894632295209284214L;
	private WebdataMoviesService webdatamoviesService;
	private WebdataMoviesMaintainService webdataMoviesMaintainService;
	private WebdataMoviesSellstateService webdataMoviesSellstateService;
	
	private Logger logger = Logger.getLogger(WebdataMoviesAction.class);
	
	@SuppressWarnings("unchecked")
	public void search() {
		try {
			WebdataMoviesForm searchEty = (WebdataMoviesForm) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), WebdataMoviesForm.class);
			ExtLimit extLimit = (ExtLimit) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), ExtLimit.class);
			searchEty.setExtLimit(extLimit);
			searchEty.setStatus(0);
			int count = webdatamoviesService.selectLimitCount(searchEty);
			List dataList = webdatamoviesService.selectByLimit(searchEty);
			AjaxOut.responseJSonGrid(ServletActionContext.getResponse(), dataList, count, new SimpleDateFormat("yyyy-MM-dd"));
		}
		catch (Exception e) {
			logger.error("", e);
		}
	}
	
	/**
	 * 得到维护列表
	 */
	@SuppressWarnings("unchecked")
	public void getMovieMaintainList() {
		try {
			WebdataMoviesMaintain maintainEty = (WebdataMoviesMaintain) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), WebdataMoviesMaintain.class);
			ExtLimit extLimit = (ExtLimit) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), ExtLimit.class);
			maintainEty.setExtLimit(extLimit);
			maintainEty.setStatus(0);
			int count = webdataMoviesMaintainService.selectLimitCount(maintainEty);
			List dataList = webdataMoviesMaintainService.selectByLimit(maintainEty);
			AjaxOut.responseJSonGrid(ServletActionContext.getResponse(), dataList, count, new SimpleDateFormat("yyyy-MM-dd"));
		}
		catch (Exception e) {
			logger.error("", e);
		}
	}
	
	/**
	 * 保存维护信息
	 */
	public void saveMovieMaintain() {
		JSONObject obj = new JSONObject();
		obj.put("success",true);
		try {
			WebdataMoviesMaintain maintainEty = (WebdataMoviesMaintain) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), WebdataMoviesMaintain.class);
			if(!webdataMoviesMaintainService.checkSave(maintainEty)) {
				obj.put("result", "error");
				obj.put("info", "购买方式冲突！");
			}
			else {
				if(maintainEty.getId() == null) {
					webdataMoviesMaintainService.insert(maintainEty);
				}
				else {
					webdataMoviesMaintainService.updateById(maintainEty);
				}
				obj.put("result", "success");
			}
		}
		catch (Exception e) {
			logger.error("", e);
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), obj.toString());
	}
	
	/**
	 * 删除维护信息
	 */
	public void deleteMovieMaintain() {
		JSONObject retObj = new JSONObject();
		retObj.put("success",true);
		try {
			String id = ServletActionContext.getRequest().getParameter("id");
			webdataMoviesMaintainService.deleteById(Integer.parseInt(id));
			retObj.put("result", "success");
		}
		catch (Exception e) {
			logger.error("", e);
			retObj.put("result","error");
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), retObj.toString());
	}
	
	/**
	 * 得到销售列表
	 */
	@SuppressWarnings("unchecked")
	public void getMovieSellstateList() {
		try {
			WebdataMoviesSellstate sellState = new WebdataMoviesSellstate();
			String movieId = ServletActionContext.getRequest().getParameter("movieId");
			sellState.setMovieId(Integer.parseInt(movieId));
			sellState.setStatus(0);
			int count = webdataMoviesSellstateService.selectLimitCount(sellState);
			List dataList = webdataMoviesSellstateService.selectByLimit(sellState);
			AjaxOut.responseJSonGrid(ServletActionContext.getResponse(), dataList, count, new SimpleDateFormat("yyyy-MM-dd"));
		}
		catch (Exception e) {
			logger.error("", e);
		}
	}
	
	/**
	 * 保存销售信息
	 */
	public void saveMovieSellstate() {
		JSONObject obj = new JSONObject();
		obj.put("success",true);
		try {
			WebdataMoviesSellstate sellState = (WebdataMoviesSellstate) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), WebdataMoviesSellstate.class);
			if(sellState.getId() == null) {
				webdataMoviesSellstateService.insert(sellState);
			}
			else {
				webdataMoviesSellstateService.updateById(sellState);
			}
			obj.put("result", "success");
		}
		catch (Exception e) {
			logger.error("", e);
			obj.put("result","error");
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), obj.toString());
	}
	/**
	 * 删除销售信息
	 */
	public void deleteMovieSellstate() {
		JSONObject retObj = new JSONObject();
		retObj.put("success",true);
		try {
			String id = ServletActionContext.getRequest().getParameter("id");
			webdataMoviesSellstateService.deleteById(Integer.parseInt(id));
			retObj.put("result", "success");
		}
		catch (Exception e) {
			logger.error("", e);
			retObj.put("result", "error");
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), retObj.toString());
	}
	
	public void export() {
		try {
			WebdataMoviesForm searchEty = (WebdataMoviesForm) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), WebdataMoviesForm.class);
			searchEty.setStatus(new Integer(0));
			ExtLimit limit = (ExtLimit) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), ExtLimit.class);
			XLSExportor.doExport(ServletActionContext.getRequest(), ServletActionContext.getResponse(), webdatamoviesService.getBaseDao(), searchEty, "FilmData", limit);
			return;
		}
		catch (Exception e) {
			logger.error("", e);
		}
	}
	
	/**
	 * 保存片单信息
	 */
	public void save() {
		try {
			
		}
		catch (Exception e) {
			logger.error("", e);
		}
	}
	
	public WebdataMoviesService getWebdatamoviesService() {
		return webdatamoviesService;
	}

	public void setWebdatamoviesService(WebdataMoviesService webdatamoviesService) {
		this.webdatamoviesService = webdatamoviesService;
	}

	public WebdataMoviesMaintainService getWebdataMoviesMaintainService() {
		return webdataMoviesMaintainService;
	}

	public void setWebdataMoviesMaintainService(
			WebdataMoviesMaintainService webdataMoviesMaintainService) {
		this.webdataMoviesMaintainService = webdataMoviesMaintainService;
	}

	public WebdataMoviesSellstateService getWebdataMoviesSellstateService() {
		return webdataMoviesSellstateService;
	}

	public void setWebdataMoviesSellstateService(
			WebdataMoviesSellstateService webdataMoviesSellstateService) {
		this.webdataMoviesSellstateService = webdataMoviesSellstateService;
	}
	
}
