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

public class InsertIntoTest {
	private static EPRuntime runtime;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider();

		EPAdministrator admin = epService.getEPAdministrator();

		String product = Product.class.getName();
		String epl1 = "insert into AllEvent(p,t) select price, type from " + product;
		String epl2 = "select sum(p), t from AllEvent.std:groupwin(t).win:length_batch(2) group by t";

		admin.createEPL(epl1);
		EPStatement state = admin.createEPL(epl2);
		state.addListener(new InsertIntoListener());

		runtime = epService.getEPRuntime();
	}

	@Test
	public void test() {
		Product esb = new Product();
		esb.setPrice(1);
		esb.setType("esb");
		System.out.println("sendEvent: " + esb);
		runtime.sendEvent(esb);

		Product eos = new Product();
		eos.setPrice(2);
		eos.setType("eos");
		System.out.println("sendEvent: " + eos);
		runtime.sendEvent(eos);

		Product esb1 = new Product();
		esb1.setPrice(2);
		esb1.setType("esb");
		System.out.println("sendEvent: " + esb1);
		runtime.sendEvent(esb1);
		
		Assert.assertEquals(3, InsertIntoListener.sum);
		System.out.println("type: " +  InsertIntoListener.type + ", sum(price): " + InsertIntoListener.sum);

		Product eos1 = new Product();
		eos1.setPrice(5);
		eos1.setType("eos");
		System.out.println("sendEvent: " + eos1);
		runtime.sendEvent(eos1);
		
		Assert.assertEquals(7, InsertIntoListener.sum);
		System.out.println("type: " +  InsertIntoListener.type + ", sum(price): " + InsertIntoListener.sum);
	}

}
