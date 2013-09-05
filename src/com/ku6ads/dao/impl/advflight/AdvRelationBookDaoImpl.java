package com.ku6ads.dao.impl.advflight;

import java.util.HashMap;
import java.util.List;

import com.ku6ads.dao.base.BaseAbstractDao;
import com.ku6ads.dao.entity.advflight.AdvRelationBook;
import com.ku6ads.dao.entity.advflight.Advertisement;
import com.ku6ads.dao.iface.advflight.AdvRelationBookDao;
import com.ku6ads.struts.advflight.AdvRelationBookForm;
import com.ku6ads.struts.advflight.BookForm;

public class AdvRelationBookDaoImpl extends BaseAbstractDao implements AdvRelationBookDao {

	@Override
	public void deleteByAdvertisementId(int advertisementId) {
		getSqlMapClientTemplate().delete("advflight.AdvRelationBook.deleteByAdvertisementId", advertisementId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AdvRelationBook> selectByProperty(AdvRelationBook advRelationBook) {
		
		return getSqlMapClientTemplate().queryForList("advflight.AdvRelationBook.selectByProperty", advRelationBook);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Advertisement> selectAdvflightRelationAdv(int bookId) {
		return getSqlMapClientTemplate().queryForList("advflight.AdvRelationBook.selectAdvflightRelationAdv", bookId);
	}

	@Override
	public List<AdvRelationBookForm> selectRelationProduct(HashMap paramMap) {
		return getSqlMapClientTemplate().queryForList("advflight.AdvRelationBook.selectRelationProduct", paramMap);
	}

	@Override
	public List<BookForm> selectRelationProductBooks(HashMap paramMap) {
		return getSqlMapClientTemplate().queryForList("advflight.AdvRelationBook.selectRelationProductBooks", paramMap);
	}

}
