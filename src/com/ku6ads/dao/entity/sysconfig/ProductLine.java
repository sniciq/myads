package com.ku6ads.dao.entity.sysconfig;

import com.ku6ads.dao.entity.ExtEntity;

/**
 * 产品线
 * @author liujunshi
 *
 */
public class ProductLine extends ExtEntity {

	private Integer productLineId;
	private String productLineName;
	private Integer advertiserId;
	public static final int STATUS_USE = 0;
	public static final int STATUS_DEL = 1;
	public Integer getProductLineId() {
		return productLineId;
	}
	public void setProductLineId(Integer productLineId) {
		this.productLineId = productLineId;
	}
	public String getProductLineName() {
		return productLineName;
	}
	public void setProductLineName(String productLineName) {
		this.productLineName = productLineName;
	}
	public Integer getAdvertiserId() {
		return advertiserId;
	}
	public void setAdvertiserId(Integer advertiserId) {
		this.advertiserId = advertiserId;
	}
	
	
	
}
	