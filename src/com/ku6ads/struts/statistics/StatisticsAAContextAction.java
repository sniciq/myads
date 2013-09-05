package com.ku6ads.struts.statistics;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.ku6ads.dao.entity.statistics.StatisticsAAContext;
import com.ku6ads.dao.entity.sysconfig.Channel;
import com.ku6ads.dao.iface.statistics.StatisticsAAContextDao;
import com.ku6ads.dao.iface.sysconfig.ChannelDao;
import com.ku6ads.util.AjaxOut;
import com.ku6ads.util.EntityReflect;
import com.ku6ads.util.TypeConverterUtil;
import com.opensymphony.xwork2.ActionSupport;

public class StatisticsAAContextAction extends ActionSupport {
	private static final long serialVersionUID = 3940148817717306464L;
	
	@Resource(name="StatisticsAAContextDao")
	private StatisticsAAContextDao statisticsAAContextDao;
	
	@Resource(name="ChannelDao")
	private ChannelDao channelDao;
	
	private static JSONArray GridColumModleInfo = new JSONArray();
	
	static {
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		sb.append("{header :'频道名称', dataIndex:'channelName', sortable:false, menuDisabled:true, align:'center'},");
		sb.append("{header:'广告展示总次数', dataIndex:'statisticsClass', sortable:false, menuDisabled:true, align:'center'}");
		sb.append("]");
		GridColumModleInfo = JSONArray.fromObject(sb.toString());
	}
	
	public void searchAllChannel() {
		List<Channel> channelList = channelDao.selectByEntity(null);
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		for (int i = 0; i < channelList.size(); i++) {
			Channel channel = channelList.get(i);
			sb.append("['" + channel.getSourceId() + "','" + channel.getName() + "']");
			if (i < channelList.size() - 1)
				sb.append(",");
		}
		sb.append("]");
		AjaxOut.responseText(ServletActionContext.getResponse(), sb.toString());
	}
	
	public void search() {
		try {
			JSONObject retObj = new JSONObject();
			retObj.put("action", true);
			
			StatisticsAAContextForm sf = (StatisticsAAContextForm) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), StatisticsAAContextForm.class);
			List<StatisticsAAContext> dataList = statisticsAAContextDao.statistSearch(sf);
			retObj.put("size", dataList.size());
			
			JSONArray columModleArray = new JSONArray();
			JSONArray topGroup = new JSONArray();
			JSONArray secondGroup = new JSONArray();
			
			JSONObject jobj = new JSONObject();
			jobj.put("header", "");
			jobj.put("colspan", 2);
			jobj.put("align", "center");
			topGroup.add(jobj);
			secondGroup.add(jobj);
			
			JSONArray dayColumArr = createDayColum(dataList, topGroup, secondGroup);
			columModleArray.addAll(GridColumModleInfo);
			columModleArray.addAll(dayColumArr);
			
			retObj.put("columns", columModleArray);
			retObj.put("topGroup", topGroup);
			retObj.put("secondGroup", secondGroup);
			retObj.put("data", getData(dataList));
			retObj.put("fields", getFieldsNamesArray(dataList));
			
