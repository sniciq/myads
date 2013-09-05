package com.ku6ads.struts.basic;

import java.util.List;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.ku6ads.dao.entity.basic.Resource;
import com.ku6ads.dao.iface.basic.ResourceDao;
import com.opensymphony.xwork2.ActionSupport;
import com.ku6ads.util.AjaxOut;
import com.ku6ads.util.EntityReflect;
import com.ku6ads.util.ExtLimit;

public class ResourcesAction extends ActionSupport {
	private static final long serialVersionUID = 4421287836625761608L;
	private Resource resource;
	private ResourceDao resourceDao;
	
	
	public void showAll() {
		ExtLimit limit = (ExtLimit) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), ExtLimit.class);
		Resource serachEty = new Resource();
		serachEty.setExtLimit(limit);
		int count = resourceDao.selectByResourceCount(serachEty);
		List<Resource> resourceList = resourceDao.selectByResourceLimit(serachEty);
		AjaxOut.responseJSonGrid(ServletActionContext.getResponse(), resourceList, count, null);
	}
	
	public void save() {
		JSONObject obj = new JSONObject();
		obj.put("success",true);
		
		try {
			Resource resource = (Resource) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), Resource.class);
			if(resource.getResourceId() != null) {
				resourceDao.updateResource(resource);
			}
			else {
				resourceDao.insertResource(resource);
			}
			obj.put("result", "success");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), obj.toString());
	}
	
	public void getResourceDetail() {
		String resourceId = ServletActionContext.getRequest().getParameter("resourceId");
		Resource resource = resourceDao.selectByResourceId(Integer.parseInt(resourceId));
		JSONObject retObj = new JSONObject();
		retObj.put("success",true);
		retObj.put("data", resource);
		AjaxOut.responseText(ServletActionContext.getResponse(), retObj.toString());
	}
	
	public Resource getResource() {
		return resource;
	}
	public void setResource(Resource resource) {
		this.resource = resource;
	}
	public ResourceDao getResourceDao() {
		return resourceDao;
	}
	public void setResourceDao(ResourceDao resourceDao) {
		this.resourceDao = resourceDao;
	}
}
