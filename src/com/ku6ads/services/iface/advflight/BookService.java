package com.ku6ads.services.iface.advflight;

import java.util.List;
import java.util.Map;

import com.ku6ads.dao.entity.advflight.Book;
import com.ku6ads.dao.entity.advflight.Project;
import com.ku6ads.services.base.BaseServiceIface;
import com.ku6ads.struts.advflight.BookForm;


public interface BookService extends BaseServiceIface {
	
	/**
	 * 根据排期ID查找到相应排期
	 */
	public List<Book> selectByProjectId(Book book);
	
	public Integer selectPreviewBookLimitCount(Integer projectId);
	
	public List<BookForm> selectPreviewBookLimit(Book book);
	
	public List<Book> selectPreviewBookPoint(Integer projectId);

	public Map getProjectAllPrice(Integer projectId);

	public Double getPSPrice(Integer projectId);
	
	public Map getProjectPeriodsInfo(Integer projectId);

	public Project selectProjectById(Integer projectId);

}
