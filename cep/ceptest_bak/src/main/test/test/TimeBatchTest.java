package test;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;

import example.model.Timer;

/**
 * 外部时间批处理测试
 * 设置当前时间为初始时间，每两秒为一个时间窗口，计算Timer类的平均time
 * 
 * @author luonq(luonq@primeton.com)
 *
 */
public class TimeBatchTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider();

		EPAdministrator admin = epService.getEPAdministrator();

		String epl1 = "select avg(time) as at, current_timestamp as ct from " + Timer.class.getName() + ".win:ext_timed_batch(current_timestamp(), 2 sec)";

		EPStatement state = admin.createEPL(epl1);
		state.addListener(new TimeBatchListener());
		System.out.println("InitialTime:" + System.currentTimeMillis());

		EPRuntime runtime = epService.getEPRuntime();

		Timer t1 = new Timer();
		t1.setId(1);
		t1.setTime(15);
		runtime.sendEvent(t1);

		Timer t2 = new Timer();
		t2.setId(2);
		t2.setTime(35);
		runtime.sendEvent(t2);

		Thread.sleep(3000);

		Timer t3 = new Timer();
		t3.setId(3);
		t3.setTime(30);
		runtime.sendEvent(t3);

		Timer t4 = new Timer();
		t4.setId(4);
		t4.setTime(20);
		runtime.sendEvent(t4);

		Thread.sleep(2000);

		Timer t5 = new Timer();
		t5.setId(5);
		t5.setTime(20);
		runtime.sendEvent(t5);
	}

	@Test
	public void test1() {
		Assert.assertEquals(25.0, TimeBatchListener.avgTime[0]);
		System.out.println("avgTime: " + TimeBatchListener.avgTime[0] + " currentTime" + TimeBatchListener.currentTime[0]);
	}

	@Test
	public void test2() {
		Assert.assertEquals(25.0, TimeBatchListener.avgTime[1]);
		System.out.println("avgTime: " + TimeBatchListener.avgTime[1] + " currentTime" + TimeBatchListener.currentTime[1]);
	}

	@Test
	public void test3() {
		Assert.assertEquals(-1.0, TimeBatchListener.avgTime[2]);
	}

}
