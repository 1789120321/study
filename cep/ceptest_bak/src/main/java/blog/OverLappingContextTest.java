package blog;

import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

class InitialEvent
{
}

class TerminateEvent
{
}

class SomeEvent
{
	private int id;

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}
}

class OverLappingContextListener implements UpdateListener
{

	public void update(EventBean[] newEvents, EventBean[] oldEvents)
	{
		if (newEvents != null)
		{
			EventBean event = newEvents[0];
			System.out.println("context.id:" + event.get("id") + ", id:" + event.get("id"));
		}
	}
}

class OverLappingContextListener2 implements UpdateListener
{

	public void update(EventBean[] newEvents, EventBean[] oldEvents)
	{
		if (newEvents != null)
		{
			EventBean event = newEvents[0];
			System.out.println("Class:" + event.getUnderlying().getClass().getName() + ", id:" + event.get("id"));
		}
	}
}

public class OverLappingContextTest
{
	public static void main(String[] args)
	{
		EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider();
		EPAdministrator admin = epService.getEPAdministrator();
		EPRuntime runtime = epService.getEPRuntime();

		String initial = InitialEvent.class.getName();
		String terminate = TerminateEvent.class.getName();
		String some = SomeEvent.class.getName();
		// 以InitialEvent事件作为初始事件，TerminateEvent事件作为终结事件
		String epl1 = "create context OverLapping initiated " + initial + " terminated " + terminate;
		String epl2 = "context OverLapping select context.id from " + initial;
		String epl3 = "context OverLapping select * from " + some;

		admin.createEPL(epl1);
		EPStatement state = admin.createEPL(epl2);
		state.addListener(new OverLappingContextListener());
		EPStatement state1 = admin.createEPL(epl3);
		state1.addListener(new OverLappingContextListener2());

		InitialEvent i = new InitialEvent();
		System.out.println("sendEvent: InitialEvent");
		runtime.sendEvent(i);

		SomeEvent s = new SomeEvent();
		s.setId(2);
		System.out.println("sendEvent: SomeEvent");
		runtime.sendEvent(s);

		InitialEvent i2 = new InitialEvent();
		System.out.println("sendEvent: InitialEvent");
		runtime.sendEvent(i2);

		TerminateEvent t = new TerminateEvent();
		System.out.println("sendEvent: TerminateEvent");
		runtime.sendEvent(t);

		SomeEvent s2 = new SomeEvent();
		s2.setId(4);
		System.out.println("sendEvent: SomeEvent");
		runtime.sendEvent(s2);
	}
}
