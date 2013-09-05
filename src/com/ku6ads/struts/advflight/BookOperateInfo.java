package com.ku6ads.struts.advflight;

import java.util.Map;

public class BookOperateInfo {
	private boolean success;
	private String result;
	private String info;
	private Map conflictMap;
	
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public Map getConflictMap() {
		return conflictMap;
	}
	public void setConflictMap(Map conflictMap) {
		this.conflictMap = conflictMap;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
}
