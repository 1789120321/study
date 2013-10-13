package example;

import java.util.Random;

import com.espertech.esper.client.ConfigurationOperations;
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
class Pink
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

class OutputWhenListener implements UpdateListener
{
	public void update(EventBean[] newEvents, EventBean[] oldEvents)
	{
		if (newEvents != null)
		{
			for (int i = 0; i < newEvents.length; i++)
			{
				Pink pink = (Pink) newEvents[i].getUnderlying();
				System.out.println("Output Pink: " + pink);
			}
		}
	}
}

public class OutputWhenTest
{
	public static void main(String[] args) throws InterruptedException
	{
		EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider();

		EPAdministrator admin = epService.getEPAdministrator();
		ConfigurationOperations config = admin.getConfiguration();
		config.addVariable("exceed", boolean.class, false);

		String pink = Pink.class.getName();
		// 当exceed为true时，输出所有进入EPL的事件，然后设置exceed为false
		String epl = "select * from " + pink + " output when exceed then set exceed=false";

		EPStatement state = admin.createEPL(epl);
		state.addListener(new OutputWhenListener());

		EPRuntime runtime = epService.getEPRuntime();

		Random r = new Random(47);
		for (int i = 1; i <= 10; i++)
		{
			int price = r.nextInt(10);
			Pink p = new Pink();
			p.setId(i);
			p.setPrice(price);
			System.out.println("Send Pink Event: " + p);
			runtime.sendEvent(p);
			// 当price大于5时，exceed变量为true
			if (price > 5)
			{
				runtime.setVariableValue("exceed", true);
				// 因为主线程和输出线程不是同一个，所以这里休息1秒保证输出线程将事件全部输出，方便演示。
				Thread.sleep(1000);
			}
		}
	}
}
