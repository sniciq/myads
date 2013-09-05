package com.ku6ads.dao.impl.webdata;

import com.ku6ads.dao.base.BaseAbstractDao;
import com.ku6ads.dao.entity.webdata.WebdataMovies;
import com.ku6ads.dao.iface.webdata.WebdataMoviesDao;

public class WebdataMoviesDaoImpl extends BaseAbstractDao implements WebdataMoviesDao {

	/**
	 * 根据SourceId将相应数据删除
	 * @param id
	 */
	public void deleteBySourceId(Integer sourceId) {
		getSqlMapClientTemplate().delete("default.webdata.WebdataMovies.deleteBySourceId",sourceId);
	}
	
	/**
	 * 根据SourceId将相应数据更新
	 * @param id
	 */
	public void updateBySourceId(WebdataMovies webdataMovies){
		getSqlMapClientTemplate().update("default.webdata.WebdataMovies.updateBySourceId",webdataMovies);
	}

}
