package test;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

public class AverageBatchListener implements UpdateListener {
	public static Object[] obj = new Object[4];
	public static int i = 0;

	public void update(EventBean[] newEvents, EventBean[] oldEvents) {
		if (newEvents != null) {
			EventBean event = newEvents[0];
			obj[i] = event.get("avg(price)");
			i++;
		}
	}

}
