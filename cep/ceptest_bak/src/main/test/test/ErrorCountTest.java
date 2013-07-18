package test;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

import example.model.Product;

class CountListener implements UpdateListener {

	private int current = 0;
	private EPRuntime runtime;

	public CountListener(EPRuntime runtime) {
		this.runtime = runtime;
	}

	public void update(EventBean[] newEvents, EventBean[] oldEvents) {
		if (newEvents != null) {
			long count = (Long) newEvents[0].get("prevcount(i)");
			current = (int) count;
		}
		if (oldEvents != null) {
			Map<String, Object> event = new HashMap<String, Object>();
			event.put("sum", current);
			runtime.sendEvent(event, "OneTest");

			current -= oldEvents.length;
		}
	}
}

class Result1Listener implements UpdateListener {
	public static boolean flag;
	
	public void update(EventBean[] newEvents, EventBean[] oldEvents) {
		if (newEvents != null) {
			flag = true;
			System.out.println("Clarm!!!!");
		} else {
			flag = false;
		}
	} 
}

public class ErrorCountTest {
	private static EPRuntime runtime;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		int N = 5;
		EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider();

		EPAdministrator admin = epService.getEPAdministrator();

		Map<String, Object> mapDef = new HashMap<String, Object>();
		mapDef.put("sum", int.class);

		admin.getConfiguration().addEventType("OneTest", mapDef);

		runtime = epService.getEPRuntime();

		String product = Product.class.getName();
		String count = "select irstream *, prevcount(i) from " + product + ".win:time(5 sec) as i";
		String result = "select * from OneTest(sum > " + N + ")";

		EPStatement state1 = admin.createEPL(count);
		state1.addListener(new CountListener(runtime));
		EPStatement state2 = admin.createEPL(result);
		state2.addListener(new Result1Listener());
	}

	@Test
	public void test() throws InterruptedException {
		Product esb = new Product();
		esb.setPrice(1);
		esb.setType("esb");
		runtime.sendEvent(esb);

		Product eos = new Product();
		eos.setPrice(2);
		eos.setType("eos");
		runtime.sendEvent(eos);

		Product esb1 = new Product();
		esb1.setPrice(3);
		esb1.setType("esb");
		runtime.sendEvent(esb1);

		Product eos1 = new Product();
		eos1.setPrice(5);
		eos1.setType("eos");
		runtime.sendEvent(eos1);

		Product esb2 = new Product();
		esb2.setPrice(6);
		esb2.setType("esb");
		runtime.sendEvent(esb2);

		Product eos3 = new Product();
		eos3.setPrice(7);
		eos3.setType("eos");
		runtime.sendEvent(eos3);

		Thread.sleep(5000);
        Assert.assertTrue(Result1Listener.flag);
	}

}
