package test;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import net.sf.json.JSONObject;

import org.w3c.dom.Document;

/**
 * Spring定时任务，取得与片单的接口， 并对数据库进行更新
 * 
 * 
 */
public class Test3 {

	public static void main(String[] args) {
		// new
		// Test3().getDoc("http://admin2.ku6.com/api/filmInfo4ads.jsp?dec=no&key=ku6ads",
		// "gbk");
		new Test3().test("http://admin2.ku6.com/api/filmInfo4ads.jsp?dec=no&key=ku6ads");
	}

	public void test(String urlStr) {
		try {
			URL url = new URL(urlStr);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			// conn.setRequestProperty("content-type", "text/xml");
			 conn.setRequestProperty("encoding", "gbk");
			// conn.setRequestMethod("POST");
			// conn.setDoInput(true);
			// conn.setDoOutput(true);
			conn.setAllowUserInteraction(false);
			conn.connect();

			String charSet = "";
			String type = conn.guessContentTypeFromStream(conn.getInputStream());
			if (type == null)
				type = conn.getContentType();
			System.out.println("ContentType:" + type);
			
			if (type.indexOf("charset=") > 0)
				charSet = type.substring(type.indexOf("charset=") + 8);

			System.out.println("charset:" + charSet);
			
			if(charSet.equals(""))
				charSet = "UTF-8";

			InputStream in = new BufferedInputStream(conn.getInputStream());
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(in,charSet)); 

			String temp;
			while ((temp = reader.readLine()) != null) {
				System.out.println(temp);
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Document getDoc(String urlStr, String encoding) {
		Document doc = null;
		try {
			URL url = new URL(urlStr);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestProperty("content-type", "text/xml");
			// conn.setRequestProperty("encoding", "gb2312");
			conn.setRequestMethod("POST");
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.connect();
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dombuilder = builderFactory.newDocumentBuilder();
			InputStream in = new BufferedInputStream(conn.getInputStream());
			InputStreamReader isr = new InputStreamReader(in, encoding);

			String type = conn.guessContentTypeFromStream(conn.getInputStream());
			String charSet = "";
			if (type == null)
				type = conn.getContentType();

			if (type == null || type.trim().length() == 0 || type.trim().indexOf("text/html") < 0)
				return null;

			if (type.indexOf("charset=") > 0)
				charSet = type.substring(type.indexOf("charset=") + 8);

			System.out.println("ContentType:" + type);

			System.out.println("charset:" + charSet);

			// BufferedReader reader = new BufferedReader(isr);
			// String line = null;
			// while ((line = reader.readLine()) != null) {
			// System.out.println(line);
			// }

			// int str = -1;
			// StringBuffer sb = new StringBuffer();
			// while ((str = isr.read()) != -1)
			// sb.append((char) str);
			// DocumentBuilderFactory dbf =
			// DocumentBuilderFactory.newInstance();
			// DocumentBuilder db = dbf.newDocumentBuilder();
			// isr.close();
			// System.out.println(sb);
			// doc = db.parse(new InputSource(new StringReader(sb.toString())));

			// InputSource inputSource = new InputSource(isr);
			// doc = dombuilder.parse(inputSource);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return doc;
	}
}
