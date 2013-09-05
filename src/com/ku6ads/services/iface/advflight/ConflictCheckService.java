package com.ku6ads.services.iface.advflight;

import java.util.Map;

import com.ku6ads.dao.entity.advflight.Book;

public interface ConflictCheckService {
	
	/**
	 * 检查排期是否有冲突
	 * @param book 需要检查的排期
	 * @param avlFlightNum 排期变化量
	 * @return Map; result: true/false, ConflictInfo: hourConflict/areaConflict/noLastContent, ConflictDetail: , 
	 */
	public Map<String, String> checkBook(Book book, int avlFlightNum);
}
