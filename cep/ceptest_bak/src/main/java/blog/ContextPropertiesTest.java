package blog;

import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

import example.model.ESB;
import example.model.User;

class ContextPropertiesListener implements UpdateListener
{

	public void update(EventBean[] newEvents, EventBean[] oldEvents)
	{
		if (newEvents != null)
		{
			System.out.println();
		}
	}
}

public class ContextPropertiesTest
{
	public static void main(String[] args)
	{
		EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider();
		EPAdministrator admin = epService.getEPAdministrator();
		EPRuntime runtime = epService.getEPRuntime();

		String esb = ESB.class.getName();
		// 创建context
		String epl1 = "create context esbtest partition by id from " + esb + ", age from " + User.class.getName();
		// context.id针对不同的esb的id建立一个context，如果事件的id相同，则context.id也相同，即表明事件进入了同一个context
		// context.key1表明id的值，context.name即为context的名称，一直不变。
		String epl2 = "context esbtest select context.id,context.name,context.key1,context.key2 from " + esb;
		String epl3 = "context esbtest select context.id,context.name,context.key1 from " + User.class.getName();

		admin.createEPL(epl1);
		EPStatement state = admin.createEPL(epl2);
		state.addListener(new ContextPropertiesListener());
		EPStatement state1 = admin.createEPL(epl3);
		state1.addListener(new ContextPropertiesListener());

		User u1 = new User();
		u1.setAge(2);
		runtime.sendEvent(u1);

		ESB e1 = new ESB();
		e1.setId(1);
		e1.setPrice(20);
		System.out.println("sendEvent: id=1, price=20");
		runtime.sendEvent(e1);


		ESB e2 = new ESB();
		e2.setId(2);
		e2.setPrice(30);
		System.out.println("sendEvent: id=2, price=30");
		runtime.sendEvent(e2);

		ESB e3 = new ESB();
		e3.setId(1);
		e3.setPrice(40);
		System.out.println("sendEvent: id=1, price=30");
		runtime.sendEvent(e3);
	}
}
