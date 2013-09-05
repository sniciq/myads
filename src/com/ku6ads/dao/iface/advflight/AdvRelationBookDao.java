package com.ku6ads.dao.iface.advflight;

import java.util.HashMap;
import java.util.List;

import com.ku6ads.dao.base.BaseDao;
import com.ku6ads.dao.entity.advflight.AdvRelationBook;
import com.ku6ads.dao.entity.advflight.Advertisement;
import com.ku6ads.struts.advflight.AdvRelationBookForm;
import com.ku6ads.struts.advflight.BookForm;

public interface AdvRelationBookDao extends BaseDao {

	void deleteByAdvertisementId(int advertisementId);
	/**
	 * 根据属性查询
	 * @param advRelationBook
	 * @return
	 */
	public List<AdvRelationBook> selectByProperty(AdvRelationBook advRelationBook);

	/**
	 * 根据排期id查询排期关联广告
	 * @param bookId
	 * @return List<Advertisement>
	 */
	public List<Advertisement> selectAdvflightRelationAdv(int bookId);
	
	
	List<AdvRelationBookForm> selectRelationProduct(HashMap paramMap);
	
	
	List<BookForm> selectRelationProductBooks(HashMap paramMap);
}
