package com.ku6ads.dao.iface.webdata;

import com.ku6ads.dao.base.BaseDao;
import com.ku6ads.dao.entity.webdata.WebdataMovies;

public interface WebdataMoviesDao extends BaseDao {
	/**
	 * 根据SourceId将相应数据删除
	 * @param id
	 */
	public void deleteBySourceId(Integer sourceId);
	
	/**
	 * 根据SourceId将相应数据更新
	 * @param id
	 */
	public void updateBySourceId(WebdataMovies webdataMovies);
	
}
