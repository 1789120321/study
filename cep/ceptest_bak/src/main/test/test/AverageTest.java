package test;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;

import example.model.Product;

public class AverageTest {
	private static EPRuntime runtime;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider();

		EPAdministrator admin = epService.getEPAdministrator();

		String product = Product.class.getName();
		String epl1 = "select avg(price) from " + product + ".win:length(2)";

		EPStatement state = admin.createEPL(epl1);
		state.addListener(new AverageListener());

		runtime = epService.getEPRuntime();
	}

	@Test
	public void test() {
		Product esb = new Product();
		esb.setPrice(1);
		esb.setType("esb");
		runtime.sendEvent(esb);
		Assert.assertEquals(1.0, AverageListener.d);
		System.out.println("avg price(win:length(2)): " + AverageListener.d);

		Product eos = new Product();
		eos.setPrice(2);
		eos.setType("eos");
		runtime.sendEvent(eos);
		Assert.assertEquals(1.5, AverageListener.d);
		System.out.println("avg price(win:length(2)): " + AverageListener.d);

		Product esb1 = new Product();
		esb1.setPrice(2);
		esb1.setType("esb");
		runtime.sendEvent(esb1);
		Assert.assertEquals(2.0, AverageListener.d);
		System.out.println("avg price(win:length(2)): " + AverageListener.d);

		Product eos1 = new Product();
		eos1.setPrice(5);
		eos1.setType("eos");
		runtime.sendEvent(eos1);
		Assert.assertEquals(3.5, AverageListener.d);
		System.out.println("avg price(win:length(2)): " + AverageListener.d);
	}

}
