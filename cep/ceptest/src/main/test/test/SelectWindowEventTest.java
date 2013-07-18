package test;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;

import example.model.User;

/**
 * 查看当前view中存在哪些事件
 * 为状态恢复做准备，即将某一时刻的状态记录下来，这里记录的是view中的残留事件
 * 
 * @author luonq(luonq@primeton.com)
 *
 */
public class SelectWindowEventTest {
	static EPRuntime runtime;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider();

		EPAdministrator admin = epService.getEPAdministrator();

		String viewEpl = "select prevwindow(user) from " + User.class.getName() + ".win:length(2) as user";

		EPStatement state1 = admin.createEPL(viewEpl);
		state1.addListenerWithReplay(new ViewListener());

	    runtime = epService.getEPRuntime();	
	}

	@Test
	public void test1() {
		User user1 = new User();
		user1.setId(1);
		System.out.println("sendEvent: " + user1);
		runtime.sendEvent(user1);
		assertEquals("User: 1 ", ViewListener.user[0]);
		System.out.println(ViewListener.user[0]);
	}
	
	@Test
	public void test2() {
		User user2 = new User();
		user2.setId(3);
		System.out.println("sendEvent: " + user2);
		runtime.sendEvent(user2);
		assertEquals("User: 3 User: 1 ", ViewListener.user[1]);
		System.out.println(ViewListener.user[1]);
	}
	
	@Test
	public void test3() {
		User user3 = new User();
		user3.setId(5);
		System.out.println("sendEvent: " + user3);
		runtime.sendEvent(user3);
		assertEquals("User: 5 User: 3 ", ViewListener.user[2]);
		System.out.println(ViewListener.user[2]);
	}

}
