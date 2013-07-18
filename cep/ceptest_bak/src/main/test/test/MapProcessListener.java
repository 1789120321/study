package test;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

public class MapProcessListener implements UpdateListener {
	public static int[] id = new int[4], sumId = new int[4];
	public static int i = 0;

	public void update(EventBean[] newEvents, EventBean[] oldEvents) {
		if (newEvents != null) {
			Object o1 = newEvents[0].get("id");
			if (o1 != null)
				id[i] = (Integer) o1;
			Object o2 = newEvents[0].get("sumCount");
			if (o2 != null) {
				sumId[i] = (Integer) o2;
			}
			i++;
		}
	}
}
