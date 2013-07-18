package test;

import java.io.File;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.ConfigurationOperations;
import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EventType;

import example.model.ESB;
import example.model.Product;

public class ConfigTest {
	private static ConfigurationOperations conf;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Configuration config = new Configuration();
		config.configure(new File("src/main/test/test/esper.examples.cfg.xml"));
		EPServiceProvider epService = EPServiceProviderManager.getProvider("default", config);

		EPAdministrator admin = epService.getEPAdministrator();

		String esb = ESB.class.getName();
		String context1 = "create context esbtest partition by id from " + esb;
		String epl1 = "context esbtest select avg(price) as aPrice from " + esb;

		admin.createEPL(context1, "context1");
		admin.createEPL(epl1, "epl1");

		conf = admin.getConfiguration();
		conf.addEventType("product", Product.class);
	}

	@Test
	public void test() {
		EventType[] types = conf.getEventTypes();
		
		Assert.assertEquals("product", types[0].getName().toString());
		Assert.assertEquals("class example.model.Product", types[0].getUnderlyingType().toString());
		System.out.println("name: " + types[0].getName() + ", underlyingName: " + types[0].getUnderlyingType());
		
		Assert.assertEquals("example.model.ESB", types[1].getName().toString());
		Assert.assertEquals("class example.model.ESB", types[1].getUnderlyingType().toString());
		System.out.println("name: " + types[1].getName() + ", underlyingName: " + types[1].getUnderlyingType());
		
		Assert.assertEquals("timer", types[2].getName().toString());
		Assert.assertEquals("class example.model.Timer", types[2].getUnderlyingType().toString());
		System.out.println("name: " + types[2].getName() + ", underlyingName: " + types[2].getUnderlyingType());
	}

}
