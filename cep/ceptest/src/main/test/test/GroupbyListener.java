package test;

import java.util.ArrayList;
import java.util.List;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

public class GroupbyListener implements UpdateListener {
	public static String[] t = new String[4], list = new String[4];
	public static int[] last = new int[4];
	public static int i = 0;

	public void update(EventBean[] newEvents, EventBean[] oldEvents) {
		if (newEvents != null) {
			System.out.println("==========RESULT==========");
			for (EventBean event : newEvents) {
				String type = "";
				int lastPrice = 0;
				List<Integer> priceList = new ArrayList<Integer>();

				Object o1 = event.get("type");
				if (o1 != null) {
					type = (String) o1;
				}
				Object o2 = event.get("lastPrice");
				if (o2 != null) {
					lastPrice = (Integer) o2;
				}
				Object o3 = event.get("priceList");
				if (o3 != null) {
					for (int price : (int[]) o3) {
						priceList.add(price);
					}
				}

				t[i] = type;
				last[i] = lastPrice;
				list[i] = "" + priceList;
				System.out.println(priceList);
				i++;
			}
		}
	}
}
