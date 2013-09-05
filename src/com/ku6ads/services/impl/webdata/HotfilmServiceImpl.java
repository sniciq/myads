package com.ku6ads.services.impl.webdata;

import com.ku6ads.dao.iface.webdata.HotfilmVideoDao;
import com.ku6ads.services.base.BaseAbstractService;
import com.ku6ads.services.iface.webdata.HotfilmService;

public class HotfilmServiceImpl extends BaseAbstractService implements HotfilmService {

	private HotfilmVideoDao hotfilmVideoDao;
	
	@Override
	public void deleteHotfilm(int htofilmId) {
		getBaseDao().deleteById(htofilmId);
		hotfilmVideoDao.deleteByFilmId(htofilmId);
	}

	public HotfilmVideoDao getHotfilmVideoDao() {
		return hotfilmVideoDao;
	}

	public void setHotfilmVideoDao(HotfilmVideoDao hotfilmVideoDao) {
		this.hotfilmVideoDao = hotfilmVideoDao;
	}
}
