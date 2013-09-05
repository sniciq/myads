package com.ku6ads.struts.statistics;

import java.util.Date;

public class StatisticsMediaForm {
	private Integer id;
	private String xField;
	private Date statTime;
	private String name; 
	private Integer pv;
	private Integer uv;
	private Integer click;
	private Integer uc;
	
	public Integer getPv() {
		return pv;
	}
	public void setPv(Integer pv) {
		this.pv = pv;
	}
	public Integer getUv() {
		return uv;
	}
	public void setUv(Integer uv) {
		this.uv = uv;
	}
	public Integer getClick() {
		return click;
	}
	public void setClick(Integer click) {
		this.click = click;
	}
	public Integer getUc() {
		return uc;
	}
	public void setUc(Integer uc) {
		this.uc = uc;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getStatTime() {
		return statTime;
	}
	public void setStatTime(Date statTime) {
		this.statTime = statTime;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getXField() {
		return xField;
	}
	public void setXField(String xField) {
		this.xField = xField;
	}
	
	
}
