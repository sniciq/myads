package com.ku6ads.dao.entity.webdata;

public class DimArea {
	private Integer area_id;
	private String city;
	private String province;
	private String country;
	
	public Integer getArea_id() {
		return area_id;
	}
	public void setArea_id(Integer areaId) {
		area_id = areaId;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
}
