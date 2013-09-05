package com.ku6ads.dao.entity.advflight;

import java.util.Date;

import com.ku6ads.dao.entity.ExtEntity;
/**
 * 广告活动
 * @author liujunshi
 *
 */
public class AdvActive extends ExtEntity {

	private Integer id;//编号
	private String name;//名称
	private Integer projectId;//执行单id
	private String projectName;//执行单名称
	private Integer consumerId;//客户id
	private String consumerName;//客户 名称
	private Integer advertiserId;//广告主id
	private String advertiserName;//广告主名称
	private String ditchName;//渠道销售
	private Integer ditchId;//渠道销售Id
	private String saleName;//直客销售
	private Integer saleId;//直客销售Id
	//private Date createTime;//创建时间
	
	public static final int STATUS_USE = 0;//状态常量 可用
	public static final int STATUS_DEL = 1;//状态常量 删除
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
	public Integer getProjectId() {
		return projectId;
	}
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public Integer getConsumerId() {
		return consumerId;
	}
	public void setConsumerId(Integer consumerId) {
		this.consumerId = consumerId;
	}
	public String getConsumerName() {
		return consumerName;
	}
	public void setConsumerName(String consumerName) {
		this.consumerName = consumerName;
	}
	public Integer getAdvertiserId() {
		return advertiserId;
	}
	public void setAdvertiserId(Integer advertiserId) {
		this.advertiserId = advertiserId;
	}
	public String getAdvertiserName() {
		return advertiserName;
	}
	public void setAdvertiserName(String advertiserName) {
		this.advertiserName = advertiserName;
	}
	public String getDitchName() {
		return ditchName;
	}
	public void setDitchName(String ditchName) {
		this.ditchName = ditchName;
	}
	public Integer getDitchId() {
		return ditchId;
	}
	public void setDitchId(Integer ditchId) {
		this.ditchId = ditchId;
	}
	public String getSaleName() {
		return saleName;
	}
	public void setSaleName(String saleName) {
		this.saleName = saleName;
	}
	public Integer getSaleId() {
		return saleId;
	}
	public void setSaleId(Integer saleId) {
		this.saleId = saleId;
	}


	
}
