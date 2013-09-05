package com.ku6ads.dao.iface.advflight;

import java.util.List;
import java.util.Map;

import com.ku6ads.dao.base.BaseDao;
import com.ku6ads.dao.entity.advflight.BookPackage;
import com.ku6ads.struts.book.BookPackageAdvProductForm;
/**
 * 排期包Dao
 * @author chenshaofeng
 *
 */
public interface BookPackageDao extends BaseDao {

	/**
	 * 根据排期包ID查找到相应排期包
	 */
	public List<BookPackage> selectByProjectId(BookPackage bookPackage);
	
	/**
	 * 对排期包进行更新
	 */
	public void update(BookPackage bookPackage);
	
	public void updateByProjectId(BookPackage bookPackage);

	public void updateRunTimeRarge(Integer bookPackageId);
	
	/**
	 * 更新排期表中的：日均流量（预估量），总流量（预估量*天数），日均点击（刊例点击率），总点击（日均点击*天数），
	 * @param book
	 */
	public void updateBookPackageState(Map paramMap);

	public List<BookPackage> selectRelationBackgroudBookPkgLimit(BookPackage bookPackage);

	public Integer selectRelationBackgroudBookPkgLimitCount(BookPackage bookPackage);

	public BookPackageAdvProductForm selectBookPackageAdvProduct(int bookPackageId);

	public Integer selectBookPackageAdvbarLimitCount(BookPackage bookPEty);

	public List selectBookPackageAdvbarByLimit(BookPackage bookPEty);
}
