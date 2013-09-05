package com.ku6ads.services.iface.webdata;

import com.ku6ads.dao.entity.webdata.WebdataMovies;
import com.ku6ads.services.base.BaseServiceIface;

public interface WebdataMoviesService extends BaseServiceIface {
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
