package com.primeton.cep.engine.manage;

import java.util.List;

import com.primeton.cep.engine.api.CEPEngine;
import com.primeton.cep.engine.model.JVMParameter;

public interface CEPEngineManager {

	public void registerEngine(CEPEngine engine);

	public void unregisterEngine(String engineName);

	public void startEngine(String engineName);

	public void stopEngine(String engineName);

	public void suspendEngine(String engineName);

	public void resumeEngine(String engineName);

	public void startAllEngines();

	public void stopAllEngines();

	public void suspendAllEngines();

	public void resumeAllEngines();

	public CEPEngine getEngine(String engineName);

	public List<CEPEngine> getEngines();

	public JVMParameter getJVMParameter();
}
