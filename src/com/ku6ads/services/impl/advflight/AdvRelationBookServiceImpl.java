package com.ku6ads.services.impl.advflight;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ku6ads.dao.entity.advflight.AdvRelationBook;
import com.ku6ads.dao.entity.advflight.Advertisement;
import com.ku6ads.dao.iface.advflight.AdvRelationBookDao;
import com.ku6ads.dao.iface.advflight.BookDao;
import com.ku6ads.services.iface.advflight.AdvRelationBookService;
import com.ku6ads.struts.advflight.AdvRelationBookForm;
import com.ku6ads.struts.advflight.BookForm;


public class AdvRelationBookServiceImpl implements AdvRelationBookService {

	private BookDao bookDao;
	private AdvRelationBookDao advRelationBookDao;
	
	@Override
	public List<BookForm> getRelationBooks(Map paramMap) {
		return bookDao.getRelationBooks(paramMap);
	}
	
	@Override
	public List<AdvRelationBookForm> getRelationBookPackages(HashMap paramMap) {
		return bookDao.getRelationBookPackages(paramMap);
	}
	
	@Override
	public void save(int advertisementId, int bartemplateId, String[] bookIds) {
		advRelationBookDao.deleteByAdvertisementId(advertisementId);
		
		for(int i = 0; i < bookIds.length; i++) {
			AdvRelationBook ety = new AdvRelationBook();
			ety.setAdvertisementId(advertisementId);
			ety.setBartemplateId(bartemplateId);
			ety.setBookId(Integer.parseInt(bookIds[i]));
			advRelationBookDao.insert(ety);
		}
	}

	
	@Override
	public List<AdvRelationBook> selectByProperty(AdvRelationBook advRelationBook) {
		
		return advRelationBookDao.selectByProperty(advRelationBook);
	}
	
	@Override
	public List<Advertisement> selectAdvflightRelationAdv(int bookId) {
		return advRelationBookDao.selectAdvflightRelationAdv(bookId);
	}
	
	public BookDao getBookDao() {
		return bookDao;
	}

	public void setBookDao(BookDao bookDao) {
		this.bookDao = bookDao;
	}

	public AdvRelationBookDao getAdvRelationBookDao() {
		return advRelationBookDao;
	}

	public void setAdvRelationBookDao(AdvRelationBookDao advRelationBookDao) {
		this.advRelationBookDao = advRelationBookDao;
	}
	
}
