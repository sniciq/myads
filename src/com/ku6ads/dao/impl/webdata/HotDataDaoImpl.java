package com.ku6ads.dao.impl.webdata;

import java.util.List;

import com.ku6ads.dao.base.BaseAbstractDao;
import com.ku6ads.dao.iface.webdata.HotDataDao;
import com.ku6ads.struts.webdata.HotFilmData;
import com.ku6ads.struts.webdata.ProgramData;

public class HotDataDaoImpl extends BaseAbstractDao implements HotDataDao {

	public List selectProgramLimit(ProgramData programData) {
		return getSqlMapClientTemplate().queryForList("default.webdata.hotdata.selectProgramLimit", programData);
	}

	public Integer selectProgramLimitCount(ProgramData programData) {
		return (Integer) getSqlMapClientTemplate().queryForObject("default.webdata.hotdata.selectProgramLimitCount", programData);
	}

	public List<HotFilmData> searchEveryDayData(HotFilmData hotFilmData) {
		return getSqlMapClientTemplate().queryForList("default.webdata.hotdata.searchEveryDayData", hotFilmData);
	}

	public Integer searchEveryDayDataCount(HotFilmData hotFilmData) {
		return (Integer) getSqlMapClientTemplate().queryForObject("default.webdata.hotdata.searchEveryDayDataCount", hotFilmData);
	}

	public List<HotFilmData> searchAvgData(HotFilmData hotFilmData) {
		return getSqlMapClientTemplate().queryForList("default.webdata.hotdata.searchAvgData", hotFilmData);
	}

	public Integer searchAvgDataCount(HotFilmData hotFilmData) {
		return (Integer) getSqlMapClientTemplate().queryForObject("default.webdata.hotdata.searchAvgDataCount", hotFilmData);
	}

	public List<HotFilmData> searchAvgVVData(HotFilmData hotFilmData) {
		return getSqlMapClientTemplate().queryForList("default.webdata.hotdata.searchAvgVVData", hotFilmData);
	}

	public Integer searchAvgVVDataCount(HotFilmData hotFilmData) {
		return (Integer) getSqlMapClientTemplate().queryForObject("default.webdata.hotdata.searchAvgVVDataCount", hotFilmData);
	}

	public List<HotFilmData> searchEveryDayVVData(HotFilmData hotFilmData) {
		return getSqlMapClientTemplate().queryForList("default.webdata.hotdata.searchEveryDayVVData", hotFilmData);
	}

	public Integer searchEveryDayVVDataCount(HotFilmData hotFilmData) {
		return (Integer) getSqlMapClientTemplate().queryForObject("default.webdata.hotdata.searchEveryDayVVDataCount", hotFilmData);
	}

}
