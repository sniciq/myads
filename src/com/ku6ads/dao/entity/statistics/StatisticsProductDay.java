package com.ku6ads.dao.entity.statistics;

import java.util.Date;

import com.ku6ads.dao.entity.ExtEntity;

public class StatisticsProductDay extends ExtEntity {
	private Integer id;
	private Date statTime;
	private Date statDayTimeEnd;
	private Integer hour;
	private Integer productId;
	private java.lang.Long pv;
	private java.lang.Long uv;
	private java.lang.Long click;
	private java.lang.Long uc;
	
	private String productName;
	private String xField;
	private String name; 
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getStatDayTimeEnd() {
		return statDayTimeEnd;
	}
	public void setStatDayTimeEnd(Date statDayTimeEnd) {
		this.statDayTimeEnd = statDayTimeEnd;
	}
	public Integer getHour() {
		return hour;
	}
	public void setHour(Integer hour) {
		this.hour = hour;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public java.lang.Long getPv() {
		return pv;
	}
	public void setPv(java.lang.Long pv) {
		this.pv = pv;
	}
	public java.lang.Long getUv() {
		return uv;
	}
	public void setUv(java.lang.Long uv) {
		this.uv = uv;
	}
	public java.lang.Long getClick() {
		return click;
	}
	public void setClick(java.lang.Long click) {
		this.click = click;
	}
	public java.lang.Long getUc() {
		return uc;
	}
	public void setUc(java.lang.Long uc) {
		this.uc = uc;
	}
	public Date getStatTime() {
		return statTime;
	}
	public void setStatTime(Date statTime) {
		this.statTime = statTime;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getxField() {
		return xField;
	}
	public void setxField(String xField) {
		this.xField = xField;
	}
	public String getName() {
		return this.productName;
	}
	public void setName(String name) {
		this.name = name;
	}
}
