package com.ku6ads.services.iface.webdata;

import com.ku6ads.services.base.BaseServiceIface;

public interface HotfilmService extends BaseServiceIface {
	
	
	/**
	 * 删除热拍剧,需要同时删除热拍剧下的视频
	 */
	public void deleteHotfilm(int htofilmId);
}
