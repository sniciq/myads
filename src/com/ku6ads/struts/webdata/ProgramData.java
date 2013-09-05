package com.ku6ads.struts.webdata;

import java.util.Date;

import com.ku6ads.util.ExtLimit;

public class ProgramData {
	private Integer id;
	
	private String startTime;
	private String endTime;
	private String custom_code;
	private Date day_time;
	private Integer pv;
	private Integer uv;
	private Integer hour_id;
	
	private String programName;
	private String programId;
	
	private ExtLimit extLimit;
	
	
	public ExtLimit getExtLimit() {
		return extLimit;
	}
	public void setExtLimit(ExtLimit extLimit) {
		this.extLimit = extLimit;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getProgramName() {
		return programName;
	}
	public void setProgramName(String programName) {
		this.programName = programName;
	}
	public String getProgramId() {
		return programId;
	}
	public void setProgramId(String programId) {
		this.programId = programId;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCustom_code() {
		return custom_code;
	}
	public void setCustom_code(String customCode) {
		custom_code = customCode;
	}
	public Date getDay_time() {
		return day_time;
	}
	public void setDay_time(Date dayTime) {
		day_time = dayTime;
	}
	public Integer getPv() {
		return pv;
	}
	public void setPv(Integer pv) {
		this.pv = pv;
	}
	public Integer getUv() {
		return uv;
	}
	public void setUv(Integer uv) {
		this.uv = uv;
	}
	public Integer getHour_id() {
		return hour_id;
	}
	public void setHour_id(Integer hourId) {
		hour_id = hourId;
	}
}
