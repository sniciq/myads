package com.ku6ads.services.iface.advflight;

import java.util.Map;

import net.sf.json.JSONArray;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ku6ads.dao.entity.advflight.BookPackage;

public interface BookAdvproductService {

	@Transactional(propagation=Propagation.REQUIRED)
	public Map<String, Object> saveBookPackage(BookPackage bookPackage, JSONArray pointDataArray) throws Exception;

	@Transactional(propagation=Propagation.REQUIRED)
	public Map<String, Object> updateBookPackage(BookPackage bookPackage, JSONArray pointDataArray) throws Exception;
	
	@Transactional(propagation=Propagation.REQUIRED)
	public Map<String, Object> updateBookPackagePriority(BookPackage bookPackage) throws Exception;
	
}
