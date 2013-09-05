package com.ku6ads.services.iface.advflight;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ku6ads.dao.entity.advflight.AdvRelationBook;
import com.ku6ads.dao.entity.advflight.Advertisement;
import com.ku6ads.struts.advflight.AdvRelationBookForm;
import com.ku6ads.struts.advflight.BookForm;

public interface AdvRelationBookService {
	public List<BookForm> getRelationBooks(Map paramMap);
	
	@Transactional(propagation=Propagation.REQUIRED)
	public void save(int advertisementId, int bartemplateId, String[] bookIds);
	
	public List<AdvRelationBook> selectByProperty(AdvRelationBook advRelationBook);
	
	public List<AdvRelationBookForm> getRelationBookPackages(HashMap paramMap);
	
	public List<Advertisement> selectAdvflightRelationAdv(int bookId);
}
