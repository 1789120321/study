package test;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;

/**
 * 展示如何定义map事件和处理方式
 * 
 * @author luonq(luonq@primeton.com)
 *
 */
public class MapProcessTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider();

		EPAdministrator admin = epService.getEPAdministrator();
		Map<String, Object> mapDef = new HashMap<String, Object>();
		mapDef.put("id", int.class);
		mapDef.put("count", int.class);
		admin.getConfiguration().addEventType("preSumIdEvent", mapDef);

		String epl1 = "select sum(count) as sumCount, id from preSumIdEvent.std:groupwin(id).win:length(2) group by id";

		EPStatement state = admin.createEPL(epl1);
		state.addListener(new MapProcessListener());

		EPRuntime runtime = epService.getEPRuntime();

		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("id", 1);
		map1.put("count", 1);
		runtime.sendEvent(map1, "preSumIdEvent");

		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("id", 2);
		map2.put("count", 4);
		runtime.sendEvent(map2, "preSumIdEvent");

		Map<String, Object> map3 = new HashMap<String, Object>();
		map3.put("id", 2);
		map3.put("count", 3);
		runtime.sendEvent(map3, "preSumIdEvent");

		Map<String, Object> map4 = new HashMap<String, Object>();
		map4.put("id", 1);
		map4.put("count", 3);
		runtime.sendEvent(map4, "preSumIdEvent");
	}

	@Test
	public void test1() {
		Assert.assertEquals(1, MapProcessListener.id[0]);
		System.out.println("id: 1");
		Assert.assertEquals(1, MapProcessListener.sumId[0]);
		System.out.println("sumCount: 1");
	}

	@Test
	public void test2() {
		Assert.assertEquals(2, MapProcessListener.id[1]);
		System.out.println("id: 2");
		Assert.assertEquals(4, MapProcessListener.sumId[1]);
		System.out.println("sumCount: 4");
	}

	@Test
	public void test3() {
		Assert.assertEquals(2, MapProcessListener.id[2]);
		System.out.println("id: 2");
		Assert.assertEquals(7, MapProcessListener.sumId[2]);
		System.out.println("sumCount: 7");
	}

	@Test
	public void test4() {
		Assert.assertEquals(1, MapProcessListener.id[3]);
		System.out.println("id: 1");
		Assert.assertEquals(4, MapProcessListener.sumId[3]);
		System.out.println("sumCount: 4");
	}

}
