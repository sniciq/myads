package com.ku6ads.dao.entity.advert;

import java.util.Date;

import com.ku6ads.dao.entity.ExtEntity;
/**
 * 
 * @author liujunshi
 *
 */
public class Positionsize extends ExtEntity {

	private Integer id;
	private String name ;
	private Integer width;
	private Integer high;
	private Integer type;
	private String note;
	
	public static final int POSITIONSIZE_TYPE = 0;
	public static final int BARSIZE_TYPE = 1;
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
	public Integer getWidth() {
		return width;
	}
	public void setWidth(Integer width) {
		this.width = width;
	}
	public Integer getHigh() {
		return high;
	}
	public void setHigh(Integer high) {
		this.high = high;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
}
