package com.ku6ads.services.impl.advert;

import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.core.io.ClassPathResource;

import com.ku6ads.dao.entity.advert.Advbar;
import com.ku6ads.dao.entity.advert.Advposition;
import com.ku6ads.dao.entity.advert.Postemplate;
import com.ku6ads.dao.entity.basic.BaseData;
import com.ku6ads.dao.iface.advert.AdvpositionDao;
import com.ku6ads.services.base.BaseAbstractService;
import com.ku6ads.services.iface.advert.AdvpositionService;
import com.ku6ads.util.BussinessStatus;
import com.ku6ads.util.PropertiesUtils;
//import com.ku6ads.util.PushMemoryUtil;

/**
 * @author xuxianan
 *
 */
public class AdvpositionServiceImpl extends BaseAbstractService implements AdvpositionService {

	public static final String OP_ADD = "add";
	public static final String OP_DELETE = "del";
	
	public List<Advposition> selectAdvposition() {
		return ((AdvpositionDao) getBaseDao()).selectAdvposition();
	}

	public List<Advposition> selectAdvpositionByPositionsizeId(int id) {
		Advposition advposition = new Advposition();
		advposition.setPositionsizeId(id);
		return ((AdvpositionDao) getBaseDao()).selectAdvpositionById(advposition);
	}

	public List<Advposition> selectAdvpositionByPostemId(int id) {
		Advposition advposition = new Advposition();
		advposition.setPostemId(id);
		return ((AdvpositionDao) getBaseDao()).selectAdvpositionById(advposition);
	}

	public List<Advposition> selectAdvpositionByName(String name) {
		Advposition advposition = new Advposition();
		advposition.setName(name);
		return ((AdvpositionDao) getBaseDao()).selectAdvpositionByName(advposition);
	}

	public Advposition selectAdvpositionInAdvbar(int advpositionId) {
		return ((AdvpositionDao) getBaseDao()).selectAdvpositionInAdvbar(advpositionId);
	}

	public List<BaseData> selectPostemTypeByTypeValue(String typeValue) {
		return ((AdvpositionDao) getBaseDao()).selectPostemTypeByTypeValue(typeValue);
	}

	public List<Advposition> loadByChannelId(Integer channelId) {
		// TODO Auto-generated method stub
		return ((AdvpositionDao) getBaseDao()).loadByChannelId(channelId);
	}

	public List<Postemplate> selectPostemplateByType(Postemplate postemplate) {
		return ((AdvpositionDao) getBaseDao()).selectPostemplateByType(postemplate);
	}

	@Override
	public List<Advbar> selectAdvbarByAdvpositionId(int advpositionId) {
		return ((AdvpositionDao) getBaseDao()).selectAdvbarByAdvpositionId(advpositionId);
	}

	/**
	 * 先插入表中，再调用推入内存接口。保持事务
	 */
	public void insertAndMemory(Advposition advposition)throws Exception {
		super.insert(advposition);
		//如果是已经启用的广告位
		if(advposition.getStatus() == BussinessStatus.ADVPOSITION_START){
			PropertiesUtils.load(new ClassPathResource("upload.properties"));
			String FLIGHTURL_FLIGHTID = PropertiesUtils.getValue("ADVPOSURL_ADVPOSID");
			String FLIGHTURL_OP = PropertiesUtils.getValue("ADVPOSURL_OP");
			//调用内存接口
			
//			FIXME 刘钧石
//			PushMemoryUtil.PushMemoryList(FLIGHTURL_FLIGHTID, advposition.getId(), FLIGHTURL_OP, OP_ADD);
		}
	}

