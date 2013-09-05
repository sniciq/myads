package com.ku6ads.struts.advflight;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import com.ku6ads.dao.entity.advflight.Advertisement;
import com.ku6ads.dao.entity.sysconfig.UserGroup;
import com.ku6ads.dao.entity.sysconfig.UserInfoEty;
import com.ku6ads.services.iface.advflight.AdvMaterialService;
import com.ku6ads.services.iface.advflight.AdvertisementService;
import com.ku6ads.services.iface.sysconfig.GroupService;
import com.ku6ads.struts.basic.BaseAction;
import com.ku6ads.util.AjaxOut;
import com.ku6ads.util.EntityReflect;
import com.ku6ads.util.ExtLimit;
import com.ku6ads.util.TypeConverterUtil;

/**
 * 广告
 * @author liyonghui
 *
 */
public class AdvertisementAction extends BaseAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = -789499582251234982L;
	private AdvMaterialService advMaterialService;
	private AdvertisementService advertisementService;
	private GroupService groupService;
	private Logger logger = Logger.getLogger(AdvertisementAction.class);
	
	public AdvMaterialService getAdvMaterialService() {
		return advMaterialService;
	}

	public void setAdvMaterialService(AdvMaterialService advMaterialService) {
		this.advMaterialService = advMaterialService;
	}
	/**
	 * 新增广告
	 */
	public void save() {
		JSONObject obj = new JSONObject();
		obj.put("success", true);
		try {
			String urlDataAry = ServletActionContext.getRequest().getParameter("urlDataAry");
			Advertisement advertisement = (Advertisement) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(),
					Advertisement.class);
			int stat = advertisement.getStatus();
			if("http://".equalsIgnoreCase(advertisement.getMonition())||advertisement.getMonition().length()<7){
				advertisement.setMonition(null);
			}
			if("http://".equalsIgnoreCase(advertisement.getRedirect())||advertisement.getRedirect().length()<7){
				advertisement.setRedirect((null));
			}
			//advertisement.setPriority(3);
			//advertisement.setWeight(10);
			advertisement.setAdvparUrl(urlDataAry);
			if (advertisement.getId() == null) {
				advertisement.setCreateTime(new Date());
				advertisement.setStatus(stat);
				int advId = advertisementService.insertAdv(advertisement);
				List<String> l = new ArrayList<String>();
				String strs[] = urlDataAry.split(",");
				for(String s:strs){
					if(s.contains("image"))
						l.add("image");
					else if(s.contains("video"))
						l.add("video");
					else if(s.contains("flash"))
						l.add("video");
					else if(s.contains("text"))
						l.add("text");
					else if(s.contains("all"))
						l.add("all");
				}
				advMaterialService.insert(l, advId);
			} else {
				advertisement.setModifyTime(new Date());
				advertisementService.updateById(advertisement);
			}
			obj.put("result", "success");
		} catch (Exception e) {
			e.printStackTrace();
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), obj.toString());
	}

	/**
	 * 获得广告列表(首页分页显示列表)
	 */
	@SuppressWarnings("unchecked")
	public void showAll() {
		try {
			String aid = ServletActionContext.getRequest().getParameter("advActiveId");
			if(aid!=null){
				Integer advActiveId = Integer.parseInt(aid);
				ExtLimit limit = (ExtLimit) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), ExtLimit.class);
				Advertisement advertisement = (Advertisement) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), Advertisement.class);
				advertisement.setExtLimit(limit);
				advertisement.setAdvActiveId(advActiveId);
				int count = advertisementService.selectLimitCount(advertisement);
				List<Advertisement> advertisementList = advertisementService.selectByLimit(advertisement);
				AjaxOut.responseJSonGrid(ServletActionContext.getResponse(), advertisementList, count, new SimpleDateFormat("yyyy-MM-dd"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获得广告活动详细信息
	 */
	public void getDetail() {
		JSONObject retObj = new JSONObject();
		retObj.put("success", true);
		try {
			String id = ServletActionContext.getRequest().getParameter("id");
			Advertisement advertisement = (Advertisement) advertisementService.selectById(Integer.parseInt(id));
			//判断是否为管理员
			UserInfoEty user = this.getLoginUser();
			String userName = user.getUsername();
			List<UserGroup>  UGList = groupService.selectUserGroupByUserName(userName);
			for(int i =0;i<UGList.size();i++){
				if(UGList.get(i).getStatus()==3){
					advertisement.setIsAdmin(1);
				} 
			}
			String s = EntityReflect.getObjectJSonString(advertisement, new SimpleDateFormat("yyyy-MM-dd"));
			retObj.put("data", s);
		} catch (Exception e) {
			logger.error("", e);
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), retObj.toString());
	}
	
	/**
	 * 获得广告列表
	 */
	public void getAdvertisementList() {
		Integer advactiveId = Integer.parseInt(ServletActionContext.getRequest().getParameter("id"));
		List<Advertisement> advertisementList = advertisementService.selectAdvertisement(advactiveId);
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		for (int i = 0; i < advertisementList.size(); i++) {
			Advertisement advertisement = advertisementList.get(i);
			sb.append("['" + advertisement.getId() + "','" + advertisement.getName() + "']");
			if (i < advertisementList.size() - 1)
				sb.append(",");
		}
		sb.append("]");
		AjaxOut.responseText(ServletActionContext.getResponse(), sb.toString());
	}
	
	/**
	 * 获得当前广告的广告条模板信息列表
	 */
	public void getTemplateList(){
		try {
			String id = ServletActionContext.getRequest().getParameter("id");
			if(id!=null){
				int aid = Integer.parseInt(id);
				Advertisement advertisement = (Advertisement) advertisementService.selectById(aid);
				JSONArray dataArray = JSONArray.fromObject(advertisement.getAdvparUrl());
				AjaxOut.responseJSonGrid(ServletActionContext.getResponse(), dataArray);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void delete() {
		JSONObject retObj = new JSONObject();
		retObj.put("success", true);
		try {
			String id = ServletActionContext.getRequest().getParameter("id");
			advertisementService.deleteById(TypeConverterUtil.parseInt(id));
			retObj.put("result", "success");
		} catch (Exception e) {
			logger.error("", e);
			retObj.put("result", "error");
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), retObj.toString());
	}

	


	//++++++++++++++++GETTER SETTER++++++++++++++++++//
	public AdvertisementService getAdvertisementService() {
		return advertisementService;
	}

	public void setAdvertisementService(AdvertisementService advertisementService) {
		this.advertisementService = advertisementService;
	}

	public GroupService getGroupService() {
		return groupService;
	}

	public void setGroupService(GroupService groupService) {
		this.groupService = groupService;
	}
	
}
