package com.ku6ads.dao.entity.advflight;

public class AdvRelationBook {
	private Integer id;
	private Integer advertisementId;
	private Integer bartemplateId;
	private Integer bookId;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getAdvertisementId() {
		return advertisementId;
	}
	public void setAdvertisementId(Integer advertisementId) {
		this.advertisementId = advertisementId;
	}
	public Integer getBartemplateId() {
		return bartemplateId;
	}
	public void setBartemplateId(Integer bartemplateId) {
		this.bartemplateId = bartemplateId;
	}
	public Integer getBookId() {
		return bookId;
	}
	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}
}
