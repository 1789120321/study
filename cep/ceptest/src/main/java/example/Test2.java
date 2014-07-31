package example;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPOnDemandQueryResult;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

import java.io.Serializable;

/**
 * @author luonanqin
 */
class MergeListener implements UpdateListener {

	public void update(EventBean[] newEvents, EventBean[] oldEvents) {
		if (newEvents != null) {
			for (int i = 0; i < newEvents.length; i++) {
				EventBean newEvent = newEvents[i];
				System.out.println(newEvent);
			}
		}
	}
}

class TestListener2 implements UpdateListener {

	public void update(EventBean[] newEvents, EventBean[] oldEvents) {
		if (newEvents != null) {
			for (int i = 0; i < newEvents.length; i++) {
				EventBean newEvent = newEvents[i];
				System.out.println(newEvent.getUnderlying());
			}
		}
	}
}

class BCDListener implements UpdateListener {

	private EPRuntime runtime;

	public BCDListener(EPRuntime runtime) {
		this.runtime = runtime;
	}

	public void update(EventBean[] newEvents, EventBean[] oldEvents) {
		if (newEvents != null) {
			A a = new A();
			a.setId(11);
			a.setFlag("A");
			runtime.sendEvent(a);
		}
	}
}

class EFGListener implements UpdateListener {

	private EPRuntime runtime;

	public EFGListener(EPRuntime runtime) {
		this.runtime = runtime;
	}

	public void update(EventBean[] newEvents, EventBean[] oldEvents) {
		if (newEvents != null) {
			B b = new B();
			b.setId(21);
			b.setFlag("BCD");
			runtime.sendEvent(b);
		}
	}
}

class HIListener implements UpdateListener {

	private EPRuntime runtime;

	public HIListener(EPRuntime runtime) {
		this.runtime = runtime;
	}

	public void update(EventBean[] newEvents, EventBean[] oldEvents) {
		if (newEvents != null) {
			C c = new C();
			c.setId(22);
			c.setFlag("BCD");
			runtime.sendEvent(c);
		}
	}
}

class DListener implements UpdateListener {

	private EPRuntime runtime;

	public DListener(EPRuntime runtime) {
		this.runtime = runtime;
	}

	public void update(EventBean[] newEvents, EventBean[] oldEvents) {
		if (newEvents != null) {
			D d = new D();
			d.setId(23);
			d.setFlag("BCD");
			runtime.sendEvent(d);
		}
	}
}

class OutputResult implements UpdateListener {

	private EPRuntime runtime;

	public OutputResult(EPRuntime runtime) {
		this.runtime = runtime;
	}

	public void update(EventBean[] newEvents, EventBean[] oldEvents) {
		if (newEvents != null) {
			System.out.println();
			for (int i = 0; i < newEvents.length; i++) {
				EventBean newEvent = newEvents[i];
				System.out.println(newEvent.getUnderlying());
			}
		}
		runtime.sendEvent(new Restore());
	}
}

class Restore {
}

interface ID extends Serializable {
	int getId();

	String getFlag();

	boolean isRead();

	void setRead(boolean read);
}

class A implements ID {
	private int id;
	private B b;
	private C c;
	private D d;
	private String flag;
	private boolean read;

	public boolean isRead() {
		return read;
	}

	public void setRead(boolean read) {
		this.read = read;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public B getB() {
		return b;
	}

	public void setB(B b) {
		this.b = b;
	}

	public C getC() {
		return c;
	}

	public void setC(C c) {
		this.c = c;
	}

	public void setD(D d) {
		this.d = d;
	}

	public D getD() {
		return d;
	}
}

class B implements ID {
	private int id;
	private E e;
	private F f;
	private G g;
	private String flag;
	private boolean read;

	public boolean isRead() {
		return read;
	}

	public void setRead(boolean read) {
		this.read = read;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public E getE() {
		return e;
	}

	public void setE(E e) {
		this.e = e;
	}

	public F getF() {
		return f;
	}

	public void setF(F f) {
		this.f = f;
	}

	public G getG() {
		return g;
	}

	public void setG(G g) {
		this.g = g;
	}
}

class C implements ID {
	private int id;
	private H h;
	private I i;
	private String flag;
	private boolean read;

	public boolean isRead() {
		return read;
	}

