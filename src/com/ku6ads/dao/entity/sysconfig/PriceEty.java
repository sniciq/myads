package com.ku6ads.dao.entity.sysconfig;

import com.ku6ads.dao.entity.ExtEntity;

public class PriceEty extends ExtEntity {

	private Integer id;
	private String priceName;	//名称
	private Integer saleTypeValue;	//售卖方式ID
	private String format;	//表现形式
	private String price;	//价格
	private String remark;
	private Double clickRate;//点击
	private String impression;//曝光
	private String materiel;//物料要求

	private String saleTypeName;
	private String formatName;
	
	public Integer getId() {
		return this.id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	* 得到 名称
	* @return 名称 : String
	*/
	public String getPriceName() {
		return this.priceName;
	}
	/**
	 * 设置 名称
	 * @param priceName, 名称 : String
	*/
	public void setPriceName(String priceName) {
		this.priceName = priceName;
	}

	/**
	* 得到 表现形式
	* @return 表现形式 : String
	*/
	public String getFormat() {
		return this.format;
	}
	/**
	 * 设置 表现形式
	 * @param format, 表现形式 : String
	*/
	public void setFormat(String format) {
		this.format = format;
	}

	/**
	* 得到 价格
	* @return 价格 : String
	*/
	public String getPrice() {
		return this.price;
	}
	/**
	 * 设置 价格
	 * @param price, 价格 : String
	*/
	public void setPrice(String price) {
		this.price = price;
	}

	public String getRemark() {
		return this.remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getSaleTypeValue() {
		return saleTypeValue;
	}
	public void setSaleTypeValue(Integer saleTypeValue) {
		this.saleTypeValue = saleTypeValue;
	}
	public String getSaleTypeName() {
		return saleTypeName;
	}
	public void setSaleTypeName(String saleTypeName) {
		this.saleTypeName = saleTypeName;
	}
	public String getFormatName() {
		return formatName;
	}
	public void setFormatName(String formatName) {
		this.formatName = formatName;
	}
	public Double getClickRate() {
		return clickRate;
	}
	public void setClickRate(Double clickRate) {
		this.clickRate = clickRate;
	}
	public String getImpression() {
		return impression;
	}
	public void setImpression(String impression) {
		this.impression = impression;
	}
	public String getMateriel() {
		return materiel;
	}
	public void setMateriel(String materiel) {
		this.materiel = materiel;
	}
	
}