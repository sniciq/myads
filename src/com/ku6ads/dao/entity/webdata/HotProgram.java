package com.ku6ads.dao.entity.webdata;

import com.ku6ads.util.ExtLimit;


public class HotProgram {
	private Integer id;
	private String name;
	private String remark;
	private ExtLimit extLimit;;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public ExtLimit getExtLimit() {
		return extLimit;
	}
	public void setExtLimit(ExtLimit extLimit) {
		this.extLimit = extLimit;
	}
	
}
