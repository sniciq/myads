package com.ku6ads.dao.impl.advflight;

import java.util.List;
import java.util.Map;

import com.ku6ads.dao.base.BaseAbstractDao;
import com.ku6ads.dao.entity.advflight.AdvbarPreBook;
import com.ku6ads.dao.entity.advflight.BookPackage;
import com.ku6ads.dao.iface.advflight.AdvbarPreBookDao;

public class AdvbarPreBookDaoImpl extends BaseAbstractDao implements AdvbarPreBookDao {

	public List<AdvbarPreBook> searchMonthBookedList(AdvbarPreBook searchEty) {
		return getSqlMapClientTemplate().queryForList("default.advbarprebook.searchMonthBookedList", searchEty);
	}

	public void releasePreBooks(Map paramMap) {
		getSqlMapClientTemplate().update("default.advbarprebook.releasePreBooks", paramMap);
	}

	@Override
	public void releaseBookPackagePreBooks(BookPackage bookPackage) {
		getSqlMapClientTemplate().update("default.advbarprebook.releaseBookPackagePreBooks", bookPackage);
	}

}
