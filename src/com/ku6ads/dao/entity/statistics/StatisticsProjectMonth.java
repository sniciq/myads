package com.ku6ads.dao.entity.statistics;

import java.util.Date;

import com.ku6ads.dao.entity.ExtEntity;

public class StatisticsProjectMonth extends ExtEntity {

	private Integer id;
	private Integer month;
	private Date statTime;
	
	private Integer projectId;
	private String projectName;
	private Long pv;
	private java.lang.Long uv;
	private java.lang.Long click;
	private java.lang.Long uc;
	
	private Integer pcpde;
	private Integer dcode;
	
	private String province;
	private String city;
	private Date stateTimeMonthEnd;
	private String xField;
	private String name; 

	public Integer getId() {
		return this.id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getMonth() {
		return this.month;
	}
	public void setMonth(Integer month) {
		this.month = month;
	}

	public Date getStatTime() {
		return this.statTime;
	}
	public void setStatTime(Date statTime) {
		this.statTime = statTime;
	}

	public Integer getProjectId() {
		return this.projectId;
	}
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return this.projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Long getPv() {
		return this.pv;
	}
	public void setPv(Long pv) {
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
	public Date getStateTimeMonthEnd() {
		return stateTimeMonthEnd;
	}
	public void setStateTimeMonthEnd(Date stateTimeMonthEnd) {
		this.stateTimeMonthEnd = stateTimeMonthEnd;
	}
	public Integer getPcpde() {
		return pcpde;
	}
	public void setPcpde(Integer pcpde) {
		this.pcpde = pcpde;
	}
	public Integer getDcode() {
		return dcode;
	}
	public void setDcode(Integer dcode) {
		this.dcode = dcode;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getxField() {
		return xField;
	}
	public void setxField(String xField) {
		this.xField = xField;
	}
	public String getName() {
		return this.projectName;
	}
	public void setName(String name) {
		this.name = name;
	}
}
