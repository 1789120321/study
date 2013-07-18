package test;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPOnDemandQueryResult;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;

public class LogProcessTest {
	private static EPRuntime runtime;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider();

		EPAdministrator admin = epService.getEPAdministrator();

		Map<String, Object> mapDef = new HashMap<String, Object>();
		mapDef.put("batch", String.class);
		mapDef.put("eventNode", String.class);
		mapDef.put("eventNum", String.class);
		mapDef.put("eventTime", String.class);
		admin.getConfiguration().addEventType("Logger", mapDef);

		Map<String, Object> output = new HashMap<String, Object>();
		output.put("batch", String.class);
		admin.getConfiguration().addEventType("OutputResult", output);

		//		String context = "create context abc partition by batch from Logger(eventNode!='R'), batch from OutputResult";
		String window = "create window LogWindow.win:time_batch(150 sec) as Logger";
		String epl = "insert into LogWindow select * from Logger(eventNode!='R')";
		String delete = "on OutputResult delete from LogWindow where OutputResult.batch = LogWindow.batch";
		EPStatement state = admin.createEPL(window);
		state.addListener(new LogProcessListener());

		admin.createEPL(epl);

		EPStatement state2 = admin.createEPL(delete);
		state2.addListener(new LogProcessListener());

		runtime = epService.getEPRuntime();
	}

	@Test
	public void test() {
		Map<String, Object> log5 = new HashMap<String, Object>();
		log5.put("batch", "1");
		log5.put("eventNode", "T");
		log5.put("eventNum", "5");
		runtime.sendEvent(log5, "Logger");

		Map<String, Object> log1 = new HashMap<String, Object>();
		log1.put("batch", "1");
		log1.put("eventNode", "I");
		log1.put("eventNum", "1");
		log1.put("eventTime", String.valueOf(System.currentTimeMillis()));
		runtime.sendEvent(log1, "Logger");

		Map<String, Object> log2 = new HashMap<String, Object>();
		log2.put("batch", "1");
		log2.put("eventNode", "U");
		log2.put("eventNum", "2");
		runtime.sendEvent(log2, "Logger");

		Map<String, Object> log3 = new HashMap<String, Object>();
		log3.put("batch", "1");
		log3.put("eventNode", "U");
		log3.put("eventNum", "3");
		runtime.sendEvent(log3, "Logger");

		Map<String, Object> log4 = new HashMap<String, Object>();
		log4.put("batch", "1");
		log4.put("eventNode", "U");
		log4.put("eventNum", "4");
		runtime.sendEvent(log4, "Logger");

		Map<String, Object> log6 = new HashMap<String, Object>();
		log6.put("batch", "1");
		log6.put("eventNode", "R");
		log6.put("eventNum", "6");
		runtime.sendEvent(log6, "Logger");

		String query = "select * from LogWindow";
		EPOnDemandQueryResult result = runtime.executeQuery(query);
		for (EventBean row : result.getArray()) {
			System.out.println(row.getUnderlying());
		}

		Map<String, Object> o1 = new HashMap<String, Object>();
		o1.put("batch", "1");
		runtime.sendEvent(o1, "OutputResult");
		Assert.assertEquals(5, LogProcessListener.len);
		System.out.println(LogProcessListener.len);
		
		String query2 = "select * from LogWindow";
		EPOnDemandQueryResult result2 = runtime.executeQuery(query2);
		for (EventBean row : result2.getArray()) {
			System.out.println(row.getUnderlying());
		}

		String query3 = "select * from LogWindow where batch = '1'";
		EPOnDemandQueryResult result3 = runtime.executeQuery(query3);
		for (EventBean row : result3.getArray()) {
			System.out.println(row.getUnderlying());
		}
	}

}
