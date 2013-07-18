package com.primeton.cep.engine.model;

public interface JVMParameter {

	public String getVersion();

	public String getVendor();

	public String getMinHeap();

	public String getMaxHeap();

	public String getPermHeap();

	public String getCurrentHeap();
}
