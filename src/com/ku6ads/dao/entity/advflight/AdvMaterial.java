package com.ku6ads.dao.entity.advflight;

import com.ku6ads.dao.entity.ExtEntity;

public class AdvMaterial  extends ExtEntity{

	private Integer id;
	private Integer advId;
	private Integer materialId;
	private String materialName;
	private Integer sNumber;
	private String path;
	private String type;
	private String note;
	private String textContent;
	private String mexplain;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getAdvId() {
		return advId;
	}
	public void setAdvId(Integer advId) {
		this.advId = advId;
	}
	public Integer getMaterialId() {
		return materialId;
	}
	public void setMaterialId(Integer materialId) {
		this.materialId = materialId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
	public Integer getsNumber() {
		return sNumber;
	}
	public void setsNumber(Integer sNumber) {
		this.sNumber = sNumber;
	}
	public String getTextContent() {
		return textContent;
	}
	public void setTextContent(String textContent) {
		this.textContent = textContent;
	}
	public String getMexplain() {
		return mexplain;
	}
	public void setMexplain(String mexplain) {
		this.mexplain = mexplain;
	}

	
	
	
}
