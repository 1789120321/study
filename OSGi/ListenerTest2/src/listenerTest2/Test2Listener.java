package listenerTest2;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

public class Test2Listener implements UpdateListener {

	@Override
	public void update(EventBean[] arg0, EventBean[] arg1) {
		System.out.println("TestListener2!");
	}

}
