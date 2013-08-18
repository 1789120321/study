package example;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EventType;

public class MapEventTest
{
	public static void main(String[] args)
	{
		EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider();
		EPAdministrator admin = epService.getEPAdministrator();

		// Address定义
		Map<String, Object> address = new HashMap<String, Object>();
		address.put("road", String.class);
		address.put("street", String.class);
		address.put("houseNo", int.class);

		// Person定义
		Map<String, Object> person = new HashMap<String, Object>();
		person.put("name", String.class);
		person.put("age", int.class);
		person.put("children", List.class);
		person.put("phones", Map.class);
		person.put("addresses", "Address[]");

		// 注册Address到Esper
		admin.getConfiguration().addEventType("Address", address);
		// 注册Person到Esper
		admin.getConfiguration().addEventType("Person", person);

		// 新增一个gender属性
		person.put("gender", int.class);
		admin.getConfiguration().updateMapEventType("Person", person);

		/** 输出结果：
		 * Person props: [address, age, name, children, phones, gender]
		 */
		EventType event = admin.getConfiguration().getEventType("Person");
		System.out.println("Person props: " + Arrays.asList(event.getPropertyNames()));
	}
}
