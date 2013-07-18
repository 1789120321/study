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

public class AverageBatchTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider();

		EPAdministrator admin = epService.getEPAdministrator();

		String product = Product.class.getName();
		String epl1 = "select avg(price) from " + product + ".win:length_batch(2)";

		EPStatement state = admin.createEPL(epl1);
		state.addListener(new AverageBatchListener());

		EPRuntime runtime = epService.getEPRuntime();
		
		Product esb = new Product();
		esb.setPrice(1);
		esb.setType("esb");
		runtime.sendEvent(esb);

		Product eos = new Product();
		eos.setPrice(2);
		eos.setType("eos");
		runtime.sendEvent(eos);

		Product esb1 = new Product();
		esb1.setPrice(2);
		esb1.setType("esb");
		runtime.sendEvent(esb1);

		Product eos1 = new Product();
		eos1.setPrice(5);
		eos1.setType("eos");
		runtime.sendEvent(eos1);
	}

	@Test
	public void test() {
		Assert.assertEquals(1.5, AverageBatchListener.obj[0]);
		System.out.println("avg price(win:length_batch(2)): " + AverageBatchListener.obj[0]);
		Assert.assertEquals(3.5, AverageBatchListener.obj[1]);
		System.out.println("avg price(win:length_batch(2)): " + AverageBatchListener.obj[1]);
	}

}
