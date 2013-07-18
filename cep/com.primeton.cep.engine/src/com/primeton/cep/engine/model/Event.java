package com.primeton.cep.engine.model;

public interface Event {

	public Class<?> getType();

	public Object get(String name);
}
