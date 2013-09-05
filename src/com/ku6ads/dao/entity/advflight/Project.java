package com.ku6ads.dao.entity.advflight;

import java.util.Date;
import com.ku6ads.dao.entity.ExtEntity;

/**
 * 执行单
 * @author xuxianan
 *
 */
public class Project extends ExtEntity {

	private Integer id; // 编号
	private String contractNum; // 合同号
	private Integer type; // 执行单类型
	private String name; // 执行单名称
	private String consumerName; // 客户名称
	private Integer consumerId; // 客户id
	private String advertiserName; // 广告主名称
	private Integer advertiserId; //广告主id
	private String productLineName; //产品线名称
	private Integer productLineId; //产品线id
	private String productName; //产品名称
	private Integer productId; //产品id
	private Integer area; //销售区域
	private String ditchName; //渠道销售名称
	private Integer ditchId; //渠道销售id
	private String saleName; //直客销售名称
	private Integer saleId; //直客销售id
	private Double sum; //执行单总金额
	private Double discount; //折扣
	private Double sendRate; //配送比例
	private Date startTime; //开始时间
	private Date endTime; // 结束时间
	private String explains; //广告形式需求
	private Integer impression; //曝光
	private Double click; //点击
	private String note; // 备注	
	private Integer bussinessStatus;//业务状态,见BussinessStatus类
	
	private Integer statusFlag;//状态标志
	
	private Integer isDefault;	// 是否为默认执行单

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getContractNum() {
		return contractNum;
	}

	public void setContractNum(String contractNum) {
		this.contractNum = contractNum;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getConsumerName() {
		return consumerName;
	}

	public void setConsumerName(String consumerName) {
		this.consumerName = consumerName;
	}

	public Integer getConsumerId() {
		return consumerId;
	}

	public void setConsumerId(Integer consumerId) {
		this.consumerId = consumerId;
	}

	public String getAdvertiserName() {
		return advertiserName;
	}

	public void setAdvertiserName(String advertiserName) {
		this.advertiserName = advertiserName;
	}

	public Integer getAdvertiserId() {
		return advertiserId;
	}

	public void setAdvertiserId(Integer advertiserId) {
		this.advertiserId = advertiserId;
	}

	public String getProductLineName() {
		return productLineName;
	}

	public void setProductLineName(String productLineName) {
		this.productLineName = productLineName;
	}

	public Integer getProductLineId() {
		return productLineId;
	}

	public void setProductLineId(Integer productLineId) {
		this.productLineId = productLineId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getArea() {
		return area;
	}

	public void setArea(Integer area) {
		this.area = area;
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

	public Double getSum() {
		return sum;
	}

	public void setSum(Double sum) {
		this.sum = sum;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public Double getSendRate() {
		return sendRate;
	}

	public void setSendRate(Double sendRate) {
		this.sendRate = sendRate;
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

	public String getExplains() {
		return explains;
	}

	public void setExplains(String explains) {
		this.explains = explains;
	}

	public Integer getImpression() {
		return impression;
	}

	public void setImpression(Integer impression) {
		this.impression = impression;
	}

	public Double getClick() {
		return click;
	}

	public void setClick(Double click) {
		this.click = click;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	/**
	 * 业务状态,见BussinessStatus类
	 * @return
	 */
	public Integer getBussinessStatus() {
		return bussinessStatus;
	}
	
	/**
	 * 业务状态,见BussinessStatus类
	 * @param bussinessStatus
	 */
	public void setBussinessStatus(Integer bussinessStatus) {
		this.bussinessStatus = bussinessStatus;
	}

	/**
	 * 是否为默认执行单
	 * @return @param 0为否,1为是
	 */
	public Integer getIsDefault() {
		return isDefault;
	}

	/**
	 * 是否为默认执行单
	 * @param 0为否,1为是
	 */
	public void setIsDefault(Integer isDefault) {
		this.isDefault = isDefault;
	}

	public Integer getStatusFlag() {
		return statusFlag;
	}

	public void setStatusFlag(Integer statusFlag) {
		this.statusFlag = statusFlag;
	}
	
}
