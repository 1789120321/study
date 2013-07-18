package com.primeton.cep.engine.esper.api;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPStatement;
import com.primeton.cep.engine.api.CEPEngineRuntime;
import com.primeton.cep.engine.api.EPS;

public class EsperEngineRuntimeImpl implements CEPEngineRuntime {

	private boolean _running = true;
	private boolean _suspend = false;

	private EPRuntime runtime;
	private EPAdministrator admin;

	public EsperEngineRuntimeImpl(EPRuntime runtime, EPAdministrator admin) {
		this.runtime = runtime;
		this.admin = admin;
	}

	public void sendEvent(Object event) {
		if (!_running) {
			// TODO throws a OperationException(RuntimeException)
		}
		if (_suspend) {
			// TODO throws a OperationException(RuntimeException)
		}
		runtime.sendEvent(event);
	}

	public void sendEvent(String name, Object[] event) {
		if (!_running) {
			// TODO throws a OperationException(RuntimeException)
		}
		if (_suspend) {
			// TODO throws a OperationException(RuntimeException)
		}
		runtime.sendEvent(event, name);
	}

	public void sendEvent(String name, Map<String, Object> event) {
		if (!_running) {
			// TODO throws a OperationException(RuntimeException)
		}
		if (_suspend) {
			// TODO throws a OperationException(RuntimeException)
		}
		runtime.sendEvent(event, name);
	}

	public void setVariableValue(String name, Object value) {
		if (!_running) {
			// TODO throws a OperationException(RuntimeException)
		}
		if (_suspend) {
			// TODO throws a OperationException(RuntimeException)
		}
		runtime.setVariableValue(name, value);
	}

	public void setVariableValue(Map<String, Object> values) {
		if (!_running) {
			// TODO throws a OperationException(RuntimeException)
		}
		if (_suspend) {
			// TODO throws a OperationException(RuntimeException)
		}
		runtime.setVariableValue(values);
	}

	public Object getVariableValue(String name) {
		return runtime.getVariableValue(name);
	}

	public Object getConstantValue(String name) {
		return runtime.getVariableValue(name);
	}

	public Map<String, Object> getVariableValues(Set<String> names) {
		return runtime.getVariableValue(names);
	}

	public Map<String, Object> getAllVariableValues() {
		return runtime.getVariableValueAll();
	}

	public Map<String, Object> getConstantValues(Set<String> names) {
		return runtime.getVariableValue(names);
	}

	public int getRunningEPSInstanceCount() {
		String[] stmtNames = admin.getStatementNames();
		int count = 0;
		if (stmtNames == null || stmtNames.length == 0) {
			return 0;
		}
		for (String name : stmtNames) {
			EPStatement e = admin.getStatement(name);
			if (e.isStarted()) {
				count++;
			}
		}

		return count;
	}

	public int getStoppedEPSInstanceCount() {
		String[] stmtNames = admin.getStatementNames();
		int count = 0;
		if (stmtNames == null || stmtNames.length == 0) {
			return 0;
		}
		for (String name : stmtNames) {
			EPStatement e = admin.getStatement(name);
			if (e.isStopped()) {
				count++;
			}
		}

		return count;
	}

	public EPS[] getRunningEPSs() {
		String[] stmtNames = admin.getStatementNames();
		List<EPS> epses = new LinkedList<EPS>();
		if (stmtNames == null || stmtNames.length == 0) {
			return new EPS[0];
		}
		for (String name : stmtNames) {
			EPStatement e = admin.getStatement(name);
			if (e.isStarted()) {
				epses.add(new EPSImpl(e, e.getName()));
			}
		}
		return epses.toArray(new EPS[0]);
	}

	public EPS[] getStoppedEPSs() {
		String[] stmtNames = admin.getStatementNames();
		List<EPS> epses = new LinkedList<EPS>();
		if (stmtNames == null || stmtNames.length == 0) {
			return new EPS[0];
		}
		for (String name : stmtNames) {
			EPStatement e = admin.getStatement(name);
			if (e.isStopped()) {
				epses.add(new EPSImpl(e, e.getName()));
			}
		}
		return epses.toArray(new EPS[0]);
	}

	public void resume() {
		_suspend = false;
	}

	public void stop() {
		_running = false;
	}

	public void suspend() {
		_suspend = true;
	}
}
