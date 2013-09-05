package com.ku6ads.dao.entity.webdata;

import com.ku6ads.util.ExtLimit;

public class Hotfilm {

	private Integer id;
	private String film_name;
	private java.util.Date time_start;
	private java.util.Date time_end;
	private java.lang.Boolean isstate;
	private String remark;
	
	private ExtLimit extLimit;
	private String stateText;

	public Integer getId() {
		return this.id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public String getFilm_name() {
		return this.film_name;
	}
	public void setFilm_name(String film_name) {
		this.film_name = film_name;
	}

	public java.lang.Boolean getIsstate() {
		return isstate;
	}
	public void setIsstate(java.lang.Boolean isstate) {
		this.isstate = isstate;
	}
	public String getRemark() {
		return this.remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public ExtLimit getExtLimit() {
		return extLimit;
	}
	public void setExtLimit(ExtLimit extLimit) {
		this.extLimit = extLimit;
	}
	public java.util.Date getTime_start() {
		return time_start;
	}
	public void setTime_start(java.util.Date timeStart) {
		time_start = timeStart;
	}
	public java.util.Date getTime_end() {
		return time_end;
	}
	public void setTime_end(java.util.Date timeEnd) {
		time_end = timeEnd;
	}
	public String getStateText() {
		return stateText;
	}
	public void setStateText(String stateText) {
		this.stateText = stateText;
	}
}