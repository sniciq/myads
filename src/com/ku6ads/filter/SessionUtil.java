package com.ku6ads.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionUtil {
	
	public static final String Index = "index.html";
	public static final String Login = "login.html";
	
	public static final String IndexS = "index.jsp";
	public static final String LoginS = "index.jsp";
	
	public static final String Error = "error.html";
	public static final String Redirect="/admin/redirect.jsp";
	public static final String LoginAction = "LoginAction_login.action";
	public static final String LogOutAction = "login.action";
	public static final String IsTransCodeAction = "IsTransCodeAction.action";
	public static final String MakeMPathAtion = "MakeMPathAtion.action";
	public static final String GuidCookieAction = "GuidCookieAction_processingCookie.action";
	public static final String AreaCookieAction = "AreaCookieAction_processingAreaCookie.action";
	public static final String ControllerAction = "ControllerAction_processingAdvposition.action";
	public static final String AdvflightLogAction = "AdvFlightAction_getFlightList.action";
	
	public static final String StatisticsContextHTML = "StatisticsContext.html";
	public static final String StatisticsContextAction_search = "StatisticsAAContextAction_search.action";
	public static final String StatisticsContextAction_searchAllChannel = "StatisticsAAContextAction_searchAllChannel.action";
	
	public static final String JS = ".js";
	public static final String CSS = ".css";
	public static final String JPG = ".jpg";
	public static final String GIF = ".gif";
	public static final String PNG = ".png";
	public static final String TXT = ".txt";

	/**
	 * 判断session是否过期
	 * @param HttpServletRequest
	 * @return 如果session有效，则返回true，否则返回false
	 */
	public static boolean ifFlagExistInSession(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null && session.getAttribute("UserEty") != null) {
				return true;
		}
		return false;
	}
}
