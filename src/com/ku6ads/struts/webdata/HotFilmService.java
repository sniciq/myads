package com.ku6ads.struts.webdata;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.ku6ads.dao.entity.webdata.Hotfilm;
import com.ku6ads.dao.entity.webdata.HotfilmVideo;
import com.ku6ads.dao.iface.webdata.HotfilmDao;
import com.ku6ads.dao.impl.webdata.HotfilmVideoDaoImpl;
import com.ku6ads.util.AjaxOut;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 为数据统计做的接口，提供每天需要统计的热播剧信息
 * 
 * @author yanghanguang
 * 
 */
public class HotFilmService extends ActionSupport {

	private static final long serialVersionUID = 1910208460286295938L;
	private HotfilmDao hotfilmDao;
	private HotfilmVideoDaoImpl hotfilmVideoDao;
	private Logger logger = Logger.getLogger(HotFilmService.class);

	public void getStateData() {
		JSONObject retObj = new JSONObject();
		try {
			Hotfilm filmEty = new Hotfilm();
			filmEty.setIsstate(true);
			List<Hotfilm> filmList = hotfilmDao.selectByEntity(filmEty);
			JSONArray allFilmArray = new JSONArray();
			for (int i = 0; i < filmList.size(); i++) {
				Hotfilm iEty = filmList.get(i);
				HotfilmVideo videoEty = new HotfilmVideo();
				videoEty.setHotfilmId(iEty.getId());
				
				List<HotfilmVideo> vlist = hotfilmVideoDao.selectByEntity(videoEty);
				JSONArray videoArray = new JSONArray();
				for(int j = 0; j < vlist.size(); j++) {
					HotfilmVideo hv = vlist.get(j);
					videoArray.add(hv);
				}
				JSONObject aFilmObj = new JSONObject();
				aFilmObj.put("film", iEty);
				aFilmObj.put("videos", videoArray);
				allFilmArray.add(aFilmObj);
			}
			
//			JSONArray allFilmArray = new JSONArray();
//			List<String> vidList = hotfilmVideoDao.selectStateVideos();
//			for (int i = 0; i < vidList.size(); i++) {
//				allFilmArray.add(vidList.get(i));
//			}
			retObj.put("AllVids", allFilmArray);
		} catch (Exception e) {
			logger.error("", e);
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), retObj.toString());
	}

	public HotfilmDao getHotfilmDao() {
		return hotfilmDao;
	}

	public void setHotfilmDao(HotfilmDao hotfilmDao) {
		this.hotfilmDao = hotfilmDao;
	}

	public HotfilmVideoDaoImpl getHotfilmVideoDao() {
		return hotfilmVideoDao;
	}

	public void setHotfilmVideoDao(HotfilmVideoDaoImpl hotfilmVideoDao) {
		this.hotfilmVideoDao = hotfilmVideoDao;
	}
	
}
