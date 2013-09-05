package com.ku6ads.dao.entity.advert;

import java.util.Date;

import com.ku6ads.dao.entity.ExtEntity;
/**
 * 
 * @author liujunshi
 *
 */
public class PostemBartem extends ExtEntity {

	private Integer id;
	private String name ;
	private Integer postemId;
	private String postemName ;
	private Integer bartemId;
	private String bartemName ;
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
	public Integer getPostemId() {
		return postemId;
	}
	public void setPostemId(Integer postemId) {
		this.postemId = postemId;
	}
	public String getPostemName() {
		return postemName;
	}
	public void setPostemName(String postemName) {
		this.postemName = postemName;
	}
	public Integer getBartemId() {
		return bartemId;
	}
	public void setBartemId(Integer bartemId) {
		this.bartemId = bartemId;
	}
	public String getBartemName() {
		return bartemName;
	}
	public void setBartemName(String bartemName) {
		this.bartemName = bartemName;
	}
	


	

	
}
