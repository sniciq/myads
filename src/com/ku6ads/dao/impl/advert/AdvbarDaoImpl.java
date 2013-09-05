package com.ku6ads.dao.impl.advert;

import java.util.List;

import com.ku6ads.dao.base.BaseAbstractDao;
import com.ku6ads.dao.entity.advert.Advbar;
import com.ku6ads.dao.entity.basic.BaseData;
import com.ku6ads.dao.iface.advert.AdvbarDao;

/**
 * @author xuxianan
 *
 */
public class AdvbarDaoImpl extends BaseAbstractDao implements AdvbarDao {

	@SuppressWarnings("unchecked")
	public List<Advbar> selectAdvbar() {
		return getSqlMapClientTemplate().queryForList("advert.Advbar.selectByEntity");
	}

	@SuppressWarnings("unchecked")
	public List<Advbar> selectAdvbarById(Advbar advbar) {
		return getSqlMapClientTemplate().queryForList("advert.Advbar.selectAdvbarById", advbar);
	}

	public int selectBarContent(Integer advbarId) {
		Integer nn = (Integer) getSqlMapClientTemplate().queryForObject("advert.Advbar.selectBarContent", advbarId);
		if(nn == null)
			return 0;
		return nn.intValue();
	}

	public BaseData getAdvbarPageType(int advbarId) {
		return (BaseData) getSqlMapClientTemplate().queryForObject("advert.Advbar.getAdvbarPageType", advbarId);
	}

	@Override
	public Integer insertAdvbarCallbackId(Advbar advbar) {
		return (Integer) getSqlMapClientTemplate().insert("advert.Advbar.insertAdvbarCallbackId", advbar);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Advbar> selectUsebleAdvbar(Advbar ety) {
		return getSqlMapClientTemplate().queryForList("advert.Advbar.selectUsebleAdvbar", ety);
	}

	@Override
	public Advbar selectAdvbarNameIsRepeated(Advbar advbar) {
		return (Advbar) getSqlMapClientTemplate().queryForObject("advert.Advbar.selectAdvbarNameIsRepeated", advbar);
	}

}
