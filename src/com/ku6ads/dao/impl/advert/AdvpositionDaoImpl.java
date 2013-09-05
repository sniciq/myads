package com.ku6ads.dao.impl.advert;

import java.util.List;

import com.ku6ads.dao.base.BaseAbstractDao;
import com.ku6ads.dao.entity.advert.Advbar;
import com.ku6ads.dao.entity.advert.Advposition;
import com.ku6ads.dao.entity.advert.Postemplate;
import com.ku6ads.dao.entity.basic.BaseData;
import com.ku6ads.dao.iface.advert.AdvpositionDao;

/**
 * @author xuxianan
 *
 */
public class AdvpositionDaoImpl extends BaseAbstractDao implements AdvpositionDao {

	@SuppressWarnings("unchecked")
	public List<Advposition> selectAdvposition() {
		return getSqlMapClientTemplate().queryForList("advert.Advposition.selectByEntity");
	}

	@SuppressWarnings("unchecked")
	public List<Advposition> selectAdvpositionById(Advposition advposition) {
		return getSqlMapClientTemplate().queryForList("advert.Advposition.selectAdvpositionById", advposition);
	}

	@SuppressWarnings("unchecked")
	public List<Advposition> selectAdvpositionByName(Advposition advposition) {
		return getSqlMapClientTemplate().queryForList("advert.Advposition.selectAdvpositionByName", advposition);
	}

	public Advposition selectAdvpositionInAdvbar(int advpositionId) {
		return (Advposition) getSqlMapClientTemplate().queryForObject("advert.Advposition.selectAdvpositionInAdvbar",
				advpositionId);
	}

	@SuppressWarnings("unchecked")
	public List<BaseData> selectPostemTypeByTypeValue(String typeValue) {
		return getSqlMapClientTemplate().queryForList("advert.Advposition.selectPostemTypeByPageType", typeValue);
	}

	@Override
	public List<Advposition> loadByChannelId(Integer channelId) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().queryForList("advert.Advposition.loadByChannelId", channelId);
	}

	@SuppressWarnings("unchecked")
	public List<Postemplate> selectPostemplateByType(Postemplate postemplate) {
		return getSqlMapClientTemplate().queryForList("advert.Advposition.selectPostemplateTypeByPageType", postemplate);
	}

	@SuppressWarnings("unchecked")
	public List<Advbar> selectAdvbarByAdvpositionId(int advpositionId) {
		return getSqlMapClientTemplate().queryForList("advert.Advposition.selectAdvbarByAdvpositionId", advpositionId);
	}

	@Override
	public Advposition selectAdvNameIsRepeated(Advposition advposition) {
		return (Advposition) getSqlMapClientTemplate().queryForObject("advert.Advposition.selectAdvNameIsRepeated", advposition);
	}

}
