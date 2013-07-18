package test;

import java.util.Arrays;

import junit.framework.Assert;

import org.junit.Test;

import com.espertech.esper.client.ConfigurationOperations;
import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EventType;


public class AddArrayEventTypeTest {

	@Test
	public void test() throws InterruptedException {
		EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider();

		EPAdministrator admin = epService.getEPAdministrator();
		ConfigurationOperations config = admin.getConfiguration();

		config.addEventType("arrayTest", new String[] { "a", "b" }, new Object[] { String.class, int.class });
		EventType event = config.getEventType("arrayTest");
		Assert.assertEquals("[a, b]", Arrays.asList(event.getPropertyNames()).toString());
		System.out.println("Event Names: " + Arrays.asList(event.getPropertyNames()));

		config.updateObjectArrayEventType("arrayTest", new String[] { "c" }, new Object[] { long.class });
		event = config.getEventType("arrayTest");
		Assert.assertEquals("[a, b, c]", Arrays.asList(event.getPropertyNames()).toString());
		System.out.println("Event Names: " + Arrays.asList(event.getPropertyNames()));

	}

}
