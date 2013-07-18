package com.primeton.cep.engine.esper.api;

import java.util.HashMap;
import java.util.Map;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.ConfigurationOperations;
import com.primeton.cep.engine.esper.model.EsperConstant;
import com.primeton.cep.engine.model.Constant;

public class EsperEngineConfigImpl implements EsperEngineConfig {

	private Map<String, Object> constantMap = new HashMap<String, Object>();

	private ConfigurationOperations config;

	public EsperEngineConfigImpl(ConfigurationOperations config) {
		this.config = config;
	}

	public void addEventTypeAuto(String packagePath) {
		config.addEventTypeAutoName(packagePath);
	}

	public void addImport(Class<?> clazz) {
		config.addImport(clazz);
	}

	public void addPackage(String packagePath) {
		config.addImport(packagePath);
	}

	public void addEventType(Class<?> clazz) {
		config.addEventType(clazz);
	}

	public void addEventType(String name, Class<?> clazz) {
		if (!isEventTypeExist(name)) {
			// TODO throw a configException(RuntimeException)
		}
		config.addEventType(name, clazz);
	}

	public void addMapEventType(String name, Map<String, Class<?>> map) {
		if (!isEventTypeExist(name)) {
			// TODO throw a configException(RuntimeException)
		}
		Map<String, Object> m = new HashMap<String, Object>();
		for (String key : map.keySet()) {
			m.put(key, map.get(key));
		}
		config.addEventType(name, m);
	}

	public void addArrayEventType(String name, String[] propNames, Class<?>[] propTypes) {
		if (!isEventTypeExist(name)) {
			// TODO throw a configException(RuntimeException)
		}
		config.addEventType(name, propNames, propTypes);
	}

	public void addVariable(String name, Class<?> clazz, Object initialValue) {
		config.addVariable(name, clazz, initialValue);
	}

	public void addConstant(String name, Class<?> clazz, Object initialValue) {
		config.addVariable(name, clazz.getName(), initialValue, true);
		constantMap.put(name, initialValue);
	}

	public void addConstant(Constant constant) {
		addConstant(constant.getName(), constant.getType(), constant.getValue());
	}

	public boolean removeEventType(String name) {
		return config.removeEventType(name, false);
	}

	public boolean forceRemoveEventType(String name) {
		return config.removeEventType(name, true);
	}

	public boolean removeVariable(String name) {
		return config.removeVariable(name, false);
	}

	public boolean forceRemoveVariable(String name) {
		return config.removeVariable(name, true);
	}

	public boolean removeConstant(String name) {
		return config.removeVariable(name, false);
	}

	public boolean forceRemoveConstant(String name) {
		return config.removeVariable(name, true);
	}

	public void increaseUpdateArrayEventType(String name, String[] propNames, java.lang.Class<?>[] propTypes) {
		if (!isEventTypeExist(name)) {
			// TODO throw a configException(RuntimeException)
		}
		config.updateObjectArrayEventType(name, propNames, propTypes);
	}

	public void increaseUpdateMapEventType(String name, Map<String, Class<?>> map) {
		if (!isEventTypeExist(name)) {
			// TODO throw a configException(RuntimeException)
		}
		Map<String, Object> m = new HashMap<String, Object>();
		for (String key : map.keySet()) {
			m.put(key, map.get(key));
		}
		config.updateMapEventType(name, m);
	}

	public Class<?> getEventType(String name) {
		return config.getEventType(name).getUnderlyingType();
	}

	public Class<?> getVariable(String name) {
		return config.getEventType(name).getUnderlyingType();
	}

	public Constant getConstant(String name) {
		if (!isEventTypeExist(name)) {
			return null;
		}
		Class<?> clazz = config.getEventType(name).getUnderlyingType();
		Object value = constantMap.get(name);
		return new EsperConstant(clazz, name, value);
	}

	public boolean isEventTypeExist(String name) {
		return config.isEventTypeExists(name);
	}

	public Configuration getConfiguration() {
		return (Configuration) config;
	}

}
