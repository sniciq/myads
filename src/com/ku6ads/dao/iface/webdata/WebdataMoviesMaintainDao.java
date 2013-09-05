package com.ku6ads.dao.iface.webdata;

import com.ku6ads.dao.base.BaseDao;
import com.ku6ads.dao.entity.webdata.WebdataMoviesMaintain;

public interface WebdataMoviesMaintainDao extends BaseDao {
	
	public Integer selectConflictCount(WebdataMoviesMaintain webdataMovies);

}
