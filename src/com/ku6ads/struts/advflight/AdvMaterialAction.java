package com.ku6ads.struts.advflight;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.ku6ads.dao.entity.advflight.AdvMaterial;
import com.ku6ads.dao.entity.sysconfig.UserInfoEty;
import com.ku6ads.services.iface.advflight.AdvMaterialService;
import com.ku6ads.services.iface.advflight.AdvertisementService;
import com.ku6ads.struts.basic.BaseAction;
import com.ku6ads.util.AjaxOut;
import com.ku6ads.util.EntityReflect;
import com.ku6ads.util.ExtLimit;
import com.ku6ads.util.TypeConverterUtil;

/**
 * 关联物料
 * @author liujunshi
 *
 */
public class AdvMaterialAction extends BaseAction{
	AdvMaterialService advMaterialService;
	AdvertisementService advertisementService;
	private Logger logger = Logger.getLogger(AdvMaterialAction.class);
	/**
	 * 新增广告物料关系
	 */
	public void save() {
		JSONObject obj = new JSONObject();
		obj.put("success", true);
		try {
			String id = ServletActionContext.getRequest().getParameter("id");
			String advId = ServletActionContext.getRequest().getParameter("advId");
			String materialIds = ServletActionContext.getRequest().getParameter("materialIdList");
			UserInfoEty user = this.getLoginUser();
			if(advId!=null&&materialIds!=null){
				AdvMaterial advMaterial = new AdvMaterial();
				advMaterial.setAdvId(TypeConverterUtil.parseInt(advId));
				advMaterial.setId(TypeConverterUtil.parseInt(id));
				advMaterial.setModifier(user.getUsername());
				advMaterial.setModifyTime(new Date());
				String[] materialIdList = materialIds.split(",");
				
				for (int i = 0; i < materialIdList.length; i++) {
					advMaterial.setMaterialId(TypeConverterUtil.parseInt(materialIdList[i]));
					advMaterialService.updateById(advMaterial);
					
				}
				obj.put("result", "success");
			}else{
				
				obj.put("result", "error");
			}

			
		} catch (Exception e) {
			logger.error("", e);
			obj.put("result", e);
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), obj.toString());
	}

	/**
	 * 获得广告物料关系列表(首页分页显示列表)
	 */
	@SuppressWarnings("unchecked")
	public void showAll() {
		try {
			
			ExtLimit limit = (ExtLimit) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), ExtLimit.class);
			AdvMaterial advMaterial = (AdvMaterial) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), AdvMaterial.class);
			advMaterial.setExtLimit(limit);
			String advId = ServletActionContext.getRequest().getParameter("advId");
			int aid = TypeConverterUtil.parseInt(advId);
			if(aid == 0){
				return;
			}
			List<AdvMaterial> advMaterialList;
			//TODO 播放器广告
//			int count = 43;
//			int BarType = advertisementService.getBarTemType(aid);
//			//如果是播放器广告
//			if(BarType==1){
//				advMaterialList = new ArrayList();
//				AdvMaterial advMaterial1 = new AdvMaterial();
//				for(int i = 0; i<47 ;i++){
//					
//				}
//			}else{
				advMaterial.setAdvId(aid);
				int count = advMaterialService.selectLimitCount(advMaterial);
				advMaterialList = advMaterialService.selectByLimit(advMaterial);
//			}
			AjaxOut.responseJSonGrid(ServletActionContext.getResponse(), advMaterialList, count, new SimpleDateFormat("yyyy-MM-dd"));
		} catch (Exception e) {
			logger.error("", e);
		}
	}


	/**
	 * 删除广告物料关系;
	 */
	public void delete() {
		JSONObject retObj = new JSONObject();
		retObj.put("success", true);
		try {
			String ids = ServletActionContext.getRequest().getParameter("advMaterialList");
			String[] idList = ids.split(",");
			AdvMaterial advMaterial  = new AdvMaterial();
			for (int i = 0; i < idList.length; i++) {
				advMaterial.setId(TypeConverterUtil.parseInt(idList[i]));
				advMaterial.setMaterialId(0);
				advMaterial.setModifier("Rocky");
				advMaterial.setModifyTime(new Date());
				advMaterialService.updateById(advMaterial);
			}
			retObj.put("result", "success");
		} catch (Exception e) {
			logger.error("", e);
			retObj.put("result", "error");
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), retObj.toString());
	}




	//++++++++++++++++GETTER SETTER++++++++++++++++++//


	public AdvMaterialService getAdvMaterialService() {
		return advMaterialService;
	}

	public void setAdvMaterialService(AdvMaterialService advMaterialService) {
		this.advMaterialService = advMaterialService;
	}

	public AdvertisementService getAdvertisementService() {
		return advertisementService;
	}

	public void setAdvertisementService(AdvertisementService advertisementService) {
		this.advertisementService = advertisementService;
	}


	
}
