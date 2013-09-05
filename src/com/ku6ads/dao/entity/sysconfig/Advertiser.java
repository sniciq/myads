package com.ku6ads.dao.entity.sysconfig;

import com.ku6ads.dao.entity.ExtEntity;

/**
 * 广告主
 * @author liujunshi
 *
 */
public class Advertiser extends ExtEntity {

	private Integer id;
	private String name;
	private String company;	
	private Integer industry;
	private String industryName;
	private Integer area;
	private String areaName;
	
	public static final int STATUS_USE = 0;
	public static final int STATUS_DEL = 1;
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
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public Integer getIndustry() {
		return industry;
	}
	public void setIndustry(Integer industry) {
		this.industry = industry;
	}
	public String getIndustryName() {
		return industryName;
	}
	public void setIndustryName(String industryName) {
		this.industryName = industryName;
	}
	public Integer getArea() {
		return area;
	}
	public void setArea(Integer area) {
		this.area = area;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}


		
}
	