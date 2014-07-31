package test;

import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

/**
 * Created by Luonanqin on 5/15/14.
 */
class ATest {
	private int age;
	private int id;

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}

class ATestListener implements UpdateListener {

	public void update(EventBean[] newEvents, EventBean[] oldEvents) {
		if (newEvents != null) {
			for (int i = 0; i < newEvents.length; i++) {
				EventBean newEvent = newEvents[i];
				System.out.println(newEvent.getUnderlying());
			}
		}
	}
}

public class AAATest {

	public static void main(String[] args) {
		EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider();
		EPRuntime runtime = epService.getEPRuntime();
		EPAdministrator admin = epService.getEPAdministrator();

		String atest = ATest.class.getName();
		String epl1 = "@Hint('reclaim_group_aged=1,reclaim_group_freq=2') select id, sum(age) from " + atest + ".win:time_batch(2 sec) group by id";

		admin.createEPL(epl1).addListener(new ATestListener());

		ATest a1 = new ATest();
		a1.setId(1);
		a1.setAge(0);

		ATest a2 = new ATest();
		a2.setId(2);
		a2.setAge(1);

		ATest a3 = new ATest();
		a3.setId(1);
		a3.setAge(2);

		ATest a4 = new ATest();
		a4.setId(2);
		a4.setAge(3);

		runtime.sendEvent(a1);
		runtime.sendEvent(a2);
		try {
			Thread.sleep(8000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		runtime.sendEvent(a3);
		runtime.sendEvent(a4);

		System.out.println();
	}
}
