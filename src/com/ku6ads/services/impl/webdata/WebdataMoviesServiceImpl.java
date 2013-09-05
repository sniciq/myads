package com.ku6ads.services.impl.webdata;

import com.ku6ads.dao.iface.webdata.WebdataMoviesDao;
import com.ku6ads.services.base.BaseAbstractService;
import com.ku6ads.services.iface.webdata.WebdataMoviesService;
import com.ku6ads.dao.entity.webdata.WebdataMovies;

public class WebdataMoviesServiceImpl extends BaseAbstractService implements WebdataMoviesService {

	/**
	 * 根据SourceId将相应数据删除
	 * @param id
	 */
	public void deleteBySourceId(Integer sourceId) {
		((WebdataMoviesDao)getBaseDao()).deleteBySourceId(sourceId);
	}
	
	/**
	 * 根据SourceId将相应数据更新
	 * @param id
	 */
	public void updateBySourceId(WebdataMovies webdataMovies){
		((WebdataMoviesDao)getBaseDao()).updateBySourceId(webdataMovies);
	}
}
