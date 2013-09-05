package com.ku6ads.services.iface.webdata;

import com.ku6ads.dao.entity.webdata.WebdataMoviesMaintain;
import com.ku6ads.services.base.BaseServiceIface;

public interface WebdataMoviesMaintainService extends BaseServiceIface {
	
	/**
	 * 保存检查，如果<br>
	 * 同一部剧 在同一时间段 如果包段了 就不能部分购买<br>
	 * 如果部分购买了 就不能包段<br>
	 * 但是 可以存在多条部分购买的数据
	 * @param maintainEty
	 * @return true: 可以保存，false: 不可以保存
	 */
	public boolean checkSave(WebdataMoviesMaintain maintainEty);
}
