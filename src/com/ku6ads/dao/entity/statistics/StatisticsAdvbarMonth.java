package com.ku6ads.dao.entity.statistics;

import java.util.Date;

import com.ku6ads.dao.entity.ExtEntity;

public class StatisticsAdvbarMonth extends ExtEntity {

	private Integer id;
	private Integer month;
	private Date statTime;
	private Integer advbarId;
	private String advbarName;
	private Long pv;
	private Long uv;
	private Long click;
	private Long uc;
	
	private Integer pcpde;
	private Integer dcode;
	
	private String province;
	private String city;

	private Date stateTimeMonthEnd;
	private String xField;
	private String name; 
	
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

	public Integer getAdvbarId() {
		return this.advbarId;
	}
	public void setAdvbarId(Integer advbarId) {
		this.advbarId = advbarId;
	}

	public String getAdvbarName() {
		return this.advbarName;
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
	
	public Date getStateTimeMonthEnd() {
		return stateTimeMonthEnd;
	}
	public void setStateTimeMonthEnd(Date stateTimeMonthEnd) {
		this.stateTimeMonthEnd = stateTimeMonthEnd;
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
