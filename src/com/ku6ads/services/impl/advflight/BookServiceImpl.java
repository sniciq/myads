package com.ku6ads.services.impl.advflight;

import java.util.List;
import java.util.Map;

import com.ku6ads.dao.entity.advflight.Book;
import com.ku6ads.dao.entity.advflight.Project;
import com.ku6ads.dao.iface.advflight.BookDao;
import com.ku6ads.services.base.BaseAbstractService;
import com.ku6ads.services.iface.advflight.BookService;

public class BookServiceImpl extends BaseAbstractService implements BookService {

	public List<Book> selectByProjectId(Book book) {
		return ((BookDao)getBaseDao()).selectByProjectId(book);
	}

	public Integer selectPreviewBookLimitCount(Integer projectId) {
		return ((BookDao)getBaseDao()).selectPreviewBookLimitCount(projectId);
	}

	public List selectPreviewBookLimit(Book book) {
		return ((BookDao)getBaseDao()).selectPreviewBookLimit(book);
	}

	public List<Book> selectPreviewBookPoint(Integer projectId) {
		return ((BookDao)getBaseDao()).selectPreviewBookPoint(projectId);
	}

	@Override
	public Map getProjectAllPrice(Integer projectId) {
		return ((BookDao)getBaseDao()).getProjectAllPrice(projectId);
	}

	@Override
	public Double getPSPrice(Integer projectId) {
		return ((BookDao)getBaseDao()).getPSPrice(projectId);
	}

	@Override
	public Map getProjectPeriodsInfo(Integer projectId) {
		return ((BookDao)getBaseDao()).getProjectPeriodsInfo(projectId);
	}

	@Override
	public Project selectProjectById(Integer projectId) {
		return ((BookDao)getBaseDao()).selectProjectById(projectId);
	}
}
