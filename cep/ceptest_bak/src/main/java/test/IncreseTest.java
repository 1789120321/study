package test;

import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

import example.model.Product;
import example.model.User;

class SelectAListener implements UpdateListener {

	public void update(EventBean[] newEvents, EventBean[] oldEvents) {
		if (newEvents != null) {
			int count = 0;
			for (int i = 0; i < newEvents.length; i++) {
				int temp = (Integer) newEvents[i].get("stdc");
				if (temp > count) {
					count = temp;
				}
			}
		}
	}
}

class SelectBListener implements UpdateListener {

	public void update(EventBean[] newEvents, EventBean[] oldEvents) {
		if (newEvents != null) {
			int count = 0;
			for (int i = 0; i < newEvents.length; i++) {
				int temp = (Integer) newEvents[i].get("c");
				if (temp > count) {
					count = temp;
				}
			}
		}
	}
}

class IncreseListener implements UpdateListener {

	public void update(EventBean[] newEvents, EventBean[] oldEvents) {

	}
}

public class IncreseTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider();

		EPAdministrator admin = epService.getEPAdministrator();

		String product = Product.class.getName();
		String user = User.class.getName();
		String window = "create window ABC.win:keepall() as select * from " + product;
		String window2 = "create window EFG.win:keepall() as select * from " + product;
		String epl = "insert into ABC select * from " + product + " output at (*,*,*,*,*,*:*+30)";
		String epl2 = "insert into EFG select * from pattern[every a=" + product + " -> b=" + product + "(b.price>=(a.price*1.05))] output at (*,*,*,*,*,0:30)";
		String delete = "on " + user + " delete from ABC";
		String select = "insert into StdC(stdc) select count(*) from ABC output at (*,*,*,*,*,32)";
		String select1 = "insert into C(c) select count(*) from EFG output at (*,*,*,*,*,32)";

		String result = "every a=StdC -> every b=C(c+1=a.stdc) or every b=C -> every a=StdC(c+1=a.stdc)";

		admin.createEPL(window);
		admin.createEPL(window2);
		admin.createEPL(epl);
		admin.createEPL(epl2);
		EPStatement state = admin.createEPL(select);
		state.addListener(new SelectAListener());
		EPStatement state1 = admin.createEPL(select1);
		state1.addListener(new SelectBListener());
		EPStatement state2 = admin.createEPL(result);
		state2.addListener(new IncreseListener());
		admin.createEPL(delete);

		EPRuntime runtime = epService.getEPRuntime();

		Product esb = new Product();
		esb.setPrice(1);
		esb.setType("esb");
		runtime.sendEvent(esb);

		Product eos = new Product();
		eos.setPrice(2);
		eos.setType("eos");
		runtime.sendEvent(eos);

		Product esb1 = new Product();
		esb1.setPrice(3);
		esb1.setType("esb");
		runtime.sendEvent(esb1);

		Product eos1 = new Product();
		eos1.setPrice(5);
		eos1.setType("eos");
		runtime.sendEvent(eos1);

		Product esb2 = new Product();
		esb2.setPrice(6);
		esb2.setType("esb");
		runtime.sendEvent(esb2);

		Product eos3 = new Product();
		eos3.setPrice(7);
		eos3.setType("eos");
		runtime.sendEvent(eos3);

		System.out.println();

		User u = new User();
		runtime.sendEvent(u);

		System.out.println();
	}

}
