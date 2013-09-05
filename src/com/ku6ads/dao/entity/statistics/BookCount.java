package com.ku6ads.dao.entity.statistics;

import java.util.Date;

import com.ku6ads.dao.entity.ExtEntity;

public class BookCount extends ExtEntity{

	private String channelName;	//	频道
	private String siteName;	//	版块
	private Integer advbarId;	//	广告条Id
	private String advbarName;	//	广告条名称
	private Long impression;	//	日均曝光量
	private Long use;	//日均使用总量
	private Long buy;	//购买量
	private String buyRate;		//	购买率；
	private Long free;	//配送总量
	private String freeRate;	//配送率
	private Long compensate;	//补偿总量
	private String compensateRate;	//补偿率
	private Long pr;	//软性总量
	private String prRate;	//软性率
	private Long test;	//	测试量
	private String testRate;	//测试率
	private Long ecommerce;	//	电商量
	private String ecommerceRate;	//电商率
	private Long game;	//	游戏量
	private String gameRate;	//游戏率
	private Long replace;	//	置换量
	private String replaceRate;	//	置换率
	private Long bd;	//	BD推广量
	private String bdRate;	//BD推广率
	private Long inuse;	//	内部使用量
	private String inuseRate;	//	内部使用率
	private Long leaving;	//剩余量
	private String leavingRate;	//剩余率
	private String clickRate; //点击率
	private Long click;
	private Integer useType;
	private Date startTime;
	private Date endTime;
	
	public Long getClick() {
		return click;
	}
	public void setClick(Long click) {
		this.click = click;
	}
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	public Integer getAdvbarId() {
		return advbarId;
	}
	public void setAdvbarId(Integer advbarId) {
		this.advbarId = advbarId;
	}
	public String getAdvbarName() {
		return advbarName;
	}
	public void setAdvbarName(String advbarName) {
		this.advbarName = advbarName;
	}
	public Long getImpression() {
		return impression;
	}
	public void setImpression(Long impression) {
		this.impression = impression;
	}
	public Long getUse() {
		return use;
	}
	public void setUse(Long use) {
		this.use = use;
	}
	public Long getBuy() {
		return buy;
	}
	public void setBuy(Long buy) {
		this.buy = buy;
	}
	public String getBuyRate() {
		return buyRate;
	}
	public void setBuyRate(String buyRate) {
		this.buyRate = buyRate;
	}
	public Long getFree() {
		return free;
	}
	public void setFree(Long free) {
		this.free = free;
	}
	public String getFreeRate() {
		return freeRate;
	}
	public void setFreeRate(String freeRate) {
		this.freeRate = freeRate;
	}
	public Long getCompensate() {
		return compensate;
	}
	public void setCompensate(Long compensate) {
		this.compensate = compensate;
	}
	public String getCompensateRate() {
		return compensateRate;
	}
	public void setCompensateRate(String compensateRate) {
		this.compensateRate = compensateRate;
	}
	public Long getPr() {
		return pr;
	}
	public void setPr(Long pr) {
		this.pr = pr;
	}
	public String getPrRate() {
		return prRate;
	}
	public void setPrRate(String prRate) {
		this.prRate = prRate;
	}
	public Long getTest() {
		return test;
	}
	public void setTest(Long test) {
		this.test = test;
	}
	public String getTestRate() {
		return testRate;
	}
	public void setTestRate(String testRate) {
		this.testRate = testRate;
	}
	public Long getEcommerce() {
		return ecommerce;
	}
	public void setEcommerce(Long ecommerce) {
		this.ecommerce = ecommerce;
	}
	public String getEcommerceRate() {
		return ecommerceRate;
	}
	public void setEcommerceRate(String ecommerceRate) {
		this.ecommerceRate = ecommerceRate;
	}
	public Long getGame() {
		return game;
	}
	public void setGame(Long game) {
		this.game = game;
	}
	public String getGameRate() {
		return gameRate;
	}
	public void setGameRate(String gameRate) {
		this.gameRate = gameRate;
	}
	public Long getReplace() {
		return replace;
	}
	public void setReplace(Long replace) {
		this.replace = replace;
	}
	public String getReplaceRate() {
		return replaceRate;
	}
	public void setReplaceRate(String replaceRate) {
		this.replaceRate = replaceRate;
	}
	public Long getBd() {
		return bd;
	}
	public void setBd(Long bd) {
		this.bd = bd;
	}
	public String getBdRate() {
		return bdRate;
	}
	public void setBdRate(String bdRate) {
		this.bdRate = bdRate;
	}
	public Long getInuse() {
		return inuse;
	}
	public void setInuse(Long inuse) {
		this.inuse = inuse;
	}
	public String getInuseRate() {
		return inuseRate;
	}
	public void setInuseRate(String inuseRate) {
		this.inuseRate = inuseRate;
	}
	public Long getLeaving() {
		return leaving;
	}
	public void setLeaving(Long leaving) {
		this.leaving = leaving;
	}
	public String getLeavingRate() {
		return leavingRate;
	}
	public void setLeavingRate(String leavingRate) {
		this.leavingRate = leavingRate;
	}
	public String getClickRate() {
		return clickRate;
	}
	public void setClickRate(String clickRate) {
		this.clickRate = clickRate;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Integer getUseType() {
		return useType;
	}
	public void setUseType(Integer useType) {
		this.useType = useType;
	}
	
}
