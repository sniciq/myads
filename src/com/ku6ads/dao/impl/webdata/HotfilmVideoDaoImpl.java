package com.ku6ads.dao.impl.webdata;

import java.util.Date;
import java.util.List;

import com.ku6ads.dao.base.BaseAbstractDao;
import com.ku6ads.dao.iface.webdata.HotfilmVideoDao;

public class HotfilmVideoDaoImpl extends BaseAbstractDao implements HotfilmVideoDao {

	public List selectStateVideos() {
		return getSqlMapClientTemplate().queryForList("default.HotfilmVideo.selectStateVideos");
	}

	public Date selectSysDate() {
		return (Date) getSqlMapClientTemplate().queryForObject("default.HotfilmVideo.selectSysDate");
	}

	public Integer deleteByFilmId(Integer filmId) {
		return (Integer) getSqlMapClientTemplate().delete("default.HotfilmVideo.deleteByFilmId", filmId);
	}
	
	
}
