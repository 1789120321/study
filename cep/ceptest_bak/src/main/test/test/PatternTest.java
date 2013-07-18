package test;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;

import example.model.Fruit;

public class PatternTest {
	private static EPRuntime runtime;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider();

		EPAdministrator admin = epService.getEPAdministrator();

		String fruit = Fruit.class.getName();
		String epl1 = "every p1=" + fruit + " -> p2=" + fruit + "(p1.price>p2.price)";
		EPStatement state = admin.createPattern(epl1, "epl1");

		state.addListener(new PatternListener());

		runtime = epService.getEPRuntime();
	}

	@Test
	public void test() {
		Fruit apple = new Fruit();
		apple.setName("apple");
		apple.setPrice(50);
		runtime.sendEvent(apple);

		Fruit orange = new Fruit();
		orange.setName("orange");
		orange.setPrice(55);
		runtime.sendEvent(orange);

		Fruit banana = new Fruit();
		banana.setName("banana");
		banana.setPrice(48);
		runtime.sendEvent(banana);
		
		Assert.assertEquals(2, PatternListener.len);
		System.out.println(PatternListener.len);

		Fruit pink = new Fruit();
		pink.setName("pink");
		pink.setPrice(47);
		runtime.sendEvent(pink);
		
		Assert.assertEquals(1, PatternListener.len);
		System.out.println(PatternListener.len);

		Fruit pear = new Fruit();
		pear.setName("pear");
		pear.setPrice(43);
		runtime.sendEvent(pear);
		
		Assert.assertEquals(1, PatternListener.len);
		System.out.println(PatternListener.len);

		Fruit peach = new Fruit();
		peach.setName("peach");
		peach.setPrice(46);
		runtime.sendEvent(peach);

		Fruit strawberry = new Fruit();
		strawberry.setName("strawberry");
		strawberry.setPrice(43);
		runtime.sendEvent(strawberry);
		
		Assert.assertEquals(1, PatternListener.len);
		System.out.println(PatternListener.len);
	}

}
