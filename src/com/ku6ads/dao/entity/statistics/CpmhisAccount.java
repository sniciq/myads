package com.ku6ads.dao.entity.statistics;

import java.util.Date;

import com.ku6ads.dao.entity.ExtEntity;

public class CpmhisAccount extends ExtEntity{

	private Integer advbarId;	//广告条ID
	private String advbarName;	//广告条名称
	private String channelName;	//频道
	private String format;	//广告表现形式
	private Double price;	//价格
	private Long barImpression;		//广告条曝光量
	private Long advImpression;		//广告曝光量
	private String impressionRate;	//广告曝光率
	private Integer unexecute;		//未执行预订量
	private String unexecuteRate;	//未执行预订率
	private Integer execute;	//执行预订量
	private String executeRate;		//执行预订率
	private Date startTime;
	private Date endTime;
	private Integer advertiseId;
	
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
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Long getBarImpression() {
		return barImpression;
	}
	public void setBarImpression(Long barImpression) {
		this.barImpression = barImpression;
	}
	public Long getAdvImpression() {
		return advImpression;
	}
	public void setAdvImpression(Long advImpression) {
		this.advImpression = advImpression;
	}
	public String getImpressionRate() {
		return impressionRate;
	}
	public void setImpressionRate(String impressionRate) {
		this.impressionRate = impressionRate;
	}
	
	public Integer getUnexecute() {
		return unexecute;
	}
	public void setUnexecute(Integer unexecute) {
		this.unexecute = unexecute;
	}
	public void setExecute(Integer execute) {
		this.execute = execute;
	}
	public String getUnexecuteRate() {
		return unexecuteRate;
	}
	public void setUnexecuteRate(String unexecuteRate) {
		this.unexecuteRate = unexecuteRate;
	}
	public String getExecuteRate() {
		return executeRate;
	}
	public void setExecuteRate(String executeRate) {
		this.executeRate = executeRate;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Integer getExecute() {
		return execute;
	}
	public Integer getAdvertiseId() {
		return advertiseId;
	}
	public void setAdvertiseId(Integer advertiseId) {
		this.advertiseId = advertiseId;
	}
	
}
