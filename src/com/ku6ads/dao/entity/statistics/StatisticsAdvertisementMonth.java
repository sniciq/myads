package com.ku6ads.dao.entity.statistics;

import java.util.Date;

import com.ku6ads.dao.entity.ExtEntity;

public class StatisticsAdvertisementMonth extends ExtEntity {

	private Integer id;
	private Date statTime;
	private Date stateTimeMonthEnd;
	private Integer month;
	private Integer advertiseId;
	private String advertiseName;
	private Integer pv;
	private Integer uv;
	private Integer uc;
	private Integer click;
	
	private Integer pcpde;
	private Integer dcode;
	
	private String province;
	private String city;
	private String xField;
	private String name; 
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Date getStatTime() {
		return statTime;
	}
	public void setStatTime(Date statTime) {
		this.statTime = statTime;
	}
	public Integer getMonth() {
		return month;
	}
	public void setMonth(Integer month) {
		this.month = month;
	}
	public Integer getAdvertiseId() {
		return advertiseId;
	}
	public void setAdvertiseId(Integer advertiseId) {
		this.advertiseId = advertiseId;
	}
	public String getAdvertiseName() {
		return advertiseName;
	}
	public void setAdvertiseName(String advertiseName) {
		this.advertiseName = advertiseName;
	}
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
	public Integer getUc() {
		return uc;
	}
	public void setUc(Integer uc) {
		this.uc = uc;
	}
	public Integer getClick() {
		return click;
	}
	public void setClick(Integer click) {
		this.click = click;
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
		return this.advertiseName;
	}
	public void setName(String name) {
		this.name = name;
	}
}
