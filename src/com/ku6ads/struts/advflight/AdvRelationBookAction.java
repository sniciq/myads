package com.ku6ads.struts.advflight;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.ku6ads.dao.entity.advflight.Advertisement;
import com.ku6ads.dao.entity.advflight.Book;
import com.ku6ads.services.iface.advflight.AdvRelationBookService;
import com.ku6ads.services.iface.advflight.BookService;
import com.ku6ads.util.AjaxOut;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 广告关联排期Action
 * @author yangHanguang
 *
 */
public class AdvRelationBookAction extends ActionSupport {
	private static final long serialVersionUID = 6139104992828877633L;
	
	@Resource(name="AdvRelationBookService")
	private AdvRelationBookService advRelationBookService;
	
	@Resource(name="BookService")
	private BookService bookService;
	
	private Logger logger = Logger.getLogger(AdvRelationBookAction.class);
	
	@SuppressWarnings("unchecked")
	public void getAdvBooks() {
		try {
			JSONArray array = new JSONArray();
			String node = ServletActionContext.getRequest().getParameter("node");
			if(node.equals("0")) {//查询一级结点, text:"频道名称_广告条名称_使用类型",
				HashMap paramMap = new HashMap();
				int advertisementId = Integer.parseInt(ServletActionContext.getRequest().getParameter("advertisementId"));
				int bartemplateId = Integer.parseInt(ServletActionContext.getRequest().getParameter("bartemplateId"));
				paramMap.put("advertisementId", advertisementId);
				paramMap.put("bartemplateId", bartemplateId);
				List<AdvRelationBookForm> list = advRelationBookService.getRelationBookPackages(paramMap);
				for(int i = 0; i < list.size(); i++) {
					AdvRelationBookForm ety = list.get(i);
					JSONObject obj = new JSONObject();
					obj.put("id", ety.getId());
					String count = "";
					if(ety.getUnRelationBookCount() != null) 
						count += ety.getUnRelationBookCount();
					
					obj.put("text", ety.getChannelName() + "_" + ety.getAdvbarName() + "_" + ety.getUseTypeName() + "_未关联数(" + count + ")");
					obj.put("leaf", false);
					obj.put("checked", false);
					array.add(obj);
				}
			}
			else {//查询子结点
				HashMap paramMap = new HashMap();
				int advertisementId = Integer.parseInt(ServletActionContext.getRequest().getParameter("advertisementId"));
				int bartemplateId = Integer.parseInt(ServletActionContext.getRequest().getParameter("bartemplateId"));
				int bookPaackageId = Integer.parseInt(node);
				paramMap.put("advertisementId", advertisementId);
				paramMap.put("bartemplateId", bartemplateId);
				paramMap.put("bookPackageId", bookPaackageId);
				List<BookForm> list = advRelationBookService.getRelationBooks(paramMap);
				
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
			logger.error("广告关联排期查询错误！", e);
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
	
	/**
	 * 查询排期关联广告
	 */
	public void getAdvflightRelationAdv() {
		try {
			String advflightId = ServletActionContext.getRequest().getParameter("advflightId");
			
			Book book = new Book();
			book.setBookPackageId(Integer.parseInt(advflightId));
			
			List<Book> bookList = bookService.selectByProjectId(book);
			List<Advertisement> advertisementList = new ArrayList<Advertisement>();
			for (Book book2 : bookList) {
				List<Advertisement> advertisementListTmp = advRelationBookService.selectAdvflightRelationAdv(book2.getId());
				for (Advertisement advertisement : advertisementListTmp) {
					advertisementList.add(advertisement);
				}
			}
			AjaxOut.responseJSonGrid(ServletActionContext.getResponse(), advertisementList, 0, new SimpleDateFormat("yyyy-MM-dd"));
		} catch (Exception e) {
			logger.error("", e);
		}
	}
}
