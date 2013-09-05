package com.ku6ads.struts.advert;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.ku6ads.dao.entity.advert.Advbar;
import com.ku6ads.dao.entity.advert.Advposition;
import com.ku6ads.dao.entity.advert.Bartemplate;
import com.ku6ads.dao.entity.advert.Positionsize;
import com.ku6ads.dao.entity.advert.PostemBartem;
import com.ku6ads.dao.entity.advflight.BookPackage;
import com.ku6ads.dao.entity.advflight.FutureFlight;
import com.ku6ads.dao.entity.basic.BaseData;
import com.ku6ads.dao.entity.sysconfig.UserInfoEty;
import com.ku6ads.dao.iface.advflight.FutureFlightDao;
import com.ku6ads.services.iface.advert.AdvbarService;
import com.ku6ads.services.iface.advert.AdvpositionService;
import com.ku6ads.services.iface.advert.BartemplateService;
import com.ku6ads.services.iface.advert.PositionsizeService;
import com.ku6ads.services.iface.advert.PostemplateService;
import com.ku6ads.services.iface.advflight.BookPackageService;
import com.ku6ads.struts.basic.BaseAction;
import com.ku6ads.util.AjaxOut;
import com.ku6ads.util.EntityReflect;
import com.ku6ads.util.ExtLimit;
import com.ku6ads.util.TypeConverterUtil;

/**
 * 广告条 action
 * @author xuxianan
 *
 */
public class AdvbarAction extends BaseAction {

	private static final long serialVersionUID = -9066981850534240298L;
	private Logger logger = Logger.getLogger(AdvbarAction.class);
	private AdvbarService advbarService;
	private BartemplateService bartemplateService;
	private PostemplateService postemplateService;
	private PositionsizeService positionsizeService;
	private FutureFlightDao futureFlightDao;
	private BookPackageService bookPackageService;
	private AdvpositionService advpositionService;
	
