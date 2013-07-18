package test;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

public class TimeBatchListener implements UpdateListener {
	public static double[] avgTime = new double[3];
	public static Long[] currentTime = new Long[3];
	public static int i = 0;

	public void update(EventBean[] newEvents, EventBean[] oldEvents) {
		avgTime[i] = (Double) newEvents[0].get("at");
		currentTime[i] = (Long) newEvents[0].get("ct");
		i++;
		avgTime[i] = -1.0;
	}
}
