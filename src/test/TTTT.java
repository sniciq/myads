package test;

import java.text.SimpleDateFormat;

import com.ku6ads.struts.advflight.BookForm;
import com.ku6ads.util.EntityReflect;


public class TTTT {
	public static void main(String[] asd) {
		try {
			BookForm bf = new BookForm();
			bf.setSaleTypeName("CPM");
			System.out.println(EntityReflect.getObjectJSonString(bf, new SimpleDateFormat("yyyy-MM-dd")));
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void aaa() {
//		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//		Date date = df.parse("2011-1-4");
//		Calendar cal = Calendar.getInstance();
//		System.out.println(cal.get(Calendar.WEEK_OF_YEAR));
//		
//		cal.setTime(date);
//		cal.set(Calendar.WEEK_OF_YEAR, -1);
//		
//		System.out.println(df.format(cal.getTime()));
//		System.out.println(cal.get(Calendar.WEEK_OF_YEAR));
	}
}
