package com.ku6ads.services.impl.advert;

import java.util.List;

import org.springframework.core.io.ClassPathResource;

import com.ku6ads.dao.entity.advert.Advbar;
import com.ku6ads.dao.entity.basic.BaseData;
import com.ku6ads.dao.iface.advert.AdvbarDao;
import com.ku6ads.services.base.BaseAbstractService;
import com.ku6ads.services.iface.advert.AdvbarService;
import com.ku6ads.util.PropertiesUtils;
//import com.ku6ads.util.PushMemoryUtil;

/**
 * @author xuxianan
 *
 */
public class AdvbarServiceImpl extends BaseAbstractService implements AdvbarService {

	public static final String OP_ADD = "add";
	public static final String OP_DELETE = "del";
	
	public List<Advbar> selectAdvbar() {
		return ((AdvbarDao) getBaseDao()).selectAdvbar();
	}

	public List<Advbar> selectAdvbarByChannelId(int id) {
		Advbar advbar = new Advbar();
		advbar.setChannelId(id);
		return ((AdvbarDao) getBaseDao()).selectAdvbarById(advbar);
	}

	public List<Advbar> selectAdvbarByPosId(int id) {
		Advbar advbar = new Advbar();
		advbar.setPosId(id);
		return ((AdvbarDao) getBaseDao()).selectAdvbarById(advbar);
	}

	public List<Advbar> selectAdvbarBySiteBarsizeId(int id) {
		Advbar advbar = new Advbar();
		advbar.setBarsizeId(id);
		return ((AdvbarDao) getBaseDao()).selectAdvbarById(advbar);
	}

	public List<Advbar> selectAdvbarBySrcposId(int id) {
		Advbar advbar = new Advbar();
		advbar.setSrcposId(id);
		return ((AdvbarDao) getBaseDao()).selectAdvbarById(advbar);
	}

	public List<Advbar> selectAdvbarByBartemId(int id) {
		Advbar advbar = new Advbar();
		advbar.setBartemId(id);
		return ((AdvbarDao) getBaseDao()).selectAdvbarById(advbar);
	}

	public BaseData getAdvbarPageType(int advbarId) {
		return ((AdvbarDao) getBaseDao()).getAdvbarPageType(advbarId);
	}

	public int insertAdvbarCallbackId(Advbar advbar) {
		return ((AdvbarDao) getBaseDao()).insertAdvbarCallbackId(advbar);
	}

	/*
	 * (non-Javadoc)
	 * @see com.ku6ads.services.iface.advert.AdvbarService#insertAdvbarCallbackIdMemory(com.ku6ads.dao.entity.advert.Advbar)
	 */
	public int insertAdvbarCallbackIdMemory(Advbar advbar) throws Exception{
		int res = insertAdvbarCallbackId(advbar);
		PropertiesUtils.load(new ClassPathResource("upload.properties"));
		String ADVBARURL_ADVBARID = PropertiesUtils.getValue("ADVBARURL_ADVBARID");
		String ADVBARURL_OP = PropertiesUtils.getValue("ADVBARURL_OP");
		
//		FIXME 刘钧石
//		PushMemoryUtil.PushMemoryList(ADVBARURL_ADVBARID, advbar.getId(), ADVBARURL_OP, OP_ADD);
		
		return res;
	}

	/*
	 * (non-Javadoc)
	 * @see com.ku6ads.services.iface.advert.AdvbarService#updateAndMemory(com.ku6ads.dao.entity.advert.Advposition)
	 */
	public void updateAndMemory(Advbar advbar) throws Exception {
		super.updateById(advbar);
		PropertiesUtils.load(new ClassPathResource("upload.properties"));
		String ADVBARURL_ADVBARID = PropertiesUtils.getValue("ADVBARURL_ADVBARID");
		String ADVBARURL_OP = PropertiesUtils.getValue("ADVBARURL_OP");
		
//		FIXME 刘钧石
//		//先清除内存
//		PushMemoryUtil.PushMemoryList(ADVBARURL_ADVBARID, advbar.getId(), ADVBARURL_OP, OP_DELETE);
//		//在加入内存
//		PushMemoryUtil.PushMemoryList(ADVBARURL_ADVBARID, advbar.getId(), ADVBARURL_OP, OP_ADD);
	}

	/*
	 * (non-Javadoc)
	 * @see com.ku6ads.services.iface.advert.AdvbarService#deleteAndMemory(java.lang.Integer)
	 */
	public void deleteAndMemory(Integer advbarId) throws Exception {
		super.deleteById(advbarId);
		PropertiesUtils.load(new ClassPathResource("upload.properties"));
		String ADVBARURL_ADVBARID = PropertiesUtils.getValue("ADVBARURL_ADVBARID");
		String ADVBARURL_OP = PropertiesUtils.getValue("ADVBARURL_OP");
		
//		FIXME 刘钧石
//		//先清除内存
//		PushMemoryUtil.PushMemoryList(ADVBARURL_ADVBARID, advbarId, ADVBARURL_OP, OP_DELETE);
		
	}

	@Override
	public Advbar selectAdvbarNameIsRepeated(int advpositionId, String advbarName) {
		Advbar advbar = new Advbar();
		advbar.setPosId(advpositionId);
		advbar.setName(advbarName);
		return ((AdvbarDao) getBaseDao()).selectAdvbarNameIsRepeated(advbar);
	}

}
