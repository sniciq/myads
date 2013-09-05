package com.ku6ads.struts.book;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.ku6ads.dao.entity.advflight.Book;
import com.ku6ads.dao.entity.advflight.BookPackage;
import com.ku6ads.dao.entity.advproduct.AdvproductEty;
import com.ku6ads.dao.entity.sysconfig.UserInfoEty;
import com.ku6ads.dao.iface.advflight.BookDao;
import com.ku6ads.dao.iface.advflight.BookPackageDao;
import com.ku6ads.dao.iface.advflight.ProjectDao;
import com.ku6ads.dao.iface.advproduct.AdvproductDao;
import com.ku6ads.services.iface.advflight.BookAdvproductService;
import com.ku6ads.services.iface.advflight.BookPackageService;
import com.ku6ads.services.iface.advflight.BookService;
import com.ku6ads.services.impl.advflight.BookCommon;
import com.ku6ads.services.impl.advflight.ConflictExcption;
import com.ku6ads.struts.advflight.ProjectForm;
import com.ku6ads.struts.basic.BaseAction;
import com.ku6ads.util.AjaxOut;
import com.ku6ads.util.BussinessStatus;
import com.ku6ads.util.EntityReflect;
import com.ku6ads.util.ExtLimit;
import com.ku6ads.util.TypeConverterUtil;

/**
 * 排期处理
 * 
 * @author yangHanguang
 * 
 */
public class BookAction extends BaseAction {

	@Resource(name="BookPackageService")
	private BookPackageService bookPackageService;
	
	@Resource(name="BookPackageDao")
	private BookPackageDao bookPackageDao;
	
	@Resource(name="BookAdvproductService")
	private BookAdvproductService bookAdvproductService;
	
	@Resource(name="BookService")
	private BookService bookService;
	
	@Resource(name="ProjectDao")
	private ProjectDao projectDao;
	
	@Resource(name="BookDao")
	private BookDao bookDao;
	
	@Resource(name="AdvproductDao")
	private AdvproductDao advproductDao;

	private static final long serialVersionUID = -6527903161213965912L;
	private Logger logger = Logger.getLogger(BookAction.class);
	
