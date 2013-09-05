package com.ku6ads.dao.impl.advert;

import java.util.List;

import com.ku6ads.dao.base.BaseAbstractDao;
import com.ku6ads.dao.entity.advert.Bartemplate;
import com.ku6ads.dao.iface.advert.BartemplateDao;

/**
 * @author liujunshi
 *
 */
public class BartemplateDaoImpl extends BaseAbstractDao implements BartemplateDao {

	@SuppressWarnings("unchecked")
	public List<Bartemplate> getEnableBartemplate() {
		return getSqlMapClientTemplate().queryForList("advert.Bartemplate.selectEnableBartemplate");
	}

}
