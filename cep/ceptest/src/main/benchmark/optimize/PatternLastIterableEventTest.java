package optimize;

import java.util.Iterator;

import junit.framework.Assert;

import org.junit.Test;

import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;

import example.model.Fruit;

/**
 * 测试对象：com.espertech.esper.core.service.StatementResultServiceImpl
 * 测试目标：iterator()方法返回最新的匹配pattern结果
 * 
 * @author luonanqin
 *
 */
public class PatternLastIterableEventTest
{

	@Test
	public void test()
	{
		EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider();

		EPAdministrator admin = epService.getEPAdministrator();

		String fruit = Fruit.class.getName();
		String epl1 = "every p1=" + fruit + " -> p2=" + fruit + "(p1.price>p2.price)";
		EPStatement state = admin.createPattern(epl1, "epl1");

		EPRuntime runtime = epService.getEPRuntime();

		Fruit apple = new Fruit();
		apple.setName("apple");
		apple.setPrice(50);
		runtime.sendEvent(apple);

		Fruit orange = new Fruit();
		orange.setName("orange");
		orange.setPrice(55);
		runtime.sendEvent(orange);

		Iterator<EventBean> i1 = state.iterator();
		Assert.assertFalse(i1.hasNext());

		Fruit banana = new Fruit();
		banana.setName("banana");
		banana.setPrice(48);
		runtime.sendEvent(banana);

		// 测试点=========
		Iterator<EventBean> i2 = state.iterator();
		while (i2.hasNext())
		{
			EventBean event = i2.next();
			Assert.assertEquals(orange, event.get("p1"));
			Assert.assertEquals(banana, event.get("p2"));
		}
		// 测试点=========
	}
}
