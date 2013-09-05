package com.ku6ads.dao.entity.advert;

import java.util.Date;

import com.ku6ads.dao.entity.ExtEntity;
/**
 * 广告位模板广告条模板关联
 * @author liujunshi
 *
 */
public class Postemplate extends ExtEntity {

	private Integer id;
	private String name ;
	private String nameEn;
	private Integer barNum;
	private Integer type;
	private String ptype;//模板种类
	private String typeString;
	private String code;
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
	public String getNameEn() {
		return nameEn;
	}
	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	}
	public Integer getBarNum() {
		return barNum;
	}
	public void setBarNum(Integer barNum) {
		this.barNum = barNum;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getTypeString() {
		return typeString;
	}
	public void setTypeString(String typeString) {
		this.typeString = typeString;
	}
	public String getPtype() {
		return ptype;
	}
	public void setPtype(String ptype) {
		this.ptype = ptype;
	}

	
	

	

	
}
