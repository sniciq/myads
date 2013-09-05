package com.ku6ads.dao.entity.webdata;

import java.util.Date;

import com.ku6ads.dao.entity.ExtEntity;

/**
 * 片单销售工具
 * @author yanghanguang
 *
 */
public class WebdataMoviesSellstate extends ExtEntity  {
	private Integer id;
	private Integer movieId;
	private Date startTime;
	private Date endTime;
	private String sellType;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getMovieId() {
		return movieId;
	}
	public void setMovieId(Integer movieId) {
		this.movieId = movieId;
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
	public String getSellType() {
		return sellType;
	}
	public void setSellType(String sellType) {
		this.sellType = sellType;
	}
}
