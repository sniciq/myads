package com.ku6ads.services.iface.advert;

import java.util.List;

import com.ku6ads.dao.entity.advert.Bartemplate;
import com.ku6ads.services.base.BaseServiceIface;

/**
 * 
 * @author liujunshi
 *
 */
public interface BartemplateService extends BaseServiceIface {

	public List<Bartemplate> getEnableBartemplate();
}
