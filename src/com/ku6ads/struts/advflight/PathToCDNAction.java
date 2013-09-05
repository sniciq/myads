package com.ku6ads.struts.advflight;

import org.apache.struts2.ServletActionContext;

import com.ku6ads.services.iface.advflight.PathToCDNService;
import com.opensymphony.xwork2.ActionSupport;

public class PathToCDNAction  extends ActionSupport {

	private static final long serialVersionUID = 1L;
	PathToCDNService pathToCDNService;

	public void getPathFile(){
		String filePath = ServletActionContext.getRequest().getRealPath("/");
		pathToCDNService.writeFile(filePath);
	}

	public PathToCDNService getPathToCDNService() {
		return pathToCDNService;
	}

	public void setPathToCDNService(PathToCDNService pathToCDNService) {
		this.pathToCDNService = pathToCDNService;
	}

	
}
