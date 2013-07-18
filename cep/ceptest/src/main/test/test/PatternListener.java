package test;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

public class PatternListener implements UpdateListener {
	public static int len;

	public void update(EventBean[] newEvents, EventBean[] oldEvents) {
		if (newEvents != null) 
			len = newEvents.length;
	}
}
