package com.ku6ads.dao.entity.statistics;

import java.util.Date;

import com.ku6ads.dao.entity.ExtEntity;

public class StatisticsAdvpositionMonth extends ExtEntity {

	private Integer id;
	private Integer month;
	private Date statTime;
	private Integer advPositionId;
	private String advPositionName;
	private Integer pv;
	private Integer uv;
	private Integer click;
	private Integer uc;
	private Date createTime;
	
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

	public Date getCreateTime() {
		return this.createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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
		return this.advPositionName;
	}
	public void setName(String name) {
		this.name = name;
	}

}
