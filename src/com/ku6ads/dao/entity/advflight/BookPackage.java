package com.ku6ads.dao.entity.advflight;

import java.util.Date;

import com.ku6ads.dao.entity.ExtEntity;

public class BookPackage extends ExtEntity {
	private Integer id;
	private Integer projectId;	//执行单id
	private Integer advbarId;	//广告条id
	private Integer channelId;	//频道id
	private Date startTime;	//开始时间
	private Date endTime;	//结束时间
	private Integer saleType;	//售卖方式
	private String format;
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
	private String areaDirectCode;//地域定向编码
	private Integer isFrequency;	//是否频次定向
	private Integer frequencyType;	//频次定向方式
	private Integer frequencyNum;	//次数
	private Integer isContentDirect;	//是否内容定向
	private String keyword;	//关键字
	private String user;	//用户
	private String video;	//视频id
	private String program;	//节目
	private String activity;	//活动
	private String subject;	//专题
	private Integer isNull;	//是否空广告
	private Integer bussinessStatus;//业务状态,见BussinessStatus类
	private String remark;//备注
	private Integer scrBPackageId;//关联排期包ID
	private Integer bookpackageType;//排期包类型，1：广告条排期包，2：广告产品排期包
	private Integer advproductId;//广告产品ID
	
	//关联属性
	private String channelName;	// 频道名称
	private String advbarName;	// 广告条名称
	private String saleTypeName;//售卖方式名称
	private String useTypeName;//使用类型名称
	private String priceName;//
	private String frequencyTypeName;//频次定向名称
	private String advproductName;	// 产品名称
	
