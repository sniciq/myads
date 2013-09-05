package com.ku6ads.dao.entity.basic;

import com.ku6ads.dao.entity.ExtEntity;

public class BaseData extends ExtEntity{
	private Integer id;
	private String dataType;
	private String dataName;
	private String dataValue;
	private String remark;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getDataValue() {
		return dataValue;
	}
	public void setDataValue(String dataValue) {
		this.dataValue = dataValue;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getDataName() {
		return dataName;
	}
	public void setDataName(String dataName) {
		this.dataName = dataName;
	}
}
