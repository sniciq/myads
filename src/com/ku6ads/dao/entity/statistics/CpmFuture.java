package com.ku6ads.dao.entity.statistics;

import java.util.Date;

import com.ku6ads.dao.entity.ExtEntity;

public class CpmFuture extends ExtEntity{

	private Integer advbarId;	//广告条ID
	private String advbarName;	//	广告条名称
	private String channelName;	//	频道
	private String format;	//广告表现形式
	private Double price;	//	价格
	private Integer futrueImpression; //	广告条预估库存
	private Integer book;	//	预定量
	private String bookRate;  //	预定率
	private Integer confirm;	//	确定量
	private String confirmRate;	//	确定率
	private Date startTime;
	private Date endTime;
	
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
	public Integer getFutrueImpression() {
		return futrueImpression;
	}
	public void setFutrueImpression(Integer futrueImpression) {
		this.futrueImpression = futrueImpression;
	}
	public String getBookRate() {
		return bookRate;
	}
	public void setBookRate(String bookRate) {
		this.bookRate = bookRate;
	}
	public Integer getBook() {
		return book;
	}
	public void setBook(Integer book) {
		this.book = book;
	}
	public Integer getConfirm() {
		return confirm;
	}
	public void setConfirm(Integer confirm) {
		this.confirm = confirm;
	}
	public String getConfirmRate() {
		return confirmRate;
	}
	public void setConfirmRate(String confirmRate) {
		this.confirmRate = confirmRate;
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
	
	
}
