package com.ku6ads.dao.entity.statistics;

import java.util.Date;


public class StatisticsAAContext {
	
	private Integer id;
	private String channelSourceId;
	private String statisticsClass;
	private Double brandImpression;
	private Double brandClick;
	private Double aaDisplayCount;
	private Double aaClick;
	private Double aaTest;
	private Double vv;
	private Double cv;
	private Double normalOverCount;
	private Double suspendCount;
	private Date statisticsTime;
	
	private String channelName;
	
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getChannelSourceId() {
		return channelSourceId;
	}
	public void setChannelSourceId(String channelSourceId) {
		this.channelSourceId = channelSourceId;
	}
	public String getStatisticsClass() {
		return statisticsClass;
	}
	public void setStatisticsClass(String statisticsClass) {
		this.statisticsClass = statisticsClass;
	}
	public Double getBrandImpression() {
		return brandImpression;
	}
	public void setBrandImpression(Double brandImpression) {
		this.brandImpression = brandImpression;
	}
	public Double getBrandClick() {
		return brandClick;
	}
	public void setBrandClick(Double brandClick) {
		this.brandClick = brandClick;
	}
	public Double getAaDisplayCount() {
		return aaDisplayCount;
	}
	public void setAaDisplayCount(Double aaDisplayCount) {
		this.aaDisplayCount = aaDisplayCount;
	}
	public Double getAaClick() {
		return aaClick;
	}
	public void setAaClick(Double aaClick) {
		this.aaClick = aaClick;
	}
	public Double getAaTest() {
		return aaTest;
	}
	public void setAaTest(Double aaTest) {
		this.aaTest = aaTest;
	}
	public Double getVv() {
		return vv;
	}
	public void setVv(Double vv) {
		this.vv = vv;
	}
	public Double getCv() {
		return cv;
	}
	public void setCv(Double cv) {
		this.cv = cv;
	}
	public Double getNormalOverCount() {
		return normalOverCount;
	}
	public void setNormalOverCount(Double normalOverCount) {
		this.normalOverCount = normalOverCount;
	}
	public Double getSuspendCount() {
		return suspendCount;
	}
	public void setSuspendCount(Double suspendCount) {
		this.suspendCount = suspendCount;
	}
	public Date getStatisticsTime() {
		return statisticsTime;
	}
	public void setStatisticsTime(Date statisticsTime) {
		this.statisticsTime = statisticsTime;
	}

}
