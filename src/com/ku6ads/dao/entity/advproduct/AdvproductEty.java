package com.ku6ads.dao.entity.advproduct;

import com.ku6ads.dao.entity.ExtEntity;

public class AdvproductEty extends ExtEntity {

	private Integer id;
	private String advproductName;
	private String type;
	private String remark;
	
	private Integer advbarCount;

	public Integer getId() {
		return this.id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public String getAdvproductName() {
		return this.advproductName;
	}
	public void setAdvproductName(String advproductName) {
		this.advproductName = advproductName;
	}

	public String getType() {
		return this.type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public String getRemark() {
		return this.remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getAdvbarCount() {
		return advbarCount;
	}
	public void setAdvbarCount(Integer advbarCount) {
		this.advbarCount = advbarCount;
	}
	
}