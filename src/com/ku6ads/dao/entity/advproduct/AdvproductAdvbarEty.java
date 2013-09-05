package com.ku6ads.dao.entity.advproduct;

import com.ku6ads.dao.entity.ExtEntity;

public class AdvproductAdvbarEty extends ExtEntity {

	private Integer id;
	private Integer advproductId;
	private Integer advbarId;

	private Integer channelId; // 所属频道id
	private String channelName; // 所属频道名称
	private String advbarName;
	private String barsizeName;
	private String barsizeId;
	private String content;
	private Integer sortOrder;
	
	public Integer getId() {
		return this.id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAdvproductId() {
		return this.advproductId;
	}
	public void setAdvproductId(Integer advproductId) {
		this.advproductId = advproductId;
	}

	public Integer getAdvbarId() {
		return this.advbarId;
	}
	public void setAdvbarId(Integer advbarId) {
		this.advbarId = advbarId;
	}

	public Integer getSortOrder() {
		return this.sortOrder;
	}
	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}
	public String getBarsizeName() {
		return barsizeName;
	}
	public void setBarsizeName(String barsizeName) {
		this.barsizeName = barsizeName;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getAdvbarName() {
		return advbarName;
	}
	public void setAdvbarName(String advbarName) {
		this.advbarName = advbarName;
	}
	public String getBarsizeId() {
		return barsizeId;
	}
	public void setBarsizeId(String barsizeId) {
		this.barsizeId = barsizeId;
	}
	public Integer getChannelId() {
		return channelId;
	}
	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
}