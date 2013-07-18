package test;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

public class StandardGroupWinListener implements UpdateListener {
	public static Object t, p;

	public void update(EventBean[] newEvents, EventBean[] oldEvents) {
		if (newEvents != null) {
			EventBean event = newEvents[0];
			t = event.get("type");
			p = event.get("sum(price)");
		}
	}

}
