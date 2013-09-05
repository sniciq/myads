package com.ku6ads.dao.entity.advflight;



import java.io.File;

import com.ku6ads.dao.entity.ExtEntity;
/**
 * 物料
 * @author liujunshi
 *
 */
public class Material extends ExtEntity  {

	private Integer materialId;//编号
	private String materialName;//名字
	private String path;//路径
	private String meterialType;
	private String type2;
	private String note;//备注
	private Integer  advId;//广告ID
	private String physicalName;//服务器上物理名称
	private Integer isSuccess;//是否转码成功
	private String name;
	private String advertiserName;
	private Integer advertiserId;
	private String textContent;
	private String pureCode;
	private Integer playTime;//转码后时间长度
	
	public static final int STATUS_USE = 0;
	public static final int STATUS_DEL = 1;
	
	public static final int TRANSFAIL= 0;
	public static final int TRANSSUCCESS = 1;
	public static final int TRANSING = 4;
	//物料四种类型 TEXT IMAGE FLASH VIDEO
	public static final String TYPE_TEXT = "TEXT";
	public static final String TYPE_IMAGE = "IMAGE";
	public static final String TYPE_FLASH = "FLASH";
	public static final String TYPE_VIDEO = "VIDEO";
	
	public Integer getMaterialId() {
		return materialId;
	}
	public void setMaterialId(Integer materialId) {
		this.materialId = materialId;
	}
	public String getMaterialName() {
		return materialName;
	}
	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}

	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public Integer getAdvId() {
		return advId;
	}
	public void setAdvId(Integer advId) {
		this.advId = advId;
	}
	public String getMeterialType() {
		return meterialType;
	}
	public void setMeterialType(String meterialType) {
		this.meterialType = meterialType;
	}
	public String getPhysicalName() {
		return physicalName;
	}
	public void setPhysicalName(String physicalName) {
		this.physicalName = physicalName;
	}
	public Integer getIsSuccess() {
		return isSuccess;
	}
	public void setIsSuccess(Integer isSuccess) {
		this.isSuccess = isSuccess;
	}
	public String getType2() {
		return type2;
	}
	public void setType2(String type2) {
		this.type2 = type2;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAdvertiserName() {
		return advertiserName;
	}
	public void setAdvertiserName(String advertiserName) {
		this.advertiserName = advertiserName;
	}
	public Integer getAdvertiserId() {
		return advertiserId;
	}
	public void setAdvertiserId(Integer advertiserId) {
		this.advertiserId = advertiserId;
	}
	public String getTextContent() {
		return textContent;
	}
	public void setTextContent(String textContent) {
		this.textContent = textContent;
	}
	public String getPureCode() {
		return pureCode;
	}
	public void setPureCode(String pureCode) {
		this.pureCode = pureCode;
	}
	public Integer getPlayTime() {
		return playTime;
	}
	public void setPlayTime(Integer playTime) {
		this.playTime = playTime;
	}



}
