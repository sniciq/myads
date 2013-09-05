package com.ku6ads.dao.impl.webdata;

import com.ku6ads.dao.base.BaseAbstractDao;
import com.ku6ads.dao.entity.webdata.WebdataMoviesMaintain;
import com.ku6ads.dao.iface.webdata.WebdataMoviesMaintainDao;

public class WebdataMoviesMaintainDaoImpl extends BaseAbstractDao implements WebdataMoviesMaintainDao {
	
	@Override
	public Integer selectConflictCount(WebdataMoviesMaintain webdataMovies) {
		 return (Integer) getSqlMapClientTemplate().queryForObject("default.Webdata.WebdataMovies.Maintain.selectConflictCount", webdataMovies);
	}
}
