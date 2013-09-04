package blog;

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
class Apple
{
	private int id;
	private int price;
	private String name;

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

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

}

class AppleListener implements UpdateListener
{

	public void update(EventBean[] newEvents, EventBean[] oldEvents)
	{
		if (newEvents != null)
		{
			System.out.println("Apple is " + newEvents[0]);
		}
	}

}
public class InsertRemoveStreamTest {

	public static void main(String[] args) throws InterruptedException {
		EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider();

		EPAdministrator admin = epService.getEPAdministrator();

		String product = Apple.class.getName();
		String epl = "select sum(price),name from " + product + ".win:length_batch(3) group by name";

		EPStatement state = admin.createEPL(epl);
		state.addListener(new AppleListener());

		EPRuntime runtime = epService.getEPRuntime();

		Apple apple1 = new Apple();
		apple1.setId(1);
		apple1.setPrice(5);
		apple1.setName("a1");
		runtime.sendEvent(apple1);

		Apple apple2 = new Apple();
		apple2.setId(1);
		apple2.setPrice(2);
		apple2.setName("a1");
		runtime.sendEvent(apple2);

		Apple apple3 = new Apple();
		apple3.setId(3);
		apple3.setPrice(5);
		apple3.setName("a3");
		runtime.sendEvent(apple3);
	}
}
