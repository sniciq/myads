 package com.ku6ads.struts.advflight;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.ku6ads.dao.iface.advflight.AdvRelationBookDao;
import com.ku6ads.services.iface.advflight.AdvRelationBookService;
import com.ku6ads.util.AjaxOut;
import com.opensymphony.xwork2.ActionSupport;

public class AdvRelationProductBookAction extends ActionSupport {

	private static final long serialVersionUID = -909070053037756809L;
	private Logger logger = Logger.getLogger(AdvRelationProductBookAction.class);
	
	@Resource(name="AdvRelationBookDao")
	private AdvRelationBookDao advRelationBookDao;
	
	@Resource(name="AdvRelationBookService")
	private AdvRelationBookService advRelationBookService;
	
	public void getRelationProductBook() {
		try {
			int advertisementId = Integer.parseInt(ServletActionContext.getRequest().getParameter("advertisementId"));
			int bartemplateId = Integer.parseInt(ServletActionContext.getRequest().getParameter("bartemplateId"));
			String relationType = ServletActionContext.getRequest().getParameter("relationType");
			
			JSONArray array = new JSONArray();
			String node = ServletActionContext.getRequest().getParameter("node");
			if(node.equals("0")) {//第一层结点！查询排期包，B类产品排期包
				HashMap paramMap = new HashMap();
				paramMap.put("advertisementId", advertisementId);
				paramMap.put("bartemplateId", bartemplateId);
				paramMap.put("type", relationType);
				List<AdvRelationBookForm> list = advRelationBookDao.selectRelationProduct(paramMap);
				for(int i = 0; i < list.size(); i++) {
					AdvRelationBookForm ety = list.get(i);
					JSONObject obj = new JSONObject();
					obj.put("id", ety.getId());
					String count = "";
					if(ety.getUnRelationBookCount() != null) 
						count += ety.getUnRelationBookCount();
					
					obj.put("text", ety.getAdvproductName() + "_" + ety.getUseTypeName() + "_未关联数(" + count + ")");
					obj.put("leaf", false);
					obj.put("checked", false);
					array.add(obj);
				}
			}
			else {//查询子结点
				HashMap paramMap = new HashMap();
				int bookPaackageId = Integer.parseInt(node);
				paramMap.put("advertisementId", advertisementId);
				paramMap.put("bartemplateId", bartemplateId);
				paramMap.put("bookPackageId", bookPaackageId);
				List<BookForm> list = advRelationBookDao.selectRelationProductBooks(paramMap);
				
				SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd");
				
				for(int i = 0; i < list.size(); i++) {
					BookForm ety = list.get(i);
					JSONObject obj = new JSONObject();
					obj.put("id", ety.getId());
					obj.put("text", ety.getSaleTypeName() + "_" + df.format(ety.getStartTime()) + "_" + ety.getFlightNum() + "_已关联广告(" + ety.getRelationAdsIds() + ")");
					obj.put("leaf", true);
					if(ety.getRelationId() != null)
						obj.put("checked", true);
					else
						obj.put("checked", false);
					array.add(obj);
				}
			}

			AjaxOut.responseText(ServletActionContext.getResponse(), array.toString());
		}
		catch (Exception e) {
			logger.error("", e);
		}
	}
	
	public void save() {
		JSONObject obj = new JSONObject();
		obj.put("success",true);
		try {
			int advertisementId = Integer.parseInt(ServletActionContext.getRequest().getParameter("advertisementId"));
			int bartemplateId = Integer.parseInt(ServletActionContext.getRequest().getParameter("bartemplateId"));
			String selectNodes = ServletActionContext.getRequest().getParameter("selectNodes");
			advRelationBookService.save(advertisementId, bartemplateId, StringUtils.split(selectNodes, ","));
			obj.put("result", "success");
		}
		catch (Exception e) {
			logger.error("广告关联排期查保存错误！", e);
			obj.put("result", "error");
			obj.put("info", "广告关联排期查保存错误！");
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), obj.toString());
	}
}
