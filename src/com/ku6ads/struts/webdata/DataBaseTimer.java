package com.ku6ads.struts.webdata;

import java.util.TimerTask;

import com.ku6ads.dao.iface.webdata.HotfilmVideoDao;

/**
 * 验证postgresql数据库连接<br>
 * 之前出现连接池不用一会后再次连接会很慢，设置该定时方法用于保证连接池使用
 * @author yanghanguang
 *
 */
public class DataBaseTimer extends TimerTask {
	private HotfilmVideoDao hotfilmVideoDao;
	
	public void run() {
		hotfilmVideoDao.selectSysDate();
	}

	public HotfilmVideoDao getHotfilmVideoDao() {
		return hotfilmVideoDao;
	}

	public void setHotfilmVideoDao(HotfilmVideoDao hotfilmVideoDao) {
		this.hotfilmVideoDao = hotfilmVideoDao;
	}
	
}
