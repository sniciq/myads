package com.ku6ads.services.impl.sysconfig;

import java.util.Dictionary;
import java.util.Iterator;
import java.util.List;

import com.ku6ads.dao.entity.basic.BaseData;
import com.ku6ads.dao.entity.sysconfig.Channel;
import com.ku6ads.dao.entity.sysconfig.Site;
import com.ku6ads.dao.iface.sysconfig.SiteDao;
import com.ku6ads.exception.DictionaryNotFoundException;
import com.ku6ads.services.base.BaseAbstractService;
import com.ku6ads.services.iface.sysconfig.ChannelService;
import com.ku6ads.services.iface.sysconfig.SiteService;
import com.ku6ads.struts.basic.cache.DictionaryFactory;

public class SiteServiceImpl extends BaseAbstractService implements SiteService {

	private SiteDao siteDao;
	
	private ChannelService channelService;
	public SiteDao getSiteDao() {
		return siteDao;
	}
	public void setSiteDao(SiteDao siteDao) {
		this.siteDao = siteDao;
	}
	@Override
	public List<Site> selectSite() {
		return siteDao.selectAll();
	}
	
	public Integer deleteCheckChannelsById(Integer id){
		Integer deletefalg = new Integer(0);
		List<Channel> channelList = channelService.loadBySiteId(id);
		if(IsDeleteChannels(channelList)){
			siteDao.deleteById(id);
			deletefalg = new Integer(1);
		}
		return deletefalg;
	}
	
	private boolean IsDeleteChannels(List<Channel> channelList) {
		boolean deleteflag = true;
		if(channelList!=null && channelList.size()>0){
			deleteflag =false;		
		}
		return deleteflag;
	}
	public ChannelService getChannelService() {
		return channelService;
	}
	public void setChannelService(ChannelService channelService) {
		this.channelService = channelService;
	}
	
}
