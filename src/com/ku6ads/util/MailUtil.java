package com.ku6ads.util;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import com.ku6ads.dao.entity.sysconfig.Email;

public class MailUtil {
	
	public static void sentMobileMes(Email em) {
		SimpleEmail email = new SimpleEmail();
		email.setTLS(true);
		email.setHostName(em.getHostName());
		email.setAuthentication(em.getSender(), em.getPassword());

		try {
			email.addTo(em.getReceiver());
			email.setFrom(em.getSender()); // 我方
			email.setSubject(em.getTitle()); // 设置标题
			email.setCharset(em.getCharSet()); // 设置字符集
			email.setMsg(em.getMessage()); // 内容
			email.send(); 
		} catch (EmailException e) {
			e.printStackTrace();
		}
	}
}
