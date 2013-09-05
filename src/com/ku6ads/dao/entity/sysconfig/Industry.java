package com.ku6ads.dao.entity.sysconfig;

import com.ku6ads.dao.entity.ExtEntity;

/**
 * 区域 华东、华南等
 * @author liujunshi
 *
 */
public class Industry extends ExtEntity{

	private int IndustryId;
	private String IndustryName;
	public int getIndustryId() {
		return IndustryId;
	}
	public void setIndustryId(int IndustryId) {
		this.IndustryId = IndustryId;
	}
	public String getIndustryName() {
		return IndustryName;
	}
	public void setIndustryName(String IndustryName) {
		this.IndustryName = IndustryName;
	}
}
