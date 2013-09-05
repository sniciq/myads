package test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TestTT {

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

				if (its > 0 && its < 10 && to.canUseCount > its) {
					to.needCount += its;
					its = 0;
					break;
				}
				
				float f = to.allCount / sm;
				int zz = (int) (f * ts);
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
	
	public boolean check(List<OEty> dataList, int sum) {
		int nn = 0;
		for (int i = 0; i < dataList.size(); i++) {
			nn += dataList.get(i).canUseCount;
		}
		
		return nn >= sum;
	}
	
	public void test() {
		Map<String, OEty> allDataMap = new HashMap<String, OEty>();
		
		OEty oe = new OEty();
		oe.allCount = 600;
		oe.canUseCount = 100;
		oe.needCount = 0;
		allDataMap.put("1", oe);
		
		oe = new OEty();
		oe.allCount = 200;
		oe.canUseCount = 100;
		oe.needCount = 0;
		allDataMap.put("2", oe);
		
		oe = new OEty();
		oe.allCount = 200;
		oe.canUseCount = 100;
		oe.needCount = 0;
		allDataMap.put("3", oe);
		
		divideFlight(allDataMap, 150);
		
		
		Set<String> s = allDataMap.keySet();
		Iterator<String> itor = s.iterator();
		
		while(itor.hasNext()) {
			String key = itor.next();
			OEty to = allDataMap.get(key);
			System.out.println(key + ": " + to.allCount + "_" + to.canUseCount + " " + to.needCount);
		}
		
	}

	public static void main(String[] args) {
		TestTT tt = new TestTT();
		tt.test();
	}
}