	public Integer getId() {
		return this.id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	* 得到 执行单id
	* @return 执行单id : Integer
	*/
	public Integer getProjectId() {
		return this.projectId;
	}
	/**
	 * 设置 执行单id
	 * @param projectId, 执行单id : Integer
	*/
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	/**
	* 得到 广告条id
	* @return 广告条id : Integer
	*/
	public Integer getAdvbarId() {
		return this.advbarId;
	}
	/**
	 * 设置 广告条id
	 * @param advbarId, 广告条id : Integer
	*/
	public void setAdvbarId(Integer advbarId) {
		this.advbarId = advbarId;
	}

	/**
	* 得到 频道id
	* @return 频道id : Integer
	*/
	public Integer getChannelId() {
		return this.channelId;
	}
	/**
	 * 设置 频道id
	 * @param channelId, 频道id : Integer
	*/
	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	/**
	* 得到 开始时间
	* @return 开始时间 : Date
	*/
	public Date getStartTime() {
		return this.startTime;
	}
	/**
	 * 设置 开始时间
	 * @param startTime, 开始时间 : Date
	*/
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	/**
	* 得到 结束时间
	* @return 结束时间 : Date
	*/
	public Date getEndTime() {
		return this.endTime;
	}
	/**
	 * 设置 结束时间
	 * @param endTime, 结束时间 : Date
	*/
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	/**
	* 得到 售卖方式
	* @return 售卖方式 : Integer
	*/
	public Integer getSaleType() {
		return this.saleType;
	}
	/**
	 * 设置 售卖方式
	 * @param saleType, 售卖方式 : Integer
	*/
	public void setSaleType(Integer saleType) {
		this.saleType = saleType;
	}

	/**
	* 得到 刊例价
	* @return 刊例价 : java.lang.Double
	*/
	public java.lang.Double getPrice() {
		return this.price;
	}
	/**
	 * 设置 刊例价
	 * @param price, 刊例价 : java.lang.Double
	*/
	public void setPrice(java.lang.Double price) {
		this.price = price;
	}

	/**
	* 得到 折扣
	* @return 折扣 : java.lang.Double
	*/
	public java.lang.Double getDiscount() {
		return this.discount;
	}
	/**
	 * 设置 折扣
	 * @param discount, 折扣 : java.lang.Double
	*/
	public void setDiscount(java.lang.Double discount) {
		this.discount = discount;
	}

	/**
	* 得到 折后单价
	* @return 折后单价 : java.lang.Double
	*/
	public java.lang.Double getDisprice() {
		return this.disprice;
	}
	/**
	 * 设置 折后单价
	 * @param disprice, 折后单价 : java.lang.Double
	*/
	public void setDisprice(java.lang.Double disprice) {
		this.disprice = disprice;
	}

	/**
	* 得到 使用方式
	* @return 使用方式 : Integer
	*/
	public Integer getUseType() {
		return this.useType;
	}
	/**
	 * 设置 使用方式
	 * @param useType, 使用方式 : Integer
	*/
	public void setUseType(Integer useType) {
		this.useType = useType;
	}

	/**
	* 得到 投放量
	* @return 投放量 : Integer
	*/
	public Integer getFlightNum() {
		return this.flightNum;
	}
	/**
	 * 设置 投放量
	 * @param flightNum, 投放量 : Integer
	*/
	public void setFlightNum(Integer flightNum) {
		this.flightNum = flightNum;
	}

	/**
	* 得到 优先级
	* @return 优先级 : Integer
	*/
	public Integer getPriority() {
		return this.priority;
	}
	/**
	 * 设置 优先级
	 * @param priority, 优先级 : Integer
	*/
	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	/**
	* 得到 权重
	* @return 权重 : java.lang.Double
	*/
	public java.lang.Double getProportion() {
		return this.proportion;
	}
	/**
	 * 设置 权重
	 * @param proportion, 权重 : java.lang.Double
	*/
	public void setProportion(java.lang.Double proportion) {
		this.proportion = proportion;
	}

	/**
	* 得到 刊例id
	* @return 刊例id : Integer
	*/
	public Integer getPriceId() {
		return this.priceId;
	}
	/**
	 * 设置 刊例id
	 * @param priceId, 刊例id : Integer
	*/
	public void setPriceId(Integer priceId) {
		this.priceId = priceId;
	}

	/**
	* 得到 小时定向
	* @return 小时定向 : String
	*/
	public String getHourDirect() {
		return this.hourDirect;
	}
	/**
	 * 设置 小时定向
	 * @param hourDirect, 小时定向 : String
	*/
	public void setHourDirect(String hourDirect) {
		this.hourDirect = hourDirect;
	}

	/**
	* 得到 区域定向
	* @return 区域定向 : String
	*/
	public String getAreaDirect() {
		return this.areaDirect;
	}
	/**
	 * 设置 区域定向
	 * @param areaDirect, 区域定向 : String
	*/
	public void setAreaDirect(String areaDirect) {
		this.areaDirect = areaDirect;
	}

	/**
	* 得到 是否频次定向
	* @return 是否频次定向 : Integer
	*/
	public Integer getIsFrequency() {
		return this.isFrequency;
	}
	/**
	 * 设置 是否频次定向
	 * @param isFrequency, 是否频次定向 : Integer
	*/
	public void setIsFrequency(Integer isFrequency) {
		this.isFrequency = isFrequency;
	}

	/**
	* 得到 频次定向方式
	* @return 频次定向方式 : Integer
	*/
	public Integer getFrequencyType() {
		return this.frequencyType;
	}
	/**
	 * 设置 频次定向方式
	 * @param frequencyType, 频次定向方式 : Integer
	*/
	public void setFrequencyType(Integer frequencyType) {
		this.frequencyType = frequencyType;
	}

	/**
	* 得到 次数
	* @return 次数 : Integer
	*/
	public Integer getFrequencyNum() {
		return this.frequencyNum;
	}
	/**
	 * 设置 次数
	 * @param frequencyNum, 次数 : Integer
	*/
	public void setFrequencyNum(Integer frequencyNum) {
		this.frequencyNum = frequencyNum;
	}

	/**
	 * 得到 是否内容定向
	 * @return
	 */
	public Integer getIsContentDirect() {
		return isContentDirect;
	}
	
	/**
	 * 是否内容定向
	 * @param isContentDirect
	 */
	public void setIsContentDirect(Integer isContentDirect) {
		this.isContentDirect = isContentDirect;
	}
	/**
	* 得到 关键字
	* @return 关键字 : String
	*/
	public String getKeyword() {
		return this.keyword;
	}
	/**
	 * 设置 关键字
	 * @param keyword, 关键字 : String
	*/
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	/**
	* 得到 用户
	* @return 用户 : String
	*/
	public String getUser() {
		return this.user;
	}
	/**
	 * 设置 用户
	 * @param user, 用户 : String
	*/
	public void setUser(String user) {
		this.user = user;
	}

	/**
	* 得到 视频id
	* @return 视频id : String
	*/
	public String getVideo() {
		return this.video;
	}
	/**
	 * 设置 视频id
	 * @param video, 视频id : String
	*/
	public void setVideo(String video) {
		this.video = video;
	}

	/**
	* 得到 节目
	* @return 节目 : String
	*/
	public String getProgram() {
		return this.program;
	}
	/**
	 * 设置 节目
	 * @param program, 节目 : String
	*/
	public void setProgram(String program) {
		this.program = program;
	}

	/**
	* 得到 活动
	* @return 活动 : String
	*/
	public String getActivity() {
		return this.activity;
	}
	/**
	 * 设置 活动
	 * @param activity, 活动 : String
	*/
	public void setActivity(String activity) {
		this.activity = activity;
	}

	/**
	* 得到 专题
	* @return 专题 : String
	*/
	public String getSubject() {
		return this.subject;
	}
	/**
	 * 设置 专题
	 * @param subject, 专题 : String
	*/
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	* 得到 是否空广告
	* @return 是否空广告 : Integer
	*/
	public Integer getIsNull() {
		return this.isNull;
	}
	/**
	 * 设置 是否空广告
	 * @param isNull, 是否空广告 : Integer
	*/
	public void setIsNull(Integer isNull) {
		this.isNull = isNull;
	}
	
	/**
	 * 得到频道名称
	 */
	public String getChannelName() {
		return channelName;
	}
	/**
	 * 得到广告条名称
	 */
	public String getAdvbarName() {
		return advbarName;
	}
	
	/**
	 * 业务状态,见BussinessStatus类
	 * @return
	 */
	public Integer getBussinessStatus() {
		return bussinessStatus;
	}
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * 业务状态,见BussinessStatus类
	 * @param bussinessStatus
	 */
	public void setBussinessStatus(Integer bussinessStatus) {
		this.bussinessStatus = bussinessStatus;
	}
	public String getSaleTypeName() {
		return saleTypeName;
	}
	public void setSaleTypeName(String saleTypeName) {
		this.saleTypeName = saleTypeName;
	}
	public String getUseTypeName() {
		return useTypeName;
	}
	public void setUseTypeName(String useTypeName) {
		this.useTypeName = useTypeName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	public void setAdvbarName(String advbarName) {
		this.advbarName = advbarName;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public String getAreaDirectCode() {
		return areaDirectCode;
	}
	public void setAreaDirectCode(String areaDirectCode) {
		this.areaDirectCode = areaDirectCode;
	}
	public String getFrequencyTypeName() {
		return frequencyTypeName;
	}
	public void setFrequencyTypeName(String frequencyTypeName) {
		this.frequencyTypeName = frequencyTypeName;
	}
	
	/**
	 * 关联排期包ID
	 * @return
	 */
	public Integer getScrBPackageId() {
		return scrBPackageId;
	}
	
	/**
	 * 关联排期包ID
	 * @param scrBPackageId
	 */
	public void setScrBPackageId(Integer scrBPackageId) {
		this.scrBPackageId = scrBPackageId;
	}
	
	/**
	 * 1：广告条排期包，2：广告产品排期包
	 * @param bookpackageType
	 */
	public Integer getBookpackageType() {
		return bookpackageType;
	}
	
	/**
	 * 1：广告条排期包，2：广告产品排期包
	 * @param bookpackageType
	 */
	public void setBookpackageType(Integer bookpackageType) {
		this.bookpackageType = bookpackageType;
	}
	public Integer getAdvproductId() {
		return advproductId;
	}
	public void setAdvproductId(Integer advproductId) {
		this.advproductId = advproductId;
	}
	public String getPriceName() {
		return priceName;
	}
	public void setPriceName(String priceName) {
		this.priceName = priceName;
	}
	public String getAdvproductName() {
		return advproductName;
	}
	public void setAdvproductName(String advproductName) {
		this.advproductName = advproductName;
	}
}
