package com.ku6ads.services.iface.sysconfig;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ku6ads.dao.entity.advert.Advbar;
import com.ku6ads.dao.entity.sysconfig.Channel;
import com.ku6ads.services.base.BaseServiceIface;

public interface ChannelService extends BaseServiceIface {

	public List<Channel> selectChannel();
	
	/**
	 * 根据网站id获取频道列表
	 * 	add by zhangyan
	 * @return
	 */
	public List<Channel> loadBySiteId(Integer siteId);
	
	/**
	 * 删除频道
	 * @param channelId
	 * @return
	 */
	public Integer deleteCheckAdvposById(Integer channelId);
	
	/**
	 * 得到所有可用的频道
	 * @return
	 */
	public List<Channel> getAllUseableChannelList();
	
	/**
	 * 查询一个网站下的频道名称是否重复
	 * @param siteId
	 * @param channelName
	 * @return Integer
	 */
	public Channel selectChannelNameIsRepeated(int siteId, String channelName);
	
	/**
	 * 新建频道推入内存
	 * @param channel
	 * @return
	 * @throws Exception
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public int insertCallbackIdMemory(Channel channel) throws Exception;
	/***
	 * 修改频道
	 * @param channel
	 * @throws Exception
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public void updateAndMemory(Channel channel) throws Exception;
	
	/**
	 * 删除DB并清除缓存
	 * @param channelId
	 * @return
	 */
	public Integer deleteCheckAdvposByIdMemory(Integer channelId)throws Exception;
}
