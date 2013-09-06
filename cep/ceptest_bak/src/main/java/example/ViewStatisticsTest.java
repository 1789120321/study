package example;

import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

import example.model.Fruit;

/**
 * 每两秒输出window里的事件量，方便做统计
 * 
 * @author luonanqin
 *
 */
class ViewStatisticsTestListener implements UpdateListener {

	public void update(EventBean[] newEvents, EventBean[] oldEvents) {
		if (newEvents != null)
			System.out.println("WindowTest has " + newEvents[0].get("count(*)") + " events.");
	}

}

public class ViewStatisticsTest {

	public static void main(String[] args) throws InterruptedException {
		EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider();

		EPAdministrator admin = epService.getEPAdministrator();

		String fruit = Fruit.class.getName();
		String epl1 = "select * from " + fruit + ".win:length(100)";
		String epl2 = "select prevcount(a) from " + fruit + ".win:length_batch(100) as a output snapshot every 2 sec";
		EPStatement state = admin.createEPL(epl1, "epl1");
		EPStatement state1 = admin.createEPL(epl2, "epl2");

		state1.addListener(new ViewStatisticsTestListener());

		EPRuntime runtime = epService.getEPRuntime();

		int i = 0;

		Fruit apple = new Fruit();
		apple.setName("apple");
		apple.setPrice(50);
		System.out.println("SendEvent. Count is " + (++i));
		runtime.sendEvent(apple);

		Fruit orange = new Fruit();
		orange.setName("orange");
		orange.setPrice(55);
		System.out.println("SendEvent. Count is " + (++i));
		runtime.sendEvent(orange);

		Fruit banana = new Fruit();
		banana.setName("banana");
		banana.setPrice(48);
		System.out.println("SendEvent. Count is " + (++i));
		runtime.sendEvent(banana);

		Thread.sleep(2000);

		Fruit pink = new Fruit();
		pink.setName("pink");
		pink.setPrice(47);
		System.out.println("SendEvent. Count is " + (++i));
		runtime.sendEvent(pink);

		Fruit pear = new Fruit();
		pear.setName("pear");
		pear.setPrice(43);
		System.out.println("SendEvent. Count is " + (++i));
		runtime.sendEvent(pear);

		Thread.sleep(2000);

		Fruit peach = new Fruit();
		peach.setName("peach");
		peach.setPrice(46);
		System.out.println("SendEvent. Count is " + (++i));
		runtime.sendEvent(peach);

		Fruit strawberry = new Fruit();
		strawberry.setName("strawberry");
		strawberry.setPrice(43);
		System.out.println("SendEvent. Count is " + (++i));
		runtime.sendEvent(strawberry);

		Thread.sleep(2000);
	}
}
