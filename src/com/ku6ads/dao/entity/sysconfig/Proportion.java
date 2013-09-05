package com.ku6ads.dao.entity.sysconfig;

import java.util.Date;

import com.ku6ads.dao.entity.ExtEntity;

/**
 * 权重参数entity
 * @author chenshaofeng
 * @createTime 2010-11-27
 * @lastMofifyTime 2010-11-27
 */
public class Proportion extends ExtEntity{

	private Integer id;
	private String name;
	private Double value;
	private String type;
	private Date startTime;
	private Date endTime;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		this.value = value;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
