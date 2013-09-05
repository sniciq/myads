package com.ku6ads.struts.statistics;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.ku6ads.dao.entity.advflight.BookPackage;
import com.ku6ads.dao.entity.advflight.Project;
import com.ku6ads.dao.iface.advflight.BookPackageDao;
import com.ku6ads.dao.iface.advflight.ProjectDao;
import com.ku6ads.util.AjaxOut;
import com.ku6ads.util.EntityReflect;
import com.ku6ads.util.ExtLimit;
import com.opensymphony.xwork2.ActionSupport;

public class StatisticsBusinessSearchAction extends ActionSupport {

	private static final long serialVersionUID = 6324153150503340312L;
	
	@Resource(name="ProjectDao")
	private ProjectDao projectDao;
	
	@Resource(name="BookPackageDao")
	private BookPackageDao bookPackageDao;
	private Logger logger = Logger.getLogger(StatisticsBusinessSearchAction.class);
	
	/** 
	 * 查询执行单
	 */
	public void searchProject() {
		try {
			Project projectEty = (Project) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), Project.class);
			ExtLimit limit = (ExtLimit) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), ExtLimit.class);
			projectEty.setExtLimit(limit);
			
			String id = ServletActionContext.getRequest().getParameter("id"); 
			if(id != null && !id.trim().equals("")) {
				projectEty.setId(Integer.parseInt(id));
			}
			
			int count = projectDao.selectLimitCount(projectEty);
			List dataList = projectDao.selectByLimit(projectEty);
			AjaxOut.responseJSonGrid(ServletActionContext.getResponse(), dataList, count, new SimpleDateFormat("yyyy-MM-dd"));
		}
		catch (Exception e) {
			logger.error("查询执行单错误！！！", e);
		}
	}
	
	/**
	 * 查询排期
	 */
	public void searchBookPackage() {
		try {
			BookPackage bookPEty = (BookPackage) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), BookPackage.class);
			ExtLimit limit = (ExtLimit) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), ExtLimit.class);
			
			String id = ServletActionContext.getRequest().getParameter("id"); 
			if(id != null && !id.trim().equals("")) {
				bookPEty.setId(Integer.parseInt(id));
			}
			
			bookPEty.setExtLimit(limit);
			int count = bookPackageDao.selectLimitCount(bookPEty);
			List dataList = bookPackageDao.selectByLimit(bookPEty);
			AjaxOut.responseJSonGrid(ServletActionContext.getResponse(), dataList, count, new SimpleDateFormat("yyyy-MM-dd"));
		}
		catch (Exception e) {
			logger.error("查询执行单错误！！！", e);
		}
	}
	
	public void searchProduct() {
		try {
			BookPackage bookPEty = (BookPackage) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), BookPackage.class);
			ExtLimit limit = (ExtLimit) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), ExtLimit.class);
			
			String id = ServletActionContext.getRequest().getParameter("id"); 
			if(id != null && !id.trim().equals("")) {
				bookPEty.setId(Integer.parseInt(id));
			}
			
			bookPEty.setExtLimit(limit);
			int count = bookPackageDao.selectLimitCount(bookPEty);
			List dataList = bookPackageDao.selectByLimit(bookPEty);
			AjaxOut.responseJSonGrid(ServletActionContext.getResponse(), dataList, count, new SimpleDateFormat("yyyy-MM-dd"));
		}
		catch (Exception e) {
			logger.error("查询执行单错误！！！", e);
		}
	}
	
	public void searchBookPackageAdvbar() {
		try {
			String bookPackageId = ServletActionContext.getRequest().getParameter("bookPackageId");
			BookPackage bookPEty = new BookPackage();
			bookPEty.setId(Integer.parseInt(bookPackageId));
			ExtLimit limit = (ExtLimit) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), ExtLimit.class);
			bookPEty.setExtLimit(limit);
			int count = bookPackageDao.selectBookPackageAdvbarLimitCount(bookPEty);
			List dataList = bookPackageDao.selectBookPackageAdvbarByLimit(bookPEty);
			AjaxOut.responseJSonGrid(ServletActionContext.getResponse(), dataList, count, new SimpleDateFormat("yyyy-MM-dd"));
		}
		catch (Exception e) {
			logger.error("查询执行单错误！！！", e);
		}
	}
}
