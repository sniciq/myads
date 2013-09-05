package com.ku6ads.dao.impl.advflight;

import java.util.List;

import com.ku6ads.dao.base.BaseAbstractDao;
import com.ku6ads.dao.entity.advflight.FutureFlight;
import com.ku6ads.dao.iface.advflight.FutureFlightDao;

public class FutureFlightDaoImpl extends BaseAbstractDao implements FutureFlightDao {

	public List<FutureFlight> selectAdvbarFutureFlight(FutureFlight futureFlight) {
		return getSqlMapClientTemplate().queryForList("advflight.futureflight.selectAdvbarFutureFlight", futureFlight);
	}

	@Override
	public void deleteToday() {
		getSqlMapClientTemplate().delete("advflight.futureflight.deleteToday");
	}

	@Override
	public void updateByAdvbarId(FutureFlight futureFlight) {
		getSqlMapClientTemplate().update("advflight.futureflight.updateByAdvbarId", futureFlight);
	}

}
