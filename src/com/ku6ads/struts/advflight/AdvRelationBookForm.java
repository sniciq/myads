package com.ku6ads.struts.advflight;

import com.ku6ads.dao.entity.advflight.BookPackage;

public class AdvRelationBookForm extends BookPackage {
	private Integer unRelationBookCount;//排期包下没有关联广告的排期数

	/**
	 * 排期包下没有关联广告的排期数
	 * @return
	 */
	public Integer getUnRelationBookCount() {
		return unRelationBookCount;
	}

	/**
	 * 排期包下没有关联广告的排期数
	 * @param unRelationBookCount
	 */
	public void setUnRelationBookCount(Integer unRelationBookCount) {
		this.unRelationBookCount = unRelationBookCount;
	}
	
}