	public void setRead(boolean read) {
		this.read = read;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public H getH() {
		return h;
	}

	public void setH(H h) {
		this.h = h;
	}

	public I getI() {
		return i;
	}

	public void setI(I i) {
		this.i = i;
	}
}

class D implements ID {
	private int id;
	private I i;
	private String flag;
	private boolean read;

	public boolean isRead() {
		return read;
	}

	public void setRead(boolean read) {
		this.read = read;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public I getI() {
		return i;
	}

	public void setI(I i) {
		this.i = i;
	}
}

class E implements ID {
	private int id;
	private String flag;
	private boolean read;

	public boolean isRead() {
		return read;
	}

	public void setRead(boolean read) {
		this.read = read;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}

class F implements ID {
	private int id;
	private String flag;
	private boolean read;

	public boolean isRead() {
		return read;
	}

	public void setRead(boolean read) {
		this.read = read;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}

class G implements ID {
	private int id;
	private String flag;
	private boolean read;

	public boolean isRead() {
		return read;
	}

	public void setRead(boolean read) {
		this.read = read;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}

class H implements ID {
	private int id;
	private String flag;
	private boolean read;

	public boolean isRead() {
		return read;
	}

	public void setRead(boolean read) {
		this.read = read;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}

class I implements ID {
	private int id;
	private String flag;
	private boolean read;

	public boolean isRead() {
		return read;
	}

	public void setRead(boolean read) {
		this.read = read;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}

public class Test2 {

