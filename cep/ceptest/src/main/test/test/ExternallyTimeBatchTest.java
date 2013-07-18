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

public class ExternallyTimeBatchTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider();

		EPAdministrator admin = epService.getEPAdministrator();
		long timeVar = 1368629247245L;
		epService.getEPAdministrator().getConfiguration().addVariable("stdTime", long.class, timeVar);

		String timer = Timer.class.getName();
		String epl1 = "expression st{ stdTime } select st(), prevcount(t) as count, avg(time) as at from " + timer + ".win:ext_timed_batch(st(), 2 sec) as t";
		/**	
		 * 因为espression内部返回的是单个变量，所以可以不需要建立expression，如下所示：
		 * String epl1 = "select prevcount(t) as count, avg(time) as at from " + timer + ".win:ext_timed_batch(stdTime, 2 sec) as t";
		 */
		long ct = System.currentTimeMillis();

		EPStatement state = admin.createEPL(epl1);
		System.out.println(state.getText());
		state.addListener(new ExternallyTimeBatchListener());

		EPRuntime runtime = epService.getEPRuntime();
		
		Timer t1 = new Timer();
		t1.setId(1);
		t1.setTime(15);
		runtime.sendEvent(t1);

		long temp = System.currentTimeMillis();
		long interval = temp - ct;
		ct = temp;
		timeVar += interval;
		runtime.setVariableValue("stdTime", timeVar);
		runtime.getVariableValue("stdTime");

		Timer t2 = new Timer();
		t2.setId(2);
		t2.setTime(35);
		runtime.sendEvent(t2);

		Thread.sleep(3000);
		temp = System.currentTimeMillis();
		interval = temp - ct;
		ct = temp;
		timeVar += interval;
		runtime.setVariableValue("stdTime", timeVar);

		Timer t3 = new Timer();
		t3.setId(3);
		t3.setTime(30);
		runtime.sendEvent(t3);

		temp = System.currentTimeMillis();
		interval = temp - ct;
		ct = temp;
		timeVar += interval;
		runtime.setVariableValue("stdTime", timeVar);

		Timer t4 = new Timer();
		t4.setId(4);
		t4.setTime(20);
		runtime.sendEvent(t4);

		Thread.sleep(3000);
		temp = System.currentTimeMillis();
		interval = temp - ct;
		ct = temp;
		timeVar += interval;
		runtime.setVariableValue("stdTime", timeVar);

		Timer t5 = new Timer();
		t5.setId(5);
		t5.setTime(20);
		runtime.sendEvent(t5);

		temp = System.currentTimeMillis();
		temp = System.currentTimeMillis();
		interval = temp - ct;
		ct = temp;
		timeVar += interval;
		runtime.setVariableValue("stdTime", timeVar);
		interval = temp - ct;
		ct = temp;
		timeVar += interval;
		runtime.setVariableValue("stdTime", timeVar);

		Timer t6 = new Timer();
		t6.setId(6);
		t6.setTime(25);
		runtime.sendEvent(t6);
	}

	@Test
	public void test() {
		Assert.assertEquals(25.0, ExternallyTimeBatchListener.avg[0]);
		Assert.assertEquals("2", ExternallyTimeBatchListener.con[0].toString());
		System.out.println("avgTime: " + ExternallyTimeBatchListener.avg[0] + " " + ExternallyTimeBatchListener.con[0]);
		
		Assert.assertEquals(25.0, ExternallyTimeBatchListener.avg[1]);
		Assert.assertEquals("2", ExternallyTimeBatchListener.con[1].toString());
		System.out.println("avgTime: " + ExternallyTimeBatchListener.avg[1] + " " + ExternallyTimeBatchListener.con[1]);
	}

}
