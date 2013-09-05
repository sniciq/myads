package com.ku6ads.struts.advflight;

import com.ku6ads.dao.entity.advflight.Book;

public class BookForm extends Book {
	private String useTypeName;	// 使用方式名称
	private String remark;
	private String frequencyText;	// 
	private Integer relationId;
	private String relationAdsIds;//排期已关联的广告ID
	
	private Boolean canDelete;
	
	public String getUseTypeName() {
		return useTypeName;
	}
	public void setUseTypeName(String useTypeName) {
		this.useTypeName = useTypeName;
	}
	public Integer getRelationId() {
		return relationId;
	}
	public void setRelationId(Integer relationId) {
		this.relationId = relationId;
	}
	public Boolean getCanDelete() {
		return canDelete;
	}
	public void setCanDelete(Boolean canDelete) {
		this.canDelete = canDelete;
	}
	public String getFrequencyText() {
		return frequencyText;
	}
	public void setFrequencyText(String frequencyText) {
		this.frequencyText = frequencyText;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	/**
	 * 排期已关联的广告ID
	 * @return
	 */
	public String getRelationAdsIds() {
		return relationAdsIds;
	}
	
	/**
	 * 排期已关联的广告ID
	 * @param relationAdsIds
	 */
	public void setRelationAdsIds(String relationAdsIds) {
		this.relationAdsIds = relationAdsIds;
	}
}