			AjaxOut.responseText(ServletActionContext.getResponse(), retObj.toString());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private JSONArray createDayColum(List<StatisticsAAContext> dataList, JSONArray TopGroup, JSONArray secondGroup) {
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		JSONArray dayColumArr = new JSONArray();
		
		Map<String, String> map = new HashMap<String, String>();
		
		for(StatisticsAAContext sEty : dataList) {
			String statDate = df.format(sEty.getStatisticsTime());

			//头不能重复
			if(map.containsKey(statDate))
				continue;
			map.put(statDate, statDate);
			
			StringBuffer sb = new StringBuffer();
			sb.append("[");
			sb.append("{header :'曝光', dataIndex:'brandImpression_" + statDate + "', sortable:false, menuDisabled:true, align:'center'},");
			sb.append("{header :'点击', dataIndex:'brandClick_" + statDate + "', sortable:false, menuDisabled:true, align:'center'},");
			sb.append("{header :'AA-展示量', dataIndex:'aaDisplayCount_" + statDate + "', sortable:false, menuDisabled:true, align:'center'},");
			sb.append("{header :'点击', dataIndex:'aaClick_" + statDate + "', sortable:false, menuDisabled:true, align:'center'},");
			sb.append("{header :'测试', dataIndex:'aaTest_" + statDate + "', sortable:false, menuDisabled:true, align:'center'},");
			sb.append("{header :'VV', dataIndex:'vv_" + statDate + "', sortable:false, menuDisabled:true, align:'center'},");
			sb.append("{header :'CV', dataIndex:'cv_" + statDate + "', sortable:false, menuDisabled:true, align:'center'},");
			sb.append("{header :'正常结束', dataIndex:'normalOverCount_" + statDate + "', sortable:false, menuDisabled:true, align:'center'},");
			sb.append("{header :'暂停次数', dataIndex:'suspendCount_" + statDate + "', sortable:false, menuDisabled:true, align:'center'}");
			sb.append("]");
			
			JSONArray adayArr = JSONArray.fromObject(sb.toString());
			dayColumArr.addAll(adayArr);
			
			JSONObject jobj = new JSONObject();
			jobj.put("header", statDate);
			jobj.put("colspan", 9);
			jobj.put("align", "center");
			TopGroup.add(jobj);
			
			
			jobj = new JSONObject();
			jobj.put("header", "品牌");
			jobj.put("colspan", 2);
			jobj.put("align", "center");
			secondGroup.add(jobj);
			
			jobj = new JSONObject();
			jobj.put("header", "AA");
			jobj.put("colspan", 3);
			jobj.put("align", "center");
			secondGroup.add(jobj);
			
			jobj = new JSONObject();
			jobj.put("header", "播放量");
			jobj.put("colspan", 4);
			jobj.put("align", "center");
			secondGroup.add(jobj);
		}
		
		return dayColumArr;
	}
	
	private JSONArray getData(List<StatisticsAAContext> dataList) {
		JSONArray dataArr = new JSONArray();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Map<String, JSONObject> map = new HashMap<String, JSONObject>();
		for(StatisticsAAContext sEty : dataList) {
			String statDate = df.format(sEty.getStatisticsTime());
			String key = sEty.getChannelSourceId() + "_" + sEty.getStatisticsClass();
			JSONObject aDataObj = map.get(key);
			if(aDataObj == null) {
				aDataObj = new JSONObject();
				map.put(key, aDataObj);
			}
			
			aDataObj.put("channelName", sEty.getChannelName());
			aDataObj.put("channelSourceId", sEty.getChannelSourceId());
			aDataObj.put("statisticsClass", sEty.getStatisticsClass());
			aDataObj.put("brandImpression_" + statDate, sEty.getBrandImpression());
			aDataObj.put("brandClick_" + statDate, sEty.getBrandClick());
			aDataObj.put("aaDisplayCount_" + statDate, sEty.getAaDisplayCount());
			aDataObj.put("aaClick_" + statDate, sEty.getAaClick());
			aDataObj.put("aaTest_" + statDate, sEty.getAaTest());
			aDataObj.put("vv_" + statDate, sEty.getVv());
			aDataObj.put("cv_" + statDate, sEty.getCv());
			aDataObj.put("normalOverCount_" + statDate, sEty.getNormalOverCount());
			aDataObj.put("suspendCount_" + statDate, sEty.getSuspendCount());
			aDataObj.put("statisticsTime_" + statDate, sEty.getStatisticsTime());
		}
		
		dataArr.addAll(map.values());
		return dataArr;
	}
	
	private JSONArray getFieldsNamesArray(List<StatisticsAAContext> dataList) {
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		sb.append("{name:'channelSourceId'},");
		sb.append("{name:'channelName'},");
		sb.append("{name:'statisticsClass'},");
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		for(int i = 0; i < dataList.size(); i++ ) {
			StatisticsAAContext sEty = dataList.get(i);
			String statDate = df.format(sEty.getStatisticsTime());
			
			sb.append("{name:'brandImpression_" + statDate + "'},");
			sb.append("{name:'brandClick_" + statDate + "'},");
			sb.append("{name:'aaDisplayCount_" + statDate + "'},");
			sb.append("{name:'aaClick_" + statDate + "'},");
			sb.append("{name:'aaTest_" + statDate + "'},");
			sb.append("{name:'vv_" + statDate + "'},");
			sb.append("{name:'cv_" + statDate + "'},");
			sb.append("{name:'normalOverCount_" + statDate + "'},");
			sb.append("{name:'suspendCount_" + statDate + "'},");
			sb.append("{name:'statisticsTime_" + statDate + "'}");
			
			if(i < dataList.size() - 1)
				sb.append(",");
		}
		sb.append("]");
		JSONArray fieldsNamesArray = JSONArray.fromObject(sb.toString());
		return fieldsNamesArray;
	}

}
