package com.ku6ads.dao.entity.sysconfig;

import com.ku6ads.dao.entity.ExtEntity;

/**
 * 广告主类别
 * @author liujunshi
 *
 */
public class AdvertiserCategory extends ExtEntity{

	private int categoryId;
	private String categoryName;
	
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
	
}
