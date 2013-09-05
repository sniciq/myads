package com.ku6ads.dao.entity.statistics;

import java.util.Date;

import com.ku6ads.dao.entity.ExtEntity;

public class StatisticsSiteViews extends ExtEntity {

	private Integer id;
	private Integer siteId;
	private String name;
	private String onceMore;
	private String twiceMore;
	private String threeMore;
	private String fourthMore;
	private String fiveMore;
	private String sixMore;
	private String sevenMore;
	private String eightMore;
	private String nineMore;
	private String teenMore;
	private Date updateTime;

	public Integer getId() {
		return this.id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSiteId() {
		return this.siteId;
	}
	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public String getOnceMore() {
		return this.onceMore;
	}
	public void setOnceMore(String onceMore) {
		this.onceMore = onceMore;
	}

	public String getTwiceMore() {
		return this.twiceMore;
	}
	public void setTwiceMore(String twiceMore) {
		this.twiceMore = twiceMore;
	}

	public String getThreeMore() {
		return this.threeMore;
	}
	public void setThreeMore(String threeMore) {
		this.threeMore = threeMore;
	}

	public String getFourthMore() {
		return this.fourthMore;
	}
	public void setFourthMore(String fourthMore) {
		this.fourthMore = fourthMore;
	}

	public String getFiveMore() {
		return this.fiveMore;
	}
	public void setFiveMore(String fiveMore) {
		this.fiveMore = fiveMore;
	}

	public String getSixMore() {
		return this.sixMore;
	}
	public void setSixMore(String sixMore) {
		this.sixMore = sixMore;
	}

	public String getSevenMore() {
		return this.sevenMore;
	}
	public void setSevenMore(String sevenMore) {
		this.sevenMore = sevenMore;
	}

	public String getEightMore() {
		return this.eightMore;
	}
	public void setEightMore(String eightMore) {
		this.eightMore = eightMore;
	}

	public String getNineMore() {
		return this.nineMore;
	}
	public void setNineMore(String nineMore) {
		this.nineMore = nineMore;
	}

	public String getTeenMore() {
		return this.teenMore;
	}
	public void setTeenMore(String teenMore) {
		this.teenMore = teenMore;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}