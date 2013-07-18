package test;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

public class AverageListener implements UpdateListener {
	public static Object d;
	
	public void update(EventBean[] newEvents, EventBean[] oldEvents) {
		if (newEvents != null) {
			EventBean event = newEvents[0];
			d = event.get("avg(price)");
		}
	}
}
