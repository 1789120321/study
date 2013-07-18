package test;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

public class ExternallyTimeBatchListener implements UpdateListener {
	public static Object[] avg = new Object[2];
	public static Object[] con = new Object[2];
	public static int i = 0;

	public void update(EventBean[] newEvents, EventBean[] oldEvents) {
		avg[i] = newEvents[0].get("at");
		con[i] =  newEvents[0].get("count");
		i++;
	}

}
