package com.ku6ads.services.impl.webdata;

import com.ku6ads.dao.entity.webdata.WebdataMoviesMaintain;
import com.ku6ads.dao.iface.webdata.WebdataMoviesMaintainDao;
import com.ku6ads.services.base.BaseAbstractService;
import com.ku6ads.services.iface.webdata.WebdataMoviesMaintainService;

public class WebdataMoviesMaintainServiceImpl extends BaseAbstractService implements WebdataMoviesMaintainService{

	@Override
	public boolean checkSave(WebdataMoviesMaintain maintainEty) {
		
//		同一部剧 在同一时间段 如果包段了 就不能部分购买
//		如果部分购买了 就不能包段
//		但是 可以存在多条部分购买的数据
		int n = ((WebdataMoviesMaintainDao)getBaseDao()).selectConflictCount(maintainEty);
		return (n > 0 ? false : true);
	}

}
