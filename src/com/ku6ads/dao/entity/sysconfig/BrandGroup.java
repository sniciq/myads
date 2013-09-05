package com.ku6ads.dao.entity.sysconfig;

import com.ku6ads.dao.entity.ExtEntity;

/**
 * 品牌组
 * @author liujunshi
 *
 */
public class BrandGroup extends ExtEntity{

	private int brandGroupId;
	private int contactPersonId;
	private int areaId;
	private int productId;
	private int advertiserId;
	private String brandGroupName;
	public int getBrandGroupId() {
		return brandGroupId;
	}
	public void setBrandGroupId(int brandGroupId) {
		this.brandGroupId = brandGroupId;
	}
	public int getContactPersonId() {
		return contactPersonId;
	}
	public void setContactPersonId(int contactPersonId) {
		this.contactPersonId = contactPersonId;
	}
	public int getAreaId() {
		return areaId;
	}
	public void setAreaId(int areaId) {
		this.areaId = areaId;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public int getAdvertiserId() {
		return advertiserId;
	}
	public void setAdvertiserId(int advertiserId) {
		this.advertiserId = advertiserId;
	}
	public String getBrandGroupName() {
		return brandGroupName;
	}
	public void setBrandGroupName(String brandGroupName) {
		this.brandGroupName = brandGroupName;
	}
	
	
	
	
}
