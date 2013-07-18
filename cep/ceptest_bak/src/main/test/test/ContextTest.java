package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.context.ContextPartitionSelectorSegmented;

import example.model.ESB;

public class ContextTest {
	private static EPStatement state;
	private static EPRuntime runtime;
	private static ContextPartitionSelectorSegmented selectCtx;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider();

		EPAdministrator admin = epService.getEPAdministrator();

		String esb = ESB.class.getName();
		// 创建context
		String epl1 = "create context esbtest partition by id from " + esb;
		String epl2 = "context esbtest select avg(price) as aPrice, id from " + esb;

		EPStatement context = admin.createEPL(epl1);
		state = admin.createEPL(epl2);
		state.addListener(new ContextListener());

		runtime = epService.getEPRuntime();
		// 查看id为1的平均价格
		selectCtx = new ContextPartitionSelectorSegmented() {

			// 该方法的实现方式与context定义的properties有关，如果有两个property id和time，则Object数组长度为2，obj[0]为id值，
			// obj[1]为time值，然后再添加到list中并返回
			public List<Object[]> getPartitionKeys() {
				Object[] o = new Object[1];
				o[0] = 1;
				List<Object[]> list = new ArrayList<Object[]>();
				list.add(o);
				return list;
			}
		};
	}

	@Test
	public void test() {
		ESB t1 = new ESB();
		t1.setId(1);
		t1.setPrice(20);
		System.out.println("sendEvent: id=1, price=20");
		runtime.sendEvent(t1);
		Assert.assertEquals(20.0, ContextListener.ePrice);
		System.out.println("id: " + ContextListener.eId + ", avgPrice: " + ContextListener.ePrice);

		ESB t2 = new ESB();
		t2.setId(2);
		t2.setPrice(30);
		System.out.println("sendEvent: id=2, price=30");
		runtime.sendEvent(t2);
		Assert.assertEquals(30.0, ContextListener.ePrice);
		System.out.println("id: " + ContextListener.eId + ", avgPrice: " + ContextListener.ePrice);

		ESB t3 = new ESB();
		t3.setId(1);
		t3.setPrice(30);
		System.out.println("sendEvent: id=1, price=30");
		runtime.sendEvent(t3);
		Assert.assertEquals(25.0, ContextListener.ePrice);
		System.out.println("id: " + ContextListener.eId + ", avgPrice: " + ContextListener.ePrice);

		Iterator<EventBean> it = state.iterator(selectCtx);
		EventBean event = it.hasNext() ? it.next() : null;
		Assert.assertEquals(25.0, event.get("aPrice"));
		System.out.println("Iterator context: id=1, avgPrice=" + event.get("aPrice"));

		ESB t4 = new ESB();
		t4.setId(2);
		t4.setPrice(40);
		System.out.println("sendEvent: id=2, price=40");
		runtime.sendEvent(t4);
		Assert.assertEquals(35.0, ContextListener.ePrice);
		System.out.println("id: " + ContextListener.eId + ", avgPrice: " + ContextListener.ePrice);
		

		ESB t5 = new ESB();
		t5.setId(5);
		t5.setPrice(10);
		System.out.println("sendEvent: id=5, price=10");
		runtime.sendEvent(t5);
		Assert.assertEquals(10.0, ContextListener.ePrice);
		System.out.println("id: " + ContextListener.eId + ", avgPrice: " + ContextListener.ePrice);

		ESB t6 = new ESB();
		t6.setId(5);
		t6.setPrice(20);
		System.out.println("sendEvent: id=5, price=20");
		runtime.sendEvent(t6);
		Assert.assertEquals(15.0, ContextListener.ePrice);
		System.out.println("id: " + ContextListener.eId + ", avgPrice: " + ContextListener.ePrice);
	}

}
