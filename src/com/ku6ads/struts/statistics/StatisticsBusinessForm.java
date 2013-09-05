package com.ku6ads.struts.statistics;

import java.util.Date;

public class StatisticsBusinessForm {
	
	private Integer id;
	private String xField;
	private Date statTime; 
	private String name;
	private Long pv;
	private Long uv;
	private Long click;
	private Long uc;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getxField() {
		return xField;
	}
	public void setxField(String xField) {
		this.xField = xField;
	}
	public Date getStatTime() {
		return statTime;
	}
	public void setStatTime(Date statTime) {
		this.statTime = statTime;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public Long getClick() {
		return click;
	}
	public void setClick(Long click) {
		this.click = click;
	}
	public Long getUc() {
		return uc;
	}
	public void setUc(Long uc) {
		this.uc = uc;
	}
	
}
