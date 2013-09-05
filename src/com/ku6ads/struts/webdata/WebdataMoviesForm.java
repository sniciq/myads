package com.ku6ads.struts.webdata;

import java.util.Date;

import com.ku6ads.dao.entity.ExtEntity;

public class WebdataMoviesForm extends ExtEntity {
	private String displayId;
	private Integer id;
	private String name;
	private String type;
	private String fitScene;
	private String classType;
	private String productionPlace;
	private String level;
	private String playPlan;
	private String sellLength;
	private String director;
	private String mainActor;
	private java.lang.Boolean exclusive;
	private String expectedFlow;
	private String sellState;
	private String introduction;
	private String canSell;
	
	private String startTime;
	private String endTime;
	private String buyType;
	private String customerName;
	
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getBuyType() {
		return buyType;
	}
	public void setBuyType(String buyType) {
		this.buyType = buyType;
	}
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getFitScene() {
		return fitScene;
	}
	public void setFitScene(String fitScene) {
		this.fitScene = fitScene;
	}
	public String getClassType() {
		return classType;
	}
	public void setClassType(String classType) {
		this.classType = classType;
	}
	public String getProductionPlace() {
		return productionPlace;
	}
	public void setProductionPlace(String productionPlace) {
		this.productionPlace = productionPlace;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getPlayPlan() {
		return playPlan;
	}
	public void setPlayPlan(String playPlan) {
		this.playPlan = playPlan;
	}
	public String getSellLength() {
		return sellLength;
	}
	public void setSellLength(String sellLength) {
		this.sellLength = sellLength;
	}
	public String getDirector() {
		return director;
	}
	public void setDirector(String director) {
		this.director = director;
	}
	public String getMainActor() {
		return mainActor;
	}
	public void setMainActor(String mainActor) {
		this.mainActor = mainActor;
	}
	public java.lang.Boolean getExclusive() {
		return exclusive;
	}
	public void setExclusive(java.lang.Boolean exclusive) {
		this.exclusive = exclusive;
	}
	public String getExpectedFlow() {
		return expectedFlow;
	}
	public void setExpectedFlow(String expectedFlow) {
		this.expectedFlow = expectedFlow;
	}
	public String getSellState() {
		return sellState;
	}
	public void setSellState(String sellState) {
		this.sellState = sellState;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	public String getCanSell() {
		return canSell;
	}
	public void setCanSell(String canSell) {
		this.canSell = canSell;
	}
	public String getDisplayId() {
		return displayId;
	}
	public void setDisplayId(String displayId) {
		this.displayId = displayId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
}
