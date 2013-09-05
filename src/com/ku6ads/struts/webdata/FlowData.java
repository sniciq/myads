package com.ku6ads.struts.webdata;

import java.util.Date;

import com.ku6ads.util.ExtLimit;

public class FlowData {
	private String area_name;
	private String province;
	private String city;
	private Integer area_id;
	private String prod_name;
	private Integer prod_id;
	private String startTime;
	private String endTime;
	private Integer pv;
	private Integer uv;
	private Integer vv;
	private ExtLimit extLimit;
	private Integer id;
	private Date day_id;
	private Integer weekDay;//星期几
	private String weekDayStr;//星期几
	
	private Integer hour_id;
	
	private String prodNameList;
	private String areaNameList;
	private String prodIdList;
	private String areaIdList;
	
	private String dataType;
	
	public String getArea_name() {
		return area_name;
	}
	public void setArea_name(String areaName) {
		area_name = areaName;
	}
	public Integer getArea_id() {
		return area_id;
	}
	public void setArea_id(Integer areaId) {
		area_id = areaId;
	}
	public String getProd_name() {
		return prod_name;
	}
	public void setProd_name(String prodName) {
		prod_name = prodName;
	}
	public Integer getProd_id() {
		return prod_id;
	}
	public void setProd_id(Integer prodId) {
		prod_id = prodId;
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
	
	public ExtLimit getExtLimit() {
		return extLimit;
	}
	public void setExtLimit(ExtLimit extLimit) {
		this.extLimit = extLimit;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getDay_id() {
		return day_id;
	}
	public void setDay_id(Date dayId) {
		day_id = dayId;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getProdNameList() {
		return prodNameList;
	}
	public void setProdNameList(String prodNameList) {
		this.prodNameList = prodNameList;
	}
	public String getAreaNameList() {
		return areaNameList;
	}
	public void setAreaNameList(String areaNameList) {
		this.areaNameList = areaNameList;
	}
	public String getProdIdList() {
		return prodIdList;
	}
	public void setProdIdList(String prodIdList) {
		this.prodIdList = prodIdList;
	}
	public String getAreaIdList() {
		return areaIdList;
	}
	public void setAreaIdList(String areaIdList) {
		this.areaIdList = areaIdList;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public Integer getHour_id() {
		return hour_id;
	}
	public void setHour_id(Integer hourId) {
		hour_id = hourId;
	}
	public Integer getWeekDay() {
		return weekDay;
	}
	public void setWeekDay(Integer weekDay) {
		this.weekDay = weekDay;
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
	public Integer getVv() {
		return vv;
	}
	public void setVv(Integer vv) {
		this.vv = vv;
	}
	public String getWeekDayStr() {
		switch (weekDay) {
		case 0:
			return "星期日";
		case 1:
			return "星期一";
		case 2:
			return "星期二";
		case 3:
			return "星期三";
		case 4:
			return "星期四";
		case 5:
			return "星期五";
		case 6:
			return "星期六";
		default:
			break;
		}
		
		return "";
	}
	public void setWeekDayStr(String weekDayStr) {
		this.weekDayStr = weekDayStr;
	}
	
}