	private String barDivId;//广告条层id
	/**
	 * 新增广告条
	 */
	@SuppressWarnings("static-access")
	public void save() {
		JSONObject obj = new JSONObject();
		obj.put("success", true);
		try {
			String advpositionNum = ServletActionContext.getRequest().getParameter("advpositionNum");
			Advbar advbar = (Advbar) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(),Advbar.class);
			UserInfoEty user = this.getLoginUser();
			
			Positionsize posSize = (Positionsize) positionsizeService.selectById(advbar.getBarsizeId());
			Bartemplate bt = (Bartemplate)bartemplateService.selectById(advbar.getBartemId());
			int width = posSize.getWidth();
			int high = posSize.getHigh();
			//String divCodePre = "<div height=\""+high+"\" width=\""+width+"\">";
			String tString = bt.getCode()==null?"":bt.getCode();
			//String divCodeEnd = "</div>";
			//String code = divCodePre+tString+divCodeEnd;
			String code = tString;
			advbar.setCode(code);
			
			if (advbar.getId() == null) {
				List<Advbar> advbarList = advbarService.selectAdvbarByPosId(advbar.getPosId());
				if (advbarList.size() < TypeConverterUtil.parseInt(advpositionNum)) {
					advbar.setName(advbar.getPosName() + "_" + advbar.getName());
					advbar.setChannelName(advbar.getChannelName());
					advbar.setSiteName(advbar.getSiteName());
					advbar.setCreator(user.getUsername());
					advbar.setCreateTime(new Date());
					advbar.setStatus(advbar.START_STATUS);
					
					Integer recordId = advbarService.insertAdvbarCallbackIdMemory(advbar);
					if (recordId != null) {
						FutureFlight futureFlight = new FutureFlight();
						futureFlight.setAdvbarId(recordId);
						futureFlight.setNum1(advbar.getFutureFlight());
						futureFlight.setNum2(advbar.getFutureFlight());
						futureFlight.setNum3(advbar.getFutureFlight());
						futureFlight.setNum4(advbar.getFutureFlight());
						futureFlight.setNum5(advbar.getFutureFlight());
						futureFlight.setNum6(advbar.getFutureFlight());
						futureFlight.setNum7(advbar.getFutureFlight());
						futureFlight.setCreateTime(new Date());
						futureFlightDao.insert(futureFlight);
					}
				} else {
					obj.put("result", "full");	// full 表示当前广告位容量已满;
				}
			} else {
				String advbarName = advbar.getName();
				try {
					advbarName = advbarName.split("_")[1];
					advbar.setName(advbar.getPosName() + "_" + advbarName);
					advbar.setModifier(user.getUsername());
					advbar.setModifyTime(new Date());
				} catch (Exception e) {
					advbar.setName(advbar.getPosName() + "_" + advbarName);
					advbar.setModifier(user.getUsername());
					advbar.setModifyTime(new Date());
				}
				
				FutureFlight futureFlight = new FutureFlight();
				futureFlight.setAdvbarId(advbar.getId());
				futureFlight.setNum1(advbar.getFutureFlight());
				futureFlight.setNum2(advbar.getFutureFlight());
				futureFlight.setNum3(advbar.getFutureFlight());
				futureFlight.setNum4(advbar.getFutureFlight());
				futureFlight.setNum5(advbar.getFutureFlight());
				futureFlight.setNum6(advbar.getFutureFlight());
				futureFlight.setNum7(advbar.getFutureFlight());
				futureFlightDao.updateByAdvbarId(futureFlight);
				
				advbarService.updateAndMemory(advbar);
			}
			obj.put("result", "success");
		} catch (Exception e) {
			logger.error("", e);
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), obj.toString());
	}

	/**
	 * 获得频道列表;
	 */
	@SuppressWarnings("unchecked")
	public void showAll() {
		try {
			ExtLimit limit = (ExtLimit) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(),
					ExtLimit.class);
			String advpositionId = ServletActionContext.getRequest().getParameter("advpositionId");
			Advbar advbar = (Advbar) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(),Advbar.class);
			if (advpositionId != null) {
				advbar.setPosId(TypeConverterUtil.parseInt(advpositionId));
			}
			advbar.setCreator(this.validationUserGroup());
			advbar.setExtLimit(limit);
			int count = advbarService.selectLimitCount(advbar);
			List<Advbar> advbarList = advbarService.selectByLimit(advbar);
			AjaxOut.responseJSonGrid(ServletActionContext.getResponse(), advbarList, count, new SimpleDateFormat(
					"yyyy-MM-dd"));
		} catch (Exception e) {
			logger.error("", e);
		}
	}

	/**
	 * 获得广告条列表
	 */
	public void getAdvbarList() {
		List<Advbar> list = advbarService.selectAdvbar();
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		for (int i = 0; i < list.size(); i++) {
			Advbar advbar = list.get(i);
			sb.append("['" + advbar.getId() + "','" + advbar.getName() + "']");
			if (i < list.size() - 1)
				sb.append(",");
		}
		sb.append("]");
		AjaxOut.responseText(ServletActionContext.getResponse(), sb.toString());
	}

	/**
	 * 获得广告条模板列表</br>
	 * </br>
	 * 有以下两种情况:</br>
	 * 1. 从广告位下的广告条访问(可以得到广告位中的广告位模板id)</br>
	 * 2. 从广告条管理直接访问(需要手动查一次)</br>
	 */
	public void getBartemplateList() {
		try {
			PostemBartem postemBartem = new PostemBartem();
			postemBartem.setStatus(0);
			
			// 1. 广告位模板id直接进行查询
			String postemId = ServletActionContext.getRequest().getParameter("id");
			if (null != postemId) {
				postemBartem.setPostemId(Integer.parseInt(postemId));
			}
			
			// 2. 广告条管理访问.先根据广告位id查询到广告位模板id在进行查询
			String posId = ServletActionContext.getRequest().getParameter("posId");
			if (null != posId) {
				Advposition advposition = (Advposition) advpositionService.selectById(Integer.parseInt(posId));
				postemBartem.setPostemId(advposition.getPostemId());
			}
			
			List<PostemBartem> list = postemplateService.getBartemByPostemId(postemBartem);
			StringBuffer sb = new StringBuffer();
			sb.append("[");
			for (int i = 0; i < list.size(); i++) {
				PostemBartem postemBartemTemp = list.get(i);
				sb.append("['" + postemBartemTemp.getBartemId() + "','" + postemBartemTemp.getBartemName() + "']");
				if (i < list.size() - 1)
					sb.append(",");
			}
			sb.append("]");
			AjaxOut.responseText(ServletActionContext.getResponse(), sb.toString());
		} catch (Exception e) {
			logger.error("", e);
		}
	}

	/**
	 * 获得广告条规格列表
	 */
	public void getPositionsizeList() {
		List<Positionsize> list = positionsizeService.getEnableBarsize();
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		for (int i = 0; i < list.size(); i++) {
			Positionsize positionsize = list.get(i);
			sb.append("['" + positionsize.getId() + "','" + positionsize.getName() + "']");
			if (i < list.size() - 1)
				sb.append(",");
		}
		sb.append("]");
		AjaxOut.responseText(ServletActionContext.getResponse(), sb.toString());
	}

	/**
	 * 获得广告位详细信息
	 */
	public void getAdvbarDetail() {
		JSONObject retObj = new JSONObject();
		retObj.put("success", true);
		try {
			String id = ServletActionContext.getRequest().getParameter("id");
			Advbar advbar = (Advbar) advbarService.selectById(TypeConverterUtil.parseInt(id));
			retObj.put("data", advbar);
		} catch (Exception e) {
			logger.error("", e);
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), retObj.toString());
	}

	/**
	 * 删除广告条;
	 */
	public void delete() {
		JSONObject retObj = new JSONObject();
		retObj.put("success", true);
		try {
			String ids = ServletActionContext.getRequest().getParameter("advbarList");
			String[] idList = StringUtils.split(ids, ",");
			for (int i = 0; i < idList.length; i++) {
				advbarService.deleteAndMemory(TypeConverterUtil.parseInt(idList[i]));
			}
			retObj.put("result", "success");
		} catch (Exception e) {
			logger.error("", e);
			retObj.put("result", "error");
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), retObj.toString());
	}
	
	/**
	 * 更改广告条状态
	 */
	public void updateStatus() {
		JSONObject retObj = new JSONObject();
		retObj.put("success", true);
		try {
			Advbar advbar = (Advbar) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), Advbar.class);
			if (advbar.getId() != null) {
				Advbar advbarTemp = (Advbar) advbarService.selectById(advbar.getId());
				advbarTemp.setModifier("");
				advbarTemp.setModifyTime(new Date());
				if (advbar.getStatus() != null) {
					advbarTemp.setStatus(advbar.getStatus());
					advbarService.updateById(advbarTemp);
				}
				retObj.put("result", "success");
			} else {
				retObj.put("result", "error");
			}
		} catch (Exception e) {
			logger.error("", e);
			retObj.put("result", "error");
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), retObj.toString());
	}

	/**
	 * 关联广告条id
	 */
	public void relationAdvbarId() {
		JSONObject retObj = new JSONObject();
		retObj.put("success", true);
		try {
			String srcposId = ServletActionContext.getRequest().getParameter("srcposId");
			Advbar advbarTemp = (Advbar) advbarService.selectById(TypeConverterUtil.parseInt(srcposId));
			if (advbarTemp == null) {
				retObj.put("result", "empty");
			} else {
				String advbarId = ServletActionContext.getRequest().getParameter("advbarId");
				if ("".trim().equals(advbarId)) {
					retObj.put("result", "error");
				} else {
					Advbar advbar = (Advbar) advbarService.selectById(TypeConverterUtil.parseInt(advbarId));
					advbar.setSrcposId(TypeConverterUtil.parseInt(srcposId));
					advbarService.updateById(advbar);
					retObj.put("result", "success");
				}
			}
		} catch (Exception e) {
			logger.error("", e);
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), retObj.toString());
	}
	
	public void getAdvbarPageType() {
		JSONObject retObj = new JSONObject();
		retObj.put("success", true);
		try {
			String advbarId = ServletActionContext.getRequest().getParameter("advbarId");
			BaseData baseDataEty = advbarService.getAdvbarPageType(Integer.parseInt(advbarId));
			retObj.put("data", baseDataEty);
		} catch (Exception e) {
			logger.error("", e);
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), retObj.toString());
	}
	
	/**
	 * 获得广告条对应的所有排期
	 */
	@SuppressWarnings("unchecked")
	public void getAdvbarAllAdvflight() {
		try {
			ExtLimit limit = (ExtLimit) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), ExtLimit.class);
			String advbarId = ServletActionContext.getRequest().getParameter("advbarId");
			
			// 1. 通过广告条id查询排期包中对应的执行单信息
			BookPackage bookPackage = new BookPackage();
			bookPackage.setAdvbarId(Integer.parseInt(advbarId));
			List<BookPackage> bookPackageList = bookPackageService.selectByProjectId(bookPackage);
			
			List<BookPackage> packageList = new ArrayList<BookPackage>();
			if (!bookPackageList.isEmpty()) {
				for (BookPackage bPackage : bookPackageList) {
					bookPackage.setProjectId(bPackage.getProjectId());
					bookPackage.setExtLimit(limit);
					List<BookPackage> packageListTmp = bookPackageService.selectByLimit(bookPackage);
					for (BookPackage bookPackage2 : packageListTmp) {
						packageList.add(bookPackage2);
					}
				}
			}
			AjaxOut.responseJSonGrid(ServletActionContext.getResponse(), packageList, 0, new SimpleDateFormat("yyyy-MM-dd"));
		} catch (Exception e) {
			logger.error("", e);
		}
	}
	
	/**
	 * 查询同一广告位下的广告条名称是否有重复
	 */
	public void vldAdvbarNameRepeated() {
		JSONObject obj = new JSONObject();
		obj.put("success", true);
		try {
			String advId = ServletActionContext.getRequest().getParameter("id");
			int posId = Integer.parseInt(ServletActionContext.getRequest().getParameter("posId"));
			String name = ServletActionContext.getRequest().getParameter("name");
			String posName = ServletActionContext.getRequest().getParameter("posName");
			String advbarName = posName + "_" + name;
			
			Advbar advbar = advbarService.selectAdvbarNameIsRepeated(posId, advbarName);
			if (null == advbar) {
				obj.put("result", "success");
			}
			
			// 当记录重复时,判断是否是同一条记录
			if (null != advbar) {
				if (!("").equals(advId) && advbar.getId() == Integer.parseInt(advId))
					obj.put("result", "success");
				else
					obj.put("result", "use");
			}
		} catch (Exception e) {
			logger.error("", e);
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), obj.toString());
	}
	
	public AdvbarService getAdvbarService() {
		return advbarService;
	}

	public void setAdvbarService(AdvbarService advbarService) {
		this.advbarService = advbarService;
	}

	public BartemplateService getBartemplateService() {
		return bartemplateService;
	}

	public void setBartemplateService(BartemplateService bartemplateService) {
		this.bartemplateService = bartemplateService;
	}

	public PositionsizeService getPositionsizeService() {
		return positionsizeService;
	}

	public void setPositionsizeService(PositionsizeService positionsizeService) {
		this.positionsizeService = positionsizeService;
	}

	public String getBarDivId() {
		return barDivId;
	}

	public void setBarDivId(String barDivId) {
		this.barDivId = barDivId;
	}

	public PostemplateService getPostemplateService() {
		return postemplateService;
	}

	public void setPostemplateService(PostemplateService postemplateService) {
		this.postemplateService = postemplateService;
	}

	public FutureFlightDao getFutureFlightDao() {
		return futureFlightDao;
	}

	public void setFutureFlightDao(FutureFlightDao futureFlightDao) {
		this.futureFlightDao = futureFlightDao;
	}

	public BookPackageService getBookPackageService() {
		return bookPackageService;
	}

	public void setBookPackageService(BookPackageService bookPackageService) {
		this.bookPackageService = bookPackageService;
	}

	public AdvpositionService getAdvpositionService() {
		return advpositionService;
	}

	public void setAdvpositionService(AdvpositionService advpositionService) {
		this.advpositionService = advpositionService;
	}
	
}
