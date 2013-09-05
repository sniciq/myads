package com.ku6ads.struts.controller;

import java.util.UUID;

import javax.servlet.http.Cookie;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.core.io.ClassPathResource;
import com.ku6ads.util.AjaxOut;
import com.ku6ads.util.PropertiesUtils;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 
 * guid cookie 
 * @author xuxianan
 *
 */
public class GuidCookieAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(GuidCookieAction.class);

	/**
	 * 处理用户请求cookie
	 * 
	 * 1. 如果cookie不为空的话,则返回cookie value
	 * 
	 * 2. 如果cookie为空的话,save配置文件中的cookie配置,然后返回save的cookie value
	 */
	public void processingCookie() {

		try {
			PropertiesUtils.load(new ClassPathResource("cookies.properties"));
			String name = PropertiesUtils.getValue("NAME");
			String domain = PropertiesUtils.getValue("DOMAIN");
			String path = PropertiesUtils.getValue("PATH");

			Cookie[] cookies = ServletActionContext.getRequest().getCookies();
			if (cookies == null) {

				UUID guid = UUID.randomUUID();
				Cookie cookie = new Cookie(name, String.valueOf(guid));
				cookie.setDomain(domain); // TODO 本机不存在域,暂时不设置此属性
				cookie.setMaxAge((60 * 60 * 24 * 365) * 10);
				cookie.setPath(path);
				ServletActionContext.getResponse().addCookie(cookie);

				AjaxOut.responseText(ServletActionContext.getResponse(), cookie.getValue());
			} else {
				for (Cookie ck : cookies) {
					if (ck.getName().equals(name)) {
						AjaxOut.responseText(ServletActionContext.getResponse(), ck.getValue());
					} else {
						UUID guid = UUID.randomUUID();
						Cookie cookie = new Cookie(name, String.valueOf(guid));
						cookie.setDomain(domain); // TODO 本机不存在域,暂时不设置此属性
						cookie.setMaxAge((60 * 60 * 24 * 365) * 10);
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

}
