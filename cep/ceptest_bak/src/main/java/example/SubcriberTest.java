package example;

import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;

import example.model.Product;

class StatementSubcriber {

	// 参数必须和select的内容一一对应
	public void update(Product p) {
		System.out.println(p);
	}

	public void updateStart(int insertStreamLength, int removeStreamLength) {
		System.out.println(insertStreamLength + " " + removeStreamLength);
	}
}

/**
 * Subcriber类似UpdateListener，可以实时查看进入的事件以及出去的事件
 * @author luonanqin
 *
 */
public class SubcriberTest {

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider();

		EPAdministrator admin = epService.getEPAdministrator();

		String product = Product.class.getName();
		String epl1 = "select irstream * from " + product + ".win:length(10)";

		EPStatement state1 = admin.createEPL(epl1, "epl1");
		state1.setSubscriber(new StatementSubcriber());

		EPRuntime runtime = epService.getEPRuntime();

		Product esb = new Product();
		esb.setPrice(1);
		esb.setType("esb");
		System.out.println("sendEvent: " + esb);
		runtime.sendEvent(esb);

		Product eos = new Product();
		eos.setPrice(2);
		eos.setType("eos");
		System.out.println("sendEvent: " + eos);
		runtime.sendEvent(eos);

		Product esb1 = new Product();
		esb1.setPrice(2);
		esb1.setType("esb");
		System.out.println("sendEvent: " + esb1);
		runtime.sendEvent(esb1);

		Product eos1 = new Product();
		eos1.setPrice(5);
		eos1.setType("eos");
		System.out.println("sendEvent: " + eos1);
		runtime.sendEvent(eos1);
	}
}
