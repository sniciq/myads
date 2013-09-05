package com.ku6ads.dao.impl.sysconfig;

import java.util.List;

import com.ku6ads.dao.base.BaseAbstractDao;
import com.ku6ads.dao.entity.sysconfig.Channel;
import com.ku6ads.dao.iface.sysconfig.ChannelDao;

/**
 * @author xuxianan
 *
 */
public class ChannelDaoImpl extends BaseAbstractDao implements ChannelDao {

	@SuppressWarnings("unchecked")
	public List<Channel> loadBySiteId(Integer siteId){
		return getSqlMapClientTemplate().queryForList("sysconfig.Channel.loadBySiteId", siteId);
	}

	@Override
	public Channel selectChannelNameIsRepeated(Channel channel) {
		return (Channel) getSqlMapClientTemplate().queryForObject("sysconfig.Channel.selectChannelNameIsRepeated", channel);
	}

	/*
	 * (non-Javadoc)
	 * @see com.ku6ads.dao.iface.sysconfig.ChannelDao#insertCallBackId(com.ku6ads.dao.entity.sysconfig.Channel)
	 */
	public int insertCallBackId(Channel channel) {
		// TODO Auto-generated method stub
		return  (Integer) getSqlMapClientTemplate().insert("sysconfig.Channel.insertCallBackId", channel);
	}

}