	public void getProjectInfo() {
		JSONObject obj = new JSONObject();
		obj.put("success",true);
		try {
			String id = ServletActionContext.getRequest().getParameter("id");
			ProjectForm project = projectDao.selectProjectDetailById(Integer.parseInt(id));
			String s = EntityReflect.getObjectJSonString(project, new SimpleDateFormat("yyyy-MM-dd"));
			obj.put("data", s);
		}
		catch (Exception e) {
			logger.error("执行单基本信息查询错误！", e);
			obj.put("result","error");
			obj.put("info",e.getMessage());
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), obj.toString());
	}

	/**
	 * 排期保存
	 */
	public void save() {
		JSONObject obj = new JSONObject();
		obj.put("success",true);
		try {
			BookPackage bookPackage = (BookPackage) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), BookPackage.class);
			String areaDataAry = ServletActionContext.getRequest().getParameter("areaDataAry");
			String pointDataAry = ServletActionContext.getRequest().getParameter("pointDataAry");
			JSONArray areaDataArray = JSONArray.fromObject(areaDataAry);
			JSONArray pointDataArray = JSONArray.fromObject(pointDataAry);
			
			
			String hourDirect = bookPackage.getHourDirect();
			String[] hourDirectArr = StringUtils.split(hourDirect, ",");
			hourDirect = StringUtils.join(hourDirectArr, ",");
			bookPackage.setHourDirect(hourDirect);
			
			Map<String, Object> retMap = new HashMap<String, Object>();
			//生成地域定向编码
			if(areaDataArray != null && areaDataArray.size() > 0) {
				JSONObject jsonObj = null; 
				StringBuffer areaDirectCode = new StringBuffer();
				for(int i = 0; i < areaDataArray.size(); i++) {
					jsonObj = JSONObject.fromObject(areaDataArray.get(i));
					if(jsonObj.isNullObject())
						continue;
					
					if(jsonObj.containsKey("pcode") && jsonObj.containsKey("dcode")) {//城市
						areaDirectCode.append(jsonObj.get("pcode") + "|" + jsonObj.get("dcode"));
					}
					else if(jsonObj.containsKey("pcode")) {//省份
						areaDirectCode.append(jsonObj.get("pcode"));
					}
					
					if(i < areaDataArray.size() - 1)
						areaDirectCode.append(",");
				}
				bookPackage.setAreaDirectCode(areaDirectCode.toString());
			}
			bookPackage.setAreaDirect(areaDataArray.toString());
			
			//频次定向
			if(bookPackage.getIsFrequency() == null || bookPackage.getIsFrequency() != 1) {
				bookPackage.setIsFrequency(0);
				bookPackage.setFrequencyNum(0);
				bookPackage.setFrequencyType(0);
			}
			
			String priorityStr = ServletActionContext.getRequest().getParameter("paramPriority");
			if(priorityStr != null && !priorityStr.trim().equals("")) {
				bookPackage.setPriority(Integer.parseInt(priorityStr));
			}
			else if(bookPackage.getIsContentDirect() != null && bookPackage.getIsContentDirect() == 1)
				bookPackage.setPriority(5);
			else
				bookPackage.setPriority(3);
			
			double proportion = BookCommon.createBPkgProportion(bookPackage);
			bookPackage.setProportion(proportion);
			
			String bookPackageId = ServletActionContext.getRequest().getParameter("bookPackageId");
			if(bookPackageId == null || bookPackageId.trim().equals("")) {//保存
				bookPackage.setCreator(getLoginUser().getUsername());
				try {
					Date date = new Date();
					bookPackage.setCreateTime(date);
					bookPackage.setStatus(0);
					bookPackage.setBussinessStatus(BussinessStatus.draft);
					
					if(bookPackage.getIsContentDirect() == null || bookPackage.getIsContentDirect() != 1) {
						bookPackage.setKeyword(null);
						bookPackage.setUser(null);
						bookPackage.setVideo(null);
						bookPackage.setProgram(null);
						bookPackage.setActivity(null);
						bookPackage.setSubject(null);
					}
					
					if(bookPackage.getBookpackageType() == 1) {
						retMap = bookPackageService.saveBookPackage(bookPackage, pointDataArray);
					}
					else if(bookPackage.getBookpackageType() == 2)  {
						retMap = bookAdvproductService.saveBookPackage(bookPackage, pointDataArray);
					}
				}
				catch (ConflictExcption ce) {//排期有冲突
					retMap.put("info", ce.getMessage());
					retMap.putAll(ce.getConflictMap());
				}
				catch (Exception e) {
					logger.error("保存排期包错误", e);
					retMap.put("result", "error");
					retMap.put("info", "保存排期包错误!" + e.getMessage());
				}
			}
			else {//修改
				bookPackage.setId(Integer.parseInt(bookPackageId));
				bookPackage.setModifier(getLoginUser().getUsername());
				try {
					Date date = new Date();
					bookPackage.setModifyTime(date);
					if(bookPackage.getBookpackageType() == 1) {
						retMap = bookPackageService.updateBookPackage(bookPackage, pointDataArray);
					}
					else {
						retMap = bookAdvproductService.updateBookPackage(bookPackage, pointDataArray);
					}
				}
				catch (ConflictExcption ce) {//排期有冲突
					retMap.put("info", ce.getMessage());
					retMap.putAll(ce.getConflictMap());
				}
				catch (Exception e) {
					logger.error("修改排期包错误", e);
					retMap.put("result", "error");
					retMap.put("info", "修改排期包错误" + e.getMessage());
				}
			}
			obj.put("result", retMap.get("result"));
			obj.put("info", retMap.get("info"));
			obj.put("detailInfo", retMap);
		} catch (Exception e) {
			logger.error("排期保存错误！", e);
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), obj.toString());
	}
	
	/**
	 * 修改优先级
	 */
	public void updatePriority() {
		JSONObject obj = new JSONObject();
		obj.put("success",true);
		try {
			String priority = ServletActionContext.getRequest().getParameter("priority");
			String bookPackageId = ServletActionContext.getRequest().getParameter("bookPackageId");
			
			BookPackage bookPackage = (BookPackage) bookPackageService.selectById(Integer.parseInt(bookPackageId)); 
			bookPackage.setPriority(Integer.parseInt(priority));
			
			bookPackage.setModifier(getLoginUser().getUsername());
			Map<String, Object> retMap = new HashMap<String, Object>();
			try {
				Date date = new Date();
				bookPackage.setModifyTime(date);
				
				if(bookPackage.getBookpackageType() == 1) {
					retMap = bookPackageService.updateBookPackagePriority(bookPackage);
				}
				else if(bookPackage.getBookpackageType() == 2)  {
					retMap = bookAdvproductService.updateBookPackagePriority(bookPackage);
				}
				//得到排期的点位
			}
			catch (ConflictExcption ce) {//排期有冲突
				retMap.put("info", ce.getMessage());
				retMap.putAll(ce.getConflictMap());
			}
			catch (Exception e) {
				logger.error("修改排期包错误", e);
				retMap.put("result", "error");
				retMap.put("info", "修改排期包错误" + e.getMessage());
			}
			obj.put("result", retMap.get("result"));
			obj.put("info", retMap.get("info"));
			obj.put("detailInfo", retMap);
		}
		catch (Exception e) {
			logger.error("修改优先级错误！", e);
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), obj.toString());
	}
	
	/**
	 * 删除排期包
	 */
	public void deleteBookPackage() {
		JSONObject obj = new JSONObject();
		obj.put("success",true);
		try {
			String bookPackageId = ServletActionContext.getRequest().getParameter("bookPackageId");
			String projectId = ServletActionContext.getRequest().getParameter("projectId");
			Map<String, Object> retMap = new HashMap<String, Object>();
			retMap = bookPackageService.deleteBookPackage(Integer.parseInt(bookPackageId), Integer.parseInt(projectId));
			obj.put("result", retMap.get("result"));
			obj.put("info", retMap.get("info"));
		}
		catch (Exception e) {
			logger.error("删除排期包错误！", e);
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), obj.toString());
	}

	/**
	 * 显示排期的所有点位
	 */
	public void getBookList() {
		try {
			String bookPackageId = ServletActionContext.getRequest().getParameter("bookPackageId");
			Book book = new Book();
			book.setBookPackageId(Integer.parseInt(bookPackageId));
			book.setStatus(0);
			
			BookPackage bookPackageEty = (BookPackage) bookPackageDao.selectById(Integer.parseInt(bookPackageId));
			List<Book> bookList = null;
			//根据不同的类型点位显示不同
			if(bookPackageEty.getAdvproductId() == null) {//广告条排期包----------直接显示点位
				bookList = bookDao.selectBookPointList(book);
			}
			else {//广告产品排期包
				AdvproductEty advPety = (AdvproductEty) advproductDao.selectById(bookPackageEty.getAdvproductId());
				if(advPety.getType().equals("A")) {//A类显示点位量的总和
					bookList = bookDao.selectBookPointListSum(book);
				}
				else if(advPety.getType().equals("B")) {//B类显示点位量的平均值
					bookList = bookDao.selectBookPointListAvg(book);
				}
			}
			
			JSONObject dataObj = new JSONObject();
			dataObj.put("total", bookList.size());
			JSONArray dataArray = new JSONArray();
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			
			Date nowTime = new Date();
			String nowTimeStr = df.format(nowTime);
			nowTime = df.parse(nowTimeStr);
			
			for (int i = 0; i < bookList.size(); i++) {
				Book bookEty = bookList.get(i);
				if(bookEty.getStartTime().before(nowTime))
					bookEty.setCanEdit(false);
				else 
					bookEty.setCanEdit(true);
				if(bookEty.getSaleTypeName().equalsIgnoreCase("CPM")) {//CPM, 转为千单位
					bookEty.setFlightNum(bookEty.getFlightNum() / 1000);
				}
				
				JSONObject obj = JSONObject.fromObject(EntityReflect.getObjectJSonString(bookEty, df));
				dataArray.add(obj);
			}
			dataObj.put("invdata", dataArray);
			AjaxOut.responseText(ServletActionContext.getResponse(), dataObj.toString());
			
//			AjaxOut.responseJSonGrid(ServletActionContext.getResponse(), bookList, count, new SimpleDateFormat("yyyy-MM-dd"));
		} catch (Exception e) {
			logger.error("显示排期的所有点位错误！", e);
		}
	}

	/**
	 * 显示所有排期包
	 */
	@SuppressWarnings("unchecked")
	public void showAllBookPackage() {
		try {
			String projectId = ServletActionContext.getRequest().getParameter("projectId");
			ExtLimit limit = (ExtLimit) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), ExtLimit.class);
			BookPackage bookPackage = new BookPackage();
			bookPackage.setProjectId(Integer.parseInt(projectId));
			bookPackage.setStatus(0);
			bookPackage.setExtLimit(limit);
			int count = bookPackageService.selectLimitCount(bookPackage);
			List<BookPackage> bookPackageList = bookPackageService.selectByLimit(bookPackage);
			AjaxOut.responseJSonGrid(ServletActionContext.getResponse(), bookPackageList, count, new SimpleDateFormat("yyyy-MM-dd"));
		} catch (Exception e) {
			logger.error("显示所有排期包错误！", e);
		}
	}

	/**
	 * 得到排期包点位预览列表
	 */
	public void getBookPackagePointInfo() {
		try {
			ExtLimit limit = (ExtLimit) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), ExtLimit.class);
			String projectId = ServletActionContext.getRequest().getParameter("projectId").trim(); // 得到执行单id
			Book book = (Book) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), Book.class);
			book.setExtLimit(limit);
			book.setProjectId(TypeConverterUtil.parseInt(projectId));
			int count = bookService.selectLimitCount(book);
			List<Book> bookList = bookService.selectByLimit(book);
			AjaxOut.responseJSonGrid(ServletActionContext.getResponse(), bookList, count, new SimpleDateFormat("yyyy-MM-dd"));
		} catch (Exception e) {
			logger.error("得到排期包点位预览列表错误！", e);
		}
	}
	
	/**
	 * 得到排期包的基本信息<br>
	 * 1. 位置<br>
	 * 2. 定向策略<br>
	 * 3. 内容策略<br>
	 */
	public void getBookPackageInfo() {
		JSONObject retObj = new JSONObject();
		retObj.put("success", true);
		try {
			String bookPackageId = ServletActionContext.getRequest().getParameter("bookPackageId");
			BookPackage bookPackage = (BookPackage) bookPackageService.selectById(Integer.parseInt(bookPackageId));
			
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date nowDate = new Date();
			String nowDateStr = df.format(nowDate);
			nowDate = df.parse(nowDateStr);
			if(bookPackage.getEndTime() != null && bookPackage.getEndTime().before(nowDate)) {//如果执行单没有未执行的，则不能修改
				retObj.put("CanSave", false);
			}
			else 
				retObj.put("CanSave", true);
			
			retObj.put("data", bookPackage);
		} catch (Exception e) {
			logger.error("得到排期包的基本信息错误！", e);
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), retObj.toString());
	}
	
	/**
	 * 得到广告产品排期包的基本信息
	 */
	public void getBookPackageAdvProductInfo() {
		JSONObject retObj = new JSONObject();
		retObj.put("success", true);
		try {
			String bookPackageId = ServletActionContext.getRequest().getParameter("bookPackageId");
			BookPackageAdvProductForm bookPackage = bookPackageDao.selectBookPackageAdvProduct(Integer.parseInt(bookPackageId));
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date nowDate = new Date();
			String nowDateStr = df.format(nowDate);
			nowDate = df.parse(nowDateStr);
			if(bookPackage.getEndTime() != null && bookPackage.getEndTime().before(nowDate)) {//如果执行单没有未执行的，则不能修改
				retObj.put("CanSave", false);
			}
			else 
				retObj.put("CanSave", true);
			
			retObj.put("data", bookPackage);
		} catch (Exception e) {
			logger.error("得到排期包的基本信息错误！", e);
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), retObj.toString());
	}
	
	/**
	 * 创建人创建的背景广告条的排期包列表
	 */
	public void getBackgroudBookPkg() {
		try {
			UserInfoEty user = this.getLoginUser();
			ExtLimit limit = (ExtLimit) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), ExtLimit.class);
//			String projectId = ServletActionContext.getRequest().getParameter("projectId").trim(); // 得到执行单id
//			bookPackage.setProjectId(Integer.parseInt(projectId));
			
			BookPackage bookPackage = (BookPackage) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), BookPackage.class);
			bookPackage.setCreator(user.getUsername());
			bookPackage.setExtLimit(limit);
			bookPackage.setStatus(0);
			
			int count = bookPackageService.selectRelationBackgroudBookPkgLimitCount(bookPackage);
			List<BookPackage> bookPakgList = bookPackageService.selectRelationBackgroudBookPkgLimit(bookPackage);
			AjaxOut.responseJSonGrid(ServletActionContext.getResponse(), bookPakgList, count, new SimpleDateFormat("yyyy-MM-dd"));
		}
		catch (Exception e) {
			logger.error("查询创建人创建的背景广告条的排期包列表错误！", e);
		}
	}
}
