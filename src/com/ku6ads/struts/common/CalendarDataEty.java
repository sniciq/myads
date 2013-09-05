package com.ku6ads.struts.common;

public class CalendarDataEty {
	
	private String text;
	private Integer day = 0;//日期
	private Integer weekday;//周几
	
	private Integer allSize = 5000;//总量
	private Integer lastSize = 100;//余量
	private boolean canSelect = true;//是否可选择
	private boolean today = false;//是否是今天(当前天)
	private boolean select = false;
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Integer getDay() {
		return day;
	}
	public void setDay(Integer day) {
		this.day = day;
	}
	public Integer getAllSize() {
		return allSize;
	}
	public void setAllSize(Integer allSize) {
		this.allSize = allSize;
	}
	public Integer getLastSize() {
		return lastSize;
	}
	public void setLastSize(Integer lastSize) {
		this.lastSize = lastSize;
	}
	public boolean isCanSelect() {
		return canSelect;
	}
	public void setCanSelect(boolean canSelect) {
		this.canSelect = canSelect;
	}
	public boolean isToday() {
		return today;
	}
	public void setToday(boolean today) {
		this.today = today;
	}
	public boolean isSelect() {
		return select;
	}
	public void setSelect(boolean select) {
		this.select = select;
	}
	public Integer getWeekday() {
		return weekday;
	}
	public void setWeekday(Integer weekday) {
		this.weekday = weekday;
	}
	
}
