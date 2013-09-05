package com.ku6ads.dao.entity.advflight;

import java.util.Date;

/**
 * 预测广告条实体<br>
 * 考虑到一次查询出全部广告条，故使用如下精简对象
 * @author yangHanguang
 *
 */
public class ForecastAdvbarEty {
	private Integer id; // 编号
	private String name; // 名称
	private String forecasrDate;
	
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
	public String getForecasrDate() {
		return forecasrDate;
	}
	public void setForecasrDate(String forecasrDate) {
		this.forecasrDate = forecasrDate;
	}
}
