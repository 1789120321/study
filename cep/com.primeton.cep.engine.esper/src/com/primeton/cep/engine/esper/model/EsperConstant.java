package com.primeton.cep.engine.esper.model;

import com.primeton.cep.engine.model.Constant;

public class EsperConstant implements Constant {

	private Class<?> type;
	private String name;
	private Object value;

	public EsperConstant(Class<?> clazz, String name, Object value) {
		this.type = clazz;
		this.name = name;
		this.value = value;
	}

	public Class<?> getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public Object getValue() {
		return value;
	}

}
