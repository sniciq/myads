package com.ku6ads.struts.advert;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.ku6ads.dao.entity.advert.Positionsize;
import com.ku6ads.dao.entity.sysconfig.UserInfoEty;
import com.ku6ads.services.iface.advert.AdvpositionService;
import com.ku6ads.services.iface.advert.PositionsizeService;
import com.ku6ads.struts.basic.BaseAction;
import com.ku6ads.util.AjaxOut;
import com.ku6ads.util.EntityReflect;
import com.ku6ads.util.ExtLimit;
import com.ku6ads.util.TypeConverterUtil;

/**
 * 
 * @author liujunshi
 * 
 */
public class PositionsizeAction extends BaseAction {

	private static final long serialVersionUID = -4909029991028983773L;
	private PositionsizeService positionsizeService;
	private AdvpositionService advpositionService;
	public static final int POSITIONSIZE_TYPE = 1;

	/**
	 * 新增广告规格
	 */
	public void save() {
		JSONObject obj = new JSONObject();
		obj.put("success", true);
		try {
			Positionsize positionsize = (Positionsize) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), Positionsize.class);
			UserInfoEty user = this.getLoginUser();
			if (positionsize.getId() == null) {
				List<Positionsize> listByName = positionsizeService.getSizebyName(positionsize);
				//根据名称判断重复
				if(listByName==null || listByName.isEmpty()){
					List<Positionsize> list = positionsizeService.getSizeWithNotName(positionsize);
					if(list==null || list.isEmpty()){	
						positionsize.setCreator(user.getUsername());
						positionsize.setCreateTime(new Date());
						positionsize.setModifier(user.getUsername());
						positionsize.setModifyTime(new Date());
						positionsizeService.insert(positionsize);
						obj.put("result", "success");
					}else{
					obj.put("result", "multiple");
					}
				}else{
				obj.put("result", "nameMul");
				}
			}else {
				
				List<Positionsize> listByName = positionsizeService.getSizebyName(positionsize);
				//根据名称判断重复
				if(listByName==null || listByName.isEmpty()||listByName.get(0).getId().equals(positionsize.getId())){
					positionsize.setModifier(user.getUsername());
					positionsize.setModifyTime(new Date());
					List<Positionsize> list = positionsizeService.getSizeWithNotName(positionsize);
					if(list==null ||list.isEmpty()||list.get(0).getId().equals(positionsize.getId())){
						positionsizeService.updateById(positionsize);
						obj.put("result", "success");
					}else{
						obj.put("result", "multiple");
					}
				}else{
					obj.put("result", "nameMul");
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			obj.put("result", "error");
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), obj.toString());
	}

	/**
	 * 获得广告规格列表;
	 */
	@SuppressWarnings("unchecked")
	public void showAll() {
		try {
			ExtLimit limit = (ExtLimit) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), ExtLimit.class);
			Positionsize positionsize = (Positionsize) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), Positionsize.class);
			positionsize.setExtLimit(limit);
			int count = positionsizeService.selectLimitCount(positionsize);
			List<Positionsize> advpositionList = positionsizeService.selectByLimit(positionsize);
			AjaxOut.responseJSonGrid(ServletActionContext.getResponse(), advpositionList, count, new SimpleDateFormat("yyyy-MM-dd"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获得广告规格详细信息
	 */
	public void getPositionsizeDetail() {
		JSONObject retObj = new JSONObject();
		retObj.put("success", true);
		try {
			String id = ServletActionContext.getRequest().getParameter("id");
			Positionsize positionsize = (Positionsize) positionsizeService.selectById(TypeConverterUtil.parseInt(id));
			retObj.put("data", positionsize);
		} catch (Exception e) {
			e.printStackTrace();
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), retObj.toString());
	}

	/**
	 * 删除广告规格;
	 */
	public void delete() {
		JSONObject retObj = new JSONObject();
		retObj.put("success", true);
		List listPos = null;
		boolean flagUse = false;
		try {
			String ids = ServletActionContext.getRequest().getParameter("positionsizeList");
			String[] idList = ids.split(",");
			for (int i = 0; i < idList.length; i++) {
				int id = TypeConverterUtil.parseInt(idList[i]);

				listPos = advpositionService.selectAdvpositionByPositionsizeId(id);
				if (listPos == null || listPos.isEmpty()) {
					positionsizeService.deleteById(id);
				} else {
					flagUse = true;
					continue;
				}
			}
			if (flagUse) {
				retObj.put("result", "use");
			} else {
				retObj.put("result", "success");
			}
		} catch (Exception e) {
			e.printStackTrace();
			retObj.put("result", "error");
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), retObj.toString());
	}

	public PositionsizeService getPositionsizeService() {
		return positionsizeService;
	}

	public void setPositionsizeService(PositionsizeService positionsizeService) {
		this.positionsizeService = positionsizeService;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public AdvpositionService getAdvpositionService() {
		return advpositionService;
	}

	public void setAdvpositionService(AdvpositionService advpositionService) {
		this.advpositionService = advpositionService;
	}

}
