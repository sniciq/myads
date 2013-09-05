package com.ku6ads.dao.impl.advflight;

import java.util.List;

import com.ku6ads.dao.base.BaseAbstractDao;
import com.ku6ads.dao.entity.advflight.ForecastAdvbarEty;
import com.ku6ads.dao.iface.advflight.FlowForecastDao;

public class FlowForecastDaoImpl extends BaseAbstractDao implements FlowForecastDao {

	@Override
	public List<ForecastAdvbarEty> selectAllAdvbar() {
		return getSqlMapClientTemplate().queryForList("advflight.FlowForecast.selectAllAdvbar");
	}

}
