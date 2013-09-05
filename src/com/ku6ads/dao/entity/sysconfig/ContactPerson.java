package com.ku6ads.dao.entity.sysconfig;

import com.ku6ads.dao.entity.ExtEntity;

/**
 * 联系人
 * @author liujunshi
 *
 */
public class ContactPerson extends ExtEntity{

	private Integer contactPersonId;
	private String contactPersonName;
	private String contactPersonTel;
	private Integer	consumerId; 
	public Integer getConsumerId() {
		return consumerId;
	}
	public void setConsumerId(Integer consumerId) {
		this.consumerId = consumerId;
	}
	public String getContactPersonTel() {
		return contactPersonTel;
	}
	public void setContactPersonTel(String contactPersonTel) {
		this.contactPersonTel = contactPersonTel;
	}
	private String contactPersonOnline;
	private String contactPersonTitle;
	private String type;

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public final String TYPE_CONSUMER = "C";
	public final String TYPE_ADGROUP = "A";
	
	public Integer getContactPersonId() {
		return contactPersonId;
	}
	public void setContactPersonId(Integer contactPersonId) {
		this.contactPersonId = contactPersonId;
	}
	public String getContactPersonName() {
		return contactPersonName;
	}
	public void setContactPersonName(String contactPersonName) {
		this.contactPersonName = contactPersonName;
	}
	public String getContactPersonOnline() {
		return contactPersonOnline;
	}
	public void setContactPersonOnline(String contactPersonOnline) {
		this.contactPersonOnline = contactPersonOnline;
	}
	public String getContactPersonTitle() {
		return contactPersonTitle;
	}
	public void setContactPersonTitle(String contactPersonTitle) {
		this.contactPersonTitle = contactPersonTitle;
	}
}
