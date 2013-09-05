package com.ku6ads.dao.entity.advflight;

import com.ku6ads.dao.entity.ExtEntity;

/**
 * 广告
 * 
 * @author liyonghui
 */
public class Advertisement extends ExtEntity {

	private Integer id;// 编号
	private String name;// 名称
	private Integer bartemplateId;// 广告条模板
	private String bartemplateName;// 广告条模板名称
	private String advparUrl;// 广告参数文件地址 AdvparUrl
	private String redirect;// 跳转地址 Redirect
	private String monition;// 第三方监测 Monitior
//	private Integer priority; // 优先级 Priority
//	private Integer weight;// 权重 Weight
	private Integer advActiveId;//广告活动id
	private Integer editable = 0;//是否可编辑
	private Integer isAdmin = 0;//是否为管理员显示权重和洗漱
	private Integer isFrequency;	//是否频次定向
	private Integer frequencyType;	//频次定向方式
	private String frequencyNum;	//次数
	private Integer isProduct;	//是否频次定向
	
	private String consumerName;	// 客户名称
	private String advertiserName;	// 广告主名称
	

	public Integer getEditable() {
		return editable;
	}
	public void setEditable(Integer editable) {
		this.editable = editable;
	}
	public String getBartemplateName() {
		return bartemplateName;
	}
	public void setBartemplateName(String bartemplateName) {
		this.bartemplateName = bartemplateName;
	}
	public Integer getBartemplateId() {
		return bartemplateId;
	}
	public void setBartemplateId(Integer bartemplateId) {
		this.bartemplateId = bartemplateId;
	}
	public Integer getAdvActiveId() {
		return advActiveId;
	}
	public void setAdvActiveId(Integer advActiveId) {
		this.advActiveId = advActiveId;
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
	public String getAdvparUrl() {
		return advparUrl;
	}
	public void setAdvparUrl(String advparUrl) {
		this.advparUrl = advparUrl;
	}
	public String getRedirect() {
		return redirect;
	}
	public void setRedirect(String redirect) {
		this.redirect = redirect;
	}
	public String getMonition() {
		return monition;
	}
	public void setMonition(String monition) {
		this.monition = monition;
	}
	public Integer getIsAdmin() {
		return isAdmin;
	}
	public void setIsAdmin(Integer isAdmin) {
		this.isAdmin = isAdmin;
	}
	public Integer getIsFrequency() {
		return isFrequency;
	}
	public void setIsFrequency(Integer isFrequency) {
		this.isFrequency = isFrequency;
	}
	public Integer getFrequencyType() {
		return frequencyType;
	}
	public void setFrequencyType(Integer frequencyType) {
		this.frequencyType = frequencyType;
	}
	public String getFrequencyNum() {
		return frequencyNum;
	}
	public void setFrequencyNum(String frequencyNum) {
		this.frequencyNum = frequencyNum;
	}
	public Integer getIsProduct() {
		return isProduct;
	}
	public void setIsProduct(Integer isProduct) {
		this.isProduct = isProduct;
	}
	public String getConsumerName() {
		return consumerName;
	}
	public void setConsumerName(String consumerName) {
		this.consumerName = consumerName;
	}
	public String getAdvertiserName() {
		return advertiserName;
	}
	public void setAdvertiserName(String advertiserName) {
		this.advertiserName = advertiserName;
	}
	
}
