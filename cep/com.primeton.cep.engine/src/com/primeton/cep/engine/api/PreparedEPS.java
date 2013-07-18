package com.primeton.cep.engine.api;

import java.util.Map;

public interface PreparedEPS {

	public String getPreparedEPS();

	public void setString(int index, String value);

	public void setLong(int index, long value);

	public void setInt(int index, int value);

	public void setDouble(int index, double value);

	public void setFloat(int index, float value);

	public void setBoolean(int index, boolean value);

	public Map<Integer, Object> getParamters();

	public String getName();
}
