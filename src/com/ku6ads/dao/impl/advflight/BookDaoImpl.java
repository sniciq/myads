package com.ku6ads.dao.impl.advflight;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ku6ads.dao.base.BaseAbstractDao;
import com.ku6ads.dao.entity.advflight.Book;
import com.ku6ads.dao.entity.advflight.Project;
import com.ku6ads.dao.iface.advflight.BookDao;
import com.ku6ads.struts.advflight.AdvRelationBookForm;
import com.ku6ads.struts.advflight.BookForm;

public class BookDaoImpl extends BaseAbstractDao implements BookDao {

	public List<Book> selectByProjectId(Book book) {
		return getSqlMapClientTemplate().queryForList("advflight.book.selectByEntity",book);
	}
	
	public void deleteBooksNotInIds(Map<String, Object> paraMap) {
		getSqlMapClientTemplate().delete("advflight.book.deleteBooksNotInIds", paraMap);
	}

	public void deleteByBookPackageId(Integer bookPackageId) {
		getSqlMapClientTemplate().delete("advflight.book.deleteByBookPackageId", bookPackageId);
	}

	public List selectPreviewBookLimit(Book book) {
		return this.getSqlMapClientTemplate().queryForList("advflight.book.selectPreviewBookLimit", book);
	}

	public Integer selectPreviewBookLimitCount(Integer projectId) {
		return (Integer) getSqlMapClientTemplate().queryForObject("advflight.book.selectPreviewBookLimitCount", projectId);
	}

	public List selectPreviewBookPoint(Integer projectId) {
		return this.getSqlMapClientTemplate().queryForList("advflight.book.selectPreviewBookPoint", projectId);
	}
	
	public void updateByProjectId(Book book){
		getSqlMapClientTemplate().update("advflight.book.updateByProjectId",book);
	}
	
	public void update(Book book){
		getSqlMapClientTemplate().update("advflight.book.updateById",book);
	}

	public List<Book> selectAdvbarMonthBookedList(Book book) {
		return this.getSqlMapClientTemplate().queryForList("advflight.book.selectAdvbarMonthBookedList", book);
	}

	@Override
	public Map getProjectAllPrice(Integer projectId) {
		return (Map) getSqlMapClientTemplate().queryForObject("advflight.book.getProjectAllPrice", projectId);
	}

	@Override
	public Double getPSPrice(Integer projectId) {
		return (Double) getSqlMapClientTemplate().queryForObject("advflight.book.getPSPrice", projectId);
	}

	@Override
	public List<BookForm> getRelationBooks(Map paramMap) {
		return this.getSqlMapClientTemplate().queryForList("advflight.book.getRelationBooks", paramMap);
	}

	@Override
	public List<AdvRelationBookForm> getRelationBookPackages(HashMap paramMap) {
		return this.getSqlMapClientTemplate().queryForList("advflight.book.getRelationBookPackages", paramMap);
	}

	@Override
	public Integer selectExecutedBookCount(int bookPackageId) {
		return (Integer) this.getSqlMapClientTemplate().queryForObject("advflight.book.selectExecutedBookCount", bookPackageId);
	}

	@Override
	public List<Book> selectByBookPackageId(int bookPackageId) {
		return this.getSqlMapClientTemplate().queryForList("advflight.book.selectByBookPackageId", bookPackageId);
	}

	@Override
	public Map getProjectPeriodsInfo(Integer projectId) {
		return (Map) getSqlMapClientTemplate().queryForObject("advflight.book.getProjectPeriodsInfo", projectId);
	}

	@Override
	public Project selectProjectById(Integer projectId) {
		return (Project) getSqlMapClientTemplate().queryForObject("advflight.book.selectProjectById", projectId);
	}

	@Override
	public List<Book> selectBookPointList(Book book) {
		return getSqlMapClientTemplate().queryForList("advflight.book.selectBookPointList", book);
	}

	@Override
	public List<Book> selectBookPointListAvg(Book book) {
		return getSqlMapClientTemplate().queryForList("advflight.book.selectBookPointListAvg", book);
	}

	@Override
	public List<Book> selectBookPointListSum(Book book) {
		return getSqlMapClientTemplate().queryForList("advflight.book.selectBookPointListSum", book);
	}

}
