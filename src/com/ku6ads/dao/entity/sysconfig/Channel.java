package com.ku6ads.dao.entity.sysconfig;

import java.util.Date;

import com.ku6ads.dao.entity.ExtEntity;

/**
 * 频道
 * @author xuxianan
 *
 */
public class Channel extends ExtEntity {

	private Integer channelId; // 频道编号
	private Integer parentChannelId; // 上级频道编号
	private String name; // 频道名称
	private String nameEn; // 英文名称
	private String url;//域名
	private int level;//级别
	private Integer siteId;//所属网站id
	private String siteName;
	private String sourceId;
	
	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}
	private double proportion;//权重系数
	private String modifier;//修改人
	private String parentName; // 上级频道名称
	private boolean status; // 频道状态 0:禁用 1:启用
	private Integer creatorId; // 创建者编号
	private Date createDate; // 创建日期
	
	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public double getProportion() {
		return proportion;
	}

	public void setProportion(double proportion) {
		this.proportion = proportion;
	}

	public String getModifier() {
		return modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getNameEn() {
		return nameEn;
	}

	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	}

	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	public Integer getParentChannelId() {
		return parentChannelId;
	}

	public void setParentChannelId(Integer parentChannelId) {
		this.parentChannelId = parentChannelId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Integer getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Integer creatorId) {
		this.creatorId = creatorId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

}
