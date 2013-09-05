package com.ku6ads.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class EntityReflect {
	
	public static String getObjectJSonString(Object obj) {
		return getObjectJSonString(obj, new SimpleDateFormat("yyyy-mm-dd"));
	}
	
	private static String getObjectProperitiesJSonValues(Object obj, Class cls, SimpleDateFormat df) {
		StringBuffer retBuf = new StringBuffer();
		
		Method[] methods = cls.getDeclaredMethods();
		for (int n = 0; n < methods.length; n++) {
			int modifiers = methods[n].getModifiers();
			if(modifiers != 1) continue;
			if(methods[n].getName().startsWith("get")) {
				String fieldName = methods[n].getName().substring(3);
				String ch = fieldName.charAt(0) + "";
				fieldName = fieldName.replaceFirst(ch, ch.toLowerCase());
				Object value = invokeGetMethod(obj, cls, methods[n].getName());
				if(value == null)
					value = "";
				if(value instanceof java.lang.String) {
					if(((String)value).trim().equals(""))
						value = "";
				}
				else if(value instanceof Date) {
					value = df.format((Date)value);
				}
				
				String vStr = value + "";
				if(vStr.contains("\"")) {
					vStr = vStr.replace("\"", "\\\"");
				}
				
				if(vStr.contains("\r")) {
					vStr = vStr.replace("\r", "\\r");
				}
				
				if(vStr.contains("\n")) {
					vStr = vStr.replace("\n", "\\n");
				}
				
				String nameStr = fieldName + "";
				if(nameStr.contains("\"")) {
					nameStr = nameStr.replace("\"", "\\\"");
				}
				
				if(nameStr.contains("\r")) {
					nameStr = nameStr.replace("\r", "\\r");
				}
				
				if(nameStr.contains("\n")) {
					nameStr = nameStr.replace("\n", "\\n");
				}
				
				retBuf.append("\"" + nameStr + "\":\"" + vStr + "\",");
			}
		}
		
		Class spClass = cls.getSuperclass();
		if(spClass != null) {
			retBuf.append(getObjectProperitiesJSonValues(obj, spClass, df));
		}
		
		return retBuf.toString();
	}
	
	public static String getObjectJSonString(Object obj, SimpleDateFormat df) {
		StringBuffer retBuf = new StringBuffer();
		retBuf.append("{");
		retBuf.append(getObjectProperitiesJSonValues(obj, obj.getClass(), df));
		retBuf.replace(retBuf.length() - 1, retBuf.length(), "");
		retBuf.append("}");
		return retBuf.toString();
	}
	
	public static Object getObjectProperty(Object obj, String property) {
		if(property == null || property.trim().equals(""))
			return "";
		String method = "get";
		String ch = property.charAt(0) + "";
		property = property.replaceFirst(ch, ch.toUpperCase());
		return invokeGetMethod(obj, method + property);
	}
	
	private static Object invokeGetMethod(Object obj, Class cls, String methodName) {
		Object retObj = null;
		try {
			Method method = cls.getDeclaredMethod(methodName, null);
			retObj = method.invoke(obj, null);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		return retObj;
	}
	
	private static Object invokeGetMethod(Object obj, String methodName) {
		Object retObj = null;
		try {
			Method method = obj.getClass().getDeclaredMethod(methodName, null);
			retObj = method.invoke(obj, null);
		} catch (Exception e) {
			return null;
		}
		return retObj;
	}
	
	public static Object invokeMethod(Object obj, String methodName, Object params) {
		Object retObj = null;
		Class[] paramClass = new Class[1] ;
		paramClass[0] = params.getClass();
		Method method = null;
		try {
			try {
				method = obj.getClass().getMethod(methodName, (Class[]) paramClass);
			} catch (NoSuchMethodException e) {
				Class[] objectClass = new Class[1] ;
				objectClass[0] = new Object().getClass();
				method = obj.getClass().getMethod(methodName, (Class[]) objectClass);
			}
			if(method != null)
				retObj = method.invoke(obj, params);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return retObj;
	}
	
	@SuppressWarnings("unchecked")
	public static Object invokeSetMethod(Object obj, String methodName, Object[] params) {
		Object retObj = null;
		Class[] paramClass = null;
		if (params != null && params.length > 0)
			paramClass = new Class[params.length];

		if(params != null && params.length > 0) {
			for (int i = 0; i < params.length; i++) {
				paramClass[i] = params[i].getClass();
			}
		}
		try {
			Method method = obj.getClass().getDeclaredMethod(methodName, (Class[]) paramClass);
			retObj = method.invoke(obj, params);
		} catch (Exception e) {
//			e.printStackTrace();
		}
		
		return retObj;
	}
	
	/**
	 * 将自定义JSON字符串内容放到对象中
	 * @param jsonStr
	 * @param obj
	 */
	public static void setJsonStringToObject(String jsonStr, Object obj) {
		jsonStr = jsonStr.substring(1, jsonStr.length() - 1);
		String[] nodes = jsonStr.split("~");
		for(int i = 0; i < nodes.length; i++) {
			String[] temps = nodes[i].split("\\|");
			String name = temps[0].replaceAll("\\^", "");
			
			String fc = name.charAt(0) + "";
			name = name.replaceFirst(fc, fc.toUpperCase());
			
			String vale = temps[1].replaceAll("\\^", "");
			Object[] os = new Object[1];
			os[0] = vale;
			EntityReflect.invokeSetMethod(obj, "set" + name, os);
		}
	}
	
	public static Object createObjectFromRequest(HttpServletRequest request, Class c) {
		try {
			Object obj = c.newInstance();
			mappingMapToObject(request, obj);
			return obj;
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	private static void getClassMethodMap(Class cls, Map<String, Method> objMethodMap) {
		if(objMethodMap == null)
			objMethodMap = new HashMap<String, Method>();
		
		Method[] methods = cls.getDeclaredMethods();
		for(Method m : methods) 
			objMethodMap.put(m.getName(), m);
		
		if(cls.getSuperclass() != null) {
			getClassMethodMap(cls.getSuperclass(), objMethodMap);
		}
	}
	
	/**
	 * 将request对象中的值放入到实体中<br>
	 * 如果request既不是RequestEncodingPostWrapper也不是RequestEncodingWrapper，则说明是Ajax请求<b>
	 * 用此方法时，用ajax提交的form不能用get方式，否则form中的中文会有乱码
	 * @param request
	 * @param object
	 */
	public static void mappingMapToObject(HttpServletRequest request, Object object) {
		try {
			Iterator itor = request.getParameterMap().keySet().iterator();
			
			Map<String, Method> objMethodMap = new HashMap<String, Method>();
			getClassMethodMap(object.getClass(), objMethodMap);
			
			while(itor.hasNext()) {
				try {
					String keyName = (String) itor.next();
					String paraValue = request.getParameter(keyName);
					
					if(request.getMethod().equalsIgnoreCase("GET")) {
						try {
							paraValue = java.net.URLDecoder.decode(paraValue,"UTF-8");
						}
						catch (Exception e) {
							e.printStackTrace();
						}
					}
					
					String fc = keyName.charAt(0) + "";
					keyName = keyName.replaceFirst(fc, fc.toUpperCase());
					String methodName = "set" + keyName;

					Method mthod = objMethodMap.get(methodName);
					Class<?>[] parasClass = mthod.getParameterTypes();
					Class pc = parasClass[0];
					Object[] vaObjs = new Object[1];
					
					if(pc.getName().equals(Date.class.getName())) {//如果是时间类型，则进行格式化
						SimpleDateFormat df;
						if(paraValue.contains(" "))
							df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						else
							df = new SimpleDateFormat("yyyy-MM-dd");
						vaObjs[0] = df.parse(paraValue);
					}
					else {
						Constructor cunstructor = pc.getConstructor(paraValue.getClass());					
						vaObjs[0] = cunstructor.newInstance(paraValue);
					}
					mthod.invoke(object, vaObjs);
				}
				catch (Exception e) {
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
 }
