package com.primeton.cep.engine.esper.api;

import java.util.HashMap;
import java.util.Map;

import com.espertech.esper.client.EPPreparedStatement;

public class PreparedEPSImpl implements EsperPreparedEPS {

	private Map<Integer, Object> parameters = new HashMap<Integer, Object>();

	private EPPreparedStatement pstmt;
	private String name;
	private String text;

	public PreparedEPSImpl(EPPreparedStatement pstmt, String name, String text) {
		this.pstmt = pstmt;
		this.name = name;
		this.text = text;
	}

	public EPPreparedStatement getPreparedEPL() {
		return pstmt;
	}

	public String getPreparedEPS() {
		return text;
	}

	public void setString(int index, String value) {
		pstmt.setObject(index, value);
		parameters.put(index, value);
	}

	public void setLong(int index, long value) {
		pstmt.setObject(index, value);
		parameters.put(index, value);
	}

	public void setInt(int index, int value) {
		pstmt.setObject(index, value);
		parameters.put(index, value);
	}

	public void setDouble(int index, double value) {
		pstmt.setObject(index, value);
		parameters.put(index, value);
	}

	public void setFloat(int index, float value) {
		pstmt.setObject(index, value);
		parameters.put(index, value);
	}

	public void setBoolean(int index, boolean value) {
		pstmt.setObject(index, value);
		parameters.put(index, value);
	}

	public Map<Integer, Object> getParamters() {
		return parameters;
	}

	public String getName() {
		return name;
	}
}
