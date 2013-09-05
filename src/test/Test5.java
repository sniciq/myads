package test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Test5 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			int[] data = {0, 1, 2, 3, 4, 5, 6};
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date date1 = df.parse("2011-2-12");
			Date date2 = df.parse("2011-2-19");
			
			int c = (int) (date1.getTime() / 86400000 - date2.getTime() / 86400000);
			if (c < 0) c = (-1) * c;
			if(c % 7 == 0) 
				System.out.println(data[0]);
			else if(c % 7 == 1) 
				System.out.println(data[1]);
			else if(c % 7 == 2) 
				System.out.println(data[2]);
			else if(c % 7 == 3) 
				System.out.println(data[3]);
			else if(c % 7 == 4) 
				System.out.println(data[4]);
			else if(c % 7 == 5) 
				System.out.println(data[5]);
			else if(c % 7 == 6) 
				System.out.println(data[6]);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
