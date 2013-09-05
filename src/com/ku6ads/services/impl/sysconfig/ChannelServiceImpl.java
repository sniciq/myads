package com.ku6ads.services.impl.sysconfig;

import java.util.List;

import org.springframework.core.io.ClassPathResource;

import com.ku6ads.dao.entity.advert.Advposition;
import com.ku6ads.dao.entity.sysconfig.Channel;
import com.ku6ads.dao.iface.sysconfig.ChannelDao;
import com.ku6ads.services.base.BaseAbstractService;
import com.ku6ads.services.iface.advert.AdvpositionService;
import com.ku6ads.services.iface.sysconfig.ChannelService;
import com.ku6ads.util.PropertiesUtils;
//import com.ku6ads.util.PushMemoryUtil;

public class ChannelServiceImpl extends BaseAbstractService implements
		ChannelService {
	public static final String OP_ADD = "add";
	public static final String OP_DELETE = "del";
	private AdvpositionService advpositionService;

	@SuppressWarnings("unchecked")
	public List<Channel> selectChannel() {
		Channel chEty = new Channel();
		chEty.setParentChannelId(0);
		return ((ChannelDao) getBaseDao()).selectByEntity(chEty);
	}

	public List<Channel> loadBySiteId(Integer siteId) {
		return ((ChannelDao) getBaseDao()).loadBySiteId(siteId);
	}

	public Integer deleteCheckAdvposById(Integer channelId) {
		Integer deletefalg = new Integer(0);
		List<Advposition> advposList = advpositionService
				.loadByChannelId(channelId);
		if (IsDeleteChannels(advposList)) {
			((ChannelDao) getBaseDao()).deleteById(channelId);
			deletefalg = new Integer(1);
		}
		return deletefalg;
	}

	private boolean IsDeleteChannels(List<Advposition> advposList) {
		boolean deleteflag = true;
		if (advposList != null && advposList.size() > 0) {
			deleteflag = false;
		}
		return deleteflag;
	}
	/*
	 * (non-Javadoc)
	 * @see com.ku6ads.services.iface.sysconfig.ChannelService#insertCallbackIdMemory(com.ku6ads.dao.entity.sysconfig.Channel)
	 */
	public int insertCallbackIdMemory(Channel channel) throws Exception {
		// TODO Auto-generated method stub
		 int res = ((ChannelDao) getBaseDao()).insertCallBackId(channel);
		 PropertiesUtils.load(new ClassPathResource("upload.properties"));
		 String ADVBARURL_ADVBARID = PropertiesUtils.getValue("channelURL_cid");
		 String ADVBARURL_OP = PropertiesUtils.getValue("channelURL_OP");
		 
//			FIXME 刘钧石
//		 PushMemoryUtil.PushMemoryList(ADVBARURL_ADVBARID, res, ADVBARURL_OP, OP_ADD);
		 
		 return res;
	}

	/*
	 * (non-Javadoc)
	 * @see com.ku6ads.services.iface.sysconfig.ChannelService#updateAndMemory(com.ku6ads.dao.entity.sysconfig.Channel)
	 */
	public void updateAndMemory(Channel channel) throws Exception {
		super.updateById(channel);
		 PropertiesUtils.load(new ClassPathResource("upload.properties"));
		 String ADVBARURL_ADVBARID = PropertiesUtils.getValue("channelURL_cid");
		 String ADVBARURL_OP = PropertiesUtils.getValue("channelURL_OP");
//			FIXME 刘钧石
//		 PushMemoryUtil.PushMemoryList(ADVBARURL_ADVBARID, channel.getChannelId(), ADVBARURL_OP, OP_DELETE);
//		 PushMemoryUtil.PushMemoryList(ADVBARURL_ADVBARID, channel.getChannelId(), ADVBARURL_OP, OP_ADD);
	}
	
	
	/*
	 * (non-Javadoc)
	 * @see com.ku6ads.services.iface.sysconfig.ChannelService#deleteCheckAdvposByIdMemory(java.lang.Integer)
	 */
	public Integer deleteCheckAdvposByIdMemory(Integer channelId) throws Exception {
		int res = deleteCheckAdvposById(channelId);
		//如果删除成功清除内存
		if(res==1){
			PropertiesUtils.load(new ClassPathResource("upload.properties"));
			 String ADVBARURL_ADVBARID = PropertiesUtils.getValue("channelURL_cid");
			 String ADVBARURL_OP = PropertiesUtils.getValue("channelURL_OP");
			 
//				FIXME 刘钧石
//			 PushMemoryUtil.PushMemoryList(ADVBARURL_ADVBARID, channelId, ADVBARURL_OP, OP_DELETE);
		}
		return res;
	}
	
	@SuppressWarnings("unchecked")
	public List<Channel> getAllUseableChannelList() {
		Channel chEty = new Channel();
		chEty.setParentChannelId(0);
		chEty.setStatus(0);
		return ((ChannelDao) getBaseDao()).selectByEntity(chEty);
	}

	@Override
	public Channel selectChannelNameIsRepeated(int siteId, String channelName) {
		Channel channel = new Channel();
		channel.setName(channelName);
		channel.setSiteId(siteId);
		return ((ChannelDao) getBaseDao()).selectChannelNameIsRepeated(channel);
	}

	public AdvpositionService getAdvpositionService() {
		return advpositionService;
	}

	public void setAdvpositionService(AdvpositionService advpositionService) {
		this.advpositionService = advpositionService;
	}





	
}
