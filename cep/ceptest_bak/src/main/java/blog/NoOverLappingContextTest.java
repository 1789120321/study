package blog;

import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

class StartEvent
{
}

class EndEvent
{
}

class OtherEvent
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

class NoOverLappingContextTest3 implements UpdateListener
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

public class NoOverLappingContextTest
{
	public static void main(String[] args)
	{
		EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider();
		EPAdministrator admin = epService.getEPAdministrator();
		EPRuntime runtime = epService.getEPRuntime();

		String start = StartEvent.class.getName();
		String end = EndEvent.class.getName();
		String other = OtherEvent.class.getName();
		// 以StartEvent事件作为开始条件，EndEvent事件作为结束条件
		String epl1 = "create context NoOverLapping start " + start + " end " + end;
		String epl2 = "context NoOverLapping select * from " + other;

		admin.createEPL(epl1);
		EPStatement state = admin.createEPL(epl2);
		state.addListener(new NoOverLappingContextTest3());

		StartEvent s = new StartEvent();
		System.out.println("sendEvent: StartEvent");
		runtime.sendEvent(s);

		OtherEvent o = new OtherEvent();
		o.setId(2);
		System.out.println("sendEvent: OtherEvent");
		runtime.sendEvent(o);

		EndEvent e = new EndEvent();
		System.out.println("sendEvent: EndEvent");
		runtime.sendEvent(e);

		OtherEvent o2 = new OtherEvent();
		o2.setId(4);
		System.out.println("sendEvent: OtherEvent");
		runtime.sendEvent(o2);
	}
}
