package com.primeton.cep.engine.api;

import java.util.Map;

import com.primeton.cep.engine.model.Constant;

public interface CEPEngineConfig {

	public void addImport(Class<?> clazz);

	public void addPackage(String packagePath);

	public void addEventTypeAuto(String packagePath);

	public void addEventType(Class<?> clazz);

	public void addEventType(String name, Class<?> clazz);

	public void addMapEventType(String name, Map<String, Class<?>> map);

	public void addArrayEventType(String name, String[] propNames, Class<?>[] propTypes);

	public void addVariable(String name, Class<?> clazz, Object initialValue);

	public void addConstant(String name, Class<?> clazz, Object initialValue);

	public void addConstant(Constant constant);

	public boolean removeEventType(String name);

	public boolean forceRemoveEventType(String name);

	public boolean removeVariable(String name);

	public boolean forceRemoveVariable(String name);

	public boolean removeConstant(String name);

	public boolean forceRemoveConstant(String name);

	public void increaseUpdateArrayEventType(String name, String[] propNames, Class<?>[] propTypes);

	public void increaseUpdateMapEventType(String name, Map<String, Class<?>> map);

	public Class<?> getEventType(String name);

	public Class<?> getVariable(String name);

	public Constant getConstant(String name);

	public boolean isEventTypeExist(String name);
}
