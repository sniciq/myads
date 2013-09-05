package com.ku6ads.dao.iface.advflight;

import java.util.List;

import com.ku6ads.dao.base.BaseDao;
import com.ku6ads.dao.entity.advflight.FutureFlight;

public interface FutureFlightDao extends BaseDao {
	
	public List<FutureFlight> selectAdvbarFutureFlight(FutureFlight futureFlight);

	/**
	 * 删除今天的预估值
	 */
	public void deleteToday();
	
	/**
	 * 根据广告条id更新预估值
	 */
	public void updateByAdvbarId(FutureFlight futureFlight);
}
