package test;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EPStatementState;

import example.model.User;

/**
 * 查看EPL的运行状态
 * 
 * @author luonq(luonq@primeton.com)
 *
 */
public class StatementStartedOrStoppedTest {
	static EPAdministrator admin;
	static String[] stateNames;
	static EPStatement state;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider();

		admin = epService.getEPAdministrator();

		String epl1 = "select id from " + User.class.getName();
		String epl2 = epl1 + ".win:time(10 sec)";

		admin.createEPL(epl1);
		admin.createEPL(epl2);

		stateNames = admin.getStatementNames();
	}

	@Test
	public void test1() {
		state = admin.getStatement(stateNames[0]);
		assertEquals(EPStatementState.STARTED, state.getState());
		System.out.println("EPL: " + state.getText() + " State: " + state.getState());
	}

	@Test
	public void test2() {
		state = admin.getStatement(stateNames[1]);
		assertEquals(EPStatementState.STARTED, state.getState());
		System.out.println("EPL: " + state.getText() + " State: " + state.getState());
	}

}
