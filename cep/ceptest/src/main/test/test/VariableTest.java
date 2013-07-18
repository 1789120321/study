package test;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import com.espertech.esper.client.ConfigurationOperations;
import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;

import example.model.ESB;
import example.model.Product;

public class VariableTest {
	private static EPServiceProvider epService;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		epService = EPServiceProviderManager.getDefaultProvider();

		EPAdministrator admin = epService.getEPAdministrator();

		String esb = ESB.class.getName();
		String context1 = "create context esbtest partition by id from " + esb;
		String epl1 = "context esbtest select avg(price) as aPrice from " + esb;

		admin.createEPL(context1, "context1");
		admin.createEPL(epl1, "epl1");

		ConfigurationOperations conf = admin.getConfiguration();
		conf.addEventType("product", Product.class);
		conf.addVariable("abc", String.class, "initVariable");
		conf.addVariable("constabc", int.class.getName(), 123, true);
	}

	@Test
	public void test() {
		Object variableValue = epService.getEPRuntime().getVariableValue("abc");
		Object constValue = epService.getEPRuntime().getVariableValue("constabc");
		Assert.assertEquals("initVariable", variableValue.toString());
		System.out.println("variable value: " + variableValue);
		Assert.assertEquals("123", constValue.toString());
		System.out.println("const value: " + constValue);
	}

}
