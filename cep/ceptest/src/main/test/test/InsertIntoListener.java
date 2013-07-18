package test;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

public class InsertIntoListener implements UpdateListener {
	public static Object type, sum;

	public void update(EventBean[] newEvents, EventBean[] oldEvents) {
		if (newEvents != null) {
			EventBean event = newEvents[0];
			type = event.get("t");
			sum = event.get("sum(p)");
		}
	}

}