	public static void main(String[] args) throws InterruptedException {
		Configuration config = new Configuration();
		config.configure("esper.examples.cfg.xml");

		EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider(config);
		EPRuntime runtime = epService.getEPRuntime();
		EPAdministrator admin = epService.getEPAdministrator();

		String id = ID.class.getName();
		String restore = Restore.class.getName();

		String epl15 = "@Priority(60) on " + id + "(id=11) set BCDFlag = '!BCD'";
		String epl16 = "@Priority(59) on " + id + "(id=21 or id=11) set EFGFlag = '!EFG'";
		String epl17 = "@Priority(58) on " + id + "(id=22 or id=11) set HIFlag = '!HI'";
		admin.createEPL(epl15);
		admin.createEPL(epl16);
		admin.createEPL(epl17);

		String epl18 = "create schema MergeID as " + id;
		admin.createEPL(epl18);

		String epl20 = "@Priority(47) create context BCDExpireContext initiated by pattern [MergeID(id in [21, 22, 23] and flag=BCDFlag)] terminated after 10 sec";
		admin.createEPL(epl20);
		String epl21 = "@Priority(48) create context EFGExpireContext initiated by pattern [MergeID(id in [31, 32, 33] and flag=EFGFlag)] terminated after 10 sec";
		admin.createEPL(epl21);
		String epl22 = "@Priority(49) create context HIExpireContext initiated by pattern [MergeID(id in [34, 35] and flag=HIFlag)] terminated after 10 sec";
		admin.createEPL(epl22);

		String epl1 = "select * from pattern[every (B and C and D)]";
		String epl2 = "@Priority(42) select * from pattern[MergeID(id=31) and (MergeID(id=32) where timer:within(10 sec)) and (MergeID(id=33) where timer:within(10 sec))]";
		// EPStatement state4 = admin.createEPL(epl2);
		// state4.addListener(new EFGListener(runtime));
		String epl3 = "select * from pattern[every (H and I)]";
		String epl4 = "select * from pattern[(every i=" + B.class.getName() + ") where timer:within(1 sec)]";
		String epl5 = "select * from pattern[every I]";

		// String epl6 = "select * from pattern[every H and (I where timer:within(3 sec))]";
		// admin.createEPL(epl6);

		String epl12 = "create window TestWindow.std:unique(id).win:keepall() as " + id;
		admin.createEPL(epl12);

		String epl7 = "@Priority(41) context BCDExpireContext select * from MergeID(id in [21,22,23] and flag = BCDFlag and read = true).std:unique(id).win:length_batch(3)"
				+ " output every 3 events and when terminated then set BCDFlag='!BCD'";
		EPStatement state3 = admin.createEPL(epl7);
		state3.addListener(new BCDListener(runtime));

		String epl8 = "@Priority(42) context EFGExpireContext select * from MergeID(id in [31,32,33] and flag = EFGFlag and read = true).std:unique(id).win:length_batch(3)"
				+ " output every 3 events and when terminated then set EFGFlag='!EFG'";
		EPStatement state4 = admin.createEPL(epl8);
		state4.addListener(new EFGListener(runtime));

		String epl9 = "@Priority(43) context HIExpireContext select * from MergeID(id in [34,35] and flag = HIFlag and read = true).std:unique(id).win:length_batch(2)"
				+ " output every 2 events and when terminated then set HIFlag='!HI'";
		EPStatement state5 = admin.createEPL(epl9);
		state5.addListener(new HIListener(runtime));

		String epl10 = "@Priority(44) select * from MergeID(id = 35 and read = true).std:unique(id).win:length_batch(1)";
		EPStatement state6 = admin.createEPL(epl10);
		state6.addListener(new DListener(runtime));

		String epl11 = "update istream TestWindow set read = true where read = false";
		admin.createEPL(epl11);

		String epl13 = "@Priority(46) insert into TestWindow select * from " + id
				+ " where (flag=BCDFlag or flag=EFGFlag or flag=HIFlag or flag=AFlag) and read = false";
		EPStatement state8 = admin.createEPL(epl13);
		state8.addListener(new TestListener2());

		String epl14 = "@Priority(39) on " + id + " inputID merge TestWindow tw"
				+ " when matched and inputID.id=11 then delete where tw.id in [21, 22, 23, 31, 32, 33, 34, 35]"
				+ " when matched and inputID.id=21 then delete where tw.id in [31, 32, 33]"
				+ " when matched and inputID.id=22 then delete where tw.id in [34, 35]" + " when matched and inputID.id=23 then delete where tw.id=35";
		EPStatement state9 = admin.createEPL(epl14);
		state9.addListener(new MergeListener());

		String epl19 = "@Priority(45) insert into MergeID select * from TestWindow where read = true";
		admin.createEPL(epl19);

		String alert = "@Priority(38) on pattern[MergeID(id in [21, 22, 23, 31, 32, 33, 34, 35]) -> timer:interval(15 sec)] select tw.* from TestWindow as tw";
		EPStatement state10 = admin.createEPL(alert);
		state10.addListener(new OutputResult(runtime));

		String restoreEpl = "on " + restore + " set BCDFlag='BCD', EFGFlag='EFG', HIFlag='HI'";
		admin.createEPL(restoreEpl);

		A a = new A();
		a.setId(11);
		a.setFlag("A");
		B b = new B();
		b.setFlag("BCD");
		b.setId(21);
		C c = new C();
		c.setId(22);
		c.setFlag("BCD");
		D d = new D();
		d.setId(23);
		d.setFlag("BCD");
		E e = new E();
		e.setId(31);
		e.setFlag("EFG");
		F f = new F();
		f.setId(32);
		f.setFlag("EFG");
		G g = new G();
		g.setId(33);
		g.setFlag("EFG");
		H h = new H();
		h.setId(34);
		h.setFlag("HI");
		I i = new I();
		i.setId(35);
		i.setFlag("HI");

		//runtime.sendEvent(a);
		//runtime.sendEvent(b);
		//runtime.sendEvent(c);
		//runtime.sendEvent(d);
		runtime.sendEvent(e);
		runtime.sendEvent(f);
		runtime.sendEvent(g);
		//runtime.sendEvent(h);
		runtime.sendEvent(i);

		String select = "select * from TestWindow";
		EPOnDemandQueryResult result;
		EventBean[] events;
		System.out.println();
		//result = epService.getEPRuntime().executeQuery(select);
		//events = result.getArray();
		//for (int j = 0; j < events.length; j++) {
		//	EventBean event = events[j];
		//	System.out.println(event.getUnderlying());
		//}

		Thread.sleep(16000);
		System.out.println();
		runtime.sendEvent(new Restore());
		e.setRead(false);
		d.setRead(false);
		g.setRead(false);
		i.setRead(false);
		runtime.sendEvent(e);
		runtime.sendEvent(f);
		runtime.sendEvent(g);
		runtime.sendEvent(i);

		Thread.sleep(16000);
		System.out.println();
		//result = epService.getEPRuntime().executeQuery(select);
		//events = result.getArray();
		//System.out.println();
		//for (int j = 0; j < events.length; j++) {
		//	EventBean event = events[j];
		//	System.out.println(event.getUnderlying());
		//}

	}
}
