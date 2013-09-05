package com.ku6ads.struts.advflight;




import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.ku6ads.services.iface.advflight.PathToCDNService;
import com.opensymphony.xwork2.ActionSupport;

public class MakeMPathAtion extends ActionSupport{

	PathToCDNService pathToCDNService;
	private Logger log = Logger.getLogger(MakeMPathAtion.class);
	private static final long serialVersionUID = 1L;


	/**
	 * 生成路径
	 */
	public void makeMpath() {
		String filePath = ServletActionContext.getRequest().getRealPath("/");
		pathToCDNService.writeFile(filePath);
	}
	
	
	//-------------GETTER��SERTTER---------------//
	public PathToCDNService getPathToCDNService() {
		return pathToCDNService;
	}

	public void setPathToCDNService(PathToCDNService pathToCDNService) {
		this.pathToCDNService = pathToCDNService;
	}
	
}
