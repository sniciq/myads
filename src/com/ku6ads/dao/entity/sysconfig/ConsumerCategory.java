package com.ku6ads.dao.entity.sysconfig;

import com.ku6ads.dao.entity.ExtEntity;

/**
 * 客户（下单公司）类别
 * @author liujunshi
 *
 */
public class ConsumerCategory extends ExtEntity{

	private int consumerCategoryId;
	private String consumerCategoryName;
	
	public int getConsumerCategoryId() {
		return consumerCategoryId;
	}
	public void setConsumerCategoryId(int consumerCategoryId) {
		this.consumerCategoryId = consumerCategoryId;
	}
	public String getConsumerCategoryName() {
		return consumerCategoryName;
	}
	public void setConsumerCategoryName(String consumerCategoryName) {
		this.consumerCategoryName = consumerCategoryName;
	}
}
