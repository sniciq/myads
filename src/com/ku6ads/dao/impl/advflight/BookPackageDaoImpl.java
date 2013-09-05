package com.ku6ads.dao.impl.advflight;

import java.util.List;
import java.util.Map;

import com.ku6ads.dao.base.BaseAbstractDao;
import com.ku6ads.dao.entity.advflight.BookPackage;
import com.ku6ads.dao.iface.advflight.BookPackageDao;
import com.ku6ads.struts.book.BookPackageAdvProductForm;
/**
 * 排期包Service
 * @author chenshaofeng
 *
 */
public class BookPackageDaoImpl extends BaseAbstractDao implements BookPackageDao {
	/**
	 * 根据排期包ID查找到相应排期包
	 */
	public List<BookPackage> selectByProjectId(BookPackage bookPackage) {
		return getSqlMapClientTemplate().queryForList("advflight.bookpackage.selectByEntity",bookPackage);
	}
	/**
	 * 对排期包进行更新
	 */
	public void updateByProjectId(BookPackage bookPackage) {
		getSqlMapClientTemplate().update("advflight.bookpackage.updateByProjectId",bookPackage);
	}

	public void update(BookPackage bookPackage) {
		getSqlMapClientTemplate().update("advflight.bookpackage.updateById",bookPackage);
	}
	
	public void updateRunTimeRarge(Integer bookPackageId) {
		getSqlMapClientTemplate().update("advflight.bookpackage.updateRunTimeRarge", bookPackageId);
	}
	
	@Override
	public void updateBookPackageState(Map paramMap) {
		getSqlMapClientTemplate().update("advflight.bookpackage.updateBookPackageState", paramMap);
	}
	
	@Override
	public List<BookPackage> selectRelationBackgroudBookPkgLimit(BookPackage bookPackage) {
		return getSqlMapClientTemplate().queryForList("advflight.bookpackage.selectRelationBackgroudBookPkgLimit",bookPackage);
	}
	
	@Override
	public Integer selectRelationBackgroudBookPkgLimitCount(BookPackage bookPackage) {
		return (Integer) getSqlMapClientTemplate().queryForObject("advflight.bookpackage.selectRelationBackgroudBookPkgLimitCount", bookPackage);
	}
	
	@Override
	public BookPackageAdvProductForm selectBookPackageAdvProduct(int bookPackageId) {
		return (BookPackageAdvProductForm) getSqlMapClientTemplate().queryForObject("advflight.bookpackage.selectBookPackageAdvProduct", bookPackageId);
	}
	
	@Override
	public List selectBookPackageAdvbarByLimit(BookPackage bookPEty) {
		return getSqlMapClientTemplate().queryForList("advflight.bookpackage.selectBookPackageAdvbarByLimit",bookPEty);
	}
	
	@Override
	public Integer selectBookPackageAdvbarLimitCount(BookPackage bookPEty) {
		return (Integer) getSqlMapClientTemplate().queryForObject("advflight.bookpackage.selectBookPackageAdvbarLimitCount", bookPEty);
	}
}
