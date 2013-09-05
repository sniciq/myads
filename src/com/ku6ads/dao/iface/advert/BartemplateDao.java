package com.ku6ads.dao.iface.advert;

import java.util.List;

import com.ku6ads.dao.base.BaseDao;
import com.ku6ads.dao.entity.advert.Bartemplate;

/**
 * 
 * @author liujunshi
 *
 */
public interface BartemplateDao extends BaseDao {

	public List<Bartemplate> getEnableBartemplate();
}
