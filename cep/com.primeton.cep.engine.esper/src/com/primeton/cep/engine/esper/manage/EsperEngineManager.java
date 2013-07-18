package com.primeton.cep.engine.esper.manage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.primeton.cep.engine.api.CEPEngine;
import com.primeton.cep.engine.manage.CEPEngineManager;
import com.primeton.cep.engine.model.JVMParameter;

public class EsperEngineManager implements CEPEngineManager {

	private Map<String, CEPEngine> engines = new HashMap<String, CEPEngine>();

	public void registerEngine(CEPEngine engine) {
		engines.put(engine.getName(), engine);
	}

	public void unregisterEngine(String engineName) {
		engines.remove(engineName);
	}

	public void startEngine(String engineName) {
		CEPEngine engine = engines.get(engineName);
		verifyEngine(engine);
		engine.start();
	}

	public void stopEngine(String engineName) {
		CEPEngine engine = engines.get(engineName);
		verifyEngine(engine);
		engine.stop();
	}

	public void suspendEngine(String engineName) {
		CEPEngine engine = engines.get(engineName);
		verifyEngine(engine);
		engine.suspend();
	}

	public void resumeEngine(String engineName) {
		CEPEngine engine = engines.get(engineName);
		verifyEngine(engine);
		engine.resume();
	}

	public void startAllEngines() {
		Collection<CEPEngine> e = engines.values();
		for (CEPEngine engine : e) {
			if (e != null) {
				engine.start();
			}
		}
	}

	public void stopAllEngines() {
		Collection<CEPEngine> e = engines.values();
		for (CEPEngine engine : e) {
			if (e != null) {
				engine.stop();
			}
		}
	}

	public void suspendAllEngines() {
		Collection<CEPEngine> e = engines.values();
		for (CEPEngine engine : e) {
			if (e != null) {
				engine.suspend();
			}
		}
	}

	public void resumeAllEngines() {
		Collection<CEPEngine> e = engines.values();
		for (CEPEngine engine : e) {
			if (e != null) {
				engine.resume();
			}
		}
	}

	public CEPEngine getEngine(String engineName) {
		return engines.get(engineName);
	}

	public List<CEPEngine> getEngines() {
		Collection<CEPEngine> e = engines.values();
		List<CEPEngine> es = new ArrayList<CEPEngine>(e);
		return es;
	}

	public JVMParameter getJVMParameter() {
		// TODO
		return null;
	}

	private void verifyEngine(CEPEngine engine) {
		if (engine == null) {
			// TODO throw a EngineIsNotExistExceptoin(RuntimeException)
		}
	}
}
