package com.ku6ads.dao.entity.advflight;

import java.util.Date;

public class AdvbarPreBook {
	private Integer id;
	private Integer advbarId;//广告条ID
	private Integer priority;//优先级
	private Integer saleType;//售卖方式
	private Date bookDate;//预订日期
	private Integer bookedFlight;//预订量
	private Boolean canBook;//是否还可预订
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getAdvbarId() {
		return advbarId;
	}
	public void setAdvbarId(Integer advbarId) {
		this.advbarId = advbarId;
	}
	public Integer getSaleType() {
		return saleType;
	}
	public void setSaleType(Integer saleType) {
		this.saleType = saleType;
	}
	public Date getBookDate() {
		return bookDate;
	}
	public void setBookDate(Date bookDate) {
		this.bookDate = bookDate;
	}
	public Integer getBookedFlight() {
		return bookedFlight;
	}
	public void setBookedFlight(Integer bookedFlight) {
		this.bookedFlight = bookedFlight;
	}
	public Boolean getCanBook() {
		return canBook;
	}
	public void setCanBook(Boolean canBook) {
		this.canBook = canBook;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	
}
