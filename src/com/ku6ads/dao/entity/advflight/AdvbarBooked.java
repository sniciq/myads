package com.ku6ads.dao.entity.advflight;

import java.util.Date;

/**
 * 已订点位信息
 * @author YangHanguang
 *
 */
public class AdvbarBooked {
	private Integer id;
	private Integer advbarId;//广告条ID
	private Integer saleType;//售卖方式
	private Date bookDate;//预订日期
	private Integer bookedFlight;//预订量
	
	private Integer priority;//优先级
	
	private Integer strategyCode;//预订策略编码
	
	private String hour;//预订时段
	private String city;//预订城市
	
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
	public String getHour() {
		return hour;
	}
	public void setHour(String hour) {
		this.hour = hour;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public Integer getStrategyCode() {
		return strategyCode;
	}
	public void setStrategyCode(Integer strategyCode) {
		this.strategyCode = strategyCode;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
}
