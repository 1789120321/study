package com.primeton.cep.engine.api;

import java.util.Map;
import java.util.Set;

public interface CEPEngineRuntime {

	public void sendEvent(Object event);

	public void sendEvent(String name, Object[] event);

	public void sendEvent(String name, Map<String, Object> event);

	public void setVariableValue(String name, Object value);

	public void setVariableValue(Map<String, Object> values);

	public Object getVariableValue(String name);

	public Object getConstantValue(String name);

	public Map<String, Object> getVariableValues(Set<String> names);

	public Map<String, Object> getConstantValues(Set<String> names);

	public Map<String, Object> getAllVariableValues();

	public int getRunningEPSInstanceCount();

	public int getStoppedEPSInstanceCount();

	public EPS[] getRunningEPSs();

	public EPS[] getStoppedEPSs();

	public void stop();

	public void suspend();

	public void resume();
}
