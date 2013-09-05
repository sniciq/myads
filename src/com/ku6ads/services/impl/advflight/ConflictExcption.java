package com.ku6ads.services.impl.advflight;

import java.util.Map;

public class ConflictExcption extends RuntimeException {
	
	private static final long serialVersionUID = -2389376856384355423L;
	Map<String, String> conflictMap;
	public ConflictExcption(String message) {
		super(message);
	}
	public Map<String, String> getConflictMap() {
		return conflictMap;
	}
	public void setConflictMap(Map<String, String> conflictMap) {
		this.conflictMap = conflictMap;
	}
}
