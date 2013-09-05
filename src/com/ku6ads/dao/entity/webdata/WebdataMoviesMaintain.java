package com.ku6ads.dao.entity.webdata;

import java.util.Date;

import com.ku6ads.dao.entity.ExtEntity;

/**
 * 片单维护信息
 * @author yanghanguang
 *
 */
public class WebdataMoviesMaintain extends ExtEntity {
	private Integer id;
	private Integer movieId;
	private Date startTime;
	private Date endTime;
	private String buyType;
	private String customerName;

	public Integer getId() {
		return this.id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getMovieId() {
		return this.movieId;
	}
	public void setMovieId(Integer movieId) {
		this.movieId = movieId;
	}

	public Date getStartTime() {
		return this.startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return this.endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getBuyType() {
		return this.buyType;
	}
	public void setBuyType(String buyType) {
		this.buyType = buyType;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
}
