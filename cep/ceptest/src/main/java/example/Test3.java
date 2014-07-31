package example;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

/**
 * Created by Luonanqin on 5/15/14.
 */
class TestEvent3 {

	private int age;

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
}
class Test3Listener implements UpdateListener{

	public void update(EventBean[] newEvents, EventBean[] oldEvents) {
		if (newEvents != null) {
			for (int i = 0; i < newEvents.length; i++) {
				EventBean newEvent = newEvents[i];
				System.out.println(newEvent.getUnderlying());
			}
		}
	}
}
public class Test3 {

	public static void main(String[] args) {
		Configuration config = new Configuration();
		config.configure("esper.examples.cfg.xml");

		EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider(config);
		EPRuntime runtime = epService.getEPRuntime();
		EPAdministrator admin = epService.getEPAdministrator();

		String epl = "select * from pattern[i = " + TestEvent3.class.getName() + "(age in [1,2,3])]";
		admin.createEPL(epl).addListener(new Test3Listener());

		TestEvent3 t1=new TestEvent3();
		t1.setAge(1);
		runtime.sendEvent(t1);

		TestEvent3 t2=new TestEvent3();
		t2.setAge(2);
		runtime.sendEvent(t2);

		TestEvent3 t3=new TestEvent3();
		t3.setAge(3);
		runtime.sendEvent(t3);
	}
}
