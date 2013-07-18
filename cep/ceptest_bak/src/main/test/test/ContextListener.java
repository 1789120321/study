package test;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

public class ContextListener implements UpdateListener {
	public static Object eId, ePrice;

	public void update(EventBean[] newEvents, EventBean[] oldEvents) {
		if (newEvents != null) {
			EventBean event = newEvents[0];
			eId = event.get("id");
			ePrice = event.get("aPrice");
		}
	}

}
