package com.ku6ads.dao.entity.statistics;

import java.util.Date;

import com.ku6ads.dao.entity.ExtEntity;

public class CpdFuture extends ExtEntity{
	
	private Integer bookId;
	private Integer advbarId;
	private String advbarName;
	private String channelName;
	private String format;
	private Double price;
	private Integer barContent;
	private Integer book;
	private String bookRate;
	private Integer confirm;
	private String confirmRate;
	private Date startTime;
	private Date endTime;
	
	private Integer bussinessStatus;
	
	
	public Integer getBookId() {
		return bookId;
	}
	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}
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
	public Integer getBarContent() {
		return barContent;
	}
	public void setBarContent(Integer barContent) {
		this.barContent = barContent;
	}
	public Integer getBook() {
		return book;
	}
	public void setBook(Integer book) {
		this.book = book;
	}
	public String getBookRate() {
		return bookRate;
	}
	public void setBookRate(String bookRate) {
		this.bookRate = bookRate;
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
	public Integer getBussinessStatus() {
		return bussinessStatus;
	}
	public void setBussinessStatus(Integer bussinessStatus) {
		this.bussinessStatus = bussinessStatus;
	}
	
}
