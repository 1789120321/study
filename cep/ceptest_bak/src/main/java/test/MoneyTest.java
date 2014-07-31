package main.java.test;

import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

/**
 * Created by Luonanqin on 5/30/14.
 */
class Money {
	private int id1;
	private int id2;
	private int money;
	private long time;

	public int getId1() {
		return id1;
	}

	public void setId1(int id1) {
		this.id1 = id1;
	}

	public int getId2() {
		return id2;
	}

	public void setId2(int id2) {
		this.id2 = id2;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}
}

class MoneyListener implements UpdateListener {

	public void update(EventBean[] newEvents, EventBean[] oldEvents) {
		if (newEvents != null) {
			System.out.println("Notify!");
		}
	}
}

public class MoneyTest {

	public static void main(String[] args) {
		EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider();
		EPRuntime runtime = epService.getEPRuntime();
		EPAdministrator admin = epService.getEPAdministrator();

		String money = Money.class.getName();

		String epl = "select * from pattern[every a=" + money + "->b=" + money
				+ "(b.time-a.time<300000 and a.id1=b.id1 and a.id2=b.id2 and a.money<10 and b.money<10)]";

		EPStatement statement = admin.createEPL(epl);
		statement.addListener(new MoneyListener());

		Money m1 = new Money();
		m1.setId1(1);
		m1.setId1(2);
		m1.setMoney(5);
		m1.setTime(System.currentTimeMillis());
		runtime.sendEvent(m1);

		Money m2 = new Money();
		m2.setId1(2);
		m2.setId1(3);
		m2.setMoney(6);
		m2.setTime(System.currentTimeMillis());
		runtime.sendEvent(m2);

		Money m3 = new Money();
		m3.setId1(1);
		m3.setId1(2);
		m3.setMoney(5);
		m3.setTime(System.currentTimeMillis());
		runtime.sendEvent(m3);

		Money m4 = new Money();
		m4.setId1(2);
		m4.setId1(3);
		m4.setMoney(10);
		m4.setTime(System.currentTimeMillis());
		runtime.sendEvent(m4);

		System.out.println();
	}
}
