package com.ku6ads.dao.entity.advert;

import com.ku6ads.dao.entity.ExtEntity;

/**
 * 广告条
 * @author xuxianan
 *
 */
public class Advbar extends ExtEntity {

	private Integer id; // 编号
	private String name; // 名称
	private String nameEn; // 英文名称
	private Integer posId; // 所属广告位id
	private String posName; // 所属广告位名称
	private Integer channelId; // 所属频道id
	private String channelName; // 所属频道名称
	private Integer siteId; // 所属网站id
	private String siteName; // 所属网站名称
	private Integer barsizeId; // 广告条规格id
	private Integer bartemId; // 广告条模板id
	private Integer content; // 广告容量
	private Integer num; // 广告条排序
	private Integer srcposId; // 关联广告条
	private String advType; // 物料类型
	private String code; // 广告条代码
	private String note; // 备注
	
	private Integer isBackground;	// 是否为背景广告条 
	private Integer futureFlight;

	private String positionName; // 规格名称

	public static final Integer START_STATUS = 0;

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

	public String getNameEn() {
		return nameEn;
	}

	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	}

	public Integer getPosId() {
		return posId;
	}

	public void setPosId(Integer posId) {
		this.posId = posId;
	}

	public String getPosName() {
		return posName;
	}

	public void setPosName(String posName) {
		this.posName = posName;
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

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public Integer getBarsizeId() {
		return barsizeId;
	}

	public void setBarsizeId(Integer barsizeId) {
		this.barsizeId = barsizeId;
	}

	public Integer getBartemId() {
		return bartemId;
	}

	public void setBartemId(Integer bartemId) {
		this.bartemId = bartemId;
	}

	public Integer getContent() {
		return content;
	}

	public void setContent(Integer content) {
		this.content = content;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Integer getSrcposId() {
		return srcposId;
	}

	public void setSrcposId(Integer srcposId) {
		this.srcposId = srcposId;
	}

	public String getAdvType() {
		return advType;
	}

	public void setAdvType(String advType) {
		this.advType = advType;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getPositionName() {
		return positionName;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getFutureFlight() {
		return futureFlight;
	}

	public void setFutureFlight(Integer futureFlight) {
		this.futureFlight = futureFlight;
	}

	/**
	 * 是否背景广告条<br>
	 * <br>
	 * 1: 是 0：否
	 */
	public Integer getIsBackground() {
		return isBackground;
	}

	/**
	 * 是否背景广告条<br>
	 * <br>
	 * 1: 是 0：否
	 */
	public void setIsBackground(Integer isBackground) {
		this.isBackground = isBackground;
	}
	
}
