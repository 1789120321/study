package blog;

import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

class ESB2
{

	private int id;
	private int price;

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public int getPrice()
	{
		return price;
	}

	public void setPrice(int price)
	{
		this.price = price;
	}

}

class User
{
	private int uid;

	public int getUid()
	{
		return uid;
	}

	public void setUid(int uid)
	{
		this.uid = uid;
	}

}

class ContextPropertiesListener3 implements UpdateListener
{

	public void update(EventBean[] newEvents, EventBean[] oldEvents)
	{
		if (newEvents != null)
		{
			EventBean event = newEvents[0];
			System.out.println("context.name " + event.get("name") + ", context.id " + event.get("id") + ", context.key1 " + event.get("key1")
					+ ", context.key2 " + event.get("key2"));
		}
	}
}

public class HashContextTest
{
	public static void main(String[] args)
	{
		EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider();
		EPAdministrator admin = epService.getEPAdministrator();
		EPRuntime runtime = epService.getEPRuntime();

		String esb = ESB2.class.getName();
		String user = User.class.getName();
		// 创建context
		String epl1 = "create context HashPerson coalesce by hash_code(id) from " + esb + "(id>0), consistent_hash_crc32(uid) from " + user + " granularity 10";
		// context.id针对不同的esb的id,price建立一个context，如果事件的id和price相同，则context.id也相同，即表明事件进入了同一个context
		String epl2 = "context esbtest select context.id,context.name,context.key1,context.key2 from " + esb;

		admin.createEPL(epl1);
		EPStatement state = admin.createEPL(epl2);
		state.addListener(new ContextPropertiesListener3());

		ESB2 e1 = new ESB2();
		e1.setId(1);
		e1.setPrice(20);
		System.out.println("sendEvent: id=1, price=20");
		runtime.sendEvent(e1);


		ESB2 e2 = new ESB2();
		e2.setId(2);
		e2.setPrice(30);
		System.out.println("sendEvent: id=2, price=30");
		runtime.sendEvent(e2);

		ESB2 e3 = new ESB2();
		e3.setId(1);
		e3.setPrice(20);
		System.out.println("sendEvent: id=1, price=20");
		runtime.sendEvent(e3);

		ESB2 e4 = new ESB2();
		e4.setId(4);
		e4.setPrice(20);
		System.out.println("sendEvent: id=4, price=20");
		runtime.sendEvent(e4);
	}
}
