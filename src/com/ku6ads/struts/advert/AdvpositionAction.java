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
import com.ku6ads.dao.entity.advert.Positionsize;
import com.ku6ads.dao.entity.advert.Postemplate;
import com.ku6ads.dao.entity.basic.BaseData;
import com.ku6ads.dao.entity.sysconfig.Channel;
import com.ku6ads.dao.entity.sysconfig.Site;
import com.ku6ads.dao.entity.sysconfig.UserInfoEty;
import com.ku6ads.services.iface.advert.AdvbarService;
import com.ku6ads.services.iface.advert.AdvpositionService;
import com.ku6ads.services.iface.advert.PositionsizeService;
import com.ku6ads.services.iface.advert.PostemplateService;
import com.ku6ads.services.iface.sysconfig.ChannelService;
import com.ku6ads.services.iface.sysconfig.SiteService;
import com.ku6ads.struts.basic.BaseAction;
import com.ku6ads.util.AjaxOut;
import com.ku6ads.util.BussinessStatus;
import com.ku6ads.util.EntityReflect;
import com.ku6ads.util.ExtLimit;
import com.ku6ads.util.TypeConverterUtil;

/**
 * 广告位action
 * @author xuxianan
 *
 */
public class AdvpositionAction extends BaseAction {

	private static final long serialVersionUID = -84971691643060057L;
	private Logger logger = Logger.getLogger(AdvpositionAction.class);

	private AdvpositionService advpositionService;
	private AdvbarService advbarService;
	private PositionsizeService positionsizeService;
	private PostemplateService postemplateService;
	private ChannelService channelService;
	private SiteService siteService;

	private static final String ADVPOSITION_SIZE = "0"; // 广告位规格
	private static final int PLAYER_ADVERTISING = 2;	// 播放器广告
	private String posDivId;//广告位层id
	/**
	 * 新增广告位
	 */
	public void save() {
		JSONObject obj = new JSONObject();
		obj.put("success", true);
		try {
			Advposition advposition = (Advposition) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), Advposition.class);
			UserInfoEty user = this.getLoginUser();
			Positionsize posSize = (Positionsize) positionsizeService.selectById(advposition.getPositionsizeId());
			Postemplate pt = (Postemplate)postemplateService.selectById(advposition.getPostemId());
			int width = posSize.getWidth();
			int high = posSize.getHigh();
			//String divCodePre = "<div height=\""+high+"\" width=\""+width+"\">";
			String tString = pt.getCode()==null?"":pt.getCode();
			//String divCodeEnd = "</div>";
			//String code =divCodePre+tString+divCodeEnd;
			//TODO 广告位代码中加入第三方监控代码
			//String code = advposition.getDirCode()+divCodePre+tString+divCodeEnd;
			advposition.setCode(tString);
			
			// 根据播放器广告模板种类插入配置文件中对应的value
			String typeStr = ServletActionContext.getRequest().getParameter("pageValue");
			if (!("").equals(typeStr)) {
				if (advposition.getPageType().equals(PLAYER_ADVERTISING)) {
					int type = Integer.parseInt(ServletActionContext.getRequest().getParameter("pageValue"));
					advposition.setSort(advpositionService.getAdvpositionOrder(type, "advpositionOrder.properties"));
				}
			}
			
