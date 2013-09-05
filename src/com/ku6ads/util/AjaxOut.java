package com.ku6ads.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


public class AjaxOut {
 
	/**
	 * AJAX输出页面
	 * 
	 * @param response
	 * @param s
	 */
	public static void responseText(HttpServletResponse response, String s) {
		// 指定内容类型
		response.setContentType("text/html;charset=GBK");
		// 禁止缓存
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		try {
			PrintWriter out = response.getWriter();
			out.print(s);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void responseXml(HttpServletResponse response, String s) {
		// 指定内容类型
		response.setContentType("text/xml;charset=GBK");
		// 禁止缓存
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		try {
			PrintWriter out = response.getWriter();
			out.println("<?xml version=\"1.0\" encoding=\"GBK\"?>");
			out.print(s);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String getThrowableTrace(Throwable throwable) {
		try {
			StringBuffer errorBuf = new StringBuffer();
			StackTraceElement[] trace = throwable.getStackTrace();
			for (int i = 0; i < trace.length; i++) {
				errorBuf.append("  at " + trace[i] + "\n");
			}
			return errorBuf.toString();
		}
		catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 将错误信息输出到页面
	 * 
	 * @param response
	 * @param info 信息
	 * @param cl 错误类
	 * @param throwable
	 */
	@SuppressWarnings("unchecked")
	public static void responseException(HttpServletResponse response, String info, Class cl, Throwable throwable) {
		response.setContentType("text/html;charset=GBK");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		try {
			StringBuffer errorBuf = new StringBuffer();
			errorBuf.append("<table>");
			errorBuf.append("<tr>");
			errorBuf.append("<td>");
			errorBuf.append(info + ": " + cl);
			errorBuf.append("</td>");
			errorBuf.append("</tr>");

			errorBuf.append("<tr>");
			errorBuf.append("<td>");
			errorBuf.append(throwable.getClass() + ": " + throwable.getMessage());
			errorBuf.append("</td>");
			errorBuf.append("</tr>");

			StackTraceElement[] trace = throwable.getStackTrace();
			for (int i = 0; i < trace.length; i++) {
				errorBuf.append("<tr>");

				if (trace[i].toString().contains(cl.getName()))
					errorBuf.append("<td bgcolor='#FF0000'>");
				else
					errorBuf.append("<td>");

				errorBuf.append("&nbsp;&nbsp;at " + trace[i]);
				errorBuf.append("</td>");
				errorBuf.append("</tr>");
			}

			Throwable ourCause = throwable.getCause();
			if (ourCause != null) {
				errorBuf.append("<tr>");
				errorBuf.append("<td>");
				errorBuf.append(ourCause);
				errorBuf.append("</td>");
				errorBuf.append("</tr>");
			}
			errorBuf.append("</table>");

			PrintWriter out = response.getWriter();
			out.print(errorBuf);

			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 将JSon格式的grid输出到页面<br>
	 * 如果在grid中有特殊要求，如链接等，则需要自己重新实现该方法，不可先遍历dataList再调用该方法
	 * 
	 * @param response
	 * @param displayDataList 显示的列表
	 * @param recordsCount Grid的总记录数
	 */
	@SuppressWarnings("unchecked")
	public static void responseJSonGrid(HttpServletResponse response, List displayDataList, int totalCount, SimpleDateFormat df) {
		try {
			JSONObject dataObj = new JSONObject();
			dataObj.put("total", totalCount);
			JSONArray dataArray = new JSONArray();
			for (int i = 0; i < displayDataList.size(); i++) {
				JSONObject obj = JSONObject.fromObject(EntityReflect.getObjectJSonString(displayDataList.get(i), df));
				dataArray.add(obj);
			}
			dataObj.put("invdata", dataArray);
			AjaxOut.responseText(response, dataObj.toString());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void responseJSonGrid(HttpServletResponse response, List displayDataList, int totalCount) {
		try {
			JSONObject dataObj = new JSONObject();
			dataObj.put("total", totalCount);
			JSONArray dataArray = new JSONArray();
			for (int i = 0; i < displayDataList.size(); i++) {
				dataArray.add(displayDataList.get(i));
			}
			dataObj.put("invdata", dataArray);
			AjaxOut.responseText(response, dataObj.toString());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 将JSon格式的grid输出到页面<br>
	 * @param response
	 * @param　jsonArray　现成的json数组
	 */
	@SuppressWarnings("unchecked")
	public static void responseJSonGrid(HttpServletResponse response,JSONArray jsonArray) {
		try {
			JSONObject dataObj = new JSONObject();
			dataObj.put("invdata", jsonArray);
			AjaxOut.responseText(response, dataObj.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
