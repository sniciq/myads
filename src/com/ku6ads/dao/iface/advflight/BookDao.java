package com.ku6ads.dao.iface.advflight;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ku6ads.dao.base.BaseDao;
import com.ku6ads.dao.entity.advflight.Book;
import com.ku6ads.dao.entity.advflight.Project;
import com.ku6ads.struts.advflight.AdvRelationBookForm;
import com.ku6ads.struts.advflight.BookForm;

public interface BookDao extends BaseDao {
	
	public void deleteBooksNotInIds(Map<String, Object> paraMap);
	
	public void deleteByBookPackageId(Integer bookPackageId);
	
	/**
	 * 根据排期ID查找到相应排期
	 * @param book 
	 * @return 
	 */
	public List<Book> selectByProjectId(Book book);
	
	public Integer selectPreviewBookLimitCount(Integer projectId);
	
	public List selectPreviewBookLimit(Book book);
	
	public List selectPreviewBookPoint(Integer projectId);

	public void update(Book book);
	
	public void updateByProjectId(Book book);
	
	/**
	 * 取当月每天已经订了的CPM量
	 * @param book
	 * @return
	 */
	public List<Book> selectAdvbarMonthBookedList(Book book);

	public Map getProjectAllPrice(Integer projectId);

	public Double getPSPrice(Integer projectId);

	public List<BookForm> getRelationBooks(Map paramMap);

	public List<AdvRelationBookForm> getRelationBookPackages(HashMap paramMap);

	public Integer selectExecutedBookCount(int bookPackageId);

	public List<Book> selectByBookPackageId(int bookPackageId);

	public Map getProjectPeriodsInfo(Integer projectId);

	public Project selectProjectById(Integer projectId);

	public List<Book> selectBookPointList(Book book);
	
	public List<Book> selectBookPointListSum(Book book);
	
	public List<Book> selectBookPointListAvg(Book book);
	
}
