package com.ku6ads.dao.entity.sysconfig;

import com.ku6ads.dao.entity.ExtEntity;

/**
 * 网站
 * 
 * @author liyonghui
 * 
 */
public class Site extends ExtEntity {
	private Integer siteId;
	private String siteName;
	private String nameEn;
	private String note;
	private Double modulus;//网站系数

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getNameEn() {
		return nameEn;
	}

	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	/**
	 * 网站系数
	 * @return
	 */
	public Double getModulus() {
		return modulus;
	}

	/**
	 * 网站系数
	 * @param modulus
	 */
	public void setModulus(Double modulus) {
		this.modulus = modulus;
	}

}
