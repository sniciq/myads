package com.ku6ads.dao.iface.sysconfig;

import java.util.List;

import com.ku6ads.dao.base.BaseDao;
import com.ku6ads.dao.entity.sysconfig.Channel;

public interface ChannelDao extends BaseDao {

	/**
	 * 根据网站id查询频道
	 * @return List<Channel>
	 */
	public List<Channel> loadBySiteId(Integer siteId);

	/**
	 * 查询一个网站下的频道名称是否重复
	 */
	public Channel selectChannelNameIsRepeated(Channel channel);
	
	/**
	 * 新建频道返回Id
	 * @param channel
	 * @return
	 */
	public int insertCallBackId(Channel channel);
}
