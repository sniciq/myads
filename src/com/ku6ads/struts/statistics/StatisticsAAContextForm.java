package com.ku6ads.struts.statistics;

import com.ku6ads.dao.entity.statistics.StatisticsAAContext;

public class StatisticsAAContextForm extends StatisticsAAContext {
	
	public String startTime;
	public String endTime;
	
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
	
}
