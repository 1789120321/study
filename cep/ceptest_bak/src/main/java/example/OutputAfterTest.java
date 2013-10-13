package example;

import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

/**
 * 
 * @author luonanqin
 *
 */
class Orange
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

	public String toString()
	{
		return "id: " + id + ", price: " + price;
	}
}

class OutputAfterListener implements UpdateListener
{
	public void update(EventBean[] newEvents, EventBean[] oldEvents)
	{
		if (newEvents != null)
		{
			int price = (Integer) newEvents[0].get("sPrice");
			System.out.println("Orange's sum price is " + price);
		}
	}
}

public class OutputAfterTest
{
	public static void main(String[] args) throws InterruptedException
	{
		EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider();

		EPAdministrator admin = epService.getEPAdministrator();

		String orange = Orange.class.getName();
		// 统计最新3个Orange事件的sum price，并且从EPL可用起，等待第一个事件进入后，以每两个事件进入的频率输出统计结果
		String epl = "select sum(price) as sPrice from " + orange + ".win:length(3) output after 1 events first every 2 events";

		EPStatement state = admin.createEPL(epl);
		state.addListener(new OutputAfterListener());

		EPRuntime runtime = epService.getEPRuntime();

		Orange b1 = new Orange();
		b1.setId(1);
		b1.setPrice(6);
		System.out.println("Send Orange Event: " + b1);
		runtime.sendEvent(b1);

		Orange b2 = new Orange();
		b2.setId(2);
		b2.setPrice(3);
		System.out.println("Send Orange Event: " + b2);
		runtime.sendEvent(b2);

		Orange b3 = new Orange();
		b3.setId(3);
		b3.setPrice(0);
		System.out.println("Send Orange Event: " + b3);
		runtime.sendEvent(b3);

		Orange b4 = new Orange();
		b4.setId(4);
		b4.setPrice(0);
		System.out.println("Send Orange Event: " + b4);
		runtime.sendEvent(b4);

		Orange b5 = new Orange();
		b5.setId(5);
		b5.setPrice(0);
		System.out.println("Send Orange Event: " + b5);
		runtime.sendEvent(b5);
	}
}
