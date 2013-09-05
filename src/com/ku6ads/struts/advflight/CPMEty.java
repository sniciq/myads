package com.ku6ads.struts.advflight;

import java.util.Date;

public class CPMEty {
	public static String contentStatus_full = "full";
	public static String contentStatus_part = "part";
	public static String contentStatus_none = "empty";
	
//	预估量(t_futureflight)*网站系数*广告条系数(t_bar_proportion 跟时间有关系)*0.9 - 已经定了的量(t_book表中同一天已经定的量)
	
	private Integer advbarId = 0;
	private Date date = null;
	private int futureFlight = 0;
	private double siteProp = 1;
	private double barProportion = 1.0;
	private int alreadyFlight = 0;
	private int lastContent = -1;
	private String contentStatus = "empty";//剩余状态:full, part, none
	
	/**
	 * 得到广告条余量
	 * @return
	 */
	public double getLastFlight() {
		if(lastContent != -1 )//CPD形式下
			return lastContent;
		return (futureFlight * siteProp * barProportion * 0.9 - alreadyFlight) / 1000;
	}
	
	public int getFutureFlight() {
		return futureFlight;
	}
	public void setFutureFlight(int futureFlight) {
		this.futureFlight = futureFlight;
	}
	public double getSiteProp() {
		return siteProp;
	}
	public void setSiteProp(double siteProp) {
		this.siteProp = siteProp;
	}

	public double getBarProportion() {
		return barProportion;
	}
	public void setBarProportion(double barProportion) {
		this.barProportion = barProportion;
	}
	public int getAlreadyFlight() {
		return alreadyFlight;
	}
	public void setAlreadyFlight(int alreadyFlight) {
		this.alreadyFlight = alreadyFlight;
	}

	public Integer getAdvbarId() {
		return advbarId;
	}

	public void setAdvbarId(Integer advbarId) {
		this.advbarId = advbarId;
	}
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getLastContent() {
		return lastContent;
	}

	public void setLastContent(int lastContent) {
		this.lastContent = lastContent;
	}

	public String getContentStatus() {
		return contentStatus;
	}

	public void setContentStatus(String contentStatus) {
		this.contentStatus = contentStatus;
	}
}
