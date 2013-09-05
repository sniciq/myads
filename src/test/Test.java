package test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.core.io.Resource;
import org.springframework.orm.ibatis.SqlMapClientFactoryBean;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			
			for(int i = 0; i < 10; i++) {
				if(1 == 1) {
					if(i == 2) {
						continue;
					}
				}
				
				System.out.println("4444444444444");
			}
			
			
			String startTime = "2010-10-1";
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date selDate = df.parse(startTime);
			Calendar cal = Calendar.getInstance();
			cal.setFirstDayOfWeek(Calendar.MONDAY);
			cal.setTime(selDate);
			cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			Date startDate = cal.getTime();
			cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
			Date endDate = cal.getTime();
			
			System.out.println(df.format(startDate));
			System.out.println(df.format(endDate));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		org.springframework.core.io.Resource rs = new Resource() {
			public InputStream getInputStream() throws IOException {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public long lastModified() throws IOException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public boolean isReadable() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean isOpen() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public URL getURL() throws IOException {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public URI getURI() throws IOException {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getFilename() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public File getFile() throws IOException {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getDescription() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public boolean exists() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public Resource createRelative(String arg0) throws IOException {
				// TODO Auto-generated method stub
				return null;
			}
		};
		
	}

}
