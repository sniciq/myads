package com.ku6ads.dao.entity.statistics;

import java.util.Date;

import com.ku6ads.dao.entity.ExtEntity;

public class CpdhisAccount extends ExtEntity{
	
	private Integer advbarId;	//	广告条ID
	private String advbarName;	//	广告条名称
	private String channelName;	//	频道
	private String advpositionName;	//	广告位
	private Double price;	//	广告条价格
	private Integer barContent;	//	广告条容量
	private Integer advexecute;	//	广告执行量
	private String advexecuteRate;//	广告执行率
	private Integer advUnexecute;//	未执行预订量
	private String advUnexecuteRate;//	未执行预订率 
	private Integer execute;//	执行预订量
	private String executeRate;//	执行预订率
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
	public String getAdvpositionName() {
		return advpositionName;
	}
	public void setAdvpositionName(String advpositionName) {
		this.advpositionName = advpositionName;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Integer getBarContent() {
		return barContent;
	}
	public void setBarContent(Integer barContent) {
		this.barContent = barContent;
	}
	public Integer getAdvexecute() {
		return advexecute;
	}
	public void setAdvexecute(Integer advexecute) {
		this.advexecute = advexecute;
	}
	public String getAdvexecuteRate() {
		return advexecuteRate;
	}
	public void setAdvexecuteRate(String advexecuteRate) {
		this.advexecuteRate = advexecuteRate;
	}
	public Integer getAdvUnexecute() {
		return advUnexecute;
	}
	public void setAdvUnexecute(Integer advUnexecute) {
		this.advUnexecute = advUnexecute;
	}
	public String getAdvUnexecuteRate() {
		return advUnexecuteRate;
	}
	public void setAdvUnexecuteRate(String advUnexecuteRate) {
		this.advUnexecuteRate = advUnexecuteRate;
	}
	public Integer getExecute() {
		return execute;
	}
	public void setExecute(Integer execute) {
		this.execute = execute;
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
	public Integer getAdvertiseId() {
		return advertiseId;
	}
	public void setAdvertiseId(Integer advertiseId) {
		this.advertiseId = advertiseId;
	}
	
}
