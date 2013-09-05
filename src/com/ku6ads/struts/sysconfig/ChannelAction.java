package com.ku6ads.struts.sysconfig;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.ku6ads.dao.entity.sysconfig.Channel;
import com.ku6ads.dao.entity.sysconfig.Site;
import com.ku6ads.services.iface.advert.AdvbarService;
import com.ku6ads.services.iface.sysconfig.ChannelService;
import com.ku6ads.services.iface.sysconfig.SiteService;
import com.ku6ads.util.AjaxOut;
import com.ku6ads.util.EntityReflect;
import com.ku6ads.util.ExtLimit;
import com.ku6ads.util.TypeConverterUtil;
import com.opensymphony.xwork2.ActionSupport;

/**
 * @author LiYongHui
 * 
 */
public class ChannelAction extends ActionSupport {

	private static final long serialVersionUID = 8778346821808364916L;
	private ChannelService channelService;
	private SiteService siteService;
	private AdvbarService advbarService;
	private Logger logger = Logger.getLogger(ChannelAction.class);

	/**
	 * 新增频道,成功或失败返回json提示;
	 */
	public void save() {
		JSONObject obj = new JSONObject();
		obj.put("success", true);
		try {
			Channel channel = (Channel) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), Channel.class);
			Site site = (Site) siteService.selectById(channel.getSiteId());
			channel.setSiteName(site.getSiteName());
			String ids = ServletActionContext.getRequest().getParameter("ids");
			if (channel.getChannelId() == null) {
				channel.setCreateDate(new Date());
				channelService.insertCallbackIdMemory(channel);
			} else {
				Channel c = (Channel) channelService.selectById(channel.getChannelId());
				if (ids == "" || ids == null) {
					channel.setParentChannelId(c.getParentChannelId());
				} else {
					channel.setParentChannelId(TypeConverterUtil.parseInt(ids));
				}
				if (channel.getSiteId() == 0 || channel.getSiteId() == null) {
					channel.setSiteId(c.getSiteId());
				}
				channelService.updateAndMemory(channel);
			}
			obj.put("result", "success");
		} catch (Exception e) {
			e.printStackTrace();
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), obj.toString());
	}

	/**
	 * 获得频道名称列表
	 */
	public void getChannelList() {
		List<Channel> list = channelService.selectChannel();
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		for (int i = 0; i < list.size(); i++) {
			Channel channel = list.get(i);
			sb.append("['" + channel.getChannelId() + "','" + channel.getName() + "']");
			if (i < list.size() - 1)
				sb.append(",");
		}
		sb.append("]");
		AjaxOut.responseText(ServletActionContext.getResponse(), sb.toString());
	}
	
	/**
	 * 获得频道列表详细信息;
	 */
	@SuppressWarnings("unchecked")
	public void showAll() {
		try {
			ExtLimit limit = (ExtLimit) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), ExtLimit.class);
			Channel channel = (Channel) EntityReflect.createObjectFromRequest(ServletActionContext.getRequest(), Channel.class);
			Site site = (Site) siteService.selectById(channel.getSiteId());
			if (site != null)
				channel.setSiteName(site.getSiteName());
			channel.setExtLimit(limit);
			int count = channelService.selectLimitCount(channel);
			List<Channel> roleList = channelService.selectByLimit(channel);
			AjaxOut.responseJSonGrid(ServletActionContext.getResponse(), roleList, count, new SimpleDateFormat("yyyy-MM-dd"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获得频道列表对应的详细信息;
	 */
	public void getChannelDetail() {
		JSONObject retObj = new JSONObject();
		retObj.put("success", true);
		try {
			String id = ServletActionContext.getRequest().getParameter("channelId");
			Channel channel = (Channel) channelService.selectById(TypeConverterUtil.parseInt(id));
			Site site = (Site) siteService.selectById(channel.getSiteId());
			channel.setSiteName(site.getSiteName());
			retObj.put("data", channel);
		} catch (Exception e) {
			e.printStackTrace();
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), retObj.toString());
	}

	/**
	 * 根据页面传递过来的频道id集合,删除对应的频道;
	 */
	public void delete() {
		JSONObject retObj = new JSONObject();
		retObj.put("success", true);
		try {
			String ids = ServletActionContext.getRequest().getParameter("channelList");
			String[] idList = ids.split(",");
			String delMessage = "";
			for (int i = 0; i < idList.length; i++) {
				Integer deletesuccess = channelService.deleteCheckAdvposByIdMemory(TypeConverterUtil.parseInt(idList[i]));
				if (deletesuccess == 0) {
					Channel channel = (Channel) channelService.selectById(TypeConverterUtil.parseInt(idList[i]));
					delMessage = delMessage + channel.getName() + ",";
				}
			}
			if (idList.length == 1 && delMessage != "") {
				retObj.put("result", "use");
			} else if (delMessage != "") {
				retObj.put("delMessage", delMessage.substring(0, delMessage.length() - 1));
			} else {
				retObj.put("result", "success");
			}

		} catch (Exception e) {
			e.printStackTrace();
			retObj.put("result", "error");
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), retObj.toString());
	}

	/**
	 * 得到所有频道
	 */
	public void getAllChannelList() {
		try {
			List<Channel> list = channelService.getAllUseableChannelList();
			StringBuffer sb = new StringBuffer();
			sb.append("[");
			for (int i = 0; i < list.size(); i++) {
				Channel channel = list.get(i);
				sb.append("['" + channel.getChannelId() + "','" + channel.getName() + "']");
				if (i < list.size() - 1)
					sb.append(",");
			}
			sb.append("]");
			AjaxOut.responseText(ServletActionContext.getResponse(), sb.toString());
		} catch (Exception e) {
			logger.error("", e);
		}
	}

	/**
	 * 根据板块id获得对应的频道列表
	 */
	public void getChannelListBySiteId() {
		try {
			String siteId = ServletActionContext.getRequest().getParameter("siteId");
			List<Channel> channelList = channelService.loadBySiteId(TypeConverterUtil.parseInt(siteId));
			StringBuffer sb = new StringBuffer();
			sb.append("[");
			for (int i = 0; i < channelList.size(); i++) {
				Channel channel = channelList.get(i);
				sb.append("['" + channel.getChannelId() + "','" + channel.getName() + "']");
				if (i < channelList.size() - 1)
					sb.append(",");
			}
			sb.append("]");
			AjaxOut.responseText(ServletActionContext.getResponse(), sb.toString());
		} catch (Exception e) {
			logger.error("", e);
		}
	}

	/**
	 * 查询同一网站下的频道是否有重复
	 */
	public void vldChannelNameRepeated() {
		JSONObject obj = new JSONObject();
		obj.put("success", true);
		try {
			String channelId = ServletActionContext.getRequest().getParameter("channelId");
			int siteId = Integer.parseInt(ServletActionContext.getRequest().getParameter("siteId"));
			String name = ServletActionContext.getRequest().getParameter("name");
			
			Channel channel = channelService.selectChannelNameIsRepeated(siteId, name);
			if (null == channel) {
				obj.put("result", "success");
			}
			
			// 当记录重复时,判断是否是同一条记录
			if (null != channel) {
				if (!("").equals(channelId) && channel.getChannelId() == Integer.parseInt(channelId))
					obj.put("result", "success");
				else
					obj.put("result", "use");
			}
		} catch (Exception e) {
			logger.error("", e);
		}
		AjaxOut.responseText(ServletActionContext.getResponse(), obj.toString());
	}
	
	public ChannelService getChannelService() {
		return channelService;
	}

	public void setChannelService(ChannelService channelService) {
		this.channelService = channelService;
	}

	public SiteService getSiteService() {
		return siteService;
	}

	public void setSiteService(SiteService siteService) {
		this.siteService = siteService;
	}

	public AdvbarService getAdvbarService() {
		return advbarService;
	}

	public void setAdvbarService(AdvbarService advbarService) {
		this.advbarService = advbarService;
	}

}
