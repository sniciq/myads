package com.ku6ads.interceptor.auth;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 
 * 权限私有工具类
 * 
 * @author xuxianan
 *
 */
public class AuthUtils {

	/**
	 * 解析请求后返回的json, 拼接菜单
	 * 
	 * @param json
	 * @return
	 */
	public static JSONArray getMenulist(String json) {
		JSONArray arr = new JSONArray();
		try {
			JSONObject jsonObj = JSONObject.fromObject(json);
			JSONObject tablesObj = jsonObj.getJSONObject("data");
			Object[] obj = getDTOArray(tablesObj.getString("tables"));
			for (Object object : obj) {
				JSONObject menu = new JSONObject();
				JSONObject tableJson = JSONObject.fromObject(object);
				menu.put("childTable", tableJson.getString("childTable"));
				menu.put("id", tableJson.getString("id"));
				menu.put("name", tableJson.getString("name"));
				Object[] modulesObj = getDTOArray(tableJson.getString("modules"));
				JSONArray modulesArr = new JSONArray();
				for (Object object2 : modulesObj) {
					JSONObject module = JSONObject.fromObject(object2);
					module.put("id", module.getString("id"));
					module.put("tableId", module.getString("tableId"));
					module.put("name", module.getString("name"));
					module.put("url", module.getString("url"));
					modulesArr.add(module);
				}
				menu.put("modules", modulesArr);
				arr.add(menu);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return arr;
	}

	/**
	 * 解析权限请求后返回的json, 得到用户所在组列表, 拼接对象存放session中.
	 * 
	 * @param json
	 * @return
	 */
	public static List<Group> getUserGroup(String json) {
		List<Group> list = new ArrayList<Group>();

		JSONObject jsonObj = JSONObject.fromObject(json);
		JSONObject groupObj = jsonObj.getJSONObject("data");
		Object[] obj = getDTOArray(groupObj.getString("groups"));

		for (Object object : obj) {
			JSONObject groupJson = JSONObject.fromObject(object);
			Group group = new Group();
			group.setId(groupJson.getString("id") == null ? null : Integer.parseInt(groupJson.getString("id")));
			group.setName(groupJson.getString("name"));
			list.add(group);
		}
		return list;
	}

	/**
	 * 解析权限请求后返回的json, 得到用户权限, 拼接对象存放session中.
	 * 
	 * @param json
	 * @return
	 */
	public static List<Role> getUserRole(String json) {
		List<Role> list = new ArrayList<Role>();

		JSONObject jsonObj = JSONObject.fromObject(json);
		JSONObject roleObj = jsonObj.getJSONObject("data");
		Object[] obj = getDTOArray(roleObj.getString("roles"));

		for (Object object : obj) {
			JSONObject roleJson = JSONObject.fromObject(object);
			Role role = new Role();
			role.setId(roleJson.getString("id") == null ? null : Integer.parseInt(roleJson.getString("id")));
			role.setName(roleJson.getString("name"));
			list.add(role);
		}
		return list;
	}

	/**
	 * 
	 * 解析json返回对象数组
	 * 
	 * @param jsonString
	 * @return
	 */
	public static Object[] getDTOArray(String jsonString) {
		JSONArray array = JSONArray.fromObject(jsonString);
		Object[] obj = new Object[array.size()];
		for (int i = 0; i < array.size(); i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			obj[i] = JSONObject.toBean(jsonObject);
		}
		return obj;
	}

	public static String requsetURL(String url) throws Exception {
		URL u = new URL(url);
		URLConnection connection = u.openConnection();
		connection.connect();

		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String line = "";
		String result = "";
		while ((line = in.readLine()) != null) {
			result += line;
		}
		in.close();

		return result;
	}

	/**
	 * 
	 * 按照指定url获取链接,并获得返回json.
	 * 
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public static String getResponseInfo(String urlStr, String params) throws Exception {
		URL url = new URL(urlStr);
		URLConnection conn = url.openConnection();
		conn.setDoOutput(true);
		OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
		out.write(params);
		out.flush();
		out.close();
		BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String line = "";
		String result = "";
		while ((line = reader.readLine()) != null) {
			result += line;
		}
		return result;
	}

}
