package com.ku6ads.dao.entity.advert;

import java.util.Date;

import com.ku6ads.dao.entity.ExtEntity;

/**
 * 广告条系数
 * @author xuxianan
 *
 */
public class BarProportion extends ExtEntity {

	private Integer id; // id
	private Date startTime; // 开始时间
	private Date endTime; // 结束时间
	private Double proportion; // 系数
	private Integer barId; // 广告条id

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Double getProportion() {
		return proportion;
	}

	public void setProportion(Double proportion) {
		this.proportion = proportion;
	}

	public Integer getBarId() {
		return barId;
	}

	public void setBarId(Integer barId) {
		this.barId = barId;
	}

}
