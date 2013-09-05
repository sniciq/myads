package com.ku6ads.dao.entity.statistics;

import java.util.Date;

import com.ku6ads.dao.entity.ExtEntity;

public class StatisticsAdvpositionDay extends ExtEntity {
	private Integer id;
	private Date statTime;
	private Integer hour;
	private Integer advPositionId;
	private String advPositionName;
	private Integer pv;
	private Integer uv;
	private Integer click;
	private Integer uc;
	
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

	public Integer getAdvPositionId() {
		return this.advPositionId;
	}
	public void setAdvPositionId(Integer advPositionId) {
		this.advPositionId = advPositionId;
	}

	public String getAdvPositionName() {
		return this.advPositionName;
	}
	public void setAdvPositionName(String advPositionName) {
		this.advPositionName = advPositionName;
	}

	public Integer getPv() {
		return this.pv;
	}
	public void setPv(Integer pv) {
		this.pv = pv;
	}

	public Integer getUv() {
		return this.uv;
	}
	public void setUv(Integer uv) {
		this.uv = uv;
	}

	public Integer getClick() {
		return this.click;
	}
	public void setClick(Integer click) {
		this.click = click;
	}

	public Integer getUc() {
		return this.uc;
	}
	public void setUc(Integer uc) {
		this.uc = uc;
	}
	public String getxField() {
		return xField;
	}
	public void setxField(String xField) {
		this.xField = xField;
	}
	public String getName() {
		return this.advPositionName;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
