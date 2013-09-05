package com.ku6ads.dao.entity.advert;

import com.ku6ads.dao.entity.ExtEntity;

public class AdvbarPrice  extends ExtEntity {
	private Integer id;//编号
	private String pos;//位置
	private String format;//形式
	private String impression;//曝光
	private String clickRate;//点击率
	private Integer saleType;//售卖方式
	private String price;//价格
	private String storage;//库存(后改为存名称)
	private String materiel;//物料要求
	private Integer barId;//广告条id
	
	//以下增加外加属性
	private String siteName; //网站名称
	
	private String channelName; //频道名称
	
	private String advbarName;//广告条名称
	
	private String barsize;//广告条规格
	
	private String pageType;//广告类型
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPos() {
		return pos;
	}
	public void setPos(String pos) {
		this.pos = pos;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public String getImpression() {
		return impression;
	}
	public void setImpression(String impression) {
		this.impression = impression;
	}
	public String getClickRate() {
		return clickRate;
	}
	public void setClickRate(String clickRate) {
		this.clickRate = clickRate;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getStorage() {
		return storage;
	}
	public void setStorage(String storage) {
		this.storage = storage;
	}
	public Integer getBarId() {
		return barId;
	}
	public void setBarId(Integer barId) {
		this.barId = barId;
	}
	public String getMateriel() {
		return materiel;
	}
	public void setMateriel(String materiel) {
		this.materiel = materiel;
	}
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	public String getAdvbarName() {
		return advbarName;
	}
	public void setAdvbarName(String advbarName) {
		this.advbarName = advbarName;
	}
	public String getBarsize() {
		return barsize;
	}
	public void setBarsize(String barsize) {
		this.barsize = barsize;
	}
	public String getPageType() {
		return pageType;
	}
	public void setPageType(String pageType) {
		this.pageType = pageType;
	}
	public Integer getSaleType() {
		return saleType;
	}
	public void setSaleType(Integer saleType) {
		this.saleType = saleType;
	}
}
