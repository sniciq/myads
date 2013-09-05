package test;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.poi.util.SystemOutLogger;


public class testLiu {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String test = "['json','is','easy']";  
		JSONArray jsonArray = JSONArray.fromObject( test );     
		System.out.println(jsonArray);   
		
		String myJson = "{'age':36,'email':'liujunshi@ku6.com','id':'2','name':'testName'}";  
		JSONObject jsonObject1 = JSONObject.fromObject( myJson );     
//		TestBean bean = (TestBean) JSONObject.toBean( jsonObject1, TestBean.class );     
//		System.out.println("beanName:" + bean.getName());  
//		System.out.println("age:" + bean.getAge());
//		System.out.println("email:" + bean.getEmail());
	}

}
