package com.ku6ads.services.impl.advflight;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.ku6ads.dao.entity.advflight.Advertisement;
import com.ku6ads.dao.entity.sysconfig.Email;
import com.ku6ads.dao.entity.sysconfig.User;
import com.ku6ads.services.iface.advflight.AdvertisementService;
import com.ku6ads.services.iface.sysconfig.RoleService;
import com.ku6ads.services.iface.sysconfig.UserService;
import com.ku6ads.util.MailUtil;

public class MailNotifyService  extends QuartzJobBean{
	private AdvertisementService advertisementService;
	private UserService userService;
	private RoleService roleService;
	
	/**
	 * 在二审时如果关联了排期没有关联物料给相关人员发邮件
	 */
	public void selectMailNotify(){
		try{
			
		 Date now = new Date();
		 Calendar later = Calendar.getInstance();   
		 later.setTime(now);   
		 later.set(Calendar.DATE, later.get(Calendar.DATE) + 7);   

		List<Advertisement> advertisementsList = advertisementService.selectMailNotify(later.getTime());

		Email mail = new Email();
		roleService.setMailProperty(mail);
		for(int i = 0;i < advertisementsList.size();i++){
			Advertisement advertisement = advertisementsList.get(i);
			String messgage = "广告"+advertisement.getName()+"还没有关联物料！";
			mail.setMessage(messgage);
			
			advertisement.getCreator();
			User user = new User();
			user.setUsername(advertisement.getCreator());
			List userList = userService.selectByEntity(user);
			if(userList!=null && userList.size()!=0)
			{
				User user2 = (User)userList.get(0);
				mail.setReceiver(user2.getMail());
				MailUtil.sentMobileMes(mail);
			}	
		}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		selectMailNotify();
	}

	public AdvertisementService getAdvertisementService() {
		return advertisementService;
	}

	public void setAdvertisementService(AdvertisementService advertisementService) {
		this.advertisementService = advertisementService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public RoleService getRoleService() {
		return roleService;
	}

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}
}
