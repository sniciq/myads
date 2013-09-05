package com.ku6ads.services.impl.advflight;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ku6ads.dao.entity.advflight.Book;

public class BookAdvproductFlightDivider {
	
	/**
	 * CPD， B类产品分配
	 * @param bookList
	 * @param flight
	 */
	public void divideFlight_CPD_B(List<Book> bookList, int flight) {
		for(int i = 0; i < bookList.size(); i++) {
			Book bookEty = bookList.get(i);
			if(bookEty.getLastFlight() >= 1) {
				bookEty.setFlightNum(1);//CPD
			}
		}
	}
	
	/**
	 * CPM， B类产品分配
	 * @param bookList
	 * @param flight
	 */
	public void divideFlight_CPM_B(List<Book> bookList, int flight) {
		for(int i = 0; i < bookList.size(); i++) {
			Book bookEty = bookList.get(i);
			bookEty.setFlightNum(flight * 1000);//CPM
		}
	}
	
	private void displayMap(Map<String, OEty> allDataMap) {
		Iterator<String> itor = allDataMap.keySet().iterator();
		while(itor.hasNext()) {
			String key = itor.next();
			OEty ety = allDataMap.get(key);
			System.out.println(key + ": " + ety.allCount + " | " + ety.canUseCount + " | " + ety.needCount );
		}
	}
	
	/**
	 * 输入中的预估值是不以是千次为单位，计算时需要改用千次来计算！<br>
	 * 返回结果*1000
	 * CPM，A类产品分配
	 * @param bookList
	 * @param flight
	 */
	public void divideFlight_CPM_A(List<Book> bookList, int flight) {
		Map<String, OEty> allDataMap = new HashMap<String, OEty>();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		for(int i = 0; i < bookList.size(); i++) {
			Book bookEty = bookList.get(i);
			OEty ety = new OEty();
			ety.allCount = bookEty.getFutureFlight().intValue() / 1000;
			ety.canUseCount = bookEty.getLastFlight() / 1000;
			ety.needCount = 0;
			allDataMap.put(bookEty.getAdvbarId() + "_" + df.format(bookEty.getStartTime()), ety);
		}
		
//		System.out.println("-----------分前---------------");
//		displayMap(allDataMap);
		
		//按比例进行折量
		divideFlight(allDataMap, flight);
		
//		System.out.println("-----------分后---------------");
//		displayMap(allDataMap);
		
		for(int i = 0; i < bookList.size(); i++) {
			Book bookEty = bookList.get(i);
			String key = bookEty.getAdvbarId() + "_" + df.format(bookEty.getStartTime());
			OEty ety = allDataMap.get(key);
			bookEty.setFlightNum(ety.needCount * 1000);
		}
	}

	/**
	 * CPD，A类产品分配
	 * @param bookList
	 * @param flight
	 */
	public void divideFlight_CPD_A(List<Book> bookList, int flight) {
		for(int i = 0; i < bookList.size(); i++) {
			Book bookEty = bookList.get(i);
			if(bookEty.getLastFlight() >= 1) {
				bookEty.setFlightNum(1);//CPD
			}
		}
	}
	
	public void divTest() {
		Map<String, OEty> allDataMap = new HashMap<String, OEty>();
		OEty ety = new OEty();
		
		ety = new OEty();
		ety.allCount = 900.0f;
		ety.canUseCount = 900;
		ety.needCount = 0;
		allDataMap.put("131_2011-07-04", ety);
		
		ety = new OEty();
		ety.allCount = 9.0f;
		ety.canUseCount = 9;
		ety.needCount = 0;
		allDataMap.put("130_2011-07-04", ety);
		
		ety = new OEty();
		ety.allCount = 900.0f;
		ety.canUseCount = 900;
		ety.needCount = 0;
		allDataMap.put("132_2011-07-04", ety);
		
		System.out.println("-----------分前---------------");
		displayMap(allDataMap);
		
		//按比例进行折量
		divideFlight(allDataMap, 1801);
		
		System.out.println("-----------分后---------------");
		displayMap(allDataMap);
	}
	
	public static void main(String[] as) {
		BookAdvproductFlightDivider aa = new BookAdvproductFlightDivider();
		aa.divTest();
	}
	
	
	private void divideFlight(Map<String, OEty> allDataMap, int flight) {
		int ts = flight;
		int k = 0;
		while (ts > 0 && k < 1000) {
			int sm = getSum(allDataMap);
			Set<String> s = allDataMap.keySet();
			Iterator<String> itor = s.iterator();
			
			int its = ts;
			while(itor.hasNext()) {
				String key = itor.next();
				
				OEty to = allDataMap.get(key);
				if (to.ismoved)
					continue;
				
				if(its <= 0)
					break;

				if (its > 0 && its < 10 && to.canUseCount > its) {
					to.needCount += its;
					its = 0;
					to.canUseCount -= its;
					break;
				}
				
				float f = to.allCount / sm;
				float ff = f * ts;
				int zz = (int)(f * ts);
				if(0.0 < ff &&  ff < 1.0)
					zz = 1;
//				int zz = Math.round(f * ts);
				
				if (zz > to.canUseCount) {
					to.needCount += to.canUseCount;
					its -= to.canUseCount;
					to.canUseCount = 0;
				} else {
					to.needCount += zz;
					its -= zz;
					to.canUseCount -= zz;
				}

				if (to.canUseCount == 0)
					to.ismoved = true;
			}
			
			ts = its;
			k++;
		}
	}
	
	private int getSum(Map<String, OEty> allDataMap) {
		int nn = 0;
		Set<String> s = allDataMap.keySet();
		Iterator<String> itor = s.iterator();
		while(itor.hasNext()) {
			String key = itor.next();
			OEty to = allDataMap.get(key);
			if (to.ismoved)
				continue;
			nn += to.allCount;
		}
		return nn;
	}
	
	class OEty {
		float allCount;
		int canUseCount;
		int needCount;
		boolean ismoved = false;
	}
}
