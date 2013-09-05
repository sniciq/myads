package com.ku6ads.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * sessionfilter
 * 
 * @author xuxianan
 * 
 */
public class SessionFilter implements Filter {
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		if(request instanceof HttpServletRequest) {
			HttpServletRequest httpServletRequest = (HttpServletRequest) request;
			String url = httpServletRequest.getRequestURI();// 得到请求URL
			if (!SessionUtil.ifFlagExistInSession(httpServletRequest) && !url.endsWith(SessionUtil.Index) && !url.endsWith(SessionUtil.Error) 
					&& !url.endsWith(SessionUtil.Login) && !url.endsWith(SessionUtil.Redirect) && !url.endsWith(SessionUtil.LoginAction) 
					&& !url.endsWith(SessionUtil.LogOutAction) && !url.endsWith(SessionUtil.JS) 
					&& !url.endsWith(SessionUtil.CSS)
					
					&& !url.endsWith(SessionUtil.IndexS)
					&& !url.endsWith(SessionUtil.LoginS)
					
					&& !url.endsWith(SessionUtil.IsTransCodeAction)
					&& !url.endsWith(SessionUtil.MakeMPathAtion)
					&& !url.endsWith(SessionUtil.GuidCookieAction)
					&& !url.endsWith(SessionUtil.AreaCookieAction)
					&& !url.endsWith(SessionUtil.ControllerAction)
					&& !url.endsWith(SessionUtil.AdvflightLogAction)
					
					&& !url.endsWith(SessionUtil.StatisticsContextHTML)
					&& !url.endsWith(SessionUtil.StatisticsContextAction_searchAllChannel)
					&& !url.endsWith(SessionUtil.StatisticsContextAction_search)
					
					&& !url.endsWith(SessionUtil.TXT)
					&& !url.endsWith(SessionUtil.JPG) && !url.endsWith(SessionUtil.GIF) && !url.endsWith(SessionUtil.PNG)) {
				if (response instanceof HttpServletResponse) {
					HttpServletResponse httpServletResponse = (HttpServletResponse) response;
					
					// session is null set Header timeout
					if (httpServletRequest.getHeader("x-requested-with") != null
							&& httpServletRequest.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) {
						httpServletResponse.setHeader("sessionstatus", "timeout");
						return;
					}
					
					httpServletResponse.sendRedirect("/myads/HTML/index.jsp");
					return;
				}
			}
		}
		
		try {
			chain.doFilter(request, response);
		}
		catch (Exception e) {
		}
	}

	public void destroy() {
	}

}