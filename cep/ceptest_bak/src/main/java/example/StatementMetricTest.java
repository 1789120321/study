package example;

import java.io.File;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;
import com.espertech.esper.client.metric.StatementMetric;

import example.model.Product;

class StatementMetricListener implements UpdateListener {

	public void update(EventBean[] newEvents, EventBean[] oldEvents) {
		if (newEvents != null) {
			StatementMetric metric = (StatementMetric) newEvents[0].getUnderlying();
			System.out.println("Input Count: " + metric.getNumInput());
			System.out.println("IStream Count: " + metric.getNumOutputIStream());
			System.out.println("RStream Count: " + metric.getNumOutputRStream());
		}
	}
}

/**
 * StatementMetric用来统计EPL实例的事件进出情况，EngineMetric用来统计引擎的事件进出情况，两个都可以查看一些cpu数据
 * 
 * @author luonanqin
 *
 */
public class StatementMetricTest {

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		Configuration config = new Configuration();
		config.configure(new File("etc/metric.xml"));
		EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider(config);

		EPAdministrator admin = epService.getEPAdministrator();

		String product = Product.class.getName();
		String epl1 = "select irstream * from " + product + ".win:length(10)";
		String epl2 = "select * from com.espertech.esper.client.metric.StatementMetric";

		EPStatement state1 = admin.createEPL(epl1, "epl1");
		EPStatement state2 = admin.createEPL(epl2);
		state2.addListener(new StatementMetricListener());

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
		Thread.sleep(3000);

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

		Thread.sleep(3000);

		System.out.println();
	}

}
