package test;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

public class LogProcessListener implements UpdateListener {
	public static int len;

	public void update(EventBean[] newEvents, EventBean[] oldEvents) {
		if (newEvents != null) {
			//			System.out.println();
			//			Map<String, String>[] results = (Map<String, String>[]) (newEvents[0].get("logs"));
			//			for (Map<String, String> r : results) {
			//				System.out.println(r);
			//			}
			len = newEvents.length;
		}
	}

}
