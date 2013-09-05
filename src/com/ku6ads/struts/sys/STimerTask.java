package com.ku6ads.struts.sys;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.ku6ads.dao.entity.advflight.Advertisement;
import com.ku6ads.dao.entity.sys.Config;
import com.ku6ads.dao.entity.sysconfig.Email;
import com.ku6ads.dao.entity.sysconfig.User;
import com.ku6ads.dao.entity.webdata.WebdataMovies;
import com.ku6ads.services.iface.advflight.AdvertisementService;
import com.ku6ads.services.iface.sysconfig.RoleService;
import com.ku6ads.services.iface.sysconfig.UserService;
import com.ku6ads.services.iface.webdata.WebdataMoviesService;
import com.ku6ads.struts.advflight.AdvertisementAction;
import com.ku6ads.util.MailUtil;

/**
 * Spring定时任务，取得与片单的接口， 并对数据库进行更新
 * 
 * @author chenshaofeng
 * 
 */
public class STimerTask extends QuartzJobBean  {
	private WebdataMoviesService webdataMoviesService;

	/**
	 * 解析XMl数据，并保存进数据库
	 * 
	 * @author chenshaofeng
	 */
	public void getRespondData() {
		try {
			ApplicationContext context = new ClassPathXmlApplicationContext("spring-config/applicationContext*.xml");
			Config config = (Config) context.getBean("ku6ads.config");
			Map<String, String> configMap = config.getConfig();
			String moviesURL = configMap.get("moviesURL");
			URL url = new URL(moviesURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestProperty("Accept-Encoding", "gzip");
			GZIPInputStream in = new GZIPInputStream (conn.getInputStream());
			// 解析XML数据
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(in);
			doc.normalize(); 
			
			NodeList movieList = doc.getElementsByTagName("movie");
			for (int i = 0; i < movieList.getLength(); i++) {
				Element mo = (Element) movieList.item(i);
				WebdataMovies movie = new WebdataMovies();
				
				Node sourceId = mo.getElementsByTagName("sourceId").item(0).getFirstChild();
				if(sourceId != null)
				movie.setSourceId(Integer.parseInt(sourceId.getNodeValue()));
				Node name = mo.getElementsByTagName("name").item(0).getFirstChild();
				if(name != null)
				movie.setName(name.getNodeValue());
				Node type = mo.getElementsByTagName("type").item(0).getFirstChild();
				if(type != null)
				movie.setType(type.getNodeValue());
				Node classType = mo.getElementsByTagName("classType").item(0).getFirstChild();
				if(classType != null)
				movie.setClassType(classType.getNodeValue());
				Node productionplace = mo.getElementsByTagName("productionplace").item(0).getFirstChild();
				if(productionplace != null)
				movie.setProductionPlace(productionplace.getNodeValue());
				Node level = mo.getElementsByTagName("level").item(0).getFirstChild();
				if(level != null)
				movie.setLevel(level.getNodeValue());
				Node playPlan = mo.getElementsByTagName("playPlan").item(0).getFirstChild();
				if(playPlan != null)
				movie.setPlayPlan(playPlan.getNodeValue());
				Node sellLength = mo.getElementsByTagName("sellLength").item(0).getFirstChild();
				if(sellLength != null)
				movie.setSellLength(sellLength.getNodeValue());
				Node director = mo.getElementsByTagName("director").item(0).getFirstChild();
				if(director != null)
				movie.setDirector(director.getNodeValue());
				Node mainActor = mo.getElementsByTagName("mainActor").item(0).getFirstChild();
				if(mainActor != null)
				movie.setMainActor(mainActor.getNodeValue());
				Node exclusive = mo.getElementsByTagName("exclusive").item(0).getFirstChild();
				if(exclusive != null)
				movie.setExclusive(Boolean.valueOf(exclusive.getNodeValue()));
				Node expectedFlow = mo.getElementsByTagName("expectedFlow").item(0).getFirstChild();
				if(expectedFlow != null)
				movie.setExpectedFlow(expectedFlow.getNodeValue());
				Node sellState = mo.getElementsByTagName("sellState").item(0).getFirstChild();
				if(sellState != null)
				movie.setSellState(sellState.getNodeValue());
				Node introduction = mo.getElementsByTagName("introduction").item(0).getFirstChild();
				if(introduction != null)
				movie.setIntroduction(introduction.getNodeValue());
				Node dt = mo.getElementsByTagName("dotype").item(0).getFirstChild();
				Integer dotype = 0;
				if(dt != null)
				dotype = Integer.parseInt(dt.getNodeValue());
				if (dotype == 0)
					webdataMoviesService.insert(movie);
				else if (dotype == 1)
					webdataMoviesService.updateBySourceId(movie);
				else if (dotype == 2)
					webdataMoviesService.deleteBySourceId(movie.getSourceId());
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public WebdataMoviesService getWebdataMoviesService() {
		return webdataMoviesService;
	}

	public void setWebdataMoviesService(WebdataMoviesService webdataMoviesService) {
		this.webdataMoviesService = webdataMoviesService;
	}

	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		getRespondData();
	}

}
