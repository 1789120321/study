package com.primeton.cep.engine.esper.api;

import java.util.HashMap;
import java.util.Map;

import com.espertech.esper.client.ConfigurationOperations;
import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPPreparedStatement;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.util.UuidGenerator;
import com.primeton.cep.engine.api.CEPEngine;
import com.primeton.cep.engine.api.CEPEngineConfig;
import com.primeton.cep.engine.api.CEPEngineRuntime;
import com.primeton.cep.engine.api.EPS;
import com.primeton.cep.engine.api.PreparedEPS;

public class EsperEngineImpl implements CEPEngine {

	private boolean _running = true;
	private boolean _suspend = false;

	private Map<String, EPS> allEPS = new HashMap<String, EPS>();
	private Map<String, PreparedEPS> allPEPS = new HashMap<String, PreparedEPS>();

	private EPServiceProvider epService;
	private EPAdministrator admin;

	private CEPEngineRuntime runtime;

	public EsperEngineImpl(EPServiceProvider epService) {
		this.epService = epService;
		admin = epService.getEPAdministrator();
	}

	public EPS createEPS(PreparedEPS peps) {
		return createEPS(peps, null);
	}

	public EPS createEPS(PreparedEPS peps, String name) {
		if (!_running) {
			// TODO throws a OperationException(RuntimeException)
		}
		if (_suspend) {
			// TODO throws a OperationException(RuntimeException)
		}
		if (!(peps instanceof EsperPreparedEPS)) {
			// TODO throws a RuntimeException 
		}
		EsperPreparedEPS pepsi = (EsperPreparedEPS) peps;
		if (name == null || name.trim().equals("")) {
			name = UuidGenerator.generate();
		}
		EPStatement stmt = admin.create(pepsi.getPreparedEPL(), name);
		EPS e = new EPSImpl(stmt, name);
		allEPS.put(e.getName(), e);

		return e;
	}

	public EPS createEPS(String stmt) {
		return createEPS(stmt, null);
	}

	public EPS createEPS(String stmt, String name) {
		if (!_running) {
			// TODO throws a OperationException(RuntimeException)
		}
		if (_suspend) {
			// TODO throws a OperationException(RuntimeException)
		}
		if (name == null || name.trim().equals("")) {
			name = UuidGenerator.generate();
		}
		EPStatement statement = admin.createEPL(stmt, name);
		EPS e = new EPSImpl(statement, name);
		allEPS.put(e.getName(), e);

		return e;
	}

	public PreparedEPS createPreparedEPS(String pstmt) {
		return createPreparedEPS(pstmt, null);
	}

	public PreparedEPS createPreparedEPS(String pstmt, String name) {
		if (!_running) {
			// TODO throws a OperationException(RuntimeException)
		}
		if (_suspend) {
			// TODO throws a OperationException(RuntimeException)
		}
		if (name == null || name.trim().equals("")) {
			name = UuidGenerator.generate();
		}
		EPPreparedStatement pStatement = admin.prepareEPL(pstmt);
		PreparedEPS pe = new PreparedEPSImpl(pStatement, name, pstmt);
		allPEPS.put(pe.getName(), pe);

		return pe;
	}

	public EPS createPattern(String stmt) {
		if (!_running) {
			// TODO throws a OperationException(RuntimeException)
		}
		if (_suspend) {
			// TODO throws a OperationException(RuntimeException)
		}
		EPStatement statement = admin.createPattern(stmt);
		EPS e = new EPSImpl(statement, statement.getName());
		allEPS.put(e.getName(), e);

		return e;
	}

	public EPS createContext(String stmt) {
		if (!_running) {
			// TODO throws a OperationException(RuntimeException)
		}
		if (_suspend) {
			// TODO throws a OperationException(RuntimeException)
		}
		EPStatement statement = admin.createEPL(stmt);
		EPS e = new EPSImpl(statement, statement.getName());
		allEPS.put(e.getName(), e);

		return e;
	}

	public EPS[] getAllEPSs() {
		if (allEPS.isEmpty()) {
			return new EPS[0];
		}
		return allEPS.values().toArray(new EPS[0]);
	}

	public PreparedEPS[] getAllPreparedEPSs() {
		if (allPEPS.isEmpty()) {
			return new PreparedEPS[0];
		}
		return allPEPS.values().toArray(new PreparedEPS[0]);
	}

	public CEPEngineConfig getEngineConfig() {
		ConfigurationOperations config = admin.getConfiguration();
		return new EsperEngineConfigImpl(config);
	}

	public String getEngineInfo() {
		return epService.getURI();
	}

	public CEPEngineRuntime getEngineRuntime() {
		runtime = new EsperEngineRuntimeImpl(epService.getEPRuntime(), admin);
		return runtime;
	}

	public EPS getEPS(String name) {
		return allEPS.get(name);
	}

	public PreparedEPS getPreparedEPS(String name) {
		return allPEPS.get(name);
	}

	public void destroyAllEPS() {
		admin.destroyAllStatements();
	}

	public void startAllEPS() {
		admin.startAllStatements();
	}

	public void stopAllEPS() {
		admin.stopAllStatements();
	}

	public void start() {
		_running = true;
	}

	public void stop() {
		_running = false;
	}

	public void resume() {
		_suspend = false;
		runtime.resume();
	}

	public void suspend() {
		_suspend = true;
		runtime.suspend();
	}

	public void destroy() {
		runtime.stop();
		epService.destroy();
	}

	public String getFullName() {
		// TODO how to spell the full name
		return null;
	}

	public String getName() {
		return epService.getURI();
	}

}
