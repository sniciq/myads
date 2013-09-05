package com.ku6ads.struts.sysconfig;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.ku6ads.dao.entity.sysconfig.Site;
import com.ku6ads.services.iface.sysconfig.SiteService;
import com.ku6ads.util.AjaxOut;
import com.ku6ads.util.EntityReflect;
import com.ku6ads.util.ExtLimit;
import com.ku6ads.util.TypeConverterUtil;
import com.opensymphony.xwork2.ActionSupport;

public class SiteAction extends ActionSupport {

	private static final long serialVersionUID = 3905159867337538938L;
	private SiteService siteService;

	public void save() {
		JSONObject obj = new JSONObject();
		obj.put("success", true);
		try {
			Site site = (Site) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), Site.class);
			if (site.getSiteId() != null) {
				site.setModifyTime(new Date());
				siteService.updateById(site);
			} else {
				site.setCreateTime(new Date());
				site.setStatus(0);
				siteService.insert(site);
			}
			obj.put("result", "success");
		} catch (Exception e) {
			e.printStackTrace();
			obj.put("result", "error");
			obj.put("info", e.getMessage());
			// FIXME 修改前台JS失败时的处理
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), obj.toString());
	}

	public void getSiteList() {
		List<Site> list = siteService.selectSite();
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		for (int i = 0; i < list.size(); i++) {
			Site site = list.get(i);
			sb.append("['" + site.getSiteId() + "','" + site.getSiteName() + "']");
			if (i < list.size() - 1)
				sb.append(",");
		}
		sb.append("]");
		AjaxOut.responseText(ServletActionContext.getResponse(), sb.toString());
	}

	/**
	 * 获得部门列表全部信息;
	 */
	@SuppressWarnings("unchecked")
	public void showAll() {
		try {
			ExtLimit limit = (ExtLimit) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), ExtLimit.class);
			Site site = (Site) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), Site.class);
			site.setExtLimit(limit);
			int count = siteService.selectLimitCount(site);
			List<Site> siteList = siteService.selectByLimit(site);
			AjaxOut.responseJSonGrid(ServletActionContext.getResponse(), siteList, count, new SimpleDateFormat("yyyy-MM-dd"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获得对应的部门信息;
	 */
	public void getSiteDetail() {
		JSONObject retObj = new JSONObject();
		retObj.put("success", true);
		try {
			String id = ServletActionContext.getRequest().getParameter("siteId");
			Site site = (Site) siteService.selectById(TypeConverterUtil.parseInt(id));
			retObj.put("data", site);
		} catch (Exception e) {
			e.printStackTrace();
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), retObj.toString());
	}

	/**
	 * 删除记录;
	 */
	public void delete() {
		JSONObject retObj = new JSONObject();
		retObj.put("success", true);
		try {
			String ids = ServletActionContext.getRequest().getParameter("siteList");
			String[] idList = ids.split(",");
			String delMessage = "";
			for (int i = 0; i < idList.length; i++) {
				Integer deletesuccess = siteService.deleteCheckChannelsById(TypeConverterUtil.parseInt(idList[i]));
				if (deletesuccess == 0) {
					Site site = (Site) siteService.selectById(TypeConverterUtil.parseInt(idList[i]));
					delMessage = delMessage + site.getSiteName() + ",";
				}
			}
			if (idList.length == 1 && !delMessage.equals("")) {
				retObj.put("result", "use");
			} else if (!delMessage.equals("")) {
				retObj.put("delMessage", delMessage.substring(0, delMessage.length() - 1));
			} else {
				retObj.put("result", "success");
			}

		} catch (Exception e) {
			e.printStackTrace();
			retObj.put("result", "error");
			retObj.put("info", e.getMessage());
			// FIXME 修改前台JS失败时的处理
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), retObj.toString());
	}

	public SiteService getSiteService() {
		return siteService;
	}

	public void setSiteService(SiteService siteService) {
		this.siteService = siteService;
	}
}
