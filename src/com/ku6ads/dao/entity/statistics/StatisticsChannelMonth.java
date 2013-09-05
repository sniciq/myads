package com.ku6ads.dao.entity.statistics;

import java.util.Date;

import com.ku6ads.dao.entity.ExtEntity;

public class StatisticsChannelMonth extends ExtEntity {

	private Integer id;
	private Integer month;
	private Date statTime;
	private Integer channelId;
	private String channelName;
	private Long pv;
	private Long uv;
	private Long click;
	private Long uc;
	private Date createTime;

	private Integer pcpde;
	private Integer dcode;
	
	private String province;
	private String city;

	private String xField;
	private String name; 
	private Date stateTimeMonthEnd;
	
	
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

	public Integer getChannelId() {
		return this.channelId;
	}
	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	public String getChannelName() {
		return this.channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public Date getCreateTime() {
		return this.createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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
	public String getName() {
		return channelName;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getxField() {
		return xField;
	}
	public void setxField(String xField) {
		this.xField = xField;
	}
	
}
