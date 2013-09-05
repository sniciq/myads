package com.ku6ads.dao.entity.sysconfig;

import com.ku6ads.dao.entity.ExtEntity;

/**
 * 产品
 * @author liujunshi
 *
 */
public class Product extends ExtEntity{

	
	private Integer ProductId;
	private String productName;
	private Integer productLineId;
	
	public static final int STATUS_USE = 0;
	public static final int STATUS_DEL = 1;
	public Integer getProductId() {
		return ProductId;
	}
	public void setProductId(Integer productId) {
		ProductId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Integer getProductLineId() {
		return productLineId;
	}
	public void setProductLineId(Integer productLineId) {
		this.productLineId = productLineId;
	}	
}
