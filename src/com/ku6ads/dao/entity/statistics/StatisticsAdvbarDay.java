package com.ku6ads.dao.entity.statistics;

import java.util.Date;

import com.ku6ads.dao.entity.ExtEntity;

public class StatisticsAdvbarDay extends ExtEntity {

	private Integer id;
	private Date statTime;
	private Integer hour;
	private Integer advbarId;
	private String advbarName;
	private Long pv;
	private Long uv;
	private Long uc;
	private Long click;
	
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

	public Integer getAdvbarId() {
		return advbarId;
	}
	public void setAdvbarId(Integer advbarId) {
		this.advbarId = advbarId;
	}
	public String getAdvbarName() {
		return advbarName;
	}
	public void setAdvbarName(String advbarName) {
		this.advbarName = advbarName;
	}
	
	public Long getPv() {
		return pv;
	}
	public void setPv(Long pv) {
		this.pv = pv;
	}
	public Long getUv() {
		return uv;
	}
	public void setUv(Long uv) {
		this.uv = uv;
	}
	public Long getUc() {
		return uc;
	}
	public void setUc(Long uc) {
		this.uc = uc;
	}
	public Long getClick() {
		return click;
	}
	public void setClick(Long click) {
		this.click = click;
	}
	public String getxField() {
		return xField;
	}
	public void setxField(String xField) {
		this.xField = xField;
	}
	public String getName() {
		return this.advbarName;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
