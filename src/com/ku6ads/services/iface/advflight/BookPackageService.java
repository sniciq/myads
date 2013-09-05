package com.ku6ads.services.iface.advflight;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ku6ads.dao.entity.advflight.Book;
import com.ku6ads.dao.entity.advflight.BookPackage;
import com.ku6ads.dao.entity.advflight.Project;
import com.ku6ads.services.base.BaseServiceIface;

public interface BookPackageService extends BaseServiceIface {
	
	/**
	 * 根据排期包ID查找到相应排期包
	 * @param bookPackage
	 * @return
	 */
	public List<BookPackage> selectByProjectId(BookPackage bookPackage);
	
	/**
	 * 对排期包进行更新
	 * @param bookPackage
	 */
	public void update(BookPackage bookPackage);

	/**
	 * 删除排期包，同时删除排期包中的排期
	 * @param bookPackageId 排期包ID
	 * @return 结果，包含result和info
	 * @throws Exception
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public Map<String, Object> deleteBookPackage(int bookPackageId, int projectId) throws Exception;
	
	@Transactional(propagation=Propagation.REQUIRED)
	public Map<String, Object> deleteBookPackage(int bookPackageId, Project projectEty) throws Exception;
	
	/**
	 * 更新排期表的流量数据等
	 * @param bookPackageId
	 * @param barId
	 */
	public void updateookPackageState(int bookPackageId, int barId, int priceId) throws Exception;
	
	
	public Integer selectBookPackageById(int bookPackageId);
	
	/**
	 * 申请点位<br>
	 * @param bookPackage 排期包信息
	 * @param bookList 点位列表
	 * @throws Exception
	 */
	public void applyBookPoint(BookPackage bookPackage, List<Book> bookList) throws Exception;
	
	/**
	 * 释放排期所的所有点位资源
	 * @param bookPackageId
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public void releaseBookPoint(int bookPackageId) throws Exception;
	
	@Transactional(propagation=Propagation.REQUIRED)
	public Map<String, Object> updateBookPackage(BookPackage bookPackage, JSONArray pointDataArray) throws Exception;
	
	/**
	 * 
	 * @param bookPackage
	 * @return
	 * @throws Exception
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public Map<String, Object> updateBookPackagePriority(BookPackage bookPackage) throws Exception;
	
	@Transactional(propagation=Propagation.REQUIRED)
	public Map<String, Object> saveBookPackage(BookPackage bookPackage, JSONArray pointDataArray) throws Exception;

	/**
	 * 查询关联背景排期包数
	 * @param bookPackage
	 * @return
	 */
	public int selectRelationBackgroudBookPkgLimitCount(BookPackage bookPackage);

	/**
	 * 查询关联背景排期包
	 * @param bookPackage
	 * @return
	 */
	public List<BookPackage> selectRelationBackgroudBookPkgLimit(BookPackage bookPackage);

}
