package com.ku6ads.dao.impl.advflight;

import java.util.Map;

import com.ku6ads.dao.base.BaseAbstractDao;
import com.ku6ads.dao.entity.advflight.AdvbarBooked;
import com.ku6ads.dao.entity.advflight.BookPackage;
import com.ku6ads.dao.iface.advflight.AdvbarBookedDao;

public class AdvbarBookedDaoImpl extends BaseAbstractDao implements AdvbarBookedDao{

	public int selectSaleTypeBookedCount(AdvbarBooked bookedEty) {
		return (Integer)getSqlMapClientTemplate().queryForObject("default.advbarbooked.selectCPDBookedCount", bookedEty);
	}

	public int selectIsSaledByOtherType(AdvbarBooked bookedEty) {
		return (Integer)getSqlMapClientTemplate().queryForObject("default.advbarbooked.selectOtherTypeBookedCount", bookedEty);
	}

	public void releaseDeleteBooks(Map paramMap) {
		getSqlMapClientTemplate().update("default.advbarbooked.releaseDeleteBooks", paramMap);
	}

	@Override
	public void releaseBookPackage(BookPackage bookPackage) {
		getSqlMapClientTemplate().update("default.advbarbooked.releaseBookPackage", bookPackage);
	}
}
