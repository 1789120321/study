package test;

import java.util.Iterator;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.UpdateListener;

import example.model.Timer;

public class IteratorUpdateListenerTest {
	private static EPStatement state;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider();

		EPAdministrator admin = epService.getEPAdministrator();

		String timer = Timer.class.getName();
		String epl1 = "select * from " + timer;

		state = admin.createEPL(epl1);
		state.addListener(new IteratorUpdateListener());
	}

	@Test
	public void test() {
		Iterator<UpdateListener> listeners = state.getUpdateListeners();
		while (listeners.hasNext()) {
			String s = listeners.next().getClass().getName();
			Assert.assertEquals("test.IteratorUpdateListener", s);
			System.out.println(s);
		}
	}

}
