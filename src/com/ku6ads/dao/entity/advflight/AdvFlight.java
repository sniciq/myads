package com.ku6ads.dao.entity.advflight;

import java.util.Date;

import com.ku6ads.dao.entity.ExtEntity;

/**
 * 广告投放
 * @author liujunshi
 *
 */
public class AdvFlight extends ExtEntity{
	private Integer id;
	private Integer advActiveId;//广告活动Id
	private Integer bookId;//排期Id
	private Integer projectId;	//执行单id
	private Integer bookPackageId;	//排期包id
	private Integer advbarId;	//广告条id
	private Integer channelId;	//频道id
	private Date startTime;	//起始时间
	private Date endTime;	//结束时间
	private Integer saleType;	//售卖方式
	private java.lang.Double price;	//刊例价
	private java.lang.Double discount;	//折扣
	private java.lang.Double disprice;	//折后单价
	private Integer useType;	//使用方式
	private Integer flightNum;	//投放量
	private Integer priority;	//优先级
	private java.lang.Double proportion;	//权重
	private Integer priceId;	//刊例id
	private String hourDirect;	//小时定向
	private String areaDirect;	//区域定向
	private Integer isFrequency;	//是否频次定向
	private Integer frequencyType;	//频次定向方式
	private String frequencyNum;	//次数
	private String isContentDirect;	//是否内容定向
	private String keyword;	//关键字
	private String user;	//用户
	private String video;	//视频id
	private String program;	//节目
	private String activity;	//活动
	private String subject;	//专题
	private Integer isnull;	//是否空广告
	private Integer advId;
	private java.lang.String flightcode;
	private String monition;//曝光地址
	private Date endTimeEnd;
	private String format;	// 形式
	private Integer advposId;//广告位Id
	private Integer flightstatus;
	private Integer flightstatusBack;
	private Integer scrBPackageId;//关联排期包ID
	private String flightcodejson;//播放器广告，json形式的代码。
	private Date maxTime;//关联所有排序的最后时间
	
	
	public static final int STATUS_FLIGHT_WILL = 1;
	public static final int STATUS_FIGHTING = 2;
	public static final int STATUS_FIGHT_PAUSE = 3;
	public static final int STATUS_FIGHT_COM = 4;
	public static final int STATUS_FIGHT_STOP = 5;
	public static final String BINDFAIL = "BINDFAIL";
	public static final String MEMORYFAIL = "memoryfail";
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getProjectId() {
		return projectId;
	}
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}
	public Integer getBookPackageId() {
		return bookPackageId;
	}
	public void setBookPackageId(Integer bookPackageId) {
		this.bookPackageId = bookPackageId;
	}
	public Integer getAdvbarId() {
		return advbarId;
	}
	public void setAdvbarId(Integer advbarId) {
		this.advbarId = advbarId;
	}
	public Integer getChannelId() {
		return channelId;
	}
	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
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
	public Integer getSaleType() {
		return saleType;
	}
	public void setSaleType(Integer saleType) {
		this.saleType = saleType;
	}
	public java.lang.Double getPrice() {
		return price;
	}
	public void setPrice(java.lang.Double price) {
		this.price = price;
	}
	public java.lang.Double getDiscount() {
		return discount;
	}
	public void setDiscount(java.lang.Double discount) {
		this.discount = discount;
	}
	public java.lang.Double getDisprice() {
		return disprice;
	}
	public void setDisprice(java.lang.Double disprice) {
		this.disprice = disprice;
	}
	public Integer getUseType() {
		return useType;
	}
	public void setUseType(Integer useType) {
		this.useType = useType;
	}
	public Integer getFlightNum() {
		return flightNum;
	}
	public void setFlightNum(Integer flightNum) {
		this.flightNum = flightNum;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	public java.lang.Double getProportion() {
		return proportion;
	}
	public void setProportion(java.lang.Double proportion) {
		this.proportion = proportion;
	}
	public Integer getPriceId() {
		return priceId;
	}
	public void setPriceId(Integer priceId) {
		this.priceId = priceId;
	}
	public String getHourDirect() {
		return hourDirect;
	}
	public void setHourDirect(String hourDirect) {
		this.hourDirect = hourDirect;
	}
	public String getAreaDirect() {
		return areaDirect;
	}
	public void setAreaDirect(String areaDirect) {
		this.areaDirect = areaDirect;
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
	public String getIsContentDirect() {
		return isContentDirect;
	}
	public void setIsContentDirect(String isContentDirect) {
		this.isContentDirect = isContentDirect;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getVideo() {
		return video;
	}
	public void setVideo(String video) {
		this.video = video;
	}
	public String getProgram() {
		return program;
	}
	public void setProgram(String program) {
		this.program = program;
	}
	public String getActivity() {
		return activity;
	}
	public void setActivity(String activity) {
		this.activity = activity;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public Integer getIsnull() {
		return isnull;
	}
	public void setIsnull(Integer isnull) {
		this.isnull = isnull;
	}
	public Integer getAdvId() {
		return advId;
	}
	public void setAdvId(Integer advId) {
		this.advId = advId;
	}
	public java.lang.String getFlightcode() {
		return flightcode;
	}
	public void setFlightcode(java.lang.String flightcode) {
		this.flightcode = flightcode;
	}
	public String getMonition() {
		return monition;
	}
	public void setMonition(String monition) {
		this.monition = monition;
	}
	public Integer getBookId() {
		return bookId;
	}
	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}
	public Integer getAdvActiveId() {
		return advActiveId;
	}
	public void setAdvActiveId(Integer advActiveId) {
		this.advActiveId = advActiveId;
	}
	public Date getEndTimeEnd() {
		return endTimeEnd;
	}
	public void setEndTimeEnd(Date endTimeEnd) {
		this.endTimeEnd = endTimeEnd;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public Integer getAdvposId() {
		return advposId;
	}
	public void setAdvposId(Integer advposId) {
		this.advposId = advposId;
	}
	public Integer getFlightstatus() {
		return flightstatus;
	}
	public void setFlightstatus(Integer flightstatus) {
		this.flightstatus = flightstatus;
	}
	public Integer getScrBPackageId() {
		return scrBPackageId;
	}
	public void setScrBPackageId(Integer scrBPackageId) {
		this.scrBPackageId = scrBPackageId;
	}
	public String getFlightcodejson() {
		return flightcodejson;
	}
	public void setFlightcodejson(String flightcodejson) {
		this.flightcodejson = flightcodejson;
	}
	public Date getMaxTime() {
		return maxTime;
	}
	public void setMaxTime(Date maxTime) {
		this.maxTime = maxTime;
	}
	public Integer getFlightstatusBack() {
		return flightstatusBack;
	}
	public void setFlightstatusBack(Integer flightstatusBack) {
		this.flightstatusBack = flightstatusBack;
	}
}
