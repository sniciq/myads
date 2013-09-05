package com.ku6ads.struts.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.Cookie;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.core.io.ClassPathResource;

import com.ku6ads.dao.entity.sysconfig.IP;
import com.ku6ads.dao.iface.sysconfig.IPDao;
import com.ku6ads.util.AjaxOut;
import com.ku6ads.util.PropertiesUtils;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 地域cookie处理
 * @author xuxianan
 *
 */
public class AreaCookieAction extends ActionSupport {

	private static final long serialVersionUID = -283147801727575013L;
	private Logger logger = Logger.getLogger(AreaCookieAction.class);
	
	private IPDao ipDao;

	/**
	 * 根据用户访问IP 处理地域cookie请求
	 * 
	 * 如果存在地域cookie则返回cookie value, 如果不存在cookie 则set新的cookie并返回value
	 */
	public void processingAreaCookie() {

		try {
			PropertiesUtils.load(new ClassPathResource("cookies.properties"));
			String name = PropertiesUtils.getValue("AREA_NAME");
			String domain = PropertiesUtils.getValue("DOMAIN");
			String path = PropertiesUtils.getValue("PATH");

			String ip = ServletActionContext.getRequest().getRemoteAddr(); // 根据 用户访问ip判断用户所在地域 然后设为cookie value
			
			IP ipEty = ipDao.selectEtyByIP(ip);

			// FIXME 临时使用,等调用地域接口后将替换该规则
			String areaId;
			if (ip.equals("127.0.0.1")) {
				areaId = "1";
			} else {
				areaId = "2";
			}

			Cookie[] cookies = ServletActionContext.getRequest().getCookies();
			if (cookies == null) {
				int time = resultTime();

				Cookie cookie = new Cookie(name, areaId);
				cookie.setDomain(domain);
				cookie.setMaxAge(time / 1000);
				cookie.setPath(path);
				ServletActionContext.getResponse().addCookie(cookie);

				AjaxOut.responseText(ServletActionContext.getResponse(), cookie.getValue());
			} else {
				for (Cookie ck : cookies) {
					if (ck.getName().equals(name)) {
						AjaxOut.responseText(ServletActionContext.getResponse(), ck.getValue());
					} else {
						int time = resultTime();

						Cookie cookie = new Cookie(name, areaId);
						cookie.setDomain(domain);
						cookie.setMaxAge(time);
						cookie.setPath(path);
						ServletActionContext.getResponse().addCookie(cookie);

						AjaxOut.responseText(ServletActionContext.getResponse(), cookie.getValue());
					}
				}
			}
		} catch (Exception e) {
			logger.error("", e);
		}

	}

	/**
	 * 计算当前时间 至 当天23:59:59 剩余时间
	 * @param simpleDateFormat
	 * @return
	 * @throws ParseException
	 */
	private static int resultTime() throws ParseException {
		Calendar now = Calendar.getInstance();
		String lastTime = now.get(Calendar.YEAR) + "-" + (now.get(Calendar.MONTH) + 1) + "-"
				+ now.get(Calendar.DAY_OF_MONTH) + " " + "23:59:59";
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date end = format.parse(lastTime);
		long result = end.getTime() - new Date().getTime();
		return (int) result;
	}

	public IPDao getIpDao() {
		return ipDao;
	}

	public void setIpDao(IPDao ipDao) {
		this.ipDao = ipDao;
	}
	
}