	/**
	 * 先插入表中，再调用推入内存接口,保持事务
	 */
	public void updateAndMemory(Advposition advposition) throws Exception {
		
		super.updateById(advposition);
		
		//如果是已经启用的广告位
		//Advposition advpositionTem = (Advposition)super.selectByEntity(advposition).get(0);
		Advposition advpositionTem = (Advposition)super.selectById(advposition.getId());
		if(advpositionTem.getStatus() == BussinessStatus.ADVPOSITION_START){
			PropertiesUtils.load(new ClassPathResource("upload.properties"));
			String FLIGHTURL_FLIGHTID = PropertiesUtils.getValue("ADVPOSURL_ADVPOSID");
			String FLIGHTURL_OP = PropertiesUtils.getValue("ADVPOSURL_OP");
			
			//FIXME 刘钧石
//			//调用内存接口 清除内存
//			PushMemoryUtil.PushMemoryList(FLIGHTURL_FLIGHTID, advposition.getId(), FLIGHTURL_OP, OP_DELETE);
//			
//			//调用内存接口 推入内存
//			PushMemoryUtil.PushMemoryList(FLIGHTURL_FLIGHTID, advposition.getId(), FLIGHTURL_OP, OP_ADD);
		}

	}

	/**
	 * 先插入表中，再调用推入内存接口,保持事务
	 */
	public void deleteAndMemory(Integer advposId) throws Exception {
		super.deleteById(advposId);
		PropertiesUtils.load(new ClassPathResource("upload.properties"));
		String FLIGHTURL_FLIGHTID = PropertiesUtils.getValue("ADVPOSURL_ADVPOSID");
		String FLIGHTURL_OP = PropertiesUtils.getValue("ADVPOSURL_OP");
		//调用内存接口 清除内存
//		FIXME 刘钧石
//		PushMemoryUtil.PushMemoryList(FLIGHTURL_FLIGHTID, advposId, FLIGHTURL_OP, OP_DELETE);	
	}

	@Override
	public int getAdvpositionOrder(Integer type, String fileName) {
		JSONObject obj = getAdvpositionConfig(fileName);
		int sort;
		switch (type) {
		case 1: sort = obj.getString("ago") == "" ? 0 : obj.getInt("ago");
			break;
		case 2: sort = obj.getString("after") == "" ? 0 : obj.getInt("after");
			break;
		case 3: sort = obj.getString("background") == "" ? 0 : obj.getInt("background");
			break;
		case 4: sort = obj.getString("corner") == "" ? 0 : obj.getInt("corner");
			break;
		case 5: sort = obj.getString("flashWindow") == "" ? 0 : obj.getInt("flashWindow");
			break;
		case 6: sort = obj.getString("pause") == "" ? 0 : obj.getInt("pause");
			break;
		default:
			sort = 0;
			break;
		}
		return sort;
	}

	/**
	 * 查询广告位排序配置文件,返回对应模板value <br>
	 * <br>
	 * ago : 前贴片 value 1 <br>
	 * after : 后贴片 value 2 <br>
	 * background : 背景 value 3 <br>
	 * corner : 角标 value 4 <br>
	 * flashWindow 炫视窗 value 5 <br>
	 * pause : 暂停 value 6 <br>
	 * @return JSONObject
	 */
	private static JSONObject getAdvpositionConfig(String fileName) {
		JSONObject obj = new JSONObject();
		try {
			PropertiesUtils.load(new ClassPathResource(fileName));
			String ago = PropertiesUtils.getValue("ago");
			String after = PropertiesUtils.getValue("after");
			String background = PropertiesUtils.getValue("background");
			String corner = PropertiesUtils.getValue("corner");
			String flashWindow = PropertiesUtils.getValue("flash_window");
			String pause = PropertiesUtils.getValue("pause");
			
			obj.put("ago", ago);
			obj.put("after", after);
			obj.put("background", background);
			obj.put("corner", corner);
			obj.put("flashWindow", flashWindow);
			obj.put("pause", pause);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}

	@Override
	public Advposition selectAdvNameIsRepeated(int channelId, String advName) {
		Advposition advposition = new Advposition();
		advposition.setChannelId(channelId);
		advposition.setName(advName);
		return ((AdvpositionDao) getBaseDao()).selectAdvNameIsRepeated(advposition);
	}
	
}