			if (advposition.getId() == null) {
				advposition.setStatus(BussinessStatus.ADVPOSITION_CREATE); // 默认为新建
				advposition.setCreator(user.getUsername());
				advposition.setCreateTime(new Date());
				advpositionService.insertAndMemory(advposition);
			} else {
				advposition.setModifier(user.getUsername());
				advposition.setModifyTime(new Date());
				advpositionService.updateAndMemory(advposition);
				
				// 变更广告条网站和频道信息
				List<Advbar> list = advpositionService.selectAdvbarByAdvpositionId(advposition.getId());
				if (!list.isEmpty()) {
					for (Advbar advbar : list) {
						advbar.setSiteId(advposition.getSiteId());
						advbar.setChannelId(advposition.getChannelId());
						advbarService.updateById(advbar);
					}
				}
			}
			obj.put("result", "success");
		} catch (Exception e) {
			logger.error("", e);
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), obj.toString());
	}

	/**
	 * 显示广告位列表;
	 */
	@SuppressWarnings("unchecked")
	public void showAll() {
		try {
			ExtLimit limit = (ExtLimit) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(),
					ExtLimit.class);
			Advposition advposition = (Advposition) EntityReflect.createObjectFromRequest(ServletActionContext
					.getRequest(), Advposition.class);
			advposition.setCreator(this.validationUserGroup());
			advposition.setExtLimit(limit);
			int count = advpositionService.selectLimitCount(advposition);
			List<Advposition> advpositionList = advpositionService.selectByLimit(advposition);
			AjaxOut.responseJSonGrid(ServletActionContext.getResponse(), advpositionList, count, new SimpleDateFormat(
					"yyyy-MM-dd"));
		} catch (Exception e) {
			logger.error("", e);
		}
	}

	/**
	 * 获得广告位列表
	 */
	public void getAdvpositionList() {
		List<Advposition> list = advpositionService.selectAdvposition();
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		for (int i = 0; i < list.size(); i++) {
			Advposition advposition = list.get(i);
			sb.append("['" + advposition.getId() + "','" + advposition.getName() + "']");
			if (i < list.size() - 1)
				sb.append(",");
		}
		sb.append("]");
		AjaxOut.responseText(ServletActionContext.getResponse(), sb.toString());
	}

	/**
	 * 获得广告位规格列表
	 */
	public void getPositionsizeList() {
		try {
			String type = ServletActionContext.getRequest().getParameter("type");
			List<Positionsize> list = new ArrayList<Positionsize>();
			if (type.trim().equals(ADVPOSITION_SIZE)) {
				list = positionsizeService.getEnablePositionsize(); //  广告位规格
			} else {
				list = positionsizeService.getEnableBarsize(); // 广告条规格
			}
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
		} catch (Exception e) {
			logger.error("", e);
		}
	}

	/**
	 * 获得广告位模板列表
	 */
	public void getPostemplateList() {
		List<Postemplate> list = postemplateService.getEnablePostemplate();
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		for (int i = 0; i < list.size(); i++) {
			Postemplate postemplate = list.get(i);
			sb.append("['" + postemplate.getId() + "','" + postemplate.getName() + "']");
			if (i < list.size() - 1)
				sb.append(",");
		}
		sb.append("]");
		AjaxOut.responseText(ServletActionContext.getResponse(), sb.toString());
	}

	/**
	 * 获得广告位详细信息
	 */
	public void getAdvpostionDetail() {
		JSONObject retObj = new JSONObject();
		retObj.put("success", true);
		try {
			String id = ServletActionContext.getRequest().getParameter("id");
			Advposition advposition = (Advposition) advpositionService.selectById(TypeConverterUtil.parseInt(id));
			retObj.put("data", advposition);
		} catch (Exception e) {
			logger.error("", e);
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), retObj.toString());
	}

	/**
	 * 删除广告位;
	 */
	public void delete() {
		JSONObject retObj = new JSONObject();
		retObj.put("success", true);
		try {
			String ids = ServletActionContext.getRequest().getParameter("advpositionList");
			String[] idList = StringUtils.split(ids, ",");
			List<Advbar> list = new ArrayList<Advbar>();
			for (int i = 0; i < idList.length; i++) {
				list = advbarService.selectAdvbarByPosId(TypeConverterUtil.parseInt(idList[i]));
				if (list.isEmpty()) {
					advpositionService.deleteAndMemory(TypeConverterUtil.parseInt(idList[i]));
					retObj.put("result", "success");
				} else {
					retObj.put("result", "use");
					break;
				}
			}
		} catch (Exception e) {
			logger.error("", e);
			retObj.put("result", "error");
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), retObj.toString());
	}

	/**
	 * 验证广告位名称是否唯一(非删除状态下)
	 */
	public void checkAdvpositionName() {
		String alertMessage = null;
		try {
			String name = ServletActionContext.getRequest().getParameter("name");
			if (name.trim().equals("")) {
				alertMessage = "empty";
			} else {
				List<Advposition> advpositionList = advpositionService.selectAdvpositionByName(name.trim());
				if (advpositionList.size() > 1) {
					alertMessage = "use";
				} else {
					alertMessage = "success";
				}
			}
		} catch (Exception e) {
			logger.error("", e);
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), alertMessage);
	}

	/**
	 * 显示频道列表列表
	 */
	public void getChannelList() {
		List<Channel> list = channelService.selectChannel();
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		for (int i = 0; i < list.size(); i++) {
			Channel channel = list.get(i);
			sb.append("['" + channel.getChannelId() + "','" + channel.getName() + "']");
			if (i < list.size() - 1)
				sb.append(",");
		}
		sb.append("]");
		AjaxOut.responseText(ServletActionContext.getResponse(), sb.toString());
	}

	/**
	 * 显示网站列表
	 */
	public void getSiteList() {
		List<Site> list = siteService.selectSite();
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		for (int i = 0; i < list.size(); i++) {
			Site site = list.get(i);
			sb.append("['" + site.getSiteId() + "','" + site.getSiteName() + "']");
			if (i < list.size() - 1)
				sb.append(",");
		}
		sb.append("]");
		AjaxOut.responseText(ServletActionContext.getResponse(), sb.toString());
	}

	/**
	 * 更改广告位状态
	 */
	public void updateStatus() {
		JSONObject retObj = new JSONObject();
		retObj.put("success", true);
		try {
			int id = Integer.parseInt(ServletActionContext.getRequest().getParameter("id"));
			int status = Integer.parseInt(ServletActionContext.getRequest().getParameter("status"));

			Advposition advpositionTemp = (Advposition) advpositionService.selectById(id);
			advpositionTemp.setModifier(this.getLoginUser().getUsername());
			advpositionTemp.setModifyTime(new Date());
			advpositionTemp.setStatus(status);

			List<Advbar> list = advpositionService.selectAdvbarByAdvpositionId(id);
			if (status == BussinessStatus.ADVPOSITION_START) { // 启用状态
				if (list.size() >= advpositionTemp.getNum()) {
					int i = 0;
					for (Advbar advbar : list) {
						if (advbar.getStatus() == 0)
							i++;
					}
					if (i < list.size()) {
						retObj.put("result", "statusStart"); // 需要将广告条状态设为启用
					} else {
						advpositionService.updateAndMemory(advpositionTemp);
						retObj.put("result", "success");
					}
				} else {
					retObj.put("result", "under"); // 广告条数量小于广告位规定广告条数量
				}
			}

			if (status == BussinessStatus.ADVPOSITION_STOP) { // 3为停用状态
				for (Advbar advbar : list) {
					advbar.setStatus(2); // 将广告位下的所有广告条变更为停用状态;
					advbarService.updateAndMemory(advbar);
					retObj.put("result", "success");
				}
			}
		} catch (Exception e) {
			logger.error("", e);
			retObj.put("result", "error");
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), retObj.toString());
	}

	/**
	 * 针对广告条模块中对广告位的定制查询
	 */
	public void getAdvpositionDetailInAdvbar() {
		JSONObject retObj = new JSONObject();
		retObj.put("success", true);
		try {
			String id = ServletActionContext.getRequest().getParameter("id");
			Advposition advposition = advpositionService.selectAdvpositionInAdvbar(TypeConverterUtil.parseInt(id));
			retObj.put("data", EntityReflect.getObjectJSonString(advposition, new SimpleDateFormat("yyyy-MM-dd")));
		} catch (Exception e) {
			logger.error("", e);
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), retObj.toString());
	}

	/**
	 * 通过pageType联动得到postemType
	 */
	public void getPostemTypeByPageType() {
		try {
			String dataName = java.net.URLDecoder.decode(ServletActionContext.getRequest().getParameter("dataName"),
					"UTF-8");
			List<BaseData> baseDataList = advpositionService.selectPostemTypeByTypeValue(dataName);
			StringBuffer sb = new StringBuffer();
			sb.append("[");
			for (int i = 0; i < baseDataList.size(); i++) {
				BaseData baseData = baseDataList.get(i);
				sb.append("['" + baseData.getDataValue() + "','" + baseData.getDataName() + "']");
				if (i < baseDataList.size() - 1)
					sb.append(",");
			}
			sb.append("]");
			AjaxOut.responseText(ServletActionContext.getResponse(), sb.toString());
		} catch (Exception e) {
			logger.error("", e);
		}
	}

	/**
	 * 通过模板种类获得对应的模板
	 */
	public void getAdvpositionPostemplateType() {
		try {
			String dataType = ServletActionContext.getRequest().getParameter("dataType");
			String type = ServletActionContext.getRequest().getParameter("type");
			Postemplate postemplate1 = new Postemplate();
			postemplate1.setType(TypeConverterUtil.parseInt(type));
			postemplate1.setPtype(dataType);
			
			List<Postemplate> postemplateList = advpositionService.selectPostemplateByType(postemplate1);
			StringBuffer sb = new StringBuffer();
			sb.append("[");
			for (int i = 0; i < postemplateList.size(); i++) {
				Postemplate postemplate = postemplateList.get(i);
				sb.append("['" + postemplate.getId() + "','" + postemplate.getName() + "']");
				if (i < postemplateList.size() - 1)
					sb.append(",");
			}
			sb.append("]");
			AjaxOut.responseText(ServletActionContext.getResponse(), sb.toString());
		} catch (Exception e) {
			logger.error("", e);
		}
	}
	
	/**
	 * 查询同一频道下的广告位名称是否重复
	 * 
	 */
	public void vldAdvNameRepeated() {
		JSONObject obj = new JSONObject();
		obj.put("success", true);
		try {
			String advId = ServletActionContext.getRequest().getParameter("id");
			int channelId = Integer.parseInt(ServletActionContext.getRequest().getParameter("channelId"));
			String name = ServletActionContext.getRequest().getParameter("name");
			
			Advposition advposition = advpositionService.selectAdvNameIsRepeated(channelId, name);
			// 没有重复记录, 可以新增
			if (null == advposition) {
				obj.put("result", "success");
			}
			
			// 当记录重复时,判断是否是同一条记录
			if (null != advposition) {
				if (!("").equals(advId) && advposition.getId() == Integer.parseInt(advId))
					obj.put("result", "success");
				else
					obj.put("result", "use");
			}
			
		} catch (Exception e) {
			logger.error("", e);
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), obj.toString());
	}
	
	/**********************************GETTER/SETTER**************************************/
	public AdvpositionService getAdvpositionService() {
		return advpositionService;
	}

	public void setAdvpositionService(AdvpositionService advpositionService) {
		this.advpositionService = advpositionService;
	}

	public AdvbarService getAdvbarService() {
		return advbarService;
	}

	public void setAdvbarService(AdvbarService advbarService) {
		this.advbarService = advbarService;
	}

	public PositionsizeService getPositionsizeService() {
		return positionsizeService;
	}

	public void setPositionsizeService(PositionsizeService positionsizeService) {
		this.positionsizeService = positionsizeService;
	}

	public PostemplateService getPostemplateService() {
		return postemplateService;
	}

	public void setPostemplateService(PostemplateService postemplateService) {
		this.postemplateService = postemplateService;
	}

	public ChannelService getChannelService() {
		return channelService;
	}

	public void setChannelService(ChannelService channelService) {
		this.channelService = channelService;
	}

	public SiteService getSiteService() {
		return siteService;
	}

	public void setSiteService(SiteService siteService) {
		this.siteService = siteService;
	}

	public String getPosDivId() {
		return posDivId;
	}

	public void setPosDivId(String posDivId) {
		this.posDivId = posDivId;
	}
}
