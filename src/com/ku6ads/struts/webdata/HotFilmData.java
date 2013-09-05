package com.ku6ads.struts.webdata;

import java.util.Date;

import com.ku6ads.util.ExtLimit;

public class HotFilmData {
	
	private String filmName;
	private String videoName;
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
	private Date day_time;
	private Integer weekDay;//星期几
	
	private Integer hour_id;
	
	private String areaNameList;
	private String areaPcodeList;
	private String cityDcodeList;
	private String prodIdList;
	
	private String dataType;

	public String getArea_name() {
		return area_name;
	}

	public void setArea_name(String areaName) {
		area_name = areaName;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
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

	public Date getDay_time() {
		return day_time;
	}

	public void setDay_time(Date dayTime) {
		day_time = dayTime;
	}

	public Integer getWeekDay() {
		return weekDay;
	}

	public void setWeekDay(Integer weekDay) {
		this.weekDay = weekDay;
	}

	public Integer getHour_id() {
		return hour_id;
	}

	public void setHour_id(Integer hourId) {
		hour_id = hourId;
	}


	public String getAreaNameList() {
		return areaNameList;
	}

	public void setAreaNameList(String areaNameList) {
		this.areaNameList = areaNameList;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getAreaPcodeList() {
		return areaPcodeList;
	}

	public void setAreaPcodeList(String areaPcodeList) {
		this.areaPcodeList = areaPcodeList;
	}

	public String getCityDcodeList() {
		return cityDcodeList;
	}

	public void setCityDcodeList(String cityDcodeList) {
		this.cityDcodeList = cityDcodeList;
	}

	public String getFilmName() {
		return filmName;
	}

	public void setFilmName(String filmName) {
		this.filmName = filmName;
	}

	public String getVideoName() {
		return videoName;
	}

	public void setVideoName(String videoName) {
		this.videoName = videoName;
	}

	public String getProdIdList() {
		return prodIdList;
	}

	public void setProdIdList(String prodIdList) {
		this.prodIdList = prodIdList;
	}
}
