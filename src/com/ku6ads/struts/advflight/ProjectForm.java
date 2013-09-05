package com.ku6ads.struts.advflight;

import com.ku6ads.dao.entity.advflight.Project;

public class ProjectForm extends Project {
	private String useTypeName;
	private String areaName;
	
	public String getUseTypeName() {
		return useTypeName;
	}
	public void setUseTypeName(String useTypeName) {
		this.useTypeName = useTypeName;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
}
