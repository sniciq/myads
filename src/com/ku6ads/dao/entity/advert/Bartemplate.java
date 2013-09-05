package com.ku6ads.dao.entity.advert;

import java.util.Date;
import com.ku6ads.dao.entity.ExtEntity;
/**
 * 
 * @author liujunshi
 *
 */
public class Bartemplate extends ExtEntity {

	private Integer id;
	private String name;
	private String nameEn;
	private String code;
	private Integer textNum;
	private Integer imgNum;
	private Integer videoNum;
	private Integer materialSum;
	private String demoUrl;
	private Integer type;
	
	
	public static final int STATUS_USE= 1;
	public static final int STATUS_DEL= 0;
	public static final int STATUS_STOP= 2;
	
	public static final int TYPE_FLASH_IMAGE = 1;
	public static final int TYPE_QIANTIEPIAN = 2;
	public static final int TYPE_HOUTIEPIAN = 3;
	public static final int TYPE_ZANTING = 4;
	public static final int TYPE_JIAOBIAO = 5;
	
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
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
	public Integer getTextNum() {
		return textNum;
	}
	public void setTextNum(Integer textNum) {
		this.textNum = textNum;
	}
	public Integer getImgNum() {
		return imgNum;
	}
	public void setImgNum(Integer imgNum) {
		this.imgNum = imgNum;
	}
	public Integer getVideoNum() {
		return videoNum;
	}
	public void setVideoNum(Integer videoNum) {
		this.videoNum = videoNum;
	}
	public Integer getMaterialSum() {
		return materialSum;
	}
	public void setMaterialSum(Integer materialSum) {
		this.materialSum = materialSum;
	}
	public String getDemoUrl() {
		return demoUrl;
	}
	public void setDemoUrl(String demoUrl) {
		this.demoUrl = demoUrl;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}


	
}
