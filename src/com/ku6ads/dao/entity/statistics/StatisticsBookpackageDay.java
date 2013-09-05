package com.ku6ads.dao.entity.statistics;

import java.util.Date;

import com.ku6ads.dao.entity.ExtEntity;

public class StatisticsBookpackageDay extends ExtEntity {

	private Integer id;
	private Date statTime;
	private Date statDayTimeEnd;
	private Integer hour;
	private Integer bookpackageId;
	private java.lang.Long pv;
	private java.lang.Long uv;
	private java.lang.Long click;
	private java.lang.Long uc;
	
	private String xField;
	private String name; 

	public Integer getId() {
		return this.id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public Date getStatTime() {
		return this.statTime;
	}
	public void setStatTime(Date statTime) {
		this.statTime = statTime;
	}

	public Integer getHour() {
		return this.hour;
	}
	public void setHour(Integer hour) {
		this.hour = hour;
	}

	public Integer getBookpackageId() {
		return this.bookpackageId;
	}
	public void setBookpackageId(Integer bookpackageId) {
		this.bookpackageId = bookpackageId;
	}

	public java.lang.Long getPv() {
		return this.pv;
	}
	public void setPv(java.lang.Long pv) {
		this.pv = pv;
	}

	public java.lang.Long getUv() {
		return this.uv;
	}
	public void setUv(java.lang.Long uv) {
		this.uv = uv;
	}

	public java.lang.Long getClick() {
		return this.click;
	}
	public void setClick(java.lang.Long click) {
		this.click = click;
	}

	public java.lang.Long getUc() {
		return this.uc;
	}
	public void setUc(java.lang.Long uc) {
		this.uc = uc;
	}

	public Date getStatDayTimeEnd() {
		return statDayTimeEnd;
	}
	public void setStatDayTimeEnd(Date statDayTimeEnd) {
		this.statDayTimeEnd = statDayTimeEnd;
	}
	public String getxField() {
		return xField;
	}
	public void setxField(String xField) {
		this.xField = xField;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
