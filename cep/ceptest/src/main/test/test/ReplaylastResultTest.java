package test;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.SafeIterator;

import example.model.User;

/**
 * 重现上一个/上一批事件的处理结果
 * 这里重现的是view中两个User的id和，在新event进入后，可得到进入前view中User产生的事件处理结果
 * 
 * @author luonq(luonq@primeton.com)
 *
 */
public class ReplaylastResultTest {
	static EPStatement state;
	static EPRuntime runtime;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider();

		EPAdministrator admin = epService.getEPAdministrator();

		String epl1 = "select avg(id) as aid from " + User.class.getName() + ".win:length(2)";

		state = admin.createEPL(epl1);
		state.addListenerWithReplay(new ResultListener());

		runtime = epService.getEPRuntime();
	}

	@Test
	public void test1() {
		User user1 = new User();
		user1.setId(1);
		System.out.println("sendEvent: " + user1);
		runtime.sendEvent(user1);
		assertEquals(1.0, safeCurrentEventResult(state), 0);
        System.out.println("replayResult: 1.0");
	}
	
	@Test
	public void test2() {
		User user2 = new User();
		user2.setId(3);
		System.out.println("sendEvent: " + user2);
		runtime.sendEvent(user2);
		assertEquals(2.0, safeCurrentEventResult(state), 0);
        System.out.println("replayResult: 2.0");
	}
	
	@Test
	public void test3() {
		User user3 = new User();
		user3.setId(5);
		System.out.println("sendEvent: " + user3);
		runtime.sendEvent(user3);
		assertEquals(4.0, safeCurrentEventResult(state), 0);
        System.out.println("replayResult: 4.0");
	}
	
	@Test
	public void test4() {
		User user4 = new User();
		user4.setId(5);
		System.out.println("sendEvent: " + user4);
		runtime.sendEvent(user4);
		assertEquals(5.0, safeCurrentEventResult(state), 0);
        System.out.println("replayResult: 5.0");
	}
	
	public static double safeCurrentEventResult(EPStatement state) {
		EventBean event = null;
		SafeIterator<EventBean> events = state.safeIterator();
		while (events.hasNext()) 
			event = events.next();
		events.close();
		return (Double) event.get("aid");
	}

}
