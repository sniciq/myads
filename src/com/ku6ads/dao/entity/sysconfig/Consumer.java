package com.ku6ads.dao.entity.sysconfig;

import java.util.List;

import com.ku6ads.dao.entity.ExtEntity;

/**
 * 客户（下单公司）
 * @author liujunshi
 *
 */
public class Consumer extends ExtEntity{

	private Integer consumerId;
	private Integer areaId;
	private String areaName;
	private Integer categoryId;
	private String categoryName;
	private Integer companyId;
	private String companyName;
	private String consumerName;
	private String consumerAdress;
	private String consumerZip;
	private List<ContactPerson> contactlist;
	private Integer consumerPid;
	
	public List<ContactPerson> getContactlist() {
		return contactlist;
	}
	public void setContactlist(List<ContactPerson> contactlist) {
		this.contactlist = contactlist;
	}
	public Integer getConsumerId() {
		return consumerId;
	}
	public void setConsumerId(Integer consumerId) {
		this.consumerId = consumerId;
	}
	public Integer getAreaId() {
		return areaId;
	}
	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public Integer getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getConsumerName() {
		return consumerName;
	}
	public void setConsumerName(String consumerName) {
		this.consumerName = consumerName;
	}
	public String getConsumerAdress() {
		return consumerAdress;
	}
	public void setConsumerAdress(String consumerAdress) {
		this.consumerAdress = consumerAdress;
	}
	public String getConsumerZip() {
		return consumerZip;
	}
	public void setConsumerZip(String consumerZip) {
		this.consumerZip = consumerZip;
	}
	public Integer getConsumerPid() {
		return consumerPid;
	}
	public void setConsumerPid(Integer consumerPid) {
		this.consumerPid = consumerPid;
	}
	
	
}
